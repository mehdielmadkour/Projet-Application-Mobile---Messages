package ca.uqac.programmationmobile.messages.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uqac.programmationmobile.messages.models.Message
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MessagesDataSource {

    data class MessagesHolder(val messages: List<Message>?, val errorMsg: String?)

    fun getMessagesfromConversation(conversationId: String) : LiveData<MessagesHolder> {
        val data = MutableLiveData<MessagesHolder>()

        val messages = listOf<Message>(
            Message("bonjour!", "user1","2021-01-01 10:50:00"),
            Message("bonjour!", "user1","2021-01-01 10:51:00"),
            Message("bonjour!", "user2","2021-01-02 10:50:00"),
            Message("bonjour!", "user2","2021-01-02 10:52:00"),
            Message("bonjour!", "user2","2021-01-03 10:40:00"),
            Message("bonjour!", "user3","2021-01-03 10:50:00")
        )

        data.value = MessagesHolder(messages, null)

        return data
    }

}