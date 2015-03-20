package com.example.examplersqr;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

    private TargetedSwipeRefreshLayout swiper;
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

    // See QRBar setup below
    private int columnCount;
    private int qrBarHeight;

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

        // In production, better to get this from a "values.xml" resource
        // in a res folder appropriate to screen size / orientation
        columnCount = 2;

        // Set the QRBar Height to that of the ActionBar
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            qrBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        tvQRBarTitle = (TextView) findViewById(R.id.tvQRBarTitle);
        tvQRBarTitle.setText("Tap to add item at top...");
        tvQRBarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemAtPosition(0, "NEW CARD ADDED ON: "+new Date().toString());
            }
        });

        //////////////////////////////
        //  Setup Swipe To Refresh  //
        //////////////////////////////
        swiper = (TargetedSwipeRefreshLayout) findViewById(R.id.swipe_container);
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
        mRecycler.addItemDecoration(new QRBarDecoration(columnCount, qrBarHeight));
        // mRecycler.addItemDecoration(new OverlapDecoration());

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
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                // Simple check if moved vertically.
                // React to scrolls of a minimum amount (3, in this case)
                if (dy > 3) {

                    if (qrBar.getVisibility() == View.VISIBLE)
                        hideQRBar();

                } else if (dy < -3) {

                    if (qrBar.getVisibility() == View.GONE)
                        showQRBar();
                }
            }
        });


        // Set the RecyclerView's Adapter
        mRecycler.setAdapter(mAdapter);

        // Set the Recyclerview to be the target scrollable view
        // for the TargetedSwipeRefreshAdapter.
        swiper.setTargetScrollableView(mRecycler);

    }

    private void hideQRBar() {
        qrBar.startAnimation(outAnim);
        qrBar.setVisibility(View.GONE);
    }

    private void showQRBar() {
        qrBar.startAnimation(inAnim);
        qrBar.setVisibility(View.VISIBLE);
    }

    /**
     * Adds an item at postion, and scrolls to that position.
     * @param position index in dataset
     * @param item to add
     */
    public void addItemAtPosition(int position, String item) {
        myDataset.add(position, item);
        mAdapter.notifyItemInserted(position);
        mSGLM.scrollToPosition(position);

        // Items added to the top row? Better invalidate the decorator.
        // Delay to ensure that the previous layout pass has completed.
        // NO LONGER REQUIRED:  as of R22 support libs
//        if (position < columnCount) {
//            new Handler().post(new Runnable() {
//                @Override
//                public void run() {
//                    mRecycler.invalidateItemDecorations();
//                }
//            });
//        }
    }


}
