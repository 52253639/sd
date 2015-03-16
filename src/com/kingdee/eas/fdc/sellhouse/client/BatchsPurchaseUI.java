/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.log4j.Logger;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BatchsPurchaseUI extends AbstractBatchPurchaseUI
{
	private static final Logger logger = CoreUIObject.getLogger(BatchPurchaseUI.class);
	
	/**
	 * output class constructor
	 */
	public BatchsPurchaseUI() throws Exception
	{
		super();
	}
	
	public void loadFields() {
		super.loadFields();
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}
	
	public void onLoad() throws Exception {
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemSave.setVisible(false);
		this.rMenuItemSubmit.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.rMenuItemSubmitAndPrint.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnSave.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnLast.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.f7SellOrder.setRequired(true);
		
		
		this.tabCusPurchase.checkParsed();
		this.tabCusPurchase.getSelectManager().setSelectMode(
				KDTSelectManager.CELL_SELECT);
		this.tabCusPurchase.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		KDBizPromptBox customerBox = new KDBizPromptBox();
		customerBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
		customerBox.setRequired(true);
		KDTDefaultCellEditor customerEditor = new KDTDefaultCellEditor(customerBox);
		this.tabCusPurchase.getColumn("contCustomer").setEditor(customerEditor);
		this.tabCusPurchase.getColumn("contCustomer").getStyleAttributes().setBackground(
				FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tabCusPurchase.getColumn("contSincerityAmount").getStyleAttributes().setLocked(false);
		this.tabCusPurchase.getColumn("contSincerityAmount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		this.tabCusPurchase.getColumn("contSincerityAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		KDBizPromptBox roomModelBox = new KDBizPromptBox();
		roomModelBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery");
		roomModelBox.setRequired(true);
		KDTDefaultCellEditor roomModelEditor = new KDTDefaultCellEditor(roomModelBox);
		this.tabCusPurchase.getColumn("contRoommodelType").setEditor(roomModelEditor);
		this.tabCusPurchase.getColumn("contRoommodelType").getStyleAttributes().setBackground(
				FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tabCusPurchase.getColumn("contDescription").getStyleAttributes().setLocked(false);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tabCusPurchase.getColumn("contDescription").setEditor(txtEditor);
		KDTextField sellorderNum = new KDTextField();
		KDTDefaultCellEditor pinnerEditor = new KDTDefaultCellEditor(sellorderNum);
		//KDTDefaultCellEditor a = new 
		this.tabCusPurchase.getColumn("sellorderNum").setEditor(pinnerEditor);
		super.onLoad();
		handleCodingRule();
		
		
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		SincerityPurchaseInfo sinPurinfo = new SincerityPurchaseInfo();
		sinPurinfo.setSellOrder((SellOrderInfo) this.f7SellOrder.getValue());
		sinPurinfo.setBookDate((Date) this.dptBookDate.getValue());
		sinPurinfo.setValidDate((Date) this.dptValidDate.getValue());
		sinPurinfo.setSalesman((UserInfo) this.f7Salesman.getValue());
		sinPurinfo.setCurrency((CurrencyInfo) this.f7Currency.getValue());
		sinPurinfo.setSincerityState(SincerityPurchaseStateEnum.ArrangeNum);
		SincerityPurchaseCollection coll;
		verifyInput(e);
		for(int i=0;i<this.tabCusPurchase.getRowCount();i++)
		{
			IRow row = this.tabCusPurchase.getRow(i);
			String contNumber = (String)row.getCell("contNumber").getValue();
			if(contNumber==null || contNumber.equals(""))
			{
			MsgBox.showInfo("行业编码不能为空!!");
			abort();
			}
			sinPurinfo.setNumber((String)row.getCell("contNumber").getValue());
			// by tim_gao 预约排号，客户字段分录结构变化 2011-06-15
			sinPurinfo.getCustomer().get(0).setCustomer((SHECustomerInfo)row.getCell("contCustomer").getValue());
//			sinPurinfo.setCustomer((FDCCustomerInfo)row.getCell("contCustomer").getValue());
			BigDecimal contSincerityAmount;
			int sellorderNum;
			Integer sellorder = (Integer)row.getCell("sellorderNum").getValue();
			if(sellorder==null)
			{
				sellorderNum = 0;
			}else
			{
				sellorderNum = sellorder.intValue();
			}		
			sinPurinfo.setSellOrderArrangeNum(sellorderNum);
			String sincerityAmount = (String)row.getCell("contSincerityAmount").getValue();
			if(sincerityAmount==null || sincerityAmount.equals(""))
			{
				contSincerityAmount =  FDCHelper.ZERO;
				
			}else
			{
				contSincerityAmount = new BigDecimal((String)row.getCell("contSincerityAmount").getValue());
			}
			sinPurinfo.setSincerityAmount(contSincerityAmount);
			sinPurinfo.setRoomModelType((RoomModelTypeInfo)row.getCell("contRoommodelType").getValue());
			sinPurinfo.setDescription((String)row.getCell("contDescription").getValue());
			this.setOprtState("ADDNEW");
			//this.getUIContext().put("sinPurinfo",sinPurinfo);
			coll=new SincerityPurchaseCollection();
			coll.add(sinPurinfo);
			this.verify(e);
			SincerityPurchaseFactory.getRemoteInstance().submit(sinPurinfo);
		}
		
		this.showMessageForStatus();
		this.showSubmitSuccess();
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		if(this.f7SellOrder.getValue()==null)
		{
			MsgBox.showInfo("盘次不能为空!");
			abort();
		}
		if(this.f7Currency.getValue()==null)
		{
			MsgBox.showInfo("币别不能为空!");
			abort();
		}
		if (this.dptBookDate.getValue() == null) {
			MsgBox.showInfo("登记日期必须录入！");
			abort();
		}
		if(this.dptValidDate.getValue()==null)
		{
			MsgBox.showInfo("有效日期不能为空!");
			abort();
		}
		if(this.tabCusPurchase.getRowCount()==0)
		{
		MsgBox.showInfo("请增加客户信息!");
		abort();
		}
		super.verifyInput(e);
	}
	
	protected void handleCodingRule() throws BOSException, CodingRuleException,
	EASBizException {
		editData = new SincerityPurchaseInfo();
		//SincerityPurchaseInfo info = (SincerityPurchaseInfo)this.editData;
		String currentOrgId = FDCClientHelper.getCurrentOrgId();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
		.getRemoteInstance();
		if(iCodingRuleManager.isExist(editData,currentOrgId))
		{
			
		}else
		{
			//CodingRuleInfo info = new CodingRuleInfo();
//			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData,
//					currentOrgId);
			//iCodingRuleManager.addNew(info);
			
			this.getNumberByCodingRule(editData,currentOrgId);
			
		}
//		if (STATUS_ADDNEW.equals(this.oprtState)
//				&& iCodingRuleManager.isExist(editData, currentOrgId)) {
//			// EditUI提供了方法，但没有调用，在onload后调用，以覆盖抽象类loadfields里面的调用（该调用没有处理断号选择）
//			
//			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData,
//					currentOrgId);
//			if (isAddView) {
//				getNumberByCodingRule(editData, currentOrgId);
//			} else {
//				String number = null;
//				
//				if (iCodingRuleManager.isUseIntermitNumber(editData,
//						currentOrgId)) { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
//					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
//						// 启用了断号支持功能,同时启用了用户选择断号功能
//						// KDBizPromptBox pb = new KDBizPromptBox();
//						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
//								editData, currentOrgId, null, null);
//						// pb.setSelector(intermilNOF7);
//						// 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
//						Object object = null;
//						if (iCodingRuleManager
//								.isDHExist(editData, currentOrgId)) {
//							intermilNOF7.show();
//							object = intermilNOF7.getData();
//						}
//						if (object != null) {
//							number = object.toString();
//						}
//					}
//				}
//				KDTextField textField = new KDTextField();
//				KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
//				txtEditor.setValue(number);
//				this.tabCusPurchase.getColumn("contNumber").setEditor(txtEditor);
//			}
//			
//			setNumberTextEnabled();
//		}
	}
	
	protected void setNumberTextEnabled() {
		
		if (this.tabCusPurchase.getColumn("contNumber") != null) {
			CostCenterOrgUnitInfo currentCostUnit = SysContext.getSysContext()
			.getCurrentCostUnit();
			
			if (currentCostUnit != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(
						editData, currentCostUnit.getId().toString());
//				txtNumber.setEnabled(isAllowModify);
//				txtNumber.setEditable(isAllowModify);
//				txtNumber.setRequired(isAllowModify);
			}
		}
	}
	/**
	 * getNumberByCodingRule只提供了获取编码的功能，没有提供设置到控件的功能，实现此方法将根据编码规则的"是否新增显示"属性设置编码到控件
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		KDTextField textField = new KDTextField();
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		txtEditor.setValue(number);
		this.tabCusPurchase.getColumn("contNumber").setEditor(txtEditor);
		
	}
	
	protected void btnAddPurchase_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		super.btnAddPurchase_actionPerformed(e);
		int count = this.tabCusPurchase.getRowCount();
		IRow row = this.tabCusPurchase.getRow(count-1);
		if(count==0)
		{
			IRow row1 = this.tabCusPurchase.addRow();
			row1.getCell("sellorderNum").setValue(new Integer(1));
			row1.getCell("sellorderNum").getStyleAttributes().setLocked(true);
		}
		else
		{
			IRow row2 = this.tabCusPurchase.addRow();
			row = this.tabCusPurchase.getRow(count-1);
			Integer sellorder = (Integer)
			row.getCell("sellorderNum").getValue();
			row2.getCell("sellorderNum").setValue(new Integer(sellorder.intValue()+1));
			row2.getCell("sellorderNum").getStyleAttributes().setLocked(true);
		}		
	}
	
	protected void btnDeletePurchase_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		super.btnDeletePurchase_actionPerformed(e);
		int activeRowIndex = this.tabCusPurchase.getSelectManager()
		.getActiveRowIndex();
		this.tabCusPurchase.removeRow(activeRowIndex);
	}
	
	protected void f7SellOrder_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
	{
		super.f7SellOrder_dataChanged(e);
		if (!(e.getNewValue() instanceof SellOrderInfo)) {
			return;
		}
		SellOrderInfo sellOrder = (SellOrderInfo) e.getNewValue();
		if (sellOrder == null) {
			//this.txtRoom.setText(null);
			//this.txtRoom.setUserObject(null);
			//this.spiSellOrderNum.setValue(new Integer(0));
		} else {
			if (sellOrder.equals(e.getOldValue())) {
				return;
			} else {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);
				filter.getFilterItems().add(
						new FilterItemInfo("sellOrder.id", sellOrder.getId()
								.toString()));
				SincerityPurchaseCollection sincerityPurchases = SincerityPurchaseFactory
				.getRemoteInstance().getSincerityPurchaseCollection(
						view);
				int value = 0;
				int max =0;
				if(sincerityPurchases.size()>0)
				{
					max = sincerityPurchases.get(0).getSellOrderArrangeNum();
				}
				for (int i = 0; i < sincerityPurchases.size(); i++) {
					SincerityPurchaseInfo info = sincerityPurchases.get(i);
						//if (info.getSellOrder() != null&& info.getSellOrder().getId().toString().equals(sellOrder.getId().toString())) {
							value = info.getSellOrderArrangeNum();
							if(value>max)
							{
								max=value;
							}
//					} else {
//						value++;
//					}
				}
				for(int i=0;i<this.tabCusPurchase.getRowCount();i++)
				{
					IRow row = this.tabCusPurchase.getRow(i);
					row.getCell("sellorderNum").setValue(new Integer(max+1));
					max++;
					row.getCell("sellorderNum").getStyleAttributes().setLocked(true);
				}
			}
		}
	}
	
	protected IObjectValue createNewData() {
		return new SincerityPurchaseInfo();
	}
	protected void inOnload() throws Exception {
		// super.inOnload();
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
	
}