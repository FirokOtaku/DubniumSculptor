package firok.spring.dbsculptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * todo 可能会换成 record 模式
 * @since 17.0.0
 * @author Firok
 * */
public class Sculptor
{
	final Class<?> beanClass;
	final Dubnium dubnium;
	final BaseMapper<?> beanMapper;
	final DirectMapper directMapper;

	/**
	 * @param beanClass 实体类
	 * @param dubnium 配置信息
	 * @param beanMapper 实体相关 mapper. 如果没有启用相关组件, 此参数为空
	 * @param directMapper 直接执行 SQL 用辅助类
	 * */
	public Sculptor(Class<?> beanClass, Dubnium dubnium, BaseMapper<?> beanMapper, DirectMapper directMapper)
	{
		this.beanClass = beanClass;
		this.dubnium = dubnium;
		this.beanMapper = beanMapper;
		this.directMapper = directMapper;
	}

	/**
	 * 检测指定实体类是否需要生成表结构
	 * */
	public boolean needSculptured()
	{
		if(beanMapper == null)
			throw new IllegalArgumentException("默认蚀刻器需要 CurrentMappers 组件");

		try
		{
			var count = beanMapper.selectCount(new QueryWrapper<>());
			return count < 0;
		}
		catch (Exception any)
		{
			return true;
		}
	}

	/**
	 * 生成表结构
	 * */
	public void doSculpture()
	{
		if(beanMapper == null)
			throw new IllegalArgumentException("默认蚀刻器需要 CurrentMappers 组件");

		var sculpturalScript = dubnium.sculpturalScript();
		directMapper.update(sculpturalScript);
	}
}
