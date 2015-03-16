package com.kingdee.eas.fdc.invite.app;

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

import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.PageHeadInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.eas.fdc.invite.PageHeadCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

public class PageHeadControllerBean extends AbstractPageHeadControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.invite.app.PageHeadControllerBean");

	/**
	 * 处理
	 * 
	 * @author sxhong Date 2006-11-25
	 */
	protected void handleIntermitNumber(Context ctx, DataBaseInfo info)
			throws BOSException, CodingRuleException, EASBizException {
		// 如果用户在客户端手工选择了断号,则此处不必在抢号
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getLocalInstance(ctx);
		// 对成本中心进行处理
		String costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId()
				.toString();
		if (StringUtils.isEmpty(costUnitId)) {
			return;
		}
		if (iCodingRuleManager.isExist(info, costUnitId)) {
			// 选择了断号支持或者没有选择新增显示,获取并设置编号
			if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId)
					|| !iCodingRuleManager.isAddView(info, costUnitId))
			// 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
			{
				// 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
				String number = iCodingRuleManager.getNumber(info, costUnitId);
				info.setNumber(number);
			}
		}
	}

	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		DataBaseInfo fDCBillInfo = (DataBaseInfo) model;
		if (fDCBillInfo.getId() == null
				|| !this._exists(ctx, new ObjectUuidPK(fDCBillInfo.getId()))) {
			handleIntermitNumber(ctx, fDCBillInfo);
		}
		checkNameDup(ctx,model);
		super._save(ctx, pk, fDCBillInfo);
	}

	private void checkNameDup(Context ctx, IObjectValue model) throws EASBizException, BOSException{
		DataBaseInfo fDCBillInfo = (DataBaseInfo) model;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if(fDCBillInfo.getId()!=null)
			builder.appendSql("select * from t_inv_pageHead where upper(fname_l2)=?  and fid!=?  ");
		else
			builder.appendSql("select * from t_inv_pageHead where upper(fname_l2)=? ");
		builder.addParam(fDCBillInfo.getName().toUpperCase());
		if(fDCBillInfo.getId()!=null){
			builder.addParam(fDCBillInfo.getId().toString());
		}
		if(builder.isExist()){
			throw new EASBizException(new NumericExceptionSubItem("100","存在重复的页签名称,为便于Excel导出,页签名称不区分大小写"));
		}
	}
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		DataBaseInfo fDCBillInfo = (DataBaseInfo) model;
		if (fDCBillInfo.getId() == null
				|| !this._exists(ctx, new ObjectUuidPK(fDCBillInfo.getId()))) {
			handleIntermitNumber(ctx, fDCBillInfo);
		}
		checkNameDup(ctx,model);
		return super._save(ctx, fDCBillInfo);
	}

	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		DataBaseInfo fDCBillInfo = (DataBaseInfo) model;
		if (fDCBillInfo.getId() == null
				|| !this._exists(ctx, new ObjectUuidPK(fDCBillInfo.getId()))) {
			handleIntermitNumber(ctx, fDCBillInfo);
		}
		checkNameDup(ctx,model);
		super._submit(ctx, pk, fDCBillInfo);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		DataBaseInfo fDCBillInfo = (DataBaseInfo) model;
		if (fDCBillInfo.getId() == null
				|| !this._exists(ctx, new ObjectUuidPK(fDCBillInfo.getId()))) {
			handleIntermitNumber(ctx, fDCBillInfo);
		}
		checkNameDup(ctx,model);
		return super._submit(ctx, fDCBillInfo);
	}
	
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		recycleNumber(ctx, pk);
		super._delete(ctx, pk);
	}
	
	/**
	 * 回收Number，如果配置了编码规则并支持断号的话
	 * @param ctx
	 * @param pk
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 */
	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		DataBaseInfo info = (DataBaseInfo)_getValue(ctx, pk);
		OrgUnitInfo currentCostUnit = ContextUtil.getCurrentOrgUnit(ctx);		
		if(currentCostUnit == null){
			currentCostUnit = ContextUtil.getCurrentSaleUnit(ctx);
		}
		String curOrgId = currentCostUnit.getId().toString();
		if(info.getNumber()!=null&&info.getNumber().length()>0){
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	        if (iCodingRuleManager.isExist(info, curOrgId) && iCodingRuleManager.isUseIntermitNumber(info, curOrgId)) {
	            iCodingRuleManager.recycleNumber(info, curOrgId, info.getNumber());
	        }
		}
	}
}