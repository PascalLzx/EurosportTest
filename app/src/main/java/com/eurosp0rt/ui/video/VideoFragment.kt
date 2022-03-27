package com.eurosp0rt.ui.video

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.eurosp0rt.R
import com.eurosp0rt.databinding.VideoFragmentBinding

class VideoFragment : Fragment() {

    private lateinit var binding: VideoFragmentBinding
    private val args: VideoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.video_fragment, container, false
        )
        val mediaController = MediaController(activity)
        binding.videoView.setMediaController(mediaController);
        binding.videoView.setVideoPath(args.link)
        binding.videoView.setOnPreparedListener { mediaPlayer ->
            binding.loader.visibility = View.GONE
            mediaPlayer.start()
            mediaPlayer.isLooping = false
        }
        return binding.root
    }
}