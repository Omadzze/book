package com.omaddev.read

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.chip.Chip
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.ktx.Firebase
import com.omaddev.read.model.Books


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mSearchBar: EditText
    lateinit var chipAll: Chip
    lateinit var chipPoem: Chip
    lateinit var chipGazelees: Chip
    lateinit var chipHamsa: Chip
    lateinit var hamburgerMenu: ImageView

    private lateinit var booksAdapter: FirebaseRecyclerAdapter<Books, BooksAdapter.BooksViewHolder>
    private var mBaseQuery = FirebaseDatabase.getInstance().getReference("CategoryBook")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.book_recycler)
        mSearchBar = findViewById(R.id.searchView)
        chipAll = findViewById(R.id.chipAll)
        chipPoem = findViewById(R.id.chipPoem)
        chipGazelees = findViewById(R.id.chipGazelees)
        chipHamsa = findViewById(R.id.chipHamsa)
        hamburgerMenu = findViewById(R.id.hamburgerButton)

        booksAdapter = getAdapter()

        mRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = booksAdapter
        }

        chipAll.setOnClickListener(this)
        chipPoem.setOnClickListener(this)
        chipGazelees.setOnClickListener(this)
        chipHamsa.setOnClickListener(this)
        hamburgerMenu.setOnClickListener(this)
    }

    private fun getAdapter(): FirebaseRecyclerAdapter<Books, BooksAdapter.BooksViewHolder> {

        val options = FirebaseRecyclerOptions.Builder<Books>()
                .setLifecycleOwner(this)
                .setQuery(getFirstQuery(), Books::class.java)
                .build()

        return object : FirebaseRecyclerAdapter<Books, BooksAdapter.BooksViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksAdapter.BooksViewHolder {
                return BooksAdapter.BooksViewHolder(
                        layoutInflater.inflate(
                                R.layout.grid_view_item,
                                parent,
                                false
                        )
                )
            }

            override fun onBindViewHolder(holder: BooksAdapter.BooksViewHolder, position: Int, model: Books) {
                holder.bind(model, position)
            }
        }
    }

    private fun getFirstQuery() = mBaseQuery
    private fun getPoemQuery() = mBaseQuery.orderByChild("poem").equalTo(true)
    private fun getGazellesQuery() = mBaseQuery.orderByChild("gazelees").equalTo(true)
    private fun getHamsaQuery() = mBaseQuery.orderByChild("hamsa").equalTo(true)

    override fun onClick(v: View?) {
        val query =
                if (v == chipAll) {
                    getFirstQuery()
                } else if (v == chipPoem) {
                    getPoemQuery()
                } else if (v == chipGazelees) {
                    getGazellesQuery()
                } else if (v == hamburgerMenu) {
                    var searchText: String = mSearchBar.text.toString()
                    getSearchQuery(searchText)
                } else {
                    getHamsaQuery()
                }

        // Make new options
        val newOptions = FirebaseRecyclerOptions.Builder<Books>()
                .setQuery(query, Books::class.java)
                .build()

        // Change options of adapter.
        booksAdapter.updateOptions(newOptions)
    }
    private fun getSearchQuery(searchText: String) = mBaseQuery.orderByChild("title").startAt(searchText).endAt(searchText + "\uf8ff")
}