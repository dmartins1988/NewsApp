package com.example.newsapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import com.example.newsapp.R
import com.example.newsapp.extensions.executeFragOperation
import com.example.newsapp.presentation.ui.main.NewsFragment
import com.example.newsapp.utils.FragOperations

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        executeFragOperation(NewsFragment.newInstance(), R.id.container, FragOperations.ADD)

        configureFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return configureMenu(menu)
    }

    private fun configureFragment() {
        var fragment = supportFragmentManager.findFragmentById(R.id.container) as NewsFragment?
        if (fragment == null) {
            fragment = NewsFragment()
            supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
        }
    }

    private fun configureMenu(menu: Menu?) : Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.queryHint = getString(R.string.search)
        return true
    }

}
