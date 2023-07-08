package edu.birzeit.android_lab_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CaptionedImagesAdapter_course extends RecyclerView.Adapter<CaptionedImagesAdapter_course.ViewHolder>{
    private Context context;
    private List<Course> items;

    public CaptionedImagesAdapter_course(Context context, List<Course> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_edit_delete,
                parent,
                false);

        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Course item = items.get(position);
        CardView myCardView = holder.cardView;

        TextView txtName = (TextView) myCardView.findViewById(R.id.txtName);
        Button editButton = myCardView.findViewById(R.id.editButton);
        Button deleteButton = myCardView.findViewById(R.id.deleteButton);

        txtName.setText(item.getCOURSE_TITLE());
        myCardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateCourse(item);
            }
        });
        editButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateCourse(item);
            }
        });
        deleteButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DataBaseHelper databasehelper = new DataBaseHelper(v.getContext(), "train", null, 1);
                if(databasehelper.checkCourseExistsInRegistration(item.getCOURSE_TITLE())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Important")
                            .setMessage("This Course have sections, delete anyway?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    databasehelper.deleteCourse(item.getCOURSE_TITLE());
                                    Toast.makeText(context, "Course deleted", Toast.LENGTH_SHORT).show();
                                    // Reopen the EditDeleteCourseFragment
                                    AppCompatActivity activity = (AppCompatActivity) context;
                                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.fragment_container, new EditDeleteCourseFragment())
                                            .commit();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
                                    // Reopen the EditDeleteCourseFragment
                                    AppCompatActivity activity = (AppCompatActivity) context;
                                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.fragment_container, new EditDeleteCourseFragment())
                                            .commit();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    databasehelper.deleteCourse(item.getCOURSE_TITLE());
                }
                // Reopen the EditDeleteCourseFragment
                AppCompatActivity activity = (AppCompatActivity) context;
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new EditDeleteCourseFragment())
                        .commit();
            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }

    public void updateCourse(Course item){
        // Create a dialog and find views inside the dialog layout
        Dialog myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.course_dialog_card);
        EditText textTitle = myDialog.findViewById(R.id.textTitle);
        EditText textTopic = myDialog.findViewById(R.id.textTopic);
        EditText textPrerequisites = myDialog.findViewById(R.id.textPrerequisites);
        Button editButton = myDialog.findViewById(R.id.editButton);

        String oldTitle = item.getCOURSE_TITLE();
        textTitle.setText(oldTitle);
        textTopic.setText(item.getMAIN_TOPICS());
        textPrerequisites.setText(item.getPREREQUISITES());
        editButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String courseTitle = textTitle.getText().toString();
                if(courseTitle.isEmpty()){
                    Toast toast =Toast.makeText(context,
                            "please fill course title!",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    String courseMainTopic = textTopic.getText().toString();
                    if (courseMainTopic.isEmpty()){
                        Toast toast =Toast.makeText(context,
                                "please fill course main topic!",Toast.LENGTH_SHORT);
                        toast.show();
                    } else{
                        String prerequisites = textPrerequisites.getText().toString();
                        if (prerequisites.isEmpty()){
                            Toast toast =Toast.makeText(v.getContext(),
                                    "please fill course prerequisites!",Toast.LENGTH_SHORT);
                            toast.show();
                        } else{
                            DataBaseHelper databasehelper = new DataBaseHelper(v.getContext(), "train", null, 1);
                            Cursor Course_Data = databasehelper.getCourseInfo(courseTitle);
                            if(Course_Data.moveToNext() && !(oldTitle.equals(courseTitle))){
                                Toast toast =Toast.makeText(v.getContext(),
                                        "The course title already exists",Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                Course course = new Course(courseTitle,courseMainTopic,prerequisites,"no photo");
                                databasehelper.updateCourse(course, oldTitle);
                                Toast toast =Toast.makeText(v.getContext(),
                                        "Course updated successfully",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }
                }
            }
        });
        myDialog.show();
    }
}

