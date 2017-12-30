package com.cn.huidashi.Utils;

import com.cn.huidashi.entity.Apply;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by alvin on 2017/8/11.
 */
public class ParamUtil {

    /**
     * 将POJO转换为路径参数
     * 忽略“pageOffSet”和“class”属性
     * @return
     */
    public static String parseBeanToPathParam(Object bean) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String,String> props = BeanUtils.describe(bean);

        StringBuffer pathParam = new StringBuffer();

        for(String key : props.keySet()){

            if("class".equals(key)) continue;
            if("pageOffSet".equals(key)) continue;

            if(props.get(key) != null){
                pathParam.append("&" + key + "=" + props.get(key));
            }
        }

        return pathParam.toString();
    };
}
