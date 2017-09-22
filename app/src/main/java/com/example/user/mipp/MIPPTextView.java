package com.example.user.mipp;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class MIPPTextView extends android.support.v7.widget.AppCompatTextView {

    String fonte = "fonts/helvetica-bold.ttf";


    public MIPPTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), fonte);
        this.setTypeface(typeFace);
    }

    public MIPPTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), fonte);
        this.setTypeface(typeFace);
    }

    public MIPPTextView(Context context) {
        super(context);
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), fonte);
        this.setTypeface(typeFace);
    }
}

