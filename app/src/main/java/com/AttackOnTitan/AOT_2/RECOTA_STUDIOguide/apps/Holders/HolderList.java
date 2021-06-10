package com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Holders;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.R;

/**
 * This project create by SAID MOTYA on 06/17/2020.
 * contact on Facebook : https://web.facebook.com/motya.said
 * contact on Email : zonek.app@hotmail.com or zonek.app@gmail.com
 * it a free code source for member secret gfx
 */

public class HolderList extends RecyclerView.ViewHolder {

    public ImageView preview;
    public ImageView favourite;
    public TextView title ;
    public TextView counter ;
    public CardView relativeList;

    public HolderList(LayoutInflater layoutInflater , ViewGroup viewGroup) {
        super(layoutInflater.inflate(R.layout.items_list, viewGroup,false));

        preview = itemView.findViewById(R.id.preview);
        favourite = itemView.findViewById(R.id.fav);
        title = itemView.findViewById(R.id.title);
        counter = itemView.findViewById(R.id.counter);
        relativeList = itemView.findViewById(R.id.relativeList);

    }
}
