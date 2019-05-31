package com.exercise.musicshuffle.domain.artist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class GetArtistListUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var musicRepositoryMock: MusicRepository
    private lateinit var getArtistListUseCase: GetArtistListUseCase

    @Before
    fun setUp() {
        musicRepositoryMock = Mockito.mock(MusicRepository::class.java)
        getArtistListUseCase = GetArtistListUseCase(musicRepository = musicRepositoryMock)

    }

    @Test
    fun executeGetArtistListUseCaseTest() {
        val musicMock = Music(
            id = 1,
            artistId = 1,
            artistName = "artistNameMock",
            primaryGenre = "genreMock",
            trackName = "trackNameMock"
        )
        val responseFromRepository = listOf(musicMock)
        val responseFromUseCase = GetArtistListUseCase.Result.Success(listOf(musicMock)) as GetArtistListUseCase.Result
        Mockito.`when`(musicRepositoryMock.getArtist(Mockito.anyString()))
            .thenReturn(Single.just(responseFromRepository))
        val responseFromExecute: Observable<GetArtistListUseCase.Result> = getArtistListUseCase.execute("any_string")
        responseFromExecute.subscribe({
            assertEquals(responseFromUseCase, responseFromExecute)
        }, {})
    }

    @Test
    fun executeGetArtistListUseCaseTestError() {
        val responseFromUseCase = GetArtistListUseCase.Result.Failure(Throwable()) as GetArtistListUseCase.Result
        Mockito.`when`(musicRepositoryMock.getArtist(Mockito.anyString()))
            .thenReturn(Single.error(Throwable()))
        val responseFromExecute: Observable<GetArtistListUseCase.Result> = getArtistListUseCase.execute("any_string")
        responseFromExecute.subscribe({
            assertEquals(responseFromUseCase, responseFromExecute)
        }, {})
    }
}