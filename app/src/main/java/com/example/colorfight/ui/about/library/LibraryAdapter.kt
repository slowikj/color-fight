package com.example.colorfight.ui.about.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colorfight.R

class LibraryAdapter : RecyclerView.Adapter<LibraryViewHolder>() {

	private var items: List<Library> = emptyList()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder =
		LibraryViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.library_layout,
				parent,
				false
			)
		)

	override fun getItemCount(): Int =
		items.size

	override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
		holder.bind(items[position])
	}

	fun resetItems(items: List<Library>) {
		this.items = items
	}
}