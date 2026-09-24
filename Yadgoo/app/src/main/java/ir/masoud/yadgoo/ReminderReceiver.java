package ir.masoud.yadgoo;
import android.app.*;import android.content.*;
public class ReminderReceiver extends android.content.BroadcastReceiver {
 public void onReceive(Context c, Intent i){String channel="reminders"; NotificationManager m=c.getSystemService(NotificationManager.class);m.createNotificationChannel(new NotificationChannel(channel,"یادآوری‌ها",NotificationManager.IMPORTANCE_HIGH)); Notification.Builder b=new Notification.Builder(c,channel).setSmallIcon(android.R.drawable.ic_lock_idle_alarm).setContentTitle("یادگو: وقت انجام کار").setContentText(i.getStringExtra("title")).setAutoCancel(true); m.notify(i.getIntExtra("id",0),b.build());}
}
