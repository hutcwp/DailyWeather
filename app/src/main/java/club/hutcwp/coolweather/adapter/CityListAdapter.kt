/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package club.hutcwp.coolweather.adapter

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import club.hutcwp.coolweather.R

/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

class CityListAdapter(context: Context, @param:LayoutRes private val resourceId: Int, list: List<String>) : ArrayAdapter<String>(context, resourceId, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val name = getItem(position)
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, parent, false)
            viewHolder = ViewHolder()
            viewHolder.textView = view.findViewById<View>(R.id.name) as TextView
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.textView!!.text = name
        return view
    }

    private inner class ViewHolder {
        var textView: TextView? = null
    }
}
