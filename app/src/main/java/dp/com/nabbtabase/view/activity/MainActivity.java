package dp.com.nabbtabase.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.notification.FirebaseMessageService;
import dp.com.nabbtabase.servise.model.pojo.LoginRegisterContent;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.utils.NetWorkConnection;
import dp.com.nabbtabase.viewmodel.CartViewModel;
import io.reactivex.annotations.NonNull;

public class MainActivity extends BaseActivity {

    public static String AppLang;
    CartViewModel cartViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras()!=null) {
            if(getIntent().getStringExtra("title").equals("account-activated")){
                LoginRegisterContent response = CustomUtils.getInstance().getSaveUserObject(this);
                response.setActivated("true");
                CustomUtils.getInstance().saveDataToPrefs(response,this);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finishAffinity();
            }
        }
        cartViewModel=ViewModelProviders.of(this).get(CartViewModel.class);
        setContentView(R.layout.activity_main);
        cartItems();
        new Handler().postDelayed(() -> {
            if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {

                if (CustomUtils.getInstance().getSaveUserObject(getApplicationContext()) != null) {
                    if (CustomUtils.getInstance().getSaveUserObject(getApplicationContext()).getActivated().equals("true")) {
                        Intent i = new Intent(MainActivity.this, ContainerActivity.class);
                        startActivity(i);
                    } else {
                        // TODO: 12/11/2018 call activation activity
                        Intent i = new Intent(MainActivity.this, ActivationActivity.class);
                        startActivity(i);
                    }
                } else {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            } else {
                Intent i = new Intent(MainActivity.this, NoInternetActivity.class);
                startActivity(i);
            }
            finish();
        }, 3000);
        changeAppLanguage();
    }
    public void changeAppLanguage() {
        System.out.println("Lang App :" + CustomUtils.getInstance().getAppLanguage(getApplicationContext()));
        String lang = CustomUtils.getInstance().getAppLanguage(getApplicationContext()) != null ? CustomUtils.getInstance().getAppLanguage(getApplicationContext()) : "en";
        Locale locale = new Locale(lang);
        CustomUtils.getInstance().saveAppLanguage(getApplicationContext(), lang);
        AppLang = CustomUtils.getInstance().getAppLanguage(getApplicationContext());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    public void cartItems(){
        cartViewModel.getCartProducts().observe(this, cartProducts -> {
            MyApp.setNotificationCounter(cartProducts.size());
            System.out.println("items in cart container :" + MyApp.getNotificationCounter());
        });
    }
}
