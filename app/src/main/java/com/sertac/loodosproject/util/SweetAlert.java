package com.sertac.loodosproject.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.sertac.loodosproject.view.activity.HamburgerActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

abstract public class SweetAlert {

    public static void basicMessage(Context context, String title, String message){
        new SweetAlertDialog(context)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }

    public static void errorPopup(Context context, String title, String message){
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }

    public static void successPopup(Context context, String title, String message){
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }

    public static void successPopupClickListener(Context context, String title, String message){
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(message).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent intent = new Intent(context, HamburgerActivity.class);
                        context.startActivity(intent);
                    }
                })
                .show();
    }

    public static void questoinPopup(Context context, String title, String question){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(question)
                .setCancelText("No")
                .setConfirmText("Yes")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();
                        sDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Toast.makeText(context, "Yess", Toast.LENGTH_SHORT).show();
                        sweetAlertDialog.cancel();
                    }
                })
                .show();
    }
}
