package com.tencent.wxcloudrun.common;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaowen
 * @version 2022/1/19 19:21
 */
@Getter
public enum CommonErrorCode {

    //1打头是微信错误，其他是程序错误
    WX_LOGIN_BUSY(1002,"系统繁忙，此时请开发者稍候再试","微信小程序繁忙，请稍候再试"),
    WX_LOGIN_INVALID_CODE(1003,"无效的code","授权失败，请检查微信账号是否正常"),
    WX_LOGIN_FREQUENCY_REFUSED(1004,"请求太频繁，一分钟不能超过100次","请勿多次重复授权"),
    WX_LOGIN_UNKNOWN_ERROR(1005,"微信授权未知错误","微信异常，请稍后再试"),
    WX_APPSECRET_INVALID(1006,"AppSecret 错误或者 AppSecret 不属于这个小程序","系统异常，请稍后再试"),
    WX_GRANTTYPE_MUSTBE_CLIENTCREDENTIAL(1007,"请确保 grant_type 字段值为 client_credential","系统异常，请稍后再试"),
    WX_APPID_INVALID(1008,"不合法的 AppID","系统异常，请稍后再试"),
    WX_CONTENT_ILLEGAL(1009,"内容安全校验不通过","内容含有违法违规内容，请修改"),
    WX_CONTENT_SECURITY_FORMAT_ERROR(1010,"内容安全校验格式不合法","系统异常，请稍后再试"),

    //微信支付回调
    WX_NOTIFY_RESULT_NULL(1011,"回调结果为空","回调结果为空"),
    WX_NOTIFY_RESULT_UNEXPECTED(1011,"回调结果不是success","回调结果不是success"),

    //微信订阅消息
    WX_SUBSCRIBE_SEND_NULL(140000,"订阅消息返回体为空","系统异常，请稍后再试"),
    WX_SUBSCRIBE_SEND_40003(140003,"touser字段openid为空或者不正确","系统异常，请稍后再试"),
    WX_SUBSCRIBE_SEND_40037(140037,"订阅模板id为空或不正确","系统异常，请稍后再试"),
    WX_SUBSCRIBE_SEND_43101(143101,"用户拒绝接受消息，如果用户之前曾经订阅过，则表示用户取消了订阅关系","系统异常，请稍后再试"),
    WX_SUBSCRIBE_SEND_47003(147003,"模板参数不准确，可能为空或者不满足规则，errmsg会提示具体是哪个字段出错","系统异常，请稍后再试"),
    WX_SUBSCRIBE_SEND_41030(141030,"page路径不正确，需要保证在现网版本小程序中存在，与app.json保持一致","系统异常，请稍后再试"),
    //微信退款

    //微信二维码
    WX_QRCODE_UNAUTHORIZED(1012,"暂无生成权限","系统异常，请稍后再试"),
    WX_QRCODE_TOO_FREQUENT(1013,"调用分钟频率受限(目前5000次/分钟，会调整)，如需大量小程序码，建议预生成","系统繁忙，请稍后重试"),

    USER_NOT_EXIST(2001,"用户不存在","用户不存在"),
    SYSTEM_ERROR(2002,"系统错误","系统错误，请重试"),
    INVALID_SESSION(2006,"会话丢失","登录已失效，请重新登录"),
    SCHOOL_UNAUTHORIZED(2007,"未通过学校认证","尚未进行校园认证，请先认证"),
    INVALID_PICTURE_TYPE(2008,"无效的图片类型（必须是goods或advice）","图片上传出错，请重试"),
    TEL_USED_ERROR(2009,"手机号已注册","请前往登录"),
    VERIFY_FAILED(2010,"验证失败","请重试"),
    LOGIN_FAILED(2011,"登录失败","用户名或密码错误"),
    PARAMS_INVALID(2012,"存在有误的参数","请重试"),
    UNSIGNED_USER(2013,"未注册用户","请前往注册"),
    INVALID_PHONE(2014,"无效手机号","请输入正确的手机号"),
    UPDATE_FAIL(2015,"更新失败，出现竞态条件","请稍后重试"),
    USER_NOT_ADMIN(2016,"用户非管理员","用户非管理员"),
    NEED_SESSION_ID(2017,"未传入sessionId","请传入会话id"),
    LOGIN_HAS_OVERDUE(2018,"登录已过期","登录已过期"),
    SESSION_IS_INVALID(2019,"该session数据库里没有","请在header里key为session处对应有效的sessionId"),
    DUPLICATE_DATABASE_INFORMATION(2020,"重复的数据库信息","信息添加失败，请重试"),
    UPLOAD_FILE_FAIL(2021,"上传文件失败","请检查网络状况后稍后重试"),
    FILENAME_CAN_NOT_BE_NULL(2022,"文件名不能为空","请取一个有后缀的文件名"),
    EXECUTION_FAIL(2023,"方法执行错误","请稍后重试"),
    DATA_NOT_EXISTS(2024, "无效的数据库信息", "数据库查询失败"),
    INVALID_PARAM(2025, "非法参数", "参数非法，请检查输入"),
    OPERATION_TOO_FREQUENT(2026, "操作过于频繁", "操作过于频繁，请稍后再试"),
    USERNAME_HAS_BEEN_SIGNED_UP(2027,"用户名已被注册","请尝试新的用户名"),
    PASSWORD_NOT_RIGHT(2028,"密码错误","密码错误，请重新输入密码"),
    TWO_INPUTED_PASSWORDS_NOT_THE_SAME(2028,"两次输入的密码不一致","两次输入密码不一致，请重新输入"),
    READ_FILE_ERROR(2029,"读取文件失败","请检查文件格式后重新上传文件"),
    FILE_NOT_EXIST(2030,"文件不存在","文件不存在"),
    DOWNLOAD_FILE_FAILED(2031,"下载失败","请在浏览器地址栏中输入链接来测试，或者检查网络或系统状况"),
    SCRIPT_NOT_EXIST(2032,"剧本不存在","剧本不存在"),
    CAN_NOT_MODIFY(2033, "不可修改", "无权修改"),
    ACCOUNT_STATUS_NORMAL(2034, "账号状态正常","账号状态正常，无需申诉"),
    HAVE_NO_PERMISSION(2035, "无权进行本次操作","无权进行本次操作"),
    TELEPHONE_HAS_BEEN_SIGNED_UP(2036,"手机号已被注册","手机号已被注册"),
    PASSWORD_NOT_QUANTIFIED(2037,"密码不符合要求","密码不符合要求"),
    SCRIPT_ALREADY_EXIST(2038,"剧本已存在","剧本已存在，请尝试编辑或删除已有剧本"),
    ZIPPATH_ERROR(2039,"打包路径错误，必须以.zip结尾","打包路径错误，必须以.zip结尾"),
    MESSAGE_NOT_EXIST(2040,"消息不存在","消息不存在"),
    MESSAGE_NOT_QUALIFIED(2041,"申请非本人发起，无权限进行相关请求","申请非本人发起，无权限进行相关请求"),
    SCRIPT_PATH_NOT_EXIST(2042,"剧本路径不存在","剧本路径不存在"),
    PARAMETERS_INCOMPLETE(2043,"参数不全","参数不全"),
    CODING_ERROR(2044,"编码错误","编码错误"),
    PARAMETER_ERROR(2045, "参数错误", "请检查传入参数错误"),
    BONUS_NOT_EXIST(2046, "奖品不存在", "奖品不存在"),
    ROLE_NOT_EXIST(2047, "角色不存在", "角色不存在"),
    BIND_ALREADY_EXSIT(2048, "已经绑定", "用户已经与该剧本的角色绑定"),
    BIND_NOT_EXIST(2049, "绑定不存在", "绑定不存在"),
    BONUS_ALREADY_COLLECTED(2050, "该彩蛋已收集", "该彩蛋已收集"),
    STAKE_NOT_EXIST(2051, "发布不存在", "发布不存在"),
    STAKE_NOT_BEGIN(2052, "发布未开始", "发布未开始"),
    STAKE_ALREADY_END(2053, "发布已结束", "发布已结束"),
    QUOTA_NOT_ENOUGH(2054, "额度不足", "额度不足"),
    USER_NOT_PUBLISHER(2055, "用户不是发行", "用户不是发行"),
    STAKE_ALREADY_BEGIN(2056, "发布已开始", "发布已开始"),
    COLLECTION_NOT_EXIST(2057, "收藏不存在", "收藏不存在"),
    CODE_NOT_EXIST(2058, "抽奖码不存在", "抽奖码不存在"),
    EXHIBITION_NOT_EXIST(2059, "展会不存在", "展会不存在"),
    SWEEP_ALREADY(2060, "已经收集该发行", "已经收集该发行"),
    MERCHANT_NOT_EXIST(2061, "发行不存在", "发行不存在"),
    KEY_ALREADY_EXIST(2062, "key已存在", "key已存在"),
    BANNER_NOT_EXIST(2063, "banner不存在", "banner不存在"),
    STAKE_TIME_CONFLICT(2064, "发布时间冲突", "发布时间冲突"),
    COLLECTION_ALREADY_EXIST(2065, "收藏已存在", "收藏已存在"),
    CHIP_ID_EXIST(2066, "芯片ID已存在", "芯片ID已存在"),
    COUNT_ERROR(2067, "数量错误", "数量错误"),
    PREVIEW_COUNT_LIMIT(2068, "该时间段公车已达上限", "该时间段公车已达上限"),
    PUBLISH_COUNT_LIMIT(2069, "今日预约已达上限", "今日预约已达上限"),
    PREVIEW_NOT_EXIST(2070, "预约不存在", "预约不存在"),
    PREVIEW_ALREADY_STARTED(2071, "预约已开始", "预约已开始"),
    PREVIEW_ALREADY_ENDED(2072, "预约已结束", "预约已结束"),
    PREVIEW_NOT_STARTED(2073, "预约未开始", "预约未开始"),
    STAKE_ALREADY_CANCEL(2074, "发布已取消", "发布已取消"),
    PREVIEW_ALREADY_CANCELED(2075, "预约已取消", "预约已取消"),
    SEND_MESSAGE_ERROR(2076, "发送消息失败", "发送消息失败"),
    NO_USER_COLLECT_SCRIPT(2077, "未有用户收藏该剧本", "未有用户收藏该剧本"),
    SCRIPT_HAS_BEEN_NOTIFIED(2078, "剧本已被通知", "剧本已被通知"),
    EXHIBIT_SCRIPT_NOT_EXIST(2079, "展会剧本不存在", "展会剧本不存在"),
    COMMENT_LIKE_NOT_EXIST(2080, "评论点赞不存在", "评论点赞不存在"),
    COMMENT_LIKE_ALREADY_EXIST(2081, "评论点赞已存在", "评论点赞已存在"),
    COMMENT_NOT_EXIST(2082, "评论不存在", "评论不存在"),
    SCRIPT_PICTURE_EXIST(2083, "剧本图片已存在", "剧本图片已存在"),
    FILE_UPLOAD_ERROR(2084, "文件上传失败", "文件上传失败"),
    EXHIBITION_ID_IS_NULL(2085, "展会ID为空", "展会ID为空"),
    SCRIPT_ID_IS_NULL(2086, "剧本ID为空", "剧本ID为空"),
    BANNER_TYPE_ERROR(2087, "banner类型错误", "banner类型错误"),
    UNKNOWN_ERROR(2088, "未知错误", "未知错误"),
    PERMISSION_DENIED(2089, "权限不足", "权限不足"),
    ;

    /**
     * 错误码
     */
    private final Integer errorCode;

    /**
     * 错误原因（给开发看的）
     */
    private final String errorReason;

    /**
     * 错误行动指示（给用户看的）
     */
    private final String errorSuggestion;

    CommonErrorCode(Integer errorCode, String errorReason, String errorSuggestion) {
        this.errorCode = errorCode;
        this.errorReason = errorReason;
        this.errorSuggestion = errorSuggestion;
    }

    @Override
    public String toString() {
        return "CommonErrorCode{" +
                "errorCode=" + errorCode +
                ", errorReason='" + errorReason + '\'' +
                ", errorSuggestion='" + errorSuggestion + '\'' +
                '}';
    }

    //use for json serialization
    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("errorCode",errorCode);
        map.put("errorReason",errorReason);
        map.put("errorSuggestion",errorSuggestion);
        return map;
    }


}