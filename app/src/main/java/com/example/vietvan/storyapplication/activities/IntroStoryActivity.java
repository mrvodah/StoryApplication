package com.example.vietvan.storyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroStoryActivity extends AppCompatActivity {

    Story story;
    boolean isMark;

    @BindView(R.id.des_tv_title)
    TextView desTvTitle;
    @BindView(R.id.des_tv_author)
    TextView desTvAuthor;
    @BindView(R.id.des_tv_descrip)
    TextView desTvDescrip;
    @BindView(R.id.des_tv_start)
    TextView desTvStart;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_mark)
    ImageView ivMark;
    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        ButterKnife.bind(this);

        story = (Story) getIntent().getSerializableExtra("story");

        isMark = story.bookmark == 0;
        desTvTitle.setText(story.title);
        if (story.author != "")
            desTvAuthor.setText(story.author);
        else
            desTvAuthor.setText("Chưa rõ");
        desTvDescrip.setText(story.content);
        if (story.image != "")
            Picasso.with(this).load(story.image).into(iv);
        if (story.bookmark == 1)
            ivMark.setImageResource(R.drawable.ic_bookmark_black_24dp);
        else
            ivMark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);

    }

    @OnClick({R.id.iv_back, R.id.iv_mark, R.id.iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_mark:
                if (isMark) {
                    DatabaseManager.newInstance(IntroStoryActivity.this).updateMark(story, 1);
                    ivMark.setImageResource(R.drawable.ic_bookmark_black_24dp);
                } else {
                    DatabaseManager.newInstance(IntroStoryActivity.this).updateMark(story, 0);
                    ivMark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                }
                isMark = !isMark;
                break;
        }
    }

    @OnClick(R.id.des_tv_start)
    public void onViewClicked() {
        Intent intent = new Intent(this, DetailStoryActivity.class);
        intent.putExtra("detail", story);
        startActivity(intent);
    }
}
