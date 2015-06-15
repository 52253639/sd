/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.contract.EvaluationResultInfo;
import com.kingdee.eas.fdc.contract.EvaluationTypeEntryInfo;
import com.kingdee.eas.fdc.contract.IEvaluationType;
import com.kingdee.eas.fdc.contract.EvaluationTypeCollection;
import com.kingdee.eas.fdc.contract.EvaluationTypeFactory;
import com.kingdee.eas.fdc.contract.EvaluationTypeInfo;
import com.kingdee.eas.fdc.contract.PayContentTypeInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFieldTypeEnum;
import com.kingdee.eas.fdc.aimcost.PlanIndexFormulaTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.PayContentTypeEntryInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.tools.datatask.DIETemplateInfo;
import com.kingdee.eas.tools.datatask.client.PropertySetUI;
import com.kingdee.eas.tools.datatask.client.UIUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class EvaluationTypeEditUI extends AbstractEvaluationTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(EvaluationTypeEditUI.class);
    public EvaluationTypeEditUI() throws Exception
    {
        super();
    }
    private EvaluationTypeInfo parentInfo;

	private String parentNumber = null;

	private String strTemp = null;

	protected IObjectValue createNewData() {
		EvaluationTypeInfo info=new EvaluationTypeInfo();
		return info;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return EvaluationTypeFactory.getRemoteInstance();
	}
	public void loadFields() {
		super.loadFields();
		parentInfo = (EvaluationTypeInfo) getUIContext().get(UIContext.PARENTNODE);
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
		Object entry[] = new Object[editData.getEntry().size()];
		for(int i = 0; i < editData.getEntry().size(); i++)
			entry[i] = editData.getEntry().get(i).getResult();
		this.prmtEntry.setValue(entry);
	}
	  public void storeFields()
      {
		  editData.getEntry().clear();
		  if(prmtEntry.getValue() != null){
			  Object value[] = (Object[])prmtEntry.getValue();
			  for(int i = 0; i < value.length; i++)
				  if(value[i] != null && (value[i] instanceof EvaluationResultInfo)){
					  EvaluationTypeEntryInfo entry = new EvaluationTypeEntryInfo();
					  entry.setResult((EvaluationResultInfo)value[i]);
					  editData.getEntry().add(entry);
                  }
		  }
		  super.storeFields();
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
//			FDCBaseTypeValidator.validate(((EvaluationTypeListUI) getUIContext().get(UIContext.OWNER)).getMainTable(), txtLongNumber, bizName, getSelectBOID());
		
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
		FDCBaseDataClientUtils.setupUITitle(this, "合同流程类型");
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
		setBtnStatus();
		if (parentInfo != null && parentInfo.isIsEnabled()) {
			this.chkIsEnabled.setSelected(true);
		}
		txtLongNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
				EvaluationTypeInfo parent = (EvaluationTypeInfo) getUIContext().get(UIContext.PARENTNODE);
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
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtEntry.setEntityViewInfo(view);
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("parent.*"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("entry.result.*"));
		return sic;
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
		parentInfo = (EvaluationTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		if (getUIContext().get(UIContext.PARENTNODE) != null) {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
				String strTemp = parentInfo.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				((EvaluationTypeInfo) dataObject).setLongNumber(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
				String strTemp = ((EvaluationTypeInfo) dataObject).getLongNumber();
				strTemp = strTemp.replace('!', '.');
				((EvaluationTypeInfo) dataObject).setLongNumber(strTemp);
			}
		}
	}
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData != null && this.editData.getId() != null) {
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((IEvaluationType) getBizInterface()).disEnabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
				loadFields();
				setSave(true);
				setSaved(true);
			}
		}
	}

	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData != null && this.editData.getId() != null) {
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((IEvaluationType) getBizInterface()).enabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
				loadFields();
				setSave(true);
				setSaved(true);
			}
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
			this.btnRemove.setEnabled(false);
		}
	}
}