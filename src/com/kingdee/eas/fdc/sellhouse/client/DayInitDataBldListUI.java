/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.excel.model.struct.embed.shape.KDSEllipse;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.DayInitDataBldFactory;
import com.kingdee.eas.fdc.sellhouse.DayInitDataBldInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class DayInitDataBldListUI extends AbstractDayInitDataBldListUI
{
    public DayInitDataBldListUI() throws Exception {
		super();
	}

	private static final Logger logger = CoreUIObject.getLogger(DayInitDataBldListUI.class);

	protected ICoreBase getBizInterface() throws Exception {
		return DayInitDataBldFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		return;
	}
    

	public void onLoad() throws Exception {
		super.onLoad();
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		formattedTextField.setPrecision(0);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor formatTextEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblMain.getColumn("yearRoomCount").setEditor(formatTextEditor);
		this.tblMain.getColumn("initRoomCount").setEditor(formatTextEditor);
		
		for(int i=9;i<this.tblMain.getColumnCount();i++)
		this.tblMain.getColumn(i).setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		
		
		this.tblMain.getGroupManager().setGroup(true);
		for(int i=4;i<6;i++)
			this.tblMain.getColumn(i).setGroup(true);		
		this.tblMain.getGroupManager().group();
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {  //集团下才能修改
			this.actionSave.setVisible(true);
			this.tblMain.getStyleAttributes().setLocked(false);
			this.tblMain.getColumn("orgUnit.name").getStyleAttributes().setLocked(true);
			this.tblMain.getColumn("sellProject.name").getStyleAttributes().setLocked(true);
			this.tblMain.getColumn("building.name").getStyleAttributes().setLocked(true);
		}
		
		
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	
	private DayInitDataBldInfo getRowInfo(IRow row) {
		String sellProId = (String)row.getCell("sellProject.id").getValue();
		String buildId = (String)row.getCell("building.id").getValue();
		if(sellProId==null || buildId==null)	return null;
		
		DayInitDataBldInfo initDataInfo = new DayInitDataBldInfo();
		String fid = (String)row.getCell("id").getValue();
		if(fid!=null)	initDataInfo.setId(BOSUuid.read(fid));
		SellProjectInfo sellProInfo = new SellProjectInfo();
		sellProInfo.setId(BOSUuid.read(sellProId));
		initDataInfo.setSellProject(sellProInfo);
		BuildingInfo buildInfo = new BuildingInfo();
		buildInfo.setId(BOSUuid.read(buildId));
		initDataInfo.setBuilding(buildInfo);

		Integer yearRoomCount = (Integer)row.getCell("yearRoomCount").getValue(); 
		if(yearRoomCount!=null)   initDataInfo.setYearRoomCount(yearRoomCount.intValue());	else initDataInfo.setYearRoomCount(0);
		Integer initRoomCount = (Integer)row.getCell("initRoomCount").getValue();
		if(initRoomCount!=null)	 initDataInfo.setInitRoomCount(initRoomCount.intValue());	else initDataInfo.setInitRoomCount(0);
		BigDecimal yearAreaAmount = (BigDecimal)row.getCell("yearAreaAmount").getValue();
		if(yearAreaAmount!=null) initDataInfo.setYearAreaAmount(yearAreaAmount);	else initDataInfo.setYearAreaAmount(new BigDecimal(0.00));
		BigDecimal initAreaAmount = (BigDecimal)row.getCell("initAreaAmount").getValue();
		if(initAreaAmount!=null) initDataInfo.setInitAreaAmount(initAreaAmount);	else initDataInfo.setInitAreaAmount(new BigDecimal(0.00));
		BigDecimal yearContractAmount = (BigDecimal)row.getCell("yearContractAmount").getValue();
		if(yearContractAmount!=null) initDataInfo.setYearContractAmount(yearContractAmount);	else initDataInfo.setYearContractAmount(new BigDecimal(0.00));
		BigDecimal initContractAmount = (BigDecimal)row.getCell("initContractAmount").getValue();
		if(initContractAmount!=null) initDataInfo.setInitContractAmount(initContractAmount);	else initDataInfo.setInitContractAmount(new BigDecimal(0.00));
		BigDecimal yearRsvAmount = (BigDecimal)row.getCell("yearRsvAmount").getValue();
		if(yearRsvAmount!=null) initDataInfo.setYearRsvAmount(yearRsvAmount);	else initDataInfo.setYearRsvAmount(new BigDecimal(0.00));
		BigDecimal initRsvAmount = (BigDecimal)row.getCell("initRsvAmount").getValue();
		if(initRsvAmount!=null) initDataInfo.setInitRsvAmount(initRsvAmount);	else initDataInfo.setInitRsvAmount(new BigDecimal(0.00));
		BigDecimal yearLoanAmount = (BigDecimal)row.getCell("yearLoanAmount").getValue();
		if(yearLoanAmount!=null) initDataInfo.setYearLoanAmount(yearLoanAmount);	else initDataInfo.setYearLoanAmount(new BigDecimal(0.00));
		BigDecimal initLoanAmount = (BigDecimal)row.getCell("initLoanAmount").getValue();
		if(initLoanAmount!=null) initDataInfo.setInitLoanAmount(initLoanAmount);	else initDataInfo.setInitLoanAmount(new BigDecimal(0.00));
		BigDecimal yearCosateAmount = (BigDecimal)row.getCell("yearCosateAmount").getValue();
		if(yearCosateAmount!=null) initDataInfo.setYearCosateAmount(yearCosateAmount);	else	initDataInfo.setYearCosateAmount(new BigDecimal(0.00));
		BigDecimal initCosateAmount = (BigDecimal)row.getCell("initCosateAmount").getValue();
		if(initCosateAmount!=null) initDataInfo.setInitCosateAmount(initCosateAmount);	else initDataInfo.setInitCosateAmount(new BigDecimal(0.00));
		BigDecimal yearCstRsvAmount = (BigDecimal)row.getCell("yearCstRsvAmount").getValue();
		if(yearCstRsvAmount!=null) initDataInfo.setYearCstRsvAmount(yearCstRsvAmount);	else initDataInfo.setYearCstRsvAmount(new BigDecimal(0.00));
		BigDecimal initCstRsvAmount = (BigDecimal)row.getCell("initCstRsvAmount").getValue();
		if(initCstRsvAmount!=null) initDataInfo.setInitCstRsvAmount(initCstRsvAmount);	else initDataInfo.setInitCstRsvAmount(new BigDecimal(0.00));
		
		return initDataInfo;
	}
	
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		int rowCount = this.tblMain.getRowCount();
		CoreBaseCollection coreAddColl = new CoreBaseCollection();
		CoreBaseCollection coreUpdateColl = new CoreBaseCollection();
		for(int i=0;i<rowCount;i++) {	
			DayInitDataBldInfo thisInfo = getRowInfo(this.tblMain.getRow(i));
			if(thisInfo!=null) {
				if(thisInfo.getId()==null) {
					BOSUuid newId = BOSUuid.create(thisInfo.getBOSType()); 
					thisInfo.setId(newId);
					this.tblMain.getRow(i).getCell("id").setValue(newId.toString());
					this.tblMain.getRow(i).setUserObject(thisInfo);
					coreAddColl.add(thisInfo);
				}else{
					coreUpdateColl.add(thisInfo);
				}				
			}
		}
		if(coreAddColl.size()>0)
			DayInitDataBldFactory.getRemoteInstance().addnew(coreAddColl);
		if(coreUpdateColl.size()>0)
			DayInitDataBldFactory.getRemoteInstance().update(coreUpdateColl);
		
		MsgBox.showInfo("保存成功！");
		
	}
	
	
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		viewInfo = (EntityViewInfo)this.mainQuery.clone();
		FilterInfo filter = new FilterInfo();
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		String compayIds = "select fid from  t_org_sale where (FNumber = '"+saleOrg.getNumber()+"' or FLongNumber like '"+saleOrg.getLongNumber()+"!%' or FLongNumber like '%!"+saleOrg.getLongNumber()+"!%') ";
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",compayIds,CompareType.INNER));
		if (viewInfo.getFilter() == null)
			viewInfo.setFilter(filter);
		else {
			try {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	
	
	
}