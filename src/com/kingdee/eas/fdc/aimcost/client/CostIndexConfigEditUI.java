/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Dimension;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigFactory;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryTypeEnum;
import com.kingdee.eas.fdc.aimcost.FieldTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CostIndexConfigEditUI extends AbstractCostIndexConfigEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostIndexConfigEditUI.class);
    public CostIndexConfigEditUI() throws Exception
    {
        super();
    }
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception{
    	IRow row = this.kdtEntry.addRow();
		row.setUserObject(new CostIndexConfigEntryInfo());
		row.getCell("isHide").setValue(Boolean.FALSE);
		row.getCell("isRequired").setValue(Boolean.FALSE);
    }
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception{
    	insertLine(this.kdtEntry);
    }
    protected final boolean isTableColumnSelected(KDTable table){
    	if(table.getSelectManager().size() > 0){
    		KDTSelectBlock block = table.getSelectManager().get();
    		if(block.getMode() == 4 || block.getMode() == 8)
    			return true;
        }
    	return false;
    }
    protected void insertLine(KDTable table) {
		if (table == null) {
			return;
		}
		IRow row = null;
		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();
			if (isTableColumnSelected(table)) {
				row = table.addRow();
				row.setUserObject(new CostIndexConfigEntryInfo());
				row.getCell("isHide").setValue(Boolean.FALSE);
				row.getCell("isRequired").setValue(Boolean.FALSE);
			} else {
				row = table.addRow(top);
				row.setUserObject(new CostIndexConfigEntryInfo());
				row.getCell("isHide").setValue(Boolean.FALSE);
				row.getCell("isRequired").setValue(Boolean.FALSE);
			}
		} else {
			row = table.addRow();
			row.setUserObject(new CostIndexConfigEntryInfo());
			row.getCell("isHide").setValue(Boolean.FALSE);
			row.getCell("isRequired").setValue(Boolean.FALSE);
		}
	}
    protected void removeLine(KDTable table) {
		if (table == null) {
			return;
		}
		if ((table.getSelectManager().size() == 0)){
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
			return;
		}
		if (confirmRemove()) {
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null){
				return;
			}
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				table.removeRow(rowIndex);
			}
			if (table.getRow(0) != null){
				table.getSelectManager().select(0, 0);
			}
		}
	}
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception{
    	removeLine(this.kdtEntry);
    }
	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}
	protected KDBizMultiLangBox getNameCtrl() {
		return this.txtName;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		CostIndexConfigInfo info=new CostIndexConfigInfo();
		info.setIsEnabled(true);
		info.setInviteType((InviteTypeInfo)this.getUIContext().get("inviteType"));
		info.setEntryType(null);
		return info;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return CostIndexConfigFactory.getRemoteInstance();
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbEntryType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtInviteType);
		
		if(this.kdtEntry.getRowCount()>50){
			FDCMsgBox.showWarning(this,"字段数量限制为50字段！");
			SysUtil.abort();
		}
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			String fieldName=(String)this.kdtEntry.getRow(i).getCell("fieldName").getValue();
			FieldTypeEnum fieldType=(FieldTypeEnum)this.kdtEntry.getRow(i).getCell("fieldType").getValue();
			if(fieldName==null||"".equals(fieldName.trim())){
				FDCMsgBox.showWarning(this,"字段名称不能为空！");
				this.kdtEntry.getEditManager().editCellAt(i, this.kdtEntry.getColumnIndex("fieldName"));
				SysUtil.abort();
			}
			if(fieldType==null){
				FDCMsgBox.showWarning(this,"字段类型不能为空！");
				this.kdtEntry.getEditManager().editCellAt(i, this.kdtEntry.getColumnIndex("fieldType"));
				SysUtil.abort();
			}
		}
		super.verifyInput(e);
	}
	public void storeFields() {
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			if(this.kdtEntry.getRow(i).getCell("fieldType").getValue()!=null
					&&this.kdtEntry.getRow(i).getCell("fieldType").getValue() instanceof String){
				boolean isExist=false;
				for(int j=0;j<FieldTypeEnum.getEnumList().size();j++){
					if(((FieldTypeEnum)FieldTypeEnum.getEnumList().get(j)).getAlias().equals(this.kdtEntry.getRow(i).getCell("fieldType").getValue().toString())){
						this.kdtEntry.getRow(i).getCell("fieldType").setValue(FieldTypeEnum.getEnumList().get(j));
						isExist=true;
						break;
					}
				}
				if(!isExist){
					this.kdtEntry.getRow(i).getCell("fieldType").setValue(null);
				}
			}
		}
		super.storeFields();
	}
	public void onLoad() throws Exception {
		this.kdtEntry.checkParsed();
		KDCheckBox hit = new KDCheckBox();
		hit.setSelected(false);
 		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(hit);
 		this.kdtEntry.getColumn("isHide").setEditor(editor);
 		this.kdtEntry.getColumn("isRequired").setEditor(editor);
 		
 		KDComboBox combo = new KDComboBox();
        for(int i = 0; i < FieldTypeEnum.getEnumList().size(); i++){
        	combo.addItem(FieldTypeEnum.getEnumList().get(i));
        }
        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.kdtEntry.getColumn("fieldType").setEditor(comboEditor);
		
		super.onLoad();
		this.txtDescription.setMaxLength(255);
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		this.cbEntryType.setRequired(true);
		this.prmtInviteType.setRequired(true);
		
		KDWorkButton addLine=new KDWorkButton();
		this.actionAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		addLine = (KDWorkButton)this.contEntry.add(this.actionAddLine);
		addLine.setText("新增行");
		addLine.setSize(new Dimension(140, 19));
		
		KDWorkButton insertLine=new KDWorkButton();
		this.actionInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		insertLine = (KDWorkButton)this.contEntry.add(this.actionInsertLine);
		insertLine.setText("插入行");
		insertLine.setSize(new Dimension(140, 19));
		
		KDWorkButton removeLine=new KDWorkButton();
		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		removeLine = (KDWorkButton)this.contEntry.add(this.actionRemoveLine);
		removeLine.setText("删除行");
		removeLine.setSize(new Dimension(140, 19));
		
		this.actionAttachment.setEnabled(true);
	}
	protected boolean isShowAttachmentAction(){      
		return true;
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
		}
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel=super.getSelectors();
		sel.add("isEnabled");
		return sel;
	}
	
}