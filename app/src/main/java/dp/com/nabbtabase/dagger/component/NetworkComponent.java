package dp.com.nabbtabase.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import dp.com.nabbtabase.dagger.modules.AppModule;
import dp.com.nabbtabase.dagger.modules.NetworkModule;
import dp.com.nabbtabase.servise.repository.EndPoints;


@Singleton
@Component(modules = {AppModule.class,NetworkModule.class})
public interface NetworkComponent {

    EndPoints getEndPoint();
}
