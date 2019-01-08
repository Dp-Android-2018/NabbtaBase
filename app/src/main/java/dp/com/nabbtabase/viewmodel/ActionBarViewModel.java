package dp.com.nabbtabase.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.google.android.gms.wallet.Cart;

import dp.com.nabbtabase.view.activity.CartActivity;

public class ActionBarViewModel {

    public ObservableInt searchVisibality;
    public ObservableInt cartVisibailty;
    public ObservableInt backVisibality;
    private Context context;

    public ActionBarViewModel(Context context,boolean search,boolean cart,boolean back) {
     this.context=context;
     initVariavles();
     if(search){
         searchVisibality.set(View.VISIBLE);
     }
     if(cart){
         cartVisibailty.set(View.VISIBLE);
     }
     if (back){
         backVisibality.set(View.VISIBLE);
     }
    }
    public void initVariavles(){
        searchVisibality=new ObservableInt(View.GONE);
        cartVisibailty=new ObservableInt(View.GONE);
        backVisibality=new ObservableInt(View.GONE);
    }

    public void back(View view){
        ((Activity)context).finish();
    }

    public void cart(View view){
        Intent intent=new Intent(context,CartActivity.class);
        context.startActivity(intent);
    }
}
