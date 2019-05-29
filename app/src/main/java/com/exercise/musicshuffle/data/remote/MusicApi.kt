package com.exercise.musicshuffle.data.remote

class MusicApi : Api {
    override val endpoint: String
        get() = "https://us-central1-tw-exercicio-mobile.cloudfunctions.net/lookup?"

}