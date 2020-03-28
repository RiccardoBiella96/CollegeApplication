package supsi.mobile_systems.converters;

import androidx.room.TypeConverter;

import supsi.mobile_systems.models.Qualification;

public class QualificationConverter {
    @TypeConverter
    public static Integer fromQualificationToInteger(Qualification qualification) {
        if (qualification != null)
            return qualification.getCode();
        return null;
    }

    @TypeConverter
    public static Qualification fromIntegerToQualification(Integer code) {
        if(code != null) {
            switch (code) {
                case 0:
                    return Qualification.PROFESSOR;
                case 1:
                    return Qualification.ASSISTANT;
                case 2:
                    return Qualification.RESEARCHER;
            }
        }
        return null;
    }
}
