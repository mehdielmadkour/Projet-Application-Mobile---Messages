package ca.uqac.programmationmobile.messages.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.textclassifier.ConversationAction
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.adapters.MessagesAdapter
import ca.uqac.programmationmobile.messages.data.ConversationsDataSource
import ca.uqac.programmationmobile.messages.data.MessagesDataSource
import ca.uqac.programmationmobile.messages.services.MessageService
import com.google.android.gms.auth.api.signin.GoogleSignIn

class Conversation : Fragment() {

    private lateinit var conversationMenu: Menu
    private lateinit var conversationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_conversation, container, false)

        conversationId = arguments?.get("conversationId") as String
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        view.findViewById<RecyclerView>(R.id.recycler_chat).adapter = MessagesAdapter(null, requireContext(), viewLifecycleOwner)

        Intent(context, MessageService::class.java)
            .putExtra("conversationId", conversationId)
            .also { intent ->
                context?.startService(intent)
            }

        updateMessages(conversationId)

        view.findViewById<Button>(R.id.button_send).setOnClickListener {
            val editText = view.findViewById<EditText>(R.id.edit_message)
            val text = editText.text.toString()
            if (text != "") {
                MessagesDataSource(requireContext(), viewLifecycleOwner).postMessage(conversationId, account!!.id, text)
                editText.text = null
                updateMessages(conversationId)
            }
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.conversation_menu, menu)
        conversationMenu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.invite_friend -> {
                val action = ConversationDirections.actionConversation3ToInviteFriends(conversationId)
                findNavController().navigate(action)
                return true
            }
        }
        return false
    }


    fun updateMessages(conversationId: String) {
        ConversationsDataSource.updateConversation(conversationId)
        ConversationsDataSource.conversationHolder.observe(viewLifecycleOwner, { holder ->
            if (holder.conversation?.messages != null) {
                (view?.findViewById<RecyclerView>(R.id.recycler_chat)?.adapter as MessagesAdapter).updateData(holder.conversation.messages)
            }
            else {
                Toast.makeText(activity, holder.errorMsg, Toast.LENGTH_SHORT).show()
            }
        })
    }
}