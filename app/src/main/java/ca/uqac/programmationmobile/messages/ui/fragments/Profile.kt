package ca.uqac.programmationmobile.messages.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.ui.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class Profile : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)


        val account = GoogleSignIn.getLastSignedInAccount(context)

        view.findViewById<TextView>(R.id.username).also { textview ->
            textview.text = account!!.displayName
        }

        view.findViewById<Button>(R.id.btn_sign_out).setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestProfile()
                .build()
            val signInClient = GoogleSignIn.getClient(requireContext(), gso)
            signInClient.signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

        return view
    }
}