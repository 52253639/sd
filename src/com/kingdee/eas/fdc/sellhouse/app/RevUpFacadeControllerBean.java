package com.kingdee.eas.fdc.sellhouse.app;

import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.util.app.DbUtil;

public class RevUpFacadeControllerBean extends AbstractRevUpFacadeControllerBean
{
    private static Logger log =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RevUpFacadeControllerBean");
    protected String _executeSQL(Context ctx, List sqls)throws BOSException
    {
		StringBuffer sb = new StringBuffer();

		long s1 = System.currentTimeMillis();

		sb.append("开始执行：");
		sb.append("\r\n");
		for (int i = 0; i < sqls.size() - 1; i++) {
			long s2 = System.currentTimeMillis();
			String tmp = (String) sqls.get(i);

			String[] ss1 = tmp.split("\r\n");

			String sqlDes = "";
			for (int j = 0; j < ss1.length; j++) {
				if (ss1[j].startsWith("--")) {
					sqlDes += ss1[j];
				}
			}
			
			//为保证升级日志的输出，error输出
			log.error("准备执行：" + sqlDes);
			sb.append("准备执行：" + sqlDes);
			sb.append("\r\n");

			DbUtil.execute(ctx, tmp);

			long s3 = System.currentTimeMillis();
			log.error("执行完成：" + sqlDes);
			sb.append("执行完成，耗时：" + (s3 - s2) + "ms");
			sb.append("\r\n");
		}

		long s4 = System.currentTimeMillis();
		log.error("全部执行完成！");

		sb.append("全部完成：耗时" + (s4 - s1) + "ms");
		return sb.toString();
	}
    
    /*StringBuffer sb = new StringBuffer();

		Connection conn = null;
		Statement statement = null;
		
		long s1 = System.currentTimeMillis();
		try {
			conn = EJBFactory.getConnection(ctx);
//			conn.setAutoCommit(false);
			statement = conn.createStatement();
			
			sb.append("开始执行：");
			sb.append("\r\n");
			for (int i = 0; i < sqls.size() - 1; i++) {
				long s2 = System.currentTimeMillis();
				String tmp = (String) sqls.get(i);

				String[] ss1 = tmp.split("\r\n");

				String sqlDes = "";
				for (int j = 0; j < ss1.length; j++) {
					if (ss1[j].startsWith("--")) {
						sqlDes += ss1[j];
					}
				}

				log.info("准备执行：" + sqlDes);
				sb.append("准备执行：" + sqlDes);
				sb.append("\r\n");

				statement.execute(tmp);

				long s3 = System.currentTimeMillis();
				log.info("执行完成：" + sqlDes);
				sb.append("执行完成，耗时：" + (s3 - s2) + "ms");
				sb.append("\r\n");
			}
//			conn.commit();
//			conn.setAutoCommit(true);
		} catch (SQLException exc) {
//			try {
//				conn.rollback();
//			} catch (SQLException e) {
//				throw new BOSException(e);
//			}
			throw new BOSException(exc);
		} finally {
			SQLUtils.cleanup(statement, conn);
		}

		long s4 = System.currentTimeMillis();
		log.info("全部执行完成！");

		sb.append("全部完成：耗时" + (s4 - s1) + "ms");
		return sb.toString();*/
}