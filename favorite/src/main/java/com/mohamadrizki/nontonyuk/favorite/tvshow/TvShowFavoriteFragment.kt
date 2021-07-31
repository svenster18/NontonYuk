package com.mohamadrizki.nontonyuk.favorite.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohamadrizki.nontonyuk.core.ui.TvShowAdapter
import com.mohamadrizki.nontonyuk.databinding.FragmentTvShowBinding
import com.mohamadrizki.nontonyuk.detail.DetailTvShowActivity
import org.koin.android.ext.android.inject

class TvShowFavoriteFragment : Fragment() {

    private val viewModel: TvShowFavoriteViewModel by inject()

    private var _fragmentTvShowBinding: FragmentTvShowBinding? = null
    private val binding get() = _fragmentTvShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentTvShowBinding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val tvShowAdapter = TvShowAdapter()
            tvShowAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailTvShowActivity::class.java)
                intent.putExtra(DetailTvShowActivity.EXTRA_TV_SHOW, selectedData.id)
                startActivity(intent)
            }
            binding?.progressBar?.visibility = View.VISIBLE
            viewModel.favoriteTvShow.observe(viewLifecycleOwner, { tvShows ->
                if (tvShows != null) {
                    binding?.progressBar?.visibility = View.GONE
                    tvShowAdapter.setData(tvShows)
                }
            })

            with(binding?.rvTvShow) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = tvShowAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentTvShowBinding = null
    }
}