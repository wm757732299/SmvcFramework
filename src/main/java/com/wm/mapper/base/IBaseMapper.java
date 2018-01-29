package com.wm.mapper.base;

import java.util.List;
import java.util.Map;


/**
 * Mapepr公共接口
 * @version 1.0
 * @author WangMingM
 * @date 2017.10.27
 *
 */
public interface IBaseMapper<T> {
	
	
	public long insert (T t);
	
	public long deleteTrueByKey (String id);
	
	public long deleteFalseByKey (String id);
	
	public long update (T t);
	
	public T queryByKey (String id);
	
	public List<T> queryByCondition (T t);
	
	public List<T> queryByPage (Map<String, Object> t);
	
 	public long queryCount(T t);
 
}
