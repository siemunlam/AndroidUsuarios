package com.siem.siemusuarios.ui.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.utils.Constants;

/**
 * Created by Lucas on 2/10/17.
 */

public class CustomEdittextUbicacion extends RelativeLayout {

    private Typeface mTypeface;
    private AppCompatEditText mEdittext;
    private AppCompatImageView mIconUbicacion;
    private AppCompatImageView mIconClear;

    public CustomEdittextUbicacion(Context context) {
        super(context);
        initialice(context, null);
    }

    public CustomEdittextUbicacion(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialice(context, attrs);
    }

    private void initialice(Context context, AttributeSet attrs){
        inflate(context, R.layout.custom_edittext_ubicacion, this);
        mTypeface = Typeface.createFromAsset(context.getAssets(), Constants.PRIMARY_FONT);
        mEdittext = (AppCompatEditText)findViewById(R.id.edittext);
        mIconUbicacion = (AppCompatImageView)findViewById(R.id.iconUbicacion);
        mIconClear = (AppCompatImageView)findViewById(R.id.iconClear);
        mEdittext.setTypeface(mTypeface);
    }
}
