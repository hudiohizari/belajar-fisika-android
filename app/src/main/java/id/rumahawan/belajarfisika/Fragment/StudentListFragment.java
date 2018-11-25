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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.rumahawan.belajarfisika.Data.Session;
import id.rumahawan.belajarfisika.Object.Lesson;
import id.rumahawan.belajarfisika.Object.ThreeItems;
import id.rumahawan.belajarfisika.Object.User;
import id.rumahawan.belajarfisika.R;
import id.rumahawan.belajarfisika.RecyclerViewAdapter.ThreeItemsListStyle1Adapter;
import id.rumahawan.belajarfisika.StudentProfileActivity;

public class StudentListFragment extends Fragment {
    private ProgressBar progressBar;

    private Session session;
    private Query query;
    private ArrayList<ThreeItems> arrayList;
    private ThreeItemsListStyle1Adapter adapter;

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

        session = new Session(getContext());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://belajar-fisika.firebaseio.com/User");
        query = databaseReference.orderByChild("level").equalTo("student");
        query.keepSynced(true);

        progressBar = rootView.findViewById(R.id.progressBar);
        TextView tvTitleFragment = rootView.findViewById(R.id.tvTitleFragment);
        tvTitleFragment.setText("Student List");
        TextView tvListName = rootView.findViewById(R.id.tvListName);
        tvListName.setText("Daftar Pelajar");

        addData();
        RecyclerView recyclerView = rootView.findViewById(R.id.rcContainer);
        adapter = new ThreeItemsListStyle1Adapter(arrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new StudentListFragment.RecyclerTouchListener(context,
                new StudentListFragment.ClickListener() {
                    @Override
                    public void onClick(View view, final int position) {
                        TextView tvId = view.findViewById(R.id.tvId);
                        startActivity(
                                new Intent(getContext(), StudentProfileActivity.class)
                                        .putExtra("id", tvId.getText().toString())
                        );
                    }
                }));

        return rootView;
    }

    void addData(){
        arrayList = new ArrayList<>();

        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                TextView tvSubtitleFragment = getView().findViewById(R.id.tvSubtitleFragment);
                tvSubtitleFragment.setText("Registered student : " + dataSnapshot.getChildrenCount());

                arrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User newUser = postSnapshot.getValue(User.class);
                    arrayList.add(new ThreeItems(newUser.getEmail(), newUser.getName(), newUser.getClasc(), newUser.getRegistrationNumber()));
                }
                adapter.notifyDataSetChanged();
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