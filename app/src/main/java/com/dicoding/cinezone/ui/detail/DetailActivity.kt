package com.dicoding.cinezone.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.cinezone.R
import com.dicoding.cinezone.core.domain.model.Movie
import com.dicoding.cinezone.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailMovie = intent.getParcelableExtra<Movie>("data")

        detailMovie?.let { data ->
            val vote = data.voteAverage * 10
            val voteResult = vote.toInt()

            Glide.with(this@DetailActivity)
                .load(data.backdropPath)
                .centerCrop()
                .into(binding.ivBackdrop)

            Glide.with(this@DetailActivity)
                .load(data.posterPath)
                .centerCrop()
                .into(binding.ivPoster)

            binding.apply {
                progressBar.progress = voteResult
                tvVote.text = voteResult.toString()
                tvTitle.text = data.title
                tvOverview.text = data.overview
            }

            var statusFavorite = data.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fab.setOnClickListener {
                if (statusFavorite) {
                    Toast.makeText(this, R.string.remove_favorite, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, R.string.add_favorite, Toast.LENGTH_SHORT)
                        .show()
                }
                statusFavorite = !statusFavorite
                viewModel.setFavoriteMovie(data, statusFavorite)
                setStatusFavorite(statusFavorite)
            }

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(data.releaseDate)
            if (date != null) {
                val formattedDate =
                    SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
                binding.tvDate.text = formattedDate
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_white
                )
            )
        } else {
            binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_not_favorite_white
                )
            )
        }
    }
}