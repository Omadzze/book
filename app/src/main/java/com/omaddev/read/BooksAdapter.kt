package com.omaddev.read

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.omaddev.read.model.Books
import com.squareup.picasso.Picasso

class BooksAdapter {

    class BooksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var titleBook: TextView = itemView.findViewById(R.id.titleBook)
        var authorBook: TextView = itemView.findViewById(R.id.authorBook)
        var thumbnail: ImageView = itemView.findViewById(R.id.thumbnailImage)
        var constrainLayout: ConstraintLayout = itemView.findViewById(R.id.constrainMain)
        var rating: ImageView = itemView.findViewById(R.id.raiting)

        fun bind(books: Books, position: Int) {
            titleBook.text = books.title
            authorBook.text = books.author
            Picasso.get().load(books.thumbnail).into(thumbnail)

            constrainLayout.setTag(position)
            constrainLayout.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    val dataIntent = Intent(view.context, DetailActivity::class.java)
                    dataIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    dataIntent.putExtra("description", books.description)
                    dataIntent.putExtra("title", books.title)
                    dataIntent.putExtra("thumbnail", books.thumbnail)
                    dataIntent.putExtra("author", books.author)
                    dataIntent.putExtra("download", books.download)
                    dataIntent.putExtra("file", books.file)
                    dataIntent.putExtra("fileName", books.fileName)
                    dataIntent.putExtra("rating", books.rating)
                    view.context.startActivity(dataIntent)
                }
            })

            if (books.rating!! >= 5.0) {
                rating.setImageResource(R.drawable.ic_five_star)
            } else if (books.rating!! >= 4.0) {
                rating.setImageResource(R.drawable.ic_four_star)
            }

        }
    }
}