package ua.com.prologistic.openweathermapapp.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ua.com.prologistic.openweathermapapp.R;
import ua.com.prologistic.openweathermapapp.util.PreferenceHelper;

public class LocationDialogFragment extends DialogFragment {

    private LocationChangeListener locationChangeListener;
    PreferenceHelper preferenceHelper;

    public interface LocationChangeListener {
        void onLocationChanged();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            locationChangeListener = (LocationChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement interface LocationChangeListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        preferenceHelper = PreferenceHelper.getInstance();
        String currentLocation = preferenceHelper.getString(PreferenceHelper.PREF_KEY_LOCATION);

        // init UI
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(R.string.dialog_title_location);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View container = inflater.inflate(R.layout.dialog_location, null);

        final TextInputLayout tilLocation = (TextInputLayout) container.findViewById(R.id.tilLocation);
        final EditText editTextLocation = tilLocation.getEditText();
        tilLocation.setHint(getResources().getString(R.string.text_location));
        if (currentLocation != null && !currentLocation.isEmpty()) {
            editTextLocation.setText(currentLocation);
        }

        alertDialogBuilder.setView(container);

        // init dialog buttons
        alertDialogBuilder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                preferenceHelper.putString(PreferenceHelper.PREF_KEY_LOCATION, editTextLocation.getText().toString());
                locationChangeListener.onLocationChanged();
                dialogInterface.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        // buttons behavior configuration
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                if (editTextLocation.length() == 0) {
                    positiveButton.setEnabled(false);
                    tilLocation.setError(getResources().getString(R.string.dialog_title_error));
                }
                editTextLocation.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                        if (charSequence.length() == 0) {
                            positiveButton.setEnabled(false);
                            tilLocation.setError(getResources().getString(R.string.dialog_title_error));
                        } else {
                            positiveButton.setEnabled(true);
                            tilLocation.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });

            }
        });

        return alertDialog;
    }
}
