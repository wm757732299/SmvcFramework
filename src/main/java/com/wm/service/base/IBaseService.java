package com.wm.service.base;

import java.util.List;

/**
 * service公共接口
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2017.10.27
 * @param <T>
 */
public interface IBaseService<T> {

//	public long updateOrInsert(T t);待实现

	public long insert(T t);

	public long delete(T t);

	public long update(T t);

	public T queryByKey(String id);

	public List<T> queryByCondition(T t);

	public List<T> queryByPage(T t);
}
