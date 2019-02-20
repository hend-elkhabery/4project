package com.example.hend.bakingapp.UI.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hend.bakingapp.Models.StepsModel;
import com.example.hend.bakingapp.R;

import androidx.fragment.app.Fragment;


public class step_details_Fragment extends Fragment {
    private StepsModel step;
    Button btnpre, btnnext;

    public step_details_Fragment() {
    }

    public static step_details_Fragment newInstance(String param1, String param2) {
        step_details_Fragment fragment = new step_details_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details_, container, false);
        btnpre = (Button) view.findViewById(R.id.btn_previous_step);
        btnnext = (Button) view.findViewById(R.id.btn_next_step);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
