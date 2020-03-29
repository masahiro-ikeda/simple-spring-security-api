package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

  /**
   * テスト用のRestController
   *
   * @return
   */
  @GetMapping("sample")
  public String selectFacility() {
    return "Success";
  }

  @GetMapping("/")
  public void healthCheck() {
  }
}
