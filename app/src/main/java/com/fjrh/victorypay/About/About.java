package com.fjrh.victorypay.About;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.fjrh.victorypay.R;

public class About extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla el diseño del fragmento que deseas mostrar en el cuadro de diálogo
        View view = inflater.inflate(R.layout.fragment_about_, container, false);
        // Configura la vista del fragmento si es necesario
        // ...
        return view;
    }
}