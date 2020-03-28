package supsi.mobile_systems.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import supsi.mobile_systems.converters.QualificationConverter;
import supsi.mobile_systems.interfaces.CourseDao;
import supsi.mobile_systems.interfaces.InstructorDao;
import supsi.mobile_systems.models.Course;
import supsi.mobile_systems.models.Instructor;
import supsi.mobile_systems.models.Qualification;

@Database(entities = {Instructor.class, Course.class}, version = 1)
@TypeConverters({ QualificationConverter.class })
public abstract class CollegeDatabase extends RoomDatabase {

    private static volatile CollegeDatabase INSTANCE;
    public abstract InstructorDao getInstructorDao();
    public abstract CourseDao getCourseDao();

    public static CollegeDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CollegeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CollegeDatabase.class,
                            "college_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}