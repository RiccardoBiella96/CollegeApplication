package supsi.mobile_systems.viewmodels;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import supsi.mobile_systems.databases.CollegeDatabase;
import supsi.mobile_systems.models.Instructor;

public class InstructorViewModel extends AndroidViewModel {

    private CollegeDatabase collegeDatabase;
    private LiveData<List<Instructor>> instructors;

    public InstructorViewModel(Application application) {
        super(application);
        collegeDatabase = CollegeDatabase.getInstance(application.getApplicationContext());
        instructors = collegeDatabase.getInstructorDao().getInstructors();
    }

    public LiveData<List<Instructor>> getInstructors() { return instructors; };

    public void insertInstructor(Instructor task) {
        new InsertInstructorAsyncTask().execute(task);
    }

    private class InsertInstructorAsyncTask extends AsyncTask<Instructor, Void, Void> {
        @Override
        protected Void doInBackground(Instructor... instructors) {
            Instructor instructor = instructors[0];
            collegeDatabase.getInstructorDao().insert(instructor);
            return null;
        }
    }
}