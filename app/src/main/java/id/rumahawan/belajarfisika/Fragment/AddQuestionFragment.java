package id.rumahawan.belajarfisika.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import id.rumahawan.belajarfisika.R;

public class AddQuestionFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_question, container, false);

        TextView tvUrutSoal = rootView.findViewById(R.id.tvUrutSoal);
        tvUrutSoal.setText(getResources().getString(R.string.question) + " " + 1);

        View vUploadGambarSoal = rootView.findViewById(R.id.vUploadGambarSoal);
        vUploadGambarSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Upload gambar ditekan", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnUploadSoal = rootView.findViewById(R.id.btnUploadSoal);
        btnUploadSoal.setText(getResources().getString(R.string.upload_question) + " " + 1);
        btnUploadSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Upload soal ditekan", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
