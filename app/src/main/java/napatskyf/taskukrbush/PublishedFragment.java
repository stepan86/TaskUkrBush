package napatskyf.taskukrbush;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by SERVER 1C 8 hlib on 16.08.2016.
 */
public class PublishedFragment extends Fragment {
    public final static String LOG_TAG = "my_log_main";
    public final static String TAG_ID = "id";
    public final static String TAG_STATUS = "status";
    public final static String TAG_PUB_DATE = "pub_date";
    public final static String TAG_AUTHOR = "author";
    public final static String TAG_AUTHOR_ID = "author_id";
    public final static String TAG_TEXT = "text";
    public final static String TAG_RATING = "rating";
    public final static String TAG_URI = "URI";

    ListView listView;
    ProgressDialog pDialog;
    String URI;
    View rootView;

    public PublishedFragment(String uri) {
        this.URI = uri;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.published, container, false);

        new ParseTask().execute();


        listView = ((ListView) rootView.findViewById(R.id.listLiewPublisher));
//        listView.setAdapter(new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_list_item_1,new String[]{"OK"}));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(rootView.getContext(), "OK", Toast.LENGTH_SHORT).show();
                String textPublished = ((TextView) view.findViewById(R.id.textPublished)).getText().toString();
                String pub_date = ((TextView) view.findViewById(R.id.pub_date)).getText().toString();
                String id = ((TextView) view.findViewById(R.id.id)).getText().toString();
                Intent intent = new Intent(rootView.getContext(), SingleActivity.class);
                intent.putExtra(TAG_URI, URI);
                intent.putExtra(TAG_TEXT, textPublished);
                intent.putExtra(TAG_PUB_DATE, pub_date);
                intent.putExtra(TAG_ID, id);
                startActivity(intent);
            }
        });


        //getActivity().setTitle(catNameTitle)
        return rootView;
    }

    public class ParseTask extends AsyncTask<Void, Void, String> {
        ArrayList<HashMap<String, String>> publishedList;
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        String resultJson = null;


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(rootView.getContext());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String stringJson) {
            super.onPostExecute(stringJson);
            Log.d(LOG_TAG, stringJson);
            JSONArray dataJsonArray = null;
            publishedList = new ArrayList<HashMap<String, String>>();
            try {
                dataJsonArray = new JSONArray(stringJson);
                Log.d(LOG_TAG, String.valueOf(dataJsonArray.length()));
                for (int i = 0; i < dataJsonArray.length(); i++) {
                    JSONObject jsonObject = dataJsonArray.getJSONObject(i);

                    String id = jsonObject.getString(TAG_ID);
                    long pub_date = jsonObject.getLong(TAG_PUB_DATE);
                    String author = jsonObject.getString(TAG_AUTHOR);
                    String author_id = jsonObject.getString(TAG_AUTHOR_ID);
                    String rating = jsonObject.getString(TAG_RATING);
                    String status = jsonObject.getString(TAG_STATUS);
                    String text = jsonObject.getString(TAG_TEXT);

                    HashMap<String, String> listHash = new HashMap<>();

                    listHash.put(TAG_ID, id);
                    listHash.put(TAG_PUB_DATE, prepareData(pub_date));
                    listHash.put(TAG_AUTHOR, author);
                    listHash.put(TAG_AUTHOR_ID, author_id);
                    listHash.put(TAG_RATING, rating);
                    listHash.put(TAG_STATUS, status);
                    listHash.put(TAG_TEXT, text);

                    try {
                        publishedList.add(listHash);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(LOG_TAG, e.toString());
                    }
                    //Log.d(LOG_TAG,jsonObject.getString("name"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            listView = (ListView) rootView.findViewById(R.id.listLiewPublisher);
            String[] from = {TAG_TEXT, TAG_ID, TAG_PUB_DATE};
            int[] to = {R.id.textPublished, R.id.id, R.id.pub_date};

            MySimpleAdaptor mySimpleAdaptor = new MySimpleAdaptor(rootView.getContext(), publishedList, R.layout.list_item, from, to);
//
// ListAdapter listAdapter = new SimpleAdapter(Published.this, publishedList, R.layout.list_item, new String[]{TAG_TEXT, TAG_ID, TAG_PUB_DATE}, new int[]{R.id.textPublished, R.id.id, R.id.pub_date});

            listView.setAdapter(mySimpleAdaptor);


            if (pDialog.isShowing())
                pDialog.dismiss();
        }

        String prepareData(long add_date) {
            Date date = new Date(add_date * 1000);
            SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
            String dateText = df2.format(date);
            return dateText;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(URI);
                //URL url = new URL("http://androiddocs.ru/api/friends.json");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                resultJson = buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }


    }

}
