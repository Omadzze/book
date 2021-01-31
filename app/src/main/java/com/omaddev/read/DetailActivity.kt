package com.omaddev.read

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class DetailActivity : AppCompatActivity() {

    lateinit var ref: StorageReference
    lateinit var storageReference: StorageReference
    lateinit var file: String
    lateinit var fileName: String
    lateinit var download: String

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detail)

        storageReference = FirebaseStorage.getInstance().getReference()
        ref = storageReference

        var thumbnailImage: ImageView = findViewById(R.id.thumbnailDetail)
        var authorText: TextView = findViewById(R.id.authorDetail)
        var titleText: TextView = findViewById(R.id.titleDetail)
        var descriptionText: TextView = findViewById(R.id.descrDetail)
        var downloadButton: Button = findViewById(R.id.downloadDetail)
        var backButton: ImageView = findViewById(R.id.backButtonDetail)

        val intentReceive: Intent = intent
        val bundle = intentReceive.extras

        if (bundle != null) {
            val description = bundle["description"] as String?
            val thumbnail = bundle["thumbnail"] as String?
            val title = bundle["title"] as String?
            download = bundle["download"] as String
            val author = bundle["author"] as String?
            file = bundle["file"] as String
            fileName = bundle["fileName"] as String

            Picasso.get().load(thumbnail).into(thumbnailImage)

            authorText.text = author
            titleText.text = title
            descriptionText.text = description

        }

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        downloadButton.setOnClickListener {
            ref.child(file).downloadUrl.addOnSuccessListener {
                downloadFile(this, fileName, ".pdf", Environment.DIRECTORY_DOWNLOADS, download)
            }.addOnFailureListener {
                Toast.makeText(this, "Error occured. Please try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun downloadFile(context: Context, fileName: String, fileExtension: String, destinationDirectory: String?, url: String?) {
        val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension)
        downloadManager.enqueue(request)
    }


}
