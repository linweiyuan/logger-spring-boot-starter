# Spring Boot Logger

## How to use

### 1. Preparation

`git clone https://github.com/linweiyuan/logger-spring-boot-starter.git`

`./gradlew publishToMavenLocal` (prefer maven for dependency management, but gradle for building)

### 2. Dependency

```xml
<dependency>
    <groupId>com.linweiyuan</groupId>
    <artifactId>logger-spring-boot-starter</artifactId>
    <version>0.1.0</version>
</dependency>
```

### 3. Exception logging

Add `@EnableExceptionLog` in your `Application` class

If you want to change the config dynamically without re-compile, add `allowOverride` to this annotation
like `@EnableExceptionLog(allowOverride = true)`, then all configurations in this annotation will be ignored, you must
add them in `application.properties` or `application.yml` if you want to customize the configuration

All configurations for exception logging

```properties
linweiyuan.logger.exception.border-color=#ffffff
linweiyuan.logger.exception.border-background-color=#ffffff
linweiyuan.logger.exception.border-blink=true
linweiyuan.logger.exception.text-color=#ffffff
linweiyuan.logger.exception.text-background-color=#ffffff
linweiyuan.logger.exception.text-bold=false
linweiyuan.logger.exception.text-italic=false
linweiyuan.logger.exception.text-blink=true
linweiyuan.logger.exception.empty-space-background-color=#ffffff
```

or

```yaml
linweiyuan:
  logger:
    exception:
      border-color: "#ffffff"
      border-background-color: "#ffffff"
      border-blink: true
      text-color: "#ffffff"
      text-background-color: "#ffffff"
      text-bold: false
      text-italic: false
      text-blink: true
      empty-space-background-color: "#ffffff"
```

Annotation will have two more properties, one is `allowOverride`, the other is `showAll`, if `true`, then the whole
stack trace will be printed, default is `false`, only the package name related exception will be printed

Full properties in annotation

```kotlin
@EnableExceptionLog(
    showAll = true,
    borderColor = "#ffffff",
    borderBackgroundColor = "#ffffff",
    borderBlink = true,
    textColor = "#ffffff",
    textBackgroundColor = "#ffffff",
    textBold = true,
    textItalic = true,
    textBlink = true,
    emptySpaceBackgroundColor = "#ffffff",
    allowOverride = true
)
```

### 4. Api logging

Api logging can only be configured via annotation

Full properties in annotation

```kotlin
@ApiLog(
    borderColor = "#ffffff",
    borderBackgroundColor = "#ffffff",
    borderBlink = true,
    textColor = "#ffffff",
    textBackgroundColor = "#ffffff",
    textBold = true,
    textItalic = true,
    textBlink = true,
    emptySpaceBackgroundColor = "#ffffff",
    useDefaultColor = true
)
```

If `useDefaultColor` is `true`, there will be some default mappings, default is `true` (only valid
with `ResponseEntity`)

| Response Code | Color  |
|---------------|--------|
| 2xx           | green  |
| 4xx           | yellow |
| 5xx           | red    |
