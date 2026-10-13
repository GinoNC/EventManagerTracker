package com.gino.eventmanagertracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEventName, tvEventDate, tvEventLocation, tvEventOrganiser, tvEventType, tvEventStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvEventDate = itemView.findViewById(R.id.tvEventDate);
            tvEventLocation = itemView.findViewById(R.id.tvEventLocation);
            tvEventOrganiser = itemView.findViewById(R.id.tvEventOrganiser);
            tvEventType = itemView.findViewById(R.id.tvEventType);
            tvEventStatus = itemView.findViewById(R.id.tvEventStatus);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                Context context = v.getContext();

                EventModel evt = items.get(pos);
                Intent it = new Intent(itemView.getContext(), DetailActivity.class);
                it.putExtra("evtID", evt.id);
                context.startActivity(it);
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvEventName.setText(items.get(position).eventName);
        holder.tvEventDate.setText(items.get(position).eventDate);
        holder.tvEventLocation.setText(items.get(position).eventLocation);
        holder.tvEventOrganiser.setText(items.get(position).eventOrganiser);
        holder.tvEventType.setText(items.get(position).eventType);
        holder.tvEventStatus.setText(items.get(position).eventStatus);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private ArrayList<EventModel> items = new ArrayList<>();

    public ItemAdapter(EventDBHelper db) {
        items = db.getList();
    }
}
