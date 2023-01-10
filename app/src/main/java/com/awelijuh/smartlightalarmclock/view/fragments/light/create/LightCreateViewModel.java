package com.awelijuh.smartlightalarmclock.view.fragments.light.create;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.awelijuh.smartlightalarmclock.core.domain.Light;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.NoArgsConstructor;

@HiltViewModel
@NoArgsConstructor(onConstructor = @__(@Inject))
public class LightCreateViewModel extends ViewModel {

    public final MutableLiveData<String> name = new MutableLiveData<>(null);

    public final MutableLiveData<String> type = new MutableLiveData<>(null);

    public final MutableLiveData<Object> device = new MutableLiveData<>(null);

    public void clear() {
        name.setValue(null);
        type.setValue(null);
        device.setValue(null);
    }

    public void selectType(String type) {
        if (!Objects.equals(type, this.type.getValue())) {
            this.type.setValue(type);
        }
    }

    public Light createItem() {
        Light light = new Light();
        light.setName(name.getValue());
        light.setType(type.getValue());
        light.setDevice(device.getValue());
        return light;
    }
}
