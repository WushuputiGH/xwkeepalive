# xwkeepalive
安卓开发保活

安卓保活采用多种保活机制，主要解决以下问题：

- 应用在后台被系统限制运行
- 三星等机型无法接收离线推送
- 电池优化策略导致的后台服务终止

## 核心功能

1. 多机制保活：集成进程守护、白名单管理、系统限制绕过等技术
2. 推送保障：特别优化三星等厂商的离线推送接收问题
3. 电池优化忽略：突破系统电池限制策略

## 使用方法

### 1. 基础后台保活

```kotlin
KeepAliveManager(this).start() // 初始化基础保活服务
```

### 2. 开启忽略电池优化

【忽略电池优化，手动杀死应用，依旧可以进行后台运行】

```kotlin
BatteryOptimizationUtil.requestIgnoreBatteryOptimization(applicationContext)
```

### 3. 主项目需要添加的权限

【不管有没有用，都加上】

```kotlin
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="28" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.GET_TASKS" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
    <uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS"/>
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE_SPECIAL_USE"/>
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
```