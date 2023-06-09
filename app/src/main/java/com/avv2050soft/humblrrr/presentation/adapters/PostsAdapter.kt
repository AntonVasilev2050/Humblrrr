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
import com.avv2050soft.humblrrr.domain.models.ApiResult
import com.avv2050soft.humblrrr.domain.models.response.Children
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
    private var activeVideoPosition: Int = RecyclerView.NO_POSITION

    companion object {
        internal var player: ExoPlayer? = null
    }

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
                    val videoUri = Uri.parse(children.data.media.redditVideo.dashUrl)
                    if (position == activeVideoPosition) {
                        holder.startPlayer(videoUri)
                    } else {
                        holder.stopPlayer()
                    }
                    holder.startPlayer(videoUri)
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

    override fun onViewAttachedToWindow(holder: PostViewHolder) {
        super.onViewAttachedToWindow(holder)
        player?.play()
    }

    override fun onViewDetachedFromWindow(holder: PostViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.stopPlayer()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        player?.play()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        player?.stop()
    }

    override fun onViewRecycled(holder: PostViewHolder) {
        super.onViewRecycled(holder)
        holder.stopPlayer()
    }
}

@UnstableApi
class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
    fun startPlayer(videoUri: Uri) {
        PostsAdapter.player = ExoPlayer.Builder(binding.root.context).build()
        PostsAdapter.player?.playWhenReady = false
        PostsAdapter.player?.repeatMode = Player.REPEAT_MODE_OFF
        binding.playerView.player = PostsAdapter.player
        binding.playerView.controllerAutoShow = true
        val mediaItem = MediaItem.fromUri(videoUri)
        PostsAdapter.player?.setMediaItem(mediaItem)
        PostsAdapter.player?.prepare()
//                    player?.play()
    }

    fun stopPlayer(){
        PostsAdapter.player?.stop()
//        PostsAdapter.player?.release()
    }
}