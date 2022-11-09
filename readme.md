# obsidian 复习小程序

复习笔记的小程序

## 使用
修改 app.java 中的目录名和其他配置，  
方法 1：使用 ide 运行 app 类的 main 方法  
方法 2：下载之后使用 maven 打包之后，使用命令行运行

```shell
nohup java -jar target/review-jar-with-dependencies.jar> review.log &
```

## 详细描述

功能是在 obsidian 目录文件下随机挑选出 10 个 markdown 文件，
合并成一个文件，并转换成 html 文件。

写这个程序一方面是因为知道笔记是需要复习的，另一个方面也是受到了
Readwise 提醒邮件的启发，可以每天定时的复习一部分内容。

这个程序和 Readwise 有什么区别呢，Readwise 是邮件，有及时通知，

这个程序会定时运行，每三个小时生成一次 html。
但是还差一个及时通知，这个可以使用一些别的工具，比如邮件，bark，pushdeer，server 酱等等


