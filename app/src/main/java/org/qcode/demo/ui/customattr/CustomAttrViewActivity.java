package org.qcode.demo.ui.customattr;

import android.os.Bundle;

import org.qcode.demo.BaseActivity;
import org.qcode.qskinloader.SkinManager;
import org.qcode.skintestdemo.R;

import static org.qcode.demo.ui.customattr.DefBackgroundAttrHandler.DEF_BACKGROUND;
import static org.qcode.demo.ui.customattr.DefTextColorAttrHandler.DEF_TEXT_COLOR;

public class CustomAttrViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        registerHandler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_attr_test);
    }

    private void registerHandler() {
        SkinManager.getInstance().registerSkinAttrHandler(
                DEF_TEXT_COLOR, new DefTextColorAttrHandler());

        SkinManager.getInstance().registerSkinAttrHandler(
                DEF_BACKGROUND, new DefBackgroundAttrHandler());
    }
}
