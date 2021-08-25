package com.google.springboot.commom;

public class OrderConstants {

    public static final Integer H5_PARAM_PRE = 2333;

    public static final Integer H5_PARAM_OTH = 1221;

    public static final String FULL_RED_MSG = "满%s减";

    public static final String FROM_USERNAME_MSG = "来自{userName}的优惠券";

    public static final String FROM_FRIEND_MSG = "来自好友:%s";

    public static final String DISCOUNT_ZHE_MSG = "折";

    public static final String DISCOUNT_RED_MSG = "立减";

    public static final String PRESALE_DIS_MSG = "定金立减";

    public static final String BAOTUANGOU_LABEL = "抱团购立减";

    public static final String NO_AVAILABLE_MSG = "暂无可用";

    public static final String SELECT_MSG = "请选择";

    public static final String LUCK_BAG_NUM_MSG = "每张订单只能买10个福袋哦~";

    public static final String DIST_CHECK_ERROR_MSG = "该地址没有可配送的商品，请重新选择！";

    public static final Integer SUCCESS_CODE = 0;

    //限购
    public static final String MEM_LEV_MSG = "MEM_LEV_MSG";

    public static final String VIP_LEV_MSG = "VIP_LEV_MSG";

    public static final Integer OUT_OF_LIMIT_KEY = 1;

    public static final Integer NOT_MATCH_LIMIT_KEY = 2;

    public static final Integer WHITE_PRE_LIMIT_KEY = 3;

    public static final Integer WHITE_PRE_ORDER_LIMIT_KEY = 4;

    public static final Integer WHITE_NOT_BUY = 5;

    public static final String LIMIT_NO_VIP_MSG = "你还不是大会员，无法购买(Ｔ▽Ｔ)";

    public static final String LIMIT_NO_VIP_YEAR_MSG = "你还不是年度大会员，无法购买(Ｔ▽Ｔ)";

    public static final String LIMIT_NO_LEVEL_MSG = "当前等级不够，无法购买(Ｔ▽Ｔ)";


    public static final String SYSTEM = "Systems";

    public static final String BUY_COMMENT = "创建订单,买家留言";

    public static final String PAYMENT_SUCCESS = "SUCCESS";

    public static final Long NO_TXID = 0L;

    public static final String PHONE_REG = "^1\\d{10}$";

    /**
     * 子订单类型
     */
    // 实物
    public static final byte ORDER_TYPE_REAL = 0;
    // 虚拟
    public static final byte ORDER_TYPE_UNREAL = 1;
    // 预售
    public static final byte ORDER_TYPE_PRESALE = 2;

    /**
     * 大订单类型
     */
    // 购物车
    public static final byte CART_ORDER_TYPE_CART = 0;
    // 单商品
    public static final byte CART_ORDER_TYPE_SINGLE = 1;
    // 预售
    public static final byte CART_ORDER_TYPE_PRESALE = 2;
    public static final byte CART_ORDER_TYPE_BOOK = 3;//先行预订
    public static final byte CART_ORDER_TYPE_MNG = 4;//运营端创单
    public static final byte CART_ORDER_TYPE_FULL_PRESALE = 5;//全款预售
    public static final byte CART_ORDER_TYPE_BOX_CABINET = 7;//盒柜订单
    public static final byte CART_ORDER_TYPE_MIX = 9;//混合订单

    /**
     * 大订单对于处理实例
     */
    // 购物车
    public static final String CART_ORDER_TYPE_CART_COMPONENT = "CART_ORDER_SERVICE";
    // 单商品
    public static final String CART_ORDER_TYPE_SINGLE_COMPONENT = "SINGLE_ORDER_SERVICE";
    // 预售
    public static final String CART_ORDER_TYPE_PRESALE_COMPONENT = "PRESALE_ORDER_SERVICE";

    /**
     * 预售相关子状态
     */

    public static final byte SUBSTATUS_NO_MEAN = 0;

    // 待支付
    public static final byte PRESALE_UNDER_PAY_SUBSTATUS_FRONT_MONEY = 1;
    public static final byte PERSALE_UNDER_PAY_SUBSTATUS_FINAL_MONEY_DUE_DATE = 2;
    public static final byte PERSALE_UNDER_PAY_SUBSTATUS_FINAL_MONEY_UNDER_PAY = 3;

    public static final byte ORDER_DELIVER_RECEIVE_TO_COMMENT = 41; //订单已收货待评价
    public static final byte ORDER_DELIVER_RECEIVE_COMMENT_DONE = 42; //订单已收货已评价
    public static final byte ORDER_DELIVER_RECEIVE_COMMENT_CLOSE = 43; //订单已收货评价关闭

    public static final byte PRESALE_ORDER_CANCEL_SUBSTATUS_SYS_CANCEL_FRONT_MONEY = 1;
    public static final byte PRESALE_ORDER_CANCEL_SUBSTATUS_USER_CANCEL_FRONT_MONEY = 2;
    public static final byte PRESALE_ORDER_CANCEL_SUBSTATUS_SYS_CANCEL_FINAL_MONEY = 3;
    public static final byte PRESALE_ORDER_CANCEL_SUBSTATUS_USER_CANCEL_FINAL_MONEY = 4;

    /**
     * 未付款取消
     */
    public static final byte ORDER_CANCEL_NOT_PAY = 51;
    /**
     * 先行预定取消
     */
    public static final byte ORDER_CANCEL_BOOK = 52;
    /**
     * 未支付尾款取消
     */
    public static final byte ORDER_CANCEL_SALE = 53;

    /***
     * 盲盒订单待支付状态
     */
    public static final byte ORDER_EXTRACT_BLIND_BOX = 44;


    /**
     * 预售商品相关状态
     */
    public static final byte DEPOSIT_TYPE_PERCENT = 0;
    public static final byte DEPOSIT_TYPE_FIXED = 1;
    public static final byte DEPOSIT_TYPE_ALL = 2;

    /**
     * 预售 order seq
     */

    public static final byte PRESALE_ORDER_SEQ_FRONT = 0;

    public static final byte PRESALE_ORDER_SEQ_FINAL = 1;

    /**
     * 订单隐藏状态
     */
    public static final byte ORDER_HIDDEN = 1;
    public static final byte ORDER_NORMAL = 0;

    /**
     * 配置状态
     */

    public static final Byte DELIVER_TYPE_NOTHING = 0;
    public static final Byte DELIVER_TYPE_FAST = 1;

    /**
     * 投递
     */
    public static final Integer DELIVER_ID_PRESALE_FRONT_NO_MEANING = 0;

    // 支付相关

    public static final String CART_ORDER_TYPE_DESC = "cartOrderId";
    public static final String CART_ORDER_ID_DESC = "orderId";

    public static final String CALLBACK_RESPONSE_SUCCESS = "SUCCESS";
    public static final String CALLBACK_RESPONSE_FAIL = "FAIL";

    public static final String ORDER_CART_SEQ_ID = "order_cart";

    public static final Long DEFAULT_CART_ORDER_ID = 0L;

    //订单是否逻辑删除
    public static final Byte LOGIC_DELETED = (byte) 1;

    //订单拆单门槛
    public static final Integer DEFAULT_DEPART_MONEY = 5000 * 100;
    public static final Integer BANMA_DEPART_MONEY = 3000 * 100;

    //异步下单
    public static final String ORDER_CREATE_LOCK_KEY = "o_c_k_";
    public static final long ORDER_CREATE_LOCK_EXPIRE_TIME = 31000L;
    public static final String ORDER_REPEAT_LOCK_KEY = "o_r_";
    public static final long ORDER_REPEAT_LOCK_EXPIRE_TIME_START = 60000L;
    public static final long ORDER_REPEAT_LOCK_EXPIRE_TIME_END = 120000L;
    public static final long ASYNC_ORDER_EXPIRE_TIME_SECOND_START = 60L;
    public static final long ASYNC_ORDER_EXPIRE_TIME_SECOND_END = 120L;
    public static final long ASYNC_INTERVAL_MILLISECONDS = 28000L;

    //退款单
    public static final String ORDER_REFUND_LOCK_KEY = "r_k_";
    public static final long ORDER_REFUND_LOCK_EXPIRE_TIME = 10000L; //10s

    //延期出荷
    public static final String ITEMS_DELAY_NOTIFY_LOCK_KEY = "i_d_n_k_";
    public static final long ITEMS_DELAY_NOTIFY_EXPIRE_TIME = 5000L; //10s

    //通知订单中心
    public static final String SYNC_ORDER_LOCK_KEY = "s_o_c_l_";
    public static final long  SYNC_ORDER_EXPIRE_TIME = 25 * 60 *60 *1000L; //一天

    //插入购物车
    public static final String INSERT_CART_LOCK_KEY = "i_c_l_";
    //批量加车
    public static final String BATCH_INSERT_CART_LOCK_KEY = "b_i_c_l_";

    public static final long  INSERT_CAR_EXPIRE_TIME = 3 *1000L; //3s
    //创建盒柜商品
    public static final String CREATE_BLIND_BOX = "c_b_b_";
    public static final long CREATE_BLIND_BOX_EXPIRE_TIME = 10 * 1000L;//10秒
    /**
     * 客服操作类型为1
     */
    public static final Byte OPT_TYPE_KEFU =  (byte) 1;
    /**
     * 系统或用户默认为0
     */
    public static final Byte OPT_TYPE_DEFAULT =  (byte) 0;


    public static final String SHOP_DETAIL = "shopDetail";
    public static final String SHOP_ADD_CART = "shop_add_cart";

    //申报状态
    public static final Byte NOT_DECLARE = (byte)0; // 未申报
    public static final Byte IN_DECLARE = (byte)1; // 申报中
    public static final Byte DECLARE_SUC = (byte)2; // 申报成功
    public static final Byte DECLARE_FAIL = (byte)3; // 申报失败
    public static final Byte CLEARANCE_FAIL= (byte)4; // 清关失败
    //支付渠道
    public static final String PAID_CHANNEL = "paidChannel";
    //信用付
    public static final String CREDIT_PAY = "creditPay";
    public static final String ORDER_ID = "orderId";
    public static final String ADDR_ID = "addrId";
    public static final String ORDER_BLINDBOX_LOCK_KEY = "o_b_d_";
    public static final long ORDER_BLINDBOX_LOCK_EXPIRE_TIME = 9000L;

    //退款失败
    public static final String REFUND_FAIL = "REFUND_FAL";

    //支付类型 0是扣款，1是退款
    public static final Byte PAY_FORWARD = 0;
    public static final Byte PAY_BACKWARD = 1;
    public static final Byte GET_WAY_PRIZE = 1;//盒柜里的奖品
    //默认包邮金额
    public static final Integer BAOYOU_MONEY = 29900;

    public static final Byte PRIZE_WAY = 1;

    public static final Byte FREIGHT_STEP = (byte) 104;

    public static final Byte TIME_UP = (byte)1;

    public static final String WAIT_PAY_FREIGHT_TIP = "请在%s内完成支付，超时将自动取消本次修改申请哦~";
    public static final String WAIT_PAY_FREIGHT_CONTENT = "请在%s内完成支付";

    private OrderConstants(){}
}
