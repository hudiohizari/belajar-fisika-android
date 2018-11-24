package id.rumahawan.belajarfisika;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.rumahawan.belajarfisika.Object.ThreeItems;
import id.rumahawan.belajarfisika.RecyclerViewAdapter.ThreeItemsListStyle2Adapter;

public class StudentProfileActivity extends AppCompatActivity {
    private ArrayList<ThreeItems> arrayList;

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
        getSupportActionBar().setTitle("Student Profile");addData();

        RecyclerView recyclerView = findViewById(R.id.rcContainer);
        ThreeItemsListStyle2Adapter adapter = new ThreeItemsListStyle2Adapter(arrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new StudentProfileActivity.RecyclerTouchListener(this,
                new StudentProfileActivity.ClickListener() {
                    @Override
                    public void onClick(View view, final int position) {
                        TextView clickedTitle = view.findViewById(R.id.tvTitle);
                        Toast.makeText(StudentProfileActivity.this, "Clicked " + clickedTitle.getText(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    void addData(){
        arrayList = new ArrayList<>();
        arrayList.add(new ThreeItems("Mirror - Chapter 2", "20", "out of " + 100));
        arrayList.add(new ThreeItems("Electricity - Chapter 1", "44", "out of " + 100));
        arrayList.add(new ThreeItems("Vibration - Chapter 5", "56", "out of " + 100));
        arrayList.add(new ThreeItems("Electricity - Chapter 2", "55", "out of " + 100));
        arrayList.add(new ThreeItems("Mirror - Chapter 1", "48", "out of " + 100));
        arrayList.add(new ThreeItems("Vibration - Chapter 8", "80", "out of " + 100));
        arrayList.add(new ThreeItems("Electricity - Chapter 3", "90", "out of " + 100));
        arrayList.add(new ThreeItems("Electricity - Chapter 4", "100", "out of " + 100));
    }

    public interface ClickListener{
        void onClick(View view, int position);
    }
}
