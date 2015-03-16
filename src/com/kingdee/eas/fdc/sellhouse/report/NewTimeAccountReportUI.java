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
import java.util.HashSet;
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
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
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
import com.kingdee.eas.fdc.sellhouse.client.CommerceChangeNewListUI;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.PrePurchaseManageListUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageListUI;
import com.kingdee.eas.fdc.sellhouse.client.SignManageListUI;
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
public class NewTimeAccountReportUI extends AbstractNewTimeAccountReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(NewTimeAccountReportUI.class);
    
    private DefaultKingdeeTreeNode resultTreeNode; 
    private boolean isQuery=false;
    private boolean isAll=false;
    private boolean isOnLoad=false;
    private String txtSp;
    /**
     * output class constructor
     */
    public NewTimeAccountReportUI() throws Exception
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
		return new TimeAccountReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return NewTimeAccountReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		
		sum();
		
		tblMain.getColumn("tel").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("visit").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("monthTel").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("monthVisit").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("yearTel").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("yearVisit").getStyleAttributes().setFontColor(Color.BLUE);
		
//		tblMain.getColumn("preAmount").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("signPreAmount").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("unSignPurAmount").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("signPurAmount").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("directSignAmount").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("signAmount").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("monthSignAmount").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("yearSignAmount").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("accUnSignPreAmount").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("accUnSignPurAmount").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("accSignAmount").getStyleAttributes().setFontColor(Color.BLUE);
		
		tblMain.getColumn("signBackAccount").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("monthSignBackAccount").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("yearSignBackAccount").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("accSignBackAccount").getStyleAttributes().setFontColor(Color.BLUE);
		
//		tblMain.getColumn("quitPreAmount").getStyleAttributes().setFontColor(Color.RED);
//		tblMain.getColumn("quitPreArea").getStyleAttributes().setFontColor(Color.RED);
//		tblMain.getColumn("quitPreAccount").getStyleAttributes().setFontColor(Color.RED);
//		tblMain.getColumn("quitPreAverage").getStyleAttributes().setFontColor(Color.RED);

		tblMain.getColumn("quitPurAmount").getStyleAttributes().setFontColor(Color.RED);
//		tblMain.getColumn("quitPurArea").getStyleAttributes().setFontColor(Color.RED);
//		tblMain.getColumn("quitPurAccount").getStyleAttributes().setFontColor(Color.RED);
//		tblMain.getColumn("quitPurAverage").getStyleAttributes().setFontColor(Color.RED);
		
		tblMain.getColumn("quitSignAmount").getStyleAttributes().setFontColor(Color.RED);
//		tblMain.getColumn("quitSignArea").getStyleAttributes().setFontColor(Color.RED);
//		tblMain.getColumn("quitSignAccount").getStyleAttributes().setFontColor(Color.RED);
//		tblMain.getColumn("quitSignAverage").getStyleAttributes().setFontColor(Color.RED);
		
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"onLoadAmount","preArea","preAccount","preAverage","quitPreArea","quitPreAccount","quitPreAverage","purArea"
				,"purAccount","purAverage","signPreArea","signPreAccount","signPreAverage","unSignPurArea","unSignPurAccount","unSignPurAverage","signPurArea","signPurAccount","signPurAverage","directSignArea","directSignAccount","directSignAverage","quitPurArea","quitPurAccount","quitPurAverage"
				,"signArea","signAccount","signAverage","compensateArea","compensateAccount","compensateTotalArea","compensateAccountTotal","signBackAccount","quitSignArea","quitSignAccount","quitSignAverage","monthSignArea","monthSignAccount","monthSignAverage","mcompensateArea","mcompensateAccount","mcompensateTotalArea","mcompensateAccountTotal","monthSignBackAccount","monthSignPlan","monthSignPlanRate","monthSignBackAccountPlan","monthSignBackAccountPlanRate"
				,"monthSignMarketAccount","yearSignArea","yearSignAccount","yearSignAverage","ycompensateArea","ycompensateAccount","ycompensateTotalArea","ycompensateAccountTotal","yearSignBackAccount","yearSignPlan","yearSignPlanRate","yearSignBackAccountPlan","yearSignBackAccountPlanRate","accUnSignPreArea","accUnSignPreAccount","accUnSignPreAverage","accUnSignPurArea","accUnSignPurAccount"
				,"accUnSignPurAverage","accSignArea","accSignAccount","accSignAverage","accSignBackAccount","accSignReceAccount","accSignReceAccountT","accSignReceAccountY"});
	
		tblMain.getViewManager().freeze(0, 4);
	}
	
	public void addSumRow(DefaultKingdeeTreeNode node,int dep) throws EASBizException, BOSException{
		if(node!=null){
			Object obj = node.getUserObject();
			if(obj instanceof OrgStructureInfo||obj instanceof SellProjectInfo){
				IRow row=this.tblMain.addRow();
				row.setTreeLevel(dep);
				row.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
				if(obj instanceof SellProjectInfo){
					String sellProjectStr="";
					if(isAll){
						sellProjectStr=SHEManageHelper.getStringFromSet(SHEManageHelper.getAllSellProjectCollection(null,(SellProjectInfo)obj));
					}else{
						sellProjectStr="'"+((SellProjectInfo)obj).getId().toString()+"'";
					}
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
		if(isAll){
			for(int i=0;i<this.tblMain.getRowCount();i++){
				IRow row=this.tblMain.getRow(i);
				if(row.getCell("sellProjectId").getValue()!=null&&!"".equals(row.getCell("sellProjectId").getValue().toString().trim())
						&&row.getCell("sellProjectId").getValue().toString().indexOf(",")<0&&row.getCell("sellProjectId").getValue().toString().indexOf(sellProjectId)>=0){
					return row;
				}
			}
		}else{
			for(int i=0;i<this.tblMain.getRowCount();i++){
				IRow row=this.tblMain.getRow(i);
				if(row.getCell("sellProjectId").getValue()!=null&&!"".equals(row.getCell("sellProjectId").getValue().toString().trim())
						&&row.getCell("sellProjectId").getValue().toString().indexOf(",")<0&&row.getCell("sellProjectId").getValue().toString().indexOf(sellProjectId)>=0
							&&row.getCell("sellProjectId").getValue().toString().indexOf("ORG")<0){
					return row;
				}
			}
		}
		return null;
	}
	public void sumRow(IRow sumRow,IRow row,String name){
		SHEManageHelper.addSumColoum(sumRow,row,new String[]{name+"Area",name+"Account"});
	}
	
	public void sum(){
		for(int i=this.tblMain.getRowCount()-1;i>=0;i--){
			IRow row=this.tblMain.getRow(i);
			String id=row.getCell("sellProjectId").getValue().toString();
			if(id.indexOf("'")<0){
				for(int j=0;j<i;j++){
					IRow sumRow=this.tblMain.getRow(j);
					if(sumRow.getCell("sellProjectId").getValue().toString().indexOf(id)>0&&sumRow.getCell("sellProjectId").getValue().toString().indexOf("'")==0){
						if(sumRow.getCell("sellProjectId").getValue().toString().indexOf("*PSP*")>0){
							if(row.getCell("tel").getValue()!=null){
								sumRow.getCell("tel").setValue(row.getCell("tel").getValue());
							}
							if(row.getCell("visit").getValue()!=null){
								sumRow.getCell("visit").setValue(row.getCell("visit").getValue());
							}
							if(row.getCell("monthTel").getValue()!=null){
								sumRow.getCell("monthTel").setValue(row.getCell("monthTel").getValue());
							}
							if(row.getCell("monthVisit").getValue()!=null){
								sumRow.getCell("monthVisit").setValue(row.getCell("monthVisit").getValue());
							}
							if(row.getCell("yearTel").getValue()!=null){
								sumRow.getCell("yearTel").setValue(row.getCell("yearTel").getValue());
							}
							if(row.getCell("yearVisit").getValue()!=null){
								sumRow.getCell("yearVisit").setValue(row.getCell("yearVisit").getValue());
							}
							
						}
				    	sumRow(sumRow,row,"sign");
				    	sumRow(sumRow,row,"monthSign");
				    	sumRow(sumRow,row,"yearSign");
				    	sumRow(sumRow,row,"accSign");
				    	
				    	SHEManageHelper.addSumColoum(sumRow,row,new String[]{"onLoadAmount","compensateAreaTotal","compensateAccountTotal","signBackAccount","monthSignBackAccount","yearSignBackAccount",
				    			"accSignBackAccount","quitSignAmount","quitPurAmount"});
					}
				}
				row.getCell("tel").setValue(null);
				row.getCell("visit").setValue(null);
				row.getCell("monthTel").setValue(null);
				row.getCell("monthVisit").setValue(null);
				row.getCell("yearTel").setValue(null);
				row.getCell("yearVisit").setValue(null);
			}else if(id.indexOf("*PSP*")>0){
				id=getSellProjectId(id).split(",")[0].replace("'", "");
				for(int j=0;j<i;j++){
					IRow sumRow=this.tblMain.getRow(j);
					if(sumRow.getCell("sellProjectId").getValue().toString().indexOf(id)>0&&sumRow.getCell("sellProjectId").getValue().toString().indexOf("*ORG*")>0){
						SHEManageHelper.addSumColoum(sumRow,row,new String[]{"tel","visit","monthTel","monthVisit","yearTel","yearVisit"});
					}
				}
			}
		}
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		if(isOnLoad||(!txtSp.equals(params.getObject("txtSp")))||!Boolean.valueOf(isAll).equals(Boolean.valueOf(params.getBoolean("isAll")))){
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
        	        		String id=rs.getString("sellProjectId");
        	        		row=getParentRow(id);
        		        	if(row!=null){
        		        		IRow addRow=tblMain.addRow(row.getRowIndex()+1);
        	        			((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(addRow, rs.toRowArray());
        	        			addRow.setTreeLevel(row.getTreeLevel()+1);
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
			isAll=params.getBoolean("isAll");
			txtSp=(String) params.getObject("txtSp");
			if(params.getObject("tree")!=null){
				this.treeMain.setModel((TreeModel) params.getObject("tree"));
			}else if(params.getObject("baseTree")!=null){
				this.treeMain.setModel((TreeModel) params.getObject("baseTree"));
			}else{
				this.treeMain.setModel(TimeAccountReportFilterUI.getSellProjectForSHESellProject(actionOnLoad, MoneySysTypeEnum.SalehouseSys,isAll));
			}
			if(isAll){
				SHEManageHelper.getProductTypeNodes(this.treeMain,(DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
			}
		    this.treeMain.expandPath(this.treeMain.getSelectionPath());
		  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void getSellProjectOrgId(Set id,DefaultKingdeeTreeNode node) throws BOSException{
		if(node.getUserObject() instanceof OrgStructureInfo) {
			for (int i = 0; i < node.getChildCount(); i++) {
				if(((DefaultKingdeeTreeNode) node.getChildAt(i)).getUserObject() instanceof SellProjectInfo) {
					id.addAll(SHEManageHelper.getAllSellProjectCollection(null,(SellProjectInfo) ((DefaultKingdeeTreeNode) node.getChildAt(i)).getUserObject()));
				}else if(((DefaultKingdeeTreeNode) node.getChildAt(i)).getUserObject() instanceof OrgStructureInfo){
					getSellProjectOrgId(id,(DefaultKingdeeTreeNode) node.getChildAt(i));
				}
			}
		}
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				params.setObject("sellProject", null);
				Set id=new HashSet();
				getSellProjectOrgId(id,treeNode);
				String allSpIdStr = FDCTreeHelper.getStringFromSet(id);
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
			String unSignPurDate="";
	    	if(fromDate!=null&&toDate!=null){
	    		unSignPurDate=unSignPurDate+"and (fbusAdscriptionDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'} or fbusAdscriptionDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'})";
			}else if(fromDate!=null&&toDate==null){
				unSignPurDate=unSignPurDate+"and fbusAdscriptionDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}";
			}else if(fromDate==null&&toDate!=null){
				unSignPurDate=unSignPurDate+"and fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}";
			}
	    	
	    	String signPurDate="";
	    	if(fromDate!=null){
	    		signPurDate=signPurDate+" and fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}";
			}
			if(toDate!=null){
				signPurDate=signPurDate+" and fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}";
			}
			boolean isLock=params.getBoolean("isLock");

			boolean isFitment=params.getBoolean("isFitment");
			
			String sellProjectId=getSellProjectId(this.tblMain.getRow(e.getRowIndex()).getCell("sellProjectId").getValue().toString());
			String productTypeId=(String)this.tblMain.getRow(e.getRowIndex()).getCell("productTypeId").getValue();
			
			if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("tel")){
				toCommerceChance(true,0,sellProjectId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("visit")){
				toCommerceChance(false,0,sellProjectId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("monthTel")){
				toCommerceChance(true,1,sellProjectId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("monthVisit")){
				toCommerceChance(false,1,sellProjectId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("yearTel")){
				toCommerceChance(true,2,sellProjectId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("yearVisit")){
				toCommerceChance(false,2,sellProjectId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("preAmount")){
				if(isLock){
					toRelateBill("t_she_prePurchaseManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PreApple','PreAudit','ToPur','ToSign','QRNullify'",null,null,0,sellProjectId,productTypeId);
				}else{
					toBaseTransaction("t_she_prePurchaseManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PreApple','PreAudit','ToPur','ToSign'",null,0,sellProjectId,productTypeId);
				}
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("quitPreAmount")){
				toChange("t_she_prePurchaseManage","'4AUDITTED'",sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("signPreAmount")){
				if(isLock){
					toRelateBill("t_she_prePurchaseManage","'ToSign'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify') "+signPurDate+" and fsellProjectId in("+sellProjectId+") and "+getLockChangeSrcId(fromDate,toDate,0,sellProjectId)+" and pre.fid=t.fsrcId))","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('QRNullify') and fsellProjectId in("+sellProjectId+") and pre.fid=t.fsrcId))",0,sellProjectId,productTypeId);
				}else{
					toBaseTransaction("t_she_prePurchaseManage","'ToSign'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit') "+signPurDate+" and pre.fid=t.fsrcId))",0,sellProjectId,productTypeId);
				}
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("unSignPurAmount")){
				if(isLock){
					StringBuilder sb = new StringBuilder();
					sb.append(" select pre.fid from t_she_purchaseManage pre left join t_she_room room on room.fid=pre.froomid  left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
					sb.append(" where pre.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PurApple','PurAudit','QRNullify') and productType.fid is not null");
					sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED') and pre.fid=tt.fnewId )");
					if(fromDate!=null){
		    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		    		}
		    		if(toDate!=null){
		    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		    		}
			    	if(sellProjectId!=null){
						sb.append(" and pre.fsellprojectid in ("+sellProjectId+")");
					}
			    	if(productTypeId!=null){
			    		sb.append(" and productType.fid = '"+productTypeId+"'");
			    	}
			    	if(!unSignPurDate.equals("")){
			    		sb.append(" union all select pre.fid from t_she_purchaseManage pre left join t_she_room room on room.fid=pre.froomid  left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
						sb.append(" where pre.fbizState in('ToSign') and productType.fid is not null");
						sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED') and pre.fid=tt.fnewId )");
						if(fromDate!=null){
			    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
			    		}
			    		if(toDate!=null){
			    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
			    		}
				    	if(sellProjectId!=null){
							sb.append(" and pre.fsellprojectid in ("+sellProjectId+")");
						}
				    	if(productTypeId!=null){
				    		sb.append(" and productType.fid = '"+productTypeId+"'");
				    	}
			    		sb.append(" and (EXISTS (select fsrcId from t_she_signManage t where fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify') "+unSignPurDate+" and fsellProjectId in("+sellProjectId+") and "+getLockChangeSrcId(fromDate,toDate,0,sellProjectId)+" and pre.fid=t.fsrcId))");
			    	}
			    	FilterInfo filter=new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
					
					sb = new StringBuilder();
					sb.append(" select pre.fid from t_she_purchaseManage pre left join t_she_room room on room.fid=pre.froomid  left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
					sb.append(" right join t_she_changeManage change on change.fsrcid=pre.fid where change.fstate in('4AUDITTED') and change.fbizType='quitRoom' and productType.fid is not null");
					if(fromDate!=null){
		    			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		    		}
		    		if(toDate!=null){
		    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		    		}
			    	if(sellProjectId!=null){
						sb.append(" and change.fsellprojectid in ("+sellProjectId+")");
					}
			    	if(productTypeId!=null){
			    		sb.append(" and productType.fid = '"+productTypeId+"'");
			    	}
		    		sb.append(" and (EXISTS (select fid from t_she_purchaseManage t where fbizState in('QRNullify') and pre.fid=t.fId))");
		    		if(!unSignPurDate.equals("")){
		    			sb.append(" union all select pre.fid from t_she_purchaseManage pre left join t_she_room room on room.fid=pre.froomid  left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
						sb.append(" right join t_she_changeManage change on change.fsrcid=pre.fid where change.fstate in('4AUDITTED') and change.fbizType='quitRoom' and productType.fid is not null");
						if(fromDate!=null){
			    			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
			    		}
			    		if(toDate!=null){
			    			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
			    		}
				    	if(sellProjectId!=null){
							sb.append(" and change.fsellprojectid in ("+sellProjectId+")");
						}
				    	if(productTypeId!=null){
				    		sb.append(" and productType.fid = '"+productTypeId+"'");
				    	}
			    		sb.append(" and (EXISTS (select fsrcId from t_she_signManage t where fbizState in('QRNullify') "+unSignPurDate+" and fsellProjectId in("+sellProjectId+") and pre.fid=t.fsrcId))");
		    		}
			    	FilterInfo changeFilter=new FilterInfo();
			    	changeFilter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
					
			    	UIContext uiContext = new UIContext(this);
					uiContext.put(UIContext.OWNER, this);
					uiContext.put("filter", filter);
					uiContext.put("changeFilter", changeFilter);
					uiContext.put("table", "t_she_purchaseManage");
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RelateBillUI.class.getName(), uiContext, null, OprtState.VIEW);
					uiWindow.show();
				}else{
					StringBuilder sb = new StringBuilder();
					sb.append(" select pre.fid from t_she_purchaseManage pre left join t_she_room room on room.fid=pre.froomid  left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
					sb.append(" where pre.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PurApple','PurAudit') and productType.fid is not null");
					sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED') and pre.fid=tt.fnewId )");
					if(fromDate!=null){
		    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		    		}
		    		if(toDate!=null){
		    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		    		}
			    	if(sellProjectId!=null){
						sb.append(" and pre.fsellprojectid in ("+sellProjectId+")");
					}
			    	if(productTypeId!=null){
			    		sb.append(" and productType.fid = '"+productTypeId+"'");
			    	}
			    	if(!unSignPurDate.equals("")){
			    		sb.append(" union all select pre.fid from t_she_purchaseManage pre left join t_she_room room on room.fid=pre.froomid  left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
						sb.append(" where pre.fbizState in('ToSign') and productType.fid is not null");
						sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED') and pre.fid=tt.fnewId )");
						if(fromDate!=null){
			    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
			    		}
			    		if(toDate!=null){
			    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
			    		}
				    	if(sellProjectId!=null){
							sb.append(" and pre.fsellprojectid in ("+sellProjectId+")");
						}
				    	if(productTypeId!=null){
				    		sb.append(" and productType.fid = '"+productTypeId+"'");
				    	}
			    		sb.append(" and (EXISTS (select fsrcId from t_she_signManage t where fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit')"+unSignPurDate+" and pre.fid=t.fsrcId))");
			    	}
			    	
			    	FilterInfo filter=new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
					
					UIContext uiContext = new UIContext(this);
					uiContext.put(UIContext.OWNER, this);
					uiContext.put("filter", filter);
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PurchaseManageListUI.class.getName(), uiContext, null, OprtState.VIEW);
					uiWindow.show();
				}
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("signPurAmount")){
				if(isLock){
					toRelateBill("t_she_purchaseManage","'ToSign'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify') "+signPurDate+"and fsellProjectId in("+sellProjectId+") and "+getLockChangeSrcId(fromDate,toDate,0,sellProjectId)+" and pre.fid=t.fsrcId))","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('QRNullify') "+signPurDate+"and fsellProjectId in("+sellProjectId+") and pre.fid=t.fsrcId))",0,sellProjectId,productTypeId);
				}else{
					toBaseTransaction("t_she_purchaseManage","'ToSign'","(EXISTS (select fsrcId from t_she_signManage t where fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit') "+signPurDate+" and pre.fid=t.fsrcId))",0,sellProjectId,productTypeId);
				}
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("directSignAmount")){
				if(isLock){
					toRelateBill("t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify'","(pre.fsrcId is null or NOT EXISTS (select fid from t_she_prePurchaseManage t where 1=1 and pre.fsrcId=t.fid and fsellProjectId in("+sellProjectId+") union select fid from t_she_purchaseManage t where 1=1 and pre.fsrcId=t.fid and fsellProjectId in("+sellProjectId+")))","(pre.fsrcId is null or NOT EXISTS (select fid from t_she_prePurchaseManage t where 1=1 and pre.fsrcId=t.fid and fsellProjectId in("+sellProjectId+") union select fid from t_she_purchaseManage t where 1=1 and pre.fsrcId=t.fid and fsellProjectId in("+sellProjectId+")))",0,sellProjectId,productTypeId);
				}else{
					toBaseTransaction("t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit'","(pre.fsrcId is null or NOT EXISTS (select fid from t_she_prePurchaseManage t where fsellprojectid in ("+sellProjectId+") and pre.fsrcId=t.fid union select fid from t_she_purchaseManage t where fsellprojectid in ("+sellProjectId+") and pre.fsrcId=t.fid))",0,sellProjectId,productTypeId);
				}
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("quitPurAmount")){
				toChange("t_she_purchaseManage","'4AUDITTED'",sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("signAmount")){
				if(isLock){
					toRelateBill("t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify'",null,null,0,sellProjectId,productTypeId);
				}else{
					toBaseTransaction("t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit'",null,0,sellProjectId,productTypeId);
				}
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("quitSignAmount")){
				toChange("t_she_signManage","'4AUDITTED'",sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("monthSignAmount")){
				if(isLock){
					toRelateBill("t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify'",null,null,1,sellProjectId,productTypeId);
				}else{
					toBaseTransaction("t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit'",null,1,sellProjectId,productTypeId);
				}
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("yearSignAmount")){
				if(isLock){
					toRelateBill("t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit','QRNullify'",null,null,2,sellProjectId,productTypeId);
				}else{
					toBaseTransaction("t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit'",null,2,sellProjectId,productTypeId);
				}
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("accUnSignPreAmount")){
				toBaseTransaction("t_she_prePurchaseManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PreApple','PreAudit'",null,3,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("accUnSignPurAmount")){
				toBaseTransaction("t_she_purchaseManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','PurApple','PurAudit'",null,3,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("accSignAmount")){
				toBaseTransaction("t_she_signManage","'ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit'",null,3,sellProjectId,productTypeId);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("signBackAccount")){
				toSheRevBill(0, sellProjectId, productTypeId,isFitment);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("monthSignBackAccount")){
				toSheRevBill(1, sellProjectId, productTypeId,isFitment);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("yearSignBackAccount")){
				toSheRevBill(2, sellProjectId, productTypeId,isFitment);
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("accSignBackAccount")){
				toSheRevBill(3, sellProjectId, productTypeId,isFitment);
			}
		}
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
	protected void toCommerceChance(boolean isTel,int type,String sellProjectId) throws BOSException, SQLException{
		StringBuilder where = new StringBuilder();
    	if(sellProjectId!=null){
    		where.append(" and cct.fsellProjectId in ("+sellProjectId+")");
		}
    	where.append(" and cc.fid is not null group by cct.fcommerceChanceid");
		
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	StringBuilder dateWhere = new StringBuilder();
    	dateWhere.append(" having 1=1");
		if(type==0){
    		if(fromDate!=null){
    			dateWhere.append(" and min(cct.ftrackDate)>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			dateWhere.append(" and min(cct.ftrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			dateWhere.append(" and min(cct.ftrackDate)>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			dateWhere.append(" and min(cct.ftrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			dateWhere.append(" and min(cct.ftrackDate)>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			dateWhere.append(" and min(cct.ftrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}
		StringBuilder sb = new StringBuilder();
		if(isTel){
			sb.append(" select cct.fcommerceChanceid from t_she_commerceChanceTrack cct left join (select min(cct.ftrackDate) ftrackDate ,cct.fcommerceChanceid from t_she_commerceChanceTrack cct where cct.finteractionType='INTERVIEW' group by cct.fcommerceChanceid) cct1");
			sb.append(" on cct.fcommerceChanceid=cct1.fcommerceChanceid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='TELEPHONE' and (cct.ftrackDate<cct1.ftrackDate or cct1.ftrackDate is null)");
		}else{
			sb.append(" select cct.fcommerceChanceid from t_she_commerceChanceTrack cct left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='INTERVIEW'");
		}
		sb.append(where);
		sb.append(dateWhere);
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("isHideTree", Boolean.TRUE);
		uiContext.put("filter", filter);
		IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CommerceChangeNewListUI.class.getName(),uiContext, null, OprtState.VIEW);
		ui.show();
	}
	protected void toRelateBill(String table,String state,String where,String changeWhere,int type,String sellProjectId,String productTypeId) throws UIException{
		Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
		StringBuilder sb = new StringBuilder();
		sb.append(" select pre.fid from "+table+" pre left join t_she_room room on room.fid=pre.froomid  left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
		sb.append(" where pre.fbizState in("+state+") and productType.fid is not null");
		sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED','3AUDITTING') and pre.fid=tt.fnewId )");
		if(type==0){
    		if(fromDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}
    	if(sellProjectId!=null){
			sb.append(" and pre.fsellprojectid in ("+sellProjectId+")");
		}
    	if(productTypeId!=null){
    		sb.append(" and productType.fid = '"+productTypeId+"'");
    	}
    	if(where!=null){
    		sb.append(" and "+where);
    	}
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		
		sb = new StringBuilder();
		sb.append(" select pre.fid from "+table+" pre left join t_she_room room on room.fid=pre.froomid  left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
		sb.append(" right join t_she_changeManage change on change.fsrcid=pre.fid where change.fstate in('4AUDITTED') and change.fbizType='quitRoom' and productType.fid is not null");
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
    	}
    	if(sellProjectId!=null){
			sb.append(" and change.fsellprojectid in ("+sellProjectId+")");
		}
    	if(productTypeId!=null){
    		sb.append(" and productType.fid = '"+productTypeId+"'");
    	}
    	if(changeWhere!=null){
    		sb.append(" and "+where);
    	}
    	FilterInfo changeFilter=new FilterInfo();
    	changeFilter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		
    	UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("filter", filter);
		uiContext.put("changeFilter", changeFilter);
		uiContext.put("table", table);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RelateBillUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	protected void toBaseTransaction(String table,String state,String where,int type,String sellProjectId,String productTypeId) throws BOSException, SQLException{
		Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
		StringBuilder sb = new StringBuilder();
		sb.append(" select pre.fid from "+table+" pre left join t_she_room room on room.fid=pre.froomid  left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
		sb.append(" where pre.fbizState in("+state+") and productType.fid is not null");
		sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED','3AUDITTING') and pre.fid=tt.fnewId )");
		if(type==0){
    		if(fromDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}");
    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}");
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}");
    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}");
    		}
    	}else if(type==3){
    		if(toDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(sellProjectId!=null){
			sb.append(" and pre.fsellprojectid in ("+sellProjectId+")");
		}
    	if(productTypeId!=null){
    		sb.append(" and productType.fid = '"+productTypeId+"'");
    	}
    	if(where!=null){
    		sb.append(" and "+where);
    	}
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		
		String editUI=null;
		if(table.equals("t_she_prePurchaseManage")){
			editUI=PrePurchaseManageListUI.class.getName();
		}else if(table.equals("t_she_purchaseManage")){
			editUI=PurchaseManageListUI.class.getName();
		}else if(table.equals("t_she_signManage")){
			editUI=SignManageListUI.class.getName();
		}
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("filter", filter);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(editUI, uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	protected void toSheRevBill(int type,String sellProjectId,String productTypeId,boolean isFitment) throws BOSException, SQLException{
		Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
		StringBuilder sb = new StringBuilder();
		
    	sb.append(" select entry.fid id from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid left join t_she_room room on room.fid=revBill.froomid");
    	sb.append(" left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')");
    	if(!isFitment){
    		sb.append(" and md.fnumber !='024'");
    	}
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
    		sb.append(" and productType.fid = '"+productTypeId+"'");
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
	protected void toChange(String table,String state,String sellProjectId,String productTypeId) throws BOSException, SQLException{
		Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
		StringBuilder sb = new StringBuilder();
		sb.append(" select pre.fid from "+table+" pre left join t_she_room room on room.fid=pre.froomid  left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid");
		sb.append(" right join t_she_changeManage change on change.fsrcid=pre.fid where change.fstate in("+state+") and change.fbizType='quitRoom' and productType.fid is not null");
		if(fromDate!=null){
			sb.append(" and change.fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and change.fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	if(sellProjectId!=null){
			sb.append(" and change.fsellprojectid in ("+sellProjectId+")");
		}
    	if(productTypeId!=null){
    		sb.append(" and productType.fid = '"+productTypeId+"'");
    	}
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		
		String editUI=null;
		if(table.equals("t_she_prePurchaseManage")){
			editUI=PrePurchaseManageListUI.class.getName();
		}else if(table.equals("t_she_purchaseManage")){
			editUI=PurchaseManageListUI.class.getName();
		}else if(table.equals("t_she_signManage")){
			editUI=SignManageListUI.class.getName();
		}
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("filter", filter);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(editUI, uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	private String getLockChangeSrcId(Date fromDate,Date toDate,int type,String sellProjectId){
    	String change="(NOT EXISTS (select fsrcId from t_she_changeManage tt where fstate in('4AUDITTED') and fbizType='quitRoom' and tt.fsrcId=t.fid ";
    	if(type==0){
    		if(fromDate!=null){
    			change=change+" and fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}";
    		}
    		if(toDate!=null){
    			change=change+" and fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}";
    		}
    	}else if(type==1){
    		if(fromDate!=null){
    			change=change+" and fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(fromDate)))+ "'}";
    			change=change+" and fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(fromDate)))+ "'}";
    		}
    	}else if(type==2){
    		if(fromDate!=null){
    			change=change+" and fchangeDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(fromDate)))+ "'}";
    			change=change+" and fchangeDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(fromDate)))+ "'}";
    		}
    	}
    	if(sellProjectId!=null){
    		change=change+" and fsellProjectId in ("+sellProjectId+")";
		}
    	change=change+"))";
    	return change;
    }

}