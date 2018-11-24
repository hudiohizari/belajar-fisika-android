package id.rumahawan.belajarfisika;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import id.rumahawan.belajarfisika.Fragment.AddQuestionFragment;
import id.rumahawan.belajarfisika.Fragment.ViewQuestionFragment;

public class LessonDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_detail);
        getSupportActionBar().setTitle("Lesson Detail");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContainer, new ViewQuestionFragment())
                .commit();

        ImageView ivLesson = findViewById(R.id.ivLesson);
        final String originalUrl = "https://www.youtube.com/watch?v=2ccaHpy5Ewo";
        String videoId = "2ccaHpy5Ewo";
        String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/0.jpg";
        Picasso.get()
                .load(thumbnailUrl)
                .placeholder(R.color.colorSligthTranslucent)
                .fit()
                .centerCrop()
                .into(ivLesson);
        ivLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(originalUrl)));
                Log.i("Video", "Video Playing....");
            }
        });

        ImageView ivPlayVideo = findViewById(R.id.ivPlayVideo);
        ivPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(originalUrl)));
                Log.i("Video", "Video Playing....");
            }
        });
    }
}
