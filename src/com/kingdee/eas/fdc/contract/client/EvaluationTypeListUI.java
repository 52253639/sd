/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.contract.IEvaluationType;
import com.kingdee.eas.fdc.contract.EvaluationTypeCollection;
import com.kingdee.eas.fdc.contract.EvaluationTypeFactory;
import com.kingdee.eas.fdc.contract.EvaluationTypeInfo;
import com.kingdee.eas.fdc.contract.client.EvaluationTypeEditUI;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class EvaluationTypeListUI extends AbstractEvaluationTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(EvaluationTypeListUI.class);
    
    public EvaluationTypeListUI() throws Exception
    {
        super();
        this.btnQuery.setVisible(false);
		this.menuItemQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
    }
    protected ITreeBase getTreeInterface() throws Exception {
		return EvaluationTypeFactory.getRemoteInstance();
	}
	public void onLoad() throws Exception {
		FDCTableHelper.setColumnMoveable(tblMain, true);
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener() {
			public void afterDataFill(KDTDataRequestEvent e) {
				// do something
				String strTemp = "";
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					strTemp = tblMain.getRow(i).getCell(0).getValue().toString();
					strTemp = strTemp.replace('!', '.');
					tblMain.getRow(i).getCell(0).setValue(strTemp);
				}
			}
		});
		super.onLoad();
		this.btnEnabled.setIcon(EASResource.getIcon("imgTbtn_staruse"));
		this.btnDisEnabled.setIcon(EASResource.getIcon("imgTbtn_forbid"));
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.actionCancelCancel.setVisible(true);
			this.actionCancel.setVisible(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
		} else {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.actionCancelCancel.setVisible(false);
			this.actionCancel.setVisible(false);			
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
		if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled") != null) {
				boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue())
						.booleanValue();
				// 随着每一行规则的isEnabled的值改变，两个WBT的状态也改变
				changeWBTEnabeld(status);
			}
		} else {
			disabledWBT();

		}
	}

	private void changeWBTEnabeld(boolean isEnabled) {
		this.actionCancelCancel.setEnabled(!isEnabled);
		this.actionCancel.setEnabled(isEnabled);
	}
	private void disabledWBT() {
		this.actionCancelCancel.setEnabled(false);
		this.actionCancel.setEnabled(false);
	}
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof EvaluationTypeInfo) {
			EvaluationTypeInfo cti = (EvaluationTypeInfo) node.getUserObject();
			HashSet lnUps = new HashSet();
			try {
				EvaluationTypeCollection ctc = EvaluationTypeFactory.getRemoteInstance().getEvaluationTypeCollection(
						"select id where longNumber like '" + cti.getLongNumber() + "!%'");
				lnUps.add(cti.getId().toString());
				for (int i = 0; i < ctc.size(); i++) {
					lnUps.add(ctc.get(i).getId().toString());
				}
			} catch (BOSException e1) {
				handUIException(e1);
			}
			FilterInfo filterInfo = new FilterInfo();
			if (lnUps.size() != 0) {
				filterInfo.getFilterItems().add(new FilterItemInfo("id", lnUps, CompareType.INCLUDE));
				this.mainQuery.setFilter(filterInfo);
			}
		} else {
			this.mainQuery.setFilter(new FilterInfo());
		}

		FilterInfo filter = (FilterInfo) this.getUIContext().get("F7Filter");
		if (filter != null) {
			this.mainQuery.getFilter().mergeFilter(filter, "and");
		}
		tblMain.removeRows();// 触发新查询
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
//		EvaluationTypeInfo cti = null;
//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
//		if (node == null) {
//			return;
//		} else {
//			if (node.getUserObject() instanceof EvaluationTypeInfo) {
//				cti = (EvaluationTypeInfo) node.getUserObject();
//			}
//		}
//		if (cti != null) {
//			if ((!this.isHasSubLevel(cti)) && this.isReferenced(cti)) {
//				if (!MsgBox.isYes(MsgBox.showConfirm2(this,"当前父类型已被引用，此操作将转移对应数据到新增子类型，是否继续？"))) {
//					// 不继续
//					return;
//				}
//			}
//		}
		super.actionAddNew_actionPerformed(e);
	}
	private boolean isHasSubLevel(EvaluationTypeInfo cti) throws EASBizException, BOSException {
		boolean flag = true;
		if (cti != null) {
			flag = EvaluationTypeFactory.getRemoteInstance().exists("select id where parent.id = '" + cti.getId().toString() + "'");
		}
		return flag;
	}
	private boolean isReferenced(EvaluationTypeInfo cti) throws EASBizException, BOSException {
		boolean flag = false;
//		if (cti != null) {
//			flag = NewPlanIndexFactory.getRemoteInstance().exists("select id where config.id = '" + cti.getId().toString() + "'");
//		}
		return flag;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		this.verifyNotAccepted(this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()));
		super.actionRemove_actionPerformed(e);
	}

	protected String getEditUIName() {
		return EvaluationTypeEditUI.class.getName();
	}
	protected String getRootName() {
		return "评估类型";
	}
	// 支持编码、名称的定位功能
	protected String[] getLocateNames()
    {
        String[] locateNames = new String[2];
        locateNames[0] = IFWEntityStruct.tree_LongNumber;
        locateNames[1] = IFWEntityStruct.dataBase_Name;
        return locateNames;
    }
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		IEvaluationType iEvaluationType = EvaluationTypeFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		if (iEvaluationType.enabled(pk)) {
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			showMessage();
			tblMain.removeRows();// 触发新查询
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(true);
		}
    }
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
        IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		IEvaluationType iEvaluationType = EvaluationTypeFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		if (iEvaluationType.disEnabled(pk)) {
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			showMessage();
			tblMain.removeRows();// 触发新查询
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(true);
		}
        
    }
	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}
	private void verifyNotAccepted(IRow row) {
		if (row.getCell("CU.id") != null && row.getCell("CU.id").getValue() != null
				&& (OrgConstants.SYS_CU_ID.equals(row.getCell("CU.id").getValue().toString()))) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Delete_SysData"));
			SysUtil.abort();
		}
	}
	private boolean verifySysDataEdit() {
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			return false;
		} else {
			int i = this.tblMain.getSelectManager().getActiveRowIndex();
			if (OrgConstants.SYS_CU_ID.equals(this.tblMain.getRow(i).getCell("CU.id").getValue().toString())) {
				MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Update_SysData"));
				SysUtil.abort();
				return false;
			} else {
				return true;
			}
		}
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("isCost"));
		sic.add(new SelectorItemInfo("payScale"));
		sic.add(new SelectorItemInfo("dutyOrgUnit.name"));
		sic.add(new SelectorItemInfo("stampTaxRate"));
		sic.add(new SelectorItemInfo("parent.*"));
		return sic;
	}

	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		}
		return UIFactoryName.MODEL;
	}
	protected boolean isShowAttachmentAction() {
		return true;
	}
}