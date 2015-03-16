/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.market.client.TableUtils;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ActivityValueHistoryListUI extends AbstractActivityValueHistoryListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ActivityValueHistoryListUI.class);
    
    /**
     * output class constructor
     */
    public ActivityValueHistoryListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.actionView.setVisible(false);
    	this.actionAddNew.setVisible(false);
    	this.actionQuery.setVisible(false);
    	this.actionRefresh.setVisible(false);
    	this.actionEdit.setVisible(false);
    	this.actionRemove.setVisible(false);
    	this.actionLocate.setVisible(false);
    	this.actionPrint.setVisible(false);
    	this.actionPrintPreview.setVisible(false);
    	this.btnLocate.setVisible(false);
    	tblMain.getColumn("projectlongnumber").getStyleAttributes().setHided(true);
    	tblMain.getColumn("description").getStyleAttributes().setHided(true);
    	tblMain.getColumn("projectname").getStyleAttributes().setHided(true);
    	versionTable.getColumn("id").getStyleAttributes().setHided(true);
    	versionTable.getStyleAttributes().setLocked(true);
    	versionTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    }
    public void onShow()  throws Exception
    {
    	super.onShow();
    	String projectId = (String)getUIContext().get("projectId");
    	if(projectId!=null)
    		fillVersionTable(projectId);
    	if(versionTable.getRowCount()>0){
    		String version = (String)versionTable.getRow(0).getCell("version").getValue();
    		executeQuery(version);
    	}else{
    		executeQuery("V10000.0");
    	}
    }
    /**
     * 点击版本号列表
     * */
    protected void versionTable_tableClicked(KDTMouseEvent e) throws Exception {
		super.versionTable_tableClicked(e);
		int indexRow = versionTable.getSelectManager().getActiveRowIndex();
		String version = (String)versionTable.getRow(indexRow).getCell("version").getValue();
		executeQuery(version);
	}
	/**
     * 根据版本号去过滤动态货值
     * */
    public void executeQuery(String version){
    	String projectId = (String)getUIContext().get("projectId");
    	if(version!=null && projectId!=null){
    		EntityViewInfo evInfo = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("projectid",projectId));
    		filter.getFilterItems().add(new FilterItemInfo("description",version));
    		evInfo.setFilter(filter);
    		SorterItemCollection sic=new SorterItemCollection();
    		SorterItemInfo info=new SorterItemInfo("seq");
    		info.setSortType(SortType.ASCEND);
    		sic.add(info);
    		evInfo.setSorter(sic);
    		mainQuery = evInfo;
    		execQuery();
    		
    		ActivityValueReportUI.getRowSum(tblMain);
    		ActivityValueReportUI.setTotalRow(tblMain);
    		ActivityValueReportUI.reSetExpressions(tblMain);
    	}
    }
    protected boolean isCanOrderTable() {
    	return false;
    }
    /**
	 * 获取版本号,并形成版本列表
	 * */
	public void fillVersionTable(String projectid) throws Exception{
		String version = "";
		String quickname = "";
		String quickDes = "";
		String sql = "select max(FCreateTime) FCreateTime,fdescription_l2,fquickname,fquickDes from T_MAR_ActivityValueReport where fprojectid='"+projectid+"' " +
				" group by fdescription_l2,fquickname,fquickDes " +
				" order by fdescription_l2 desc";
		FDCSQLBuilder db = new FDCSQLBuilder();
		db.appendSql(sql);
		IRowSet rowset = db.executeQuery();
		while(rowset.next()){
			version = rowset.getString("fdescription_l2");
			quickname = rowset.getString("fquickname");
			quickDes = rowset.getString("fquickDes");
			IRow row = versionTable.addRow();
			row.getCell("id").setValue(projectid);
			row.getCell("version").setValue(version);
			row.getCell("quickName").setValue(quickname);
			row.getCell("quickDes").setValue(quickDes);
			row.getCell("createTime").setValue(rowset.getDate("FCreateTime"));
		}
		versionTable.getSelectManager().select(0, 0);
	}
  //设置合计行以及融合
	public static void setTotalRow(KDTable table){
		int business = 0;
		int park = 0;
		int publicBuild = 0;
		int house = 0;
		KDTMergeManager mm = table.getMergeManager();
		int rowCount = table.getRowCount();
		for(int i=0;i<rowCount;i++){
			String planIndexType = (String)table.getCell(i, 3).getValue();
			if(PlanIndexTypeEnum.business.getAlias().equals(planIndexType)){
				if(business==0){
					business = i;
					IRow row = table.addRow(i);
					rowCount++;
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(4).setValue("合计");
					row.getCell(3).setValue(PlanIndexTypeEnum.house.getAlias());
					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
					mm.mergeBlock(house, 3, business, 3, KDTMergeManager.SPECIFY_MERGE);
				}
			}if("车位".equals(planIndexType)){
				if(park==0){
					park = i;
					IRow row = table.addRow(i);
					rowCount++;
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(4).setValue("合计");
					row.getCell(3).setValue(PlanIndexTypeEnum.business.getAlias());
					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
					mm.mergeBlock(business+1, 3, park, 3, KDTMergeManager.SPECIFY_MERGE);
				}
			}if(PlanIndexTypeEnum.publicBuild.getAlias().equals(planIndexType)){
				if(publicBuild==0){
					publicBuild=i;
					IRow row = table.addRow(i);
					rowCount++;
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(4).setValue("合计");
					row.getCell(3).setValue("车位");
					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
					mm.mergeBlock(park+1, 3, publicBuild, 3, KDTMergeManager.SPECIFY_MERGE);
				}
			}
			if(table.getCell(i, "description")!=null && table.getCell(i-1, "description")!=null
					&& table.getCell(i, "description").getValue()!=null
					&& table.getCell(i-1, "description").getValue()!=null
					&& !table.getCell(i, "description").getValue().equals(table.getCell(i-1, "description").getValue())){
				if(house==0){
					house=i;
					IRow row = table.addRow(i);
					rowCount++;
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(4).setValue("合计");
					row.getCell(3).setValue(PlanIndexTypeEnum.publicBuild.getAlias());
					mm.mergeBlock(publicBuild+1, 3, house, 3, KDTMergeManager.SPECIFY_MERGE);
					business = 0;
					park = 0;
					publicBuild = 0;
				}
			}else{
				if(i==rowCount-1){
					house=i;
					IRow row = table.addRow(++i);
					rowCount++;
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(4).setValue("合计");
					row.getCell(3).setValue(PlanIndexTypeEnum.publicBuild.getAlias());
					mm.mergeBlock(publicBuild+1, 3, rowCount-1, 3, KDTMergeManager.SPECIFY_MERGE);
				}
			}
		}
		EnterprisePlanEditUI.mergerTable(table,new String[]{"typename"},new String[]{"typename"});
		mm.mergeBlock(0, 2, rowCount-1, 2, KDTMergeManager.SPECIFY_MERGE);
//		mm.mergeBlock(0, 3, business, 3, KDTMergeManager.SPECIFY_MERGE);
//		mm.mergeBlock(business+1, 3, park, 3, KDTMergeManager.SPECIFY_MERGE);
//		mm.mergeBlock(park+1, 3, publicBuild, 3, KDTMergeManager.SPECIFY_MERGE);
//		mm.mergeBlock(publicBuild+1, 3, rowCount-1, 3, KDTMergeManager.SPECIFY_MERGE);
	}
	private void reSetExpressions(){
		int hjColumn = 6;
    	int dynRowBase = 3;
		int houseSubIndex=0;
		int businessSubIndex=0;
		int parkingSubIndex=0;
		int rowCount = tblMain.getRowCount();
		if(tblMain.getRowCount()>0){
			int publicSubIndex=rowCount-1;
			for(int i=0;i<rowCount-1;i++){
				IRow row=tblMain.getRow(i);
				if(row.getCell(dynRowBase).getValue().equals(PlanIndexTypeEnum.house.getAlias()) && isTotalRow(row)){
					houseSubIndex=i;
				}
				if(row.getCell(dynRowBase).getValue().equals(PlanIndexTypeEnum.business.getAlias()) && isTotalRow(row)){
					businessSubIndex=i;
				}
				if(row.getCell(dynRowBase).getValue().equals(PlanIndexTypeEnum.parking.getAlias()) && isTotalRow(row)){
					parkingSubIndex=i;
				}
				
			}
			//住宅
			if(houseSubIndex>0){
				IRow houseRow=tblMain.getRow(houseSubIndex);
				reSetExpressions(tblMain,houseRow,hjColumn,1,houseSubIndex);
			}
			//商业
			if(businessSubIndex>(houseSubIndex+1)){
				IRow businessRow=tblMain.getRow(businessSubIndex);
				reSetExpressions(tblMain,businessRow,hjColumn,houseSubIndex+2,businessSubIndex);
			}
			//停车
			if(parkingSubIndex>(businessSubIndex+1)){
				IRow parkRow=tblMain.getRow(parkingSubIndex);
//				reSetParkExpressions(tblMain,parkRow,hjColumn,businessSubIndex+2,parkingSubIndex);
			}
			//公建
			if(publicSubIndex>(parkingSubIndex+1)){
				IRow publicRow=tblMain.getRow(publicSubIndex);
				reSetExpressions(tblMain,publicRow,hjColumn,parkingSubIndex+2,publicSubIndex);
			}
		}
	}
	//求小计
	public static void reSetExpressions(KDTable tblMain,IRow row,int hjColumn,int n ,int m){
		for(int j=hjColumn;j<tblMain.getColumnCount();j++){
			String c ="";
			String f ="";
			String i ="";
			String exp = "";
			if(j<=25){
			   c=String.valueOf(((char)('A'+j)));
			}else{
			   c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
			}
			exp="=sum("+c+(n)+":"+c+(m)+")";
			row.getCell(j).setExpressions(exp);
			if(j%4==0){
				BigDecimal sumAmount = FDCHelper.ZERO;
		        BigDecimal acreage = FDCHelper.ZERO;
		        if(row.getCell(j).getValue()!=null)
		        	sumAmount = new BigDecimal(row.getCell(j).getValue().toString());
		        if(row.getCell(j-2).getValue()!=null)
		        	acreage = new BigDecimal(row.getCell(j-3).getValue().toString());
		        if(acreage.intValue()!=0)
		        	row.getCell(j-1).setValue(sumAmount.divide(acreage,4,2));
				row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			}
		}
	}
	private boolean isTotalRow(IRow row){
		boolean isSubTotalRow=false;
		if(row.getCell(4).getValue()!=null&&row.getCell(4).getValue().equals("合计")){
			isSubTotalRow=true;
		}
		return isSubTotalRow;
	}
	
	/**
	 * 行总计
	 * */
	private void getRowSum(){
		tblMain.setRowCount(tblMain.getRowCount()+4);
		TableUtils.getFootRow(tblMain, new String[]{"tararea","tarcount","tarunitprice","taramount",
				"yeararea","yearcount","yearunitprice","yearamount",
				"zjarea","zjcount","zjunitprice","zjamount",
				"wqzarea","wqzcount","wqzunitprice","wqzamount",
				"yqzwdjarea","yqzwdjcount","yqzwdjunitprice","yqzwdjamount",
				"wqzydjarea","wqzydjcount","wqzydjunitprice","wqzydjamount",
				"yqzydjarea","yqzydjcount","yqzydjunitprice","yqzydjamount",
				"ysarea","yscount","ysunitprice","ysamount"});
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        footRow = footRowManager.getFootRow(0);
        TableUtils.changeTableNumberFormat(tblMain, new String[]{"tararea","tarcount","tarunitprice","taramount",
        		"yeararea","yearcount","yearunitprice","yearamount",
				"zjarea","zjcount","zjunitprice","zjamount",
				"wqzarea","wqzcount","wqzunitprice","wqzamount",
				"yqzwdjarea","yqzwdjcount","yqzwdjunitprice","yqzwdjamount",
				"wqzydjarea","wqzydjcount","wqzydjunitprice","wqzydjamount",
				"yqzydjarea","yqzydjcount","yqzydjunitprice","yqzydjamount",
				"ysarea","yscount","ysunitprice","ysamount"});
	}
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

}