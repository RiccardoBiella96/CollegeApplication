package supsi.mobile_systems.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {
        @ForeignKey(entity = Instructor.class,
                    parentColumns = "uuid",
                    childColumns = "instructorUuid")}
        )
public class Course implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long uuid;

    private long instructorUuid;
    private String title;
    private String description;
    private int creditsNumber;

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public long getInstructorUuid() {
        return instructorUuid;
    }

    public void setInstructorUuid(long instructorUuid) {
        this.instructorUuid = instructorUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreditsNumber() {
        return creditsNumber;
    }

    public void setCreditsNumber(int creditsNumber) {
        this.creditsNumber = creditsNumber;
    }
}
