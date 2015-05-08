package com.kingdee.eas.fdc.schedule.app;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.AdjustReqGattWBSEnum;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqInfo;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqWBSEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCreateReasonEnum;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerFactory;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class ScheduleAdjustGattReqControllerBean extends AbstractScheduleAdjustGattReqControllerBean
{
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("entrys.*");
    	sic.add("entrys.wbs.id");
    	sic.add("baseVer.*");
    	sic.add("baseVer.version");
    	sic.add("newVer.*");
    	
    	ScheduleAdjustGattReqInfo gattReqInfo = this.getScheduleAdjustGattReqInfo(ctx, new ObjectUuidPK(billId),sic);
    	if(gattReqInfo.getState()!=null&&(gattReqInfo.getState().equals(FDCBillStateEnum.SUBMITTED)||gattReqInfo.getState().equals(FDCBillStateEnum.AUDITTING))){
    		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    		builder.setPrepareStatementSql("update t_sch_fdcwbs set FIsUnVisible=? where fid=?");
    		builder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
    		
    		for(Iterator it = gattReqInfo.getEntrys().iterator();it.hasNext();){
    			ScheduleAdjustGattReqWBSEntryInfo entry = (ScheduleAdjustGattReqWBSEntryInfo)it.next();
    			if (AdjustReqGattWBSEnum.ADDNEW.equals(entry.getAdjustWbsType())) {
    				builder.addParam(Boolean.FALSE);
    				builder.addParam(entry.getWbs().getId().toString());
    				builder.addBatch();
    				
    			} else if (AdjustReqGattWBSEnum.DELETE.equals(entry.getAdjustWbsType())) {
    				builder.addParam(Boolean.TRUE);
    				builder.addParam(entry.getWbs().getId().toString());
    				builder.addBatch();
    			}
    		}
    		if(gattReqInfo.getEntrys().size()>0){
    			builder.executeBatch();
    		}
    		
    		builder.clear();
    		
    		float version = gattReqInfo.getBaseVer().getVersion();
    		float newVersion = version + 1;
    		
    		/***
    		 * 更新此项目下的最新版本的任务为非最新版本
    		 * 状态为已调整
    		 */
    		Date auditTime = new Date();
    		builder.appendSql("update t_sch_fdcschedule set fislatestver=0,fstate=? where fislatestver=1 and fprojectid=?");
    		builder.addParam(ScheduleStateEnum.ADJUSTED_VALUE);
    		builder.addParam(gattReqInfo.getCurProject().getId().toString());
    		
    		builder.execute();
    		
    		/***
    		 * 更新此项目下的调整中的任务为最新版本，及版本号
    		 */
    		builder.clear();
    		builder.appendSql("update t_sch_fdcschedule set fislatestver=1,fstate=?,faudittime=?,fauditorid=?,FCreateReason=?,fversion = (select verentry.fversion from t_sch_schedulevermanagerentry verentry where  t_sch_fdcschedule.fid=verentry.fscheduleid)");
    		builder.appendSql(" 	where fversion=0 and fprojectid=? and ");
    		builder.appendSql(" 	exists(select 1 from t_sch_schedulevermanagerentry verentry where  t_sch_fdcschedule.fid=verentry.fscheduleid)");
    		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
    		builder.addParam(auditTime);
    		builder.addParam(ContextUtil.getCurrentUserInfo(ctx).getId().toString());
    		builder.addParam(ScheduleCreateReasonEnum.ADJUST_VALUE);
    		builder.addParam(gattReqInfo.getCurProject().getId().toString());
    		builder.execute();
    		
    		/***
    		 * 更新此项目下的总计划版本最新版本为非最新版本
    		 * 状态为已调整
    		 */
    		builder.clear();
    		builder.appendSql("update t_sch_schedulevermanager set fislatestver=0,fstate=? where fislatestver=1 and fprojectid=? ");
    		builder.addParam(ScheduleStateEnum.ADJUSTED_VALUE);
    		builder.addParam(gattReqInfo.getCurProject().getId().toString());
    		builder.execute();
    		
    		/***
    		 * 更新此项目下的调整中的总计划为最新版本，及版本号
    		 */
    		builder.clear();
    		builder.appendSql("update t_sch_schedulevermanager set fislatestver=1 , fversion=? ,fcreatereason=?,fstate=? where fprojectid=? and fversion=0 ");
    		builder.addParam(Float.valueOf(String.valueOf(newVersion)));
    		builder.addParam(ScheduleCreateReasonEnum.ADJUST_VALUE);
    		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
    		builder.addParam(gattReqInfo.getCurProject().getId().toString());
    		builder.execute();
    		
    		/***
    		 * 更新此项目下的调整单为已审批状态
    		 */
    		builder.clear();
    		builder.appendSql("update T_SCH_ScheduleAdjustGattReq set fstate=?,faudittime=?,fauditorid=? where fid=? ");
    		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
    		builder.addParam(auditTime);
    		builder.addParam(ContextUtil.getCurrentUserInfo(ctx).getId().toString());
    		builder.addParam(gattReqInfo.getId().toString());
    		builder.execute();
    		
    		
    	}else{
    		throw new EASBizException(new NumericExceptionSubItem("100","存在不符合条件的记录，不允许此操作！"));
    	}
    	
	}

	protected void _audit(Context ctx, List idList) throws BOSException,
			EASBizException {
		super._audit(ctx, idList);
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("entry.*");
    	sic.add("entry.wbs.id");
    	sic.add("baseVer.*");
    	sic.add("newVer.*");
    	
    	ScheduleAdjustGattReqInfo gattReqInfo = this.getScheduleAdjustGattReqInfo(ctx, pk,sic);
    	if(gattReqInfo.getState()!=null&&
    			(gattReqInfo.getState().equals(FDCBillStateEnum.SUBMITTED)||gattReqInfo.getState().equals(FDCBillStateEnum.SAVED))){
    		String newVerId = gattReqInfo.getNewVer().getId().toString();
    		sic.clear();
    		sic.add("entrys.*");
    		sic.add("entrys.schedule.id");
    		sic.add("entrys.schedule.state");
    		ScheduleVerManagerInfo info = ScheduleVerManagerFactory.getLocalInstance(ctx).getScheduleVerManagerInfo(new ObjectUuidPK(newVerId),sic);
    		for(Iterator it = info.getEntrys().iterator();it.hasNext();){
    			ScheduleVerManagerEntryInfo entry = (ScheduleVerManagerEntryInfo)it.next();
    			if (entry.getSchedule() != null
						&& (ScheduleStateEnum.SAVED.equals(entry.getSchedule().getState()) || ScheduleStateEnum.SUBMITTED.equals(entry
								.getSchedule().getState()))) {
    				FDCScheduleFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(entry.getSchedule().getId()));
    			}
    		}
    		FilterInfo fi = new FilterInfo();
    		fi.getFilterItems().add(new FilterItemInfo("curProject", gattReqInfo.getCurProject().getId().toString()));
    		fi.getFilterItems().add(new FilterItemInfo("isUnVisible", Boolean.TRUE));
    		FDCWBSFactory.getLocalInstance(ctx).delete(fi);
    		ScheduleVerManagerFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(newVerId));
    		super.innerDelete(ctx, pk);
    		
    	}else{
    		throw new EASBizException(new NumericExceptionSubItem("100","存在不符合条件的记录，不允许此操作！"));
    	}
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		
		super._delete(ctx, arrayPK);
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		
		super._unAudit(ctx, billId);
	}

	protected void _unAudit(Context ctx, List idList) throws BOSException,
			EASBizException {
		
		super._unAudit(ctx, idList);
	}

	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ScheduleAdjustGattReqControllerBean");
}