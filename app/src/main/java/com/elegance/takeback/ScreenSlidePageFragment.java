package com.elegance.takeback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.StringTokenizer;

public class ScreenSlidePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String strtext = getArguments().getString("Question");
        Log.d("dtat", strtext);

        StringTokenizer stringTokenizer = new StringTokenizer(strtext);
        stringTokenizer.nextToken(";");

        TextView textView = (TextView) getView().findViewById(R.id.test);
        textView.setText(stringTokenizer.nextToken());
    }
}
