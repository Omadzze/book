package com.omaddev.read

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.omaddev.read.model.Books
import com.squareup.picasso.Picasso

class BooksAdapter(options: FirebaseRecyclerOptions<Books>) :
    FirebaseRecyclerAdapter<Books, BooksAdapter.BooksViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {

        return BooksViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grid_view_item, parent, false))

    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int, model: Books) {
        holder.authorBook.text = model.author
        holder.titleBook.text = model.title
        Log.i("TAG", holder.titleBook.toString())
        Picasso.get().load(model.thumbnail).into(holder.thumbnail)

        holder.constrainLayout.setTag(position)
        holder.constrainLayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val dataIntent = Intent(view.context, DetailActivity::class.java)
                dataIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                dataIntent.putExtra("description", model.description)
                dataIntent.putExtra("title", model.title)
                dataIntent.putExtra("thumbnail", model.thumbnail)
                dataIntent.putExtra("author", model.author)
                dataIntent.putExtra("download", model.download)
                dataIntent.putExtra("file", model.file)
                dataIntent.putExtra("fileName", model.fileName)
                view.context.startActivity(dataIntent)
            }
        })
    }

    class BooksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var titleBook: TextView = itemView.findViewById(R.id.titleBook)
        var authorBook: TextView = itemView.findViewById(R.id.authorBook)
        var thumbnail: ImageView = itemView.findViewById(R.id.thumbnailImage)
        var constrainLayout: ConstraintLayout = itemView.findViewById(R.id.constrainMain)
    }
}