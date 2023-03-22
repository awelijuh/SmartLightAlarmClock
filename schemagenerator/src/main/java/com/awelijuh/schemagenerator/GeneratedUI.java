package com.awelijuh.schemagenerator;


import android.content.Context;
import android.widget.LinearLayout;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.schemagenerator.dto.TypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

public class GeneratedUI {

    private static final Map<Class, TypeEnum> FIELD_MAP = Map.of(
            String.class, TypeEnum.String,
            Integer.class, TypeEnum.Integer,
            Boolean.class, TypeEnum.Boolean
    );

    private final Context context;
    private final LinearLayout linearLayout;
    private FieldUiItem rootItem;

    public GeneratedUI(Context context, LinearLayout linearLayout) {
        this.context = context;
        this.linearLayout = linearLayout;
    }

    public static <T> SchemaItem mapClassToSchema(Class<T> tClass) {
        SchemaItem schemaItem = new SchemaItem();
        schemaItem.setType(TypeEnum.Json);
        schemaItem.setCode("root");

        Map<String, SchemaItem> map = new HashMap<>();

        FieldUtils.getFieldsListWithAnnotation(tClass, UiField.class).forEach(e -> {
            UiField a = e.getDeclaredAnnotation(UiField.class);
            SchemaItem s = new SchemaItem();

            if (e.getType().isEnum()) {
                s.setType(TypeEnum.Enum);
                s.setRange(Arrays.stream(e.getType().getEnumConstants()).map(String::valueOf).collect(Collectors.toList()));
            } else if (FIELD_MAP.containsKey(e.getType())) {
                s.setType(FIELD_MAP.get(e.getType()));
            } else {
                s.setType(TypeEnum.Json);
                SchemaItem item = mapClassToSchema(e.getType());
                s.setValues(item.getValues());
            }

            s.setMin(a.min());
            s.setMax(a.max());

            s.setName(a.name());
            if (s.getName() == null || s.getName().isEmpty()) {
                s.setName(e.getName());
            }
            s.setCode(e.getName());
            map.put(e.getName(), s);
        });
        schemaItem.setValues(map);

        return schemaItem;
    }

    public <T> void build(Class<T> tClass) {
        linearLayout.removeAllViews();

        rootItem = SchemaGeneratorUtils.createJsonInput(context, mapClassToSchema(tClass));

        linearLayout.addView(rootItem.getView());
    }

    public void build(SchemaItem schemaItem) {
        linearLayout.removeAllViews();
        rootItem = SchemaGeneratorUtils.createJsonInput(context, schemaItem);

        linearLayout.addView(rootItem.getView());
    }

    public Map<String, Object> getMapResult() {
        return (Map<String, Object>) rootItem.getValue().get();
    }

    @SneakyThrows
    public <T> T getResult(Class<T> tClass) {
        T target = tClass.getConstructor().newInstance();
        for (Map.Entry<String, Object> e : getMapResult().entrySet()) {
            String key = e.getKey();
            Object value = e.getValue();
            Field field = tClass.getDeclaredField(key);
            field.setAccessible(true);
            FieldUtils.writeField(field, target, value);
            field.setAccessible(false);
        }
        return target;
    }

    public void setValue(Map<String, Object> value) {
        if (value == null) {
            return;
        }
        rootItem.getAssignable().assign(value);
    }

    public <T> void setValue(T t) {
        if (t == null) {
            return;
        }
        setValue(mapObjectToMap(t));
    }

    @SneakyThrows
    private Map<String, Object> mapObjectToMap(Object o) {
        Map<String, Object> result = new HashMap<>();
        for (Field e : FieldUtils.getFieldsListWithAnnotation(o.getClass(), UiField.class)) {
            e.setAccessible(true);
            result.put(e.getName(), e.get(o));
            e.setAccessible(false);
        }

        return result;
    }

}
