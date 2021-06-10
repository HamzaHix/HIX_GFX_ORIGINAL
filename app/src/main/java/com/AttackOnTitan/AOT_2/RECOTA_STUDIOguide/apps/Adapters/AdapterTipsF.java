package com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdsManager;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.classes.HIX_HOME;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Holders.AdHolder;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Holders.HolderList;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Models.ModelsTips;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.R;

import java.util.ArrayList;
import java.util.List;


/**
 * This project create by SAID MOTYA on 06/17/2020.
 * contact on Facebook : https://web.facebook.com/motya.said
 * contact on Email : zonek.app@hotmail.com or zonek.app@gmail.com
 * it a free code source for member secret gfx
 */

public class AdapterTipsF extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NativeAd> mAdItems;
    private NativeAdsManager mNativeAdsManager;
   // private static final int AD_DISPLAY_FREQUENCY = HIX_HOME.NumberOfNativeAdInList_Facebook;
    private static final int POST_TYPE = 0;
    private static final int AD_TYPE = 1;

    private Context context;
    private List<Object> objects;
    private ModelsTips modelsTips;
    private HolderList holderList ;
    private OnClickItems onClickItems ;
    private String paths ;

    public AdapterTipsF(Context context, List<Object> objects,NativeAdsManager nativeAdsManager) {
        this.context = context;
        this.objects = objects;
        mNativeAdsManager = nativeAdsManager;
        mAdItems = new ArrayList<>();

    }

    public void setOnClickItems(OnClickItems onClickItems) {
        this.onClickItems = onClickItems;
    }
    public interface OnClickItems{
        void onClick(View view, List<Object> objects, int position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == AD_TYPE) {
            NativeAdLayout inflatedView = (NativeAdLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.items_ad_native, parent, false);
            return new AdHolder(inflatedView);
        } else {
            return new HolderList(LayoutInflater.from(parent.getContext()), parent);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        setBindView(holder,position);
    }


    private void setBindView(@NonNull RecyclerView.ViewHolder holder, final int position){

        if (holder.getItemViewType() == AD_TYPE) {
            myAdsBin(holder,position);
        } else {
            onBindData(holder, position);
        }


    }

    private void onBindData(@NonNull RecyclerView.ViewHolder holder, final int position){

        holderList = (HolderList)holder;
        final int index = position - (position / AdFrequency()) - 1;
        modelsTips = (ModelsTips)objects.get(index);
        paths = HIX_HOME.assetsPath+ HIX_HOME.FOLDER_PREVIEW+"/"+modelsTips.getPreview() ;

        holderList.title.setText(""+modelsTips.getTitle());
        holderList.counter.setText(""+modelsTips.getPosition());
        holderList.relativeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItems !=null){
                    onClickItems.onClick(v,objects,index);
                }
            }
        });

        holderList.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (modelsTips.getPreview().startsWith(HIX_HOME.Tags.HTTP)){
            Glide.with(context)
                    .load(modelsTips.getPreview())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holderList.preview);
            return;
        }
        Glide.with(context)
                .load(paths)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holderList.preview);


    }

    private void myAdsBin(RecyclerView.ViewHolder holder, int position){
        NativeAd ad = null;
        AdHolder adHolder = (AdHolder) holder;
        if (mAdItems.size() > position / AdFrequency()) {
            ad = mAdItems.get(position / AdFrequency());
        }
        else {

            try {
                ad = mNativeAdsManager.nextNativeAd();
                if (!ad.isAdInvalidated()) {
                    mAdItems.add(ad);
                }

            }catch (Exception e){
                Log.d("motya", "Ad is invalidated! : " +e);
            }

        }

        adHolder.adChoicesContainer.removeAllViews();
        if (ad != null) {
            adHolder.tvAdTitle.setText(ad.getAdvertiserName());
            adHolder.tvAdBody.setText(ad.getAdBodyText());
            adHolder.tvAdSocialContext.setText(ad.getAdSocialContext());
            adHolder.tvAdSponsoredLabel.setText("ponsored");
            adHolder.btnAdCallToAction.setText(ad.getAdCallToAction());
            adHolder.btnAdCallToAction.setVisibility(ad.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);

            AdOptionsView adOptionsView = new AdOptionsView(context, ad, adHolder.nativeAdLayout);
            adHolder.adChoicesContainer.removeAllViews();
            adHolder.adChoicesContainer.addView(adOptionsView, 0);

            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(adHolder.ivAdIcon);
            clickableViews.add(adHolder.mvAdMedia);
            clickableViews.add(adHolder.btnAdCallToAction);
            ad.registerViewForInteraction(adHolder.nativeAdLayout,adHolder.mvAdMedia,adHolder.ivAdIcon,clickableViews);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position % AdFrequency() == 0 ? AD_TYPE : POST_TYPE;
    }

    @Override
    public int getItemCount() {
        return objects.size() + mAdItems.size();
    }


    private int AdFrequency(){
        int size = objects.size();
        try {
            if (size <= 3){
                return 1 ;
            }
            return size / 2;
        }catch (Exception e){
            return 0 ;
        }
    }


}
