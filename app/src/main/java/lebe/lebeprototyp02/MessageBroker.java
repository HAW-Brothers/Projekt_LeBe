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

    class MessageHandler extends Handler {

        Map<String, String> messages = new HashMap<>();   // HashMap zum Speichern der Nachrichten
        // in <Kategorie der Nachricht, Nachricht>
        // wahrscheinlich nicht optimal, kann sich noch ändern
        @Override
        public void handleMessage(Message msg) {          // Methode die eingehende Nchrichten abarbeitet

            switch (msg.what) {                                         // switch-case für die Art der Nachricht und entsprechende Vorgehensweise

                case SET_DATA:                                          // Die erhaltene Nchricht möchte Daten speichern
                    Bundle setBundle = msg.getData();                   // Die Daten werden als Bundle übertragen und extrahiert
                    String[] values = setBundle.getStringArray("key");  // Holt sich die eigentlichen Daten, evtl mehrere -> Array, diese Daten sind mit "key" gekennzeichnet
                    assert values != null;                              // Sicherstellen dass Daten vorhanden sind
                    messages.put(values[0], values[1]);                 // Speichern der Daten mit der Kategorie als key und den Daten als Value
                    break;
                case GET_DATA:                                          // Die erhaltene Nchricht fordert bestimmte Daten an
                    Bundle getBundle = msg.getData();                   // s.o.
                    String category = getBundle.getString("key");       // Welche Kategorie von Daten sind gefordert?
                    String answer = messages.get(category);             // Erhalten der Daten der geforderten kategorie aus der HashMap
                    Bundle replyBundle = new Bundle();                  // Erstellen eines neuen Bundles für die Antwort
                    replyBundle.putString("reply", answer);             // Antwort ins Bundle einfügen und mit "reply" kennzeichnen
                    Message replyMessage = Message.obtain();            // Antwortnchricht erstellen
                    replyMessage.setData(replyBundle);                  // Antwort-Bundle in Nachricht einfügen
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



    @Override
    public void onCreate(){
        brokerMessenger = new Messenger(new MessageHandler());
    }  // initialisierung des MessageHandlers

    @Override
    public IBinder onBind(Intent intent) {
        return brokerMessenger.getBinder();
    }  // Methode zum Binden von Messengern
}