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



public class MessageBroker extends Service {        // MessageBroker ist ein Service -> Activity, die im Hintergrund läuft

    static final int SET_DATA = 0;                  // Konstanten um eingehende Nachrichten zu
    static final int GET_DATA = 1;                  // unterscheiden.
    public Messenger brokerMessenger;
    static private Map<String, Bundle> messages = new HashMap<>();   // HashMap zum Speichern der Nachrichten

    class MessageHandler extends Handler {


        // in <Kategorie der Nachricht, Datenpaket (Bundle)>
        // wahrscheinlich nicht optimal, kann sich noch ändern
        @Override
        public void handleMessage(Message msg) {          // Methode die eingehende Nchrichten abarbeitet

            switch (msg.what) {                                         // switch-case für die Art der Nachricht und entsprechende Vorgehensweise

                case SET_DATA:                                          // Die erhaltene Nchricht möchte Daten speichern
                    Bundle setBundle = msg.getData();                   // Die Daten werden als Bundle übertragen und extrahiert
                    String setCategory = setBundle.getString("TAG");    // Die Kategorie der zu speichernden Daten
                    messages.put(setCategory, setBundle);               // Speichern der Daten mit der Kategorie als key und den Daten als Value
                    break;
                case GET_DATA:                                          // Die erhaltene Nchricht fordert bestimmte Daten an
                    Bundle getBundle = msg.getData();                   // s.o.
                    String getCategory = getBundle.getString("TAG");    // Welche Kategorie von Daten sind gefordert?
                    Bundle answer;
                    if (messages.get(getCategory) != null) {
                        answer = messages.get(getCategory);          // Erhalten der Daten der geforderten kategorie aus der HashMap
                    } else {
                        break;
                    }
                    Message replyMessage = Message.obtain();            // Antwortnchricht erstellen
                    replyMessage.setData(answer);                       // Antwort-Bundle in Nachricht einfügen
                    replyMessage.what = 0;                              // Antwortnachricht als solche kennzeichnen, siehe Connector Klasse in Plugins
                    try {
                        replyMessage.replyTo = brokerMessenger;         // brokerMessenger als Sender eintragen
                        msg.replyTo.send(replyMessage);                 // Antwortnachricht an den Sender der Ursprungsnachricht schicken
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:                                                // falls obige cases fehlschlagen
                    super.handleMessage(msg);                           // wird nicht richtig funktionieren, aber Standardpraxis
                    break;
            }

        }

    }

    static public Bundle getFromMessageMap(String category) {
        if (messages.get(category) != null) {
            return messages.get(category);
        } else {
            return null;
        }
    }



    @Override
    public void onCreate(){
        brokerMessenger = new Messenger(new MessageHandler());
    }  // initialisierung des MessageHandlers

    @Override
    public IBinder onBind(Intent intent) {
        return brokerMessenger.getBinder();
    }  // Methode zum Binden von Messengern
}