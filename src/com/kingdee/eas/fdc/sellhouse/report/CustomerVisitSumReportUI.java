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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
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
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.BankPaymentInfo;
import com.kingdee.eas.fdc.sellhouse.InteractionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.BankPaymentEditUI;
import com.kingdee.eas.fdc.sellhouse.client.CommerceChanceTrackListUI;
import com.kingdee.eas.fdc.sellhouse.client.CommerceChangeNewListUI;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class CustomerVisitSumReportUI extends AbstractCustomerVisitSumReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerVisitSumReportUI.class);
    
    /**
     * output class constructor
     */
    public CustomerVisitSumReportUI() throws Exception
    {
        super();
        disposeUIWindow();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new CustomerVisitSumReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return null;
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		getTableForPrintSetting().removeRows();
		Window win = SwingUtilities.getWindowAncestor(this);
        LongTimeDialog dialog = null;
        if(win instanceof Frame){
        	dialog = new LongTimeDialog((Frame)win);
        }else if(win instanceof Dialog){
        	dialog = new LongTimeDialog((Dialog)win);
        }
        if(dialog==null) return;
        dialog.setLongTimeTask(new ILongTimeTask() {
            public Object exec()throws Exception{
            	List list = CustomerVisitSumReportFacadeFactory.getRemoteInstance().getTableList(params);
            	return list;
            }
            public void afterExec(Object result)throws Exception{
            	List list=(List)result;
            	if(list!=null){
    				for(int i=0;i<list.size();i++){
    					Map map = (Map)list.get(i);
    					IRow row = getTableForPrintSetting().addRow();
    					DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
    					if(treeNode!=null){
    						row.getCell("itemName").setValue(treeNode.getUserObject());
    					}
    					BigDecimal telCount = new BigDecimal("0");
    					BigDecimal visitCount = new BigDecimal("0");
    					BigDecimal telToVisitCount= new BigDecimal("0");
    					BigDecimal visitToDealCount = new BigDecimal("0");
    					BigDecimal cardCount = new BigDecimal("0");
    					//来电
    					if(map.get("telCount")!=null){
    						telCount = new BigDecimal(map.get("telCount").toString());
    						row.getCell("telCount").setValue(telCount);
    						row.getCell("telCount").getStyleAttributes().setFontColor(Color.BLUE);
    					}
    					//来访
    					if(map.get("visitCount")!=null){
    						visitCount = new BigDecimal(map.get("visitCount").toString());
    						row.getCell("visitCount").setValue(visitCount);
    						row.getCell("visitCount").getStyleAttributes().setFontColor(Color.BLUE);
    					}
    					//来电转来访
    					if(map.get("telToVisit")!=null){
    						telToVisitCount = new BigDecimal(map.get("telToVisit").toString());
    						row.getCell("telToVisit").setValue(telToVisitCount);
    						row.getCell("telToVisit").getStyleAttributes().setFontColor(Color.BLUE);
    					}
    					//来访转成交
    					if(map.get("visitToDeal")!=null){
    						visitToDealCount = new BigDecimal(map.get("visitToDeal").toString());
    						row.getCell("visitToDeal").setValue(visitToDealCount);
    						row.getCell("visitToDeal").getStyleAttributes().setFontColor(Color.BLUE);
    					}
    					//来电转来访比率（%）
    					if(telCount.compareTo(new BigDecimal("0.0"))>0){
    						row.getCell("telToVisitPercent").setValue(((telToVisitCount.divide(telCount,4, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100"))).setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
    					}else{
    						row.getCell("telToVisitPercent").setValue("");
    					}
    					//来访转成交比率（%）
    					if(visitCount.compareTo(new BigDecimal("0.0"))>0){
    						row.getCell("visitToDealPercent").setValue(((visitToDealCount.divide(visitCount,4, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100"))).setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
    					}else{
    						row.getCell("visitToDealPercent").setValue("");
    					}
    					//办卡数量
    					if(map.get("cardCount")!=null){
    						cardCount = new BigDecimal(map.get("cardCount").toString());
    						row.getCell("cardCount").setValue(cardCount);
    						row.getCell("cardCount").getStyleAttributes().setFontColor(Color.BLUE);
    					}
    				}
    			}
            	getTableForPrintSetting().setRowCount(list.size());
            }
        }
        );
        dialog.show();
	}

	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.framework.report.client.CommRptBaseUI#onLoad()
	 */
	public void onLoad() throws Exception {
		setShowDialogOnLoad(true);
		super.onLoad();
		initTree();
		initTable();
	}
	protected void initTable(){
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(1);
        enableExportExcel(tblMain);
        //设置垂直滚动条
        getTableForPrintSetting().setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_SHOW);
        //设置水平滚动条
        getTableForPrintSetting().setScrollStateHorizon(KDTStyleConstants.SCROLL_STATE_SHOW);
        
		String[] fields=new String[tblMain.getColumnCount()];
		for(int i=0;i<tblMain.getColumnCount();i++){
			fields[i]=tblMain.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(tblMain,fields);
	}
	protected void initTree() throws Exception{
		this.treeMain.setModel(CRMTreeHelper.getSellProjectTree(actionOnLoad, false));
	    treeMain.setSelectionRow(0);
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				params.setObject("sellProject", null);
			}else 
			if(treeNode !=null && treeNode.getUserObject() instanceof SellProjectInfo){
				params.setObject("sellProject", treeNode.getUserObject());
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
			FilterInfo filter=null;
			if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("telCount")){
				filter=getTelFilter();
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("visitCount")){
				filter=getVisitFilter();
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("telToVisit")){
				filter=getTelToVisitFilter();
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("visitToDeal")){
				filter=getVisitToDealFilter();
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("cardCount")){
				filter=getCardFilter();
			}
			if(filter!=null){
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("isHideTree", Boolean.TRUE);
				uiContext.put("filter", filter);
				IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CommerceChangeNewListUI.class.getName(),uiContext, null, OprtState.VIEW);
				ui.show();
			}
		}
	}
	public FilterInfo getTelFilter() throws SQLException, BOSException{
		StringBuilder sb = new StringBuilder();
		sb.append(" select cct.fcommerceChanceid from t_she_commerceChanceTrack cct left join (select min(cct.ftrackDate) ftrackDate ,cct.fcommerceChanceid from t_she_commerceChanceTrack cct where cct.finteractionType='INTERVIEW' group by cct.fcommerceChanceid) cct1");
		sb.append(" on cct.fcommerceChanceid=cct1.fcommerceChanceid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='TELEPHONE' and (cct.ftrackDate<cct1.ftrackDate or cct1.ftrackDate is null)");
		sb.append(" and cc.fid is not null"); 
		SellProjectInfo sellProject = (SellProjectInfo)params.getObject("sellProject");
		if(sellProject!=null){
			sb.append(" and cct.fsellprojectid = '"+sellProject.getId()+"' ");
		}
		sb.append(" group by cct.fcommerceChanceid having 1=1");
		if(params.getObject("beginDate")!=null){
			sb.append(" and min(cct.FTrackDate)>= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.getObject("beginDate")))+ "'} ");
		}
		if(params.getObject("endDate")!=null){
			sb.append(" and min(cct.FTrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.getObject("endDate")))+ "'}");
		}
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		return filter;
	}
	public FilterInfo getVisitFilter() throws SQLException, BOSException{
		StringBuilder sb = new StringBuilder();
		sb.append(" select cct.fcommerceChanceid from t_she_commerceChanceTrack cct left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='INTERVIEW'");
		sb.append(" and cc.fid is not null"); 
		SellProjectInfo sellProject = (SellProjectInfo)params.getObject("sellProject");
		if(sellProject!=null){
			sb.append(" and cct.fsellprojectid = '"+sellProject.getId()+"' ");
		}
		sb.append(" group by cct.fcommerceChanceid having 1=1");
		if(params.getObject("beginDate")!=null){
			sb.append(" and min(cct.FTrackDate)>= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.getObject("beginDate")))+ "'} ");
		}
		if(params.getObject("endDate")!=null){
			sb.append(" and min(cct.FTrackDate)<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.getObject("endDate")))+ "'}");
		}
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		return filter;
	}
	public FilterInfo getTelToVisitFilter() throws SQLException, BOSException{
		StringBuilder sb = new StringBuilder();
		sb.append(" select cct.fcommerceChanceid from t_she_commerceChanceTrack cct left join (select cct.ftrackDate,cct.fcommerceChanceid from t_she_commerceChanceTrack cct where cct.finteractionType='TELEPHONE') cct1");
		sb.append(" on cct.fcommerceChanceid=cct1.fcommerceChanceid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='INTERVIEW' and cc.fid is not null");
		SellProjectInfo sellProject = (SellProjectInfo)params.getObject("sellProject");
		if(sellProject!=null){
			sb.append(" and cct.fsellprojectid = '"+sellProject.getId()+"' ");
		}
		sb.append(" group by cct.fcommerceChanceid having min(cct.ftrackDate)>min(cct1.ftrackDate)");
		if(params.getObject("beginDate")!=null){
			sb.append(" and min(cct.FTrackDate)>= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.getObject("beginDate")))+ "'} ");
		}
		if(params.getObject("endDate")!=null){
			sb.append(" and min(cct.FTrackDate) <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.getObject("endDate")))+ "'}");
		}
		if(params.getObject("beginDate")!=null){
			sb.append(" and min(cct1.FTrackDate)>= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.getObject("beginDate")))+ "'} ");
		}
		if(params.getObject("endDate")!=null){
			sb.append(" and min(cct1.FTrackDate) <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.getObject("endDate")))+ "'}");
		}
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		return filter;
	}
	public FilterInfo getVisitToDealFilter() throws BOSException, SQLException{
		StringBuilder sb = new StringBuilder();
		sb.append(" select cct.fcommerceChanceid from T_SHE_CommerceChanceTrack cct left join (select cct.ftrackDate,cct.fcommerceChanceid from t_she_commerceChanceTrack cct where cct.finteractionType='INTERVIEW') cct1" );
		sb.append(" on cct.fcommerceChanceid=cct1.fcommerceChanceid left join (select cct.ftrackDate,cct.fcommerceChanceid from t_she_commerceChanceTrack cct where cct.FTrackEvent = 'QuitRoom') cct2 on cct.fcommerceChanceid=cct2.fcommerceChanceid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where (cct.FTrackEvent = 'Purchase' or cct.FTrackEvent = 'Sign') and cc.fid is not null");
		SellProjectInfo sellProject = (SellProjectInfo)params.getObject("sellProject");
		if(sellProject!=null){
			sb.append(" and cct.fsellprojectid = '"+sellProject.getId()+"' ");
		}
		sb.append(" group by cct.fcommerceChanceid having min(cct.ftrackDate)>min(cct1.ftrackDate)");
		if(params.getObject("beginDate")!=null){
			sb.append(" and min(cct.FTrackDate)>= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.getObject("beginDate")))+ "'} ");
		}
		if(params.getObject("endDate")!=null){
			sb.append(" and min(cct.FTrackDate) <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.getObject("endDate")))+ "'}");
		}
		if(params.getObject("beginDate")!=null){
			sb.append(" and min(cct1.FTrackDate)>= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.getObject("beginDate")))+ "'} ");
		}
		if(params.getObject("endDate")!=null){
			sb.append(" and min(cct1.FTrackDate) <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.getObject("endDate")))+ "'}");
		}
		sb.append(" and ((min(cct2.ftrackDate)<=min(cct.ftrackDate)");
		if(params.getObject("beginDate")!=null){
			sb.append(" and min(cct2.FTrackDate) >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.getObject("beginDate")))+ "'} ");
		}
		if(params.getObject("endDate")!=null){
			sb.append(" and min(cct2.FTrackDate) <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.getObject("endDate")))+ "'}");
		}
		sb.append(" )or min(cct2.ftrackDate) is null)");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		return filter;
	}
	public FilterInfo getCardFilter() throws BOSException, SQLException{
		StringBuilder sb = new StringBuilder();
		sb.append(" select cct.fcommerceChanceid from t_she_commerceChanceTrack cct ");
		sb.append(" left join T_SHE_CommerceChanceAssistant cca on cca.FID=cct.FCommerceLevelID left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cca.fname_l2 = 'VIP客户' and cc.fid is not null");
    	if(params.getObject("beginDate")!=null){
			sb.append(" and cct.FTrackDate>= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.getObject("beginDate")))+ "'} ");
		}
		if(params.getObject("endDate")!=null){
			sb.append(" and cct.FTrackDate <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.getObject("endDate")))+ "'}");
		}
		SellProjectInfo sellProject = (SellProjectInfo)params.getObject("sellProject");
		if(sellProject!=null){
			sb.append(" and cct.fsellprojectid = '"+sellProject.getId()+"' ");
		}
		sb.append(" group by cct.fcommerceChanceid");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
		return filter;
	}
	
}