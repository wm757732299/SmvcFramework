package com.wm.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wm.controller.base.BaseController;
import com.wm.mapper.entity.${entityName};
import com.wm.service.${entityName}Service;

@Controller
@RequestMapping(value = "/${humpEntityName}")
public class ${entityName}Controller extends BaseController<${entityName}> {

	private static final Logger LOGGER = Logger.getLogger(${entityName}Controller.class);

	@Resource(type = ${entityName}Service.class)
	private ${entityName}Service ${humpEntityName}Service;
	
}