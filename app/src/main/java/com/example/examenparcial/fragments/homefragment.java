package com.example.examenparcial.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examenparcial.MainActivity;
import com.example.examenparcial.Principal;
import com.example.examenparcial.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homefragment extends Fragment {
   FirebaseFirestore mFire;
   View mview;
   TextView textView;
   CircleImageView circle;
   FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public homefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homefragment newInstance(String param1, String param2) {
        homefragment fragment = new homefragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFire = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mview = inflater.inflate(R.layout.fragment_homefragment, container, false);
        textView = mview.findViewById(R.id.nombreusuario);
        circle = mview.findViewById(R.id.circleback);

        circle.setOnClickListener(view -> {
            Intent intent2 = new Intent(getContext(),MainActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
        });
        mostrardatos();

        return mview;
    }

    void mostrardatos(){
        String id =  mAuth.getCurrentUser().getUid();



        mFire.collection("Users").document(id).get().addOnSuccessListener(documentSnapshot -> {
           if(documentSnapshot.exists()){
               String nombre = documentSnapshot.getString("username").toString();
               textView.setText(nombre);
           }else{
               Toast.makeText(getActivity().getApplicationContext(), "No se pudieron obtenr los datos", Toast.LENGTH_SHORT).show();

           }

        });

    }
}