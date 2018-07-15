package com.gmail.bishoybasily.yolo.sample;

import com.gmail.bishoybasily.yolo.annotations.Bean;
import com.gmail.bishoybasily.yolo.annotations.Configuration;
import com.gmail.bishoybasily.yolo.annotations.Qualifier;
import com.gmail.bishoybasily.yolo.annotations.Scope;


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
