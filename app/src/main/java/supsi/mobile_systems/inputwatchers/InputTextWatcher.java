package supsi.mobile_systems.inputwatchers;

import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Iterator;
import java.util.List;

public class InputTextWatcher implements TextWatcher {
    private AlertDialog alertDialog;
    private List<TextInputLayout> textInputLayoutList;
    private String errorMessage;

    public InputTextWatcher(List<TextInputLayout> textInputLayoutList, String errorMessage) {
        this.textInputLayoutList = textInputLayoutList;
        this.errorMessage = errorMessage;
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public List<TextInputLayout> getTextInputLayoutList() {
        return textInputLayoutList;
    }

    public void setTextInputLayoutList(List<TextInputLayout> textInputLayoutList) {
        this.textInputLayoutList = textInputLayoutList;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        boolean isValid = true;
        Iterator<TextInputLayout> it = textInputLayoutList.iterator();

        while (it.hasNext()) {
            TextInputLayout textInputLayout = it.next();
            if (textInputLayout.getEditText().getText().toString().isEmpty()) {
                textInputLayout.setError(errorMessage);
                isValid = false;
            } else {
                textInputLayout.setErrorEnabled(false);
            }
        }
        this.getAlertDialog().getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(isValid);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
