package com.example.homework4.bean;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SaveInfo {
    public static boolean SaveInformation(Context context, String username, String password1, String password2) {
        try {
            FileOutputStream fos = context.openFileOutput("data.txt", Context.MODE_APPEND);
            fos.write(("用户名:" + username + " 密码:" ).getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Map<String, String> getSaveInformation(Context context) {
        try {
            FileInputStream fis = context.openFileInput("data.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String str = br.readLine();
            String[] infos = str.split("用户名:"+"密码:");
            Map<String, String> map = new HashMap<String, String>();
            map.put("username", infos[0]);
            map.put("password", infos[1]);
            fis.close();
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
