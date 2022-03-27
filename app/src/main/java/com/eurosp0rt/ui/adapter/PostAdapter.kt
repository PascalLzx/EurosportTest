package com.eurosp0rt.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eurosp0rt.databinding.ItemStoryBinding
import com.eurosp0rt.databinding.ItemVideoBinding
import com.eurosp0rt.domain.models.Post
import com.eurosp0rt.domain.models.PostType

class PostAdapter(private val clickListener: PostListener) :
    ListAdapter<Post, RecyclerView.ViewHolder>(PostItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PostType.VIDEO.ordinal -> VideoViewHolder.from(parent)
            PostType.STORY.ordinal -> StoryViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VideoViewHolder -> {
                holder.bind(getItem(position), clickListener)
            }
            is StoryViewHolder -> {
                holder.bind(getItem(position), clickListener)
            }
        }
    }

    class VideoViewHolder private constructor(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): RecyclerView.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemVideoBinding.inflate(layoutInflater, parent, false)
                return VideoViewHolder(binding)
            }
        }

        fun bind(item: Post, clickListener: PostListener) {
            binding.post = item
            binding.root.setOnClickListener {
                clickListener.onItemClick(item)
            }
            binding.executePendingBindings()
        }
    }

    class StoryViewHolder private constructor(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): StoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemStoryBinding.inflate(layoutInflater, parent, false)
                return StoryViewHolder(binding)
            }
        }

        fun bind(item: Post, clickListener: PostListener) {
            binding.post = item
            binding.root.setOnClickListener {
                clickListener.onItemClick(item)
            }
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val post = getItem(position) as Post
        return post.postType.ordinal
    }
}

class PostItemDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

interface PostListener {
    fun onItemClick(post: Post)
}