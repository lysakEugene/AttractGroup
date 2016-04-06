package com.eugene.lysak.attractgrouptest.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by zeka on 06.04.16.
 */
public class ImageLoader extends AsyncTask<String, Integer, Bitmap> {

    private final WeakReference<ImageView> viewReference;
    private String imageName;
    private String folderToSave;

    public ImageLoader( ImageView view,String folderToSave ,String imageName ) {
        this.viewReference = new WeakReference<>( view );
        this.imageName = imageName;
        this.folderToSave = folderToSave;
    }

    @Override
    protected Bitmap doInBackground( String... params ) {

        URL url = null;
        try {
            File file = new File(folderToSave+"/"+imageName);
            if(file.exists())
            {
                return BitmapFactory.decodeFile(folderToSave + "/" + imageName);
            }else{
                url = new URL(params[ 0 ]);
                URLConnection conn = url.openConnection();
                Bitmap image = BitmapFactory.decodeStream(conn.getInputStream());
                image.compress(Bitmap.CompressFormat.JPEG, 75, new FileOutputStream(file));
                return image;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute( Bitmap bitmap ) {
        ImageView imageView = viewReference.get();
        if( imageView != null ) {
            imageView.setImageBitmap( bitmap );
        }
    }
}
