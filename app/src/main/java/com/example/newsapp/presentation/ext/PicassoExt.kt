package com.example.newsapp.presentation.ext

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

fun Picasso.picassoLoading(imageString: String?, imageView: ImageView, progressBar: ProgressBar) {
    load(imageString)
        .into(imageView, object : Callback {
            override fun onSuccess() {
                progressBar.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
                progressBar.visibility = View.GONE
            }
        })
}
