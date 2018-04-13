package com.example.vietvan.storyapplication;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailStoryActivity extends AppCompatActivity {

    private static final String TAG = "1";
    TextView title, content ,author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        Story story = (Story) getIntent().getSerializableExtra("detail");

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        author = findViewById(R.id.author);
        Typeface typeface1
                = Typeface.createFromAsset(
                getAssets(), "SouthernAire_Personal_Use_Only.ttf");
        title.setTypeface(typeface1);
        Typeface typeface2
                = Typeface.createFromAsset(
                getAssets(), "UTM Essendine Caps.ttf");
        content.setTypeface(typeface2);

        title.setText(story.title);
        content.setText(story.content.replaceAll("\\\\n", "\n"));
        author.setText(story.author);

    }
}
