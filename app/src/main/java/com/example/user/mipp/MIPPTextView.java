package com.example.user.mipp;

import android.content.Context;
import android.graphics.Typeface;
import android.provider.SyncStateContract;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by user on 13/09/17.
 */

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

