package com.eugene.lysak.attractgrouptest.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eugene.lysak.attractgrouptest.DataBase.Contract;
import com.eugene.lysak.attractgrouptest.R;
import com.eugene.lysak.attractgrouptest.Utils.HeroesPagerAdapter;


public class HeroPagerFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String ID_HERO = "id_hero";
    private static final String SAVE_POSITION = "save_position";

    private long mIdHero;
    private HeroesPagerAdapter adapter;
    private ViewPager viewPager;
    private int currentPosition = -1;


    public HeroPagerFragment() {
    }

    //Передаем id героя для отображения нужной страницы на ViewPager
    public static HeroPagerFragment newInstance(long idHero) {
        HeroPagerFragment fragment = new HeroPagerFragment();
        Bundle args = new Bundle();
        args.putLong(ID_HERO, idHero);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIdHero = getArguments().getLong(ID_HERO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hero_pager, container, false);
            viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
            adapter = new HeroesPagerAdapter(getFragmentManager(), null);
            viewPager.setAdapter(adapter);

            getLoaderManager().initLoader(1, null, this);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        setVisibleFAB(View.GONE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                Contract.Heroes.CONTENT_URI,
                null,
                null,
                null,
                Contract.Heroes.TIME
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(!data.isClosed()) {
            adapter.swapCursor(data);
            adapter.initPositionMap();

            //Ставим нужную страницу ViewPager через id записи или сохраненной позиции
            if(currentPosition<0)
                viewPager.setCurrentItem(adapter.getPositionById(mIdHero));
            else
                viewPager.setCurrentItem(currentPosition);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    protected void onReceiveBackPressed() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        //Сохраняем позицию ViewPager при смене ориентации
        outState.putInt(SAVE_POSITION,viewPager.getCurrentItem());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(SAVE_POSITION);
        }
    }
}
