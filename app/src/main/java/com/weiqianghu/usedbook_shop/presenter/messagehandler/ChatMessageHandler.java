package com.weiqianghu.usedbook_shop.presenter.messagehandler;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by weiqianghu on 2016/4/2.
 */
public class ChatMessageHandler extends BmobIMMessageHandler {
    private Context context;

   /* public ChatMessageHandler(Context context) {
        this.context = context;
    }
*/
    @Override
    public void onMessageReceive(final MessageEvent event) {
        //当接收到服务器发来的消息时，此方法被调用

        BmobIMMessage msg = event.getMessage();
        Log.d("message", "收到消息:" + msg.getContent());

       /* UserModel.getInstance().updateUserInfo(event, new UpdateCacheListener() {
            @Override
            public void done(BmobException e) {
                BmobIMMessage msg = event.getMessage();
                //用户自定义的消息类型，其类型值均为0
                if (BmobIMMessageType.getMessageTypeValue(msg.getMsgType()) == 0) {
                    //自行处理自定义消息类型
                    Logger.i(msg.getMsgType() + "," + msg.getContent() + "," + msg.getExtra());
                } else {//SDK内部内部支持的消息类型
                    if (BmobNotificationManager.getInstance(context).isShowNotification()) {
                        //如果需要显示通知栏，可以使用BmobNotificationManager类提供的方法，也可以自己写通知栏显示方法
                    } else {//直接发送消息事件
                        Logger.i("当前处于应用内，发送event");
                        EventBus.getDefault().post(event);
                    }
                }
            }
        });*/
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
