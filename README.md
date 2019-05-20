# Android-Kata
大多数app其实都是可以通过套路快速搭建的，其中无非分为联网与不联网，而联网可以延展分为列表、详情、提交表单。我曾经写了MVVM的框架，可是DataBinding至今都没普及，比较多的都是用MVP，但实际上MVP模式写起来也略为复杂，基于MVP和MVVM我重新定义了把P作为伪M，加上一些套路，总结了这个框架，我称它为Kata。

一年前就写好了一直没开源都是自己用，经过实践一个应用即使单干，使用Kata一个月之内不加班轻轻松松也能完成，速度快的话只需一周。

用到的其他库有：
BaseRecyclerViewAdapterHelper
ultra-ptr
rxjava
rxlifecycle

感谢他们

Demo如下：

[KataDemo-账房先生](https://github.com/saiwu-bigkoo/KataDemo-androidlite)

实战如下，一个月一个人在不加班情况下完成80%功能，后加入团队的小伙伴三分钟上手一个月零一周完成所有功能进入测试阶段，不到三个月妥妥的上线：
[广汽蔚来新能源汽车app-合创](https://a.app.qq.com/o/simple.jsp?pkgname=com.gac.nioapp)