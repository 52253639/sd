/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;

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
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.DynamicCostDiffFacadeFactory;
import com.kingdee.eas.fdc.contract.ProjectDynamicCostFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;

/**
 * output class name
 */
public class DynamicCostDiffReportUI extends AbstractDynamicCostDiffReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(DynamicCostDiffReportUI.class);
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    IUIWindow uiWindow=null;
    public DynamicCostDiffReportUI() throws Exception
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
		return new DynamicCostDiffReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return DynamicCostDiffFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
		
		Set indexSet = new HashSet();
		for(int i=0;i<tblMain.getRowCount();i++){
			boolean isDelete=true;
			IRow row=tblMain.getRow(i);
			String number=row.getCell("number").getValue().toString();
			int isLeaf=Integer.parseInt(row.getCell("isLeaf").getValue().toString());
			if(isLeaf==1){
				continue;
			}
			for(int j=i+1;j<tblMain.getRowCount();j++){
				IRow nrow=tblMain.getRow(j);
				String nnumber=nrow.getCell("number").getValue().toString();
				int nisLeaf=Integer.parseInt(nrow.getCell("isLeaf").getValue().toString());
				if(nisLeaf==1){
					if(nnumber.indexOf(number)==0){
						isDelete=false;
					}
					break;
				}
			}
			if(isDelete){
				indexSet.add(i);
			}
		}
		Integer[] indexArr = new Integer[indexSet.size()];
		Object[] indexObj = indexSet.toArray();
		System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
		Arrays.sort(indexArr);
		for (int i = indexArr.length - 1; i >= 0; i--) {
			int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
			tblMain.removeRow(rowIndex);
		}
		tblMain.getColumn("A").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("B").getStyleAttributes().setFontColor(Color.BLUE);
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"A","B","balance"});
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
                     
                     RptRowSet fromRowset = (RptRowSet)((RptParams)result).getObject("fromRowset");
                     RptRowSet toRowset = (RptRowSet)((RptParams)result).getObject("toRowset");
         	         tblMain.setRowCount(fromRowset.getRowCount()+tblMain.getRowCount());
         	         tblMain.setRefresh(false);
         	         int max=1;
         	         Map sumMap=new HashMap();
         	         Map isAddRow=new HashMap();
         	         int seq=0;
         	         while(fromRowset.next()){
         	        	 String id=fromRowset.getString("id");
         	        	 String number=fromRowset.getString("number");
         	        	 int isLeaf=fromRowset.getInt("isLeaf");
        	        	 int level=fromRowset.getInt("levelNumber")-1;
         	        	 BigDecimal amount=FDCHelper.ZERO;
        	        	 if(isLeaf==1){
        	        		 amount=fromRowset.getBigDecimal("amount")==null?FDCHelper.ZERO:fromRowset.getBigDecimal("amount");
        	        	 }
         	        	 BigDecimal contractAmount=fromRowset.getBigDecimal("contractAmount")==null?FDCHelper.ZERO:fromRowset.getBigDecimal("contractAmount");
         	        	 BigDecimal supplyAmount=fromRowset.getBigDecimal("supplyAmount")==null?FDCHelper.ZERO:fromRowset.getBigDecimal("supplyAmount");
         	        	 BigDecimal contractWTAmount=fromRowset.getBigDecimal("contractWTAmount")==null?FDCHelper.ZERO:fromRowset.getBigDecimal("contractWTAmount");
         	        	 BigDecimal CONFIRM=fromRowset.getBigDecimal("CONFIRM")==null?FDCHelper.ZERO:fromRowset.getBigDecimal("CONFIRM");
         	        	 BigDecimal UNCONFIRM=fromRowset.getBigDecimal("UNCONFIRM")==null?FDCHelper.ZERO:fromRowset.getBigDecimal("UNCONFIRM");
         	        	 BigDecimal estimateAmount=fromRowset.getBigDecimal("estimateAmount")==null?FDCHelper.ZERO:fromRowset.getBigDecimal("estimateAmount");
         	        	 BigDecimal settleAmount=fromRowset.getBigDecimal("settleAmount")==null?FDCHelper.ZERO:fromRowset.getBigDecimal("settleAmount");
         	        	 BigDecimal dynamicTotalAmount=FDCHelper.ZERO;
         	        	 Boolean isContract=fromRowset.getBoolean("isContract", false);
        	       	 	 Boolean isSettle=fromRowset.getBoolean("isSettle",false);
        	       	 	 if(isSettle)estimateAmount=FDCHelper.ZERO;
         	        	 if(level+1>max){
         	        		 max=level+1;
         	        	 }
         	        	 IRow row=null;
         	        	 if(isAddRow.containsKey(id)){
         	        		 row=(IRow) isAddRow.get(id);
         	        	 }else{
         	        		 row=tblMain.addRow();
         	        		 isAddRow.put(id, row);
         	        	 }
         	        	 row.setTreeLevel(level);
         	        	 row.getCell("id").setValue(id);
         	        	 row.getCell("isLeaf").setValue(isLeaf);
         	        	 row.getCell("levelNumber").setValue(level);
         	        	 row.getCell("number").setValue(number);
         	        	 if(seq==0){
         	        		row.getCell("name").setValue("四项成本");
         	        	 }else{
         	        		row.getCell("name").setValue(fromRowset.getString("name"));
         	        	 }
         	        	 
         	        	 if(isLeaf==1){
         	        		if(isSettle){
          	        			 dynamicTotalAmount=settleAmount.add(contractWTAmount);
          	        		 }else{
          	        			if(isContract){
        	           				dynamicTotalAmount=contractAmount.add(supplyAmount).add(contractWTAmount).add(CONFIRM).add(UNCONFIRM).add(estimateAmount);
        	           			 }else{
        	           				dynamicTotalAmount=amount;
        	           			 }
          	        		 }
	           	        	 row.getCell("A").setValue(dynamicTotalAmount);
         	        	 }else{
         	        		 row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
         	        	 }
        	        	 sumMap.put(number, row);
        	        	 
        	        	 if(number.indexOf(".")>0){
        	        		 String pnumber=number.substring(0, number.lastIndexOf("."));
        	        		 for(int k=0;k<level;k++){
        	        			 if(sumMap.get(pnumber)!=null){
        	        				 IRow prow=(IRow) sumMap.get(pnumber);
        	        				 if(prow.getCell("A").getValue()!=null){
        	        					 prow.getCell("A").setValue(((BigDecimal)prow.getCell("A").getValue()).add(dynamicTotalAmount));
        	        				 }else{
        	        					 prow.getCell("A").setValue(dynamicTotalAmount);
             	        			 }
             	        		 }
             	        		 if(pnumber.indexOf(".")>0){
             	        			 pnumber=pnumber.substring(0, pnumber.lastIndexOf("."));
             	        		 }
             	        	 }
         	        	 }
        	        	 seq=seq+1;
         	         }
         	         while(toRowset.next()){
        	        	 String id=toRowset.getString("id");
        	        	 String number=toRowset.getString("number");
        	        	 int isLeaf=toRowset.getInt("isLeaf");
        	        	 int level=toRowset.getInt("levelNumber")-1;
        	        	 BigDecimal amount=FDCHelper.ZERO;
        	        	 if(isLeaf==1){
        	        		 amount=toRowset.getBigDecimal("amount")==null?FDCHelper.ZERO:toRowset.getBigDecimal("amount");
        	        	 }
        	        	 BigDecimal contractAmount=toRowset.getBigDecimal("contractAmount")==null?FDCHelper.ZERO:toRowset.getBigDecimal("contractAmount");
        	        	 BigDecimal supplyAmount=toRowset.getBigDecimal("supplyAmount")==null?FDCHelper.ZERO:toRowset.getBigDecimal("supplyAmount");
        	        	 BigDecimal contractWTAmount=toRowset.getBigDecimal("contractWTAmount")==null?FDCHelper.ZERO:toRowset.getBigDecimal("contractWTAmount");
        	        	 BigDecimal CONFIRM=toRowset.getBigDecimal("CONFIRM")==null?FDCHelper.ZERO:toRowset.getBigDecimal("CONFIRM");
        	        	 BigDecimal UNCONFIRM=toRowset.getBigDecimal("UNCONFIRM")==null?FDCHelper.ZERO:toRowset.getBigDecimal("UNCONFIRM");
        	        	 BigDecimal estimateAmount=toRowset.getBigDecimal("estimateAmount")==null?FDCHelper.ZERO:toRowset.getBigDecimal("estimateAmount");
        	        	 BigDecimal settleAmount=toRowset.getBigDecimal("settleAmount")==null?FDCHelper.ZERO:toRowset.getBigDecimal("settleAmount");
        	        	 BigDecimal dynamicTotalAmount=FDCHelper.ZERO;
        	        	 Boolean isContract=toRowset.getBoolean("isContract", false);
        	       	 	 Boolean isSettle=toRowset.getBoolean("isSettle",false);
        	       	     if(isSettle)estimateAmount=FDCHelper.ZERO;
        	        	 IRow row=null;
        	        	 if(isAddRow.containsKey(id)){
        	        		 row=(IRow) isAddRow.get(id);
        	        	 }else{
        	        		 continue;
        	        	 }
        	        	 if(isLeaf==1){
	       	        		 if(isSettle){
	       	        			 dynamicTotalAmount=settleAmount.add(contractWTAmount);
	       	        		 }else{
	       	        			if(isContract){
        	           				dynamicTotalAmount=contractAmount.add(supplyAmount).add(contractWTAmount).add(CONFIRM).add(UNCONFIRM).add(estimateAmount);
        	           			 }else{
        	           				dynamicTotalAmount=amount;
        	           			 }
	       	        		 }
    	       	        	 BigDecimal A=(BigDecimal) row.getCell("A").getValue();
    	       	        	 row.getCell("B").setValue(dynamicTotalAmount);
    	       	        	 
    	       	        	 row.getCell("balance").setValue(A.subtract(dynamicTotalAmount));
            	        	 if(((BigDecimal)row.getCell("balance").getValue()).compareTo(FDCHelper.ZERO)<0){
            	        		 row.getCell("balance").getStyleAttributes().setFontColor(Color.RED);
            	        	 }else{
            	        		 row.getCell("balance").getStyleAttributes().setFontColor(Color.BLUE);
            	        	 }
        	        	 }
	       	        	 if(number.indexOf(".")>0){
	       	        		 String pnumber=number.substring(0, number.lastIndexOf("."));
	       	        		 for(int k=0;k<level;k++){
	       	        			 if(sumMap.get(pnumber)!=null){
	       	        				 IRow prow=(IRow) sumMap.get(pnumber);
	       	        				 if(prow.getCell("B").getValue()!=null){
	       	        					 prow.getCell("B").setValue(((BigDecimal)prow.getCell("B").getValue()).add(dynamicTotalAmount));
	       	        				 }else{
	       	        					 prow.getCell("B").setValue(dynamicTotalAmount);
	        	        			 }
	       	        				 BigDecimal totalA=(BigDecimal) prow.getCell("A").getValue();
	       	        				 BigDecimal totalB=(BigDecimal) prow.getCell("B").getValue();
	       	        				 prow.getCell("balance").setValue(totalA.subtract(totalB));
        	        	        	 if(((BigDecimal)prow.getCell("balance").getValue()).compareTo(FDCHelper.ZERO)<0){
        	        	        		 prow.getCell("balance").getStyleAttributes().setFontColor(Color.RED);
        	        	        	 }else{
        	        	        		 prow.getCell("balance").getStyleAttributes().setFontColor(Color.BLUE);
        	        	        	 }
	        	        		 }
            	        		 if(pnumber.indexOf(".")>0){
            	        			 pnumber=pnumber.substring(0, pnumber.lastIndexOf("."));
            	        		 }
            	        	 }
        	        	 }
        	         }
         	         tblMain.setRefresh(true);
         	         if(tblMain.getRowCount() > 0){
         	        	tblMain.getTreeColumn().setDepth(max);
         	        	tblMain.expandTreeColumnTo(1);
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
	protected void buildOrgTree() throws Exception{
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, this.treeMain, actionOnLoad);
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
    	DefaultKingdeeTreeNode node = this.getSelectedTreeNode(this.treeMain);
    	if(node.getUserObject() instanceof CurProjectInfo){
    		CurProjectInfo curProject=(CurProjectInfo)node.getUserObject();
    		params.setObject("curProject", curProject);
    	}else{
    		params.setObject("curProject", null);
    	}
    	query();
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh();
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
		setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		buildOrgTree();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		isOnLoad=false;
		
		this.refresh();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row=this.tblMain.getRow(e.getRowIndex());
			Object amount=row.getCell(e.getColIndex()).getValue();
			if(amount==null||!(amount instanceof BigDecimal)||((BigDecimal)amount).compareTo(FDCHelper.ZERO)==0){
				return;
			}
			RptParams param=new RptParams();
			Date fromDate = (Date)params.getObject("fromDate");
		    Date toDate =   (Date)params.getObject("toDate");
		    Date auditDate=null;
		    String title="";
		    if(this.tblMain.getColumnKey(e.getColIndex()).equals("A")){
		    	auditDate=fromDate;
		    	title=row.getCell("name").getValue().toString()+" - "+FDCDateHelper.formatDate2(auditDate);
		    }else if(this.tblMain.getColumnKey(e.getColIndex()).equals("B")){
		    	auditDate=toDate;
		    	title=row.getCell("name").getValue().toString()+" - "+FDCDateHelper.formatDate2(auditDate);
		    }else if(this.tblMain.getColumnKey(e.getColIndex()).equals("balance")){
		    	param.setObject("fromDate", fromDate);
		    	param.setObject("toDate", toDate);
		    	title=row.getCell("name").getValue().toString()+" - 差异(A-B)";
		    }else{
		    	return;
		    }
		    if(uiWindow!=null){
				uiWindow.close();
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			
			param.setObject("auditDate", auditDate);
			param.setObject("longNumber", row.getCell("number").getValue().toString());
			param.setObject("curProject", params.getObject("curProject"));
			uiContext.put("RPTFilter", param);
			uiContext.put("title", title);
			uiContext.put("name", row.getCell("name").getValue().toString());
			uiContext.put("levelNumber", row.getCell("levelNumber").getValue());
			uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(DynamicCostControlReportUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

}