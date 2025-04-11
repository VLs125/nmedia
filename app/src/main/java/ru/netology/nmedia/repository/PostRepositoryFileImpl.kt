package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post
import java.text.SimpleDateFormat
import java.util.Date

class PostRepositoryFileImpl(private val context: Context) : PostRepository {

    companion object {
        private val gson = Gson()
        private val typeToken = TypeToken.getParameterized(List::class.java, Post::class.java).type
        private const val FILENAME = "posts.json"
    }


    private var id = 0L
    private var posts = List(10) {
        Post(
            id = ++id,
            likes = 10100,
            shares = 100,
            author = " $id Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! " +
                    "Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. " +
                    "Затем появились курсы по дизайну, разработке, аналитике и управлению. " +
                    "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. " +
                    "Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила," +
                    " которая заставляет хотеть больше, целиться выше, бежать быстрее. " +
                    "Наша миссия — помочь встать на путь роста и начать цепочку перемен → " +
                    "http://netolo.gy/fyb",
            publshed = "21 мая в 18:36",
            likedByMe = false
        )
    }.reversed()

        set(value) {
            field = value
            sync()
        }


    private fun sync() {
        context.openFileOutput(FILENAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }

    }

    private val data = MutableLiveData(posts)

    override fun get(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likes = if (post.likedByMe) {
                        post.likes - 1
                    } else {
                        post.likes + 1
                    },
                    likedByMe = !post.likedByMe,
                )

            } else {
                post
            }
        }
        data.value = posts

    }

    override fun isLiked(id: Long): Boolean {
        try {
            val v = posts.first() { it.id == id }
            return v.likedByMe
        } catch (e: Exception) {
            return false
        }
    }

    override fun getLikeCount(id: Long): Int {
        val el = findElementById(id)
        if (el != null) {
            return el.likes
        }
        return 0
    }

    override fun getShareCount(id: Long): Int {
        val el = findElementById(id)
        if (el != null) {
            return el.shares
        }
        return 0
    }

    override fun increaseShareCount(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    shares = post.shares + 1
                )
            } else {
                post
            }
        }

        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts

    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            posts =
                arrayListOf(post.copy(++id, publshed = currentDate, author = "Me")) + posts
        } else {
            posts = posts.map {
                if (it.id != post.id) {
                    it
                } else {
                    it.copy(
                        content = post.content
                    )
                }
            }
        }
        data.value = posts
    }

    private fun findElementById(id: Long): Post? {
        return try {
            data.value?.first() { it.id == id }
        } catch (ex: Exception) {
            null
        }

    }

    init {
        val file = context.filesDir.resolve(FILENAME)
        if (file.exists()) {
            context.openFileInput(FILENAME).bufferedReader().use { it ->
                posts = gson.fromJson(it, typeToken)
                id = (posts.maxOfOrNull { it.id } ?: 0) + 1
                data.value = posts
            }
        }
    }

}