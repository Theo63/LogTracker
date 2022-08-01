package com.codetracker.logtracker;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button dismissBtn;
    public TextView message;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_dialog_class);
        dismissBtn = (Button) findViewById(R.id.dismiss);
        dismissBtn.setOnClickListener(this);
        message = (TextView) findViewById(R.id.txt_dia);

    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    public void setInfo(String info){
        message.setText(info);
    }

}