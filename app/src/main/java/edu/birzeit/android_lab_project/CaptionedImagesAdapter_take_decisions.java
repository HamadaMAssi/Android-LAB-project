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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CaptionedImagesAdapter_take_decisions extends RecyclerView.Adapter<CaptionedImagesAdapter_take_decisions.ViewHolder>{
    private Context context;
    private List<TraineeReg> items;

    public CaptionedImagesAdapter_take_decisions(Context context, List<TraineeReg> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_take_decisions,
                parent,
                false);

        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TraineeReg item = items.get(position);
        CardView myCardView = holder.cardView;

        TextView courseName = (TextView) myCardView.findViewById(R.id.coursetxtName);
        TextView traineeName = (TextView) myCardView.findViewById(R.id.traineetxtName);
        Button acceptButton = myCardView.findViewById(R.id.acceptButton);
        Button rejectButton = myCardView.findViewById(R.id.rejectButton);

        courseName.setText(item.getCOURSE_TITLE());
        traineeName.setText(item.getTrainee_Name());
        acceptButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DataBaseHelper databasehelper = new DataBaseHelper(v.getContext(), "train", null, 1);
                databasehelper.acceptTraineesRegState(item);
                Toast.makeText(context, "Accepted", Toast.LENGTH_SHORT).show();

                AppCompatActivity activity = (AppCompatActivity) context;
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new TakeDecisionsFragment())
                        .commit();
            }
        });
        rejectButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DataBaseHelper databasehelper = new DataBaseHelper(v.getContext(), "train", null, 1);
                databasehelper.rejectTraineesRegState(item);
                Toast.makeText(context, "Rejected", Toast.LENGTH_SHORT).show();

                AppCompatActivity activity = (AppCompatActivity) context;
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new TakeDecisionsFragment())
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


}

