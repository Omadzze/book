package com.omaddev.read

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

class PdfActivity : AppCompatActivity() {

    lateinit var pdfView: PDFView
    lateinit var fileName: String
    lateinit var fileSystem: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        pdfView = findViewById(R.id.pdfView)

        val intentReceive: Intent = intent
        val bundle = intentReceive.extras

        if (bundle != null) {
            fileName = bundle["file"] as String
        }

        fileSystem = File("/storage/emulated/0/Android/data/com.omaddev.read/files/Download/$fileName")
        pdfView.fromFile(fileSystem).load()
//        if (fileSystem == null) {
//            pdfView.fromFile(fileSystem).load()
//        } else {
//            Toast.makeText(this, "Please download book then open it", Toast.LENGTH_SHORT).show()
//        }

        }
}