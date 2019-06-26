package com.example.eyecaredemo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * android护眼app实现原理及源码(跨app)
 * https://blog.csdn.net/weixin_40826724/article/details/88391578
 * <p>
 * https://gitee.com/qq137712630/MeiZiNews
 * <p>
 * Android换肤／夜间模式的Android框架，配合theme和换肤控件框架可以做到无缝切换换肤（无需重启应用和当前页面）。
 * This framework of Android app support multiple theme(such as day/night mode) and needn’t
 * finish current application or current activity when you switch theme-mode.
 * <p>
 * https://github.com/dersoncheng/MultipleTheme
 * <p>
 * 一种完全无侵入的换肤方式，支持插件式和应用内，无需重启Activity.
 * https://github.com/hongyangAndroid/AndroidChangeSkin
 * <p>
 * Android夜间护眼模式源码
 * https://download.csdn.net/download/robertcpp/8332367
 * <p>
 * 搜索引擎搜索: android源码护眼
 */
public class ReadActivity extends AppCompatActivity {

    private static final String TAG = "ReadActivity";

    ImageView iv_image;
    Dialog dialog;

    @BindView(R.id.sb_light)
    SeekBar sbLight;
    @BindView(R.id.sb_red)
    SeekBar sbRed;
    @BindView(R.id.sb_blue)
    SeekBar sbBlue;
    @BindView(R.id.sb_green)
    SeekBar sbGreen;

    //红蓝绿 三原色的初始值
    private int red = 100;
    private int green = 100;
    private int blue = 100;
    // 透明度
    private int alpha = 100;

    private int blueProgress = 0;
    private int redProgress = 0;
    private int greenProgress = 0;
    private int alphaProgress = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read);
        ButterKnife.bind(this);

        openAleterWindow();
        initData();
        initListener();

    }


    private void initListener() {
        sbLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int add = progress - alphaProgress;
                alpha = alpha + add;
                alphaProgress = progress;
                Log.e(TAG, "alpha" + alpha);
                iv_image.setBackgroundColor(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        sbRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int add = progress - redProgress;
                red = red - add;
                redProgress = progress;
                Log.e(TAG, "red:" + red);
                iv_image.setBackgroundColor(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        sbBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int add = progress - blueProgress;
                blue = blue - add;
                blueProgress = progress;
                Log.e(TAG, "blue:" + blue);  // E/this: blue:37
                Log.e(TAG, "onProgressChanged: "
                        + " alpha的值为: " + alpha
                         +" red的值为: " +  red
                        + " green的值为: " + green
                        + " blue的值为: " + blue);
                // E/ReadActivity: onProgressChanged:  alpha的值为: 100 red的值为: 100 green的值为: 100 blue的值为: 31
                iv_image.setBackgroundColor(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int add = progress - greenProgress;
                green = green - add;
                greenProgress = progress;
                Log.e("this", "green:" + green);
                iv_image.setBackgroundColor(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void initData() {    //每当打开acitivy 对 sharedprefernces 初始化
        SharedPreferences myPreference = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPreference.edit();
        editor.putInt("alpha", 100);
        editor.putInt("red", 100);
        editor.putInt("blue", 100);
        editor.putInt("green", 100);

        editor.apply();

    }

    public void getData() {  //获取 存储 sharePrefrence 保存的三原色值
        SharedPreferences preferences = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        alpha = preferences.getInt("alpha", 100);
        red = preferences.getInt("red", 100);
        green = preferences.getInt("green", 100);
        blue = preferences.getInt("blue", 100);
        sbLight.setProgress(alpha - 100);
        sbRed.setProgress(100 - red);
        sbGreen.setProgress(100 - green);
        sbBlue.setProgress(100 - blue);
        iv_image.setBackgroundColor(Color.argb(alpha, red, green, blue));
//        changeAppBrightness(ld);
    }

    public void saveData() {
        SharedPreferences myPreference = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPreference.edit();
        if (alpha >= 0 && alpha <= 255 && red >= 0 && red <= 100 && blue >= 0 & blue <= 100 && green >= 0 && green <= 100) {
            editor.putInt("alpha", alpha);
            editor.putInt("red", red);
            editor.putInt("blue", blue);
            editor.putInt("green", green);
            editor.apply();
        }


    }


    private void openAleterWindow() {   //打开 dailog 窗口 对 dailog 初始化

        dialog = new Dialog(this, R.style.dialog_translucent);
        dialog.setContentView(R.layout.dailog);


        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;  //设置不影响下层的触碰
        if (Build.VERSION.SDK_INT >= 26) {
            lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        }


        dialog.getWindow().setAttributes(lp);

        dialog.show();


        iv_image = dialog.findViewById(R.id.ll_main);

        getData();


    }


    public void permission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
            }
        }
    }


    public void changeAppBrightness(int brightness) {   //改变系统屏幕亮度
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        Float ld = Float.valueOf(brightness) * (1f / 100f);
        Log.e("this", "ld:" + ld);
        lp.screenBrightness = ld;

        getWindow().setAttributes(lp);
    }


}
