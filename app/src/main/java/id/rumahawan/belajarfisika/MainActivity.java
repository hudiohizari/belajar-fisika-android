package id.rumahawan.belajarfisika;

import android.content.Context;
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
import id.rumahawan.belajarfisika.RecyclerView.ThreeItemsAdapter;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ThreeItems> arrayList;

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        RecyclerTouchListener(Context context, final ClickListener clicklistener){

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
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Lesson");

        addData();
        RecyclerView recyclerView = findViewById(R.id.rcPelajaran);
        ThreeItemsAdapter adapter = new ThreeItemsAdapter(arrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                TextView clickedTitle = view.findViewById(R.id.tvTitle);
                Toast.makeText(MainActivity.this, "Clicked " + clickedTitle.getText(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    void addData(){
        arrayList = new ArrayList<>();
        arrayList.add(new ThreeItems("Mirror - Chapter 2", "Physic", "level 3"));
        arrayList.add(new ThreeItems("Electricity - Chapter 1", "Physic", "level 3"));
        arrayList.add(new ThreeItems("Vibration - Chapter 5", "Physic", "level 5"));
    }

    public interface ClickListener{
        void onClick(View view, int position);
    }
}
