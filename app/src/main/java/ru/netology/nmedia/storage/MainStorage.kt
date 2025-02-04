package ru.netology.nmedia.storage

class  MainStorage {
    private val storage: HashMap<String, Int> = HashMap()

    fun increaseLikeCount() {
        val likeCount = storage[LIKE]
        if (likeCount == null) {
            storage[LIKE] = 1
        } else if (likeCount >= 1) {
            storage[LIKE] = likeCount + 1
        }
    }

    fun getLikeCount(): Int {
        return if (storage[LIKE] == null) 0 else storage[LIKE]!!
    }

    fun decreaseLikeCount() {
        val likeCount = storage[LIKE]
        if (likeCount == null) {
            return
        } else if (likeCount <= 0) {
            return
        } else storage[LIKE] = likeCount - 1
    }

    fun increaseShareCount() {
        val likeCount = storage[SHARE]
        if (likeCount == null) {
            storage[SHARE] = 1
        } else if (likeCount >= 1) {
            storage[SHARE] = likeCount + 1
        }
    }

    fun getShareCount(): Int {
        return if (storage[SHARE] == null) 0 else storage[SHARE]!!
    }

    fun decreaseShareCount() {
        val likeCount = storage[SHARE]
        if (likeCount == null) {
            return
        } else if (likeCount <= 0) {
            return
        } else storage[SHARE] = likeCount - 1
    }

    companion object {
        const val SHARE = "SHARE"
        const val LIKE = "LIKE"
    }
}