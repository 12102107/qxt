package io.renren.common.utils;

/**
 * 常量
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月15日 下午1:23:52
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;

    /**
     * 菜单类型
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 响应结果
     */
    public enum Result {
        //请求成功
        SUCCESS(200),
        //请求失败
        FAIL(500),
        //第三方用户未绑定手机号
        NO_MOBILE(201),

        ERROR_MOBILE(202),
        //验证码查询结果为空
        SMS_CODE_NULL(203),
        //验证码不正确
        SMS_CODE_ERROR(204),
        //验证码过期
        SMS_CODE_EXPIRE(205),
        //验证码正确
        SMS_CODE_CORRECT(206),
        //电话号码已被注册
        REG_MOBILE(207),
        //电话号码未被注册过
        NO_REG_MOBILE(208),
        //第三方帐号已存在
        COOPERATION_EXIST(209),
        //第三方帐号不存在
        COOPERATION_NOT_EXIST(210),
        //Session过期
        SESSION_EXPIRE(211),
        //Session错误
        SESSION_ERROR(212),
        //获取第三方信息错误
        INFO_ERROR(213);

        private int value;

        Result(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 响应消息
     */
    public enum Message {
        //请求成功
        SUCCESS("成功"),
        //请求失败
        FAIL("失败"),
        //未知错误
        UNKNOWN("系统异常"),
        //第三方用户未绑定手机号
        NO_MOBILE("未绑定手机号"),
        //短信发送失败
        SMS_FAIL("短信发送失败"),

        ERROR_MOBILE("手机号码或密码错误"),

        REG_MOBILE("手机号码已被注册"),

        NO_REG_MOBILE("手机号码未被注册"),
        //短信验证码不正确
        SMS_CODE_ERROR("短信验证码不正确"),
        //短信验证码已过期
        SMS_CODE_EXPIRE("短信验证码已过期"),
        //第三方帐号已存在
        COOPERATION_EXIST("第三方帐号已存在"),
        //第三方帐号不存在
        COOPERATION_NOT_EXIST("第三方帐号不存在"),
        //session错误
        SESSION_ERROR("Session错误");

        private String value;

        Message(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
