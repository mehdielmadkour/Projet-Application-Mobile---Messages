package ca.uqac.programmationmobile.messages.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.adapters.ConversationsAdapter
import ca.uqac.programmationmobile.messages.models.Message
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MessagesDataSource(val context : Context, val viewLifecycleOwner: LifecycleOwner) {

    data class MessagesHolder(val messages: List<Message>?, val errorMsg: String?)

    fun getMessagesfromConversation(conversationId: String) : LiveData<MessagesHolder> {
        val data = MutableLiveData<MessagesHolder>()

        ConversationsDataSource(context).getConversation(conversationId).observe(viewLifecycleOwner, { holder ->
            if (holder.conversation != null) {
                data.value = MessagesHolder(holder.conversation.messages, null)
            }
            else {
                data.value = MessagesHolder(null, holder.errorMsg)
            }
        })

        return data
    }

}