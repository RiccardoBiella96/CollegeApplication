package supsi.mobile_systems.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import supsi.mobile_systems.models.Course;

@Dao
public interface CourseDao {
    @Insert
    void insert(Course course);

    @Query("DELETE FROM course")
    void deleteCourses();

    @Query("SELECT * FROM course")
    LiveData<List<Course>> getCourses();

    @Query("SELECT * FROM course WHERE uuid = :uuid")
    Course findCourseByUuid(long uuid);

    @Query("SELECT * FROM course WHERE instructorUuid =  :uuid")
    LiveData<List<Course>> findCoursesByInstructorUuid(long uuid);
}
