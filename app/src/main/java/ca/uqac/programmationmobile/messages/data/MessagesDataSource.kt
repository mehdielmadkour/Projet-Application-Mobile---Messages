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
import ca.uqac.programmationmobile.messages.network.APIConversationInterface
import ca.uqac.programmationmobile.messages.network.ApiClient
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MessagesDataSource(val context : Context, val viewLifecycleOwner: LifecycleOwner) {

    data class MessagesHolder(val messages: List<Message>?, val errorMsg: String?)

    private var apiInterface: APIConversationInterface? = null

    init {
        apiInterface = ApiClient.getApiClient().create(APIConversationInterface::class.java)
    }

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

    fun postMessage(conversationId: String, user: String, text: String) {
        apiInterface?.postMessage(conversationId, user, text)?.enqueue(object : retrofit2.Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {}
            override fun onFailure(call: Call<Unit>, t: Throwable) {}
        })
    }

}