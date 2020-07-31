package com.jess.arms.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.R;


/**
 * version   0.1
 * date   2018/3/20 10:53
 * author   caojiaxu
 */
public class BottomEditDialog extends Dialog implements View.OnClickListener {
    private OnDialogClickListener mOnClickListener;
    private String mTitle;

    private Button mBtConfirm;
    private Button mBtCancel;
    private EditText mEtContent;

    public BottomEditDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        init(context);
    }

    public BottomEditDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public BottomEditDialog(@NonNull Context context, String title) {
        super(context, R.style.ActionSheetDialogStyle);
        mTitle = title;
        init(context);
    }

    private void init(@NonNull Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_dialog_bottom_edit, null);
        setContentView(inflate);
        mEtContent = inflate.findViewById(R.id.editText);
        mBtConfirm = inflate.findViewById(R.id.bt_confirm);
        mBtConfirm.setOnClickListener(this);
        mBtCancel = inflate.findViewById(R.id.bt_cancel);
        mBtCancel.setOnClickListener(this);
        //获取当前Activity所在的窗体
        Window dialogWindow = this.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
    }


    public BottomEditDialog hideConfirmButton(String buttonText) {
        mBtConfirm.setText(buttonText);
        mBtConfirm.setVisibility(View.GONE);
        return this;
    }

    public BottomEditDialog hideCancelButton() {
        mBtCancel.setVisibility(View.GONE);
        return this;
    }

    protected BottomEditDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_confirm) {
            if (mOnClickListener != null) {
                mOnClickListener.onConfirmClick(this,mEtContent.getText().toString());
            }
        } else if (id == R.id.bt_cancel) {
            this.dismiss();
        }
    }


    public interface OnDialogClickListener {
        void onConfirmClick(BottomEditDialog dialog, String content);

        void onCancelClick();
    }

    public BottomEditDialog setOnClickListener(OnDialogClickListener onClickListener) {
        mOnClickListener = onClickListener;
        return this;
    }
}
