package com.awelijuh.smartlightalarmclock.view.fragments.bulb;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.awelijuh.smartlightalarmclock.adapters.database.dao.BulbDao;
import com.awelijuh.smartlightalarmclock.adapters.database.domain.Bulb;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import lombok.NoArgsConstructor;

@HiltViewModel
@NoArgsConstructor(onConstructor = @__(@Inject))
public class BulbViewModel extends ViewModel {

    public final MutableLiveData<Bulb> selectedBulb = new MutableLiveData<>(null);

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    BulbDao bulbDao;

    public void saveBulb(Bulb bulb) {
        bulbDao.save(bulb);
    }

    public void clear() {
        selectedBulb.setValue(null);
    }

    public void init() {
        clear();
    }

}