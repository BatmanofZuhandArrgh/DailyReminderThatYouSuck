package com.example.dailyreminderthatyousuck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;


public class AlarmReceiver extends BroadcastReceiver{
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onReceive(Context context, Intent intent) {
        // we will use vibrator first
        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(4000);

//
//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if (alarmUri == null) {
//            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        }
//
//        // setting default ringtone
//        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
//
//        // play ringtone
//        ringtone.play();

//        Toast.makeText( context, "startService", Toast.LENGTH_SHORT).show();
        Intent serviceIntent = new Intent(context, Text2Speech.class);
        context.startForegroundService(serviceIntent);

//        Toast.makeText(context, "doneService", Toast.LENGTH_SHORT).show();

    }

}