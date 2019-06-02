package com.exercise.musicshuffle.application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.exercise.musicshuffle.domain.artist.GetArtistListShuffledUseCase
import com.exercise.musicshuffle.domain.artist.GetArtistListUseCase
import com.exercise.musicshuffle.domain.artist.Music
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val getArtistListUseCase: GetArtistListUseCase,
    private val getArtistListShuffledUseCase: GetArtistListShuffledUseCase,
    private val subscribeOn: Scheduler = Schedulers.io(),
    private val observeOn: Scheduler = AndroidSchedulers.mainThread(),
    private var isShuffleReady: Boolean = false,
    val artistList: MutableLiveData<List<Music>> = MutableLiveData(),
    val hasFailed: MutableLiveData<Boolean> = MutableLiveData(),
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private fun handleGetArtistListSuccess(result: GetArtistListUseCase.Result) {
        when (result) {
            is GetArtistListUseCase.Result.Success -> {
                artistList.postValue(result.musicList)
                isLoading.postValue(false)
                isShuffleReady = true
            }
            is GetArtistListUseCase.Result.Failure -> {
                hasFailed.postValue(true)
                isLoading.postValue(false)
            }
            is GetArtistListUseCase.Result.Loading -> {
                isLoading.postValue(true)
            }
        }
    }

    private fun handleGetShuffledArtistListSuccess(result: GetArtistListShuffledUseCase.Result) {
        when (result) {
            is GetArtistListShuffledUseCase.Result.Success -> {
                artistList.postValue(result.musicList)
                isLoading.postValue(false)
                isShuffleReady = true
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
            disposables.add(
                getArtistListShuffledUseCase
                    .execute(artistList.value!!.toMutableList())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(observeOn)
                    .subscribe(this::handleGetShuffledArtistListSuccess)
            )
            isShuffleReady = true
        }
    }

    fun destroy() {
        disposables.clear()
    }


    class MainViewModelFactory(
        private val getArtistListUseCase: GetArtistListUseCase = GetArtistListUseCase(),
        private val getArtistListShuffledUseCase: GetArtistListShuffledUseCase = GetArtistListShuffledUseCase(),
        private val subscribeOn: Scheduler = Schedulers.io(),
        private val observeOn: Scheduler = AndroidSchedulers.mainThread()
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(
                getArtistListUseCase = getArtistListUseCase,
                getArtistListShuffledUseCase = getArtistListShuffledUseCase,
                subscribeOn = subscribeOn,
                observeOn = observeOn
            ) as T
        }

    }

}