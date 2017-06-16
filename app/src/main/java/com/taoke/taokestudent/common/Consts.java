package com.taoke.taokestudent.common;

import android.os.Environment;

/**
 * 常量
 */
public class Consts {
    /**
     * 网络请求URL
     */
    public class NetUrl {
        public static final String BASE_URL = "http://192.168.1.166/";
        /** 91条款 */
        public static final String TERM_91 = "http://www.91taoke.com/index.php?m=Help&a=xieyi";
        /** 个人中心：发送验证码 */
        public static final String PERSONAL_CENTER_SEND_VALIDATE_CODE = BASE_URL + "RegisterInterface/send_yanzhengma";
        /** 个人中心：注册 */
        public static final String PERSONAL_CENTER_REGISTER = BASE_URL + "RegisterInterface/register";
        /** 个人中心：登录 */
        public static final String PERSONAL_CENTER_LOGIN = BASE_URL + "LoginInterface/studentLogin";
        /** 个人中心：根据UID获取用户信息 */
        public static final String PERSONAL_CENTER_LOAD_USER_INFO_BY_UID = BASE_URL + "LoginInterface/getUserInfo";
        /** 个人中心：修改用户信息 */
        public static final String PERSONAL_CENTER_UPDATE_USER_INFO = BASE_URL + "LoginInterface/updateUserInfo";
        /** 个人中心：修改密码 */
        public static final String PERSONAL_CENTER_UPDATE_PASSWORD = BASE_URL + "LoginInterface/updatePassword";
        /** 个人中心：充值-生成支付订单 */
        public static final String PERSONAL_CENTER_RECHARGE_CREATE_ORDER = BASE_URL + "RechargeInterface/createOrder";
        /** 个人中心：获取家长列表 */
        public static final String PERSONAL_CENTER_LOAD_CONNECTION_ACCOUNT_LIST = BASE_URL + "StudentInterface/getParentList";
        /** 个人中心：绑定家长 */
        public static final String PERSONAL_CENTER_BIND_CONNECTION_ACCOUNT = BASE_URL + "StudentInterface/binDing";
        /** 个人中心：解除绑定家长 */
        public static final String PERSONAL_CENTER_RELIEVE_CONNECTION_ACCOUNT = BASE_URL + "StudentInterface/relieve";

        /** 微课视频：获取基础分类 */
        public static final String MICRO_LESSON_VIDEO_GET_BASIC_CLASS = BASE_URL + "WeikeInterface/get_basic_class";
        /** 微课视频：获取模块分类 */
        public static final String MICRO_LESSON_VIDEO_GET_MOKUAI = BASE_URL + "WeikeInterface/get_mokuai";
        /** 微课视频：获取资源分类 */
        public static final String MICRO_LESSON_VIDEO_GET_DATA_TYPE = BASE_URL + "WeikeInterface/get_data_type";
        /** 微课视频-课程列表 */
        public static final String MICRO_LESSON_VIDEO_LESSON_LIST = BASE_URL + "WeikeInterface/getList";
        /** 微课视频-购买课程 */
        public static final String MICRO_LESSON_VIDEO_PAY_WEIKE = BASE_URL + "WeikeInterface/pay_weike";
        /** 微课视频-视频详情-免费课程列表 */
        public static final String MICRO_LESSON_VIDEO_DETAIL_FREE = BASE_URL + "WeikeInterface/getFreeView";
        /** 微课视频-视频详情-收费课程列表 */
        public static final String MICRO_LESSON_VIDEO_DETAIL_CHARGE = BASE_URL + "WeikeInterface/getChargeView";
        /** 微课视频-视频详情-收费课程播放地址 */
        public static final String MICRO_LESSON_VIDEO_DETAIL_CHARGE_VIDEO_URL = BASE_URL + "WeikeInterface/getViewUrl";
        /** 习题微课-课程详情-章节列表 */
        public static final String MICRO_EXERCISE_CHAPTER_LIST = BASE_URL + "WeikeInterface/getXitiweikeClass";
        /** 习题微课-课程详情-习题列表 */
        public static final String MICRO_EXERCISE_QUESTION_LIST = BASE_URL + "WeikeInterface/getXitiweikeInfo";
        /** 我的课程-课程列表 */
        public static final String MICRO_MY_LESSON_LIST = BASE_URL + "WeikeInterface/getBuyList";

        /** 在线做题：获取基础分类 */
        public static final String ONLINE_EXERCISE_GET_BASIC_CLASS = BASE_URL + "ShitikuInterface/get_basic_class";
        /** 在线做题：获取模块分类 */
        public static final String ONLINE_EXERCISE_GET_MOKUAI = BASE_URL + "ShitikuInterface/get_mokuai";
        /** 在线做题：获取章节 */
        public static final String ONLINE_EXERCISE_GET_CHAPTER = BASE_URL + "ShitikuInterface/get_zhangjie";
        /** 在线做题：试题列表 */
        public static final String ONLINE_EXERCISE_GET_QUESTION_LIST = BASE_URL + "ShitikuInterface/getShiTiList";
        /** 在线做题：上传答题结果 */
        public static final String ONLINE_EXERCISE_SUBMIT_RESULT = BASE_URL + "ShitikuInterface/submitResult";
        /** 在线做题：答题报告 */
        public static final String ONLINE_EXERCISE_GET_RESULT = BASE_URL + "ShitikuInterface/getResult";
    }

    /**
     * 事件类型
     */
    public class EventType {
        /** 登录成功 */
        public static final int EVENT_LOGIN_SUCCESS = 1;
        /** 退出账号 */
        public static final int EVENT_EXIT_ACCOUNT = 2;
        /** 购买课程成功 */
        public static final int EVENT_BUY_LESSON_SUCCESS = 3;
        /** 下载失败 */
        public static final int EVENT_DOWNLOAD_ERROR = 4;
        /** 下载开始 */
        public static final int EVENT_DOWNLOAD_START = 5;
        /** 下载进度 */
        public static final int EVENT_DOWNLOAD_PROGRESS = 6;
        /** 下载完成 */
        public static final int EVENT_DOWNLOAD_FINISH = 7;
        /** 等待下载 */
        public static final int EVENT_DOWNLOAD_WAIT = 8;
        /** 更新用户数据 */
        public static final int EVENT_UPDATE_USER = 9;
        /** 更新关联账号 */
        public static final int EVENT_UPDATE_CONNECTION_ACCOUNT = 10;
        /** 在线做题试题换页 */
        public static final int ONLINE_EXERCISE_CHANGE_QUESTION_PAGE = 11;
    }

    /**
     * 等待对话框提示的信息
     */
    public class WaitDialogMessage {
        /** 发送验证码 */
        public static final String SEND_VALIDATE_CODE = "正在请求验证码...";
        /** 注册 */
        public static final String REGISTER = "注册中...";
        /** 绑定账号 */
        public static final String BINDING = "绑定账号中...";
        /** 登录 */
        public static final String LOGIN = "登录中...";
        /** 修改个人信息 */
        public static final String UPDATE_USER_INFO = "修改个人信息中...";
        /** 正在生成订单 */
        public static final String CREATE_ORDER = "正在生成订单...";
        /** 数据加载中 */
        public static final String DATA_LODING = "数据加载中...";
        /** 购买课程 */
        public static final String BUY_LESSONING = "购买课程中...";
        /** 答案上传中 */
        public static final String SUBMIT_RESULT = "答案上传中...";
        /** 报告加载中 */
        public static final String REPORT_LOADING = "报告加载中...";
    }

    /**
     * 偏好设置
     */
    public class SPKeys {
        /** uid */
       public static final String UID_KEY = "UID_KEY";
    }


    /**
     * 侧滑菜单项索引
     *
     */
    public class MenuItemIndex {
        /** 微课视频 */
        public static final int MICRO_LESSON_VIDEO = 0;
        /** 在线做题 */
        public static final int ONLINE_EXERCISE = 1;
        /** 作业与考试 */
        public static final int ASSIGNMENT_EXAM = 2;
        /** 名师答疑 */
        public static final int TEACHER_ANSWER = 3;
        /** 消息 */
        public static final int MESSAGE = 4;
        /** 个人中心 */
        public static final int PERSONAL_CENTER = 5;
    }

    /**
     * 性别
     */
    public class Sex {
        /** 男 */
        public static final String MALE = "1";
        /** 女 */
        public static final String FEMALE = "2";
    }

    /**
     * 支付方式
     */
    public class PayType {
        /** 支付宝 */
        public static final String ZFB = "1";
        /** 微信 */
        public static final String WX = "12";
    }

    /**
     * 基础分类级别
     */
    public class BaseClassLevel {
        /** 学段 */
        public static final String XDLV = "1";
        /** 年级 */
        public static final String NJLV = "2";
        /** 学科 */
        public static final String XKLV = "3";
        /** 版本 */
        public static final String BBLV = "4";
        /** 模块 */
        public static final String MKLV = "5";
    }

    /**
     * 微课视频是否购买
     */
    public class MicroPayType {
        /** 已购买 */
        public static final String PAY = "1";
        /** 未购买 */
        public static final String UNPAY = "2";
    }

    /**
     * Intent参数key
     */
    public class IntentExtraKey {
        /** 微课视频分类 */
        public static final String MICRO_BASE_CLASS = "MICRO_BASE_CLASS";
        /** 微课视频详情 */
        public static final String MICRO_DETAIL = "MICRO_DETAIL";
        /** 微课视频下载 */
        public static final String MICRO_DOWNLOAD = "MICRO_DOWNLOAD";
        /** 下载文件夹 */
        public static final String DOWNLOAD_FOLDER = "DOWNLOAD_FOLDER";
        /** 视频URI */
        public static final String VIDEO_URI = "VIDEO_URI";
        /** 视频名 */
        public static final String VIDEO_NAME = "VIDEO_NAME";
        /** 课程id */
        public static final String LESSON_ID = "LESSON_ID";
        /** 课程名 */
        public static final String LESSON_NAME = "LESSON_NAME";
        /** 手机号 */
        public static final String PHONE_NUM = "PHONE_NUM";
        /** 用户信息 */
        public static final String USER_INFO = "USER_INFO";
        /** 在线做题分类 */
        public static final String ONLINE_EXERCISE_BASE_CLASS = "ONLINE_EXERCISE_BASE_CLASS";
        /** 在线做题单元ID */
        public static final String ONLINE_EXERCISE_UNIT_ID = "ONLINE_EXERCISE_UNIT_ID";
        /** 在线做题单元名 */
        public static final String ONLINE_EXERCISE_UNIT_NAME = "ONLINE_EXERCISE_UNIT_NAME";
        /** 在线做题题目数量 */
        public static final String ONLINE_EXERCISE_QUESTION_NUM = "ONLINE_EXERCISE_QUESTION_NUM";
        /** 在线做题试题列表 */
        public static final String ONLINE_EXERCISE_QUESTION_LIST = "ONLINE_EXERCISE_QUESTION_LIST";
        /** 在线做题答题记录ID */
        public static final String ONLINE_EXERCISE_RECORD_ID = "ONLINE_EXERCISE_RECORD_ID";
    }

    /**
     * Intent参数value
     */
    public class IntentExtraValue {
        /** 昵称 */
        public static final String NICKNAME = "NICKNAME";
        /** 真实姓名 */
        public static final String REALNAME = "REALNAME";
        /** 性别 */
        public static final String SEX = "SEX";
        /** 年级 */
        public static final String NIANJI = "NIANJI";
    }

    /**
     * 下载状态
     */
    public class DownloadType {
        /* 等待下载 */
        public static final String WAIT = "等待下载";
        /* 开始下载 */
        public static final String START = "开始下载";
        /* 正在下载 */
        public static final String DOWNLOADING = "正在下载";
        /* 下载失败 */
        public static final String ERROR = "下载失败";
        /* 下载完成 */
        public static final String FINISH = "下载完成";
        /* 暂停下载 */
        public static final String STOP = "暂停下载";
    }

    /**
     * 习题微课是否免费
     */
    public class MicroExerciseChapterFree {
        /** 免费 */
        public static final String FREE = "1";
        /** 收费 */
        public static final String CHARGE = "2";
    }

    /**
     * 微课视频基础分类记录类型
     */
    public class MicroBaseClassType {
        /** 微课视频 */
        public static final String MICRO_LESSON_VIDEO = "1";
        /** 习题微课 */
        public static final String EXERCISE_LESSON = "2";
        /** 我的课程 */
        public static final String MY_LESSON = "3";
    }

    /**
     * 试题题型
     */
    public class QuestionType {
        /** 多选 */
        public static final String MULTIPLE_CHOICE = "1";
        /** 单选 */
        public static final String SINGLE_CHOICE = "2";

    }

    /**
     * 基础分类：学段父id
     */
    public static final String BASE_CLASS_XUEDUAN_FID = "1";

    /**
     * 微课视频分类：习题微课
     */
    public static final String MICRO_DATA_TYPE_EXERCISE = "4";

    /**
     * 网络请求失败
     */
    public static final String NET_REQUEST_EXCEPTION = "数据加载失败";

    /**
     * 文件下载基路径
     */
    public static final String DOWNLOAD_BASE_PATH = Environment.getExternalStorageDirectory() + "/taokestudent/";
}
