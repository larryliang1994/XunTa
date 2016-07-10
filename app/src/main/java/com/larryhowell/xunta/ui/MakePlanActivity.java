package com.larryhowell.xunta.ui;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.larryhowell.xunta.R;
import com.larryhowell.xunta.bean.LocationSuggestion;
import com.larryhowell.xunta.bean.MyDate;
import com.larryhowell.xunta.bean.MyTime;
import com.larryhowell.xunta.common.Constants;
import com.larryhowell.xunta.common.UtilBox;
import com.larryhowell.xunta.widget.DateDialog;
import com.larryhowell.xunta.widget.TimeDialog;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MakePlanActivity extends BaseActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.edt_desc)
    EditText mDescEditText;

    @Bind(R.id.seekBar)
    SeekBar mSeekBar;

    @Bind(R.id.tv_grade)
    TextView mGradeTextView;

    @Bind(R.id.edt_startTime)
    EditText mStartTimeEditText;

    @Bind(R.id.edt_arrival)
    EditText mArrivalEditText;

    @Bind(R.id.edt_departure)
    EditText mDepartureEditText;

    @Bind(R.id.edt_terminal)
    EditText mTerminalEditText;

    @Bind(R.id.tv_space)
    TextView mSpaceTextView;

    private int grade;
    private String date;
    private LocationSuggestion departureSuggestion, terminalSuggestion;
    private boolean isArrival = false, isStartTime = false, isDeparture = false, isTerminal = false;
    private int year_arrival = 0, month_arrival = 0, day_arrival = 0,
            hour_arrival = 0, minute_arrival = 0,
            year_startTime = 0, month_startTime = 0, day_startTime = 0,
            hour_startTime = 0, minute_startTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_make_plan);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSpaceTextView.setOnTouchListener((v, event) -> {
            UtilBox.toggleSoftInput(mDescEditText, false);

            return false;
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress) {
                    case 0: mGradeTextView.setText("D");  grade = 5;  break;
                    case 1: mGradeTextView.setText("C");  grade = 4;  break;
                    case 2: mGradeTextView.setText("B");  grade = 3;  break;
                    case 3: mGradeTextView.setText("A");  grade = 2;  break;
                    case 4: mGradeTextView.setText("S");  grade = 1;  break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.make_plan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick({R.id.edt_startTime, R.id.edt_arrival,
            R.id.edt_departure, R.id.edt_terminal})
    public void onClick(View view) {
        MyDate myDate;
        DateDialog dateDialog;

        switch (view.getId()) {

            case R.id.edt_arrival:
                isArrival = true;

                myDate = getMyDate();
                dateDialog = new DateDialog(this, this,
                        myDate.getYear(), myDate.getMonth(), myDate.getDay());
                dateDialog.show();
                break;

            case R.id.edt_startTime:
                isStartTime = true;

                myDate = getMyDate();
                dateDialog = new DateDialog(this, this,
                        myDate.getYear(), myDate.getMonth(), myDate.getDay());
                dateDialog.show();
                break;

            case R.id.edt_departure:
                isDeparture = true;
                isTerminal = false;
                startActivityForResult(new Intent(this, ChooseLocationActivity.class),
                        Constants.CODE_CHOOSE_LOCATION,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;

            case R.id.edt_terminal:
                isTerminal = true;
                isDeparture = false;
                startActivityForResult(new Intent(this, ChooseLocationActivity.class),
                        Constants.CODE_CHOOSE_LOCATION,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
        }
    }

    /**
     * 获取日期
     *
     * @return 当前日期或已设定的日期
     */
    private MyDate getMyDate() {
        int year, month, day;

        if ((isArrival && year_arrival != 0)
                || (isStartTime && year_arrival != 0 && year_startTime == 0)) {
            year = year_arrival;
            month = month_arrival;
            day = day_arrival;
        } else if ((isArrival) || (isStartTime && year_startTime == 0)) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        } else {
            year = year_startTime;
            month = month_startTime;
            day = day_startTime;
        }

        return new MyDate(year, month, day);
    }

    /**
     * 获取时间
     *
     * @return 当前时间或已设定的时间
     */
    private MyTime getMyTime() {
        int hour, minute;

        if ((isArrival && hour_arrival != 0)
                || (isStartTime && hour_arrival != 0 && hour_startTime == 0)) {
            hour = hour_arrival;
            minute = minute_arrival;
        } else if ((isArrival) || (isStartTime && hour_startTime == 0)) {
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        } else {
            hour = hour_startTime;
            minute = minute_startTime;
        }

        return new MyTime(hour, minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

        if (isArrival) {
            year_arrival = year;
            month_arrival = monthOfYear;
            day_arrival = dayOfMonth;
        } else if (isStartTime) {
            year_startTime = year;
            month_startTime = monthOfYear;
            day_startTime = dayOfMonth;
        }

        MyTime myTime = getMyTime();
        TimeDialog timeDialog = new TimeDialog(this, this, myTime.getHour(), myTime.getMinute(), true);
        timeDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (minute < 10) {
            date += " " + hourOfDay + ":0" + minute;
        } else {
            date += " " + hourOfDay + ":" + minute;
        }

        if (isArrival) {
            hour_arrival = hourOfDay;
            minute_arrival = minute;

            isArrival = false;

            mArrivalEditText.setText(date);
        } else if (isStartTime) {
            hour_startTime = hourOfDay;
            minute_startTime = minute;

            isStartTime = false;

            mStartTimeEditText.setText(date);
        }

        date = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.CODE_CHOOSE_LOCATION:
                if(resultCode == RESULT_OK) {
                    LocationSuggestion suggestion = data.getParcelableExtra("location");

                    if(isDeparture) {
                        mDepartureEditText.setText(suggestion.getBody());
                        departureSuggestion = suggestion;
                    } else {
                        mTerminalEditText.setText(suggestion.getBody());
                        terminalSuggestion = suggestion;
                    }
                }
                break;
        }
    }
}
