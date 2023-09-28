package com.kotlin.toapp_recyclerview

import android.content.Context
import androidx.room.Room

class DatabaseBuilder() {
    fun buildDB( context:Context):ActivityDatabase{
        val db = Room.databaseBuilder(
        //return Room.databaseBuilder(
            context,
            ActivityDatabase::class.java,"room_db_name"
        ).build()
        return buildDB(context)
        val Dao = db.activityDao()
        val users : List<ActivityModel> = Dao.getAllActivities()
    }
    //fun get():ActivityDao{
        //val Dao = db.activit
        // }
}