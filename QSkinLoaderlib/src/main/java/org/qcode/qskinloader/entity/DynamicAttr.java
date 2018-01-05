package org.qcode.qskinloader.entity;

/**
 * 动态代码设置皮肤属性的实体类
 *
 * qqliu
 * 2016/9/25.
 */
public class DynamicAttr {

    /***
     * 对应View的属性
     */
    public String mAttrName;

    /***
     * 属性值对应的reference id值，类似R.color.XX
     */
    public int mAttrValueRefId = -1;

    /**是否设置了属性值引用*/
    public boolean hasSetValueRef = false;

    /***
     * 是否保留dynamicAttr；
     * 子类继承DynamicAttr时可以改变此属性来保留自定义的属性值
     */
    public boolean keepInstance = false;

    public DynamicAttr(String attrName) {
        this.mAttrName = attrName;
        hasSetValueRef = false;
        keepInstance = false;
    }

    public DynamicAttr(String attrName, int attrValueRefId) {
        this.mAttrName = attrName;
        this.mAttrValueRefId = attrValueRefId;
        hasSetValueRef = true;
        keepInstance = false;
    }

    @Override
    public String toString() {
        return "DynamicAttr{" +
                "mAttrName='" + mAttrName + '\'' +
                ", mAttrValueRefId=" + mAttrValueRefId +
                ", hasSetValueRef=" + hasSetValueRef +
                ", keepInstance=" + keepInstance +
                '}';
    }
}
