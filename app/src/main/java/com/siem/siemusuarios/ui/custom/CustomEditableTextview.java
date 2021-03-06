package com.siem.siemusuarios.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.View;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.utils.Constants;

/**
 * Created by lucas on 10/19/17.
 */

public class CustomEditableTextview extends ConstraintLayout {

    private Context mContext;
    private Typeface mTypeface;
    private AppCompatTextView mText;
    private AppCompatImageView mImage;

    public CustomEditableTextview(Context context) {
        super(context);
        initialice(context, null);
    }

    public CustomEditableTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialice(context, attrs);
    }

    private void initialice(Context context, AttributeSet attrs){
        inflate(context, R.layout.custom_editable_textview, this);

        mContext = context;
        mTypeface = Typeface.createFromAsset(context.getAssets(), Constants.PRIMARY_FONT);
        mText = (AppCompatTextView)findViewById(R.id.text);
        mText.setTypeface(mTypeface);
        mImage = (AppCompatImageView)findViewById(R.id.image);
        if(attrs != null){
            TypedArray typed = mContext.obtainStyledAttributes(attrs, R.styleable.customComponents);

            //Text
            mText.setText(typed.getString(R.styleable.customComponents_android_text));

            //srcCompat
            if(typed.hasValue(R.styleable.customComponents_srcCompat)){
                mImage.setImageResource(typed.getResourceId(R.styleable.customComponents_srcCompat, 0));
                mImage.setVisibility(VISIBLE);
            }else
                mImage.setVisibility(GONE);

            typed.recycle();
        }
    }

    public View getImage(){
        return mImage;
    }

    public void setText(String text){
        mText.setText(text);
    }

    public void setTextSpanned(String text){
        Spanned spanned;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned = Html.fromHtml(text);
        }
        mText.setText(spanned);
    }

    public void setDrawable(int resId) {
        mImage.setImageResource(resId);
        mImage.setVisibility(VISIBLE);
    }

    public void setOnImageClickListener(OnClickListener listener){
        mImage.setOnClickListener(listener);
    }
}
