package dp.com.nabbtabase.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import dp.com.nabbtabase.R;

public class RegisterStep2ViewModel extends AndroidViewModel {

    private Context context;

    public RegisterStep2ViewModel(@NonNull Application application) {
        super(application);
    }


    public void policy(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
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
