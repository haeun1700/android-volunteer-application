package com.example.meong_nyang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Volunteers extends AppCompatActivity {
  private RecyclerView recyclerView;
  private RecyclerView.Adapter adapter;
  private RecyclerView.LayoutManager layoutManager;
  private ArrayList<VolunteerPost> arrayList;
  private FirebaseDatabase database;
  private DatabaseReference databaseReference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.volunteerlist);

    recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    arrayList = new ArrayList<>(); //봉사리스트 객체를 담을 어레이 리스트

    database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
    databaseReference = database.getReference("straydog/VolunteerPost");
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
//        파이어베이스 데이터베이스의 데이터를 받아오는 곳
        arrayList.clear();
//        System.out.println("snapshot = " + snapshot);
        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
          VolunteerPost volunteerpost = snapshot1.getValue(VolunteerPost.class);
          arrayList.add(volunteerpost); // 담은 데이터 배열로 담고 리사이클러뷰로 보낼 준비
        }

        adapter.notifyDataSetChanged();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
//         디비 가져오던 중 에러 발생 시
        Log.e("straydog", String.valueOf(error.toException()));
      }
    });
    adapter = new VolunteerAdapter(arrayList, this);
    recyclerView.setAdapter(adapter); //리사이클러뷰에 어뎁터 연결
//    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
//    recyclerView.setLayoutManager(gridLayoutManager);



    BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

    //bottomNavigation 전환
    bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
      @SuppressLint("NonConstantResourceId")
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //if문으로 item.getItemId()값이 메뉴의 id값과 같은지 확인후 intent 로 activity로 전환
        if (item.getItemId() == R.id.info) {
          Intent intent = new Intent(Volunteers.this, MainActivity2.class);
          startActivity(intent);
        } else if (item.getItemId() == R.id.missing) {
          Intent intent = new Intent(Volunteers.this, LostAnimalBoardActivity.class);
          startActivity(intent);
        } else if (item.getItemId() == R.id.question) {
          Intent intent = new Intent(Volunteers.this, QnAActivity.class);
          startActivity(intent);
        } else if (item.getItemId() == R.id.apply) {
          Intent intent = new Intent(Volunteers.this, Volunteers.class);
          startActivity(intent);
        } else if (item.getItemId() == R.id.mypage) {
          Intent intent = new Intent(Volunteers.this, Mypage.class);
          startActivity(intent);
        }
        return true; // return true;
      }
    });


  }
}