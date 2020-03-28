package supsi.mobile_systems.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import supsi.mobile_systems.R;
import supsi.mobile_systems.models.Course;
import supsi.mobile_systems.models.Instructor;

public class CourseDetailActivity extends AppCompatActivity {

    private static final String EXTRA_INSTRUCTOR = "EXTRA_INSTRUCTOR";
    private static final String EXTRA_COURSE = "EXTRA_COURSE";
    private Instructor instructor;
    private Course course;

    public static Intent newIntent(Context packageContext, Instructor instructor, Course course) {
        Intent intent = new Intent(packageContext, CourseDetailActivity.class);
        intent.putExtra(EXTRA_INSTRUCTOR, instructor);
        intent.putExtra(EXTRA_COURSE, course);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        instructor = (Instructor) getIntent().getSerializableExtra("EXTRA_INSTRUCTOR");
        course = (Course) getIntent().getSerializableExtra("EXTRA_COURSE");

        final TextView instructor_name = findViewById(R.id.i_name);
        final TextView instructor_qualification = findViewById(R.id.i_qualification);
        final TextView course_title = findViewById(R.id.c_title);
        final TextView course_ects = findViewById(R.id.c_ects);
        final TextView course_description = findViewById(R.id.c_description);

        instructor_name.setText(instructor.getFirstName() + " " + instructor.getLastName());
        instructor_qualification.setText(instructor.getQualification().toString());
        course_title.setText(course.getTitle());
        course_ects.setText(course.getCreditsNumber() + " Credits");
        course_description.setText(course.getDescription());
    }
}
