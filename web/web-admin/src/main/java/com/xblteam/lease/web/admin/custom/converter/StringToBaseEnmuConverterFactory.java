package com.xblteam.lease.web.admin.custom.converter;

import com.xblteam.lease.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

/**
 * SpringMvc类型转换器工厂
 */
@Component
public class StringToBaseEnmuConverterFactory implements ConverterFactory<String, BaseEnum> {
    /**
     * 类型转换器工厂：String -》 <? extends BaseEnum>
     * 根据目标类型获取对性的类型转换器，需在WebMvcConfiguration中注册
     *
     * @param targetType 源类型
     * @return 目标类型:BaseEnum的子类型
     */
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return code -> {
            T[] enumConstants = targetType.getEnumConstants();
            for (T enumConstant : enumConstants) {
                if (enumConstant.getCode() == Integer.parseInt(code)) {
                    return enumConstant;
                }
            }
            throw new IllegalArgumentException(
                    "No enum constant " + targetType.getCanonicalName() + " with code " + code);
        };
    }
}
