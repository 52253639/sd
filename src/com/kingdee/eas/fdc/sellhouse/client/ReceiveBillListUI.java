/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.print.IPrintActionListener;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCStringHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.sellhouse.BalanceAdjustFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.client.TenancyBillEditUI;
import com.kingdee.eas.fi.ar.IVerificationBillentry;
import com.kingdee.eas.fi.ar.OtherBillBizException;
import com.kingdee.eas.fi.ar.VerificationBillentryCollection;
import com.kingdee.eas.fi.ar.VerificationBillentryFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IReceivingBill;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ReceiveBillListUI extends AbstractReceiveBillListUI
{
	private static final Logger logger = CoreUIObject
			.getLogger(ReceiveBillListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	// 全局变量，用于在调用getBillList和getSelectedIdValues方法时
	// 判断是否是生成凭证或删除凭证操作调用(默认值为0,1=生成凭证,2=删除凭证)
	protected int voucherFlag = 0;
	
	private int printCount=0;
	private final KDNoteHelper noteHelper;

	/**
	 * output class constructor
	 */
	public ReceiveBillListUI() throws Exception
	{
		super();
		
		//******************  BEGIN  ***************************
		//jetdai 2009-12-07
		//收款单打印次数限制    
		noteHelper = new KDNoteHelper();
		noteHelper.addKDNoteActionListener(new IPrintActionListener(){
			String templateType="/bim/fdc/sellhouse/receivebill";
			public void beforePreview()
			{
				// TODO Auto-generated method stub
			}

			public void beforePrint()
			{
				// TODO Auto-generated method stub
				try{
					if(noteHelper.isPrintTimesControllable2(templateType)){
						//jetdai 2009-12-07
						//取得单据ID，得到已打印次数
						//TODO ADD CODE
						String receiveBillID = ReceiveBillListUI.this.getSelectedKeyValue();
						printCount=FDCReceiveBillFactory.getRemoteInstance().getPrintCount(receiveBillID);
						//END
						
						
						int maxPrintTimes=noteHelper.getMaxPrintTimes2(templateType);
						int copies=noteHelper.getCopies();//一次打印的份数
						if (copies+printCount<maxPrintTimes){
							printCount=printCount+copies;
						}else{
							if(maxPrintTimes-(printCount)<=0){
								MsgBox.showError("已超过最大打印次数:["+maxPrintTimes+"次]");
								noteHelper.diposePrint();//取消打印
								SysUtil.abort();
							}else{
								copies=maxPrintTimes-printCount;
								noteHelper.setCopies(copies);
								printCount=printCount+copies;
							}	
						}
						
						//JETDAI 2009-12-07
						//更新单据打印次数
						if(printCount>0){
							FDCReceiveBillFactory.getRemoteInstance().updatePrintCount(receiveBillID, printCount);
						}
						//END
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				
				
			}

			public void beforePrintOrPreview()
			{
				// TODO Auto-generated method stub
				
			}

			public void closePreview()
			{
				// TODO Auto-generated method stub
				
			}
			
		});
		//************************** END **************************
		
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception
	{
		// super.tblMain_tableSelectChanged(e);
	}

	public void onLoad() throws Exception
	{
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);
		initControl();
		
		//TODO 调整按钮先不可使用
//		this.actionAdjust.setVisible(false);
		
//		设置可以保存样式
		tHelper = new TablePreferencesHelper(this);
	}

	private void initControl()
	{
		tblMain.getColumn("amount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn("invoice.amount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("invoice.amount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		if (!saleOrg.isIsBizUnit())
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		this.actionReceiveBillPrint.setEnabled(true);
		this.actionReceiveBillPrintView.setEnabled(true);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionDelVoucher.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.menuWorkFlow.setVisible(false);
		this.actionTraceDown.setVisible(true);
		this.actionTraceUp.setVisible(true);
		this.actionUnAudit.setVisible(true);
		this.actionAttachment.setVisible(false);
		this.actionViewBill.setVisible(false);
		
		this.actionRefundment.setVisible(false);
		this.actionBatchRec.setVisible(false);
		
		this.actionCopyTo.setVisible(false);
		this.actionCreateTo.setVisible(false);
		if(MoneySysTypeEnum.SalehouseSys.equals(this.getSystemType()) || MoneySysTypeEnum.TenancySys.equals(this.getSystemType()))
		{
			this.actionBatchSett.setVisible(false);
		}
		
		//add by jiyu_guan
		actionReceipt.setEnabled(true);
		actionRetakeReceipt.setEnabled(true);
	}
	
	protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
		BigDecimal sumAmount = FDCHelper.ZERO;
		BigDecimal sumInvoiceAmount = FDCHelper.ZERO;
		BigDecimal amount = FDCHelper.ZERO;
		
		int tempTop = 0;
		String tempReference = "";
		for (int i = 0; i < this.tblMain.getRowCount(); i++)
		{
			IRow row = this.tblMain.getRow(i);
			
			String id = (String)row.getCell("id").getValue();
			
			if(row.getCell("amount").getValue() instanceof BigDecimal)
			      amount = (BigDecimal) row.getCell("amount").getValue();
			if (amount == null)
			{
				logger.warn("存在脏数据,其收款金额为null,可能收款单录入对金额非空判定有错误.请检查");
				continue;
			}
			if (amount.compareTo(FDCHelper.ZERO) < 0)
			{
				row.getCell("amount").getStyleAttributes().setFontColor(
						Color.RED);
			}
			if(amount != null){
				sumAmount = sumAmount.add((BigDecimal) amount);
			}
			Object invoiceAmount = (BigDecimal) row.getCell("invoice.amount").getValue();
			if(invoiceAmount != null){
				sumInvoiceAmount = sumInvoiceAmount.add((BigDecimal) invoiceAmount);
			}
			
			if (!tempReference.equalsIgnoreCase(id))
			{
				tempTop = i;
				tempReference = id;
			} else
			{
				// 融合单元格
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("state"), i, this.tblMain.getColumnIndex("state"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("number"), i, this.tblMain.getColumnIndex("number"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("bizDate"), i, this.tblMain.getColumnIndex("bizDate"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("building.name"), i, this.tblMain.getColumnIndex("building.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("room.unit"), i, this.tblMain.getColumnIndex("room.unit"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("room.number"), i, this.tblMain.getColumnIndex("room.number"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("purchase.number"), i, this.tblMain.getColumnIndex("purchase.number"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("payerName"), i, this.tblMain.getColumnIndex("payerName"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("receiptNumber"), i, this.tblMain.getColumnIndex("receiptNumber"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("invoice.number"), i, this.tblMain.getColumnIndex("invoice.number"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("description"), i, this.tblMain.getColumnIndex("description"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("salesman.name"), i, this.tblMain.getColumnIndex("salesman.name"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("invoice.amount"), i, this.tblMain.getColumnIndex("invoice.amount"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("isVourcher"), i, this.tblMain.getColumnIndex("isVourcher"));
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("billType"), i, this.tblMain.getColumnIndex("billType"));
				// add by jiyu_guan
				this.tblMain.getMergeManager().mergeBlock(tempTop, this.tblMain.getColumnIndex("receiptState"), i, this.tblMain.getColumnIndex("receiptState"));
			}
			
		}
		if(this.tblMain.getFootManager() != null && this.tblMain.getFootManager().getFootRow(0) != null)
		{
			this.tblMain.getFootManager().getFootRow(0).getCell("amount").setValue(sumAmount);
			this.tblMain.getFootManager().getFootRow(0).getCell("invoice.amount").setValue(sumInvoiceAmount);
		}
	}


    /**
     * 填充结算方式
     * @param row
     * @throws BOSException 
     */
	private void fillSettleTypeByRow(IRow row) throws BOSException
	{
		if(row.getCell("fdcId").getValue() == null)
			return;
		String id = row.getCell("fdcId").getValue().toString();
		
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		view.getSelector().add("settlementType.name");
		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.id",id));
		
		FDCReceiveBillEntryCollection fdcColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(view);
		
		StringBuffer temp = new StringBuffer();
		for(int i = 0; i < fdcColl.size(); i ++)
		{
			SettlementTypeInfo info = fdcColl.get(i).getSettlementType();
			if(info == null)
				continue;
			if(temp.length() > 0)
				temp.append(",");
			temp.append(info.getName());
		}
		
		row.getCell("settlementType").setValue(temp);
	}
	
	
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null)
		{
			return;
		}
		if (node.getUserObject() instanceof Integer)  //已作废
		{
			if (saleOrg.isIsBizUnit())
			{
				this.actionAddNew.setEnabled(true);
				this.actionBatchSett.setEnabled(true);
			}
		}else if (node.getUserObject() instanceof BuildingUnitInfo)
		{
			if (saleOrg.isIsBizUnit())
			{
				this.actionAddNew.setEnabled(true);
				this.actionBatchSett.setEnabled(true);
			}
		} else if (node.getUserObject() instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			if (saleOrg.isIsBizUnit())
			{
				this.actionBatchSett.setEnabled(true);
			}else
			{
				this.actionBatchSett.setEnabled(false);
			}
			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum))
			{
				if (saleOrg.isIsBizUnit())
				{
					this.actionAddNew.setEnabled(true);
				}
			} else
			{
				this.actionAddNew.setEnabled(false);			
			}
		} else if(node.getUserObject() instanceof SellProjectInfo)
		{
			if (saleOrg.isIsBizUnit())
			{
				this.actionAddNew.setEnabled(true);
				this.actionBatchSett.setEnabled(true);
			}else
			{
				this.actionAddNew.setEnabled(false);
				this.actionBatchSett.setEnabled(false);
			}
		}else
		{
			this.actionAddNew.setEnabled(false);
			this.actionBatchSett.setEnabled(false);
		}
		
		IRow footRow=null;
        KDTFootManager footRowManager= tblMain.getFootManager();
        if (footRowManager==null)
        {
            String total=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
            footRowManager = new KDTFootManager(this.tblMain);
            footRowManager.addFootView();
            this.tblMain.setFootManager(footRowManager);
            footRow= footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
            this.tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
            this.tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        }else{
            footRow=footRowManager.getFootRow(0);
        }
        footRow.getStyleAttributes().setBackground(new Color(0xf6, 0xf6, 0xbf));
		
		this.execQuery();
		
		FDCClientUtils.fmtFootNumber(this.tblMain, new String[]
		{ "amount", "invoice.amount" });
		FDCClientHelper.initTable(tblMain);
		
		this.refresh(null);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e)
	{

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null)
		{
		
			if (node.getUserObject() instanceof BuildingUnitInfo)
			{
				BuildingUnitInfo unit = (BuildingUnitInfo)node.getUserObject();
				if(unit.getBuilding() != null)
				{
					uiContext.put("sellProjectInfo", unit.getBuilding().getSellProject());
				}
				else
				{
					uiContext.put("sellProjectInfo", null);
				}
				
			} else if (node.getUserObject() instanceof BuildingInfo)
			{
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				uiContext.put("sellProjectInfo", building.getSellProject());
				
			} 
			else if(node.getUserObject() instanceof SellProjectInfo)
			{
				SellProjectInfo sellProjectInfo = (SellProjectInfo)node.getUserObject();
				uiContext.put("sellProjectInfo", sellProjectInfo);
			}
			else
			{
				uiContext.put("sellProjectInfo", null);
			}
		}
		
		
		
		super.prepareUIContext(uiContext, e);
	}
	
	public void actionReceiveBillPrint_actionPerformed(ActionEvent e)
			throws Exception
	{
		
		this.checkSelected();
		String receiveBillID = this.getSelectedKeyValue();
		ReceiveBillPrintDataProvider data = new ReceiveBillPrintDataProvider(
				receiveBillID,
				new MetaDataPK(
						"com.kingdee.eas.fdc.sellhouse.app.ReceivePrintQuery"));
		noteHelper.print("/bim/fdc/sellhouse/receiveBill", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
		
		/*com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/receiveBill", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
		*/
		super.actionReceiveBillPrint_actionPerformed(e);

	}

	/**
	 * output actionReceiveBillPrintView_actionPerformed method
	 */
	public void actionReceiveBillPrintView_actionPerformed(ActionEvent e)
			throws Exception
	{
		this.checkSelected();
		String receiveBillID = this.getSelectedKeyValue();
		ReceiveBillPrintDataProvider data = new ReceiveBillPrintDataProvider(
				receiveBillID,
				new MetaDataPK(
						"com.kingdee.eas.fdc.sellhouse.app.ReceivePrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		noteHelper.printPreview("/bim/fdc/sellhouse/receiveBill", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionReceiveBillPrintView_actionPerformed(e);
	}
	
	protected void execQuery() {
		super.execQuery();
		BigDecimal sumAmount = FDCHelper.ZERO;
		BigDecimal sumInvoiceAmount = FDCHelper.ZERO;

		for(int i=0; i<tblMain.getRowCount(); i++){
			IRow row = tblMain.getRow(i);
			
			Object amount = row.getCell("amount").getValue();
			if(amount != null){
				sumAmount = sumAmount.add((BigDecimal) amount);
			}
			Object invoiceAmount = (BigDecimal) row.getCell("invoice.amount").getValue();
			if(invoiceAmount != null){
				sumInvoiceAmount = sumInvoiceAmount.add((BigDecimal) invoiceAmount);
			}
		}
		this.tblMain.getFootManager().getFootRow(0).getCell("amount").setValue(sumAmount);
		this.tblMain.getFootManager().getFootRow(0).getCell("invoice.amount").setValue(sumInvoiceAmount);
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo)
	{
		try
		{
			FilterInfo roomFilter = new FilterInfo();
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			if(node == null)
			{
				return super.getQueryExecutor(queryPK, viewInfo);
			}
			if (node.getUserObject() instanceof Integer)  //已作废
			{
				Integer unit = (Integer) node.getUserObject();
				BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
						.getParent()).getUserObject();
				String buildingId = building.getId().toString();
				roomFilter.getFilterItems().add(
						new FilterItemInfo("building.id", buildingId));
				roomFilter.getFilterItems().add(
						new FilterItemInfo("unit", unit));
			}else if (node.getUserObject() instanceof BuildingUnitInfo)
			{
				BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
				BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
						.getParent()).getUserObject();
				String buildingId = building.getId().toString();
				roomFilter.getFilterItems().add(
						new FilterItemInfo("building.id", buildingId));
				roomFilter.getFilterItems().add(
						new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
			} else if (node.getUserObject() instanceof BuildingInfo)
			{
				BuildingInfo building = (BuildingInfo) node.getUserObject();
//				if (!building.getCodingType().equals(
//						CodingTypeEnum.UnitFloorNum))
//				{
					String buildingId = building.getId().toString();
					roomFilter.getFilterItems().add(
							new FilterItemInfo("building.id", buildingId));
//				} else
//				{
//					roomFilter.getFilterItems().add(
//							new FilterItemInfo("id", null));
//				}
			} else if(node.getUserObject() instanceof SubareaInfo){
				SubareaInfo subarea = (SubareaInfo) node.getUserObject();
				roomFilter.getFilterItems().add(
						new FilterItemInfo("building.subarea.id", subarea.getId().toString()));
			}else if(node.getUserObject() instanceof SellProjectInfo){
				SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();			
				String sellProjectId = sellProject.getId().toString();
				roomFilter.getFilterItems().add(
							new FilterItemInfo("building.sellProject.id", sellProjectId));
			}
			
			else
			{
				roomFilter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(roomFilter);
			view.getSelector().add("id");
			RoomCollection rooms = RoomFactory.getRemoteInstance()
					.getRoomCollection(view);
			Set idSet = new HashSet();
			idSet.add("null");
			for (int i = 0; i < rooms.size(); i++)
			{
				idSet.add(rooms.get(i).getId().toString());
			}
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("room.id", idSet, CompareType.INCLUDE));
			
			//如果是销售项目，则把房间为空的诚意金收款单也显示出来。目前把这个操作只限定在 售楼系统中
			if(node.getUserObject() instanceof SellProjectInfo 
					&& MoneySysTypeEnum.SalehouseSys.equals(this.getSystemType()))
			{
				SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();			
				String sellProjectId = sellProject.getId().toString();
				
				//房间为空，但是用项目来进行过滤
				filter.getFilterItems().add(new FilterItemInfo("room.id",null));
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProjectId));
				filter.setMaskString("#0 or ( #1 and #2 )");
			}
			
		
			
			//viewInfo = (EntityViewInfo) this.mainQuery.clone();

			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		for (int i = 0; i < this.tblMain.getRowCount(); i++)
		{
			IRow row = this.tblMain.getRow(i);
			BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
			if (amount != null && amount.compareTo(FDCHelper.ZERO) < 0)
			{
				row.getCell("amount").getStyleAttributes().setFontColor(
						Color.RED);
			}
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return ReceivingBillFactory.getRemoteInstance();
	}

	protected String getEditUIName()
	{
		return ReceiveBillEidtUI.class.getName();
	}

	protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}

	
	//使项目或单元树  --  区分系统    子类覆盖
	protected MoneySysTypeEnum getSystemType() {
		return null;
	}	
	
	protected void initTree() throws Exception
	{
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,getSystemType()));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
	}

	public void actionClearInvoice_actionPerformed(ActionEvent e)
			throws Exception
	{
		super.actionClearInvoice_actionPerformed(e);
		String id = this.getSelectedKeyValue();
		if (id != null)
		{
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("fdcReceiveBill.invoice.*");
			ReceivingBillInfo rev = ReceivingBillFactory.getRemoteInstance()
					.getReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(id)),
							sels);
			InvoiceInfo invoice = rev.getFdcReceiveBill().getInvoice();
			if (invoice == null)
			{
				MsgBox.showInfo("选择收款单没有开发票!");
				return;
			}
			// 增加将状态改为换发票之前状态，并更新操作记录 update by jiyu_guan
			IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory.getRemoteInstance();
			int ret = MsgBox.showConfirm3(this, "是否清除同发票的其他收款单?");
			if (ret == MsgBox.YES)
			{
				facade.clearInvoice(invoice.getId().toString(), true);
				String sql = "update T_SHE_FDCReceiveBill set FInvoiceID=null,FReceiptState = (CASE FReceiptNumber WHEN Null THEN 0 ELSE 1 END) where FInvoiceID = '"
						+ invoice.getId().toString() + "'";
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql(sql);
				builder.executeUpdate();
				InvoiceFactory.getRemoteInstance().delete(
						new ObjectUuidPK(invoice.getId()));
			} else if (ret == MsgBox.NO)
			{
				facade.clearInvoice(rev.getFdcReceiveBill().getId().toString(), false);
				String sql = "update T_SHE_FDCReceiveBill set FInvoiceID=null,FReceiptState = (CASE FReceiptNumber WHEN Null THEN 0 ELSE 1 END) where FID = '"
						+ rev.getFdcReceiveBill().getId().toString() + "'";
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql(sql);
				builder.executeUpdate();
			} else
			{
				return;
			}
			this.refresh(null);
		}
	}

	public void actionCreateInvoice_actionPerformed(ActionEvent e)
			throws Exception
	{
		String id = this.getSelectedKeyValue();
		UIContext uiContext = new UIContext(this);
		if (id != null)
		{
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("fdcReceiveBill.room.*");
			ReceivingBillInfo rev = ReceivingBillFactory.getRemoteInstance()
					.getReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(id)),
							sels);
			if (rev.getAmount().compareTo(FDCHelper.ZERO) < 0)
			{
				MsgBox.showInfo("红字不能开发票!");
				return;
			}
			uiContext.put("room", rev.getFdcReceiveBill().getRoom());
		}
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(InvoiceEditUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();
		this.refresh(null);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		checkObjectExists();
		List selectedIds = super.getSelectedIdValues();
		/*
		 * if (selectedIds.size() == 1) { KDTSelectBlock selectBlock =
		 * tblMain.getSelectManager().get(); int rowNumber =
		 * selectBlock.getTop(); IRow row = tblMain.getRow(rowNumber); Object
		 * accountViewName = row.getCell("payeeAccount").getValue(); Object
		 * settlementType = row.getCell("settleTypeName").getValue();
		 * 
		 * if(settlementType == null){ MsgBox.showWarning(this, "");
		 * this.abort(); } if(accountViewName == null){ MsgBox.showWarning(this,
		 * ""); this.abort(); } }
		 */
		((IReceivingBill) getBizInterface()).audit(FDCHelper
				.list2Set(selectedIds));
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		refresh(e);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", FDCHelper
						.list2Set(selectedIds), CompareType.INCLUDE));

		EntityViewInfo ev = new EntityViewInfo();
		ev.setFilter(filter);
		
		ev.getSelector().add("*");
		ev.getSelector().add("fdcReceiveBill.*");
		ev.getSelector().add("fdcReceiveBill.purchase.*");
		
		ReceivingBillCollection recColl = ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection(ev);
		for(int i=0;i<recColl.size();i++)
		{
			FDCReceiveBillInfo fdcRecInfo = recColl.get(i).getFdcReceiveBill();
			PurchaseInfo purInfo = fdcRecInfo.getPurchase();
			if(purInfo!=null)  {
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("*");
				sels.add("distillCommisionEntry.*");
				purInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(purInfo.getId()), sels);
				if(purInfo.getDistillCommisionEntry().size()>0)
				{
					CoreBaseCollection recComColl = new CoreBaseCollection();
					PurDistillCommisionEntryCollection purComColl = purInfo.getDistillCommisionEntry();
					//ReceiveDistillCommisionEntryCollection recComColl = new ReceiveDistillCommisionEntryCollection();
					for(int j=0;j<purComColl.size();j++)
					{
						PurDistillCommisionEntryInfo purComInfo = purComColl.get(j);
						ReceiveDistillCommisionEntryInfo recComInfo = new ReceiveDistillCommisionEntryInfo();
						recComInfo.setSharePercent(purComInfo.getSharePercent());
						recComInfo.setTakePercentage(purComInfo.getTakePercentage());
						recComInfo.setDistillCommisionLevel(purComInfo.getDistillCommisionLevel());
						recComInfo.setIsAchieveCommision(purComInfo.isIsAchieveCommision());
						recComInfo.setUser(purComInfo.getUser());
						recComInfo.setMarketUnit(purComInfo.getMarketUnit());
						recComInfo.setHead(fdcRecInfo);
						recComColl.add(recComInfo);
					}
					ReceiveDistillCommisionEntryFactory.getRemoteInstance().addnew(recComColl);
				}
			}
		}
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		checkSelected();
		checkObjectExists();
		List selectedIds = super.getSelectedIdValues();
		((IReceivingBill) getBizInterface()).antiAudit(FDCHelper
				.list2Set(selectedIds));
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		refresh(e);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", FDCHelper
						.list2Set(selectedIds), CompareType.INCLUDE));

		EntityViewInfo ev = new EntityViewInfo();
		ev.setFilter(filter);
		
		ev.getSelector().add("*");
		ev.getSelector().add("fdcReceiveBill.*");
		ev.getSelector().add("fdcReceiveBill.distillCommisionEntry.*");
		
		ReceivingBillCollection recColl = ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection(ev);
		for(int i=0;i<recColl.size();i++)
		{
			FDCReceiveBillInfo fdcRecInfo = recColl.get(i).getFdcReceiveBill();
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(
					new FilterItemInfo("head.id",fdcRecInfo.getId().toString()));
			ReceiveDistillCommisionEntryFactory.getRemoteInstance().delete(filter2);		
		}
	}

	public void actionRec_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		this.checkObjectExists();
		// 网络对象互斥
		// MutexUtils.setMutex(this, getSelectedKeyValue());
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		Iterator iter = blocks.iterator();
		Set idSet = new HashSet();
		while (iter.hasNext())
		{
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
			{
				IRow row = tblMain.getRow(rowIndex);
				int billStatus = ((BizEnumValueInfo) row.getCell("state")
						.getValue()).getInt();
				if (BillStatusEnum.AUDITED_VALUE == billStatus)
				{
					String id = row.getCell("id").getValue().toString();
					if (isCanCanceRec(id))
					{
						idSet.add(id);
					}
				}
			}
		}
		if (idSet.size() == 0)
		{
			MsgBox.showInfo("只有已审批状态的收款单才能收款!");
			return;
		}

		((IReceivingBill) getBizInterface()).rec(idSet);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		refresh(e);
		// MutexUtils.releaseMutex(this);
	}

	/**
	 * 在操作选中的列表项之前，检查对象是否存在。不存在则异常通知控制器。 Copy from ListUI
	 */
	protected void checkObjectExists() throws Exception
	{
		if (getSelectedKeyValue() == null)
		{
			return;
		}
		if (!getBizInterface().exists(
				new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue()))))
		{
			refreshList();
			throw new EASBizException(EASBizException.CHECKEXIST);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		this.checkObjectExists();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if(selectRows != null && selectRows.length > 1)
		{
			MsgBox.showInfo("请选择单行数据进行修改操作！");
			return;
		}
		String id = this.getSelectedKeyValue();
		if (id == null)
		{
			logger.warn("逻辑错误,仔细检查");
			return;
		}
		
		if(FDCUtils.isRunningWorkflow(id)){
			MsgBox.showInfo("这条单据正在工作流中，不能修改！");
			return;
		}		
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("fdcReceiveBill.sellProject.balanceEndDate");
		ReceivingBillInfo receivingBill = ReceivingBillFactory.getRemoteInstance().getReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(id)), sels);
		if (!BillStatusEnum.SAVE.equals(receivingBill.getBillStatus())&& !BillStatusEnum.SUBMIT.equals(receivingBill.getBillStatus()))
		{
			MsgBox.showInfo(this, "只有已提交或保存状态的收款单才可以修改！");
			return;
		}
		else
		{
			FDCReceiveBillInfo fdcRev = receivingBill.getFdcReceiveBill();
			if(fdcRev != null && getSystemType()!=null && getSystemType().equals(MoneySysTypeEnum.SalehouseSys)){ //只是针对售楼系统才需要
				SellProjectInfo sellProject = fdcRev.getSellProject();
				if(sellProject != null){
					SHEHelper.verifyBalance(receivingBill.getBizDate(), sellProject.getBalanceEndDate());
				}
			}
			
			String [] temp = SHEHelper.isReceiveBillCanRemoveOrEdit(id);
			if("false".equalsIgnoreCase(temp[0]))
			{
				if(temp[1] != null)
				{
					MsgBox.showInfo(temp[1]);
				}
				else
				{
					MsgBox.showInfo("该单据不能进行修改的操作！");
				}
				return;
			}
		}
		
		this.getUIContext().put("isEdit", Boolean.TRUE);
		
		
	

		UIContext uiContext = new UIContext(this);
		uiContext.put("isEdit", Boolean.TRUE);
		uiContext.put("ID",id);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(this.getEditUIName(),uiContext, null, "VIEW");
			uiWindow.show();
	
	}


	/**
	 * 生成凭证 TODO 其业务验证过程待整理
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		Iterator iter = blocks.iterator();
		while (iter.hasNext())
		{
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
			{
				IRow row = tblMain.getRow(rowIndex);
				String id = (String) row.getCell("id").getValue();
				
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("billStatus");
				sels.add("fdcReceiveBill.billTypeEnum");
				sels.add("fdcReceiveBill.adjustSrcBill.fdcReceiveBill.billTypeEnum");
				
				ReceivingBillInfo rev = ReceivingBillFactory.getRemoteInstance().getReceivingBillInfo(new ObjectUuidPK(id), sels);
				
				int billStatus = ((BizEnumValueInfo) row.getCell("state")
						.getValue()).getInt();
				if (BillStatusEnum.RECED_VALUE != billStatus)
				{
					MsgBox.showInfo(this, "只有已收款状态的收款单才能能生成凭证!");
					this.abort();
				}
				
				//这个可能还要到财务收款单序时簿去控制 TODO
				if(rev.getFdcReceiveBill() != null  &&  ReceiveBillTypeEnum.settlement.equals(rev.getFdcReceiveBill().getBillTypeEnum())){
					MsgBox.showInfo(this, "红冲单不能生成凭证!");
					this.abort();
				}
				
				FDCReceiveBillInfo fdcRev = rev.getFdcReceiveBill();
				if(fdcRev != null  &&  ReceiveBillTypeEnum.adjust.equals(fdcRev.getBillTypeEnum())){
					ReceivingBillInfo adjustSrcRev = fdcRev.getAdjustSrcBill();
					if(adjustSrcRev != null  &&  adjustSrcRev.getFdcReceiveBill() != null){
						if(ReceiveBillTypeEnum.settlement.equals(adjustSrcRev.getFdcReceiveBill().getBillTypeEnum())){
							MsgBox.showInfo(this, "红冲单生成的调整单不能生成凭证!");
							this.abort();
						}
					}
				}
				
			}
		}
		super.actionVoucher_actionPerformed(e);
		/*
		 * checkSelected(); // 网络对象互斥 if
		 * (!ArClientUtils.arIsRelatedAccount(companyInfo)) {
		 * MsgBox.showInfo(this, ArApBillUIUtil
		 * .getStrResource("isNotRelateGL")); this.abort(); }
		 *  // MutexUtils.setMutex(this, getSelectedKeyValue()); Set rows =
		 * FMClientHelper.getSelectedRows(tblMain); if (rows.size() == 1) { int
		 * rowNumber = FMClientHelper.getSelectedRow(tblMain); IRow row =
		 * tblMain.getRow(rowNumber); // 单据状态不等于已收款的单据不能生成凭证 int billStatus =
		 * ((BizEnumValueInfo) row.getCell("billStatus") .getValue()).getInt();
		 * if (billStatus != BillStatusEnum.RECED_VALUE) { MsgBox.showInfo(this,
		 * "未收款的单据不能生成凭证."); this.abort(); }
		 *  // 检查收款类型是否为销售回款和退销售回款的收款单，若为否，则提示: 销售回款和退销售回款的收款单不能生成凭证! //
		 * 检查付款类型是否为采购付款和退采购付款的付款单，若为否，则提示: 采购付款和退采购付款的付款单不能生成凭证!; int recType =
		 * Integer.parseInt(row.getCell("recTypeNumber")
		 * .getValue().toString()); if (recType ==
		 * RecPayBillTypeEnum.REC_AR_SELL_REC_VALUE || recType ==
		 * RecPayBillTypeEnum.REC_AR_REFUNDMENT_SELL_REC_VALUE ||
		 * arapHelper.checkIsDefaultBillType(new Integer(recType)) == false) {
		 * if (!ArClientUtils.isRecBillCreateVoucher(companyInfo.getId()
		 * .toString())) { String msg = EASResource.getString(resourcePath,
		 * "CanNotGenRecVou"); MsgBox.showError(this, msg); SysUtil.abort(); } } } //
		 * 设置生成凭证操作的全局变量 voucherFlag = 1; try { // 为本次操作添加日志项 //
		 * CoreBillBaseCollection list = getBillList(); // ReceivingBillInfo
		 * recBillInfo = (ReceivingBillInfo) list.get(0); // IObjectPK logPK =
		 * LogUtil.beginLog(null, null, recBillInfo // .getBOSType(), new
		 * ObjectUuidPK(recBillInfo.getId()), // recBillInfo.getNumber(),
		 * "cas_receivingBill_voucher");
		 * 
		 * super.actionVoucher_actionPerformed(e);
		 *  // 结束日志 // LogUtil.afterLog(null, logPK); } finally { // 重置
		 * voucherFlag = 0; MutexUtils.releaseMutex(this); }
		 */
	}

	private boolean isCanCanceRec(String billId) throws BOSException,
			OtherBillBizException
	{
		IVerificationBillentry iVerifyEntryBill = VerificationBillentryFactory
				.getRemoteInstance();
		VerificationBillentryCollection verifyEntryInfoCol = null;
		verifyEntryInfoCol = iVerifyEntryBill
				.getVerificationBillentryCollection("where billID = '" + billId
						+ "'");
		if (verifyEntryInfoCol.size() > 0)
		{
			throw new OtherBillBizException(OtherBillBizException.BILLVERIFIED);
		}
		return true;
	}

	protected boolean isFootVisible()
	{
		return true;
	}

	protected String[] getLocateNames()
	{
		String[] locateNames = new String[4];
		locateNames[0] = "number";
		locateNames[1] = "state";
		locateNames[2] = "room.number";
		locateNames[3] = "payerName";
		return locateNames;
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	protected boolean isNeedfetchInitData() throws Exception {
		return false;
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception
	{	checkSelected();
		this.checkObjectExists();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if(selectRows != null && selectRows.length > 1)
		{
			MsgBox.showInfo("请选择单行数据进行删除操作！");
			return;
		}
		String id = this.getSelectedKeyValue();
		if (id == null)
		{
			logger.warn("逻辑错误,仔细检查");
			return;
		}
		
		if(FDCUtils.isRunningWorkflow(id)){
			MsgBox.showInfo("这条单据正在工作流中，不能删除！");
			return;
		}
		
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("company.id");
		sels.add("number");
		sels.add("amount");
		sels.add("bizDate");
		sels.add("createtime");
		sels.add("billstatus");
		sels.add("fivouchered");
		sels.add("auditDate");
		sels.add("settlementNumber");
		sels.add("sourceType");
		sels.add("sourceSysType");
		sels.add("receivingBillType");
		sels.add("exchangeRate");
		sels.add("lastExhangeRate");		
		sels.add("recDate");
		sels.add("payerAccountBank");
		sels.add("payerBank");
		sels.add("payerID");
		sels.add("payerNumber");
		sels.add("payerName");
		sels.add("description");
		sels.add("actRecAmt");
		sels.add("currency.id");
		sels.add("currency.name");
		sels.add("oppAccount.id");
		sels.add("oppAccount.name");
		sels.add("project.id");
		sels.add("project.name");
	    sels.add("payeeAccount.id");
		sels.add("payeeAccount.name");
		sels.add("settlementType.id");
		sels.add("settlementType.name");
		sels.add("recBillType.id");
		sels.add("recBillType.name");
		sels.add("fdcReceiveBill.id");
		sels.add("fdcReceiveBill.payQuomodo");
		sels.add("fdcReceiveBill.moneySysType");
		sels.add("fdcReceiveBill.billTypeEnum");
		sels.add("fdcReceiveBill.gatheringObject");
		sels.add("fdcReceiveBill.gathering");
		sels.add("fdcReceiveBill.isBlankOut");		
		sels.add("fdcReceiveBill.sellProject.balanceEndDate");
		sels.add("fdcReceiveBill.cheque.id");
		sels.add("fdcReceiveBill.cheque.name");
		sels.add("fdcReceiveBill.cheque.amount");
		sels.add("fdcReceiveBill.room.id");
		sels.add("fdcReceiveBill.room.name");
		sels.add("fdcReceiveBill.room.number");
		sels.add("fdcReceiveBill.tenancyContract.id");
		sels.add("fdcReceiveBill.tenancyContract.name");
		sels.add("fdcReceiveBill.tenancyContract.number");		
		sels.add("fdcReceiveBill.tenancyContract.tenancyRoomList.id");
		sels.add("fdcReceiveBill.tenancyContract.tenancyRoomList.name");
		sels.add("fdcReceiveBill.tenancyContract.tenAttachResourceList.id");
		sels.add("fdcReceiveBill.tenancyContract.oldTenancyBill.id");
		sels.add("fdcReceiveBill.purchase.contractTotalAmount");
		sels.add("fdcReceiveBill.purchase.purchaseState");
		sels.add("fdcReceiveBill.purchase.areaCompensateAmount");
		sels.add("fdcReceiveBill.purchase.prePurchaseAmount");
		sels.add("fdcReceiveBill.purchase.purchaseAmount");
		sels.add("fdcReceiveBill.purchase.loanAmount");
		sels.add("fdcReceiveBill.customerEntrys.id");
		sels.add("fdcReceiveBill.customerEntrys.number");
		sels.add("fdcReceiveBill.customerEntrys.name");
		sels.add("fdcReceiveBill.customerEntrys.head");
		sels.add("fdcReceiveBill.customerEntrys.customer");
		sels.add("fdcReceiveBill.customerEntrys.customer.id");
		sels.add("fdcReceiveBill.purchase.room.id");
		sels.add("fdcReceiveBill.purchase.room.number");
		sels.add("fdcReceiveBill.purchase.room.sellState");
		sels.add("fdcReceiveBill.purchase.payListEntry.id");
		sels.add("fdcReceiveBill.purchase.payListEntry.name");
		sels.add("fdcReceiveBill.purchase.payType.id");
		sels.add("fdcReceiveBill.purchase.payType.name");
		sels.add("fdcReceiveBill.purchase.elsePayListEntry.id");
		sels.add("fdcReceiveBill.purchase.elsePayListEntry.name");
		sels.add("fdcReceiveBill.purchase.elsePayListEntry.moneyDefine.id");
		sels.add("fdcReceiveBill.purchase.elsePayListEntry.moneyDefine.name");
		sels.add("fdcReceiveBill.purchase.elsePayListEntry.moneyDefine.revBillType.id");
		sels.add("fdcReceiveBill.purchase.elsePayListEntry.moneyDefine.revBillType.name");
		sels.add("fdcReceiveBill.purchase.elsePayListEntry.currency.id");
		sels.add("fdcReceiveBill.purchase.elsePayListEntry.currency.name");
		sels.add("fdcReceiveBillEntry.moneyDefine.id");
		sels.add("fdcReceiveBillEntry.moneyDefine.name");
		sels.add("fdcReceiveBillEntry.moneyDefine.moneyType");
		sels.add("fdcReceiveBillEntry.moneyDefine.sysType");
		sels.add("fdcReceiveBillEntry.moneyDefine.revBillType.id");
		sels.add("fdcReceiveBillEntry.moneyDefine.revBillType.name");
		sels.add("fdcReceiveBillEntry.id");
		sels.add("fdcReceiveBillEntry.amount");
		sels.add("fdcReceiveBillEntry.counteractAmount");
		sels.add("fdcReceiveBillEntry.FCounteractId");
		sels.add("fdcReceiveBillEntry.payListId");
		sels.add("fdcReceiveBillEntry.canCounteractAmount");
		sels.add("fdcReceiveBillEntry.seq");
		ReceivingBillInfo receivingBill = ReceivingBillFactory.getRemoteInstance().getReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(id)),sels);
		Date bizDate = receivingBill.getBizDate();
		FDCReceiveBillInfo fdcRev = receivingBill.getFdcReceiveBill();
		if(fdcRev != null && getSystemType()!=null && getSystemType().equals(MoneySysTypeEnum.SalehouseSys)){ //只是针对售楼系统才需要
			SellProjectInfo sellProject = fdcRev.getSellProject();
			if(sellProject != null){
				SHEHelper.verifyBalance(bizDate, sellProject.getBalanceEndDate());
			}
		}
		
		FDCReceiveBillEntryCollection entryColl = receivingBill.getFdcReceiveBillEntry();
		if(entryColl != null)
		{
			FDCReceiveBillEntryCollection coll = new FDCReceiveBillEntryCollection();
			for(int i = 0; i < entryColl.size(); i ++)
			{
				FDCReceiveBillEntryInfo info = entryColl.get(i);
				info.setAmount(FDCHelper.ZERO);
				coll.add(info);
			}
			receivingBill.getFdcReceiveBillEntry().clear();
			receivingBill.getFdcReceiveBillEntry().addCollection(coll);
		}
		
		
		if (!BillStatusEnum.SAVE.equals(receivingBill.getBillStatus())&& !BillStatusEnum.SUBMIT.equals(receivingBill.getBillStatus()))
		{
			MsgBox.showInfo(this, "只有已提交或保存状态的收款单才可以删除！");
			return;
		}
		else
		{
			String [] temp = SHEHelper.isReceiveBillCanRemoveOrEdit(id);
			if("false".equalsIgnoreCase(temp[0]))
			{
				if(temp[1] != null)
				{
					MsgBox.showInfo(temp[1]);
				}
				else
				{
					MsgBox.showInfo("该单据不能进行删除的操作！");
				}
				return;
			}
		}
		
		//可以模仿修改的操作来进行反写，只是把这个金额置为0再删除，则可以实现
		    checkSelected();
	        if (confirmRemove())	        {
	        	FDCReceiveBillFactory.getRemoteInstance().submitByCasRev(receivingBill);
	        	
	        	//删除收款单时也要删掉房地产收款单
	        	String fdcRevId = null;
	        	if(fdcRev != null){
	        		fdcRevId = fdcRev.getId().toString();
	        	}
	        	
	        	//prepareRemove(null).callHandler();
	            Remove();
	            
	            if(fdcRevId != null){
	            	FDCReceiveBillFactory.getRemoteInstance().delete(new ObjectUuidPK(fdcRev.getId().toString()));
	            }
	            
	            this.refresh(null);
	        }
	}
	/**
	 * 多房交款
	 */
	public void actionBatchRec_actionPerformed(ActionEvent e) throws Exception
	{
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
		create(BatchReceiveBillEditUI.class.getName(), new UIContext(this), null,OprtState.ADDNEW);
		uiWindow.show();
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("fdcReceiveBillEntry.account.*");

		sels.add("payeeAccount.*");
		
		return sels;
	}
	
	public void refreshListForOrder() throws Exception
	{
		super.refreshListForOrder();
		
		this.refresh(null);
	}
	protected String[] getNotOrderColumns()
	{
		return new String[] {"fdcReceiveBillEntry.amount","moneyDefine.name","settlementType.name","billType"};
	}
	
	
	public SelectorItemCollection getBOTPSelectors()
	{
		
		SelectorItemCollection sels = super.getBOTPSelectors();
		
		sels.add("fdcReceiveBillEntry.account.*");

		sels.add("payeeAccount.*");
		sels.add("fdcReceiveBillEntry.oppSubject.*");
		sels.add("fdcReceiveBillEntry.settlementType.*");
		sels.add("fdcReceiveBillEntry.revAccountBank.*");
		sels.add("fdcReceiveBillEntry.revBank.*");
		sels.add("fdcReceiveBillEntry.moneyDefine.*");
		sels.add("fdcReceiveBillEntry.*");
		sels.add("fdcReceiveBill.sellProject.*");
		sels.add("fdcReceiveBill.*");
		sels.add("fdcReceiveBill.room.*");
		sels.add("fdcReceiveBill.invoice.*");
		sels.add("fdcReceiveBill.cheque.*");
		sels.add("fdcReceiveBill.asstActType.*");
		sels.add("*");
		
		return sels;
		
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
	{
		this.getUIContext().put("isEdit", Boolean.FALSE);
		super.actionAddNew_actionPerformed(e);
	}
	
	public void actionAdjust_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		this.checkObjectExists();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if(selectRows != null && selectRows.length > 1)
		{
			MsgBox.showInfo("请选择单行数据进行调整操作！");
			return;
		}
		String id = this.getSelectedKeyValue();
		if (id == null)
		{
			logger.warn("逻辑错误,仔细检查");
			return;
		}
		
		BalanceAdjustFacadeFactory.getRemoteInstance().blankOutRevBill(id, true, true);
		MsgBox.showInfo(this, "调整成功！");
	}
	
	/**
	 * 开换收据 add by jiyu_guan
	 */
	public void actionReceipt_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		String stateName = ((BizEnumValueDTO) row.getCell("state").getValue())
				.getName();
		if (!stateName.equals(BillStatusEnum.AUDITED.getName())
				&& !stateName.equals(BillStatusEnum.RECED.getName())) {
			MsgBox.showWarning(this, "请先审批该收款单!");
			return;
		}
		if (row.getCell("receiptState").getValue() != null
				&& ((BizEnumValueDTO) row.getCell("receiptState").getValue())
						.getName().equals(ReceiptStatusEnum.HasInvoice.getName())) {
			MsgBox.showWarning(this, "已换发票不可再开换收据!");
			return;
		}
		String billType = ((BizEnumValueDTO) row.getCell("billType").getValue())
				.getName();
		if (!billType.equals(ReceiveBillTypeEnum.transfer.getName())
				&& !billType.equals(ReceiveBillTypeEnum.gathering.getName())) {
			MsgBox.showWarning(this, "只有收款或转款类型的收款单才可以开换收据!");
			return;
		}

		Map ctx = new UIContext(this);
		ctx.put("recID", row.getCell("id").getValue());
		// ctx.put("rcpNum", row.getCell("receiptNumber").getValue());
		ctx.put(UIContext.OWNER, this);

		IUIWindow uiWindow = null;
		// 弹出模式
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				MakeOutReceiptUI.class.getName(), ctx, null, OprtState.ADDNEW);

		// 开始展现UI
		uiWindow.show();
		refresh(null);
	}

	/**
	 * 回收收据 add by jiyu_guan
	 */
	public void actionRetakeReceipt_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row.getCell("receiptState").getValue() != null
				&& ((BizEnumValueDTO) row.getCell("receiptState").getValue())
						.getName().equals(ReceiptStatusEnum.HasMake.getName())) {
			if (MsgBox.showConfirm2(this, "是否回收该收款单的收据") == 0) {
				if (FDCStringHelper.isEmpty(row.getCell("receiptNumber")
						.getValue())) {
					MsgBox.showWarning(this, "该收款单暂无收据!");
				} else {
					String pk = (String) row.getCell("id").getValue();
					String fdcPK = (String) row.getCell("fdcId").getValue();
					String receiptNum = (String) row.getCell("receiptNumber").getValue();
					IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory
							.getRemoteInstance();
					facade.retackReceipt(pk, fdcPK, receiptNum, null,null);
					MsgBox.showWarning(this, "回收收据成功!");
					refresh(null);
				}
			}
		} else {
			MsgBox.showWarning(this, "该收款单暂无收据!");
		}
	}
	
	/**
	 * 取消收款
	 * @author laiquan_luo
	 */
	public void actionCancelRec_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		this.checkObjectExists();
		// 网络对象互斥
		// MutexUtils.setMutex(this, getSelectedKeyValue());
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		Iterator iter = blocks.iterator();
		Set idSet = new HashSet();
		while (iter.hasNext())
		{
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
			{
				IRow row = tblMain.getRow(rowIndex);
				int billStatus = ((BizEnumValueInfo) row.getCell("state")
						.getValue()).getInt();
				if (BillStatusEnum.RECED_VALUE == billStatus)
				{
					String id = row.getCell("id").getValue().toString();
					if (isCanCanceRec(id))
					{
						idSet.add(id);
					}
				}
			}
		}
		if (idSet.size() == 0)
		{
			MsgBox.showInfo("只有已收款状态的收款单才能取消收款!");
			return;
		}

		((IReceivingBill) getBizInterface()).cancelRec(idSet);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		refresh(e);
		// MutexUtils.releaseMutex(this);
	}
	
	
	
	
	
}