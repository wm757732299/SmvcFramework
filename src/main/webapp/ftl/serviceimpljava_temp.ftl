package com.wm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wm.annotation.DataSourceChoose;
import com.wm.annotation.DataSourceChoose.SourceKey;
import com.wm.mapper.${entityName}Mapper;
import com.wm.mapper.entity.${entityName};
import com.wm.service.${entityName}Service;

@Service("${humpEntityName}Service")
@DataSourceChoose(sourceKey = SourceKey.DSK2)
@Transactional(timeout = 60)
public class ${entityName}ServiceImpl implements ${entityName}Service {

	@Resource(type = ${entityName}Mapper.class)
	private ${entityName}Mapper ${humpEntityName}Mapper;

	public long insert(${entityName} t) {
		return ${humpEntityName}Mapper.insert(t);
	}

	public long delete(${entityName} t) {
		return ${humpEntityName}Mapper.deleteTrueByKey(t.getId());
	}

	public long update(${entityName} t) {
		return ${humpEntityName}Mapper.update(t);
	}

	public ${entityName} queryByKey(String id) {
		return ${humpEntityName}Mapper.queryByKey(id);
	}

	public List<${entityName}> queryByCondition(${entityName} t) {
		return null;
	}

	public List<${entityName}> queryByPage(${entityName} t) {
		return null;
	}


}
