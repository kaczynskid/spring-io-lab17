package io.spring.lab.warehouse;

import java.lang.reflect.Constructor;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
public class WarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplication.class, args);
	}

	@Bean(initMethod = "initialize", destroyMethod = "dispose")
	TestBean anotherTestBean() {
	    return new TestBean();
    }
}

@Slf4j
class TestBean implements InitializingBean, DisposableBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("InitializingBean");
	}

	@PostConstruct
	public void initMe() {
        log.info("PostConstruct");
    }

    public void initialize() {
        log.info("initMethod");
    }

    @Override
    public void destroy() throws Exception {
        log.info("DisposableBean");
    }

    @PreDestroy
    public void disposeOfMe() {
        log.info("PreDestroy");
    }

    public void dispose() {
        log.info("destroyMethod");
    }
}

@Slf4j
@Component
class TestBeanPostProcess implements SmartInstantiationAwareBeanPostProcessor {

    @Override
    public Class<?> predictBeanType(Class<?> beanClass, String beanName) throws BeansException {
//        if (TestBean.class == beanClass && beanName.equals("anotherTestBean"))
//            log.info("predictBeanType");
        return null;
    }

    @Override
    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
        if (TestBean.class == beanClass && beanName.equals("anotherTestBean"))
            log.info("determineCandidateConstructors");
        return null;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        if (bean instanceof TestBean && beanName.equals("anotherTestBean"))
            log.info("getEarlyBeanReference");
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (TestBean.class == beanClass && beanName.equals("anotherTestBean"))
            log.info("postProcessBeforeInstantiation");
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (bean instanceof TestBean && beanName.equals("anotherTestBean"))
            log.info("postProcessAfterInstantiation");
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (bean instanceof TestBean && beanName.equals("anotherTestBean"))
            log.info("postProcessProperties");
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TestBean && beanName.equals("anotherTestBean"))
            log.info("postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TestBean && beanName.equals("anotherTestBean"))
            log.info("postProcessAfterInitialization");
        return bean;
    }
}
