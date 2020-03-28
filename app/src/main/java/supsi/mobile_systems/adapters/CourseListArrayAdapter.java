package supsi.mobile_systems.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import supsi.mobile_systems.R;
import supsi.mobile_systems.models.Course;

public class CourseListArrayAdapter extends ArrayAdapter {
    private List<Course> mCourses;

    public CourseListArrayAdapter(@NonNull Context context, int resource, List<Course> courses) {
        super(context, resource, courses);
        mCourses = courses;
    }

    @Override
    public int getCount() {
        return mCourses.size();
    }

    @Override
    public Course getItem(int position) {
        return mCourses.get(position);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        }
        Course course = getItem(position);
        TextView courseTitle = convertView.findViewById(R.id.course_title);
        courseTitle.setText(course.getTitle());
        TextView courseCredits = convertView.findViewById(R.id.course_credits);
        courseCredits.setText(course.getCreditsNumber()+ " Credits");

        return convertView;
    }
}