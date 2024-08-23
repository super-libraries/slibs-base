package cn.slibs.base;


/**
 * 状态码枚举
 *
 * @since 0.0.1
 */
public enum StatusCode implements IStatusCode {
    OK("0", 0, "成功！", "OK!"),
    HTTP_OK("200", 200, "成功！", "OK!"),
    SUCCESS("00000", 0, "成功！", "OK!"),

    /*
     * HTTP status codes
     */
    CREATED("201", 201, "已成功处理请求并创建了新的资源", "Created"),
    ACCEPTED("202", 202, "已经接受请求，但未处理完成", "Accepted"),
    NON_AUTHORITATIVE_INFORMATION("203", 203, "非授权信息，请求成功", "Non Authoritative Information"),
    NO_CONTENT("204", 204, "已成功处理请求，且未返回任何内容", "No Content"),
    RESET_CONTENT("205", 205, "已成功处理请求，要求请求者重置其文档视图", "Reset Content"),
    PARTIAL_CONTENT("206", 206, "服务器仅提供部分资源（字节服务）", "Partial Content"),
    MULTI_STATUS("207", 207, "该请求之后的消息体将是一个XML消息", "Multi Status"),
    ALREADY_REPORTED("208", 208, "已报告", "Already Reported"),
    IM_USED("226", 226, "已完成对资源的请求", "IM Used"),
    MULTIPLE_CHOICES("300", 300, "多种选择", "Multiple Choices"),
    MOVED_PERMANENTLY("301", 301, "请求的网页已永久移动到新位置", "Moved Permanently"),
    FOUND("302", 302, "临时性重定向", "Found (Previously 'Moved temporarily')"),
    SEE_OTHER("303", 303, "临时性重定向，且总是使用GET请求新的URI", "See Other"),
    NOT_MODIFIED("304", 304, "未修改。自从上次请求后，请求的网页未修改过", "Not Modified"),
    USE_PROXY("305", 305, "所请求的资源必须通过代理访问", "Use Proxy"),
    SWITCH_PROXY("306", 306, "后续请求应使用指定的代理（此状态码已作废）", "Switch Proxy"),
    TEMPORARY_REDIRECT("307", 307, "临时重定向（与302类似），使用GET请求重定向", "Temporary Redirect"),
    PERMANENT_REDIRECT("308", 308, "永久重定向", "Permanent Redirect"),
    BAD_REQUEST("400", 400, "客户端错误或无效的参数、未按约定传参等，导致服务器无法处理请求", "Bad Request"),
    UNAUTHORIZED("401", 401, "未经授权（与403 Forbidden类似）", "Unauthorized"),
    PAYMENT_REQUIRED("402", 402, "需要付款", "Payment Required"),
    FORBIDDEN("403", 403, "禁止访问", "Forbidden"),
    NOT_FOUND("404", 404, "找不到请求的资源", "Not Found"),
    METHOD_NOT_ALLOWED("405", 405, "请求的资源不支持的请求方法", "Method Not Allowed"),
    NOT_ACCEPTABLE("406", 406, "Accept标头生成不可接受的内容。如：客户端不支持服务端返回的Content-Type等", "Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED("407", 407, "需要代理身份验证", "Proxy Authentication Required"),
    REQUEST_TIMEOUT("408", 408, "服务器在等待请求时超时", "Request Timeout"),
    CONFLICT("409", 409, "由于资源当前状态中的冲突而无法处理请求", "Conflict"),
    GONE("410", 410, "请求的资源以前在使用中，但现在不再可用，并且将不再可用", "Gone"),
    LENGTH_REQUIRED("411", 411, "请求未指定其内容的长度，这是所请求资源所必需的", "Length Required"),
    PRECONDITION_FAILED("412", 412, "服务器不满足请求者对请求标头字段设置的前提条件之一", "Precondition Failed"),
    PAYLOAD_TOO_LARGE("413", 413, "有效载荷太大（请求实体太大）", "Payload Too Large"),
    URI_TOO_LONG("414", 414, "提供的URI太长，服务器无法处理", "URI Too Long"),
    UNSUPPORTED_MEDIA_TYPE("415", 415, "不支持的媒体类型。如：服务端不支持客户端传过来的Content-Type等", "Unsupported Media Type"),
    RANGE_NOT_SATISFIABLE("416", 416, "范围不满足", "Range Not Satisfiable"),
    EXPECTATION_FAILED("417", 417, "服务器无法满足 Expect request-header 字段的要求", "Expectation Failed"),
    IM_A_TEAPOT("418", 418, "超文本咖啡壶控制协议", "I'm a Teapot"),
    MISDIRECTED_REQUEST("421", 421, "请求定向到无法生成响应的服务器", "Misdirected Request"),
    UNPROCESSABLE_CONTENT("422", 422, "请求格式正确（即语法正确），但无法处理", "Unprocessable Content"),
    LOCKED("423", 423, "正在访问的资源已锁定", "Locked"),
    FAILED_DEPENDENCY("424", 424, "请求失败，因为它依赖于另一个请求，并且该请求失败", "Failed Dependency"),
    TOO_EARLY("425", 425, "服务器不愿意冒险处理可能重播的请求", "Too Early"),
    UPGRADE_REQUIRED("426", 426, "客户端应切换到其他协议", "Upgrade Required"),
    PRECONDITION_REQUIRED("428", 428, "源服务器要求请求是有条件的", "Precondition Required"),
    TOO_MANY_REQUESTS("429", 429, "用户在给定的时间内发送了过多的请求", "Too Many Requests"),
    REQUEST_HEADER_FIELDS_TOO_LARGE("431", 431, "请求标头字段太大", "Request Header Fields Too Large"),
    UNAVAILABLE_FOR_LEGAL_REASONS("451", 451, "因法律原因不可用", "Unavailable For Legal Reasons"),
    INTERNAL_SERVER_ERROR("500", 500, "服务内部错误，请联系管理员", "Internal Server Error"),
    NOT_IMPLEMENTED("501", 501, "服务器无法识别请求方法或无法满足请求", "Not Implemented"),
    BAD_GATEWAY("502", 502, "错误网关", "Bad Gateway"),
    SERVICE_UNAVAILABLE("503", 503, "服务不可用", "Service Unavailable"),
    GATEWAY_TIMEOUT("504", 504, "网关超时", "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED("505", 505, "服务器不支持请求中使用的HTTP版本", "HTTP Version Not Supported"),
    VARIANT_ALSO_NEGOTIATES("506", 506, "请求的透明内容协商会导致循环引用", "Variant Also Negotiates"),
    INSUFFICIENT_STORAGE("507", 507, "服务器无法存储完成请求所需的表示形式", "Insufficient Storage"),
    LOOP_DETECTED("508", 508, "服务器在处理请求时检测到无限循环", "Loop Detected"),
    NOT_EXTENDED("510", 510, "服务器需要进一步扩展请求才能满足它", "Not Extended"),
    NETWORK_AUTHENTICATION_REQUIRED("511", 511, "需要网络身份验证", "Network Authentication Required"),

    /*
     * user-defined status codes
     */
    CLIENT_SIDE_ERROR("10001", 10001, "用户端错误", "Client Side Error"),
    USER_REGISTRATION_ERROR("10100", 10100, "用户注册错误", "User Registration Error"),
    USER_HAS_NOT_AGREED_TO_THE_PRIVACY_AGREEMENT("10101", 10101, "用户未同意隐私协议", "User Has Not Agreed To The Privacy Agreement"),
    REGISTRATION_IS_LIMITED_BY_COUNTRY_OR_TERRITORY("10102", 10102, "注册国家或地区受限", "Registration Is Limited By Country Or Territory"),
    DESCRIPTION_USER_NAME_VERIFICATION_FAILED("10110", 10110, "用户名校验失败", "Description User Name Verification Failed"),
    USER_NAME_ALREADY_EXISTS("10111", 10111, "用户名已存在", "User Name Already Exists"),
    USER_NAME_CONTAINS_SENSITIVE_WORDS("10112", 10112, "用户名包含敏感词", "User Name Contains Sensitive Words"),
    USER_NAME_CONTAINS_SPECIAL_CHARACTERS("10113", 10113, "用户名包含特殊字符", "User Name Contains Special Characters"),
    PASSWORD_VERIFICATION_FAILS("10120", 10120, "密码校验失败", "Password Verification Fails"),
    SHORT_PASSWORD_LENGTH("10121", 10121, "密码长度不够", "Short Password Length"),
    WEAK_PASSWORD_STRENGTH("10122", 10122, "密码强度不够", "Weak Password Strength"),
    VERIFI_CODE_IS_INCORRECT("10130", 10130, "校验码输入错误", "Verifi Code Is Incorrect"),
    SMS_VERIFI_CODE_IS_INCORRECT("10131", 10131, "短信校验码输入错误", "SMS Verifi Code Is Incorrect"),
    EMAIL_VERIFI_CODE_IS_INCORRECT("10132", 10132, "邮件校验码输入错误", "Email Verifi Code Is Incorrect"),
    VOICE_VERIFI_CODE_IS_INCORRECT("10133", 10133, "语音校验码输入错误", "Voice Verifi Code Is Incorrect"),
    USER_ID_IS_ABNORMAL("10140", 10140, "用户证件异常", "User ID Is Abnormal"),
    USER_ID_TYPE_IS_NOT_SELECTED("10141", 10141, "用户证件类型未选择", "User ID Type Is Not Selected"),
    MAINLAND_ID_NUMBER_CHECK_IS_ILLEGAL("10142", 10142, "大陆身份证编号校验非法", "Mainland ID Number Check Is Illegal"),
    PASSPORT_NUMBER_CHECK_IS_ILLEGAL("10143", 10143, "护照编号校验非法", "Passport Number Check Is Illegal"),
    BASIC_USER_INFO_FAILS_TO_BE_VERIFIED("10150", 10150, "用户基本信息校验失败", "Basic User Info Fails To Be Verified"),
    MOBILE_PHONE_FORMAT_VERIFICATION_FAILS("10151", 10151, "手机格式校验失败", "Mobile Phone Format Verification Fails"),
    ADDRESS_FORMAT_VERIFICATION_FAILS("10152", 10152, "地址格式校验失败", "Address Format Verification Fails"),
    EMAIL_FORMAT_VERIFICATION_FAILS("10153", 10153, "邮箱格式校验失败", "Email Format Verification Fails"),
    USER_LOGIN_EXCEPTION("10200", 10200, "用户登录异常", "User Login Exception"),
    USER_ACCOUNT_DOES_NOT_EXIST("10201", 10201, "用户账户不存在", "User Account Does Not Exist"),
    USER_ACCOUNT_IS_FROZEN("10202", 10202, "用户账户被冻结", "User Account Is Frozen"),
    USER_ACCOUNT_HAS_BEEN_INVALIDATED("10203", 10203, "用户账户已作废", "User Account Has Been Invalidated"),
    INCORRECT_USER_PASSWORD("10210", 10210, "用户密码错误", "Incorrect User Password"),
    INCORRECT_PASSWD_TRY_OVER_LIMIT("10211", 10211, "用户输入密码错误次数超限", "Incorrect Passwd Try Over Limit"),
    USER_IDENTITY_VERIFICATION_FAILED("10220", 10220, "用户身份校验失败", "User Identity Verification Failed"),
    USER_FINGERPRINT_IDENTIFICATION_FAILS("10221", 10221, "用户指纹识别失败", "User Fingerprint Identification Fails"),
    USER_FACE_RECOGNITION_FAILED("10222", 10222, "用户面容识别失败", "User Face Recognition Failed"),
    USER_NOT_HAVE_THIRD_PARTY_LOGIN_AUTHORIZATION("10223", 10223, "用户未获得第三方登录授权", "User Not Have Third Party Login Authorization"),
    LOGIN_HAS_EXPIRED("10230", 10230, "用户登录已过期", "Login Has Expired"),
    USER_VERIFI_CODE_IS_INCORRECT("10240", 10240, "用户验证码错误", "User Verifi Code Is Incorrect"),
    VERIFI_CODE__TRY_OVER_LIMIT("10241", 10241, "用户验证码尝试次数超限", "Verifi Code Try Over Limit"),
    ABNORMAL_ACCESS_PERMISSION("10300", 10300, "访问权限异常", "Abnormal Access Permission"),
    ACCESS_NOT_AUTHORIZED("10301", 10301, "访问未授权", "Access Not Authorized"),
    UNDER_AUTHORIZATION("10302", 10302, "正在授权中", "Under Authorization"),
    DENIED_USER_AUTHORIZATION_APPLY("10303", 10303, "用户授权申请被拒绝", "Denied User Authorization Apply"),
    OBJECT_PRIVACY_SETTINGS_WERE_INTERCEPTED("10310", 10310, "因访问对象隐私设置被拦截", "Object Privacy Settings Were Intercepted"),
    AUTHORIZATION_HAS_EXPIRED("10311", 10311, "授权已过期", "Authorization Has Expired"),
    NO_PERMISSION_TO_USE_API("10312", 10312, "无权限使用 API", "No Permission To Use API"),
    USER_ACCESS_IS_INTERCEPTED("10320", 10320, "用户访问被拦截", "User Access Is Intercepted"),
    BLACKLIST_USERS("10321", 10321, "黑名单用户", "Blacklist Users"),
    ACCOUNT_IS_FROZEN("10322", 10322, "账号被冻结", "Account Is Frozen"),
    INVALID_IP_ADDRESS("10323", 10323, "非法 IP 地址", "Invalid IP Address"),
    RESTRICTED_GATEWAY_ACCESS("10324", 10324, "网关访问受限", "Restricted Gateway Access"),
    GEOGRAPHICAL_BLACKLIST("10325", 10325, "地域黑名单", "Geographical Blacklist"),
    SERVICE_IS_OVERDUE("10330", 10330, "服务已欠费", "Service Is Overdue"),
    ABNORMAL_USER_SIGNATURE("10340", 10340, "用户签名异常", "Abnormal User Signature"),
    RSA_SIGNATURE_ERROR("10341", 10341, "RSA 签名错误", "RSA Signature Error"),
    TOKEN_AUTHENTICATION_FAILED("10342", 10342, "TOKEN认证失败", "Token Authentication Failed"),
    USER_REQUEST_PARAMETER_IS_INCORRECT("10400", 10400, "用户请求参数错误", "User Request Parameter Is Incorrect"),
    CONTAINS_AN_ILLEGAL_MALICIOUS_JUMP_LINK("10401", 10401, "包含非法恶意跳转链接", "Contains An Illegal Malicious Jump Link"),
    INVALID_USER_INPUT("10402", 10402, "无效的用户输入", "Invalid User Input"),
    REQUIRED_PARAMETER_IS_MISSING("10410", 10410, "请求必填参数为空", "Required Parameter Is Missing"),
    USER_ORDER_NUMBER_IS_EMPTY("10411", 10411, "用户订单号为空", "User Order Number Is Empty"),
    ORDER_QUANTITY_IS_EMPTY("10412", 10412, "订购数量为空", "Order Quantity Is Empty"),
    TIMESTAMP_PARAMETER_IS_MISSING("10413", 10413, "缺少时间戳参数", "Timestamp Parameter Is Missing"),
    INVALID_TIMESTAMP_PARAMETER("10414", 10414, "非法的时间戳参数", "Invalid Timestamp Parameter"),
    INVALID_PARAM("10415", 10415, "无效的参数值", "Invalid Param"),
    REQUEST_PARAMETER_VALUE_IS_OUT_OF_THE_ALLOWED_RANGE("10420", 10420, "请求参数值超出允许的范围", "Request Parameter Value Is Out Of The Allowed Range"),
    PARAMETER_FORMAT_DOES_NOT_MATCH("10421", 10421, "参数格式不匹配", "Parameter Format Does Not Match"),
    ADDRESS_IS_OUT_OF_SERVICE("10422", 10422, "地址不在服务范围", "Address Is Out Of Service"),
    HOURS_ARE_OUT_OF_SERVICE("10423", 10423, "时间不在服务范围", "Hours Are Out Of Service"),
    EXCEEDING_THE_LIMIT("10424", 10424, "金额超出限制", "Exceeding The Limit"),
    QUANTITY_OUT_OF_LIMIT("10425", 10425, "数量超出限制", "Quantity Out Of Limit"),
    TOTAL_NUMBER_OF_REQUEST_BATCH_OVER_LIMIT("10426", 10426, "请求批量处理总个数超出限制", "Total Number Of Request Batch Over Limit"),
    FAILED_TO_REQUEST_JSON_PARSING("10427", 10427, "请求 JSON 解析失败", "Failed To Request JSON Parsing"),
    INPUT_CONTENT_IS_INVALID("10430", 10430, "用户输入内容非法", "Input Content Is Invalid"),
    CONTAINS_PROHIBITED_SENSITIVE_WORDS("10431", 10431, "包含违禁敏感词", "Contains Prohibited Sensitive Words"),
    IMAGE_CONTAINS_PROHIBITED_INFORMATION("10432", 10432, "图片包含违禁信息", "Image Contains Prohibited Information"),
    COPYRIGHT_INFRINGEMENT_OF_DOCUMENTS("10433", 10433, "文件侵犯版权", "Copyright Infringement Of Documents"),
    USER_OPERATION_EXCEPTION("10440", 10440, "用户操作异常", "User Operation Exception"),
    USER_PAYMENT_TIMEOUT("10441", 10441, "用户支付超时", "User Payment Timeout"),
    CONFIRM_ORDER_TIMEOUT("10442", 10442, "确认订单超时", "Confirm Order Timeout"),
    ORDER_IS_CLOSED("10443", 10443, "订单已关闭", "Order Is Closed"),
    USER_REQUEST_SERVICE_IS_ABNORMAL("10500", 10500, "用户请求服务异常", "User Request Service Is Abnormal"),
    NUMBER_OF_REQUESTS_EXCEEDED_THE_LIMIT("10501", 10501, "请求次数超出限制", "Number Of Requests Exceeded The Limit"),
    NUMBER_OF_CONCURRENT_REQUESTS_OVER_LIMIT("10502", 10502, "请求并发数超出限制", "Number Of Concurrent Requests Over Limit"),
    PLEASE_WAIT_FOR_THE_USER_OPERATION("10503", 10503, "用户操作请等待", "Please Wait For The User Operation"),
    WEBSOCKET_CONNECTION_IS_ABNORMAL("10504", 10504, "WebSocket 连接异常", "Websocket Connection Is Abnormal"),
    WEBSOCKET_CONNECTION_IS_DOWN("10505", 10505, "WebSocket 连接断开", "Websocket Connection Is Down"),
    USER_REPEAT_REQUEST("10506", 10506, "用户重复请求", "User Repeat Request"),
    USER_RESOURCE_EXCEPTION("10600", 10600, "用户资源异常", "User Resource Exception"),
    INSUFFICIENT_ACCOUNT_BALANCE("10601", 10601, "账户余额不足", "Insufficient Account Balance"),
    USER_DISK_SPACE_IS_INSUFFICIENT("10602", 10602, "用户磁盘空间不足", "User Disk Space Is Insufficient"),
    USER_MEMORY_SPACE_IS_INSUFFICIENT("10603", 10603, "用户内存空间不足", "User Memory Space Is Insufficient"),
    USER_OSS_CAPACITY_IS_INSUFFICIENT("10604", 10604, "用户 OSS 容量不足", "User OSS Capacity Is Insufficient"),
    USER_QUOTA_HAS_BEEN_USED_UP("10605", 10605, "用户配额已用光", "User Quota Has Been Used Up"),
    USER_FAILED_TO_UPLOAD_FILES("10700", 10700, "用户上传文件异常", "User Failed To Upload Files"),
    UPLOADED_FILE_TYPE_NOT_MATCH("10701", 10701, "用户上传文件类型不匹配", "Uploaded File Type Not Match"),
    FILE_UPLOADED_BY_THE_USER_IS_TOO_LARGE("10702", 10702, "用户上传文件太大", "File Uploaded By The User Is Too Large"),
    USERS_UPLOAD_PICTURES_THAT_ARE_TOO_LARGE("10703", 10703, "用户上传图片太大", "Users Upload Pictures That Are Too Large"),
    USERS_UPLOAD_VIDEOS_THAT_ARE_TOO_LARGE("10704", 10704, "用户上传视频太大", "Users Upload Videos That Are Too Large"),
    COMPRESSED_FILE_UPLOADED_IS_TOO_LARGE("10705", 10705, "用户上传压缩文件太大", "Compressed File Uploaded Is Too Large"),
    CURRENT_VERSION_IS_ABNORMAL("10800", 10800, "用户当前版本异常", "Current Version Is Abnormal"),
    VERSION_NOT_MATCH_THE_SYSTEM("10801", 10801, "用户安装版本与系统不匹配", "Version Not Match The System"),
    INSTALLED_VERSION_IS_TOO_EARLY("10802", 10802, "用户安装版本过低", "Installed Version Is Too Early"),
    INSTALLED_VERSION_IS_TOO_HIGH("10803", 10803, "用户安装版本过高", "Installed Version Is Too High"),
    INSTALLED_VERSION_HAS_EXPIRED("10804", 10804, "用户安装版本已过期", "Installed Version Has Expired"),
    VERSION_REQUESTED_BY_USER_API_NOT_MATCH("10805", 10805, "用户 API 请求版本不匹配", "Version Requested By User API Not Match"),
    REQUESTED_API_VERSION_IS_TOO_HIGH("10806", 10806, "用户 API 请求版本过高", "Requested API Version Is Too High"),
    VERSION_OF_THE_API_REQUEST_IS_TOO_EARLY("10807", 10807, "用户 API 请求版本过低", "Version Of The API Request Is Too Early"),
    USER_PRIVACY_IS_NOT_AUTHORIZED("10900", 10900, "用户隐私未授权", "User Privacy Is Not Authorized"),
    USER_PRIVACY_IS_NOT_SIGNED("10901", 10901, "用户隐私未签署", "User Privacy Is Not Signed"),
    USER_CAMERA_IS_NOT_AUTHORIZED("10902", 10902, "用户摄像头未授权", "User Camera Is Not Authorized"),
    USER_CAMERA_APP_IS_NOT_AUTHORIZED("10903", 10903, "用户相机未授权", "User Camera App Is Not Authorized"),
    USER_IMAGE_LIBRARY_IS_NOT_AUTHORIZED("10904", 10904, "用户图片库未授权", "User Image Library Is Not Authorized"),
    USER_FILE_IS_NOT_AUTHORIZED("10905", 10905, "用户文件未授权", "User File Is Not Authorized"),
    USER_LOCATION_INFORMATION_IS_NOT_AUTHORIZED("10906", 10906, "用户位置信息未授权", "User Location Information Is Not Authorized"),
    USER_ADDRESS_BOOK_IS_NOT_AUTHORIZED("10907", 10907, "用户通讯录未授权", "User Address Book Is Not Authorized"),
    USER_DEVICE_EXCEPTION("11000", 11000, "用户设备异常", "User Device Exception"),
    USERS_CAMERA_IS_ABNORMAL("11001", 11001, "用户相机异常", "Users Camera Is Abnormal"),
    USERS_MICROPHONE_IS_ABNORMAL("11002", 11002, "用户麦克风异常", "Users Microphone Is Abnormal"),
    USERS_HANDSET_IS_ABNORMAL("11003", 11003, "用户听筒异常", "Users Handset Is Abnormal"),
    USER_LOUDSPEAKER_IS_ABNORMAL("11004", 11004, "用户扬声器异常", "User Loudspeaker Is Abnormal"),
    USERS_GPS_LOCATION_IS_ABNORMAL("11005", 11005, "用户 GPS 定位异常", "Users GPS Location Is Abnormal"),
    SYSTEM_EXECUTION_ERROR("20001", 20001, "系统执行出错", "System Execution Error"),
    SYSTEM_EXECUTION_TIMEOUT("20100", 20100, "系统执行超时", "System Execution Timeout"),
    SYSTEM_ORDER_PROCESSING_TIMED_OUT("20101", 20101, "系统订单处理超时", "System Order Processing Timed Out"),
    SYSTEM_DR_FUNCTION_IS_TRIGGERED("20200", 20200, "系统容灾功能被触发", "System DR Function Is Triggered"),
    SYSTEM_CURRENT_LIMITING("20210", 20210, "系统限流", "System Current Limiting"),
    SYSTEM_FUNCTION_DEGRADATION("20220", 20220, "系统功能降级", "System Function Degradation"),
    ABNORMAL_SYSTEM_RESOURCES("20300", 20300, "系统资源异常", "Abnormal System Resources"),
    SYSTEM_RESOURCE_DEPLETION("20310", 20310, "系统资源耗尽", "System Resource Depletion"),
    SYSTEM_DISK_SPACE_IS_USED_UP("20311", 20311, "系统磁盘空间耗尽", "System Disk Space Is Used Up"),
    SYSTEM_MEMORY_USED_UP("20312", 20312, "系统内存耗尽", "System Memory Used Up"),
    FILE_HANDLE_IS_EXHAUSTED("20313", 20313, "文件句柄耗尽", "File Handle Is Exhausted"),
    SYSTEM_CONNECTION_POOL_IS_EXHAUSTED("20314", 20314, "系统连接池耗尽", "System Connection Pool Is Exhausted"),
    SYSTEM_THREAD_POOL_IS_EXHAUSTED("20315", 20315, "系统线程池耗尽", "System Thread Pool Is Exhausted"),
    ACCESS_TO_SYSTEM_RESOURCES_IS_ABNORMAL("20320", 20320, "系统资源访问异常", "Access To System Resources Is Abnormal"),
    DESCRIPTION_THE_SYSTEM_FAILED_TO_READ_THE_DISK_FILE("20321", 20321, "系统读取磁盘文件失败", "Description The System Failed To Read The Disk File"),
    CALLING_A_THIRD_PARTY_SERVICE_ERROR("30001", 30001, "调用第三方服务出错", "Calling A Third Party Service Error"),
    CALLING_A_THIRD_PARTY_SERVICE_TIMEOUT("30002", 30002, "调用第三方服务超时", "Calling A Third Party Service Timeout"),
    MIDDLEWARE_SERVICE_ERROR("30100", 30100, "中间件服务出错", "Middleware Service Error"),
    RPC_SERVICE_ERROR("30110", 30110, "RPC 服务出错", "RPC Service Error"),
    RPC_SERVICE_WAS_NOT_FOUND("30111", 30111, "RPC 服务未找到", "RPC Service Was Not Found"),
    RPC_SERVICE_IS_NOT_REGISTERED("30112", 30112, "RPC 服务未注册", "RPC Service Is Not Registered"),
    INTERFACE_DOES_NOT_EXIST("30113", 30113, "接口不存在", "Interface Does Not Exist"),
    MESSAGE_SERVICE_ERROR("30120", 30120, "消息服务出错", "Message Service Error"),
    MESSAGE_DELIVERY_ERROR("30121", 30121, "消息投递出错", "Message Delivery Error"),
    MESSAGE_CONSUMPTION_ERROR("30122", 30122, "消息消费出错", "Message Consumption Error"),
    MESSAGE_SUBSCRIPTION_ERROR("30123", 30123, "消息订阅出错", "Message Subscription Error"),
    MESSAGE_GROUP_IS_NOT_FOUND("30124", 30124, "消息分组未查到", "Message Group Is Not Found"),
    CACHE_SERVICE_ERROR("30130", 30130, "缓存服务出错", "Cache Service Error"),
    KEY_LENGTH_EXCEEDS_THE_LIMIT("30131", 30131, "key 长度超过限制", "Key Length Exceeds The Limit"),
    VALUE_LENGTH_EXCEEDED_THE_LIMIT("30132", 30132, "value 长度超过限制", "Value Length Exceeded The Limit"),
    STORAGE_CAPACITY_IS_FULL("30133", 30133, "存储容量已满", "Storage Capacity Is Full"),
    UNSUPPORTED_DATA_FORMAT("30134", 30134, "不支持的数据格式", "Unsupported Data Format"),
    ERROR_CONFIGURING_THE_SERVICE("30140", 30140, "配置服务出错", "Error Configuring The Service"),
    NETWORK_RESOURCE_SERVICE_FAILS("30150", 30150, "网络资源服务出错", "Network Resource Service Fails"),
    VPN_SERVICE_ERROR("30151", 30151, "VPN 服务出错", "VPN Service Error"),
    CDN_SERVICE_FAILS("30152", 30152, "CDN 服务出错", "CDN Service Fails"),
    DOMAIN_NAME_RESOLUTION_SERVICE_FAILED("30153", 30153, "域名解析服务出错", "Domain Name Resolution Service Failed"),
    GATEWAY_SERVICE_ERROR("30154", 30154, "网关服务出错", "Gateway Service Error"),
    THIRD_PARTY_SYSTEM_TIMED_OUT("30200", 30200, "第三方系统执行超时", "Third Party System Timed Out"),
    RPC_EXECUTION_TIMEOUT("30210", 30210, "RPC 执行超时", "RPC Execution Timeout"),
    MESSAGE_DELIVERY_TIMED_OUT("30220", 30220, "消息投递超时", "Message Delivery Timed Out"),
    CACHE_SERVICE_TIMEOUT("30230", 30230, "缓存服务超时", "Cache Service Timeout"),
    CONFIGURING_SERVICE_TIMEOUT("30240", 30240, "配置服务超时", "Configuring Service Timeout"),
    DATABASE_SERVICE_TIMEOUT("30250", 30250, "数据库服务超时", "Database Service Timeout"),
    DATABASE_SERVICE_ERROR("30300", 30300, "数据库服务出错", "Database Service Error"),
    TABLE_DOES_NOT_EXIST("30311", 30311, "表不存在", "Table Does Not Exist"),
    COLUMN_DOES_NOT_EXIST("30312", 30312, "列不存在", "Column Does Not Exist"),
    AMBIGUOUS_COLUMNS_WHEN_JOIN_TABLES("30321", 30321, "多表关联中存在多个相同名称的列", "Ambiguous Columns When Join Tables"),
    DATABASE_DEADLOCK("30331", 30331, "数据库死锁", "Database Deadlock"),
    PRIMARY_KEY_CONFLICT("30341", 30341, "主键冲突", "Primary Key Conflict"),
    THIRD_PARTY_DR_SYSTEM_IS_TRIGGERED("30400", 30400, "第三方容灾系统被触发", "Third Party DR System Is Triggered"),
    THIRD_PARTY_SYSTEM_LIMITS_TRAFFIC("30401", 30401, "第三方系统限流", "Third Party System Limits Traffic"),
    THIRD_PARTY_FUNCTION_DEGRADATION("30402", 30402, "第三方功能降级", "Third Party Function Degradation"),
    NOTIFICATION_SERVICE_ERROR("30500", 30500, "通知服务出错", "Notification Service Error"),
    DESCRIPTION_THE_SMS_NOTIFICATION_SERVICE_FAILED("30501", 30501, "短信提醒服务失败", "Description The SMS Notification Service Failed"),
    VOICE_NOTIFICATION_SERVICE_FAILS("30502", 30502, "语音提醒服务失败", "Voice Notification Service Fails"),
    EMAIL_NOTIFICATION_SERVICE_FAILED("30503", 30503, "邮件提醒服务失败", "Email Notification Service Failed"),
    DATASOURCE_CONNECTION_FAILURE("60100", 60100, "数据源连接失败", "Datasource Connection Failure"),
    DATASOURCE_CONNECTION_TIMEOUT("60101", 60101, "数据源连接超时", "Datasource Connection Timeout"),
    DATASOURCE_LOGIN_FAILURE("60102", 60102, "数据源登录失败", "Datasource Login Failure"),
    UNSUPPORTED_DATA_SOURCE_TYPE("60103", 60103, "不支持的数据源类型", "Unsupported Data Source Type"),
    UNABLE_TO_PARSE_SQL("60104", 60104, "无法解析SQL", "Unable To Parse SQL"),
    UNSUPPORTED_DATA_SYNCHRONIZATION_MODE("60105", 60105, "不支持的数据同步模式", "Unsupported Data Synchronization Mode"),
    UNSUPPORTED_TIME_GRANULARITY("60106", 60106, "不支持的时间粒度", "Unsupported Time Granularity"),
    DATASOURCE_DOES_NOT_EXIST("60107", 60107, "不存在的数据源", "Datasource Does Not Exist"),
    FAILED_TO_EXECUTE_DATABASE_PROCEDURE("60108", 60108, "执行数据库存储过程失败", "Failed To Execute Database Procedure"),
    ERROR_ABOUT_JDBC_AND_DATABASE("60109", 60109, "JDBC与数据库执行相关错误", "Error About JDBC And Database"),
    NO_DATA_IN_THIS_CONDITION("60110", 60110, "当前条件下未查询到相关数据", "No Data In This Condition"),
    ENGINE_INTERNAL_ERROR("90100", 90100, "引擎内部出错", "Engine Internal Error"),
    NULL_POINTER_ERROR("90200", 90200, "空指针异常", "Null Pointer Error"),
    COMMAND_EXECUTE_FAIL("90300", 90300, "命令执行失败", "Command Execute Fail"),
    UNKNOWN_ERROR("90500", 90500, "未知错误", "Unknown Error"),
    SERVICE_INTERNAL_EXCEPTION("90600", 90600, "接口服务内部异常，请联系管理员", "Service Internal Exception");


    /**
     * 错误码 (String)
     */
    public final String code;
    /**
     * 错误码 (int)
     */
    public final int codeNo;
    /**
     * 中文信息
     */
    public final String msg;
    /**
     * 英文信息
     */
    public final String msgEn;


    StatusCode(String code, int codeNo, String msg, String msgEn) {
        this.code = code;
        this.codeNo = codeNo;
        this.msg = msg;
        this.msgEn = msgEn;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public int getCodeNo() {
        return codeNo;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getMsgEn() {
        return msgEn;
    }

}
