package firok.spring.dbsculptor;

import firok.spring.mvci.runtime.CurrentMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * 蚀刻器主体
 *
 * @since 17.0.0
 * @author Firok
 * */
@Component
@ConditionalOnExpression("${firok.spring.dbsculptor.enable:false}")
public class DubniumSculptor implements CommandLineRunner
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
	public static final String Version = "17.2.0";

	@Autowired
	@Qualifier("directMapper")
	Object directMapper;

	@Autowired
	@Qualifier("currentMappers")
	Object mappers;


	@Override
	public void run(String... args) throws Exception
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
							directMapper
					);

			if(sculpturalHandler.needSculptured())
				sculpturalHandler.doSculpture();
		}
	}
}
