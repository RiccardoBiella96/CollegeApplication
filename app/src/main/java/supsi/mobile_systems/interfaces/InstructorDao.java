package supsi.mobile_systems.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import supsi.mobile_systems.models.Instructor;

@Dao
public interface InstructorDao {
    @Insert
    long insert(Instructor instructor);

    @Query("DELETE FROM instructor")
    void deleteInstructors();

    @Query("SELECT * FROM instructor")
    LiveData<List<Instructor>> getInstructors();

    @Query("SELECT * FROM instructor WHERE uuid = :uuid")
    Instructor findInstructorByUuid(long uuid);
}