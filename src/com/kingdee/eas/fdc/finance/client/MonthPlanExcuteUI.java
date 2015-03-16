/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.MonthPlanExcuteFacadeFactory;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.AccountSignReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.PeriodEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.AccountSignReportFilterUI;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.OverdueDescribeTwoListUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.report.TimeAccountReportFilterUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.DefaultKDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MonthPlanExcuteUI extends AbstractMonthPlanExcuteUI
{
    private static final Logger logger = CoreUIObject.getLogger(MonthPlanExcuteUI.class);
    private TreeModel tree;
    private DefaultKingdeeTreeNode resultTreeNode; 
    private boolean isQuery=false;
    public MonthPlanExcuteUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
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
				this.treeMain.setModel(MonthPlanExcuteFilterUI.buildTree());
			}
			this.treeMain.expandPath(this.treeMain.getSelectionPath());
		}
	}
	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new MonthPlanExcuteFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return MonthPlanExcuteFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		sum();
		for(int i=0;i<tblMain.getColumnCount();i++){
			if(tblMain.getColumnKey(i).indexOf("price")>=0||tblMain.getColumnKey(i).indexOf("Price")>=0||tblMain.getColumnKey(i).indexOf("amount")>=0||tblMain.getColumnKey(i).indexOf("Amount")>=0||tblMain.getColumnKey(i).indexOf("Rate")>=0){
				CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{tblMain.getColumnKey(i)});
			}
		}
	}
	public void sum(){
		for(int i=this.tblMain.getRowCount()-1;i>=0;i--){
			IRow row=this.tblMain.getRow(i);
			if(row.getCell("id").getValue()!=null&&row.getCell("proId").getValue()!=null){
				row.getCell("proId").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				row.getCell("proNumber").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				row.getCell("proName").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				row.getCell("proAmount").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			}
			String id=row.getCell("pCurProjectId").getValue().toString();
			IRow sumRow=getParentRow(id);
			if(sumRow==null||sumRow.getRowIndex()==row.getRowIndex()){
				continue;
			}
			for(int k=0;k<tblMain.getColumnCount();k++){
				if(tblMain.getColumnKey(k).indexOf("price")>=0||tblMain.getColumnKey(k).indexOf("Price")>=0||tblMain.getColumnKey(k).indexOf("amount")>=0||tblMain.getColumnKey(k).indexOf("Amount")>=0){
					if(row.getCell(tblMain.getColumnKey(k)).getValue()==null){
						row.getCell(tblMain.getColumnKey(k)).setValue(FDCHelper.ZERO);
					}
					SHEManageHelper.addSumColoum(sumRow,row,new String[]{tblMain.getColumnKey(k)});
				}else if(tblMain.getColumnKey(k).indexOf("payRate")>=0){
					BigDecimal rate=FDCHelper.ZERO;
					BigDecimal planAmount=(BigDecimal) row.getCell(tblMain.getColumnKey(k-2)).getValue();
					BigDecimal actAmount=(BigDecimal) row.getCell(tblMain.getColumnKey(k-1)).getValue();
					if(planAmount!=null&&actAmount!=null&&planAmount.compareTo(FDCHelper.ZERO)!=0){
						rate=actAmount.multiply(FDCHelper.ONE_HUNDRED).divide(planAmount, 2, BigDecimal.ROUND_HALF_UP);
					}
					row.getCell(tblMain.getColumnKey(k)).setValue(rate);
				}
			}
			BigDecimal rate=FDCHelper.ZERO;
			BigDecimal planAmount=(BigDecimal) row.getCell("amount").getValue();
			BigDecimal actAmount=(BigDecimal) row.getCell("accActPayAmount").getValue();
			if(planAmount!=null&&actAmount!=null&&planAmount.compareTo(FDCHelper.ZERO)!=0){
				rate=actAmount.multiply(FDCHelper.ONE_HUNDRED).divide(planAmount, 2, BigDecimal.ROUND_HALF_UP);
			}
			row.getCell("accActPayRate").setValue(rate);
		}
	}
	protected BigDecimal getMonthActPayAmount(String id,int year,int month,boolean isAll) throws BOSException, SQLException{
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(isnull(entry.factPayAmount,0))payAmount from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
		_builder.appendSql(" where bill.FContractId='"+id+"'");
		_builder.appendSql(" and bill.fstate in('2SUBMITTED','3AUDITTING','4AUDITTED') ");
		_builder.appendSql(" and bill.fhasClosed=0 and bill.famount is not null");
		if(!isAll){
			_builder.appendSql(" and year(bill.fbookedDate)='"+year+"' and month(bill.fbookedDate)='"+month+"'");
		}
		final IRowSet rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	protected BigDecimal getWTMonthActPayAmount(String id,int year,int month,boolean isAll) throws BOSException, SQLException{
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(isnull(entry.factPayAmount,0))payAmount from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
		_builder.appendSql(" left join t_con_contractwithouttext contract on contract.fid=bill.fcontractId");
		_builder.appendSql(" where contract.fid is not null and bill.FcurProjectId='"+id+"'");
		_builder.appendSql(" and bill.fstate in('2SUBMITTED','3AUDITTING','4AUDITTED') ");
		_builder.appendSql(" and bill.fhasClosed=0 and bill.famount is not null");
		if(!isAll){
			_builder.appendSql(" and year(bill.fbookedDate)='"+year+"' and month(bill.fbookedDate)='"+month+"'");
		}
		final IRowSet rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	protected BigDecimal getAccActOnLoadPayAmount(String id,int year,int month) throws BOSException, SQLException{
		Date bizDate = (Date)params.getObject("bizDate");
		Calendar cal = Calendar.getInstance();
		cal.setTime(bizDate);
		year=cal.get(Calendar.YEAR);
		month=cal.get(Calendar.MONTH)+1;
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(entry.frequestAmount-isnull(entry.factPayAmount,0))payAmount from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
		_builder.appendSql(" where bill.FContractId='"+id+"'");
		_builder.appendSql(" and year(bill.fbookedDate)='"+year+"' and month(bill.fbookedDate)='"+month+"'");
		_builder.appendSql(" and bill.fstate in('2SUBMITTED','3AUDITTING','4AUDITTED') ");
		_builder.appendSql(" and bill.fhasClosed=0 and bill.famount is not null");
		final IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	protected BigDecimal getWTAccActOnLoadPayAmount(String id,int year,int month) throws BOSException, SQLException{
		Date bizDate = (Date)params.getObject("bizDate");
		Calendar cal = Calendar.getInstance();
		cal.setTime(bizDate);
		year=cal.get(Calendar.YEAR);
		month=cal.get(Calendar.MONTH)+1;
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(entry.frequestAmount-isnull(entry.factPayAmount,0))payAmount from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
		_builder.appendSql(" left join t_con_contractwithouttext contract on contract.fid=bill.fcontractId");
		_builder.appendSql(" where contract.fid is not null and bill.FCurProjectId='"+id+"'");
		_builder.appendSql(" and year(bill.fbookedDate)='"+year+"' and month(bill.fbookedDate)='"+month+"'");
		_builder.appendSql(" and bill.fstate in('2SUBMITTED','3AUDITTING','4AUDITTED') ");
		_builder.appendSql(" and bill.fhasClosed=0 and bill.famount is not null");
		final IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	protected Set getCurProjectIdSet(BOSUuid id) throws BOSException, EASBizException {
		Set idSet = new HashSet();
		CurProjectInfo curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(id));
		String longNumber = curProjectInfo.getLongNumber();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber));
		filter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit.id", curProjectInfo.getFullOrgUnit().getId().toString()));
		filter.setMaskString("(#0 or #1) and #2");
		view.setFilter(filter);

		CurProjectCollection curProjectCollection = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);

		for (Iterator iter = curProjectCollection.iterator(); iter.hasNext();) {
			CurProjectInfo element = (CurProjectInfo) iter.next();
			idSet.add(element.getId().toString());
		}
		return idSet;
	}
	public Map getAllCurProjectIdMap(TreeNode treeNode) {
		Map idMap = new HashMap();
		if (treeNode != null) {
			fillTreeNodeIdMapForRoot(idMap, treeNode);
		}
		return idMap;
	}
	public Map fillTreeNodeIdMapForRoot(Map idMap,TreeNode treeNode) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (thisNode.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo objectInfo = (CurProjectInfo) thisNode.getUserObject();
			idMap.put(objectInfo.getId().toString(), thisNode);
		}
		int childCount = treeNode.getChildCount();

		while (childCount > 0) {
			fillTreeNodeIdMapForRoot(idMap, treeNode.getChildAt(childCount - 1));
			childCount--;
		}
		return idMap;
	}
	public void addSumRow(DefaultKingdeeTreeNode node,int dep,String parentId) throws EASBizException, BOSException{
		if(node!=null){
			Object obj = node.getUserObject();
			if(obj instanceof OrgStructureInfo||obj instanceof CurProjectInfo){
				IRow row=this.tblMain.addRow();
				row.setTreeLevel(dep);
				row.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
				if(obj instanceof CurProjectInfo){
					CurProjectInfo curProject=(CurProjectInfo)obj;
					parentId=curProject.getId().toString();
					row.getCell("curProjectId").setValue(curProject.getId().toString());
					row.getCell("curProjectNumber").setValue(curProject.getLongNumber().replaceAll("!", "."));
					row.getCell("curProjectName").setValue(curProject.getName());
				}else if(obj instanceof OrgStructureInfo){
					OrgStructureInfo org=(OrgStructureInfo)obj;
					parentId=org.getUnit().getId().toString();
					row.getCell("curProjectId").setValue(org.getUnit().getId().toString());
					row.getCell("curProjectNumber").setValue(((OrgStructureInfo)obj).getUnit().getLongNumber().replaceAll("!", "."));
					row.getCell("curProjectName").setValue(((OrgStructureInfo)obj).getUnit().getName());
				}
				row.getCell("pCurProjectId").setValue(parentId);
				++dep;
			}
			for (int i = 0; i < node.getChildCount(); i++) {
				addSumRow((DefaultKingdeeTreeNode) node.getChildAt(i),dep,parentId);
			}
		}
	}
	public IRow getParentRow(String pCurProjectId){
		if(pCurProjectId==null) return null;
		for(int i=0;i<this.tblMain.getRowCount();i++){
			IRow row=this.tblMain.getRow(i);
			if(row.getCell("curProjectId").getValue()!=null&&row.getCell("curProjectId").getValue().toString().equals(pCurProjectId)){
				return row;
			}
		}
		return null;
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
    	try {
			initTree();
		} catch (Exception e) {
			e.printStackTrace();
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
                	tblMain.setRefresh(false);
                	
                	RptParams resultRpt= getRemoteInstance().query(pp);
                 	return resultRpt;
                }
                public void afterExec(Object result)throws Exception{
                	tblMain.setRefresh(false);
                	
                	RptParams pp=(RptParams)params.clone();
                	pp.setObject("tree", null);
                	
                	RptParams rpt = getRemoteInstance().createTempTable(pp);
                    RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                    KDTableUtil.setHeader(header, tblMain);
                    
                    IRowSet rs = (IRowSet)((RptParams)result).getObject("rowset");
    	        	tblMain.getTreeColumn().setDepth(resultTreeNode.getDepth());
    		        addSumRow(resultTreeNode,0,null);
        	        tblMain.setRowCount(rs.size()+tblMain.getRowCount());
        	        
                    Map rowMap=new HashMap();
        	        IRow row=null;
        	        int rowCount=0;
        	        while(rs.next()){
        	        	String id=rs.getString("entryId");
        	        	int year=rs.getInt("year");
        	        	int month=rs.getInt("month");
        	        	String key=year+"year"+month+"m";
        	        	if(rowMap.containsKey(id)){
        	        		row=(IRow) rowMap.get(id);
        	        	}else{
        	        		rowCount++;
        	        		String pCurProjectId=rs.getString("pCurProjectId");
        	        		IRow prow=getParentRow(pCurProjectId);
        	        		if(prow==null){
        	        			row=tblMain.addRow();
        	        		}else{
        	        			row=tblMain.addRow(prow.getRowIndex()+1);
        	        			row.setTreeLevel(resultTreeNode.getDepth());
        	        		}
        	        		for(int j=0;j<header.getColumnCount();j++){
        	        			String coloumKey=header.getColumn(j).getName();
        	        			String contractId=rs.getString("id");
        	        			if(coloumKey.indexOf("year")>0){
        	        				continue;
        	        			}else if(coloumKey.equals("lastPrice")){
        	        				if(contractId==null) continue;
        	        				BigDecimal lastPrice=FDCUtils.getContractLastAmt (null,contractId);
        	        				row.getCell(coloumKey).setValue(lastPrice);
        	        			}else if(coloumKey.equals("accActPayAmount")){
        	        				if(contractId==null){
        	        					row.getCell(coloumKey).setValue(getWTMonthActPayAmount(rs.getString("curProjectId"),year,month,true));
        	        				}else{
        	        					row.getCell(coloumKey).setValue(getMonthActPayAmount(contractId,year,month,true));
        	        				}
        	        			}else if(coloumKey.equals("accActPayRate")){
        	        				BigDecimal rate=FDCHelper.ZERO;
        	        				BigDecimal planAmount=rs.getBigDecimal("amount");
        	        				BigDecimal actAmount=(BigDecimal) row.getCell("accActPayAmount").getValue();
                					if(planAmount!=null&&actAmount!=null&&planAmount.compareTo(FDCHelper.ZERO)!=0){
                						rate=actAmount.multiply(FDCHelper.ONE_HUNDRED).divide(planAmount, 2, BigDecimal.ROUND_HALF_UP);
                					}
                					row.getCell(coloumKey).setValue(rate);
        	        			}else if(coloumKey.equals("accActOnLoadPayAmount")){
        	        				if(contractId==null){
        	        					row.getCell(coloumKey).setValue(getWTAccActOnLoadPayAmount(rs.getString("curProjectId"),year,month));
        	        				}else{
        	        					row.getCell(coloumKey).setValue(getAccActOnLoadPayAmount(contractId,year,month));
        	        				}
        	        			}else{
        	        				row.getCell(coloumKey).setValue(rs.getObject(coloumKey));
        	        			}
        	        		}
        	        		if(rs.getString("id")==null){
        	        			row.getCell("name").setValue("无文本合同付款计划");
        	        		}
        	        		rowMap.put(id, row);
        	        	}
        	        	BigDecimal planAmount=rs.getBigDecimal("dateEntryAmount");
        	        	BigDecimal actAmount=FDCHelper.ZERO;
        	        	if(rs.getString("id")==null){
        					actAmount=getWTMonthActPayAmount(rs.getString("curProjectId"),year,month,false);
        				}else{
        					actAmount=getMonthActPayAmount(rs.getString("id"),year,month,false);
        				}
        				if(row.getCell(key+"planAmount")!=null){
        					row.getCell(key+"planAmount").setValue(planAmount);
        				}
        				if(row.getCell(key+"actAmount")!=null){
        					row.getCell(key+"actAmount").setValue(actAmount);
        				}
        				if(row.getCell(key+"payRate")!=null){
        					BigDecimal rate=FDCHelper.ZERO;
        					if(planAmount!=null&&actAmount!=null&&planAmount.compareTo(FDCHelper.ZERO)!=0){
        						rate=actAmount.multiply(FDCHelper.ONE_HUNDRED).divide(planAmount, 2, BigDecimal.ROUND_HALF_UP);
        					}
        					row.getCell(key+"payRate").setValue(rate);
        				}
        	        }

        	        tblMain.setRefresh(true);
        	        if(rs.size()> 0)
        	        	tblMain.reLayoutAndPaint();
        	        else
        	        	tblMain.repaint();
        	        
        	        tblMain.setRowCount(rowCount);
                }
            }
            );
            dialog.show();
    	}else{
			try {
				RptParams pp = (RptParams)params.clone();
				pp.setObject("tree", null);
	        	
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
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				params.setObject("curProject", null);
				params.setObject("org", treeNode.getUserObject());
			}else if(obj instanceof CurProjectInfo){
				params.setObject("curProject", treeNode.getUserObject());
				params.setObject("org", null);
			}else{
				params.setObject("curProject", null);
				params.setObject("org", null);
			}
			query();
		}
    }
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
//    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
//    	if(rowIndex==-1){
//    		return;
//    	}
//	    IRow row   = tblMain.getRow(rowIndex);
//	    tranID =(String)row.getCell("id").getValue();
//	    
//		if(rowIndex==-1){
//    		return;
//    	}
//	    if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
//	    	UIContext uiContext = new UIContext(this);
//		    uiContext.put("ID", row.getCell("id").getValue());
//		    IUIWindow uiWindow = null;
//			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PurchaseManageEditUI.class.getName(),uiContext,null,OprtState.VIEW);
//			uiWindow.show();
//	    }
    }
	protected Set getSelectSellProjects(DefaultKingdeeTreeNode treeNode) {
		Set projects = new HashSet();
		if(treeNode!=null){
			Enumeration en = treeNode.breadthFirstEnumeration();
			while (en.hasMoreElements()) {
				DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) en.nextElement();
				if (child.getUserObject() instanceof SellProjectInfo) {
					projects.add(((SellProjectInfo) child.getUserObject()).getId().toString());
				}
			}
		}
		return projects;
	}

}