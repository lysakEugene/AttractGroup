package com.eugene.lysak.attractgrouptest;

import android.app.Application;

import com.eugene.lysak.attractgrouptest.Services.HeroServiceHelper;
import com.eugene.lysak.attractgrouptest.Utils.StatusInternetConnection;

/**
 * Created by zeka on 06.04.16.
 */
public class AttractAndroid extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * При инициализации загружаем контент с помощью паттерна A - (Google IO 2010 Virgil Dobjanschi)
         *
         * При необходимости послать REST-запрос Activity создает Service,
         * Service асинхронно посылает запросы к REST-серверу и сохраняет результаты в Content Provider.
         * Activity получает уведомление о готовности данных и считывает результаты из Content Provider.
         */
        HeroServiceHelper mHeroServiceHelper = HeroServiceHelper.getInstance(this);
        StatusInternetConnection internetConnection = StatusInternetConnection.getInstance(this);
        if (internetConnection.MobileConnection()||internetConnection.WifiConnection()) {
            mHeroServiceHelper.getHeroes();
        }
    }
}
