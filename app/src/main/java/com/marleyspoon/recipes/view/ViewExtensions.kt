package com.marleyspoon.recipes.view

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.showLoading(show: Boolean) {
    if (show) {
        visible()
    } else {
        gone()
    }
}