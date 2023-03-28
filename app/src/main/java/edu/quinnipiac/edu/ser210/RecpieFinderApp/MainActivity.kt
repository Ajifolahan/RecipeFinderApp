package edu.quinnipiac.edu.ser210.RecpieFinderApp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ShareActionProvider
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        return when(id){
            R.id.action_settings -> {
                //change background color
                true
            }  R.id.action_share -> {
                val shareActionProvider : ShareActionProvider? = MenuItemCompat.getActionProvider(item) as ShareActionProvider?
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "This is a message for you")
                if(shareActionProvider != null){
                    shareActionProvider.setShareIntent(intent)
                }
                true
            } else -> NavigationUI.onNavDestinationSelected(item!!, navController)
                    || super.onOptionsItemSelected(item)
            //it currently navigates to a new screen so just make the screen tell us about the app and AI
            //for the help options
        }

    }
}