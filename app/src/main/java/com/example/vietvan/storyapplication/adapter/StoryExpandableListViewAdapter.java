package com.example.vietvan.storyapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vietvan.storyapplication.DatabaseManager;
import com.example.vietvan.storyapplication.R;
import com.example.vietvan.storyapplication.Story;
import com.example.vietvan.storyapplication.Story_type;

import java.util.HashMap;
import java.util.List;

/**
 * Created by VietVan on 23/03/2018.
 */

public class StoryExpandableListViewAdapter extends BaseExpandableListAdapter {

    List<Story_type> story_types;
    HashMap<String, List<Story>> hashMap;
    Context context;

    public StoryExpandableListViewAdapter(Context context, List<Story_type> story_types, HashMap<String, List<Story>> hashMap){
        this.context = context;
        this.story_types = story_types;
        this.hashMap = hashMap;
    }

    @Override
    public int getGroupCount() {
        return story_types.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return hashMap.get(story_types.get(i).name).size();
    }

    @Override
    public Object getGroup(int i) {
        return story_types.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return hashMap.get(story_types.get(i).name).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item_parent, viewGroup, false);

        Story_type storyType = (Story_type) getGroup(i);

        TextView name = view.findViewById(R.id.it_pr_name);
        TextView description = view.findViewById(R.id.it_pr_description);
        ImageView ivArray = view.findViewById(R.id.iv_arrow);
        CardView cardView = view.findViewById(R.id.it_pr_cardView);

        cardView.setCardBackgroundColor(Color.parseColor(storyType.color));

        name.setText(storyType.name);
        description.setText(storyType.description);

        if(!b)
            ivArray.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        else
            ivArray.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.item_child, viewGroup, false);

        Story story = (Story) getChild(i, i1);

        TextView title = view.findViewById(R.id.it_c_title);
        TextView description = view.findViewById(R.id.it_c_description);
        ImageView iv_mark = view.findViewById(R.id.iv_ismark);

        title.setText(story.title);
        description.setText(story.description);
        if(story.bookmark == 1)
            iv_mark.setBackgroundColor(Color.YELLOW);
        else
            iv_mark.setBackgroundColor(Color.parseColor("#60cc92"));

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public void refresh(Context context){
        hashMap.clear();
        DatabaseManager db = DatabaseManager.newInstance(context);
        hashMap.putAll(db.getHashMapStory(db.getListStoryType(), db.getListStory()));

        notifyDataSetChanged();
    }

}
