package com.eugene.lysak.attractgrouptest.Services;

import android.content.ContentValues;
import android.content.Context;

import com.eugene.lysak.attractgrouptest.DataBase.Contract;
import com.eugene.lysak.attractgrouptest.Models.Hero;
import com.eugene.lysak.attractgrouptest.Models.Heroes;
import com.eugene.lysak.attractgrouptest.Rest.GetHeroesRestMethod;
import com.eugene.lysak.attractgrouptest.Rest.RestMethodResult;

import java.util.List;


/**
 * Created by zeka on 05.04.16.
 */
public class HeroProcessor {

    private Context mContext;


    public HeroProcessor(Context context) {
        mContext = context;
    }


    void getHero(HeroProcessorCallback callback) {
        //Получаем данные с сервера
        RestMethodResult result = new GetHeroesRestMethod().execute();
        //Обновляем базу
        updateContentProvider(result);
        //Возвращаем статус код
        callback.send(result.getStatusCode());

    }

    private void updateContentProvider(RestMethodResult result) {

        Heroes resultResource = result.getResource();
        if(resultResource==null)return;

        List<Hero> heroes = resultResource.getHeroes();
        mContext.getContentResolver().delete(Contract.Heroes.CONTENT_URI, null, null);

        ContentValues values;
        for (Hero hero : heroes) {
            values = new ContentValues();
            values.put(Contract.Heroes.NAME,hero.getName());
            values.put(Contract.Heroes.IMAGE,hero.getImage());
            values.put(Contract.Heroes.DESCRIPTION,hero.getDescription());
            values.put(Contract.Heroes.TIME,hero.getTime());
            values.put(Contract.Heroes._ID,hero.getHeroId());
            mContext.getContentResolver().insert(Contract.Heroes.CONTENT_URI, values);
        }

    }

}
