/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.OperationPhasesInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.market.MeasurePlanTargetFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class MeasurePlanTargetListUI extends AbstractMeasurePlanTargetListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MeasurePlanTargetListUI.class);
    
    /**
     * output class constructor
     */
    public MeasurePlanTargetListUI() throws Exception
    {
        super();
    }
    protected String[] getLocateNames()
    {
        String[] locateNames = new String[2];
        locateNames[0] = "number";
        locateNames[1] = "versions";
        return locateNames;
    }
    public void onLoad() throws Exception {
    	actionQuery.setEnabled(false);
    	initTree();
		super.onLoad();
		actionQuery.setEnabled(true);
		
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
    	btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.menuBiz.setVisible(false);
		
		this.tblMain.getColumn("totalAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("totalAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	}
    /**
     * 构建树
     * */
    protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);
	}
    
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = getSelectedIdValues();
		((IFDCBill)getBizInterface()).audit(idList);
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = getSelectedIdValues();
		((IFDCBill)getBizInterface()).unAudit(idList);
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	/**
	 * 描述：为当前单据的新增、编辑、查看准备Context
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {
			if(getSelectedTreeNode()!=null){
				Object userObject2 = getSelectedTreeNode().getUserObject();
				if(userObject2 instanceof SellProjectInfo){
					BOSUuid projId = ((SellProjectInfo) userObject2).getId();
					uiContext.put("sellProjectId", projId.toString());
				}else{
					com.kingdee.eas.util.client.MsgBox.showWarning("请选中具体销售项目！");
					SysUtil.abort();
				}
			}
		}
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof OrgStructureInfo){
			this.actionAddNew.setEnabled(false);
		}else{
			this.actionAddNew.setEnabled(true);
		}
		this.refresh(null);
		
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK pk,EntityViewInfo viewInfo) {
		EntityViewInfo view = (EntityViewInfo)viewInfo.clone();
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		FilterInfo  filter = new FilterInfo();
		if(node!=null){
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo info = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("projectAgin.id", info.getId().toString()));
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				filter.getFilterItems().add(new FilterItemInfo("projectAgin.id",MeasurePlanTargetListUI.getAllProjectNodeId(node),CompareType.INCLUDE));
			}
		}
		try {
			if(view.getFilter()!=null){
				view.getFilter().mergeFilter(filter, "and");
			}else{
				view.setFilter(filter);
			}
			if(view.getSorter().size() < 2  ){ //默认有一个 ID ASC
				SorterItemCollection sort=new SorterItemCollection();
				SorterItemInfo isNew=new SorterItemInfo("isNew");
				isNew.setSortType(SortType.DESCEND);
				sort.add(isNew);
				SorterItemInfo versions=new SorterItemInfo("versions");
				versions.setSortType(SortType.DESCEND);
				sort.add(versions);
				view.setSorter(sort);
				view.setFilter(filter);
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return super.getQueryExecutor(pk, view);
	}
	
	/**
	 * 获取当前组织下所有的项目节点
	 * */
	public static Set getAllProjectNodeId(DefaultKingdeeTreeNode root){
		Set idSet = new HashSet();
		//取出销售组织下所有的一级节点ID，取得projectID
		String id = null;
		for(int i = 0 ; i < root.getChildCount() ; i++){
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)root.getChildAt(i);
			if(node!=null && node.getUserObject() instanceof OrgStructureInfo){
				getAllProjectNodeId(node);
			}
			else if(node!=null && node.getUserObject() instanceof SellProjectInfo){
				id = (((SellProjectInfo)node.getUserObject())).getId().toString();
				idSet.add(id);
				getAllProjectNodeId(node);
			}
		}
		if(idSet.size()==0)
			idSet.add("AAAAAAAA");
		return idSet;
	}
	protected String getEditUIName() {
    	return MeasurePlanTargetEditUI.class.getName();
    }
    protected ICoreBase getBizInterface() throws Exception {
    	return MeasurePlanTargetFactory.getRemoteInstance();
    }
    public DefaultKingdeeTreeNode getSelectedTreeNode()
    {
        return (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
    }
    protected String getEditUIModal()
    {
    	return UIFactoryName.NEWTAB;
	}
    public static void setBtnState(KDTable table,ICoreBase iCoreBase,ItemAction audit,ItemAction unAudit,ItemAction edit,ItemAction del) throws EASBizException, BOSException{
    	int index = table.getSelectManager().getActiveRowIndex();
		if (index == -1){
			audit.setEnabled(false);
			unAudit.setEnabled(false);
			edit.setEnabled(false);
			del.setEnabled(false);
			return;
		}
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("state");
		String id = (String) table.getRow(index).getCell("id").getValue();
		if(id==null){
			audit.setEnabled(false);
			unAudit.setEnabled(false);
			edit.setEnabled(false);
			del.setEnabled(false);
			return;
		}
		FDCBillInfo info=(FDCBillInfo)iCoreBase.getValue(new ObjectUuidPK(id),sel);
		if(FDCBillStateEnum.SUBMITTED.equals(info.getState())){
			audit.setEnabled(true);
			unAudit.setEnabled(false);
			edit.setEnabled(true);
			del.setEnabled(true);
		}else if(FDCBillStateEnum.AUDITTED.equals(info.getState())){
			audit.setEnabled(false);
			unAudit.setEnabled(true);
			edit.setEnabled(false);
			del.setEnabled(false);
		}else if(FDCBillStateEnum.SAVED.equals(info.getState())){
			audit.setEnabled(false);
			unAudit.setEnabled(false);
			edit.setEnabled(true);
			del.setEnabled(true);
		}else if(FDCBillStateEnum.AUDITTING.equals(info.getState())){
			audit.setEnabled(false);
			unAudit.setEnabled(false);
			edit.setEnabled(false);
			del.setEnabled(false);
		}
    }
    protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
    	setBtnState(this.tblMain,getBizInterface(),this.actionAudit,this.actionUnAudit,this.actionEdit,this.actionRemove);
	}
    protected void refresh(ActionEvent e) throws Exception{
    	boolean isSelect=false;
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1){
			isSelect=true;
		}
		super.refresh(e);
        if(this.tblMain.getRowCount()==0){
        	this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
        }
        if(isSelect){
        	tblMain.getSelectManager().select(0, 0);
        }
    }
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
}