package com.allianz.automated.testing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Policy {

    @Id
    private String id;
    private String operationNumber;
    private String restOfThings;

    public Policy(Policy other) {
        this.id = UUID.randomUUID().toString();
        this.operationNumber = other.operationNumber;
        this.restOfThings = other.restOfThings;
    }

}
