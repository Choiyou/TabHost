package com.example.os150.tabhost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by os150 on 2018-11-04.
 */


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener  {
    private  static final int GET_FROM_GALLERY = 3;
    private static final String TAG = "ProfileActivity";

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    //view objects
    private TextView textViewUserEmail;
    private Button Using;
    private Button ChangePassword;
    private EditText Username;
    private ImageView Profileimage;
    private Button buttonLogout;
    private Button userDelete;
    private Button userImage;
    private Button usernameChange;
    private Uri filePath;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing views
        userImage = (Button) findViewById(R.id.userImage);
        textViewUserEmail = (TextView) findViewById(R.id.textviewUserEmail);
        buttonLogout = (Button) findViewById(R.id.btnLogout);
        userDelete = (Button) findViewById(R.id.userDelete);
        Username = (EditText) findViewById(R.id.username);
        Using = (Button) findViewById(R.id.Using);
        ChangePassword = (Button) findViewById(R.id.btnChangePassword);
        usernameChange = (Button) findViewById(R.id.usernameChange);
        Profileimage = (ImageView) findViewById(R.id.profileimage);
        firebaseAuth = FirebaseAuth.getInstance();

        Profileimage.setBackground(new ShapeDrawable(new OvalShape()));
        Profileimage.setClipToOutline(true);

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //유저가 있다면, null이 아니면 계속 진행
        FirebaseUser user = firebaseAuth.getCurrentUser();


        if(user.getPhotoUrl()!=null) {

            GlideApp.with(this).load(user.getPhotoUrl()).into(Profileimage);

        }else{
            Toast.makeText(getApplicationContext(),"프로필 이미지가 없습니다.",Toast.LENGTH_SHORT).show();
        }

        if(user.getDisplayName()==null) {
            textViewUserEmail.setText("반갑습니다.\n이름 및 프로필을 설정해주세요.");

        }else{
            textViewUserEmail.setText("반갑습니다.\n" + user.getDisplayName() + "  님");
            Username.setText(user.getDisplayName());
        }


        //logout button event
        Using.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        userDelete.setOnClickListener(this);
        ChangePassword.setOnClickListener(this);
        userImage.setOnClickListener(this);
        usernameChange.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == Using) {
            try {
                if (Username.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "이름을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if ( user.getPhotoUrl().toString() == null) {
                    Toast.makeText(this, "프로필을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    mDatabase = FirebaseDatabase.getInstance().getReference("users");


                    Userdata userdata = new Userdata(user.getDisplayName(), user.getPhotoUrl().toString(),user.getEmail());


                    mDatabase.child("users").child(user.getUid()).setValue(userdata);
                    finish();
                    startActivity(new Intent(this, MainActivity.class));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (view == buttonLogout) {

            try {
                if (Username.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "이름을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if ( user.getPhotoUrl().toString() == null) {
                    Toast.makeText(this, "프로필을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseAuth.signOut();
                    LoginManager.getInstance().logOut();
                    mDatabase = FirebaseDatabase.getInstance().getReference("users");
                    mDatabase.child("users").child(user.getUid()).setValue(null);

                    finish();
                    startActivity(new Intent(this, MembershipActivity.class));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        if (view == ChangePassword) {
            try {
                if (Username.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "이름을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if ( user.getPhotoUrl().toString() == null) {
                    Toast.makeText(this, "프로필을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    final EditText editCPassword = new EditText(ProfileActivity.this);
                    editCPassword.getTransformationMethod();
                    AlertDialog.Builder alert_confirm1 = new AlertDialog.Builder(ProfileActivity.this);
                    alert_confirm1.setTitle("비밀번호 변경");
                    alert_confirm1.setMessage("새 Password 입력");
                    alert_confirm1.setView(editCPassword);
                    alert_confirm1.setMessage("비밀번호 변경 이후 되돌릴 수 없습니다.").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    user.updatePassword(editCPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(ProfileActivity.this, "비밀번호가 변경 되었습니다.", Toast.LENGTH_LONG).show();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    });
                                }
                            }
                    );
                    alert_confirm1.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(ProfileActivity.this, "취소", Toast.LENGTH_LONG).show();
                        }
                    });
                    alert_confirm1.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //회원탈퇴를 클릭하면 회원정보를 삭제한다.
        if (view == userDelete) {
            try {
                if (Username.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "이름을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if ( user.getPhotoUrl().toString() == null) {
                    Toast.makeText(this, "프로필을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder alert_confirm2 = new AlertDialog.Builder(ProfileActivity.this);
                    alert_confirm2.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    user.delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(ProfileActivity.this, "계정이 삭제 되었습니다.", Toast.LENGTH_LONG).show();
                                                    finish();
                                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                }
                                            });
                                    mDatabase = FirebaseDatabase.getInstance().getReference("users");
                                    mDatabase.child("users").child(user.getUid()).setValue(null);

                                }

                            }
                    );
                    alert_confirm2.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(ProfileActivity.this, "취소", Toast.LENGTH_LONG).show();
                        }
                    });
                    alert_confirm2.show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (view == userImage) {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);


        }
        if (view == usernameChange) {

            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName(Username.getText().toString())
                    .build();
            user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (Username.getText().toString().length()==0) {
                        Toast.makeText(getApplicationContext(), "이름을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    }else {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "이름이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "name update success");
                            textViewUserEmail.setText("반갑습니다.\n" + user.getDisplayName() + "  님");

                        }
                    }


                }
            });


        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK && data != null) {

           filePath = data.getData();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            final String filename = formatter.format(now) + ".png";
            Log.d(TAG, "filename :" + filename);
            final StorageReference storageReference = storage.getReferenceFromUrl("gs://test-67176.appspot.com").child("userimage/" + filename);

            if (filePath != null) {

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("업로드 중..");
                progressDialog.show();


                storageReference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        GlideApp.with(getApplicationContext()).load(filePath).into(Profileimage);
                        Toast.makeText(getApplicationContext(), "업로드 완료", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "업로드 실패", Toast.LENGTH_SHORT).show();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        @SuppressWarnings("VisibleForTests")
                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded" + ((int) progress) + "% ...");

                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요", Toast.LENGTH_SHORT).show();

            }
            try {

                final Bitmap ibitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Profileimage.setImageBitmap(ibitmap);
                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(filePath)
                        .build();
                user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"성공"+filePath.toString(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "profileimage update success");
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }catch (Exception e) {
                e.printStackTrace();
            }


        }
    }



}

