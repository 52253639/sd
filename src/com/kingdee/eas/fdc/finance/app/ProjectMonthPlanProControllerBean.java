package com.kingdee.eas.fdc.finance.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Calendar;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
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

import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanProCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.util.NumericExceptionSubItem;

public class ProjectMonthPlanProControllerBean extends AbstractProjectMonthPlanProControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.ProjectMonthPlanProProControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("curProject.id");
		sel.add("version");
		sel.add("bizDate");
		sel.add("respPerson.id");
		ProjectMonthPlanProInfo info =this.getProjectMonthPlanProInfo(ctx, new ObjectUuidPK(billId), sel);
		String proId = info.getCurProject().getId().toString();
		String personId=info.getRespPerson().getId().toString();
		int version = info.getVersion();
		version = version-1;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(info.getBizDate());
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_FNC_ProjectMonthPlanPro set FIsLatest = 1 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update T_FNC_ProjectMonthPlanPro set FIsLatest = 0 where fCurProjectid = '");
		sql.append(proId).append("'");
		sql.append("and FVersion = ").append(version);
		sql.append("and year(fbizDate) = ").append(year);
		sql.append("and month(fbizDate) = ").append(month);
		sql.append("and FRespPersonId = '").append(personId).append("'");;
		fdcSB.addBatch(sql.toString());
		fdcSB.executeBatch();
    }
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._unAudit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("curProject.id");
		sel.add("version");
		sel.add("isLatest");
		sel.add("bizDate");
		sel.add("respPerson.id");
		ProjectMonthPlanProInfo info =this.getProjectMonthPlanProInfo(ctx, new ObjectUuidPK(billId), sel);
		if(!info.isIsLatest()){
			throw new EASBizException(new NumericExceptionSubItem("100","非最新版本不能反审批！"));
		}
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",info.getCurProject().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("respPerson.id",info.getRespPerson().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("bizDate",info.getBizDate()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
		if(ProjectMonthPlanProFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","单据已修订，不能进行反审批操作！"));
		}
		String proId = info.getCurProject().getId().toString();
		String personId=info.getRespPerson().getId().toString();
		int version = info.getVersion();
		version = version-1;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(info.getBizDate());
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_FNC_ProjectMonthPlanPro set FIsLatest = 0 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update T_FNC_ProjectMonthPlanPro set FIsLatest = 1 where fCurProjectid = '");
		sql.append(proId).append("'");
		sql.append("and FVersion = ").append(version);
		sql.append("and year(fbizDate) = ").append(year);
		sql.append("and month(fbizDate) = ").append(month);
		sql.append("and FRespPersonId = '").append(personId).append("'");;
		fdcSB.addBatch(sql.toString());
		fdcSB.executeBatch();
	}
    protected boolean isUseName() {
		return false;
	}
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcId",pk.toString()));
		if(ProjectMonthPlanGatherEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","单据已经被引用，禁止删除操作！"));
		}
		super._delete(ctx, pk);
	}
}