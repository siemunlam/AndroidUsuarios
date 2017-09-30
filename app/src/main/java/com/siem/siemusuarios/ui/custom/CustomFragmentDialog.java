package com.siem.siemusuarios.ui.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.RadioGroup;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.interfaces.SexoSelectedListener;
import com.siem.siemusuarios.model.app.Sexo;
import com.siem.siemusuarios.utils.Constants;

import java.util.List;

public class CustomFragmentDialog extends Fragment {

    public Dialog getRadioButtonsEstadoDialog(final Activity activity,
                                              final String acceptText,
                                              final List<Sexo> listSexo,
                                              boolean cancelable,
                                              final SexoSelectedListener listener){
        Typeface mTypeface = Typeface.createFromAsset(activity.getAssets(), Constants.PRIMARY_FONT);
        View view = View.inflate(activity, R.layout.custom_dialog_radiobuttons, null);
        final RadioGroup mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        for (Sexo sexo : listSexo) {
            final AppCompatRadioButton radioButton = new AppCompatRadioButton(activity);
            radioButton.setText(sexo.getDescripcion());
            radioButton.setTypeface(mTypeface);
            radioButton.setId(sexo.getId());
            mRadioGroup.addView(radioButton);
        }

        DialogInterface.OnClickListener acceptListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (Sexo sexo : listSexo) {
                    if(sexo.getId() == mRadioGroup.getCheckedRadioButtonId()){
                        listener.sexoSelected(sexo);
                        break;
                    }
                }
            }
        };
        return new AlertDialog.Builder(activity)
                .setView(view)
                .setPositiveButton(acceptText, acceptListener)
                .setCancelable(cancelable)
                .create();
    }

}
