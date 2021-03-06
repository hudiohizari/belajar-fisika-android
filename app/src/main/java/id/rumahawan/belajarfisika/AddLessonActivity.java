package id.rumahawan.belajarfisika;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import id.rumahawan.belajarfisika.Data.Session;
import id.rumahawan.belajarfisika.Fragment.AddQuestionFragment;

public class AddLessonActivity extends AppCompatActivity {
    private Session session;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    private TextInputLayout tilLessonName, tilSubject, tilLevel, tilLinkVideo;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        session.removeSession("lessonId");
        session.removeSession("questionNumber");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_lesson_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if (isLessonClean()){
                pushLesson();

            }else {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Complete the form before uploading lesson", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                        .show();
            }
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

        session = new Session(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://belajar-fisika.firebaseio.com/Lesson");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        tilLessonName = findViewById(R.id.tilEmail);
        tilSubject = findViewById(R.id.tilSubject);
        tilLevel = findViewById(R.id.tilLevel);
        tilLinkVideo = findViewById(R.id.tilLinkVideo);

        java.util.Random random = new java.util.Random();
        session.setSessionString("lessonId", ""  + random.nextInt((9999 - 1) + 1 ) + 1);
        session.setSessionInt("questionNumber", 1);
    }

    private boolean isLessonClean(){
        return !tilLessonName.getEditText().getText().toString().equals("") &&
                !tilSubject.getEditText().getText().toString().equals("") &&
                !tilLevel.getEditText().getText().toString().equals("") &&
                !tilLinkVideo.getEditText().getText().toString().equals("");
    }

    private boolean isUrlVideoCorrect(String link) {
        String[] splitedMobile = link.split(".be/");
        String[] splitedWeb = link.split(".com/watch\\?v=");
        return splitedMobile.length >= 2 || splitedWeb.length >= 2;
    }

    private void pushLesson(){
        progressDialog.setMessage("Uploading lesson");
        progressDialog.show();

        if (isUrlVideoCorrect(tilLinkVideo.getEditText().getText().toString())){
            DatabaseReference newLesson = databaseReference.push();
            newLesson.child("id").setValue(session.getSessionString("lessonId"));
            newLesson.child("name").setValue(tilLessonName.getEditText().getText().toString());
            newLesson.child("subject").setValue(tilSubject.getEditText().getText().toString());
            newLesson.child("level").setValue(tilLevel.getEditText().getText().toString());
            newLesson.child("youtubeUrl").setValue(tilLinkVideo.getEditText().getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            finish();
                        }
                    })
            ;
        }
        else{
            progressDialog.dismiss();
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Youtube link is incorrect", Snackbar.LENGTH_LONG)
                    .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        }
    }
}
