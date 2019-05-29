package com.exercise.musicshuffle.domain.music

data class Music(
    val id: Long = 0,
    val artistName: String = "",
    val artistId: Long = 0,
    val primaryGenre: String = "",
    val trackName: String = ""
)