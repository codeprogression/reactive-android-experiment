package com.codeprogression.rad.movies

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.codeprogression.rad.R
import com.codeprogression.rad.databinding.MovieDetailActivityBinding
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.movie_detail_activity.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var disposables: CompositeDisposable
    private lateinit var binding: MovieDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.movie_detail_activity)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()

    }

}
