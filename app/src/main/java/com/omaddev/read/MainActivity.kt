package com.omaddev.read

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.omaddev.read.model.Books
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mDatabase: DatabaseReference

    var booksAdapter: BooksAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.book_recycler)

        mDatabase = FirebaseDatabase.getInstance().getReference("CategoryBook")
        setupRecyclerView()

    }
    fun setupRecyclerView() {
        val query: Query = mDatabase
        val firebaseRecyclerOptions: FirebaseRecyclerOptions<Books> =
                FirebaseRecyclerOptions.Builder<Books>()
                        .setQuery(query, Books::class.java)
                        .build()

        booksAdapter = BooksAdapter(firebaseRecyclerOptions)

        mRecyclerView.layoutManager = GridLayoutManager(this, 2)
        mRecyclerView.adapter = booksAdapter

    }
    override fun onStart() {
        super.onStart()
        booksAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        booksAdapter!!.stopListening()
    }
}