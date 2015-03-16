/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.rpt;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.BasicView;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaDialog;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.markesupplier.client.MarketSupplierReviewGatherEditUI;
import com.kingdee.eas.fdc.invite.markesupplier.client.MarketSupplierStockEditUI;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.client.NewSupplierStockEditUI;
import com.kingdee.eas.fdc.invite.supplier.client.SupplierReviewGatherEditUI;
import com.kingdee.eas.fdc.invite.supplier.report.SupplierStockReportFacadeFactory;
import com.kingdee.eas.fdc.market.client.EnterprisePlanEditUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.DefaultKDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MarketSupplierStockReportUI extends AbstractMarketSupplierStockReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierStockReportUI.class);
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    /**
     * output class constructor
     */
    public MarketSupplierStockReportUI() throws Exception
    {
        super();
        kDTable1.checkParsed();
        kDTable1.getDataRequestManager().addDataRequestListener(this);
        kDTable1.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(kDTable1);
    }


	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new MarketSupplierStockReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return MarketSupplierStockReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return kDTable1;
	}

	protected void query() {

		if(isOnLoad) return;
		kDTable1.removeColumns();
		kDTable1.removeRows();
		
		kDTable1.getColumn("isPass").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof BigDecimal){
					BigDecimal info = (BigDecimal)obj;
					if(IsGradeEnum.getEnum(info.intValue())==null){
						return "";
					}else{
						return IsGradeEnum.getEnum(info.intValue()).getAlias();
					}
				}
				return super.getText(obj);
			}
		});
		kDTable1.getColumn("name").getStyleAttributes().setFontColor(Color.BLUE);
		kDTable1.getColumn("kcScore").getStyleAttributes().setFontColor(Color.BLUE);
		kDTable1.getColumn("lyzhScore").getStyleAttributes().setFontColor(Color.BLUE);
		kDTable1.getColumn("lypgScore").getStyleAttributes().setFontColor(Color.BLUE);
		kDTable1.getColumn("contractName").getStyleAttributes().setFontColor(Color.BLUE);
		kDTable1.getColumn("levelSetUp").getStyleAttributes().setFontColor(Color.BLUE);
	
		CRMClientHelper.changeTableNumberFormat(kDTable1, new String[]{"kcScore","lyzhScore","lypgScore"});
		
		FDCHelper.formatTableDate(this.kDTable1, "lyzhDate");
		FDCHelper.formatTableDate(this.kDTable1, "lypgDate");
		FDCHelper.formatTableDate(this.kDTable1, "storageDate");
		
		EnterprisePlanEditUI.mergerTable(this.kDTable1, new String[]{"id"},new String[]{"id","seq","purchaseOrgUnit","inviteType","storageNumber","name","isPass","quaLevel","levelSetUpId","levelSetUp","grade","splArea"
				,"contractor","kcId","kcScore","storageDate","inviteName","lyzhId","lyzhScore","lyzhDate"});
	
	}
	protected String getInviteName(String id){
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql(" select inviteProject.fid id,inviteProject.fname name from T_INV_SupplierQualifyEntry entry left join T_INV_SupplierQualify qualify on entry.FParentID=qualify.fid left join T_INV_InviteProject inviteProject on inviteProject.fid=qualify.finviteProjectId");
		sqlBuilder.appendSql(" where qualify.fstate='4AUDITTED' and entry.fsupplierId=?");
		sqlBuilder.addParam(id);
		String inviteName="";
		try {
			IRowSet rs = sqlBuilder.executeQuery();
			while (rs.next()){
				String inviteProjectId=rs.getString("id");
				String name=rs.getString("name");
				sqlBuilder = new FDCSQLBuilder();
				sqlBuilder.appendSql(" select curProject.fname_l2 curProjectName from T_INV_InviteProjectEntries inviteEntry left join T_FDC_CurProject curProject on curProject.fid=inviteEntry.FProjectID");
				sqlBuilder.appendSql(" where inviteEntry.fparentid=?");
				sqlBuilder.addParam(inviteProjectId);
				
				String curProjectName="（";
				IRowSet curProjectRs = sqlBuilder.executeQuery();
				while (curProjectRs.next()){
					curProjectName=curProjectName+curProjectRs.getString("curProjectName")+",";
				}
				if(curProjectName.indexOf(",")>0){
					curProjectName=curProjectName.substring(0, curProjectName.lastIndexOf(","))+"）";
				}else{
					curProjectName="";
				}
				inviteName=inviteName+curProjectName+name+";\n";
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inviteName;
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

	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.kDTree1.getLastSelectedPathComponent();
    	if(treeNode!=null){
    		Window win = SwingUtilities.getWindowAncestor(this);
            LongTimeDialog dialog = null;
            if(win instanceof Frame){
            	dialog = new LongTimeDialog((Frame)win);
            }else if(win instanceof Dialog){
            	dialog = new LongTimeDialog((Dialog)win);
            }
            if(dialog==null){
            	dialog = new LongTimeDialog(new Frame());
            }
            dialog.setLongTimeTask(new ILongTimeTask() {
                public Object exec()throws Exception{
                	RptParams resultRpt= getRemoteInstance().query(params);
                 	return resultRpt;
                }
                public void afterExec(Object result)throws Exception{
                	RptParams rpt = getRemoteInstance().createTempTable(params);
                    RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                    KDTableUtil.setHeader(header, kDTable1);
                    
                    RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
                    kDTable1.setRowCount(rs.getRowCount()+kDTable1.getRowCount());
        	        
                    kDTable1.setRefresh(false);
        	        
//        	        Map curProject=(HashMap)((RptParams)result).getObject("curProject");
        	        IRow row=null;
        	        int seq=1;
        	        String inviteName=null;
        	        while(rs.next()){
        	        	if(row!=null&&!row.getCell("id").getValue().toString().equals(rs.getString("id"))){
        	        		seq=seq+1;
        	        	}
        	        	if(inviteName==null||!row.getCell("id").getValue().toString().equals(rs.getString("id"))){
//        	        		inviteName=(String) curProject.get(rs.getString("id"));
        	        	}
        	        	row=kDTable1.addRow();
            			((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
            			row.getCell("seq").setValue(seq);
//        	        	row.getCell("inviteName").setValue(inviteName);
        	        }
        	        if(rs.getRowCount() > 0){
        	        	kDTable1.reLayoutAndPaint();
        	        }else{
        	        	kDTable1.repaint();
        	        }
        	        kDTable1.setRefresh(true);
                }
            }
            );
            dialog.show();
    	}
    	isQuery=false;
	}
	protected void buildInviteTypeTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("longNumber","6%",CompareType.LIKE));
		TreeModel model = FDCClientHelper.createDataTree(InviteTypeFactory.getRemoteInstance(), filter, "采购类别");
		this.kDTree2.setModel(model);
		this.kDTree2.setSelectionRow(0);
	}
	protected void buildOrgTree() throws Exception{
		OrgUnitInfo cuInfo = SysContext.getSysContext().getCurrentOrgUnit();
		if (!cuInfo.isIsPurchaseOrgUnit()) {
			MsgBox.showInfo(this, "非采购组织不能操作");
			SysUtil.abort();
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.PURCHASE,"", cuInfo.getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
		this.kDTree1.setModel(orgTreeModel);
		this.kDTree1.setSelectionRow(0);
	}
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
			}
		return null;
	}
	private void refresh() throws Exception {
		DefaultKingdeeTreeNode TypeNode = this.getSelectedTreeNode(kDTree2);
    	DefaultKingdeeTreeNode OrgNode = this.getSelectedTreeNode(kDTree1);
    	Object TypeInfo = null;
    	if(TypeNode != null && TypeNode.getUserObject() != null){
    		TypeInfo = TypeNode.getUserObject();
    		kDContainer1.setTitle(TypeNode.getText());
    	}
    	if (TypeInfo instanceof InviteTypeInfo) {
		    String longNumber = ((InviteTypeInfo)TypeInfo).getLongNumber();
		    params.setObject("inviteType", longNumber);
		}else{
			params.setObject("inviteType", null);
		}
    	if(OrgNode.getUserObject() instanceof OrgStructureInfo){
    		OrgStructureInfo org=(OrgStructureInfo)OrgNode.getUserObject();
			String longNumber=org.getLongNumber();
    		params.setObject("org", longNumber);
    	}else{
    		params.setObject("org", null);
    	}
    	query();
	}
	protected void supplierTypeTree_valueChanged(TreeSelectionEvent e)throws Exception {
		this.refresh();
	}
	protected void orgTree_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh();
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
		setShowDialogOnLoad(true);
		kDTable1.getStyleAttributes().setLocked(true);
		super.onLoad();
		buildOrgTree();
		buildInviteTypeTree();
		kDTable1.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		isOnLoad=false;
		
		this.refresh();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getColIndex() == kDTable1.getColumnIndex("inviteName")&&kDTable1.getRow(e.getRowIndex()).getCell("inviteName").getValue()!=null
				&&!"".equals(kDTable1.getRow(e.getRowIndex()).getCell("inviteName").getValue().toString().trim())){
			this.showDialog(this, kDTable1, 400, 200, 2000);
		}
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(kDTable1.getColumnKey(e.getColIndex()).equals("name")){
				String id=(String)kDTable1.getRow(e.getRowIndex()).getCell("id").getValue();
				if(id==null) return;
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put(UIContext.ID,id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(MarketSupplierStockEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}else if(kDTable1.getColumnKey(e.getColIndex()).equals("kcScore")){
				String id=(String)kDTable1.getRow(e.getRowIndex()).getCell("kcId").getValue();
				if(id==null) return;
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put(UIContext.ID,id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(MarketSupplierReviewGatherEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}else if(kDTable1.getColumnKey(e.getColIndex()).equals("lyzhScore")){
				String id=(String)kDTable1.getRow(e.getRowIndex()).getCell("lyzhId").getValue();
				if(id==null) return;
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put(UIContext.ID,id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(MarketSupplierReviewGatherEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}else if(kDTable1.getColumnKey(e.getColIndex()).equals("lypgScore")){
				String id=(String)kDTable1.getRow(e.getRowIndex()).getCell("lypgId").getValue();
				if(id==null) return;
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put(UIContext.ID,id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(MarketSupplierReviewGatherEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}else if(kDTable1.getColumnKey(e.getColIndex()).equals("contractName")){
				String id=(String)kDTable1.getRow(e.getRowIndex()).getCell("contractId").getValue();
				if(id==null) return;
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put(UIContext.ID,id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}else if(kDTable1.getColumnKey(e.getColIndex()).equals("levelSetUp")){
				String id=(String)kDTable1.getRow(e.getRowIndex()).getCell("levelSetUpId").getValue();
				if(id==null) return;
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put(UIContext.ID,id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(MarketSupplierReviewGatherEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}

}