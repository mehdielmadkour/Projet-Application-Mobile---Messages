package ca.uqac.programmationmobile.messages.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.data.ConversationsDataSource
import com.google.android.gms.auth.api.signin.GoogleSignIn

class CreateConversation : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_conversation, container, false)

        val account = GoogleSignIn.getLastSignedInAccount(context)

        view.findViewById<Button>(R.id.create_conversation_btn).setOnClickListener {
            val editText = view.findViewById<EditText>(R.id.conversation_name)
            ConversationsDataSource.createConversation(editText.text.toString(), account!!.id).observe(viewLifecycleOwner, { id ->
                val action = CreateConversationDirections.actionCreateConversationToConversation3(conversationId = id)
                findNavController().navigate(action)
            })
        }

        return view
    }
}