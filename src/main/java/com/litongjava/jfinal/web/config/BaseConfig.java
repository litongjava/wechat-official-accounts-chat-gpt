package com.litongjava.jfinal.web.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.template.Engine;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.litongjava.jfinal.web.utils.PropKitUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseConfig extends JFinalConfig {

  public void configConstant(Constants me) {
    me.setDevMode(PropKitUtils.getBoolean("undertow.devMode"));
    // 配置对超类中的属性进行注入
    me.setInjectSuperClass(true);

    // 开启解析 json 请求，5.0.0 版本新增功能
    me.setResolveJsonRequest(true);
  }

  public void configRoute(Routes me) {
    me.setMappingSuperClass(true);
    me.scan("com.litongjava.jfinal.web.");
  }

  public void configEngine(Engine me) {
  }

  public void configPlugin(Plugins me) {
  }

  public void configInterceptor(Interceptors me) {
  }

  public void configHandler(Handlers me) {
  }

  @Override
  public void onStart() {
    configWeiXin();
  }

  /**
   * 配置微信
   */
  private void configWeiXin() {
    String weixinAppId = PropKitUtils.get("weixin.app.id");
    log.info("weixinAppId:{}", weixinAppId);
    String weixinAppSecret = PropKitUtils.get("weixin.app.secret");
    String weixinAppToken = PropKitUtils.get("weixin.app.token");
    String weixnAppEncodingAesKey = PropKitUtils.get("weixin.app.encoding_aes_key");
    Boolean weixinAppEncryptMessage = PropKitUtils.getBoolean("weixin.app.encryptMessage");

    ApiConfig ac = new ApiConfig();
    // 配置微信 API 相关参数
    ac.setAppId(weixinAppId);
    ac.setAppSecret(weixinAppSecret);
    ac.setToken(weixinAppToken);

    /**
     *  是否对消息进行加密，对应于微信平台的消息加解密方式：
     *  1：true进行加密且必须配置 encodingAesKey
     *  2：false采用明文模式，同时也支持混合模式
     */
    ac.setEncryptMessage(weixinAppEncryptMessage);
    ac.setEncodingAesKey(weixnAppEncodingAesKey);

    /**
     * 多个公众号时，重复调用ApiConfigKit.putApiConfig(ac)依次添加即可，第一个添加的是默认。
     */
    ApiConfigKit.putApiConfig(ac);
    ApiConfigKit.setDevMode(true);

  }
}