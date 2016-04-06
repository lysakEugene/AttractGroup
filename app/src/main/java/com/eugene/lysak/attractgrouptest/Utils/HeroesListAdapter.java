package com.eugene.lysak.attractgrouptest.Utils;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eugene.lysak.attractgrouptest.DataBase.Contract;
import com.eugene.lysak.attractgrouptest.R;

import java.util.Date;

/**
 * Created by zeka on 05.04.16.
 */
public class HeroesListAdapter extends CursorAdapter {


    private String folderToSave;

    private    static  class   ViewHolder  {
        TextView nameHero;
        TextView timeHero;
        ImageView imageHero;
        int name;
        int time;
        int image;

    }

    public HeroesListAdapter(Context context, Cursor c) {
        super(context, c, 0);
        this.folderToSave = context.getFilesDir().toString();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View   view    =   LayoutInflater.from(context).inflate(R.layout.list_item,null);
        ViewHolder holder  =   new ViewHolder();
        holder.nameHero = (TextView)view.findViewById(R.id.name_hero);
        holder.imageHero = (ImageView)view.findViewById(R.id.img_hero);
        holder.timeHero = (TextView)view.findViewById(R.id.time);
        holder.name = cursor.getColumnIndex(Contract.Heroes.NAME);
        holder.image = cursor.getColumnIndex(Contract.Heroes.IMAGE);
        holder.time = cursor.getColumnIndex(Contract.Heroes.TIME);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        final ViewHolder holder  =   (ViewHolder)view.getTag();

        holder.nameHero.setText(cursor.getString(holder.name));
        holder.timeHero.setText(Utils.getDateFormat().format(new Date(cursor.getLong(holder.time))));

        String url = cursor.getString(holder.image);
        String fileName = Utils.getNameFileFromUrl(url);

        new ImageLoader( holder.imageHero,folderToSave,fileName ).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);

    }
}
