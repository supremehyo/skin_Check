package com.example.s20141210jinwoojung.capston.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import com.example.s20141210jinwoojung.capston.Fragments.NoticeFragment;
import com.example.s20141210jinwoojung.capston.Fragments.VolumeFragment;
import com.example.s20141210jinwoojung.capston.R;
import com.example.s20141210jinwoojung.capston.utils.SharedPrefManager;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //
    private static final String SELECTED_ITEM_ID = "SELECTED_ITEM_ID";
    private final Handler mDrawerHandler = new Handler();
    private DrawerLayout mDrawerLayout;
    private int mPrevSelectedId;
    private NavigationView mNavigationView;
    private int mSelectedId;
    private Toolbar mToolbar;
    //main
    //private ArrayList<User> users;
    SharedPrefManager sharedPrefManager;
    /*@BindView(R.id.temptext)
    TextView tokendata;
    @BindView(R.id.tokentest)
    Button btn_tokentest;
    @BindView(R.id.logout)
    Button btn_logout;*/

    /*@OnClick({R.id.tokentest,R.id.logout})
    void onSomeThingClick(View view){
        switch (view.getId()) {
            case R.id.logout:
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);

                break;
            case R.id.tokentest:
                GetTokenService getTokenService = RetrofitInstance.getService();
                Call<Test> call = getTokenService.getSecret(sharedPrefManager.getSPToken());
                call.enqueue(new Callback<Test>() {
                    @Override
                    public void onResponse(Call<Test> call, Response<Test> response) {
                        if(response.code()==200) {
                            tokendata.setText(response.body().getTestData().getEmail());
                        }
                    }

                    @Override
                    public void onFailure(Call<Test> call, Throwable t) {

                    }
                });
        }
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awal);
        sharedPrefManager = new SharedPrefManager(this);
        ButterKnife.bind(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        assert mNavigationView != null;
        mNavigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                super.onDrawerSlide(drawerView, 0);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSelectedId = mNavigationView.getMenu().getItem(prefs.getInt("default_view", 0)).getItemId();
        mSelectedId = savedInstanceState == null ? mSelectedId : savedInstanceState.getInt(SELECTED_ITEM_ID);
        mPrevSelectedId = mSelectedId;
        mNavigationView.getMenu().findItem(mSelectedId).setChecked(true);

        if (savedInstanceState == null) {
            mDrawerHandler.removeCallbacksAndMessages(null);
            mDrawerHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigate(mSelectedId);
                }
            }, 250);

            boolean openDrawer = prefs.getBoolean("open_drawer", false);

            if (openDrawer)
                mDrawerLayout.openDrawer(GravityCompat.START);
            else
                mDrawerLayout.closeDrawers();
        }

        //getCountries();
        if (!sharedPrefManager.getSPSudahLogin()){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

    }
    /*public Object getCountries(){
        GetTokenService getTokenService = RetrofitInstance.getService();
        Call<List<User>> call = getTokenService.getUser();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                 List<User> users =response.body();
                 if(users!=null){
                     for(int i = 0 ; i < users.size();i++){
                         Log.e("user"+i,users.get(i).getEmail());
                         Log.e("user"+i,users.get(i).getUuid());
                         Log.e("user"+i,users.get(i).getCreatedAt());
                         Log.e("user"+i,users.get(i).getUpdatedAt());
                     }
                 }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
        return users;
    }*/
    public void switchFragment(int itemId) {
        mSelectedId = mNavigationView.getMenu().getItem(itemId).getItemId();
        mNavigationView.getMenu().findItem(mSelectedId).setChecked(true);
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(mSelectedId);
            }
        }, 250);
        mDrawerLayout.closeDrawers();
    }

    private void navigate(final int itemId) {
        final View elevation = findViewById(R.id.elevation);
        Fragment navFragment = null;
        switch (itemId) {
            case R.id.nav_1:
                mPrevSelectedId = itemId;
                setTitle(R.string.nav_home);
                navFragment = new VolumeFragment();
                break;
            case R.id.nav_2:
                mPrevSelectedId = itemId;
                setTitle("지도");
                navFragment = new NoticeFragment();
                break;
            //case R.id.nav_5:
            //startActivity(new Intent(this, SettingsActivity.class));
            //mNavigationView.getMenu().findItem(mPrevSelectedId).setChecked(true);
            //return;
            case R.id.nav_6:
                startActivity(new Intent(this, AboutActivity.class));
                mNavigationView.getMenu().findItem(mPrevSelectedId).setChecked(true);
                break;
            case R.id.nav_7:
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);

                return;
        }

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(4));

        if (navFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            try {
                transaction.replace(R.id.content_frame, navFragment).commit();

                //setting jarak elevasi bayangan ketika menggunakan tabs
                if (elevation != null) {
                    params.topMargin = navFragment instanceof VolumeFragment ? dp(48) : 0;

                    Animation a = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            elevation.setLayoutParams(params);
                        }
                    };
                    a.setDuration(150);
                    elevation.startAnimation(a);
                }
            } catch (IllegalStateException ignored) {
            }
        }
    }

    public int dp(float value) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;

        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mSelectedId = menuItem.getItemId();
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(mSelectedId);
            }
        }, 250);
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
