package ru.netology.nmedia.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.internal.ViewUtils.showKeyboard
import ru.netology.nmedia.R
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
        applyInset(binding.main)
        val postViewModel: PostViewModel by viewModels()
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
                }
            })
        binding.main.adapter = adapter
        postViewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        postViewModel.edit.observe(this) { editedPost ->
            if (editedPost.id == 0L) {
                return@observe
            } else {
                binding.postText.setText(editedPost.content)
                binding.postText.requestFocus()
                showKeyboard(binding.postText)
            }
        }
        binding.addPostButton.setOnClickListener {
            val text = binding.postText.text.toString().trim()
            if (text.isBlank() || text.isEmpty()) {
                Toast.makeText(
                    this,
                    R.string.empty_text_error,
                    Toast.LENGTH_LONG
                )
                    .show()
                return@setOnClickListener
            }
            postViewModel.savePost(text)
            binding.postText.setText("")
            binding.postText.clearFocus()
            hideKeyboard(it)
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

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun EditText.showKeyboard(
    ) {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

}