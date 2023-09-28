package com.kotlin.toapp_recyclerview

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActivityModel(
    @PrimaryKey val uid: Int,
    val image: String?,
    var name:String="",
    var time:String="",
    var description:String="",
    var status:ActivityStatus=ActivityStatus.Completed
)

