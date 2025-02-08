package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl

class PostViewModel : ViewModel() {
    private val repo: PostRepository = PostRepositoryImpl()
    val data = repo.get()
    fun like() = repo.like()
    fun isLiked() = repo.isLiked()
}