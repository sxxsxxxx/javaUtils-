# javaUtils-
工作中实用的Java工具类整理
- config
    - 多数据源的配置及动态切换数据库的切面配置（还未完善好，配置文件暂上传）
- database
    - JedisUtil（redis连接操作实现）
- encrypt
    - RsaUtils（rsa加密Java实现）
- http
- http.async
    - HttpAsyncClient(httpasyncclient4.1.3封装)
- http.factory
    - HttpClientFactory(同步异步httpclient连接池工厂)
- http.sync
    - HttpSyncClient（httpclient4.5.x封装）
    - HttpSSLConnectionSocketFactory（https请求实现）
    - HttpSSLConnectionSocketFactoryV2（https协议增加）
    - MyHttpEntity（响应封装）
- poi
    - ExcelUtil（Excel文件读取）
    - PDFBoxUtil（PDF文件读取）
- system
    - PropertyManager（配置读取）
- wechat
    - WechatMonitor（企业微信发送消息api实现）
- file
    - DownloadWithRange 定义一个单个线程下载的部分
    - DownloadFileWithThreadPool 定义线程Pool
    - FilesLifeCycle 定时删除文件，包括遍历文件子目录
    - GetFileSize 得到文件的大小
- json
    - jsonUtils转换
    - JSON data util, for parse and generate JSON data    
    
    # ----------------------------------------
# spring boot核心属性大全
# ----------------------------------------

# 文件编码
banner.charset= UTF-8
# 文件位置
banner.location= classpath:banner.txt


# 日志配置
# 日志配置文件的位置。 例如对于Logback的`classpath：logback.xml`
logging.config= 
# ％wEx#记录异常时使用的转换字。
logging.exception-conversion-word= 
# 日志文件名。 例如`myapp.log`
logging.file= 
# 日志级别严重性映射。 例如`logging.level.org.springframework =  DEBUG`
logging.level.*= 
# 日志文件的位置。 例如`/ var / log`
logging.path= 
# 用于输出到控制台的Appender模式。 只支持默认的logback设置。
logging.pattern.console= 
# 用于输出到文件的Appender模式。 只支持默认的logback设置。
logging.pattern.file= 
# 日志级别的Appender模式（默认％5p）。 只支持默认的logback设置。
logging.pattern.level= 
#注册日志记录系统的初始化挂钩。
logging.register-shutdown-hook= false


# AOP 切面
# 添加@EnableAspectJAutoProxy。
spring.aop.auto= true
# 是否要创建基于子类（CGLIB）的代理（true），而不是基于标准的基于Java接口的代理（false）。
spring.aop.proxy-target-class= false


# 应用程序上下文初始化器
# 应用指标。
spring.application.index= 
# 应用程序名称。
spring.application.name= 


# 国际化（消息源自动配置）
#
spring.messages.basename= messages
# 以逗号分隔的基础名称列表，每个都在ResourceBundle约定之后。
# 加载的资源束文件缓存到期，以秒为单位。 设置为-1时，软件包将永久缓存。
spring.messages.cache-seconds= -1
# 消息编码。
spring.messages.encoding= UTF-8
# 设置是否返回到系统区域设置，如果没有找到特定语言环境的文件。
spring.messages.fallback-to-system-locale= true


# REDIS (Redis 配置)
# 连接工厂使用的数据库索引。
spring.redis.database= 0
# Redis服务器主机。
spring.redis.host= localhost
# 登录redis服务器的密码。
spring.redis.password= 
# 给定时间池可以分配的最大连接数。 使用负值为无限制。
spring.redis.pool.max-active= 8
# 池中“空闲”连接的最大数量。 使用负值来表示无限数量的空闲连接。
spring.redis.pool.max-idle= 8
# 连接分配在池耗尽之前在抛出异常之前应阻止的最大时间量（以毫秒为单位）。 使用负值无限期地阻止。
spring.redis.pool.max-wait= -1
# 定义池中维护的最小空闲连接数。 此设置只有在正值时才有效果。
spring.redis.pool.min-idle= 0
# redis服务器端口
spring.redis.port= 6379
# redis服务器名称
spring.redis.sentinel.master=
# 
spring.redis.sentinel.nodes= 
# 连接超时（毫秒）。
spring.redis.timeout= 0


# 管理员 （Spring应用程序管理员JMX自动配置）
# 开启应用管理功能。
spring.application.admin.enabled= false
# JMX应用程序名称MBean。
spring.application.admin.jmx-name= org.springframework.boot:type= Admin,name= SpringApplication


# 自动配置
# 自动配置类排除。
spring.autoconfigure.exclude= 


# spring 核心配置
# 跳过搜索BeanInfo类。
spring.beaninfo.ignore= true


# spring 缓存配置
# 由底层缓存管理器支持的要创建的缓存名称的逗号分隔列表。
spring.cache.cache-names= 
# 用于初始化EhCache的配置文件的位置。
spring.cache.ehcache.config= 
# 用于创建缓存的规范。 检查CacheBuilderSpec有关规格格式的更多细节。
spring.cache.guava.spec= 
# 用于初始化Hazelcast的配置文件的位置。
spring.cache.hazelcast.config= 
# 用于初始化Infinispan的配置文件的位置。
spring.cache.infinispan.config= 
# 用于初始化缓存管理器的配置文件的位置。
spring.cache.jcache.config= 
# 用于检索符合JSR-107的缓存管理器的CachingProvider实现的完全限定名称。 只有在类路径上有多个JSR-107实现可用时才需要。
spring.cache.jcache.provider= 
# 缓存类型，默认情况下根据环境自动检测。
spring.cache.type= 


# spring配置 （配置文件应用侦听器）
# 配置文件位置。
spring.config.location= 
# 配置文件名。
spring.config.name= application


# hazelcast配置(Hazelcast是一个高度可扩展的数据分发和集群平台，提供了高效的、可扩展的分布式数据存储、数据缓存.)
# 用于初始化Hazelcast的配置文件的位置。
spring.hazelcast.config= 


# JMX
# JMX域名。
spring.jmx.default-domain= 
# 将管理bean暴露给JMX域。
spring.jmx.enabled= true
# MBean服务器bean名称。
spring.jmx.server= mbeanServer


# Email (MailProperties)  邮件属性
# 默认MimeMessage编码。
spring.mail.default-encoding= UTF-8
# SMTP服务器主机。 例如`smtp.example.com`
spring.mail.host= 
# 会话JNDI名称。 设置时，优先于其他邮件设置。
spring.mail.jndi-name= 
# 登录SMTP服务器的密码。
spring.mail.password= 
# SMTP服务器端口。
spring.mail.port= 
# 其他JavaMail会话属性。
spring.mail.properties.*= 
# SMTP服务器使用的协议。
spring.mail.protocol= smtp
# 测试邮件服务器在启动时可用。
spring.mail.test-connection= false
# 登录SMTP服务器的用户。
spring.mail.username= 


# 应用设置（spring应用）
# 用于在应用程序运行时显示横幅的模式。
spring.main.banner-mode= console
# 源（类名，包名或XML资源位置）包含在ApplicationContext中。
spring.main.sources= 
# 在Web环境中运行应用程序（默认情况下自动检测）。
spring.main.web-environment= 


# 文件编码（文件编码应用程序侦听器）
# 应用程序使用的预期字符编码。
spring.mandatory-file-encoding= 


# 输出
# 配置ANSI输出（可以是“detect”，“always”，“never”）-->“检测”，“永远”，“从不”
spring.output.ansi.enabled= detect


# PID文件（应用程序文件写入器）
# 如果使用ApplicationPidFileWriter但是无法写入PID文件，则失败。
spring.pid.fail-on-write-error= 
# 要写入的PID文件的位置（如果使用ApplicationPidFileWriter）。
spring.pid.file= 


#   简介（profiles 这个单词翻译过来就是这样... 没用过这个属性，有哪位大神用过请留言我改正，感谢。）
# 活动配置文件的逗号分隔列表。
spring.profiles.active= 
# 无条件地激活指定的逗号分隔的配置文件。
spring.profiles.include= 


# SendGrid（SendGrid自动配置）
# SendGrid帐号用户名
spring.sendgrid.username= 
# SendGrid帐号密码
spring.sendgrid.password= 
# SendGrid代理主机
spring.sendgrid.proxy.host= 
# SendGrid代理端口
spring.sendgrid.proxy.port= 


# ----------------------------------------
#   WEB属性
# ----------------------------------------


# 文件上传属性
# 启用对文件上传的支持。
multipart.enabled= true
# 将文件写入磁盘后的阈值。 值可以使用后缀“MB”或“KB”表示兆字节或千字节大小。
multipart.file-size-threshold= 0
# 上传文件的位置。
multipart.location= 
# 最大文件大小。 值可以使用后缀“MB”或“KB”表示兆字节或千字节大小。
multipart.max-file-size= 1Mb
# 最大请求大小。 值可以使用后缀“MB”或“KB”表示兆字节或千字节大小。
multipart.max-request-size= 10Mb


# 嵌入式服务器配置（服务器属性）
# 服务器应绑定到的网络地址。
server.address= 
# 如果启用响应压缩。
server.compression.enabled= false
# 从压缩中排除的用户代理列表。
server.compression.excluded-user-agents= 
# 应该压缩的MIME类型的逗号分隔列表。 例如`text / html，text / css，application / json`
server.compression.mime-types= 
# 执行压缩所需的最小响应大小。 例如2048
server.compression.min-response-size= 
# Servlet上下文初始化参数。 例如`server.context-parameters.a =  alpha`
server.context-parameters.*= 
# 应用程序的上下文路径。
server.context-path= 
# 显示应用程序的名称。
server.display-name= application
# 何时包含“stacktrace”属性。
server.error.include-stacktrace= never
# 错误控制器的路径。
server.error.path= /error
# 启动浏览器中出现服务器错误时显示的默认错误页面。
server.error.whitelabel.enabled= true
# JSP servlet的类名。
server.jsp-servlet.class-name= org.apache.jasper.servlet.JspServlet
# Init参数用于配置JSP servlet
server.jsp-servlet.init-parameters.*= 
# JSP servlet是否被注册
server.jsp-servlet.registered= true
# 服务器HTTP端口。
server.port= 8080
# 主调度程序servlet的路径。
server.servlet-path= /
# 会话cookie的注释。
server.session.cookie.comment= 
# 会话cookie的域。
server.session.cookie.domain= 
# “HttpOnly”标志为会话cookie。
server.session.cookie.http-only= 
# 会话cookie的最大时长（以秒为单位）。
server.session.cookie.max-age= 
# 会话cookie名称。
server.session.cookie.name= 
# 会话cookie的路径。
server.session.cookie.path= 
# 会话cookie的“安全”标志。
server.session.cookie.secure= 
# 重启之间持续会话数据。
server.session.persistent= false
# 用于存储会话数据的目录。
server.session.store-dir= 
# 会话超时（秒）。
server.session.timeout= 
# 会话跟踪模式（以下一个或多个：“cookie”，“url”，“ssl”）。
server.session.tracking-modes= 
# 支持SSL密码。
server.ssl.ciphers= 
# 客户端认证是否需要（“want”）或需要（“need”）。 需要信任存储。
server.ssl.client-auth= 
# ssl配置
server.ssl.enabled= 
server.ssl.key-alias= 
server.ssl.key-password= 
server.ssl.key-store= 
server.ssl.key-store-password= 
server.ssl.key-store-provider= 
server.ssl.key-store-type= 
server.ssl.protocol= 
server.ssl.trust-store= 
server.ssl.trust-store-password= 
server.ssl.trust-store-provider= 
server.ssl.trust-store-type= 
# 创建日志文件的目录。 可以相对于tomcat base dir或absolute。
server.tomcat.accesslog.directory= 
# 启用访问日志。
server.tomcat.accesslog.enabled= false
# 访问日志的格式化模式。
server.tomcat.accesslog.pattern= common
# 日志文件名前缀。
server.tomcat.accesslog.prefix= access_log
# 日志文件名后缀。
server.tomcat.accesslog.suffix= .log
# 在调用backgroundProcess方法之间延迟秒。
server.tomcat.background-processor-delay= 30
# Tomcat基本目录。 如果未指定，将使用临时目录。
server.tomcat.basedir= 
# 正则表达式匹配可信IP地址。
server.tomcat.internal-proxies= 10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|\\192\\.168\\.\\d{1,3}\\.\\d{1,3}|\\169\\.254\\.\\d{1,3}\\.\\d{1,3}|\\127\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|\\172\\.1[6-9]{1}\\.\\d{1,3}\\.\\d{1,3}|\\172\\.2[0-9]{1}\\.\\d{1,3}\\.\\d{1,3}|\\172\\.3[0-1]{1}\\.\\d{1,3}\\.\\d{1,3}
# HTTP消息头的最大大小（以字节为单位）。
server.tomcat.max-http-header-size= 0
# 最大工作线程数。
server.tomcat.max-threads= 0
# 用于覆盖原始端口值的HTTP头的名称。
server.tomcat.port-header= X-Forwarded-Port
# 头文件，保存传入协议，通常命名为“X-Forwarded-Proto”。
server.tomcat.protocol-header= 
# 表示传入请求使用SSL的协议头的值。
server.tomcat.protocol-header-https-value= https
# 提取远程ip的HTTP头的名称。 例如`X-FORWARDED-FOR`
server.tomcat.remote-ip-header= 
# 用于解码URI的字符编码。
server.tomcat.uri-encoding= UTF-8
# 访问日志目录。
server.undertow.accesslog.dir= 
# 启用访问日志。
server.undertow.accesslog.enabled= false
# 访问日志的格式化模式。
server.undertow.accesslog.pattern= common
# 每个缓冲区的大小字节数。
server.undertow.buffer-size= 
# 每个区域的缓冲区数。
server.undertow.buffers-per-region= 
# 在Java堆之外分配缓冲区。
server.undertow.direct-buffers= 
# 为工作者创建的I / O线程数。
server.undertow.io-threads= 
# 工作线程数。
server.undertow.worker-threads= 
# 如果X-Forwarded- *头应该应用于HttpRequest。
server.use-forward-headers= 


# 自由标记（自由标记自动配置）
# 设置是否允许HttpServletRequest属性重写（隐藏）控制器生成的同名模型属性。
spring.freemarker.allow-request-override= false
# 设置是否允许HttpSession属性重写（隐藏）控制器生成的相同名称的模型属性。
spring.freemarker.allow-session-override= false
# 启用模板缓存。
spring.freemarker.cache= false
# 模板编码。
spring.freemarker.charset= UTF-8
# 检查模板位置是否存在。
spring.freemarker.check-template-location= true
# Content-Type值。
spring.freemarker.content-type= text/html
# 启用此技术的MVC视图分辨率。
spring.freemarker.enabled= true
# 设置在与模板合并之前是否应将所有请求属性添加到模型中。
spring.freemarker.expose-request-attributes= false
# 设置在与模板合并之前是否应将所有HttpSession属性添加到模型中。
spring.freemarker.expose-session-attributes= false
# 设置是否公开一个RequestContext供Spring 的宏库使用，名称为“springMacroRequestContext”。
spring.freemarker.expose-spring-macro-helpers= true
# 首选文件系统访问模板加载。 文件系统访问可以对模板更改进行热检测。
spring.freemarker.prefer-file-system-access= true
# 前缀，在构建URL时先查看名称。
spring.freemarker.prefix= 
# 所有视图的RequestContext属性的名称。
spring.freemarker.request-context-attribute= 
# 公开的FreeMarker密钥将被传递给FreeMarker的配置。
spring.freemarker.settings.*= 
# 后缀，在构建URL时附加到查看名称。
spring.freemarker.suffix= 
# 逗号分隔的模板路径列表。
spring.freemarker.template-loader-path= classpath:/templates/
# 可以解决的视图名称的白名单。
spring.freemarker.view-names= 


# groovr模板（Groovy模板自动配置）
# 设置是否允许HttpServletRequest属性重写（隐藏）控制器生成的同名模型属性。
spring.groovy.template.allow-request-override= false
# 设置是否允许HttpSession属性重写（隐藏）控制器生成的相同名称的模型属性。
spring.groovy.template.allow-session-override= false
# 启用模板缓存。
spring.groovy.template.cache= 
# 模板编码。
spring.groovy.template.charset= UTF-8
# 检查模板位置是否存在。
spring.groovy.template.check-template-location= true
# 请参阅GroovyMarkupConfigurer
spring.groovy.template.configuration.*= 
# Content-Type值。
spring.groovy.template.content-type= test/html
# 启用此技术的MVC视图分辨率。
spring.groovy.template.enabled= true
# 设置在与模板合并之前是否应将所有请求属性添加到模型中。
spring.groovy.template.expose-request-attributes= false
# 设置在与模板合并之前是否应将所有HttpSession属性添加到模型中。
spring.groovy.template.expose-session-attributes= false
# 设置是否公开一个RequestContext供Spring Spring的宏库使用，名称为“springMacroRequestContext”。
spring.groovy.template.expose-spring-macro-helpers= true
# 前缀，在构建URL时先查看名称。
spring.groovy.template.prefix= 
# 所有视图的RequestContext属性的名称。
spring.groovy.template.request-context-attribute= 
# 模板路径。
spring.groovy.template.resource-loader-path= classpath:/templates/
# 后缀，在构建URL时附加到查看名称。
spring.groovy.template.suffix= .tpl
# 可以解决的视图名称的白名单。
spring.groovy.template.view-names= 


# spring Hateoas 配置
# 指定应用程序/ hal + json响应是否应发送到接受application / json的请求。
spring.hateoas.use-hal-as-default-json-media-type= true


# HTTP 消息转换
# 首选JSON映射程序用于HTTP消息转换。 设置为“gson”强制使用Gson，当它和Jackson都在类路径上时。
spring.http.converters.preferred-json-mapper= jackson


# HTTP 编码（Http编码属性）
# HTTP请求和响应的字符集。 如果未明确设置，则添加到“Content-Type”头。
spring.http.encoding.charset= UTF-8
# 启用http编码支持。
spring.http.encoding.enabled= true
# 将编码强制到HTTP请求和响应上配置的字符集。
spring.http.encoding.force= true


# Jackson(解析json和序列化json) 配置
# 日期格式字符串或全限定日期格式类名。 例如`yyyy-MM-dd HH：mm：ss`。
spring.jackson.date-format= 
# Jones开/关功能，影响Java对象反序列化的方式。
spring.jackson.deserialization.*= 
# 关闭或者打开Jackson 功能
spring.jackson.generator.*= 
# Joda日期时间格式字符串。 如果未配置，如果配置了格式字符串，则“日期格式”将用作后备。
spring.jackson.joda-date-time-format= 
# 用于格式化的区域设置。
spring.jackson.locale= 
# jackson通用开/关功能。
spring.jackson.mapper.*= 
# Jackson 解析器的开/关功能。
spring.jackson.parser.*= 
# Jackson的PropertyNamingStrategy的一个常量。 也可以是PropertyNamingStrategy子类的完全限定类名。
spring.jackson.property-naming-strategy= 
# Jones开/关功能，影响Java对象序列化的方式。
spring.jackson.serialization.*= 
# 控制在序列化期间包含属性。 配置了Jackson的JsonInclude.Include枚举中的一个值。
spring.jackson.serialization-inclusion= 
# 格式化日期时使用的时区。 例如`America / Los_Angeles`
spring.jackson.time-zone= 


# Jersey 配置
# 作为应用程序的基本URI的路径。 如果指定，则覆盖“@ApplicationPath”的值。
spring.jersey.application-path= 
# jersey过滤器链顺序。
spring.jersey.filter.order= 0
#  init参数传递到Jersey通过servlet或过滤器。
spring.jersey.init.*= 
# jersey整合型。可以是“servlet”也可以是“filter”。
spring.jersey.type= servlet


# spring 视图分解器 配置
# 启用后退解析支持。
spring.mobile.devicedelegatingviewresolver.enable-fallback= false
# 启用设备视图解析器。
spring.mobile.devicedelegatingviewresolver.enabled= false
# 前缀，用于查看移动设备的名称。
spring.mobile.devicedelegatingviewresolver.mobile-prefix= mobile/
# 后缀，附加到查看移动设备的名称。
spring.mobile.devicedelegatingviewresolver.mobile-suffix= 
# 前缀，用于查看普通设备的名称。
spring.mobile.devicedelegatingviewresolver.normal-prefix= 
# 后缀，附加到查看普通设备的名称。
spring.mobile.devicedelegatingviewresolver.normal-suffix= 
# 前缀，用于查看平板设备的名称。
spring.mobile.devicedelegatingviewresolver.tablet-prefix= tablet/
# 后缀，附加到查看平板电脑设备的名称。
spring.mobile.devicedelegatingviewresolver.tablet-suffix= 


# 移动网站首选项 （站点首选项自动配置）
# 启用SitePreferenceHandler。
spring.mobile.sitepreference.enabled= true


# MUSTACHE模板（Mustache AutoConfiguration）
# 启用模板缓存。
spring.mustache.cache= false
# 模板编码。
spring.mustache.charset= UTF-8
# 检查模板位置是否存在。
spring.mustache.check-template-location= true
# Content-Type值
spring.mustache.content-type= text/html
# 启用此技术的MVC视图分辨率。
spring.mustache.enabled= true
# 前缀应用于模板名称。
spring.mustache.prefix= classpath:/templates/
# 后缀应用于模板名称。
spring.mustache.suffix= .html
# 可以解决的视图名称的白名单。
spring.mustache.view-names= 


# SPRING MVC (Web Mvc 配置)
# 异步请求处理超时之前的时间量（以毫秒为单位）。
spring.mvc.async.request-timeout= 
# 要使用的日期格式。 例如`dd / MM / yyyy`。
spring.mvc.date-format= 
# 发送TRACE请求到FrameworkServlet doService方法。
spring.mvc.dispatch-trace-request= false
# 发送OPTIONS请求到FrameworkServlet doService方法。
spring.mvc.dispatch-options-request= false
# 启用favicon.ico的解析。
spring.mvc.favicon.enabled= true
# 如果在重定向方案期间应该忽略“默认”模型的内容。
spring.mvc.ignore-default-model-on-redirect= true
# 要使用的区域设置。
spring.mvc.locale= 
# 将文件扩展名映射到内容协商的媒体类型。
spring.mvc.media-types.*= 
# 消息代码格式策略。 例如`PREFIX_ERROR_CODE`。
spring.mvc.message-codes-resolver-format= 
# 用于静态资源的路径模式。
spring.mvc.static-path-pattern= /**
# 如果没有发现处理程序来处理请求，则应抛出“NoHandlerFoundException”。
spring.mvc.throw-exception-if-no-handler-found= false
# Spring MVC视图前缀。
spring.mvc.view.prefix= 
# Spring MVC视图后缀。
spring.mvc.view.suffix= 


 #SPRING RESOURCES HANDLING（ResourceProperties）资源处理
spring.resources.add-mappings = true #启用默认资源处理。
spring.resources.cache-period = #由资源处理程序提供的资源的缓存期，以秒为单位。
spring.resources.chain.cache = true #在资源链中启用缓存。
spring.resources.chain.enabled = #启用Spring资源处理链。默认情况下禁用，除非启用了至少一个策略。
spring.resources.chain.html-application-cache = false #启用HTML5应用程序缓存清单重写。
spring.resources.chain.strategy.content.enabled = false #启用内容版本策略。
spring.resources.chain.strategy.content.paths = / ** #应用于版本策略的模式的逗号分隔列表。
spring.resources.chain.strategy.fixed.enabled = false #启用固定版本策略。
spring.resources.chain.strategy.fixed.paths = #应用于版本策略的逗号分隔的模式列表。
spring.resources.chain.strategy.fixed.version = #用于版本策略的版本字符串。
spring.resources.static-locations = classpath：/ META-INF / resources /，classpath：/ resources /，classpath：/ static /，classpath：/ public / #静态资源的位置。


 #SPRING SOCIAL（SocialWebAutoConfiguration）集群
spring.social.auto-connection-views = false #启用支持的提供程序的连接状态视图。


 #SPRING SOCIAL FACEBOOK（FacebookAutoConfiguration）
spring.social.facebook.app-id = #您的应用程序的Facebook应用程序ID
spring.social.facebook.app-secret = #你的应用程序的Facebook应用程序密码


 #SPRING SOCIAL LINKEDIN（LinkedInAutoConfiguration）
spring.social.linkedin.app-id = #您的应用程序的LinkedIn应用程序ID
spring.social.linkedin.app-secret = #您的应用程序的LinkedIn App Secret


 #SPRING SOCIAL TWITTER（TwitterAutoConfiguration）
spring.social.twitter.app-id = #你的应用程序的Twitter应用程序ID
spring.social.twitter.app-secret = #你的应用程序的Twitter App Secret


 #THYMELEAF Thymeleaf模板引擎配置
spring.thymeleaf.cache = true #启用模板缓存。
spring.thymeleaf.check-template-location = true #检查模板位置是否存在。
spring.thymeleaf.content-type = text / html #Content-Type值。
spring.thymeleaf.enabled = true #启用MVC Thymeleaf视图分辨率。
spring.thymeleaf.encoding = UTF-8 #模板编码。
spring.thymeleaf.excluded-view-names = #应该从解决方案中排除的视图名称的逗号分隔列表。
spring.thymeleaf.mode = HTML5 #应用于模板的模板模式。另请参见StandardTemplateModeHandlers。
spring.thymeleaf.prefix = classpath：/ templates / #在构建URL时预先查看名称的前缀。
spring.thymeleaf.suffix = .html #构建URL时附加查看名称的后缀。
spring.thymeleaf.template-resolver-order = #链中模板解析器的顺序。
spring.thymeleaf.view-names = #可以解析的视图名称的逗号分隔列表。


 #VELOCITY TEMPLATES（VelocityAutoConfiguration）
spring.velocity.allow-request-override = false #设置是否允许HttpServletRequest属性覆盖（隐藏）控制器生成的同名的模型属性。
spring.velocity.allow-session-override = false #设置是否允许HttpSession属性重写（隐藏）控制器生成的同名的模型属性。
spring.velocity.cache = #启用模板缓存。
spring.velocity.charset = UTF-8 #模板编码。
spring.velocity.check-template-location = true #检查模板位置是否存在。
spring.velocity.content-type = text / html #Content-Type值。
spring.velocity.date-tool-attribute = #在视图的Velocity上下文中公开的DateTool辅助对象的名称。
spring.velocity.enabled = true #启用此技术的MVC视图分辨率。
spring.velocity.expose-request-attributes = false #设置在与模板合并之前是否应将所有请求属性添加到模型中。
spring.velocity.expose-session-attributes = false #设置在与模板合并之前是否应将所有HttpSession属性添加到模型中。
spring.velocity.expose-spring-macro-helpers = true #设置是否公开一个RequestContext供Spring Spring的宏库使用，名称为“springMacroRequestContext”。
spring.velocity.number-tool-attribute = #在视图的Velocity上下文中公开的NumberTool帮助对象的名称。
spring.velocity.prefer-file-system-access = true #首选文件系统访问模板加载。文件系统访问可以对模板更改进行热检测。
spring.velocity.prefix = #前缀，用于在构建URL时查看名称。
spring.velocity.properties。* = #附加速度属性。
spring.velocity.request-context-attribute = #所有视图的RequestContext属性的名称。
spring.velocity.resource-loader-path = classpath：/ templates / #模板路径。
spring.velocity.suffix = .vm #构建URL时附加到查看名称的后缀。
spring.velocity.toolbox-config-location = #Velocity Toolbox配置位置。例如`/ WEB-INF / toolbox.xml'
spring.velocity.view-names = #可以解决的视图名称的白名单。






 #----------------------------------------
 #安全属性
 #----------------------------------------
 #SECURITY（SecurityProperties）
security.basic.authorize-mode = role #应用安全授权模式。
security.basic.enabled = true #启用基本身份验证。
security.basic.path = / ** #安全路径的逗号分隔列表。
security.basic.realm = Spring #HTTP基本的领域名称。
security.enable-csrf = false #启用跨站点请求伪造支持。
security.filter-order = 0 #安全过滤器连锁订单。
security.headers.cache = true #启用缓存控制HTTP头。
security.headers.content-type = true# 启用“X-Content-Type-Options”头。
security.headers.frame = true #启用“X-Frame-Options”标题。
security.headers.hsts = # HTTP严格传输安全（HSTS）模式（无，域，全部）。
security.headers.xss = true #启用跨站点脚本（XSS）保护。
security.ignored = #从默认安全路径中排除的路径的逗号分隔列表。
security.require-ssl = false #为所有请求启用安全通道。
security.sessions = stateless #会话创建策略（永远不会，if_required，无状态）。
security.user.name = user #默认用户名。
security.user.password = #默认用户名的密码。默认情况下，启动时会记录随机密码。
security.user.role = USER #为默认用户名授予角色。


 #SECURITY OAUTH2 CLIENT（OAuth2ClientProperties
security.oauth2.client.client-id = #OAuth2客户端ID。
security.oauth2.client.client-secret = #OAuth2客户机密码。默认生成随机密码


 #SECURITY OAUTH2 RESOURCES（ResourceServerProperties
security.oauth2.resource.id = #资源的标识符。
security.oauth2.resource.jwt.key-uri = #JWT令牌的URI。如果值不可用并且密钥是公共的，可以设置。
security.oauth2.resource.jwt.key-value = #JWT令牌的验证密钥。可以是对称秘密或PEM编码的RSA公钥。
security.oauth2.resource.prefer-token-info = true #使用令牌信息，可以设置为false以使用用户信息。
security.oauth2.resource.service-id = resource #
security.oauth2.resource.token-info-uri = #令牌解码端点的URI。
security.oauth2.resource.token-type = #使用userInfoUri时发送的令牌类型。
security.oauth2.resource.user-info-uri = #用户端点的URI。


 #SECURITY OAUTH2 SSO（OAuth2SsoProperties
security.oauth2.sso.filter-order = #如果不提供显式的WebSecurityConfigurerAdapter，则应用过滤器顺序
security.oauth2.sso.login-path = / login #登录页面的路径，即触发重定向到OAuth2授权服务器的路径




# ----------------------------------------
# DATA PROPERTIES 数据性能
# ----------------------------------------


# FLYWAY (FlywayProperties)
flyway.baseline-description = #
flyway.baseline-version = 1 #版本开始迁移
flyway.baseline-on-migrate = #
flyway.check-location = false #检查迁移脚本位置是否存在。
flyway.clean-on-validation-error = #
flyway.enabled = true #启用飞行路线。
flyway.encoding = #
flyway.ignore-failed-future-migration = #
flyway.init-sqls = #执行SQL语句，以便在获取连接后立即初始化连接。
flyway.locations = classpath：db / migration #迁移脚本的位置
flyway.out-of-order = #如果您希望Flyway创建自己的DataSource，则需要使用#path密码
flyway.placeholder-prefix = #
flyway.placeholder-replacement = #
flyway.placeholder-suffix = #
flyway.placeholders。* = #
flyway.schemas = #schemas来更新
flyway.sql-migration-prefix = V #
flyway.sql-migration-separator = #
flyway.sql-migration-suffix = .sql #
flyway.table = #
flyway.url = #要迁移的数据库的JDBC url。如果未设置，则使用主配置的数据源。
flyway.user = #登录要迁移的数据库的用户。
flyway.validate-on-migrate = #


# LIQUIBASE (LiquibaseProperties)
liquibase.change-log = classpath：/db/changelog/db.changelog-master.yaml #更改日志配置路径。
liquibase.check-change-log-location = true #检查更改日志位置是否存在。
liquibase.contexts = #使用逗号分隔的运行时上下文列表。
liquibase.default-schema = #默认数据库模式。
liquibase.drop-first = false #首先删除数据库模式。
liquibase.enabled = true #启用liquidibase支持。
liquibase.labels = #使用逗号分隔的运行时标签列表。
liquibase.parameters。* = #更改日志参数。
liquibase.password = #登录要迁移的数据库的密码。
liquibase.url = #要迁移的数据库的JDBC url。 如果未设置，则使用主配置的数据源。
liquibase.user = #登录要迁移的数据库的用户。



# DAO (PersistenceExceptionTranslationAutoConfiguration)
spring.dao.exceptiontranslation.enabled= true # 启用持久异常翻译后处理器。


# CASSANDRA (CassandraProperties)
spring.data.cassandra.cluster-name = #Cassandra群集的名称。
spring.data.cassandra.compression = #由Cassandra二进制协议支持的压缩。
spring.data.cassandra.connect-timeout-millis = #套接字选项：连接超时。
spring.data.cassandra.consistency-level = #查询一致性级别。
spring.data.cassandra.contact-points = localhost #集群节点地址的逗号分隔列表。
spring.data.cassandra.fetch-size = #查询默认的抓取大小。
spring.data.cassandra.keyspace-name = #要使用的密钥空间名称。
spring.data.cassandra.load-balancing-policy = #负载均衡策略的类名。
spring.data.cassandra.port = #Cassandra服务器端口。
spring.data.cassandra.password = #登录服务器的密码。
spring.data.cassandra.read-timeout-millis = #套接字选项：读取超时。
spring.data.cassandra.reconnection-policy = #重新连接策略类。
spring.data.cassandra.retry-policy = #重试策略的类名。
spring.data.cassandra.serial-consistency-level = #查询串行一致性级别。
spring.data.cassandra.ssl = false #启用SSL支持。
spring.data.cassandra.username = #登录用户的服务器。


# ELASTICSEARCH (ElasticsearchProperties)
spring.data.elasticsearch.cluster-name = elasticsearch #弹性搜索集群名称。
spring.data.elasticsearch.cluster-nodes = #集群节点地址的逗号分隔列表。 如果未指定，则启动客户端节点。
spring.data.elasticsearch.properties。* = #用于配置客户端的其他属性。
spring.data.elasticsearch.repositories.enabled = true #启用Elasticsearch存储库。


# MONGODB (MongoProperties)
spring.data.mongodb.authentication-database = #验证数据库名称。
spring.data.mongodb.database = test #数据库名称。
spring.data.mongodb.field-naming-strategy = #要使用的FieldNamingStrategy的完全限定名称。
spring.data.mongodb.grid-fs-database = #GridFS数据库名称。
spring.data.mongodb.host = localhost #Mongo服务器主机。
spring.data.mongodb.password = #登录mongo服务器的密码。
spring.data.mongodb.port = 27017 #Mongo服务器端口。
spring.data.mongodb.repositories.enabled = true #启用Mongo存储库。
spring.data.mongodb.uri = mongodb：// localhost / test #Mongo数据库URI。 设置时，主机和端口将被忽略。
spring.data.mongodb.username = #登录mongo服务器的用户。


# DATA REST (RepositoryRestProperties)
spring.data.rest.base-path = #由Spring Data REST用于公开存储库资源的基本路径。
spring.data.rest.default-page-size = #页面的默认大小。
spring.data.rest.enable-enum-translation = #通过Spring Data REST默认资源包启用枚举值转换。
spring.data.rest.limit-param-name = #指示一次返回多少结果的URL查询字符串参数的名称。
spring.data.rest.max-page-size = #最大页面大小。
spring.data.rest.page-param-name = #指示要返回的页面的URL查询字符串参数的名称。
spring.data.rest.return-body-on-create = #创建一个实体后返回响应体。
spring.data.rest.return-body-on-update = #更新实体后返回响应体。
spring.data.rest.sort-param-name = #指示排序结果的方向的URL查询字符串参数的名称。


# SOLR (SolrProperties)
spring.data.solr.host = http://127.0.0.1:8983/solr #Solr主机。 如果设置了“zk-host”，则被忽略。
spring.data.solr.repositories.enabled = true #启用Solr存储库。
spring.data.solr.zk-host = #ZooKeeper主机地址，格式为HOST：PORT。


# 数据源 配置 (DataSourceAutoConfiguration & DataSourceProperties)
spring.datasource.continue-on-error = false #初始化数据库时发生错误时不要停止。
spring.datasource.data = #Data（DML）脚本资源引用。
spring.datasource.driver-class-name = #JDBC驱动程序的完全限定名称。默认情况下，根据URL自动检测。
spring.datasource.initialize = true #使用'data.sql'填充数据库。
spring.datasource.jmx-enabled = false #启用JMX支持（如果由底层池提供）。
spring.datasource.jndi-name = #数据源的JNDI位置。设置时，类，网址，用户名和密码将被忽略。
spring.datasource.max-active = #例如100
spring.datasource.max-idle = #例如8
spring.datasource.max等待=
spring.datasource.min-evictable空闲时间-米利斯=
spring.datasource.min-idle = 8
spring.datasource.name = testdb #数据源的名称。
spring.datasource.password = #登录数据库的密码。
spring.datasource.platform = all #在资源模式（schema - $ {platform} .sql）中使用的平台。
spring.datasource.schema = #Schema（DDL）脚本资源引用。
spring.datasource.separator =;  #语句分隔符在SQL初始化脚本中。
spring.datasource.sql-script-encoding = #SQL脚本编码。
spring.datasource.test-on-borrow = #例如`false`
spring.datasource.test-on-return = #例如`false`
spring.datasource.test-while-idle = #
spring.datasource.time-between-eviction-runs-millis = 1
spring.datasource.type = #要使用的连接池实现的完全限定名称。默认情况下，它是从类路径自动检测的。
spring.datasource.url = #数据库的JDBC url。
spring.datasource.username= 
spring.datasource.validation-query= 


# H2 Web Console (H2ConsoleProperties)  
spring.h2.console.enabled = false #启用控制台。
spring.h2.console.path = / h2-console #控制台可用的路径。


# JOOQ (JooqAutoConfiguration)
spring.jooq.sql-dialect=  # 与配置的数据源通信时使用的SQLDialect JOOQ。 例如`POSTGRES`


# JPA (JpaBaseConfiguration, HibernateJpaAutoConfiguration)
spring.data.jpa.repositories.enabled = true #启用JPA存储库。
spring.jpa.database = #目标数据库进行操作，默认情况下自动检测。可以使用“databasePlatform”属性设置。
spring.jpa.database-platform = #要运行的目标数据库的名称，默认情况下自动检测。可以使用“数据库”枚举来设置。
spring.jpa.generate-ddl = false #启动时初始化模式。
spring.jpa.hibernate.ddl-auto = #DDL模式。这实际上是“hibernate.hbm2ddl.auto”属性的快捷方式。使用嵌入式数据库时默认为“创建删除”，否则为“否”。
spring.jpa.hibernate.naming-strategy = #命名策略完全限定名。
spring.jpa.open-in-view = true #注册OpenEntityManagerInViewInterceptor。将JPA EntityManager绑定到线程以进行请求的整个处理。
spring.jpa.properties。* = #在JPA提供程序上设置的其他本机属性。
spring.jpa.show-sql = false #启用SQL语句的日志记录。


# JTA (JtaAutoConfiguration)
spring.jta。* = #技术特定配置
spring.jta.log-dir = #Transaction logs目录。


# ATOMIKOS
spring.jta.atomikos.connectionfactory.borrow-connection-timeout = 30 #从池中借用连接的超时（以秒为单位）。
spring.jta.atomikos.connectionfactory.ignore-session-transacted-flag = true #创建会话时是否忽略事务标志。
spring.jta.atomikos.connectionfactory.local-transaction-mode = false #是否需要本地事务。
spring.jta.atomikos.connectionfactory.maintenance-interval = 60 #池的维护线程运行之间的时间（以秒为单位）。
spring.jta.atomikos.connectionfactory.max-idle-time = 60 #从池中清除连接之后的时间（以秒为单位）。
spring.jta.atomikos.connectionfactory.max-lifetime = 0 #在被破坏之前可以将连接合并的时间（以秒为单位）。 0表示无限制。
spring.jta.atomikos.connectionfactory.max-pool-size = 1 #池的最大大小。
spring.jta.atomikos.connectionfactory.min-pool-size = 1 #池的最小大小。
spring.jta.atomikos.connectionfactory.reap-timeout = 0 #借用连接的收获超时（以秒为单位）。 0表示无限制。
spring.jta.atomikos.connectionfactory.unique-resource-name = jmsConnectionFactory #用于在恢复期间标识资源的唯一名称。
spring.jta.atomikos.datasource.borrow-connection-timeout = 30 #从池中借出连接的超时（秒）。
spring.jta.atomikos.datasource.default-isolation-level = #池提供的连接的默认隔离级别。
spring.jta.atomikos.datasource.login-timeout = #用于建立数据库连接的超时（以秒为单位）。
spring.jta.atomikos.datasource.maintenance-interval = 60 #池的维护线程运行之间的时间（以秒为单位）。
spring.jta.atomikos.datasource.max-idle-time = 60 #从池中清除连接之后的时间（以秒为单位）。
spring.jta.atomikos.datasource.max-lifetime = 0 #在被破坏之前可以将连接合并的时间（以秒为单位）。 0表示无限制。
spring.jta.atomikos.datasource.max-pool-size = 1 #池的最大大小。
spring.jta.atomikos.datasource.min-pool-size = 1 #池的最小大小。
spring.jta.atomikos.datasource.reap-timeout = 0 #借用连接的收获超时（以秒为单位）。 0表示无限制。
spring.jta.atomikos.datasource.test-query = #用于在返回连接之前验证连接的SQL查询或语句。
spring.jta.atomikos.datasource.unique-resource-name = dataSource #用于在恢复期间识别资源的唯一名称。


# BITRONIX
spring.jta.bitronix.connectionfactory.acquire-increment = 1 #生成池时要创建的连接数。
spring.jta.bitronix.connectionfactory.acquisition-interval = 1 #在获取无效连接后再次尝试获取连接之前等待的时间（以秒为单位）。
spring.jta.bitronix.connectionfactory.acquisition-timeout = 30 #从池中获取连接的超时（以秒为单位）。
spring.jta.bitronix.connectionfactory.allow-local-transactions = true #事务管理器是否允许混合XA和非XA事务。
spring.jta.bitronix.connectionfactory.apply-transaction-timeout = false #当XAResource被登记时，是否应该设置事务超时。
spring.jta.bitronix.connectionfactory.automatic-enlisting-enabled = true #资源是否应该被自动登记和删除。
spring.jta.bitronix.connectionfactory.cache-producer-consumer = true #是否生产和消费者应该被缓存。
spring.jta.bitronix.connectionfactory.defer-connection-release = true #提供程序是否可以在同一连接上运行许多事务，并支持事务交织。
spring.jta.bitronix.connectionfactory.ignore-recovery-failures = false #是否应忽略恢复失败。
spring.jta.bitronix.connectionfactory.max-idle-time = 60 #从池中清除连接之后的时间（以秒为单位）。
spring.jta.bitronix.connectionfactory.max-pool-size = 10 #池的最大大小。 0表示无限制。
spring.jta.bitronix.connectionfactory.min-pool-size = 0 #池的最小大小。
spring.jta.bitronix.connectionfactory.password = #用于连接到JMS提供程序的密码。
spring.jta.bitronix.connectionfactory.share-transaction-connections = false #ACCESSIBLE状态中的连接是否可以在事务的上下文中共享。
spring.jta.bitronix.connectionfactory.test-connections = true #从池中获取连接是否应该进行测试。
spring.jta.bitronix.connectionfactory.two-pc-ordering-position = 1 #在两阶段提交期间该资源应该采取的位置（始终为Integer.MIN_VALUE，始终为Integer.MAX_VALUE）。
spring.jta.bitronix.connectionfactory.unique-name = jmsConnectionFactory #用于在恢复期间标识资源的唯一名称。
spring.jta.bitronix.connectionfactory.use-tm-join = true启动XAResource时是否应使用TMJOIN。
spring.jta.bitronix.connectionfactory.user = #用于连接到JMS提供者的用户。
spring.jta.bitronix.datasource.acquire-increment = 1 #生成池时要创建的连接数。
spring.jta.bitronix.datasource.acquisition-interval = 1 #在获取无效连接后再尝试获取连接之前等待的时间（以秒为单位）。
spring.jta.bitronix.datasource.acquisition-timeout = 30 #从池中获取连接的超时（以秒为单位）。
spring.jta.bitronix.datasource.allow-local-transactions = true #事务管理器是否允许混合XA和非XA事务。
spring.jta.bitronix.datasource.apply-transaction-timeout = false #当XAResource被登记时，是否应该设置事务超时。
spring.jta.bitronix.datasource.automatic-enlisting-enabled = true #资源是否应该被登记和自动删除。
spring.jta.bitronix.datasource.cursor-holdability = #连接的默认游标保持性。
spring.jta.bitronix.datasource.defer-connection-release = true #数据库是否可以在同一连接上运行许多事务，并支持事务交织。
spring.jta.bitronix.datasource.enable-jdbc4-connection-test = #从池中获取连接时是否调用Connection.isValid（）。
spring.jta.bitronix.datasource.ignore-recovery-failures = false #是否应忽略恢复失败。
spring.jta.bitronix.datasource.isolation-level = #连接的默认隔离级别。
spring.jta.bitronix.datasource.local-auto-commit = #本地事务的默认自动提交模式。
spring.jta.bitronix.datasource.login-timeout = #用于建立数据库连接的超时（以秒为单位）。
spring.jta.bitronix.datasource.max-idle-time = 60 #从池中清除连接之后的时间（以秒为单位）。
spring.jta.bitronix.datasource.max-pool-size = 10 #池的最大大小。 0表示无限制。
spring.jta.bitronix.datasource.min-pool-size = 0 #池的最小大小。
spring.jta.bitronix.datasource.prepared-statement-cache-size = 0 #准备好的语句高速缓存的目标大小。 0禁用缓存。
spring.jta.bitronix.datasource.share-transaction-connections = false #ACCESSIBLE状态下的连接是否可以在事务的上下文中共享。
spring.jta.bitronix.datasource.test-query = #用于在返回连接之前验证连接的SQL查询或语句。
spring.jta.bitronix.datasource.two-pc-ordering-position = 1 #在两阶段提交期间该资源应该采取的位置（始终为Integer.MIN_VALUE，始终为Integer.MAX_VALUE）。
spring.jta.bitronix.datasource.unique-name = dataSource #用于在恢复期间标识资源的唯一名称。
spring.jta.bitronix.datasource.use-tm-join = true启动XAResource时是否应使用TMJOIN。



# EMBEDDED MONGODB (EmbeddedMongoProperties)
spring.mongodb.embedded.features = SYNC_DELAY #启用功能的逗号分隔列表。
spring.mongodb.embedded.version = 2.6.10 #Mongo使用版本。



# ----------------------------------------
# 整合属性
# ----------------------------------------

 #ACTIVEMQ（ActiveMQProperties）
spring.activemq.broker-url = #ActiveMQ代理的URL。 默认自动生成。 例如`tcp：// localhost：61616`
spring.activemq.in-memory = true #指定默认代理URL是否应在内存中。 如果指定了一个显式代理，则被忽略。
spring.activemq.password = #登录密码的代理。
spring.activemq.pooled = false #指定是否创建PooledConnectionFactory而不是常规的ConnectionFactory。
spring.activemq.user = #代理登录用户。


# ARTEMIS (ArtemisProperties)
spring.artemis.embedded.cluster-password = #群集密码。 默认情况下随机生成。
spring.artemis.embedded.data-directory = #日志文件目录。 如果持久性被关闭，则不需要。
spring.artemis.embedded.enabled = true #如果Artemis服务器API可用，启用嵌入式模式。
spring.artemis.embedded.persistent = false #启用持久存储。
spring.artemis.embedded.queues = #启动时要创建的队列的逗号分隔列表。
spring.artemis.embedded.server-id = #服务器ID。 默认情况下，使用自动递增的计数器。
spring.artemis.embedded.topics = #启动时要创建的主题的逗号分隔列表。
spring.artemis.host = localhost #Artemis代理主机。
spring.artemis.mode = #Artemis部署模式，默认情况下自动检测。 可以显式设置为“native”或“embedded”。
spring.artemis.port = 61616 #Artemis 中间件端口。


# SPRING BATCH(Batch 配置)
spring.batch.initializer.enabled = true #如果需要，在启动时创建所需的批处理表。
spring.batch.job.enabled = true #在启动时执行上下文中的所有Spring批处理作业。
spring.batch.job.names = #在启动时执行的作业名称的逗号分隔列表（例如`job1，job2`）。 默认情况下，执行在上下文中找到的所有作业。
spring.batch.schema = classpath：org / springframework / batch / core / schema - @@ platform @@。sql #用于初始化数据库模式的SQL文件的路径。
spring.batch.table-prefix = #所有批次元数据表的表前缀。


# HORNETQ (HornetQ 配置)
spring.hornetq.embedded.cluster-password = #集群密码。 默认情况下随机生成。
spring.hornetq.embedded.data-directory = #日志文件目录。 如果持久性被关闭，则不需要。
spring.hornetq.embedded.enabled = true #如果HornetQ服务器API可用，启用嵌入式模式。
spring.hornetq.embedded.persistent = false #启用持久存储。
spring.hornetq.embedded.queues = #启动时要创建的队列的逗号分隔列表。
spring.hornetq.embedded.server-id = #服务器ID。 默认情况下，使用自动递增的计数器。
spring.hornetq.embedded.topics = #在启动时创建的主题的逗号分隔列表。
spring.hornetq.host = localhost #HornetQ代理主机。
spring.hornetq.mode = #HornetQ部署模式，默认情况下自动检测。 可以显式设置为“native”或“embedded”。
spring.hornetq.port = 5445 #HornetQ代理端口。


# JMS (Jms 配置)
# 连接工厂JNDI名称。 设置时，优先于其他连接工厂自动配置。
spring.jms.jndi-name= 
# 容器的确认模式。 默认情况下，监听器被自动确认处理。
spring.jms.listener.acknowledge-mode= 
# 启动时自动启动容器。
spring.jms.listener.auto-startup= true
# 最小并发消费者数。
spring.jms.listener.concurrency= 
# 最大并发消费者数。
spring.jms.listener.max-concurrency= 
# 指定默认的目的地类型是否为主题。
spring.jms.pub-sub-domain= false


# RABBIT (Rabbit 配置)
# 客户端应连接到的逗号分隔的地址列表。
spring.rabbitmq.addresses = 
spring.rabbitmq.dynamic =  true # 创建一个AmqpAdmin bean。
spring.rabbitmq.host =  localhost# RabbitMQ主机。
spring.rabbitmq.listener.acknowledge-mode = # 容器的确认模式。
spring.rabbitmq.listener.auto-startup =  true# 启动时自动启动容器。
spring.rabbitmq.listener.concurrency = # 最少消费者数。
spring.rabbitmq.listener.max-concurrency = # 最大消费者数。
spring.rabbitmq.listener.prefetch = # 在单个请求中要处理的消息数。它应该大于或等于事务大小（如果使用）。
spring.rabbitmq.listener.transaction-size = # 在事务中要处理的消息数。为了获得最佳结果，它应该小于或等于预取计数。
spring.rabbitmq.password = # 登录以对代理进行身份验证。
spring.rabbitmq.port =  5672# RabbitMQ端口。
spring.rabbitmq.requested-heartbeat = # 请求的心跳超时，以秒为单位;零为无。
spring.rabbitmq.ssl.enabled =  false# 启用SSL支持。
spring.rabbitmq.ssl.key-store =  # 保存SSL证书的密钥存储区的路径。
spring.rabbitmq.ssl.key-store-password = # 用于访问密钥库的密码。
spring.rabbitmq.ssl.trust-store = # 保存SSL证书的Trust存储。
spring.rabbitmq.ssl.trust-store-password = # 用于访问信任存储的密码。
spring.rabbitmq.username = # 登录用户对代理进行身份验证。
spring.rabbitmq.virtual-host = # 连接到代理时使用的虚拟主机。


# 端点配置（EndpointCorsProperties）
# 设置是否支持凭据。 未设置时，不支持凭据。
endpoints.cors.allow-credentials= 
# 在请求中允许的头文件逗号分隔列表。 '*'允许所有标题。
endpoints.cors.allowed-headers= 
# 逗号分隔的允许的方法列表。 '*'允许所有方法。
endpoints.cors.allowed-methods= GET
# 逗号分隔的起始列表允许。 '*'允许所有来源。 未设置时，禁用CORS支持。
endpoints.cors.allowed-origins= 
# 包含在响应中的标题的逗号分隔列表。
endpoints.cors.exposed-headers= 
# 客户端可以缓存飞行前请求的响应时间（秒）。
endpoints.cors.max-age= 1800


# JMX ENDPOINT (EndpointMBeanExportProperties) （端点MBean导出属性）
# JMX域名。 如果设置为'spring.jmx.default-domain'的值初始化。
endpoints.jmx.domain= 
# 启用所有端点的JMX导出。
endpoints.jmx.enabled= true
# 附加静态属性以附加到表示端点的MBean的所有对象名称。
endpoints.jmx.static-names= 
# 确保在发生冲突时修改ObjectNames。
endpoints.jmx.unique-names= false


# JOLOKIA  JOLOKIA 配置
# 见Jolokia手册
jolokia.config.*= 


# 管理HTTP服务器（管理服务器属性）
# 在每个响应中添加“X-Application-Context”HTTP头。
management.add-application-context-header= true
# 管理端点应绑定到的网络地址。
management.address= 
# 管理端点上下文路径。 例如`/ actuator`
management.context-path= 
# 管理端点HTTP端口。 默认使用与应用程序相同的端口。
management.port= 
# 启用安全性
management.security.enabled= true
# 访问管理端点所需的角色。
management.security.role= ADMIN
# 会话创建策略使用（always，never，if_required，stateless）（总是，永远，if_required，无状态）。
management.security.sessions= stateless


# HEALTH INDICATORS (previously health.*)
# 启用数据库运行状况检查
management.health.db.enabled= true
# 启用默认的健康指标。
management.health.defaults.enabled= true
# 启用磁盘空间运行状况检查。
management.health.diskspace.enabled= true
# 用于计算可用磁盘空间的路径。
management.health.diskspace.path= 
# 应该可用的最小磁盘空间（以字节为单位）。
management.health.diskspace.threshold= 0
# 启用弹性搜索健康检查。
management.health.elasticsearch.enabled= true
# 逗号分隔的索引名称。
management.health.elasticsearch.indices= 
# 等待群集响应的时间（以毫秒为单位）。
management.health.elasticsearch.response-timeout= 100
# 启用JMS健康检查。
management.health.jms.enabled= true
# 启用邮件运行状况检查。
management.health.mail.enabled= true
# 启用MongoDB健康检查。
management.health.mongo.enabled= true
# 启用RabbitMQ运行状况检查。
management.health.rabbit.enabled= true
# 启用Redis健康检查。
management.health.redis.enabled= true
# 启用Solr运行状况检查。
management.health.solr.enabled= true
# 按照严重性的顺序，以逗号分隔的健康状态列表。
management.health.status.order= DOWN, OUT_OF_SERVICE, UNKNOWN, UP


# TRACING ((TraceProperties) 跟踪性能
# 跟踪中包含的项目。
management.trace.include= request-headers,response-headers,errors


# 远程 shell配置
# 验证类型。 根据环境自动检测。
shell.auth= simple
# JAAS域。
shell.auth.jaas.domain= my-domain
# 验证密钥的路径。 这应该指向一个有效的“.pem”文件。
shell.auth.key.path= 
# 登录用户。
shell.auth.simple.user.name= user
# 登录用户的密码。
shell.auth.simple.user.password= 
# 登录到CRaSH控制台的所需的角色，以逗号分隔列表。
shell.auth.spring.roles= ADMIN
# 用于查找命令的模式。
shell.command-path-patterns= classpath*:/commands/**,classpath*:/crash/commands/**
# 扫描更改并在必要时更新命令（以秒为单位）。
shell.command-refresh-interval= -1
# 用于查找配置的模式。
shell.config-path-patterns= classpath*:/crash/*
# 逗号分隔的要禁用的命令列表。
shell.disabled-commands= jpa*,jdbc*,jndi*
# 禁用逗号分隔的插件列表。 默认情况下，根据环境禁用某些插件。
shell.disabled-plugins= 
# 用户被提示再次登录后的毫秒数。
shell.ssh.auth-timeout = 
# 启用CRaSH SSH支持。
shell.ssh.enabled= true
# 未使用的连接关闭之后的毫秒数。
shell.ssh.idle-timeout = 
# SSH服务器密钥路径。
shell.ssh.key-path= 
# SSH端口。
shell.ssh.port= 2000
# 启用CRaSH telnet支持。 如果TelnetPlugin可用，默认情况下启用。
shell.telnet.enabled= false
# Telnet端口。
shell.telnet.port= 5000


# GIT 信息配置
# 生成的git信息属性文件的资源引用。
spring.git.properties= 


# 标准出口
# 模式，告诉聚合器如何从源存储库中的键。
spring.metrics.export.aggregate.key-pattern= 
# 全局存储库的前缀如果处于活动状态。
spring.metrics.export.aggregate.prefix= 
# 导出刻度之间以毫秒为单位的延迟。 按照这种延迟，指标将按计划导出到外部来源。
spring.metrics.export.delay-millis= 5000
# 标志以启用度量标准导出（假设MetricWriter可用）。
spring.metrics.export.enabled= true
# 要排除的度量名称列表。 应用后包括。
spring.metrics.export.excludes= 
# 要包含的度量名称的模式列表。
spring.metrics.export.includes= 
# redis存储库导出的密钥（如果活动）。
spring.metrics.export.redis.key= keys.spring.metrics
# redis存储库的前缀 如果处于活动状态。
spring.metrics.export.redis.prefix= spring.metrics
# 标志基于不导出不变的度量值来关闭任何可用的优化。
spring.metrics.export.send-latest= 
# 主机的statsd服务器接收导出的指标。
spring.metrics.export.statsd.host= 
# 接收导出指标的statsd服务器端口。
spring.metrics.export.statsd.port= 8125
# statsd导出指标的前缀。
spring.metrics.export.statsd.prefix= 
# 每个MetricWriter bean名称具有特定的触发器属性。
spring.metrics.export.triggers.*= 


# ----------------------------------------
# DEVTOOLS属性
# ----------------------------------------

# DEVTOOLS（开发工具属性）
# 启用一个livereload.com兼容的服务器。
spring.devtools.livereload.enabled= true
#  # Server port.
spring.devtools.livereload.port= 35729
# 应该排除的触发完全重新启动的其他模式。
spring.devtools.restart.additional-exclude= 
# 观看更改的附加路径。
spring.devtools.restart.additional-paths= 
# 启用自动重启功能。
spring.devtools.restart.enabled= true
# 应该排除的模式触发完全重新启动。
spring.devtools.restart.exclude= META-INF/maven/**,META-INF/resources/**,resources/**,static/**,public/**,templates/**,**/*Test.class,**/*Tests.class,git.properties
# 轮询类路径更改之间等待的时间量（以毫秒为单位）。
spring.devtools.restart.poll-interval= 1000
# 触发重新启动之前没有任何类路径更改所需的安静时间量（以毫秒为单位）。
spring.devtools.restart.quiet-period= 400
# 更改后的特定文件的名称将触发重新启动检查。 如果未指定任何类路径文件更改将触发重新启动。
spring.devtools.restart.trigger-file= 


# 远程开发工具属性
# 用于处理远程连接的上下文路径。
spring.devtools.remote.context-path= /.~~spring-boot!~
# 启用远程调试支持。
spring.devtools.remote.debug.enabled= true
# 本地远程调试服务器端口。
spring.devtools.remote.debug.local-port= 8000
# 用于连接到远程应用程序的代理主机。
spring.devtools.remote.proxy.host= 
# 用于连接到远程应用程序的代理端口。
spring.devtools.remote.proxy.port= 
# 启用远程重启。
spring.devtools.remote.restart.enabled= true
# 建立连接所需的共享密钥（需要启用远程支持）。
spring.devtools.remote.secret= 
# HTTP头用于传输共享密钥。</ span>
spring.devtools.remote.secret-header-name= X-AUTH-TOKEN


