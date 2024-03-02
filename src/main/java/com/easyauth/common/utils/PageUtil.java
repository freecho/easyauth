package com.easyauth.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyauth.common.constant.MessageConstant;
import com.easyauth.common.exception.BeanConvertException;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mybatis-Plus 分页对象工具类
 */
public class PageUtil {

    /**
     * 泛型版本的 Page 对象转换工具方法，使用 BeanUtils 复制属性。
     *
     * @param <T>         源对象数据类型
     * @param <V>         目标类型
     * @param sourcePage  源 Page 对象
     * @param targetClass 目标类 Class 对象
     * @return 转换后的 Page<V> 对象
     */
    public static <T, V> Page<V> convert(Page<T> sourcePage, Class<V> targetClass) {
        List<V> targetVOList = sourcePage.getRecords().stream().map(sourceEntity -> {
            try {
                V targetVO = targetClass.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(sourceEntity, targetVO);
                return targetVO;
            } catch (Exception e) {
                throw new BeanConvertException(MessageConstant.DARA_ERROR);
            }
        }).collect(Collectors.toList());

        Page<V> targetPage = new Page<>();
        BeanUtils.copyProperties(sourcePage, targetPage);
        targetPage.setRecords(targetVOList);

        return targetPage;
    }
}
