package com.kotlin.toapp_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        //always requires a first value
        val image= intent.getIntExtra("image", R.drawable.spirited)
        val name = intent.getStringExtra("name")

        val icon =findViewById<ImageView>(R.id.imageIcon)
    }
}