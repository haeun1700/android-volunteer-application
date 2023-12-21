package com.example.meong_nyang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class LostAnimalBoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<LostAnimal> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostanimal_board);

        //add toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //액티비티의 앱바(App Bar)로 지정
        getSupportActionBar().setDisplayShowTitleEnabled(false); //앱바의 App Name 비활성화

        recyclerView = findViewById(R.id.recyclerView); // 리사이클러뷰 연결
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // LostAnimal 객체 담을 Arraylist

        database = FirebaseDatabase.getInstance(); // Firebase Database Connect

        databaseReference = database.getReference("LostAnimal"); // DB Table Connect
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Firebase Database 데이터를 받아오는 곳
                arrayList.clear(); // ArrayList init
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) { // 반복문으로 데이터 List 추출
                    LostAnimal lostAnimal = dataSnapshot1.getValue(LostAnimal.class); // LostAnimal 객체에 데이터 전송
                    arrayList.add(lostAnimal); // 전송된 데이터 arraylist에 넣어 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB error
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show(); // print error
                Log.e("RecyclerActivity", String.valueOf(databaseError.toException()));
            }
        });

        adapter = new LostAnimalAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); // Recyclerview adapter connect

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //bottomNavigation 전환
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //if문으로 item.getItemId()값이 메뉴의 id값과 같은지 확인후 intent 로 activity로 전환
                if (item.getItemId() == R.id.info) {
                    Intent intent = new Intent(LostAnimalBoardActivity.this, MainActivity2.class);
                    startActivity(intent);
                }
                else if (item.getItemId() == R.id.missing) {
                    Intent intent = new Intent(LostAnimalBoardActivity.this, LostAnimalBoardActivity.class);
                    startActivity(intent);
                }
                else if (item.getItemId() == R.id.question) {
                    Intent intent = new Intent(LostAnimalBoardActivity.this, QnAActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.apply) {
                    Intent intent = new Intent(LostAnimalBoardActivity.this, Volunteers.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.mypage) {
                    Intent intent = new Intent(LostAnimalBoardActivity.this, Mypage.class);
                    startActivity(intent);
                }
                return true; // return true;
            }
        });
    }

    // Inflate ; define xml menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lostanimalboard_menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.registerbtn) {
            // Handle the "Register" menu item click
            startActivity(new Intent(this, LostAnimalRegisterActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}