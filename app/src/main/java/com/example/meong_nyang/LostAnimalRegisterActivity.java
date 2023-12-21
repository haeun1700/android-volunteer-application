package com.example.meong_nyang;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;

import javax.annotation.meta.When;

public class LostAnimalRegisterActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private RecyclerView.Adapter adapter;
    private Uri[] uris = new Uri[3];
    private ImageButton selectImage;
    private Dialog dialog1, dialog2;
    private int count = 0;
    private ImageView[] imageViews = new ImageView[3];
    private EditText lostlocation, losttime, lostdate, name, species, callnum, title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostanimal_register);

        //add toolbar
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        getSupportActionBar().setDisplayShowTitleEnabled(false); //앱바의 App Name 비활성화
        ActionBar actionBar = getSupportActionBar(); //앱바 제어를 위해 툴바 액세스
        actionBar.setDisplayHomeAsUpEnabled (true); //앱바에 뒤로가기 버튼 만들기

        selectImage = findViewById(R.id.selectimage);

        imageViews[0] = findViewById(R.id.imageView1);
        imageViews[1] = findViewById(R.id.imageView2);
        imageViews[2] = findViewById(R.id.imageView3);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select();
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
                    Intent intent = new Intent(LostAnimalRegisterActivity.this, MainActivity2.class);
                    startActivity(intent);
                }
                else if (item.getItemId() == R.id.missing) {
                    Intent intent = new Intent(LostAnimalRegisterActivity.this, LostAnimalBoardActivity.class);
                    startActivity(intent);
                }
                else if (item.getItemId() == R.id.question) {
                    Intent intent = new Intent(LostAnimalRegisterActivity.this, QnAActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.apply) {
                    Intent intent = new Intent(LostAnimalRegisterActivity.this, Volunteers.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.mypage) {
                    Intent intent = new Intent(LostAnimalRegisterActivity.this, Mypage.class);
                    startActivity(intent);
                }
                return true; // return true;
            }
        });
    }

    private void select() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT );
        intent.setType("image/*");
        launcher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();

                    // Determine the ImageView to set the image
                    ImageView targetImageView = determineTargetImageView();

                    // Set the image to the determined ImageView
                    if (targetImageView != null) {
                        int index = Arrays.asList(imageViews).indexOf(targetImageView);
                        uris[index] = uri;
                        targetImageView.setImageURI(uri);
                        Log.d("test", uri.toString());
                    } else {
                        // Handle the case where all ImageViews are occupied
                        Toast.makeText(LostAnimalRegisterActivity.this, "최대 3개의 이미지만 업로드 할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    }

                }
            });

    private ImageView determineTargetImageView() {
        for (ImageView imageView : imageViews) {
            if (imageView.getDrawable() == null) {
                return imageView;
            }
        }
        return null;
    }

    // CustomToolBar 반영하기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate (R.menu.lostanimalregister_menu_top, menu);
        return true;
    }

    // Toolbar button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        dialog1 = new Dialog(LostAnimalRegisterActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.activity_popup_complete);

        dialog2 = new Dialog(LostAnimalRegisterActivity.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.activity_popup_cancel);

        if (item.getItemId() == R.id.registercompletebtn) {
            popupcomplete();

            return true;
        } else if (item.getItemId() == android.R.id.home) {
            popupcancel();

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
                // count images;
                for (int i = 0; i<3; i++) {
                    if (imageViews[i].getDrawable() != null && uris[i] != null) {
                        count = count + 1;
                    }
                }
                if(count == 0) {
                    Toast.makeText(LostAnimalRegisterActivity.this, "최소 하나의 사진을 첨부해주세요.", Toast.LENGTH_SHORT).show();
                    dialog1.dismiss();
                }
                else {
                    upload();
                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }

    private void popupcancel() {
        dialog2.show();
        Button yes = dialog2.findViewById(R.id.btn_yes);
        Button no = dialog2.findViewById(R.id.btn_no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LostAnimalBoardActivity.class);
                startActivity(intent);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
    }

    private void upload() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("meong-nyang");

        // Create an array to store download URLs
        String[] downloadUrls = new String[3];


        for (int i = 0; i < count; i++) {
            if (imageViews[i].getDrawable() != null && uris[i] != null) {
                final int index = i;
                storageReference.child("images/image" + (index + 1)).putFile(uris[i]).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            // Get the download URL of the uploaded image
                            storageReference.child("images/image" + (index + 1)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    // Save the image URL to the array
                                    downloadUrls[index] = downloadUri.toString();

                                    // Check if all images have been uploaded
                                    if (index == count-1) {
                                        // All images have been uploaded, save URLs to the Realtime Database
                                        saveImageUrlToDatabase(downloadUrls);
                                        Intent intent = new Intent(getApplicationContext(), LostAnimalBoardActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    private void saveImageUrlToDatabase(String[] imageUrls) {
        // Define object
        title = findViewById(R.id.title_et);
        content = findViewById(R.id.content_et);
        lostlocation = findViewById(R.id.lostlocation_et);
        lostdate = findViewById(R.id.lostdate_et);
        losttime = findViewById(R.id.losttime_et);
        name = findViewById(R.id.name_et);
        species = findViewById(R.id.species_et);
        callnum = findViewById(R.id.callnum_et);

        String ttitle = title.getText().toString();
        String tcontent = content.getText().toString();
        String tlostlocation = lostlocation.getText().toString();
        String tlostdate = lostdate.getText().toString();
        String tlosttime = losttime.getText().toString();
        String tname = name.getText().toString();
        String tspecies = species.getText().toString();
        String tcallnum = callnum.getText().toString();

        // Create a new LostAnimal object with relevant information
        LostAnimal lostAnimal = new LostAnimal();

        lostAnimal.setTitle(ttitle);
        lostAnimal.setContent(tcontent);
        lostAnimal.setLostlocation(tlostlocation);
        lostAnimal.setLostdate(tlostdate);
        lostAnimal.setLosttime(tlosttime);
        lostAnimal.setSpecies(tspecies);
        lostAnimal.setName(tname);
        lostAnimal.setCallnum(tcallnum);

        // Set image URLs based on the number of uploaded images
        if (imageUrls.length >= 1) {
            lostAnimal.setImage1(imageUrls[0]);
        }
        if (imageUrls.length >= 2) {
            lostAnimal.setImage2(imageUrls[1]);
        }
        if (imageUrls.length >= 3) {
            lostAnimal.setImage3(imageUrls[2]);
        }

        // Push the LostAnimal object to Firebase Realtime Database
        mDatabase.child("LostAnimal").push().setValue(lostAnimal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LostAnimalRegisterActivity.this, "작성을 완료했습니다", Toast.LENGTH_SHORT).show();
                    // Clear or navigate to another screen after successful upload
                } else {
                    Toast.makeText(LostAnimalRegisterActivity.this, "작성을 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}