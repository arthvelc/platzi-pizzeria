package com.platzi.pizza.persistence.audit;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.apache.logging.log4j.util.internal.SerializationUtil;
import org.springframework.util.SerializationUtils;

public class AuditPizzaListener {
    private PizzaEntity currentValue;


    @PostLoad
    public void postLoad(PizzaEntity entity){
        System.out.println("PostLoad");
        this.currentValue = SerializationUtils.clone(entity);
    }

    @PostPersist
    @PostUpdate
    public void onPostPersist(PizzaEntity entity){
        System.out.println("Post Persist update");
        System.out.println("Old value: " + this.currentValue);
        System.out.println("New value: " + entity.toString());
    }

    @PreRemove
    public void onPreDelete(PizzaEntity entity){
        System.out.println(entity.toString());
    }
}
