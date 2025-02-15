package ru.netology.nmedia.service

class WordEndingService {
    companion object {
        fun getCountWord(count: Int): String {
            return when {
                count in 1000..10000 -> (count / 1000).toString() + "." + (count % 1000) / 100 + "K"
                count in 10000..1_000_000 -> count.toString() + "K"
                count > 999_999 -> (count / 1_000_000).toString() + "." + ((count % 1_000_000) / 100_000) + "M"
                else -> count.toString()
            }
        }
    }
}