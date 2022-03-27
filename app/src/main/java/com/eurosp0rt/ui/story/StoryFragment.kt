package com.eurosp0rt.ui.story

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.eurosp0rt.R
import com.eurosp0rt.databinding.StoryFragmentBinding
import com.eurosp0rt.domain.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoryFragment : Fragment() {

    private lateinit var binding: StoryFragmentBinding
    private val viewModel: StoryViewModel by viewModel()
    private val args: StoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.story_fragment, container, false
        )

        binding.lifecycleOwner = this
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.share.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, viewModel.getTextToShare())
                type = "text/plain"
            }
            startActivity(sendIntent)
        }
        val id = args.id
        viewModel.getPost(id)
        viewModel.post.observe(this) {
            it?.let { resource ->
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        binding.post = resource.data
                    }
                    Resource.Status.LOADING -> {
                        binding.loader.visibility = View.GONE
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(activity, resource.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return binding.root
    }
}