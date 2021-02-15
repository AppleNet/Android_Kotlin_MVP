package com.example.llc.skin_lib;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.example.llc.skin_lib.utils.SkinResources;

import java.lang.reflect.Method;
import java.util.Observable;

/**
 * com.example.llc.skin_lib.SkinManager
 * @author liulongchao
 * @since 2020-09-23
 *
 *  皮肤管理者
 * */
public class SkinManager extends Observable {

    private final Application mContext;
    private static volatile SkinManager instance;

    private SkinManager(Application application) {
        mContext = application;
        SkinPreference.init(application);
        SkinResources.init(application);
        // 注册 Activity 生命周期，并设置被观察者
        SkinApplicationActivityLifeCycle skinApplicationActivityLifeCycle = new SkinApplicationActivityLifeCycle(this);
        application.registerActivityLifecycleCallbacks(skinApplicationActivityLifeCycle);
        // 加载上次使用保存的皮肤
        loadSkin(SkinPreference.getInstance().getSkin());
    }

    /**
     * 初始化必须放在 application 中
     * */
    public static void init(Application application) {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager(application);
                }
            }
        }
    }

    /**
     *  加载皮肤并应用
     *  @param skin skin 皮肤路径
     *
     *  对外暴露这个方法，可以添加设置页面，增加换肤按钮，点击事件中 调用此方法，传递皮肤包路径
     *
     * */
    public void loadSkin(String skin) {
        if (TextUtils.isEmpty(skin)) {
            // 还原默认皮肤
            SkinPreference.getInstance().reset();
            SkinResources.getInstance().reset();
        } else {
            // 宿主 app 的 resources
            Resources resources = mContext.getResources();
            // 反射创建 AssetManager 与 Resources
            try {
                AssetManager assetManager = AssetManager.class.newInstance();
                // 资源路径设置目录或者压缩包
                // TODO 同一个 key 的颜色值 就会被替换掉，所以未打开的Activity页面，在初次打开的时候，使用的就是新的 resources 进行颜色的渲染
                // TODO Resources进行getColor(R.color.xxx) 因为是同名的，这里已经替换成皮肤插件包中的值了，所以未打开过的Activity，执行onCreate方法中的setContentView
                // TODO 中的inflate的时候 读取到的颜色值 就是插件包中的
                Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.setAccessible(true);
                addAssetPath.invoke(assetManager, skin);

                // 根据当前的设备显示器信息与配置(横竖屏，语言等) 创建 Resources，
                Resources skinResources = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
                // 获取外部 apk(皮肤包) 包名
                PackageManager mPm = mContext.getPackageManager();
                PackageInfo info = mPm.getPackageArchiveInfo(skin, PackageManager.GET_ACTIVITIES);
                String packageName = info.packageName;
                // 这里就会进行一个赋值，如果皮肤插件包有值，就会给SkinResources进行赋值
                SkinResources.getInstance().applySkin(skinResources, packageName);

                // 记录
                SkinPreference.getInstance().setSkin(skin);
            } catch (Exception e) {
                //
            }
        }

        // 通知采集的 view 更新皮肤
        // 被观察者改变，通知所有的观察者
        setChanged();
        notifyObservers(null);
    }

    public static SkinManager getInstance() {
        return  instance;
    }
}
