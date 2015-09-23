package com.example.devon.scrollingexperiment;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.TextView;

import java.util.jar.Attributes;

/**
 * Created by Devon on 21/09/2015.
 */
public class DialScrollView extends DialView {

    private ListView listView = null;
    private TextView textView = null;
    private int listViewId = -1;
    private int textViewId = -1;
    protected double index;
    private int maxIndex;
    private int previousOffset;

    public DialScrollView(Context context) {
        this(context, null);
    }

    public DialScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DialScrollView,
                0, 0);

        try {
            listViewId = a.getResourceId(R.styleable.DialScrollView_listView, -1);
            textViewId = a.getResourceId(R.styleable.DialScrollView_textView, -1);
        } finally {
            a.recycle();
        }

        // a step every 20°
        setStepAngle(20f);
        // area from 30% to 90%
        setDiscArea(.3f, 1.0f);
        previousOffset = 0;
    }

    public void setListView(Context context) {
        Activity activity = (Activity) context;
        if (listViewId != -1) {
            listView = (ListView) activity.findViewById(listViewId);
        }
    }

    public void setTextView(Context context) {
        Activity activity = (Activity) context;
        if (textViewId != -1) {
            textView = (TextView) activity.findViewById(textViewId);
        }
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    @Override
    protected void onRotate(int offset) {
        if (offset > 0 && previousOffset < 0 || offset < 0 && previousOffset > 0) {
            scrollLen = 0;
        }
        previousOffset = offset;

        index += (offset + scrollLen/4);
        if (index > maxIndex) {
            index = maxIndex;
        }
        if (index < 0) {
            index = 0;
        }

        scrollLen += offset;
        if (listView != null) {
            listView.setSelection((int) index);
        }

        if (textView != null) {
            textView.setText(String.valueOf(index) + " | " + String.valueOf(scrollLen));
        }
    }
}
