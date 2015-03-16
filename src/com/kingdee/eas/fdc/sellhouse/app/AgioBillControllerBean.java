package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK; //import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean; //import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;

import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysContextConstant;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.fdc.sellhouse.AgioBillCollection;

public class AgioBillControllerBean extends AbstractAgioBillControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.AgioBillControllerBean");

	protected void _enable(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		UserInfo currentUser = (UserInfo) ctx.get(SysContextConstant.USERINFO);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SHE_AgioBill set  FIsEnabled = ?,FEnableManId = ?,FEnabledDate = NOW()   where FID = ? ");
		builder.addParam(new Integer(1));
		builder.addParam(currentUser.getId().toString());
		builder.addParam(pk.toString());
		builder.executeUpdate(ctx);
	}

	protected void _disEnable(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		UserInfo currentUser = (UserInfo) ctx.get(SysContextConstant.USERINFO);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SHE_AgioBill set  FIsEnabled = ?,FUnenableManId = ?,FUnenabledDate = NOW()  where FID = ? ");
		builder.addParam(new Integer(0));
		builder.addParam(currentUser.getId().toString());
		builder.addParam(pk.toString());
		builder.executeUpdate(ctx);
	}
	
	
	protected void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {

		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);
		
		checkNumberDup(ctx, FDCBillInfo);
		
		checkNameDup(ctx, FDCBillInfo);	
	}
	
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)	throws BOSException, EASBizException {
		//同一根项目内设置时，“编码、名称”不允许重复
		if(!isUseName()) return;
		
		AgioBillInfo agioInfo = (AgioBillInfo)billInfo;
		if(agioInfo.getProject()==null) return;
		SellProjectInfo spInfo = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select * where id = '"+agioInfo.getProject().getId()+"'");
		while(spInfo.getLevel()!=1 && spInfo.getParent()!=null) {
			spInfo = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select * where id = '"+spInfo.getParent().getId()+"'");
		}
		if(spInfo==null) return;
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", agioInfo.getNumber()));
		filter.getFilterItems().add(new FilterItemInfo("project.id", spInfo.getId() ));
		filter.getFilterItems().add(new FilterItemInfo("project.longNumber", spInfo.getLongNumber()+"!%",CompareType.LIKE ));
		if (agioInfo.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", agioInfo.getId().toString(), CompareType.NOTEQUALS));
			filter.setMaskString("#0 and (#1 or #2) and #3");
		}else{
			filter.setMaskString("#0 and (#1 or #2) ");
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NAME_DUP);
		}
	}
	

	protected void checkNumberDup(Context ctx, FDCBillInfo billInfo)	throws BOSException, EASBizException {
		//同一根项目内设置时，“编码、名称”不允许重复
		if(!isUseNumber()) return;
		
		AgioBillInfo agioInfo = (AgioBillInfo)billInfo;
		if(agioInfo.getProject()==null) return;
		SellProjectInfo spInfo = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select * where id = '"+agioInfo.getProject().getId()+"'");
		while(spInfo.getLevel()!=1 && spInfo.getParent()!=null) {
			spInfo = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo("select * where id = '"+spInfo.getParent().getId()+"'");
		}
		if(spInfo==null) return;
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", agioInfo.getNumber()));
		filter.getFilterItems().add(new FilterItemInfo("project.id", spInfo.getId() ));
		filter.getFilterItems().add(new FilterItemInfo("project.longNumber", spInfo.getLongNumber()+"!%",CompareType.LIKE ));
		if (agioInfo.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", agioInfo.getId().toString(), CompareType.NOTEQUALS));
			filter.setMaskString("#0 and (#1 or #2) and #3");
		}else{
			filter.setMaskString("#0 and (#1 or #2) ");
		}		
		
		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}
	}
	
}