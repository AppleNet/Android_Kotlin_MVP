package com.llc.permission.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.llc.permission.R;
import com.llc.permission.core.IPermission;
import com.llc.permission.utils.PermissionUtils;

/**
 * com.llc.permission.activity.PermissionActivity
 *
 * @author liulongchao
 * @since 2021/2/20
 */
public class PermissionActivity extends Activity {

    // 定义权限处理的标记， ---- 接收用户传递进来的
    private final static String PARAM_PERMISSION = "param_permission";
    private final static String PARAM_PERMISSION_CODE = "param_permission_code";
    public final static int PARAM_PERMISSION_CODE_DEFAULT = -1;

    // 方便回调的监听  告诉外交 已授权，被拒绝，被取消
    private static IPermission iPermissionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        // 真正接收存储的变量
        String[] permissions = getIntent().getStringArrayExtra(PARAM_PERMISSION);
        int requestCode = getIntent().getIntExtra(PARAM_PERMISSION_CODE, PARAM_PERMISSION_CODE_DEFAULT);

        if (permissions == null && requestCode < 0 && iPermissionListener == null) {
            this.finish();
            return;
        }

        // 能够走到这里，就开始去检查，是否已经授权了
        boolean permissionRequest = PermissionUtils.hasPermissionRequest(this, permissions);
        if (permissionRequest) {
            // 有权限
            iPermissionListener.ganted();
            this.finish();
            return;
        }

        // 没有权限 申请权限
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 返回的结果，需要去验证一下，是否完全成功
        if (PermissionUtils.requestPermissionSuccess(grantResults)) {
            iPermissionListener.ganted();
            this.finish();
            return;
        }

        // 没有成功，可能是用户 不听话
        // 如果用户点击了，拒绝（勾选了”不再提醒“） 等操作
        if (PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {
            // 用户拒绝，不再提醒
            iPermissionListener.denied();
            this.finish();
            return;
        }

        // 取消
        iPermissionListener.cancel();
        this.finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    // 此权限申请专用的Activity ，对外暴露， static
    public static void requestPermissionAction(Context context, String[] permissions,
                                               int requestCode, IPermission iPermissionListener) {
        PermissionActivity.iPermissionListener = iPermissionListener;
        Intent intent = new Intent(context, PermissionActivity.class);
        // 效果
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putInt(PARAM_PERMISSION_CODE, requestCode);
        bundle.putStringArray(PARAM_PERMISSION, permissions);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
