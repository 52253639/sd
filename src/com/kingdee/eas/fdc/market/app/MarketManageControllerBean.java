package com.kingdee.eas.fdc.market.app;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.market.MarketManageEntryCollection;
import com.kingdee.eas.fdc.market.MarketManageEntryInfo;
import com.kingdee.eas.fdc.market.MarketManageInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

public class MarketManageControllerBean extends AbstractMarketManageControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.market.app.MarketManageControllerBean");

	public IObjectPK save(Context ctx, CoreBaseInfo model) throws EASBizException, BOSException {
		IObjectPK pk = super.save(ctx, model);
		MarketManageEntryInfo marketManageEntryInfo = null;
		MarketManageEntryCollection marketManageEntryCollection = null;
		MarketManageInfo marketManageInfo = (MarketManageInfo) model;
		marketManageEntryCollection = marketManageInfo.getEntrys();
		BigDecimal totalMoney = new BigDecimal(0);
		for (int i = 0; i < marketManageEntryCollection.size(); i++) {
			marketManageEntryInfo = marketManageEntryCollection.get(i);
			totalMoney = totalMoney.add(marketManageEntryInfo.getAmount());
		}
		marketManageInfo.setFactTotalMoney(totalMoney);
		handleIntermitNumber(ctx, marketManageInfo);
		super.save(ctx, marketManageInfo);
		return pk;
	}

	public IObjectPK submit(Context ctx, CoreBaseInfo model) throws BOSException, EASBizException {
		MarketManageEntryInfo marketManageEntryInfo = null;
		MarketManageEntryCollection marketManageEntryCollection = null;
		MarketManageInfo marketManageInfo = (MarketManageInfo) model;
		marketManageEntryCollection = marketManageInfo.getEntrys();
		BigDecimal totalMoney = new BigDecimal(0);
		for (int i = 0; i < marketManageEntryCollection.size(); i++) {
			marketManageEntryInfo = marketManageEntryCollection.get(i);
			totalMoney = totalMoney.add(marketManageEntryInfo.getAmount());
		}
		marketManageInfo.setFactTotalMoney(totalMoney);
		handleIntermitNumber(ctx, marketManageInfo);
		return super.submit(ctx, marketManageInfo);
	}

	protected void handleIntermitNumber(Context ctx, MarketManageInfo info) throws BOSException, CodingRuleException, EASBizException {
		// 如果用户在客户端手工选择了断号,则此处不必在抢号
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		String costUnitId = "";
		if (info.getCompany() != null) {
			costUnitId = info.getCompany().getId().toString();
		} else {
			costUnitId = ContextUtil.getCurrentSaleUnit(ctx).getId().toString();
		}
		if (StringUtils.isEmpty(costUnitId)) {
			return;
		}
		boolean isExist = true;
		if (!iCodingRuleManager.isExist(info, costUnitId)) {
			costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
			if (!iCodingRuleManager.isExist(info, costUnitId)) {
				isExist = false;
			}
		}
		if (isExist) {
			// 选择了断号支持或者没有选择新增显示,获取并设置编号
			if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId) || !iCodingRuleManager.isAddView(info, costUnitId))
			// 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
			{
				// 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
				String number = iCodingRuleManager.getNumber(info, costUnitId);
				info.setNumber(number);
			}
		}
	}
}