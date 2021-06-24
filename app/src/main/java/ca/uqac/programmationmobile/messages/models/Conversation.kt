package ca.uqac.programmationmobile.messages.models

data class Conversation(
    val id: String,
    val title: String,
    val lastMessage: Message?,
    val messages: List<Message>?
)
