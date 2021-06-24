package ca.uqac.programmationmobile.messages.services

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import ca.uqac.programmationmobile.messages.data.ConversationsDataSource

class MessageService : Service() {

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null
    private var running = false

    private inner class ServiceHandler(looper: Looper) : Handler(looper){
        override fun handleMessage(msg: Message) {
            try {
                while (running) {
                    msg.data.getString("uid")?.let { id ->
                        ConversationsDataSource.updateConversations(id)
                    }
                    msg.data.getString("conversationId")?.let { id ->
                        ConversationsDataSource.updateConversation(id)
                    }
                    Thread.sleep(5000)
                }
            }catch (e: InterruptedException){
                Thread.currentThread().interrupt()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        running = true
        HandlerThread("MessageService", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()

            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val uid = intent?.getStringExtra("uid")
        val conversationId = intent?.getStringExtra("conversationId")

        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId
            val bundle = Bundle()
            bundle.putString("uid", uid)
            bundle.putString("uid", conversationId)
            serviceHandler?.sendMessage(msg)
        }

        return START_NOT_STICKY
    }

}