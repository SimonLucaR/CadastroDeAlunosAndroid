package com.example.cadastroalunos.util;

import android.view.View;
import android.widget.TextView;
import com.example.cadastroalunos.R;
import com.google.android.material.snackbar.Snackbar;

public class Util {

    public static void customSnackBar(View view, String msg, int tipo){
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        View viewSnack = snackbar.getView();
        TextView tv = (TextView) viewSnack.findViewById(R.id.snackbar_text);
        if(tipo == 0)
            tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_cancel,0,0,0);
        else if(tipo == 1)
            tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_confirm,0,0,0);
        else if(tipo == 2)
            tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_warning2,0,0,0);

        snackbar.show();
    }

}
