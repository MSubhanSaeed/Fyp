package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ListItemBinding

class LocationAdapter(
    private val context: Context,
    private val locations: List<String>
) : ArrayAdapter<String>(context, 0, locations) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ListItemBinding

        val view = if (convertView == null) {
            binding = ListItemBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root
        } else {
            binding = ListItemBinding.bind(convertView)
            convertView
        }

        val cityName = locations[position]
        binding.txtLocation.text = cityName

        // Button Click Listeners
        binding.btnRead.setOnClickListener {
            Toast.makeText(context, "$cityName marked as Read", Toast.LENGTH_SHORT).show()
            binding.txtLocation.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
        }

        binding.btnUnread.setOnClickListener {
            Toast.makeText(context, "$cityName marked as Unread", Toast.LENGTH_SHORT).show()
            binding.txtLocation.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
        }

        return view
    }
}