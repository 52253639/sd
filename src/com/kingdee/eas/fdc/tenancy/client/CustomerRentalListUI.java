/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.CustomerRentalFacadeFactory;
import com.kingdee.eas.framework.*;

/**
 * 房源租赁明细表
 */
public class CustomerRentalListUI extends AbstractCustomerRentalListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerRentalListUI.class);
    private IRow footRow = null;
    private CustomerRentalListFilterUI filterUI = null;
	private CommonQueryDialog commonQueryDialog = null;
    /**
     * output class constructor
     */
    public CustomerRentalListUI() throws Exception
    {
        super();
    }

    protected String getSelectedKeyValue() {
    	return null;
    }
    public void onLoad() throws Exception {
    	KDTFootManager footRowManager= tblMain.getFootManager();
		footRowManager = new KDTFootManager(tblMain);
		footRowManager.addFootView();
		tblMain.setFootManager(footRowManager);
		footRow= footRowManager.addFootRow(0);
		footRow.setUserObject("FDC_PARAM_TOTALCOST");
		footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
		footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
		tblMain.getIndexColumn().setWidth(60);
		footRowManager.addIndexText(0, "合计");  
    	super.onLoad();
    	actionAddNew.setVisible(false);
    	actionRemove.setVisible(false);
    	actionEdit.setVisible(false);
    	actionAttachment.setVisible(false);
//    	actionQuery.setVisible(false);
    	actionView.setVisible(false);
//    	actionRefresh.setVisible(false);
    	actionLocate.setVisible(false);
    	tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	
    	this.setUITitle("房源租赁明细表");
    }
	protected void initTree() throws Exception
	{
		this.treeMain.setModel(FDCTreeHelper.getBuildingTree(actionOnLoad, null));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	protected String getKeyFieldName() {
		return null;
	}
	protected void fillFirstData(RequestRowSetEvent e) {;
	}
	private void addTreeSelectParam(Map param) {
		if (param == null) {
			param = new HashMap();
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			Object obj = node.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				Set set = getSelectSellProjects(node);
				param.put("orgUnit", set);
			} else if (obj instanceof SellProjectInfo) {
				param.put("project", ((SellProjectInfo) obj).getId().toString());
			} else if (obj instanceof BuildingInfo) {
				param.put("build", ((BuildingInfo) obj).getId().toString());
			}else if (obj instanceof SubareaInfo) {
				param.put("subareaInfo", ((SubareaInfo) obj).getId().toString());
			}
		}
	}
	private Set getSelectSellProjects(DefaultKingdeeTreeNode node) {
		Set projects = new HashSet();
		Enumeration en = node.breadthFirstEnumeration();
		while (en.hasMoreElements()) {
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) en.nextElement();
			if (child.getUserObject() instanceof SellProjectInfo) {
				projects.add(((SellProjectInfo) child.getUserObject()).getId().toString());
			}
		}
		return projects;
	}
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
		execQuery();
     }
	public void showDialog(final Map param){
        Window win = null;
        LongTimeDialog dialog = null;
        if(this.getParent() instanceof KDTabbedPane){
        	win = SwingUtilities.getWindowAncestor(this);
        	if(SwingUtilities.getWindowAncestor(this) instanceof Frame)
        		dialog = new LongTimeDialog((Frame)win);
        	else
        		dialog = new LongTimeDialog((Dialog)win);
        }else{
        	dialog = new LongTimeDialog((Frame)this.getParent());
        }
        dialog.setLongTimeTask(new ILongTimeTask() {
        	public Object exec()throws Exception {
        		Map result = CustomerRentalFacadeFactory.getRemoteInstance().getCustomerRentalListList(param);
	    		fillTable(result);
	    		return result;
           }
        	public void afterExec(Object result){
        		
        	}
        });
        dialog.show();
    }
	private void fillTable(Map result) {
		tblMain.removeRows();
		List list = (List) result.get("customer");
		
		BigDecimal alltotalBuildingArea =FDCHelper.ZERO;
		
		BigDecimal allappAmount = FDCHelper.ZERO;
		BigDecimal allactAmount =FDCHelper.ZERO;
		
		BigDecimal alllastAmount = FDCHelper.ZERO;
		BigDecimal allleaAppAmount =FDCHelper.ZERO;
		BigDecimal allleaActAmount = FDCHelper.ZERO;
		BigDecimal allleaLastAmount = FDCHelper.ZERO;
		
		BigDecimal allallAppAmount = FDCHelper.ZERO;
		BigDecimal allallActAmount = FDCHelper.ZERO;
		BigDecimal allallLastAmount = FDCHelper.ZERO;
		
		BigDecimal alldepositAmount =FDCHelper.ZERO;
			
		BigDecimal allendDepositAmount =FDCHelper.ZERO;
		BigDecimal alldepositLastAmount =FDCHelper.ZERO;
		
		if (list != null && list.size() > 0) {
			// 当前已加汇总行数
			int nums = 0;

			for (int i = 0; i < list.size(); i++) {
				Map customerList = (Map) list.get(i);
				if (customerList != null) {
					
					String id=(String)customerList.get("id");
					String org=(String)customerList.get("org");
					String projectName=(String)customerList.get("projectName");
					String build=(String)customerList.get("build");
					String floor=(String)customerList.get("floor");
					String cell=(String)customerList.get("cell");
					String type=(String)customerList.get("type");
					String customer=(String)customerList.get("customer");
					String tenancyNumber=(String)customerList.get("tenancyNumber");
					String tenancyName=(String)customerList.get("tenancyName");
					
					BigDecimal totalBuildingArea = (BigDecimal) customerList.get("totalBuildingArea")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("totalBuildingArea");
					
					
					Date actDeliveryRoomDate=(Date) customerList.get("actDeliveryRoomDate");
					
					Date startDate=(Date) customerList.get("startDate");
					Date endDate=(Date) customerList.get("endDate");
					Date actQuitTenDate=(Date) customerList.get("actQuitTenDate");
					
					Integer leaseCount = (Integer) customerList.get("leaseCount");
					Integer actLeaseCount = (Integer) customerList.get("actLeaseCount");
					Integer lastLeaseCount = (Integer) customerList.get("lastLeaseCount");
					
					BigDecimal appAmount = (BigDecimal) customerList.get("appAmount")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("appAmount");
					BigDecimal actAmount = (BigDecimal) customerList.get("actAmount")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("actAmount");
					
					BigDecimal lastAmount = (BigDecimal) customerList.get("lastAmount")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("lastAmount");
					BigDecimal leaAppAmount = (BigDecimal) customerList.get("leaAppAmount")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("leaAppAmount");
					BigDecimal leaActAmount = (BigDecimal) customerList.get("leaActAmount")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("leaActAmount");
					BigDecimal leaLastAmount = (BigDecimal) customerList.get("leaLastAmount")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("leaLastAmount");
					
					BigDecimal allAppAmount = (BigDecimal) customerList.get("allAppAmount")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("allAppAmount");
					BigDecimal allActAmount = (BigDecimal) customerList.get("allActAmount")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("allActAmount");
					BigDecimal allLastAmount = (BigDecimal) customerList.get("allLastAmount")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("allLastAmount");
				
					
					BigDecimal unitStandard = (BigDecimal) customerList.get("unitStandard")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("unitStandard");
					BigDecimal unitActual = (BigDecimal) customerList.get("unitActual")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("unitActual");
					BigDecimal unitDiffer = (BigDecimal) customerList.get("unitDiffer")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("unitDiffer");
					BigDecimal unitRate = (BigDecimal) customerList.get("unitRate")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("unitRate");
					
					BigDecimal depositAmount = (BigDecimal) customerList.get("depositAmount")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("depositAmount");
					
					Date appDepositDate=(Date)customerList.get("appDepositDate");
					Date depositDate=(Date)customerList.get("depositDate");
			
					BigDecimal endDepositAmount = (BigDecimal) customerList.get("endDepositAmount")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("endDepositAmount");
					BigDecimal depositLastAmount = (BigDecimal) customerList.get("depositLastAmount")==null?FDCHelper.ZERO:(BigDecimal) customerList.get("depositLastAmount");
					
					String department=(String)customerList.get("department");
					String tenancyAdviser=(String)customerList.get("tenancyAdviser");
					
					
					alltotalBuildingArea =alltotalBuildingArea.add(totalBuildingArea);
					allappAmount=allappAmount.add(appAmount);
					allactAmount = allactAmount.add(actAmount);
					
					
					alllastAmount = alllastAmount.add(lastAmount);
					allleaAppAmount =allleaAppAmount.add(leaAppAmount);
					allleaActAmount = allleaActAmount.add(leaActAmount);
					allleaLastAmount=allleaLastAmount.add(leaLastAmount);
					
					allallAppAmount=allallAppAmount.add(allAppAmount);
					allallActAmount=allallActAmount.add(allActAmount);
					allallLastAmount=allallLastAmount.add(allLastAmount);
					
					alldepositAmount=alldepositAmount.add(depositAmount);
					
					allendDepositAmount =allendDepositAmount.add(endDepositAmount);
					alldepositLastAmount =alldepositLastAmount.add(depositLastAmount);
					
					IRow row = tblMain.addRow();
					
					row.getCell("id").setValue(id);
					row.getCell("org").setValue(org);
					row.getCell("projectName").setValue(projectName);
					row.getCell("build").setValue(build);
					row.getCell("floor").setValue(floor);
					row.getCell("cell").setValue(cell);
					row.getCell("type").setValue(type);
					
					row.getCell("customer").setValue(customer);
					row.getCell("tenancyNumber").setValue(tenancyNumber);
					row.getCell("tenancyName").setValue(tenancyName);
					
					row.getCell("totalBuildingArea").setValue(totalBuildingArea);

					row.getCell("actDeliveryRoomDate").setValue(actDeliveryRoomDate);
					row.getCell("startDate").setValue(startDate);
					row.getCell("endDate").setValue(endDate);
					row.getCell("actQuitTenDate").setValue(actQuitTenDate);
					
					row.getCell("leaseCount").setValue(leaseCount);
					//大于总租期取总租期 @by huanghefh
					if(actLeaseCount.compareTo(leaseCount)>0){	
						row.getCell("actLeaseCount").setValue(leaseCount);
					}else{	
						row.getCell("actLeaseCount").setValue(actLeaseCount);
					}	
					
					//小于零取零 @by huanghefh
					if(lastLeaseCount.compareTo(new Integer(0))<0){	
						row.getCell("lastLeaseCount").setValue(new Integer(0));
					}else{	
						row.getCell("lastLeaseCount").setValue(lastLeaseCount);
					}	
					row.getCell("appAmount").setValue(appAmount);
					row.getCell("actAmount").setValue(actAmount);
					row.getCell("lastAmount").setValue(lastAmount);
					row.getCell("leaAppAmount").setValue(leaAppAmount);
					row.getCell("leaActAmount").setValue(leaActAmount);
					row.getCell("leaLastAmount").setValue(leaLastAmount);
					
					row.getCell("allAppAmount").setValue(allAppAmount);
					row.getCell("allActAmount").setValue(allActAmount);
					row.getCell("allLastAmount").setValue(allLastAmount);
					
					row.getCell("unitStandard").setValue(unitStandard);
					row.getCell("unitActual").setValue(unitActual);
					
					row.getCell("unitDiffer").setValue(unitDiffer);
					row.getCell("unitRate").setValue(unitRate);
					
					row.getCell("depositAmount").setValue(depositAmount);
					row.getCell("appDepositDate").setValue(appDepositDate);
					row.getCell("depositDate").setValue(depositDate);
					row.getCell("endDepositAmount").setValue(endDepositAmount);
								
					row.getCell("depositLastAmount").setValue(depositLastAmount);
					row.getCell("department").setValue(department);
					row.getCell("tenancyAdviser").setValue(tenancyAdviser);
					
					nums++;
				}
			}
			footRow.getCell("totalBuildingArea").setValue(alltotalBuildingArea );
			
			footRow.getCell("appAmount").setValue(allappAmount );
			footRow.getCell("actAmount").setValue(allactAmount );
			
			footRow.getCell("lastAmount").setValue(alllastAmount );
			footRow.getCell("leaAppAmount").setValue(allleaAppAmount );
			footRow.getCell("leaActAmount").setValue(allleaActAmount );
			footRow.getCell("leaLastAmount").setValue(allleaLastAmount );
			
			footRow.getCell("allAppAmount").setValue(allallAppAmount );
			footRow.getCell("allActAmount").setValue(allallActAmount);
			footRow.getCell("allLastAmount").setValue(allallLastAmount );
			
			footRow.getCell("depositAmount").setValue(alldepositAmount );
				
			footRow.getCell("endDepositAmount").setValue(allendDepositAmount );
			footRow.getCell("depositLastAmount").setValue(alldepositLastAmount );
		}
		
	}
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	
    }

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
	}

	public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception {
		super.actionMoveTree_actionPerformed(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.execQuery();
	}
	protected void execQuery() {
		Map param = getFilterParam();
		addTreeSelectParam(param);
		showDialog(param);
	}
	
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(620);
		commonQueryDialog.setTitle("房源租赁明细表 - 条件查询");
		try {
			commonQueryDialog.addUserPanel(this.getFilterUI());
		} catch (Exception e) {
			super.handUIException(e);
		}
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected boolean initDefaultFilter() {
		return true;
	}
	private CustomerRentalListFilterUI getFilterUI() {
		if (filterUI == null) {
			try {
				filterUI = new CustomerRentalListFilterUI();
			} catch (Exception e) {
				handleException(e);
			}
		}
		return filterUI;
	}
	private Map getFilterParam() {
		if (filterUI == null) {
			filterUI = getFilterUI();
		}
		Map param = new HashMap();
		if (filterUI != null) {
			param = filterUI.getParam();
		}
		return param;
	}
}