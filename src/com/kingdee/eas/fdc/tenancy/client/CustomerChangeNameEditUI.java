/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enterprisedt.net.ftp.EventListener;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.CustomerCardUI;
import com.kingdee.eas.fdc.sellhouse.client.CustomerEditUI;
import com.kingdee.eas.fdc.tenancy.CustomerChangeEntryCollection;
import com.kingdee.eas.fdc.tenancy.CustomerChangeEntryInfo;
import com.kingdee.eas.fdc.tenancy.CustomerChangeName;
import com.kingdee.eas.fdc.tenancy.CustomerChangeNameFactory;
import com.kingdee.eas.fdc.tenancy.CustomerChangeNameInfo;
import com.kingdee.eas.fdc.tenancy.CustomerChangeOldEntryCollection;
import com.kingdee.eas.fdc.tenancy.CustomerChangeOldEntryInfo;
import com.kingdee.eas.fdc.tenancy.ITenBillBase;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CustomerChangeNameEditUI extends AbstractCustomerChangeNameEditUI implements TenancyBillConstant
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerChangeNameEditUI.class);
    private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
    
    public CustomerChangeNameEditUI() throws Exception
    {
        super();
    }
       
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
        this.actionAudit.setVisible(true);
    }
    
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	String id = getSelectBOID();
		if (id != null) {
			((ITenBillBase)getBizInterface()).audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
    }

    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
	protected void attachListeners() {
		
	}

	protected void detachListeners() {
	}

	protected ICoreBase getBizInterface() throws Exception {
		
		return CustomerChangeNameFactory.getRemoteInstance();
	}


	protected KDTextField getNumberCtrl() {
		return this.txtName;
	}

	protected IObjectValue createNewData() {
		CustomerChangeNameInfo info = 	new CustomerChangeNameInfo();
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		info.setCreator(user);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setCreateTime(new Timestamp(new Date().getTime()));
		info.setBizDate(new Date());
		Map  context = this.getUIContext();
		SellProjectInfo sellProject = (SellProjectInfo)context.get("sellProject");
		info.setSellProject(sellProject);
		return info;
	}
	
	protected void btnAddCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddCustomer_actionPerformed(e);
		int activeRowIndex = this.tblCustomer.getSelectManager().getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.tblCustomer.addRow();
		} else {
			row = this.tblCustomer.addRow(activeRowIndex + 1);
		}
		row.setHeight(20);
		row.setUserObject(new CustomerChangeEntryInfo());
		if (this.tblCustomer.getRowCount() == 1) {
			row.getCell(C_CUS_PROPERTY_PERCENT).setValue(new BigDecimal("100"));
		}
	}

	protected void btnNewCustomer_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerEditUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		
		CustomerEditUI cusEditUI = (CustomerEditUI) uiWindow.getUIObject();
		FDCCustomerInfo cus = (FDCCustomerInfo) cusEditUI.getUIContext().get(CustomerEditUI.KEY_SUBMITTED_DATA);
		if (cus != null) {
			addCustomerRow(cus);
		}
	}
	private void addCustomerRow(FDCCustomerInfo customer) {
		int activeRowIndex = this.tblCustomer.getSelectManager().getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.tblCustomer.addRow();
		} else {
			row = this.tblCustomer.addRow(activeRowIndex + 1);
		}
		row.setHeight(20);
		row.setUserObject(new CustomerChangeEntryInfo());
		if (this.tblCustomer.getRowCount() == 1) {
			row.getCell(C_CUS_PROPERTY_PERCENT).setValue(new BigDecimal("100"));
		}
		row.getCell("customer").setValue(customer);
		row.getCell("phone").setValue(customer.getPhone());
		if(customer.getPostalcode()!=null){
			row.getCell("postalcode").setValue(customer.getPostalcode());
		}
		if(customer.getCertificateName()!=null){
			row.getCell("certificateName").setValue(customer.getCertificateName());
		}
		if(customer.getCertificateNumber()!=null){
			row.getCell("certificateNumber").setValue(customer.getCertificateNumber());
		}
		if(customer.getMailAddress()!=null){
			row.getCell("mailAddress").setValue(customer.getMailAddress());
		}
		row.getCell("bookDate").setValue(customer.getCreateTime());
		if(customer.getDescription()!=null){
			row.getCell("des").setValue(customer.getDescription());
		}
	}
	
	protected void btnRemoveCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.btnRemoveCustomer_actionPerformed(e);
		removeRow(this.tblCustomer);
	}
	
	/** 删除table中的一行,有选中时删除选中行,否则删除最后一行 */
	private void removeRow(KDTable table) {
		int activeRowIndex = table.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = table.getRowCount();
		}
		table.removeRow(activeRowIndex);
	}

	protected KDTable getDetailTable() {
		return null;
	}
	
	public void onLoad() throws Exception {
		initF7Tenancybill();
		initCustomeTable(this.kdtOldCustomer);
		initCustomeTable(this.tblCustomer);
		this.kdtOldCustomer.getStyleAttributes().setLocked(true);
		this.txtName.setRequired(true);
		this.txtNumber.setRequired(true);
		super.onLoad();
		this.actionAudit.setVisible(true);
	}

	private void initF7Tenancybill() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		Set stateSet =new HashSet();
		stateSet.add(TenancyBillStateEnum.EXECUTING_VALUE);
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",stateSet,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",(SellProjectInfo)this.getUIContext().get("sellProject")==null?null:((SellProjectInfo)this.getUIContext().get("sellProject")).getId()));
		view.setFilter(filter);
		this.prmtTenancyBill.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");
		this.prmtTenancyBill.setEditable(true);
		this.prmtTenancyBill.setDisplayFormat("$name$");
		this.prmtTenancyBill.setEditFormat("$name$");
		this.prmtTenancyBill.setCommitFormat("$number$");
		this.prmtTenancyBill.setEntityViewInfo(view);
	}
	protected void prmtTenancyBill_dataChanged(DataChangeEvent e)
			throws Exception {
		TenancyBillInfo tenancyInfo = (TenancyBillInfo)this.prmtTenancyBill.getValue();
		String tenancyID= null;
		if(tenancyInfo!=null){
			tenancyID = tenancyInfo.getId().toString();
		}else{
			return;
		}
		//取得合同的客户信息 
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add("*");
		coll.add("fdcCustomer.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill",tenancyID));
		view.setFilter(filter);
		view.setSelector(coll);
		TenancyCustomerEntryCollection customerColl = TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryCollection(view);
		//租赁客户转换成更名单客户
		CustomerChangeOldEntryCollection cutomerChangeColl = tenCustoemrToCustomerChange(customerColl);
		loadOldCustomers(cutomerChangeColl,this.kdtOldCustomer);
	}
	private CustomerChangeOldEntryCollection tenCustoemrToCustomerChange(TenancyCustomerEntryCollection customerColl) {
		CustomerChangeOldEntryCollection cutomerChangeColl = new CustomerChangeOldEntryCollection();
		for(int i = 0 ;i <customerColl.size();i++){
			TenancyCustomerEntryInfo tenancyCust = customerColl.get(i);
			CustomerChangeOldEntryInfo changeInfo = new CustomerChangeOldEntryInfo();
			changeInfo.setPropertyPercent(tenancyCust.getPropertyPercent());
			changeInfo.setDescription(tenancyCust.getDescription());
			changeInfo.setFdcCustomer(tenancyCust.getFdcCustomer());
			cutomerChangeColl.add(changeInfo);
		}
		return cutomerChangeColl;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection coll = super.getSelectors();
		coll.add("newCustomer.propertyPercent");
		coll.add("newCustomer.description");
		coll.add("newCustomer.fdcCustomer.*");
		
		coll.add("oldCustomer.propertyPercent");
		coll.add("oldCustomer.description");
		coll.add("oldCustomer.fdcCustomer.*");
		coll.add("sellProject");
		return coll;
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		verifyCustomer();
		super.verifyInput(e);
	}

	public void loadFields() {
		super.loadFields();
		CustomerChangeNameInfo changeNameInfo = this.editData;
		loadCustomers(changeNameInfo.getNewCustomer(),this.tblCustomer);
		loadOldCustomers(changeNameInfo.getOldCustomer(),this.kdtOldCustomer);
		
	}
	private void loadOldCustomers(CustomerChangeOldEntryCollection tenCustomerList,
			KDTable table) {
		table.removeRows();
		for (int i = 0; i < tenCustomerList.size(); i++) {
			CustomerChangeOldEntryInfo tenCustomer = tenCustomerList.get(i);
			IRow row = table.addRow();
			row.setHeight(20);
			row.setUserObject(tenCustomer);
			row.getCell(C_CUS_PROPERTY_PERCENT).setValue(tenCustomer.getPropertyPercent());
			row.getCell(C_CUS_DES).setValue(tenCustomer.getDescription());
			FDCCustomerInfo customer = tenCustomer.getFdcCustomer();
			if (customer != null) {
				row.getCell(C_CUS_CUSTOMER).setValue(customer);
				row.getCell(C_CUS_POSTALCODE).setValue(customer.getPostalcode());
				row.getCell(C_CUS_PHONE).setValue(customer.getPhone());
				row.getCell(C_CUS_CERTIFICATE_NAME).setValue(customer.getCertificateName());
				row.getCell(C_CUS_CERTIFICATE_NUMBER).setValue(customer.getCertificateNumber());
				row.getCell(C_CUS_MAIL_ADDRESS).setValue(customer.getMailAddress());
				row.getCell(C_CUS_BOOK_DATE).setValue(customer.getCreateTime());
			}
		}
		
	}

	public void storeFields()
	    {
	        CustomerChangeNameInfo tenBill = this.editData;
			CustomerChangeEntryCollection newCustomerInfos = tenBill.getNewCustomer();
			storeTenancyCustomer(newCustomerInfos,this.tblCustomer);
			CustomerChangeOldEntryCollection oldCustomerInfos = tenBill.getOldCustomer();
			storeOldTenancyCustomer(oldCustomerInfos,this.kdtOldCustomer);
			super.storeFields();

			
	    }
	private void storeOldTenancyCustomer(
			CustomerChangeOldEntryCollection customerInfos,
			KDTable table) {
		customerInfos.clear();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			CustomerChangeOldEntryInfo customerInfo = (CustomerChangeOldEntryInfo) row.getUserObject();
			FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell(C_CUS_CUSTOMER).getValue();
			if(customer==null)
			{
				MsgBox.showInfo("租赁客户不能为空!");
				this.abort();
			}
			if (customer != null) {
				customer.setMailAddress((String) row.getCell(C_CUS_MAIL_ADDRESS).getValue());
				customer.setPhone((String) row.getCell(C_CUS_PHONE).getValue());
				customer.setPostalcode((String) row.getCell(C_CUS_POSTALCODE).getValue());
				if (row.getCell(C_CUS_CERTIFICATE_NAME).getValue() != null) {
//					customer.setCertificateName((CertifacateNameEnum) row.getCell(C_CUS_CERTIFICATE_NAME).getValue());
				}
				customer.setCertificateNumber((String) row.getCell(C_CUS_CERTIFICATE_NUMBER).getValue());
			}
			customerInfo.setFdcCustomer(customer);
			customerInfo.setPropertyPercent((BigDecimal) row.getCell(C_CUS_PROPERTY_PERCENT).getValue());
			customerInfo.setDescription((String) row.getCell(C_CUS_DES).getValue());
			customerInfos.add(customerInfo);
		}
	}

	private void loadCustomers(CustomerChangeEntryCollection tenCustomerList,KDTable table) {
		table.removeRows();
		for (int i = 0; i < tenCustomerList.size(); i++) {
			CustomerChangeEntryInfo tenCustomer = tenCustomerList.get(i);
			IRow row = table.addRow();
			row.setHeight(20);
			row.setUserObject(tenCustomer);
			row.getCell(C_CUS_PROPERTY_PERCENT).setValue(tenCustomer.getPropertyPercent());
			row.getCell(C_CUS_DES).setValue(tenCustomer.getDescription());
			FDCCustomerInfo customer = tenCustomer.getFdcCustomer();
			if (customer != null) {
				row.getCell(C_CUS_CUSTOMER).setValue(customer);
				row.getCell(C_CUS_POSTALCODE).setValue(customer.getPostalcode());
				row.getCell(C_CUS_PHONE).setValue(customer.getPhone());
				row.getCell(C_CUS_CERTIFICATE_NAME).setValue(customer.getCertificateName());
				row.getCell(C_CUS_CERTIFICATE_NUMBER).setValue(customer.getCertificateNumber());
				row.getCell(C_CUS_MAIL_ADDRESS).setValue(customer.getMailAddress());
				row.getCell(C_CUS_BOOK_DATE).setValue(customer.getCreateTime());
			}
		}
	}	
	
	private void storeTenancyCustomer(CustomerChangeEntryCollection customerInfos,KDTable table) {
		customerInfos.clear();
//		StringBuffer tenCustomerDes = new StringBuffer();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			CustomerChangeEntryInfo customerInfo = (CustomerChangeEntryInfo) row.getUserObject();
			FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell(C_CUS_CUSTOMER).getValue();
			if(customer==null)
			{
				MsgBox.showInfo("租赁客户不能为空!");
				this.abort();
			}
			if (customer != null) {
				customer.setMailAddress((String) row.getCell(C_CUS_MAIL_ADDRESS).getValue());
				customer.setPhone((String) row.getCell(C_CUS_PHONE).getValue());
				customer.setPostalcode((String) row.getCell(C_CUS_POSTALCODE).getValue());
				if (row.getCell(C_CUS_CERTIFICATE_NAME).getValue() != null) {
//					customer.setCertificateName((CertifacateNameEnum) row.getCell(C_CUS_CERTIFICATE_NAME).getValue());
				}
				customer.setCertificateNumber((String) row.getCell(C_CUS_CERTIFICATE_NUMBER).getValue());
			}
			customerInfo.setFdcCustomer(customer);
			customerInfo.setPropertyPercent((BigDecimal) row.getCell(C_CUS_PROPERTY_PERCENT).getValue());
			customerInfo.setDescription((String) row.getCell(C_CUS_DES).getValue());
			customerInfos.add(customerInfo);
//			if (i != 0) {
//				tenCustomerDes.append(",");
//			}
//			tenCustomerDes.append(customer.getName());
		}
//		tenBill.setTenCustomerDes(tenCustomerDes.toString());
	}
	
	/** 初始化客户列表 
	 * @throws BOSException 
	 * @throws EASBizException */
	private void initCustomeTable(KDTable table) throws EASBizException, BOSException {
		table.checkParsed();
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		table.getColumn(C_CUS_BOOK_DATE).getStyleAttributes().setLocked(true);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn(C_CUS_PROPERTY_PERCENT).setEditor(numberEditor);
		table.getColumn(C_CUS_PROPERTY_PERCENT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn(C_CUS_PROPERTY_PERCENT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		table.getColumn(C_CUS_BOOK_DATE).getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		KDBizPromptBox f7Customer = new KDBizPromptBox();
		f7Customer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
		f7Customer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(userInfo, MoneySysTypeEnum.TenancySys));
		f7Customer.setEditable(true);
		f7Customer.setDisplayFormat("$name$");
		f7Customer.setEditFormat("$number$");
		f7Customer.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Customer);
		table.getColumn(C_CUS_CUSTOMER).setEditor(f7Editor);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn(C_CUS_DES).setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn(C_CUS_PHONE).setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn(C_CUS_MAIL_ADDRESS).setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn(C_CUS_POSTALCODE).setEditor(txtEditor);
		KDComboBox comboField = new KDComboBox();
		List list = CertifacateNameEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		table.getColumn(C_CUS_CERTIFICATE_NAME).setEditor(comboEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn(C_CUS_CERTIFICATE_NUMBER).setEditor(txtEditor);
		table.getColumn(C_CUS_CERTIFICATE_NUMBER).setRequired(true);
	}
	protected void tblCustomer_editStopped(KDTEditEvent e) throws Exception {
		
		if (e.getColIndex() == 1) {
			IRow row = this.tblCustomer.getRow(e.getRowIndex());
			int i=row.getRowIndex()-1;
			FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell(C_CUS_CUSTOMER).getValue();
			if(customer==null){
				return;
			}
			boolean flag=true;
			// 对比相同客户 tim_gao 2010-9-7
			while(i>=0&&flag){
				FDCCustomerInfo compra = (FDCCustomerInfo)this.tblCustomer.getRow(i).getCell(C_CUS_CUSTOMER).getValue();
				if(customer.getId().toString().equals(compra.getId().toString())){
					MsgBox.showWarning(this, "与第"+(i+1)+"行客户相同,禁止添加!");
					flag=false;
				}
				i--;
			}
			if (customer != null&&flag) {
				row.getCell(C_CUS_PHONE).setValue(customer.getPhone());
				row.getCell(C_CUS_POSTALCODE).setValue(customer.getPostalcode());
				row.getCell(C_CUS_CERTIFICATE_NAME).setValue(customer.getCertificateName());
				row.getCell(C_CUS_CERTIFICATE_NUMBER).setValue(customer.getCertificateNumber());
				row.getCell(C_CUS_MAIL_ADDRESS).setValue(customer.getMailAddress());
				row.getCell(C_CUS_BOOK_DATE).setValue(customer.getCreateTime());
			} else {
				row.getCell(C_CUS_PHONE).setValue(null);
				row.getCell(C_CUS_POSTALCODE).setValue(null);
				row.getCell(C_CUS_CERTIFICATE_NAME).setValue(null);
				row.getCell(C_CUS_CERTIFICATE_NUMBER).setValue(null);
				row.getCell(C_CUS_MAIL_ADDRESS).setValue(null);
				row.getCell(C_CUS_BOOK_DATE).setValue(null);
			}
		}
	}
	
	/**
	 * 添加 对客户信息的验证
	 */
	private void verifyCustomer() throws BOSException {
		BigDecimal percent = FDCHelper.ZERO;
		Map customerMap = new HashMap();
		if (this.tblCustomer.getRowCount() == 0) {
			MsgBox.showInfo("没有租赁客户,请添加!");
			this.abort();
		}

		for (int i = 0; i < this.tblCustomer.getRowCount(); i++) {
			IRow row = this.tblCustomer.getRow(i);
			if (row.getCell("propertyPercent").getValue() == null) {
				MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户没有设置出租比例!");
				this.abort();
			}

			FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell("customer").getValue();
			if (customer == null) {
				MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户没有录入!");
				this.abort();
			}
			if (customer.getCertificateName() == null || customer.getCertificateNumber() == null) {
				MsgBox.showInfo("客户" + customer.getName() + "证件不能为空!!");
				CustomerCardUI.addTheCustomerAuthority(customer, this);
				setCustomerRowData(customer, row);
				this.abort();
			}
			if (customerMap.containsKey(customer)) {
				MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户重复!");
				this.abort();
			} else {
				customerMap.put(customer, customer);
			}
			percent = percent.add((BigDecimal) row.getCell("propertyPercent").getValue());
		}
		if (percent.compareTo(new BigDecimal("100")) != 0) {
			MsgBox.showInfo("出租比例不等100%!");
			this.abort();
		}
	}
	private void setCustomerRowData(FDCCustomerInfo customer, IRow row) {
		if (customer != null) {
			row.getCell("customer").setValue(customer);

			row.getCell("phone").setValue(customer.getPhone());
			row.getCell("postalcode").setValue(customer.getPostalcode());
			row.getCell("certificateName").setValue(customer.getCertificateName());
			row.getCell("certificateNumber").setValue(customer.getCertificateNumber());
			row.getCell("mailAddress").setValue(customer.getMailAddress());
			row.getCell("bookDate").setValue(customer.getCreateTime());
		} else {
			row.getCell("phone").setValue(null);
			row.getCell("postalcode").setValue(null);
			row.getCell("certificateName").setValue(null);
			row.getCell("certificateNumber").setValue(null);
			row.getCell("mailAddress").setValue(null);
			row.getCell("bookDate").setValue(null);
		}
	}
	protected void handleOldData() {
		super.handleOldData();
	}
}