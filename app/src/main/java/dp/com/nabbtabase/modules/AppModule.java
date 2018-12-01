package dp.com.nabbtabase.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bahaa Gabal on 24,November,2018
 */

@Module
public class AppModule {

    Context mContext;

    public AppModule(Context context){
        this.mContext = context;
    }

    @Provides
    @Singleton
    Context getContext(){
        return mContext;
    }
}
