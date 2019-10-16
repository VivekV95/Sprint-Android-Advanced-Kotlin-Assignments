package com.example.glideextension

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var imageLoaded: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image_view.loadurl("https://www.vets4pets.com/siteassets/species/dog/puppy/husky-puppy-on-dog-walk.jpg?width=1040")
    }

    private fun ImageView.loadurl(url: String) {
        Glide.with(this)
            .load(url)
            .onSuccessOrFailure(
                onSuccess = {
                    imageLoaded = true
                    Toast.makeText(this@MainActivity, "It worked", Toast.LENGTH_LONG).show()
                }, onFailure = {
                    imageLoaded = false
                    Toast.makeText(this@MainActivity, "It didn't work", Toast.LENGTH_LONG).show()
                })
            .into(image_view)
    }

    private fun RequestBuilder<Drawable>.onSuccessOrFailure(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ): RequestBuilder<Drawable> {

        return this.addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onFailure.invoke()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onSuccess.invoke()
                return false
            }
        })
    }
}
