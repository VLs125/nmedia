package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl

var emptyPost = Post(
    id = 0L,
    likes = 0,
    shares = 0,
    author = "",
    publshed = "",
    content = "",
    likedByMe = false
)

class PostViewModel : ViewModel() {
    private val repo: PostRepository = PostRepositoryImpl()
    val edit = MutableLiveData(emptyPost)
    val data = repo.get()
    fun like(id: Long) = repo.likeById(id)
    fun isLiked(id: Long) = repo.isLiked(id)
    fun shareCount(id: Long) = repo.getShareCount(id)
    fun increaseShare(id: Long) = repo.increaseShareCount(id)
    fun likeCount(id: Long) = repo.getLikeCount(id)
    fun removePost(id: Long) = repo.removeById(id)
    fun savePost(content: String) =
        run {
            edit.value?.let { editPost -> repo.save(editPost.copy(content = content)) }
            edit.value = emptyPost
        }

    fun onEdit(post: Post) {
        edit.value = post
    }
}