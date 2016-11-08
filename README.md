# QSkinLoader

**README分三部分：基本简介、使用方法、框架由来与架构设计。
如果不嫌麻烦，还可以去看文章[夜间模式方案调研](http://blog.csdn.net/u013478336/article/details/52484322)和[QSkinLoader框架介绍](http://blog.csdn.net/u013478336/article/details/53083054)**

#**基本简介：**
一个支持多种场景的Android换肤框架。基本原理是通过代理LayoutInflater的View创建过程解析皮肤相关属性（background/src/textColor等），将皮肤相关属性设置到View的Tag内，在切换皮肤时寻找对应的皮肤来完成实时刷新动作。此方案具有代码及XML侵入性小、功能完善（支持Activity/Dialog/悬浮窗/PopWindow等）、无需重启Activity、支持自定义属性换肤、同时支持资源内换肤和独立资源包（下载后换肤）等优点。

#**使用方法**
## 基本使用
由于可以自定义皮肤资源加载过程，QSkinLoader框架内并未提供当前皮肤的保存逻辑（不能支持loadCurrentSkin之类的接口）。因此建议使用框架时封装两个类：一个负责保存当前皮肤（保存皮肤其实就是SharePreference持久化存储，此处略去），一个负责与框架交互，如下：
```Java
public void init(Context context) {
    SkinManager.getInstance().init(context);
}

public void switchSkinMode(OnSkinChangeListener listener) {
    mIsSwitching = true;
    mIsDefaultMode = !mIsDefaultMode;
    refreshSkin(listener);
}

public void refreshSkin(OnSkinChangeListener listener) {
    if (mIsDefaultMode) {
        //恢复到默认皮肤
        SkinManager.getInstance().restoreDefault(SkinConstant.DEFAULT_SKIN, new MyLoadSkinListener(listener));
    } else {
        changeSkin(listener);
    }
}

private void changeSkin(OnSkinChangeListener listener) {
    SkinManager.getInstance().loadSkin("_night",
            new SuffixResourceLoader(mContext),
            new MyLoadSkinListener(listener));
}
```
具体代码此处不完全贴出了，工程内有详细的代码。

###1. 框架初始化
在Application创建过程中执行框架的初始化：
```Java
// 初始化皮肤框架
SkinChangeHelper.getInstance().init(this);
//初始化上次缓存的皮肤
SkinChangeHelper.getInstance().refreshSkin(null);
```
初始化了框架后需要调用refreshSkin来加载上一次的皮肤设置，refreshSkin加载完成皮肤后会通知各Activity界面刷新皮肤设置，由于此处在Application初始化时调用，可能加载完成皮肤设置后界面仍未初始化，这并不无影响，因为Activity初始化时会主动执行一次换肤操作，弥补此过程的缺失。

###2. Activity初始化与各生命周期调用
因为换肤一般是整个应用都需要执行的过程，此处建议实现一个基础类(BaseActivity)来封装换肤相关逻辑，此类建议实现接口ISkinActivity，告知是否支持换肤，以及在换肤操作触发后如果界面不在前台是否立刻换肤：
```Java
@Override
public boolean isSupportSkinChange() {
    //告知当前界面是否支持换肤：true支持换肤，false不支持
    return true;
}

@Override
public boolean isSwitchSkinImmediately() {
    //告知当切换皮肤时，是否立刻刷新当前界面；true立刻刷新，false表示在界面onResume时刷新；
    //减轻换肤时性能压力
    return false;
}

@Override
public void handleSkinChange() {
    //当前界面在换肤时收到的回调，可以在此回调内做一些其他事情；
    //比如：通知WebView内的页面切换到夜间模式等
}
```
然后在Activity的onCreate中执行IActivitySkinEventHandler的初始化与配置工作：
```Java
//初始化并配置IActivitySkinEventHandler，应在IActivitySkinEventHandler.onCreate之前执行
mSkinEventHandler = SkinManager.newActivitySkinEventHandler()
        .setSwitchSkinImmediately(isSwitchSkinImmediately())
        .setSupportSkinChange(isSupportSkinChange())
        .setWindowBackgroundResource(getWindowBackgroundResource())
        .setNeedDelegateViewCreate(false);
//通知框架onCreate事件
mSkinEventHandler.onCreate(this);
```
其中，**setWindowBackgroundResource**用于设置Activity的背景色，在换肤时会寻找对应的背景色替换之，此处传入的不能是色值，只支持引用，类似R.color.xx。
**setNeedDelegateViewCreate**用于设置是否需要代理View创建，因为LayoutInflater的代理View创建Factory只支持设置一次，如果外部已经设置了Factory，则框架内再次设置会引起崩溃，所以框架使用配置与回调来处理此问题。具体见高级使用部分。

其他生命周期回调基本类似，挑两个做实例，如下：
```Java
@Override
protected void onResume() {
    super.onResume();
    //皮肤相关，此通知放在此处，尽量让子类的view都添加到view树内
    if (mFirstTimeApplySkin) {
        mSkinEventHandler.onViewCreated();
        mFirstTimeApplySkin = false;
    }
    //皮肤相关
    mSkinEventHandler.onResume();
}

@Override
public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);

    //皮肤相关
    mSkinEventHandler.onWindowFocusChanged(hasFocus);
}
```
###3. XML配置
QSkinLoader只支持引用型资源换肤，所有的颜色定义都应定义在colors.xml内，在使用时引用。
对于一个布局，需要定义一个skin的命名空间：
```XML
xmlns:skin="http://schemas.android.com/android/skin"
```
然后对所有需要换肤的View增加属性：
```XML
skin:enable="true"
```
即可完成换肤配置。举例如下：
```XML
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:skin="http://schemas.android.com/android/skin"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    skin:enable="true"
    android:gravity="center_vertical"
    android:background="@color/color_background">
    <ImageView
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:src="@mipmap/ic_launcher"/>

    <TextView
        android:id="@+id/list_item_text_view"
        android:layout_width="100dp"
        android:layout_height="50dp"
        android:textColor="@color/color_text"
        skin:enable="true"/>
</LinearLayout>
```
在这段布局内，框架代理创建Linearlayout时会解析其background属性，代理创建View时不解析任何属性，代理创建TextView时会解析textColor属性。

###4. 图片蒙层
对ImageView/ImageButton可以配置属性：
```XML
skin:drawShadow="@color/night_shadow_color"
```
来支持图片蒙层，night_shadow_color是一个颜色引用，在默认情况下建议使用透明色，同时在皮肤包内定义此值为另一个色值（不必须是半透明色）。
需要注意的是：蒙层的原理是ImageView的ColorFilter，有时候对ImageView设置ColorFilter会失效。但是对Drawable设置ColorFilter基本都会生效，所以如果是对ImageView的Src属性做蒙层，建议使用框架内的ShadowImageView替代ImageView。如下：
```XML
<org.qcode.qskinloader.view.ShadowImageView
            android:id="@+id/logo"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerVertical="true"
            skin:enable="true"
            skin:drawShadow="@color/news_pic_night_shadow_color"/>
```

###5. 换肤与恢复默认皮肤
**先来看看换肤操作：**
```Java
SkinManager.getInstance().loadSkin("_night",
            new SuffixResourceLoader(mContext),
            new MyLoadSkinListener(listener));
```
这是基于资源后缀的换肤方式，对于R.color.color_text，切换到夜间模式时，框架会去找R.color.color_text_night作为夜间模式的资源。
QSkinLoader换肤框架还支持另一种默认的换肤方式——APK资源换肤，也就是将资源文件定义在独立的APK文件内，此文件可从服务端下载，从而真正实现动态换肤。此方式对现有工程的影响比较小，非常值得推荐。具体方式如下：
```Java
SkinUtils.copyAssetSkin(mContext);
File skin = new File(SkinUtils.getTotalSkinPath(mContext));
SkinManager.getInstance().loadAPKSkin(
        skin.getAbsolutePath(), new MyLoadSkinListener(listener));
```
当然也可以写成：
```Java
SkinManager.getInstance().loadSkin(skin.getAbsolutePath(),
            new APKResourceLoader(mContext),
            new MyLoadSkinListener(listener));
```
此时对于资源R.color.color_text，框架会去skin路径的APK文件内寻找对于的资源R.color.color_text，找不到就继续使用当前应用的color_text资源。
自定义皮肤加载过程见高级使用部分。

**那么怎么恢复默认皮肤呢？**
```Java
//恢复到默认皮肤
SkinManager.getInstance().restoreDefault(SkinConstant.DEFAULT_SKIN,
        new MyLoadSkinListener(listener));
```
DEFAULT_SKIN值对框架而言并无意义，框架只是把此值回调到ILoadSkinListener使外部知道当前加载的是默认皮肤，所以此值是在**框架外**定义的。

###6. 动态创建View的皮肤设置
上文中指出的使用方式是基于XML配置的，如果是在Java代码内如何使用呢？
QSkinLoader框架提供了一个帮助类ISkinViewHelper来添加/删除View的皮肤属性。此类设计为链式编程方式，提供的接口有：
```Java
ISkinViewHelper setViewAttrs(String attrName, int resId);
ISkinViewHelper setViewAttrs(DynamicAttr... dynamicAttrs);
ISkinViewHelper setViewAttrs(SkinAttr... skinAttrs);
ISkinViewHelper setViewAttrs(List<DynamicAttr> dynamicAttrs);

ISkinViewHelper addViewAttrs(String attrName, int resId);
ISkinViewHelper addViewAttrs(DynamicAttr... dynamicAttrs);
ISkinViewHelper addViewAttrs(SkinAttr... skinAttrs);
ISkinViewHelper addViewAttrs(List<DynamicAttr> dynamicAttrs);

ISkinViewHelper cleanAttrs(boolean clearChild);
void applySkin(boolean applyChild);
```
如果View本身已经有了皮肤属性，setViewAttrs接口会替换已有的皮肤属性，而addViewAttrs不会覆盖已有属性，而是在已有的皮肤属性内添加新的属性。
cleanAttrs会清除View的所有皮肤属性，如果传入clearChild为true则遍历所有子元素清除皮肤属性，false只清除自身属性。
applySkin则对当前View应用皮肤设置，如果传入applyChild为true则遍历所有子元素应用皮肤，false只应用自身。
假设对一个TextView，动态设置View的皮肤属性大致如下：
```Java
SkinManager
    .with(textview)
    .setViewAttrs(SkinAttrName.BACKGROUND, R.color.white)
    .addViewAttrs(SkinAttrName.TEXT_COLOR, R.color.black)
    .applySkin(false);
```
所有框架支持的属性名称都定义在SkinAttrName内，如果需要扩展属性支持，建议参考自定义View属性处理器部分。

###7. 特定View的换肤管理
上面的换肤过程都是对Activity的View树做遍历换肤操作的，树根是：
```Java
activity.findViewById(android.R.id.content);
```
所有不在这颗树内的View都不能换肤，哪些View不在换肤范围呢？
Dialog的View、popWindow的View、悬浮窗(WindowManager上直接加View)，目前这三类View要换肤都应该使用特定View的换肤管理模块。
需要注意的是：Dialog的交互具有排他型，通常在换肤操作时是不展示的，所以一般可以在show接口调用时做换肤，而不使用IWindowViewManager。
**怎么对特定View进行换肤管理呢？**
框架提供了IWindowViewManager接口来提供特定View的管理，支持链式编程，接口如下：
```Java
IWindowViewManager addWindowView(View view);
IWindowViewManager removeWindowView(View view);
IWindowViewManager clear();
void applySkinForViews(boolean applyChild);
List<View> getWindowViewList();
```
接口比较简单，主要是增加/删除/清空全局View列表和应用皮肤的操作。
使用如下：
```Java
View popView = LayoutInflater.from(mContext).inflate(
    R.layout.news_list_item_pop, null);
SkinManager.getInstance().applySkin(popView, true);
SkinManager
        .getWindowViewManager()
        .addWindowView(popView);
popupWindow = new PopupWindow(popView, popWidth, popHeight);
```
通常不建议使用applySkinForViews接口，因为它会遍历所有全局View列表的View做遍历，所以替代方式是先对当前View做属性设置，再添加到框架内管理，从而在下次换肤时接口换肤事件。
IWindowViewManager内的View是弱引用存储的，所以不会发生内存泄露，但建议在View无用的时候从框架内移除特定View。

## 高级使用
###1. 自定义View属性处理器
当项目需要自定义View时，一般都会自定义一些属性，这些属性框架是不支持换肤的，此时需要自定义属性处理器并注册到框架内。自定义属性处理器实现接口ISkinAttrHandler，实现方法：
```Java
void apply(View view,
        SkinAttr skinAttr,
        IResourceManager resourceManager);
```
下面是一个示例：
若有一个自定义CustomTextView，使用属性defTextColor来定义文字颜色，如下：
```XML
<org.qcode.demo.ui.customattr.CustomTextView
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:gravity="center"
        android:text="自定义的文字颜色和背景1"
        app:defBackground="@color/color_background"
        app:defTextColor="@color/color_text"
        skin:enable="true" />
```
则其自定义属性处理器为：
```Java
public class DefTextColorAttrHandler implements ISkinAttrHandler {
    public static final String DEF_TEXT_COLOR = "defTextColor";

    @Override
    public void apply(View view, SkinAttr skinAttr, IResourceManager resourceManager) {
        if(!(view instanceof CustomTextView)) {
            //防止在错误的View上设置了此属性
            return;
        }
        CustomTextView tv = (CustomTextView) view;
        if (RES_TYPE_NAME_COLOR.equals(skinAttr.mAttrValueTypeName)) {
            if (SkinConstant.RES_TYPE_NAME_COLOR.equals(
            skinAttr.mAttrValueTypeName)) {
                try {
                    //先尝试按照int型颜色解析
                    int textColor = resourceManager.getColor(
                            skinAttr.mAttrValueRefId, 
                            skinAttr.mAttrValueRefName);
                    tv.setTextColor(textColor);

                } catch (Resources.NotFoundException ex) {
                    //不是int型则按照ColorStateList引用来解析
                    ColorStateList textColor = 
                    resourceManager.getColorStateList(
                            skinAttr.mAttrValueRefId, 
                            skinAttr.mAttrValueRefName);
                    tv.setTextColor(textColor);
                }
            }
        }
    }
}
```
定义了属性处理器后，再注册到框架内，**注册需要在setContentView之前**：
```Java
SkinManager.getInstance().registerSkinAttrHandler(
        DEF_TEXT_COLOR, new DefTextColorAttrHandler());
```
**注意：**自定义属性处理器不一定就是与皮肤相关的属性的处理，也可以是换肤过程中需要对View进行的特定处理。比如RecyclerView换肤的时候要清除内部View缓存（因为其onBindViewHolder不是每次子View显示时都回调），此时，可以定义如下的属性处理器：
```Java
class RecyclerViewClearSubAttrHandler implements ISkinAttrHandler {
    @Override
    public void apply(View view, SkinAttr skinAttr, IResourceManager resourceManager) {
        Field declaredField = view.getDeclaredField("mRecycler");
        ......
        RecyclerView.RecycledViewPool recycledViewPool = recyclerView.getRecycledViewPool();
        recycledViewPool.clear();
}
```
此处代码有删减，具体见框架内的RecyclerViewClearSubAttrHandler处理器。使用时如下：
```Java
SkinAttr clearSubAttr = new SkinAttr(SkinAttrName.CLEAR_RECYCLER_VIEW);
SkinManager
        .with(view)
        .addViewAttrs(clearSubAttr);
```
###2. 自定义皮肤资源加载
框架默认支持资源名称后缀加载、APK加载两种换肤方式，如果项目准备采用其他的加载方式，可以通过自定义皮肤资源加载过程来实现。
自定义皮肤资源加载的核心是实现IResourceLoader接口，接口只有一个方法：
```Java
void loadResource(String skinIdentifier,
                      ILoadResourceCallback loadCallBack);
```
也就是定义了从皮肤标识符skinIdentifier加载资源，并在加载过程中通过loadCallBack对外通知加载过程：
```Java
public interface ILoadResourceCallback {
    void onLoadStart(String identifier);
    void onLoadSuccess(String identifier, IResourceManager result);
    void onLoadFail(String identifier, int errorCode);
}
```
加载开始和失败没啥可说的，主要是加载完成后，需要返回一个资源管理类IResourceManager。这个类定义了如何从指定的换肤流程中抽取对应的皮肤资源：
```Java
public interface IResourceManager {
    String getSkinIdentifier();
    Drawable getDrawable(int resId, String resName) throws Resources.NotFoundException;
    int getColor(int resId, String resName) throws Resources.NotFoundException;
    ColorStateList getColorStateList(int resId, String resName) throws Resources.NotFoundException;
}
```
整个过程比较简单，自定义一个加载过程，再返回一个资源管理类即可。下面以后缀资源加载的方式做个示例（摘录部分代码，具体见工程）：
```Java
public class SuffixResourceLoader implements IResourceLoader {
    private String mSkinSuffix;
    
    @Override
    public void loadResource(final String skinIdentifier,
                final ILoadResourceCallback loadCallBack) {
        //通知加载开始
        loadCallBack.onLoadStart(skinIdentifier);
        //后缀存下，加载过程就结束了，不像apk加载，还需要操作AssetManager
        mSkinSuffix = skinIdentifier;
        //通知加载结束，返回一个资源管理类SuffixResourceManager
        loadCallBack.onLoadSuccess(skinIdentifier,
                    new SuffixResourceManager(mContext, mSkinSuffix));
    }
}
```
```Java
public class SuffixResourceManager implements IResourceManager {
    private Context mContext;
    private Resources mResources;
    private String mSkinSuffix;
    private String mPackageName;

    private HashMapCache<String, Integer> mColorCache
            = new HashMapCache<String, Integer>(true);

    public SuffixResourceManager(Context context, String skinSuffix) {
        mContext = context;
        mPackageName = mContext.getPackageName();
        mResources = mContext.getResources();
        mSkinSuffix = skinSuffix;
    }

    @Override
    public int getColor(int resId, String resName) {
        String trueResName = resName + mSkinSuffix;
        //找到名字+后缀的id，读取颜色
        int trueResId = mResources.getIdentifier(
                trueResName,
                SkinConstant.RES_TYPE_NAME_COLOR,
                mPackageName);
        int trueColor = mResources.getColor(trueResId);
        return trueColor;
    }
    ......
}
```
###3.解决与其他代理View创建过程的冲突
上文也简要的提到了此问题，对每个Activity的LayoutInflater的setFactory接口（代理View创建与属性解析）只能调用一次，而换肤框架是依赖此操作来完成皮肤属性解析的，因此我们需要设计一套方案在确保框架外已经代理了LayoutInflater后还能保证换肤功能的可用性。
我们需要保证两点：
- 如果框架外需要代理View创建，则框架应被告知不能代理View创建，并且提供一个帮助类在外部创建View创建时完成属性解析；
- 如果框架外不需要代理View创建，但需要解析属性，则提供接口在View创建前后对外回调；
对于第一点，可以通过IActivitySkinEventHandler.setNeedDelegateViewCreate来告知框架不代理View创建，解析属性的帮助类也可以从IActivitySkinEventHandler内取到，如下：
```Java
LayoutInflater.from(this).setFactory(new LayoutInflater.Factory() {
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = createView(name, context, attrs);
        //创建View后通知框架解析属性
        ISkinAttributeParser parser =
            mSkinEventHandler.getSkinAttributeParser();
        if (parser.isSupportSkin(name, context, attrs)) {
            parser.parseAttribute(view, name, context, attrs);
        }
        return view;
    }
});
```
核心代码就是这段解析属性的逻辑。

对于第二点，我们提供接口IViewCreateListener来监听View创建过程：
```Java
public interface IViewCreateListener {
        View beforeCreate(String name, Context context, AttributeSet attrs);
        void afterCreated(View view, String name, Context context, AttributeSet attrs);
}
```
beforeCreate在View创建之前执行，可以拦截框架的View创建过程，自己创建View，afterCreated在框架创建View后执行，用于框架外进一步处理。
此接口应通过IActivitySkinEventHandler.setViewCreateListener()设置到框架内使用。

##- 各种View的换肤应用
###1. ViewPager
ViewPager使用时，应在PagerAdapter的instantiateItem回调中对创建的View应用当前的皮肤。
```Java
mViewPager.setAdapter(new PagerAdapter() {
    ......
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = onCreatePagerView(position);
        container.addView(view);
        //每次实例化某个View时都对其应用皮肤设置
        SkinManager.with(view).applySkin(true);
        return view;
    }
});
```
###2. ListView/GridView
ListView/GridView都继承AbsListView，并使用BaseAdapter作为适配器，其换肤方法为：
```Java
listView.setAdapter(new BaseAdapter() {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null == convertView) {
            convertView = onCreateContentView(position);
        }
        //每次某个子元素需要展示时，都应用当前皮肤设置
        SkinManager.with(convertView).applySkin(true);
        return convertView;
    }
});
```
需要注意的是，如果ListView存在HeaderView或FooterView时，只使用上面的方法是不完善的，如果换肤时HeaderView/FooterView不在ListView内展示，则换肤失效，此时应调用ListView.mRecycler.clear()方法清除View缓存，具体见[上一篇文章](http://blog.csdn.net/u013478336/article/details/52484322)。
###3. RecyclerView
上一章也大致讲了RecyclerView换肤的注意事项，由于RecyclerView滑动时，其子元素出现的过程不一定会伴有onBindViewHolder回调，导致我们有时出现两种皮肤并存的问题。因此，使用RecyclerView时换肤一定要清除RecyclerView的缓存。
```Java
recyclerView.setAdapter(new RecyclerView.Adapter() {
    ......
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //此回调非必执行，但是执行时还是要应用皮肤设置
        SkinManager.with(holder.itemView).applySkin(true);
    }
});
```
为了应对RecyclerView清除缓存的问题，框架内定义了一个特殊的属性处理器RecyclerViewClearSubAttrHandler，其作用就是在换肤时，清除RecyclerView内的View缓存。具体使用方式如下：
```Java
SkinManager
    .with(recyclerView)
    .addViewAttrs(new SkinAttr(SkinAttrName.CLEAR_RECYCLER_VIEW));
```

#**框架由来与设计思路**
##1. 框架由来
此框架脱胎于项目需要实现夜间模式的需求，在[文章](http://blog.csdn.net/u013478336/article/details/52484322)中，我们列举了常见的几种实现夜间模式切换的方案，并大致对比了一下各种方案的优缺点，此处不再一一列举。仅大致摘录夜间模式的需求分析如下：
>夜间模式需要对屏幕上的文字/图片/视频三种表现形式做特殊处理，具体细化如下：
>**1）对界面背景，**白色等浅色背景应该变成黑色/灰色之类的深色背景，以此降低屏幕亮度减少视觉刺激；
>**2）对文字，**因背景色变深，文字颜色需变浅，以形成对比效果；
>**3）对图片，**对图片加蒙层，避免加载浅色图片带来的视觉刺激；
>**4）对视频，**通常在播放界面增加亮度变化功能，由用户来决定屏幕亮度。

目前成熟的方案各有优缺点，在项目最终选择方案时，需要考虑用户体验、工作量、框架稳定性、代码侵入性等各种因素。对各种实现方案进行考量后，最终项目决定在AndroidChangeSkin和AndroidSkinLoader两个框架内选择一个合适的框架做夜间模式的实现效果。
AndroidChangeSkin是基于View的Tag指定另一套皮肤的资源Id的框架，此框架有一个优化版本[Injor](https://github.com/hackware1993/injor)，本文基于此版本与[AndroidSkinLoader](https://github.com/fengjundev/Android-Skin-Loader)做对比。整体来看，Injor和AndroidSkinLoader的换肤速度都是满足需求的，大约在200～500ms之间；两者都不需要重启Activity使换肤生效。其他的对比如下：

1. Injor需要占用View的Tag标签来指定资源id，标签内容格式比较复杂（类似：skin:textColor:color_text|Background:color_bg）；但支持自定义View的属性，可扩展性比较好；
2. AndroidSkinLoader通过代理LayoutInflater创建View的过程来解析属性id，再从其他资源包中找出相同id的资源来做皮肤切换；不过所有View都被存在框架内，可能会导致内存泄露；并且不支持自定义属性，可扩展性比较差；

这样的对比结果下，Injor显然更胜一筹，但是Injor存在的最致命缺点就是集成复杂性较高(tag定义)，而AndroidSkinLoader只要在View上加一个标签android:enable="true"即可让View支持夜间模式，所以项目最终选择结合Injor和AndroidSkinLoader实现了一套新的皮肤加载框架——QSkinLoader。三者的区别联系如下：

```
|特性       |QSkinLoader                                |Injor                   |AndroidSkinLoader        |
|-----------|:------------------------------------------|:-----------------------|:------------------------|
|属性表达   |View的Tag内存储属性对象                    |String类型的Tag         |View列表内存储属性对象   |
|属性解析   |解析android标签的属性值                    |解析Tag                 |解析android标签的属性值  |
|属性保存   |存在View的id/tag内                         |存在View的id/tag内      |存在框架内的数据列表内   |
|换肤过程   |遍历View树，取Tag换肤                      |遍历View树，解析Tag换肤 |遍历框架内的View列表换肤 |
|侵入性     |侵入性弱                                   |侵入性偏强              |侵入性弱                 |
|性能       |稍快于Injor，差距不明显                    |正常                    |正常                     |
|可扩展性   |抽象了自定义皮肤加载过程，内置多种换肤方式 |支持id映射/皮肤包       |仅支持apk皮肤包          |
|自定义属性 |支持                                       |支持                    |支持较弱                 |
|内存消耗   |比Injor高                                  |正常                    |比Injor高                |
```

整体来讲，QSkinLoader就是在AndroidSkinLoader的代理View创建思想上，解析View的皮肤相关属性，形成属性对象，存到View的id/tag内，在换肤时，遍历所有Activity的View树来实现换肤的过程。

##2. 框架架构与实现
###1. 框架架构
![QSkinLoader基本架构](http://img.blog.csdn.net/20161108154132875)
- **对外接口层**定义了框架对外支持的能力，如皮肤资源加载与切换、Activity页面换肤逻辑封装、特定View的管理、给View添加皮肤属性等。
- **皮肤资源加载模块**负责加载皮肤资源，对外接口提供抽象的资源加载过程，默认可以从指定的apk文件中加载，也可以根据指定的映射规则从当前应用的资源内寻找合适的资源（比如按根据后缀区分皮肤：color_text对应color_text_red）。
- **Activity交互模块**封装换肤过程中与Activity相关的所有逻辑，设计为接收所有Activity的声明周期回调即可。
- **全局View管理模块**，主要负责一些不在ActivityView树内的View的换肤工作，比如Dialog、popWindow、悬浮窗等View的管理与换肤。
- 换肤过程中需要处理View的很多种属性，比如textColor/background等，所以需要设计一个**属性处理器**。
- 框架最核心的价值在于减少集成的复杂性，减少代码侵入性，便于成熟的应用集成使用，而这就是**代理View创建与属性解析模块**应该做的事情。此模块才是此框架最核心的元素，其他模块都是为增强可用性而在此模块外层的各种封装。

###2. 最终实现
![QSkinLoader类设计](http://img.blog.csdn.net/20161108154206344)
**SkinManager是框架对外接口的封装，提供四个接口，分别对应框架的四种能力：**

1. **ISkinManager：**皮肤管理类，包括加载指定皮肤、恢复默认皮肤、注册属性处理器和对View应用皮肤设置的能力。
2. **IWindowManager：**负责添加/删除特定View到框架内维护，来确保换肤时此View能正常换肤，通常维护Dialog/popwindow/悬浮窗内持有的View。
3. **IActivitySkinEventHandler：**定义了换肤框架与Activity交互的所有逻辑，每个需要支持换肤的Activity都应持有此实例，并在生命周期回调中调用此实例相关接口。在Activity初始化时，IActivitySkinEventHandler负责处理拦截LayoutInflater的创建View过程，并解析皮肤相关属性；在换肤管理模块触发了换肤过程时，IActivitySkinEventHandler负责完成所在Activity的换肤工作。
4. **ISkinViewHelper：**基于链式编程设计。负责对指定View设置/添加一个或多个皮肤相关属性，或者删除所有的皮肤相关属性，同时也支持快捷的对View应用皮肤。