package dp.com.nabbtabase.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {NetworkModule.class})
public interface ApiClient {

    Retrofit provideRetrofit();
}
