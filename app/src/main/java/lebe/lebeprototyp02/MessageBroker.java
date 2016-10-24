package lebe.lebeprototyp02;



import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.util.HashMap;
import java.util.Map;



public class MessageBroker extends Service {

    static final int SET_DATA = 0;
    static final int GET_DATA = 1;
    public Messenger brokerMessenger;

    class MessageHandler extends Handler {

        Map<String, String> messages = new HashMap<>();

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case SET_DATA:
                    Bundle setBundle = msg.getData();
                    String[] values = setBundle.getStringArray("key");
                    assert values != null;
                    messages.put(values[0], values[1]);
                    break;
                case GET_DATA:
                    Bundle getBundle = msg.getData();
                    String category = getBundle.getString("key");
                    String answer = messages.get(category);
                    Bundle replyBundle = new Bundle();
                    replyBundle.putString("reply", answer);

                    Message replyMessage = Message.obtain();
                    replyMessage.setData(replyBundle);
                    replyMessage.what = 0;
                    try {
                        replyMessage.replyTo = brokerMessenger;
                        msg.replyTo.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }

    }



    @Override
    public void onCreate(){
        brokerMessenger = new Messenger(new MessageHandler());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return brokerMessenger.getBinder();
    }
}