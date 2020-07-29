package com.senld.gzlt.flowBuy.base;

/**
 * 服务相关URL 基类
 */
public interface BaseUrl {

	/** 查询列表URL(分页) */
	String QUERY_URL = "/list";
	/** 查询列表URL(无分页) */
	String QUERY_NO_PAGE_URL = "/listNoPage";
	/** 根据ID列表查询 */
	String QUERY_BATCH_IDS = "/queryBatchIds";
	/** 根据ID查询详细信息URL */
	String INFO_URL = "/info";
	/** 根据其他唯一条件查询详细信息URL */
	String QUERY_ONE_URL = "/queryOne";
	/** 新增信息URL */
	String SAVE_URL = "/save";
	/** 修改信息URL */
	String UPDATE_URL = "/update";
	/** 禁用启用信息URL */
	String STATUS_URL = "/status";
	/** 删除信息URL */
	String DELETE_URL = "/delete";
	/** 批量删除信息URL */
	String DELETE_BATCH_URL = "/deleteBatch";
	/** 刷新redis缓存 */
	String REFRESH_CACHE_URL = "/api/refreshCache";
	/** 统计查询(分页) */
	String QUERY_COUNT = "/count";
	/** 异常统计 */
	String QUERY_PROBLEM_LIST = "/problemList";
	/** 车辆总数 */
	String GIS_CEHICLEFLEET = "/vehicleFleet";
	/** 在线 */
	String GIS_ONLINE = "/onLine";
	/** 不在线 */
	String GIS_NOTONLINE = "/notOnLine";
	/** 树结构 */
	String QUERY_TREE = "/tree";
	/** 上传 */
	String UPLOAD = "/upload";
	/** 导入excel表格前汇总 */
	String UPLOAD_EXCEL = "/uploadExcel";
	/** 导入excel表格前汇总 */
	String IMPORT_ONLY_EXCEL = "/importOnlyExcel";
	/** 导入excel表格 */
	String IMPORT_EXCEL = "/importExcel";
	/** 导出excel表格 */
	String DOWN_EXCEL = "/downExcel";
	/** 导出excel表格 */
	String DOWN_FAIL_EXCEL = "/downFailExcel";
	/** 导入模板 */
	String DOWN_TEMPLET = "/downTemplet/{type}";
	/** 绑定 */
	String BIND_URL = "/bind";
	/** 解绑 */
	String UNTY_URL = "/unty";
	/** 批量绑定 */
	String BIND_BATCH_URL = "/bindBatch";
	/** 批量解绑 */
	String UNTY_BATCH_URL = "/untyBatch";
	/** 启用URL */
	String DISABLE_URL = "/disable";
	/** 停用URL */
	String ENABLE_URL = "/enable";
	/** 初始化数据 */
	String INIT_DATAS_URL = "/initData";
	/** 根据条件获取数据 */
	String GET_DATAS_URL = "/getDatas";
	/** 根据条件获取数据 */
	String GET_INFO_URL = "/getInfos";

}
