package com.siem.siemusuarios.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.RadioGroup;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.interfaces.RadioButtonSelectedListener;
import com.siem.siemusuarios.utils.Constants;

import java.util.List;

public class CustomFragmentDialog extends Fragment {

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
