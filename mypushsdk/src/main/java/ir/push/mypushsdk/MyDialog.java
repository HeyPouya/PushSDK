package ir.push.mypushsdk;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class MyDialog extends AppCompatActivity {
    TextView txtAlertdialogTitle;
    TextView txtAlertdialogMessageFirst;
    TextView txtAlertdialogMessageSecond;
    ImageView imgAlertDialog;
    ImageView imgCloseAlert;
    Button btnDialog;
    boolean shouldShowSecondButton = false;
    BroadcastReceiver receiver;
    Boolean isSmsSent = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        receiver = new SMSUtils();

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description1 = intent.getStringExtra("description1");
        String description2 = intent.getStringExtra("description2");
        String image = intent.getStringExtra("image");
        String txtBtn = intent.getStringExtra("button");
        final String btnFunction = intent.getStringExtra("btnfunction");
        final String url = intent.getStringExtra("url");
        final String intentUri = intent.getStringExtra("intent");
        final String storeIntentUri = intent.getStringExtra("package");
        final String isForced = intent.getStringExtra("isforced");
        final String phone = intent.getStringExtra("phone");
        final String keyword = intent.getStringExtra("keyword");
        final String titleSeconsStep = intent.getStringExtra("title2");
        final String description1SecondStep = intent.getStringExtra("description_first2");
        final String description2SecondStep = intent.getStringExtra("description_second2");
        final String imageSecondStep = intent.getStringExtra("image2");
        final String buttonSecondStep = intent.getStringExtra("button2");
        final String phoneSecondStep = intent.getStringExtra("phone2");
        final String keywordSecondStep = intent.getStringExtra("keyword2");
        final String isTwoStep = intent.getStringExtra("istwostep");
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alertdialog_view, null);
        dialog.setView(view);
        txtAlertdialogTitle = (TextView) view.findViewById(R.id.txt_alert_dialog_title);
        txtAlertdialogMessageFirst = (TextView) view.findViewById(R.id.txt_alert_dialog_message_first);
        txtAlertdialogMessageSecond = (TextView) view.findViewById(R.id.txt_alert_dialog_message_second);
        imgAlertDialog = (ImageView) view.findViewById(R.id.img_alert_dialog);
        imgCloseAlert = (ImageView) view.findViewById(R.id.img_close);
        btnDialog = (Button) view.findViewById(R.id.btn_dialog);

        txtAlertdialogTitle.setText(title);
        txtAlertdialogMessageFirst.setText(description1);
        txtAlertdialogMessageSecond.setText(description2);
        Picasso.get().load(image).into(imgAlertDialog);
        btnDialog.setText(txtBtn);
        final AlertDialog alertDialog = dialog.create();
        alertDialog.setCancelable(false);
        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    switch (btnFunction) {
                        case "app":
                            alertDialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(intentUri));
                            intent.setPackage(storeIntentUri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            finish();
                            startActivity(intent);
                            break;
                        case "web":
                            alertDialog.dismiss();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            finish();
                            startActivity(i);
                            break;
                        case "sms":
                            if (isTwoStep.equals("true")) {
                                if (!shouldShowSecondButton) {
                                    shouldShowSecondButton = true;
                                    SMSUtils.sendSMS(MyDialog.this, phone, keyword, receiver);
                                    isSmsSent = true;
                                    txtAlertdialogTitle.setText(titleSeconsStep);
                                    txtAlertdialogMessageFirst.setText(description1SecondStep);
                                    txtAlertdialogMessageSecond.setText(description2SecondStep);
                                    Picasso.get().load(imageSecondStep).into(imgAlertDialog);
                                    btnDialog.setText(buttonSecondStep);
                                } else if (shouldShowSecondButton) {
                                    SMSUtils.sendSMS(MyDialog.this, phoneSecondStep, keywordSecondStep, receiver);
                                    isSmsSent = true;
                                    alertDialog.dismiss();
                                    finish();
                                }
                            } else {
                                SMSUtils.sendSMS(MyDialog.this, phone, keyword, receiver);
                                isSmsSent = true;
                                alertDialog.dismiss();
                                finish();
                            }
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
            }
        });

        alertDialog.show();
        imgCloseAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                finish();
            }
        });
        if (isForced.equals("true")) {
            imgCloseAlert.setVisibility(View.GONE);
        }


    }

    @Override
    protected void onStop() {
        if (receiver != null && isSmsSent)
            unregisterReceiver(receiver);
        super.onStop();
    }
}
