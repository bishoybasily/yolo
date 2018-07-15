package com.gmail.bishoybasily.yolo.sample;

import com.gmail.bishoybasily.yolo.annottions.Bean;
import com.gmail.bishoybasily.yolo.annottions.Configuration;
import com.gmail.bishoybasily.yolo.annottions.Qualifier;
import com.gmail.bishoybasily.yolo.annottions.Scope;


@Configuration
public class FeatureOne {

    private Token token;

    public FeatureOne(Token token) {
        this.token = token;
    }

    @Bean
    @Scope("prototype")
    public ActivityOne activityOne(ServiceRegistration serviceRegistration, @Qualifier("oracle") Database database) {
        return new ActivityOne(serviceRegistration, database);
    }

    @Bean
    @Scope("singleton")
    public Something something() {
        return new Something();
    }

}
