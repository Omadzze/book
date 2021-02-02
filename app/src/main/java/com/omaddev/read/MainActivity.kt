package com.omaddev.read

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.chip.Chip
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.omaddev.read.model.Books


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mSearchBar: EditText
    lateinit var mDatabase: DatabaseReference
    lateinit var chipAll: Chip
    lateinit var chipPoem: Chip
//    private lateinit var booksAdapter: FirebaseRecyclerAdapter<Books, BooksAdapter.BooksViewHolder>

    var booksAdapter: BooksAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.book_recycler)
        mSearchBar = findViewById(R.id.searchView)
        chipAll = findViewById(R.id.chipAll)
        chipPoem = findViewById(R.id.chipPoem)
        mDatabase = FirebaseDatabase.getInstance().getReference("CategoryBook")
        mDatabase.keepSynced(true)
        setupRecyclerView()

        chipAll.setOnClickListener(this)
        chipPoem.setOnClickListener(this)

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

//    fun setupRecyclerViewChipGroup(searchText: String) {
//        val query: Query = mDatabase.orderByChild("poem").startAt(searchText).endAt(searchText + "\uf8ff")
//        Log.i("TAG", query.toString())
//        val firebaseRecyclerOptions: FirebaseRecyclerOptions<Books> =
//                FirebaseRecyclerOptions.Builder<Books>()
//                        .setQuery(query, Books::class.java)
//                        .build()
//
//        booksAdapter = BooksAdapter(firebaseRecyclerOptions)
//
//        mRecyclerView.layoutManager = GridLayoutManager(this, 2)
//        mRecyclerView.adapter = booksAdapter
//
//    }
    override fun onStart() {
        super.onStart()
        booksAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        booksAdapter!!.stopListening()
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}