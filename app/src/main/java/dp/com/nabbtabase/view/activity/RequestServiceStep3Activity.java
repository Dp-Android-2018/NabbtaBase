package dp.com.nabbtabase.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dp.com.nabbtabase.R;

public class RequestServiceStep3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_servise_step3);
    }

    public void done(View view){
        Intent intent=new Intent(this,ContainerActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
