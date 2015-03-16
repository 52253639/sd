package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
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

import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.base.multiapprove.ApproveResult;
import com.kingdee.eas.base.multiapprove.MultiApproveCollection;
import com.kingdee.eas.base.multiapprove.MultiApproveFactory;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountFactory;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class SpecialDiscountControllerBean extends AbstractSpecialDiscountControllerBean
{
    private static Logger logger =Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.SpecialDiscountControllerBean");
    
	protected void _audit(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		SpecialDiscountInfo billInfo = new SpecialDiscountInfo();
		billInfo.setId(billID);
		billInfo.setBizState(FDCBillStateEnum.AUDITTED);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("billId",billID.toString()));
		filter.getFilterItems().add(new FilterItemInfo("isPass",ApproveResult.PASS_VALUE));
		view.setFilter(filter);
		SorterItemInfo sorter =new SorterItemInfo();
		sorter.setPropertyName("createtime");
		sorter.setSortType(SortType.DESCEND);
		view.getSorter().add(sorter);
		view.getSelector().add("billid");
		view.getSelector().add("creator.id");
		view.getSelector().add("creator.name");
		view.getSelector().add("createtime");
		MultiApproveCollection mulColl = MultiApproveFactory.getLocalInstance(ctx).getMultiApproveCollection(view);
		if(mulColl.size()>0){
			billInfo.setAuditor(mulColl.get(0).getCreator());
			billInfo.setAuditTime(mulColl.get(0).getCreateTime());
		}else{
			billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
			billInfo.setAuditTime(new Date());
		}
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("bizState");
        selector.add("auditor");
        selector.add("auditTime");
		_updatePartial(ctx, billInfo, selector);
	}
	protected void _auditing(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		SpecialDiscountInfo billInfo = new SpecialDiscountInfo();
		billInfo.setId(billID);
		billInfo.setBizState(FDCBillStateEnum.AUDITTING);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("bizState");
        selector.add("auditor");
        selector.add("auditTime");
        _updatePartial(ctx, billInfo, selector);
	}
	protected void _unAudit(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		SpecialDiscountInfo billInfo = new SpecialDiscountInfo();
		billInfo.setId(billID);
		billInfo.setBizState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("bizState");
        selector.add("auditor");
        selector.add("auditTime");
        _updatePartial(ctx, billInfo, selector);
	}
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
		SpecialDiscountInfo info = (SpecialDiscountInfo) model;
		info.setBizState(FDCBillStateEnum.SAVED);
		
		super._save(ctx, pk, info);
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		SpecialDiscountInfo info = (SpecialDiscountInfo) model;
		info.setBizState(FDCBillStateEnum.SAVED);
		
		return super._save(ctx, info);
	}
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
		SpecialDiscountInfo info = (SpecialDiscountInfo) model;
		info.setBizState(FDCBillStateEnum.SUBMITTED);
		
		super._submit(ctx, pk, info);
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		SpecialDiscountInfo info = (SpecialDiscountInfo) model;
		info.setBizState(FDCBillStateEnum.SUBMITTED);
		
		return super._submit(ctx, info);
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("specialAgio.id", pk.toString()));
		if(SignManageFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","单据已经发生签约业务，不能进行删除操作！"));
		}
		super._delete(ctx, pk);
		
	}
}