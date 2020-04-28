/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.materialme

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

import java.util.ArrayList
import android.content.Intent



/***
 * The adapter class for the RecyclerView, contains the sports data
 */
internal class SportsAdapter
/**
 * Constructor that passes in the sports data and the context
 * @param sportsData ArrayList containing the sports data
 * @param context Context of the application
 */
(private val mContext: Context, //Member variables
 private val mSportsData: ArrayList<Sport>) : RecyclerView.Adapter<SportsAdapter.ViewHolder>() {


    /**
     * Required method for determining the size of the data set.
     * @return Size of the data set.
     */
    override  fun getItemCount(): Int {
        return mSportsData.size
    }

    /**
     * Required method for creating the viewholder objects.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly create ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false))
    }

    /**
     * Required method that binds the data to the viewholder.
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    override fun onBindViewHolder(holder: SportsAdapter.ViewHolder, position: Int) {
        //Get current sport
        val currentSport = mSportsData[position]

        //Populate the imageviews with data image using Glide Library
        Glide.with(mContext).load(currentSport.getImageResource()).into(holder.mSportsImage)
        //Populate the textviews with data
        holder.bindTo(currentSport)
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView
     */
    internal inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Constructor for the ViewHolder, used in onCreateViewHolder().
     * @param itemView The rootview of the list_item.xml layout file
     */
        private var mTitleText: TextView
        private var mInfoText: TextView
        //Add a variable in the SportsAdapter class, ViewHolder class for the ImageView, and initialize it in the ViewHolder constructor
        var mSportsImage : ImageView


        init {
            //Member Variables for the TextViews
            mTitleText = itemView.findViewById(R.id.title)
            mInfoText = itemView.findViewById(R.id.subTitle)
            mSportsImage = itemView.findViewById(R.id.sportsImage)

            itemView.setOnClickListener{ _: View ->
                //var sportPosition: Int = this.adapterPosition
                val currentSport : Sport = mSportsData[adapterPosition]

                val detailIntent = Intent(mContext, DetailActivity::class.java)
                detailIntent.putExtra("title", currentSport.title)
                detailIntent.putExtra("image_resource", currentSport.getImageResource())

                mContext.startActivity(detailIntent)
            }

        }

        fun bindTo(currentSport: Sport) {
            //Populate the textviews with data
            mTitleText.text = currentSport.title
            mInfoText.text = currentSport.info

        }




    }
}
