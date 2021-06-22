package ca.uqac.programmationmobile.messages.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uqac.programmationmobile.messages.models.Message
import ca.uqac.programmationmobile.messages.models.User

class UserDataSource {

    data class UsersHolder(val users: List<User>?, val errorMsg: String?)

    fun getFriends(uid: String) : LiveData<UsersHolder> {
        val data = MutableLiveData<UsersHolder>()

        val friends = listOf<User>(
            User("1", "user1", "http://i.imgur.com/DvpvklR.pnghttp://i.imgur.com/DvpvklR.png"),
            User("2", "user2", "http://i.imgur.com/DvpvklR.pnghttp://i.imgur.com/DvpvklR.png"),
            User("3", "user3", "http://i.imgur.com/DvpvklR.pnghttp://i.imgur.com/DvpvklR.png"),
            User("4", "user4", "http://i.imgur.com/DvpvklR.pnghttp://i.imgur.com/DvpvklR.png"),
            User("5", "user5", "http://i.imgur.com/DvpvklR.pnghttp://i.imgur.com/DvpvklR.png"),
            User("6", "user6", "http://i.imgur.com/DvpvklR.pnghttp://i.imgur.com/DvpvklR.png")
        )

        data.value = UsersHolder(friends, null)

        return data
    }
}