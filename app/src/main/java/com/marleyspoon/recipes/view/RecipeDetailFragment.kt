package com.marleyspoon.recipes.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.marleyspoon.recipes.R
import com.marleyspoon.recipes.model.Recipe
import com.marleyspoon.recipes.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_recipe_detail.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RecipeDetailFragment : Fragment(R.layout.fragment_recipe_detail) {

    private val viewModel: SharedViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedRecipe.observe(viewLifecycleOwner, Observer {
            setRecipeDetail(it)
        })
    }

    private fun setRecipeDetail(recipe: Recipe) {
        Glide.with(requireContext()).load(recipe.imageUrl).into(ivRecipe)
        tvTitle.text = recipe.title
        recipe.chef?.let {
            tvChef.text = getString(R.string.chef, it)
        }
        recipe.description?.let {
            tvDesciption.text = getString(R.string.description, it)
        }
        recipe.tags?.let {
            tvTag.text = getString(R.string.tags, it.toString())
        }
    }
}
