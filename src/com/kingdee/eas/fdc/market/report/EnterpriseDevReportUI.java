/**
 * output package name
 */
package com.kingdee.eas.fdc.market.report;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
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
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.report.CustomerVisitSumReportFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class EnterpriseDevReportUI extends AbstractEnterpriseDevReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(EnterpriseDevReportUI.class);
    Map map = new HashMap();
    /**
     * output class constructor
     */
    public EnterpriseDevReportUI() throws Exception
    {
        super();
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
		
		return new com.kingdee.eas.fdc.market.report.EnterpriseDevReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return null;
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.framework.report.client.CommRptBaseUI#onLoad()
	 */
	public void onLoad() throws Exception {
		setShowDialogOnLoad(false);
		super.onLoad();
		initTable();
		initTree();
	}
	protected void initTree() throws Exception{
		this.treeMain.setModel(FDCTreeHelper.getSellProjectForSHESellProject(actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		//SHEManageHelper.getProductTypeNodes(this.treeMain,(DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);
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
	}
	protected void query() {
		getTableForPrintSetting().removeRows();
		if(map.get("fullOrgUnit")==null&&map.get("sellProject")==null){
			return;
		}
		map.put("fromBeginDate", params.getObject("fromBeginDate"));
		map.put("toBeginDate", params.getObject("toBeginDate"));
		map.put("fromEndDate", params.getObject("fromEndDate"));
		map.put("toEndDate", params.getObject("toEndDate"));
		try {
			Map map1 = EnterpriseDevReportFacadeFactory.getRemoteInstance().getTableList(map);
			if(map1!=null){
				List list = (ArrayList)map1.get("list");
				//总计
				BigDecimal planCostSum = new BigDecimal("0.00");//总计计划金额
				BigDecimal factAmountSum = new BigDecimal("0.00");//总计实际金额
				BigDecimal contractAmountSum = new BigDecimal("0.00");//总计合同金额
				//小计
				BigDecimal planCostSubtotal = new BigDecimal("0.00");//小计计划金额
				BigDecimal factAmountSubtotal = new BigDecimal("0.00");//小计实际金额
				BigDecimal contractAmountSubtotal = new BigDecimal("0.00");//小计合同金额
				int rowCount = 0;
				String accountViewName = "";
				for(int i=0;i<list.size();i++){
					Map detialMap = (Map)list.get(i);
					DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
					if(treeNode!=null&&detialMap.get("accountView")!=null){
						if((i!=0&&!accountViewName.equals(detialMap.get("accountView")))){
							accountViewName = (String)detialMap.get("accountView");
							IRow subtotalRow = this.tblMain.addRow();
							rowCount++;
							subtotalRow.getCell("accountView").setValue("小计");
							subtotalRow.getCell("planCost").setValue(planCostSubtotal);
							subtotalRow.getCell("factAmount").setValue(factAmountSubtotal);
							subtotalRow.getCell("contractAmount").setValue(contractAmountSubtotal);
							
							if(planCostSubtotal.compareTo(new BigDecimal("0.00"))>0){
								BigDecimal subDevRate = (contractAmountSubtotal.subtract(planCostSubtotal)).divide(planCostSubtotal,4,BigDecimal.ROUND_HALF_UP);
								subDevRate = subDevRate.multiply(new BigDecimal("100"));
								subtotalRow.getCell("devRate").setValue(subDevRate);//偏差率%
							}else{
								subtotalRow.getCell("devRate").setValue(null);
							}
							
							subtotalRow.getStyleAttributes().setBackground(new Color(255,255,218));
							planCostSubtotal = new BigDecimal("0.00");
							factAmountSubtotal = new BigDecimal("0.00");
							contractAmountSubtotal = new BigDecimal("0.00");
						}
						if(!accountViewName.equals(detialMap.get("accountView"))){
							accountViewName = (String)detialMap.get("accountView");
						}
						IRow row = this.tblMain.addRow();
						rowCount++;
						row.getCell("accountView").setValue(detialMap.get("accountView"));
						row.getCell("channelType").setValue(detialMap.get("channelType"));
						row.getCell("mediaName").setValue(detialMap.get("mediaName"));
						row.getCell("themeName").setValue(detialMap.get("themeName"));
						row.getCell("themeDescription").setValue(detialMap.get("themeDescription"));
						row.getCell("contentItem").setValue(detialMap.get("contentItem"));
						row.getCell("planCost").setValue(detialMap.get("planCost"));
						row.getCell("factAmount").setValue(detialMap.get("factAmount"));
						BigDecimal devRate = new BigDecimal("0.00");
						if(detialMap.get("planCost")!=null&&detialMap.get("contractAmount")!=null&&
								((BigDecimal)detialMap.get("planCost")).compareTo(new BigDecimal("0.00"))>0){
							devRate = (((BigDecimal)detialMap.get("contractAmount")).subtract((BigDecimal)detialMap.get("planCost"))).divide((BigDecimal)detialMap.get("planCost"),4,BigDecimal.ROUND_HALF_UP);
							devRate = devRate.multiply(new BigDecimal("100"));
							row.getCell("devRate").setValue(devRate);//偏差率%
						}else{
							row.getCell("devRate").setValue(null);
						}
						row.getCell("contractAmount").setValue(detialMap.get("contractAmount"));
						row.getCell("payAmount").setValue(detialMap.get("payAmount"));
						row.getCell("contractName").setValue(detialMap.get("contractName"));
						row.getCell("contractID").setValue(detialMap.get("contractID"));//"OP/ubd9oR56CjRGpsBLYLQ1t0fQ="
						row.getCell("contractType").setValue(detialMap.get("contractType"));
						
						//计划金额
						if(row.getCell("planCost").getValue()!=null){
							planCostSum = planCostSum.add((BigDecimal)row.getCell("planCost").getValue());
							planCostSubtotal = planCostSubtotal.add((BigDecimal)row.getCell("planCost").getValue());
						}
						//实际金额
						if(row.getCell("factAmount").getValue()!=null){
							factAmountSum = factAmountSum.add((BigDecimal)row.getCell("factAmount").getValue());
							factAmountSubtotal = factAmountSubtotal.add((BigDecimal)row.getCell("factAmount").getValue());
						}
						//合同金额
						if(row.getCell("contractAmount").getValue()!=null){
							contractAmountSum = contractAmountSum.add((BigDecimal)row.getCell("contractAmount").getValue());
							contractAmountSubtotal = contractAmountSubtotal.add((BigDecimal)row.getCell("contractAmount").getValue());
						}
					}
				}
				if(rowCount>0){
					//最后一个小计行
					IRow subtotalRow = this.tblMain.addRow();
					rowCount++;
					subtotalRow.getCell("accountView").setValue("小计");
					subtotalRow.getCell("planCost").setValue(planCostSubtotal);//计划金额（元）
					subtotalRow.getCell("factAmount").setValue(factAmountSubtotal);
					subtotalRow.getCell("contractAmount").setValue(contractAmountSubtotal);//合同金额
					
					if(planCostSubtotal.compareTo(new BigDecimal("0.00"))>0){
						BigDecimal subDevRate = (contractAmountSubtotal.subtract(planCostSubtotal)).divide(planCostSubtotal,4,BigDecimal.ROUND_HALF_UP);
						subDevRate = subDevRate.multiply(new BigDecimal("100"));
						subtotalRow.getCell("devRate").setValue(subDevRate);//偏差率%
					}else{
						subtotalRow.getCell("devRate").setValue(null);
					}
					
					subtotalRow.getStyleAttributes().setBackground(new Color(255,255,218));
					//总计行
					IRow row = this.tblMain.addRow();
					rowCount++;
					row.getCell("accountView").setValue("总计");
					row.getCell("planCost").setValue(planCostSum);
					row.getCell("factAmount").setValue(factAmountSum);
					row.getCell("contractAmount").setValue(contractAmountSum);
					
					if(planCostSum.compareTo(new BigDecimal("0.00"))>0){
						BigDecimal subDevRate = (contractAmountSum.subtract(planCostSum)).divide(planCostSum,4,BigDecimal.ROUND_HALF_UP);
						subDevRate = subDevRate.multiply(new BigDecimal("100"));
						row.getCell("devRate").setValue(subDevRate);//偏差率%
					}else{
						row.getCell("devRate").setValue(null);
					}
					
					row.getStyleAttributes().setBackground(new Color(255,255,204));
					this.tblMain.setRowCount(rowCount);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

	}
	
	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.market.report.AbstractEnterpriseDevReportUI#tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent)
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		//super.tblMain_tableClicked(e);
		if(e.getColIndex()==10||e.getColIndex()==12){
			if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
				if(tblMain.getSelectManager()!=null&&tblMain.getSelectManager().size()>0){
					
					IRow row = this.tblMain.getRow(e.getRowIndex());
					if(row!=null&&row.getCell("contractID").getValue()!=null){
						String id = row.getCell("contractID").getValue().toString();
						if(!id.equals("")){
							if(row.getCell("contractType").getValue()!=null&&row.getCell("contractType").getValue().toString().equals("contractWithoutText")){
								UIContext uiContext = new UIContext(this);
								uiContext.put("ID", id);
								IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractWithoutTextEditUI.class.getName(), uiContext, null, OprtState.VIEW);
								uiWindow.show();
							}
							if(row.getCell("contractType").getValue()!=null&&row.getCell("contractType").getValue().toString().equals("contractBill")){
								UIContext uiContext = new UIContext(this);
								uiContext.put("ID", id);
								IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
								uiWindow.show();
							}
						}
					}
				}
			}
		}
	}

	public void tableDataRequest(KDTDataRequestEvent arg0) {
		
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.market.report.AbstractEnterpriseDevReportUI#treeMain_valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				map.put("sellProject", null);
				map.put("fullOrgUnit", treeNode.getUserObject());
			}else 
			if(treeNode !=null && treeNode.getUserObject() instanceof SellProjectInfo){
				map.put("sellProject", treeNode.getUserObject());
				map.put("fullOrgUnit",null);
			}
			query();
		}
	}

 

}