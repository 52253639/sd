/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAbstractAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.invite.HeadTypeFactory;
import com.kingdee.eas.fdc.invite.IHeadType;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class HeadTypeListUI extends AbstractHeadTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(HeadTypeListUI.class);
    public OrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();
    /**
     * output class constructor
     */
    public HeadTypeListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected ITreeBase getTreeInterface() throws Exception {
		return HeadTypeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return HeadTypeEditUI.class.getName();
	}

//	protected ICoreBase getBizInterface() throws Exception {
//		return HeadTypeFactory.getRemoteInstance();
//	}

	protected String getEditUIModal()
    {
        // return UIFactoryName.MODEL;
        // return UIFactoryName.NEWWIN;
        // 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
        String openModel = UIConfigUtility.getOpenModel();
        if (openModel != null)
        {
            return openModel;
        }
        else
        {
            return UIFactoryName.MODEL;
        }
    }

	public void onLoad() throws Exception {
		if (currentOrg == null || !currentOrg.isIsCompanyOrgUnit()) {
			MsgBox.showWarning(this, "非财务组织不能进入!");
			abort();
		}

		this.setIncludeAllChildren(true);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.tblMain.getDataRequestManager().addDataFillListener(
				new KDTDataFillListner());
		super.onLoad();
		this.tblMain.checkParsed();
		this.tblMain.getColumn("longNumber").setWidth(200);
		if (this.tblMain.getColumn("org.name") != null) {
			this.tblMain.getColumn("org.name").setWidth(200);
		}
		this.tblMain.getStyleAttributes().setLocked(false);
		if(OrgConstants.DEF_CU_ID.equals(currentOrg.getId().toString())){
			actionAddNew.setEnabled(true);
			actionEdit.setEnabled(true);
			actionRemove.setEnabled(true);
		}else{
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		}
//		ActionMap actionMap = this.tblMain.getActionMap();
//		actionMap.put(KDTAction.DELETE, new KDTDeleteAction(tblMain));
//		this.kDWorkImport.setVisible(false);
	}

	class KDTDataFillListner implements KDTDataFillListener {
		public void afterDataFill(KDTDataRequestEvent e) {
			// do something
			String strTemp = "";
			CellTreeNode node = null;
			for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
				IRow row = tblMain.getRow(i);
				Object objLongNum = row.getCell("longNumber").getValue();
				if (objLongNum == null)
					continue;

				strTemp = objLongNum.toString();
				strTemp = strTemp.replace('!', '.');
				// 列里面加树状
				node = new CellTreeNode();
				node.setValue(strTemp);

				int level = Integer.parseInt(row.getCell("level").getValue()
						.toString()) - 1;
				boolean isLeaf = ((Boolean) row.getCell("isLeaf").getValue())
						.booleanValue();
				node.setTreeLevel(level);
				if (isLeaf) {
					node.setCollapse(false);
					node.setHasChildren(false);
				} else {
//					if (level == 0) {
						node.setCollapse(false);
//					} else {
//						node.setCollapse(false);
//					}
					node.setHasChildren(true);
				}
				row.getCell("longNumber").setValue(node);
			}
		}
	}

	protected String getRootName() {
		return "全部类型";
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected boolean isIgnoreTreeCUFilter() {
		return true;
	}

	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		KDTable table = this.tblMain;
		int[] rows = KDTableUtil.getSelectedRows(this.tblMain);
		boolean deleteEnabled = true;
		String currentOrgId = currentOrg.getId().toString();
		for (int i = 0; i < rows.length; i++) {
			IRow row = table.getRow(rows[i]);
			// row.getCell("org.id")!=null给F7用
			// row.getCell("org.id").getValue() == null 是为了照顾以前没加组织的数据
			ICell cellOrgId = row.getCell("org.id");
			if (cellOrgId != null && cellOrgId.getValue() != null
					&& !currentOrgId.equals(cellOrgId.getValue().toString())) {
				deleteEnabled = false;
				break;
			}
		}
		if (deleteEnabled) {
			this.actionRemove.setEnabled(true);
		} else {
			this.actionRemove.setEnabled(false);
		}
		if (rows.length > 0) {
			IRow row = table.getRow(rows[0]);
			ICell cellOrgId = row.getCell("org.id");
			if (cellOrgId == null || cellOrgId.getValue() == null
					|| currentOrgId.equals(cellOrgId.getValue().toString())) {
				this.actionEdit.setEnabled(true);
				// this.actionView.setEnabled(true);
			} else {
				this.actionEdit.setEnabled(false);
//				this.actionView.setEnabled(false);
			}
		}
	}

//	 是否需要进行表格排序。
	protected boolean isOrderForClickTableHead() {
		return false;
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		String id = getSelectedKeyValue();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(!node.isRoot()){
			String tables[] = new String[] { "T_INV_NewListTempletPage",
					"T_INV_NewListingPage","T_INV_ListingItem" };
			Object[] newTables = HeadTypeFactory.getRemoteInstance()
					.getRelateData(id, tables);
			if (newTables.length > 0) {
				MsgBox.showWarning("当前表头类型已经被引用,不能在它下面添加明细节点!");
				return;
			}
		}
		super.actionAddNew_actionPerformed(e);
	}

	static class KDTDeleteAction extends KDTAbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2412854206322758237L;

		public KDTDeleteAction(KDTable table) {
			super(table);
		}

		public void actionPerformed(ActionEvent arg0) {

		}

	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList list = getSelectedIdValues();
		int IDSize = list.size();
		String tables[] = new String[] { "T_INV_NewListTempletPage",
				"T_INV_NewListingPage", "T_INV_ListingItem" };
		IHeadType iHeadType = HeadTypeFactory.getRemoteInstance();
		for (int i = 0; i < IDSize; i++) {
			String id = list.get(i).toString();
			if (iHeadType.exists("select id where parent.id = '" + id + "'")) {
				MsgBox.showWarning(this, "有明细节点，请先删除明细数据!");
				return;
			}
			Object[] newTables = iHeadType.getRelateData(id, tables);
			if (newTables.length > 0) {
				MsgBox.showWarning("表头已经被引用,不能删除!");
				return;
			}
		}
		super.actionRemove_actionPerformed(e);
	}
}