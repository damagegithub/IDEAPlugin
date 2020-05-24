package com.itmo.jph;

import com.intellij.openapi.vcs.history.VcsRevisionNumber;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {
    public String translate(String langTo,
                            String word) throws Exception {
        //google api
        String url = "https://translate.googleapis.com/translate_a/single?" +
                "client=gtx&" +
                "sl=" + "auto" +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //get data from JSON
        //处理数据
        Pattern pattern = Pattern.compile("\"");
        Matcher matcher = pattern.matcher(response.toString());
        matcher.find();
        int start = matcher.start()+1;
        matcher.find();
        int end=matcher.start();
        String s=new String( response.toString().substring(start,end).getBytes("utf-8") , "utf-8");
        return s;

    }

}
