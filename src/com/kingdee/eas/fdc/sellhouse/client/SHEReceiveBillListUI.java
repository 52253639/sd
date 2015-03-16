package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.tenancy.client.TenancyBillEditUI;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.util.client.MsgBox;

public class SHEReceiveBillListUI extends ReceiveBillListUI
{

	public SHEReceiveBillListUI() throws Exception
	{
		super();
	}
	
	public void setUITitle(String title)
	{
		super.setUITitle("售楼收款单序时簿");
	}
	
	protected String getEditUIName() {
		return SHEReceiveBillEditUI.class.getName();
	}	
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo)
	{
		viewInfo = (EntityViewInfo) mainQuery.clone();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.moneySysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
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

	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.getColumn("purchase.number").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("salesman.name").getStyleAttributes().setHided(false);
		
		this.tblMain.getColumn("tenancyContract.number").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("tenancyAdviser.name").getStyleAttributes().setHided(true);
		
		this.actionViewBill.setVisible(true);
	}

	
	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.SalehouseSys;
	}
	
	
	
	/**
	 * 查看单据
	 */
	public void actionViewBill_actionPerformed(ActionEvent e) throws Exception
	{
		this.checkSelected();
		this.checkObjectExists();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if(selectRows != null && selectRows.length > 1)
		{
			MsgBox.showInfo("请选择单行数据进行相关单据查看操作！");
			return;
		}
		String id = this.getSelectedKeyValue();
		if (id == null)	{
			return;
		}
		SelectorItemCollection selectorColl = new SelectorItemCollection();
		selectorColl.add("fdcReceiveBill.purchase");
		ReceivingBillInfo receivingBill = ReceivingBillFactory.getRemoteInstance().getReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(id)),selectorColl);
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBill.getFdcReceiveBill();
		if(fdcReceiveBillInfo.getPurchase() != null)
		{
			String billId = fdcReceiveBillInfo.getPurchase().getId().toString();
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", billId);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PurchaseEditUI.class.getName(), uiContext,null, "VIEW");
			uiWindow.show();
		}
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		String billType = "";
		if(row!=null && row.getCell("billType")!=null && row.getCell("billType").getValue()!=null){
			billType = row.getCell("billType").getValue().toString();
		}
		uiContext.put("billType", billType);
		
	}
}
