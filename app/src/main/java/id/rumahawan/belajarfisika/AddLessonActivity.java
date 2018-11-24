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
        Boolean edit = false;
        if (!edit){
            getMenuInflater().inflate(R.menu.add_lesson_menu, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.edit_lesson_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            Toast.makeText(this, "Save Button", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.edit) {
            Toast.makeText(this, "Edit ditekan", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.delete) {
            Toast.makeText(this, "Delete ditekan", Toast.LENGTH_SHORT).show();
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
