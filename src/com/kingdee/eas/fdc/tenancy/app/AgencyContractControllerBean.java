package com.kingdee.eas.fdc.tenancy.app;

import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.tenancy.AgencyContractInfo;
import com.kingdee.eas.fdc.tenancy.ContractStateEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

public class AgencyContractControllerBean extends AbstractAgencyContractControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.AgencyContractControllerBean");
    
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	AgencyContractInfo contract = (AgencyContractInfo) model;
    	contract.setContractState(ContractStateEnum.SUBMITTED);
    	handleIntermitNumber(ctx,(AgencyContractInfo)model);
    	return super._submit(ctx, model);
    }
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	super._audit(ctx, billId);
    	
    	AgencyContractInfo contractInfo = getAgencyContractInfo(ctx, new ObjectUuidPK(billId));
    	if(contractInfo == null){
    		logger.error("contractInfo为空，ID=" + billId.toString());
    		throw new BOSException("逻辑错误!");
    	}
    	contractInfo.setContractState(ContractStateEnum.Audited);
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("contractState");
    	updatePartial(ctx, contractInfo, sels);
    }
    
    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	AgencyContractInfo contractInfo = getAgencyContractInfo(ctx, new ObjectUuidPK(pk.toString()));
    	if(contractInfo == null){
    		logger.error("contractInfo为空，ID=" + pk.toString());
    		throw new BOSException("逻辑错误!");
    	}
    	contractInfo.setContractState(ContractStateEnum.Stopped);
    	contractInfo.setStopper(ContextUtil.getCurrentUserInfo(ctx));
    	contractInfo.setStopUsingDate(new Date());
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("contractState");
    	sels.add("stopper");
    	sels.add("stopUsingDate");
    	updatePartial(ctx, contractInfo, sels);
    }
    
    protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	AgencyContractInfo contractInfo = getAgencyContractInfo(ctx, new ObjectUuidPK(pk.toString()));
    	if(contractInfo == null){
    		logger.error("contractInfo为空，ID=" + pk.toString());
    		throw new BOSException("逻辑错误!");
    	}
    	contractInfo.setContractState(ContractStateEnum.InUsing);
    	contractInfo.setHandler(ContextUtil.getCurrentUserInfo(ctx));
    	contractInfo.setStartUsingDate(new Date());
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("contractState");
    	sels.add("handler");
    	sels.add("startUsingDate");
    	updatePartial(ctx, contractInfo, sels);
    }
    
    public static void handleIntermitNumber(Context ctx, AgencyContractInfo info)
			throws BOSException, CodingRuleException, EASBizException {
		// 如果用户在客户端手工选择了断号,则此处不必在抢号
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getLocalInstance(ctx);

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
    
}