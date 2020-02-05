package com.marleyspoon.recipes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contentful.java.cda.CDAEntry
import com.marleyspoon.recipes.data.DataRepository
import com.marleyspoon.recipes.model.Recipe
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class SharedViewModel(dataRepository: DataRepository) : ViewModel() {

    private val cdaClient = dataRepository.cdaClient
    private val recipeList: MutableList<Recipe> = mutableListOf()
    val stateLiveData: MutableLiveData<ViewState> = MutableLiveData()
    val selectedRecipe: MutableLiveData<Recipe> = MutableLiveData()

    init {
        fetchRecipeList()
    }

    fun fetchRecipeList() {
        viewModelScope.launch(IO) {
            val result = try {
                cdaClient.fetch(CDAEntry::class.java)
                    .withContentType(Recipe.CONTENT_TYPE)
                    .all()
                    .items()
                    .map {
                        recipeList.add(Recipe.createRecipeFromCDAResource(it))
                    }
                ViewState.Success(recipeList)
            } catch (e: Exception) {
                ViewState.Error(e)
            } finally {
                ViewState.Loading(false)
            }
            stateLiveData.postValue(result)
        }
    }


    fun setSelectedRecipe(recipe: Recipe) {
        selectedRecipe.value = recipe
    }
}

