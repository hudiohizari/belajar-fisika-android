package id.rumahawan.belajarfisika.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import id.rumahawan.belajarfisika.Object.Question;
import id.rumahawan.belajarfisika.R;

public class ViewQuestionFragment extends Fragment {
    private ProgressBar progressBar;
    private TextView tvUrutSoal;
    private ImageView ivGambarSoal;
    private Button btnOpsiA;
    private Button btnOpsiB;
    private Button btnOpsiC;
    private Button btnOpsiD;

    private ProgressDialog progressDialog;
    private Session session;
    private ArrayList<String> arrayJawaban = new ArrayList<>();
    private ArrayList<String> arrayJawabanBenar = new ArrayList<>();
    private ArrayList<String> arrayJawabanTerpilih = new ArrayList<>();

    private String jawabanTerpilih, jawabanBenar;
    private long jumlahSoalLesson;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_question, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
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
        Button btnDone = rootView.findViewById(R.id.btnDone);

        if(session.getSessionString("currentLevel").equals("teacher")){
            btnDone.setVisibility(View.GONE);
        }

        btnOpsiA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAnswerPressed(btnOpsiA);
            }
        });
        btnOpsiB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAnswerPressed(btnOpsiB);
            }
        });
        btnOpsiC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAnswerPressed(btnOpsiC);
            }
        });
        btnOpsiD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAnswerPressed(btnOpsiD);
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.isExist("arrayJawabanTerpilih")){
                    arrayJawabanTerpilih = session.getSessionArraylistString("arrayJawabanTerpilih");
                    arrayJawabanBenar = session.getSessionArraylistString("arrayJawabanBenar");
                }
                arrayJawabanTerpilih.add(jawabanTerpilih);
                arrayJawabanBenar.add(jawabanBenar);
                session.setSessionArraylistString("arrayJawabanTerpilih", arrayJawabanTerpilih);
                session.setSessionArraylistString("arrayJawabanBenar", arrayJawabanBenar);

                if (session.getSessionInt("questionNumber") == jumlahSoalLesson){
                    int benar = 0;
                    for (int i = 0;i < arrayJawabanTerpilih.size();i++){
                        if (arrayJawabanTerpilih.get(i).equals(arrayJawabanBenar.get(i))){
                            benar++;
                        }
                    }
                    System.out.println("Benar : "+benar);
                    System.out.println("Size : "+arrayJawabanTerpilih.size());
                    double pointPerSoal = 100 / arrayJawabanTerpilih.size();
                    int skor = (int) Math.round(pointPerSoal * benar);
                    if (benar == arrayJawabanTerpilih.size()){skor = 100;}
                    final int finalSkor = skor;
                    progressDialog.setMessage("Submitting answer");

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://belajar-fisika.firebaseio.com/Answer");
                    DatabaseReference newJawaban = databaseReference.push();
                    newJawaban.child("selectedAnswer").setValue(arrayJawabanTerpilih);
                    newJawaban.child("correctAnswer").setValue(arrayJawabanBenar);
                    newJawaban.child("lessonId").setValue(session.getSessionString("lessonId"));
                    newJawaban.child("owner").setValue(session.getSessionString("currentEmail"))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();

                                    AlertDialog.Builder builder;
                                    builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Your skor was : " + finalSkor)
                                            .setMessage("You can find all your score on your profile")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    restartActivity();
                                                }
                                            })
                                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                @Override
                                                public void onCancel(DialogInterface dialog) {
                                                    restartActivity();
                                                }
                                            })
                                            .show();
                                }
                            })
                    ;
                }
                else{
                    reloadFragment();
                }
            }
        });

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jumlahSoalLesson = dataSnapshot.getChildrenCount();
                int i = 1;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (i == session.getSessionInt("questionNumber")){
                        Question question = postSnapshot.getValue(Question.class);

                        progressBar.setVisibility(View.GONE);
                        tvUrutSoal.setText(getResources().getString(R.string.question) + " " + session.getSessionInt("questionNumber") + " of " + dataSnapshot.getChildrenCount());
                        Picasso.get()
                                .load(question.getQuestionUrl())
                                .placeholder(R.color.colorSligthTranslucent)
                                .fit()
                                .centerCrop()
                                .into(ivGambarSoal);
                        if (question != null) {
                            jawabanBenar = question.getCorrectAnswer();
                        }

                        arrayJawaban.add(question.getCorrectAnswer());
                        arrayJawaban.add(question.getWrongAnswer0());
                        arrayJawaban.add(question.getWrongAnswer1());
                        arrayJawaban.add(question.getWrongAnswer2());

                        Collections.shuffle(arrayJawaban);
                        btnOpsiA.setText(arrayJawaban.get(0));
                        btnOpsiB.setText(arrayJawaban.get(1));
                        btnOpsiC.setText(arrayJawaban.get(2));
                        btnOpsiD.setText(arrayJawaban.get(3));
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    public void buttonAnswerPressed(Button btnTerpilih){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            btnOpsiA.setBackground(getResources().getDrawable(R.drawable.rectangle_radius_white_disabled));
            btnOpsiB.setBackground(getResources().getDrawable(R.drawable.rectangle_radius_white_disabled));
            btnOpsiC.setBackground(getResources().getDrawable(R.drawable.rectangle_radius_white_disabled));
            btnOpsiD.setBackground(getResources().getDrawable(R.drawable.rectangle_radius_white_disabled));
            btnTerpilih.setBackground(getResources().getDrawable(R.drawable.rectangle_radius_white));
            jawabanTerpilih = btnTerpilih.getText().toString();
            Toast.makeText(getContext(), "Selected : " + btnTerpilih.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void reloadFragment(){
        System.out.print("arrayJawabanTerpilih sebelum di next : ");
        for (int i = 0;i<arrayJawabanTerpilih.size();i++){
            System.out.print(arrayJawabanTerpilih.get(i) + " ");
        }
        System.out.println();
        System.out.print("arrayJawabanBenar sebelum di next : ");
        for (int i = 0;i<arrayJawabanBenar.size();i++){
            System.out.print(arrayJawabanBenar.get(i) + " ");
        }
        System.out.println();

        if (getFragmentManager() != null && getActivity() != null) {
            session.setSessionInt("questionNumber", session.getSessionInt("questionNumber") + 1);

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flContainer, new ViewQuestionFragment()).commit();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private void restartActivity(){
        Intent intent = getActivity().getIntent();
        intent.putExtra("id", session.getSessionString("lessonId"));
        getActivity().finish();
        startActivity(intent);
    }
}
