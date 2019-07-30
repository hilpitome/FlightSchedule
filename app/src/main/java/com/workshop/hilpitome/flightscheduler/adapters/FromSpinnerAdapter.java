package com.workshop.hilpitome.flightscheduler.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.workshop.hilpitome.flightscheduler.R;
import com.workshop.hilpitome.flightscheduler.model.AirportInfo;

import java.util.ArrayList;
import java.util.List;

public class FromSpinnerAdapter extends BaseAdapter {
        private List<AirportInfo> mItems = new ArrayList<>();
        private Context mContext;

        public FromSpinnerAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public void clear() {
            mItems.clear();
        }

        public void addPrompt(AirportInfo prompt) {
            mItems.add(0, prompt);
        }

        public void addItems(List<AirportInfo> airportInfoList) {
            mItems.addAll(airportInfoList);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public AirportInfo getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getDropDownView(int position, View view, ViewGroup parent) {
            if (view == null || !view.getTag().toString().equals("DROPDOWN")) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(R.layout.spinner_item_dropdown, parent, false);
                view.setTag("DROPDOWN");
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getTitle(position));

            return view;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null || !view.getTag().toString().equals("NON_DROPDOWN")) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(R.layout.
                        spinner_item_non_dropdown, viewGroup, false);
                view.setTag("NON_DROPDOWN");

            }
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getTitle(position));
            return view;
        }

        private String getTitle(int position) {
            String airportName = mItems.get(position).getNames().getName().get$();
            return position >= 0 && position < mItems.size() ? airportName : "";
        }

    }

