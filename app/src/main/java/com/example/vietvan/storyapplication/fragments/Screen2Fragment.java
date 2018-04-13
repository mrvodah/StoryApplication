package com.example.vietvan.storyapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.example.vietvan.storyapplication.adapter.StoryExpandableListViewAdapter;

import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Screen2Fragment extends Fragment {


    private static final String ARG_POSITION = "POS";

    public Screen2Fragment() {
    }

    public static Screen2Fragment newInstance(int position){
        Screen2Fragment screen2Fragment = new Screen2Fragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        screen2Fragment.setArguments(b);

        return screen2Fragment;
    }

    public static com.example.vietvan.storyapplication.DatabaseManager db;
    public static ExpandableListView ex;
    public static StoryExpandableListViewAdapter story;
    public static HashMap<String, List<com.example.vietvan.storyapplication.Story>> hashMap;
    public static List<com.example.vietvan.storyapplication.Story_type> storyTypes;
    int lastExpandedPosition = -1;
    public static RelativeLayout bgSc2;
    public static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_screen2, container, false);

        context = getContext();
        bgSc2 = v.findViewById(R.id.bg_sc2);
        ex = v.findViewById(R.id.elv_story);
        db = new com.example.vietvan.storyapplication.DatabaseManager(getActivity());
        storyTypes = db.getListStoryType();
        hashMap = db.getHashMapStory(db.getListStoryType(), db.getListStory());
        story = new StoryExpandableListViewAdapter(getActivity(), storyTypes, hashMap);
        ex.setAdapter(story);

        ex.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Intent intent = new Intent(getActivity(), com.example.vietvan.storyapplication.IntroStoryActivity.class);
                intent.putExtra("story", hashMap.get(storyTypes.get(i).name).get(i1));
                startActivity(intent);
                return true;
            }
        });

        ex.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long l) {
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    parent.expandGroup(groupPosition);
                }

                parent.smoothScrollToPositionFromTop(groupPosition, 0);

                return true;
            }
        });

        ex.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1
                        && i != lastExpandedPosition) {
                    ex.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        story.refresh(getActivity());
    }

}
