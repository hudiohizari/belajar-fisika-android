package id.rumahawan.belajarfisika.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import id.rumahawan.belajarfisika.R;

public class ViewQuestionFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_question, container, false);

        TextView tvUrutSoal = rootView.findViewById(R.id.tvUrutSoal);
        tvUrutSoal.setText(getResources().getString(R.string.question) + " " + 1);

        String imageLink = "https://image.slidesharecdn.com/howtooverlaytextonimagesjpgs-141214122828-conversion-gate01/95/how-to-overlay-text-on-images-5-simple-methods-1-638.jpg";
        ImageView ivGambarSoal= rootView.findViewById(R.id.ivGambarSoal);
        Picasso.get()
                .load(imageLink)
                .placeholder(R.color.colorSligthTranslucent)
                .fit()
                .centerCrop()
                .into(ivGambarSoal);
        ivGambarSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Soal ditekan", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnOpsiA = rootView.findViewById(R.id.btnOpsiA);
        btnOpsiA.setText("80");
        btnOpsiA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "A ditekan", Toast.LENGTH_SHORT).show();
            }
        });
        Button btnOpsiB = rootView.findViewById(R.id.btnOpsiB);
        btnOpsiB.setText("59");
        btnOpsiB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "B ditekan", Toast.LENGTH_SHORT).show();
            }
        });
        Button btnOpsiC = rootView.findViewById(R.id.btnOpsiC);
        btnOpsiC.setText("519");
        btnOpsiC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "C ditekan", Toast.LENGTH_SHORT).show();
            }
        });
        Button btnOpsiD = rootView.findViewById(R.id.btnOpsiD);
        btnOpsiD.setText("19");
        btnOpsiD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "D ditekan", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnDone = rootView.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Next soal ditekan", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
