package com.exercise.musicshuffle.application

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exercise.musicshuffle.domain.artist.GetArtistListShuffledUseCase
import com.exercise.musicshuffle.domain.artist.GetArtistListUseCase
import com.exercise.musicshuffle.domain.artist.Music
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MainViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var getArtistListUseCaseMock: GetArtistListUseCase
    private lateinit var mainViewModel: MainViewModel
    private lateinit var subscribeOn: Scheduler
    private lateinit var observeOn: Scheduler

    @Before
    fun setUp() {
        subscribeOn = Schedulers.trampoline()
        observeOn = Schedulers.trampoline()
        getArtistListUseCaseMock = Mockito.mock(GetArtistListUseCase::class.java)
        mainViewModel = MainViewModel(
            getArtistListUseCase = getArtistListUseCaseMock,
            getArtistListShuffledUseCase = GetArtistListShuffledUseCase(),
            subscribeOn = subscribeOn,
            observeOn = observeOn
        )

    }

    @Test
    fun getArtistList() {
        val musicMock = Music(
            id = 1,
            artistId = 1,
            artistName = "artistNameMock",
            primaryGenreName = "genreMock",
            trackName = "trackNameMock"
        )
        val responseFromUseCase = GetArtistListUseCase.Result.Success(listOf(musicMock)) as GetArtistListUseCase.Result

        Mockito.`when`(getArtistListUseCaseMock.execute(Mockito.anyString()))
            .thenReturn(Observable.just(responseFromUseCase))
        mainViewModel.getArtistList(Mockito.anyString())

        assertEquals(mainViewModel.artistList.value, listOf(musicMock))

    }
}