package com.kingdee.eas.fdc.sellhouse.app;

import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;

public class JoinDoSchemeControllerBean extends AbstractJoinDoSchemeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.JoinDoSchemeControllerBean");

	protected void _setEnabled(Context ctx, List idList, boolean isEnabled)
			throws BOSException, EASBizException {
		if(idList!=null && idList.size()>0){
			try {
				String sql = "update T_SHE_JoinDoScheme set fisenabled=? where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);

				for (int i = 0; i < idList.size(); i++) {
					if(isEnabled){
						sqlBuilder.addParam(new Integer("1"));
					}else{
						sqlBuilder.addParam(new Integer("0"));
					}
					sqlBuilder.addParam(idList.get(i).toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新入伙方案失败!");
				throw new BOSException(ex.getMessage() + "更新入伙方案失败!");
			}

		}
		
	}
}