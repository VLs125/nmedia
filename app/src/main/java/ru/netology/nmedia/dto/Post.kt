package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val likes: Int,
    val shares: Int,
    val author: String,
    val publshed: String,
    val content: String,
    val likedByMe: Boolean = false
)