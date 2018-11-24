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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.rumahawan.belajarfisika.Object.ThreeItems;
import id.rumahawan.belajarfisika.R;
import id.rumahawan.belajarfisika.RecyclerViewAdapter.ThreeItemsListStyle1Adapter;
import id.rumahawan.belajarfisika.StudentProfileActivity;

public class StudentListFragment extends Fragment {
    private ArrayList<ThreeItems> arrayList;

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private StudentListFragment.ClickListener clicklistener;
        private GestureDetector gestureDetector;

        RecyclerTouchListener(Context context, final StudentListFragment.ClickListener clicklistener){

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
        View rootView = inflater.inflate(R.layout.fragment_three_items_list, container, false);
        final Context context = getActivity();

        TextView tvTitleFragment = rootView.findViewById(R.id.tvTitleFragment);
        tvTitleFragment.setText("Student List");
        TextView tvSubtitleFragment = rootView.findViewById(R.id.tvSubtitleFragment);
        tvSubtitleFragment.setText("Jumlah siswa terdaftar : 180");

        addData();
        RecyclerView recyclerView = rootView.findViewById(R.id.rcContainer);
        ThreeItemsListStyle1Adapter adapter = new ThreeItemsListStyle1Adapter(arrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                new StudentListFragment.ClickListener() {
                    @Override
                    public void onClick(View view, final int position) {
                        TextView clickedTitle = view.findViewById(R.id.tvTitle);
                        Toast.makeText(context, "Clicked " + clickedTitle.getText(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), StudentProfileActivity.class));
                    }
                }));

        return rootView;
    }

    void addData(){
        arrayList = new ArrayList<>();
        arrayList.add(new ThreeItems("Alghozi Simuntilong", "6706160114", "S1 Teknik Perhotelan"));
        arrayList.add(new ThreeItems("Alghozi Simantupong", "6706160423", "S1 Teknik Perhotelan"));
        arrayList.add(new ThreeItems("Alghozi Simuntipong", "6706160116", "S1 Teknik Perhotelan"));
        arrayList.add(new ThreeItems("Alghozi Simantilong", "6706162314", "S1 Teknik Perhotelan"));
        arrayList.add(new ThreeItems("Alghozi Simontilong", "6706162314", "S1 Teknik Perhotelan"));
        arrayList.add(new ThreeItems("Alghozi Simontiling", "6706162314", "S1 Teknik Perhotelan"));
        arrayList.add(new ThreeItems("Alghozi Simintoling", "6706162314", "S1 Teknik Perhotelan"));
        arrayList.add(new ThreeItems("Alghozi Simantupang", "6706164141", "S1 Teknik Perhotelan"));
    }

    public interface ClickListener{
        void onClick(View view, int position);
    }

}