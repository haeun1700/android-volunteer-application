package com.example.meong_nyang;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
  private Dialog dialog1;

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

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar); //액티비티의 앱바(App Bar)로 지정
    getSupportActionBar().setDisplayShowTitleEnabled(false); //앱바의 App Name 비활성화
    ActionBar actionBar = getSupportActionBar(); //앱바 제어를 위해 툴바 액세스

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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.volunteeraplication_menu_top, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {

    dialog1 = new Dialog(VolunteerPoster.this);
    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog1.setContentView(R.layout.activity_popup_volunteer);

    if (item.getItemId() == R.id.aplication) {
      popupcomplete();

      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void popupcomplete() {
    dialog1.show();
    Button yes = dialog1.findViewById(R.id.btn_yes);
    Button no = dialog1.findViewById(R.id.btn_no);

    yes.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        count++;
        btnAdd.setTitle(count + "");
        Toast.makeText(VolunteerPoster.this, "해당 봉사 신청이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
        dialog1.dismiss();
      }
    });

    no.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog1.dismiss();
      }
    });
  }
}

