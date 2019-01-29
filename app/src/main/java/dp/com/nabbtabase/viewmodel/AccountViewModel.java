package dp.com.nabbtabase.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.view.activity.ChangePasswordActivity;
import dp.com.nabbtabase.view.activity.LoginActivity;
import dp.com.nabbtabase.view.activity.MainActivity;
import dp.com.nabbtabase.view.activity.OrderHistoryActivity;
import dp.com.nabbtabase.view.activity.ProfileActivity;
import dp.com.nabbtabase.view.activity.ServicesHistoryActivity;
import dp.com.nabbtabase.view.activity.ShippingAddressActivity;
import dp.com.nabbtabase.view.callback.CallBackInterface;

public class AccountViewModel extends AndroidViewModel {
    Context context;
    Intent intent;
    CallBackInterface callBackInterface;
    public AccountViewModel(@NonNull Application application) {

        super(application);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void myOrders(View view){
        if(CustomUtils.getInstance().getSaveUserObject(context)==null) {
            callBackInterface.errorMessage(context.getResources().getString(R.string.you_must_be_Register));
            return;
        }
        Intent intent=new Intent(context,OrderHistoryActivity.class);
        context.startActivity(intent);
    }

    public void editProfile(View view){
        if(CustomUtils.getInstance().getSaveUserObject(context)==null) {
            callBackInterface.errorMessage(context.getResources().getString(R.string.you_must_be_Register));
            return;
        }
        intent=new Intent(context,ProfileActivity.class);
        context.startActivity(intent);
    }

    public void shippingAddress(View view){
        if(CustomUtils.getInstance().getSaveUserObject(context)==null) {
            callBackInterface.errorMessage(context.getResources().getString(R.string.you_must_be_Register));
            return;
        }
        intent=new Intent(context,ShippingAddressActivity.class);
        context.startActivity(intent);
    }

    public void changePassword(View view){
        if(CustomUtils.getInstance().getSaveUserObject(context)==null) {
            callBackInterface.errorMessage(context.getResources().getString(R.string.you_must_be_Register));
            return;
        }
        intent=new Intent(context,ChangePasswordActivity.class);
        context.startActivity(intent);
    }

    public void changeLanguage(View view){
        if(CustomUtils.getInstance().getAppLanguage(context).equals("en")){
            CustomUtils.getInstance().saveAppLanguage(context,"ar");
        }else {
            CustomUtils.getInstance().saveAppLanguage(context,"en");
        }
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
        ((Activity)context).finishAffinity();
    }

    public void logout(View view){
        CustomUtils.getInstance().clearSharedPref(context);
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
        ((Activity)context).finishAffinity();
    }

    public void servicesHistory(View view){
        if(CustomUtils.getInstance().getSaveUserObject(context)==null) {
            callBackInterface.errorMessage(context.getResources().getString(R.string.you_must_be_Register));
            return;
        }
        Intent intent=new Intent(context,ServicesHistoryActivity.class);
        context.startActivity(intent);
    }

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }
}
