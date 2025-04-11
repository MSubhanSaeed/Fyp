package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Switch
import android.widget.TextView

class TrackingAdapter(context: Context, private val nameList: List<Pair<String, Boolean>>) :
    ArrayAdapter<Pair<String, Boolean>>(context, R.layout.listitem_names, nameList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.listitem_names, parent, false)

        val tvName: TextView = view.findViewById(R.id.tvName)
        val switchTracking: Switch = view.findViewById(R.id.swTracking)

        val (name, trackingEnabled) = nameList[position]

        tvName.text = name
        switchTracking.isChecked = trackingEnabled

        // Change switch text dynamically
        switchTracking.text = if (trackingEnabled) "Tracking Enabled" else "Tracking Disabled"

        // Toggle switch state
        switchTracking.setOnCheckedChangeListener { _, isChecked ->
            switchTracking.text = if (isChecked) "Tracking Enabled" else "Tracking Disabled"
        }

        return view
    }
}
