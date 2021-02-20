package com.llc.permission.core;

/**
 * com.llc.permission.core.IPermission
 *
 * @author liulongchao
 * @since 2021/2/20
 */
public interface IPermission {

    void ganted(); // 已经授权

    void cancel(); // 取消权限

    void denied(); // 拒绝权限
}
