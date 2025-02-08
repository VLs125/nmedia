package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.ShareRepository
import ru.netology.nmedia.repository.ShareRepositoryImpl

class ShareViewModel : ViewModel() {
    private val repo: ShareRepository = ShareRepositoryImpl()
    val shareCount = repo.getShareCount()
    fun increaseShare() = repo.increaseShareCount()
    val data = repo.get()

}