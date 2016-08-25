package napatskyf.taskukrbush;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by SERVER 1C 8 hlib on 16.08.2016.
 */
public class SingleActivity extends MainActivity {
   String URI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single);
        TextView textPublished  = (TextView) findViewById(R.id.textPublished);
        TextView id             = (TextView) findViewById(R.id.id);
        TextView pub_date       =  (TextView) findViewById(R.id.pub_date);
        URI = (String) getIntent().getExtras().getString(Published.TAG_URI);
        textPublished.setText((CharSequence) getIntent().getExtras().getString(Published.TAG_TEXT));
        id.setText((CharSequence) getIntent().getExtras().getString(Published.TAG_PUB_DATE));
        pub_date.setText((CharSequence) getIntent().getExtras().getString(Published.TAG_ID));
    }

    public void onClickSingleTextPublished(View view)
    {
        Intent intent = new Intent(this,Published.class);
        intent.putExtra(Published.TAG_URI,URI);
        startActivity(intent);
        finish();
    }
}
