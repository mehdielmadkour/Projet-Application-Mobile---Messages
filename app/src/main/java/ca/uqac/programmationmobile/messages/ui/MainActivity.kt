package ca.uqac.programmationmobile.messages.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ca.uqac.programmationmobile.messages.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private var permissionsListener = emptyMap<Int, (Int, String, Boolean)->Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (account == null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

        val navBar = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        val navController = findNavController(R.id.my_fragment_view)

        val bottomNavConfig = AppBarConfiguration(
            setOf(
                R.id.profile,
                R.id.friends,
                R.id.conversationList,
                R.id.qrScanner
            )
        )
        setupActionBarWithNavController(navController, bottomNavConfig)
        navBar.setupWithNavController(navController)
    }

    fun askForPermission(permissions : Array<String>, requestCode: Int, listener: (Int, String, Boolean) -> Unit){
        permissionsListener = permissionsListener + Pair(requestCode, listener)
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }
}