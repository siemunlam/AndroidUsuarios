package com.siem.siemusuarios.ui.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RadioGroup;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.interfaces.RadioButtonSelectedListener;
import com.siem.siemusuarios.utils.Constants;

import java.util.List;

public class CustomFragmentDialog extends Fragment {

    public Dialog getTextViewDialog(Activity activity,
                                    String textShow,
                                    String acceptText,
                                    DialogInterface.OnClickListener acceptListener,
                                    String cancelText,
                                    DialogInterface.OnClickListener cancelListener,
                                    boolean cancelable){
        View view = View.inflate(activity, R.layout.custom_dialog_text, null);
        Typeface mTypeface = Typeface.createFromAsset(activity.getAssets(), Constants.PRIMARY_FONT);
        final AppCompatTextView mTextView = (AppCompatTextView)view.findViewById(R.id.textview);
        mTextView.setTypeface(mTypeface);
        mTextView.setText(textShow);
        return new AlertDialog.Builder(activity)
                .setView(view)
                .setPositiveButton(acceptText, acceptListener)
                .setNegativeButton(cancelText, cancelListener)
                .setCancelable(cancelable)
                .create();
    }

    public Dialog getRadioButtonsEstadoDialog(final Context context,
                                              final String acceptText,
                                              final List<String> listItem,
                                              boolean cancelable,
                                              final RadioButtonSelectedListener listener){
        Typeface mTypeface = Typeface.createFromAsset(context.getAssets(), Constants.PRIMARY_FONT);
        View view = View.inflate(context, R.layout.custom_dialog_radiobuttons, null);
        final RadioGroup mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        for(int i = 0; i < listItem.size(); i++){
            final AppCompatRadioButton radioButton = new AppCompatRadioButton(context);
            radioButton.setText(listItem.get(i));
            radioButton.setTypeface(mTypeface);
            radioButton.setId(i);
            mRadioGroup.addView(radioButton);
        }

        DialogInterface.OnClickListener acceptListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mRadioGroup.getCheckedRadioButtonId() != -1)
                    listener.radioButtonSelected(mRadioGroup.getCheckedRadioButtonId());
            }
        };
        return new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton(acceptText, acceptListener)
                .setCancelable(cancelable)
                .create();
    }

}
