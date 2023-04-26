package com.fjrh.victorypay.configPanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fjrh.victorypay.About.About;
import com.fjrh.victorypay.App;
import com.fjrh.victorypay.R;
import com.fjrh.victorypay.dataBases.params.Params;
import com.fjrh.victorypay.dataBases.register.CleanRegisterTable;

import java.util.HashMap;

public class ConfigPanel extends AppCompatActivity {

    private Context context;

    private Button cancel;
    private Button accept;
    private TextView deleteRegister;
    private TextView stopRemenber;
    private TextView about;
    private Switch updateAtStart;

    private Params params;
    private ContentValues values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_panel);
        context = this;
        initComponents();
        fillInputs();
        initEvents();

    }

    private void initComponents() {

        cancel = findViewById(R.id.btnCancel);
        accept = findViewById(R.id.btnAccept);
        updateAtStart = findViewById(R.id.switchUpdate);
        deleteRegister = findViewById(R.id.deleteRegisters);
        stopRemenber = findViewById(R.id.rememberMe);
        about = findViewById(R.id.About);
        values = new ContentValues();
        params = new Params(context);

    }

    private void initEvents() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAppMenu();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (values.size() > 0) {
                    params.insertParamsList(values);
                    //Log.i("XXX", params.getParams().get("loadAtStart"));
                    goToAppMenu();
                }else{
                    goToAppMenu();
                }

            }
        });

        ////

        updateAtStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                values.put("param", "loadAtStart");
                values.put("value", String.valueOf(isChecked));
            }
        });

        //

        deleteRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRegisterTable();
            }
        });

        //

        stopRemenber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params.insertParam("remember", "false");
                Toast.makeText(context, "Se ha dejado de recordar al usuario", Toast.LENGTH_LONG).show();
            }
        });

        //

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                About dialogFragment = new About();
                dialogFragment.show(getSupportFragmentManager(), "mi_dialogo");
            }
        });


    }

    ///

    private void fillInputs() {
        HashMap<String, String> confItems = params.getParams();

        if (confItems.containsKey("loadAtStart")) {

            updateAtStart.setChecked(Boolean.parseBoolean(confItems.get("loadAtStart")));
        }


    }


    private void goToAppMenu() {
        Intent i = new Intent(context, App.class);
        startActivity(i);
    }

    //
    private void deleteRegisterTable() {
        new AlertDialog.Builder(this)
                .setTitle("¿Eliminar Registros?")
                .setMessage("¿Está seguro de que desea borrar los registros?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Acción cuando se presiona el botón "Sí"
                        new CleanRegisterTable(context).clean();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Acción cuando se presiona el botón "No"
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }





}