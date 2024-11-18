package com.jinlink.modules.system.entity.bo;

import lombok.Builder;
import lombok.Data;

/**
 * 第三方登录 QQ 业务处理对象
 *
 */
@Data
@Builder
public class QQBo {
    private int ret;
    private String msg;
    private int isLost;
    private String nickname;
    private String gender;
    private int genderType;
    private String province;
    private String city;
    private String year;
    private String figureurl;
    private String figureurl_1;
    private String figureurl_2;
    private String figureurl_qq_1;
    private String figureurl_qq_2;
    private String figureurl_qq;
    private String isYellowVip;
    private String vip;
    private String yellowVipLevel;
    private String level;
    private String isYellowYearVip;
}
