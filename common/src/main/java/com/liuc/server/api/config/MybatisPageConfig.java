package com.liuc.server.api.config;


import com.github.AopLogAutoConfiguration;
import com.github.pagehelper.PageInterceptor;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import net.dreamlu.mica.core.utils.StringUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * mybatis分页插件
 *
 * 由于com.github.ealenxie.aop-log依赖包自动配置
 * 导致分页插件自动配置类PageHelperAutoConfiguration失效，需要手动注入分页插件
 */
@Configuration
@ConditionalOnClass(AopLogAutoConfiguration.class)
@EnableConfigurationProperties(value = {PageHelperProperties.class})
public class MybatisPageConfig {

    private PageHelperProperties pageHelperProperties;

    public MybatisPageConfig(ObjectProvider<PageHelperProperties> objectProvider) {
        this.pageHelperProperties = objectProvider.getIfUnique();
    }

    /**
     * 注入分页插件，springBoot会将注入的插件自动设置到sqlSessionFactory中
     */
    @Bean
    public Interceptor interceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        //加载配置
        Properties properties = new Properties();
        //方言不设置可以自动选择
        if (StringUtil.isNotBlank(pageHelperProperties.getHelperDialect()))
            properties.setProperty("helperDialect", pageHelperProperties.getHelperDialect());
        properties.setProperty("reasonable", pageHelperProperties.getHelperDialect());
        properties.setProperty("pageSizeZero", pageHelperProperties.getHelperDialect());
        //设置参数
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
}
