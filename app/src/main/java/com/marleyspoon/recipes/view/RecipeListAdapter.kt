package com.marleyspoon.recipes.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marleyspoon.recipes.R
import com.marleyspoon.recipes.model.Recipe
import kotlinx.android.synthetic.main.adapter_rate_list.view.*

class RateListAdapter(
    private val recipeList: MutableList<Recipe>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<RateListAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(recipe: Recipe) {
            view.tvTitle.text = recipe.title
            Glide.with(view).load(recipe.imageUrl).into(view.ivRecipe)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_rate_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipeList[position])
        holder.view.setOnClickListener {
            itemClickListener.onItemClicked(recipeList[position])
        }
    }

    override fun getItemCount() = recipeList.size
}

interface OnItemClickListener {
    fun onItemClicked(recipe: Recipe)
}