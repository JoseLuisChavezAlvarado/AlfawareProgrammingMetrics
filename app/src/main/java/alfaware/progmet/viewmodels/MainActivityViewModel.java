package alfaware.progmet.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import alfaware.progmet.abstract_classes.ListResult;
import alfaware.progmet.entities.Nota;
import alfaware.progmet.repositories.NotaRepository;
import alfaware.progmet.utilities.Status;

public class MainActivityViewModel extends SuperViewModel {

    private MutableLiveData<ListResult<Nota>> notas = new MutableLiveData<>();

    //==============================================================================================

    public LiveData<ListResult<Nota>> nota() {
        if (notas.getValue() == null) {
            notas.setValue(new ListResult<>());
            notas.getValue().getResult().setData(new ArrayList<>());
        }
        return notas;
    }

    //==============================================================================================

    public void getNota() {
        NotaRepository.getInstance().subscribeList(notas);
        setStatus(Status.STATUS_LOADING);
    }

    //==============================================================================================
}
