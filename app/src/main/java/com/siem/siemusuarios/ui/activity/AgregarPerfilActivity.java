package com.siem.siemusuarios.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.ActivityAgregarPerfilBinding;
import com.siem.siemusuarios.interfaces.RadioButtonDialogListener;
import com.siem.siemusuarios.model.app.Perfil;
import com.siem.siemusuarios.ui.custom.CustomFragmentDialog;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.SexoLoader;
import com.siem.siemusuarios.utils.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lucas on 29/9/17.
 */

public class AgregarPerfilActivity extends ToolbarActivity implements
        DatePickerDialog.OnDateSetListener,
        RadioButtonDialogListener {

    private static final int REQUEST_CHOOSE_CONTACT = 50;
    private static final int CONTACT_PERMISSIONS_REQUEST = 1000;
    private static final String TEXT_EMPTY = "";

    private Intent mSeleccionarContactIntent;
    private ActivityAgregarPerfilBinding mBinding;
    private Typeface mTypeface;
    private Date mFechaNacimiento;
    private Perfil mPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_agregar_perfil);
        setToolbar(true);

        mFechaNacimiento = new Date();
        mSeleccionarContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mTypeface = Typeface.createFromAsset(getAssets(), Constants.PRIMARY_FONT);
        setTypeface();

        if(getIntent().getSerializableExtra(Constants.KEY_PERFIL) != null){
            mPerfil = (Perfil)getIntent().getSerializableExtra(Constants.KEY_PERFIL);
            bindData(mPerfil);
        }else{
            mPerfil = new Perfil();
        }
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

        mBinding.edittextNroContacto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                controlateAddButton();
            }
        });

        mBinding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPerfil.setNombre(mBinding.edittextNombre.getText().toString());
                mPerfil.setApellido(mBinding.edittextApellido.getText().toString());
                mPerfil.setContacto(mBinding.edittextNroContacto.getText().toString());
                mPerfil.setSexo(mBinding.edittextSexo.getText().toString());
                mPerfil.setFechaNacimiento(String.valueOf(mFechaNacimiento.getTime()));
                mPerfil.save(AgregarPerfilActivity.this);
                Utils.addFinishTransitionAnimation(AgregarPerfilActivity.this);
                finish();
            }
        });

        /**
         * Evitar que la app se cuelgue si no hay Activity que entienda el Intent de los contactos
         */
        if(!Utils.understandIntent(this, mSeleccionarContactIntent))
            mBinding.buttonSeleccionarContacto.setEnabled(false);

        mBinding.buttonSeleccionarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(AgregarPerfilActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
                    startActivityForResult(mSeleccionarContactIntent, REQUEST_CHOOSE_CONTACT);
                }else{
                    ActivityCompat.requestPermissions(AgregarPerfilActivity.this, Constants.contacts_permissions, CONTACT_PERMISSIONS_REQUEST);
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        controlateAddButton();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case REQUEST_CHOOSE_CONTACT:
                    if(data != null){
                        Uri contactUri = data.getData();
                        Cursor cursor = getContentResolver().query(
                                contactUri,
                                null,
                                null,
                                null,
                                null
                        );

                        if(cursor != null){
                            if(cursor.moveToFirst()){
                                String nombreCompleto = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                                String numero = getPhone(cursor);
                                bindDataContact(nombreCompleto, numero);
                            }
                            cursor.close();
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CONTACT_PERMISSIONS_REQUEST:
                if(ActivityCompat.checkSelfPermission(AgregarPerfilActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
                    startActivityForResult(mSeleccionarContactIntent, REQUEST_CHOOSE_CONTACT);
                }else{
                    Toast.makeText(AgregarPerfilActivity.this, getString(R.string.noPermissionContactGranted), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void setTypeface() {
        mBinding.buttonSeleccionarContacto.setTypeface(mTypeface);
        mBinding.edittextNombre.setTypeface(mTypeface);
        mBinding.edittextApellido.setTypeface(mTypeface);
        mBinding.edittextNroContacto.setTypeface(mTypeface);
        mBinding.edittextFechaNacimiento.setTypeface(mTypeface);
        mBinding.edittextSexo.setTypeface(mTypeface);
    }

    private String getPhone(Cursor cursor) {
        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        Cursor phones = getContentResolver().query(
                Phone.CONTENT_URI,
                null,
                Phone.CONTACT_ID + " = " + contactId,
                null,
                null);

        String number = "";
        if(phones != null){
            if (phones.moveToNext()) {
                number = phones.getString(phones.getColumnIndex(Phone.NUMBER));
            }
            phones.close();
        }

        return number;
    }

    private void bindDataContact(String nombreCompleto, String numero) {
        String[] name = nombreCompleto.split(" ");

        mBinding.edittextNombre.setText(name[0]);
        mBinding.edittextApellido.setText(nombreCompleto.substring(name[0].length()).trim());
        mBinding.edittextNroContacto.setText(numero);
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
     * RadioButtonDialogListener
     */
    @Override
    public void radioButtonSelected(int positionItem) {
        mBinding.edittextSexo.setText(SexoLoader.getListSexo(AgregarPerfilActivity.this).get(positionItem));
        controlateAddButton();
    }

    private void bindData(Perfil perfil) {
        mBinding.edittextNombre.setText(perfil.getNombre());
        mBinding.edittextApellido.setText(perfil.getApellido());
        mBinding.edittextSexo.setText(perfil.getSexo());
        mFechaNacimiento = new Date(Long.parseLong(perfil.getFechaNacimiento()));
        setDateText();
        mBinding.edittextNroContacto.setText(perfil.getContacto());
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
