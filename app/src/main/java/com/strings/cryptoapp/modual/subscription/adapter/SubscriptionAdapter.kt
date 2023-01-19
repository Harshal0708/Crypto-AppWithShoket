package com.strings.cryptoapp.modual.subscription.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.strings.airqualityvisualizer.R
import com.strings.airqualityvisualizer.Response.UserSubscriptionDataResponse
import com.strings.airqualityvisualizer.modual.subscription.SubscriptionDetailActivity

class SubscriptionAdapter (val context: Context, var subscriptionModelList: List<UserSubscriptionDataResponse>, var planId:String) :
    RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txt_subscriptionName : TextView = itemView.findViewById(R.id.txt_subscriptionName)
        var txt_subscriptionPrice : TextView = itemView.findViewById(R.id.txt_subscriptionPrice)
        var txt_subscriptionMoreDetail : TextView = itemView.findViewById(R.id.txt_subscriptionMoreDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(context).inflate(R.layout.subscription_adapter,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_subscriptionName.text=subscriptionModelList.get(position).subscriptionName
        holder.txt_subscriptionPrice.text="$  ${subscriptionModelList.get(position).subscriptionPrice}"
        holder.txt_subscriptionMoreDetail.setOnClickListener {
            val intent  = Intent(context,SubscriptionDetailActivity::class.java)
            intent.putExtra("subscriptionId", subscriptionModelList.get(position).subscriptionId)
            intent.putExtra("planId", planId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return subscriptionModelList.size
    }

}