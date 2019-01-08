package dp.com.nabbtabase.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.activity.ChangePasswordActivity;
import dp.com.nabbtabase.view.activity.LoginActivity;
import dp.com.nabbtabase.view.activity.ProfileActivity;
import dp.com.nabbtabase.view.activity.ServicesHistoryActivity;
import dp.com.nabbtabase.view.activity.ShippingAddressActivity;

public class AccountViewModel extends AndroidViewModel {
    Context context;
    Intent intent;
    public AccountViewModel(@NonNull Application application) {

        super(application);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void myOrders(View view){

    }

    public void editProfile(View view){
        intent=new Intent(context,ProfileActivity.class);
        context.startActivity(intent);
    }

    public void shippingAddress(View view){
        intent=new Intent(context,ShippingAddressActivity.class);
        context.startActivity(intent);
    }

    public void changePassword(View view){
        intent=new Intent(context,ChangePasswordActivity.class);
        context.startActivity(intent);
    }

    public void changeLanguage(View view){

    }

    public void logout(View view){
        CustomUtils.getInstance().clearSharedPref(context);
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
        ((Activity)context).finishAffinity();
    }

    public void servicesHistory(View view){
        Intent intent=new Intent(context,ServicesHistoryActivity.class);
        context.startActivity(intent);
    }
}
