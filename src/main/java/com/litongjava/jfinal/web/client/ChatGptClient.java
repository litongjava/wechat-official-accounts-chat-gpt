package com.litongjava.jfinal.web.client;

import com.litongjava.jfinal.web.utils.PropKitUtils;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.util.Proxys;

import lombok.extern.slf4j.Slf4j;

import java.net.Proxy;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ChatGptClient {
  public static String keysString = PropKitUtils.get("chatgpt.keys");
  public static ChatGPT chatGPT = null;

  public static String ask(String user, String content) {
    if (chatGPT == null) {
      chatGPT = init();
    }
    log.info("user:{},content:{}", user, content);

    // Message system = Message.ofSystem(content);
    Message m1 = Message.of(content);
    // Message message = Message.of("写一段七言绝句诗，题目是：火锅！");

    ChatCompletion.ChatCompletionBuilder builder1 = ChatCompletion.builder();
    builder1.user(user);
    builder1.model(ChatCompletion.Model.GPT_3_5_TURBO.getName());
    // builder1.model("text-davinci-002-render-sha");
    // com.plexpt.chatgpt.exception.ChatException: The model `text-davinci-002-render-sha` does not exist
    builder1.messages(Arrays.asList(m1)).maxTokens(3000).temperature(0.9);
    ChatCompletion chatCompletion = builder1.build();

    ChatCompletionResponse response;
    try{
      response = chatGPT.chatCompletion(chatCompletion);
      Message res = response.getChoices().get(0).getMessage();
      return res.getContent();
    }catch (Exception e){
      return e.getMessage();
    }
  }

  private static ChatGPT init() {
    // 国内需要代理 国外不需要
    // Proxy proxy = Proxys.http("127.0.0.1", 1080);
    String[] split = keysString.split(",");
    List<String> asList = Arrays.asList(split);
    ChatGPT.ChatGPTBuilder builder = ChatGPT.builder();
    builder.apiKeyList(asList);
    configProxy(builder);

    builder.apiHost("https://api.openai.com/"); // 反向代理地址
    ChatGPT chatGPT = builder.build();
    chatGPT.init();
    return chatGPT;
  }

  private static void configProxy(ChatGPT.ChatGPTBuilder builder) {
    Boolean enable = PropKitUtils.getBoolean("chatgpt.proxy.enable");
    if (enable) {
      String type = PropKitUtils.get("chatgpt.proxy.type");
      Proxy proxy = null;
      if ("http".equals(type)) {
        // 国内需要代理
        String host = PropKitUtils.get("chatgpt.proxy.http.host");
        int port = PropKitUtils.getInt("chatgpt.proxy.http.port");
        proxy = Proxys.http(host, port);
      } else {
        String host = PropKitUtils.get("chatgpt.proxy.socks5.host");
        int port = PropKitUtils.getInt("chatgpt.proxy.socks5.port");
        // socks5 代理
        proxy = Proxys.socks5(host, port);
      }
      builder.proxy(proxy).timeout(900);
    }
  }

}
