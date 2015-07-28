/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.agent.AgentUtility;
import com.kingdee.bos.framework.agent.IObjectValueAgent;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.client.f7.AdminF7;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IContractType;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ObjectValueForEditUIUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * 描述:合同类型编辑界面
 * 
 * @author jackwang date:2006-7-7
 * @version EAS5.1
 */
public class ContractTypeEditUI extends AbstractContractTypeEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ContractTypeEditUI.class);

	private ContractTypeInfo parentInfo;

	private String parentNumber = null;

	private String strTemp = null;

	/**
	 * output class constructor
	 */
	public ContractTypeEditUI() throws Exception {
		super();
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("isCost"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("parent.*"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("payScale"));
		sic.add(new SelectorItemInfo("dutyOrgUnit.*"));
		return sic;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected IObjectValue createNewData() {
		ContractTypeInfo contractTypeInfo = new ContractTypeInfo();
		// contractTypeInfo.setIsEnabled(true);
		contractTypeInfo.setIsCost(true);
		return contractTypeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractTypeFactory.getRemoteInstance();
	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		super.loadFields();
		parentInfo = (ContractTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		this.setDataObject(editData);
		if (getUIContext().get(UIContext.PARENTNODE) != null) {
			strTemp = parentInfo.getLongNumber();
			strTemp = strTemp.replace('!', '.');
			parentNumber = strTemp;
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
				this.txtLongNumber.setText(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);

				} else {
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}
			} else if (STATUS_VIEW.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);

				} else {
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}

			}
		} else {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
				
			} else if (STATUS_EDIT.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				if(strTemp.lastIndexOf(".")>=0){
					parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				}
				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);

				} else {
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}
				if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
					this.btnRemove.setEnabled(false);
				}
			} else if (STATUS_VIEW.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				if(strTemp.lastIndexOf(".")>=0){
					parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				}
				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);

				} else {
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}

			}
		}
		if (STATUS_VIEW.equals(getOprtState())) {
			this.chkIsCost.setEnabled(false);
		}
	}

	/**
	 * 校验值对象的合法性
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// 编码是否为空
		String longNumber = this.txtLongNumber.getText().trim();
		if (longNumber == null || longNumber.trim().length() < 1 || longNumber.lastIndexOf(".")+1==longNumber.length() || longNumber.indexOf(".")==0
				 || longNumber.lastIndexOf("!")+1==longNumber.length() || longNumber.indexOf("!")==0 ) {
			txtLongNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
		if (getOprtState().equals(OprtState.ADDNEW)){
			FDCBaseTypeValidator.validate(((ContractTypeListUI) getUIContext().get(UIContext.OWNER)).getMainTable(), txtLongNumber, bizName, getSelectBOID());
		
			if (parentNumber != null && (longNumber.equals(parentNumber + ".") || longNumber.length() < parentNumber.length() + 1)) {
				// 编码不完整
				txtLongNumber.requestFocus(true);
				throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_2);
			}
		}
		if (parentNumber != null && ((!longNumber.equalsIgnoreCase(parentNumber) && longNumber.substring(parentNumber.length() + 1, longNumber.length()).indexOf('.') >= 0)// 用户打了点
				|| (!longNumber.substring(0, parentNumber.length()).equals(parentNumber)))) {// 初始父编码被修改
			// 编码不符合规范
			txtLongNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
		
		//用户输入叹号或者修改了初始父编码，则抛出编码不符合规范异常
		if(parentNumber != null && ((!longNumber.equalsIgnoreCase(parentNumber) && longNumber.substring(parentNumber.length() + 1, longNumber.length()).indexOf('!') >= 0)
				||(!longNumber.substring(0, parentNumber.length()).equals(parentNumber))))
		{
			txtLongNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
		this.editData.setNumber(longNumber.substring(longNumber.lastIndexOf(".") + 1,longNumber.length()));
		
		longNumber = longNumber.replace('.', '!');
		this.editData.setLongNumber(longNumber);

		// 名称是否为空
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, this.editData, "name");
		if (flag) {
			bizName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}

	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.CONTRACTTYPE));
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
		setBtnStatus();
		AdminF7 adminF7 = new AdminF7();
		// adminF7.s.setIsCUFilter(true);
		// adminF7.setIsShowSub(false);
		this.bizDutyOrgUnit.setSelector(adminF7);

		// 设置责任部门F7，只能选择最明细的行政组织，刘培德，07.03.26
		FDCClientUtils.setRespDeptF7(bizDutyOrgUnit, this);

		kdftxtPayScale.setHorizontalAlignment(JTextField.RIGHT);
		kdftxtPayScale.setPrecision(2);
		kdftxtPayScale.setSupportedEmpty(true);
		kdftxtPayScale.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
		kdftxtPayScale.setMaximumValue(FDCHelper.MAX_DECIMAL);
		txtStampTaxRate.setHorizontalAlignment(JTextField.RIGHT);
		txtStampTaxRate.setSupportedEmpty(true);
		FDCClientHelper.setValueScopeForPercentCtrl(kdftxtPayScale);
		FDCClientHelper.setValueScopeForPercentCtrl(txtStampTaxRate);
		if (parentInfo != null && parentInfo.isIsEnabled()) {
			this.chkIsEnabled.setSelected(true);
		}
		
		
	//上级编码不允许改
		
		txtLongNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
				ContractTypeInfo parent = (ContractTypeInfo) getUIContext().get(UIContext.PARENTNODE);
				if (/*STATUS_VIEW.equals(getOprtState()) ||*/ parent == null)
					return;
				String longNumber = parent.getLongNumber().replace('!', '.') + '.';
				if (!txtLongNumber.getText().startsWith(longNumber)) {
					txtLongNumber.setText(longNumber);
					txtLongNumber.setSelectionStart(longNumber.length());
				}
			}
			public void keyTyped(KeyEvent e) {}
		});
	}

	private void setBtnStatus() {
		if (STATUS_ADDNEW.equals(getOprtState())) {// 新增状态
			this.btnCancelCancel.setVisible(false);// 启用按钮不可见
			this.btnCancel.setVisible(false);// 禁用按钮不可见
		} else if (STATUS_EDIT.equals(getOprtState())) {// 修改状态
			if (this.editData.isIsEnabled()) {// 如果当前为启用状态
				this.btnCancel.setVisible(true);// 禁用按钮可用
				this.btnCancel.setEnabled(true);// 禁用按钮可用
				this.btnCancelCancel.setVisible(false);// 启用按钮不可见
			} else {// 如果当前为禁用状态
				this.btnCancelCancel.setVisible(true);// 启用按钮可见
				this.btnCancelCancel.setEnabled(true);// 启用按钮可用
				this.btnCancel.setEnabled(false);// 禁用按钮不可见
			}
			if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
				this.btnRemove.setEnabled(false);
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {// 查看状态
			if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
				if (this.editData.isIsEnabled()) {// 如果当前为启用状态
					this.btnCancel.setVisible(true);// 禁用按钮可用
					this.btnCancel.setEnabled(true);// 禁用按钮可用
					this.btnCancelCancel.setVisible(false);// 启用按钮不可见
				} else {// 如果当前为禁用状态
					this.btnCancelCancel.setVisible(true);// 启用按钮可见
					this.btnCancelCancel.setEnabled(true);// 启用按钮可用
					this.btnCancel.setEnabled(false);// 禁用按钮不可见
				}
				this.btnAddNew.setEnabled(true);
				this.btnEdit.setEnabled(true);
				this.menuItemAddNew.setEnabled(true);
				this.menuItemEdit.setEnabled(true);
				this.menuItemRemove.setEnabled(true);
			} else {
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnCancel.setVisible(false);
				this.btnCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
			if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
				this.btnAddNew.setEnabled(false);
				// this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnCancel.setVisible(false);
				this.btnCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}

		}
	}

	protected void initOldData(IObjectValue dataObject) {
		parentInfo = (ContractTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		if (getUIContext().get(UIContext.PARENTNODE) != null) {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
				// String strTemp =
				// ((ContractTypeInfo)getUIContext().get(UIContext.PARENTNODE)).getLongNumber();
				String strTemp = parentInfo.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				((ContractTypeInfo) dataObject).setLongNumber(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
				String strTemp = ((ContractTypeInfo) dataObject).getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				((ContractTypeInfo) dataObject).setLongNumber(strTemp);
			}
		}
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCancel_actionPerformed(e);
		// this.editData.setIsEnabled(false);
		// SelectorItemCollection selector = new SelectorItemCollection();
		// selector.add(new SelectorItemInfo("isEnabled"));
		// this.getBizInterface().updatePartial(this.editData, selector);
		if (this.editData != null && this.editData.getId() != null) {
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((IContractType) getBizInterface()).disEnabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
				loadFields();
				setSave(true);
				setSaved(true);
				// this.btnCancelCancel.setVisible(true);
				// this.btnCancelCancel.setEnabled(true);
				// this.btnCancel.setVisible(false);
				// this.chkIsEnabled.setSelected(true);
				// this.editData.setIsEnabled(false);

			}
		}
	}

	protected void showResultMessage(String message) {
		// setMessageText(EASResource.getString(message));
		setMessageText(message);
		// setMessageIcon(SHOW_MESSAGE_ICON_ERROR);
		// setMessageBgcolor(SHOW_MESSAGE_BG_ERROR);
		showMessage();
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCancelCancel_actionPerformed(e);
		// this.editData.setIsEnabled(true);
		// SelectorItemCollection selector = new SelectorItemCollection();
		// selector.add(new SelectorItemInfo("isEnabled"));
		// this.getBizInterface().updatePartial(this.editData, selector);
		if (this.editData != null && this.editData.getId() != null) {
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((IContractType) getBizInterface()).enabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
				loadFields();
				setSave(true);
				setSaved(true);
				// this.btnCancel.setVisible(true);
				// this.btnCancel.setEnabled(true);
				// this.btnCancelCancel.setVisible(false);
				// this.chkIsEnabled.setSelected(true);
				// this.editData.setIsEnabled(true);
			}
		}
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
			this.btnRemove.setEnabled(false);
		}
		this.chkIsCost.setEnabled(true);
	}
}