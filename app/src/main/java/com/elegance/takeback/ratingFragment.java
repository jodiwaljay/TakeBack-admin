package com.elegance.takeback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.StringTokenizer;

/**
 * Created by root on 2/4/16.
 */
public class ratingFragment extends Fragment {
    String answer;
    Boolean flag = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.rating_layout, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String strtext = getArguments().getString("Question");
        Log.d("dtat", strtext);

        if(strtext!=null || getView()!=null) {

            StringTokenizer stringTokenizer = new StringTokenizer(strtext);
            stringTokenizer.nextToken(";");

            final TextView textView = (TextView) getView().findViewById(R.id.test);
            textView.setText(stringTokenizer.nextToken());

            final RatingBar rate = (RatingBar) getView().findViewById(R.id.ratingBar);
            rate.setOnRatingBarChangeListener (new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    ratingBar.setRating(rating);
                    if(flag){
                        Form_Creation.Questions_Loaded.remove(answer);
                    }
                    answer = textView.getText().toString() + ";" + rating;
                    Form_Creation.Questions_Loaded.add(answer);
                    flag = true;
                }
            });
        }
    }
}