package poi.model;

import lombok.Getter;

/**
 * Field 类型
 *
 * @author 幽明
 * @serial 2018/11/5
 */
public enum FieldType {
    /**
     * Numeric cell type (whole numbers, fractional numbers, dates)
     */
    INTEGER(0),

    /**
     * String (text) cell type
     */
    Long(1),

    /**
     * String cell type
     */
    STRING(2),

    /**
     * Date cell type
     */
    DATE(3),

    /**
     * Boolean cell type
     */
    BOOLEAN(4),


    LIST(5);

    @Getter
    private Integer type;

    FieldType(Integer type) {
        this.type = type;
    }
}
