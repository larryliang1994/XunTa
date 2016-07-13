package com.larryhowell.xunta.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.igexin.sdk.PushConsts;
import com.larryhowell.xunta.R;
import com.larryhowell.xunta.common.Config;
import com.larryhowell.xunta.ui.BindListActivity;

public class PushDemoReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                byte[] payload = bundle.getByteArray("payload");

                if (payload != null) {
                    String message = new String(payload);

                    sendNotification(context, message);
                }
                break;

            case PushConsts.GET_CLIENTID:
                Config.device_token = bundle.getString("clientid");
                break;
        }
    }

    private void sendNotification(Context context, String message) {
        Intent intent = new Intent(context, BindListActivity.class);
        Config.message = message;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.icon);
        builder.setAutoCancel(true);
        builder.setContentTitle("有人请求与你绑定");
        builder.setContentText("点击处理该消息");
        builder.setVibrate(new long[] {500L, 200L, 200L, 500L});

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(
                        Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
