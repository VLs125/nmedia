package ru.netology.nmedia.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.service.WordEndingService
import ru.netology.nmedia.viewmodel.LikeViewModel
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.viewmodel.ShareViewModel

class MainActivity : AppCompatActivity() {
    private val wordsService = WordEndingService()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyInset(binding.main)
        val postViewModel: PostViewModel by viewModels()
        val likeViewModel: LikeViewModel by viewModels()
        val shareViewModel: ShareViewModel by viewModels()

        likeViewModel.data.observe(this) { it ->
            with(binding) {
                likeCount.text = wordsService.getCountWord(it)
            }
        }
        shareViewModel.data.observe(this) { it ->
            with(binding) {
                shareCount.text = wordsService.getCountWord(it)
            }
        }

        postViewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.publshed
                content.text = post.content
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )

            }
        }

        binding.like.setOnClickListener {
            if (postViewModel.isLiked()) {
                postViewModel.like()
                likeViewModel.decreaseLike()

            } else {
                postViewModel.like()
                likeViewModel.increaseLike()

            }
        }

        binding.share.setOnClickListener {
            shareViewModel.increaseShare()
        }
    }

    private fun applyInset(main: View) {
        ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                v.paddingLeft + systemBars.left,
                v.paddingTop + systemBars.top,
                v.paddingRight + systemBars.right,
                v.paddingBottom + systemBars.bottom
            )
            insets
        }
    }
}