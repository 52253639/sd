package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.List;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedFactory;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;

public class AFMortgagedControllerBean extends AbstractAFMortgagedControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.AFMortgagedControllerBean");

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException
	{
		AFMortgagedInfo dataBaseInfo = (AFMortgagedInfo) model;
		//编码规则支持不断号处理
		com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getLocalInstance(ctx);
		String companyID = null;
		companyID = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		if (iCodingRuleManager.isExist(dataBaseInfo, companyID)) {//是否存在指定条件的编码规则对象
             if(!iCodingRuleManager.isAddView(dataBaseInfo, companyID)){//指定的编码规则是否启用断号支持功能
            	 dataBaseInfo.setNumber(iCodingRuleManager.getNumber(dataBaseInfo,companyID));
            }
        }
		return super._submit(ctx, dataBaseInfo);
	}

	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException
	{
		AFMortgagedInfo dataBaseInfo = (AFMortgagedInfo) model;
		
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, dataBaseInfo.getNumber(), CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (dataBaseInfo.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, dataBaseInfo.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			// filter.setMaskString("#1 and #2");
			
		}
		if(dataBaseInfo!=null && dataBaseInfo.getProject()!=null){
			filterItem = new FilterItemInfo("project",dataBaseInfo.getProject().getId());
			filter.getFilterItems().add(filterItem);
		}
		if (super._exists(ctx, filter)) {
			String number = this._getPropertyAlias(ctx, dataBaseInfo, IFWEntityStruct.dataBase_Number) + dataBaseInfo.getNumber();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { number });
		}
	}

	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException
	{
		AFMortgagedInfo dataBaseInfo = (AFMortgagedInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, dataBaseInfo.getName(), CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (dataBaseInfo.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, dataBaseInfo.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			// filter.setMaskString("#1 and #2");
			
		}
		filterItem = new FilterItemInfo("project",dataBaseInfo.getProject().getId());
		filter.getFilterItems().add(filterItem);
		if (super._exists(ctx, filter)) {
			String name = this._getPropertyAlias(ctx, dataBaseInfo, IFWEntityStruct.dataBase_Name) + dataBaseInfo.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { name });
		}
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		//回收编码， 支持断号处理
//		AFMortgagedInfo dataBaseInfo =AFMortgagedFactory.getLocalInstance(ctx).getAFMortgagedInfo(pk);
//		com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getLocalInstance(ctx);
//		String companyID = null;
//		companyID = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
//		if (iCodingRuleManager.isExist(dataBaseInfo, companyID)) {//是否存在指定条件的编码规则对象
//             if(iCodingRuleManager.isUseIntermitNumber(dataBaseInfo, companyID)){//指定的编码规则是否启用断号支持功能
//            	if(dataBaseInfo.getNumber()!=null){ 
//            	 iCodingRuleManager.recycleNumber(dataBaseInfo, companyID, dataBaseInfo.getNumber());
//            	}
//            }
//		}
		super._delete(ctx, pk);
	}

	protected void _setEnabled(Context ctx, List idList, boolean isEnabled)
			throws BOSException, EASBizException {
		if(idList!=null && idList.size()>0){
			try {
				String sql = "update T_SHE_AFMortgaged set fisenabled=? where fid=?";
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
				logger.error(ex.getMessage() + "更新按揭方案失败!");
				throw new BOSException(ex.getMessage() + "更新按揭方案失败!");
			}

		}
		
	}
    
}