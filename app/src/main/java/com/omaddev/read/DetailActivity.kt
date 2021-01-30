package com.omaddev.read

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detail)

        var thumbnailImage: ImageView = findViewById(R.id.thumbnailDetail)
        var authorText: TextView = findViewById(R.id.authorDetail)
        var titleText: TextView = findViewById(R.id.titleDetail)
        var descriptionText: TextView = findViewById(R.id.descrDetail)
        var downloadButton: Button = findViewById(R.id.downloadDetail)
        var backButton: ImageView = findViewById(R.id.backButtonDetail)

        val intentReceive: Intent = intent
        val bundle = intentReceive.extras

        if (bundle != null)  {
            val description = bundle["description"] as String?
            val thumbnail = bundle["thumbnail"] as String?
            val title = bundle["title"] as String?
            val download = bundle["download"] as String?
            val author = bundle["author"] as String?

            Picasso.get().load(thumbnail).into(thumbnailImage)

            authorText.text = author
            titleText.text = title
            descriptionText.text = description

        }

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}