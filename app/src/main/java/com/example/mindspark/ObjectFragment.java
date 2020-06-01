package com.example.mindspark;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ObjectFragment extends Fragment {


    public static final String ARG_OBJECT = "object";
    public static final String headline = "headline";
    TextView textView;
    TextView articleHeader;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_object, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        IntroAdapter introAdapter = new IntroAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.smallViewPager);
        viewPager.setAdapter(introAdapter);
        textView = view.findViewById(R.id.text1);
        articleHeader = view.findViewById(R.id.articleHeader);
        articleHeader.setText(args.getString(headline));
        textView.setText(Integer.toString(args.getInt(ARG_OBJECT)));


    }


}
