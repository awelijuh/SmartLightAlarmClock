package com.awelijuh.smartlightalarmclock.adapters.database.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class ScriptElement {

    @PrimaryKey
    private Long id;

    private Long time;

    private Long scriptId;

    private String payload;

}
