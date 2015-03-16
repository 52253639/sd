/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAbstractAction;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.TreePathUtil;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 *
 * 描述:采购类别列表页面
 *
 * @author 肖飙彪:2007-4-25
 *         <p>
 * @version EAS5.3
 */
public class InviteTypeListUI extends AbstractInviteTypeListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(InviteTypeListUI.class);

	public OrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();

	public static final String resourcePath = "com.kingdee.eas.fdc.invite.FDCInviteResource";

	/**
	 * output class constructor
	 */
	public InviteTypeListUI() throws Exception {
		super();
		
	}

	// 是否需要进行表格排序。
	protected boolean isOrderForClickTableHead() {
		return true;
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		String id = getSelectedKeyValue();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		
		
		if(node.isLeaf()){
			String tables[] = new String[] { "T_INV_NewListing",
					"T_INV_NewListTemplet" , "T_INV_InviteProject"};
			Object[] newTables = InviteTypeFactory.getRemoteInstance()
					.getRelateData(id, tables);
			if (newTables.length > 0) {
				MsgBox.showWarning("所选采购类别已经被引用,不能执行此操作");
				return;
			}
		}
		super.actionAddNew_actionPerformed(e);
		
		refreshAfterAddNew();
	}

	/**
	 * 因为actionAddNew_actionPerformed新增调用refresh不能刷新左边树，自定义新增后另类刷新，
	 * @author owen_wen 2010-11-24
	 * @throws Exception 
	 */
	private void refreshAfterAddNew() throws Exception{
        TreePath oldPath = treeMain.getSelectionPath();
        this.initTree();
        TreePath path=TreePathUtil.getNewTreePath(treeMain , treeMain.getModel() , oldPath);
        if (path!=null)
        {
        	treeMain.setSelectionPath(path);
        }else
        {
        	treeMain.setSelectionRow(0);
        }
	}


	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList list = getSelectedIdValues();
		
		//系统预设不能删除
		if(tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("org.id").getValue() != null)
		{
			String orgId = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("org.id").getValue().toString();
			if(OrgConstants.SYS_CU_ID.equals(orgId))
			{
				MsgBox.showWarning(this, "系统预设数据不能删除");
				SysUtil.abort();
			
			}
		}
	 
		int IDSize = list.size();
		if(IDSize == 1){
			String id = getSelectedKeyValue();
			String tables[] = new String[] { "T_INV_NewListing",
					"T_INV_NewListTemplet", "T_INV_InviteProject"};
			
			if (getTreeInterface().exists("select * where parent.id='"+id+"'")){
				MsgBox.showWarning(this, "明细类别已经有数据，不能执行此操作");
				return;
			}
			
			Object[] newTables = InviteTypeFactory.getRemoteInstance()
					.getRelateData(id, tables);
			
			if (newTables.length > 0) {
				MsgBox.showWarning("所选采购类别已经被引用,不能执行此操作");
				return;
			}
		
			
			
		}else{
			for(int i=0; i<IDSize; i++){
				String id = list.get(i).toString();
				String tables[] = new String[] { "T_INV_NewListing",
				"T_INV_NewListTemplet" };
				Object[] newTables = InviteTypeFactory.getRemoteInstance()
				.getRelateData(id, tables);
				if (newTables.length > 0) {
					MsgBox.showWarning("所选采购类别已经被引用,不能执行此操作");
					return;
				}
			}
		}	

		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)throws Exception {
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
//			this.actionRemove.setEnabled(true);
//			this.actionEdit.setEnabled(true);
			
//			this.btnAddNew.setEnabled(true);
//			this.btnEdit.setEnabled(true);
//			this.btnRemove.setEnabled(true);

//			this.menuItemAddNew.setEnabled(true);
//			this.menuItemEdit.setEnabled(true);
//			this.menuItemRemove.setEnabled(true);
		} else {

//			this.btnAddNew.setEnabled(true);
//			this.btnEdit.setEnabled(true);
//			this.btnRemove.setEnabled(false);

//			this.menuItemAddNew.setEnabled(true);
//			this.menuItemEdit.setEnabled(true);
//			this.menuItemRemove.setEnabled(false);
			
//			this.actionRemove.setEnabled(false);
////			this.actionEdit.setEnabled(false);
		}

//		if (rows.length > 0) {
//			IRow row = table.getRow(rows[0]);
//			if (row.getCell("org.id") == null
//					|| row.getCell("org.id").getValue() == null
//					|| SysContext
//							.getSysContext()
//							.getCurrentOrgUnit()
//							.getId()
//							.toString()
//							.equals(row.getCell("org.id").getValue().toString())) {
////				this.actionEdit.setEnabled(true);
//			} else {
//				this.actionEdit.setEnabled(false);
//			}
//		}

	}

	protected boolean isIgnoreCUFilter() {
		// TODO 自动生成方法存根
		return true;
	}

	protected boolean isIgnoreTreeCUFilter() {
		// TODO 自动生成方法存根
		return true;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return InviteTypeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return InviteTypeEditUI.class.getName();
	}

	// 重写返回“根节点”名称的方法
	protected String getRootName() {
		return "全部类别";
	}

	static class KDTDeleteAction extends KDTAbstractAction {

		/**
		 *
		 */
		private static final long serialVersionUID = 528704412813139367L;

		public KDTDeleteAction(KDTable table) {
			super(table);
		}

		public void actionPerformed(ActionEvent arg0) {

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
		this.tblMain.getColumn("description").setWidth(255);
//		if (this.tblMain.getColumn("org.name") != null) {
//			this.tblMain.getColumn("org.name").setWidth(200);
//		}
//		
		this.tblMain.getColumn("longNumber").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
//		if (this.tblMain.getColumn("isEnabled") != null) {
//			this.tblMain.getColumn("isEnabled").getStyleAttributes().setHided(
//					true);
//		}
		this.tblMain.getStyleAttributes().setLocked(true);
//		ActionMap actionMap = this.tblMain.getActionMap();
//		actionMap.put(KDTAction.DELETE, new KDTDeleteAction(tblMain));
		
		this.treeMain.setSelectionRow(0);
		this.kDWorkImport.setVisible(false);
		
//		if(OrgConstants.DEF_CU_ID.equals(currentOrg.getId().toString())){
//			actionAddNew.setEnabled(true);
//			actionEdit.setEnabled(true);
//			actionRemove.setEnabled(true);
//		}else{
//			actionAddNew.setEnabled(false);
//			actionEdit.setEnabled(false);
//			actionRemove.setEnabled(false);
//		}
		//集团可以新增所有的类型,但是不能删除系统预设的类型(OrgConstraint.SYS_CU_ID)
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
/*			this.btnEnabled.setVisible(true);
			this.btnDisEnabled.setVisible(true);*/
//			this.actionCancelCancel.setVisible(true);
//			this.actionCancel.setVisible(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
			// this.menuItemCancel.setv(true)
		} else {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
/*			this.btnEnabled.setVisible(false);
			this.btnDisEnabled.setVisible(false);*/
//			this.actionCancelCancel.setVisible(false);
//			this.actionCancel.setVisible(false);			
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
//		
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	class KDTDataFillListner implements KDTDataFillListener {
		public void afterDataFill(KDTDataRequestEvent e) {
			// do something
			String strTemp = "";
//			CellTreeNode node = null;
			for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
				IRow row = tblMain.getRow(i);
				strTemp = row.getCell("longNumber").getValue().toString();
				strTemp = strTemp.replace('!', '.');
				// 列里面加树状
//				node = new CellTreeNode();
//				node.setValue(strTemp);

////				int level = Integer.parseInt(row.getCell("level").getValue()
//						.toString()) - 1;
//				boolean isLeaf = ((Boolean) row.getCell("isLeaf").getValue())
//						.booleanValue();
//				node.setTreeLevel(level);
//				if (isLeaf) {
//					node.setCollapse(false);
//					node.setHasChildren(false);
//				} else {
////					if (level == 0) {
//						node.setCollapse(false);
////					} else {
////						node.setCollapse(false);
////					}
//					node.setHasChildren(true);
//				}
				row.getCell("longNumber").setValue(strTemp);
			}
		}
	}

	/*
	 * 导入参数设置 jackwang 2006/11/16
	 */
	protected ArrayList getImportParam() {
		DatataskParameter param = new DatataskParameter();
		// Hashtable hs = new Hashtable();
		// param.setContextParam(hs);
		String solutionName = "eas.fdc.invite.inviteType";
		param.solutionName = solutionName;
		param.alias = EASResource.getString(resourcePath, "InviteType");
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.kDWorkImport.setIcon(EASResource.getIcon("imgTbtn_input"));
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
	}
}