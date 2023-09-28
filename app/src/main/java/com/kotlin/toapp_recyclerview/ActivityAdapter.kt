package com.kotlin.toapp_recyclerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ActivityAdapter(var context:Context, var list: ArrayList<ActivityModel>):RecyclerView.Adapter
<ActivityAdapter.ActivityViewHolder>() {
    class ActivityViewHolder(itemView: View):ViewHolder(itemView) {
        // helps to get the elements in/from the view (list item layout)
        val image =itemView.findViewById<ImageView>(R.id.ActivityImage)
        val name =itemView.findViewById<TextView>(R.id.Activitytitle)
        val time =itemView.findViewById<TextView>(R.id.Activitytime)
        val card =itemView.findViewById<CardView>(R.id.myCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        // helps to create  the view (list item layout)
        val view =LayoutInflater.from(context).inflate(R.layout.list_item_layout,null,false)
        return ActivityViewHolder(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        //connects the list item layout view to the data(Activity model)
        //handles the logic
        holder.image.setImageURI(list[position].image?.toUri())
        holder.name.text=list[position].name
        holder.time.text=list[position].time

       holder.card.setOnClickListener{
           //startActivity...requires a context
           var intent = Intent(context,DetailsActivity::class.java)
           intent.putExtra("image",list[position].image)
           intent.putExtra("name",list[position].name)
           intent.putExtra("time",list[position].time)
           context.startActivity(intent)
       }
    }
}