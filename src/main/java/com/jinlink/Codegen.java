package com.jinlink;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zaxxer.hikari.HikariDataSource;

public class Codegen {

    public static void main(String[] args) {
        //配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://47.113.197.48:3306/jinlink_boot?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("sr2539195984");

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();
        //GlobalConfig globalConfig = createGlobalConfigUseStyle2();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
    }

    public static GlobalConfig createGlobalConfigUseStyle1() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        globalConfig.getJavadocConfig()
                .setAuthor("Summer")
                .setSince("1.0.0");

        //设置根包
        globalConfig.setBasePackage("com.jinlink.modules.system");

        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix("tb_");
        globalConfig.setGenerateTable("sys_role_permission");

        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);
        //设置项目的JDK版本，项目的JDK为14及以上时建议设置该项，小于14则可以不设置
        globalConfig.setEntityJdkVersion(17);

        //设置生成 mapper
        globalConfig.getMapperConfig()
                .setClassSuffix("Mapper")
                .setSuperClass(BaseMapper.class);
        globalConfig.setMapperGenerateEnable(true);

        //设置生成 xml
        globalConfig.getMapperXmlConfig()
                .setFileSuffix("Mapper");
        globalConfig.setMapperXmlGenerateEnable(true);

        //设置生成 Service
        globalConfig.getServiceConfig()
                .setClassSuffix("Service")
                .setSuperClass(IService.class);
        globalConfig.setServiceGenerateEnable(true);

        //设置生成 ServiceImpl
        globalConfig.getServiceImplConfig()
                .setClassSuffix("ServiceImpl")
                .setSuperClass(ServiceImpl.class);
        globalConfig.setServiceImplGenerateEnable(true);

        //设置生成 Controller
        globalConfig.setControllerGenerateEnable(true);
        //可以单独配置某个列
        ColumnConfig columnConfig = new ColumnConfig();
        columnConfig.setLarge(true);
        columnConfig.setVersion(true);
        globalConfig.setColumnConfig("blue_game_map", columnConfig);

        return globalConfig;
    }
}
