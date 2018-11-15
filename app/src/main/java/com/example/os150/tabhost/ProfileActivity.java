package com.example.os150.tabhost;

        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
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
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.auth.UserInfo;

/**
 * Created by os150 on 2018-11-04.
 */


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ProfileActivity";

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button Using;
    private Button ChangePassword;
    private EditText Username;
    private ImageView imageView;
    private Button buttonLogout;
    private Button userDelete;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textviewUserEmail);
        buttonLogout = (Button) findViewById(R.id.btnLogout);
        userDelete = (Button) findViewById(R.id.userDelete);
        Username = (EditText) findViewById(R.id.username);
        Using = (Button) findViewById(R.id.Using);
        ChangePassword =(Button)findViewById(R.id.btnChangePassword);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //유저가 있다면, null이 아니면 계속 진행
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user.getPhotoUrl()!=null){
            String photoUrl = user.getPhotoUrl().toString();
            ImageView photoImageView = (ImageView) findViewById(R.id.profileimage);
            Glide.with(this).load(photoUrl).into(photoImageView);
            
        }

        //textViewUserEmail의 내용을 변경해 준다.
        textViewUserEmail.setText("반갑습니다.\n"+ user.getEmail()+"  님");
        Username.setText("사용자 이름 : "+ user.getDisplayName());
        //logout button event
        Using.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        userDelete.setOnClickListener(this);
        ChangePassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == Using){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        if(view == ChangePassword){
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
//            finish();
//            startActivity(new Intent(this,FindActivity.class));
            }

        //회원탈퇴를 클릭하면 회원정보를 삭제한다.
        if(view == userDelete) {
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
    }
}
