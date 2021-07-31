package com.mohamadrizki.nontonyuk.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mohamadrizki.nontonyuk.R
import com.mohamadrizki.nontonyuk.core.domain.model.TvShow
import com.mohamadrizki.nontonyuk.databinding.ActivityDetailMovieBinding
import com.mohamadrizki.nontonyuk.databinding.ContentDetailMovieBinding
import org.koin.android.ext.android.inject

class DetailTvShowActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TV_SHOW = "extra_tv_show"
    }

    private lateinit var activityDetailMovieBinding: ActivityDetailMovieBinding
    private lateinit var detailContentBinding: ContentDetailMovieBinding

    private val viewModel: DetailTvShowViewModel by inject()
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailMovieBinding = ActivityDetailMovieBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailMovieBinding.detailContent

        setContentView(activityDetailMovieBinding.root)

        setSupportActionBar(activityDetailMovieBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            val id = extras.getInt(EXTRA_TV_SHOW)
            if (id != -1) {
                activityDetailMovieBinding.progressBar.visibility = View.VISIBLE
                activityDetailMovieBinding.content.visibility = View.INVISIBLE

                viewModel.setSelectedTvShow(id)

                viewModel.tvShow.observe(this, { tv ->
                    activityDetailMovieBinding.progressBar.visibility = View.GONE
                    activityDetailMovieBinding.content.visibility = View.VISIBLE
                    populateTv(tv)
                })
            }
        }
    }

    private fun populateTv(tvShow: TvShow) {
        detailContentBinding.textTitle.text = tvShow.title
        detailContentBinding.textYear.text = tvShow.year.toString()
        detailContentBinding.textDate.text = tvShow.date
        detailContentBinding.textUserScore.text = "User Score ${tvShow.userScore}%"
        detailContentBinding.textDescription.text = tvShow.description

        Glide.with(this)
            .load(tvShow.image)
            .apply(RequestOptions().override(250,375))
            .into(detailContentBinding.imagePoster)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        activityDetailMovieBinding.progressBar.visibility = View.VISIBLE
        viewModel.tvShow.observe(this, { tvShow ->
            if (tvShow != null) {
                activityDetailMovieBinding.progressBar.visibility = View.GONE
                val state = tvShow.favorite
                setFavoriteState(state)
            }
        })
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            viewModel.setFavorite()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorited_white)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white)
        }
    }
}