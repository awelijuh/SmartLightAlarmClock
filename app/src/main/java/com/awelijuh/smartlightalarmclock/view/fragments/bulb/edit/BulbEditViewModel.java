package com.awelijuh.smartlightalarmclock.view.fragments.bulb.edit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.awelijuh.smartlightalarmclock.adapters.database.domain.Bulb;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@HiltViewModel
@NoArgsConstructor(onConstructor = @__(@Inject))
public class BulbEditViewModel extends ViewModel {

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

    @SneakyThrows
    public Bulb createItem() {
        Bulb bulb = new Bulb();
        bulb.setName(name.getValue());
        bulb.setType(type.getValue());
        bulb.setDevice(new ObjectMapper().writeValueAsString(device.getValue()));
        return bulb;
    }
}
