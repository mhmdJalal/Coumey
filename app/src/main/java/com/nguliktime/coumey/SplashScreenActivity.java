package com.nguliktime.coumey;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nguliktime.coumey.fitur.vidcon.VidconActivity;
import com.nguliktime.coumey.helper.RealmHelper;
import com.nguliktime.coumey.model.VidconModel;

import java.text.DateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SplashScreenActivity extends AppCompatActivity {
    String currentDateString;
    Realm realm;
    RealmHelper realmHelper;
    VidconModel vidconModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);

        Calendar c = Calendar.getInstance();
        c.get(Calendar.YEAR);
        c.get(Calendar.MONTH);
        c.get(Calendar.DAY_OF_MONTH);
        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        vidconModel = new VidconModel();

        vidconModel = realmHelper.getVidconDate(currentDateString, "Belum Selesai");

        if (vidconModel != null){
            notificationVidcon(vidconModel.getMapel(), vidconModel.getTanggal(), vidconModel.getMateri());
        }
    }

    private void notificationVidcon(String mapel, String tanggal, String materi){
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, VidconActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        @SuppressLint("ResourceAsColor") NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.idea2)
                        .setAutoCancel(true)
                        .setContentTitle("Video Conference " + mapel)
                        .setContentText(tanggal + " : " + materi)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setLights(R.color.colorPrimaryDark, 3000, 3000)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, VidconActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);

        // mId allows you to update the notification later on.
        notificationManager.notify(1, mBuilder.build());
    }
}
