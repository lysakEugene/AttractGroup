package com.eugene.lysak.attractgrouptest.Models;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeka on 05.04.16.
 */
public class Heroes {


    private JSONArray heroesData;
    private List<Hero> heroes;

    public Heroes(JSONArray timelineData) {
        this.heroesData = timelineData;
    }

    public List<Hero> getHeroes() {
        //ленивая загрузка
        if(heroes == null){
            heroes = new ArrayList<>();
            for (int i = 0; i < heroesData.length(); i++) {
                try {
                    Hero hero = new Hero(heroesData.getJSONObject(i));
                    heroes.add(hero);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return heroes;
    }

}
