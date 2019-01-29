package dp.com.nabbtabase.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.view.activity.CartActivity;

public class ActionBarViewModel {

    //public ObservableInt searchVisibality;
    public ObservableInt cartVisibailty;
    public ObservableInt backVisibality;
    public ObservableField<String>notificationCounter;
    private Context context;

    public ActionBarViewModel(Context context, boolean search, boolean cart, boolean back) {
        this.context = context;
        initVariavles();
        System.out.println("items in cart in ViewModel : "+MyApp.getNotificationCounter());

//        if (search) {
//            searchVisibality.set(View.VISIBLE);
//        }
        if (cart) {
            if(MyApp.getNotificationCounter()==0){
                cartVisibailty.set(View.GONE);
            }else {
                cartVisibailty.set(View.VISIBLE);
            }
        }
        if (back) {
            backVisibality.set(View.VISIBLE);
        }
    }

    public void initVariavles() {
        //searchVisibality = new ObservableInt(View.GONE);
        cartVisibailty = new ObservableInt(View.GONE);
        backVisibality = new ObservableInt(View.GONE);
        notificationCounter=new ObservableField<>(String.valueOf(MyApp.getNotificationCounter()));
    }

    public void back(View view) {
        ((Activity) context).finish();
    }

    public void cart(View view) {
        Intent intent = new Intent(context, CartActivity.class);
        context.startActivity(intent);
    }



}
