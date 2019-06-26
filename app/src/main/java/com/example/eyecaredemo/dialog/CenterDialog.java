package com.example.eyecaredemo.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.eyecaredemo.R;

/**
 * Created by jinhui on 2018/4/3.
 * email: 1004260403@qq.com
 *
 * 自定义dialog
 */

public class CenterDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private int layoutResID;
       // 要监听的控件id
    private int[] listenedItems;


    public CenterDialog(Context context, int layoutResID, int[] items) {
        super(context, R.style.dialog_custom);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = items;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);  // 此处可以设置dialog显示的位置为居中
//        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(layoutResID);
        WindowManager windowManager = ((Activity)context).getWindowManager(); // 宽度全屏
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth()*4/5; // 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);
//        setCanceledOnTouchOutside(true); // 点击Dialog外部消失
        setCanceledOnTouchOutside(false);
        for (int id: listenedItems){
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        listener.OnCenterItemClick(this, view);
    }

    public interface OnCenterItemClickListener{
        void OnCenterItemClick(CenterDialog dialog, View view);
    }

    private OnCenterItemClickListener listener;

    public OnCenterItemClickListener getListener() {
        return listener;
    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }
}
