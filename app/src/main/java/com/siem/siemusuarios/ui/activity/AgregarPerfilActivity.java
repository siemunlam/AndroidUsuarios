package com.siem.siemusuarios.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.ActivityAgregarPerfilBinding;
import com.siem.siemusuarios.interfaces.SexoSelectedListener;
import com.siem.siemusuarios.model.app.Sexo;
import com.siem.siemusuarios.ui.custom.CustomFragmentDialog;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.SexoLoader;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lucas on 29/9/17.
 */

public class AgregarPerfilActivity extends ToolbarActivity implements
        DatePickerDialog.OnDateSetListener,
        SexoSelectedListener{

    private static final String TEXT_EMPTY = "";

    private ActivityAgregarPerfilBinding mBinding;
    private Date mFechaNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_agregar_perfil);
        setToolbar(true);

        mFechaNacimiento = new Date();

        mBinding.edittextSexo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomFragmentDialog().getRadioButtonsEstadoDialog(
                        AgregarPerfilActivity.this,
                        getString(R.string.aceptar),
                        SexoLoader.getListSexo(AgregarPerfilActivity.this),
                        true,
                        AgregarPerfilActivity.this
                ).show();
            }
        });

        mBinding.edittextFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.setTime(mFechaNacimiento);
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AgregarPerfilActivity.this,
                        mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setVersion(DatePickerDialog.Version.VERSION_1);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        mBinding.edittextNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                controlateAddButton();
            }
        });

        mBinding.edittextApellido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                controlateAddButton();
            }
        });
    }

    /**
     * DatePickerDialog.OnDateSetListener
     */
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(year, monthOfYear, dayOfMonth);
        mFechaNacimiento = mCalendar.getTime();
        setDateText();
        controlateAddButton();
    }

    /**
     * SexoSelectedListener
     */
    @Override
    public void sexoSelected(Sexo sexo) {
        mBinding.edittextSexo.setText(sexo.getDescripcion());
        controlateAddButton();
    }

    private void setDateText() {
        mBinding.edittextFechaNacimiento.setText(Constants.DATE_FORMAT.format(mFechaNacimiento));
    }

    private void controlateAddButton() {
        if(!mBinding.edittextNombre.getText().toString().equals(TEXT_EMPTY) &&
                !mBinding.edittextApellido.getText().toString().equals(TEXT_EMPTY) &&
                !mBinding.edittextSexo.getText().toString().equals(TEXT_EMPTY) &&
                !mBinding.edittextFechaNacimiento.getText().toString().equals(TEXT_EMPTY)){
            showAddButton();
            return;
        }

        hideAddButton();
    }

    private void hideAddButton(){
        if(mBinding.addButton.getVisibility() != View.GONE){
            mBinding.addButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hide_center));
            mBinding.addButton.setVisibility(View.GONE);
        }
    }

    private void showAddButton(){
        if(mBinding.addButton.getVisibility() != View.VISIBLE){
            mBinding.addButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.show_center));
            mBinding.addButton.setVisibility(View.VISIBLE);
        }
    }
}
