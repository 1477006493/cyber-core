package com.cyber.core.tool;

import com.cyber.core.enums.Code;
import com.cyber.core.exception.BaseException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 对象转换相关工具类
 * 继承spring BeanUtils
 * @author cyber
 * @date 2022年6月9日
 */
public class BeanUtil extends BeanUtils{

	/**
	 * 单个dto/vo/entity互相转换
	 *
	 * @param source 源数据
	 * @param target 目标类型类模板
	 * @param <E>    目标类型
	 * @return dto/vo/entity
	 */
	public static <E> E copy(Object source, Class<E> target) {
		if (source == null || target == null) {
			return null;
		}
		E e = null;
		try {
			e = target.newInstance();
			BeanUtils.copyProperties(source, e);
		} catch (InstantiationException | IllegalAccessException ex) {
			throw new BaseException(Code.ERROR);
		}
		return e;
	}

	/**
	 * dto/vo/entity列表互相转换
	 *
	 * @param list   列表
	 * @param target 目标类型
	 * @param <V>    dto/vo/entity
	 * @param <E>    dto/vo/entity
	 * @return 列表互相转换
	 */
	public static <V, E> List<E> copyList(List<V> list, Class<E> target) {
		List<E> result = new ArrayList<>();
		if (list == null || list.size() <= 0 || target == null) {
			return result;
		}
		list.forEach(v -> {
			try {
				E e = target.newInstance();
				BeanUtils.copyProperties(v, e);
				result.add(e);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		return result;
	}

	/**
	 * 通过反射获取包括父类的所有类属性
	 *
	 * @param object obj
	 * @return 获取类的所有属性
	 */
	public static Field[] getAllFields(Object object) {
		Class<?> clazz = object.getClass();
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}

}
