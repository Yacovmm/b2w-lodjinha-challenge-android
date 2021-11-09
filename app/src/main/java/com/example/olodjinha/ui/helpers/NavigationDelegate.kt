package com.example.olodjinha.ui.helpers

interface NavigationDelegate {
    fun setToolBarTitle(title: String)

    fun setToolbarFilterClick(action: () -> Unit)
}