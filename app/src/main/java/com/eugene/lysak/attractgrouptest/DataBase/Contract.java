package com.eugene.lysak.attractgrouptest.DataBase;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by zeka on 05.04.16.
 */
public class Contract {

    public static final String AUTHORITY = "com.eugene.lysak.attractgrouptest.heroes";

    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public interface HeroColumns {
        String NAME = "name";
        String IMAGE = "image";
        String DESCRIPTION = "description";
        String TIME = "time";
    }

    public static final class Heroes implements BaseColumns, HeroColumns {
        public static final String CONTENT_PATH = "heroes";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + CONTENT_PATH;
    }

}
