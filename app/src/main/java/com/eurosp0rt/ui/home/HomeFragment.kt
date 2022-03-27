package com.eurosp0rt.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eurosp0rt.R
import com.eurosp0rt.databinding.HomeFragmentBinding
import com.eurosp0rt.domain.models.Post
import com.eurosp0rt.domain.models.PostType
import com.eurosp0rt.domain.utils.Resource
import com.eurosp0rt.ui.adapter.PostAdapter
import com.eurosp0rt.ui.adapter.PostListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.home_fragment, container, false
        )
        val adapter = PostAdapter(object : PostListener {
            override fun onItemClick(post: Post) {
                when(post.postType) {
                    PostType.STORY ->
                        findNavController()
                            .navigate(HomeFragmentDirections.actionHomeFragmentToStoryFragment(post.id))
                    PostType.VIDEO ->
                        findNavController()
                            .navigate(HomeFragmentDirections.actionHomeFragmentToVideoFragment(post.url!!))
                }
            }
        })
        val manager = LinearLayoutManager(activity)
        binding.postList.adapter = adapter
        binding.postList.layoutManager = manager
        binding.swipeRefresh.setOnRefreshListener {
            adapter.submitList(emptyList())
            viewModel.refreshData()
        }
        viewModel.refreshData()
        viewModel.posts.observe(this) {
            it?.let { resource ->
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        adapter.submitList(resource.data?.let { data -> viewModel.order(data) })
                        binding.swipeRefresh.isRefreshing = false
                    }
                    Resource.Status.LOADING -> {
                        binding.swipeRefresh.isRefreshing = true
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(activity, resource.message, Toast.LENGTH_SHORT).show()
                        binding.swipeRefresh.isRefreshing = false
                    }
                }
            }
        }
        return binding.root
    }
}