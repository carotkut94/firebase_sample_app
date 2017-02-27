package com.death.samplephp;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayoutManager mLinearLayoutManager;
    public static class MobileViewHolder extends RecyclerView.ViewHolder {
        public TextView modelTextView;
        public ImageView imageView;
        public TextView brandTextView;
        public TextView descTextView;
        public TextView offerTextView;
        public TextView priceTextView;

        public MobileViewHolder(View v) {
            super(v);
            modelTextView = (TextView) itemView.findViewById(R.id.model);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            brandTextView = (TextView) itemView.findViewById(R.id.brand);
            descTextView = (TextView) itemView.findViewById(R.id.desc);
            offerTextView = (TextView) itemView.findViewById(R.id.offer);
            priceTextView = (TextView) itemView.findViewById(R.id.price);

        }
    }
    RecyclerView offerContainer;
    FirebaseDatabase database;
    private FirebaseRecyclerAdapter<MobileModel, MobileViewHolder> mFirebaseAdapter;
    private DatabaseReference mFirebaseDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        offerContainer = (RecyclerView) findViewById(R.id.offerContainer);
        database = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = database.getReference();
        mFirebaseDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("VALUES", dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mFirebaseAdapter = new FirebaseRecyclerAdapter<MobileModel, MobileViewHolder>(
                MobileModel.class,
                R.layout.mobile_layout,
                MobileViewHolder.class,
                mFirebaseDatabaseReference.child("mobiles")) {

            @Override
            protected MobileModel parseSnapshot(DataSnapshot snapshot) {
                MobileModel friendlyMessage = super.parseSnapshot(snapshot);
                if (friendlyMessage != null) {
                    friendlyMessage.setId(snapshot.getKey());
                    Log.e("ID", snapshot.getKey());
                }
                return friendlyMessage;
            }

            @Override
            protected void populateViewHolder(final MobileViewHolder viewHolder,
                                              MobileModel mobileModel, int position) {
                if (mobileModel.getModel() != null) {
                    viewHolder.modelTextView.setText(mobileModel.getModel());
                    viewHolder.brandTextView.setText(mobileModel.getBrand());
                    viewHolder.priceTextView.setText(mobileModel.getOprice());
                    viewHolder.offerTextView.setText(mobileModel.getOffers());
                    viewHolder.descTextView.setText(mobileModel.getDescription());
                    Log.e("LINK",mobileModel.getImage());
                    Glide.with(MainActivity.this).load(mobileModel.getImage()).into(viewHolder.imageView);
                } else {
                    Toast.makeText(MainActivity.this, "Empty", Toast.LENGTH_LONG).show();
                }

            }
        };
        mLinearLayoutManager = new LinearLayoutManager(this, 1, false);

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
            }
        });

        offerContainer.setLayoutManager(mLinearLayoutManager);
        offerContainer.setAdapter(mFirebaseAdapter);


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
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
