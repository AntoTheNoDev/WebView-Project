# Proyecto Android Studio Webview 17/5/2021 

## Permisos 
Estos son los permisos que se han utilizado 
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
        tools:ignore="ScopedStorage" />
```

## Cambiar Url Webview
Para cambiar el url del webview nos tendremos que dirigir al archivo MainActivity.java (app\src\main\java\com.webview.intur_colectividades.sc_DQBDID\MainActivity.java)
```java
private static String webview_url   = "com.example.webviewabril";
```

## Nombre de la app
El nombre de la app se puede modificar desde el archivo strings.xml (app\src\main\res\values\strings.xml)
```xml
<string name="app_name">WebViewAbril</string>
```

## Nombre del proyecto / Directorio root
Para cambiar el directorio root de nuestro proyecto tendremos que dirigirnos al archivo settings.gradle 
y cambiar la linea rootProject.name='NUEVO_NOMBRE'
```gradle
rootProject.name = "WebViewAbril"
```

## Paquete / Package de la app
El paquete lo podemos encontrar en el archivo AndroidManifest.xml (app\src\main\res\AndroidManifest.xml)
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.example.webviewabril"> 
```

Si se tiene un paquete nuevo habra que cambiar el applicationID en el archivo build.gradle(:app), se tendra que poner el 
nuevo paquete y sincronizar el gradle.
```gradle
defaultConfig {
        applicationId "com.example.webviewabril"
    }
```

## Version de la app
La version de la app la podemos encontrar en build.gradle nivel app (<project>/<app-module>/build.gradle)
```gradle
defaultConfig {
        versionName "1.0"
    }
```

## Escala de la app
setInitialScale() podemos cambiar la escala inicial de la app
```java
webSettings.setJavaScriptEnabled(true);
webView.setInitialScale(1);
webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
webSettings.setDomStorageEnabled(true);
```

## Iconos de la app
Los iconos los podemos encontrar en el modulo/carpeta res (app\src\main\res)
En esta carpeta podemos encontrar el icono que utilizamos para las notificaciones en la carpeta /drawable
ic_launcher-playstore.png seria el icono que estaria en la playstore

En las carpetas mipmap se pueden encontrar los iconos (son diferentes carpetas ya que estos iconos estan para diferentes resoluciones segun
el dispositivo en el que se este utilizando) 

Archivo AndroidManifest.xml
```xml
<!---icono de la app-->
<application
android:roundIcon="@mipmap/ic_launcher_round">
</application>
```

## Enlaces a otra app
Enlaces a jit.si ...
Este primer `shouldOverrideUrlLoading()`, sirve para que cuando se accede a la aplicacion se pueda 
ver perfectamente la app.
```java
/*
webView.setWebViewClient(new WebViewClient(){
    @Override
    public boolean shouldOverrideUrlLoading(WebView viewl, String url){
                    if (url.startsWith("http") || url.startsWith("https")){ //no complete action
                        return false;
                    }
    
                    if (url.startsWith("intent")) { //intent or https
                        try {
                            Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                            String fallbackUrl = intent.getStringExtra("browser_fallback_url");
                            if (fallbackUrl != null){
                                webView.loadUrl(fallbackUrl);
                                return true;
                            }}
                        catch (URISyntaxException e){
                        }
                    }
                    return true;
                }
*/
```
Este `shouldOverrideUrlLoading()` permite acceder a aplicaciones como jit.si
```java
/*
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){

          Uri uri = request.getUrl();
          if ("intent".equals(uri.getScheme())){
               Intent intent = null;
               try {
                    intent = Intent.parseUri(uri.toString(), Intent.URI_INTENT_SCHEME);
               } catch (URISyntaxException e) {
                    e.printStackTrace();
               }
               intent.addCategory("android.intent.category.BROWSABLE");
               intent.setComponent(null);
               startActivity(intent);
               return true;
               }
               return super.shouldOverrideUrlLoading(view, request);
            }
)};
*/
```

## Firebase / google-services.json
Para poder mandar notificaciones con nuestro panel de Firebase tendremos que agregar un fichero y unas dependecias.
El google-services.json lo tendremos que agregar en el modulo app (app\google-services.json)
Este contendra diferentes parametros que necesita Firebase para poder mandar notificaciones a los dispositivos que tengan la app

Tendremos que agregar las siguientes dependencias en los archivos gradle.
Archivo build.gradle nivel proyecto (<project>/build.gradle)
```gradle
buildscript {
    repositories {
        google() //chequear si tenemos esta linea
    }
    dependencies {
        //añadir esta linea
        classpath "com.google.gms:google-services:4.3.5"
    }
}

allprojects {
    repositories {
        google() //chequear si tenemos esta linea
    }
}
```
Archivo build.gradle nivel de app (<project>/<app-module>/build.gradle)
```gradle
    //añadir esta linea 
    apply plugin: 'com.google.gms.google-services'

    dependencies {
      // importar 
      implementation platform('com.google.firebase:firebase-bom:28.0.1')
    
      // añadir esta dependencia para el Firebase SDK para Google Analytics
      implementation 'com.google.firebase:firebase-analytics'

```
Una vez hecho este proceso le daremos a Sync.