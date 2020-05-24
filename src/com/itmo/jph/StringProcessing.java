package com.itmo.jph;

import org.jetbrains.annotations.NotNull;

public class StringProcessing {
    public String toCamelCase(@NotNull String s) {
        //set user id ->setUserId
        String strs[] = s.trim().split("\\s");
        String result = "";
        result += strs[0].toLowerCase();
        for (int i = 1; i < strs.length; i++) {
            if (strs[i] == "") continue;
            StringBuilder stringBuilder = new StringBuilder(strs[i].toLowerCase());
            stringBuilder.setCharAt(0, toUpper(stringBuilder.charAt(0)));
            result += stringBuilder.toString();
        }
        return result;
    }

     public String toCommonCase(String s) {
        //setUserId -> set user id
        int i = 0,start=0,end=0;
        String result = "";
        while (i < s.length()) {
            if(Character.isUpperCase(s.charAt(i)))
            {
                end=i;
                result+=(s.substring(start,end).toLowerCase()+" ");
                start=end;
            }
            i++;
        }
        result+=(s.substring(start).toLowerCase()+" ");
        return result.trim();
    }

    public char toUpper(char c) {
        return Character.isUpperCase(c) ? c : Character.toUpperCase(c);
    }

}
