package com.example.meong_nyang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class QnAActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_qna);

        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        getSupportActionBar().setDisplayShowTitleEnabled(false); //앱바의 App Name 비활성화

        Button animalregisterbtn = (Button) findViewById(R.id.animalregisterbtn);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //bottomNavigation 전환
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //if문으로 item.getItemId()값이 메뉴의 id값과 같은지 확인후 intent 로 activity로 전환
                if (item.getItemId() == R.id.info) {
                    Intent intent = new Intent(QnAActivity.this, MainActivity2.class);
                    startActivity(intent);
                }
                else if (item.getItemId() == R.id.missing) {
                    Intent intent = new Intent(QnAActivity.this, LostAnimalBoardActivity.class);
                    startActivity(intent);
                }
                else if (item.getItemId() == R.id.question) {
                    Intent intent = new Intent(QnAActivity.this, QnAActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.apply) {
                    Intent intent = new Intent(QnAActivity.this, Volunteers.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.mypage) {
                    Intent intent = new Intent(QnAActivity.this, Mypage.class);
                    startActivity(intent);
                }
                return true; // return true;
            }
        });
        animalregisterbtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QnAAnimalRegisterActivity.class);
                startActivity(intent);
            }
        });

        Button healthinfobtn = (Button) findViewById(R.id.healthinfobtn);
        healthinfobtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QnAHealthInfoActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.qna_menu_top, menu);
        return true;
    }
}
