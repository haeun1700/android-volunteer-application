package com.example.meong_nyang;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class VolunteerPoster extends AppCompatActivity {
  Intent intent;
  String profile;
  String center;
  String title;
  String day;
  String content;

  TextView title_tx, center_tx, day_tx, content_tx;
  ImageView imageView;
  Toolbar btnAdd;
  int count = 0;
  Uri uri;

  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.volunteer_post);
    center_tx = findViewById(R.id.center);
    day_tx = findViewById(R.id.day);
    content_tx = findViewById(R.id.content_et);
    imageView = findViewById(R.id.image);
    title_tx = findViewById(R.id.title);
    btnAdd = findViewById(R.id.toolbar);

    intent = getIntent();
    title = intent.getStringExtra("name");
    center = intent.getStringExtra("center");
    day = intent.getStringExtra("day");
    content = intent.getStringExtra("content").replace(" ▶", "\n▶");
    profile = intent.getStringExtra("image");

    title_tx.setText(title);
    center_tx.setText(center);
    day_tx.setText(day);
    content_tx.setText(content);

    uri = Uri.parse(profile);
    System.out.println("uri = " + imageView);
    Glide.with(VolunteerPoster.this).load(uri).into(imageView);

    btnAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AlertDialog.Builder ad = new AlertDialog.Builder(VolunteerPoster.this);
        ad.setIcon(R.mipmap.ic_launcher_round);
        ad.setTitle("재확인");
        ad.setMessage("봉사를 신청하시겠습니까?");

        final EditText et = new EditText(VolunteerPoster.this);
        ad.setView(et);

        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            count++;
            btnAdd.setTitle(count + "");
          }
        });
        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        });
        ad.show();
      }
    });

    BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

    //bottomNavigation 전환
    bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
      @SuppressLint("NonConstantResourceId")
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //if문으로 item.getItemId()값이 메뉴의 id값과 같은지 확인후 intent 로 activity로 전환
        if (item.getItemId() == R.id.info) {
          Intent intent = new Intent(VolunteerPoster.this, MainActivity2.class);
          startActivity(intent);
        } else if (item.getItemId() == R.id.missing) {
          Intent intent = new Intent(VolunteerPoster.this, LostAnimalBoardActivity.class);
          startActivity(intent);
        } else if (item.getItemId() == R.id.question) {
          Intent intent = new Intent(VolunteerPoster.this, QnAActivity.class);
          startActivity(intent);
        } else if (item.getItemId() == R.id.apply) {
          Intent intent = new Intent(VolunteerPoster.this, Volunteers.class);
          startActivity(intent);
        } else if (item.getItemId() == R.id.mypage) {
          Intent intent = new Intent(VolunteerPoster.this, Mypage.class);
          startActivity(intent);
        }
        return true; // return true;
      }
    });

  }


}