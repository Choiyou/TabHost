package com.example.os150.tabhost;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.os150.tabhost.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by os150 on 2018-11-03.
 */

public class SigninActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textViewSignin;
    TextView textViewMessage;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextUserName;
    Button btnSignup;
    ProgressDialog progressDialog; // 회원등록에 사용
    FirebaseAuth firebaseAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        firebaseAuth = FirebaseAuth.getInstance();

        //로그인 된 경우
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        textViewMessage = (TextView) findViewById(R.id.textViewMessage);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        progressDialog = new ProgressDialog(this);

        btnSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }
    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }

        progressDialog.setMessage("등록중입니다. 기다려 주세요...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            textViewMessage.setText("에러유형\n - 이미 등록된 이메일  \n -암호 최소 6자리 이상 \n - 서버에러");
                            Toast.makeText(SigninActivity.this, "등록 에러!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == btnSignup) {
            //TODO
            registerUser();
        }

        if(view == textViewSignin) {
            //TODO
            startActivity(new Intent(this, LoginActivity.class)); //추가해 줄 로그인 액티비티
        }
    }
}