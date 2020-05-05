package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("user")  //如果表名与对象名称一致,则可以省略不写
public class User {
	@TableId(type = IdType.AUTO) //表示主键自增
	private Integer id;
	//@TableField("name")  //如果字段与名称一致时,可以省略不写
	private String name;
	private Integer age;
	private String sex;
}
