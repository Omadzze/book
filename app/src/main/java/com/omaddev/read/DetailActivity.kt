package com.omaddev.read

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Runnable
import java.io.File
import java.util.*


class DetailActivity : AppCompatActivity() {

    lateinit var ref: StorageReference
    lateinit var storageReference: StorageReference
    lateinit var file: String
    lateinit var fileName: String
    lateinit var download: String
    lateinit var layout: ConstraintLayout
    lateinit var textView: TextView
    lateinit var progressBar: ProgressBar
    lateinit var downloadView: CardView
    lateinit var applicationFile: File
    lateinit var fade_in: Animation

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
        var backButton: ImageView = findViewById(R.id.backButtonDetail)
        var readButton: Button = findViewById(R.id.readButton)
        var starImage: ImageView = findViewById(R.id.starsDetail)
        var gradeDetail: TextView = findViewById(R.id.gradeDetail)
        layout = findViewById(R.id.constraintDetail)
        textView = findViewById(R.id.downloadText)
        progressBar = findViewById(R.id.downloadProgress)
        downloadView = findViewById(R.id.downloadView)

        fade_in = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)

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
            val rating = bundle["rating"] as Double

            Picasso.get().load(thumbnail).into(thumbnailImage)

            authorText.text = author
            titleText.text = title
            descriptionText.text = description

            if (rating >= 5.0) {
                starImage.setImageResource(R.drawable.ic_five_star_detail)
                gradeDetail.text = "5.0"
            } else if (rating >= 4.0) {
                starImage.setImageResource(R.drawable.ic_four_star_detail)
                gradeDetail.text = "4.0"
            }

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
                    Toast.makeText(applicationContext, R.string.download_book_then_open, Toast.LENGTH_SHORT).show()
                }
            }
        })

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        if (applicationFile.canRead()) {
            downloadView.visibility = View.GONE
        }

        layout.setOnClickListener {
            if (applicationFile.canRead()) {
                Toast.makeText(applicationContext, R.string.already_download_book, Toast.LENGTH_SHORT).show()
            } else {
                ref.child(file).downloadUrl.addOnSuccessListener {
                    downloadFile(this, fileName, ".pdf", Environment.DIRECTORY_DOWNLOADS, download)
                }.addOnFailureListener {
                    Toast.makeText(this, R.string.error_occurred, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun downloadFile(context: Context, fileName: String, fileExtension: String, destinationDirectory: String?, url: String?) {
        val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension)
        val downloadId: Long = downloadManager.enqueue(request)

        Thread {
            var downloading = true
            while (downloading) {
                val q = DownloadManager.Query()
                q.setFilterById(downloadId)
                val cursor: Cursor = downloadManager.query(q)
                cursor.moveToFirst()
                val bytes_downloaded: Int = cursor.getInt(cursor
                        .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                val bytes_total: Int = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) === DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                runOnUiThread {
                    if (downloading) {
                        progressBar.visibility = View.VISIBLE
                        textView.text = resources.getString(R.string.please_wait)
                        textView.animation = fade_in
                        progressBar.animation = fade_in
                    } else {
                        progressBar.visibility = View.GONE
                        textView.text = resources.getString(R.string.done)
                        layout.setBackgroundColor(getColor(R.color.star))
                    }
                }
                cursor.close()
            }
        }.start()
    }

}



