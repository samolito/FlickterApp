package com.ffl.flickster.Adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ffl.flickster.R;
import com.ffl.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by SAMY_PJ on 7/21/2017.
 */

public class
MovieArrayAdapter extends ArrayAdapter<Movie>{

  public  static class ViewHolder
    {
        TextView tvOriginalTitle;
        TextView tvOverview;
        ImageView ivImage;

    }
    public MovieArrayAdapter(Context context, List<Movie>movies)
    {
        super(context,android.R.layout.simple_list_item_1,movies);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Movie movie=getItem(position);
        final ViewHolder viewholder;

        //Check if existing
        if (convertView==null)
        {
            viewholder =new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.item_movie,parent,false);
            viewholder.ivImage=(ImageView)convertView.findViewById(R.id.ivMovieImage);
            viewholder.tvOriginalTitle=(TextView)convertView.findViewById(R.id.tvTitle);
            viewholder.tvOverview=(TextView)convertView.findViewById(R.id.tvOverview);
            convertView.setTag(viewholder);
        }
        else {
            viewholder=(ViewHolder)convertView.getTag();
        }
        viewholder.ivImage.setImageResource(0);
        //populate data
        viewholder.tvOriginalTitle.setText(movie.getOriginalTitle());
        viewholder.tvOverview.setText(movie.getOverview());

        int orientation=getContext().getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            Picasso.with(getContext()).load(movie.getPosterPath()).transform(new RoundedCornersTransformation(8, 8)).into(viewholder.ivImage);
        }else if(orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            Picasso.with(getContext()).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(8, 8)).into(viewholder.ivImage);
        }

            return convertView;
    }


}
