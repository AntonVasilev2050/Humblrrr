package com.avv2050soft.humblrrr.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.get
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.databinding.ItemSubredditBinding
import com.avv2050soft.humblrrr.domain.models.response.Children
import java.text.SimpleDateFormat
import java.util.Locale

class SubredditAdapter(
    private val onClick: (Children) -> Unit,
    private val onClickImage: (Children) -> Unit
) : PagingDataAdapter<Children, SubredditViewHolder>(DiffUtilCallback()) {
    override fun onBindViewHolder(holder: SubredditViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            item?.let {
                textViewTitle.text= it.data.title
                val dateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.getDefault())
                textViewCreated.text = dateFormat.format((it.data.createdUtc * 1000L))
            }
            root.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    item?.let {
                        onClick.invoke(it)
                    }
                }
            }
            imageView.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    item?.let {
                        onClickImage.invoke(it)
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

class DiffUtilCallback : DiffUtil.ItemCallback<Children>() {
    override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean =
        oldItem.data.id == newItem.data.id

    override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean =
        oldItem == newItem
}

class SubredditViewHolder(val binding: ItemSubredditBinding) : RecyclerView.ViewHolder(binding.root)