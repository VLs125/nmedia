package ru.netology.nmedia.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.postText.requestFocus()
        if (intent.hasExtra("content")) {
            binding.postText.setText(intent.getStringExtra("content"))
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                actionActivityResolver(binding)
                finish()
            }
        })

        binding.ok.setOnClickListener {
            actionActivityResolver(binding)
            finish()
        }

    }

    private fun actionActivityResolver(bind: ActivityNewPostBinding) {
        val intent = Intent()
        if (bind.postText.text.isNullOrBlank()) {
            setResult(Activity.RESULT_CANCELED, intent)
        } else {
            val content = bind.postText.text.toString()
            intent.putExtra(Intent.EXTRA_TEXT, content)
            setResult(Activity.RESULT_OK, intent)
        }
    }
}


object NewPostContract : ActivityResultContract<String?, String?>() {
    override fun createIntent(context: Context, input: String?) =
        Intent(context, NewPostActivity::class.java)
            .putExtra("content", input)

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return intent?.getStringExtra(Intent.EXTRA_TEXT)
    }
}