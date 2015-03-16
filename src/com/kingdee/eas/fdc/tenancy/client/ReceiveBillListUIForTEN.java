package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.ReceiveBillEidtUI;
import com.kingdee.eas.fdc.sellhouse.client.ReceiveBillListUI;

/**
 * 租赁收款单序事簿
 * @author laiquan_luo
 *
 */
public class ReceiveBillListUIForTEN extends ReceiveBillListUI
{

	public ReceiveBillListUIForTEN() throws Exception
	{
		super();
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e)
	{
		super.prepareUIContext(uiContext, e);
		
		uiContext.put(ReceiveBillEidtUI.KEY_MONEYTSYSTYPE,MoneySysTypeEnum.TenancySys);
	}
	
	public void setUITitle(String title)
	{
		super.setUITitle("租赁收款单序事簿");
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo)
	{
		viewInfo = (EntityViewInfo) mainQuery.clone();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.moneySysType",MoneySysTypeEnum.TENANCYSYS_VALUE));
		// 合并查询条件
		try
		{
			
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (Exception e)
		{
			this.handUIException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.TenancySys;
	}
	
	
}
