package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData


interface LikeRepository {
    fun get(): LiveData<Int>
    fun getLikeCount(): Int
    fun increaseLikeCount()
    fun decreaseLikeCount()
}