package com.litongjava.jfinal.web.controller;

import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.MenuApi;
import com.jfinal.weixin.sdk.api.UserApi;
import com.jfinal.weixin.sdk.jfinal.ApiController;

public class WeixinApiController extends ApiController {
  public void index() {
    render("/api/index.html");
  }

  /**
   * 获取公众号菜单
   */
  public void getMenu() {
    ApiResult apiResult = MenuApi.getMenu();
    if (apiResult.isSucceed())
      renderText(apiResult.getJson());
    else
      renderText(apiResult.getErrorMsg());
  }

  /**
   * 获取公众号关注用户
   */
  public void getFollowers() {
    ApiResult apiResult = UserApi.getFollows();
    renderText(apiResult.getJson());
  }
}