package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.storage.MainStorage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val storage = MainStorage()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyInset(binding.main)
        val post = Post(
            id = 1,
            author = "Нетология",
            publshed = "21 мая в 18:36",
            content = "\"Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов " +
                    "по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике " +
                    "и управлению. Мы растём сами и помогаем расти студентам: от новичков до " +
                    "уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в " +
                    "каждом уже есть сила, которая заставляет хотеть больше, целиться выше, " +
                    "бежать быстрее. Наша миссия — помочь встать на путь роста и начать" +
                    " цепочку перемен → http://netolo.gy/fyb\""
        )
        binding.likeCount.setText(
            storage.getLikeCount()
        )
        binding.shareCount.setText(
            storage.getShareCount()
        )
        binding.like.setImageResource(
            if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
        )
        with(binding) {
            like.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) like.setImageResource(R.drawable.ic_liked_24)
                else like.setImageResource(
                    R.drawable.ic_like_24
                )
            }
            content.text = post.content
            author.text = post.author
            published.text = post.publshed
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