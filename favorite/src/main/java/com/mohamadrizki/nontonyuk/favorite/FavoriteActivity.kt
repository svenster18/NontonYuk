package com.mohamadrizki.nontonyuk.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mohamadrizki.nontonyuk.R
import com.mohamadrizki.nontonyuk.databinding.ActivityHomeBinding
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        loadKoinModules(favoriteModule)

        supportActionBar?.title = resources.getString(R.string.favorite)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        activityHomeBinding.viewPager.adapter = sectionsPagerAdapter
        activityHomeBinding.tabs.setupWithViewPager(activityHomeBinding.viewPager)

        supportActionBar?.elevation = 0f
    }
}