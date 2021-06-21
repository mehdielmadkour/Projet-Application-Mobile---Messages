package ca.uqac.programmationmobile.messages.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.adapters.ConversationsAdapter
import ca.uqac.programmationmobile.messages.data.ConversationsDataSource
import ca.uqac.programmationmobile.messages.models.Conversation

class ConversationList : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_conversation_list, container, false)

        view.findViewById<RecyclerView>(R.id.conversation_list).adapter = ConversationsAdapter(null) { conversation ->
            onConversationClick(conversation)
        }

        ConversationsDataSource().getUserConversations("").observe(viewLifecycleOwner, { holder ->
            if (holder.conversations != null) {
                (view.findViewById<RecyclerView>(R.id.conversation_list).adapter as ConversationsAdapter).updateData(holder.conversations)
            }
            else {
                Toast.makeText(activity, holder.errorMsg, Toast.LENGTH_SHORT).show()
            }
        })

        view.findViewById<View>(R.id.new_conversation_button).setOnClickListener {
            val action = ConversationListDirections.actionConversationListToCreateConversation()
            findNavController().navigate(action)
        }

        return view
    }

    fun onConversationClick(conversation: Conversation) {
        val action = ConversationListDirections.actionConversationListToConversation3(conversationId = conversation.id)
        findNavController().navigate(action)
    }
}