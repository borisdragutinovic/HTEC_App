package com.example.htecapp.util;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;


public class Alert {

    public static void showAlertDialog(Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }
}
