package com.awelijuh.schemagenerator;

import android.view.View;

import java.util.function.Supplier;

import lombok.Data;

@Data
public class FieldUiItem {

    private String key;

    private View view;

    private Supplier<?> value;

    private Assignable<Object> assignable;
}
