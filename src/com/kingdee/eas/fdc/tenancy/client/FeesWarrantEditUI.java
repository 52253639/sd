/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.spi.SPInfo;
import com.kingdee.bos.spi.SPManager;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.FeesWarrantEntrysCollection;
import com.kingdee.eas.fdc.tenancy.FeesWarrantEntrysInfo;
import com.kingdee.eas.fdc.tenancy.FeesWarrantFactory;
import com.kingdee.eas.fdc.tenancy.FeesWarrantInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.IDAPTrans;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class FeesWarrantEditUI extends AbstractFeesWarrantEditUI
{
	 	private IDAPTrans dapTrans;
		private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		public static String COL_TENANCY="tenancyBill";//F7
		public static String COL_TENANCYNAME="tenancyName";
		public static String COL_CUSTOMER="customername";//F7
		public static String COL_ROOM="room";//F7
		public static String COL_MONEYDEFINE="moneyDefine";//F7
		public static String COL_ACCOUNT="account";//F7
		public static String COL_OPPSUBJECT="oppSubject";//F7
		public static String COL_SATRTDATE="startDate";//F7
		public static final String DATE_FORMAT_STR = "yyyy-MM-dd";
		public static String COL_STARTDATE="startDate";//date
		public static String COL_ENDDATE="endDate";//date
		public static String COL_APPDATE="appDate";//date
		public static String COL_ACTREVDATE="actRevDate";//date
		public static String COL_APPAMOUNT="appAmount";//BIGD
		public static String COL_ACTREVAMOUNT="actRevAmount";//BIGD
	//	public static String COL_TENANCYNUBMER="tenancybillnumer";//文本 合同编号
		private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
		private SellProjectInfo sellProjectname =null;
		
	public void actionSetAccount_actionPerformed(ActionEvent e)
		throws Exception {
			super.actionSetAccount_actionPerformed(e);
			AccountViewInfo accountViewInfo=null;
			if(kdtFeesWarrantEntry.getRowCount()>1&&kdtFeesWarrantEntry.getRow(0).getCell("account").getValue()!=null) 
				 accountViewInfo=(AccountViewInfo)kdtFeesWarrantEntry.getRow(0).getCell("account").getValue();
			for(int i=0;i<kdtFeesWarrantEntry.getRowCount();i++){
				kdtFeesWarrantEntry.getRow(i).getCell("account").setValue(accountViewInfo);
			}
	}
  
	public void actionSetunAccount_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSetunAccount_actionPerformed(e);
		AccountViewInfo accountViewInfo=null;
		if(kdtFeesWarrantEntry.getRowCount()>1&&kdtFeesWarrantEntry.getRow(0).getCell("oppSubject").getValue()!=null) 
			 accountViewInfo=(AccountViewInfo)kdtFeesWarrantEntry.getRow(0).getCell("oppSubject").getValue();
		for(int i=0;i<kdtFeesWarrantEntry.getRowCount();i++){
			kdtFeesWarrantEntry.getRow(i).getCell("oppSubject").setValue(accountViewInfo);
		}
	}

	public void actionAddEntry_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddEntry_actionPerformed(e);
		UIContext uiContext = new UIContext(this);
		if(sellProject.getValue()!=null)
		sellProjectname=(SellProjectInfo)sellProject.getValue();
		if (sellProjectname!= null&& sellProjectname instanceof SellProjectInfo) {
			uiContext.put("sellProject", sellProjectname);
		}else{
			uiContext.put("sellProject", null);
		}
		uiContext.put("entry", kdtFeesWarrantEntry);
		IUIWindow uiWindow =UIFactory.createUIFactory(UIFactoryName.MODEL).create("com.kingdee.eas.fdc.tenancy.client.FeesWarrantFeeListUI", uiContext, null, OprtState.VIEW);		    
		uiWindow.show();
		
	}
	public void onLoad() throws Exception {
		kDWorkButton1.setEnabled(false);
		initEntry();
		this.kDWorkButton2.setIcon(EASResource.getIcon("imgTbtn_emend")); 
		this.kDWorkButton3.setIcon(EASResource.getIcon("imgTbtn_emend")); 
		super.onLoad();
		SPInfo spInfo = SPManager.getInstance().getSeviceProvider("DAPTransImpl");
		Constructor constructor = spInfo.getProviderClass().getConstructor(new Class[] {
                com.kingdee.eas.framework.client.CoreBillEditUI.class
            });
		Object dapObject = constructor.newInstance(new Object[] {
                this
            });
            dapTrans = (IDAPTrans)dapObject;
		dapTrans.init();
		this.kdtFeesWarrantEntry.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
		this.kdtFeesWarrantEntry.getColumn("actRevAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(3));
		contCreator.setEnabled(false);prmtCreator.setEnabled(false);
		contCreateTime.setEnabled(false);
		contLastUpdateUser.setEnabled(false);prmtLastUpdateUser.setEnabled(false);
		contLastUpdateTime.setEnabled(false);
		pkBizDate.setValue(new Date());
		contState.setVisible(false);
		if (saleOrg.isIsBizUnit()) 
		{
			this.actionEdit.setEnabled(true);
			this.actionAddNew.setEnabled(true);
			this.actionRemove.setEnabled(true);
			
			
//			if(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.SAVED.equals(comboState.getSelectedItem())||com.kingdee.eas.fdc.basedata.FDCBillStateEnum..AUDITTING.equals(comboState.getSelectedItem())){
				kDWorkButton1.setEnabled(true);
//			}
		}else{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			kDWorkButton1.setEnabled(false);
		}
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		this.prmtCreator.setEnabled(false);
		sellProject.setEnabled(false);
		this.storeFields();
		
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		hideButton();
		if(this.getOprtState().equalsIgnoreCase(OprtState.ADDNEW)){
			addNews();
			this.actionSetAccount.setEnabled(true);
			this.actionSetunAccount.setEnabled(true);
		}else if(this.getOprtState().equalsIgnoreCase(OprtState.EDIT)){
			loadFields();
			this.actionSetAccount.setEnabled(true);
			this.actionSetunAccount.setEnabled(true);
		}else if(this.getOprtState().equalsIgnoreCase(OprtState.VIEW)){
			this.actionSetAccount.setEnabled(false);
			this.actionSetunAccount.setEnabled(false);
		}
		//锁定：
		setColumnLocked(COL_TENANCY);//合同
		setColumnLocked(COL_TENANCYNAME);//合同名称
		setColumnLocked(COL_CUSTOMER);
		setColumnLocked(COL_ROOM);//房间
		setColumnLocked(COL_MONEYDEFINE);//款项
		setColumnLocked(COL_STARTDATE);//开始日期
		setColumnLocked(COL_ENDDATE);//结束日期
		setColumnLocked(COL_APPDATE);//应付日期
		setColumnLocked(COL_ACTREVDATE);//实收日期
		setColumnLocked(COL_APPAMOUNT);//应收金额
		setColumnLocked(COL_ACTREVAMOUNT);///实收金额
		
		
    }
	
	private void loadEntry(FeesWarrantInfo info) {
		this.kdtFeesWarrantEntry.removeRows();
		FeesWarrantEntrysCollection ew = info.getFeesWarrantEntry();
		for (int i = 0; i < ew.size(); i++) {
			FeesWarrantEntrysInfo entry = ew.get(i);
			if (entry == null)
				return;
			IRow row = this.kdtFeesWarrantEntry.addRow();
			row.setUserObject(entry);
			row.getCell(COL_TENANCY).setValue(entry.getTenancyBill());//合同
			row.getCell(COL_TENANCYNAME).setValue(entry.getTenancyName());//合同名称
			row.getCell(COL_CUSTOMER).setValue(entry.getCustomer());
			row.getCell(COL_ROOM).setValue(entry.getRoom());//房间
			row.getCell(COL_MONEYDEFINE).setValue(entry.getMoneyDefine());//款项
			row.getCell(COL_STARTDATE).setValue(entry.getStartDate());//开始日期
			row.getCell(COL_ENDDATE).setValue(entry.getEndDate());//结束日期
			row.getCell(COL_APPDATE).setValue(entry.getAppDate());//应付日期
			row.getCell(COL_ACTREVDATE).setValue(entry.getActRevDate());//实收日期
			row.getCell(COL_APPAMOUNT).setValue(entry.getAppAmount());//应收金额
			row.getCell(COL_ACTREVAMOUNT).setValue(entry.getActRevAmount());///实收金额
			row.getCell(COL_ACCOUNT).setValue(entry.getAccount());///付款科目
			row.getCell(COL_OPPSUBJECT).setValue(entry.getOppSubject());///对方科目
		}
		

	}
	public void loadFields() {
		super.loadFields();
		FeesWarrantInfo info=this.editData;
		loadEntry(info);
	}
	private void addNews() {
		sellProjectname = (SellProjectInfo) this.getUIContext().get("sellProject");
		sellProject.setValue(sellProjectname);
		if(sellProject.getValue()!=null){
			sellProject.setEnabled(false);
		}else{
			sellProject.setEnabled(true);
		}
		comboState.setSelectedItem(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.SAVED);
		comboState.setEnabled(false);
	}
	private void storeEntry(){
		FeesWarrantInfo info = this.editData;
		FeesWarrantEntrysCollection collection=info.getFeesWarrantEntry();
		collection.clear();
		
		FeesWarrantEntrysCollection fwec=new FeesWarrantEntrysCollection();
		for (int i = 0; i < this.kdtFeesWarrantEntry.getRowCount(); i++) {
			IRow row = this.kdtFeesWarrantEntry.getRow(i);
			FeesWarrantEntrysInfo entryinfo=(FeesWarrantEntrysInfo)row.getUserObject();
			
			entryinfo.setTenancyBill((TenancyBillInfo)row.getCell(COL_TENANCY).getValue());//合同
			entryinfo.setTenancyName((String)row.getCell(COL_TENANCYNAME).getValue());//合同名称
			
			entryinfo.setCustomer((FDCCustomerInfo)row.getCell(COL_CUSTOMER).getValue());
			entryinfo.setRoom((RoomInfo)row.getCell(COL_ROOM).getValue());
			entryinfo.setMoneyDefine((MoneyDefineInfo)row.getCell(COL_MONEYDEFINE).getValue());
			entryinfo.setStartDate((Date)row.getCell(COL_STARTDATE).getValue());
			entryinfo.setEndDate((Date)row.getCell(COL_ENDDATE).getValue());
			entryinfo.setAppDate((Date)row.getCell(COL_APPDATE).getValue());
			entryinfo.setActRevDate((Date)row.getCell(COL_ACTREVDATE).getValue());
			entryinfo.setAppAmount((BigDecimal)row.getCell(COL_APPAMOUNT).getValue());
			entryinfo.setActRevAmount((BigDecimal)row.getCell(COL_ACTREVAMOUNT).getValue());
			entryinfo.setAccount((AccountViewInfo)row.getCell(COL_ACCOUNT).getValue());//付款科目
			entryinfo.setOppSubject((AccountViewInfo)row.getCell(COL_OPPSUBJECT).getValue());//对方科目
			fwec.add(entryinfo);
		}
		this.editData.getFeesWarrantEntry().addCollection(fwec);
	}
	private EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo)
	{
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------转6.0后修改,科目不按CU隔离,根据财务组织进行隔离
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}
	private void setColumnLocked(String colName){
		IColumn col = this.kdtFeesWarrantEntry.getColumn(colName);
		col.getStyleAttributes().setLocked(true);
	}
	
	private void initEntry() {
		this.kdtFeesWarrantEntry.checkParsed();   

		this.kdtFeesWarrantEntry.getColumn(COL_CUSTOMER).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery", null));
		this.kdtFeesWarrantEntry.getColumn(COL_ROOM).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.F7RoomQuery", null));
		this.kdtFeesWarrantEntry.getColumn(COL_MONEYDEFINE).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery", null));
		// 收款科目
		KDBizPromptBox gatheringSubject = new KDBizPromptBox();
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext().getCurrentFIUnit();
		
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox opseelect = new AccountPromptBox(this, curCompany,view.getFilter(), false, true);
		gatheringSubject.setEntityViewInfo(view);
		gatheringSubject.setSelector(opseelect);
		gatheringSubject.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
		gatheringSubject.setEditFormat("$number$");
		gatheringSubject.setCommitFormat("$number$");

		ICellEditor f7Editor = new KDTDefaultCellEditor(gatheringSubject);
		this.kdtFeesWarrantEntry.getColumn(COL_ACCOUNT).setEditor(f7Editor);//对方科目
		this.kdtFeesWarrantEntry.getColumn(COL_OPPSUBJECT).setEditor(f7Editor);//对方科目
//		this.kdtFeesWarrantEntry.getColumn(COL_ACCOUNT).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.basedata.master.account.app.AccountViewQuery", null));
//		this.kdtFeesWarrantEntry.getColumn(COL_OPPSUBJECT).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery", null));
		this.kdtFeesWarrantEntry.getColumn(COL_STARTDATE).getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		this.kdtFeesWarrantEntry.getColumn(COL_ENDDATE).getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		this.kdtFeesWarrantEntry.getColumn(COL_APPDATE).getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		this.kdtFeesWarrantEntry.getColumn(COL_ACTREVDATE).getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		
		
		
		
		//开始日期
		KDDatePicker startDate = new KDDatePicker();
		kdtFeesWarrantEntry.getColumn(COL_STARTDATE).setEditor(new KDTDefaultCellEditor(startDate));
		//结束日期
		KDDatePicker endDate = new KDDatePicker();
		kdtFeesWarrantEntry.getColumn(COL_ENDDATE).setEditor(new KDTDefaultCellEditor(endDate));
		//实际日期
		KDDatePicker actappDate = new KDDatePicker();
		kdtFeesWarrantEntry.getColumn(COL_ACTREVDATE).setEditor(new KDTDefaultCellEditor(actappDate));
		//应付日期
		KDDatePicker appDate = new KDDatePicker();
		kdtFeesWarrantEntry.getColumn(COL_APPDATE).setEditor(new KDTDefaultCellEditor(appDate));
		//应付金额 
		KDFormattedTextField appAmount = new KDFormattedTextField();
		 appAmount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		 appAmount.setPrecision(3);
		 appAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		 appAmount.setMaximumValue(FDCHelper.MAX_VALUE);
		 kdtFeesWarrantEntry.getColumn(COL_APPAMOUNT).setEditor(new KDTDefaultCellEditor(appAmount));
		 kdtFeesWarrantEntry.getColumn(COL_APPAMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		 //实付金额
		 KDFormattedTextField actAmount = new KDFormattedTextField();
		 actAmount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		 actAmount.setPrecision(3);
		 actAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		 actAmount.setMaximumValue(FDCHelper.MAX_VALUE);
		 kdtFeesWarrantEntry.getColumn(COL_ACTREVAMOUNT).setEditor(new KDTDefaultCellEditor(actAmount));
		 kdtFeesWarrantEntry.getColumn(COL_ACTREVAMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		 
			
		 KDBizPromptBox box = new KDBizPromptBox();
		 box.setEditable(false);
		 box.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillF7Query");
		 box.setDisplayFormat("$name$");
		 box.setEditFormat("$number$");
		 box.setCommitFormat("$number$");
	     ICellEditor f7editor = new KDTDefaultCellEditor(box);
	     
	     ObjectValueRender numberRender = new ObjectValueRender(); 
	     numberRender.setFormat(new IDataFormat(){ 
			public String format(Object o) { 
				if(o instanceof TenancyBillInfo){ 
					return ((TenancyBillInfo)o).getNumber(); 
				} 
				return o.toString(); 
			}}); 

	     this.kdtFeesWarrantEntry.getColumn(COL_TENANCY).setRenderer(numberRender);
    	 this.kdtFeesWarrantEntry.getColumn(COL_TENANCY).setEditor(f7editor);
	}
	private KDTDefaultCellEditor createTxtCellEditor(int length, boolean editable) {
		return TenancyClientHelper.createTxtCellEditor(length, editable);
	}
	/**
	 * 隐藏按钮
	 *com.kingdee.eas.basedata.master.account.app.AccountView
	 */
	private void hideButton()
	{
		this.menuBiz.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		//this.actionCopyFrom.setVisible(false);
		this.actionSave.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(true);
		this.menuSubmitOption.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionFirst.setVisible(false);
		actionAttachment.setVisible(false);
		actionCreateFrom.setVisible(false);
		btnPrint.setVisible(false);
		btnPrintPreview.setVisible(false);
		btnAddNew.setVisible(false);
		btnAddLine.setVisible(false);
		btnWorkFlowG.setVisible(false);
		btnWFViewdoProccess.setVisible(false);
		btnRemoveLine.setVisible(false);
		btnMultiapprove.setVisible(false);
		btnNextPerson.setVisible(false);
		comboState.setEnabled(false);
		chkFiVouchered.setEnabled(false);
		sellProject.setEnabled(false);
		comboState.setEnabled(false);
	}
	 /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
        storeEntry();
    }

	protected void verifyInput(ActionEvent actionevent) throws Exception {
		// TODO Auto-generated method stub
		super.verifyInput(actionevent);
		if(StringUtils.isEmpty(this.txtNumber.getText())){
			MsgBox.showInfo("编码必须录入！");
			abort();
		}
		if(StringUtils.isEmpty(this.txtName.getText())){
			MsgBox.showInfo("名称必须录入！");
			abort();
		}
	}
	protected ICoreBase getBizInterface() throws Exception {
		return FeesWarrantFactory.getRemoteInstance();
	}
	private static final Logger logger = CoreUIObject.getLogger(FeesWarrantEditUI.class);
    
    /**
     * output class constructor
     */
    public FeesWarrantEditUI() throws Exception
    {
        super();
    }

   
    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    	this.prmtCreator.setEnabled(false);
		sellProject.setEnabled(false);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
    	CoreBillBaseCollection sourceBillCollection = getBillCollection();
        verifyBillCollection(sourceBillCollection);
        dapTrans.trans(sourceBillCollection);
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

	protected KDTable getDetailTable() {
		return null;
	}
	  protected IObjectValue createNewData() {
			FeesWarrantInfo objectValue = new FeesWarrantInfo();
	        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
	        objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
	        return objectValue;
		}
	  
	  public SelectorItemCollection getSelectors() {
			SelectorItemCollection sels = super.getSelectors();
			sels.add("feesWarrantEntry.*");
			sels.add("feesWarrantEntry.customer");
			sels.add("feesWarrantEntry.customer.*");
			sels.add("feesWarrantEntry.tenancyBill");
			sels.add("feesWarrantEntry.tenancyBill.*");
			sels.add("feesWarrantEntry.room");
			sels.add("feesWarrantEntry.room.*");
			sels.add("feesWarrantEntry.moneyDefine");
			sels.add("feesWarrantEntry.moneyDefine.*");
			sels.add("feesWarrantEntry.account");
			sels.add("feesWarrantEntry.account.*");
			sels.add("feesWarrantEntry.oppSubject");
			sels.add("feesWarrantEntry.oppSubject.*");
			return sels;
		}
}