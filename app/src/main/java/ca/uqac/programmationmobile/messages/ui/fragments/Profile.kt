package ca.uqac.programmationmobile.messages.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.data.UserDataSource
import ca.uqac.programmationmobile.messages.ui.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso

class Profile : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)


        val account = GoogleSignIn.getLastSignedInAccount(context)

        UserDataSource(requireContext()).createUser(
            account!!.id,
            account!!.displayName,
            (account!!.photoUrl ?: "EMPTY") as String
        )

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

        view.findViewById<ImageView>(R.id.profile_image).also { imageView ->
            var photoUrl : String?

            Picasso.get()
                .load(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(imageView);

            account?.photoUrl?.let { uri ->
                photoUrl = uri.toString().replace("http:", "https:")
                Picasso.get()
                    .load(photoUrl)
                    .error(R.drawable.ic_profile)
                    .into(imageView);
            }
        }


        view.findViewById<ImageView>(R.id.qr_code).also { imageView ->
            val displayMetrics = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)

            val width = displayMetrics.widthPixels
            val height = displayMetrics.heightPixels

            var size = if(width < height) width else height
            size = size * 3 / 4

            val QREncoder = QRGEncoder(account?.id.toString(), null, QRGContents.Type.TEXT, size)
            imageView.setImageBitmap(QREncoder.encodeAsBitmap())
        }

        return view
    }
}