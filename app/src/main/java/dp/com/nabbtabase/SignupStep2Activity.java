package dp.com.nabbtabase;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class SignupStep2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
    }

    public void policy(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater =(this).getLayoutInflater();
        View filter = inflater.inflate(R.layout.bottom_dialog_layout, null);
        builder.setView(filter);
        builder.setCancelable(true);
        Dialog dialog=builder.create();
        Window window=dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity=Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.setBackgroundDrawableResource(R.color.transparent);
        dialog.show();
    }
}
