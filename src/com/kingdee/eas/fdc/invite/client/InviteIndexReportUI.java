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
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.invite.InviteIndexDetailReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
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
public class InviteIndexReportUI extends AbstractInviteIndexReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteIndexReportUI.class);
    public InviteIndexReportUI() throws Exception
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
		tblMain.getColumn("value1").getStyleAttributes().setFontColor(Color.BLUE);
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
		
		tblMain.setVisible(false);
		tblValue.setEnabled(false);
		tblValue.getColumn("rate").setRenderer(render_scale);
		
		if(tblMain.getRow(0)!=null){
			tblValue.getRow(0).getCell("rate").setValue(tblMain.getRow(0).getCell("rate1").getValue()==null?0:tblMain.getRow(0).getCell("rate1").getValue());
			tblValue.getRow(1).getCell("rate").setValue(tblMain.getRow(0).getCell("rate2").getValue()==null?0:tblMain.getRow(0).getCell("rate2").getValue());
			tblValue.getRow(2).getCell("rate").setValue(tblMain.getRow(0).getCell("rate3").getValue()==null?0:tblMain.getRow(0).getCell("rate3").getValue());
			tblValue.getRow(3).getCell("rate").setValue(tblMain.getRow(0).getCell("rate4").getValue()==null?0:tblMain.getRow(0).getCell("rate4").getValue());
			tblValue.getRow(4).getCell("rate").setValue(tblMain.getRow(0).getCell("rate5").getValue()==null?0:tblMain.getRow(0).getCell("rate5").getValue());
		}
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
                     
                     tblValue.removeRows();
                     IRow row=tblValue.addRow();
                     row.getCell("name").setValue("新增供应商中标率");
                     row.getCell("des").setValue("新增供应商招标项数/已完成采购的合同总项数（剔除集团战略或集中采购）×100%");
                     
                     row=tblValue.addRow();
                     row.getCell("name").setValue("目标成本单价达成率");
                     row.getCell("des").setValue("已完成采购且符合采购计划中目标成本单价的合同项数/已完成采购的合同总项数（剔除集团战略或集中采购）×100%");
                     
                     row=tblValue.addRow();
                     row.getCell("name").setValue("招标率");
                     row.getCell("des").setValue("制度要求招标且实际采用招标的合同项数/制度要求招标的合同项数（剔除集团战略或集中采购）×100%");
                     
                     row=tblValue.addRow();
                     row.getCell("name").setValue("最低价中标率");
                     row.getCell("des").setValue("采用最低价中标的合同数（不包含直接委托）/已完成采购的合同总项数（剔除集团战略或集中采购）×100%");
                     
                     row=tblValue.addRow();
                     row.getCell("name").setValue("采购计划达成率");
                     row.getCell("des").setValue("已按时完成采购的合同项数/已完成采购的合同总项数（剔除集团战略或集中采购）×100%");
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
		tblValue.checkParsed();
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

}