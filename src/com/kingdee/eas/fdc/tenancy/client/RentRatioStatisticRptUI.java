/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.tenancy.RentRatioFacadeFactory;
import com.kingdee.eas.fi.ar.client.VerifyDataFileUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.util.SysUtil;

/**
 * 描述：出租率统计表UI
 * 
 * @author pu_zhang @date 2010-08-10
 *
 */
public class RentRatioStatisticRptUI extends AbstractRentRatioStatisticRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(RentRatioStatisticRptUI.class);
    private RentRatioStatisticRptFilterUI filterUI = null;
	private CommonQueryDialog commonQueryDialog = null;
    /**
     * output class constructor
     */
    public RentRatioStatisticRptUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.btnView.setVisible(false);
		this.actionView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.tblMain.setEditable(false);
    }
	protected String[] getLocateNames() {
		 String[] locateNames = new String[3];
	     locateNames[0] = "projectName";
	     locateNames[1] = "area";
	     locateNames[2] = "building";
	     return locateNames;
	}
    /**
     * 描述：初始化查询页面
     * @author pu_zhang
     * @date   2010-7-18
     * @return CommonQueryDialog
     */
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}
	private RentRatioStatisticRptFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new RentRatioStatisticRptFilterUI(this, this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	protected boolean initDefaultFilter() {
		return true;
	}
	
	
	
	/**
	 * 描述：查询报表数据
	 * @author pu_zhang
	 * @date   2010-7-16
	 */
	protected void execQuery() {
		try {
			//初始化表格信息
			initTable();
//			//获取参数
//			Map param = getFilterParam();
			//装载数据
			fillTable();
		} catch (Exception e) {
			super.handUIException(e);
		}
	}
	
	/**
	 * 描述：获取参数
	 * @author pu_zhang
	 * @date 2010-7-16
	 * @return Map
	 */
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
	/**
	 * 描述：初始化表格
	 * @author pu_zhang
	 * @date   2010-7-16
	 */
	private void initTable() {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		
		tblMain.getColumn("projectName").getStyleAttributes().setWrapText(true);
		tblMain.getColumn("area").getStyleAttributes().setWrapText(true);
		tblMain.getColumn("building").getStyleAttributes().setWrapText(true);
		
		tblMain.getColumn("rentedArea").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn("rentedArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		
		tblMain.getColumn("avalableArea").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn("avalableArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblMain.getColumn("averageRentRatio").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn("averageRentRatio").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblMain.getColumn("endRentRatio").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn("endRentRatio").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblMain.getColumn("averagePrice").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn("averagePrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);		
	}
	
	/**
	 * 通过过滤条件查询结果集，并显示到表格
	 * 
	 * @param param
	 */
	private void fillTable() {
		tblMain.removeRows();
		Map param=new HashMap();
		try {
			super.execQuery();
			//当以默认方案直接进入 时，获取默认方案
			if(mainQuery!=null){
				//取保存的分配方案
				if(mainQuery.getFilter()!=null){
					FilterInfo filter=mainQuery.getFilter();
					for(Iterator iter=filter.getFilterItems().iterator();iter.hasNext();){
						FilterItemInfo item=(FilterItemInfo)iter.next();
						//项目
						if("F7Project".equalsIgnoreCase(item.getPropertyName())){
							param.put("projectid",item.getCompareValue().toString());
						}
						//客户
						if ("areaIds".equalsIgnoreCase(item.getPropertyName())) {
							if (item.getCompareValue() != null) {
								String str1=item.getCompareValue().toString().replaceAll("[\\[\\]]", "");
								String[] arearid=str1.split(",");
								for(int i=0;i<arearid.length;i++){
									arearid[i]=arearid[i].trim();
								}
								List arearidList=Arrays.asList(arearid);
								param.put("arearidList",arearidList);
							}
						}
						
						 //款项类型
						if("buildingIds".equalsIgnoreCase(item.getPropertyName())){
							String str2=item.getCompareValue().toString().replaceAll("[\\[\\]]", "");
							String[] buildingid=str2.split(",");
							for(int i=0;i<buildingid.length;i++){
								buildingid[i]=buildingid[i].trim();
							}
							List buildingidList=Arrays.asList(buildingid);
							param.put("buildingidList",buildingidList);
						}
					}
				}
			}
			
			//获取到查询的结果
			Map result = RentRatioFacadeFactory.getRemoteInstance()
					.getRentRatioInfo(param);
			List rentRatioInfo = (List) result.get("rentRatioInfo");

			if (rentRatioInfo != null && rentRatioInfo.size() > 0) {
				for (int i = 0; i < rentRatioInfo.size(); i++) {
					Map rentRatio = (Map) rentRatioInfo.get(i);
					IRow row = tblMain.addRow();
					row.getCell("ID").setValue(rentRatio.get("ID"));
					row.getCell("projectName").setValue(rentRatio.get("projectName"));
					row.getCell("area").setValue(rentRatio.get("area"));
					row.getCell("building").setValue(rentRatio.get("building"));
					row.getCell("rentedArea").setValue(rentRatio.get("rentedArea"));
					row.getCell("avalableArea").setValue(rentRatio.get("avalableArea"));
					row.getCell("averageRentRatio").setValue(rentRatio.get("averageRentRatio"));
					row.getCell("endRentRatio").setValue(rentRatio.get("endRentRatio"));
					row.getCell("averagePrice").setValue(rentRatio.get("averagePrice"));
				}
			}
		} catch (BOSException e) {
			handUIException(e);
		}
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
    	SysUtil.abort();
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
	protected String getKeyFieldName() {
		// TODO Auto-generated method stub
		return "ID";
	}
	
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
}