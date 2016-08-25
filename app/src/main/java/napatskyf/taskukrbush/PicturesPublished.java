package napatskyf.taskukrbush;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SERVER 1C 8 hlib on 17.08.2016.
 */
public class PicturesPublished extends MainActivity {
    public final static String LOG_TAG = "my_log_main";
    public final static String TAG_IMAGE = "image";
    public final static String TAG_TEXT_IMAGE = "title";

    public final static String TAG_URI = "URI";


    private static String URI = "";
    ProgressDialog progressDialog;
    ListView listViewImagePublisd;
    TextView textViewImegeText;
    ArrayList<HashMap<String,String>> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pictures_published);
        URI =  getIntent().getExtras().getString("URI");
        DownloadTask downloadTask = new DownloadTask();
        listViewImagePublisd = (ListView) findViewById(R.id.listViewPicturesPublished);
        textViewImegeText    = (TextView) findViewById(R.id.textViewImageText);
        downloadTask.execute();
    }

    class DownloadTask extends AsyncTask<Void,Void,ArrayList> {
        @Override
        protected void onPreExecute() {
           progressDialog = new ProgressDialog(PicturesPublished.this);
            progressDialog.setMessage("Please wiat ... ");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList doInBackground(Void... voids) {
            arrayList = new ArrayList<>();
            JSONArray jsonArray = null;
            JSONObject jsonObject;
            try {
                jsonArray = JSONfanctions.getJsonArray(URI);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < jsonArray.length() ; i++) {
                try {
                    HashMap<String,String> hashMap = new HashMap<>();
                    jsonObject = jsonArray.getJSONObject(i);
                    String image = jsonObject.getString(TAG_IMAGE);
                    String title = jsonObject.getString(TAG_TEXT_IMAGE);
                    hashMap.put(TAG_IMAGE,image);
                    hashMap.put(TAG_TEXT_IMAGE,title);
                    arrayList.add(hashMap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);
            String[] from = {TAG_IMAGE,TAG_TEXT_IMAGE};
            int[] to      = {R.id.imagPublisher,R.id.textViewImageText};
            BaseAdapter baseAdapter = new MyBaseAdapterFromPicturePublished(PicturesPublished.this,arrayList);
            listViewImagePublisd.setAdapter(baseAdapter);
            progressDialog.dismiss();
        }
    }
}
