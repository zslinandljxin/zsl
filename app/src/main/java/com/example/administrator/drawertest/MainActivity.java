package com.example.administrator.drawertest;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/10/31.
 */
public class MainActivity extends Activity {
    DrawerLayout drawerLayout ;
    String[] books;
    ListView listView;
    ArrayAdapter adapter;
    CharSequence drawerTitle;
    CharSequence mTitle;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        listView = (ListView) findViewById(R.id.list_content);
        books = getResources().getStringArray(R.array.Books);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,books);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnClick());

        mTitle = getTitle();
        drawerTitle = "drawer";
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.drawable.b9,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isOpen = drawerLayout.isDrawerOpen(listView);
        menu.findItem(R.id.setting).setVisible(!isOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    public class OnClick implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selected(position);
        }

        public void selected(int position){
            Fragment fragment = new FragmentDemo();
            Bundle arg = new Bundle();
            arg.putString("TAG", books[position]);
            fragment.setArguments(arg);
            android.app.FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();

            transaction.replace(R.id.content_pager,fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            listView.setItemChecked(position,true);
            drawerLayout.closeDrawer(listView);
        }
    }
    public static class FragmentDemo extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_demo,container,false);
            TextView textViewt = (TextView) view.findViewById(R.id.text);
            Bundle arg = getArguments();
            textViewt.setText(arg.getString("TAG"));
            return view;
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
