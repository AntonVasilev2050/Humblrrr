package com.avv2050soft.humblrrr.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.databinding.ItemSubredditBinding
import com.avv2050soft.humblrrr.domain.models.ApiResult
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class SubredditAdapter(
    private val onClick: (Children) -> Unit,
    private val onClickSubscribe: (displayName: String, userIsSubscribed: Boolean, position: Int) -> Unit,
    private val onClickShare: (String) -> Unit
) : PagingDataAdapter<Children, SubredditViewHolder>(DiffUtilCallbackChildren()) {

    fun updateElement(data: ApiResult<Int>) {
        data.data?.let { position ->
            snapshot()[position]?.let {
                if (it.data.userIsSubscriber) {
                    it.data.userIsSubscriber = false
                    it.data.subscribers--
                } else {
                    it.data.userIsSubscriber = true
                    it.data.subscribers++
                }
                notifyItemChanged(position)
            }
        }
    }

    override fun onBindViewHolder(holder: SubredditViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                Glide
                    .with(imageViewLogo)
                    .load(it.data.iconImg)
                    .placeholder(R.drawable.reddit_placeholder)
                    .circleCrop()
                    .into(imageViewLogo)
                textViewName.text = it.data.displayNamePrefixed
                textViewDescription.text = it.data.publicDescription
                textViewSubscribers.text = it.data.subscribers.toString()
                val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                textViewCreated.text = dateFormat.format((it.data.createdUtc * 1000L))
                if (it.data.userIsSubscriber) {
                    imageViewSubscribeButton.setImageResource(R.drawable.subscribed)
                } else {
                    imageViewSubscribeButton.setImageResource(R.drawable.subscribe)
                }
            }
            root.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    item?.let {
                        onClick.invoke(it)
                    }
                }
            }
            imageViewShareButton.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    item?.let {
                        onClickShare.invoke(it.data.url)
                    }
                }
            }
            imageViewSubscribeButton.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    item?.let {
                        onClickSubscribe.invoke(
                            it.data.displayName,
                            it.data.userIsSubscriber,
                            position
                        )
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubredditViewHolder {
        return SubredditViewHolder(
            ItemSubredditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}

class DiffUtilCallbackChildren : DiffUtil.ItemCallback<Children>() {
    override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean =
        oldItem.data.id == newItem.data.id

    override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean =
        oldItem == newItem
}

class SubredditViewHolder(val binding: ItemSubredditBinding) : RecyclerView.ViewHolder(binding.root)