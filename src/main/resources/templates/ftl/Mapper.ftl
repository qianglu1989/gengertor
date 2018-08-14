<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
		"http://mybatis.org/dtd/mybatis-3-mapper.dtd">		
<#assign right="{" />
<#assign left="}" />
<#assign pk=table.primaryKey />
<mapper namespace="${packageName}.${entityName}Po">
  <resultMap id="BaseResultMap" type="${packageName}.${entityName}Po">
	<#list table.columns as column>
	<#if column.name == pk>
	<#assign pkPropName="${column.propName}" />
	<id column="${column.name}" property="${column.propName}" />
	<#else>
	<result column="${column.name}" property="${column.propName}" />
	</#if>
	</#list>
  </resultMap>

  <sql id="Table_Name">
    ${table.name}
  </sql>
  
  <sql id="Base_Column">
    <#list table.columns as column>
    <#if column_has_next>
	${column.name},
	<#else>
	${column.name}
	</#if>
	</#list>
  </sql>
  
  <sql id="Base_Where">
    <trim suffixOverrides="AND">
    <#list table.columns as column>
      <if test="${column.propName} != null">
        ${column.name}=#${right}${column.propName}${left}
      <#if column_has_next>
        AND 
      </#if>
      </if>
    </#list>
    </trim>
  </sql>
  
  <sql id="Base_Select">
    select
    <include refid="Base_Column" />
    from
    <include refid="Table_Name" />
    <where>
      <include refid="Base_Where" />
    </where>
  </sql>
	
  <!-- 单条插入 -->
  <insert id="insert" parameterType="${packageName}.${entityName}Po" useGeneratedKeys="true" keyProperty="${pkPropName}">
    insert into
    <include refid="Table_Name" />
    <trim prefix="(" suffix=")" suffixOverrides=",">
        <@generateInsertColumn/>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
        <@generateInsertValue/>
    </trim>
  </insert>
  
  <!-- 批量插入 -->
  <insert id="insertList" parameterType="java.util.List" >
	insert into 
	<include refid="Table_Name" />
	<trim prefix="(" suffix=")" suffixOverrides=",">
    <@generateInsertListColumn/>
    </trim>
	values
	<foreach collection="list" item="item" separator=",">
	<trim prefix="(" suffix=")" suffixOverrides=",">
    <@generateInsertListValue/>
    </trim>
	</foreach>
  </insert>
  
  <!-- 根据主键更新 -->
  <update id="update" parameterType="${packageName}.${entityName}Po">
	update
	<include refid="Table_Name" />
	<set>
	  <#list table.columns as column>
	  <#if column.name != pk && column.name != "create_time" && column.name != "update_time">
	  <if test="${column.propName} != null">
		${column.name}=#${right}${column.propName}${left},
	  </if>
	  </#if>
	  </#list>
	</set>
	where 
	<#list table.columns as column>
	<#if column.name == pk>
	 ${column.name}=#${right}${column.propName}${left}
	</#if>
	</#list>
  </update>
	
  <!-- 根据主键批量更新 -->
  <update id="updateList" parameterType="java.util.List">
     <foreach collection="list" item="item" separator=";">
      update
	  <include refid="Table_Name" />
	  <set>
	  <#list table.columns as column>
	  <#if column.name != pk && column.name != "create_time" && column.name != "update_time">
	  <if test="item.${column.propName} != null">
		${column.name}=#${right}item.${column.propName}${left},
	  </if>
	  </#if>
	  </#list>
	  </set>
	  where 
	  <#list table.columns as column>
	  <#if column.name == pk>
	   ${column.name}=#${right}item.${column.propName}${left}
	  </#if>
	  </#list>
     </foreach>
  </update>
  
  <!-- 
  <delete id="delete" parameterType="${packageName}.${entityName}Po">
     delete from
     <include refid="Table_Name" />
     <where>
     <#list table.columns as column>
	  <#if column.name == pk>
	   ${column.name}=#${right}${column.propName}${left}
	  </#if>
	  </#list>
    </where>
  </delete>
  
  <delete id="deleteList" parameterType="java.util.List" >
    <foreach collection="list" item="item" separator=";">  
	delete from
	<include refid="Table_Name" />
	where 
	<#list table.columns as column>
	  <#if column.name == pk>
	  ${column.name}=#${right}item.${column.propName}${left}
	  </#if>
	</#list>
    </foreach>
  </delete>
  -->
  
  <!-- 查询单表符合条件总条数 -->
  <select id="count" parameterType="${packageName}.${entityName}Po" resultType="int">
   select count(1) from
   <include refid="Table_Name" />
   <where>
   <include refid="Base_Where" />
   </where>
   </select>
  
  <!-- 查询符合条件的一条记录 -->
  <select id="selectOne" parameterType="${packageName}.${entityName}Po" resultMap="BaseResultMap">
     <include refid="Base_Select" />
     limit 1
   </select>
   
  <!-- 查询符合条件的记录 -->
  <select id="selectList" parameterType="${packageName}.${entityName}Po" resultMap="BaseResultMap">
     <include refid="Base_Select" />
   </select>
   
  <!-- 查询符合条件的记录  锁定符合条件的行-->
  <select id="selectForUpdate" parameterType="${packageName}.${entityName}Po" resultMap="BaseResultMap">
     <include refid="Base_Select" />
     for update
   </select>
   
  <#macro generateInsertColumn>
	<#list table.columns as column>
	<#if column.name != "create_time" && column.name != "update_time">
	  <if test="${column.propName} != null">
		${column.name},
	  </if>
	  </#if>
	</#list>
	</#macro>
	
	<#macro generateInsertValue>
	<#list table.columns as column>
	<#if column.name != "create_time" && column.name != "update_time">
	  <if test="${column.propName} != null">
		#${right}${column.propName}${left},
	  </if>
	  </#if>
	</#list>
	</#macro>
	
	<#macro generateInsertListColumn>
	<#list table.columns as column>
	<#if column.name != pk && column.name != "create_time" && column.name != "update_time">
      ${column.name},
	</#if>
	</#list>
	</#macro>
	
	<#macro generateInsertListValue>
	<#list table.columns as column>
	<#if column.name != pk && column.name != "create_time" && column.name != "update_time">
      #${right}item.${column.propName}${left},
	</#if>
	</#list>
	</#macro>
</mapper>