package com.ara.dontgetticket;

import java.io.*;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * Created by aRa on 30/4/15.
 */

public class DataBaseFile {
    String dataBase="database.csv";
    Context ctx;
    FileOutputStream fos;
    FileInputStream fis;
    File appDir;
    BufferedOutputStream bufferOutData;
    Boolean canWrite;

    public DataBaseFile(Context context){
        this.ctx = context;
        try{
            appDir = new File(String.valueOf(Environment.getExternalStorageDirectory())+"/DontGetTicket");
            if(!appDir.exists()){
                appDir.mkdir();
            }
            fos = new FileOutputStream(new File(appDir,dataBase), true);
            canWrite = true;
        }
        catch (IOException ioe){
            canWrite = false;
        }


    }

    public void write(String inputData){
        if (canWrite){
            try {
                bufferOutData = new BufferedOutputStream(fos);
                bufferOutData.write(inputData.getBytes());
                bufferOutData.close();
            } catch (FileNotFoundException e) {
                Log.e("",""+e.getMessage());
            } catch (IOException ex) {
                Log.e("", "" + ex.getMessage());
            }
        }

        else{
            Log.d("DGT","No se puede escribir en el archivo...");
        }
    }

    public String read(){
        String data="";
        try{
            fis = ctx.openFileInput(dataBase);
            int i=0;
            char c ='a';

            do {
                i = fis.read();
                if (i != '\n'){
                    c = (char)i;
                    data = data + c;
                }
                else{
                    data = data+"\n";
                }
            } while (i > 0);
            data += '\n';
        } catch (Exception e){}
        return data;
    }
}
