package dp.com.nabbtabase.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.utils.CustomUtils;
import dp.com.nabbtabase.utils.NetWorkConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            if(NetWorkConnection.isConnectingToInternet(getApplicationContext())) {

                if (CustomUtils.getInstance().getSaveUserObject(getApplicationContext()) != null) {
                    if(CustomUtils.getInstance().getSaveUserObject(getApplicationContext()).getActivated().equals("true")) {
                        Intent i = new Intent(MainActivity.this, ContainerActivity.class);
                        startActivity(i);
                    }else {
                        // TODO: 12/11/2018 call activation activity
                        Intent i = new Intent(MainActivity.this, ContainerActivity.class);
                        startActivity(i);
                    }

                } else {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }else{
                Snackbar.make((findViewById(R.id.cl_root)),"No Internet Connection",Snackbar.LENGTH_LONG).show();
            }
                finish();
        },3000);

    }
}
