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
import supsi.mobile_systems.models.Instructor;

public class InstructorListArrayAdapter extends ArrayAdapter {
    private List<Instructor> mInstructors;

    public InstructorListArrayAdapter(@NonNull Context context, int resource, List<Instructor> instructors) {
        super(context, resource, instructors);
        mInstructors = instructors;
    }

    @Override
    public int getCount() {
        return mInstructors.size();
    }

    @Override
    public Instructor getItem(int position) {
        return mInstructors.get(position);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.instructor_item, parent, false);
        }
        Instructor instructor = getItem(position);
        TextView instructorName = convertView.findViewById(R.id.instructor_name);
        instructorName.setText(instructor.getFirstName() + " " + instructor.getLastName());

        TextView instructorQualification = convertView.findViewById(R.id.instructor_qualification);
        instructorQualification.setText(instructor.getQualification().toString());
        return convertView;
    }
}