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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Höling on 14.11.2016.
 */

public class InstallAPK extends AsyncTask<String, Void, Void> {
    ProgressDialog progressDialog;
    int status = 0;

    private Context context;
    public void setContext(Context context){
        this.context = context;

    }

    public void onPreExecute() {

    }

    @Override
    protected Void doInBackground(String... arg0) {
        try {
            URL url = new URL(arg0[0]);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();


            File sdcard = Environment.getDownloadCacheDirectory();

            File myDir = new File(sdcard,"Android/data/com.example.chris.lebeprototyp02.test/temp");
            myDir.mkdirs();
            File outputFile = new File(myDir, url.getFile());
            if(outputFile.exists()){
                outputFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.flush();
            fos.close();
            is.close();

            /*Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(sdcard,"Android/data/com.example.chris.lebeprototyp02.test/temp/"+url.getFile())), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
            context.startActivity(intent);*/


        } catch (FileNotFoundException fnfe) {
            status = 1;
            Log.e("File", "FileNotFoundException! " + fnfe);
        }

        catch(Exception e)
        {
            Log.e("UpdateAPP", "Exception " + e);
        }
        return null;
    }

    public void onPostExecute(Void unused) {

        if(status == 1)
            Toast.makeText(context,"APK Not Available",Toast.LENGTH_LONG).show();
    }
}