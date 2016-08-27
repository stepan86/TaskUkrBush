package napatskyf.taskukrbush;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SERVER 1C 8 hlib on 17.08.2016.
 */
public class JSONfanctions {

    public static JSONArray getJsonArray(String URI) throws IOException, JSONException {
        String line = "";
        JSONArray jsonArray = null;

        URL url;
        url = new URL(URI);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        InputStream inputStream = httpURLConnection.getInputStream();
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        line = reader.readLine();
        jsonArray = new JSONArray(line.toString());
        return jsonArray;
    }

}
