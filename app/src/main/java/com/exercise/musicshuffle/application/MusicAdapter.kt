package com.exercise.musicshuffle.application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.exercise.musicshuffle.R
import com.exercise.musicshuffle.domain.artist.Music
import kotlinx.android.synthetic.main.music_list_item.view.*

class MusicAdapter(private val musics: List<Music>, private val requestManager: RequestManager) :
    RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_list_item, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(musics[position], requestManager)
    }

    override fun getItemCount(): Int = musics.size

    class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewMusicName: TextView = view.textViewMusicName
        var textViewMusicArtist: TextView = view.textViewMusicArtist
        var imageViewMusicArtWork: ImageView = view.imageViewMusicArtWork

        fun bind(music: Music, requestManager: RequestManager) {
            textViewMusicArtist.text = music.artistName
            textViewMusicName.text = music.trackName
            requestManager
                .load(music.artworkUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .apply(RequestOptions.circleCropTransform())
                .into(imageViewMusicArtWork)

        }
    }
}