package com.eugene.lysak.attractgrouptest.Utils;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.eugene.lysak.attractgrouptest.DataBase.Contract;
import com.eugene.lysak.attractgrouptest.Fragments.HeroDetailFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zeka on 06.04.16.
 */
public class HeroesPagerAdapter extends FragmentStatePagerAdapter {


    private Cursor mCursor;
    private Map<Long,Integer> positionMap;

    public HeroesPagerAdapter(FragmentManager fm, Cursor cursor) {
        super(fm);
        this.mCursor = cursor;
        this.positionMap = new HashMap<>();
    }

    @Override
    public int getCount() {
        if(mCursor == null) {
            return 0;
        } else {
            return mCursor.getCount();
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (mCursor.moveToPosition(position)) {

            return HeroDetailFragment.newInstance(mCursor.getString(mCursor.getColumnIndex(Contract.Heroes.NAME)),
                    mCursor.getString(mCursor.getColumnIndex(Contract.Heroes.DESCRIPTION)),
                    mCursor.getString(mCursor.getColumnIndex(Contract.Heroes.IMAGE)),
                    mCursor.getLong(mCursor.getColumnIndex(Contract.Heroes.TIME)));
        }
        return null;
    }

    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    //Возвращает позицию в ViewPager через id героя
    public int getPositionById(long id)
    {
        return positionMap.get(id);
    }


    //Создаем карту позиций ViewPager по id героя
    public void initPositionMap()
    {
        if (mCursor.moveToFirst()) {
            do {
                positionMap.put(mCursor.getLong(mCursor.getColumnIndex(Contract.Heroes._ID)),mCursor.getPosition());
            } while (mCursor.moveToNext());
        }
    }

}
