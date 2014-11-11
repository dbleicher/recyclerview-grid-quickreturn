package com.example.examplersqr;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Copyright 2014 David Bleicher
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 * Initial version created 11/10/2014
 */
public class MainActivity extends ActionBarActivity {

    private SwipeRefreshLayout swiper;
    private RecyclerView mRecycler;
    private StaggeredGridLayoutManager mSGLM;
    private LinearLayout qrBar;
    private TextView tvQRBarTitle;

    private RVAdapter mAdapter;
    private ArrayList<String> myDataset;
    private Animation inAnim;
    private Animation outAnim;

    // An array of meaningless titles
    private static final String[] someTitles = {
            "This is a Title",
            "Another Short Title",
            "Here's a Much Longer Title for Everyone to Deal With!!",
            "Coming Soon...",
            "Hey Bud, How About That Local Sports Team?",
            "What's Up Pussycat? Whoaaa"
    };

    // For randomizing titles across our dataset
    private Random randy = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////////////
        //  Setup QR Bar    //
        //////////////////////
        inAnim = AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_top);
        outAnim = AnimationUtils.loadAnimation(this,R.anim.abc_slide_out_top);

        qrBar = (LinearLayout) findViewById(R.id.myQRBar);

        tvQRBarTitle = (TextView) findViewById(R.id.tvQRBarTitle);
        tvQRBarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You Clicked Me!", Toast.LENGTH_SHORT).show();
            }
        });
        tvQRBarTitle.setText("Click me if you like...");


        //////////////////////////////
        //  Setup Swipe To Refresh  //
        //////////////////////////////
        swiper = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swiper.setSize(SwipeRefreshLayout.LARGE);
        swiper.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light
        );

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // This is where you should kickoff the
                // refreshing task.

                // For now, just wait a few seconds and turn off refreshing.
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        if (myDataset != null && mAdapter != null) {
                            Collections.shuffle(myDataset);
                            mAdapter.notifyDataSetChanged();
                        }
                        swiper.setRefreshing(false);
                    }
                }, 5000);
            }
        });


        //////////////////////////////////////////////
        //  Grab the StaggeredGrid & Layout Manager //
        //////////////////////////////////////////////
        mRecycler = (RecyclerView) findViewById(R.id.rvExampleGrid);

        // In production, better to get this from a "values.xml" resource
        // in a res folder appropriate to screen size / orientation
        int columnCount = 2;

        mSGLM = new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(mSGLM);

        //////////////////////////////
        //  Setup Adapter & DataSet //
        //////////////////////////////
        myDataset = new ArrayList<String>();

        // Load up the dataset with random titles
        for (int x = 0; x < 50; x++) {
            myDataset.add(someTitles[randy.nextInt(someTitles.length)]);
        }

        mAdapter = new RVAdapter(this, myDataset);

        /////////////////////////////////////////////
        //  Setup the RecyclerView Scroll Listener //
        /////////////////////////////////////////////
        mRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                // This 'magic' came from: http://stackoverflow.com/a/25183693/2259418
                // Needed to make sure refresh only occurs from TOP of list.
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0)
                                ? 0 : recyclerView.getChildAt(0).getTop();
                swiper.setEnabled(topRowVerticalPosition >= 0);

                // Simple check if moved vertically.
                // TODO: Enhance with check for scrolled distance and state
                if (dy > 0) {
                    if (qrBar.getVisibility() == View.VISIBLE)
                        hideCommToolBar();

                } else {

                    if (qrBar.getVisibility() == View.GONE)
                        showCommToolBar();
                }
            }
        });


        // Finally, set the RecyclerView's Adapter
        mRecycler.setAdapter(mAdapter);
    }

    private void hideCommToolBar() {
        qrBar.startAnimation(outAnim);
        qrBar.setVisibility(View.GONE);
    }

    private void showCommToolBar() {
        qrBar.startAnimation(inAnim);
        qrBar.setVisibility(View.VISIBLE);
    }

}
