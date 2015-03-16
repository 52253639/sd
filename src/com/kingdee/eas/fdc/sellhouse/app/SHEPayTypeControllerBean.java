package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.util.app.ContextUtil;

public class SHEPayTypeControllerBean extends AbstractSHEPayTypeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.SHEPayTypeControllerBean");

	protected void _enable(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_SHE_SHEPayType set  FIsEnabled = ?  where FID = ? ");
		builder.addParam(new Integer(1));
		builder.addParam(pk.toString());
		builder.executeUpdate(ctx);
	}

	protected void _disEnable(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_SHE_SHEPayType set  FIsEnabled = ?  where FID = ? ");
		builder.addParam(new Integer(0));
		builder.addParam(pk.toString());
		builder.executeUpdate(ctx);

	}

	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SHEPayTypeInfo type = (SHEPayTypeInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(
				IFWEntityStruct.dataBase_Name, type.getName(),
				CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (type.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, type
					.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		filterItem = new FilterItemInfo("project.id", type.getProject().getId().toString());
		filter.getFilterItems().add(filterItem);
		
		filterItem = new FilterItemInfo("isDelete", Boolean.FALSE);
		filter.getFilterItems().add(filterItem);
		
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));

		if (super._exists(ctx, filter)) {
			String name = this._getPropertyAlias(ctx, type,
					IFWEntityStruct.dataBase_Name)
					+ type.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { name });
		}
	}

	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SHEPayTypeInfo type = (SHEPayTypeInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(
				IFWEntityStruct.dataBase_Number, type.getNumber(),
				CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (type.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, type
					.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		filterItem = new FilterItemInfo("project.id", type.getProject().getId().toString());
		filter.getFilterItems().add(filterItem);
		
		filterItem = new FilterItemInfo("isDelete", Boolean.FALSE);
		filter.getFilterItems().add(filterItem);
		
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));

		if (super._exists(ctx, filter)) {
			String number = this._getPropertyAlias(ctx, type,
					IFWEntityStruct.dataBase_Number)
					+ type.getNumber();
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { number });
		}
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		DataBaseCodeRuleHelper.handleIntermitNumber(ctx, (DataBaseInfo)model);
		return super._submit(ctx, model);
	}
	
	/**
	 * 增加在删除单据的时候
	 * 回收number
	 * by renliang at 2010-12-4
	 */
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
		SHEPayTypeInfo info = (SHEPayTypeInfo)_getValue(ctx, pk);
		//------ 销售组织下获取成本中心为空的处理 zhicheng_jin 090319
		OrgUnitInfo currentCostUnit = ContextUtil.getCurrentOrgUnit(ctx);		
		if(currentCostUnit == null){
			currentCostUnit = ContextUtil.getCurrentSaleUnit(ctx);
		}
		//-------- over --------
		String curOrgId = currentCostUnit.getId().toString();
		if(info.getNumber()!=null&&info.getNumber().length()>0){
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	        if (iCodingRuleManager.isExist(info, curOrgId) && iCodingRuleManager.isUseIntermitNumber(info, curOrgId)) {
	            iCodingRuleManager.recycleNumber(info, curOrgId, info.getNumber());
	        }
		}
	}

	/**
	 * 逻辑删除付款方案
	 * by renliang at 2011-6-1
	 */
	protected boolean _deleteById(Context ctx, String billId)
			throws BOSException, EASBizException {
		
		try{
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder
					.appendSql("update T_SHE_SHEPayType set  FIsDelete = ?  where FID = ? ");
			builder.addParam(new Integer(1));
			builder.addParam(billId);
			builder.executeUpdate(ctx);
		}catch(BOSException ex){
			logger.error(ex.getMessage()+"update FIsDelete is falure in T_SHE_SHEPayType!");
			return false;
		}
		return true;
	}
}