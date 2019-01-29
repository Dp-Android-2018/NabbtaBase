package dp.com.nabbtabase.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ActivityNoIternetBinding;

public class NoInternetActivity extends BaseActivity {

    ActivityNoIternetBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_no_iternet);
    }
}
