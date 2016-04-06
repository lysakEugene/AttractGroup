package com.eugene.lysak.attractgrouptest.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.eugene.lysak.attractgrouptest.Fragments.BaseFragment;
import com.eugene.lysak.attractgrouptest.Fragments.HeroListFragment;
import com.eugene.lysak.attractgrouptest.Fragments.HeroPagerFragment;
import com.eugene.lysak.attractgrouptest.R;
import com.eugene.lysak.attractgrouptest.Services.HeroServiceHelper;
import com.eugene.lysak.attractgrouptest.Utils.StatusInternetConnection;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HeroListFragment.OnHeroListFragmentListener{

    private FloatingActionButton fab;
    private boolean mTwoPane;
    private DrawerLayout drawer;
    private IntentFilter heroServiceIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Отправляем запрос на обновление данных
                StatusInternetConnection internetConnection = StatusInternetConnection.getInstance(MainActivity.this);
                if (internetConnection.MobileConnection() || internetConnection.WifiConnection()) {
                    HeroServiceHelper.getInstance(MainActivity.this).getHeroes();
                }
            }
        });

        heroServiceIntentFilter =  new IntentFilter(HeroServiceHelper.ACTION_REQUEST_RESULT);

        //Проверка на телефон или планшет
        if (findViewById(R.id.drawer_layout) != null) {
            mTwoPane = false;
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        }else{
            mTwoPane = true;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null) {
            pushFragment(new HeroListFragment());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(requestReceiver, heroServiceIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (requestReceiver != null) {
            try {
                this.unregisterReceiver(requestReceiver);
            } catch (IllegalArgumentException e) {
               e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!mTwoPane&&drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Посылает broadCast сообщение при нажатии кнопки "Назад" которое получает нужный фрагмент
            LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(this);
            mgr.sendBroadcast(new Intent(BaseFragment.ACTION_BACK_PRESSED));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        final HeroListFragment fragment = (HeroListFragment)manager.findFragmentByTag(HeroListFragment.class.getName());

        searchView.setOnQueryTextListener(fragment);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search)
            return true;

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }

        if(!mTwoPane)
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void pushFragment(Fragment newFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, newFragment,newFragment.getClass().getName())
                .addToBackStack(newFragment.getClass().getName())
                .commit();
    }


    @Override
    public void onFragmentInteraction(long id) {
        pushFragment(HeroPagerFragment.newInstance(id));
    }

    public void setVisibleFAB(int visibleFAB) {
        fab.setVisibility(visibleFAB);
    }


    BroadcastReceiver requestReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra(HeroServiceHelper.EXTRA_RESULT_CODE, 0);

            if (resultCode == 200) {

                Snackbar.make(fab, "Данные обновлены", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            } else {

                Snackbar.make(fab, "Невозможно обновить данные! Проверьте подключение к интернету", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        }
    };

}
