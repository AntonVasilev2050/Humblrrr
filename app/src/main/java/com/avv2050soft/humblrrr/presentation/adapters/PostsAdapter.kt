package com.avv2050soft.humblrrr.presentation.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.humblrrr.databinding.ItemPostBinding
import com.avv2050soft.humblrrr.domain.models.ApiResult
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.avv2050soft.humblrrr.presentation.utils.toStringWithKNotation
import com.bumptech.glide.Glide

class PostsAdapter(
    private val onClick: (Children) -> Unit,
    private val onClickShare: (String) -> Unit,
    private val onAuthorClick: (String) -> Unit,
    private val onVoteClick: (Int, String, Int) -> Unit,
    private val onOpenCommentsClick: (Children) -> Unit
) : PagingDataAdapter<Children, PostViewHolder>(DiffUtilCallbackChildren()) {

    fun updatePostScore(data: ApiResult<Int>, voteDirection: Int) {
        data.data?.let { position ->
            snapshot()[position]?.let {
                it.data.ups = it.data.ups + voteDirection
                notifyItemChanged(position)
            }
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let { children ->
                textViewAuthor.text = children.data.author
                textViewTitle.text = children.data.title
                textViewContent.text = children.data.selfText
                Glide
                    .with(imageViewContent.context)
                    .load(children.data.url)
                    .into(imageViewContent)
                if (children.data.isVideo) {
                    val videoUri = Uri.parse(
                        children.data.media.redditVideo.fallbackUrl.substringBefore("?", "?")
                    )
                    val player = ExoPlayer.Builder(playerView.context).build()
                    player.playWhenReady
                    player.repeatMode
                    playerView.player = player
                    val mediaItem = MediaItem.fromUri(videoUri)
                    player.setMediaItem(mediaItem)
                    player.prepare()
                    player.play()
                } else {
                    playerView.visibility = View.GONE
                }

                textViewPostScore.text = children.data.ups.toStringWithKNotation()
                textViewCommentsCount.text = children.data.numComments?.toStringWithKNotation()
                root.setOnClickListener {
                    if (position != RecyclerView.NO_POSITION) {
                        onClick.invoke(item)
                    }
                }
                imageButtonShare.setOnClickListener {
                    if (position != RecyclerView.NO_POSITION) {
                        onClickShare.invoke(item.data.url)
                    }
                }
                textViewAuthor.setOnClickListener {
                    onAuthorClick.invoke(item.data.author.toString())
                }

                imageButtonVoteUp.setOnClickListener {
                    onVoteClick.invoke(1, item.data.name, position)
                }

                imageButtonVoteDown.setOnClickListener {
                    onVoteClick.invoke(-1, item.data.name, position)
                }

                imageButtonOpenComments.setOnClickListener {
                    onOpenCommentsClick.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}

class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)