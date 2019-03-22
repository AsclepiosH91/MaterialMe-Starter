package com.example.android.materialme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //initialize the ImageView and title TextView
        val sportsTitle : TextView = findViewById<View>(R.id.titleDetail) as TextView
        val sportsImage : ImageView = findViewById<View>(R.id.sportsImageDetail) as ImageView

        //Get the title from the incoming Intent and set it to the TextView
        sportsTitle.text = intent.getStringExtra("title")

        //Use Glide to load the image into the ImageView
        Glide.with(this).load(intent.getIntExtra("image_resource",0)).into(sportsImage)

    }
}
