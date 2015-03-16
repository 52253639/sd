/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.OperationPhasesInfo;
import com.kingdee.eas.fdc.basedata.ProjectDataBuildTraceReportFacadeFactory;
import com.kingdee.eas.fdc.basedata.Utils.ProjectTreeForSL;
import com.kingdee.eas.fdc.basedata.Utils.UIHelper;
import com.kingdee.eas.fdc.market.DynamicValueReportFacadeFactory;
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
public class DynamicValueReportUI extends AbstractDynamicValueReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(DynamicValueReportUI.class);
    
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    public DynamicValueReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
       
    }
    public void onLoad() throws Exception {
		isOnLoad=true;
		setShowDialogOnLoad(false);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		buildProjectTree();
		isOnLoad=false;
		this.actionQuery.setVisible(false);
		this.query();
	}
    public void buildProjectTree() throws Exception {
		UIHelper.isCompany();
		ProjectTreeForSL projectTreeBuilder = new ProjectTreeForSL();
		projectTreeBuilder.build(this, this.treeMain, actionOnLoad);
		
		if(this.treeMain.getRowCount() > 0){
			this.treeMain.setSelectionRow(0);
			this.treeMain.expandAllNodes(true,(DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
		}
	}
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
    	if(node!=null){
    		String allSpIdStr = FDCTreeHelper.getStringFromSet(getAllObjectIdMap(node).keySet());
    		params.setObject("project", allSpIdStr);
    	}else{
    		params.setObject("project", null);
    	}
    	query();
    }
    protected Map getAllObjectIdMap(TreeNode treeNode) {
		Map idMap = new HashMap();
		fillTreeNodeIdMap(idMap, treeNode);
		return idMap;
	}
    protected void fillTreeNodeIdMap(Map idMap, TreeNode treeNode) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (thisNode.getUserObject() instanceof OperationPhasesInfo) {
			OperationPhasesInfo objectInfo = (OperationPhasesInfo) thisNode.getUserObject();
			idMap.put(objectInfo.getId().toString(), thisNode);
		}
		int childCount = treeNode.getChildCount();

		while (childCount > 0) {
			fillTreeNodeIdMap(idMap, treeNode.getChildAt(childCount - 1));
			childCount--;
		}
	}
    protected RptParams getParamsForInit() {
		return null;
	}
	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return null;
	}
	protected ICommRptBase getRemoteInstance() throws BOSException {
		return DynamicValueReportFacadeFactory.getRemoteInstance();
	}
	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	protected void query() {
		if(isOnLoad) return;
		this.tblMain.removeColumns();
		this.tblMain.removeRows();

		this.tblMain.getColumn("accountArea").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.tblMain.getColumn("accountArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblMain.getColumn("hqAmount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		this.tblMain.getColumn("hqAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		for(int i=0;i<this.tblMain.getColumnCount();i++){
			if(this.tblMain.getColumnKey(i).indexOf("amount")>=0||this.tblMain.getColumnKey(i).indexOf("price")>=0){
				this.tblMain.getColumn(i).getStyleAttributes().setNumberFormat("#,##0;-#,##0");
				this.tblMain.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}else if(this.tblMain.getColumnKey(i).indexOf("area")>=0){
				this.tblMain.getColumn(i).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				this.tblMain.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
		}
	}
	public void tableDataRequest(KDTDataRequestEvent arg0) {
		if(isQuery) return;
		isQuery=true;
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    	if(treeNode!=null||this.getUIContext().get("title")!=null){
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
                	 tblMain.setRefresh(false);
                	 
                	 RptParams rpt = getRemoteInstance().createTempTable(params);
                     RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                     KDTableUtil.setHeader(header, tblMain);
                     tblMain.getHeadRow(0).setHeight(30);
                     RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
                     
                     BigDecimal hqAmount=FDCHelper.ZERO;
                     BigDecimal account=FDCHelper.ZERO;
             		BigDecimal area=FDCHelper.ZERO;
             		BigDecimal amount=FDCHelper.ZERO;
             		
             		BigDecimal saccount=FDCHelper.ZERO;
             		BigDecimal sarea=FDCHelper.ZERO;
             		BigDecimal samount=FDCHelper.ZERO;
             		
             		BigDecimal daccount=FDCHelper.ZERO;
             		BigDecimal darea=FDCHelper.ZERO;
             		BigDecimal damount=FDCHelper.ZERO;
             		
             		BigDecimal paccount=FDCHelper.ZERO;
             		BigDecimal parea=FDCHelper.ZERO;
             		BigDecimal pamount=FDCHelper.ZERO;
             		
             		BigDecimal totalHqAmount=FDCHelper.ZERO;
             		BigDecimal subHqAmount=FDCHelper.ZERO;
             		BigDecimal totalAccount=FDCHelper.ZERO;
             		BigDecimal subAccount=FDCHelper.ZERO;
             		BigDecimal totalArea=FDCHelper.ZERO;
             		BigDecimal subArea=FDCHelper.ZERO;
             		BigDecimal totalAmount=FDCHelper.ZERO;
             		BigDecimal subAmount=FDCHelper.ZERO;
             		
             		BigDecimal totalSAccount=FDCHelper.ZERO;
             		BigDecimal subSAccount=FDCHelper.ZERO;
             		BigDecimal totalSArea=FDCHelper.ZERO;
             		BigDecimal subSArea=FDCHelper.ZERO;
             		BigDecimal totalSAmount=FDCHelper.ZERO;
             		BigDecimal subSAmount=FDCHelper.ZERO;
             		
             		BigDecimal totalDAccount=FDCHelper.ZERO;
             		BigDecimal subDAccount=FDCHelper.ZERO;
             		BigDecimal totalDArea=FDCHelper.ZERO;
             		BigDecimal subDArea=FDCHelper.ZERO;
             		BigDecimal totalDAmount=FDCHelper.ZERO;
             		BigDecimal subDAmount=FDCHelper.ZERO;
             		
             		BigDecimal totalPAccount=FDCHelper.ZERO;
             		BigDecimal subPAccount=FDCHelper.ZERO;
             		BigDecimal totalPArea=FDCHelper.ZERO;
             		BigDecimal subPArea=FDCHelper.ZERO;
             		BigDecimal totalPAmount=FDCHelper.ZERO;
             		BigDecimal subPAmount=FDCHelper.ZERO;
                     while(rs.next()){
                    	 IRow row=tblMain.addRow();
              			 ((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
              			 row.getCell("orgUnit").setValue(row.getCell("orgUnit").getValue().toString().split("_")[3]);
              			 
              			subHqAmount=(BigDecimal) row.getCell("hqAmount").getValue();
            			hqAmount=FDCHelper.add(hqAmount, subHqAmount);
            			totalHqAmount=FDCHelper.add(totalHqAmount, subHqAmount);
            			
              			subAccount=(BigDecimal) row.getCell("account").getValue();
              			account=FDCHelper.add(account, subAccount);
              			totalAccount=FDCHelper.add(totalAccount, subAccount);
              			
              			subArea=(BigDecimal) row.getCell("area").getValue();
            			area=FDCHelper.add(area, subArea);
            			totalArea=FDCHelper.add(totalArea, subArea);
            			
            			subAmount=(BigDecimal) row.getCell("amount").getValue();
            			amount=FDCHelper.add(amount, subAmount);
            			totalAmount=FDCHelper.add(totalAmount, subAmount);
            			
              			subSAccount=(BigDecimal) row.getCell("saccount").getValue();
              			saccount=FDCHelper.add(saccount, subSAccount);
              			totalSAccount=FDCHelper.add(totalSAccount, subSAccount);
              			
              			subSArea=(BigDecimal) row.getCell("sarea").getValue();
            			sarea=FDCHelper.add(sarea, subSArea);
            			totalSArea=FDCHelper.add(totalSArea, subSArea);
            			
            			subSAmount=(BigDecimal) row.getCell("samount").getValue();
            			samount=FDCHelper.add(samount, subSAmount);
            			totalSAmount=FDCHelper.add(totalSAmount, subSAmount);
            			
            			subDAccount=(BigDecimal) row.getCell("daccount").getValue();
              			daccount=FDCHelper.add(daccount, subDAccount);
              			totalDAccount=FDCHelper.add(totalDAccount, subDAccount);
              			
              			subDArea=(BigDecimal) row.getCell("darea").getValue();
            			darea=FDCHelper.add(darea, subDArea);
            			totalDArea=FDCHelper.add(totalDArea, subDArea);
            			
            			subDAmount=(BigDecimal) row.getCell("damount").getValue();
            			damount=FDCHelper.add(damount, subDAmount);
            			totalDAmount=FDCHelper.add(totalDAmount, subDAmount);
            			
            			subPAccount=(BigDecimal) row.getCell("paccount").getValue();
              			paccount=FDCHelper.add(paccount, subPAccount);
              			totalPAccount=FDCHelper.add(totalPAccount, subPAccount);
              			
              			subPArea=(BigDecimal) row.getCell("parea").getValue();
            			parea=FDCHelper.add(parea, subPArea);
            			totalPArea=FDCHelper.add(totalPArea, subPArea);
            			
            			subPAmount=(BigDecimal) row.getCell("pamount").getValue();
            			pamount=FDCHelper.add(pamount, subPAmount);
            			totalPAmount=FDCHelper.add(totalPAmount, subPAmount);
            			
            			if(subHqAmount==null||subHqAmount.compareTo(FDCHelper.ZERO)==0){
            				row.getCell("hqAmount").setValue(null);
            			}
            			if(subAccount==null||subAccount.compareTo(FDCHelper.ZERO)==0){
              				row.getCell("account").setValue(null);
              			}
            			if(subArea==null||subArea.compareTo(FDCHelper.ZERO)==0){
            				row.getCell("area").setValue(null);
            			}
            			if(row.getCell("price").getValue()==null||((BigDecimal)row.getCell("price").getValue()).compareTo(FDCHelper.ZERO)==0){
            				row.getCell("price").setValue(null);
            			}
            			if(subAmount==null||subAmount.compareTo(FDCHelper.ZERO)==0){
            				row.getCell("amount").setValue(null);
            			}
            			
            			if(subDAccount==null||subDAccount.compareTo(FDCHelper.ZERO)==0){
              				row.getCell("daccount").setValue(null);
              			}
            			if(subDArea==null||subDArea.compareTo(FDCHelper.ZERO)==0){
            				row.getCell("darea").setValue(null);
            			}
            			if(row.getCell("dprice").getValue()==null||((BigDecimal)row.getCell("dprice").getValue()).compareTo(FDCHelper.ZERO)==0){
            				row.getCell("dprice").setValue(null);
            			}
            			if(subDAmount==null||subDAmount.compareTo(FDCHelper.ZERO)==0){
            				row.getCell("damount").setValue(null);
            			}
            			
            			if(subSAccount==null||subSAccount.compareTo(FDCHelper.ZERO)==0){
              				row.getCell("saccount").setValue(null);
              			}
            			if(subSArea==null||subSArea.compareTo(FDCHelper.ZERO)==0){
            				row.getCell("sarea").setValue(null);
            			}
            			if(row.getCell("sprice").getValue()==null||((BigDecimal)row.getCell("sprice").getValue()).compareTo(FDCHelper.ZERO)==0){
            				row.getCell("sprice").setValue(null);
            			}
            			if(subSAmount==null||subSAmount.compareTo(FDCHelper.ZERO)==0){
            				row.getCell("samount").setValue(null);
            			}
            			
            			if(subPAccount==null||subPAccount.compareTo(FDCHelper.ZERO)==0){
              				row.getCell("paccount").setValue(null);
              			}
            			if(subPArea==null||subPArea.compareTo(FDCHelper.ZERO)==0){
            				row.getCell("parea").setValue(null);
            			}
            			if(row.getCell("pprice").getValue()==null||((BigDecimal)row.getCell("pprice").getValue()).compareTo(FDCHelper.ZERO)==0){
            				row.getCell("pprice").setValue(null);
            			}
            			if(subPAmount==null||subPAmount.compareTo(FDCHelper.ZERO)==0){
            				row.getCell("pamount").setValue(null);
            			}
            			if(row.getCell("operationPhases").getValue()!=null&&tblMain.getRow(row.getRowIndex()-1)!=null&&
            					 tblMain.getRow(row.getRowIndex()-1).getCell("operationPhases").getValue()!=null&&!row.getCell("operationPhases").getValue().toString().equals(tblMain.getRow(row.getRowIndex()-1).getCell("operationPhases").getValue().toString())){
            				 IRow totalRow=tblMain.addRow(row.getRowIndex());
                       		 totalRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
                       		 totalRow.getCell("hqAmount").setValue(FDCHelper.subtract(totalHqAmount, subHqAmount));
                       		 totalRow.getCell("account").setValue(FDCHelper.subtract(totalAccount, subAccount));
                       		 totalRow.getCell("area").setValue(FDCHelper.subtract(totalArea, subArea));
                       		 
                       		 totalRow.getCell("accountArea").setValue(FDCHelper.divide(totalRow.getCell("area").getValue(), totalRow.getCell("account").getValue(), 2, BigDecimal.ROUND_HALF_UP));
                       		 totalRow.getCell("amount").setValue(FDCHelper.subtract(totalAmount, subAmount));
                       		 totalRow.getCell("price").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("amount").getValue(), new BigDecimal(10000)), totalRow.getCell("area").getValue(), 0, BigDecimal.ROUND_HALF_UP));
                       		
                       		 totalRow.getCell("saccount").setValue(FDCHelper.subtract(totalSAccount, subSAccount));
                      		 totalRow.getCell("sarea").setValue(FDCHelper.subtract(totalSArea, subSArea));
                      		 totalRow.getCell("samount").setValue(FDCHelper.subtract(totalSAmount, subSAmount));
                      		 totalRow.getCell("sprice").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("samount").getValue(), new BigDecimal(10000)), totalRow.getCell("sarea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
                      		
                      		 totalRow.getCell("daccount").setValue(FDCHelper.subtract(totalDAccount, subDAccount));
                      		 totalRow.getCell("darea").setValue(FDCHelper.subtract(totalDArea, subDArea));
                      		 totalRow.getCell("damount").setValue(FDCHelper.subtract(totalDAmount, subDAmount));
                      		 totalRow.getCell("dprice").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("damount").getValue(), new BigDecimal(10000)), totalRow.getCell("darea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
                      		
                      		 
                      		 totalRow.getCell("paccount").setValue(FDCHelper.subtract(totalPAccount, subPAccount));
                      		 totalRow.getCell("parea").setValue(FDCHelper.subtract(totalPArea, subPArea));
                      		 totalRow.getCell("pamount").setValue(FDCHelper.subtract(totalPAmount, subPAmount));
                      		 totalRow.getCell("pprice").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("pamount").getValue(), new BigDecimal(10000)), totalRow.getCell("parea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
                      		
                       		 totalRow.getCell("project").setValue(tblMain.getRow(row.getRowIndex()-1).getCell("project").getValue().toString());
                       		 totalRow.getCell("operationPhases").setValue(tblMain.getRow(row.getRowIndex()-1).getCell("operationPhases").getValue().toString());
                       		totalRow.getCell("orgUnit").setValue(tblMain.getRow(totalRow.getRowIndex()-1).getCell("orgUnit").getValue().toString());
                       		totalRow.getCell("batch").setValue("小计");
                       		 
                       		 totalHqAmount=subHqAmount;
                       		 totalAccount=subAccount;
                       		 totalArea=subArea;
                       		 totalAmount=subAmount;
                       		 
                       		 totalSAccount=subSAccount;
                      		 totalSArea=subSArea;
                      		 totalSAmount=subSAmount;

                      		 totalDAccount=subDAccount;
                      		 totalDArea=subDArea;
                      		 totalDAmount=subDAmount;
                      		 
                      		 totalPAccount=subPAccount;
                      		 totalPArea=subPArea;
                      		 totalPAmount=subPAmount;
                       	 }
         	         }
                     if(tblMain.getRowCount()>0){
            		   	 IRow totalRow=tblMain.addRow();
            			 totalRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
            			 totalRow.getCell("hqAmount").setValue(totalHqAmount);
                   		 totalRow.getCell("account").setValue(totalAccount);
                   		 totalRow.getCell("area").setValue(totalArea);
                   		 totalRow.getCell("accountArea").setValue(FDCHelper.divide(totalRow.getCell("area").getValue(), totalRow.getCell("account").getValue(), 2, BigDecimal.ROUND_HALF_UP));
                   		 totalRow.getCell("amount").setValue(totalAmount);
                   		 totalRow.getCell("price").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("amount").getValue(), new BigDecimal(10000)), totalRow.getCell("area").getValue(), 0, BigDecimal.ROUND_HALF_UP));
                   		
                   		 totalRow.getCell("saccount").setValue(totalSAccount);
                  		 totalRow.getCell("sarea").setValue(totalSArea);
                  		 totalRow.getCell("samount").setValue(totalSAmount);
                  		 totalRow.getCell("sprice").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("samount").getValue(), new BigDecimal(10000)), totalRow.getCell("sarea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
                  		
                  		 totalRow.getCell("daccount").setValue(totalDAccount);
                  		 totalRow.getCell("darea").setValue(totalDArea);
                  		 totalRow.getCell("damount").setValue(totalDAmount);
                  		 totalRow.getCell("dprice").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("damount").getValue(), new BigDecimal(10000)), totalRow.getCell("darea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
                  		
                  		 totalRow.getCell("paccount").setValue(totalPAccount);
                  		 totalRow.getCell("parea").setValue(totalPArea);
                  		 totalRow.getCell("pamount").setValue(totalPAmount);
                  		 totalRow.getCell("pprice").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("pamount").getValue(), new BigDecimal(10000)), totalRow.getCell("parea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
                  		
                   		 totalRow.getCell("project").setValue(tblMain.getRow(totalRow.getRowIndex()-1).getCell("project").getValue().toString());
                   		 totalRow.getCell("operationPhases").setValue(tblMain.getRow(totalRow.getRowIndex()-1).getCell("operationPhases").getValue().toString());
                   		 totalRow.getCell("orgUnit").setValue(tblMain.getRow(totalRow.getRowIndex()-1).getCell("orgUnit").getValue().toString());
                   		totalRow.getCell("batch").setValue("小计");
                    }
            		IRow footRow = null;
                    KDTFootManager footRowManager = tblMain.getFootManager();
                    if(footRowManager == null)
                    {
                        String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
                        footRowManager = new KDTFootManager(tblMain);
                        footRowManager.addFootView();
                        tblMain.setFootManager(footRowManager);
                        footRow = footRowManager.addFootRow(0);
                        tblMain.getIndexColumn().setWidthAdjustMode((short)1);
                        tblMain.getIndexColumn().setWidth(30);
                        footRowManager.addIndexText(0, total);
                    } else
                    {
                        footRow = footRowManager.getFootRow(0);
                    }
                    footRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
                    footRow.getCell("hqAmount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("hqAmount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
                    footRow.getCell("hqAmount").setValue(hqAmount);
                    
                    footRow.getCell("account").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                    footRow.getCell("account").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
                    footRow.getCell("account").setValue(account);
                    
                    footRow.getCell("area").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("area").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    footRow.getCell("area").setValue(area);
                    
                    footRow.getCell("accountArea").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("accountArea").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    footRow.getCell("accountArea").setValue(FDCHelper.divide(footRow.getCell("area").getValue(), footRow.getCell("account").getValue(), 2, BigDecimal.ROUND_HALF_UP));
                    
                    footRow.getCell("amount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("amount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
                    footRow.getCell("amount").setValue(amount);
                    
                    footRow.getCell("price").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("price").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
                    footRow.getCell("price").setValue(FDCHelper.divide(FDCHelper.multiply(amount, new BigDecimal(10000)), area, 0, BigDecimal.ROUND_HALF_UP));
                    
                    footRow.getCell("saccount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                    footRow.getCell("saccount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
                    footRow.getCell("saccount").setValue(saccount);
                    
                    footRow.getCell("sarea").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("sarea").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    footRow.getCell("sarea").setValue(sarea);
                    
                    footRow.getCell("samount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("samount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
                    footRow.getCell("samount").setValue(samount);
                    
                    footRow.getCell("sprice").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("sprice").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
                    footRow.getCell("sprice").setValue(FDCHelper.divide(FDCHelper.multiply(samount, new BigDecimal(10000)), sarea, 0, BigDecimal.ROUND_HALF_UP));
                    
                    footRow.getCell("daccount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                    footRow.getCell("daccount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
                    footRow.getCell("daccount").setValue(daccount);
                    
                    footRow.getCell("darea").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("darea").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    footRow.getCell("darea").setValue(darea);
                    
                    footRow.getCell("damount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("damount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
                    footRow.getCell("damount").setValue(damount);
                    
                    footRow.getCell("dprice").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("dprice").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
                    footRow.getCell("dprice").setValue(FDCHelper.divide(FDCHelper.multiply(damount, new BigDecimal(10000)), darea, 0, BigDecimal.ROUND_HALF_UP));
                    
                    footRow.getCell("paccount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
                    footRow.getCell("paccount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
                    footRow.getCell("paccount").setValue(paccount);
                    
                    footRow.getCell("parea").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("parea").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    footRow.getCell("parea").setValue(parea);
                    
                    footRow.getCell("pamount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("pamount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
                    footRow.getCell("pamount").setValue(pamount);
                    
                    footRow.getCell("pprice").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
                    footRow.getCell("pprice").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
                    footRow.getCell("pprice").setValue(FDCHelper.divide(FDCHelper.multiply(pamount, new BigDecimal(10000)), parea, 0, BigDecimal.ROUND_HALF_UP));
            		
                     tblMain.setRefresh(true);
         	         if(rs.getRowCount() > 0){
         	        	tblMain.reLayoutAndPaint();
         	         }else{
         	        	tblMain.repaint();
         	         }
         	        tblMain.getGroupManager().setGroup(true);
        			
         	        tblMain.getColumn("orgUnit").setGroup(true);
         	        tblMain.getColumn("orgUnit").setMergeable(true);
       			
        			tblMain.getColumn("project").setGroup(true);
        			tblMain.getColumn("project").setMergeable(true);
        			
        			tblMain.getColumn("operationPhases").setGroup(true);
        			tblMain.getColumn("operationPhases").setMergeable(true);
                 }
            }
            );
            dialog.show();
    	}
    	isQuery=false;
	}
	private void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
		int merger=0;
		for(int i=0;i<table.getRowCount();i++){
			if(i>0){
				boolean isMerge=true;
				for(int j=0;j<coloum.length;j++){
					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
					if(!getString(curRow).equals(getString(lastRow))){
						isMerge=false;
						merger=i;
					}
				}
				if(isMerge){
					for(int j=0;j<mergeColoum.length;j++){
						table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));
					}
				}
			}
		}
	}
	private String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}

}