package com.example.vietvan.storyapplication;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class Screen1Fragment extends Fragment {

    private static final String ARG_POSITION = "POS";
    public static ConstraintLayout bgSc1;
    Unbinder unbinder;
    public static ImageView ivSc1;
    public static int currentColor2 = Color.parseColor("#ffffff");

    public static Screen1Fragment newInstance(int position) {
        Screen1Fragment screen1Fragment = new Screen1Fragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        screen1Fragment.setArguments(b);

        return screen1Fragment;
    }

    public Screen1Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_screen1, container, false);
        unbinder = ButterKnife.bind(this, v);
        bgSc1 = v.findViewById(R.id.bg_sc1);
        ivSc1 = v.findViewById(R.id.iv_sc1);

        ivSc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickerDialogBuilder
                        .with(getContext())
                        .setTitle("Choose color")
                        .initialColor(currentColor2)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {

                            }
                        })
                        .setPositiveButton("Ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                currentColor2 = selectedColor;
                                ivSc1.setColorFilter(currentColor2);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static class BackgroundColorHolder {
        private int backgroundColor;

        public BackgroundColorHolder(Context context) {
    /*  Obtain the basic color from resources  */
            backgroundColor = context.getResources().getColor(R.color.background);
        }

        public int getCurrentBackgroundColor() {
            return backgroundColor;
        }

        public void changeBackgroundColor(int newColor) {
            backgroundColor = newColor;
    /*  Some kind of notification for all of the affected views  */
        }
    }

}
