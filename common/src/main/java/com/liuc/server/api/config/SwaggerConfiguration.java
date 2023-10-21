package com.liuc.server.api.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.liuc.server.api.common.C;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by jclang on 2017/5/19.
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Bean
    public Docket defaultAllApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfoWebBack())
                .select()
                .apis(new Predicate<RequestHandler>() {
                    @Override
                    public boolean test(RequestHandler requestHandler) {
                        return requestHandler.isAnnotatedWith(ApiOperation.class);
                    }
                })
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(getDefaultParameterList());
    }

    private ApiInfo apiInfoWebBack() {
        Contact contact = new Contact("huangxianhong", "", "xianhong@6cheng.com.cn");
        return new ApiInfoBuilder()
                .title("用友水电-api接口")
                .description(C.codeStr)
                .contact(contact)
                .version("1.0")
                .build();
    }

    private List<RequestParameter> getDefaultParameterList() {
        List<RequestParameter> parameterList = new ArrayList<>();
        RequestParameterBuilder appkeyBuilder = new RequestParameterBuilder();
        appkeyBuilder.name(C.API_HEADER_PARAM_APPKEY)
                .query(q -> q.defaultValue("1").model(modelSpecificationBuilder -> modelSpecificationBuilder.scalarModel(ScalarType.STRING)))
                .description("appkey")
                .in("header")
                .required(true)
                .build();
        parameterList.add(appkeyBuilder.build());

        RequestParameterBuilder timestampBuilder = new RequestParameterBuilder();
        timestampBuilder.name(C.API_HEADER_PARAM_TIMESTAMP)
                .query(q -> q.defaultValue("111111111111").model(modelSpecificationBuilder -> modelSpecificationBuilder.scalarModel(ScalarType.STRING)))
                .description("timestamp")
                .in("header")
                .required(true)
                .build();
        parameterList.add(timestampBuilder.build());

        RequestParameterBuilder signBuilder = new RequestParameterBuilder();
        signBuilder.name(C.API_HEADER_PARAM_SIGN)
                .query(q -> q.defaultValue("1").model(modelSpecificationBuilder -> modelSpecificationBuilder.scalarModel(ScalarType.STRING)))
                .description("sign")
                .in("header")
                .required(true)
                .build();
        parameterList.add(signBuilder.build());

        RequestParameterBuilder sessionBuilder = new RequestParameterBuilder();
        sessionBuilder.name(C.API_HEADER_PARAM_SESSION)
                .query(q -> q.defaultValue("111111111111111111111111111111111").model(modelSpecificationBuilder -> modelSpecificationBuilder.scalarModel(ScalarType.STRING)))
                .description("session")
                .in("header")
                .required(true)
                .build();
        parameterList.add(sessionBuilder.build());
        return parameterList;
    }

}
