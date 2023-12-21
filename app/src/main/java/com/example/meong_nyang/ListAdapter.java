package com.example.meong_nyang;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private ArrayList<productPrice> arrayList;
    private Context context;
    private Intent intent;//인텐트 선언

    public ListAdapter(ArrayList<productPrice> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        ListViewHolder holder = new ListViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {

        holder.distributor.setText(arrayList.get(position).getDistributor());
        holder.textPrice.setText(Integer.toString(arrayList.get(position).getPrice()));
        holder.textDelivery.setText(Integer.toString(arrayList.get(position).getDelivery()));
        holder.button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                Intent url = new Intent(Intent.ACTION_VIEW, Uri.parse(arrayList.get(pos).getLink()));
                view.getContext().startActivity(url);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView distributor;
        TextView textPrice;
        TextView textDelivery;

        Button button;

        public ListViewHolder (@NonNull View itemView) {
            super(itemView);

            this.distributor = itemView.findViewById(R.id.textView_list1);
            this.textPrice = itemView.findViewById(R.id.textView_list2);
            this.textDelivery = itemView.findViewById(R.id.textView_list3);
            this.button = itemView.findViewById(R.id.button1);
        }
    }
}
