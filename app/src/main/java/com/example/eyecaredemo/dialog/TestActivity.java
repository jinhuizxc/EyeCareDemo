package com.example.eyecaredemo.dialog;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.eyecaredemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ChangeBindActivity
 *
 */
public class TestActivity extends AppCompatActivity implements CenterDialog.OnCenterItemClickListener {

    @BindView(R.id.bt_verify)
    Button btVerify;
    private CenterDialog centerDialog;

    private EditText etPhone;
    private EditText etCode;

    // 获取验证码
    protected int brewTime = 1; // 默认1分钟
    boolean isGetCode = true;  // 获取验证码
    CountDownTimer countTimer;
    private Button btCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_verify)
    public void onViewClicked() {

        centerDialog = new CenterDialog(this, R.layout.dialog_verify,
                new int[]{R.id.dialog_cancel, R.id.dialog_sure, R.id.bt_code});

        centerDialog.setOnCenterItemClickListener(this);
        centerDialog.show();

        etPhone = (EditText) centerDialog.findViewById(R.id.et_phone);
        etCode = (EditText) centerDialog.findViewById(R.id.et_code);
        btCode = (Button) centerDialog.findViewById(R.id.bt_code);

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 0) {
                    btCode.setEnabled(true);
                    isGetCode = true;
//                    if (etCode.getText().toString().length() > 0){
//                        btOk.setEnabled(true);
//                    }
                } else {
                    btCode.setEnabled(false);
//                    if (etCode.getText().toString().length() > 0){
//                        btOk.setEnabled(false);
//                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.length() > 0 && etPhone.getText().toString().length() > 0) {
//                    btOk.setEnabled(true);
//                } else {
//                    btOk.setEnabled(false);
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public void OnCenterItemClick(CenterDialog dialog, View view) {
        switch (view.getId()) {
            case R.id.dialog_cancel:
                centerDialog.dismiss();
                if (countTimer != null){
                    countTimer.cancel();
                }
                break;
            case R.id.dialog_sure:
                // 验证手机号是否合法，输入内容改变的话获取验证码状态进行改变，

                if (TextUtils.isEmpty(etPhone.getText().toString())){
                    ToastUtils.showShort("请输入手机号");
                    return;
                }
                boolean mobileExact = RegexUtils.isMobileExact(etPhone.getText().toString());
                if (!mobileExact){
                    ToastUtils.showShort("输入手机号不合法");
                    return;
                }
                if (TextUtils.isEmpty(etCode.getText().toString())){
                    ToastUtils.showShort("验证码不能为空");
                    return;
                }

                Toast.makeText(this, "可以认证了...", Toast.LENGTH_SHORT).show();

                break;
            case R.id.bt_code:
                boolean isMobile = RegexUtils.isMobileExact(etPhone.getText().toString());
                if (!isMobile){
                    ToastUtils.showShort("输入手机号不合法");
                    return;
                }
//                if (!etPhone.getText().toString().matches("[1][3456789]\\d{9}")) {
//                    Toast.makeText(this, "请输入有效的手机号", Toast.LENGTH_SHORT).show();
//                    return;
//                }else {
//
//                }
                if (isGetCode){
                    getCode();
                    isGetCode = false;
                }else {
                    Log.e("不获取验证码", "避免重复点击");
                }
                break;
        }
    }

    private void getCode() {
        ToastUtils.showShort("获取验证码ing");
        startCountDown();
    }

    private void startCountDown() {
        countTimer = new CountDownTimer(brewTime * 60 * 1000, 1000) {
            @Override
            public void onTick(long minutes) {
                if (btCode != null){
                    btCode.setText(String.valueOf(minutes / 1000) + "秒后重发");
                }
            }

            @Override
            public void onFinish() {
                if (btCode != null){
                    btCode.setEnabled(true);
                    btCode.setText("获取验证码");
                }
                isGetCode = true;
            }
        };
        countTimer.start();
        btCode.setEnabled(false);
    }
}
