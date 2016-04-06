package com.eugene.lysak.attractgrouptest.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zeka on 05.04.16.
 */
public class Hero {

    private static final String NAME_KEY = "name";
    private static final String IMAGE_KEY = "image";
    private static final String DESCRIPTION_KEY = "description";
    private static final String TIME_KEY = "time";
    private static final String ITEM_ID = "itemId";

    private JSONObject hero;

    public Hero(JSONObject heroData) {
        this.hero = heroData;
    }

    public String getDescription(){
        try {
            return this.hero.getString(DESCRIPTION_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getName() {
        try {
            return this.hero.getString(NAME_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getTime() {
        try {
            return this.hero.getLong(TIME_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getImage() {
        try {
            return this.hero.getString(IMAGE_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getHeroId() {
        try {
            return this.hero.getLong(ITEM_ID);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
