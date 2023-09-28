package com.kotlin.toapp_recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list =ArrayList<ActivityModel>()
        //list.add(ActivityModel(R.drawable.ic_launcher_foreground,"check mails","5:30am","check",ActivityStatus.Completed))

        val activityAdapter = ActivityAdapter(this,list)
        val recyclerView =findViewById<RecyclerView>(R.id.myRecyclerview)
        recyclerView.adapter=activityAdapter

        recyclerView.layoutManager=LinearLayoutManager(this)

        val bt = findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        bt.setOnClickListener{
            val intent = Intent(this,AddActivity::class.java)
            startActivity(intent)

        }
    }
}