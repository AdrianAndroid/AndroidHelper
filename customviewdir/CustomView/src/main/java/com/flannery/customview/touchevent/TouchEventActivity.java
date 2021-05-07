package com.flannery.customview.touchevent;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.flannery.customview.R;

public class TouchEventActivity extends AppCompatActivity {

    private View1 v1;
    private ViewGroup1 vg1;
    private ViewGroup2 vg2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);


        v1 = findViewById(R.id.v1);
        vg1 = findViewById(R.id.vg1);
        vg2 = findViewById(R.id.vg2);

        setOnCheckedListener(
                R.id.customV1,
                R.id.customVG1,
                R.id.customVG2,

                R.id.onInterceptTouchEventV1,
                R.id.onInterceptTouchEventVG1,
                R.id.onInterceptTouchEventVG2,

                R.id.dispatchTouchEventV1,
                R.id.dispatchTouchEventVG1,
                R.id.dispatchTouchEventVG2,

                R.id.setOnTouchEventV1,
                R.id.setOnTouchEventVG1,
                R.id.setOnTouchEventVG2,

                R.id.onTouchEventV1,
                R.id.onTouchEventVG1,
                R.id.onTouchEventVG2

        );
    }

    private void setOnCheckedListener(@IdRes int... idRes) {
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {

            private boolean isThisId(View view, int id) {
                return view.getId() == id;
            }

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(TouchEventActivity.this, "isChecked=" + isChecked, Toast.LENGTH_SHORT).show();
                if (isThisId(buttonView, R.id.customV1)) {
                    v1.custom = isChecked;
                } else if (isThisId(buttonView, R.id.dispatchTouchEventV1)) {
                    v1.dispatchTouchEvent = isChecked;
                } else if (isThisId(buttonView, R.id.onInterceptTouchEventV1)) {
                    //v1.onIn = isChecked;
                } else if (isThisId(buttonView, R.id.onTouchEventV1)) {
                    v1.onTouchEvent = isChecked;
                } else if (isThisId(buttonView, R.id.setOnTouchEventV1)) {
                    v1.setOnTouchEvent = isChecked;

                } else if (isThisId(buttonView, R.id.customVG1)) {
                    vg1.custom = isChecked;
                } else if (isThisId(buttonView, R.id.dispatchTouchEventVG1)) {
                    vg1.dispatchTouchEvent = isChecked;
                } else if (isThisId(buttonView, R.id.onInterceptTouchEventVG1)) {
                    vg1.onInterceptTouchEvent = isChecked;
                } else if (isThisId(buttonView, R.id.onTouchEventVG1)) {
                    vg1.onTouchEvent = isChecked;
                } else if (isThisId(buttonView, R.id.setOnTouchEventVG1)) {
                    vg1.setOnTouchEvent = isChecked;

                } else if (isThisId(buttonView, R.id.customVG2)) {
                    vg2.custom = isChecked;
                } else if (isThisId(buttonView, R.id.dispatchTouchEventVG2)) {
                    vg2.dispatchTouchEvent = isChecked;
                } else if (isThisId(buttonView, R.id.onInterceptTouchEventVG2)) {
                    vg2.onInterceptTouchEvent = isChecked;
                } else if (isThisId(buttonView, R.id.onTouchEventVG2)) {
                    vg2.onTouchEvent = isChecked;
                } else if (isThisId(buttonView, R.id.setOnTouchEventVG2)) {
                    vg2.setOnTouchEvent = isChecked;

                }
            }
        };
        for (int idRe : idRes) {
            View viewById = findViewById(idRe);
            if (viewById instanceof CheckBox) {
                ((CheckBox) viewById).setOnCheckedChangeListener(listener);
            }
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        L.m(getClass(), "TouchTest");
        boolean b = super.dispatchTouchEvent(ev);
        L.m(getClass(), "TouchTest", b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.m(getClass(), "TouchTest");
        boolean b = super.onTouchEvent(event);
        L.m(getClass(), "TouchTest", b);
        return b;
    }
}