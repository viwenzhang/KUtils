package com.kiven.sample.imui;

import android.os.Bundle;

import com.kiven.kutils.activityHelper.KActivityDebugHelper;
import com.kiven.kutils.activityHelper.KHelperActivity;
import com.kiven.sample.R;

public class ImActivity extends KActivityDebugHelper {
    @Override
    public void onCreate(KHelperActivity activity, Bundle savedInstanceState) {
        super.onCreate(activity, savedInstanceState);
        setContentView(R.layout.activity_im);
    }
}
