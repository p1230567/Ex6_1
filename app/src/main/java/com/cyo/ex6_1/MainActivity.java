package com.cyo.ex6_1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class MainActivity extends Activity {
    private static TextView tv_time;
    private Spinner spn_time;
    private static int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
//        tv_time顯示當前時間
        showRightNow();
    }

    private void findViews() {
        tv_time = (TextView) findViewById(R.id.tv_time);
        spn_time = (Spinner) findViewById(R.id.spn_time);
//        手動建立spinner
//        裡面選項有三個，使用String[]存放
        String[] item = {"NONE", "DatePicker", "TimePicker"};
//        建立一個Adapter使用在MainActivity
//        將item利用android.R.layout.simple_spinner_item格式
//        把item放進Adapter
        ArrayAdapter<String> item_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, item);
//        下拉式選單的動畫樣式
        item_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//      用spinner的方式把Adapter呈現
        spn_time.setAdapter(item_Adapter);
//        設定spinner的按鍵監聽器為listener
        spn_time.setOnItemSelectedListener(listener);
    }

//          建立Spinner.OnItemSelectedListener，在裡面匿名內部類別的@Override onItemSelected
//          分辨選的選項並跳出日期或時間的選擇器
    private Spinner.OnItemSelectedListener listener = new Spinner.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String Choice = parent.getItemAtPosition(position).toString();
            if (Choice.equals("DatePicker")) {
//                跳出日期選擇器
//                建立DatePicker的fragment
                DatePickerFragment datePickerFragment = new DatePickerFragment();
//                取得FragmentManager fm
                FragmentManager fm = getFragmentManager();
//                將Fragment 顯示
                datePickerFragment.show(fm, "datePicker");
            } else if (Choice.equals("TimePicker")) {
//                時間選擇器
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                FragmentManager fm = getFragmentManager();
                timePickerFragment.show(fm, "timePicker");
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }


    };

    private static void showRightNow() {
//        取得日曆的物件
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        updateInfo();
    }

    //      顯示時間在TEXTVIEW
    private static void updateInfo() {

        tv_time.setText(new StringBuilder().append(year).append("-")
//                要顯示的月份+1為正確日期
                .append(parseNum(month + 1)).append("-").append(parseNum(day)).append(" ")
                .append(hour).append(":").append(parseNum(minute)));
    }

    // 若數字有十位數，直接顯示；若只有個位數則補0後再顯示。例如7會改成07後再顯示
    private static String parseNum(int day) {
        if (day >= 10)
            return String.valueOf(day);
        else
            return "0" + String.valueOf(day);
    }


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        // 改寫此方法以提供Dialog內容
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // 建立DatePickerDialog物件
            // this為OnDateSetListener物件
            // year、month、day會成為日期挑選器預選的年月日
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(), this, year, month, day);
            return datePickerDialog;
        }

        @Override
        // 日期挑選完成會呼叫此方法，並傳入選取的年月日
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            year = y;
            month = m;
            day = d;
            updateInfo();
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        // 改寫此方法以提供Dialog內容
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // 建立TimePickerDialog物件
            // this為OnTimeSetListener物件
            // hour、minute會成為時間挑選器預選的時與分
            // false 設定是否為24小時制顯示
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getActivity(), this, hour, minute, false);
            return timePickerDialog;
        }

        @Override
        // 時間挑選完成會呼叫此方法，並傳入選取的時與分
        public void onTimeSet(TimePicker timePicker, int h, int m) {
            hour = h;
            minute = m;
            updateInfo();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
