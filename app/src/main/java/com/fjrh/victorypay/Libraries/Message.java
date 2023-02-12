package com.fjrh.victorypay.Libraries;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class Message {

    static int response = 0;


    //
    public static void ConfirmDioalog(Context context, String title, String message) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Your action here
            }
        });

        alert.show();
    }

    //
    public static void ConfirmDioalog(Context context, String title) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Your action here
            }
        });

        alert.show();
    }

    //

    public static int OkCancelDialog(Context context, String title, String message) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                response = 1;
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                response = 2;
            }
        });

        alert.show();
        return response;
    }


}
