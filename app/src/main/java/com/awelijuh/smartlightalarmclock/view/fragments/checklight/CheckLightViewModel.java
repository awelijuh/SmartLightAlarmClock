package com.awelijuh.smartlightalarmclock.view.fragments.checklight;

import androidx.lifecycle.MutableLiveData;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.smartlightalarmclock.core.domain.Light;
import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.NoArgsConstructor;

@HiltViewModel
@NoArgsConstructor(onConstructor = @__(@Inject))
public class CheckLightViewModel {

    public final MutableLiveData<Light> light = new MutableLiveData<>(null);

    public final MutableLiveData<SchemaItem> schema = new MutableLiveData<>(null);

    @Inject
    Map<String, LedUseCase> leds;

    public LedUseCase getLed() {
        return leds.get(light.getValue().getType());
    }

    public void loadSchema() {
        getLed().getSchemaControl(light.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(schema::setValue);
    }

}
