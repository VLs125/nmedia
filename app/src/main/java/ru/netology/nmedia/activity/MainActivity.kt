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
import ru.netology.nmedia.service.WordEndingService
import ru.netology.nmedia.storage.MainStorage

class MainActivity : AppCompatActivity() {
    val storage = MainStorage()
    val wordsService = WordEndingService()
    override fun onCreate(savedInstanceState: Bundle?) {
        storage.initializeStorageForTest()

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
        // set shares and likes count
        binding.likeCount.text = wordsService.getCountWord(storage.getLikeCount())
        binding.shareCount.text = wordsService.getCountWord(storage.getShareCount())

        binding.like.setImageResource(
            if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
        )
        with(binding) {
            like.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) {
                    like.setImageResource(R.drawable.ic_liked_24)
                    storage.increaseLikeCount()
                    likeCount.text =
                        wordsService.getCountWord(storage.getLikeCount())
                } else {
                    like.setImageResource(
                        R.drawable.ic_like_24
                    )
                    storage.decreaseLikeCount()
                    likeCount.text =
                        wordsService.getCountWord(storage.getLikeCount())

                }
            }
            share.setOnClickListener {
                storage.increaseShareCount()
                shareCount.text = wordsService.getCountWord(storage.getShareCount())
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