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

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import android.support.v7.widget.helper.ItemTouchHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


/***
 * Main Activity for the Material Me app, a mock sports news application with poor design choices
 */
class MainActivity : AppCompatActivity() {

    //Member variables
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSportsData: ArrayList<Sport>
    private lateinit var mAdapter: SportsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the integer from the integers.xml resource file
        val gridColumnCount = resources.getInteger(R.integer.grid_column_count)

        // Initialize the listener for the fab button and the action when pressed
        fabResetSports.setOnClickListener{ _ ->
            resetSports()
            //displayMap()
        }

        //Initialize the RecyclerView
        mRecyclerView = this.findViewById(R.id.recyclerView)

        //Set the Layout Manager
        mRecyclerView.layoutManager = GridLayoutManager(this,gridColumnCount)

        //Initialize the ArrayLIst that will contain the data
        mSportsData = ArrayList()

        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = SportsAdapter(this, mSportsData)
        mRecyclerView.adapter = mAdapter

        //Get the data
        initializeData()


        // Use the gridColumnCount variable to disable the swipe action when there is more than one column
        val mSwipeDirs = when {
            gridColumnCount > 1 -> 0
            else -> ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        }


        //Create a new instance of ItemTouchHelper.SimpleCallback that define what happens to RecyclerView list items when the user performs various touch actions, such as swipe, or drag and drop
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP, mSwipeDirs) {

            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {

                val from = viewHolder!!.adapterPosition
                val to = target!!.adapterPosition
                Collections.swap(mSportsData, from, to)
                mAdapter.notifyItemMoved(from, to)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                if (viewHolder != null) {
                    mSportsData.removeAt(viewHolder.adapterPosition)
                    mAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                }
            }

        }

        //After creating the new ItemTouchHelper object in the Main Activity's onCreate() method, call attachToRecyclerView() on the ItemTouchHelper instance to add it to your RecyclerView
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)

    }


    /**
     * Method for initializing the sports data from resources.
     */
    private fun initializeData() {
        //Get the resources from the XML file
        val sportsList = resources.getStringArray(R.array.sports_titles)
        val sportsInfo = resources.getStringArray(R.array.sports_info)
        val sportsImageResources = resources.obtainTypedArray(R.array.sports_images)

        //Clear the existing data (to avoid duplication)
        mSportsData.clear()

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for (i in sportsList.indices) {
            mSportsData.add(Sport(sportsList[i], sportsInfo[i], sportsImageResources.getResourceId(i,0)))
        }
        //Clean up the data in the typed array once you have created the Sport data ArrayList
        sportsImageResources.recycle()

        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged()
    }

    private fun resetSports() {
        initializeData()
    }
}
