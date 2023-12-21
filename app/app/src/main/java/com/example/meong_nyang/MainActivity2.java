package com.example.meong_nyang;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
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


public class MainActivity2 extends AppCompatActivity{
    SearchView searchView;
    RecyclerView recyclerView;
    CustomAdapter adapter;
   // RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<item> arrayList;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productlist);

        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.rv_list); // 아이디 연결
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // item 객체를 어댑터 쪽으로 담을 어레이 리스트
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        //bottomNavigation 전환
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //if문으로 item.getItemId()값이 메뉴의 id값과 같은지 확인후 intent 로 activity로 전환
                if (item.getItemId() == R.id.info) {
                    Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
                    startActivity(intent);
                }
                else if (item.getItemId() == R.id.missing) {
                    Intent intent = new Intent(MainActivity2.this, LostAnimalBoardActivity.class);
                    startActivity(intent);
                }
                else if (item.getItemId() == R.id.question) {
                    Intent intent = new Intent(MainActivity2.this, QnAActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.apply) {
                    Intent intent = new Intent(MainActivity2.this, Volunteers.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.mypage) {
                    Intent intent = new Intent(MainActivity2.this, Mypage.class);
                    startActivity(intent);
                }
                return true; // return true;
            }
        });
        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

        databaseReference = database.getReference("product"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문으로 데이터 리스트를 추출
                    // item 객체에 데이터 담는다
                    item item = snapshot.getValue(item.class);
                    arrayList.add(item); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // db를 가져오던 중 에러 발생 시
                Log.e("MainActivity2", String.valueOf(error.toException())); // 에러문 출력
            }
        });

        adapter = new CustomAdapter(arrayList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결

        //searchView 부분
        searchView.clearFocus();
        searchView.onActionViewExpanded();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //텍스트 읽어오기
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //검색창에 적은 문자 submit할 경우 실행
                adapter.getFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //검색창에 적은 문자가 바뀔 경우 실행
                adapter.getFilter(newText);
                return false;
            }
        });
    }

}



