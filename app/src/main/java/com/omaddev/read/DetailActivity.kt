package com.omaddev.read

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.File


class DetailActivity : AppCompatActivity() {

    lateinit var ref: StorageReference
    lateinit var storageReference: StorageReference
    lateinit var file: String
    lateinit var fileName: String
    lateinit var download: String

    lateinit var applicationFile: File

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
        var readButton: Button = findViewById(R.id.readButton)

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

            applicationFile = File("/storage/emulated/0/Android/data/com.omaddev.read/files/Download/" + file)

        }
        readButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (applicationFile.canRead()) {
                    val dataIntent = Intent(view.context, PdfActivity::class.java)
                    dataIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    dataIntent.putExtra("file", file)
                    view.context.startActivity(dataIntent)
                } else {
                    Toast.makeText(applicationContext, "Please download book then open it", Toast.LENGTH_SHORT).show()
                }
            }
        })


        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        downloadButton.setOnClickListener {
            if (applicationFile.canRead()) {
                Toast.makeText(applicationContext, "You are already downloaded book. Read it", Toast.LENGTH_SHORT).show()
            } else {
                ref.child(file).downloadUrl.addOnSuccessListener {
                    downloadFile(this, fileName, ".pdf", Environment.DIRECTORY_DOWNLOADS, download)
                }.addOnFailureListener {
                    Toast.makeText(this, "Error occured. Please try again", Toast.LENGTH_SHORT).show()
                }
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
