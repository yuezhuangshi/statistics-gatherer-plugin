package org.jenkins.plugins.statistics.gatherer.model.build;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ClassUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SourceObjectMap extends HashMap<String, Object> {

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    private static Set<Class<?>> getWrapperTypes()
    {
        Set<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        return ret;
    }

    public SourceObjectMap(Object source) {
        Class<?> sourceClass = source.getClass();
        Field[] sourceFields = sourceClass.getDeclaredFields();
        for (Field field: sourceFields) {
            try {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(source);

                if(isPrimitive(fieldValue)) {
                    put(fieldName, fieldValue);
                } else {
                    put(fieldName, new SourceObjectMap(fieldValue));
                }

            } catch(IllegalAccessException e) {
                // Field is not accessible => ignore it
            }
        }
    }

    private boolean isPrimitive(Object value) {
        return WRAPPER_TYPES.contains(value.getClass());
    }
}
