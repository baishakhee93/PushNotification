package com.baishakhee.pushnotification;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import com.baishakhee.pushnotification.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Notification notification;
    NotificationManagerCompat notificationManagerCompat;
    String file_name_path="notification";
    String GROUP_KEY_WORK_MESSAAGE = "com.generatepdf.com.WORK_MESSAGE";
    int PERMISSION_ALL = 5;
    String[] PERMISSIONS = {

            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder1.build());
        if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
        }


        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tittle = binding.editTextName.getText().toString().trim();
                String subject = binding.editTextSubject.getText().toString().trim();
                String body = binding.editTextBody.getText().toString().trim();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    addNotification(tittle,subject,body) ;
                }
            }


        });
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addNotification(String tittle,String subject, String body) {
        String pdfPath= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath +"/"+ tittle);
     //   Drawable d = getDrawable(R.drawable.ic_baseline_circle_notifications_24);
      //  Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        if (!file.exists()) {
            file.mkdir();
        }

        NotificationChannel channel=new NotificationChannel("shakhee",
                "baishakhee",
                NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        //Creating the notification object
        NotificationCompat.Builder notification=new NotificationCompat.Builder(this,"shakhee");
        int PROGRESS_MAX = 100;
        int PROGRESS_CURRENT = 0;
        notification.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
        notification.setContentTitle(tittle);
        notification.setContentText(subject+"\n"+body);
        notification.setSmallIcon(R.drawable.ic_baseline_circle_notifications_24);
        notification.setPriority(NotificationCompat.PRIORITY_HIGH);
        notification.setAutoCancel(true);
        notification.setColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_200));
        notification.setCategory(Notification.CATEGORY_REMINDER);
        notification.setGroup(tittle);
        notification.setGroupSummary(true);

        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new
                    NotificationChannel("Shakhee", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(false);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notification.setChannelId("Shakhee");
            assert manager != null;
            manager.createNotificationChannel(notificationChannel);
        }
        assert manager != null;

        manager.notify(( int ) System. currentTimeMillis () ,
                notification.build()) ;

    }


}
