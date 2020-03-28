package supsi.mobile_systems.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import supsi.mobile_systems.R;
import supsi.mobile_systems.adapters.InstructorListArrayAdapter;
import supsi.mobile_systems.converters.QualificationConverter;
import supsi.mobile_systems.inputwatchers.InputTextWatcher;
import supsi.mobile_systems.models.Instructor;
import supsi.mobile_systems.viewmodels.InstructorViewModel;

public class MainActivity extends AppCompatActivity {

    private InstructorViewModel mInstructorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInstructorViewModel = new ViewModelProvider(this).get(InstructorViewModel.class);

        final ListView listView = findViewById(R.id.instructorList);

        mInstructorViewModel.getInstructors().observe(this, new Observer<List<Instructor>>() {
            @Override
            public void onChanged(List<Instructor> instructors) {
                if (instructors == null) {
                    return;
                }
                InstructorListArrayAdapter adapter = new InstructorListArrayAdapter(getApplicationContext(), R.layout.instructor_item, instructors);
                listView.setAdapter(adapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Instructor selectedInstructor = (Instructor)parent.getItemAtPosition(position);

                Intent intent = InstructorDetailActivity.newIntent(MainActivity.this, selectedInstructor);
                startActivity(intent);
            }
        });

        FloatingActionButton myFab = findViewById(R.id.floatingActionButton);

        myFab.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {

                 final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                 final View dialogView = getLayoutInflater().inflate(R.layout.add_instructor, null);

                 final TextInputLayout firstnameLayout = dialogView.findViewById(R.id.textInputFirstName);
                 final TextInputLayout lastnameLayout = dialogView.findViewById(R.id.textInputLastName);

                 List<TextInputLayout> textInputLayouts = new ArrayList<>();
                 textInputLayouts.add(firstnameLayout);
                 textInputLayouts.add(lastnameLayout);

                 InputTextWatcher inputTextWatcher = new InputTextWatcher(textInputLayouts, getResources().getString(R.string.error_message));
                 firstnameLayout.getEditText().addTextChangedListener(inputTextWatcher);
                 lastnameLayout.getEditText().addTextChangedListener(inputTextWatcher);

                 final Spinner spinner = dialogView.findViewById(R.id.spinner);
                 ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                         MainActivity.this,
                         android.R.layout.simple_spinner_item,
                         getResources().getStringArray(R.array.qualification_array));
                 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                 spinner.setAdapter(adapter);

                 alertDialogBuilder.setView(dialogView)
                         .setTitle(getResources().getString(R.string.instructor_dialog_title))
                         .setMessage(getResources().getString(R.string.intructor_dialog_message))
                         .setCancelable(false)
                         .setPositiveButton("confirm",new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog,int id) {

                                 String firstname_text = firstnameLayout.getEditText().getText().toString();
                                 String lastname_text = lastnameLayout.getEditText().getText().toString();

                                 if(!firstname_text.isEmpty() && !lastname_text.isEmpty()){
                                     Instructor instructor= new Instructor();
                                     instructor.setFirstName(firstname_text);
                                     instructor.setLastName(lastname_text);
                                     instructor.setQualification(QualificationConverter.fromIntegerToQualification(spinner.getSelectedItemPosition()));
                                     mInstructorViewModel.insertInstructor(instructor);

                                     Toast.makeText(MainActivity.this, getResources().getString(R.string.add_instructor_toast), Toast.LENGTH_LONG).show();
                                 }
                             }
                         })
                         .setNegativeButton("cancel",new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog,int id) {
                                 dialog.cancel();
                             }
                         });

                 AlertDialog alertDialog = alertDialogBuilder.create();
                 inputTextWatcher.setAlertDialog(alertDialog);
                 alertDialog.show();
                 alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
             }});
    }

}
