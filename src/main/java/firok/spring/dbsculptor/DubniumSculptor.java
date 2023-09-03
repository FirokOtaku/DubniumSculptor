package firok.spring.dbsculptor;

import firok.spring.mvci.runtime.CurrentMappers;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * 蚀刻器主体
 *
 * @since 17.0.0
 * @author Firok
 * */
@Component
@ConditionalOnExpression("${firok.spring.dbsculptor.enable:false}")
public class DubniumSculptor implements ApplicationContextAware
{
//	final Object mappers;
//
//	final Object directMapper;
//
//	public DubniumSculptor(Object mappers, Object directMapper)
//	{
//		this.mappers = mappers;
//		this.directMapper = directMapper;
//	}
	public static final String Name = "Dubnium Sculptor";
	@Deprecated(forRemoval = true)
	public static final String Version = "17.3.0";

	@Autowired
	@Qualifier("dubniumDirectMapper")
	Object directMapper;

	@Autowired
	@Qualifier("currentMappers")
	Object mappers;

	private ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException
	{
		this.context = context;
	}

	@PostConstruct
	public void run() throws Exception
	{
		var classCurrentBeanNames = Class.forName("firok.spring.mvci.runtime.CurrentBeanNames");
		var fieldCurrentBeanNamesNames = classCurrentBeanNames.getField("NAMES");
		var currentBeanNames = (String[]) fieldCurrentBeanNamesNames.get(null);
		var classMappers = mappers.getClass();
		var methodGetBean = classMappers.getMethod("getByFullQualifiedBeanName", String.class);
		for(var beanName : currentBeanNames)
		{
			var beanClass = Class.forName(beanName);
			var dubnium = beanClass.getAnnotation(Dubnium.class);
			if(dubnium == null) continue;

			var beanMapper = methodGetBean.invoke(mappers, beanName);

			var sculpturalHandler = (Sculptor) dubnium.sculpturalHandler()
					.getConstructors()[0]
					.newInstance(
							beanClass,
							dubnium,
							beanMapper,
							directMapper,
							context
					);

			if(sculpturalHandler.needSculptured())
				sculpturalHandler.doSculpture();
		}
	}
}
