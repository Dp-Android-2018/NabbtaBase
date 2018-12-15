package dp.com.nabbtabase.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.activity.LoginActivity;

public class AccountViewModel extends AndroidViewModel {
    Context context;
    public AccountViewModel(@NonNull Application application) {
        super(application);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void myOrders(View view){

    }

    public void editProfile(View view){

    }

    public void shippingAddress(View view){

    }

    public void changePassword(View view){

    }

    public void changeLanguage(View view){

    }

    public void logout(View view){
        CustomUtils.getInstance().clearSharedPref(context);
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
        ((Activity)context).finishAffinity();
    }



}
