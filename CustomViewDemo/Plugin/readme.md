
360 田维术 Android插件化原理解析——概要
http://weishu.me/2016/01/28/understand-plugin-framework-overview/
project
https://github.com/tiann/understand-plugin-framework

Android插件化原理解析——Hook机制之动态代理
首先我们得找到被Hook的对象，我称之为Hook点；什么样的对象比较好Hook呢？自然是容易找到的对象。什么样的对象容易找到？静态变量和单例
寻找Hook点，原则是静态变量或者单例对象，尽量Hook pulic的对象和方法，非public不保证每个版本都一样，需要适配。