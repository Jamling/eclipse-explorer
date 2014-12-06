## 介绍 ##
Eclipse Explorer 是运行于eclipse中, 浏览所选资源所在目录的辅助插件,如在windows资源管理器中打开Java文件,所支持的资源包括Java元素如 .java, .class, jar file, package, source folder and java project
普通资源如.txt, .xml,及其它一些特殊资源, 如同步视图中的CVS元素.

注: 本插件依赖于JDT插件. 因此不能被安装于非Java语言的eclipse.

本插件的开发环境为: eclipse3.5 + jdk1.5,完全测试环境: window 32bit+eclipse3.5+jdk1.5, linux64bits+eclipse3.6+jdk1.6

## 安装 ##
### 应用市场 ###
  * Help->Marketplace
  * 输入关键字eclipse explorer进行搜索
  * 点击Install按钮进行安装
详情请点击
[https://marketplace.eclipse.org/content/eclipse-explorer](https://marketplace.eclipse.org/content/eclipse-explorer)

### 本地档案 ###
  * 下载org.melord.pde.explorer.site.zip包
  * eclipse->Help->Install new software...->Local archive选择下载的档案
  * 重启eclipse (File->Restart)以使插件生效
  
## 配置 ##
### 命令 ###
浏览命令: 本插件会自动根据操作系统自动配置浏览命令.当然用户也可以在 Window->Preference->General->Explorer中更改或重新配置. 

### 快捷键 ###
快捷键: 默认的快捷键为"Ctrl + `", 当然您也可以在Window->Preference->General->Keys自己设置.

## 教程 ##
  - 选择一个可以浏览的资源或元素
  - 右键菜单中选择"Open in Explorer" 或按下设置的快捷键.
