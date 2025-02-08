package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData

interface ShareRepository {
    fun get(): LiveData<Int>
    fun getShareCount(): Int
    fun increaseShareCount()
    fun decreaseShareCount()
}