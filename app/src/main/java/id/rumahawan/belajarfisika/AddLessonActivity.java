package id.rumahawan.belajarfisika;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import id.rumahawan.belajarfisika.Fragment.AddQuestionFragment;

public class AddLessonActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_lesson_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            Toast.makeText(this, "Save Button", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);
        getSupportActionBar().setTitle("Add Lesson");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContainer, new AddQuestionFragment())
                .commit();
    }
}
