package ru.netology.nmedia.adapter

import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.service.WordEndingService

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onIneractionListener: OnInteractionListener,

    ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.publshed
            content.text = post.content
            like.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
            )
            likeCount.text = WordEndingService.getCountWord(post.likes)
            shareCount.text = WordEndingService.getCountWord(post.shares)

            like.setOnClickListener { onIneractionListener.onLike(post) }
            share.setOnClickListener { onIneractionListener.onShare(post) }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_menu)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                onIneractionListener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                onIneractionListener.onEdit(post)
                                true
                            }

                            else -> false

                        }


                    }
                }.show()
            }

        }
    }
}