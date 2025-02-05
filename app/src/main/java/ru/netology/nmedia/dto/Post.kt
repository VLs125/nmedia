package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val publshed: String,
    val content: String,
    var likedByMe: Boolean = false
)