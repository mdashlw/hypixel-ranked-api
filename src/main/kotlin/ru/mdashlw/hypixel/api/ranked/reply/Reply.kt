package ru.mdashlw.hypixel.api.ranked.reply

abstract class Reply<T>(
    val success: Boolean,
    val cause: String?,
    val element: T?
)
