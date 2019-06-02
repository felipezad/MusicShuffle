package com.exercise.musicshuffle.domain.artist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class GetArtistListShuffledUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var getArtistListShuffledUseCase: GetArtistListShuffledUseCase

    @Before
    fun setUp() {
        getArtistListShuffledUseCase = GetArtistListShuffledUseCase()
    }

    @Test
    fun `shuffle music list`() {
        val original = mutableListOf(
            Music(
                id = 1,
                artistId = 1,
                artistName = "John",
                primaryGenreName = "genreMock",
                trackName = "trackNameMock"
            ), Music(
                id = 2,
                artistId = 2,
                artistName = "John",
                primaryGenreName = "genreMock",
                trackName = "trackNameMock"
            ), Music(
                id = 3,
                artistId = 1,
                artistName = "Mary",
                primaryGenreName = "genreMock",
                trackName = "trackNameMock"
            ), Music(
                id = 4,
                artistId = 2,
                artistName = "Mary",
                primaryGenreName = "genreMock",
                trackName = "trackNameMock"
            )
        )
        val notShuffledMusicList = GetArtistListShuffledUseCase.Result.Success(original).musicList
        getArtistListShuffledUseCase
            .execute(original)
            .subscribe({
                val success = it as GetArtistListShuffledUseCase.Result.Success
                assertNotEquals(success.musicList, notShuffledMusicList)
                assertNotEquals(success.musicList[1], notShuffledMusicList[1])
            }, {})
    }
}