package com.exercise.musicshuffle.domain.artist

data class Music(
    val id: Long = 0,
    val artistName: String = "",
    val artistId: Long = 0,
    val primaryGenreName: String = "",
    val trackName: String = "",
    val artworkUrl: String = ""
)