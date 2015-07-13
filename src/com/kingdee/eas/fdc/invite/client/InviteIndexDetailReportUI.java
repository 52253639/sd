/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
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
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractBillReportUI;
import com.kingdee.eas.fdc.invite.InviteIndexDetailReportFacadeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.app.SupplierStockControllerBean;
import com.kingdee.eas.fdc.invite.supplier.client.NewSupplierStockEditUI;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.report.PaymentReportUI;
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

/**
 * output class name
 */
public class InviteIndexDetailReportUI extends AbstractInviteIndexDetailReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteIndexDetailReportUI.class);
    public InviteIndexDetailReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new InviteIndexReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return InviteIndexDetailReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
		
		CRMClientHelper.changeTableNumberFormat(tblMain,new String []{"amount","value3","value4","rate1","rate2","rate3","rate4"});
		FDCHelper.formatTableDate(tblMain, "conDate");
		tblMain.getColumn("conName").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("supplier").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getViewManager().freeze(0, 6);
		sumTotal(tblMain);
		
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				if(o==null){
					return null;
				}else{
					String str = o.toString();
					return str + "%";
				}
				
			}
		});
		tblMain.getColumn("rate1").setRenderer(render_scale);
		tblMain.getColumn("rate2").setRenderer(render_scale);
		tblMain.getColumn("rate3").setRenderer(render_scale);
		tblMain.getColumn("rate4").setRenderer(render_scale);
		tblMain.getColumn("rate5").setRenderer(render_scale);
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
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
                     KDTableUtil.setHeader(header, tblMain);
                     
                     int max=1;
                     RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rs");
                     while(rs.next()){
         	        	 IRow row=tblMain.addRow();
         	        	 int level=rs.getInt("levelNumber")-1;
         	        	 row.setTreeLevel(level);
         	        	 if(level+1>max){
         	        		 max=level+1;
         	        	 }
         	        	 ((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
         	         }
         	         tblMain.setRefresh(true);
         	         if(rs.getRowCount() > 0){
         	        	tblMain.getTreeColumn().setDepth(max);
         	        	tblMain.reLayoutAndPaint();
         	         }else{
         	        	tblMain.repaint();
         	         }
                 }
            }
            );
            dialog.show();
    	}
    	isQuery=false;
	}
	protected List getUnionColumns() {
		List columns = new ArrayList();
		columns.add("value1");
		columns.add("value2");
		columns.add("value3");
		columns.add("value4");
		columns.add("value5");
		columns.add("value6");
		columns.add("value7");
		columns.add("value8");
		columns.add("value9");
		return columns;
	}
	protected void sumTotal(KDTable table) {
		List unionColumns = getUnionColumns();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);

			IRow rowNext = table.getRow(i+1);
			if(rowNext != null && rowNext.getCell("number") != null && rowNext.getCell("number").getValue() != null 
					&& row.getCell("number") != null && row.getCell("number").getValue() != null){
				String longNumNext = rowNext.getCell("number").getValue().toString();
				try{
					longNumNext = longNumNext.substring(0,longNumNext.lastIndexOf("."));
				}catch (Exception e) {
					System.out.println(e.getMessage());
					return;
				}
				String longNum = row.getCell("number").getValue().toString();
				if(longNumNext.equals(longNum)){
					row.getStyleAttributes().setBackground(new Color(0xEEEEEE));
				}
			}
			if (row.getUserObject() == null) {
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					rowList.add(rowAfter);
				}
				for (int k = 0; k < unionColumns.size(); k++) {
					String colName = (String) unionColumns.get(k);
					Object destValue = row.getCell(colName).getValue();
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (colName.equals("value3")||colName.equals("value4")) {
								if (destValue == null) {
									destValue = FDCHelper.ZERO;
								}
								destValue = FDCHelper.toBigDecimal(destValue, 2).add(FDCHelper.toBigDecimal(value, 2));
							} else {
								if (destValue == null) {
									destValue = new Integer(0);
								}
								destValue = Integer.parseInt(destValue.toString()) + Integer.parseInt(value.toString());
							}
						}
					}
					row.getCell(colName).setValue(destValue);
					row.getCell("rate1").setValue(FDCHelper.divide(FDCHelper.multiply(row.getCell("value2").getValue(), new BigDecimal(100)), row.getCell("value1").getValue(),2,BigDecimal.ROUND_HALF_UP));
					row.getCell("rate2").setValue(FDCHelper.divide(FDCHelper.multiply(row.getCell("value5").getValue(), new BigDecimal(100)), row.getCell("value1").getValue(),2,BigDecimal.ROUND_HALF_UP));
					row.getCell("rate3").setValue(FDCHelper.divide(FDCHelper.multiply(row.getCell("value6").getValue(), new BigDecimal(100)), row.getCell("value7").getValue(),2,BigDecimal.ROUND_HALF_UP));
					row.getCell("rate4").setValue(FDCHelper.divide(FDCHelper.multiply(row.getCell("value8").getValue(), new BigDecimal(100)), row.getCell("value1").getValue(),2,BigDecimal.ROUND_HALF_UP));
					row.getCell("rate5").setValue(FDCHelper.divide(FDCHelper.multiply(row.getCell("value9").getValue(), new BigDecimal(100)), row.getCell("value1").getValue(),2,BigDecimal.ROUND_HALF_UP));
				}
			}
		}
	}
	protected void buildOrgTree() throws Exception{
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, this.treeMain, this.actionOnLoad);

		DefaultKingdeeTreeNode orgRoot = (DefaultKingdeeTreeNode) ((TreeModel) this.treeMain.getModel()).getRoot();
		this.treeMain.setSelectionRow(0);
		this.treeMain.expandAllNodes(true, orgRoot);
	}
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}
	private void refresh() throws Exception {
		DefaultKingdeeTreeNode node = this.getSelectedTreeNode(this.treeMain);
    	if(node!=null){
    		if(node.getUserObject() instanceof CurProjectInfo){
        		CurProjectInfo curProject=(CurProjectInfo)node.getUserObject();
        		params.setObject("curProject", curProject);
        	}else{
        		params.setObject("curProject", null);
        	}
    		query();
    	}
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh();
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
		setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		buildOrgTree();
		isOnLoad=false;
		this.refresh();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(tblMain.getColumnKey(e.getColIndex()).equals("supplier")){
				String supplierId=(String)this.tblMain.getRow(e.getRowIndex()).getCell("supplierId").getValue();
				if(supplierId!=null){
					SupplierStockCollection supplierCol=SupplierStockFactory.getRemoteInstance().getSupplierStockCollection("select id from where sysSupplier.id='"+supplierId+"'");
					if(supplierCol.size()>0){
						UIContext uiContext = new UIContext(this);
						uiContext.put(UIContext.OWNER, this);
						uiContext.put("ID", supplierCol.get(0).getId().toString());
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(NewSupplierStockEditUI.class.getName(), uiContext, null, OprtState.VIEW);
						uiWindow.show();
						return;
					}else{
						FDCMsgBox.showWarning(this,"无关联供应商档案登记信息！");
					}
				}
			}else if(tblMain.getColumnKey(e.getColIndex()).equals("conName")){
				String conId=(String)this.tblMain.getRow(e.getRowIndex()).getCell("conId").getValue();
				if(conId!=null){
					UIContext uiContext = new UIContext(this);
					uiContext.put(UIContext.OWNER, this);
					uiContext.put("ID", conId);
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
					uiWindow.show();
					return;
				}
			}
		}
	}
}