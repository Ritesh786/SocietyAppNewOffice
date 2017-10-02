package com.riteshavikal.lue.societyapp;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.riteshavikal.lue.societyapp.Adapter.ExpandableListAdapter;
import com.riteshavikal.lue.societyapp.Fragment.ApprovedNews;
import com.riteshavikal.lue.societyapp.Fragment.ApprovedRequest;
import com.riteshavikal.lue.societyapp.Fragment.PendingNews;
import com.riteshavikal.lue.societyapp.Fragment.PendingRequest;
import com.riteshavikal.lue.societyapp.Fragment.RejectedNews;
import com.riteshavikal.lue.societyapp.Fragment.RejectedRequest;
import com.riteshavikal.lue.societyapp.Fragment.SocietyMainNews;
import com.riteshavikal.lue.societyapp.Fragment.UploadNews;
import com.riteshavikal.lue.societyapp.Fragment.UploadRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardActivity extends AbsRuntimePermission
        implements NavigationView.OnNavigationItemSelectedListener {

    String name;

    UserSessionManager session;

    TextView mnametext;

    TextView mTitle;

    private static final int REQUEST_PERMISSION = 10;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> request_section = new ArrayList<String>();
    List<String> news_section = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.pageTitle);
        setSupportActionBar(toolbar);

        session = new UserSessionManager(getApplicationContext());

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        expListView = (ExpandableListView) findViewById(R.id.expandablelstview);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView = navigationView.getHeaderView(0);
        mnametext = (TextView) hView.findViewById(R.id.textView);

        navigationView.setNavigationItemSelectedListener(this);

        if (session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getname();

        // get name
        name = user.get(UserSessionManager.KEY_name);

        mnametext.setText(name);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                parent.setItemChecked(index, true);

                Toast.makeText(DashboardActivity.this, "clicked " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString(), Toast.LENGTH_SHORT).show();

                if(request_section.get(0).equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString()))
                {

                    UploadRequest fragment = new UploadRequest();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.containerView, fragment).addToBackStack("Upload Request").commit();
                    manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {

                            try {

                                int lastBackStackEntryCount = getSupportFragmentManager().getBackStackEntryCount() - 1;

                                FragmentManager.BackStackEntry lastBackStackEntry =
                                        getSupportFragmentManager().getBackStackEntryAt(lastBackStackEntryCount);

                                mTitle.setText(lastBackStackEntry.getName());

                            } catch (Exception e) {
                                e.printStackTrace();
                                DashboardActivity.this.finish();
                                Log.e("prob",e.toString());
                            }

                        }
                    });

                }

                if(request_section.get(1).equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString()))
                {

                    PendingRequest fragment = new PendingRequest();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.containerView, fragment).addToBackStack("Pending Request").commit();
                    manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {

                            try {

                                int lastBackStackEntryCount = getSupportFragmentManager().getBackStackEntryCount() - 1;

                                FragmentManager.BackStackEntry lastBackStackEntry =
                                        getSupportFragmentManager().getBackStackEntryAt(lastBackStackEntryCount);

                                mTitle.setText(lastBackStackEntry.getName());

                            } catch (Exception e) {
                                e.printStackTrace();
                                DashboardActivity.this.finish();
                                Log.e("prob",e.toString());
                            }

                        }
                    });

                }

                if(request_section.get(2).equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString()))
                {

                    ApprovedRequest fragment = new ApprovedRequest();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.containerView, fragment).addToBackStack("Approved Request").commit();
                    manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {

                            try {

                                int lastBackStackEntryCount = getSupportFragmentManager().getBackStackEntryCount() - 1;

                                FragmentManager.BackStackEntry lastBackStackEntry =
                                        getSupportFragmentManager().getBackStackEntryAt(lastBackStackEntryCount);

                                mTitle.setText(lastBackStackEntry.getName());

                            } catch (Exception e) {
                                e.printStackTrace();
                                DashboardActivity.this.finish();
                                Log.e("prob",e.toString());
                            }

                        }
                    });

                }

                if(request_section.get(3).equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString()))
                {

                    RejectedRequest fragment = new RejectedRequest();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.containerView, fragment).addToBackStack("Rejected Request").commit();
                    manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {

                            try {

                                int lastBackStackEntryCount = getSupportFragmentManager().getBackStackEntryCount() - 1;

                                FragmentManager.BackStackEntry lastBackStackEntry =
                                        getSupportFragmentManager().getBackStackEntryAt(lastBackStackEntryCount);

                                mTitle.setText(lastBackStackEntry.getName());

                            } catch (Exception e) {
                                e.printStackTrace();
                                DashboardActivity.this.finish();
                                Log.e("prob",e.toString());
                            }

                        }
                    });

                }

                if(news_section.get(0).equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString()))
                {
                    UploadNews fragment = new UploadNews();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.containerView, fragment).addToBackStack("Upload News").commit();
                    manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {

                            try {

                                int lastBackStackEntryCount = getSupportFragmentManager().getBackStackEntryCount() - 1;

                                FragmentManager.BackStackEntry lastBackStackEntry =
                                        getSupportFragmentManager().getBackStackEntryAt(lastBackStackEntryCount);

                                mTitle.setText(lastBackStackEntry.getName());

                            } catch (Exception e) {
                                e.printStackTrace();
                                DashboardActivity.this.finish();
                                Log.e("prob",e.toString());
                            }

                        }
                    });

                }

                if(news_section.get(1).equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString()))
                {
                    PendingNews fragment = new PendingNews();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.containerView, fragment).addToBackStack("Pending News").commit();
                    manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {

                            try {

                                int lastBackStackEntryCount = getSupportFragmentManager().getBackStackEntryCount() - 1;

                                FragmentManager.BackStackEntry lastBackStackEntry =
                                        getSupportFragmentManager().getBackStackEntryAt(lastBackStackEntryCount);

                                mTitle.setText(lastBackStackEntry.getName());

                            } catch (Exception e) {
                                e.printStackTrace();
                                DashboardActivity.this.finish();
                                Log.e("prob",e.toString());
                            }

                        }
                    });

                }
                if(news_section.get(2).equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString()))
                {
                    ApprovedNews fragment = new ApprovedNews();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.containerView, fragment).addToBackStack("Approved News").commit();
                    manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {

                            try {

                                int lastBackStackEntryCount = getSupportFragmentManager().getBackStackEntryCount() - 1;

                                FragmentManager.BackStackEntry lastBackStackEntry =
                                        getSupportFragmentManager().getBackStackEntryAt(lastBackStackEntryCount);

                                mTitle.setText(lastBackStackEntry.getName());

                            } catch (Exception e) {
                                e.printStackTrace();
                                DashboardActivity.this.finish();
                                Log.e("prob",e.toString());
                            }

                        }
                    });

                }

                if(news_section.get(3).equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString()))
                {
                    RejectedNews fragment = new RejectedNews();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.containerView, fragment).addToBackStack("Rejected News").commit();
                    manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {

                            try {

                                int lastBackStackEntryCount = getSupportFragmentManager().getBackStackEntryCount() - 1;

                                FragmentManager.BackStackEntry lastBackStackEntry =
                                        getSupportFragmentManager().getBackStackEntryAt(lastBackStackEntryCount);

                                mTitle.setText(lastBackStackEntry.getName());

                            } catch (Exception e) {
                                e.printStackTrace();
                                DashboardActivity.this.finish();
                                Log.e("prob",e.toString());
                            }

                        }
                    });

                }


                drawer.closeDrawers();


                return false;
            }
        });

        tabdesign fragment = new tabdesign();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.containerView, fragment).addToBackStack("Society App").commit();


        requestAppPermissions(new String[]{

                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                R.string.msg, REQUEST_PERMISSION);


    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            session.logoutUser();
            DashboardActivity.this.finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            tabdesign fragment = new tabdesign();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.containerView, fragment).addToBackStack("Society App").commit();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Your Request");
        listDataHeader.add("Your News");
     //   listDataHeader.add("Coming Soon..");

        // Adding child data

        request_section.add("Upload Request");
        request_section.add("Pending Request");
        request_section.add("Approved Request");
        request_section.add("Rejected Request");


        news_section.add("Upload News");
        news_section.add("Pending News");
        news_section.add("Approved News");
        news_section.add("Rejected News");

//        List<String> comingSoon = new ArrayList<String>();
//        comingSoon.add("2 Guns");
//        comingSoon.add("The Smurfs 2");
//        comingSoon.add("The Spectacular Now");
//        comingSoon.add("The Canyons");
//        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), request_section); // Header, Child data
        listDataChild.put(listDataHeader.get(1), news_section);
      //  listDataChild.put(listDataHeader.get(2), comingSoon);
    }
}

