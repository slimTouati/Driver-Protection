package com.example.tanga.driverprotection;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFragment extends android.support.v4.app.DialogFragment {
List provinceList;

    public DialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_dialog, container, false);
       // MaterialEditText edt_register_model = (MaterialEditText)v.findViewById(R.id.edt_Model);
        MaterialEditText edt_register_color = (MaterialEditText)v.findViewById(R.id.edt_Color);
        SmartMaterialSpinner spProvince  = v.findViewById(R.id.regbrand);;
        provinceList = new ArrayList();
        provinceList.add("Renault");
        provinceList.add("Mercedes");
        provinceList.add("Audi");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), com.chivorn.smartmaterialspinner.R.layout.smart_material_spinner_hint_item_layout, provinceList);
        adapter.setDropDownViewResource(com.chivorn.smartmaterialspinner.R.layout.smart_material_spinner_dropdown_item);
        spProvince.setAdapter(adapter);
        //   spProvince.setItems(provinceList);
        Log.d("item menhom howa",spProvince.getItemAtPosition(1).toString());
       /* spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this, provinceList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/
        return v;
    }

}
