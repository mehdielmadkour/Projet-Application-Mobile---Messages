package ca.uqac.programmationmobile.messages.models

data class Conversation(
    val id: String,
    val title: String,
    var lastMessage: Message?,
    val messages: List<Message>?,
    val photoUrl: String
)
