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
        NO_MOBILE(201);

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
        SMS_FAIL("短信发送失败");

        private String value;

        Message(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
