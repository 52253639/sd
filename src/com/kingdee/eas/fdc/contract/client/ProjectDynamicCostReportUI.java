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
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.DynamicCostControlFacadeFactory;
import com.kingdee.eas.fdc.contract.ProjectDynamicCostFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ProjectDynamicCostReportUI extends AbstractProjectDynamicCostReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectDynamicCostReportUI.class);
    
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    private BigDecimal addRowTotalBuildArea=FDCHelper.ZERO;
    private BigDecimal addRowTotalSellArea=FDCHelper.ZERO;
    private Map sumMap=new HashMap();
    public ProjectDynamicCostReportUI() throws Exception
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
		return null;
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return ProjectDynamicCostFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
		
		for(int i=0;i<tblMain.getColumnCount();i++){
			String key=tblMain.getColumnKey(i);
			if(key.equals("amount")||key.indexOf("Amount")>0||key.equals("balance")||key.equals("rate")){
				CRMClientHelper.changeTableNumberFormat(tblMain,key);
			}
		}
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
//		try {
//			if(tblMain.getRowCount()>0){
//				addAimCostRow(1,"5001!01!01","土地成本",2,addRowTotalBuildArea,addRowTotalSellArea,sumMap);
//	        	addAimCostRow(0,"5001!01!06","开发间接费",2,addRowTotalBuildArea,addRowTotalSellArea,sumMap);
//	        	addAimCostRow(0,"5001!03","项目管理费用",1,addRowTotalBuildArea,addRowTotalSellArea,sumMap);
//	        	addAimCostRow(0,"5001!04","项目财务费用",1,addRowTotalBuildArea,addRowTotalSellArea,sumMap);
//	        	addAimCostRow(0,"5001!05","项目销售费用",1,addRowTotalBuildArea,addRowTotalSellArea,sumMap);
//	        }
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
		tblMain.getColumn("rate").setRenderer(render_scale);
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
                     
                     RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
         	         tblMain.setRowCount(rs.getRowCount()+tblMain.getRowCount());
         	         tblMain.setRefresh(false);
         	         int max=1;
         	         int seq=0;
         	         sumMap=new HashMap();
         	         while(rs.next()){
         	        	 String id=rs.getString("id");
         	        	 String number=rs.getString("number");
         	        	 int isLeaf=rs.getInt("isLeaf");
        	        	 int level=rs.getInt("levelNumber")-1;
        	        	 BigDecimal amount=FDCHelper.ZERO;
         	        	 if(isLeaf==1){
         	        		amount=rs.getBigDecimal("amount")==null?FDCHelper.ZERO:rs.getBigDecimal("amount");
        	        	 }
         	        	 BigDecimal contractAmount=rs.getBigDecimal("contractAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("contractAmount");
         	        	 BigDecimal supplyAmount=rs.getBigDecimal("supplyAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("supplyAmount");
         	        	 BigDecimal contractWTAmount=rs.getBigDecimal("contractWTAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("contractWTAmount");
         	        	 BigDecimal CONFIRM=rs.getBigDecimal("CONFIRM")==null?FDCHelper.ZERO:rs.getBigDecimal("CONFIRM");
         	        	 BigDecimal UNCONFIRM=rs.getBigDecimal("UNCONFIRM")==null?FDCHelper.ZERO:rs.getBigDecimal("UNCONFIRM");
         	        	 BigDecimal estimateAmount=rs.getBigDecimal("estimateAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("estimateAmount");
         	        	 BigDecimal settleAmount=rs.getBigDecimal("settleAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("settleAmount");
         	        	 BigDecimal dynamicTotalAmount=FDCHelper.ZERO;
         	        	 
         	        	 BigDecimal totalBuildArea=rs.getBigDecimal("totalBuildArea")==null?FDCHelper.ZERO:rs.getBigDecimal("totalBuildArea");
         	        	 BigDecimal totalSellArea=rs.getBigDecimal("totalSellArea")==null?FDCHelper.ZERO:rs.getBigDecimal("totalSellArea");
         	        	 addRowTotalBuildArea=totalBuildArea;
         	        	 addRowTotalSellArea=totalSellArea;
         	        	 if(level+1>max){
         	        		 max=level+1;
         	        	 }
         	        	 IRow row=tblMain.addRow();
         	        	 row.setTreeLevel(level);
         	        	 row.getCell("id").setValue(id);
         	        	 row.getCell("isLeaf").setValue(isLeaf);
         	        	 row.getCell("levelNumber").setValue(level);
         	        	 row.getCell("number").setValue(number);
         	        	 if(seq==0){
         	        		row.getCell("name").setValue("项目开发成本");
         	        	 }else{
         	        		row.getCell("name").setValue(rs.getString("name"));
         	        	 }
         	        	 row.getCell("amount").setValue(amount);
         	        	 
         	        	 if(isLeaf==1){
         	        		 if(contractAmount.compareTo(FDCHelper.ZERO)>0){
	           	        		 if(settleAmount.compareTo(FDCHelper.ZERO)>0){
	           	        			 dynamicTotalAmount=settleAmount.add(contractWTAmount);
	           	        		 }else{
	           	        			 dynamicTotalAmount=contractAmount.add(supplyAmount).add(contractWTAmount).add(CONFIRM).add(UNCONFIRM).add(estimateAmount);
	           	        		 }
	           	        	 }else{
	           	        		 dynamicTotalAmount=amount;
	           	        	 }
	           	        	 row.getCell("dynamicTotalAmount").setValue(dynamicTotalAmount);
	           	        	 if(totalBuildArea.compareTo(FDCHelper.ZERO)==0){
	         	        		 row.getCell("dynamicBuildAmount").setValue(FDCHelper.ZERO);
	         	        	 }else{
	         	        		 row.getCell("dynamicBuildAmount").setValue(dynamicTotalAmount.divide(totalBuildArea,2,BigDecimal.ROUND_HALF_UP));
	         	        	 }
	         	        	 if(totalSellArea.compareTo(FDCHelper.ZERO)==0){
	        	        		 row.getCell("dynamicSellAmount").setValue(FDCHelper.ZERO);
	        	        	 }else{
	        	        		 row.getCell("dynamicSellAmount").setValue(dynamicTotalAmount.divide(totalSellArea,2,BigDecimal.ROUND_HALF_UP));
	        	        	 }
	        	        	 row.getCell("balance").setValue(dynamicTotalAmount.subtract(amount));
	        	        	 if(((BigDecimal)row.getCell("balance").getValue()).compareTo(FDCHelper.ZERO)<0){
	        	        		 row.getCell("balance").getStyleAttributes().setFontColor(Color.RED);
	        	        	 }
	        	        	 if(amount.compareTo(FDCHelper.ZERO)==0){
	        	        		 row.getCell("rate").setValue(FDCHelper.ZERO);
	        	        	 }else{
	        	        		 row.getCell("rate").setValue((dynamicTotalAmount.subtract(amount)).divide(amount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
	        	        	 }
	        	        	 if(((BigDecimal)row.getCell("rate").getValue()).compareTo(FDCHelper.ZERO)<0){
	        	        		 row.getCell("rate").getStyleAttributes().setFontColor(Color.RED);
	        	        	 }
         	        	 }else{
         	        		 row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
         	        	 }
        	        	 if(totalBuildArea.compareTo(FDCHelper.ZERO)==0){
         	        		 row.getCell("aimBuildAmount").setValue(FDCHelper.ZERO);
         	        	 }else{
         	        		 row.getCell("aimBuildAmount").setValue(amount.divide(totalBuildArea,2,BigDecimal.ROUND_HALF_UP));
         	        	 }
         	        	 if(totalSellArea.compareTo(FDCHelper.ZERO)==0){
        	        		 row.getCell("aimSellAmount").setValue(FDCHelper.ZERO);
        	        	 }else{
        	        		 row.getCell("aimSellAmount").setValue(amount.divide(totalSellArea,2,BigDecimal.ROUND_HALF_UP));
        	        	 }
        	        	 sumMap.put(number, row);
        	        	 
        	        	 if(number.indexOf(".")>0){
        	        		 String pnumber=number.substring(0, number.lastIndexOf("."));
        	        		 for(int k=0;k<level;k++){
        	        			 if(sumMap.get(pnumber)!=null){
        	        				 IRow prow=(IRow) sumMap.get(pnumber);
        	        				 if(prow.getCell("amount").getValue()!=null){
        	        					 prow.getCell("amount").setValue(((BigDecimal)prow.getCell("amount").getValue()).add(amount));
        	        				 }else{
        	        					 prow.getCell("amount").setValue(amount);
             	        			 }
        	        				 if(prow.getCell("dynamicTotalAmount").getValue()!=null){
        	        					 prow.getCell("dynamicTotalAmount").setValue(((BigDecimal)prow.getCell("dynamicTotalAmount").getValue()).add(dynamicTotalAmount));
        	        				 }else{
        	        					 prow.getCell("dynamicTotalAmount").setValue(dynamicTotalAmount);
             	        			 }
        	        				 BigDecimal totalDynamicTotalAmount=(BigDecimal) prow.getCell("dynamicTotalAmount").getValue();
        	        				 BigDecimal totalAmount=(BigDecimal) prow.getCell("amount").getValue();
        	        				 if(totalBuildArea.compareTo(FDCHelper.ZERO)==0){
        	        					 prow.getCell("dynamicBuildAmount").setValue(FDCHelper.ZERO);
        	         	        	 }else{
        	         	        		 prow.getCell("dynamicBuildAmount").setValue(totalDynamicTotalAmount.divide(totalBuildArea,2,BigDecimal.ROUND_HALF_UP));
        	         	        	 }
        	         	        	 if(totalSellArea.compareTo(FDCHelper.ZERO)==0){
        	         	        		 prow.getCell("dynamicSellAmount").setValue(FDCHelper.ZERO);
        	        	        	 }else{
        	        	        		 prow.getCell("dynamicSellAmount").setValue(totalDynamicTotalAmount.divide(totalSellArea,2,BigDecimal.ROUND_HALF_UP));
        	        	        	 }
        	         	        	 prow.getCell("balance").setValue(totalDynamicTotalAmount.subtract(totalAmount));
        	        	        	 if(((BigDecimal)prow.getCell("balance").getValue()).compareTo(FDCHelper.ZERO)<0){
        	        	        		 prow.getCell("balance").getStyleAttributes().setFontColor(Color.RED);
        	        	        	 }else{
        	        	        		 prow.getCell("balance").getStyleAttributes().setFontColor(Color.BLACK);
        	        	        	 }
        	        	        	 if(totalAmount.compareTo(FDCHelper.ZERO)==0){
        	        	        		 prow.getCell("rate").setValue(FDCHelper.ZERO);
        	        	        	 }else{
        	        	        		 prow.getCell("rate").setValue((totalDynamicTotalAmount.subtract(totalAmount)).divide(totalAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        	        	        	 }
        	        	        	 if(((BigDecimal)prow.getCell("rate").getValue()).compareTo(FDCHelper.ZERO)<0){
        	        	        		 prow.getCell("rate").getStyleAttributes().setFontColor(Color.RED);
        	        	        	 }else{
        	        	        		 prow.getCell("rate").getStyleAttributes().setFontColor(Color.BLACK);
        	        	        	 }
             	        		 }
             	        		 if(pnumber.indexOf(".")>0){
             	        			 pnumber=pnumber.substring(0, pnumber.lastIndexOf("."));
             	        		 }
             	        	 }
         	        	 }
        	        	 seq=seq+1;
         	         }
         	         tblMain.setRefresh(true);
         	         if(rs.getRowCount() > 0){
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
	private void addAimCostRow(int rowIndex,String longNumber,String name,int levelNumber,BigDecimal totalBuildArea,BigDecimal totalSellArea,Map sumMap) throws BOSException{
		DefaultKingdeeTreeNode node = this.getSelectedTreeNode(this.treeMain);
    	if(node.getUserObject() instanceof CurProjectInfo){
    		CurProjectInfo curProject=(CurProjectInfo)node.getUserObject();
    		
    		FDCSQLBuilder buider = new FDCSQLBuilder();
    		buider.appendSql("select sum(costEntry.FCostAmount) amount from T_AIM_CostEntry costEntry ");
    		buider.appendSql("left join T_FDC_CostAccount costAccount on costEntry.FCostAccountID = costAccount.FID ");
    		buider.appendSql("left join T_FDC_CurProject project on costAccount.FCurProject = project.FID ");
    		buider.appendSql("left join T_AIM_AimCost aimCost on aimCost.fid = costEntry.FHeadID ");
    		buider.appendSql("where costAccount.flongNumber like '" + longNumber + "%' ");
    		buider.appendSql("and aimCost.fOrgOrProId = '" + curProject.getId().toString() + "' ");
    		buider.appendSql("and aimCost.fstate = '4AUDITTED' and aimCost.fisLastVersion=1 ");
    		try {
    			IRow row=null;
    			if(rowIndex==1){
    				row=tblMain.addRow(rowIndex);
    			}else{
    				row=tblMain.addRow();
    			}
    			row.setTreeLevel(1);
    			row.getCell("id").setValue(longNumber.replaceAll("!", "."));
    			row.getCell("isLeaf").setValue(1);
    			row.getCell("levelNumber").setValue(levelNumber-1);
    			row.getCell("number").setValue(longNumber.replaceAll("!", "."));
        		row.getCell("name").setValue(name);

	        	BigDecimal dynamicTotalAmount=FDCHelper.ZERO;
	        	BigDecimal amount=FDCHelper.ZERO;
	        	IRowSet rowSet = buider.executeQuery();
    			if (rowSet.next() && rowSet.getBigDecimal("amount") != null) {
    				amount=rowSet.getBigDecimal("amount");
    			}
	        	row.getCell("amount").setValue(amount);
	        	row.getCell("dynamicTotalAmount").setValue(dynamicTotalAmount);
	        	
	        	if(totalBuildArea.compareTo(FDCHelper.ZERO)==0){
	        		row.getCell("dynamicBuildAmount").setValue(FDCHelper.ZERO);
	        	}else{
	        		row.getCell("dynamicBuildAmount").setValue(dynamicTotalAmount.divide(totalBuildArea,2,BigDecimal.ROUND_HALF_UP));
	        	}
	        	if(totalSellArea.compareTo(FDCHelper.ZERO)==0){
	        		row.getCell("dynamicSellAmount").setValue(FDCHelper.ZERO);
	        	}else{
	        		row.getCell("dynamicSellAmount").setValue(dynamicTotalAmount.divide(totalSellArea,2,BigDecimal.ROUND_HALF_UP));
	        	}
	        	row.getCell("balance").setValue(dynamicTotalAmount.subtract(amount));
	        	if(((BigDecimal)row.getCell("balance").getValue()).compareTo(FDCHelper.ZERO)<0){
	        		row.getCell("balance").getStyleAttributes().setFontColor(Color.RED);
	        	}
	        	if(amount.compareTo(FDCHelper.ZERO)==0){
	        		row.getCell("rate").setValue(FDCHelper.ZERO);
	        	}else{
	        		row.getCell("rate").setValue((dynamicTotalAmount.subtract(amount)).divide(amount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
	        	}
	        	if(((BigDecimal)row.getCell("rate").getValue()).compareTo(FDCHelper.ZERO)<0){
	        		row.getCell("rate").getStyleAttributes().setFontColor(Color.RED);
	        	}
	        	if(totalBuildArea.compareTo(FDCHelper.ZERO)==0){
	        		row.getCell("aimBuildAmount").setValue(FDCHelper.ZERO);
	        	}else{
	        		row.getCell("aimBuildAmount").setValue(amount.divide(totalBuildArea,2,BigDecimal.ROUND_HALF_UP));
	        	}
	        	if(totalSellArea.compareTo(FDCHelper.ZERO)==0){
	        		row.getCell("aimSellAmount").setValue(FDCHelper.ZERO);
	        	 	}else{
	        	 		row.getCell("aimSellAmount").setValue(amount.divide(totalSellArea,2,BigDecimal.ROUND_HALF_UP));
	        	 	}
	        	if(levelNumber==2){
	        		IRow prow=(IRow) sumMap.get("01");
	        		if(prow.getCell("amount").getValue()!=null){
						prow.getCell("amount").setValue(((BigDecimal)prow.getCell("amount").getValue()).add(amount));
					}else{
						prow.getCell("amount").setValue(amount);
	    			}
	        		BigDecimal totalDynamicTotalAmount=FDCHelper.ZERO;
	        		if(prow.getCell("dynamicTotalAmount").getValue()!=null){
	        			totalDynamicTotalAmount=(BigDecimal) prow.getCell("dynamicTotalAmount").getValue();
	        		}
					BigDecimal totalAmount=(BigDecimal) prow.getCell("amount").getValue();
					if(totalBuildArea.compareTo(FDCHelper.ZERO)==0){
						prow.getCell("aimBuildAmount").setValue(FDCHelper.ZERO);
		        	}else{
		        		prow.getCell("aimBuildAmount").setValue(totalAmount.divide(totalBuildArea,2,BigDecimal.ROUND_HALF_UP));
		        	}
		        	if(totalSellArea.compareTo(FDCHelper.ZERO)==0){
		        		prow.getCell("aimSellAmount").setValue(FDCHelper.ZERO);
		        	}else{
		        		prow.getCell("aimSellAmount").setValue(totalAmount.divide(totalSellArea,2,BigDecimal.ROUND_HALF_UP));
		        	}
		        	prow.getCell("balance").setValue(totalDynamicTotalAmount.subtract(totalAmount));
		        	if(((BigDecimal)prow.getCell("balance").getValue()).compareTo(FDCHelper.ZERO)<0){
		        		prow.getCell("balance").getStyleAttributes().setFontColor(Color.RED);
		        	}else{
		        		prow.getCell("balance").getStyleAttributes().setFontColor(Color.BLACK);
		        	}
		        	if(totalAmount.compareTo(FDCHelper.ZERO)==0){
		        		prow.getCell("rate").setValue(FDCHelper.ZERO);
		        	}else{
		        		prow.getCell("rate").setValue((totalDynamicTotalAmount.subtract(totalAmount)).divide(totalAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
		        	}
		        	if(((BigDecimal)prow.getCell("rate").getValue()).compareTo(FDCHelper.ZERO)<0){
		        		prow.getCell("rate").getStyleAttributes().setFontColor(Color.RED);
		        	}else{
		        		prow.getCell("rate").getStyleAttributes().setFontColor(Color.BLACK);
		        	}
	        	}
    		} catch (BOSException e) {
    			e.printStackTrace();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
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
		setShowDialogOnLoad(false);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		buildOrgTree();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		isOnLoad=false;
		
		this.actionQuery.setVisible(false);
		this.refresh();
	}

}