package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun get(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun isLiked(id: Long): Boolean
    fun getLikeCount(id:Long): Int
    fun getShareCount(id:Long): Int
    fun increaseShareCount(id:Long)

}