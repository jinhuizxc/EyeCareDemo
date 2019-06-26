package com.example.eyecaredemo;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * android护眼app实现原理及源码(跨app)
 * https://blog.csdn.net/weixin_40826724/article/details/88391578
 * <p>
 * 在这个demo下实现效果;
 *
 * 设置blue的值为: 31
 *
 * 伪代码:
 * case R.id.ll_eye_setting:
 *                 // 弹出弹框，是否切换护眼模式,点击确定切换，
 *                 if (SPUtils.isEyeCare) {
 *                     // 切换普通状态
 *                     if (EyeCareUtil.dialog != null && EyeCareUtil.dialog.isShowing()){
 *                         EyeCareUtil.dialog.dismiss();
 *                     }
 *                     SPUtils.isEyeCare = false;
 *                     SPUtils.blue = 100;
 *                     SPUtils.setSharedIntData(getActivity(), "blue", SPUtils.blue);
 *                 } else {
 *                     // 设置护眼模式
 *                     EyeCareUtil.openAlerterWindow(getActivity());
 *                     SPUtils.isEyeCare = true;
 *                 }
 *                 break;
 *
 *
 * 判断状态
 *   // 判断是否是护眼模式
 *         int blue = SPUtils.getSharedIntData(mContext, "blue");
 *         if (blue == 30){
 *             // 设置护眼模式
 *             EyeCareUtil.openAlerterWindow(mContext);
 *             SPUtils.isEyeCare = true;
 *         }
 *
 */
public class SampleActivity extends AppCompatActivity {

    private static final String TAG = "SampleActivity";
    @BindView(R.id.bt_eye)
    Button btEye;

    boolean isEyeCare;

    Dialog dialog;
    ImageView iv_image;

    //红蓝绿 三原色的初始值
    private int red = 100;
    private int green = 100;
    private int blue = 100;
    // 透明度
    private int alpha = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);

        initData();



    }

    public void initData() {    //每当打开acitivy 对 sharedprefernces 初始化
        SharedPreferences myPreference = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPreference.edit();
        editor.putInt("alpha", 100);
        editor.putInt("red", 100);
        editor.putInt("blue", 100);
        editor.putInt("green", 30);
        editor.apply();
    }


    @OnClick(R.id.bt_eye)
    public void onViewClicked() {
        if (isEyeCare){
            // 切换普通状态
            dialog.dismiss();
            isEyeCare = false;
        }else {
            // 设置护眼模式
            openAleterWindow();
            isEyeCare = true;
        }
    }

    private void openAleterWindow() {
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

    public void getData() {  //获取 存储 sharePrefrence 保存的三原色值
        SharedPreferences preferences = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        alpha = preferences.getInt("alpha", 100);
        red = preferences.getInt("red", 100);
        green = preferences.getInt("green", 100);
        blue = preferences.getInt("blue", 100);
        Log.e(TAG, "onProgressChanged: "
                + " alpha的值为: " + alpha
                +" red的值为: " +  red
                + " green的值为: " + green
                + " blue的值为: " + blue);
        iv_image.setBackgroundColor(Color.argb(alpha, red, green, 30));

    }
}
