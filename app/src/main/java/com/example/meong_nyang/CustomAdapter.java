package com.example.meong_nyang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

        private ArrayList<item> arrayList;
        private ArrayList<item> arrayListAll;
        private Context context;
        private Intent intent;//인텐트 선언

    public CustomAdapter(ArrayList<item> arrayList, Context context) {
            this.arrayList = arrayList;
            this.arrayListAll = arrayList;
            this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    public void setFilter(ArrayList<item> filterList) {
        this.arrayList = filterList;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://fir-test-e9493.appspot.com");
        StorageReference storageRef = storage.getReference();
        storageRef.child(arrayList.get(position).getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시

                Glide.with(holder.itemView)
                        .load(uri)
                        .into(holder.image);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                Toast.makeText(context.getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });
        holder.textListTitle.setText(arrayList.get(position).getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = holder.getAdapterPosition();

                intent = new Intent(view.getContext(), ProductDetailActivity.class);
                intent.putExtra("name", arrayList.get(mPosition).getName());
                intent.putExtra("image", arrayList.get(mPosition).getImage());//변수값 인텐트로 넘기기
                view.getContext().startActivity(intent); //액티비티 열기

            }

        });

    }//OnBindViewHolder()

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public void getFilter(String text) {
        ArrayList<item> FilterList = new ArrayList<>();
        for (item item:arrayListAll){
            if (item.getName().toLowerCase().contains(text.toLowerCase())){
                FilterList.add(item);
            }
        }

        if (FilterList.isEmpty()){
            Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
        }else {
            setFilter(FilterList);
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textListTitle;
        CardView cardView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.image = itemView.findViewById(R.id.item);
            this.textListTitle = itemView.findViewById(R.id.textListTitle);
            this.cardView = itemView.findViewById(R.id.layout_container);
        }
    }


}
