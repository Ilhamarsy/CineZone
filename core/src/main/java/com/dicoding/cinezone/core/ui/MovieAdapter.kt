package com.dicoding.cinezone.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cinezone.core.R
import com.dicoding.cinezone.core.databinding.ItemListBinding
import com.dicoding.cinezone.core.domain.model.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    var onItemClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(data: Movie) {
            val vote = data.voteAverage * 10
            val voteResult = vote.toInt()
            with(binding) {
//                Picasso.get().load(data.posterPath).into(ivItemImage)
//                Glide.with(itemView.context)
//                    .load(data.posterPath)
//                    .into(ivItemImage)
                tvVote.text = voteResult.toString()

                progressBar.progress = voteResult
                tvItemTitle.text = data.title
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(differ.currentList[adapterPosition])
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}