/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.DynamicCostControlFacadeFactory;
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
public class DynamicCostControlReportUI extends AbstractDynamicCostControlReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(DynamicCostControlReportUI.class);
    
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    public DynamicCostControlReportUI() throws Exception
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
		return DynamicCostControlFacadeFactory.getRemoteInstance();
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
			if(key.equals("amount")||key.indexOf("Amount")>0||key.indexOf("CONFIRM")>0||key.equals("absolute")||key.equals("rate")||key.equals("changeRate")||key.equals("payRate")){
				CRMClientHelper.changeTableNumberFormat(tblMain,key);
			}
			if(key.equals("contractAmount")||key.equals("supplyAmount")||key.equals("contractWTAmount")||key.indexOf("CONFIRM")>0||key.equals("estimateAmount")||key.equals("settleAmount")){
				tblMain.getColumn(i).getStyleAttributes().setFontColor(Color.BLUE);
			}
		}
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
		tblMain.getViewManager().freeze(0, 5);
		
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
		tblMain.getColumn("rate").setRenderer(render_scale);
		tblMain.getColumn("changeRate").setRenderer(render_scale);
		tblMain.getColumn("payRate").setRenderer(render_scale);
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    	if(treeNode!=null||isThrough){
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
                     Map changeMap=(HashMap)((RptParams)result).getObject("changeMap");
                     List changeTypeCol=(ArrayList)rpt.getObject("changeTypeCol");
                     tblMain.setRefresh(false);
         	         int max=1;
         	         
         	         // ad by shilei
         	        IColumn column = tblMain.addColumn(6);
         	       column.setKey("unitName");
         	      column.setWidth(200);
         	       tblMain.getHeadRow(0).getCell("unitName").setValue("同合作单位");
         	       tblMain.getHeadRow(1).getCell("unitName").setValue("同合作单位");
         	       tblMain.getHeadRow(2).getCell("unitName").setValue("同合作单位");
         	       tblMain.getHeadMergeManager().mergeBlock(0, 6,2, 6); 
         	         
         	         Map isAddRow=new HashMap();
                     if(rs!=null){
                    	 tblMain.setRowCount(rs.getRowCount()+tblMain.getRowCount());
                    	 max=addRow((RptParams)result,rs,changeTypeCol,changeMap,isAddRow);
                     }else{
                    	 RptRowSet fromRs = (RptRowSet)((RptParams)result).getObject("fromRowset");
                         RptRowSet toRs = (RptRowSet)((RptParams)result).getObject("toRowset");
                         
                    	 Map fromChangeMap=(HashMap)((RptParams)result).getObject("fromChangeMap");
                         Map toChangeMap=(HashMap)((RptParams)result).getObject("toChangeMap");
                          
                    	 tblMain.setRowCount(fromRs.getRowCount()+tblMain.getRowCount());
                    	 max=addRow((RptParams)result,fromRs,changeTypeCol,fromChangeMap,isAddRow);
                    	 
             	         Map sumMap=new HashMap();
             	         int seq=0;
             	         rs=toRs;
             	         while(rs.next()){
             	        	 String id=rs.getString("id");
             	        	 String number=rs.getString("number");
             	        	 int isLeaf=rs.getInt("isLeaf");
             	        	 int level=rs.getInt("levelNumber")-1;
             	        	 BigDecimal amount=FDCHelper.ZERO;
             	        	 if(isLeaf==1)
             	        		amount=rs.getBigDecimal("amount")==null?FDCHelper.ZERO:rs.getBigDecimal("amount");
             	       
             	        	 BigDecimal contractAmount=rs.getBigDecimal("contractAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("contractAmount");
             	        	 BigDecimal supplyAmount=rs.getBigDecimal("supplyAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("supplyAmount");
             	        	 BigDecimal contractWTAmount=rs.getBigDecimal("contractWTAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("contractWTAmount");
             	        	 BigDecimal totalCONFIRM=FDCHelper.ZERO;
             	        	 BigDecimal totalUNCONFIRM=FDCHelper.ZERO;
             	        	 BigDecimal estimateAmount=rs.getBigDecimal("estimateAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("estimateAmount");
             	        	 BigDecimal settleAmount=rs.getBigDecimal("settleAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("settleAmount");
             	        	 BigDecimal settleAdjustAmount=FDCHelper.ZERO;
             	        	 BigDecimal unContractAmount=FDCHelper.ZERO;
             	        	 BigDecimal dynamicTotalAmount=FDCHelper.ZERO;
             	        	 BigDecimal happenedAmount=FDCHelper.ZERO;
             	        	 BigDecimal payAmount=rs.getBigDecimal("payAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("payAmount");
             	        	 Boolean isContract=rs.getBoolean("isContract", false);
             	       	 	 Boolean isSettle=rs.getBoolean("isSettle",false);
             	       	     if(isSettle)estimateAmount=FDCHelper.ZERO;
             	        	 IRow row=null;
             	        	 if(isAddRow.containsKey(id)){
             	        		 row=(IRow) isAddRow.get(id);
             	        	 }else{
             	        		 continue;
             	        	 }
             	        	 row.getCell("contractAmount").setValue(FDCHelper.subtract(row.getCell("contractAmount").getValue(), contractAmount));
            	        	 row.getCell("supplyAmount").setValue(FDCHelper.subtract(row.getCell("supplyAmount").getValue(), supplyAmount));
            	        	 row.getCell("contractWTAmount").setValue(FDCHelper.subtract(row.getCell("contractWTAmount").getValue(), contractWTAmount));
            	        	 
            	        	 setColor(row.getCell("contractAmount"));
            	        	 setColor(row.getCell("supplyAmount"));
            	        	 setColor(row.getCell("contractWTAmount"));
            	        	 
            	        	 for(int i=0;i<changeTypeCol.size();i++){
            	        		 String key=changeTypeCol.get(i).toString();
            	        		 String mapKey=id+key;
            	        		 BigDecimal CONFIRM=FDCHelper.ZERO;
            	        		 BigDecimal UNCONFIRM=FDCHelper.ZERO;
            	        		 if(toChangeMap.containsKey(mapKey)){
            	        			 Map changeAmount=(HashMap)toChangeMap.get(mapKey);
            	        			 CONFIRM=changeAmount.get("CONFIRM")==null?FDCHelper.ZERO:(BigDecimal)changeAmount.get("CONFIRM");
            	        			 UNCONFIRM=changeAmount.get("UNCONFIRM")==null?FDCHelper.ZERO:(BigDecimal)changeAmount.get("UNCONFIRM");
            	        		 }
            	        		 totalCONFIRM=totalCONFIRM.add(CONFIRM);
        	        			 totalUNCONFIRM=totalUNCONFIRM.add(UNCONFIRM);
        	        			 
        	        			 row.getCell(key+"CONFIRM").setValue(FDCHelper.subtract(row.getCell(key+"CONFIRM").getValue(), CONFIRM));
        	        			 row.getCell(key+"UNCONFIRM").setValue(FDCHelper.subtract(row.getCell(key+"UNCONFIRM").getValue(), UNCONFIRM));
            	        	 
        	        			 setColor(row.getCell(key+"CONFIRM"));
        	        			 setColor(row.getCell(key+"UNCONFIRM"));
            	        	 }
            	        	 row.getCell("estimateAmount").setValue(FDCHelper.subtract(row.getCell("estimateAmount").getValue(), estimateAmount));
            	        	 row.getCell("settleAmount").setValue(FDCHelper.subtract(row.getCell("settleAmount").getValue(), settleAmount));
            	        	 row.getCell("payAmount").setValue(FDCHelper.subtract(row.getCell("payAmount").getValue(), payAmount));
            	        	 
            	        	 setColor(row.getCell("estimateAmount"));
    	        			 setColor(row.getCell("settleAmount"));
            	        	 if(isLeaf==1){
            	        		 if(isContract){
            	           			 unContractAmount=FDCHelper.ZERO;
            	            	 }else{
            	            		 unContractAmount=amount.subtract(contractWTAmount);
            	            	 }
            	           		 if(isSettle){
            	           			 dynamicTotalAmount=settleAmount.add(contractWTAmount);
            	           			 happenedAmount=settleAmount.add(contractWTAmount);
            	           			 settleAdjustAmount=settleAmount.subtract(contractAmount.add(supplyAmount).add(contractWTAmount).add(totalCONFIRM).add(totalUNCONFIRM));
            	           		 }else{
            	           			 if(isContract){
            	           				dynamicTotalAmount=contractAmount.add(supplyAmount).add(contractWTAmount).add(totalCONFIRM).add(totalUNCONFIRM).add(estimateAmount);
            	           			 }else{
            	           				dynamicTotalAmount=amount;
            	           			 }
            	           			 happenedAmount=contractAmount.add(supplyAmount).add(contractWTAmount).add(totalCONFIRM).add(totalUNCONFIRM);
            	           			 settleAdjustAmount=FDCHelper.ZERO;
            	           		 }
            	           		 row.getCell("settleAdjustAmount").setValue(FDCHelper.subtract(row.getCell("settleAdjustAmount").getValue(), settleAdjustAmount));
            	        		 row.getCell("unContractAmount").setValue(FDCHelper.subtract(row.getCell("unContractAmount").getValue(), unContractAmount));
                	        	 row.getCell("dynamicTotalAmount").setValue(FDCHelper.subtract(row.getCell("dynamicTotalAmount").getValue(), dynamicTotalAmount));
                	        	 row.getCell("happenedAmount").setValue(FDCHelper.subtract(row.getCell("happenedAmount").getValue(), happenedAmount));
            	        	 
                	        	 setColor(row.getCell("unContractAmount"));
        	        			 setColor(row.getCell("dynamicTotalAmount"));
        	        			 setColor(row.getCell("happenedAmount"));
            	        	 }else{
            	        		 row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
            	        	 }
            	        	 row.getCell("changeRate").getStyleAttributes().setFontColor(Color.BLACK);
            	        	 row.getCell("absolute").getStyleAttributes().setFontColor(Color.BLACK);
            	        	 row.getCell("rate").getStyleAttributes().setFontColor(Color.BLACK);
            	        	 row.getCell("payRate").getStyleAttributes().setFontColor(Color.BLACK);
            	        	 row.getCell("changeRate").setValue(FDCHelper.ZERO);
            	        	 row.getCell("absolute").setValue(FDCHelper.ZERO);
        	        		 row.getCell("rate").setValue(FDCHelper.ZERO);
        	        		 row.getCell("payRate").setValue(FDCHelper.ZERO);
            	        	 sumMap.put(number, row);
            	        	 
            	        	 if(number.indexOf(".")>0){
            	        		 String pnumber=number.substring(0, number.lastIndexOf("."));
            	        		 for(int k=0;k<level;k++){
            	        			 if(sumMap.get(pnumber)!=null){
            	        				 IRow prow=(IRow) sumMap.get(pnumber);
            	        				 prow.getCell("contractAmount").setValue(FDCHelper.subtract(prow.getCell("contractAmount").getValue(), contractAmount));
            	        				 prow.getCell("supplyAmount").setValue(FDCHelper.subtract(prow.getCell("supplyAmount").getValue(), supplyAmount));
            	        				 prow.getCell("contractWTAmount").setValue(FDCHelper.subtract(prow.getCell("contractWTAmount").getValue(), contractWTAmount));
            	        				 
            	        				 setColor(prow.getCell("contractAmount"));
                        	        	 setColor(prow.getCell("supplyAmount"));
                        	        	 setColor(prow.getCell("contractWTAmount"));
                        	        	 
                 	        			 for(int i=0;i<changeTypeCol.size();i++){
                 	        				 String key=changeTypeCol.get(i).toString();
                 	        				 BigDecimal CONFIRM=row.getCell(key+"CONFIRM").getValue()==null?FDCHelper.ZERO:(BigDecimal)row.getCell(key+"CONFIRM").getValue();
                        	        		 BigDecimal UNCONFIRM=row.getCell(key+"UNCONFIRM").getValue()==null?FDCHelper.ZERO:(BigDecimal)row.getCell(key+"UNCONFIRM").getValue();
                        	        		 
                        	        		 prow.getCell(key+"CONFIRM").setValue(FDCHelper.subtract(prow.getCell(key+"CONFIRM").getValue(), CONFIRM));
                	        				 prow.getCell(key+"UNCONFIRM").setValue(FDCHelper.subtract(prow.getCell(key+"UNCONFIRM").getValue(), UNCONFIRM));
    	             	        		 
                	        				 setColor(prow.getCell(key+"CONFIRM"));
                            	        	 setColor(prow.getCell(key+"UNCONFIRM"));
                 	        			 }
                 	        			 prow.getCell("estimateAmount").setValue(FDCHelper.subtract(prow.getCell("estimateAmount").getValue(), estimateAmount));
                 	        			 prow.getCell("settleAmount").setValue(FDCHelper.subtract(prow.getCell("settleAmount").getValue(), settleAmount));
                 	        			 prow.getCell("settleAdjustAmount").setValue(FDCHelper.subtract(prow.getCell("settleAdjustAmount").getValue(), settleAdjustAmount));
                 	        			 prow.getCell("payAmount").setValue(FDCHelper.subtract(prow.getCell("payAmount").getValue(), payAmount));
                 	        			 prow.getCell("unContractAmount").setValue(FDCHelper.subtract(prow.getCell("unContractAmount").getValue(), unContractAmount));
                	        			 prow.getCell("dynamicTotalAmount").setValue(FDCHelper.subtract(prow.getCell("dynamicTotalAmount").getValue(), dynamicTotalAmount));
                	        			 prow.getCell("happenedAmount").setValue(FDCHelper.subtract(prow.getCell("happenedAmount").getValue(), happenedAmount));
                 	        		
                	        			 setColor(prow.getCell("estimateAmount"));
                        	        	 setColor(prow.getCell("settleAmount"));
                        	        	 setColor(prow.getCell("unContractAmount"));
                        	        	 setColor(prow.getCell("dynamicTotalAmount"));
                        	        	 setColor(prow.getCell("happenedAmount"));
            	        			 }
                 	        		 if(pnumber.indexOf(".")>0){
                 	        			 pnumber=pnumber.substring(0, pnumber.lastIndexOf("."));
                 	        		 }
                 	        	 }
             	        	 }
            	        	 seq=seq+1;
             	         }
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
	protected void setColor(ICell cell){
		Object value=cell.getValue();
		if(value!=null&&value instanceof BigDecimal){
			if(((BigDecimal)value).compareTo(FDCHelper.ZERO)<0){
				cell.getStyleAttributes().setFontColor(Color.RED);
			}else{
				cell.getStyleAttributes().setFontColor(Color.BLACK);
			}
		}else{
			cell.getStyleAttributes().setFontColor(Color.BLACK);
		}
	}
	protected int addRow(RptParams result,RptRowSet rs,List changeTypeCol,Map changeMap,Map isAddRow){
		int max=1;
		Map sumMap=new HashMap();
        int seq=0;
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
    	 BigDecimal totalCONFIRM=FDCHelper.ZERO;
    	 BigDecimal totalUNCONFIRM=FDCHelper.ZERO;
    	 BigDecimal estimateAmount=rs.getBigDecimal("estimateAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("estimateAmount");
    	 BigDecimal settleAmount=rs.getBigDecimal("settleAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("settleAmount");
    	 BigDecimal settleAdjustAmount=FDCHelper.ZERO;
    	 BigDecimal unContractAmount=FDCHelper.ZERO;
    	 BigDecimal dynamicTotalAmount=FDCHelper.ZERO;
    	 BigDecimal happenedAmount=FDCHelper.ZERO;
    	 BigDecimal payAmount=rs.getBigDecimal("payAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("payAmount");
    	 BigDecimal totalAmount=FDCHelper.ZERO;
    	 Boolean isContract=rs.getBoolean("isContract", false);
     	 Boolean isSettle=rs.getBoolean("isSettle",false);
     	 if(isSettle)estimateAmount=FDCHelper.ZERO;
    	 IRow row=tblMain.addRow();
    	 
    	 isAddRow.put(id, row);
    	 if(getUIContext().get("levelNumber")!=null){
    		row.setTreeLevel(level-Integer.parseInt(getUIContext().get("levelNumber").toString()));
    		if(level-Integer.parseInt(getUIContext().get("levelNumber").toString())+1>max){
    			max=level-Integer.parseInt(getUIContext().get("levelNumber").toString())+1;
        	}
    	 }else{
    		row.setTreeLevel(level);
    		if(level+1>max){
    			max=level+1;
        	}
    	 }
    	 row.getCell("id").setValue(id);
    	 row.getCell("isLeaf").setValue(isLeaf);
    	 row.getCell("levelNumber").setValue(level);
    	 row.getCell("number").setValue(number);
    	 if(seq==0){
    		 if(getUIContext().get("name")!=null){
    			row.getCell("name").setValue(getUIContext().get("name"));
    		 }else{
    			row.getCell("name").setValue("四项成本");
    		 }
    	 }else{
    		row.getCell("name").setValue(rs.getString("name"));
    	 }
    	 row.getCell("amount").setValue(amount);
    	 row.getCell("contractAmount").setValue(contractAmount);
       	 row.getCell("supplyAmount").setValue(supplyAmount);
       	 row.getCell("contractWTAmount").setValue(contractWTAmount);
       	 
       	 
       //add by shilei 
    	 try {
    		 
    		if(!UIRuleUtil.getString(row.getCell("isLeaf").getValue()).equals("0")&&
    				(contractAmount.compareTo(BigDecimal.ZERO)!=0||contractWTAmount.compareTo(BigDecimal.ZERO)!=0))
    		{
    			String unitName = getContractPayUnit(id);
    			row.getCell("unitName").setValue(unitName);
    		}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
       	 
       	 
       	 for(int i=0;i<changeTypeCol.size();i++){
       		 String key=changeTypeCol.get(i).toString();
       		 String mapKey=id+key;
       		 BigDecimal CONFIRM=FDCHelper.ZERO;
       		 BigDecimal UNCONFIRM=FDCHelper.ZERO;
       		 if(changeMap.containsKey(mapKey)){
       			 Map changeAmount=(HashMap)changeMap.get(mapKey);
       			 CONFIRM=changeAmount.get("CONFIRM")==null?FDCHelper.ZERO:(BigDecimal)changeAmount.get("CONFIRM");
       			 UNCONFIRM=changeAmount.get("UNCONFIRM")==null?FDCHelper.ZERO:(BigDecimal)changeAmount.get("UNCONFIRM");
       		 }
       		 totalCONFIRM=totalCONFIRM.add(CONFIRM);
   			 totalUNCONFIRM=totalUNCONFIRM.add(UNCONFIRM);
   			 
   			 row.getCell(key+"CONFIRM").setValue(CONFIRM);
   			 row.getCell(key+"UNCONFIRM").setValue(UNCONFIRM);
       	 }
       	 row.getCell("estimateAmount").setValue(estimateAmount);
       	 row.getCell("settleAmount").setValue(settleAmount);
       	 row.getCell("payAmount").setValue(payAmount);
       	 if(isLeaf==1){
       		 if(isContract){
       			 unContractAmount=FDCHelper.ZERO;
        	 }else{
        		 unContractAmount=amount.subtract(contractWTAmount);
        	 }
       		 totalAmount=FDCHelper.ZERO;
       		 if(isSettle){
       			 dynamicTotalAmount=settleAmount.add(contractWTAmount);
       			 happenedAmount=settleAmount.add(contractWTAmount);
       			 totalAmount=settleAmount.add(contractWTAmount);
       			 settleAdjustAmount=settleAmount.subtract(contractAmount.add(supplyAmount).add(contractWTAmount).add(totalCONFIRM).add(totalUNCONFIRM));
       		 }else{
       			 if(isContract){
       				dynamicTotalAmount=contractAmount.add(supplyAmount).add(contractWTAmount).add(totalCONFIRM).add(totalUNCONFIRM).add(estimateAmount);
       			 }else{
       				dynamicTotalAmount=amount;
       			 }
       			 happenedAmount=contractAmount.add(supplyAmount).add(contractWTAmount).add(totalCONFIRM).add(totalUNCONFIRM);
       			 totalAmount=contractAmount.add(contractWTAmount);
       			 settleAdjustAmount=FDCHelper.ZERO;
       		 }
       		 row.getCell("settleAdjustAmount").setValue(settleAdjustAmount);
       		 row.getCell("totalAmount").setValue(totalAmount);
       		 if(totalAmount.compareTo(FDCHelper.ZERO)==0){
       			 row.getCell("payRate").setValue(FDCHelper.ZERO);
	       	 }else{
	       		 row.getCell("payRate").setValue(payAmount.divide(totalAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
	       	 }
       		 row.getCell("unContractAmount").setValue(unContractAmount);
        	 row.getCell("dynamicTotalAmount").setValue(dynamicTotalAmount);
        	 row.getCell("happenedAmount").setValue(happenedAmount);
        	 if(contractAmount.add(supplyAmount).compareTo(FDCHelper.ZERO)==0){
        		 row.getCell("changeRate").setValue(FDCHelper.ZERO);
        	 }else{
        		 row.getCell("changeRate").setValue((totalCONFIRM.add(totalUNCONFIRM)).divide(contractAmount.add(supplyAmount), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        	 }
        	 row.getCell("absolute").setValue(amount.subtract(dynamicTotalAmount));
        	 if(((BigDecimal)row.getCell("absolute").getValue()).compareTo(FDCHelper.ZERO)<0){
        		 row.getCell("absolute").getStyleAttributes().setFontColor(Color.RED);
        	 }
        	 if(amount.compareTo(FDCHelper.ZERO)==0){
        		 row.getCell("rate").setValue(FDCHelper.ZERO);
        	 }else{
        		 row.getCell("rate").setValue((amount.subtract(dynamicTotalAmount)).divide(amount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        	 }
        	 if(((BigDecimal)row.getCell("rate").getValue()).compareTo(FDCHelper.ZERO)<0){
        		 row.getCell("rate").getStyleAttributes().setFontColor(Color.RED);
        	 }
        	 if(((BigDecimal)row.getCell("changeRate").getValue()).compareTo(FDCHelper.ZERO)<0){
        		 row.getCell("changeRate").getStyleAttributes().setFontColor(Color.RED);
        	 }
       	 }else{
       		 row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
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
       				 if(prow.getCell("contractAmount").getValue()!=null){
       					 prow.getCell("contractAmount").setValue(((BigDecimal)prow.getCell("contractAmount").getValue()).add(contractAmount));
       				 }else{
       					 prow.getCell("contractAmount").setValue(contractAmount);
        			 }
        			 if(prow.getCell("supplyAmount").getValue()!=null){
        				 prow.getCell("supplyAmount").setValue(((BigDecimal)prow.getCell("supplyAmount").getValue()).add(supplyAmount));
        			 }else{
        				 prow.getCell("supplyAmount").setValue(supplyAmount);
        			 }
        			 if(prow.getCell("contractWTAmount").getValue()!=null){
        				 prow.getCell("contractWTAmount").setValue(((BigDecimal)prow.getCell("contractWTAmount").getValue()).add(contractWTAmount));
        			 }else{
        				 prow.getCell("contractWTAmount").setValue(contractWTAmount);
        			 }
        			 BigDecimal sumTotalCONFIRM=FDCHelper.ZERO;
        			 BigDecimal sumTotalUNCONFIRM=FDCHelper.ZERO;
        			 for(int i=0;i<changeTypeCol.size();i++){
        				 String key=changeTypeCol.get(i).toString();
        				 BigDecimal CONFIRM=row.getCell(key+"CONFIRM").getValue()==null?FDCHelper.ZERO:(BigDecimal)row.getCell(key+"CONFIRM").getValue();
        				 BigDecimal UNCONFIRM=row.getCell(key+"UNCONFIRM").getValue()==null?FDCHelper.ZERO:(BigDecimal)row.getCell(key+"UNCONFIRM").getValue();
        	   			 
        				 if(prow.getCell(key+"CONFIRM").getValue()!=null){
	        				 prow.getCell(key+"CONFIRM").setValue(((BigDecimal)prow.getCell(key+"CONFIRM").getValue()).add(CONFIRM));
	        			 }else{
	        				 prow.getCell(key+"CONFIRM").setValue(CONFIRM);
	        			 }
	        			 if(prow.getCell(key+"UNCONFIRM").getValue()!=null){
	        				 prow.getCell(key+"UNCONFIRM").setValue(((BigDecimal)prow.getCell(key+"UNCONFIRM").getValue()).add(UNCONFIRM));
	        			 }else{
	        				 prow.getCell(key+"UNCONFIRM").setValue(UNCONFIRM);
	        			 }
	        			 sumTotalCONFIRM=FDCHelper.add(sumTotalCONFIRM, prow.getCell(key+"CONFIRM").getValue());
	        			 sumTotalUNCONFIRM=FDCHelper.add(sumTotalUNCONFIRM, prow.getCell(key+"UNCONFIRM").getValue());
	        		 }
        			 if(prow.getCell("estimateAmount").getValue()!=null){
        				 prow.getCell("estimateAmount").setValue(((BigDecimal)prow.getCell("estimateAmount").getValue()).add(estimateAmount));
        			 }else{
        				 prow.getCell("estimateAmount").setValue(estimateAmount);
        			 }
        			 if(prow.getCell("settleAmount").getValue()!=null){
        				 prow.getCell("settleAmount").setValue(((BigDecimal)prow.getCell("settleAmount").getValue()).add(settleAmount));
        			 }else{
        				 prow.getCell("settleAmount").setValue(settleAmount);
        			 }
        			 if(prow.getCell("settleAdjustAmount").getValue()!=null){
        				 prow.getCell("settleAdjustAmount").setValue(((BigDecimal)prow.getCell("settleAdjustAmount").getValue()).add(settleAdjustAmount));
        			 }else{
        				 prow.getCell("settleAdjustAmount").setValue(settleAdjustAmount);
        			 }
        			 if(prow.getCell("totalAmount").getValue()!=null){
        				 prow.getCell("totalAmount").setValue(((BigDecimal)prow.getCell("totalAmount").getValue()).add(totalAmount));
        			 }else{
        				 prow.getCell("totalAmount").setValue(totalAmount);
        			 }
        			 if(prow.getCell("payAmount").getValue()!=null){
        				 prow.getCell("payAmount").setValue(((BigDecimal)prow.getCell("payAmount").getValue()).add(payAmount));
        			 }else{
        				 prow.getCell("payAmount").setValue(payAmount);
        			 }
        			 if(prow.getCell("unContractAmount").getValue()!=null){
        				 prow.getCell("unContractAmount").setValue(((BigDecimal)prow.getCell("unContractAmount").getValue()).add(unContractAmount));
        			 }else{
        				 prow.getCell("unContractAmount").setValue(unContractAmount);
        			 }
        			 if(prow.getCell("dynamicTotalAmount").getValue()!=null){
        				 prow.getCell("dynamicTotalAmount").setValue(((BigDecimal)prow.getCell("dynamicTotalAmount").getValue()).add(dynamicTotalAmount));
        			 }else{
        				 prow.getCell("dynamicTotalAmount").setValue(dynamicTotalAmount);
        			 }
        			 if(prow.getCell("happenedAmount").getValue()!=null){
        				 prow.getCell("happenedAmount").setValue(((BigDecimal)prow.getCell("happenedAmount").getValue()).add(happenedAmount));
        			 }else{
        				 prow.getCell("happenedAmount").setValue(happenedAmount);
        			 }
        			 if(FDCHelper.add(prow.getCell("contractAmount").getValue(), prow.getCell("supplyAmount").getValue()).compareTo(FDCHelper.ZERO)==0){
        				 prow.getCell("changeRate").setValue(FDCHelper.ZERO);
                	 }else{
                		 prow.getCell("changeRate").setValue((sumTotalCONFIRM.add(sumTotalUNCONFIRM)).divide(FDCHelper.add(prow.getCell("contractAmount").getValue(), prow.getCell("supplyAmount").getValue()), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
                	 }
        			 if(((BigDecimal)prow.getCell("totalAmount").getValue()).compareTo(FDCHelper.ZERO)==0){
        				 prow.getCell("payRate").setValue(FDCHelper.ZERO);
                	 }else{
                		 prow.getCell("payRate").setValue(((BigDecimal)prow.getCell("payAmount").getValue()).divide((BigDecimal)prow.getCell("totalAmount").getValue(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
                	 }
        			 BigDecimal sumAmount=(BigDecimal)prow.getCell("amount").getValue();
        			 BigDecimal sumDynamicTotalAmount=(BigDecimal)prow.getCell("dynamicTotalAmount").getValue();
        			 
     	        	 prow.getCell("absolute").setValue(sumAmount.subtract(sumDynamicTotalAmount));
     	        	 if(((BigDecimal)prow.getCell("absolute").getValue()).compareTo(FDCHelper.ZERO)<0){
     	        		 prow.getCell("absolute").getStyleAttributes().setFontColor(Color.RED);
       	        	 }else{
       	        		 prow.getCell("absolute").getStyleAttributes().setFontColor(Color.BLACK);
       	        	 }
     	        	 if(sumAmount.compareTo(FDCHelper.ZERO)==0){
     	        		 prow.getCell("rate").setValue(FDCHelper.ZERO);
     	        	 }else{
     	        		 prow.getCell("rate").setValue((sumAmount.subtract(sumDynamicTotalAmount)).divide(sumAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
     	        	 }
     	        	 if(totalAmount.compareTo(FDCHelper.ZERO)==0){
     	       			 row.getCell("payRate").setValue(FDCHelper.ZERO);
     		       	 }else{
     		       		 row.getCell("payRate").setValue(payAmount.divide(totalAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
     		       	 }
     	        	 if(((BigDecimal)prow.getCell("rate").getValue()).compareTo(FDCHelper.ZERO)<0){
      	        		 prow.getCell("rate").getStyleAttributes().setFontColor(Color.RED);
    	        	 }else{
    	        		 prow.getCell("rate").getStyleAttributes().setFontColor(Color.BLACK);
    	        	 }
     	        	 if(((BigDecimal)prow.getCell("changeRate").getValue()).compareTo(FDCHelper.ZERO)<0){
     	        		 prow.getCell("changeRate").getStyleAttributes().setFontColor(Color.RED);
       	        	 }else{
       	        		 prow.getCell("changeRate").getStyleAttributes().setFontColor(Color.BLACK);
       	        	 }
        		 }
        		 if(pnumber.indexOf(".")>0){
        			 pnumber=pnumber.substring(0, pnumber.lastIndexOf("."));
        		 }
	        	 }
        	 }
       	 	seq=seq+1;
         }
        return max;
	}
	
	
	private String getContractPayUnit(String pcId) throws BOSException, SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.fname_l2 from");
		sb.append(" (");
		sb.append(" select min(a.FCreateTime),a.FProgrammingContract,b.fname_l2 from T_CON_ContractBill a");
		sb.append(" left join t_bd_supplier b on b.fid=a.FPartBID");
		sb.append(" where a.fContractPropert!='SUPPLY' and a.fstate='4AUDITTED' group by a.FProgrammingContract,b.fname_l2");
		sb.append(" union all");
		sb.append(" select min(a.FCreateTime),FProgrammingContract,case b.fname_l2 when '' then c.fname_l2 else b.fname_l2 end fname_l2 from T_CON_ContractWithoutText a");
		sb.append(" left join t_bd_supplier b on b.fid=a.FReceiveUnitID");
		sb.append(" left join t_bd_person c on c.fid= a.FPersonId");
		sb.append(" where a.fstate='4AUDITTED' group by a.FProgrammingContract,case b.fname_l2 when '' then c.fname_l2 else b.fname_l2 end");
		sb.append(" ) a");
		sb.append(" where a.FProgrammingContract ='"+pcId+"'");
		
		String unitName = "";
		IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
		while(rowset.next())
		{
			unitName = rowset.getString(1)!=null?rowset.getString(1):"";
			break;
		}
			
		return unitName;
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
	boolean isThrough=false;
	public void onLoad() throws Exception {
		if(this.getUIContext().get("title")!=null){
			isThrough=true;
		}
		isOnLoad=true;
		setShowDialogOnLoad(false);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		if(!isThrough){
			buildOrgTree();
		}
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		isOnLoad=false;
		
		if(isThrough){
			kDTreeView1.setVisible(false);
			this.setUITitle(this.getUIContext().get("title").toString());
			query();
		}
		this.actionQuery.setVisible(false);
		this.refresh();
	}
	IUIWindow uiWindow=null;
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row=this.tblMain.getRow(e.getRowIndex());
			Object amount=row.getCell(e.getColIndex()).getValue();
			if(amount==null||!(amount instanceof BigDecimal)){
				return;
			}
			String uiClass=null;
			boolean isSupply=false;
			boolean CONFIRM=false;
			String changeTypeId=null;
			if(this.tblMain.getColumnKey(e.getColIndex()).equals("contractAmount")){
				uiClass=ContractBillReportUI.class.getName();
			}else if(this.tblMain.getColumnKey(e.getColIndex()).equals("supplyAmount")){
				uiClass=ContractBillReportUI.class.getName();
				isSupply=true;
			}else if(this.tblMain.getColumnKey(e.getColIndex()).equals("contractWTAmount")){
				uiClass=ContractBillWTReportUI.class.getName();
			}else if(this.tblMain.getColumnKey(e.getColIndex()).indexOf("CONFIRM")>0){
				uiClass=ContractChangeBillReportUI.class.getName();
				if(this.tblMain.getColumnKey(e.getColIndex()).indexOf("UNCONFIRM")>0){
					changeTypeId=this.tblMain.getColumnKey(e.getColIndex()).substring(0,this.tblMain.getColumnKey(e.getColIndex()).indexOf("UNCONFIRM"));
				}else{
					CONFIRM=true;
					changeTypeId=this.tblMain.getColumnKey(e.getColIndex()).substring(0,this.tblMain.getColumnKey(e.getColIndex()).indexOf("CONFIRM"));
				}
			}else if(this.tblMain.getColumnKey(e.getColIndex()).equals("estimateAmount")){
				uiClass=ContractEstimateBillReportUI.class.getName();
			}else if(this.tblMain.getColumnKey(e.getColIndex()).equals("settleAmount")){
				uiClass=ContractSettleBillReportUI.class.getName();
			}
			if(uiClass==null){
				return;
			}
			if(uiWindow!=null){
				uiWindow.close();
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			RptParams param=new RptParams();
			param.setObject("isClick", Boolean.TRUE);
			param.setObject("number", row.getCell("number").getValue().toString());
			param.setObject("curProjectInfo", params.getObject("curProject"));
			param.setObject("isSupply", isSupply);
			param.setObject("changeTypeId", changeTypeId);
			param.setObject("CONFIRM", CONFIRM);
			param.setObject("auditDate", params.getObject("auditDate"));
			uiContext.put("RPTFilter", param);
			uiContext.put("title", row.getCell("name").getValue().toString());
			uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(uiClass, uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	
}