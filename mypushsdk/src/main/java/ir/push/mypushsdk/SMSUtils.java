package ir.push.mypushsdk;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

import static ir.push.mypushsdk.ConstsClass.DELIVERED_SMS_ACTION_NAME;
import static ir.push.mypushsdk.ConstsClass.SENT_SMS_ACTION_NAME;


public class SMSUtils extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        //Detect l'envoie de sms
        if (intent.getAction().equals(SENT_SMS_ACTION_NAME)) {
            switch (getResultCode()) {
                case Activity.RESULT_OK: // Sms sent
                    //  Toast.makeText(context, context.getString(R.string.sms_send), Toast.LENGTH_LONG).show();

                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE: // generic failure
                    // Toast.makeText(context, context.getString(R.string.sms_not_send), Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE: // No service
                    //  Toast.makeText(context, context.getString(R.string.sms_not_send_no_service), Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU: // null pdu
                    // Toast.makeText(context, context.getString(R.string.sms_not_send), Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF: //Radio off
                    // Toast.makeText(context, context.getString(R.string.sms_not_send_no_radio), Toast.LENGTH_LONG).show();
                    break;
            }
        }
        //detect la reception d'un sms
        else if (intent.getAction().equals(DELIVERED_SMS_ACTION_NAME)) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(context, context.getString(R.string.sms_not_receive), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    /**
     * Test if device can send SMS
     *
     * @param context
     * @return
     */
    public static boolean canSendSMS(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
    }

    public static void sendSMS(final Context context, String phoneNumber, String message, final BroadcastReceiver receiver) {

        if (!canSendSMS(context)) {
            Toast.makeText(context, context.getString(R.string.cannot_send_sms), Toast.LENGTH_LONG).show();
            return;
        }

        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(SENT_SMS_ACTION_NAME), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED_SMS_ACTION_NAME), 0);

        //register for sending and delivery
        context.registerReceiver(receiver, new IntentFilter(SENT_SMS_ACTION_NAME));
        context.registerReceiver(receiver, new IntentFilter(DELIVERED_SMS_ACTION_NAME));

        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> parts = sms.divideMessage(message);

        ArrayList<PendingIntent> sendList = new ArrayList<>();
        sendList.add(sentPI);

        ArrayList<PendingIntent> deliverList = new ArrayList<>();
        deliverList.add(deliveredPI);

        sms.sendMultipartTextMessage(phoneNumber, null, parts, sendList, deliverList);

    }

}