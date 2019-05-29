package com.exercise.musicshuffle.domain.music

import com.exercise.musicshuffle.data.remote.MusicApi
import com.exercise.musicshuffle.domain.Repository

class MusicRepository(
    private val musicApi: MusicApi,
    private val musicMapper: MusicMapper
) : Repository {

    fun getMusic(id: String): List<Music> {
        return musicMapper.to(musicApi.getMusic(id))
    }
}