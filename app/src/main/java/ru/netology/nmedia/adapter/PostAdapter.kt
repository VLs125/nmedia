package ru.netology.nmedia.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

class PostAdapter(private val likeClickListener: OnLikeClicked, private val onShareClickListener: OnShareClicked) :
    RecyclerView.Adapter<PostViewHolder>(

    ) {
    var posts = emptyList<Post>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cardPostBinding = CardPostBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(cardPostBinding, likeClickListener, onShareClickListener)
    }

    override fun getItemCount(): Int = posts.size


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }
}