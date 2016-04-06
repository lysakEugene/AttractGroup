package com.eugene.lysak.attractgrouptest.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eugene.lysak.attractgrouptest.R;
import com.eugene.lysak.attractgrouptest.Utils.ImageLoader;
import com.eugene.lysak.attractgrouptest.Utils.Utils;

import java.util.Date;


public class HeroDetailFragment extends Fragment {

    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String TIME = "time";

    private String mName;
    private String mDescription;
    private String mImage;
    private long mTime;
    private String folderToSave;


    public HeroDetailFragment() {
    }

    /**
     *
     * @param mName имя героя
     * @param mDescription описание
     * @param mImage ссылка на фото героя
     * @param mTime время
     * @return
     */
    public static HeroDetailFragment newInstance(String mName, String mDescription,String mImage,long mTime) {
        HeroDetailFragment fragment = new HeroDetailFragment();
        Bundle args = new Bundle();
        args.putString(NAME, mName);
        args.putString(DESCRIPTION, mDescription);
        args.putString(IMAGE, mImage);
        args.putLong(TIME, mTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(NAME);
            mDescription = getArguments().getString(DESCRIPTION);
            mImage = getArguments().getString(IMAGE);
            mTime = getArguments().getLong(TIME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hero_detail, container, false);

        ImageView photo = (ImageView)rootView.findViewById(R.id.photo_hero);
        TextView name = (TextView)rootView.findViewById(R.id.name_hero);
        TextView description = (TextView)rootView.findViewById(R.id.description_hero);
        TextView time = (TextView)rootView.findViewById(R.id.time_hero);

        name.setText(mName);
        description.setText(mDescription);
        time.setText(Utils.getDateFormat().format(new Date(mTime)));

        //Получаем директорию куда необходимо сохранять фотоки
        folderToSave = getActivity().getFilesDir().toString();
        //Получаем имя файла из ссылки
        String fileName = Utils.getNameFileFromUrl(mImage);
        //Загружаем фото в ImageView
        new ImageLoader( photo,folderToSave,fileName ).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mImage);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Прячем кнопку поиска
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        myActionMenuItem.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

}
