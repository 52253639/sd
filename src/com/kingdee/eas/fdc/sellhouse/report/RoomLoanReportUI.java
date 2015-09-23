/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.PeriodEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;

/**
 * output class name
 */
public class RoomLoanReportUI extends AbstractRoomLoanReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomLoanReportUI.class);
    
    public RoomLoanReportUI() throws Exception
    {
        super();
        disposeUIWindow();
    }

	protected RptParams getParamsForInit() {
		return null;
	}
	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return null;
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return RoomLoanReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
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
            	getTableForPrintSetting().removeRows();
            	getTableForPrintSetting().removeColumns();
            	RptParams resultRpt= RoomLoanReportFacadeFactory.getRemoteInstance().query(params);
             	return resultRpt;
            }
            public void afterExec(Object result)throws Exception{
            	RptParams rpt = getRemoteInstance().createTempTable(params);
                RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                KDTableUtil.setHeader(header, tblMain);
                
                tblMain.getColumn("state").setRenderer(new ObjectValueRender(){
        			public String getText(Object obj) {
        				if(obj!=null){
        					return AFMortgagedStateEnum.getEnum(Integer.parseInt(obj.toString())).getAlias();
        				}
        				return null;
        			}
        		});
                FDCHelper.formatTableDate(tblMain, "bizDate");
                FDCHelper.formatTableDate(tblMain, "actDate");
                FDCHelper.formatTableDate(tblMain, "approach1");
                FDCHelper.formatTableDate(tblMain, "approach2");
                FDCHelper.formatTableDate(tblMain, "approach3");
                FDCHelper.formatTableDate(tblMain, "approach4");
                FDCHelper.formatTableDate(tblMain, "approach5");
                
                RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
    	        tblMain.setRowCount(rs.getRowCount()+tblMain.getRowCount());
    	        KDTableUtil.insertRows(rs, 0, tblMain);
            }
        } );
        dialog.show();
	}

	public void tableDataRequest(KDTDataRequestEvent arg0) {
		
	}
	/* (non-Javadoc)
	 * @see com.kingdee.eas.framework.report.client.CommRptBaseUI#onLoad()
	 */
	public void onLoad() throws Exception {
		initTable();
		FDCSysContext.getInstance().checkIsSHEOrg(this);
		setShowDialogOnLoad(false);
		super.onLoad();
		initTree();
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		 this.actionQuery.setVisible(false);
	}
	
	protected void initTable(){
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
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
		this.treeMain.setModel(CRMTreeHelper.getBuildingTree(actionOnLoad,true));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.sellhouse.report.AbstractRoomSourceReportUI#treeMain_valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
//		super.treeMain_valueChanged(e);
//		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
//		if(treeNode!=null){
//			Object obj = treeNode.getUserObject();
//			if (obj instanceof OrgStructureInfo) {
//				params.setObject("sellProject", null);
//			}else if (treeNode.getUserObject() instanceof SellProjectInfo){
//				//项目
//				if(treeNode.getUserObject() != null){
//					params.setObject("buildUnit", null);
//					params.setObject("building", null);
//					params.setObject("sellProject", treeNode.getUserObject());
//				}			
//			}else if (treeNode.getUserObject() instanceof BuildingInfo){ 
//				// 楼栋
//				if(treeNode.getUserObject() != null){
//					BuildingInfo building=(BuildingInfo)treeNode.getUserObject();
//					params.setObject("buildUnit", null);
//					params.setObject("building", building);
//					params.setObject("sellProject", building.getSellProject());
//				}
//			}else if (treeNode.getUserObject() instanceof BuildingUnitInfo){ 
//				// 单元
//				if(treeNode.getUserObject() != null){
//					BuildingUnitInfo buildUnit=(BuildingUnitInfo)treeNode.getUserObject();
//					BuildingInfo building=buildUnit.getBuilding();
//					params.setObject("buildUnit", buildUnit);
//					params.setObject("building", building);
//					params.setObject("sellProject", building.getSellProject());
//				}
//			}
//			query();
//		}
		
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				params.setObject("sellProject", allSpIdStr);
				params.setObject("buildUnit", null);
				params.setObject("building", null);
			}else if(obj instanceof SellProjectInfo){
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				params.setObject("sellProject", allSpIdStr);
				params.setObject("buildUnit", null);
				params.setObject("building", null);
			}else if (obj instanceof BuildingInfo){ 
				BuildingInfo building=(BuildingInfo)obj;
				params.setObject("sellProject", "'"+building.getSellProject().getId().toString()+"'");
				params.setObject("buildUnit", null);
				params.setObject("building", building);
			}else if (obj instanceof BuildingUnitInfo){ 
				BuildingUnitInfo buildUnit=(BuildingUnitInfo)obj;
				BuildingInfo building=buildUnit.getBuilding();
				params.setObject("sellProject", "'"+building.getSellProject().getId().toString()+"'");
				params.setObject("buildUnit", buildUnit);
				params.setObject("building", building);
			}
			query();
		}
	}

}