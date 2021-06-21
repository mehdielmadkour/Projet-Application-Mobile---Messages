package ca.uqac.programmationmobile.messages.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uqac.programmationmobile.messages.models.Conversation

class ConversationsDataSource {
    data class ConversationsHolder(val conversations: List<Conversation>?, val errorMsg: String?)

    fun getUserConversations(uid: String) : LiveData<ConversationsHolder> {
        val data = MutableLiveData<ConversationsHolder>()

        val conversations = listOf<Conversation>(
            Conversation("1","conv 1", null),
            Conversation("2","conv 2", null),
            Conversation("3","conv 3", null),
            Conversation("4","conv 4", null),
            Conversation("5","conv 5", null),
            Conversation("6","conv 6", null),
            Conversation("7","conv 7", null)
        )

        data.value = ConversationsHolder(conversations, null)

        return data
    }
}