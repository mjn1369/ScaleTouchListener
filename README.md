[ProjectGithubUrl]:     https://github.com/mjn1369/ScaleTouchListener
[PlatformBadge]:  https://img.shields.io/badge/Platform-Android-blue.svg
[ApiBadge]:       https://img.shields.io/badge/API-11%2B-blue.svg
[ProjectLicenceUrl]:    http://www.apache.org/licenses/LICENSE-2.0
[LicenseBadge]:   https://img.shields.io/badge/License-Apache_v2.0-blue.svg
[JitpackBadge]:   https://jitpack.io/v/mjn1369/ScaleTouchListener.svg
[JitpackUrl]:    https://jitpack.io/#mjn1369/ScaleTouchListener
# ScaleTouchListener
[![Platform][PlatformBadge]][ProjectGithubUrl]
[![Api][ApiBadge]][ProjectGithubUrl]
[![License][LicenseBadge]][ProjectLicenceUrl]
[![JitpackBadge]][JitpackUrl]

# ScaleTouchListener
```ScaleTouchListener``` is an Android library that makes your view to scale down (and also fade down) on touch press and scales it back up on touch being released. It also includes a click listener in case the touch releases inside the view (without leaving it).

<div align="center">
<img src="https://raw.githubusercontent.com/mjn1369/ScaleTouchListener/master/Screenshot/screenshot.gif"/>
</div>

## Download
### Gradle:
Add the following to your project level build.gradle:

```
allprojects {
   repositories {
      maven { url "https://jitpack.io" }
   }
}
```

Add this to your app build.gradle:

```
dependencies {
   compile 'com.github.mjn1369:ScaleTouchListener:1.0.0'
}
```

## Usage
### Default Settings
```java
fab_heart.setOnTouchListener(new ScaleTouchListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Heart",Toast.LENGTH_SHORT).show();
            }
        });
```
### Custom Settings
```
ScaleTouchListener.Config config = new ScaleTouchListener.Config(
                        300,    // Duration
                        0.75f,  // ScaleDown
                        0.75f); // Alpha
fab_heart.setOnTouchListener(new ScaleTouchListener(``` `**`config`**` ```) {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
```
