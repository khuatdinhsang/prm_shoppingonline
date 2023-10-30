package com.example.shopping_online_prm392.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.model.Slide;

import java.util.List;

public class SlideAdapter extends PagerAdapter {

    private List<Slide> listSlide;

    public SlideAdapter(List<Slide> listSlide) {
        this.listSlide = listSlide;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.item_slide,container,false);
        ImageView imgSlide= view.findViewById(R.id.img_slide);
        Slide slide= listSlide.get(position);
        imgSlide.setImageResource(slide.getSlideId());
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(listSlide!=null){
            return listSlide.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);
    }
}
