[ProjectGithubUrl]:     https://github.com/mjn1369/ScaleTouchListener
[PlatformBadge]:  https://img.shields.io/badge/Platform-Android-blue.svg
[ApiBadge]:       https://img.shields.io/badge/API-11%2B-blue.svg
[ProjectLicenceUrl]:    http://www.apache.org/licenses/LICENSE-2.0
[LicenseBadge]:   https://img.shields.io/badge/License-Apache_v2.0-blue.svg
[JitpackBadge]:   https://jitpack.io/v/mjn1369/ScaleTouchListener.svg
[JitpackUrl]:    https://jitpack.io/#mjn1369/ScaleTouchListener
[AndroidArsenalUrl]: https://android-arsenal.com/details/1/7134
[AndroidArsenalBadge]:   https://img.shields.io/badge/Android%20Arsenal-ScaleTouchListener-green.svg?style=flat
# ScaleTouchListener
[![Platform][PlatformBadge]][ProjectGithubUrl]
[![Api][ApiBadge]][ProjectGithubUrl]
[![License][LicenseBadge]][ProjectLicenceUrl]
[![JitpackBadge]][JitpackUrl]
[![AndroidArsenalBadge]][AndroidArsenalUrl]

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
                //OnClickListener
                Toast.makeText(mContext,"Heart",Toast.LENGTH_SHORT).show();
            }
        });
```
### Custom Settings
```java
//create custom config object
ScaleTouchListener.Config config = new ScaleTouchListener.Config(
                        300,    // Duration
                        0.75f,  // ScaleDown
                        0.75f); // Alpha
                        
fab_heart.setOnTouchListener(new ScaleTouchListener(config) {     // <--- pass config object
            @Override
            public void onClick(View v) {
                //OnClickListener
                Toast.makeText(MainActivity.this, "Heart", Toast.LENGTH_SHORT).show();
            }
        });
```
## Config Attributes
|            Attribute            |            Description            |            Default            |
 | ------------------------------- | -------------------------------   | --------------------------    |
 | ```Duration  (int)```|The whole shebang's duration|```100 (milliseconds)```|
 | ```ScaleDown (float)```|ScaleDown value between 0.0f and 1.0f|```0.9f```|
 | ```Alpha (float)```|Alpha degree value 0.0f and 1.0f|```0.4f```|
 
 cheers :beers:
 
## License
 ```
Copyright 2018 mjn1369

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 
```  
