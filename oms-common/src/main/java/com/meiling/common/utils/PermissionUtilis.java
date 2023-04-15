package com.meiling.common.utils;

import com.hjq.permissions.Permission;

public class PermissionUtilis {
    /**
     * 权限组
     */
    public static final class Group {

        /** 存储权限 */
        public static final String[] STORAGE = new String[] {
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE};

        /** 扫一扫 */
        public static final String[] RICHSCAN = new String[] {
                Permission.CAMERA,
                Permission.READ_MEDIA_IMAGES};

        /** 日历权限 */
        public static final String[] CALENDAR = new String[] {
                Permission.READ_CALENDAR,
                Permission.WRITE_CALENDAR};

        /** 联系人权限 */
        public static final String[] CONTACTS = new String[] {
                Permission.READ_CONTACTS,
                Permission.WRITE_CONTACTS,
                Permission.GET_ACCOUNTS};

        /** 蓝牙权限 */
        public static final String[] BLUETOOTH = new String[] {
                Permission.BLUETOOTH_SCAN,
                Permission.BLUETOOTH_CONNECT,
                Permission.BLUETOOTH_ADVERTISE};
    }
}