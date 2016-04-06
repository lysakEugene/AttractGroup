package com.eugene.lysak.attractgrouptest.Utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by zeka on 06.04.16.
 */
public class Utils {

    //Вытаскиваем из url имя файла
    public static String getNameFileFromUrl(String url)
    {
        return url.substring(url.lastIndexOf('/') + 1, url.length());
    }

    public static SimpleDateFormat getDateFormat()
    {
        return new SimpleDateFormat("dd-MMMM-yyyy HH:mm", new Locale("ru"));
    }
}
