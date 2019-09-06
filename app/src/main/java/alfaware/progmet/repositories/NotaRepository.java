package alfaware.progmet.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import alfaware.progmet.abstract_classes.ListResult;
import alfaware.progmet.abstract_classes.Result;
import alfaware.progmet.entities.Nota;
import alfaware.progmet.firebase.FirebaseReference;

public class NotaRepository {

    private static NotaRepository instance;

    public static NotaRepository getInstance() {
        if (instance == null) {
            instance = new NotaRepository();
        }
        return instance;
    }

    //==============================================================================================

    public void subscribeList(MutableLiveData<ListResult<Nota>> liveData) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(FirebaseReference.NOTAS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                liveData.getValue().getResult().getData().clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Nota Nota = snapshot.getValue(Nota.class);
                    Nota.setId(snapshot.getKey());
                    liveData.getValue().getResult().getData().add(Nota);
                }
                liveData.postValue(liveData.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                liveData.getValue().getResult().setException(databaseError.toException());
                databaseError.toException().printStackTrace();
                liveData.postValue(liveData.getValue());
            }
        });
    }

    public void subscribe(MutableLiveData<Result<Nota>> liveData) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(FirebaseReference.NOTAS).child(liveData.getValue().getResult().getData().getId());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Nota Nota = dataSnapshot.getValue(Nota.class);
                Nota.setId(dataSnapshot.getKey());
                liveData.getValue().getResult().setData(Nota);
                liveData.postValue(liveData.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                liveData.getValue().getResult().setException(databaseError.toException());
                databaseError.toException().printStackTrace();
                liveData.postValue(liveData.getValue());
            }
        });
    }

    public void add(MutableLiveData<Result<Nota>> liveData) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(FirebaseReference.NOTAS).child(liveData.getValue().getResult().getData().getId());
        reference.setValue(liveData.getValue().getResult().getData()).addOnSuccessListener(aVoid -> liveData.postValue(liveData.getValue())).addOnFailureListener(e -> {
            liveData.getValue().getResult().setException(e);
            liveData.postValue(liveData.getValue());
        });
    }

    //==============================================================================================

}
