package com.example.olodjinha.utils

import android.view.View

fun View.toggleVisibility(status: Boolean) {
    if (status) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}