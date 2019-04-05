package com.example.josh.assign6


class URLMaker(baseUrl: String) {

    private val builder = StringBuilder()

    init {
        builder.append(baseUrl)
    }

    fun add(sep: String, key: String, value: String) {
        builder.append(sep)
        builder.append(key)
        builder.append(value)
    }

    fun make(): String {
        return builder.toString()
    }

}