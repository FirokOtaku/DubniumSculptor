package firok.spring.dbsculptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在实体类上, 用于配置表生成策略
 *
 * @since 17.0.0
 * @author Firok
 * */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dubnium
{
	/**
	 * 自定义生成 SQL
	 * */
	String sculpturalScript() default "";

	Class<? extends Sculptor> sculpturalHandler() default Sculptor.class;
}
