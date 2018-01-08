package abdulrahmanjavanrd.com.mynewsapp.fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextWatcher;
import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import abdulrahmanjavanrd.com.mynewsapp.R;

/**
 * Created by nfs05 on 08/01/2018.
 */

public class DateFragment extends Fragment {


    // declare context ..
    Context  context ;
    // Views Elements
    Button  btnFromDate , btnToDate ;
    TextView txvFromDate , txvToDate ;
    // DatePickerDialog
    DatePickerDialog.OnDateSetListener mDatePicker ;

    SharedPreferences mShared ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.date_fragment_layout,container,false);
        context = getActivity() ;
        mShared = PreferenceManager.getDefaultSharedPreferences(context);
        btnFromDate = v.findViewById(R.id.btnFromDate);
        btnToDate = v.findViewById(R.id.btnToDate);
        // TODO: set TextView empty ..
        txvFromDate = v.findViewById(R.id.txvFromDate);
        txvToDate  = v.findViewById(R.id.txvToDate);
        setFromDate();
        updateTextFromDate();
        return v;
    }

    /**
     * This method for Create Calender,And Show DialogCalender
     * then take what user choice date and save it in SharedPreference.
     */
    private void setFromDate(){
        btnFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////                Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show();
//                //TODO: search for get all current years .
//                Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog pickerDialog = new DatePickerDialog(context,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDatePicker,year,month,dayOfMonth);
//                pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                pickerDialog.show();
                createDateDialog();
            }
        });
        mDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               // add 1 to month because it's started of zero .
                month = month+1 ;
                String str = year+"-"+month+"-"+dayOfMonth;
                saveFromDateInShared(str);
                updateTextFromDate();
            }
        };
    }

    private void saveFromDateInShared(String strFromDate){
        SharedPreferences mShared = PreferenceManager.getDefaultSharedPreferences(context);
         SharedPreferences.Editor editor = mShared.edit() ;
          editor.putString(getString(R.string.news_use_from_date_key),strFromDate);
          editor.apply();
    }


    private void updateTextFromDate(){
        String str = mShared.getString(getString(R.string.news_use_from_date_key),"1/1/2017");
        txvFromDate.setText(str);
    }

    private void createDateDialog(){
       Calendar calendar = Calendar.getInstance();
      int year = calendar.get(Calendar.YEAR);
       int month = calendar.get(Calendar.MONTH);
       int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog = new DatePickerDialog(context
                ,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDatePicker
                ,year,month,dayOfMonth);
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }

}
