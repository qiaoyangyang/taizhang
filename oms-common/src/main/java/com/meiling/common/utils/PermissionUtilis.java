package com.meiling.common.utils;

import com.hjq.permissions.Permission;

public class PermissionUtilis {
    /**
     * 权限组
     */
    public static final class Group {

        /**
         * 存储权限
         */
        public static final String[] STORAGE = new String[]{
                Permission.READ_MEDIA_IMAGES,
                Permission.READ_MEDIA_VIDEO,
                Permission.READ_MEDIA_AUDIO
        };
        /**
         * 定位权限
         * Permission.ACCESS_COARSE_LOCATION,
         *  Manifest.permission.ACCESS_COARSE_LOCATION,
         *             Manifest.permission.ACCESS_FINE_LOCATION,
         *             Manifest.permission.WRITE_EXTERNAL_STORAGE,
         *             Manifest.permission.READ_EXTERNAL_STORAGE,
         *             Manifest.permission.READ_PHONE_STATE
         *
         *              Permission.READ_MEDIA_VIDEO,
         *                 Permission.READ_PHONE_STATE,
         *                 Permission.READ_MEDIA_AUDIO
         *                                 Permission.READ_MEDIA_IMAGES,
         */
        public static final String[] LOCAL = new String[]{
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION,
                Permission.READ_PHONE_STATE,
               };
        /**
         * 电话权限
         */
        public static final String[] PHONE_CALL = new String[]{
                Permission.CALL_PHONE};

        /**
         * 扫一扫
         */
        public static final String[] RICHSCAN = new String[]{
                Permission.CAMERA,
                Permission.READ_MEDIA_IMAGES};

        /**
         * 日历权限
         */
        public static final String[] CALENDAR = new String[]{
                Permission.READ_CALENDAR,
                Permission.WRITE_CALENDAR};

        /**
         * 联系人权限
         */
        public static final String[] CONTACTS = new String[]{
                Permission.READ_CONTACTS,
                Permission.WRITE_CONTACTS,
                Permission.GET_ACCOUNTS};

        /**
         * 蓝牙权限
         */
        public static final String[] BLUETOOTH = new String[]{
                Permission.BLUETOOTH_SCAN,
                Permission.BLUETOOTH_CONNECT,
                Permission.BLUETOOTH_ADVERTISE};
    }
}
