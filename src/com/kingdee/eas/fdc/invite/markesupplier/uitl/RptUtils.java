package com.kingdee.eas.fdc.invite.markesupplier.uitl;


import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.db.TempTablePool;

/**
 * @author JinXP
 * Ec报表工具类
 */
public class RptUtils {

	public static final String RPT_QUERYRESULT = "rpt.QueryResult";
	public static final String RPT_TEMPTABLE = "rpt.TempTable";
	public static final String RPT_PARAMETERS = "rpt.Parameters";
	
	/**
	 * 获得临时表, 创建的表名可任意指定。返回实际创建/重用的表名
	 * @param ctx
	 * @param sql  表结构
	 * @param desc 表摘要
	 * @return
	 * @throws BOSException
	 */
    public static String createTempTable(Context ctx, String sql, String desc) throws BOSException {
        String tableName = null;
        tableName = desc;
        sql = "create table " + tableName + " " + sql;
		try {
			tableName = TempTablePool.getInstance(ctx).createTempTable(sql);
		} catch (Exception e) {
			throw new BOSException(e);
		}

        return tableName;
    }
    
    /**
     * 释放临时表
     * @param ctx
     * @param tableName 表名
     * @throws BOSException
     */
    public static void dropTempTable(Context ctx, String tableName) throws BOSException {

		try {
			TempTablePool.getInstance(ctx).releaseTable(tableName);
		} catch (Exception e) {
			throw new BOSException(e);
		}

    }

}
