package com.example.calculator;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class DataServer{

    private static Integer lines=0;
    public String read(Context context){
        try {
            FileInputStream fileInputStream = context.openFileInput("data.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
                lines++;
            }
            fileInputStream.close();
            return stringBuilder.toString();
        }
        catch (Exception e){
            return "";
        }
    }
    protected boolean write(String data, Context context){
        try {
            data+= "\n";
            FileOutputStream fileOutputStream = context.openFileOutput("data.txt", Context.MODE_APPEND);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
            lines++;
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    protected Integer getLines(Context context){
        read(context);
        return lines;
    }
    protected boolean deleteLine(Context context,String data){
        try {
            FileInputStream fileInputStream = context.openFileInput("data.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if(!line.equals(data))
                    stringBuilder.append(line).append("\n");
            }
            fileInputStream.close();
            FileOutputStream fileOutputStream = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(stringBuilder.toString().getBytes());
            fileOutputStream.close();
            lines--;
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    protected boolean clear(Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
            fileOutputStream.write("".getBytes());
            fileOutputStream.close();
            lines=0;
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
