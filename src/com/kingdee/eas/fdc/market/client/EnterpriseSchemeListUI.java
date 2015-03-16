/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryCollection;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryFactory;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryInfo;
import com.kingdee.eas.fdc.market.EnterpriseSchemeEntryCollection;
import com.kingdee.eas.fdc.market.EnterpriseSchemeEntryFactory;
import com.kingdee.eas.fdc.market.EnterpriseSchemeFactory;
import com.kingdee.eas.fdc.market.EnterpriseSchemeInfo;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanFactory;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanInfo;
import com.kingdee.eas.fdc.market.ThemeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fi.gr.cslrpt.MergeModeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class EnterpriseSchemeListUI extends AbstractEnterpriseSchemeListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(EnterpriseSchemeListUI.class);

	/**
	 * output class constructor
	 */
	public EnterpriseSchemeListUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		actionQuery.setEnabled(false);
    	initTree();
		super.onLoad();
		actionQuery.setEnabled(true);
		
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		btnEvaluation.setIcon(EASResource.getIcon("imgTbtn_evaluategrade"));
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.menuBiz.setVisible(false);
		
		this.tblMain.getColumn("EnterpriseSellPlan.planCost").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("EnterpriseSellPlan.planCost").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblMain.getColumn("EnterpriseSchemeEntry.factAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("EnterpriseSchemeEntry.factAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblMain.getColumn("EnterpriseSchemeEntry.contractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("EnterpriseSchemeEntry.contractAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblMain.getColumn("EnterpriseSchemeEntry.payAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("EnterpriseSchemeEntry.payAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	
		this.tblMain.getColumn("name").setWidth(220);
		this.tblMain.getColumn("EnterpriseSchemeEntry.contractAmount").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("EnterpriseSchemeEntry.payAmount").getStyleAttributes().setHided(true);
		this.actionRemove.setVisible(false);
		
	}
	protected String[] getLocateNames() {
		String[] locateNames = new String[4];
	    locateNames[0] = "name";
	    locateNames[1] = "EnterprisePlanEntry.theme";
	    locateNames[2] = "EnterprisePlanEntry.describe";
	    locateNames[3] = "EnterpriseSellPlan.proceeding";
	    return locateNames;
	}
	/**
	 * 构建树
	 * */
	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);
	}

	/**
	 * 描述：为当前单据的新增、编辑、查看准备Context
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {
			int indexRow = tblMain.getSelectManager().getActiveRowIndex();
			String enterprisePlanEntryId = (String) tblMain.getRow(indexRow).getCell("id").getValue();
			uiContext.put("enterprisePlanEntryId", enterprisePlanEntryId);
		}
	}

	/**
	 * 点击效果评估按钮，弹出效果评估界面
	 */
	public void actionEvaluation_actionPerformed(ActionEvent e)
			throws Exception {
		int curRow = tblMain.getSelectManager().getActiveRowIndex();
		if (curRow >= 0) {
			String id = tblMain.getRow(curRow).getCell("EnterpriseScheme.id").getValue().toString();
			EnterpriseSchemeInfo info = EnterpriseSchemeFactory.getRemoteInstance().getEnterpriseSchemeInfo(new ObjectUuidPK(id));
			if (info != null) {
				if (FDCBillStateEnum.AUDITTED.equals(info.getState())) {
					UIContext context = new UIContext(this);
					context.put("ID", id);
					context.put("result", "result");
					UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(EnterpriseSchemeEditUI.class.getName(),context, null, OprtState.EDIT).show();
				} else {
					MsgBox.showInfo("单据未审核，不能录入效果评估信息！");
				}
			}
		}
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = getSelectedIdValues();
		((IFDCBill)getBizInterface()).unAudit(idList);
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = getSelectedIdValues();
		((IFDCBill)getBizInterface()).audit(idList);
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh(null);
	}
	/**
	 * 显示所有主题
	 */
	public void actionShowAllThemes_actionPerformed(ActionEvent e)throws Exception {
		this.refresh(null);
	}
	/**
	 * 新增按钮事件
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		super.actionAddNew_actionPerformed(e);
		this.refresh(null);
	}
	 private void checkObjectExists() throws BOSException, EASBizException, Exception {
	     if(getSelectedKeyValue() == null)
	         return;
	     if(!getBizInterface().exists(new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue()))))
	     {
	         refreshList();
	         throw new EASBizException(EASBizException.CHECKEXIST);
	     } else
	     {
	         return;
	     }
	 }
	 
	 protected String getKeyFieldName() {
		return "EnterpriseScheme.id";
	}
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		
		if(e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
        {
            int index = tblMain.getSelectManager().getActiveRowIndex();
            if (tblMain.getRow(index).getCell("EnterpriseScheme.id").getValue() == null) {
            	MsgBox.showInfo("没有企划实施，请新增！");
            	return;
			}else{
				super.tblMain_tableClicked(e);
			}
        } 
	}
	/**
	 * 开启自定义过滤界面
	 * 
	 */
	protected boolean initDefaultFilter() {
		return false;// 默认不弹出
	}

	/**
	 * 初始化过滤界面---ContractFilterUI
	 * 2009-10-09 by wp
	 * @return
	 */
    private CommonQueryDialog queryDlg = null;
	protected CommonQueryDialog initCommonQueryDialog() { //覆盖方法
		if (queryDlg != null)
			return queryDlg;
		try {
			queryDlg = new CommonQueryDialog();
			queryDlg.setOwner((Component) getUIContext().get("Owner"));
			queryDlg.setParentUIClassName(getClass().getName());
			queryDlg.setQueryObjectPK(mainQueryPK); //mainQueryPK 决定自定义过滤的字段
			queryDlg.setHeight(350);
			queryDlg.setWidth(570);
			queryDlg.addUserPanel(getFilterUI());//加入过滤界面
			queryDlg.setTitle("企划实施过滤界面");
			queryDlg.setShowSorter(true);
			queryDlg.setUiObject(this);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		return queryDlg;
	}

	/**
	 * 得到过滤界面
	 * 2009-10-09 by wp
	 * @return
	 * @throws Exception
	 */
	SchemeQueryPanelUI filterUI;
	public SchemeQueryPanelUI getFilterUI() throws Exception {
		if (filterUI == null)
			filterUI = new SchemeQueryPanelUI();
		return filterUI;
	}
	public void setBtnState(KDTable table,ICoreBase iCoreBase,ItemAction audit,ItemAction unAudit,ItemAction edit,ItemAction del) throws EASBizException, BOSException{
    	int index = table.getSelectManager().getActiveRowIndex();
		if (index == -1){
			audit.setEnabled(false);
			unAudit.setEnabled(false);
			edit.setEnabled(false);
			del.setEnabled(false);
			return;
		}
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("state");
		String id = (String) table.getRow(index).getCell("EnterpriseScheme.id").getValue();
		if(id==null){
			audit.setEnabled(false);
			unAudit.setEnabled(false);
			edit.setEnabled(false);
			del.setEnabled(false);
			return;
		}
		FDCBillInfo info=(FDCBillInfo)iCoreBase.getValue(new ObjectUuidPK(id),sel);
		if(FDCBillStateEnum.SUBMITTED.equals(info.getState())){
			audit.setEnabled(true);
			unAudit.setEnabled(false);
			edit.setEnabled(true);
			del.setEnabled(true);
		}else if(FDCBillStateEnum.AUDITTED.equals(info.getState())){
			audit.setEnabled(false);
			unAudit.setEnabled(true);
			edit.setEnabled(false);
			del.setEnabled(false);
		}else if(FDCBillStateEnum.SAVED.equals(info.getState())){
			audit.setEnabled(false);
			unAudit.setEnabled(false);
			edit.setEnabled(true);
			del.setEnabled(true);
		}else if(FDCBillStateEnum.AUDITTING.equals(info.getState())){
			audit.setEnabled(false);
			unAudit.setEnabled(false);
			edit.setEnabled(false);
			del.setEnabled(false);
		}
    }
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		
		setBtnState(this.tblMain,getBizInterface(),this.actionAudit,this.actionUnAudit,this.actionEdit,this.actionRemove);
		
		int index = tblMain.getSelectManager().getActiveRowIndex();
		if(index==-1){
			btnEvaluation.setEnabled(false);
			return;
		}
		String state = "";
		if (tblMain.getRow(index).getCell("EnterprisePlanEntry.state").getValue() != null) {
			state = tblMain.getRow(index).getCell("EnterprisePlanEntry.state").getValue().toString();
			if (ThemeEnum.Finish.getAlias().equals(state)) {
				this.actionEvaluation.setEnabled(true);
			}else{
				this.actionEvaluation.setEnabled(false);
			}
		}
		if(tblMain.getRow(index).getCell("EnterpriseScheme.id").getValue()== null){// 如果已经新增
			if(ThemeEnum.Canceled.getAlias().equals(state)){
				this.actionAddNew.setEnabled(false);
			}else{
				this.actionAddNew.setEnabled(true);
			}
		} else {
			this.actionAddNew.setEnabled(false);
		}

	}
	public boolean isIgnoreRowCount() {
		return false;
	}	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK pk,EntityViewInfo viewInfo) {
		EntityViewInfo view = (EntityViewInfo)viewInfo.clone();
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		FilterInfo filter = new FilterInfo();
		if(node!=null){
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo info = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("EnterprisePlan.sellProject.id", info.getId().toString()));
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				filter.getFilterItems().add(new FilterItemInfo("EnterprisePlan.sellProject.id",MeasurePlanTargetListUI.getAllProjectNodeId(node),CompareType.INCLUDE));
			}
			if(!this.kDCheckBox1.isSelected()){
				filter.getFilterItems().add(new FilterItemInfo("useing", Boolean.TRUE));
			}
		}
		try {
			if(view.getFilter()!=null){
				view.getFilter().mergeFilter(filter, "and");
			}else{
				view.setFilter(filter);
			}
			
			SorterItemCollection sort = new SorterItemCollection();
			sort.add(new SorterItemInfo("EnterprisePlan.id"));
			sort.add(new SorterItemInfo("seq"));
			sort.add(new SorterItemInfo("EnterpriseSellPlan.seq"));
			view.setSorter(sort);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return super.getQueryExecutor(pk, view);
	}
	protected String getEditUIName() {
		return EnterpriseSchemeEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return EnterpriseSchemeFactory.getRemoteInstance();
	}
	protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		mergerTable(this.tblMain, new String[]{"EnterprisePlan.id"},new String[]{"name","useing"});
		mergerTable(this.tblMain, new String[]{"id"},new String[]{"EnterprisePlanEntry.theme","EnterprisePlanEntry.state","EnterprisePlanEntry.describe"});
	}
	public void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
		int merger=0;
		Date nowTime = new Date();;
		try {
			nowTime = FDCCommonServerHelper.getServerTime();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		for(int i=0;i<table.getRowCount();i++){
			boolean isEnd = false;
        	if(table.getRow(i).getCell("EnterpriseSellPlan.state")!=null && table.getRow(i).getCell("EnterpriseSellPlan.state").getValue()!=null){
        		if(table.getRow(i).getCell("EnterpriseSellPlan.state").getValue().toString().equals("已完成")){
            		isEnd = true; 
        		}
        	}
        	if(!isEnd){
            	if(table.getRow(i).getCell("EnterpriseSellPlan.endTime")!=null){//企划实施,当事项未结束，系统日期晚于计划结束日期，则该行事项显示红色；
            		Date startTime = (Date)table.getRow(i).getCell("EnterpriseSellPlan.endTime").getValue(); 
            		if(startTime!=null && startTime.before(nowTime))
            			for(int j=0;j<table.getColumnCount();j++){
            				if(table.getColumn(j).getKey().indexOf("EnterpriseSellPlan")==0||table.getColumn(j).getKey().indexOf("EnterpriseScheme")==0){
            					table.getRow(i).getCell(table.getColumn(j).getKey()).getStyleAttributes().setBackground(Color.pink);
            				}
            			}
            	}
        	}
			if(i>0){
				boolean isMerge=true;
				for(int j=0;j<coloum.length;j++){
					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
					if(getString(curRow).equals("")||getString(lastRow).equals("")||!getString(curRow).equals(getString(lastRow))){
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
	private static String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}
	protected void refresh(ActionEvent e) throws Exception{
    	boolean isSelect=false;
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1){
			isSelect=true;
		}
		super.refresh(e);
        if(this.tblMain.getRowCount()==0){
        	this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAddNew.setEnabled(false);
			this.actionEvaluation.setEnabled(false);
        }
        if(isSelect){
        	tblMain.getSelectManager().select(0, 0);
        }
    }
	protected boolean isIgnoreCUFilter() {
    	return true;
    }
}