package com.lxh.cookcommunity.ui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import com.lxh.cookcommunity.R;
import java.util.Objects;

public class FullScreenDialog extends Dialog {

    ViewDataBinding binding;

    public FullScreenDialog(@NonNull Context context, ViewDataBinding binding) {
        super(context, R.style.MyDialog);
        setOwnerActivity((Activity)context);
        this.binding = binding;
    }

    public FullScreenDialog(@NonNull Context context, ViewDataBinding binding, int themeResId) {
        super(context, themeResId);
        this.binding = binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.getRoot());
        //按空白处不能取消动画
        setCanceledOnTouchOutside(true);
        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }



    @Override
    public void show() {
        super.show();
    }

}