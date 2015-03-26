/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BatchPurchaseUI extends AbstractBatchPurchaseUI
{
	private static final Logger logger = CoreUIObject.getLogger(BatchPurchaseUI.class);
	
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	
	RoomDisplaySetting setting = new RoomDisplaySetting();
	/**
	 * output class constructor
	 */
	public BatchPurchaseUI() throws Exception
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
	
	public static void showUI(IUIObject ui) throws EASBizException, BOSException
	{
		UIContext uiContext = new UIContext(ui);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(BatchPurchaseUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
	}
	
	public void onLoad() throws Exception {
		this.f7Currency.setRequired(true);
		this.dptBookDate.setRequired(true);
		this.dptValidDate.setRequired(true);
		
		this.btnSubmit.setVisible(false);
		this.btnAscertain.setEnabled(true);
		
		this.btnSubmit.setEnabled(true);
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
		this.f7SellProject.setRequired(true);
		this.f7SellProject.setEntityViewInfo(CommerceHelper.getPermitProjectView());
//		this.f7SellOrder.setRequired(true);
		CompanyOrgUnitInfo currentCompany = SysContext.getSysContext()
		.getCurrentFIUnit();
		CurrencyInfo baseCurrency = CurrencyFactory.getRemoteInstance()
		.getCurrencyInfo(
				new ObjectUuidPK(BOSUuid.read(currentCompany
						.getBaseCurrency().getId().toString())));
		this.f7Currency.setValue(baseCurrency);
		//this.f7Currency.setEnabled(false);
		
		this.tabCusPurchase.checkParsed();
		this.tabCusPurchase.getSelectManager().setSelectMode(
				KDTSelectManager.CELL_SELECT);
		this.tabCusPurchase.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		//按销售顾问过滤
		this.customerFilter(userInfo);
		
		//可能存在编码规则   
		boolean existNumberRule = CommerceHelper.isExistNumberRule(new SincerityPurchaseInfo());
		if(!existNumberRule)	{
			//-----------修复BUG302902。业务编码为必录，修改底色-----
			this.tabCusPurchase.getColumn("contNumber").getStyleAttributes().setBackground(
					FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		}else{
			this.tabCusPurchase.getColumn("contNumber").getStyleAttributes().setLocked(true);
		}
		
		//--------------------------
		
		//---------修复BUG311032----
		//this.tabCusPurchase.getColumn("sellorderNum").getStyleAttributes().setLocked(true);
		//-----------------
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tabCusPurchase.getColumn("contSincerityAmount").setEditor(
				numberEditor);
		this.tabCusPurchase.getColumn("contSincerityAmount").getStyleAttributes().setLocked(false);
		this.tabCusPurchase.getColumn("contSincerityAmount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		this.tabCusPurchase.getColumn("contSincerityAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		this.tabCusPurchase.getColumn("contSincerityAmount").getStyleAttributes().setBackground(
				FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		KDBizPromptBox roomModelBox = new KDBizPromptBox();
		roomModelBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery");
		roomModelBox.setRequired(true);
		roomModelBox.setDisplayFormat("$name$");
		roomModelBox.setEditFormat("$number$");
		roomModelBox.setCommitFormat("$number$");
		KDTDefaultCellEditor roomModelEditor = new KDTDefaultCellEditor(roomModelBox);
		this.tabCusPurchase.getColumn("contRoommodelType").setEditor(roomModelEditor);
		this.tabCusPurchase.getColumn("contDescription").getStyleAttributes().setLocked(false);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tabCusPurchase.getColumn("contDescription").setEditor(txtEditor);
//		KDTextField sellorderNum = new KDTextField();
//		KDTDefaultCellEditor pinnerEditor = new KDTDefaultCellEditor(sellorderNum);
//		this.tabCusPurchase.getColumn("sellorderNum").setEditor(pinnerEditor);
		EntityViewInfo view = new EntityViewInfo();
		String orgUnitID = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("CU.id", orgUnitID,CompareType.EQUALS));
		this.f7SellOrder.setEntityViewInfo(view);
		this.f7Salesman.setValue(SysContext.getSysContext().getCurrentUserInfo());
		super.onLoad();	
	}
	private void customerFilter(UserInfo info) throws EASBizException, BOSException
	{
		KDBizPromptBox customerBox = new KDBizPromptBox();
		customerBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
		customerBox.setDisplayFormat("$name$");
		customerBox.setEditFormat("$number$");
		customerBox.setCommitFormat("$number$");
		customerBox.setEntityViewInfo(CommerceHelper.getPermitCustomerView(null,info));
		ICellEditor customerEditor = new KDTDefaultCellEditor(customerBox);
		this.tabCusPurchase.getColumn("contCustomer").setEditor(customerEditor);
		this.tabCusPurchase.getColumn("contCustomer").getStyleAttributes().setBackground(
				FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
	}
	
	protected void f7Salesman_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Salesman_dataChanged(e);
		UserInfo info = (UserInfo)this.f7Salesman.getValue();
		this.customerFilter(info);
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		HashMap customerMap =new HashMap();
		SincerityPurchaseCollection coll=new SincerityPurchaseCollection();
		verifyInput(e);
		boolean existNumberRule = CommerceHelper.isExistNumberRule(new SincerityPurchaseInfo());
		for(int i=0;i<this.tabCusPurchase.getRowCount();i++)
		{
			SincerityPurchaseInfo sinPurinfo = new SincerityPurchaseInfo();
			sinPurinfo.setSellProject((SellProjectInfo)this.f7SellProject.getValue());
			sinPurinfo.setSellOrder((SellOrderInfo) this.f7SellOrder.getValue());
			sinPurinfo.setBookDate((Date) this.dptBookDate.getValue());
			sinPurinfo.setValidDate((Date) this.dptValidDate.getValue());
			sinPurinfo.setSalesman((UserInfo) this.f7Salesman.getValue());
			sinPurinfo.setCurrency((CurrencyInfo) this.f7Currency.getValue());
			sinPurinfo.setSincerityState(SincerityPurchaseStateEnum.ArrangeNum);
			
			IRow row = this.tabCusPurchase.getRow(i);
			
			if(!existNumberRule) {
				String contNumber = (String)row.getCell("contNumber").getValue();
				if(contNumber==null || contNumber.equals(""))		{
					MsgBox.showInfo("业务编码不能为空!!");
					return;
				}
			}
			FDCCustomerInfo info = (FDCCustomerInfo)row.getCell("contCustomer").getValue();
			if(info==null)
			{
				MsgBox.showInfo("客户不能为空!!");
				return;
			}
			if(info!=null && info.getId()!=null && info.getId().toString()!=null){
				String customerID= info.getId().toString();
				if(customerMap!=null && customerMap.containsKey(customerID)){
					MsgBox.showInfo("客户不能重复!!");
					return;
				}else{
					customerMap.put(info.getId().toString(), info);
				}
			}
			if(info.getCertificateName()==null || info.getCertificateNumber()==null){
				MsgBox.showInfo("客户证件不能为空!!");
				CustomerCardUI.addTheCustomerAuthority(info, this);
				return;
			}
			
			if(!existNumberRule)
			{
				sinPurinfo.setNumber((String)row.getCell("contNumber").getValue());
			    sinPurinfo.setName((String)row.getCell("contNumber").getValue());
			}				
			else
			{
				sinPurinfo.setNumber(CommerceHelper.getNumberByRule(new SincerityPurchaseInfo()));
			    sinPurinfo.setName(CommerceHelper.getNumberByRule(new SincerityPurchaseInfo()));
			}			
			
			// by tim_gao 预约排号 客户结构变化 2011-06-15
			sinPurinfo.getCustomer().get(0).setCustomer((SHECustomerInfo)row.getCell("contCustomer").getValue());
//			sinPurinfo.setCustomer((FDCCustomerInfo)row.getCell("contCustomer").getValue());
			BigDecimal contSincerityAmount;
//			int sellorderNum;
//			String sellorder = row.getCell("sellorderNum").getValue().toString();
//			if(sellorder==null)
//			{
//				sellorderNum = 0;
//			}else
//			{
//				sellorderNum = Integer.parseInt(sellorder);
//			}
//			this.vetrySellOrder(sellorderNum);
//			sinPurinfo.setSellOrderArrangeNum(sellorderNum);
			BigDecimal sincerityAmount = (BigDecimal)row.getCell("contSincerityAmount").getValue();
			if(sincerityAmount==null || sincerityAmount.compareTo(new BigDecimal(0))==0)
			{
				MsgBox.showInfo("诚意金不能为空 !");
				return;
				//contSincerityAmount =  FDCHelper.ZERO;
				
			}else
			{
				contSincerityAmount = sincerityAmount;
			}
			sinPurinfo.setSincerityAmount(contSincerityAmount);
			sinPurinfo.setRoomModelType((RoomModelTypeInfo)row.getCell("contRoommodelType").getValue());
			sinPurinfo.setDescription((String)row.getCell("contDescription").getValue());
			
			sinPurinfo.setBookDate(new Date());
			
			sinPurinfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
			sinPurinfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
			this.setOprtState("ADDNEW");
			//this.getUIContext().put("sinPurinfo",sinPurinfo);
			
			coll.add(sinPurinfo);
			this.verify(e);
			
		}
		SincerityPurchaseFactory.getRemoteInstance().submitSincer(coll);
//		for(int i=0; i<coll.size(); i++){
//			SincerityPurchaseInfo sinPurinfo = coll.get(i);
//			SincerityPurchaseFactory.getRemoteInstance().submit(sinPurinfo);
//		}
//		
		this.showMessageForStatus();
		this.showSubmitSuccess();
		super.actionExitCurrent_actionPerformed(e);
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		if(this.f7SellProject.isRequired() && this.f7SellProject.getValue()==null)
		{
			MsgBox.showInfo("项目必须录入");
			abort();
		}
//		if(this.f7SellOrder.isRequired() && this.f7SellOrder.getValue()==null)
//		{
//			MsgBox.showInfo("盘次必须录入");
//			abort();
//		}
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
	
	protected void vetrySellOrder(int sellOrderNum) throws BOSException
	{
		SellOrderInfo sellOrder = (SellOrderInfo)this.f7SellOrder.getValue();
		if(sellOrder!=null)
		{
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("sellOrder.id", sellOrder.getId()
							.toString()));
			SincerityPurchaseCollection sincerityPurchases = SincerityPurchaseFactory
			.getRemoteInstance().getSincerityPurchaseCollection(
					view);
			for(int i=0;i<sincerityPurchases.size();i++)
			{
				int num = sincerityPurchases.get(i).getSellOrderArrangeNum();
				if(num==sellOrderNum)
				{
					MsgBox.showInfo("排号"+sellOrderNum+"重复，请重新输入");
					this.abort();
				}
			}
		}
	}
	protected void btnAddPurchase_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		super.btnAddPurchase_actionPerformed(e);
		int count = this.tabCusPurchase.getRowCount();
		IRow row = this.tabCusPurchase.getRow(count-1);
		SellOrderInfo sellOrder = (SellOrderInfo)this.f7SellOrder.getValue();
		if(count==0 && sellOrder == null)
		{
			IRow row1 = this.tabCusPurchase.addRow();
			row1.getCell("sellorderNum").setValue(new Integer(1));
		}
		if(count==0 && sellOrder != null)
		{
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
				value = info.getSellOrderArrangeNum();
				if(value>max)
				{
					max=value;
				}
			}
			IRow row1 = this.tabCusPurchase.addRow();
			row1.getCell("sellorderNum").setValue(new Integer(max+1));
			//row1.getCell("sellorderNum").getStyleAttributes().setLocked(true);
		}
		else if(count >0)
		{
			row = this.tabCusPurchase.getRow(count-1);
//			if(row.getCell("sellorderNum").getValue()==null)
//			{
//				MsgBox.showInfo("盘次盘号不能为空！");
//				this.abort();
//			}
//			String sellorderString = row.getCell("sellorderNum").getValue().toString();
//			if(!BatchPurchaseUI.isNumeric(sellorderString))
//			{
//				MsgBox.showInfo("盘次盘号必须为数字！");
//				this.abort();
//			}
			//int sellorder = Integer.parseInt(sellorderString);
			IRow row2 = this.tabCusPurchase.addRow();
			if(row.getCell("contSincerityAmount").getValue()==null)
			{
				row2.getCell("contSincerityAmount").setValue(new BigDecimal(0));
			}
			else if(((BigDecimal)row.getCell("contSincerityAmount").getValue()).compareTo(new BigDecimal(0))==0)
			{
				row2.getCell("contSincerityAmount").setValue(row.getCell("contSincerityAmount").getValue());
			}
			//row2.getCell("sellorderNum").setValue(new Integer(sellorder+1));
			//row2.getCell("sellorderNum").getStyleAttributes().setLocked(true);
		}		
	}
	
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		boolean bool = pattern.matcher(str).matches();
		return bool;   
	}
	
	protected void btnDeletePurchase_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		super.btnDeletePurchase_actionPerformed(e);
		int activeRowIndex = this.tabCusPurchase.getSelectManager()
		.getActiveRowIndex();
		this.tabCusPurchase.removeRow(activeRowIndex);
	}
	protected void f7SellProject_dataChanged(DataChangeEvent e)
			throws Exception {
		super.f7SellProject_dataChanged(e);
		SellProjectInfo projectInfo = (SellProjectInfo)this.f7SellProject.getValue();
		EntityViewInfo view = new EntityViewInfo();
		String orgUnitID = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("CU.id", orgUnitID,CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("project.id", projectInfo.getId().toString(),CompareType.EQUALS));
		this.f7SellOrder.setEntityViewInfo(view);
		
		Map functionSetMap = setting.getFunctionSetMap();
		if(functionSetMap.get(projectInfo.getId().toString())!=null)	{
			FunctionSetting funcSet =(FunctionSetting)functionSetMap.get(projectInfo.getId().toString());
//			if(funcSet.getIsSincerSellOrder()!=null && funcSet.getIsSincerSellOrder().booleanValue())			{
//				this.f7SellOrder.setRequired(false);
//			}else	{
//				this.f7SellOrder.setRequired(true);
//			}
		}
	}
	protected void f7SellOrder_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
	{
		//super.f7SellOrder_dataChanged(e);
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
//					value++;
//					}
				}
				for(int i=0;i<this.tabCusPurchase.getRowCount();i++)
				{
					IRow row = this.tabCusPurchase.getRow(i);
					//row.getCell("sellorderNum").setValue(new Integer(max+1));
					max++;
					//row.getCell("sellorderNum").getStyleAttributes().setLocked(true);
				}
			}
		}
	}
	
	protected IObjectValue createNewData() {
		return null;
	}
	protected void inOnload() throws Exception {
		// super.inOnload();
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
	
}