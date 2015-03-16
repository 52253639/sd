/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.sellhouse.BooleanEnum;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeEntryFactory;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CompensateSchemeEntryEditUI extends
		AbstractCompensateSchemeEntryEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CompensateSchemeEntryEditUI.class);

	private boolean isOk = false;

	/**
	 * output class constructor
	 */
	public CompensateSchemeEntryEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected IObjectValue createNewData() {
		CompensateSchemeEntryInfo objectValue = new CompensateSchemeEntryInfo();
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CompensateSchemeEntryFactory.getRemoteInstance();
	}

	protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		FDCClientVerifyHelper.verifyRequire(this);
		if(this.txtMinValue.getValue() == null || this.txtMinValue.getValue().toString().length()<1)
		{
			this.txtMinValue.requestFocus(true);
			MsgBox.showInfo("请填写最小值！");
			return;
		}
		if(this.txtMaxValue.getValue() == null||this.txtMaxValue.getValue().toString().length()<1)
		{
			this.txtMaxValue.requestFocus(true);
			MsgBox.showInfo("请填写最大值！");
			return;
		}

		Object content = cmbMinValueCompare.getSelectedItem();
	    if(content == null){
	    	cmbMinValueCompare.requestFocus(true);
	    	MsgBox.showInfo("请选择最小值比较符！");
			return;
	    }
	    content = cmbMaxValueCompare.getSelectedItem();
	    if(content == null){
	    	cmbMaxValueCompare.requestFocus(true);
	    	MsgBox.showInfo("请选择最大值比较符！");
			return;
	    }
	    
		CompensateSchemeEntryInfo thisInfo = getSchemeEntry();
		if (thisInfo.getMaxValue().compareTo(thisInfo.getMinValue()) == -1) {
			MsgBox.showError(this, "计算条件的最大值小于了最小值，请修改！");
			SysUtil.abort();
		}
		if (getUIContext().get("compareColl") != null) {
			CompensateSchemeEntryCollection coll = (CompensateSchemeEntryCollection) getUIContext()
					.get("compareColl");
			for (Iterator it = coll.iterator(); it.hasNext();) {
				CompensateSchemeEntryInfo compareInfo = (CompensateSchemeEntryInfo) it
						.next();
				checkContains(compareInfo, thisInfo);
			}
		}
		setConfirm(true);
	}

	private void checkContains(CompensateSchemeEntryInfo entry1,
			CompensateSchemeEntryInfo entry2) throws BOSException,
			EASBizException {
		BigDecimal minValue1 = entry1.getMinValue();
		SHECompareTypeEnum minCompareType1 = entry1.getMinCompareType();
		BigDecimal maxValue1 = entry1.getMaxValue();
		SHECompareTypeEnum maxCompareType1 = entry1.getMaxCompareType();

		BigDecimal minValue2 = entry2.getMinValue();
		SHECompareTypeEnum minCompareType2 = entry2.getMinCompareType();
		BigDecimal maxValue2 = entry2.getMaxValue();
		SHECompareTypeEnum maxCompareType2 = entry2.getMaxCompareType();

		if (minValue2.compareTo(minValue1) != 0
				&& minValue2.compareTo(maxValue1) != 0) {
			if (minValue2.compareTo(minValue1) == 1
					&& minValue2.compareTo(maxValue1) == -1) {
				MsgBox.showError(this, "计算条件跟之前定义的方案明细的计算条件有交叉重复！");
				SysUtil.abort();
			}
			if (maxValue2.compareTo(minValue1) != 0
					&& maxValue2.compareTo(maxValue1) != 0) {
				if (minValue2.compareTo(minValue1) == -1
						&& maxValue2.compareTo(maxValue1) == 1) {
					MsgBox.showError(this, "计算条件跟之前定义的方案明细的计算条件有交叉重复！");
					SysUtil.abort();
				}
			}
		} else if (minValue2.compareTo(minValue1) == 0) {
			if ((SHECompareTypeEnum.EQUAL.equals(minCompareType2) || SHECompareTypeEnum.LESSEQUAL
					.equals(minCompareType2))
					&& (minCompareType1.equals(SHECompareTypeEnum.EQUAL) || minCompareType1
							.equals(SHECompareTypeEnum.LESSEQUAL))) {
				MsgBox.showError(this, "计算条件跟之前定义的方案明细的计算条件有交叉重复！");
				SysUtil.abort();
			}
		} else if (minValue2.compareTo(maxValue1) == 0) {
			if ((SHECompareTypeEnum.EQUAL.equals(minCompareType2) || SHECompareTypeEnum.LESSEQUAL
					.equals(minCompareType2))
					&& (SHECompareTypeEnum.EQUAL.equals(maxCompareType1) || SHECompareTypeEnum.LESSEQUAL
							.equals(maxCompareType1))) {
				MsgBox.showError(this, "计算条件跟之前定义的方案明细的计算条件有交叉重复！");
				SysUtil.abort();
			}
		}

		if (maxValue2.compareTo(minValue1) != 0
				&& maxValue2.compareTo(maxValue1) != 0) {
			if (maxValue2.compareTo(minValue1) == 1
					&& maxValue2.compareTo(maxValue1) == -1) {
				MsgBox.showError(this, "计算条件跟之前定义的方案明细的计算条件有交叉重复！");
				SysUtil.abort();
			}
		} else if (maxValue2.compareTo(minValue1) == 0) {
			if ((SHECompareTypeEnum.EQUAL.equals(maxCompareType2) || SHECompareTypeEnum.LESSEQUAL
					.equals(maxCompareType2))
					&& (SHECompareTypeEnum.EQUAL.equals(minCompareType1) || SHECompareTypeEnum.LESSEQUAL
							.equals(minCompareType1))) {
				MsgBox.showError(this, "计算条件跟之前定义的方案明细的计算条件有交叉重复！");
				SysUtil.abort();
			}
		} else if (maxValue2.compareTo(maxValue1) == 0) {
			if ((SHECompareTypeEnum.EQUAL.equals(maxCompareType2) || SHECompareTypeEnum.LESSEQUAL
					.equals(maxCompareType2))
					&& (SHECompareTypeEnum.EQUAL.equals(maxCompareType1) || SHECompareTypeEnum.LESSEQUAL
							.equals(maxCompareType1))) {
				MsgBox.showError(this, "计算条件跟之前定义的方案明细的计算条件有交叉重复！");
				SysUtil.abort();
			}
		}
	}

	/**
	 * output btnCancel_actionPerformed method
	 */
	protected void btnCancle_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		setConfirm(false);
		disposeUIWindow();
	}

	public void setConfirm(boolean isOk) {
		this.isOk = isOk;
		disposeUIWindow();
	}

	public boolean isOk() {
		return isOk;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionAttachment, actionPrint, actionPrintPreview, actionSave,
				actionCopy, actionPre, actionNext, actionFirst, actionLast,
				actionSubmit, actionCancel, actionCancelCancel }, false);
		if (getUIContext().get("seq") != null)
			txtSeq.setText(getUIContext().get("seq").toString());
		txtNumber.setText(getUIContext().get("number").toString());
		txtName.setText(getUIContext().get("name").toString());
		prmtSellProject.setValue(getUIContext().get("sellProject"));
		txtMinValue.setHorizontalAlignment(JTextField.RIGHT);
		txtMinValue.setRemoveingZeroInDispaly(false);
		txtMinValue.setPrecision(2);
		txtMinValue.setMinimumValue(FDCHelper._ONE_HUNDRED);
		txtMinValue.setSupportedEmpty(true);

		txtMaxValue.setHorizontalAlignment(JTextField.RIGHT);
		txtMaxValue.setRemoveingZeroInDispaly(false);
		txtMaxValue.setPrecision(2);
		txtMaxValue.setMinimumValue(FDCHelper._ONE_HUNDRED);
		txtMaxValue.setSupportedEmpty(true);

		txtFactor.setHorizontalAlignment(JTextField.RIGHT);
		txtFactor.setRemoveingZeroInDispaly(false);
		txtFactor.setPrecision(2);
		txtFactor.setSupportedEmpty(true);
		
		//cmbMinValueCompare.setSelectedItem(SHECompareTypeEnum.LESS);
	//	cmbMaxValueCompare.setSelectedItem(SHECompareTypeEnum.LESS);
		
		//给 是否补差 框设置值改变事件
		
		this.comboIsCompensate.addItemListener(new ItemListener()
				{

					public void itemStateChanged(ItemEvent arg0)
					{
						if(arg0.getItem().equals(BooleanEnum.NO))
								{
							          txtFactor.setValue(FDCHelper.ZERO);
							          txtFactor.setEnabled(false);
								}
						else
						{
					          txtFactor.setEnabled(true);
						}
						
					}
			
				});
		
	}
	
	public void onShow() throws Exception
	{
		super.onShow();
		if (this.comboIsCompensate.getSelectedItem() != null)
		{
			if (this.comboIsCompensate.getSelectedItem().equals(
					BooleanEnum.NO))
			{
				txtFactor.setValue(FDCHelper.ZERO);
				txtFactor.setEnabled(false);
			} else
			{
				txtFactor.setEnabled(true);
			}
		}
		
	}
	

	public CompensateSchemeEntryInfo getSchemeEntry() throws Exception {
		CompensateSchemeEntryInfo model = this.editData;
		model.setMaxValue(txtMaxValue.getBigDecimalValue());
		model.setMinValue(txtMinValue.getBigDecimalValue());
		model.setMaxCompareType((SHECompareTypeEnum) cmbMaxValueCompare
				.getSelectedItem());
		model.setMinCompareType((SHECompareTypeEnum) cmbMinValueCompare
				.getSelectedItem());
		model.setFactor(txtFactor.getBigDecimalValue());
		model.setDescription(txtDescription.getText());
		model
				.setIsCompensate((BooleanEnum) comboIsCompensate
						.getSelectedItem());
		model.setCalcTerms(model.getMinValue().toString()
				+ model.getMinCompareType() + "差异率" + model.getMaxCompareType()
				+ model.getMaxValue().toString());
		model.setCalcFormula("（面积差*成交单价）*" + model.getFactor().toString());
		return model;
	}

	public void initUIToolBarLayout() {

	}
	
}