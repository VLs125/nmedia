package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.LikeRepository
import ru.netology.nmedia.repository.LikeRepositoryImpl

class LikeViewModel : ViewModel() {
    private val repo: LikeRepository = LikeRepositoryImpl()
    val likeCount = repo.getLikeCount()
    fun increaseLike() = repo.increaseLikeCount()
    fun decreaseLike() = repo.decreaseLikeCount()
    val data = repo.get()

}