package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ShareRepositoryImpl : ShareRepository {
    private val shareCount = 3
    private val data = MutableLiveData(shareCount)
    override fun get(): LiveData<Int> {
        return data
    }


    override fun getShareCount(): Int {
        return if (data.value == null) 0 else data.value!!
    }

    override fun increaseShareCount() {
        val shareCount = data.value
        if (shareCount == null) {
            data.value = 1
        } else if (shareCount >= 0) {
            data.value = shareCount + 1
        }
    }

    override fun decreaseShareCount() {
        TODO("Not yet implemented")
    }
}
