package id.rumahawan.belajarfisika;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import id.rumahawan.belajarfisika.Data.Session;
import id.rumahawan.belajarfisika.Object.User;

public class LoginActivity extends AppCompatActivity {
    private View parentLayout;
    private TextInputLayout tilEmail, tilPassword;

    private Session session;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private Query query;

    private static String encryptPassword(String password){

        MessageDigest crypt = null;
        try {
            crypt = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        crypt.reset();
        try {
            crypt.update(password.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new BigInteger(1, crypt.digest()).toString(16);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorSligthTranslucent));
        }

        session = new Session(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://belajar-fisika.firebaseio.com/User");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        parentLayout = findViewById(android.R.id.content);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        ImageView ivUpParent = findViewById(R.id.ivUpParent);
        ivUpParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnLogin = findViewById(R.id.btnSignUp);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Checking");
                progressDialog.show();

                if (isLoginClean()){
                    query = databaseReference.orderByChild("email").equalTo(tilEmail.getEditText().getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() == 1){
                                User user = null;
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    user = postSnapshot.getValue(User.class);
                                }
                                if (encryptPassword(tilPassword.getEditText().getText().toString()).equals(user.getPassword())){
                                    doneProcessing();

                                    session.setSessionString("currentEmail", user.getEmail());
                                    session.setSessionString("currentName", user.getName());
                                    session.setSessionString("currentLevel", user.getLevel());
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                }
                                else {
                                    doneProcessing();
                                    Snackbar.make(parentLayout, "Wrong password", Snackbar.LENGTH_LONG)
                                            .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                            .show();
                                }
                            }
                            else{
                                doneProcessing();
                                Snackbar.make(parentLayout, "Email not registered", Snackbar.LENGTH_LONG)
                                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                        .show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            doneProcessing();
                            Snackbar.make(parentLayout, "Connection error", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                    .show();
                        }
                    });
                }else{
                    doneProcessing();
                    Snackbar.make(parentLayout, "Complete the form before continue", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                            .show();
                }
            }
        });

        TextView tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void doneProcessing(){
        progressDialog.dismiss();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private boolean isLoginClean(){
        return !tilEmail.getEditText().getText().toString().equals("") &&
                !tilPassword.getEditText().getText().toString().equals("");
    }
}
