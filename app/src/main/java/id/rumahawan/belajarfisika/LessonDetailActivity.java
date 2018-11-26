package id.rumahawan.belajarfisika;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import id.rumahawan.belajarfisika.Data.Session;
import id.rumahawan.belajarfisika.Fragment.ViewQuestionFragment;
import id.rumahawan.belajarfisika.Object.Answer;
import id.rumahawan.belajarfisika.Object.Lesson;

public class LessonDetailActivity extends AppCompatActivity {
    private View vStatus;
    private TextView tvNama, tvSubjek, tvLevel, tvStatusTitle, tvStatusSkor, tvStatusOutOf100;
    private ImageView ivLesson;
    private ProgressBar progressBar;

    private ProgressDialog progressDialog;
    private Session session;

    private String lessonId, youtubeUrl;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        session.removeSession("lessonId");
        session.removeSession("questionId");
        session.removeSession("questionNumber");
        session.removeSession("arrayJawabanBenar");
        session.removeSession("arrayJawabanTerpilih");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (session.getSessionString("currentLevel").equals("teacher")){
            getMenuInflater().inflate(R.menu.edit_lesson_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            progressDialog.setMessage("Deleting");
            progressDialog.show();
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://belajar-fisika.firebaseio.com/Lesson");
            Query query = databaseReference.orderByChild("id").equalTo(lessonId);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        final DatabaseReference deleteLesson = databaseReference.child(postSnapshot.getKey());
                        deleteLesson.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                finish();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_detail);
        getSupportActionBar().setTitle("Lesson Detail");
        lessonId = getIntent().getExtras().getString("id");

        session = new Session(this);
        DatabaseReference databaseReferenceLesson = FirebaseDatabase.getInstance().getReferenceFromUrl("https://belajar-fisika.firebaseio.com/Lesson");
        Query queryLesson = databaseReferenceLesson.orderByChild("id").equalTo(lessonId);
        DatabaseReference databaseReferenceAnswer = FirebaseDatabase.getInstance().getReferenceFromUrl("https://belajar-fisika.firebaseio.com/Answer");
        Query queryAnswer = databaseReferenceAnswer.orderByChild("owner").equalTo(session.getSessionString("currentEmail"));
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        progressBar = findViewById(R.id.progressBar);
        tvNama = findViewById(R.id.tvNama);
        tvSubjek = findViewById(R.id.tvSubjek);
        tvLevel = findViewById(R.id.tvLevel);
        ivLesson = findViewById(R.id.ivLesson);
        ImageView ivPlayVideo = findViewById(R.id.ivPlayVideo);
        vStatus = findViewById(R.id.vStatus);
        tvStatusTitle = findViewById(R.id.tvStatusTitle);
        tvStatusSkor = findViewById(R.id.tvStatusSkor);
        tvStatusOutOf100 = findViewById(R.id.tvStatusOutOf100);

        ivLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl)));
                Log.i("Video", "Video Playing....");
            }
        });
        ivPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl)));
                Log.i("Video", "Video Playing....");
            }
        });

        queryLesson.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Lesson lesson = null;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    lesson = postSnapshot.getValue(Lesson.class);
                    session.setSessionString("lessonId", lesson.getId());
                }
                if (lesson != null) {
                    session.setSessionInt("questionNumber", 1);
                    loadUi(lesson);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        queryAnswer.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Answer selectedAnswer = null;
                boolean alreadyTakeQuiz = false;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Answer answer = postSnapshot.getValue(Answer.class);
                    if (lessonId.equals(answer.getLessonId())){
                        selectedAnswer = answer;
                        alreadyTakeQuiz = true;
                    }
                }
                if (alreadyTakeQuiz){
                    int benar = 0;
                    for (int i = 0;i < selectedAnswer.getSelectedAnswer().size();i++){
                        if (selectedAnswer.getSelectedAnswer().get(i).equals(selectedAnswer.getCorrectAnswer().get(i))){
                            benar++;
                        }
                    }
                    double pointPerSoal = 100 / selectedAnswer.getCorrectAnswer().size();
                    int skor = (int) Math.round(pointPerSoal * benar);
                    if (benar == selectedAnswer.getCorrectAnswer().size()){skor = 100;}

                    vStatus.setVisibility(View.VISIBLE);
                    tvStatusTitle.setVisibility(View.VISIBLE);
                    tvStatusSkor.setVisibility(View.VISIBLE);
                    tvStatusSkor.setText("" + skor);
                    tvStatusOutOf100.setVisibility(View.VISIBLE);
                }
                else{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flContainer, new ViewQuestionFragment())
                            .commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String videoId(String link){
        String[] splitedMobile = link.split(".be/");
        String[] splitedWeb = link.split(".com/watch\\?v=");
        if (splitedMobile.length >= 2){
            return splitedMobile[1];
        }
        else{
            return splitedWeb[1];
        }
    }

    private String thumbnailUrl(String videoId){
        return "https://img.youtube.com/vi/" + videoId + "/0.jpg";
    }

    @SuppressLint("SetTextI18n")
    private void loadUi(Lesson lesson){
        progressBar.setVisibility(View.GONE);

        tvNama.setText(lesson.getName());
        tvSubjek.setText(lesson.getSubject());
        tvLevel.setText(getString(R.string.level) + " " + lesson.getLevel());

        youtubeUrl = lesson.getYoutubeUrl();

        Picasso.get()
                .load(thumbnailUrl(videoId(youtubeUrl)))
                .placeholder(R.color.colorSligthTranslucent)
                .fit()
                .centerCrop()
                .into(ivLesson);
    }
}
