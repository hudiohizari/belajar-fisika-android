package id.rumahawan.belajarfisika;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import id.rumahawan.belajarfisika.Data.Session;
import id.rumahawan.belajarfisika.Fragment.ViewQuestionFragment;
import id.rumahawan.belajarfisika.Object.Lesson;

public class LessonDetailActivity extends AppCompatActivity {
    private TextView tvNama, tvSubjek, tvLevel;
    private ImageView ivLesson;
    private ProgressBar progressBar;

    private Session session;

    private String youtubeUrl;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        session.removeSession("lessonId");
        session.removeSession("questionId");
        session.removeSession("questionNumber");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_detail);
        getSupportActionBar().setTitle("Lesson Detail");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContainer, new ViewQuestionFragment())
                .commit();
        String lessonId = getIntent().getExtras().getString("id");

        session = new Session(this);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://belajar-fisika.firebaseio.com/Lesson");
        Query query = databaseReference.orderByChild("id").equalTo(lessonId);

        progressBar = findViewById(R.id.progressBar);
        tvNama = findViewById(R.id.tvNama);
        tvSubjek = findViewById(R.id.tvSubjek);
        tvLevel = findViewById(R.id.tvLevel);
        ivLesson = findViewById(R.id.ivLesson);
        ImageView ivPlayVideo = findViewById(R.id.ivPlayVideo);

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


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Lesson lesson = null;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    lesson = postSnapshot.getValue(Lesson.class);
                    session.setSessionString("lessonId", lesson.getId());
                    session.setSessionInt("questionNumber", 1);
                }
                if (lesson != null) {
                    loadUi(lesson);
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
