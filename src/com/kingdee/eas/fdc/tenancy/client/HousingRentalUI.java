/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.HousingRentalFacadeFactory;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class HousingRentalUI extends AbstractHousingRentalUI
{
    private static final Logger logger = CoreUIObject.getLogger(HousingRentalUI.class);
    private IRow footRow = null;
    public HousingRentalUI() throws Exception
    {
        super();
    }

    protected String getSelectedKeyValue() {
    	return null;
    }
  
  /**实现 加权显示功能
   * **/
	protected void kDCheckBox1_itemStateChanged(ItemEvent e) throws Exception {
		super.kDCheckBox1_itemStateChanged(e);
		boolean ischeck=kDCheckBox1.isSelected();
		for(int month=1;month<13;month++){
			tblMain.getColumn(month+"month").getStyleAttributes().setHided(!ischeck);
			tblMain.getColumn(month+"monthAmout").getStyleAttributes().setHided(!ischeck);
		}
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
    	actionRefresh.setVisible(false);
    	actionQuery.setVisible(false);
    	actionAddNew.setVisible(false);
    	actionRemove.setVisible(false);
    	actionEdit.setVisible(false);
    	actionAttachment.setVisible(false);
    	actionView.setVisible(false);
    	actionLocate.setVisible(false);
    	tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);

    	tblMain.removeColumn(tblMain.getColumnIndex("unitStandard"));
    	tblMain.removeColumn(tblMain.getColumnIndex("unitActual"));
    	tblMain.removeColumn(tblMain.getColumnIndex("unitDiffer"));
    	tblMain.removeColumn(tblMain.getColumnIndex("unitRate"));
    	
    	tblMain.removeColumn(tblMain.getColumnIndex("totalStandard"));
    	tblMain.removeColumn(tblMain.getColumnIndex("totalActual"));
    	tblMain.removeColumn(tblMain.getColumnIndex("totalDiffer"));
    	tblMain.removeColumn(tblMain.getColumnIndex("totalRate"));
    	
    	tblMain.removeColumn(tblMain.getColumnIndex("actStandard"));
    	tblMain.removeColumn(tblMain.getColumnIndex("actActual"));
    	tblMain.removeColumn(tblMain.getColumnIndex("actDiffer"));
    	tblMain.removeColumn(tblMain.getColumnIndex("actRate"));
    	
    	this.kDWorkButton1.setIcon(EASResource.getIcon("imgTbtn_execute"));//修复日单价（标，实际）
    	actionDayprice.setVisible(true);
    	actionDayprice.setEnabled(true);
    	kDWorkButton1.setVisible(true);
    	kDWorkButton1.setEnabled(true);
    	kDCheckBox1.setSelected(true);
    }
    /******
     * 修复日单价（标，实际）
     * *****/
	public void actionDayprice_actionPerformed(ActionEvent e) throws Exception {
		super.actionDayprice_actionPerformed(e);
		Map Map = null;
		if(HousingRentalFacadeFactory.getRemoteInstance().ReSetDayPrice(Map)){
			MsgBox.showInfo("修复合同日标准，实际日单价已成功");
		}else{
			
		}
	}

	protected void initTree() throws Exception
	{
		this.treeMain.setModel(SHEHelper.getSellOrderTree(actionOnLoad, MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		treeView.setShowButton(true);

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
		Map param = new HashMap();
		addTreeSelectParam(param);
		showDialog(param);
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
        		Map result = HousingRentalFacadeFactory.getRemoteInstance().getHousingRentalList(param);
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
//		tblMain.removeRows();
		List build = (List) result.get("build");
		//@by huanghefh
		SimpleDateFormat formater=new SimpleDateFormat("yyyy");
		Date timedata= (Date)result.get("timedata");
		if(tblMain.getHeadRow(0).getCell("1month")!=null){
			tblMain.getHeadRow(0).getCell("1month").setValue("加权面积出租率（"+formater.format(timedata)+"年 )");
			tblMain.getHeadRow(0).getCell(27).setValue("加权套房出租率（"+formater.format(timedata)+"年 )");
		}
		if(tblMain.getHeadRow(0).getCell("1monthAmout")!=null){
			tblMain.getHeadRow(0).getCell("1monthAmout").setValue("加权套房出租率（"+formater.format(timedata)+"年 )");
		}
		
		BigDecimal totalTotalArea =FDCHelper.ZERO;
		
		BigDecimal totalRentArea = FDCHelper.ZERO;
		BigDecimal totalRentedArea = FDCHelper.ZERO;
		BigDecimal totalUnRentedArea =FDCHelper.ZERO;
		
		BigDecimal totalRentAmount =FDCHelper.ZERO;
		BigDecimal totalRentedAmount =FDCHelper.ZERO;
		BigDecimal totalUnRentedAmount =FDCHelper.ZERO;
		
		if (build != null && build.size() > 0) {
			// 当前已加汇总行数
			int nums = 0;

			for (int i = 0; i < build.size(); i++) {
				Map buildList = (Map) build.get(i);
				if (buildList != null) {
					String id=(String)buildList.get("id");
					String org=(String)buildList.get("org");
					String projectName=(String)buildList.get("projectName");
					String subarea=(String)buildList.get("subarea");
					String bulidingName=(String)buildList.get("buildName");
					
					BigDecimal totalArea = (BigDecimal) buildList.get("totalArea")==null?FDCHelper.ZERO:(BigDecimal) buildList.get("totalArea");
					
					BigDecimal rentArea = (BigDecimal) buildList.get("rentArea")==null?FDCHelper.ZERO:(BigDecimal) buildList.get("rentArea");
					BigDecimal rentedArea = (BigDecimal) buildList.get("rentedArea")==null?FDCHelper.ZERO:(BigDecimal) buildList.get("rentedArea");
					BigDecimal unRentedArea = (BigDecimal) buildList.get("unRentedArea")==null?FDCHelper.ZERO:(BigDecimal) buildList.get("unRentedArea");
					BigDecimal rentRate = (BigDecimal) buildList.get("rentRate")==null?FDCHelper.ZERO:(BigDecimal) buildList.get("rentRate");
					
					BigDecimal rentAmount = (BigDecimal) buildList.get("rentAmount")==null?FDCHelper.ZERO:(BigDecimal) buildList.get("rentAmount");
					BigDecimal rentedAmount = (BigDecimal) buildList.get("rentedAmount")==null?FDCHelper.ZERO:(BigDecimal) buildList.get("rentedAmount");
					BigDecimal unRentedAmount = (BigDecimal) buildList.get("unRentedAmount")==null?FDCHelper.ZERO:(BigDecimal) buildList.get("unRentedAmount");
					BigDecimal rentAmountRate = (BigDecimal) buildList.get("rentAmountRate")==null?FDCHelper.ZERO:(BigDecimal) buildList.get("rentAmountRate");

					
					totalTotalArea =totalTotalArea.add(totalArea);
					
					totalRentArea = totalRentArea.add(rentArea);
					totalRentedArea = totalRentedArea.add(rentedArea);
					totalUnRentedArea =totalUnRentedArea.add(unRentedArea);
					
					totalRentAmount =totalRentAmount.add(rentAmount);
					totalRentedAmount =totalRentedAmount.add(rentedAmount);
					totalUnRentedAmount =totalUnRentedAmount.add(unRentedAmount);
					IRow row = tblMain.addRow();
					
					row.getCell("id").setValue(id);
					row.getCell("org").setValue(org);
					row.getCell("projectName").setValue(projectName);
					row.getCell("subarea").setValue(subarea);
					row.getCell("bulidingName").setValue(bulidingName);
					row.getCell("totalArea").setValue(totalArea);
					
					row.getCell("rentArea").setValue(rentArea);
					row.getCell("rentedArea").setValue(rentedArea);
					row.getCell("unRentArea").setValue(unRentedArea);
					row.getCell("rentRate").setValue(rentRate);
					
					row.getCell("rentAmount").setValue(rentAmount);
					row.getCell("rentedAmount").setValue(rentedAmount);
					row.getCell("unRentAmount").setValue(unRentedAmount);
					row.getCell("rentAmountRate").setValue(rentAmountRate);
					//by huanghefh
					Map monthArea=(Map)buildList.get("mrote");
					for(int month=1;month<13;month++){
						BigDecimal monthRateArea = (BigDecimal)monthArea.get(month+"monthArea")==null?FDCHelper.ZERO:(BigDecimal)(BigDecimal)monthArea.get(month+"monthArea");
						BigDecimal monthRateAmount = (BigDecimal)monthArea.get(month+"monthAmount")==null?FDCHelper.ZERO:(BigDecimal)monthArea.get(month+"monthAmount");
						monthRateArea=monthRateArea.compareTo(new BigDecimal(100))>0?(new BigDecimal(100)):monthRateArea;
						monthRateAmount=monthRateAmount.compareTo(new BigDecimal(100))>0?(new BigDecimal(100)):monthRateAmount;
						row.getCell(month+"month").setValue(monthRateArea);
						row.getCell(month+"monthAmout").setValue(monthRateAmount);
					}
					//
					nums++;
				}
			
			}
			
			//增加合计行
			footRow.getCell("totalArea").setValue(totalTotalArea );
			
			footRow.getCell("rentArea").setValue(totalRentArea );
			footRow.getCell("rentedArea").setValue(totalRentedArea );
			footRow.getCell("unRentArea").setValue(totalUnRentedArea);
			
			footRow.getCell("rentAmount").setValue(totalRentAmount);
			footRow.getCell("rentedAmount").setValue(totalRentedAmount);
			footRow.getCell("unRentAmount").setValue(totalUnRentedAmount);
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

}