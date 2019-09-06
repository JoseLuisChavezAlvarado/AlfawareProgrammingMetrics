package alfaware.progmet.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import alfaware.progmet.R;
import alfaware.progmet.adapters.MainActivityAdapter;
import alfaware.progmet.databinding.MainActivityBinding;
import alfaware.progmet.utilities.Status;
import alfaware.progmet.viewmodels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private MainActivityBinding binding;
    private MainActivityAdapter adapter;

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        init();
        setListeners();
        setObservers();
    }

    //==============================================================================================

    private void init() {
        adapter = new MainActivityAdapter(getApplicationContext(), viewModel.nota().getValue().getResult().getData());
        binding.listView.setAdapter(adapter);

        viewModel.getNota();
    }

    private void setListeners() {
        binding.listView.setOnItemClickListener((adapterView, view, i, l) -> Toast.makeText(getApplicationContext(), "ID: " + adapter.getItem(i).getId(), Toast.LENGTH_SHORT).show());
    }

    private void setObservers() {
        viewModel.nota().observe(this, result -> {
            Exception exception = result.getResult().getException();
            if (exception != null) {
                Snackbar.make(getCurrentFocus(), exception.getMessage(), Snackbar.LENGTH_SHORT).show();
                viewModel.setStatus(Status.STATUS_ERROR);
                exception.printStackTrace();
            } else if (result.getResult().getData().isEmpty()) {
                viewModel.setStatus(Status.STATUS_EMPTY);
                viewModel.getNota();
            } else {
                viewModel.setStatus(Status.STATUS_OK);
            }

            adapter.notifyDataSetChanged();
        });
        viewModel.getStatus().observe(this, integer -> {
            binding.status.empty.setVisibility(View.GONE);
            binding.status.error.setVisibility(View.GONE);
            binding.status.loading.setVisibility(View.GONE);

            if (integer != null) {
                switch (integer) {
                    case Status.STATUS_LOADING:
                        binding.status.loading.setVisibility(View.VISIBLE);
                        break;
                    case Status.STATUS_EMPTY:
                        binding.status.empty.setVisibility(View.VISIBLE);
                        break;
                    case Status.STATUS_ERROR:
                        binding.status.error.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    //==============================================================================================


    //==============================================================================================

}
