package dp.com.nabbtabase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.servise.model.pojo.ConnectionReceiver;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.utils.NetWorkConnection;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity implements ConnectionReceiver.ConnectionReceiverListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.notification_bar_color));
        }
        checkConnection();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        if (CustomUtils.getInstance().getAppLanguage(newBase) != null &&
                CustomUtils.getInstance().getAppLanguage(newBase).equals("ar"))
            super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        else super.attachBaseContext(newBase);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApp.getInstance().setConnectionListener(this);
    }

    public void checkConnection() {
        if (!NetWorkConnection.isConnectingToInternet(this)) {
            Intent intent = new Intent(this, NoInternetActivity.class);
            startActivity(intent);
            finishAffinity();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        if (NetWorkConnection.isConnectingToInternet(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        } else {
            Intent intent = new Intent(this, NoInternetActivity.class);
            startActivity(intent);
            finishAffinity();
        }
    }
}
