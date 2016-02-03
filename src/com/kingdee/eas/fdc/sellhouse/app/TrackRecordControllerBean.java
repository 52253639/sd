package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;

public class TrackRecordControllerBean extends AbstractTrackRecordControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.TrackRecordControllerBean");


	protected boolean isUseName() {
		return false;
	}

	
	protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo)
	throws EASBizException, BOSException {
		if (fDCBillInfo.getOrgUnit() == null) {
			FullOrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx)
					.castToFullOrgUnitInfo();
			fDCBillInfo.setOrgUnit(orgUnit);
		}
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		IObjectPK pk = super._submit(ctx, model);
		
		StringBuffer upsql = new StringBuffer();
		upsql.append("update T_SHE_TrackRecord set fisSyn=0 where fid='"+pk.toString()+"'");
		DbUtil.execute(ctx, upsql.toString());
		
		return pk;
	}
	protected void _updatePartial(Context ctx, IObjectValue model,SelectorItemCollection selector) throws BOSException,
			EASBizException {
		super._updatePartial(ctx, model, selector);
		
		TrackRecordInfo tr = (TrackRecordInfo) model;
		StringBuffer upsql = new StringBuffer();
		upsql.append("update T_SHE_TrackRecord set fisSyn=0 where fid='"+tr.getId().toString()+"'");
		DbUtil.execute(ctx,upsql.toString());
	}
}