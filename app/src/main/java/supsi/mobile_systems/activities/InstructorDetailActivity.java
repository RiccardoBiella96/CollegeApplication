package supsi.mobile_systems.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import supsi.mobile_systems.R;
import supsi.mobile_systems.adapters.CourseListArrayAdapter;
import supsi.mobile_systems.inputwatchers.InputTextWatcher;
import supsi.mobile_systems.models.Course;
import supsi.mobile_systems.models.Instructor;
import supsi.mobile_systems.viewmodels.CourseViewModel;

public class InstructorDetailActivity extends AppCompatActivity {

    private static final String EXTRA_INSTRUCTOR = "EXTRA_INSTRUCTOR";
    private Instructor instructor;
    private CourseViewModel mCourseViewModel;

    public static Intent newIntent(Context packageContext, Instructor instructor) {
        Intent intent = new Intent(packageContext, InstructorDetailActivity.class);
        intent.putExtra(EXTRA_INSTRUCTOR, instructor);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_detail);

        instructor = (Instructor) getIntent().getSerializableExtra("EXTRA_INSTRUCTOR");

        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        final ListView listView = findViewById(R.id.courseList);
        final TextView instructorInformation = findViewById(R.id.instructor_info);
        instructorInformation.setText(instructor.getQualification() + ": " + instructor.getFirstName() + " " + instructor.getLastName());

        mCourseViewModel.findCoursesByInstructorUuid(instructor.getUuid()).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                if (courses == null) {
                    return;
                }
                CourseListArrayAdapter adapter = new CourseListArrayAdapter(getApplicationContext(), R.layout.course_item, courses);
                listView.setAdapter(adapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course selectedCourse = (Course) parent.getItemAtPosition(position);

                Intent intent = CourseDetailActivity.newIntent(InstructorDetailActivity.this, instructor, selectedCourse);
                startActivity(intent);
            }
        });

        FloatingActionButton myFab = findViewById(R.id.floatingActionButton);

        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InstructorDetailActivity.this);
                final View dialogView = getLayoutInflater().inflate(R.layout.add_course, null);

                final TextInputLayout titleLayout = dialogView.findViewById(R.id.textInputTitle);
                final TextInputLayout descriptionLayout = dialogView.findViewById(R.id.textInputDescription);
                final TextInputLayout creditsLayout = dialogView.findViewById(R.id.creditsInput);


                List<TextInputLayout> textInputLayouts = new ArrayList<>();
                textInputLayouts.add(titleLayout);
                textInputLayouts.add(descriptionLayout);

                InputTextWatcher inputTextWatcher = new InputTextWatcher(textInputLayouts, getResources().getString(R.string.error_message));
                titleLayout.getEditText().addTextChangedListener(inputTextWatcher);
                descriptionLayout.getEditText().addTextChangedListener(inputTextWatcher);

                alertDialogBuilder.setView(dialogView)
                        .setTitle(getResources().getString(R.string.course_dialog_title))
                        .setMessage(getResources().getString(R.string.course_dialog_message))
                        .setCancelable(false)
                        .setPositiveButton("confirm",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                String title_text = titleLayout.getEditText().getText().toString();
                                String description_text = descriptionLayout.getEditText().getText().toString();

                                int creditsLayout_number;
                                try{
                                    creditsLayout_number = Integer.parseInt(creditsLayout.getEditText().getText().toString());
                                }catch (Exception e){
                                    creditsLayout_number = 0;
                                }

                                if(!title_text.isEmpty() && !description_text.isEmpty()){
                                    Course course = new Course();
                                    course.setTitle(title_text);
                                    course.setDescription(description_text);
                                    course.setInstructorUuid(instructor.getUuid());
                                    course.setCreditsNumber(creditsLayout_number);
                                    mCourseViewModel.insertCourse(course);

                                    Toast.makeText(InstructorDetailActivity.this, getResources().getString(R.string.add_course_toast), Toast.LENGTH_LONG).show();
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
