package com.abc.appmain.vm;

import com.abc.appmain.ZhorMainActivity;
import com.abc.appmain.databinding.ZhorMainActivityMainBinding;
import com.abc.appmain.model.ZhorMainModel;
import com.abc.middlecommon.mvvmbase.BaseMvvmVM;

/**
 * Created by wudi on 2018/4/27.
 */

public class ZhorMainVM extends BaseMvvmVM<ZhorMainActivity,ZhorMainModel,ZhorMainActivityMainBinding> {
    public ZhorMainVM(ZhorMainActivity view, ZhorMainModel model, ZhorMainActivityMainBinding binding) {
        super(view, model, binding);
    }
}
