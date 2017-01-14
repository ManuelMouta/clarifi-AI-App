package manuelmouta.clarifi_ai_app.activities.release;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import manuelmouta.clarifi_ai_app.R;
import manuelmouta.clarifi_ai_app.activities.BaseActivity;
import manuelmouta.clarifi_ai_app.fragments.LandingFragment;
import manuelmouta.clarifi_ai_app.fragments.MapsFragment;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

/**
 * Created by manuelmouta on 09/01/17.
 */

public class ReleaseLandingPageActivity extends BaseActivity implements ViewAnimator.ViewAnimatorListener, OnMapReadyCallback {

    private List<SlideMenuItem> list = new ArrayList<>();

    private ViewAnimator viewAnimator;

    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle drawerToggle;

    private LinearLayout linearLayout;

    private LandingFragment contentFragment;

    private int res;

    public GoogleMap mMap;

    private SupportMapFragment mapFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_landing_page);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SupportMapFragment mMapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map));
        mMapFragment.getView().setVisibility(View.INVISIBLE);

        contentFragment = LandingFragment.newInstance(R.drawable.ic_camera_white);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);

    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(LandingFragment.CLOSE, R.drawable.ic_close_red);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(LandingFragment.HOME, R.drawable.ic_camera_white);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(LandingFragment.MAP, R.drawable.ic_location);
        list.add(menuItem2);
    }
    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng don = new LatLng(38.746851, -9.189231);
        mMap.addMarker(new MarkerOptions().position(don).title("Don Giovanni"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(don));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(don.latitude, don.longitude), 12.0f));

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.ic_camera_white ? R.drawable.ic_location : R.drawable.ic_camera_white;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        LandingFragment contentFragment = LandingFragment.newInstance(this.res);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        SupportMapFragment mMapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map));
        switch (slideMenuItem.getName()) {
            case LandingFragment.CLOSE:
                return screenShotable;
            case LandingFragment.HOME:
                mMapFragment.getView().setVisibility(View.INVISIBLE);

                LandingFragment landingFragment = LandingFragment.newInstance(this.res);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, landingFragment).commit();

                return landingFragment;
            case LandingFragment.MAP:
                mMapFragment.getView().setVisibility(View.VISIBLE);
                return replaceFragment(screenShotable, position);
            default:
                return replaceFragment(screenShotable, position);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
