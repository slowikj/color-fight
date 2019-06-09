package com.example.colorfight.ui.about

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.library_layout.view.*

class LibraryViewHolder(view: View): RecyclerView.ViewHolder(view) {

	fun bind(model: Library) {
		itemView.tvTitle.text = model.name
		itemView.tvSubtitle.text = model.detailedName
	}

}
