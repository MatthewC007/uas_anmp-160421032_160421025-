package com.anmp.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.anmp.myapplication.databinding.LayoutItemBinding
import com.anmp.myapplication.model.News
import com.squareup.picasso.Picasso


class NewsListAdapter(private var newsList: List<News>) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = newsList[position]
        if (item.authorName.isNullOrEmpty()) {
            holder.binding.txtAuthor.text = "Author"
        } else {
            holder.binding.txtAuthor.text = item.authorName
        }
        holder.binding.txtDeskripsi.text = item.synopsis
        holder.binding.txtJudul.text = item.title
        holder.binding.txtPenulis.text = item.username

        Picasso.get()
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.binding.imageView)

        holder.binding.btnRead.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(item, item.title.toString())
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}
