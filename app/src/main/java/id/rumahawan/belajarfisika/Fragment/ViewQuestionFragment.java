package id.rumahawan.belajarfisika.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import id.rumahawan.belajarfisika.Data.Session;
import id.rumahawan.belajarfisika.Object.Lesson;
import id.rumahawan.belajarfisika.Object.Question;
import id.rumahawan.belajarfisika.R;

public class ViewQuestionFragment extends Fragment {
    private ProgressBar progressBar;
    private TextView tvUrutSoal;
    private ImageView ivGambarSoal;
    private Button btnOpsiA, btnOpsiB, btnOpsiC, btnOpsiD, btnDone;

    private Session session;
    private ArrayList<String> arrayJawaban = new ArrayList<>();

    private long jumlahSoal = 0;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_question, container, false);

        session = new Session(getContext());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://belajar-fisika.firebaseio.com/Question");
        Query query = databaseReference.orderByChild("lessonId").equalTo(session.getSessionString("lessonId"));

        progressBar = rootView.findViewById(R.id.progressBar);
        tvUrutSoal = rootView.findViewById(R.id.tvUrutSoal);
        ivGambarSoal= rootView.findViewById(R.id.ivGambarSoal);
        btnOpsiA = rootView.findViewById(R.id.btnOpsiA);
        btnOpsiB = rootView.findViewById(R.id.btnOpsiB);
        btnOpsiC = rootView.findViewById(R.id.btnOpsiC);
        btnOpsiD = rootView.findViewById(R.id.btnOpsiD);
        btnDone = rootView.findViewById(R.id.btnDone);

        ivGambarSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Soal ditekan", Toast.LENGTH_SHORT).show();
            }
        });
        btnOpsiA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "A ditekan", Toast.LENGTH_SHORT).show();
            }
        });
        btnOpsiB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "B ditekan", Toast.LENGTH_SHORT).show();
            }
        });
        btnOpsiC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "C ditekan", Toast.LENGTH_SHORT).show();
            }
        });
        btnOpsiD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "D ditekan", Toast.LENGTH_SHORT).show();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Next soal ditekan", Toast.LENGTH_SHORT).show();
            }
        });

        progressBar.setVisibility(View.GONE);
        tvUrutSoal.setText(getResources().getString(R.string.question) + " " + session.getSessionInt("questionNumber"));
        Picasso.get()
                .load("https://www.google.co.id/imgres?imgurl=https%3A%2F%2Fi.stack.imgur.com%2Fv2dHF.png&imgrefurl=https%3A%2F%2Fstackoverflow.com%2Fquestions%2F42199057%2Fhow-to-get-all-nested-childs-data-in-firebase-database&docid=2zhRQzFL-IdCtM&tbnid=N6byhBVghOVVpM%3A&vet=10ahUKEwjmra3jou_eAhUaknAKHXmsCBcQMwg-KAEwAQ..i&w=440&h=232&safe=strict&bih=626&biw=1366&q=best%20to%20place%20value%20event%20listener%20firebase%20in%20fragment&ved=0ahUKEwjmra3jou_eAhUaknAKHXmsCBcQMwg-KAEwAQ&iact=mrc&uact=8")
                .placeholder(R.color.colorSligthTranslucent)
                .fit()
                .centerCrop()
                .into(ivGambarSoal);

        return rootView;
    }
}
