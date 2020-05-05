package com.jt;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMybatis {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void testFindAll() {
		System.out.println(userMapper.getClass());
		System.out.println(userMapper.findAll());
	}
	
	@Test
	public void testInsert() {
		User user = new User();
		user.setId(null)
			.setName("1907班")
			.setAge(4)
			.setSex("男女");
		int rows = userMapper.insert(user);
		if(rows > 0) {
			System.out.println("用户入库成功!");
		}
	}
	
	/**
	 * 1.根据主键Id查询
	 */
	@Test
	public void findById() {
		
		User user = userMapper.selectById(53);
		System.out.println(user);
	}
	/**
	 * 2.按照name属性查询   1907班
	 * QueryWrapper条件构造器:
	 * 目的:利用对象中不为null的属性充当where条件构建
	 */
	@Test
	public void findByName() {
		User user = new User();
		user.setName("1907班");
		QueryWrapper<User> queryWrapper = 
				new QueryWrapper<User>(user);
		List<User> userList = 
				userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	
	/**
	 * 多条件查询
	 * name=1907班 and age < 20
	 * = eq  > gt  < lt  >=ge <=le
	 */
	@Test
	public void findByMore() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.eq("name", "1907班")
					.lt("age", 20);
		List<User> userList = 
				userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	
	
	/**
	 * name=1907班 or age < 20
	 */
	@Test
	public void findByOr() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.eq("name", "1907班")
					.or()
					.lt("age", 20);
		List<User> userList = 
				userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	
	//查询年龄  age大于18 age<45 sex="男"
	@Test
	public void findByBet() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.eq("sex","男")
					.between("age", 18, 45);
		List<User> userList = 
				userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	
	/**
	 * 模糊查询  名称中包含  乔字
	 */
	@Test
	public void findByLike() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.like("name", "%乔%");
		List<User> userList = 
				userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	
	/**
	 * 查询  名称为null的数据
	 */
	@Test
	public void findByNull() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.isNull("name");
		List<User> userList = 
				userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	
	
	/**
	 * 删除数据
	 * 1. 删除Id=53 54
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void deleteUsers() {
		//1.根据主键删除数据
		//userMapper.deleteById(53);
		//userMapper.deleteById(54);
		Integer[] ids = {53,54};
		//2.批量删除
		List list = Arrays.asList(ids);
		userMapper.deleteBatchIds(list);
	}
	/**
	 * 修改操作  要求将55号数据改为 name=1907 age=10 sex="男"
	 */
	@Test
	public void updateUser() {
		User user = new User();
		user.setId(55)
			.setName("1907")
			.setAge(10)
			.setSex("男");
		userMapper.updateById(user);
	}
	
	/**
	 * 修改name为null的元素,name=虞姬 age=18 sex="女"
	 * entity: 要修改后的数据
	 * updateWrapper: 修改条件构造器
	 */
	@Test
	public void updateUser2() {
		User user = new User();
		user.setName("虞姬").setAge(18).setSex("女");
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		updateWrapper.isNull("name");
		userMapper.update(user, updateWrapper);
	}
	
	/**
	 * 总结:
	 * 	 如果已经主键,一般使用updateById.
	 * 	 如果获取的是其他属性字段,则使用update
	 * 
	 *  案例:
	 *  	User对象(40个属性) 修改id=10号元素.
	 *  	User user = userMapper.selectById(10);
	 *  需求:修改bir的生日改为今天 date
	 *  	user.setBir(new Date);
	 *  	userMapper.updateById(user);
	 *  	修改了除id之外的所有字段.
	 * 	实际操作:
	 * 		User userTemp = new User();
	 * 		userTemp.setBir(new Date());
	 * 		userTemp.setId(10);
	 * 		userMapper.updateById(userTemp);
	 * 		只修改1个字段
	 *  原则:
	 *  	如果利用Mybatisplus的操作过于繁琐时.则使用sql语句方式操作更快
	 * 	
	 */	
	
}
