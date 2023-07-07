package edu.birzeit.android_lab_project;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CaptionedImagesAdapter_trainee extends RecyclerView.Adapter<CaptionedImagesAdapter_trainee.ViewHolder>{
    private Context context;
    private List<Trainee> items;

    public CaptionedImagesAdapter_trainee(Context context, List<Trainee> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image,
                parent,
                false);

        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Trainee item = items.get(position);
        CardView myCardView = holder.cardView;
        ImageView myimageView = (ImageView) myCardView.findViewById(R.id.image);
        Glide.with(context).load(item.getPersonal_Photo()).into(myimageView);

        TextView mytext = (TextView)myCardView.findViewById(R.id.txtName);
        mytext.setText(item.getFirst_Name()+" "+item.getLast_Name());
        myCardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Create a dialog and find views inside the dialog layout
                Dialog myDialog = new Dialog(context);
                myDialog.setContentView(R.layout.trainee_dialog_card);
                ImageView image = myDialog.findViewById(R.id.image);
                TextView name = myDialog.findViewById(R.id.txtName);
                TextView email = myDialog.findViewById(R.id.textEmail);
                TextView phone = myDialog.findViewById(R.id.textMobile);
                TextView textAddress = myDialog.findViewById(R.id.textAddress);

                // Set mydialog views data
                Glide.with(context).load(item.getPersonal_Photo()).into(image);
                name.setText(item.getFirst_Name()+" "+item.getLast_Name());
                name.setText(item.getEmail_Address());
                name.setText(item.getMobile_Number());
                name.setText(item.getAddress());
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

