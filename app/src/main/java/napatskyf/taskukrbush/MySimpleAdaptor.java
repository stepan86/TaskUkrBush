package napatskyf.taskukrbush;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by SERVER 1C 8 hlib on 16.08.2016.
 */
public class MySimpleAdaptor extends SimpleAdapter {

    public MySimpleAdaptor(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }
}
