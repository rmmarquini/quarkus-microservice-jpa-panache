package com.rmmarquini.quarkus.panache;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.time.Instant;

@Entity
public class Publisher extends PanacheEntity {

    public String name;
    public Instant created_at = Instant.now();

    public Publisher() {
    }

    public Publisher(String name) {
        this.name = name;
    }

}