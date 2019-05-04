package dp.com.nabbtabase.dagger.modules;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.servise.repository.EndPoints;
import dp.com.nabbtabase.utils.ConfigurationFile;
import dp.com.nabbtabase.utils.CustomUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bahaa Gabal on 24,November,2018
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.header("Accept-Language", CustomUtils.getInstance().getAppLanguage(MyApp.getInstance()));
                requestBuilder.header("x-api-key", ConfigurationFile.Constants.API_KEY);
                requestBuilder.header("Content-Type", ConfigurationFile.Constants.CONTENT_TYPE);
                requestBuilder.header("Accept", ConfigurationFile.Constants.CONTENT_TYPE);
                // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = okHttpClient.build();
        return client;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Context context, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConfigurationFile.UrlConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    EndPoints getRerofitEndPoint(Retrofit retrofit) {
        return retrofit.create(EndPoints.class);
    }
}
