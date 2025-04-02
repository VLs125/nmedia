package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val likes: Int,
    val shares: Int,
    val author: String,
    val publshed: String,
    val content: String,
    val likedByMe: Boolean = false,
    val video: String? = "https://rutube.ru/video/4f93804f1d55a33e08906efed86cd8cc/?r=wd"
)