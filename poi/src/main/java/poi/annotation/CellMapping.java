package poi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Java bean 与 Excel cell的映射
 * Excel row对应一个Java bean, 一个cell对应一个filed
 *
 * @author 幽明
 * @serial 2018/11/4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CellMapping {

    /**
     * cell下标
     *
     * @return cell的下标
     */
    int index();
}
