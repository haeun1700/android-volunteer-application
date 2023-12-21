package com.example.meong_nyang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LostAnimalAdapter extends RecyclerView.Adapter<LostAnimalAdapter.LostAnimalViewHolder> {

    private ArrayList<LostAnimal> arrayList;
    private Context context;
    private Intent intent;

    public LostAnimalAdapter(ArrayList<LostAnimal> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public LostAnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lostanimal_itemlist, parent, false);
        LostAnimalViewHolder holder = new LostAnimalViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LostAnimalViewHolder holder, int position) {

        Glide.with(holder.image1)
                .load(arrayList.get(position).getImage1())
                .into(holder.image1);
        holder.title.setText(arrayList.get(position).getTitle());
        holder.content.setText(arrayList.get(position).getContent());
        holder.lostlocation.setText(arrayList.get(position).getLostlocation());
        holder.lostdate.setText(arrayList.get(position).getLostdate());
        holder.losttime.setText(arrayList.get(position).getLosttime());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = holder.getAdapterPosition();

                intent = new Intent(view.getContext(), LostAnimalDetailActivity.class);
                //정보 넘기기
                intent.putExtra("title", arrayList.get(mPosition).getTitle());
                intent.putExtra("content", arrayList.get(mPosition).getContent());
                intent.putExtra("lostlocation", arrayList.get(mPosition).getLostlocation());
                intent.putExtra("lostdate", arrayList.get(mPosition).getLostdate());
                intent.putExtra("losttime", arrayList.get(mPosition).getLosttime());
                intent.putExtra("image1", arrayList.get(mPosition).getImage1());
                intent.putExtra("image2", arrayList.get(mPosition).getImage2());
                intent.putExtra("image3", arrayList.get(mPosition).getImage3());
                intent.putExtra("name", arrayList.get(mPosition).getName());
                intent.putExtra("species", arrayList.get(mPosition).getSpecies());
                intent.putExtra("callnum", arrayList.get(mPosition).getCallnum());

                view.getContext().startActivity(intent); //액티비티 열기
            }
        });
    }

    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class LostAnimalViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        TextView lostlocation;
        TextView lostdate;
        TextView losttime;
        ImageView image1;
        CardView cardView;
        public LostAnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.losttitle);
            this.content = itemView.findViewById(R.id.lostcontent);
            this.lostlocation = itemView.findViewById(R.id.lostlocation);
            this.lostdate = itemView.findViewById(R.id.lostdate);
            this.losttime = itemView.findViewById(R.id.losttime);
            this.image1 = itemView.findViewById(R.id.lostimage);
            this.cardView = itemView.findViewById(R.id.lostanimal_container);
        }
    }
}
