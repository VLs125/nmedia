package ru.netology.nmedia.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyInset(binding.root)
        val postViewModel: PostViewModel by viewModels()
        val newPostLauncher = registerForActivityResult(NewPostContract) {
            it ?: return@registerForActivityResult
            postViewModel.savePost(it)
        }

        val adapter =
            PostAdapter(object : OnInteractionListener {
                override fun onLike(post: Post) {
                    postViewModel.like(post.id)
                }

                override fun onRemove(post: Post) {
                    postViewModel.removePost(post.id)

                }

                override fun onEdit(post: Post) {
                    postViewModel.onEdit(post)

                }


                override fun onShare(post: Post) {
                    postViewModel.increaseShare(post.id)
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, post.content)
                    }
                    val chooser = Intent.createChooser(intent, "Поделится постом")
                    startActivity(chooser)
                }

                override fun onVideoPlay(videoUrl: String?) {
                    videoUrl ?: return
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                    startActivity(intent)
                }
            })
        binding.main.adapter = adapter
        postViewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.add.setOnClickListener {
            newPostLauncher.launch("")
        }

        postViewModel.edit.observe(this) { editedPost ->
            if (editedPost.id == 0L) {
                return@observe
            } else {
                val intent = Intent(this, NewPostActivity::class.java)
                intent.putExtra("content", editedPost.content)
                startActivity(intent)
            }
        }
    }

    private fun applyInset(main: View) {
        ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Для клавиатуры:
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val isImeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            v.setPadding(
                v.paddingLeft,
                systemBars.top,
                v.paddingRight,
                if (isImeVisible) imeInsets.bottom else systemBars.bottom
            )
            insets
        }
    }
}