package id.rumahawan.belajarfisika.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.rumahawan.belajarfisika.AddLessonActivity;
import id.rumahawan.belajarfisika.LessonDetailActivity;
import id.rumahawan.belajarfisika.Object.ThreeItems;
import id.rumahawan.belajarfisika.R;
import id.rumahawan.belajarfisika.RecyclerViewAdapter.ThreeItemsListStyle1Adapter;

public class LessonListFragment extends Fragment {
    private ArrayList<ThreeItems> arrayList;

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private LessonListFragment.ClickListener clicklistener;
        private GestureDetector gestureDetector;

        RecyclerTouchListener(Context context, final LessonListFragment.ClickListener clicklistener){

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            startActivity(new Intent(getContext(), AddLessonActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_activity_menu, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_three_items_list, container, false);
        final Context context = getActivity();

        TextView tvTitleFragment = rootView.findViewById(R.id.tvTitleFragment);
        tvTitleFragment.setText("Lesson List");
        TextView tvSubtitleFragment = rootView.findViewById(R.id.tvSubtitleFragment);
        tvSubtitleFragment.setText("Jumlah pelajaran : 30");

        addData();
        RecyclerView recyclerView = rootView.findViewById(R.id.rcContainer);
        ThreeItemsListStyle1Adapter adapter = new ThreeItemsListStyle1Adapter(arrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                new LessonListFragment.ClickListener() {
                    @Override
                    public void onClick(View view, final int position) {
                        startActivity(new Intent(getContext(), LessonDetailActivity.class));
                    }
                }));

        return rootView;
    }

    void addData(){
        arrayList = new ArrayList<>();
        arrayList.add(new ThreeItems("Mirror - Chapter 2", "Physic", "Level 3"));
        arrayList.add(new ThreeItems("Electricity - Chapter 1", "Physic", "Level 3"));
        arrayList.add(new ThreeItems("Vibration - Chapter 5", "Physic", "Level 5"));
        arrayList.add(new ThreeItems("Electricity - Chapter 2", "Physic", "Level 3"));
        arrayList.add(new ThreeItems("Mirror - Chapter 1", "Physic", "Level 2"));
        arrayList.add(new ThreeItems("Vibration - Chapter 8", "Physic", "Level 6"));
        arrayList.add(new ThreeItems("Electricity - Chapter 3", "Physic", "Level 5"));
        arrayList.add(new ThreeItems("Electricity - Chapter 4", "Physic", "Level 5"));
    }

    public interface ClickListener{
        void onClick(View view, int position);
    }

}