package com.litongjava.jfinal.web.client;

import org.junit.Test;

public class ChatGptClientTest {

  @Test
  public void test() {
    String answer = ChatGptClient.ask("litong", "Java接入ChatGPT");
    System.out.println(answer);
  }

}
