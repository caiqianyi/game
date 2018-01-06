package com.lebaoxun.netty.mvc.core.invote;

import java.lang.reflect.Method;  
import java.util.HashMap;  
import java.util.Map;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caiqianyi.commons.exception.I18nMessageException;
  
public class ActionHandlerContainer {  
  
	private static Logger logger = LoggerFactory.getLogger(ActionHandlerContainer.class);
	
    private static Map<String, ActionHandler> map = new HashMap<String, ActionHandler>();  
  
    public static Object invote(String key, Object... args) throws Exception {  
        ActionHandler action = map.get(key);  
        if (action != null) {  
            Method method = action.getMethod();  
            try {
            	logger.info("method={},action={},args.length={}",method,action,args.length);
                return method.invoke(action.getObject(), args);
            } catch (Exception e) {  
                throw e;  
            }
        }  
        throw new I18nMessageException("404","未找到action");  
    }  
  
    public static void put(String key, ActionHandler action) {  
        map.put(key, action);  
    }
  
}
