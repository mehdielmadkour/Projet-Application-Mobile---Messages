package ca.uqac.programmationmobile.messages.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import ca.uqac.programmationmobile.messages.R
import com.google.android.gms.auth.api.signin.GoogleSignIn

class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIME:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var intent: Intent?

        val account = GoogleSignIn.getLastSignedInAccount(this)


        if (account == null){
            intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

        }
        else {
            intent = Intent(this, MainActivity::class.java)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
        }, SPLASH_TIME)
    }
}