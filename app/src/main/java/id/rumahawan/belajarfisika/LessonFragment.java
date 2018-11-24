package id.rumahawan.belajarfisika;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.rumahawan.belajarfisika.Object.ThreeItems;
import id.rumahawan.belajarfisika.RecyclerView.ThreeItemsAdapter;

public class LessonFragment extends Fragment {
    private ArrayList<ThreeItems> arrayList;

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private LessonFragment.ClickListener clicklistener;
        private GestureDetector gestureDetector;

        RecyclerTouchListener(Context context, final LessonFragment.ClickListener clicklistener){

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

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_three_items, container, false);
        final Context context = getActivity();

        TextView tvTitleFragment = rootView.findViewById(R.id.tvTitleFragment);
        tvTitleFragment.setText("Lesson List");
        TextView tvSubtitleFragment = rootView.findViewById(R.id.tvSubtitleFragment);
        tvSubtitleFragment.setText("Jumlah pelajaran : 30");

        addData();
        RecyclerView recyclerView = rootView.findViewById(R.id.rcPelajaran);
        ThreeItemsAdapter adapter = new ThreeItemsAdapter(arrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                new LessonFragment.ClickListener() {
                    @Override
                    public void onClick(View view, final int position) {
                        TextView clickedTitle = view.findViewById(R.id.tvTitle);
                        Toast.makeText(context, "Clicked " + clickedTitle.getText(), Toast.LENGTH_SHORT).show();
                    }
                }));

        return rootView;
    }

    void addData(){
        arrayList = new ArrayList<>();
        arrayList.add(new ThreeItems("Mirror - Chapter 2", "Physic", "level 3"));
        arrayList.add(new ThreeItems("Electricity - Chapter 1", "Physic", "level 3"));
        arrayList.add(new ThreeItems("Vibration - Chapter 5", "Physic", "level 5"));
        arrayList.add(new ThreeItems("Electricity - Chapter 2", "Physic", "level 3"));
        arrayList.add(new ThreeItems("Mirror - Chapter 1", "Physic", "level 2"));
        arrayList.add(new ThreeItems("Vibration - Chapter 8", "Physic", "level 6"));
        arrayList.add(new ThreeItems("Electricity - Chapter 3", "Physic", "level 5"));
        arrayList.add(new ThreeItems("Electricity - Chapter 4", "Physic", "level 5"));
    }

    public interface ClickListener{
        void onClick(View view, int position);
    }

}