package com.jeeban.map.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.snackbar.Snackbar;
import com.jeeban.map.R;
import com.jeeban.map.listner.AlertDialogListner;

public class Utils {

    public static String removeChar(String s){
        return s.replaceAll("[^\\d.]", "");
    }

    public static void showAlertDialog(Context context, String title, String msg, AlertDialogListner alertDialogListner){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialogListner.onClick(dialog,true);
                    }
                }).setNegativeButton(R.string.alert_dialog_negetive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialogListner.onClick(dialog,false);
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.setTitle(title);
        alertDialog.show();
    }

    public static void showProgressDialog(ProgressDialog progressDialog, String title, String message){
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    public static void showProgressDialog(ProgressDialog progressDialog,String message){
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    public static void dismissProgressDialog(ProgressDialog progressDialog){
        if (progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }


}
