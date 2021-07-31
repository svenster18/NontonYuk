package com.mohamadrizki.nontonyuk.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohamadrizki.nontonyuk.core.ui.MovieAdapter
import com.mohamadrizki.nontonyuk.databinding.FragmentMovieBinding
import com.mohamadrizki.nontonyuk.detail.DetailMovieActivity
import org.koin.android.ext.android.inject

class MovieFavoriteFragment : Fragment() {

    private val viewModel: MovieFavoriteViewModel by inject()

    private var _fragmentMovieBinding: FragmentMovieBinding? = null
    private val binding get() = _fragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val movieAdapter = MovieAdapter()
            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, selectedData.id)
                startActivity(intent)
            }

            binding?.progressBar?.visibility = View.VISIBLE
            viewModel.favoriteMovie.observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    binding?.progressBar?.visibility = View.GONE
                    movieAdapter.setData(movies)
                }
            })

            with(binding?.rvMovies) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = movieAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentMovieBinding = null
    }
}