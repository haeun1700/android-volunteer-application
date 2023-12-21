package com.example.meong_nyang;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.VolunteerViewHolder> {

  private final ArrayList<VolunteerPost> arrayList;
  private final Context context;
  private Intent intent;

  public VolunteerAdapter(ArrayList<VolunteerPost> arrayList, Context context) {
    this.arrayList = arrayList;
    this.context = context;
  }

  @NonNull
  @Override
  public VolunteerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_volunteers, parent, false);
    VolunteerViewHolder holder = new VolunteerViewHolder(view);
    return holder;
//        return new VolunteerViewHolder(parent);
  }

  @Override
//    //각 아이템 실제 매칭
  public void onBindViewHolder(@NonNull VolunteerViewHolder holder, int position) {
    Glide.with(holder.itemView)
      .load(arrayList.get(position).getProfile())
      .into(holder.iv_profile);
    holder.v_center.setText(arrayList.get(position).getCenter());
    holder.v_title.setText(arrayList.get(position).getTitle());
    holder.v_day.setText(arrayList.get(position).getDay());
    holder.itemView.setOnClickListener((view) -> {
      int mPosition = holder.getAdapterPosition();

      intent = new Intent(view.getContext(), VolunteerPoster.class);
      //정보 넘기기
      intent.putExtra("name", arrayList.get(mPosition).getTitle());
      intent.putExtra("image", arrayList.get(mPosition).getProfile());//변수값 인텐트로 넘기기
      intent.putExtra("center", arrayList.get(mPosition).getCenter());
      intent.putExtra("day", arrayList.get(mPosition).getDay());
      intent.putExtra("content", arrayList.get(mPosition).getContent());
      view.getContext().startActivity(intent); //액티비티 열기
    });
  }

  @Override
  public int getItemCount() {
    return (arrayList != null ? arrayList.size() : 0);
  }

  public class VolunteerViewHolder extends RecyclerView.ViewHolder {


    ImageView iv_profile;
    TextView v_center;
    TextView v_title;

    TextView v_day;

    public VolunteerViewHolder(@NonNull View itemView) {
      super(itemView);
      this.iv_profile = itemView.findViewById(R.id.profile);
      this.v_center = itemView.findViewById(R.id.center);
      this.v_title = itemView.findViewById(R.id.title);
      this.v_day = itemView.findViewById(R.id.day);
    }
  }
}
