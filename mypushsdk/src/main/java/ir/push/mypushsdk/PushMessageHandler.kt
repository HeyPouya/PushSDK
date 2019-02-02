package ir.push.mypushsdk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import org.json.JSONObject

class PushMessageHandler(private val context: Context, val message: JSONObject) {


    init {
        handleMessage()
    }

    private fun handleMessage() {
        var intentUri = ""
        var storeIntentUri = ""
        var type = ""
        var url = ""
        var phone = ""
        var keyword = ""
        var title = ""
        var description1 = ""
        var description2 = ""
        var button = ""
        var image = ""
        var btnFunction = ""
        var isForced = ""
        var isTwoStep = ""
        var phoneSecondStep = ""
        var keywordSecondStep = ""
        var description1SecondStep = ""
        var description2SecondStep = ""
        var titleSeconsStep = ""
        var buttonSecondStep = ""
        var imageSecondStep = ""



            try {
                type = message.getString("type")
                isTwoStep = message.getString("istwostep")
                if (type == "web") {
                    url = message.getString("url")
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(i)
                } else if (type == "google") {
                    intentUri = message.getString("intent")
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(intentUri)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)

                } else if (type == "popup") {
                    title = message.getString("title")
                    description1 = message.getString("description_first")
                    description2 = message.getString("description_second")
                    image = message.getString("image")
                    button = message.getString("button")
                    btnFunction = message.getString("btnfunction")
                    url = message.getString("url")
                    isForced = message.getString("isforced")
                    if (btnFunction == "app") {
                        intentUri = message.getString("intent")
                        storeIntentUri = message.getString("package")
                    }
                    if (btnFunction == "sms") {
                        phone = message.getString("phone")
                        keyword = message.getString("keyword")
                    }
                    if (isTwoStep == "true") {
                        titleSeconsStep = message.getString("title2")
                        description1SecondStep = message.getString("description_first2")
                        description2SecondStep = message.getString("description_second2")
                        imageSecondStep = message.getString("image2")
                        buttonSecondStep = message.getString("button2")
                        phoneSecondStep = message.getString("phone2")
                        keywordSecondStep = message.getString("keyword2")
                    }

                    val intent = Intent(context, MyDialog::class.java)
                    intent.putExtra("title", title)
                    intent.putExtra("description1", description1)
                    intent.putExtra("description2", description2)
                    intent.putExtra("image", image)
                    intent.putExtra("button", button)
                    intent.putExtra("btnfunction", btnFunction)
                    intent.putExtra("package", storeIntentUri)
                    intent.putExtra("intent", intentUri)
                    intent.putExtra("url", url)
                    intent.putExtra("isforced", isForced)
                    intent.putExtra("phone", phone)
                    intent.putExtra("keyword", keyword)
                    intent.putExtra("title2", titleSeconsStep)
                    intent.putExtra("description_first2", description1SecondStep)
                    intent.putExtra("description_second2", description2SecondStep)
                    intent.putExtra("image2", imageSecondStep)
                    intent.putExtra("button2", buttonSecondStep)
                    intent.putExtra("phone2", phoneSecondStep)
                    intent.putExtra("keyword2", keywordSecondStep)
                    intent.putExtra("istwostep", isTwoStep)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)

                } else {
                    intentUri = message.getString("intent")
                    storeIntentUri = message.getString("package")
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(intentUri)
                    intent.setPackage(storeIntentUri)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    url = message.getString("url")
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(i)
                } catch (e1: Exception) {
                    e1.printStackTrace()
                }

            }

        }


    }