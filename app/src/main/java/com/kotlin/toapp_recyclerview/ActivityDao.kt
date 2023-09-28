package com.kotlin.toapp_recyclerview

import androidx.contentpager.content.Query
import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ActivityDao {
//interfaces have no objects, can only be inherited from
    //@Insert
    //fun insertActivity( data:ActivityModel)
    //@Query
    //    ("select * from ActivityModel")
    //fun getAllActivities():List<ActivityModel>

    @androidx.room.Query("SELECT * FROM activityModel")
    fun getAllActivities():List<ActivityModel>

    @Insert
    fun insertActivity(data:ActivityModel)

}