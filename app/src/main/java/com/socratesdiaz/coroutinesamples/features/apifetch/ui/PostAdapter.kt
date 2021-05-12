package com.socratesdiaz.coroutinesamples.features.apifetch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.socratesdiaz.coroutinesamples.R
import com.socratesdiaz.coroutinesamples.features.apifetch.dto.Post

class PostAdapter(private val onClickItem: (Post) -> Unit): RecyclerView.Adapter<PostViewHolder>() {
    private val _posts: MutableList<Post> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.listitem_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = _posts[position]
        holder.bindElement(post, onClickItem)
    }

    override fun getItemCount() = _posts.size

    fun setPosts(posts: List<Post>) {
        _posts.addAll(posts)
        notifyDataSetChanged()
    }
}

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.findViewById<TextView>(R.id.title)
    private val subtitle = itemView.findViewById<TextView>(R.id.subtitle)

    fun bindElement(post: Post, onClickItem: (Post) -> Unit) {
        title.text = post.title
        subtitle.text = post.body
        itemView.setOnClickListener { onClickItem(post) }
    }
}