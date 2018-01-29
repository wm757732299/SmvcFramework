<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.wm.mapper.${targetName}">

	<resultMap type="com.wm.mapper.entity.${entityName}" id="${humpEntityName}">
		<#list prop as l>
		${l}	
		</#list>
	</resultMap>


	<sql id="Table_Fields">
		 ${tableFields}
	</sql>

    <sql id="Where_Conditions">
		<where>
			1=1
			<#list whereConditions as l>
			${l}	
			</#list>
		</where>
	</sql>
	
	<sql id="Set_Values">
		<set >
			<#list setValues as  l>
			${l}	
			</#list>
		</set>
	</sql>
	
	<insert id="insert" parameterType="com.wm.mapper.entity.${entityName}" >
			<selectKey keyProperty="id" order="BEFORE" resultType="java.lang.String">select uuid()</selectKey>
			INSERT INTO ${tableName}
			  (${tableFields})
			VALUES 
			  (${insertValues})
	</insert>
	
	<delete id="deleteTrueByKey" parameterType="java.lang.String" >
		<![CDATA[
			DELETE FROM ${tableName}
			WHERE ID = ${id}
		]]>
	</delete>
	
	<update id="deleteFalseByKey" parameterType="java.lang.String">
		<![CDATA[
			UPDATE ${tableName} SET DELETE_MARK = 1
			WHERE ID = ${id}
		]]>
	</update>
	
	<update id="update" parameterType="com.wm.mapper.entity.${entityName}" >
		UPDATE ${tableName}
		<include refid="Set_Values" />
		WHERE ID = ${id}
	</update>

	<select id="queryByKey" parameterType="java.lang.String" resultMap="${humpEntityName}">
		SELECT
		<include refid="Table_Fields" />
 		FROM ${tableName}
 		WHERE ID = ${id}
	</select>
	
	<select id="queryByCondition" parameterType="com.wm.mapper.entity.${entityName}" resultMap="${humpEntityName}">
		SELECT 
		<include refid="Table_Fields" />
		FROM ${tableName} 
		<include refid="Where_Conditions" />
	</select>
	
	<select id="queryByPage" parameterType="java.util.Map" resultMap="${humpEntityName}">
		SELECT
		<include refid="Table_Fields" />
 		FROM ${tableName}
 		<include refid="Where_Conditions" />
 		ORDER BY TIME_STAMP DESC
	</select>
	
	<select id="queryCount" parameterType="com.wm.mapper.entity.${entityName}" resultType="java.lang.Long">
		SELECT COUNT(ID)
 		FROM ${tableName}
 		<include refid="Where_Conditions" />
	</select>
</mapper>