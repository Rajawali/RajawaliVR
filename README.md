## Rajawali + Virtual Reality

## ATTENTION

As of 03/17/2016, this template is fully integrated to the examples application which has been moved to the main Rajawali repository: https://github.com/Rajawali/Rajawali. This repository will remain for historical reference but **it is effectively abandoned and no responses from the maintainers should be expected.**

==============================================================

This project integrates the [Rajawali 3D framework](https://github.com/MasDennis/Rajawali) with the [Google Cardboard SDK v0.6.0](https://developers.google.com/cardboard/).

[https://www.youtube.com/watch?v=UIRTKSEnRF0&feature=youtu.be](https://www.youtube.com/watch?v=UIRTKSEnRF0&feature=youtu.be)

## Virtual Reality Headset

You'll need a virtual reality headset for smartphones:
- [Get your Cardboard](hhttps://www.google.com/get/cardboard/get-cardboard/)

#How To Run The Example

You need a local build of the latest RajawaliVR Framework. Currently RajawaliVR is not available on maven so a local build must be created. To build a local release of RajawaliVR simply perform a checkout of the library then run the gradle tasks ```clean assembleRelease uploadArchives```.

## Linux
```
git clone https://github.com/Rajawali/RajawaliVR.git
./RajawaliVR/RajawaliVR/gradlew clean assembleRelease uploadArchives
```

## Windows
```
git clone https://github.com/Rajawali/RajawaliVR.git
./RajawaliVR/RajawaliVR/gradlew.bat clean assembleRelease uploadArchives
```

