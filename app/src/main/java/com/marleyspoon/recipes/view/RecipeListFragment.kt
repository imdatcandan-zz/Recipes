package com.marleyspoon.recipes.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.marleyspoon.recipes.R
import com.marleyspoon.recipes.model.Recipe
import com.marleyspoon.recipes.viewmodel.SharedViewModel
import com.marleyspoon.recipes.viewmodel.ViewState
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RecipeListFragment : Fragment(R.layout.fragment_recipe_list), OnItemClickListener {

    private val viewModel: SharedViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Success -> recyclerView.adapter = RateListAdapter(it.recipeList, this)
                is ViewState.Error -> showErrorDialog(it.exception)
            }
            progressBar.showLoading(false)
        })
    }

    override fun onItemClicked(recipe: Recipe) {
        viewModel.setSelectedRecipe(recipe)
        findNavController().navigate(R.id.action_recipeListFragment_to_recipeDetailFragment)
    }

    private fun showErrorDialog(exception: Throwable) {
        AlertDialog.Builder(this.requireContext())
            .setTitle(R.string.dialog_error_title)
            .setMessage(getString(R.string.dialog_error_message, exception.localizedMessage))
            .setPositiveButton(R.string.dialog_error_button) { _, _ ->
                viewModel.fetchRecipeList()
            }.create()
            .show()
    }
}
