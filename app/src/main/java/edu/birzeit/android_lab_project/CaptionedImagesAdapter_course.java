package edu.birzeit.android_lab_project;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
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

        TextView mytext = (TextView)myCardView.findViewById(R.id.coursetxtName);
        mytext.setText(item.getCOURSE_TITLE());
        myCardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Create a dialog and find views inside the dialog layout
                Dialog myDialog = new Dialog(context);
                myDialog.setContentView(R.layout.course_dialog_card);
                TextView textTitle = myDialog.findViewById(R.id.textTitle);
                TextView textTopic = myDialog.findViewById(R.id.textTopic);
                TextView textPrerequisites = myDialog.findViewById(R.id.textPrerequisites);
                Button editButton = myDialog.findViewById(R.id.editButton);

                textTitle.setText(item.getCOURSE_TITLE());
                textTopic.setText(item.getMAIN_TOPICS());
                textPrerequisites.setText(item.getPREREQUISITES());
                editButton.setOnClickListener( new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                    }
                });
                myDialog.show();
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
}

