package napatskyf.taskukrbush;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
     DrawerLayout mDrawerLayout;
    String[] arrayItemTitles;
    ListView mDrawerListView;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBarDrawerToggle myDrawerToggle;

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

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        myDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.open_menu,
                R.string.close_menu
        ) {
//            public void onDrawerClosed(View view) {
//                getSupportActionBar().setTitle("12");
//                // calling onPrepareOptionsMenu() to show action bar icons
//                invalidateOptionsMenu();
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                getSupportActionBar().setTitle(mDrawerTitle);
//                // calling onPrepareOptionsMenu() to hide action bar icons
//                invalidateOptionsMenu();
//            }
        };
        mDrawerLayout.setDrawerListener(myDrawerToggle);

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
//        Toast.makeText(this, " " + position, Toast.LENGTH_SHORT).show();

    }


//    public void onClick(View view){
//        Intent intent = null;
//        switch (view.getId()) {
//            case R.id.buttonPublished:
//                intent = new Intent(MainActivity.this,Published.class);
//                intent.putExtra("URI","https://api.ukrbash.org/1/quotes.getPublished.json?client=91f748ec00ea1a2c");
//                break;
//            case R.id.buttonUpcoming:
//                intent = new Intent(MainActivity.this,Published.class);
//                intent.putExtra("URI","https://api.ukrbash.org/1/quotes.getUpcoming.json?client=91f748ec00ea1a2c");
//                break;
//            case R.id.picturesPublished:
//                intent = new Intent(MainActivity.this,PicturesPublished.class);
//                intent.putExtra("URI","https://api.ukrbash.org/1/pictures.getPublished.json?client=91f748ec00ea1a2c");
//                break;
//        }
//        startActivity(intent);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (myDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        myDrawerToggle.syncState();
    }
}
