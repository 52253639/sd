/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.BasicView;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
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
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaDialog;
import com.kingdee.eas.fdc.market.ChannelTypeFactory;
import com.kingdee.eas.fdc.market.ChannelTypeInfo;
import com.kingdee.eas.fdc.market.ChannelTypeTreeFactory;
import com.kingdee.eas.fdc.market.ChannelTypeTreeInfo;
import com.kingdee.eas.fdc.market.EnterprisePlanCollection;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryCollection;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryFactory;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryInfo;
import com.kingdee.eas.fdc.market.EnterprisePlanFactory;
import com.kingdee.eas.fdc.market.EnterprisePlanInfo;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanCollection;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanFactory;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanInfo;
import com.kingdee.eas.fdc.market.ThemeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class EnterprisePlanListUI extends AbstractEnterprisePlanListUI
{
    private static final Logger logger = CoreUIObject.getLogger(EnterprisePlanListUI.class);
    
    /**
     * output class constructor
     */
    public EnterprisePlanListUI() throws Exception
    {
        super();
    }
    protected String getEditUIModal()
    {
    	return UIFactoryName.NEWTAB;
	}
    protected String[] getLocateNames()
    {
        String[] locateNames = new String[3];
        locateNames[0] = "number";
        locateNames[1] = "name";
        locateNames[2] = "version";
        return locateNames;
    }
    /**
     * output storeFields method
     */
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
		
		this.tblMain.getColumn("name").setWidth(220);
	}

    protected String getEditUIName() {
		return EnterprisePlanEditUI.class.getName();
    	
    }
    protected ICoreBase getBizInterface() throws Exception {
    	return EnterprisePlanFactory.getRemoteInstance();
    	
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
	protected void refresh(ActionEvent e) throws Exception{
		boolean isSelect=false;
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1){
			isSelect=true;
		}
		super.refresh(e);
        if(this.tblMain.getRowCount()==0){
        	tblMain2.removeRows();
        	this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
        }
        if(isSelect){
        	tblMain.getSelectManager().select(0, 0);
        }
	}
	
	/**
     * 审批
     * */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	ArrayList idList = getSelectedIdValues();
		((IFDCBill)getBizInterface()).audit(idList);
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
    }
    /**
     * 反审批
     * */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	ArrayList idList = getSelectedIdValues();
		((IFDCBill)getBizInterface()).unAudit(idList);
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
    }
    
	/**
     * 构建树
     * */
    protected void initTree() throws Exception {
    	this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
    	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
    	this.treeMain.setSelectionRow(0);
	}
    public DefaultKingdeeTreeNode getSelectedTreeNode()
    {
        return (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
    }
    public SelectorItemCollection getEntrySelectors(){
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("entry.sellPlanEntry.subject.*");
		sel.add("entry.sellPlanEntry.classify.*");
		sel.add("entry.sellPlanEntry.mediaName.*");
		sel.add("entry.sellPlanEntry.*");
		sel.add("entry.*");
		return sel;
	}
    protected void updateEnterprisePlanEntry() throws EASBizException, BOSException{
    	tblMain2.removeRows();
        int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
        if(rowIndex==-1){
        	return;
        }
	    IRow prow = tblMain.getRow(rowIndex);
	    String id =prow.getCell("id").getValue().toString();
	    EnterprisePlanInfo EnterInfo =EnterprisePlanFactory.getRemoteInstance().getEnterprisePlanInfo(new ObjectUuidPK(id.toString()),getEntrySelectors());
	    EnterprisePlanEntryCollection enterEntryColl =EnterInfo.getEntry(); 
	    CRMHelper.sortCollection(enterEntryColl, "seq", true);
		for(int i=0;i<enterEntryColl.size();i++){
			EnterprisePlanEntryInfo  planEntryInfo =enterEntryColl.get(i);
	    	
	    	EnterpriseSellPlanCollection sellCol = planEntryInfo.getSellPlanEntry();
	    	CRMHelper.sortCollection(sellCol, "seq", true);
	    	
	    	for(int j=0;j<sellCol.size();j++){
	    		
	    		EnterpriseSellPlanInfo sellInfo=sellCol.get(j);
	    		
	    		IRow row = tblMain2.addRow();
	    		row.getCell("id").setValue(planEntryInfo.getId());
	    		row.getCell("themeState").setValue((ThemeEnum)planEntryInfo.getState()); 
				row.getCell("theme").setValue(planEntryInfo.getTheme()); 
				row.getCell("enDescribe").setValue(planEntryInfo.getDescribe());
	    		
				row.getCell("proceeding").setValue(sellInfo.getProceeding());
				row.getCell("entryState").setValue((ThemeEnum)sellInfo.getState()); 
				row.getCell("subject").setValue(sellInfo.getSubject()); 
				row.getCell("classify").setValue(sellInfo.getClassify());
				row.getCell("mediaName").setValue(sellInfo.getMediaName());
				row.getCell("startTime").setValue(sellInfo.getStartTime());
				row.getCell("endTime").setValue(sellInfo.getEndTime());
				row.getCell("quantity").setValue(Integer.valueOf(sellInfo.getQuantity()));
				row.getCell("planCost").setValue(sellInfo.getPlanCost());
				row.getCell("remark").setValue(sellInfo.getRemark());
	    	}
	    }
		EnterprisePlanEditUI.mergerTable(this.tblMain2, new String[]{"id"},new String[]{"theme","themeState","enDescribe"});
    }
    
    public SelectorItemCollection getPalnThemeSelectors(){
		SelectorItemCollection sel=super.getSelectors();
		sel.add("*");
		sel.add("subject.*");
		sel.add("classify.*");
		sel.add("mediaName.*");
		sel.add("CU.*");
		return sel;
	}
    
    protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
    	updateEnterprisePlanEntry();
    	MeasurePlanTargetListUI.setBtnState(this.tblMain,getBizInterface(),this.actionAudit,this.actionUnAudit,this.actionEdit,this.actionRemove);
	}
	protected boolean isIgnoreCUFilter() {
    	return true;
    }
	public boolean isIgnoreRowCount() {
		return false;
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
					BOSUuid sellProjectId = ((SellProjectInfo) userObject2).getId();
					uiContext.put("sellProjectId", sellProjectId.toString());
				}else{
					com.kingdee.eas.util.client.MsgBox.showWarning("请选中具体销售项目！");
					SysUtil.abort();
				}
			}else{
				com.kingdee.eas.util.client.MsgBox.showWarning("请选中具体销售项目！");
				SysUtil.abort();
			}
		}
	}
	 /**
	 * 描述：点击某一节点过滤
	 */
   protected IQueryExecutor getQueryExecutor(IMetaDataPK pk,
		EntityViewInfo viewInfo) {
	    EntityViewInfo view =  (EntityViewInfo)viewInfo.clone();
	    
	    DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		FilterInfo filter = new FilterInfo();
		if(node!=null){
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo info = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", info.getId().toString()));
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",MeasurePlanTargetListUI.getAllProjectNodeId(node),CompareType.INCLUDE));
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
				SorterItemInfo planYear=new SorterItemInfo("planYear");
				planYear.setSortType(SortType.DESCEND);
				sort.add(planYear);
				
				SorterItemInfo planMonth=new SorterItemInfo("planMonth");
				planMonth.setSortType(SortType.DESCEND);
				sort.add(planMonth);
				
				SorterItemInfo version=new SorterItemInfo("version");
				version.setSortType(SortType.DESCEND);
				sort.add(version);
				
				view.setSorter(sort);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return super.getQueryExecutor(pk, view);
   	}
	protected void tblMain2_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain2_tableClicked(e);
		if(e.getColIndex() == tblMain2.getColumnIndex("enDescribe"))
		{
			this.showDialog(this, tblMain2, 250, 100, 2000);
		}
	}
   
	public void showDialog(JComponent owner, KDTable table, int X, int Y, int len)
    {
        int rowIndex = table.getSelectManager().getActiveRowIndex();
        int colIndex = table.getSelectManager().getActiveColumnIndex();
        ICell cell = table.getCell(rowIndex, colIndex);
        if(cell.getValue() == null)
            return;
        BasicView view = table.getViewManager().getView(5);
        Point p = view.getLocationOnScreen();
        Rectangle rect = view.getCellRectangle(rowIndex, colIndex);
        java.awt.Window parent = SwingUtilities.getWindowAncestor(owner);
        KDDetailedAreaDialog dialog;
        if(parent instanceof Dialog)
        {
            dialog = new KDDetailedAreaDialog((Dialog)parent, X, Y, true);
        }
        else
        if(parent instanceof Frame){
            dialog = new KDDetailedAreaDialog((Frame)parent, X, Y, true);
        }
        else{
            dialog = new KDDetailedAreaDialog(true);
        }
        String vals = cell.getValue().toString();
        dialog.setData(vals);
        dialog.setPRENTE_X(p.x + rect.x + rect.width);
        dialog.setPRENTE_Y(p.y + rect.y);
        dialog.setMaxLength(len);
        dialog.setEditable(false);
        dialog.show();
    }
}