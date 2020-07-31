package com.jess.arms.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.R;


/**
 * version   0.1
 * date   2018/3/20 10:53
 * author   caojiaxu
 */
public class CommonDialog extends Dialog implements View.OnClickListener {
    private TextView mTvTitle, mTvMessage, mTvConfirm, mTvCancel;
    private OnClickListener mOnClickListener;
    private String mTitle;

    private TextView mConfirm;
    private TextView mCansel;

    public CommonDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        init(context);
    }

    public CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public CommonDialog(@NonNull Context context, String title) {
        super(context, R.style.ActionSheetDialogStyle);
        mTitle = title;
        init(context);
    }

    private void init(@NonNull Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_dialog_common, null);
        setContentView(inflate);
        mTvTitle = inflate.findViewById(R.id.tv_title);
        mTvMessage = inflate.findViewById(R.id.tv_message);
        mTvConfirm = inflate.findViewById(R.id.tv_confirm);
        mTvCancel = inflate.findViewById(R.id.tv_cancel);
        if (!TextUtils.isEmpty(mTitle)) {
            mTvTitle.setText(mTitle);
        }
        mConfirm = inflate.findViewById(R.id.tv_confirm);
        mConfirm.setOnClickListener(this);
        mCansel = inflate.findViewById(R.id.tv_cancel);
        mCansel.setOnClickListener(this);
        //获取当前Activity所在的窗体
        Window dialogWindow = this.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
    }

    public CommonDialog setMessage(String message) {
        if (!TextUtils.isEmpty(message))
            mTvMessage.setText(message);
        return this;
    }

    public CommonDialog setTitle(String message) {
        if (!TextUtils.isEmpty(message))
            mTvTitle.setText(message);
        return this;
    }

    public CommonDialog hideConfirmButton(String buttonText) {
        mConfirm.setText(buttonText);
        mConfirm.setVisibility(View.GONE);
        return this;
    }

    public CommonDialog hideCancelButton() {
        mCansel.setVisibility(View.GONE);
        return this;
    }

    protected CommonDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_confirm) {
            if (mOnClickListener != null) {
                mOnClickListener.onConfirmClick(this);
            }
        } else if (id == R.id.tv_cancel) {
            this.dismiss();
        }
        this.dismiss();
    }

    public CommonDialog setPositiveButton(String text) {
        mTvConfirm.setText(text);
        return this;
    }

    public interface OnClickListener {
        void onConfirmClick(CommonDialog dialog);

        void onCancelClick();
    }

    public CommonDialog setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        return this;
    }
}
