package io.starter.config;

import org.aeonbits.owner.Config;

public interface AppConfiguration extends Config {

  @Key("BASE_TELEGRAM_URL")
  @DefaultValue("https://web.telegram.org")
  String baseTelegramUrl();

}
