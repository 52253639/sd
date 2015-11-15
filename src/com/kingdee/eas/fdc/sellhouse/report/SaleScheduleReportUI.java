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
import com.kingdee.bos.ui.face.UIFactory;
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
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.AccountReportUI;
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
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

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
                     RptRowSet onLoadRSUnLoan = (RptRowSet)((RptParams)result).getObject("onLoadRSUnLoan");
                     RptRowSet revRS = (RptRowSet)((RptParams)result).getObject("revRS");
                     
                     Map revMap=new HashMap();
                     Map onLoadMap=new HashMap();
                     Map onLoadUnLoanMap=new HashMap();
                     
                     IRow totoalSignRow=null;
                     IRow totoalRevRow=null;
                     IRow totoalOnLoadRow=null;
                     IRow totoalOnLoadRowUnLoan=null;
                     BigDecimal totalSignMonthAmount=FDCHelper.ZERO;
                     BigDecimal totalSignYearAmount=FDCHelper.ZERO;
                     
                     BigDecimal totalRevMonthAmount=FDCHelper.ZERO;
                     BigDecimal totalRevYearAmount=FDCHelper.ZERO;
                     
                     BigDecimal totalOnLoadMonthAmount=FDCHelper.ZERO;
                     BigDecimal totalOnLoadYearAmount=FDCHelper.ZERO;
                     
                     BigDecimal totalOnLoadMonthAmountUnLoan=FDCHelper.ZERO;
                     BigDecimal totalOnLoadYearAmountUnLoan=FDCHelper.ZERO;
                     DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
             		 if(treeNode!=null){
             			Object obj = treeNode.getUserObject();
             			if (obj instanceof OrgStructureInfo&&((OrgStructureInfo)obj).getLevel()==1) {
             				totoalSignRow=tblMain.addRow();
             				totoalSignRow.getCell("company").setValue("宋都控股");
             				totoalSignRow.getCell("type").setValue("销售收入");
             				totoalSignRow.getCell("act").getStyleAttributes().setFontColor(Color.BLUE);
             				totoalSignRow.getCell("yearAct").getStyleAttributes().setFontColor(Color.BLUE);
             				
             				totoalRevRow=tblMain.addRow();
             				totoalRevRow.getCell("company").setValue("宋都控股");
             				totoalRevRow.getCell("type").setValue("回笼资金");
             				totoalRevRow.getCell("act").getStyleAttributes().setFontColor(Color.BLUE);
             				totoalRevRow.getCell("yearAct").getStyleAttributes().setFontColor(Color.BLUE);
             				
             				IRow row=tblMain.addRow();
             				row.getCell("company").setValue("宋都控股");
             				row.getCell("type").setValue("营销费用（发生额）");
             				
             				row=tblMain.addRow();
             				row.getCell("company").setValue("宋都控股");
             				row.getCell("type").setValue("费效比");
             				
             				totoalOnLoadRow=tblMain.addRow();
             				totoalOnLoadRow.getCell("company").setValue("宋都控股");
             				totoalOnLoadRow.getCell("type").setValue("在途资金（按揭类）");
             				totoalOnLoadRow.getCell("act").getStyleAttributes().setFontColor(Color.BLUE);
             				totoalOnLoadRow.getCell("yearAct").getStyleAttributes().setFontColor(Color.BLUE);
             				
             				totoalOnLoadRowUnLoan=tblMain.addRow();
             				totoalOnLoadRowUnLoan.getCell("company").setValue("宋都控股");
             				totoalOnLoadRowUnLoan.getCell("type").setValue("在途资金（非按揭类）");
             				totoalOnLoadRowUnLoan.getCell("act").getStyleAttributes().setFontColor(Color.BLUE);
             				totoalOnLoadRowUnLoan.getCell("yearAct").getStyleAttributes().setFontColor(Color.BLUE);
             			}
             		 }
                     while(signRs.next()){
                    	 IRow row=tblMain.addRow();
                    	 row.getCell("orgId").setValue(signRs.getString("orgId"));
                    	 row.getCell("company").setValue(signRs.getString("company"));
                    	 row.getCell("type").setValue("销售收入");
                    	 row.getCell("act").setValue(signRs.getBigDecimal("monthAmount"));
                    	 row.getCell("act").getStyleAttributes().setFontColor(Color.BLUE);
                    	 row.getCell("yearAct").setValue(signRs.getBigDecimal("yearAmount"));
                    	 row.getCell("yearAct").getStyleAttributes().setFontColor(Color.BLUE);
                    	 
                    	 totalSignMonthAmount=FDCHelper.add(totalSignMonthAmount, signRs.getBigDecimal("monthAmount"));
                    	 totalSignYearAmount=FDCHelper.add(totalSignYearAmount, signRs.getBigDecimal("yearAmount"));
                    	 
                    	 row=tblMain.addRow();
                    	 row.getCell("orgId").setValue(signRs.getString("orgId"));
                    	 row.getCell("company").setValue(signRs.getString("company"));
                    	 row.getCell("type").setValue("回笼资金");
                    	 row.getCell("act").setValue(FDCHelper.ZERO);
                    	 row.getCell("act").getStyleAttributes().setFontColor(Color.BLUE);
                    	 row.getCell("yearAct").setValue(FDCHelper.ZERO);
                    	 row.getCell("yearAct").getStyleAttributes().setFontColor(Color.BLUE);
                    	 
                    	 revMap.put(signRs.getString("company"), row);
                    	 
                    	 row=tblMain.addRow();
                    	 row.getCell("company").setValue(signRs.getString("company"));
                    	 row.getCell("type").setValue("营销费用（发生额）");
                    	 
                    	 row=tblMain.addRow();
                    	 row.getCell("company").setValue(signRs.getString("company"));
                    	 row.getCell("type").setValue("费效比");
                    	 
                    	 row=tblMain.addRow();
                    	 row.getCell("orgId").setValue(signRs.getString("orgId"));
                    	 row.getCell("company").setValue(signRs.getString("company"));
                    	 row.getCell("type").setValue("在途资金（按揭类）");
                    	 row.getCell("act").setValue(FDCHelper.ZERO);
                    	 row.getCell("yearAct").setValue(FDCHelper.ZERO);
                    	 row.getCell("act").getStyleAttributes().setFontColor(Color.BLUE);
                    	 row.getCell("yearAct").getStyleAttributes().setFontColor(Color.BLUE);
                    	 
                    	 onLoadMap.put(signRs.getString("company"), row);
                    	 
                    	 row=tblMain.addRow();
                    	 row.getCell("orgId").setValue(signRs.getString("orgId"));
                    	 row.getCell("company").setValue(signRs.getString("company"));
                    	 row.getCell("type").setValue("在途资金（非按揭类）");
                    	 row.getCell("act").setValue(FDCHelper.ZERO);
                    	 row.getCell("yearAct").setValue(FDCHelper.ZERO);
                    	 row.getCell("act").getStyleAttributes().setFontColor(Color.BLUE);
                    	 row.getCell("yearAct").getStyleAttributes().setFontColor(Color.BLUE);
                    	 
                    	 onLoadUnLoanMap.put(signRs.getString("company"), row);
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
                        	 row.getCell("act").setValue(onLoadRS.getBigDecimal("amount"));
                        	 row.getCell("yearAct").setValue(onLoadRS.getBigDecimal("amount"));
                        	 
                        	 totalOnLoadMonthAmount=FDCHelper.add(totalOnLoadMonthAmount, onLoadRS.getBigDecimal("amount"));
                        	 totalOnLoadYearAmount=FDCHelper.add(totalOnLoadYearAmount, onLoadRS.getBigDecimal("amount"));
                		 }
                	 }
                     while(onLoadRSUnLoan.next()){
                    	 if(onLoadUnLoanMap.containsKey(onLoadRSUnLoan.getString("company"))){
                			 IRow row=(IRow) onLoadUnLoanMap.get(onLoadRSUnLoan.getString("company"));
                        	 row.getCell("act").setValue(onLoadRSUnLoan.getBigDecimal("amount"));
                        	 row.getCell("yearAct").setValue(onLoadRSUnLoan.getBigDecimal("amount"));
                        	 
                        	 totalOnLoadMonthAmountUnLoan=FDCHelper.add(totalOnLoadMonthAmountUnLoan, onLoadRSUnLoan.getBigDecimal("amount"));
                        	 totalOnLoadYearAmountUnLoan=FDCHelper.add(totalOnLoadYearAmountUnLoan, onLoadRSUnLoan.getBigDecimal("amount"));
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
                     if(totoalOnLoadRowUnLoan!=null){
                    	 totoalOnLoadRowUnLoan.getCell("act").setValue(totalOnLoadMonthAmountUnLoan);
                    	 totoalOnLoadRowUnLoan.getCell("yearAct").setValue(totalOnLoadYearAmountUnLoan);
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
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.SALE,"", SysContext.getSysContext().getCurrentSaleUnit().getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
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
			Object value=this.tblMain.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue();
			if(value==null
					||(value!=null&&value instanceof BigDecimal
							&&((BigDecimal)value).compareTo(FDCHelper.ZERO)<=0)){
				return;
			}
			String orgId=(String)this.tblMain.getRow(e.getRowIndex()).getCell("orgId").getValue();
			String type=(String)this.tblMain.getRow(e.getRowIndex()).getCell("type").getValue();
			if(!(type.equals("销售收入")||type.equals("回笼资金")||type.equals("在途资金（按揭类）")||type.equals("在途资金（非按揭类）"))){
				return;
			}
			if(uiWindow!=null)uiWindow.close();
			if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("act")){
				if(type.equals("销售收入")){
					toBaseTransaction(0,orgId);
				}else if(type.equals("回笼资金")){
					toSheRevBill(0,orgId);
				}else if(type.equals("在途资金（按揭类）")){
					toOnLoadBaseTransaction(orgId);
				}else if(type.equals("在途资金（非按揭类）")){
					toOnLoadBaseTransactionUnLoan(orgId);
				}
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("yearAct")){
				if(type.equals("销售收入")){
					toBaseTransaction(1,orgId);
				}else if(type.equals("回笼资金")){
					toSheRevBill(1,orgId);
				}else if(type.equals("在途资金（按揭类）")){
					toOnLoadBaseTransaction(orgId);
				}else if(type.equals("在途资金（非按揭类）")){
					toOnLoadBaseTransactionUnLoan(orgId);
				}
			}
		}
	}
	protected void toBaseTransaction(int type,String orgId) throws BOSException, SQLException{
		Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
		StringBuilder sb = new StringBuilder();
		sb.append(" select pre.fid from t_she_signManage pre ");
		sb.append(" where pre.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit')");
		sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED','3AUDITTING') and pre.fid=tt.fnewId )");
		if(type==0){
    		if(fromDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(toDate!=null){
    			sb.append(" and pre.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(toDate)))+ "'}");
    			sb.append(" and pre.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(orgId!=null){
			sb.append(" and pre.forgUnitId ='"+orgId+"'");
		}
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("filter", filter);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(SignManageListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	protected void toSheRevBill(int type,String orgId) throws BOSException, SQLException{
		Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
		StringBuilder sb = new StringBuilder();
		
    	sb.append(" select entry.fid id from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid");
    	sb.append(" left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount')");
    	if(type==0){
    		if(fromDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}else if(type==1){
    		if(toDate!=null){
    			sb.append(" and revBill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(toDate)))+ "'}");
    			sb.append(" and revBill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	if(orgId!=null){
			sb.append(" and revBill.fsaleOrgUnitId = '"+orgId+"'");
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
		uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PaymentReportUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
    }
	protected void toOnLoadBaseTransaction(String orgId) throws BOSException, SQLException{
		UIContext uiContext = new UIContext(this);
		uiContext.put("Owner", this);
		RptParams param = new RptParams();
		param.setObject("type", Boolean.TRUE);
		param.setObject("orgId", orgId);
		param.setObject("toDate", params.getObject("toDate"));
		uiContext.put("RPTFilter", param);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(SaleScheduleOnLoadDetailReportUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
    }
	protected void toOnLoadBaseTransactionUnLoan(String orgId) throws BOSException, SQLException{
		UIContext uiContext = new UIContext(this);
		uiContext.put("Owner", this);
		RptParams param = new RptParams();
		param.setObject("type", Boolean.FALSE);
		param.setObject("orgId", orgId);
		param.setObject("toDate", params.getObject("toDate"));
		uiContext.put("RPTFilter", param);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(SaleScheduleOnLoadDetailReportUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
    }
}