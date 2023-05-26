package com.avv2050soft.humblrrr.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.databinding.ItemFriendBinding
import com.avv2050soft.humblrrr.domain.models.friends.Children
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class UserFriendsAdapter(

) : PagingDataAdapter<Children, FriendsViewHolder>(DiffUtilCallbackFriends()) {
    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            item?.let {
                Glide
                    .with(imageViewUserFriendAvatar.context)
                        // TODO: load a real avatar of the user
                    .load(it.id)
                    .placeholder(R.drawable.reddit_placeholder)
                    .into(imageViewUserFriendAvatar)
                textViewUserFriendName.text = it.name
                val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                textViewUserFriendDateTime.text = dateFormat.format((it.date * 1000L))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        return FriendsViewHolder(
            ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}

class DiffUtilCallbackFriends : DiffUtil.ItemCallback<Children>() {
    override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean =
        oldItem == newItem
}

class FriendsViewHolder(val binding : ItemFriendBinding) : RecyclerView.ViewHolder(binding.root)