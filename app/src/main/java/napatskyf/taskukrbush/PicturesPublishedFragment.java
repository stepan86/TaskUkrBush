package napatskyf.taskukrbush;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
 * Created by SERVER 1C 8 hlib on 23.08.2016.
 */
public class PicturesPublishedFragment extends Fragment {

    public final static String LOG_TAG = "my_log_main";
    public final static String TAG_IMAGE = "image";
    public final static String TAG_TEXT_IMAGE = "title";
    public final static String TAG_URI = "URI";
    private static String URI = "";
    ProgressDialog progressDialog;
    ListView listViewImagePublisd;
    TextView textViewImegeText;
    ArrayList<HashMap<String,String>> arrayList;
    View rootView;

    public PicturesPublishedFragment(String URI) {
        this.URI = URI;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.pictures_published,container,false);
        listViewImagePublisd = (ListView) rootView.findViewById(R.id.listViewPicturesPublished);
        textViewImegeText    = (TextView) rootView.findViewById(R.id.textViewImageText);
        new DownloadTask().execute();

        listViewImagePublisd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });
        return rootView;
    }

    class DownloadTask extends AsyncTask<Void,Void,ArrayList> {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(rootView.getContext());
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
            BaseAdapter baseAdapter = new MyBaseAdapterFromPicturePublished(rootView.getContext(),arrayList);
            listViewImagePublisd.setAdapter(baseAdapter);
            progressDialog.dismiss();
        }
    }
}
