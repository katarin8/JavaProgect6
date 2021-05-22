package com.voronina.bpp;

import com.voronina.annotations.InjectRandomInt;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;

public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectRandomInt injectRandomIntAnnotation = field.getAnnotation(InjectRandomInt.class);
            if (injectRandomIntAnnotation != null) {
                int min = injectRandomIntAnnotation.minValue();
                int max = injectRandomIntAnnotation.maxValue();
                int randomValue = RandomUtils.nextInt(min, max + 1);
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, randomValue);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
