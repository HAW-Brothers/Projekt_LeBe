package lebe.lebeprototyp02.market;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Höling on 14.11.2016.
 * Diese Klasse lädt und installiert ein neues Plugin
 */
public class InstallAPK extends AsyncTask<String, Void, Void> {

    private String pfad="Android/data/com.example.chris.lebeprototyp02/LeBePlugins/";
//    private String pfad="LeBePlugins/";
    private String name;


    ProgressDialog progressDialog;
    int status = 0;

    private Context context;

    /**
     * Setzt den übergebenen Context als ApplicationContext
     * @param context
     */
    public void setContext(Context context){
        this.context = context;

    }

    public void onPreExecute() {

    }

    /**
     * Hier werden die apks heruntergeladen. diese Methode wird automatisch nach {@see onpreExecute()} ausgeführt
     * @param arg0
     * @return
     */
    @Override
    protected Void doInBackground(String... arg0) {

        name=arg0[1];
        File sdcard = Environment.getExternalStorageDirectory();
        try {
//            URL url = new URL(arg0[0]);
            URL url = new URL(arg0[0]);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.connect();
//            c.setDoOutput(true);


            sdcard = Environment.getExternalStorageDirectory().getAbsoluteFile();


            File myDir = new File(sdcard,pfad);
            File file = new File(sdcard, pfad+arg0[1]);
            myDir.mkdirs();
            myDir.getParentFile().mkdirs();
            myDir.setWritable(true);
            file.createNewFile();

            file.setExecutable(true);

            file.setReadable(true);
//            file.createNewFile();

            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = c.getInputStream();

            byte[] buffer = new byte[1024];
            int bufferLength = 0;




            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
            }
            fileOutput.close();
//            this.checkUnknownSourceEnability();
//            this.initiateInstallation();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



//            File myDir = context.getDir("Android/data/com.example.chris.lebeprototyp02", Context.MODE_PRIVATE);
////            File myDir = new File(sdcard,"Android/data/com.example.chris.lebeprototyp02/");
//            System.out.println("neues Verzeichnis soll erstellt werden");
//            myDir.mkdir();
//            myDir.mkdirs();
//            myDir.createNewFile();
//            File outputFile = new File(myDir, arg0[1]);
//            if(outputFile.exists()){
//                outputFile.delete();
//            }
//            FileOutputStream fos = new FileOutputStream(outputFile);
//
//            InputStream is = c.getInputStream();
//
//            byte[] buffer = new byte[1024];
//            int len1 = 0;
//            while ((len1 = is.read(buffer)) != -1) {
//                fos.write(buffer, 0, len1);
//            }
//            fos.flush();
//            fos.close();
//            is.close();
//

//
//
//        } catch (FileNotFoundException fnfe) {
//            status = 1;
//            Log.e("File", "FileNotFoundException! " + fnfe);
//        }
//
//        catch(Exception e)
//        {
//            Log.e("UpdateAPP", "Exception " + e);
//        }
        return null;
    }

    /**
     * wird automatisch nach beendigung von {@see doInBackground()} automatisch aufgerufen und installiert die fertig geladene Datei
     * @param unused
     */
    public void onPostExecute(Void unused) {

        File sdcard = Environment.getExternalStorageDirectory().getAbsoluteFile();


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(sdcard,pfad+name)), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
        context.startActivity(intent);


        if(status == 1)
            Toast.makeText(context,"APK Not Available",Toast.LENGTH_LONG).show();
    }
}
