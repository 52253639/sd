/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
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
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class SaleScheduleReportUI extends AbstractSaleScheduleReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(SaleScheduleReportUI.class);
    public SaleScheduleReportUI() throws Exception
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
		return new SaleScheduleReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return SaleScheduleReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
	    
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"act","yearAct"});
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
                     
                     RptRowSet signRs = (RptRowSet)((RptParams)result).getObject("signRs");
                     RptRowSet onLoadRS = (RptRowSet)((RptParams)result).getObject("onLoadRS");
                     RptRowSet revRS = (RptRowSet)((RptParams)result).getObject("revRS");
                     
                     Map revMap=new HashMap();
                     Map onLoadMap=new HashMap();
                     
                     IRow totoalSignRow=null;
                     IRow totoalRevRow=null;
                     IRow totoalOnLoadRow=null;
                     BigDecimal totalSignMonthAmount=FDCHelper.ZERO;
                     BigDecimal totalSignYearAmount=FDCHelper.ZERO;
                     
                     BigDecimal totalRevMonthAmount=FDCHelper.ZERO;
                     BigDecimal totalRevYearAmount=FDCHelper.ZERO;
                     
                     BigDecimal totalOnLoadMonthAmount=FDCHelper.ZERO;
                     BigDecimal totalOnLoadYearAmount=FDCHelper.ZERO;
                     DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
             		 if(treeNode!=null){
             			Object obj = treeNode.getUserObject();
             			if (obj instanceof OrgStructureInfo&&((OrgStructureInfo)obj).getLevel()==1) {
             				totoalSignRow=tblMain.addRow();
             				totoalSignRow.getCell("company").setValue("宋都控股");
             				totoalSignRow.getCell("type").setValue("销售收入");
             				
             				totoalRevRow=tblMain.addRow();
             				totoalRevRow.getCell("company").setValue("宋都控股");
             				totoalRevRow.getCell("type").setValue("回笼资金");
             				
             				IRow row=tblMain.addRow();
             				row.getCell("company").setValue("宋都控股");
             				row.getCell("type").setValue("营销费用（发生额）");
             				
             				row=tblMain.addRow();
             				row.getCell("company").setValue("宋都控股");
             				row.getCell("type").setValue("费效比");
             				
             				totoalOnLoadRow=tblMain.addRow();
             				totoalOnLoadRow.getCell("company").setValue("宋都控股");
             				totoalOnLoadRow.getCell("type").setValue("在途资金");
             			}
             		 }
                     while(signRs.next()){
                    	 IRow row=tblMain.addRow();
                    	 row.getCell("company").setValue(signRs.getString("company"));
                    	 row.getCell("type").setValue("销售收入");
                    	 row.getCell("act").setValue(signRs.getBigDecimal("monthAmount"));
                    	 row.getCell("yearAct").setValue(signRs.getBigDecimal("yearAmount"));
                    	 
                    	 totalSignMonthAmount=FDCHelper.add(totalSignMonthAmount, signRs.getBigDecimal("monthAmount"));
                    	 totalSignYearAmount=FDCHelper.add(totalSignYearAmount, signRs.getBigDecimal("yearAmount"));
                    	 
                    	 row=tblMain.addRow();
                    	 row.getCell("company").setValue(signRs.getString("company"));
                    	 row.getCell("type").setValue("回笼资金");
                    	 row.getCell("act").setValue(FDCHelper.ZERO);
                    	 row.getCell("yearAct").setValue(FDCHelper.ZERO);
                    	 
                    	 revMap.put(signRs.getString("company"), row);
                    	 
                    	 row=tblMain.addRow();
                    	 row.getCell("company").setValue(signRs.getString("company"));
                    	 row.getCell("type").setValue("营销费用（发生额）");
                    	 
                    	 row=tblMain.addRow();
                    	 row.getCell("company").setValue(signRs.getString("company"));
                    	 row.getCell("type").setValue("费效比");
                    	 
                    	 row=tblMain.addRow();
                    	 row.getCell("company").setValue(signRs.getString("company"));
                    	 row.getCell("type").setValue("在途资金");
                    	 row.getCell("act").setValue(FDCHelper.ZERO);
                    	 row.getCell("yearAct").setValue(FDCHelper.ZERO);
                    	 
                    	 onLoadMap.put(signRs.getString("company"), row);
                	 }
                     while(revRS.next()){
                		 if(revMap.containsKey(revRS.getString("company"))){
                			 IRow row=(IRow) revMap.get(revRS.getString("company"));
                        	 row.getCell("act").setValue(revRS.getBigDecimal("monthAmount"));
                        	 row.getCell("yearAct").setValue(revRS.getBigDecimal("yearAmount"));
                        	 
                        	 totalRevMonthAmount=FDCHelper.add(totalRevMonthAmount, revRS.getBigDecimal("monthAmount"));
                        	 totalRevYearAmount=FDCHelper.add(totalRevYearAmount, revRS.getBigDecimal("yearAmount"));
                		 }
                	 }
                     while(onLoadRS.next()){
                    	 if(onLoadMap.containsKey(onLoadRS.getString("company"))){
                			 IRow row=(IRow) onLoadMap.get(onLoadRS.getString("company"));
                        	 row.getCell("act").setValue(onLoadRS.getBigDecimal("monthAmount"));
                        	 row.getCell("yearAct").setValue(onLoadRS.getBigDecimal("yearAmount"));
                        	 
                        	 totalOnLoadMonthAmount=FDCHelper.add(totalOnLoadMonthAmount, onLoadRS.getBigDecimal("monthAmount"));
                        	 totalOnLoadYearAmount=FDCHelper.add(totalOnLoadYearAmount, onLoadRS.getBigDecimal("yearAmount"));
                		 }
                	 }
                     if(totoalSignRow!=null){
                    	 totoalSignRow.getCell("act").setValue(totalSignMonthAmount);
                    	 totoalSignRow.getCell("yearAct").setValue(totalSignYearAmount);
                     }
                     if(totoalRevRow!=null){
                    	 totoalRevRow.getCell("act").setValue(totalRevMonthAmount);
                    	 totoalRevRow.getCell("yearAct").setValue(totalRevYearAmount);
                     }
                     if(totoalOnLoadRow!=null){
                    	 totoalOnLoadRow.getCell("act").setValue(totalOnLoadMonthAmount);
                    	 totoalOnLoadRow.getCell("yearAct").setValue(totalOnLoadYearAmount);
                     }
         	         tblMain.setRefresh(true);
         	         if(signRs.getRowCount() > 0){
         	        	tblMain.reLayoutAndPaint();
         	         }else{
         	        	tblMain.repaint();
         	         }
         	        tblMain.getGroupManager().setGroup(true);
        			
        			tblMain.getColumn("company").setGroup(true);
        			tblMain.getColumn("company").setMergeable(true);
                 }
            }
            );
            dialog.show();
    	}
    	isQuery=false;
	}
	protected void buildOrgTree() throws Exception{
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.SALE,"", null, null, FDCHelper.getActionPK(this.actionOnLoad));
		this.treeMain.setModel(orgTreeModel);
//		this.treeMain.setModel(TimeAccountReportFilterUI.getSellProjectForSHESellProject(actionOnLoad, MoneySysTypeEnum.SalehouseSys,true));
		this.treeMain.setSelectionRow(0);
		this.treeMain.expandRow(0);
	}
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}
	private void refresh() throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "OrgStructure").keySet());
				params.setObject("orgUnit", allSpIdStr);
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
	IUIWindow uiWindow=null;
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
		}
	}

}