/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.PeriodEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
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

/**
 * output class name
 */
public class SignAccountReportUI extends AbstractSignAccountReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(SignAccountReportUI.class);
    private TreeModel tree;
    /**
     * output class constructor
     */
    public SignAccountReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }

    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new SignAccountReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return SignAccountReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		sum();
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"buildArea","lastYearBuildArea","lastMonthBuildArea","roomArea","lastYearRoomArea","lastMonthRoomArea","account","lastYearAccount","lastMonthAccount","revAccount","roomAreaT","roomAreaH","buildAreaT","buildAreaH","accountT","accountH","buildPrice","buildPriceT","buildPriceH","roomPrice","roomPriceT","roomPriceH"});
	
		for(int i=0;i<tblMain.getRowCount();i++){
			for(int j=0;j<tblMain.getColumnCount();j++){
				if(tblMain.getColumnKey(j).indexOf("T")>0||tblMain.getColumnKey(j).indexOf("H")>0){
					tblMain.getCell(i, j).setRenderer(new ObjectValueRender(){
			    		public String getText(Object obj) {
			    			if(obj!=null){
			    				return obj.toString()+"%";
			    			}
			    			return super.getText(obj);
			    		}
			    	});
				}
			}
		}
	}
	
	public void addSumRow(DefaultKingdeeTreeNode node,int dep) throws EASBizException, BOSException{
		if(node!=null){
			Object obj = node.getUserObject();
			if(obj instanceof OrgStructureInfo||obj instanceof SellProjectInfo){
				IRow row=this.tblMain.addRow();
				this.tblMain.setRowCount(this.tblMain.getRowCount()+1);
				row.setTreeLevel(dep);
				row.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
				if(obj instanceof SellProjectInfo){
					String sellProjectStr=SHEManageHelper.getStringFromSet(SHEManageHelper.getAllSellProjectCollection(null,(SellProjectInfo)obj));
					if(((DefaultKingdeeTreeNode)node.getParent()).getUserObject() instanceof OrgStructureInfo){
						row.getCell("sellProjectId").setValue(sellProjectStr+"*PSP*");
					}else{
						row.getCell("sellProjectId").setValue(sellProjectStr+"*SP*");
					}
					row.getCell("sellProjectName").setValue(((SellProjectInfo)obj).getName());
				}else{
					String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
					row.getCell("sellProjectId").setValue(allSpIdStr+"*ORG*");
					row.getCell("sellProjectName").setValue(((OrgStructureInfo)obj).getUnit().getName());
				}
				++dep;
			}
			for (int i = 0; i < node.getChildCount(); i++) {
				addSumRow((DefaultKingdeeTreeNode) node.getChildAt(i),dep);
			}
		}
	}
	public IRow getParentRow(String sellProjectId){
		for(int i=0;i<this.tblMain.getRowCount();i++){
			IRow row=this.tblMain.getRow(i);
			if(row.getCell("sellProjectId").getValue()!=null&&!"".equals(row.getCell("sellProjectId").getValue().toString().trim())
					&&row.getCell("sellProjectId").getValue().toString().indexOf(",")<0&&row.getCell("sellProjectId").getValue().toString().indexOf(sellProjectId)>0){
				return row;
			}
		}
		return null;
	}
	private BigDecimal getAverage(BigDecimal amount,BigDecimal lastAmount){
		if(amount==null) amount=FDCHelper.ZERO;
		if(lastAmount==null||lastAmount.compareTo(FDCHelper.ZERO)==0) return FDCHelper.ZERO;
		
		return (amount.subtract(lastAmount)).multiply(new BigDecimal(100)).divide(lastAmount, 2,BigDecimal.ROUND_HALF_UP);
	}
	public void sum(){
		for(int i=this.tblMain.getRowCount()-1;i>=0;i--){
			IRow row=this.tblMain.getRow(i);
			String id=row.getCell("sellProjectId").getValue().toString();
			if(id.indexOf("'")<0){
				for(int j=0;j<i;j++){
					IRow sumRow=this.tblMain.getRow(j);
					if(sumRow.getCell("sellProjectId").getValue().toString().indexOf(id)>0&&sumRow.getCell("sellProjectId").getValue().toString().indexOf("'")==0){
						SHEManageHelper.addSumColoum(sumRow,row,new String[]{"amount","buildArea","lastYearBuildArea","lastMonthBuildArea","roomArea","lastYearRoomArea","lastMonthRoomArea","account","lastYearAccount","lastMonthAccount","revAccount"});
						sumRow.getCell("roomPrice").setValue(((BigDecimal)sumRow.getCell("roomArea").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("account").getValue()).divide((BigDecimal)sumRow.getCell("roomArea").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("buildPrice").setValue(((BigDecimal)sumRow.getCell("buildArea").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("account").getValue()).divide((BigDecimal)sumRow.getCell("buildArea").getValue(), 2,BigDecimal.ROUND_HALF_UP));
				    	
						sumRow.getCell("buildAreaT").setValue(getAverage((BigDecimal)sumRow.getCell("buildArea").getValue(),(BigDecimal)sumRow.getCell("lastYearBuildArea").getValue()));
				    	sumRow.getCell("buildAreaH").setValue(getAverage((BigDecimal)sumRow.getCell("buildArea").getValue(),(BigDecimal)sumRow.getCell("lastMonthBuildArea").getValue()));
				    	sumRow.getCell("roomAreaT").setValue(getAverage((BigDecimal)sumRow.getCell("roomArea").getValue(),(BigDecimal)sumRow.getCell("lastYearRoomArea").getValue()));
				    	sumRow.getCell("roomAreaH").setValue(getAverage((BigDecimal)sumRow.getCell("roomArea").getValue(),(BigDecimal)sumRow.getCell("lastMonthRoomArea").getValue()));
				    	sumRow.getCell("accountT").setValue(getAverage((BigDecimal)sumRow.getCell("account").getValue(),(BigDecimal)sumRow.getCell("lastYearAccount").getValue()));
				    	sumRow.getCell("accountH").setValue(getAverage((BigDecimal)sumRow.getCell("account").getValue(),(BigDecimal)sumRow.getCell("lastMonthAccount").getValue()));
				    	sumRow.getCell("buildPriceT").setValue(getAverage((BigDecimal)sumRow.getCell("buildPrice").getValue(),((BigDecimal)sumRow.getCell("lastYearBuildArea").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("lastYearAccount").getValue()).divide((BigDecimal)sumRow.getCell("lastYearBuildArea").getValue(), 2,BigDecimal.ROUND_HALF_UP)));
				    	sumRow.getCell("buildPriceH").setValue(getAverage((BigDecimal)sumRow.getCell("buildPrice").getValue(),((BigDecimal)sumRow.getCell("lastMonthBuildArea").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("lastMonthAccount").getValue()).divide((BigDecimal)sumRow.getCell("lastMonthBuildArea").getValue(), 2,BigDecimal.ROUND_HALF_UP)));
				    	sumRow.getCell("roomPriceT").setValue(getAverage((BigDecimal)sumRow.getCell("roomPrice").getValue(),((BigDecimal)sumRow.getCell("lastYearRoomArea").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("lastYearAccount").getValue()).divide((BigDecimal)sumRow.getCell("lastYearRoomArea").getValue(), 2,BigDecimal.ROUND_HALF_UP)));
				    	sumRow.getCell("roomPriceH").setValue(getAverage((BigDecimal)sumRow.getCell("roomPrice").getValue(),((BigDecimal)sumRow.getCell("lastMonthRoomArea").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("lastMonthAccount").getValue()).divide((BigDecimal)sumRow.getCell("lastMonthRoomArea").getValue(), 2,BigDecimal.ROUND_HALF_UP)));
				    		
					}
				}
			}
		}
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
        try {
        	try {
    			initTree();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
        	RptParams pp=(RptParams)params.clone();
        	pp.setObject("tree", null);
        	
        	RptParams rpt = getRemoteInstance().createTempTable(pp);
            RptTableHeader header = (RptTableHeader)rpt.getObject("header");
            KDTableUtil.setHeader(header, tblMain);
        	if(treeNode!=null){
    	        rpt = getRemoteInstance().query(pp);
    	        tblMain.setRowCount(rpt.getInt("count")+tblMain.getRowCount());
    	        RptRowSet rs = (RptRowSet)rpt.getObject("rowset");
    	       
    	        if(!treeNode.isLeaf()){
    	        	tblMain.getTreeColumn().setDepth(treeNode.getDepth()-1);
    		        addSumRow(treeNode,0);
    	        }
    	        tblMain.setRefresh(false);
    	        IRow row;
    	        while(rs.next()){
            		if(treeNode.isLeaf()){
    	        		IRow addRow=tblMain.addRow();
            			((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(addRow, rs.toRowArray());
    	        	}else{
    	        		String id=rs.getString("sellProjectId");
    	        		row=getParentRow(id);
    		        	if(row!=null){
    		        		IRow addRow=tblMain.addRow(row.getRowIndex()+1);
    	        			((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(addRow, rs.toRowArray());
            				addRow.setTreeLevel(treeNode.getDepth()-1);
    		        	}
    	        	}
    	        }
    	        tblMain.setRefresh(true);
    	        if(rs.getRowCount() > 0)
    	        	tblMain.reLayoutAndPaint();
    	        else
    	        	tblMain.repaint();
        	}else{
                tblMain.setRowCount(0);
        	}
        } catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	public void onLoad() throws Exception {
		setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tree=(TreeModel) params.getObject("tree");
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
	}
	protected void initTree() throws Exception{
		if((tree==null&&params.getObject("tree")!=null)||(tree!=null&&params.getObject("tree")==null)||(tree!=null&&params.getObject("tree")!=null&&!tree.equals(params.getObject("tree")))||(tree==null&&params.getObject("tree")==null&&!this.isShowing())){
			if(params.getObject("tree")!=null){
				tree=(TreeModel) params.getObject("tree");
				this.treeMain.setModel((TreeModel) params.getObject("tree"));
			}else{
				this.treeMain.setModel(TimeAccountReportFilterUI.getSellProjectForSHESellProject(actionOnLoad, MoneySysTypeEnum.SalehouseSys,true));
			}
		    this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		    SHEManageHelper.getProductTypeNodes(this.treeMain,(DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
		}
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				params.setObject("sellProject", null);
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				if(allSpIdStr.trim().length()!=0){
					params.setObject("org", allSpIdStr);
				}else{
					params.setObject("org", null);
				}
			}else if(obj instanceof SellProjectInfo){
				params.setObject("sellProject", treeNode.getUserObject());
				params.setObject("productType", null);
			}else if(obj instanceof ProductTypeInfo){
				params.setObject("sellProject", ((DefaultKingdeeTreeNode)treeNode.getParent()).getUserObject());
				params.setObject("productType", treeNode.getUserObject());
			}
			query();
		}
	}

}