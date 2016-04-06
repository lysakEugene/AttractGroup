package com.eugene.lysak.attractgrouptest.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;


/**
 * Created by zeka on 05.04.16.
 */

public class HeroServiceHelper {

    public static String ACTION_REQUEST_RESULT = "REQUEST_RESULT";
    public static String EXTRA_RESULT_CODE = "EXTRA_RESULT_CODE";

    private static Object lock = new Object();

    private static HeroServiceHelper instance;

    private Context mContext;

    private HeroServiceHelper(Context context){
        this.mContext = context.getApplicationContext();
    }

    public static HeroServiceHelper getInstance(Context ctx){
        synchronized (lock) {
            if(instance == null){
                instance = new HeroServiceHelper(ctx);
            }
        }

        return instance;
    }

    public void getHeroes() {

        ResultReceiver serviceCallback = new ResultReceiver(null){
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                handleGetHeroResponse(resultCode);
            }
        };

        //Запускаем сервис и передаем в него callBack
        Intent intent = new Intent(this.mContext, HeroService.class);
        intent.putExtra(HeroService.SERVICE_CALLBACK, serviceCallback);
        this.mContext.startService(intent);
    }

    //Получаем callBack от процессора и отправляем broadCast
    private void handleGetHeroResponse(int resultCode){
            Intent resultBroadcast = new Intent(ACTION_REQUEST_RESULT);
            resultBroadcast.putExtra(EXTRA_RESULT_CODE, resultCode);
            mContext.sendBroadcast(resultBroadcast);
    }

}
