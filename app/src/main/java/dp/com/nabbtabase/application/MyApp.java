package dp.com.nabbtabase.application;


import android.app.Application;
import android.databinding.ObservableDouble;
import android.databinding.ObservableInt;

import dp.com.nabbtabase.dagger.component.DaggerNetworkComponent;
import dp.com.nabbtabase.dagger.component.NetworkComponent;
import dp.com.nabbtabase.dagger.modules.AppModule;
import dp.com.nabbtabase.databinding.ActivityCardBinding;

public class MyApp extends Application {

    private static double total;
    private static MyApp mInstance;
    private static ActivityCardBinding binding;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        initializeDaggerNetworkComponent();
    }

    private NetworkComponent daggerNetworkComponent;

    public void initializeDaggerNetworkComponent() {
        daggerNetworkComponent = DaggerNetworkComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public NetworkComponent getDaggerNetworkComponent() {
        return daggerNetworkComponent;
    }

    public static synchronized MyApp getInstance() {
        return mInstance;
    }
    public static double getTotal() {
        System.out.println("application get total "+total);
        return total;
    }

    public static void setTotal(double totalval) {
        System.out.println("application set total "+totalval);
       total=(totalval);
       binding.tvTotalAmount.setText(String.valueOf(total));
    }

    public static void setBinding(ActivityCardBinding binding) {
        MyApp.binding = binding;
    }
}
