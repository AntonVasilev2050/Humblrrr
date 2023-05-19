package com.avv2050soft.humblrrr.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.humblrrr.databinding.ItemPostBinding
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.bumptech.glide.Glide

class PostsAdapter (
    private val onClick: (Children) -> Unit,
        ) : PagingDataAdapter<Children, PostViewHolder>(DiffUtilCallbackChildren()){
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            item?.let {
                textViewAuthor.text = it.data.author
                textViewTitle.text = it.data.title
                textViewContent.text = it.data.selfText
                Glide
                    .with(imageViewContent.context)
                    .load(it.data.url)
                    .into(imageViewContent)
                textViewPostScore.text = it.data.ups.toString()
                textViewCommentsCount.text = it.data.numComments.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}

class PostViewHolder(val binding : ItemPostBinding) : RecyclerView.ViewHolder(binding.root)