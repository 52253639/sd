package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

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
import com.kingdee.eas.fdc.sellhouse.client.ReceiveBillListUI;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 租赁收款单序事簿
 */
public class TENReceiveBillListUI extends ReceiveBillListUI 
{

	public TENReceiveBillListUI() throws Exception
	{
		super();
	}
	
	public void setUITitle(String title)
	{
		super.setUITitle("租赁收款单序时簿");
	}

	protected String getEditUIName() {
		return TENReceiveBillEditUI.class.getName();
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

	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.getColumn("purchase.number").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("salesman.name").getStyleAttributes().setHided(true);
		
		this.tblMain.getColumn("tenancyContract.number").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("tenancyAdviser.name").getStyleAttributes().setHided(false);
		
		this.actionViewBill.setVisible(true);
		//旧收款单功能屏蔽只让查看
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionVoucher.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionRec.setVisible(false);
		this.actionCancelRec.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionAdjust.setVisible(false);
		this.actionCreateInvoice.setVisible(false);
		this.actionClearInvoice.setVisible(false);
		this.actionReceipt.setVisible(false);
		this.actionRetakeReceipt.setVisible(false);
	}
	
	
	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.TenancySys;
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
		selectorColl.add("fdcReceiveBill.tenancyContract");
		ReceivingBillInfo receivingBill = ReceivingBillFactory.getRemoteInstance().getReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(id)),selectorColl);
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBill.getFdcReceiveBill();
		if(fdcReceiveBillInfo.getTenancyContract() != null)
		{
			String billId = fdcReceiveBillInfo.getTenancyContract().getId().toString();
			
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", billId);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(TenancyBillEditUI.class.getName(), uiContext,null, "VIEW");
			uiWindow.show();
		}
	}
	
	
}
