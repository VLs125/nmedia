package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl

class PostViewModel : ViewModel() {
    private val repo: PostRepository = PostRepositoryImpl()
    val data = repo.get()
    fun like(id: Long) = repo.likeById(id)
    fun isLiked(id: Long) = repo.isLiked(id)
    fun shareCount(id: Long) = repo.getShareCount(id)
    fun increaseShare(id: Long) = repo.increaseShareCount(id)
    fun likeCount(id: Long) = repo.getLikeCount(id)
}