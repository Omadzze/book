package com.omaddev.read

import android.app.Application
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Read: Application() {

    override fun onCreate() {
        super.onCreate()

        Firebase.database.setPersistenceEnabled(true)
    }

}