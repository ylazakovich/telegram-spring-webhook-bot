# PATH OF EXILE STARTER

![Build Status](https://github.com/ylazakovich/path-of-exile-starter/actions/workflows/test.yml/badge.svg)

## Overview

- [What the service can do?](#what-is-about-service)
- [Status](.github/docs/ROADMAP.md#status)
    - [Telegram](.github/docs/ROADMAP.md#telegram)
    - [Aggregator](.github/docs/ROADMAP.md#aggregator)
- [Quick start](#quick-start)
- [FAQ](#faq)

## What is about Service

1) Easy start for trading in Path of Exile
2) Service is able to provide you positions for trade
3) You are able to manage it over Telegram

## Quick start

![preview](https://github.com/ylazakovich/path-of-exile-starter/blob/main/.github/docs/demo.jpg)

## FAQ

#### Q: Does Telegram bot work over Webhook ?

Yes, bot uses webhooks implementation

#### Q: Which resources do you use for checking actual prices ?

Currently, bot check prices over https://poe.ninja/

#### Q: How to up bot locally ?

So, for that purpose I would recommend you to use ngrok \
Then copy https links from console into `application.properties`
- webhook - links from ngrok
- token - botfather provide this token

Then

**Option 1**
1) run `docker-compose.yml`
2) start application from aggregator module
3) start application from telegram module

**Option 2** (not ready yet)

Run over console `run_app.sh`

Then \
Bot is ready for working

#### Q: Should I run api call for assigning webhook ?

No, you don't need to do it \
Bot has controller which assign webhook to bot after starting application

#### Q: Which database you have decided to use ?

I decided to use `mariadb` \
Also application use migrations flow over flyway mechanism

#### Q: Are you planning to make a documentation ?

It is possible, but at the moment I have no a lot of free time \
I haven't finished yet `1.0.0` version

#### Q: How to understand business of your feature ?

You can keep an eye on the status of test for every business feature \
Here: [Status](.github/docs/ROADMAP.md#status)

As each test is completed \
I will update doc and stay ref for this feature
