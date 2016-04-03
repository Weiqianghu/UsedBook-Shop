package com.weiqianghu.usedbook_shop.presenter.messagehandler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.view.activity.MainActivity;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.notification.BmobNotificationManager;

/**
 * Created by weiqianghu on 2016/4/2.
 */
public class ChatMessageHandler extends BmobIMMessageHandler {
    private Context context;

    public ChatMessageHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onMessageReceive(final MessageEvent event) {
        //当接收到服务器发来的消息时，此方法被调用

        BmobIMMessage msg = event.getMessage();
        Log.d("message", "收到消息:" + msg.getContent());

        if (BmobIMMessageType.getMessageTypeValue(msg.getMsgType()) == 0) {

        } else {//SDK内部内部支持的消息类型
            if (BmobNotificationManager.getInstance(context).isShowNotification()) {
                //如果需要显示通知栏，可以使用BmobNotificationManager类提供的方法，也可以自己写通知栏显示方法
                 Intent pendingIntent = new Intent(context, MainActivity.class);

                BmobIMMessage message = event.getMessage();
                BmobIMUserInfo info = event.getFromUserInfo();
                //这里可以是应用图标，也可以将聊天头像转成bitmap
                Bitmap largetIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
                BmobNotificationManager.getInstance(context).showNotification(largetIcon, info.getName(), message.getContent(),
                        "您有一条新消息", pendingIntent);

              /*  BmobIMMessage message = event.getMessage();
                BmobIMUserInfo info = event.getFromUserInfo();

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                mBuilder.setContentTitle(info.getName())//设置通知栏标题
                        .setContentText(message.getContent()) //设置通知栏显示内容
                        .setTicker("您有一条新消息") //通知首次出现在通知栏，带上升动画效果的
                        .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                        .setSmallIcon(R.mipmap.logo)//设置通知小ICON
                        .setAutoCancel(true);

                //构建一个Intent
                Intent intent = new Intent(context, MainActivity.class);
                //封装一个Intent
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);// 设置通知主题的意图
                mBuilder.setContentIntent(pendingIntent);
                notificationManager.notify(0, mBuilder.build());*/


            } else {//直接发送消息事件


            }
        }


    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用
        Map<String, List<MessageEvent>> map = event.getEventMap();
        Log.d("message", "离线消息属于" + map.size() + "个用户");
       /* for (Map.Entry<String, List<MessageEvent>> entry : map.entrySet()) {
            List<MessageEvent> list = entry.getValue();
            //挨个检测离线用户信息是否需要更新
            UserModel.getInstance().updateUserInfo(list.get(0), new UpdateCacheListener() {
                @Override
                public void done(BmobException e) {
                    EventBus.getDefault().post(event);
                }
            });
        }*/
    }
}
