package com.example.eyecaredemo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 护眼模式工具类
 */
public class EyeCareUtil {

    public static Dialog dialog;
    private static Context mContext;
    public static ImageView iv_image;

    public static void openAlerterWindow(Context context) {
        mContext = context;
        dialog = new Dialog(context, R.style.dialog_translucent);
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

        setData();
    }

    public static void setData() {
        // 本地持久化
        SPUtils.blue = 30;
        SPUtils.setSharedIntData(mContext, "blue", SPUtils.blue);
        iv_image.setBackgroundColor(Color.argb(SPUtils.alpha, SPUtils.red, SPUtils.green, SPUtils.blue));
    }
}
