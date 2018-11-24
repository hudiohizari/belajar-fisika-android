package id.rumahawan.belajarfisika;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorSligthTranslucent));
        }

        Button btnStudent = findViewById(R.id.btnStudent);
        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class));
            }
        });

        Button btnTeacher = findViewById(R.id.btnTeacher);
        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class));
            }
        });

        TextView tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnBoardingActivity.this, SignUpActivity.class));
            }
        });
    }
}
