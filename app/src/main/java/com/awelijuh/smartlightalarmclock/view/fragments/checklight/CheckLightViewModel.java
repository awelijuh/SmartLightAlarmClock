package com.awelijuh.smartlightalarmclock.view.fragments.checklight;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.awelijuh.schemagenerator.dto.SchemaItem;
import com.awelijuh.smartlightalarmclock.adapters.database.domain.Bulb;
import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.NoArgsConstructor;

@HiltViewModel
@NoArgsConstructor(onConstructor = @__(@Inject))
public class CheckLightViewModel extends ViewModel {

    public final MutableLiveData<Bulb> bulb = new MutableLiveData<>(null);

    public final MutableLiveData<SchemaItem> schema = new MutableLiveData<>(null);

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    Map<String, LedUseCase> leds;

    public LedUseCase<?> getLed() {
        return leds.get(bulb.getValue().getType());
    }

    public void loadSchema() {
        compositeDisposable.add(
                getLed().getSchemaControl(bulb.getValue())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(schema::setValue)
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public void onTestButtonClick(Map<String, Object> result) {
        compositeDisposable.add(
                getLed().command(bulb.getValue(), result).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe()

        );
    }
}
