package org.qcode.qskinloader.entity;

/**
 * 皮肤指定属性及其对应的值/类型的实体类封装
 * qqliu
 * 2016/9/24.
 */
public class SkinAttr {
    /***
     * 对应View的属性
     */
    public String mAttrName;

    /***
     * 属性值对应的reference id值，类似R.color.XX
     */
    public int mAttrValueRefId;

    /***
     * 属性值refrence id对应的名称，如R.color.XX，则此值为"XX"
     */
    public String mAttrValueRefName;

    /***
     * 属性值refrence id对应的类型，如R.color.XX，则此值为color
     */
    public String mAttrValueTypeName;

    public SkinAttr() {
        //empty
    }

    public SkinAttr(String attrName) {
        mAttrName = attrName;
        //others is empty
    }

    @Override
    public String toString() {
        return "SkinAttr{" +
                "mAttrName='" + mAttrName + '\'' +
                ", mAttrValueRefId=" + mAttrValueRefId +
                ", mAttrValueRefName='" + mAttrValueRefName + '\'' +
                ", mAttrValueTypeName='" + mAttrValueTypeName + '\'' +
                '}';
    }
}
