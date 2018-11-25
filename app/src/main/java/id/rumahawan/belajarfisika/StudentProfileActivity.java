package id.rumahawan.belajarfisika;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.rumahawan.belajarfisika.Object.Answer;
import id.rumahawan.belajarfisika.Object.Lesson;
import id.rumahawan.belajarfisika.Object.ThreeItems;
import id.rumahawan.belajarfisika.Object.User;
import id.rumahawan.belajarfisika.RecyclerViewAdapter.ThreeItemsListStyle2Adapter;

public class StudentProfileActivity extends AppCompatActivity {
    private DatabaseReference databaseReferenceAnswer;
    private DatabaseReference databaseReferenceLesson;
    private ThreeItemsListStyle2Adapter adapter;
    private ArrayList<ThreeItems> arrayList;

    private ProgressBar progressBar;
    private TextView tvNodata, tvNama, tvKelas, tvNomorInduk;

    private String userEmail;

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private StudentProfileActivity.ClickListener clicklistener;
        private GestureDetector gestureDetector;

        RecyclerTouchListener(Context context, final StudentProfileActivity.ClickListener clicklistener){

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child != null && clicklistener != null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        getSupportActionBar().setTitle("Student Profile");
        userEmail = getIntent().getExtras().getString("id");

        DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReferenceFromUrl("https://belajar-fisika.firebaseio.com/User");
        databaseReferenceAnswer = FirebaseDatabase.getInstance().getReferenceFromUrl("https://belajar-fisika.firebaseio.com/Answer");
        databaseReferenceLesson = FirebaseDatabase.getInstance().getReferenceFromUrl("https://belajar-fisika.firebaseio.com/Lesson");

        progressBar = findViewById(R.id.progressBar);
        tvNodata = findViewById(R.id.tvNoData);
        tvNama = findViewById(R.id.tvNama);
        tvKelas = findViewById(R.id.tvKelas);
        tvNomorInduk = findViewById(R.id.tvNomorInduk);

        Query queryUser = databaseReferenceUser.orderByChild("email").equalTo(userEmail);
        queryUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    tvNama.setText(user.getName());
                    tvKelas.setText(user.getClasc());
                    tvNomorInduk.setText(user.getRegistrationNumber());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addData();
        RecyclerView recyclerView = findViewById(R.id.rcContainer);
        adapter = new ThreeItemsListStyle2Adapter(arrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new StudentProfileActivity.RecyclerTouchListener(this,
                new StudentProfileActivity.ClickListener() {
                    @Override
                    public void onClick(View view, final int position) {

                    }
                }));
    }

    private void addData(){
        arrayList = new ArrayList<>();
        Query queryAnswer = databaseReferenceAnswer.orderByChild("owner").equalTo(userEmail);
        queryAnswer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0){
                    progressBar.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.VISIBLE);
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Answer newAnswer = postSnapshot.getValue(Answer.class);
                    int benar = 0;
                    for (int i = 0;i < newAnswer.getSelectedAnswer().size();i++){
                        if (newAnswer.getSelectedAnswer().get(i).equals(newAnswer.getCorrectAnswer().get(i))){
                            benar++;
                        }
                    }
                    final int skor = Math.round(benar / newAnswer.getCorrectAnswer().size() * 100);

                    Query queryLesson = databaseReferenceLesson.orderByChild("id").equalTo(newAnswer.getLessonId());
                    queryLesson.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Lesson newLesson = postSnapshot.getValue(Lesson.class);
                                arrayList.add(new ThreeItems("", newLesson.getName(), "" + skor, "out of " + 100));
                                adapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface ClickListener{
        void onClick(View view, int position);
    }
}
