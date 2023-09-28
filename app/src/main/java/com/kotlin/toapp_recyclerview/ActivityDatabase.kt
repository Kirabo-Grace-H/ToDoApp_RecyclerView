package com.kotlin.toapp_recyclerview

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities=[ActivityModel::class], version =1)
//abstract class..can't create objects from them, you can only extend them
abstract class ActivityDatabase: RoomDatabase() {
    abstract fun activityDao():ActivityDao
}