package ir.push.sdk

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.RemoteMessage
import ir.push.mypushsdk.PushMessageHandler
import org.json.JSONObject

class FirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {

    override fun onNewToken(s: String?) {
        super.onNewToken(s)

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        val s = remoteMessage?.data?.get("structure")
        PushMessageHandler(baseContext, JSONObject(s))


    }
}
