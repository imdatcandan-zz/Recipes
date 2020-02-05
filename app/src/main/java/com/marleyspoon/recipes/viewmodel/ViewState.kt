package com.marleyspoon.recipes.viewmodel

import com.marleyspoon.recipes.model.Recipe

sealed class ViewState {
    data class Success(val recipeList: MutableList<Recipe>) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
    data class Loading(val showLoading: Boolean) : ViewState()
}