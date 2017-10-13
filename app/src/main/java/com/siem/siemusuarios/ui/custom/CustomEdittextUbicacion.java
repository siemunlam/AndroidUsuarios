package com.siem.siemusuarios.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.Utils;

import java.io.IOException;

/**
 * Created by Lucas on 2/10/17.
 */

public class CustomEdittextUbicacion extends RelativeLayout {

    private Context mContext;
    private Typeface mTypeface;
    private AppCompatEditText mEdittext;
    private AppCompatImageView mIconUbicacion;
    private AppCompatImageView mIconClear;

    private boolean mIconUbicacionEnable = true;
    private Double mLatitude;
    private Double mLongitude;

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

            mIconUbicacionEnable = typed.getBoolean(R.styleable.customComponents_locationEnable, true);
            mIconUbicacion.setVisibility(mIconUbicacionEnable ? VISIBLE : GONE);
            typed.recycle();
        }

        mEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(mEdittext.getText().toString().isEmpty()){
                    mIconClear.setVisibility(GONE);
                    if(mIconUbicacionEnable)
                        mIconUbicacion.setVisibility(VISIBLE);
                }else{
                    mIconClear.setVisibility(VISIBLE);
                    mIconUbicacion.setVisibility(GONE);
                }
            }
        });

        mIconClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("", null, null);
            }
        });
    }

    public void setText(String text, Double lat, Double lng){
        mEdittext.setText(text);
        mLatitude = lat;
        mLongitude = lng;
    }

    public void setEdittextOnClickListener(OnClickListener listener){
        mEdittext.setOnClickListener(listener);
    }

    public void setUbicacionOnClickListener(OnClickListener listener){
        mIconUbicacion.setOnClickListener(listener);
    }

    public void newLocation(Location location) {
        new NewLocationTask().execute(location);
    }

    private class NewLocationTask extends AsyncTask<Location, Constants.ReturnAsincTask, Void> {

        private Location mLocation;
        private String mDireccion;

        @Override
        protected Void doInBackground(Location... params) {
            mLocation = params[0];
            try{
                mDireccion = Utils.getStringAddress(mContext, mLocation.getLatitude(), mLocation.getLongitude());
                publishProgress(Constants.ReturnAsincTask.OK_ADDRESS);
            }catch(IOException e1){
                publishProgress(Constants.ReturnAsincTask.IOEXCEPTION);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Constants.ReturnAsincTask... values) {
            Constants.ReturnAsincTask returnAsincTask = values[0];
            switch(returnAsincTask){
                case OK_ADDRESS:
                    setText(mDireccion, mLocation.getLatitude(), mLocation.getLongitude());
                    break;

                case IOEXCEPTION:
                    Toast.makeText(mContext, mContext.getString(R.string.error), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
