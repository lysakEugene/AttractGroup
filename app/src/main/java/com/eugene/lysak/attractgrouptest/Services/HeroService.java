package com.eugene.lysak.attractgrouptest.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;


public class HeroService extends IntentService {



    public static final String SERVICE_CALLBACK = "SERVICE_CALLBACK";

    private ResultReceiver mCallback;

    public HeroService() {
        super("HeroService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mCallback = intent.getParcelableExtra(HeroService.SERVICE_CALLBACK);

                    //Создаем процессор и передаем в него callBack
                    HeroProcessor processor = new HeroProcessor(getApplicationContext());
                    processor.getHero(makeHeroProcessorCallback());

    }

    private HeroProcessorCallback makeHeroProcessorCallback() {
        HeroProcessorCallback callback = new HeroProcessorCallback() {

            @Override
            public void send(int resultCode) {
                if (mCallback != null) {
                    mCallback.send(resultCode, null);
                }
            }
        };
        return callback;
    }

}
