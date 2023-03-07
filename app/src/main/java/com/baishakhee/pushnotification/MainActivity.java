package com.baishakhee.pushnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.baishakhee.pushnotification.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Notification notification;
    NotificationManagerCompat notificationManagerCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        String tittle = binding.editTextName.getText().toString().trim();
        String subject = binding.editTextSubject.getText().toString().trim();
        String body = binding.editTextBody.getText().toString().trim();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("my!993", "Baishakhee", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),"my1993")
                .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
                .setContentTitle(tittle)
                .setContentText(body);
        notification= builder.build();
        notificationManagerCompat=NotificationManagerCompat.from(getApplicationContext());



        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

notificationManagerCompat.notify(1,notification);

            }


        });
    }
}
