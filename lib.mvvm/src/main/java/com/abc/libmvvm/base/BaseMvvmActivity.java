package com.abc.libmvvm.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.abc.libcore.activity.BaseAppActivity;
import com.abc.libcore.rxtools.RxKeyboardTool;
import com.abc.libcore.rxtools.RxLogTool;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by wudi on 2018/4/28.
 */
public abstract class BaseMvvmActivity<VM extends BaseMvvmVM<? extends BaseMvvmActivity,?,? extends ViewDataBinding>,B extends ViewDataBinding>
        extends BaseAppActivity {

    public VM mViewModel;
    public B mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = setLayoutToView();
        bindingDefModel();
        mViewModel = createVM();
        if (mViewModel==null) {
            RxLogTool.e("当前页面（"+getLocalClassName()+"）未绑定ViewModel！");
        } else {
            bindingVM();
            Observable.interval(1, TimeUnit.SECONDS).compose(this.bindToLifecycle())
                    .subscribe();
        }
        initView();

        initData();
    }

    public abstract B setLayoutToView();

    public abstract void bindingDefModel();

    public abstract VM createVM();

    protected abstract void bindingVM();

    protected abstract void initView();

    protected abstract void initData();


    /**
     * 点击空白处隐藏软键盘
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent (MotionEvent event){
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
            RxKeyboardTool.hideSoftInput(this);
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
