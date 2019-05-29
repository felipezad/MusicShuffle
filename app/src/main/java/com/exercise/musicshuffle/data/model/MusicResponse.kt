package com.exercise.musicshuffle.data.model

data class MusicResponse(
    val id: Long = 0,
    val artistName: String = "",
    val artistId: Long = 0,
    val primaryGenre: String = "",
    val trackName: String = ""
)