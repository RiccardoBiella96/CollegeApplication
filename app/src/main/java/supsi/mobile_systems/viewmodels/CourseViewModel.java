package supsi.mobile_systems.viewmodels;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import supsi.mobile_systems.databases.CollegeDatabase;
import supsi.mobile_systems.models.Course;

public class CourseViewModel extends AndroidViewModel {

    private CollegeDatabase collegeDatabase;
    private LiveData<List<Course>> courses;

    public CourseViewModel(Application application) {
        super(application);
        collegeDatabase = CollegeDatabase.getInstance(application.getApplicationContext());
        courses = collegeDatabase.getCourseDao().getCourses();
    }

    public LiveData<List<Course>> getCourses() { return courses; };

    public LiveData<List<Course>> findCoursesByInstructorUuid(long uuid) { return collegeDatabase.getCourseDao().findCoursesByInstructorUuid(uuid); };

    public void insertCourse(Course task) {
        new InsertCourseAsyncTask().execute(task);
    }

    private class InsertCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        @Override
        protected Void doInBackground(Course... courses) {
            Course course = courses[0];
            collegeDatabase.getCourseDao().insert(course);
            return null;
        }
    }
}