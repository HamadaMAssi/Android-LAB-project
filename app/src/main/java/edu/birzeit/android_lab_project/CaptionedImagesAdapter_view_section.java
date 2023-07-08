package edu.birzeit.android_lab_project;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CaptionedImagesAdapter_view_section extends RecyclerView.Adapter<CaptionedImagesAdapter_view_section.ViewHolder>{
    private Context context;
    private List<Registration> items;

    public CaptionedImagesAdapter_view_section(Context context, List<Registration> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_section,
                parent,
                false);

        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Registration item = items.get(position);
        CardView myCardView = holder.cardView;

        TextView txtName = (TextView) myCardView.findViewById(R.id.txtName);
        Button registrationButton = myCardView.findViewById(R.id.registrationButton);

        txtName.setText(item.getCOURSE_TITLE());
        myCardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showSections(item);
            }
        });
        registrationButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){

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

    public void showSections(Registration item){
        // Create a dialog and find views inside the dialog layout
        Dialog myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.section_view_dialog_card);
        TextView textTitle = myDialog.findViewById(R.id.textTitle);
        TextView textInstructorEmail = myDialog.findViewById(R.id.textInstructorEmail);
        TextView textDeadline = myDialog.findViewById(R.id.textDeadline);
        TextView textStart_Date = myDialog.findViewById(R.id.textStart_Date);
        TextView textschedule = myDialog.findViewById(R.id.textschedule);
        TextView textvenue = myDialog.findViewById(R.id.textvenue);
        Button registrationButton = myDialog.findViewById(R.id.registrationButton);

        String oldTitle = item.getCOURSE_TITLE();
        textTitle.setText(oldTitle);
        textInstructorEmail.setText(item.getInstructorEmail());
        textDeadline.setText(item.getDeadline());
        textStart_Date.setText(item.getStart_Date());
        textschedule.setText(item.getSchedule());
        textvenue.setText(item.getVenue());

        registrationButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DataBaseHelper databasehelper = new DataBaseHelper(v.getContext(), "train", null, 1);
            }
        });
        myDialog.show();
    }

    public void userRegistration(Course course, Trainee trainee){

    }
}

