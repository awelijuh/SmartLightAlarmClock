package com.awelijuh.smartlightalarmclock.adapters.database.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class Bulb {

    @PrimaryKey
    private Long id;

    private String name;

    private String type;

    private String device;

}
