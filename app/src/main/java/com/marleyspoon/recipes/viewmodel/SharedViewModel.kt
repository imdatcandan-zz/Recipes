package com.marleyspoon.recipes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marleyspoon.recipes.data.DataRepository
import com.marleyspoon.recipes.model.Recipe
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SharedViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val recipeList: MutableList<Recipe> = mutableListOf()
    val stateLiveData: MutableLiveData<ViewState> = MutableLiveData()
    val selectedRecipe: MutableLiveData<Recipe> = MutableLiveData()

    init {
        fetchRecipeList()
    }

    fun fetchRecipeList() {
        viewModelScope.launch(IO) {
            try {
                dataRepository.fetchRecipeList()
                    .map {
                        recipeList.add(Recipe.createRecipeFromCDAResource(it))
                    }
                stateLiveData.postValue(ViewState.Success(recipeList))
            } catch (e: Exception) {
                stateLiveData.postValue(ViewState.Error(e))
            }
        }
    }

    fun setSelectedRecipe(recipe: Recipe) {
        selectedRecipe.value = recipe
    }
}

