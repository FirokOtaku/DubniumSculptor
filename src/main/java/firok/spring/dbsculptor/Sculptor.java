package firok.spring.dbsculptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.context.ApplicationContext;

/**
 * todo 可能会换成 record 模式
 * @since 17.0.0
 * @author Firok
 * */
public class Sculptor
{
	protected final Class<?> beanClass;
	protected final Dubnium dubnium;
	protected final BaseMapper<?> beanMapper;
	protected final DubniumDirectMapper directMapper;
	protected final ApplicationContext context;

	/**
	 * @param beanClass 实体类
	 * @param dubnium 配置信息
	 * @param beanMapper 实体相关 mapper. 如果没有启用相关组件, 此参数为空
	 * @param directMapper 直接执行 SQL 用辅助类
	 * @implNote 子类需要提供一个相同签名的构造方法
	 * */
	public Sculptor(Class<?> beanClass,
	                Dubnium dubnium,
	                BaseMapper<?> beanMapper,
	                DubniumDirectMapper directMapper,
	                ApplicationContext context)
	{
		this.beanClass = beanClass;
		this.dubnium = dubnium;
		this.beanMapper = beanMapper;
		this.directMapper = directMapper;
		this.context = context;
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
