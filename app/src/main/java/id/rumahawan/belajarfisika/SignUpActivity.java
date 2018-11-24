package id.rumahawan.belajarfisika;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorSligthTranslucent));
        }

        ImageView ivUpParent = findViewById(R.id.ivUpParent);
        ivUpParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String[] teacher = {
                "Teacher",
                "Karin",
                "Ingrid",
                "Helga",
                "Renate",
                "Elke",
                "Ursula",
                "Erika",
                "Christa",
                "Gisela",
                "Monika"
        };
        MaterialSpinner spinner = findViewById(R.id.spTeacher);
        spinner.setItems(teacher);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        Button btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                SignUpActivity.this.finish();
            }
        });
    }
}
