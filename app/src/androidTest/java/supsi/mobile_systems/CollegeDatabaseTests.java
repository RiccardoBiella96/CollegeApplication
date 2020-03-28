package supsi.mobile_systems;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import supsi.mobile_systems.databases.CollegeDatabase;
import supsi.mobile_systems.interfaces.CourseDao;
import supsi.mobile_systems.interfaces.InstructorDao;
import supsi.mobile_systems.models.Course;
import supsi.mobile_systems.models.Instructor;
import supsi.mobile_systems.models.Qualification;

@RunWith(AndroidJUnit4.class)
public class CollegeDatabaseTests {
    private InstructorDao instructorDao;
    private CourseDao courseDao;
    private CollegeDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, CollegeDatabase.class).build();
        instructorDao = db.getInstructorDao();
        courseDao = db.getCourseDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeAndReadTables() throws Exception {

        Instructor instructor0 = new Instructor();
        instructor0.setFirstName("Sandro");
        instructor0.setLastName("Pedrazzini");
        instructor0.setQualification(Qualification.PROFESSOR);
        long uuid0 = instructorDao.insert(instructor0);

        Instructor instructor1= new Instructor();
        instructor1.setFirstName("Patrick");
        instructor1.setLastName("Ceppi");
        instructor1.setQualification(Qualification.RESEARCHER);
        long uuid1 = instructorDao.insert(instructor1);

        Course course0 = new Course();
        course0.setTitle("Software engineering");
        course0.setCreditsNumber(9);
        course0.setDescription("At the end of this course students will be able to apply design patterns in their projects");
        course0.setInstructorUuid(uuid1);
        courseDao.insert(course0);

        List<Instructor> i = instructorDao.getInstructors().getValue();
        System.out.println();
//        Instructor byUuid0 = instructorDao.findInstructorByUuid(uuid1);
//        assertEquals(byUuid0.getUuid(), instructor1.getUuid());

//        List<Instructor> instructors = instructorDao.getInstructors();
//        assertEquals(instructors.size(), 2);

//        Course byUuid1 = courseDao.findCourseByUuid(course0.getUuid());
//        assertEquals(byUuid1.getInstructorUuid(), instructor0.getUuid());
    }
}