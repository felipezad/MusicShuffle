package com.exercise.musicshuffle.application

import android.util.Log
import androidx.lifecycle.*
import com.exercise.musicshuffle.domain.artist.GetArtistListUseCase
import com.exercise.musicshuffle.domain.artist.Music
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.random.Random

class MainViewModel(
    private val getArtistListUseCase: GetArtistListUseCase,
    private val subscribeOn: Scheduler = Schedulers.io(),
    private val observeOn: Scheduler = AndroidSchedulers.mainThread(),
    private var isShuffleReady: Boolean = false,
    val artistList: MutableLiveData<List<Music>> = MutableLiveData()
) : ViewModel() {


    private val disposables = CompositeDisposable()

    private fun handleGetArtistListSuccess(result: GetArtistListUseCase.Result) {
        when (result) {
            is GetArtistListUseCase.Result.Success -> {
                artistList.postValue(result.musicList)
                isShuffleReady = true
            }
            is GetArtistListUseCase.Result.Failure -> {
                Log.e("Error", result.failure.toString())
            }
        }
    }

    fun getArtistList(artistId: String) {
        disposables.add(
            getArtistListUseCase
                .execute(artistId = artistId)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .subscribe(this::handleGetArtistListSuccess)
        )
    }

    fun shuffleArtistList() {
        if (isShuffleReady) {
            isShuffleReady = false
            val shuffledList = artistList.value?.toMutableList()?.shuffleMusicList()
            artistList.value = shuffledList
            isShuffleReady = true
        }
    }

    fun destroy() {
        disposables.clear()
    }


    class MainViewModelFactory(
        private val getArtistListUseCase: GetArtistListUseCase = GetArtistListUseCase(),
        private val subscribeOn: Scheduler = Schedulers.io(),
        private val observeOn: Scheduler = AndroidSchedulers.mainThread()
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(
                getArtistListUseCase = getArtistListUseCase,
                subscribeOn = subscribeOn,
                observeOn = observeOn
            ) as T
        }

    }

    private fun MutableList<Music>.shuffleMusicList(): List<Music> {
        val shuffleList: MutableList<Music> = mutableListOf()
        var index = Random(System.currentTimeMillis()).nextInt(this.size)
        var getRandomMusic: Music
        shuffleList.add(this.removeAt(index))
        while (this.size > 0) {
            index = Random(System.currentTimeMillis()).nextInt(this.size)
            getRandomMusic = this[index]
            if (!shuffleList.last().artistName.equals(other = getRandomMusic.artistName, ignoreCase = true)) {
                shuffleList.add(this.removeAt(index))
            }
        }
        return shuffleList
    }

}