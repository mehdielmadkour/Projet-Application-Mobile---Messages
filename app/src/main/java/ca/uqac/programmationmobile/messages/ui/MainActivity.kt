package ca.uqac.programmationmobile.messages.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ca.uqac.programmationmobile.messages.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navBar = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        val navController = findNavController(R.id.my_fragment_view)

        val bottomNavConfig = AppBarConfiguration(
            setOf(
                R.id.profile,
                R.id.friends,
                R.id.conversationList
            )
        )
        setupActionBarWithNavController(navController, bottomNavConfig)
        navBar.setupWithNavController(navController)
    }
}