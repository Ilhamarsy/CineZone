package com.dicoding.cinezone.ui.home

import android.R.layout.select_dialog_item
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.cinezone.R
import com.dicoding.cinezone.core.data.Resource
import com.dicoding.cinezone.core.domain.model.Movie
import com.dicoding.cinezone.core.ui.MovieAdapter
import com.dicoding.cinezone.databinding.FragmentHomeBinding
import com.dicoding.cinezone.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View not available")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val viewModel by viewModels<HomeViewModel>()

    private var listData = ArrayList<Movie>()
    private var listFilter = ArrayList<Movie>()

    private var isEmpty = true

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.getTheme.observe(
            viewLifecycleOwner
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.edPlace.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    isEmpty = true
                } else {
                    isEmpty = false
                    lifecycleScope.launch {
                        viewModel.searchQuery.value = p0.toString()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        viewModel.movie.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Error -> {
                        showLoading(false)
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            result.message ?: getString(R.string.something_wrong)
                    }
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        listData.clear()
                        result.data?.forEach {
                            listData.add(it)
                        }
                        setData(isEmpty)
                    }
                }
            }
        }

        viewModel.searchResult.observe(viewLifecycleOwner) { moviesItem ->
            if (moviesItem.isEmpty()) {
                Toast.makeText(requireContext(), R.string.error_search, Toast.LENGTH_SHORT).show()
            } else {
                listFilter.clear()

                moviesItem.forEach {
                    listFilter.add(it)
                }

                val placesName = moviesItem.map { it.title }
                val adapter = ArrayAdapter(requireContext(), select_dialog_item, placesName)
                binding.edPlace.setAdapter(adapter)
                setData(isEmpty)
                adapter.notifyDataSetChanged()
            }
        }

        return binding.root
    }

    private fun setData(empty: Boolean) {
        val movieAdapter = MovieAdapter()
        movieAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("data", selectedData)
            startActivity(intent)
        }

        if (empty) movieAdapter.differ.submitList(listData) else movieAdapter.differ.submitList(listFilter)

        with(binding.rvMovie) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}