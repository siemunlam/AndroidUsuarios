package com.siem.siemusuarios.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
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

    private Context mContext;
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
        mContext = context;
        mTypeface = Typeface.createFromAsset(context.getAssets(), Constants.PRIMARY_FONT);
        mEdittext = (AppCompatEditText)findViewById(R.id.edittext);
        mIconUbicacion = (AppCompatImageView)findViewById(R.id.iconUbicacion);
        mIconClear = (AppCompatImageView)findViewById(R.id.iconClear);
        mEdittext.setTypeface(mTypeface);

        if(attrs != null){
            TypedArray typed = mContext.obtainStyledAttributes(attrs, R.styleable.customComponents);
            mEdittext.setHint(typed.getString(R.styleable.customComponents_android_hint));
            mEdittext.setFocusable(typed.getBoolean(R.styleable.customComponents_android_focusable, true));

            boolean iconUbicacionEnable = typed.getBoolean(R.styleable.customComponents_locationEnable, true);
            mIconUbicacion.setVisibility(iconUbicacionEnable ? VISIBLE : GONE);
            typed.recycle();
        }
    }

    public void setEdittextOnClickListener(OnClickListener listener){
        mEdittext.setOnClickListener(listener);
    }

    public void setUbicacionOnClickListener(OnClickListener listener){
        mIconUbicacion.setOnClickListener(listener);
    }

}
