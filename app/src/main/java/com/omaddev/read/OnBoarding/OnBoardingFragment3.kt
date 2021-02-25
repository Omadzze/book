package com.omaddev.read.OnBoarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.omaddev.read.MainActivity
import com.omaddev.read.PdfActivity
import com.omaddev.read.R

class OnBoardingFragment3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root: ViewGroup = inflater.inflate(R.layout.fragment_onboarding3, container, false) as ViewGroup
        var startButton: Button = root.findViewById(R.id.startButton)
        startButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val dataIntent = Intent(view.context, MainActivity::class.java)
                view.context.startActivity(dataIntent)
            }

        })

//        readButton.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(view: View) {
//                if (applicationFile.canRead()) {
//                    val dataIntent = Intent(view.context, PdfActivity::class.java)
//                    dataIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    dataIntent.putExtra("file", file)
//                    view.context.startActivity(dataIntent)
//                } else {
//                    Toast.makeText(applicationContext, R.string.download_book_then_open, Toast.LENGTH_SHORT).show()
//                }
//            }
//        })
        return root
    }
}