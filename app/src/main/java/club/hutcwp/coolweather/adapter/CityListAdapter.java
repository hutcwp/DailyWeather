/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package club.hutcwp.coolweather.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import club.hutcwp.coolweather.R;

/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class CityListAdapter extends ArrayAdapter<String> {


    private int resourceId;

    public CityListAdapter(@NonNull Context context, @LayoutRes int resource, List<String> list) {
        super(context, resource, list);
        resourceId = resource;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {

            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.name);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();

        }

        viewHolder.textView.setText(name);
        return view;
    }


    private class ViewHolder {

        TextView textView;

    }

}
