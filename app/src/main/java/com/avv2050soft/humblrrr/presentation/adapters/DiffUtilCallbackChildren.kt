package com.avv2050soft.humblrrr.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.avv2050soft.humblrrr.domain.models.response.Children

class DiffUtilCallbackChildren : DiffUtil.ItemCallback<Children>() {
    override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean =
        oldItem.data.id == newItem.data.id

    override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean =
        oldItem == newItem
}