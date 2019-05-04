package dp.com.nabbtabase.application;


import android.app.Application;
import androidx.databinding.ObservableInt;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.dagger.component.DaggerNetworkComponent;
import dp.com.nabbtabase.dagger.component.NetworkComponent;
import dp.com.nabbtabase.dagger.modules.AppModule;
import dp.com.nabbtabase.databinding.ActivityCardBinding;
import dp.com.nabbtabase.servise.model.pojo.ConnectionReceiver;
import dp.com.nabbtabase.utils.CustomUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApp extends Application {

    private static double total;
    private static MyApp mInstance;
    private static ActivityCardBinding binding;
    private Map<Integer, Boolean> selectedProducts;
    private static ObservableInt notificationCounter = new ObservableInt();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        if (CustomUtils.getInstance().getAppLanguage(this).equals("ar")) {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("font/Arabic_Font.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );
        } else {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("font/raleway_regular.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );
        }


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
        System.out.println("application get total " + total);
        return total;
    }

    public static void setTotal(double totalval) {
        System.out.println("application set total " + totalval);
        total = (totalval);
        binding.tvTotalAmount.setText(new DecimalFormat("##.##").format(total));
    }

    public static void setBinding(ActivityCardBinding binding) {
        MyApp.binding = binding;
    }

    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener) {
        ConnectionReceiver.connectionReceiverListener = listener;
    }

    public static void setNotificationCounter(int notificationCounter) {
        MyApp.notificationCounter.set(notificationCounter);
        MyApp.notificationCounter.notifyChange();
        //binding.actionBar.tvNotification.setText(String.valueOf(notificationCounter));
    }

    public static int getNotificationCounter() {
        return notificationCounter.get();
    }

    public void createSelectedProducts() {
        if (selectedProducts == null) {
            selectedProducts = new HashMap<>();
        }
    }

    public void addProductToSelectedProducts(int id, boolean status) {
        selectedProducts.put(id, status);
    }

    public void destorySelectedProductsMap() {
        if (selectedProducts != null) {
            selectedProducts.clear();
        }
    }

    public Map<Integer, Boolean> getSelectedProducts() {
        return selectedProducts;
    }
}


