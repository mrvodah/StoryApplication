package com.example.vietvan.storyapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.example.vietvan.storyapplication.adapter.StoryExpandableListViewAdapter;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private MaterialSearchView searchView;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    public static int currentColor = Color.parseColor("#e7922e");
    public static int currentColor2 = Color.parseColor("#e7922e");
    private com.example.vietvan.storyapplication.DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = com.example.vietvan.storyapplication.DatabaseManager.newInstance(this);
        Object[] objects = db.getListTitle().toArray();
        final String[] stringarr = new String[objects.length];
        for(int i=0;i<objects.length;i++)
            stringarr[i] = (String) objects[i];
        String[] strings = new String[objects.length];
        for(int i=0;i<objects.length;i++){
            if(stringarr[i].length() > 21)
                strings[i] = stringarr[i].substring(0, 21).toLowerCase() + "...";
            else
                strings[i] = stringarr[i].toLowerCase();
        }

        searchView = findViewById(R.id.search_view);
        searchView.setSuggestions(strings);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, com.example.vietvan.storyapplication.IntroStoryActivity.class);
                intent.putExtra("story", db.findStoryByTitle(query));
                startActivity(intent);
                searchView.closeSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setColorFilter(currentColor);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle("Choose color")
                        .initialColor(currentColor)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {

                            }
                        })
                        .setPositiveButton("Ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                currentColor = selectedColor;
                                com.example.vietvan.storyapplication.Screen1Fragment.bgSc1.setBackgroundColor(currentColor);
                                com.example.vietvan.storyapplication.Screen2Fragment.bgSc2.setBackgroundColor(currentColor);
                                fab.setColorFilter(currentColor);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        pager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabs);

        pager .setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabs.setViewPager(pager);

    }



    public class MyAdapter extends FragmentPagerAdapter {

        private String[] titles = {
                "Home", "List Story"
        };

        public MyAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return com.example.vietvan.storyapplication.Screen1Fragment.newInstance(position);
                case 1:
                    return com.example.vietvan.storyapplication.Screen2Fragment.newInstance(position);
            }

            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START) || searchView.isSearchOpen()) {
            drawer.closeDrawer(GravityCompat.START);
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_like) {
            setUp(1);
            return true;
        }
        else if (id == R.id.action_unlike){
            setUp(2);
            return true;
        }
        else if (id == R.id.action_all){
            setUp(3);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUp(int pos){
        com.example.vietvan.storyapplication.Screen2Fragment.storyTypes = com.example.vietvan.storyapplication.Screen2Fragment.db.getListStoryType();
        if(pos == 3)
            com.example.vietvan.storyapplication.Screen2Fragment.hashMap = com.example.vietvan.storyapplication.Screen2Fragment.db.getHashMapStory(com.example.vietvan.storyapplication.Screen2Fragment.db.getListStoryType(), com.example.vietvan.storyapplication.Screen2Fragment.db.getListStory());
        else if(pos == 2)
            com.example.vietvan.storyapplication.Screen2Fragment.hashMap = com.example.vietvan.storyapplication.Screen2Fragment.db.getHashMapStoryUnLike(com.example.vietvan.storyapplication.Screen2Fragment.db.getListStoryType(), com.example.vietvan.storyapplication.Screen2Fragment.db.getListStory());
        else if(pos == 1)
            com.example.vietvan.storyapplication.Screen2Fragment.hashMap = com.example.vietvan.storyapplication.Screen2Fragment.db.getHashMapStoryLike(com.example.vietvan.storyapplication.Screen2Fragment.db.getListStoryType(), com.example.vietvan.storyapplication.Screen2Fragment.db.getListStory());
        com.example.vietvan.storyapplication.Screen2Fragment.story = new StoryExpandableListViewAdapter(com.example.vietvan.storyapplication.Screen2Fragment.context, com.example.vietvan.storyapplication.Screen2Fragment.storyTypes, com.example.vietvan.storyapplication.Screen2Fragment.hashMap);

        com.example.vietvan.storyapplication.Screen2Fragment.ex.setAdapter(com.example.vietvan.storyapplication.Screen2Fragment.story);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_manage) {

        } else if(id == R.id.nav_info){

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
