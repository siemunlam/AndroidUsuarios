package com.siem.siemusuarios.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.utils.Constants;

/**
 * Created by lucas on 10/19/17.
 */

public class CustomEditableEditText extends ConstraintLayout {

    private Context mContext;
    private Typeface mTypeface;
    private AppCompatEditText mEditText;
    private AppCompatImageView mImage;
    private TextInputLayout mTextInputLayout;

    public CustomEditableEditText(Context context) {
        super(context);
        initialice(context, null);
    }

    public CustomEditableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialice(context, attrs);
    }

    private void initialice(Context context, AttributeSet attrs){
        inflate(context, R.layout.custom_editable_edittext, this);

        mContext = context;
        mTypeface = Typeface.createFromAsset(context.getAssets(), Constants.PRIMARY_FONT);
        mEditText = (AppCompatEditText) findViewById(R.id.edittext);
        mEditText.setTypeface(mTypeface);
        mImage = (AppCompatImageView)findViewById(R.id.image);
        mTextInputLayout = (TextInputLayout)findViewById(R.id.textInputLayout);
        if(attrs != null){
            TypedArray typed = mContext.obtainStyledAttributes(attrs, R.styleable.customComponents);

            //Hint
            mTextInputLayout.setHint(typed.getString(R.styleable.customComponents_android_hint));

            //srcCompat
            if(typed.hasValue(R.styleable.customComponents_srcCompat)){
                mImage.setImageResource(typed.getResourceId(R.styleable.customComponents_srcCompat, 0));
                mImage.setVisibility(VISIBLE);
            }else
                mImage.setVisibility(GONE);

            typed.recycle();
        }
    }

    public String getText(){
        return mEditText.getText().toString();
    }

    public View getImage(){
        return mImage;
    }

    public void setDrawable(int resId) {
        mImage.setImageResource(resId);
        mImage.setVisibility(VISIBLE);
    }

    public void setOnImageClickListener(OnClickListener listener){
        mImage.setOnClickListener(listener);
    }

    public void setEnabled(boolean enabled){
        mEditText.setEnabled(enabled);
    }
}
