package com.kingdee.eas.fdc.schedule.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.ScheduleFacadeFactory;
import com.kingdee.eas.fdc.schedule.TaskLoadEntryCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.app.CoreBillEntryBaseControllerBean;
import com.kingdee.eas.fdc.schedule.TaskLoadEntryInfo;
import com.kingdee.eas.framework.CoreBillEntryBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;

public class TaskLoadEntryControllerBean extends AbstractTaskLoadEntryControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.TaskLoadEntryControllerBean");

	protected void _updateConfirmStatus(Context ctx, Set idSet,
			boolean isConfirm) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_sch_taskloadentry set fisconfirm=? ,fconfirmdate=? ");
		builder.addParam(Boolean.valueOf(isConfirm));
		if(isConfirm){
			builder.addParam(new Date());
		}else{
			builder.addParam(null);
		}
		builder.appendSql("where ");
		builder.appendParam("fid", idSet.toArray());
		builder.executeUpdate();
		synchronous2Task(ctx,idSet);
	}
	protected Result _save(Context ctx, IObjectCollection colls)
			throws BOSException, EASBizException {
		
		return super._save(ctx, colls);
	}
	
	private void synchronous2Task(Context ctx,Set ids) throws BOSException, EASBizException{
		Set wbsIds = new HashSet();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select distinct FWBSID from T_SCH_TaskLoadEntry where ");
		builder.appendParam("fid", ids.toArray());
		IRowSet rowSet = builder.executeQuery();
		try {
			while(rowSet.next()){
				wbsIds.add(rowSet.getString("FWBSID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ScheduleFacadeFactory.getLocalInstance(ctx).synchronizeTask(null, wbsIds, null);
	}
}