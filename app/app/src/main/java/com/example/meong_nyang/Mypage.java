package com.example.meong_nyang;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class Mypage extends AppCompatActivity {
  private FirebaseAuth mFirebaseAuth;
  private DatabaseReference mDatabaseRef;
  TextView edictTextEmail, editTextName, edictTextPhone, editTextRegion;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mypage);

    mFirebaseAuth = FirebaseAuth.getInstance();
    mDatabaseRef = FirebaseDatabase.getInstance().getReference("straydog/UserAccount/" + mFirebaseAuth.getCurrentUser().getUid());
    edictTextEmail = findViewById(R.id.et_email);
    editTextName = findViewById(R.id.name);
    edictTextPhone = findViewById(R.id.phoneNo);
    editTextRegion = findViewById(R.id.region);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar); //액티비티의 앱바(App Bar)로 지정
    getSupportActionBar().setDisplayShowTitleEnabled(false); //앱바의 App Name 비활성화

//    mFirebaseAuth = FirebaseAuth.getInstance();
//    DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("straydog/UserAccount");

    Button btn_logout = findViewById(R.id.logout);
    btn_logout.setOnClickListener((view) -> {
              //로그아웃 하기
              mFirebaseAuth.signOut();
              Intent intent = new Intent(Mypage.this, Login.class);
              startActivity(intent);
              finish();
            }
    );


    //탈퇴 기능
    Button btn_delete = findViewById(R.id.user_delete);
    btn_delete.setOnClickListener((view) -> {
              revokeAccess();
              Toast.makeText(Mypage.this, "탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show();
              Intent intent = new Intent(Mypage.this, Login.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
              startActivity(intent);
            }
    );


    mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {


      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        Iterable<DataSnapshot> children = snapshot.getChildren();
        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
          String key = snapshot1.getKey();
          String value = snapshot1.getValue().toString();
          if (key.equals("email")) {
            edictTextEmail.setText(value);
          } else if (key.equals("phone")) {
            edictTextPhone.setText(value);
          } else if (key.equals("region")) {
            editTextRegion.setText(value);
          } else if (key.equals("name")) {
            editTextName.setText(value);
          }
          //          VolunteerPost volunteerpost = snapshot1.getValue(VolunteerPost.class);
//            담은 데이터 배열로 담고 리사이클러뷰로 보낼 준비
        }

//        adapter.notifyDataSetChanged();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
//         디비 가져오던 중 에러 발생 시
        Log.e("straydog", String.valueOf(error.toException()));
      }
    });
    BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

    //bottomNavigation 전환
    bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
      @SuppressLint("NonConstantResourceId")
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        //if문으로 item.getItemId()값이 메뉴의 id값과 같은지 확인후 intent 로 activity로 전환
        if (item.getItemId() == R.id.info) {
          intent = new Intent(Mypage.this, MainActivity2.class);
        } else if (item.getItemId() == R.id.missing) {
          intent = new Intent(Mypage.this, LostAnimalBoardActivity.class);
        } else if (item.getItemId() == R.id.question) {
          intent = new Intent(Mypage.this, QnAActivity.class);
        } else if (item.getItemId() == R.id.apply) {
          intent = new Intent(Mypage.this, Volunteers.class);
        } else if (item.getItemId() == R.id.mypage) {
          intent = new Intent(Mypage.this, Mypage.class);
        }
        startActivity(intent);
        return true; // return true;
      }
    });


  }

  private void revokeAccess() {
    mFirebaseAuth.getCurrentUser().delete();
  }


}