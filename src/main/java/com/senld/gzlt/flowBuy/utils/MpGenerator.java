package com.senld.gzlt.flowBuy.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * mybatis-plus代码生成器
 * 
 * @author chao
 *
 */
public class MpGenerator {
	private static String path = new File("src/main/java").getAbsolutePath();// 文件路径
	private static String authorName = "system"; // 作者
	private static boolean override = true;
	private static boolean activeRecord = false;
	private static String tablePrefix = "";// 数据库表名前缀

	private static String table = "FLOW_ORDER";// 需要生成的表名

	private static String packageName = "com.senld.gzlt.flowBuy";// 包名
	// private static String moduleName="menu";//模块名
	private static String entityName = "流量订单";// 自定义的参数
	private static String url = "jdbc:mysql://10.195.20.251:33060/boss-base?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull";// 数据库地址

	public static void main(String[] args) {
		build(url, packageName, table, tablePrefix, entityName);
	}

	/**
	 * 自动生成代码
	 * 
	 * @param url
	 *            数据地址
	 * 
	 * @param packageName
	 *            生成文件所在的目录
	 * @param table
	 *            需要生成的表名
	 * @param tablePrefix
	 *            数据库表名前缀，没有可为null
	 * @param entityName
	 *            模块名
	 */
	public static void build(String url, String packageName, String table, String tablePrefix,
			final String entityName) {
		AutoGenerator generator = new AutoGenerator();
		GlobalConfig globalConfig = new GlobalConfig();// 全局配置
		globalConfig.setOutputDir(path)// 文件输出路径
				.setFileOverride(override)// 是否覆盖原有的文件
				.setActiveRecord(activeRecord)// 开启 activeRecord 模式
				.setEnableCache(false)// XML 二级缓存
				.setBaseResultMap(true)// XML ResultMap
				.setBaseColumnList(true)// XML columList
				.setOpen(false)// 生成后打开文件夹
				.setAuthor(authorName).setMapperName("%sMapper")// 自定义文件命名，注意 %s
																// 会自动填充表实体属性！若是%S则这填充的部分全部大写
				.setXmlName("%sMapper").setServiceName("%sService").setServiceImplName("%sServiceImpl")
				.setControllerName("%sController");
		generator.setGlobalConfig(globalConfig);
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.MYSQL).setDriverName("com.mysql.jdbc.Driver").setUsername("root")
				.setPassword("senld").setUrl(url).setTypeConvert(new MySqlTypeConvert() { // 自定义数据库表字段类型转换【可选】
					@Override
					public DbColumnType processTypeConvert(String fieldType) {
						return super.processTypeConvert(fieldType);
					}
				});
		generator.setDataSource(dataSourceConfig);
		// 字段策略
		StrategyConfig strategyConfig = new StrategyConfig();
		// 自定义需要填充的字段
		// List<TableFill> tableFillList = new ArrayList<>();
		// tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));
		strategyConfig.setTablePrefix(new String[] { tablePrefix == null ? "" : tablePrefix })// 表名前缀
				.setCapitalMode(false)// 全局大写命名
				.setDbColumnUnderline(true)// 全局下划线命名
				.setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
				.setInclude(new String[] { tablePrefix+table })// 需要生成的表
				.setRestControllerStyle(true)// rest格式的controller，也就是controller添加@RestController注解
				// .setExclude(new String[]{"test"})//排除生成的表,不能和前面的include一起作用
				.setSuperControllerClass("com.senld.boss.common.base.BaseController") // 自定义
				.setSuperEntityClass("com.senld.boss.common.base.BaseModel")// 自定义实体父类
				.setSuperEntityColumns(new String[] { "ID" });// 自定义实体的公共字段
		// .setSuperMapperClass("")//自定义mapper的父类
		// .setSuperServiceClass("")// 自定义 service 父类
		// .setSuperServiceImplClass("")//自定义service 实现类的父类
		// .setTableFillList(tableFillList);
		generator.setStrategy(strategyConfig);
		// 包路径策略
		PackageConfig packageConfig = new PackageConfig();
		packageConfig.setParent(packageName)// 自定义包路径
				// .setModuleName(moduleName)//自定义模块名
				.setController("controller")// 指定控制器的包名，默认 web
				.setEntity("entity")// 指定实体类包名
				.setMapper("mapper")// mapper接口
				.setService("service")// 指明业务接口的包名
				.setServiceImpl("service.impl");
		// .setXml("mapper");
		generator.setPackageInfo(packageConfig);
		// 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
		InjectionConfig injectionConfig = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("entityName", entityName);
				this.setMap(map);
			}
		};
		List<FileOutConfig> fileList = new ArrayList<FileOutConfig>();
		fileList.add(new FileOutConfig("/templates/mapper.xml.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return new File("src/main/resources/mybatis").getAbsolutePath() + "/" + tableInfo.getEntityPath()
						+ "-mapper.xml";
			}
		});
		injectionConfig.setFileOutConfigList(fileList);
		generator.setCfg(injectionConfig);
		// 自定义模板配置
		TemplateConfig tc = new TemplateConfig();
		tc.setController("./templates/controller.java.vm");
		tc.setEntity("./templates/entity.java.vm");
		tc.setMapper("./templates/mapper.java.vm");
		tc.setXml("./templates/mapper.xml.vm");
		tc.setService("./templates/service.java.vm");
		tc.setServiceImpl("./templates/serviceImpl.java.vm");
		generator.setTemplate(tc);
		// generator.setTemplate(new TemplateConfig().setXml(null)// 关闭默认 xml
		// );
		generator.execute();
		// 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
		System.err.println("==================自动生成代码完成==============");
	}
}
