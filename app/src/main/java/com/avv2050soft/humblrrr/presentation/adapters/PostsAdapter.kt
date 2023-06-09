package com.avv2050soft.humblrrr.presentation.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.humblrrr.databinding.ItemPostBinding
import com.avv2050soft.humblrrr.domain.ExoPlayerInstance
import com.avv2050soft.humblrrr.domain.models.ApiResult
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.avv2050soft.humblrrr.presentation.PostsFragment
import com.avv2050soft.humblrrr.presentation.utils.toStringWithKNotation
import com.bumptech.glide.Glide

@UnstableApi
class PostsAdapter(
    private val onClick: (Children) -> Unit,
    private val onClickShare: (String) -> Unit,
    private val onAuthorClick: (String) -> Unit,
    private val onVoteClick: (Int, String, Int) -> Unit,
    private val onOpenCommentsClick: (Children) -> Unit
) : PagingDataAdapter<Children, PostViewHolder>(DiffUtilCallbackChildren()) {

    private var player: ExoPlayer? = null

    fun updatePostScore(data: ApiResult<Int>, voteDirection: Int) {
        data.data?.let { position ->
            snapshot()[position]?.let {
                it.data.ups = it.data.ups + voteDirection
                notifyItemChanged(position)
            }
        }
    }

    private fun playerStop() {
        player?.stop()
        player?.release()
        player = null
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
                    playerView.visibility = View.VISIBLE
                    imageViewContent.visibility = View.GONE
                    val videoUri = Uri.parse(children.data.media.redditVideo.dashUrl)
                    player = ExoPlayerInstance.get(root.context)
                    holder.startPlayer(videoUri, player!!)
                } else {
                    playerView.visibility = View.GONE
                    imageViewContent.visibility = View.VISIBLE
                }

                textViewPostScore.text = children.data.ups.toStringWithKNotation()
                textViewCommentsCount.text = children.data.numComments?.toStringWithKNotation()

                root.setOnClickListener {
                    onClick.invoke(item)
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
                    playerStop()
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

    override fun onViewAttachedToWindow(holder: PostViewHolder) {
        super.onViewAttachedToWindow(holder)
        player?.play()
    }

    override fun onViewDetachedFromWindow(holder: PostViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (player?.isPlaying == true) {
            playerStop()
        }
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (player?.isPlaying == false) {
            player?.play()
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        if (player?.isPlaying == true) {
            playerStop()
        }
    }

    override fun onViewRecycled(holder: PostViewHolder) {
        super.onViewRecycled(holder)
        if (player?.isPlaying == true) {
            playerStop()
        }
    }
}

@UnstableApi
class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
    fun startPlayer(videoUri: Uri, player: ExoPlayer) {
        player.playWhenReady = false
        player.repeatMode = Player.REPEAT_MODE_OFF
        binding.playerView.player = player
        binding.playerView.controllerAutoShow = false
//        val mediaItem = MediaItem.fromUri(videoUri)
        val mediaItem =
            MediaItem.Builder()
                .setUri(videoUri)
                .setClippingConfiguration(
                    MediaItem.ClippingConfiguration.Builder()
                        .setStartPositionMs(0)
                        .setEndPositionMs(3000)
                        .build()
                )
                .build()
        player.setMediaItem(mediaItem)
        player.prepare()
//        player?.play()
    }
}