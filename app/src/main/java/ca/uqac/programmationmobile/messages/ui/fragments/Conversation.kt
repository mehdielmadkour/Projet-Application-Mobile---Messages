package ca.uqac.programmationmobile.messages.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.textclassifier.ConversationAction
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.adapters.MessagesAdapter
import ca.uqac.programmationmobile.messages.data.ConversationsDataSource
import ca.uqac.programmationmobile.messages.data.MessagesDataSource
import com.google.android.gms.auth.api.signin.GoogleSignIn

class Conversation : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_conversation, container, false)

        val conversationId = arguments?.get("conversationId") as String
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        view.findViewById<RecyclerView>(R.id.recycler_chat).adapter = MessagesAdapter(null, requireContext(), viewLifecycleOwner)

        MessagesDataSource(requireContext(), viewLifecycleOwner).getMessagesfromConversation(conversationId).observe(viewLifecycleOwner, { holder ->
            if (holder.messages != null) {
                (view.findViewById<RecyclerView>(R.id.recycler_chat).adapter as MessagesAdapter).updateData(holder.messages)
            }
            else {
                Toast.makeText(activity, holder.errorMsg, Toast.LENGTH_SHORT).show()
            }
        })

        view.findViewById<Button>(R.id.button_send).setOnClickListener {
            val editText = view.findViewById<EditText>(R.id.edit_message)
            val text = editText.text.toString()
            if (text != "") {
                MessagesDataSource(requireContext(), viewLifecycleOwner).postMessage(conversationId, account!!.id, text)
                editText.text = null
            }
        }

        return view
    }
}