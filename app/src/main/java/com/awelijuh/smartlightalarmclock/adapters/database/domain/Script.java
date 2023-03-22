package com.awelijuh.smartlightalarmclock.adapters.database.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class Script {

    @PrimaryKey
    private Long id;

    private String name;


}
