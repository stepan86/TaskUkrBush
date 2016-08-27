package napatskyf.taskukrbush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SERVER 1C 8 hlib on 18.08.2016.
 */
public class MyBaseAdapterFromPicturePublished extends BaseAdapter {

    ArrayList arrayList;

    HashMap<String, String> data;
    LayoutInflater layoutInflater;

    public MyBaseAdapterFromPicturePublished(Context context, ArrayList arrayList) {
        this.arrayList = arrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public HashMap<String, String> getItem(int i) {
        data = (HashMap<String, String>) arrayList.get(i);
        return data;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_picture, viewGroup, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.textViewImageText);
        ImageView imageView = (ImageView) view.findViewById(R.id.imagPublisher);
        textView.setText((String) getItem(i).get(PicturesPublished.TAG_TEXT_IMAGE));


        new DownloadImage(imageView).execute((String) getItem(i).get(PicturesPublishedFragment.TAG_IMAGE));

        return view;
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImage(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            try {
                URL url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}
