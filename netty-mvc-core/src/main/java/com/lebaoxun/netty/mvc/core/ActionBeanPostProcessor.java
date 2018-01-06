package com.lebaoxun.netty.mvc.core;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.lebaoxun.netty.mvc.core.invote.ActionHandler;
import com.lebaoxun.netty.mvc.core.invote.ActionHandlerContainer;

/**
 * 当spring bean实例化完成后调用。赛选出方法上带有ReqestMapping Controller
 * @author Caiqianyi
 *
 */
@Component
public class ActionBeanPostProcessor implements BeanPostProcessor  {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
  
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {  
        return bean;  
    }  
  
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    	if(bean.getClass().getAnnotation(Controller.class) != null){
    		Method[] methods=bean.getClass().getDeclaredMethods();  
            for (Method method : methods) {  
                ReqestMapping reqestMapping=method.getAnnotation(ReqestMapping.class); 
                if(reqestMapping!=null){
                    ActionHandler action=new ActionHandler();  
                    action.setMethod(method);  
                    action.setObject(bean);  
                    logger.info("Mapped {[cammand="+reqestMapping.cammand()+"]}");
                    ActionHandlerContainer.put(reqestMapping.cammand(), action);  
                }  
            }
    	}
        return bean;  
    }  
  
} 
