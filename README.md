# 封装的OkHttp工具类

具体参考[鸿洋大神封装的okhttputils](https://github.com/hongyangAndroid/okhttputils)工具，做了一部分的修改

目前对应okhttp版本3.8.0

### 用法（暂时gradle方式不可用，要干项目，没时间研究什么问题导致的，可以直接使用jar引用）
--

- Android Studio

```
compile 'com.fausgoal:GLOkHttpUtils:1.0.2'

或者

在你的项目build.gradle中添加代码 maven { url 'https://jitpack.io' }，如：

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	compile 'com.github.waitforguo:OkHttpUtils:1.0.2'
```
- Eclipse
	
	下载最新jar:[GLOkHttpUtils-1\_0\_2.jar](GLOkHttpUtils-1_0_2.jar?raw=true)

	注：需要同时导入okhttp和okio的jar，下载见：[https://github.com/square/okhttp](https://github.com/square/okhttp).
	
## 目前对以下需求进行了封装

* 一般的get请求
* 一般的post请求
* 基于Http Post的文件上传（类似表单）
* 文件下载/加载图片
* 上传下载的进度回调
* 支持取消某个请求
* 支持自定义Callback
* 支持HEAD、DELETE、PATCH、PUT
* 支持session的保持
* 支持自签名网站https的访问，提供方法设置下证书就行

# 配置OkhttpClient

默认情况下，将直接使用okhttp默认的配置生成OkhttpClient，如果你有任何配置，记得在Application中调用`initClient`方法进行设置。

```java
public class GLApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initHttps();
    }

    private void initHttps() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                  .addInterceptor(new LoggerInterceptor(OkHttpUtils.TAG))
                  .connectTimeout(OkHttpUtils.TIME_OUT, TimeUnit.MILLISECONDS)
                  .readTimeout(OkHttpUtils.TIME_OUT, TimeUnit.MILLISECONDS)
                  //其他配置
                 .build();
        OkHttpUtils.initHttpClicnt(okHttpClient);
    }
}
```
别忘了在AndroidManifest中设置name="GLApp"。

## 对于Cookie(包含Session)

对于cookie一样，直接通过cookiejar方法配置，参考上面的配置过程。

```
CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
OkHttpClient okHttpClient = new OkHttpClient.Builder()
          .cookieJar(cookieJar)
          //其他配置
         .build();
                 
OkHttpUtils.initHttpClicnt(okHttpClient);
```
目前项目中包含：

* PersistentCookieStore //持久化cookie
* SerializableHttpCookie //持久化cookie
* MemoryCookieStore //cookie信息存在内存中

如果遇到问题，欢迎反馈，当然也可以自己实现CookieJar接口，编写cookie管理相关代码。

此外，对于持久化cookie还可以使用[https://github.com/franmontiel/PersistentCookieJar](https://github.com/franmontiel/PersistentCookieJar).

相当于框架中只是提供了几个实现类，你可以自行定制或者选择使用。

## 对于Log

初始化OkhttpClient时，通过设置拦截器实现，框架中提供了一个`LoggerInterceptor `，当然你可以自行实现一个Interceptor 。

```
 OkHttpClient okHttpClient = new OkHttpClient.Builder()
       .addInterceptor(new LoggerInterceptor("TAG"))
        //其他配置
        .build();
OkHttpUtils.initHttpClicnt(okHttpClient);
```


## 对于Https

依然是通过配置即可，框架中提供了一个类`HttpsUtils`

* 设置可访问所有的https网站

```
HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
         //其他配置
         .build();
OkHttpUtils.initHttpClicnt(okHttpClient);
```

* 设置具体的证书

```
HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(证书的inputstream, null, null);
OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager))
         //其他配置
         .build();
OkHttpUtils.initHttpClicnt(okHttpClient);
```

* 双向认证

```
HttpsUtils.getSslSocketFactory(
	证书的inputstream, 
	本地证书的inputstream, 
	本地证书的密码)
```

同样的，框架中只是提供了几个实现类，你可以自行实现`SSLSocketFactory`，传入sslSocketFactory即可。

##其他用法示例
参考[鸿洋大神封装的okhttputils](https://github.com/hongyangAndroid/okhttputils)工具

## 混淆

```
# GLOkHttpUtils
-dontwarn com.fausgoal.**
-keep class com.fausgoal.**{*;}

# okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

# okio
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-keep class okio.**{*;}

```