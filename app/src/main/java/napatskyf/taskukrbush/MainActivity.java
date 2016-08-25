package napatskyf.taskukrbush;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
     DrawerLayout mDrawerLayout;
    String[] arrayItemTitles;
    ListView mDrawerListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        arrayItemTitles = new String[]{"PUBLISHED","UPCOMING","PICTURES PUBLISHED"};
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item,arrayItemTitles));

        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectItem(position);
            }
        });
    }

    private void selectItem(int position) {
        String[] URI;
        Fragment publishedFragment = null;
//        PublishedFragment publishedFragment = null;
//        PicturesPublishedFragment picturesPublishedFragment = null;
        URI = new String[]{"https://api.ukrbash.org/1/quotes.getPublished.json?client=91f748ec00ea1a2c","https://api.ukrbash.org/1/quotes.getUpcoming.json?client=91f748ec00ea1a2c",
                "https://api.ukrbash.org/1/pictures.getPublished.json?client=91f748ec00ea1a2c"};
        switch (position) {
            case 0:
                publishedFragment = new PublishedFragment(URI[position]);
                break;
            case 1:
                publishedFragment = new PublishedFragment(URI[position]);
                break;
            case 2:
                publishedFragment = new PicturesPublishedFragment(URI[position]);
                break;

        }


        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, publishedFragment).commit();

        mDrawerListView.setItemChecked(position, true);


        setTitle(arrayItemTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerListView);

        Toast.makeText(this, " " + position, Toast.LENGTH_SHORT).show();

//        Bundle args = new Bundle();
//        args.putInt(CatFragment.ARG_CAT_NUMBER, position);
//        fragment.setArguments(args);

    }


    public void onClick(View view){
        Intent intent = null;
        switch (view.getId()) {
            case R.id.buttonPublished:
                intent = new Intent(MainActivity.this,Published.class);
                intent.putExtra("URI","https://api.ukrbash.org/1/quotes.getPublished.json?client=91f748ec00ea1a2c");
                break;
            case R.id.buttonUpcoming:
                intent = new Intent(MainActivity.this,Published.class);
                intent.putExtra("URI","https://api.ukrbash.org/1/quotes.getUpcoming.json?client=91f748ec00ea1a2c");
                break;
            case R.id.picturesPublished:
                intent = new Intent(MainActivity.this,PicturesPublished.class);
                intent.putExtra("URI","https://api.ukrbash.org/1/pictures.getPublished.json?client=91f748ec00ea1a2c");
                break;
        }
        startActivity(intent);
    }

}
