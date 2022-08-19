package com.github.misterchangra.appmonitor.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
    public static String match(String text, Pattern p) {
      return match(text, p, 1);
    }

    public static String match(String text, Pattern pattern, int group) {
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()) {
            return matcher.group(group);

        }
        return null;
    }
}
