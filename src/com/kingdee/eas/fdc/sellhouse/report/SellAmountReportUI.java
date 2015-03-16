/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import com.kingdee.bos.ui.face.UIException;
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
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fi.cas.client.CasReceivingBillListUI;
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
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SellAmountReportUI extends AbstractSellAmountReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(SellAmountReportUI.class);
    private DefaultKingdeeTreeNode resultTreeNode; 
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    private String txtSp;
    public SellAmountReportUI() throws Exception
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
		return new SellAmountReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return SellAmountReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		
		sum();
		
		for(int i=0;i<tblMain.getColumnCount();i++){
			String key=tblMain.getColumnKey(i);
			if(!key.equals("orgId")&&!key.equals("sellProjectId")&&!key.equals("sellProjectName")&&!key.equals("productTypeId")&&!key.equals("productTypeName")){
				CRMClientHelper.changeTableNumberFormat(tblMain,key);
				
				if(key.indexOf("TotalAmount")<0&&key.indexOf("Amount")>0){
					tblMain.getColumn(i).getStyleAttributes().setFontColor(Color.BLUE);
				}
			}
		}
        tblMain.getHeadRow(1).getCell("mtHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        tblMain.getHeadRow(2).getCell("mtHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        tblMain.getHeadRow(1).getCell("fiHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        tblMain.getHeadRow(2).getCell("fiHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        
        tblMain.getHeadRow(1).getCell("MmtHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        tblMain.getHeadRow(2).getCell("MmtHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        tblMain.getHeadRow(1).getCell("MfiHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        tblMain.getHeadRow(2).getCell("MfiHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        
        tblMain.getHeadRow(1).getCell("YmtHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        tblMain.getHeadRow(2).getCell("YmtHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        tblMain.getHeadRow(1).getCell("YfiHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        tblMain.getHeadRow(2).getCell("YfiHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        
        tblMain.getHeadRow(1).getCell("AmtHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        tblMain.getHeadRow(2).getCell("AmtHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        tblMain.getHeadRow(1).getCell("AfiHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
        tblMain.getHeadRow(2).getCell("AfiHouseAmount").getStyleAttributes().setBackground(Color.GREEN);
		tblMain.getViewManager().freeze(0,5);
	}
	public void addSumRow(DefaultKingdeeTreeNode node,int dep) throws EASBizException, BOSException{
		if(node!=null){
			Object obj = node.getUserObject();
			if((obj instanceof OrgStructureInfo||obj instanceof SellProjectInfo)&&!node.isLeaf()){
				IRow row=this.tblMain.addRow();
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
					String allOrgIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "OrgStructure").keySet());
					
					row.getCell("orgId").setValue(allOrgIdStr);
					row.getCell("sellProjectId").setValue(allSpIdStr+"*ORG*");
					row.getCell("sellProjectName").setValue(((OrgStructureInfo)obj).getUnit().getName());
				}
			    
				row.getCell("contractAmount").setValue(FDCHelper.ZERO);
				row.getCell("area").setValue(FDCHelper.ZERO);
				row.getCell("price").setValue(FDCHelper.ZERO);
				row.getCell("mtHouseAmount").setValue(FDCHelper.ZERO);
				row.getCell("mtEarnestAmount").setValue(FDCHelper.ZERO);
				row.getCell("mtTotalAmount").setValue(FDCHelper.ZERO);
				row.getCell("unGatherAmount").setValue(FDCHelper.ZERO);
				row.getCell("fiHouseAmount").setValue(FDCHelper.ZERO);
				row.getCell("fiHandAmount").setValue(FDCHelper.ZERO);
				row.getCell("fiTotalAmount").setValue(FDCHelper.ZERO);
				
				row.getCell("McontractAmount").setValue(FDCHelper.ZERO);
				row.getCell("Marea").setValue(FDCHelper.ZERO);
				row.getCell("Mprice").setValue(FDCHelper.ZERO);
				row.getCell("MmtHouseAmount").setValue(FDCHelper.ZERO);
				row.getCell("MmtEarnestAmount").setValue(FDCHelper.ZERO);
				row.getCell("MmtTotalAmount").setValue(FDCHelper.ZERO);
				row.getCell("MunGatherAmount").setValue(FDCHelper.ZERO);
				row.getCell("MfiHouseAmount").setValue(FDCHelper.ZERO);
				row.getCell("MfiHandAmount").setValue(FDCHelper.ZERO);
				row.getCell("MfiTotalAmount").setValue(FDCHelper.ZERO);
				
				row.getCell("YcontractAmount").setValue(FDCHelper.ZERO);
				row.getCell("Yarea").setValue(FDCHelper.ZERO);
				row.getCell("Yprice").setValue(FDCHelper.ZERO);
				row.getCell("YmtHouseAmount").setValue(FDCHelper.ZERO);
				row.getCell("YmtEarnestAmount").setValue(FDCHelper.ZERO);
				row.getCell("YmtTotalAmount").setValue(FDCHelper.ZERO);
				row.getCell("YunGatherAmount").setValue(FDCHelper.ZERO);
				row.getCell("YfiHouseAmount").setValue(FDCHelper.ZERO);
				row.getCell("YfiHandAmount").setValue(FDCHelper.ZERO);
				row.getCell("YfiTotalAmount").setValue(FDCHelper.ZERO);
				
				row.getCell("AcontractAmount").setValue(FDCHelper.ZERO);
				row.getCell("Aarea").setValue(FDCHelper.ZERO);
				row.getCell("Aprice").setValue(FDCHelper.ZERO);
				row.getCell("AmtHouseAmount").setValue(FDCHelper.ZERO);
				row.getCell("AmtEarnestAmount").setValue(FDCHelper.ZERO);
				row.getCell("AmtTotalAmount").setValue(FDCHelper.ZERO);
				row.getCell("AunGatherAmount").setValue(FDCHelper.ZERO);
				row.getCell("AfiHouseAmount").setValue(FDCHelper.ZERO);
				row.getCell("AfiHandAmount").setValue(FDCHelper.ZERO);
				row.getCell("AfiTotalAmount").setValue(FDCHelper.ZERO);
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
					&&row.getCell("sellProjectId").getValue().toString().indexOf(",")<0&&row.getCell("sellProjectId").getValue().toString().indexOf(sellProjectId)>=0){
				return row;
			}
		}
		return null;
	}
	public IRow getOrgRow(String orgId){
		for(int i=0;i<this.tblMain.getRowCount();i++){
			IRow row=this.tblMain.getRow(i);
			if(row.getCell("orgId").getValue()!=null&&!"".equals(row.getCell("orgId").getValue().toString().trim())
					&&row.getCell("orgId").getValue().toString().replaceAll("'", "").equals(orgId)){
				return row;
			}
		}
		return null;
	}
	public void sumRow(IRow sumRow,IRow row,boolean isOrg){
	    if(isOrg){
	    	SHEManageHelper.addSumColoum(sumRow,row,new String[]{"fiHouseAmount","fiHandAmount"
					,"MfiHouseAmount","MfiHandAmount"
					,"YfiHouseAmount","YfiHandAmount"
					,"AfiHouseAmount","AfiHandAmount"});
	    	
	    	sumRow.getCell("fiTotalAmount").setValue(FDCHelper.add(sumRow.getCell("mtEarnestAmount").getValue(),FDCHelper.add(sumRow.getCell("fiHouseAmount").getValue(), sumRow.getCell("fiHandAmount").getValue())));
			sumRow.getCell("MfiTotalAmount").setValue(FDCHelper.add(sumRow.getCell("MmtEarnestAmount").getValue(),FDCHelper.add(sumRow.getCell("MfiHouseAmount").getValue(), sumRow.getCell("MfiHandAmount").getValue())));
			sumRow.getCell("YfiTotalAmount").setValue(FDCHelper.add(sumRow.getCell("YmtEarnestAmount").getValue(),FDCHelper.add(sumRow.getCell("YfiHouseAmount").getValue(), sumRow.getCell("YfiHandAmount").getValue())));
			sumRow.getCell("AfiTotalAmount").setValue(FDCHelper.add(sumRow.getCell("AmtEarnestAmount").getValue(),FDCHelper.add(sumRow.getCell("AfiHouseAmount").getValue(), sumRow.getCell("AfiHandAmount").getValue())));
	    }else{
	    	SHEManageHelper.addSumColoum(sumRow,row,new String[]{"contractAmount","area","mtHouseAmount","mtEarnestAmount","unGatherAmount"
				,"McontractAmount","Marea","MmtHouseAmount","MmtEarnestAmount","MunGatherAmount"
				,"YcontractAmount","Yarea","YmtHouseAmount","YmtEarnestAmount","YunGatherAmount"
				,"AcontractAmount","Aarea","AmtHouseAmount","AmtEarnestAmount","AunGatherAmount"});
			sumRow.getCell("price").setValue(FDCHelper.divide(sumRow.getCell("contractAmount").getValue(), sumRow.getCell("area").getValue()));
			sumRow.getCell("Mprice").setValue(FDCHelper.divide(sumRow.getCell("McontractAmount").getValue(), sumRow.getCell("Marea").getValue()));
			sumRow.getCell("Yprice").setValue(FDCHelper.divide(sumRow.getCell("YcontractAmount").getValue(), sumRow.getCell("Yarea").getValue()));
			sumRow.getCell("Aprice").setValue(FDCHelper.divide(sumRow.getCell("AcontractAmount").getValue(), sumRow.getCell("Aarea").getValue()));
			
			sumRow.getCell("mtTotalAmount").setValue(FDCHelper.add(sumRow.getCell("mtHouseAmount").getValue(), sumRow.getCell("mtEarnestAmount").getValue()));
			sumRow.getCell("MmtTotalAmount").setValue(FDCHelper.add(sumRow.getCell("MmtHouseAmount").getValue(), sumRow.getCell("MmtEarnestAmount").getValue()));
			sumRow.getCell("YmtTotalAmount").setValue(FDCHelper.add(sumRow.getCell("YmtHouseAmount").getValue(), sumRow.getCell("YmtEarnestAmount").getValue()));
			sumRow.getCell("AmtTotalAmount").setValue(FDCHelper.add(sumRow.getCell("AmtHouseAmount").getValue(), sumRow.getCell("AmtEarnestAmount").getValue()));
		}
	}
	public void sum(){
		for(int i=this.tblMain.getRowCount()-1;i>=0;i--){
			IRow row=this.tblMain.getRow(i);
			String id=row.getCell("sellProjectId").getValue().toString();
			String orgId=null;
			if(row.getCell("orgId").getValue()!=null){
				orgId=row.getCell("orgId").getValue().toString().replaceAll("'", "");
			}
			if(id.indexOf("'")<0){
				for(int j=0;j<i;j++){
					IRow sumRow=this.tblMain.getRow(j);
					if(sumRow.getCell("sellProjectId").getValue().toString().indexOf(id)>0&&sumRow.getCell("sellProjectId").getValue().toString().indexOf("'")==0){
						sumRow(sumRow,row,false);
					}
				}
			}else if(orgId!=null){
				for(int j=0;j<i;j++){
					IRow sumRow=this.tblMain.getRow(j);
					if(sumRow.getCell("orgId").getValue()!=null&&sumRow.getCell("orgId").getValue().toString().indexOf(orgId)>0){
						sumRow(sumRow,row,true);
					}
				}
			}
		}
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		if(isOnLoad||(!txtSp.equals(params.getObject("txtSp")))){
			try {
				initTree();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
        resultTreeNode=treeNode;
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
                	RptParams pp=(RptParams)params.clone();
                	pp.setObject("tree", null);
                	pp.setObject("baseTree", null);
                	tblMain.setRefresh(false);
                	
                	RptParams resultRpt= getRemoteInstance().query(pp);
                 	return resultRpt;
                }
                public void afterExec(Object result)throws Exception{
                	tblMain.setRefresh(false);
                	
                	RptParams pp=(RptParams)params.clone();
                	pp.setObject("tree", null);
                	pp.setObject("baseTree", null);
                	
                	RptParams rpt = getRemoteInstance().createTempTable(pp);
                    RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                    KDTableUtil.setHeader(header, tblMain);
                    
                    RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
        	        if(!resultTreeNode.isLeaf()){
        	        	tblMain.getTreeColumn().setDepth(resultTreeNode.getDepth()+1);
        		        addSumRow(resultTreeNode,0);
        	        }
        	        tblMain.setRowCount(rs.getRowCount()+tblMain.getRowCount());
        	        
        	        IRow row;
        	        while(rs.next()){
                		if(resultTreeNode.isLeaf()){
        	        		IRow addRow=tblMain.addRow();
                			((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(addRow, rs.toRowArray());
        	        	}else{
        	        		String orgId=rs.getString("orgId");
        	        		String id=rs.getString("sellProjectId");
        	        		if(id!=null){
        	        			row=getParentRow(id);
            		        	if(row!=null){
            		        		IRow addRow=tblMain.addRow(row.getRowIndex()+1);
            	        			((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(addRow, rs.toRowArray());
            	        			addRow.setTreeLevel(row.getTreeLevel()+1);
            		        	}
        	        		}else if(orgId!=null){
        	        			row=getOrgRow(orgId);
        	        			if(row!=null){
        	        				row.getCell("fiHouseAmount").setValue(rs.getBigDecimal("fiHouseAmount"));
        	        				row.getCell("fiHandAmount").setValue(rs.getBigDecimal("fiHouseAmount"));
        	        				
        	        				row.getCell("MfiHouseAmount").setValue(rs.getBigDecimal("MfiHouseAmount"));
        	        				row.getCell("MfiHandAmount").setValue(rs.getBigDecimal("MfiHandAmount"));
        	        				
        	        				row.getCell("YfiHouseAmount").setValue(rs.getBigDecimal("YfiHouseAmount"));
        	        				row.getCell("YfiHandAmount").setValue(rs.getBigDecimal("YfiHandAmount"));
        	        				
        	        				row.getCell("AfiHouseAmount").setValue(rs.getBigDecimal("AfiHouseAmount"));
        	        				row.getCell("AfiHandAmount").setValue(rs.getBigDecimal("AfiHandAmount"));
		            		  	}
        	        		}
        	        	}
        	        }
        	        if(rs.getRowCount() > 0){
        	        	tblMain.reLayoutAndPaint();
        	        }else{
        	        	tblMain.repaint();
        	        }
        	        tblMain.setRefresh(true);
                }
            }
            );
            dialog.show();
    	}else{
			try {
				RptParams pp = (RptParams)params.clone();
				pp.setObject("tree", null);
				pp.setObject("baseTree", null);
	        	
	        	RptParams rpt = getRemoteInstance().createTempTable(pp);
	            RptTableHeader header = (RptTableHeader)rpt.getObject("header");
	            KDTableUtil.setHeader(header, tblMain);
	            tblMain.setRowCount(0);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	isQuery=false;
    } 
	public void onLoad() throws Exception {
		isOnLoad=true;
		setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		isOnLoad=false;
	}
	protected void initTree() throws Exception{
		try {
			txtSp=(String) params.getObject("txtSp");
			if(params.getObject("tree")!=null){
				this.treeMain.setModel((TreeModel) params.getObject("tree"));
			}else if(params.getObject("baseTree")!=null){
				this.treeMain.setModel((TreeModel) params.getObject("baseTree"));
			}else{
				this.treeMain.setModel(FDCTreeHelper.getSellProjectForSHESellProject(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
			}
			SHEManageHelper.getProductTypeNodes(this.treeMain,(DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
		    this.treeMain.expandPath(this.treeMain.getSelectionPath());
		  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				params.setObject("sellProject", allSpIdStr);
				params.setObject("productType", null);
				String allOrgIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "OrgStructure").keySet());
				params.setObject("orgId", allOrgIdStr);
			}else if(obj instanceof SellProjectInfo){
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				params.setObject("sellProject", allSpIdStr);
				params.setObject("productType", null);
				params.setObject("orgId", null);
			}else if(obj instanceof ProductTypeInfo){
				params.setObject("sellProject", "'"+((SellProjectInfo)((DefaultKingdeeTreeNode)treeNode.getParent()).getUserObject()).getId().toString()+"'");
				params.setObject("productType", treeNode.getUserObject());
				params.setObject("orgId", null);
			}
			query();
		}
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			Object value=this.tblMain.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue();
			if(value==null
					||(value!=null&&value instanceof BigDecimal
							&&((BigDecimal)value).compareTo(FDCHelper.ZERO)<=0)){
				return;
			}
			Date fromDate = (Date)params.getObject("fromDate");
	    	Date toDate =   (Date)params.getObject("toDate");
	    	String sellProjectId=getSellProjectId(this.tblMain.getRow(e.getRowIndex()).getCell("sellProjectId").getValue().toString());
			String productTypeId=(String)this.tblMain.getRow(e.getRowIndex()).getCell("productTypeId").getValue();
			String orgId=(String) (String)this.tblMain.getRow(e.getRowIndex()).getCell("orgId").getValue();
			
			if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("contractAmount"))){
				toRelateBill(fromDate,toDate,0,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("McontractAmount"))){
				toRelateBill(fromDate,toDate,1,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("YcontractAmount"))){
				toRelateBill(fromDate,toDate,2,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("AcontractAmount"))){
				toRelateBill(fromDate,toDate,3,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("mtHouseAmount"))){
				toHouseBill(fromDate,toDate,0,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("MmtHouseAmount"))){
				toHouseBill(fromDate,toDate,1,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("YmtHouseAmount"))){
				toHouseBill(fromDate,toDate,2,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("AmtHouseAmount"))){
				toHouseBill(fromDate,toDate,3,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("mtEarnestAmount"))){
				toEarnestBill(fromDate,toDate,0,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("MmtEarnestAmount"))){
				toEarnestBill(fromDate,toDate,1,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("YmtEarnestAmount"))){
				toEarnestBill(fromDate,toDate,2,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("AmtEarnestAmount"))){
				toEarnestBill(fromDate,toDate,3,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("unGatherAmount"))){
				toUnGatherBill(fromDate,toDate,0,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("MunGatherAmount"))){
				toUnGatherBill(fromDate,toDate,1,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("YunGatherAmount"))){
				toUnGatherBill(fromDate,toDate,2,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("AunGatherAmount"))){
				toUnGatherBill(fromDate,toDate,3,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("fiHouseAmount"))){
				toFiBill(fromDate,toDate,0,orgId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("MfiHouseAmount"))){
				toFiBill(fromDate,toDate,1,orgId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("YfiHouseAmount"))){
				toFiBill(fromDate,toDate,2,orgId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("AfiHouseAmount"))){
				toFiBill(fromDate,toDate,3,orgId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("fiHandAmount"))){
				toFiHandBill(fromDate,toDate,0,orgId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("MfiHandAmount"))){
				toFiHandBill(fromDate,toDate,1,orgId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("YfiHandAmount"))){
				toFiHandBill(fromDate,toDate,2,orgId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals(("AfiHandAmount"))){
				toFiHandBill(fromDate,toDate,3,orgId);
			}
		}
	}
	protected void toRelateBill(Date fromDate,Date toDate,int type,String sellProjectId,String productTypeId) throws UIException{
		StringBuffer sb = new StringBuffer();
		sb.append(" select sign.fid from t_she_signManage sign left join t_she_room room on room.fid=sign.froomid");
		sb.append(" where sign.fbizState in('SignApple','SignAudit','QRNullify')");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(sellProjectId!=null){
			sb.append(" and sign.fsellprojectid in ("+sellProjectId+")");
		}
    	if(productTypeId!=null){
    		sb.append(" and room.fproductTypeId = '"+productTypeId+"'");
    	}
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		
		sb = new StringBuffer();
		sb.append(" select sign.fid from t_she_signManage sign left join t_she_room room on room.fid=sign.froomid");
		sb.append(" left join t_she_changeManage change on change.fsrcid=sign.fid where change.fstate in('2SUBMITTED','4AUDITTED') and change.fbizType='quitRoom'");
		if(type==0){
    		if(fromDate!=null){
    			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(sellProjectId!=null){
			sb.append(" and change.fsellprojectid in ("+sellProjectId+")");
		}
    	if(productTypeId!=null){
    		sb.append(" and room.fproductTypeId = '"+productTypeId+"'");
    	}
    	FilterInfo changeFilter=new FilterInfo();
    	changeFilter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		
    	UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("filter", filter);
		uiContext.put("changeFilter", changeFilter);
		uiContext.put("table", "t_she_signManage");
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RelateBillUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	protected void toHouseBill(Date fromDate,Date toDate,int type,String sellProjectId,String productTypeId) throws BOSException, SQLException{
		StringBuffer sb=new StringBuffer();
    	sb.append(" select entry.fid id from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid left join t_she_room room on room.fid=revBill.froomid");
    	sb.append(" left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" where entry.frevAmount is not null and revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(sellProjectId!=null){
			sb.append(" and revBill.fsellProjectId in ("+sellProjectId+")");
		}
    	if(productTypeId!=null){
    		sb.append(" and room.fproductTypeId = '"+productTypeId+"'");
    	}
    	FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sb.toString());
		final IRowSet rowSet = _builder.executeQuery();
		Set id=new HashSet();
		while(rowSet.next()) {
			id.add(rowSet.getString("id"));
		}
		
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("entrys.id",id,CompareType.INCLUDE));
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("filter", filter);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PaymentReportUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
    }
	protected void toEarnestBill(Date fromDate,Date toDate,int type,String sellProjectId,String productTypeId) throws BOSException, SQLException{
		StringBuffer sb=new StringBuffer();
    	sb.append(" select entry.fid id from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid left join t_she_room room on room.fid=revBill.froomid");
    	sb.append(" left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" where entry.famount is not null and revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(sellProjectId!=null){
			sb.append(" and revBill.fsellProjectId in ("+sellProjectId+")");
		}
    	if(productTypeId!=null){
    		sb.append(" and room.fproductTypeId = '"+productTypeId+"'");
    	}
    	FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sb.toString());
		final IRowSet rowSet = _builder.executeQuery();
		Set id=new HashSet();
		while(rowSet.next()) {
			id.add(rowSet.getString("id"));
		}
		
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("entrys.id",id,CompareType.INCLUDE));
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("filter", filter);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PaymentReportUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
    }
	protected void toUnGatherBill(Date fromDate,Date toDate,int type,String sellProjectId,String productTypeId) throws BOSException, SQLException{
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select entry.fid id from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid left join t_she_room room on room.fid=revBill.froomid");
    	sb.append(" left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" where revBill.fIsGathered=0 and revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(sellProjectId!=null){
			sb.append(" and revBill.fsellProjectId in ("+sellProjectId+")");
		}
    	if(productTypeId!=null){
    		sb.append(" and room.fproductTypeId = '"+productTypeId+"'");
    	}
    	FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sb.toString());
		final IRowSet rowSet = _builder.executeQuery();
		Set id=new HashSet();
		while(rowSet.next()) {
			id.add(rowSet.getString("id"));
		}
		
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("entrys.id",id,CompareType.INCLUDE));
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("filter", filter);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PaymentReportUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
    }
	protected void toFiBill(Date fromDate,Date toDate,int type,String orgId) throws UIException{
		StringBuffer sb=new StringBuffer();
    	sb.append(" select rec.fid from T_CAS_ReceivingBill rec");
    	sb.append(" left join (select distinct gather.FReceivingBillID recId from T_SHE_ReceiveGather gather left join T_SHE_ReceiveGatherEntry entry on entry.FReceiveGatherID=gather.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
		sb.append(" where md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')) t on t.recId=rec.fid");
		sb.append(" where rec.fbillStatus =14 and t.recId is not null");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and rec.frecDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and rec.frecDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and rec.frecDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and rec.frecDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and rec.frecDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and rec.frecDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}
    	if(orgId!=null){
			sb.append(" and rec.fcompanyId in ("+orgId+")");
		}else{
			sb.append(" and rec.fcompanyId in ('null')");
		}
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		Map map=new HashMap();
		map.put("DefaultQueryFilter", filter);
		uiContext.put("BTPEDITPARAMETER", map);
		uiContext.put("BOTPViewStatus", 1);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CasReceivingBillListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
    }
	protected void toFiHandBill(Date fromDate,Date toDate,int type,String orgId) throws UIException{
		StringBuffer sb=new StringBuffer();
    	sb.append(" select rec.fid from T_CAS_ReceivingBill rec");
    	sb.append(" left join (select distinct gather.FReceivingBillID recId from T_SHE_ReceiveGather gather left join T_SHE_ReceiveGatherEntry entry on entry.FReceiveGatherID=gather.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
		sb.append(" where md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')) t on t.recId=rec.fid");
		sb.append(" where rec.fbillStatus =14 and t.recId is null");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and rec.frecDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and rec.frecDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and rec.frecDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and rec.frecDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and rec.frecDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and rec.frecDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}
    	if(orgId!=null){
			sb.append(" and rec.fcompanyId in ("+orgId+")");
		}else{
			sb.append(" and rec.fcompanyId in ('null')");
		}
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		Map map=new HashMap();
		map.put("DefaultQueryFilter", filter);
		uiContext.put("BTPEDITPARAMETER", map);
		uiContext.put("BOTPViewStatus", 1);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CasReceivingBillListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
    }
	protected String getSellProjectId(String id){
		if(id.indexOf("*PSP*")>0){
			return id.replace("*PSP*", "");
		}else if(id.indexOf("*SP*")>0){
			return id.replace("*SP*", "");
		}else if(id.indexOf("*ORG*")>0){
			return id.replace("*ORG*", "");
		}else{
			return "'"+id+"'";
		}
	}

}