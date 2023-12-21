package com.example.meong_nyang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProductDetailActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Intent intent;
    String name;
    String image;
    ProgressBar progress;
    //ArrayList<item> details;
    TextView textView1;

    RecyclerView.Adapter adapter;

    RecyclerView.LayoutManager layoutManager;

   ArrayList<productPrice> prices;
   RecyclerView recyclerView;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetail);

        textView = findViewById(R.id.name);
        imageView = findViewById(R.id.imageView);
        intent = getIntent();
        name = intent.getStringExtra("name");
        image = intent.getStringExtra("image");
        prices = new ArrayList<>();
        progress = (ProgressBar) findViewById(R.id.progressBar);
        textView1 = findViewById(R.id.textView1);
        recyclerView = findViewById(R.id.priceList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //bottomNavigation 전환
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //if문으로 item.getItemId()값이 메뉴의 id값과 같은지 확인후 intent 로 activity로 전환
                if (item.getItemId() == R.id.info) {
                    Intent intent = new Intent(ProductDetailActivity.this, MainActivity2.class);
                    startActivity(intent);
                }
                else if (item.getItemId() == R.id.missing) {
                    Intent intent = new Intent(ProductDetailActivity.this, LostAnimalBoardActivity.class);
                    startActivity(intent);
                }
                else if (item.getItemId() == R.id.question) {
                    Intent intent = new Intent(ProductDetailActivity.this, QnAActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.apply) {
                    Intent intent = new Intent(ProductDetailActivity.this, Volunteers.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.mypage) {
                    Intent intent = new Intent(ProductDetailActivity.this, Mypage.class);
                    startActivity(intent);
                }
                return true; // return true;
            }
        });

        textView.setText(name);

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://product-d7a77.appspot.com");
        StorageReference storageRef = storage.getReference();
        StorageReference submitImage = storageRef.child(image);

        submitImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ProductDetailActivity.this).load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });

        //상세 정보에 보여질 item 정보 불러오기
         databaseReference = FirebaseDatabase.getInstance().getReference("product");
        Query myTopPostsQuery = databaseReference.orderByChild("name").equalTo(name);

       myTopPostsQuery.addValueEventListener(new ValueEventListener()  {
           @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    item product = dataSnapshot.getValue(item.class);
                    progress.setProgress(product.getPercent());
                    textView1.setText(product.getDetail());
                   readCode(product.getCode()); //각 판매처 가격 정보 불러오기
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity2", "onCancelled");
            }
        });


    }

    //item code로 각 판매처 가격 정보 불러오기
    void readCode(String code){
        databaseReference = database.getInstance().getReference("price comparison");
        databaseReference = database.getInstance().getReference("price comparison").child(code);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                prices.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문으로 데이터 리스트를 추출
                    // item 객체에 데이터 담는다
                    productPrice price = snapshot.getValue(productPrice.class);
                    prices.add(price);// 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비

                }

                //prices 오름차순으로 정렬
                Collections.sort(prices, new Comparator<productPrice>() {
                    @Override
                    public int compare(productPrice productPrice, productPrice t1) {
                        return productPrice.getPrice()-t1.getPrice();
                    }
                });
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // db를 가져오던 중 에러 발생 시
                Log.e("MainActivity", String.valueOf(error.toException())); // 에러문 출력
            }
        });

        adapter = new ListAdapter(prices, this);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
