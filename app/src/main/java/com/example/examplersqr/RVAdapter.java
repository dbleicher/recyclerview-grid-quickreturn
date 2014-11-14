package com.example.examplersqr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
public class RVAdapter
        extends RecyclerView.Adapter<RVAdapter.ViewHolder>
        implements View.OnClickListener {

    // Some dark background colors to spice up
    // our title in the display (Black, Red, Green, Blue)
    private static final int[] bgColors = {
            0xAA000000,
            0xFF800000,
            0xFF008000,
            0xFF000080
    };

    // Some darker background colors to spice up
    // our subtitle in the display (Black, Red, Green, Blue)
    private static final int[] sbgColors = {
            0xFF000000,
            0xFF600000,
            0xFF006000,
            0xFF000060
    };

    // Get a Random generated to pick a cell's
    // background color from the list above.
    private static Random randy = new Random();


    private ArrayList<String> mDataset;
    private Context mContext;


    public RVAdapter (Context context, ArrayList<String> myDataset) {
        this.mDataset = myDataset;
        this.mContext = context;
    }

    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view by inflating the row item xml.
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cell_example, parent, false);

        ViewHolder holder = new ViewHolder(v);

        // Sets the click adapter for the entire cell
        // to the one in this class.
        holder.itemView.setOnClickListener(RVAdapter.this);
        holder.itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(RVAdapter.ViewHolder holder, int position) {

        // The rest is normal onBindViewHolder behavior
        // Just set the fields to their values
        int colorIndex = randy.nextInt(bgColors.length);
        holder.tvTitle.setText(mDataset.get(position));
        holder.tvTitle.setBackgroundColor(bgColors[colorIndex]);
        holder.tvSubTitle.setBackgroundColor(sbgColors[colorIndex]);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        String theString = mDataset.get(holder.getPosition());

        Toast.makeText(mContext, "Clicked: "+theString, Toast.LENGTH_SHORT).show();
    }

    /**
     * Create a ViewHolder to represent your cell layout
     * and data element structure
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvSubTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSubTitle = (TextView) itemView.findViewById(R.id.tvSubTitle);
        }
    }
}
