package com.eurosp0rt.data.utils

fun <T> List<T>.mixWith(other: List<T>): List<T> {
    val list = this.zip(other)
        .flatMap {
            listOf(it.first, it.second)
        } + if (this.size > other.size) this.drop(other.size) else other.drop(this.size)
    return list
}