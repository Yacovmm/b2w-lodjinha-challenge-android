package com.example.olodjinha.ui

import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.olodjinha.R
import com.example.olodjinha.databinding.ActivityMainBinding
import com.example.olodjinha.ui.helpers.NavigationDelegate
import com.google.android.material.textfield.TextInputLayout


class MainActivity : AppCompatActivity(), NavigationDelegate {

    private lateinit var binding: ActivityMainBinding

    private val navigator by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavView()

        setupNavigation()
    }

    private fun setupNavigation() {
        navigator.addOnDestinationChangedListener { _, destination, _ ->
//            binding.appbar.visibility = View.VISIBLE
            binding.topAppBar.visibility = View.VISIBLE

            // Setando state padrao do drawer
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            // Setando cor padrao do toolbar
            binding.topAppBar.setBackgroundColor(getColor(R.color.primaryColor))


            when (destination.id) {
                R.id.mainFragment -> {
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    for (i in 0 until binding.topAppBar.childCount) {
                        val view = binding.topAppBar.getChildAt(i)
                        if (view is TextView && view.text == destination.label) {
                            val typeface = ResourcesCompat.getFont(this, R.font.pacificoregular)
                            view.typeface = typeface
                            break
                        }
                    }
                }
                R.id.productDetailFragment -> {
                    binding.topAppBar.visibility = View.GONE
                }
                else -> {
                    for (i in 0 until binding.topAppBar.childCount) {
                        val view = binding.topAppBar.getChildAt(i)
                        if (view is TextView && view.text == destination.label) {
                            view.typeface = Typeface.DEFAULT
                            break
                        }
                    }
                }
            }
        }
    }

    private fun setupNavView() {
        binding.topAppBar.setupWithNavController(navigator, binding.drawerLayout)
        binding.navigationView.setupWithNavController(navigator)
    }

    override fun setToolBarTitle(title: String) {
        binding.topAppBar.title = title
    }
}
