package id.rumahawan.belajarfisika.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import id.rumahawan.belajarfisika.Data.Session;
import id.rumahawan.belajarfisika.R;

import static android.app.Activity.RESULT_OK;

public class AddQuestionFragment extends Fragment {
    private TextView tvUrutSoal;
    private ImageView ivUploadGambarSoal;
    private TextInputLayout correctAnswer, wrongAnwer0, wrongAnwer1, wrongAnwer2;
    private Button btnUploadSoal;

    private Session session;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    private Uri uri;

    private static int GALLERY_INTENT = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            uri = data.getData();
            ivUploadGambarSoal.setImageURI(uri);

            ImageView ivUploadPertanyaan = getView().findViewById(R.id.ivUploadPertanyaan);
            TextView tvUploadPertanyaan = getView().findViewById(R.id.tvUploadPertanyaan);
            TextView tvUpload = getView().findViewById(R.id.tvUpload);
            ivUploadPertanyaan.setVisibility(View.GONE);
            tvUploadPertanyaan.setVisibility(View.GONE);
            tvUpload.setVisibility(View.GONE);
        }

    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_question, container, false);

        session = new Session(getContext());
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://belajar-fisika.firebaseio.com/Question");
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);

        tvUrutSoal = rootView.findViewById(R.id.tvUrutSoal);
        tvUrutSoal.setText(getResources().getString(R.string.question) + " " + session.getSessionInt("questionNumber"));

        correctAnswer = rootView.findViewById(R.id.correctAnswer);
        wrongAnwer0 = rootView.findViewById(R.id.wrongAnwer0);
        wrongAnwer1 = rootView.findViewById(R.id.wrongAnwer1);
        wrongAnwer2 = rootView.findViewById(R.id.wrongAnwer2);

        ivUploadGambarSoal = rootView.findViewById(R.id.ivGambarSoal);
        ivUploadGambarSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        btnUploadSoal = rootView.findViewById(R.id.btnDone);
        btnUploadSoal.setText(getResources().getString(R.string.upload_question) + " " + session.getSessionInt("questionNumber"));
        btnUploadSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuestionClean()){
                    progressDialog.setMessage("Uploading question");
                    progressDialog.show();
                    session.setSessionInt("questionNumber", session.getSessionInt("questionNumber") + 1);
                    pushSoal();
                }
                else{
                    Snackbar.make(rootView, "Complete the form before uploading question", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary ))
                        .show();
                }
            }
        });

        return rootView;
    }

    private boolean isQuestionClean(){
        return !correctAnswer.getEditText().getText().toString().equals("") &&
                !wrongAnwer0.getEditText().getText().toString().equals("") &&
                !wrongAnwer1.getEditText().getText().toString().equals("") &&
                !wrongAnwer2.getEditText().getText().toString().equals("");
    }

    private void pushSoal(){
        StorageReference filepath = storageReference.child("Soal").child(uri.getLastPathSegment());
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                java.util.Random random = new java.util.Random();
                DatabaseReference newSoal = databaseReference.push();

                newSoal.child("id").setValue(""  + random.nextInt((999999 - 1) + 1 ) + 1);
                newSoal.child("questionUrl").setValue(taskSnapshot.getDownloadUrl().toString());
                newSoal.child("correctAnswer").setValue(correctAnswer.getEditText().getText().toString());
                newSoal.child("wrongAnswer0").setValue(wrongAnwer0.getEditText().getText().toString());
                newSoal.child("wrongAnswer1").setValue(wrongAnwer1.getEditText().getText().toString());
                newSoal.child("wrongAnswer2").setValue(wrongAnwer2.getEditText().getText().toString());
                newSoal.child("lessonId").setValue(session.getSessionString("lessonId"))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                reloadFragment();
                            }
                        })
                ;
            }
        });
    }

    private void reloadFragment(){
        if (getFragmentManager() != null && getActivity() != null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flContainer, new AddQuestionFragment()).commit();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
