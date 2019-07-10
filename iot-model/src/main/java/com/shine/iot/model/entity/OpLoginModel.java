package com.shine.iot.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("oplogin")
public class OpLoginModel implements Serializable {

    private static final long serialVersionUID = 4317134675982090382L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;       // ID
    private String loginName; //登录名
    private String passWord;  //登录密码
    private Integer version;  //版本号
    private Integer isDelete; //是否删除

    private String name; //姓名
    private String nickName; //昵称
    private String headImgUrl; //头像链接地址
    private String mobilePhone; //联系电话
    private String address; //地址

    private Integer entID; //单位ID
    private Integer isAdmin; // 用户等级-身份

   /* private Integer rid;
    private Integer userid;*/


}
