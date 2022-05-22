package com.zene.tmtpawssyetm.UI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.SystemClock;
import android.widget.Toast;

import java.util.Calendar;

public class backgroundProcess extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Ringtone ringtone = RingtoneManager.getRingtone(context,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        ringtone.play();

        Toast.makeText(context, "Dog temperature is high: \n" + Calendar.getInstance().getTime().toString(), Toast.LENGTH_SHORT).show();

        SystemClock.sleep(500);

        ringtone.stop();
    }
}
