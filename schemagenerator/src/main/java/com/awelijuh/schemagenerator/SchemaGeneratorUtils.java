package com.awelijuh.schemagenerator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.schemagenerator.dto.TypeEnum;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class SchemaGeneratorUtils {

    public static FieldUiItem createView(Context context, SchemaItem item) {
        switch (item.getType()) {
            case Integer:
                return createIntegerInput(context, item);
            case String:
                return createTextInput(context, item);
            case Boolean:
                return createBooleanInput(context, item);
            case Enum:
                return createEnumInput(context, item);
            case Json:
                return createJsonInput(context, item);
        }

        return null;
    }

    public static FieldUiItem createJsonInput(Context context, SchemaItem item) {

        LinearLayout linearLayout = createLinearLayout(context);

        List<FieldUiItem> fieldUiItemList = new ArrayList<>();
        item.getValues().forEach((key, value) -> {
            if (value.getCode() == null) {
                value.setCode(key);
            }
            if (value.getName() == null) {
                value.setName(key);
            }
            if (value.getType() == null) {
                if (value.getMin() != null && value.getMax() != null) {
                    value.setType(TypeEnum.Integer);
                }
                if (value.getRange() != null) {
                    value.setType(TypeEnum.Enum);
                }
                if (value.getValues() != null) {
                    value.setType(TypeEnum.Json);
                }
            }
            var res = createView(context, value);
            linearLayout.addView(res.getView());
            fieldUiItemList.add(res);
        });
        FieldUiItem result = new FieldUiItem();
        result.setKey(item.getCode());
        result.setView(createNameContainer(context, item.getName(), linearLayout));
        result.setValue(() -> fieldUiItemList.stream().collect(Collectors.toMap(FieldUiItem::getKey, e -> e.getValue().get())));
        result.setAssignable(v -> fieldUiItemList.forEach(e -> {
            e.getAssignable().assign(((Map<String, Object>) v).get(e.getKey()));
        }));
        return result;
    }

    private static FieldUiItem createEnumInput(Context context, SchemaItem item) {
        Spinner spinner = new Spinner(context);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, item.getRange());
        spinner.setAdapter(adapter);

        FieldUiItem result = new FieldUiItem();
        result.setKey(item.getCode());
        result.setView(createNameContainer(context, item.getName(), spinner));
        result.setValue(spinner::getSelectedItem);
        result.setAssignable(v -> spinner.setSelection(item.getRange().indexOf(v)));

        return result;
    }

    private static FieldUiItem createBooleanInput(Context context, SchemaItem item) {
        SwitchMaterial s = new SwitchMaterial(context);
        s.setText(item.getName());

        FieldUiItem result = new FieldUiItem();
        result.setKey(item.getCode());
        result.setView(s);
        result.setValue(s::isChecked);
        result.setAssignable(v -> s.setChecked((Boolean) v));
        return result;
    }

    public static FieldUiItem createTextInput(Context context, SchemaItem item) {
//        TextInputLayout textInputLayout = new TextInputLayout(context);
        TextInputEditText editText = new TextInputEditText(context);

        editText.setHint(item.getName());

//        textInputLayout.addView(editText);

        FieldUiItem result = new FieldUiItem();
        result.setView(editText);
        result.setValue(() -> editText.getText().toString());
        result.setKey(item.getCode());
        result.setAssignable(v -> editText.setText((String) v));

        return result;
    }

    private static View createNameContainer(Context context, String name, View view) {
        if (name == null) {
            return view;
        }
        LinearLayout container = createLinearLayout(context, LinearLayout.VERTICAL);
        TextView textView = new TextView(context);
        textView.setText(name + ":");

        container.addView(textView);

        container.addView(view);

        return container;
    }

    public static FieldUiItem createIntegerInput(Context context, SchemaItem item) {
        LinearLayout container = createLinearLayout(context);

        SeekBar seekBar = new SeekBar(context);
        seekBar.setMin(item.getMin());
        seekBar.setMax(item.getMax());
        seekBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        container.addView(seekBar);
        container.addView(textView);

        FieldUiItem result = new FieldUiItem();
        result.setView(createNameContainer(context, item.getName(), container));
        result.setKey(item.getCode());
        result.setValue(seekBar::getProgress);
        result.setAssignable(v -> seekBar.setProgress((Integer) v));

        return result;
    }

    private static LinearLayout createLinearLayout(Context context, int orientation) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(orientation);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return linearLayout;
    }

    private static LinearLayout createLinearLayout(Context context) {
        return createLinearLayout(context, LinearLayout.VERTICAL);
    }

}

