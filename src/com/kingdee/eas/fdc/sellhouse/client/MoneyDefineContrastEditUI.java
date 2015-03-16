/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.util.List;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUPartFIInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyAccountContrastEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastCollection;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastFactory;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyModeEnum;
import com.kingdee.eas.fdc.tenancy.client.TenancyClientHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MoneyDefineContrastEditUI extends AbstractMoneyDefineContrastEditUI {
	private static final Logger logger = CoreUIObject.getLogger(MoneyDefineContrastEditUI.class);
	private ItemAction[] hideAction = { this.actionEdit, this.actionAddNew, this.actionRemove, this.actionSave };
	private FullOrgUnitInfo fullOrgUnit = null;
	private int activeRowIndex = 0;
	private int activeColumnIndex = 0;
	private MoneyDefineCollection mdCol = null;
	TreePath curPath = null;

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}

	protected void inOnload() throws Exception {
		// super.inOnload();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		CoreBaseCollection msNewCol = new CoreBaseCollection();
		CoreBaseCollection msEditCol = new CoreBaseCollection();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() instanceof MoneySubjectContrastInfo) {
				// 对照表已有关联关系
				MoneySubjectContrastInfo mscEditInfo = (MoneySubjectContrastInfo) row.getUserObject();
				if (mscEditInfo != null) {
					if (Boolean.TRUE.equals(row.getCell("isChanged").getValue())) {
						mscEditInfo.setIsChanged(true);
					} else {
						if (row.getCell("accountView").getValue() == null || row.getCell("accountView").getValue() == "") {
							MsgBox.showConfirm2("不允许修改时,款项类型：" + row.getCell("moneyDefine").getValue() + "  应设置会计科目");
							this.tblMain.getEditManager().editCellAt(i, this.tblMain.getColumnIndex("accountView"));
							return;
						}
						mscEditInfo.setIsChanged(false);
					}

					if (row.getCell("accountView").getValue() != null && row.getCell("accountView").getValue() != "") {
						if (row.getCell("accountView").getValue() instanceof AccountViewInfo) {
							AccountViewInfo avInfo = (AccountViewInfo) row.getCell("accountView").getValue();
							if (avInfo != null) {
								mscEditInfo.setAccountView(avInfo);
							}

						} else {
							AccountViewInfo avInfo = (AccountViewInfo) row.getCell("accountView").getUserObject();
							if (avInfo != null) {
								mscEditInfo.setAccountView(avInfo);
							}
						}

					}
					MoneyAccountContrastEnum contrastSite = (MoneyAccountContrastEnum) row.getCell("contrastSite").getValue();
					if (contrastSite != null) {
						mscEditInfo.setContrastSide(contrastSite);
					}
					msEditCol.add(mscEditInfo);
				}

			} else {
				// 对照表没有关联关系
				MoneySubjectContrastInfo mscNewInfo = new MoneySubjectContrastInfo();
				FullOrgUnitInfo orgInfo = (FullOrgUnitInfo) row.getCell("org").getUserObject();
				if (orgInfo != null) {
					mscNewInfo.setFullOrgUnit(orgInfo);
				}
				MoneyDefineInfo moneyInfo = (MoneyDefineInfo) row.getCell("moneyDefine").getUserObject();
				if (moneyInfo != null) {
					mscNewInfo.setMoneyDefine(moneyInfo);
				}
				if (Boolean.TRUE.equals(row.getCell("isChanged").getValue())) {
					mscNewInfo.setIsChanged(true);
				} else {
					if (row.getCell("accountView").getValue() == null || row.getCell("accountView").getValue() == "") {
						MsgBox.showConfirm2("不允许修改时,款项类型：" + row.getCell("moneyDefine").getValue() + "  应设置会计科目");
						this.tblMain.getEditManager().editCellAt(i, this.tblMain.getColumnIndex("accountView"));
						return;
					}
					mscNewInfo.setIsChanged(false);
				}
				if (row.getCell("accountView").getValue() != null && row.getCell("accountView").getValue() != "") {
					AccountViewInfo avInfo = (AccountViewInfo) row.getCell("accountView").getValue();
					if (avInfo != null) {
						mscNewInfo.setAccountView(avInfo);
					} else {
						avInfo = (AccountViewInfo) row.getCell("accountView").getUserObject();
						mscNewInfo.setAccountView(avInfo);
					}
				}
				MoneyAccountContrastEnum contrastSite = (MoneyAccountContrastEnum) row.getCell("contrastSite").getValue();
				if (contrastSite != null) {
					mscNewInfo.setContrastSide(contrastSite);
				}
				msNewCol.add(mscNewInfo);
			}
		}
		if (msNewCol.size() > 0 || msEditCol.size() > 0) {
			try {
				if (msNewCol.size() > 0) {
					MoneySubjectContrastFactory.getRemoteInstance().addnew(msNewCol);
				}
				if (msEditCol.size() > 0) {
					MoneySubjectContrastFactory.getRemoteInstance().update(msEditCol);
				}
				super.showMessageForStatus();
				super.showSubmitSuccess();
				MsgBox.showInfo("款项科目对照表修改保存成功！");
				fillData(fullOrgUnit);
			} catch (EASBizException ex) {
				handleException(ex);
				ex.printStackTrace();
			} catch (BOSException ex) {
				handleException(ex);
				ex.printStackTrace();
			}
		} else {
			MsgBox.showInfo("没有设置会计科目或允许修改项，请检查！");
		}

	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MoneyDefineFactory.getRemoteInstance();
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() != 1) {
			return;
		}
		activeRowIndex = e.getRowIndex();
		activeColumnIndex = e.getColIndex();
		if (activeColumnIndex == this.tblMain.getColumnIndex("isChanged")) {
			ICell iCell = this.tblMain.getRow(activeRowIndex).getCell("isChanged");
			if (Boolean.TRUE.equals(iCell.getValue())) {
				iCell.setValue(Boolean.FALSE);
				this.tblMain.getRow(activeRowIndex).getCell("accountView").getStyleAttributes().setBackground(new Color(0xFCFBDF));
				this.tblMain.getEditManager().editCellAt(activeRowIndex, this.tblMain.getColumnIndex("accountView"));
			} else {
				iCell.setValue(Boolean.TRUE);
				this.tblMain.getRow(activeRowIndex).getCell("accountView").getStyleAttributes().setBackground(new Color(0xFFFFFF));

			}
		} else {
			return;
		}

	}

	/**
	 * output class constructor
	 */
	public MoneyDefineContrastEditUI() throws Exception {
		super();
	}

	private MoneyDefineCollection getMoneyDefineCollection() throws Exception {
		mdCol = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection();
		return mdCol;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
	}

	public void loadFields() {
		super.loadFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.getMoneyDefineCollection();
		FDCClientHelper.setActionVisible(hideAction, false);
		initControl();
		this.initTree();
		if (this.treeMain.getRowCount() > 0) {
			this.treeMain.setSelectionRow(0);
		}
		this.tblMain.getColumn("isChanged").getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("contrastSite").setEditor(createComboCellEditor(MoneyAccountContrastEnum.getEnumList()));

	}

	public static KDTDefaultCellEditor createComboCellEditor(List enumList) {
		KDComboBox comboField = new KDComboBox();
		for (int i = 0; i < enumList.size(); i++) {
			comboField.addItem(enumList.get(i));
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		return comboEditor;
	}

	private void initControl() {
		this.btnCopy.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.menuItemCopy.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.btnSubmit.setVisible(true);
		this.btnSubmit.setEnabled(true);
		this.menuItemSubmit.setVisible(true);
		this.menuItemSubmit.setEnabled(true);
		this.btnPrintPreview.setVisible(true);
		this.btnPrintPreview.setEnabled(true);
		this.btnPrint.setVisible(true);
		this.btnPrint.setEnabled(true);

		this.menuItemPrintPreview.setVisible(true);
		this.menuItemPrintPreview.setEnabled(true);

		this.menuItemPrint.setVisible(true);
		this.menuItemPrint.setEnabled(true);

		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnFirst.setVisible(false);
		this.actionAbout.setVisible(false);
		this.actionEdit.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuHelp.setVisible(true);
		this.menuBar.setVisible(true);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuSubmitOption.setVisible(false);
	}

	protected OrgUnitInfo getOrgUnitInfo() {
		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentFIUnit(); // 当前组织
		return crrOu;
	}

	protected void initTree() throws Exception {
		OrgUnitInfo fiOrg = getOrgUnitInfo();
		MetaDataPK dataPK = null;
		if (actionOnLoad != null) {
			String actoinName = actionOnLoad.getClass().getName();
			if (actoinName.indexOf("$") >= 0) {
				actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
			}
			dataPK = new MetaDataPK(actoinName);
		}
		this.treeMain.setModel(NewOrgUtils.getTreeModel(OrgViewType.COMPANY, "", fiOrg.getId().toString(), null, dataPK));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	protected KDTable getDetailTable() {
		return this.tblMain;
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (thisNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo orgStruct = (OrgStructureInfo) thisNode.getUserObject();
			if (orgStruct != null) {
				fullOrgUnit = orgStruct.getUnit();
				if (fullOrgUnit != null) {
					fillData(fullOrgUnit);
					this.setAccountViewInit(fullOrgUnit);
				}

			}
		}
	}

	private void setAccountViewInit(FullOrgUnitInfo fullOrg) {
		KDBizPromptBox paymentAccount = new KDBizPromptBox();
		paymentAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
		paymentAccount.setEditFormat("$number$");
		paymentAccount.setCommitFormat("$number$");
		paymentAccount.setDisplayFormat("$name$");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", fullOrg.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", new Integer(1)));
		OUPartFIInfo partFIInfo = fullOrg.getPartFI();
		if (partFIInfo != null) {
			if (partFIInfo.getAccountTable() != null) {
				filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", partFIInfo.getAccountTable().getId().toString()));
			}
		}
		view.setFilter(filter);
		paymentAccount.setEntityViewInfo(view);
		ICellEditor f7paymentAccountEditor = new KDTDefaultCellEditor(paymentAccount);
		this.tblMain.getColumn("accountView").setEditor(f7paymentAccountEditor);
		this.tblMain.getRow(activeRowIndex).getCell("isChanged").getStyleAttributes().setLocked(false);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		// preparePrintPage(tblMain);
		// this.setPrintSetting(tblMain);
		preparePrintPage(tblMain);
		tblMain.getPrintManager().print();
		// this.getPrintSetting(tblMain);
	}

	/**
	 * 打印预览
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		preparePrintPage(tblMain);
		// this.setPrintSetting(tblMain);
		tblMain.getPrintManager().printPreview();
		// this.getPrintSetting(tblMain);
	}
	
	private void fillData(FullOrgUnitInfo fullOrg) throws Exception {

		this.tblMain.removeRows();
		this.tblMain.checkParsed();

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("moneyDefine.*");
		sel.add("accountView.*");
		sel.add("fullOrgUnit.*");
		view.setSelector(sel);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("fullOrgUnit.id", fullOrg.getId().toString());
		view.setFilter(filter);
		MoneySubjectContrastCollection mscCol = new MoneySubjectContrastCollection();
		mscCol = MoneySubjectContrastFactory.getRemoteInstance().getMoneySubjectContrastCollection(view);
		for (int i = 0; i < mdCol.size(); i++) {
			MoneyDefineInfo mdInfo = mdCol.get(i);
			if (mdInfo != null) {
				IRow row = this.tblMain.addRow();
				row.getCell("org").setUserObject(fullOrg);
				row.getCell("moneyDefine").setUserObject(mdInfo);
				row.getCell("moneyDefine").setValue(mdInfo.getName());
				row.getCell("isChanged").setValue(Boolean.TRUE);
				for (int j = 0; j < mscCol.size(); j++) {
					MoneySubjectContrastInfo mscInfo = mscCol.get(j);
					if (mscInfo != null) {
						if (mscInfo.getMoneyDefine().getId().equals(mdInfo.getId())) {
							row.setUserObject(mscInfo);
							AccountViewInfo avInfo = mscInfo.getAccountView();
							if (avInfo != null) {
								row.getCell("accountView").setValue(avInfo.getName());
								row.getCell("accountView").setUserObject(avInfo);
								row.getCell("isChanged").getStyleAttributes().setLocked(false);
							}
							if (mscInfo.isIsChanged()) {
								row.getCell("isChanged").setValue(Boolean.TRUE);
								row.getCell("accountView").getStyleAttributes().setBackground(new Color(0xFFFFFF));
							} else {
								row.getCell("isChanged").setValue(Boolean.FALSE);
								row.getCell("accountView").getStyleAttributes().setBackground(new Color(0xFCFBDF));
							}
							MoneyAccountContrastEnum contrastSide = mscInfo.getContrastSide();
							if (contrastSide != null) {
								row.getCell("contrastSite").setValue(contrastSide);
							}
						}
					}
				}
			}
		}

	}
}