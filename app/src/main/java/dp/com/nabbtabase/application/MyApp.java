package dp.com.nabbtabase.application;


import android.app.Application;

import dp.com.nabbtabase.dagger.component.DaggerNetworkComponent;
import dp.com.nabbtabase.dagger.component.NetworkComponent;
import dp.com.nabbtabase.dagger.modules.AppModule;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
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



}
