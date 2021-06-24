package ca.uqac.programmationmobile.messages.models

data class User(
    val uid : String,
    val username : String,
    val photoUrl : String?,
    val friendList : List<String>?,
    val conversationList : List<String>?
)
