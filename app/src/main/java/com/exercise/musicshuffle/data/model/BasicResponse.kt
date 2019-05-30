package com.exercise.musicshuffle.data.model

data class BasicResponse<T>(val resultCount: Int, val results: List<T>)