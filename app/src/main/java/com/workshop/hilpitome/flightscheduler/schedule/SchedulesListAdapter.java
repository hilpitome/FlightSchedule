package com.workshop.hilpitome.flightscheduler.schedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.workshop.hilpitome.flightscheduler.R;
import com.workshop.hilpitome.flightscheduler.model.Arrival;
import com.workshop.hilpitome.flightscheduler.model.Departure;
import com.workshop.hilpitome.flightscheduler.model.FlightInfo;

import java.util.ArrayList;
import java.util.List;

public class SchedulesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private List<FlightInfo> mItems = new ArrayList<>();
        private Context mContext;
        private SchedulesListAdapter.OnItemClickListener mListener;

        public SchedulesListAdapter(Context mContext, SchedulesListAdapter.OnItemClickListener listener) {
            this.mContext = mContext;
            this.mListener = listener;
        }

        public void clear() {
            mItems.clear();
        }



        public void addItems(List<FlightInfo> customersList) {
            mItems.addAll(customersList);
            notifyDataSetChanged();
        }


        public FlightInfo getItem(int position) {
            return mItems.get(position);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_details_item, viewGroup, false);

            MyViewHolder holder = new MyViewHolder(itemView);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            MyViewHolder holder =(MyViewHolder) viewHolder;
            holder.bindData(getItem(i));
            holder.click(getItem(i), mListener);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public void addPrompt(FlightInfo prompt) {
            mItems.add(0, prompt);
        }


        public static class MyViewHolder extends RecyclerView.ViewHolder {
            ;
            public View mItemView;
            FlightInfo flightInfo;
            TextView durationTv, departureCodeTv, departureTimeTv,
                    arrivalCodeTv, arrivalTimeTv;
            public MyViewHolder(View itemView) {
                super(itemView);
                mItemView = itemView;
                durationTv = itemView.findViewById(R.id.duration_tv);
                departureCodeTv = itemView.findViewById(R.id.departure_airport_code);
                departureTimeTv = itemView.findViewById(R.id.departure_time);
                arrivalCodeTv = itemView.findViewById(R.id.arrival_airport_code);
                arrivalTimeTv = itemView.findViewById(R.id.arrival_time);
            }

            public void bindData(FlightInfo flightInfo){
                this.flightInfo = flightInfo;
                durationTv.setText(flightInfo.getTotalJourney().getDuration());
                Departure departure = flightInfo.getFlight().getDeparture();
                String departTime = departure.getScheduledTimeLocal().getDateTime();
                departureTimeTv.setText(departTime);
                String departCode = departure.getAirportCode();
                departureCodeTv.setText(departCode);
                Arrival arrival = flightInfo.getFlight().getArrival();
                String arrivalTime = arrival.getScheduledTimeLocal().getDateTime();
                arrivalTimeTv.setText(arrivalTime);
                String arrivalCode = arrival.getAirportCode();
                arrivalCodeTv.setText(arrivalCode);

//                departureCodeTv.setText();
            }

            public void click(final FlightInfo flightInfo,
                              final SchedulesListAdapter.OnItemClickListener listener){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(flightInfo);
                    }
                });
            }
        }

        public interface OnItemClickListener{
            void onItemClick(FlightInfo flightInfo);
        }



    }

