package ca.uqac.programmationmobile.messages.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uqac.programmationmobile.messages.models.Message

class MessagesDataSource {

    data class MessagesHolder(val messages: List<Message>?, val errorMsg: String?)

    fun getMessagesfromConversation(convId: String) : LiveData<MessagesHolder> {
        val data = MutableLiveData<MessagesHolder>()

        val messages = listOf<Message>(
            Message("bonjour!", "user1"),
            Message("bonjour!", "user2"),
            Message("bonjour!", "user3"),
            Message("bonjour!", "user4"),
            Message("bonjour!", "user5"),
            Message("bonjour!", "user6"),
        )

        data.value = MessagesHolder(messages, null)

        return data
    }

}