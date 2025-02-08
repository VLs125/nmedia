package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LikeRepositoryImpl : LikeRepository {
    private var likeCount: Int = 6
    private val data = MutableLiveData(likeCount)
    override fun get(): LiveData<Int> {
        return data
    }

    override fun getLikeCount(): Int {
        return data.value!!.toInt()
    }

    override fun increaseLikeCount() {
        if (data.value == null) {
            data.value = 1
        } else if (data.value!! >= 0) {
            data.value = data.value!! + 1
        }
    }

    override fun decreaseLikeCount() {
        if (data.value == null) {
            return
        } else if (data.value!! <= 0) {
            return
        } else {
            data.value = data.value!! - 1
        }
    }
}