package com.gmail.bishoybasily.yolo.sample;

import com.gmail.bishoybasily.yolo.annottions.Bean;
import com.gmail.bishoybasily.yolo.annottions.Configuration;
import com.gmail.bishoybasily.yolo.annottions.Qualifier;


@Configuration
public class Application {

    private FeatureOne featureOne;
    private FeatureTwo featureTwo;

    public Application(FeatureOne featureOne,
                       FeatureTwo featureTwo) {
        this.featureOne = featureOne;
        this.featureTwo = featureTwo;
    }

    @Bean
    @Qualifier("oracle")
    public Database db1(User user) {
        return new Oracle(user);
    }

    @Bean
    @Qualifier("mongoDb")
    public Database db2(User user) {
        return new MongoDb(user);
    }

    @Bean
    public RepositoryMobiles repositoryMobiles(@Qualifier("mongoDb") Database database) {
        return new RepositoryMobiles(database);
    }

    @Bean
    public RepositoryUsers repositoryUsers(Database database) {
        return new RepositoryUsers(database);
    }

    @Bean
    public ServiceRegistration serviceRegistration(RepositoryUsers repositoryUsers,
                                                   RepositoryMobiles repositoryMobiles) {
        return new ServiceRegistration(repositoryUsers, repositoryMobiles);
    }

}
