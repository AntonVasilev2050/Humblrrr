package com.avv2050soft.humblrrr.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.databinding.ItemCommentBinding
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class CommentsAdapter(
    private val onAuthorClick: (String) -> Unit,
    private val onVoteClick: (Int, String, Int) -> Unit
) : PagingDataAdapter<Children, CommentsViewHolder>(DiffUtilCallbackChildren()) {
    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                Glide
                    .with(imageViewCommenterAvatar.context)
                    .load(it.data.iconImg)
                    .placeholder(R.drawable.reddit_placeholder)
                    .into(imageViewCommenterAvatar)
                textViewCommentAuthor.text = it.data.author
                val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                textViewCommentDateTime.text = dateFormat.format((it.data.createdUtc * 1000L))
                textViewComment.text = it.data.body
                textViewCommentScore.text = it.data.ups.toString()
                textViewCommentAuthor.setOnClickListener {
                    onAuthorClick.invoke(item.data.author.toString())
                }

                imageButtonVoteUp.setOnClickListener {
                    onVoteClick.invoke(1, item.data.name, position)
                }

                imageButtonVoteDown.setOnClickListener {
                    onVoteClick.invoke(-1, item.data.name, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}

class CommentsViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root)