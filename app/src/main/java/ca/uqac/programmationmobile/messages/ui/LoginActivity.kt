package ca.uqac.programmationmobile.messages.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import ca.uqac.programmationmobile.messages.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException

class LoginActivity : AppCompatActivity() {

    private val signInRequest = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        try {
            Log.e("LOGIN_TAG", "OK")
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)

        } catch (ex : ApiException){
            Log.e("LOGIN_TAG", ex.statusCode.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestProfile()
            .build()
        val signInClient = GoogleSignIn.getClient(this, gso)

        findViewById<SignInButton>(R.id.login_btn).setOnClickListener {
            signInClient.signInIntent.also { intent ->
                signInRequest.launch(intent)
            }
        }
    }
}