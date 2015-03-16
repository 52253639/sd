/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.DayInitDataBldFactory;
import com.kingdee.eas.fdc.sellhouse.DayInitDataBldInfo;
import com.kingdee.eas.fdc.sellhouse.DayInitDataPtyFactory;
import com.kingdee.eas.fdc.sellhouse.DayInitDataPtyInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class DayInitDataPtyListUI extends AbstractDayInitDataPtyListUI
{
    private static final Logger logger = CoreUIObject.getLogger(DayInitDataPtyListUI.class);
    
    /**
     * output class constructor
     */
    public DayInitDataPtyListUI() throws Exception
    {
        super();
    }

	protected ICoreBase getBizInterface() throws Exception {
		return DayInitDataPtyFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);		
		
		this.actionQuery.setVisible(false);  //数据是sql查询填充进去的，不支持查询
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		formattedTextField.setPrecision(0);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor formatTextEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblMain.getColumn("yearRoomCount").setEditor(formatTextEditor);
		this.tblMain.getColumn("initRoomCount").setEditor(formatTextEditor);
		
		for(int i=9;i<this.tblMain.getColumnCount();i++)
		this.tblMain.getColumn(i).setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
			
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {  //集团下才能修改
			this.actionSave.setVisible(true);
			this.tblMain.getStyleAttributes().setLocked(false);
			this.tblMain.getColumn("orgUnit.name").getStyleAttributes().setLocked(true);
			this.tblMain.getColumn("sellProject.name").getStyleAttributes().setLocked(true);
			this.tblMain.getColumn("productType.name").getStyleAttributes().setLocked(true);			
		}
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);		

		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		fillData();
		
		this.tblMain.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
		this.tblMain.getGroupManager().setGroup(true);
		this.tblMain.getColumn("orgUnit.name").setGroup(true);
		this.tblMain.getColumn("sellProject.name").setGroup(true);
		this.tblMain.getGroupManager().group();
		this.tblMain.getColumn("orgUnit.name").setMergeable(true);
		this.tblMain.getColumn("sellProject.name").setMergeable(true);

		
		
	}
	
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {

		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	protected void execQuery() {
		//super.execQuery();

	}
	
	
	
	private void fillData() throws BOSException, SQLException {
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		String compayIds = "select fid from  t_org_sale where (FNumber = '"+saleOrg.getNumber()+"' or FLongNumber like '"+saleOrg.getLongNumber()+"!%' or FLongNumber like '%!"+saleOrg.getLongNumber()+"!%') ";

		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select OrgUnit.fname_l2 orgName,SellProject.fname_l2 sepName,ProductType.fname_l2 prtName,");
		builder.appendSql("OrgUnit.fid orgFid,SellProject.fid sepFid,ProductType.fid prtFid,");
		builder.appendSql("InitPty.* from (select Building.fSellProjectId,Room.fProductTypeId from t_she_room Room ");
		builder.appendSql("inner join t_she_building Building on Room.FbuildingId = Building.fid ");
		builder.appendSql("group by Building.fSellProjectId,Room.fProductTypeId) MergeTable ");
		builder.appendSql("inner join t_she_sellProject SellProject on MergeTable.fSellProjectId = SellProject.fid ");
		builder.appendSql("inner join T_ORG_BaseUnit OrgUnit on  SellProject.FOrgUnitID = OrgUnit.fid ");
		builder.appendSql("inner join T_FDC_ProductType ProductType on MergeTable.fProductTypeId = ProductType.fid ");
		builder.appendSql("left outer join t_day_initDataPty InitPty on MergeTable.fSellProjectId = InitPty.fSellProjectId ");
		builder.appendSql("and MergeTable.fProductTypeId = InitPty.fProductTypeId ");
		builder.appendSql("where OrgUnit.fid in ("+compayIds+") ");
		builder.appendSql("order by OrgUnit.fid,SellProject.fid,ProductType.fid ");
		
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String fid = tableSet.getString("fid");
			String orgName = tableSet.getString("orgName");
			String sepName = tableSet.getString("sepName");
			String prtName = tableSet.getString("prtName");
			String orgFid = tableSet.getString("orgFid");
			String sepFid = tableSet.getString("sepFid");
			String prtFid = tableSet.getString("prtFid");
			int yearRoomCount = tableSet.getInt("FyearRoomCount");
			int initRoomCount = tableSet.getInt("FinitRoomCount");
			BigDecimal yearAreaAmount = tableSet.getBigDecimal("FyearAreaAmount");
			BigDecimal initAreaAmount = tableSet.getBigDecimal("FinitAreaAmount");
			BigDecimal yearContractAmount = tableSet.getBigDecimal("FyearContractAmount");
			BigDecimal initContractAmount = tableSet.getBigDecimal("FinitContractAmount");
			BigDecimal yearRsvAmount = tableSet.getBigDecimal("FyearRsvAmount");
			BigDecimal initRsvAmount = tableSet.getBigDecimal("FinitRsvAmount");
			BigDecimal yearLoanAmount = tableSet.getBigDecimal("FyearLoanAmount");
			BigDecimal initLoanAmount = tableSet.getBigDecimal("FinitLoanAmount");
			BigDecimal yearCosateAmount = tableSet.getBigDecimal("FyearCosateAmount");
			BigDecimal FinitCosateAmount = tableSet.getBigDecimal("FinitCosateAmount");
			BigDecimal yearCstRsvAmount = tableSet.getBigDecimal("FyearCstRsvAmount");
			BigDecimal initCstRsvAmount = tableSet.getBigDecimal("FinitCstRsvAmount");			
					 	
			IRow addRow = this.tblMain.addRow();
			addRow.getCell("id").setValue(fid);
			addRow.getCell("orgUnit.name").setValue(orgName);
			addRow.getCell("sellProject.name").setValue(sepName);
			addRow.getCell("productType.name").setValue(prtName);
			addRow.getCell("orgUnit.id").setValue(orgFid);
			addRow.getCell("sellProject.id").setValue(sepFid);
			addRow.getCell("productType.id").setValue(prtFid);
			addRow.getCell("yearRoomCount").setValue(new Integer(yearRoomCount));
			addRow.getCell("initRoomCount").setValue(new Integer(initRoomCount));
			addRow.getCell("yearAreaAmount").setValue(yearAreaAmount);
			addRow.getCell("initAreaAmount").setValue(initAreaAmount);
			addRow.getCell("yearContractAmount").setValue(yearContractAmount);
			addRow.getCell("initContractAmount").setValue(initContractAmount);
			addRow.getCell("yearRsvAmount").setValue(yearRsvAmount);
			addRow.getCell("initRsvAmount").setValue(initRsvAmount);
			addRow.getCell("yearLoanAmount").setValue(yearLoanAmount);
			addRow.getCell("initLoanAmount").setValue(initLoanAmount);
			addRow.getCell("yearCosateAmount").setValue(yearCosateAmount);
			addRow.getCell("initCosateAmount").setValue(FinitCosateAmount);
			addRow.getCell("yearCstRsvAmount").setValue(yearCstRsvAmount);
			addRow.getCell("initCstRsvAmount").setValue(initCstRsvAmount);			
		}

		

	}
	
	
	

	private DayInitDataPtyInfo getRowInfo(IRow row) {
		String sellProId = (String)row.getCell("sellProject.id").getValue();
		String proTypeId = (String)row.getCell("productType.id").getValue();
		if(sellProId==null || proTypeId==null)	return null;
		
		DayInitDataPtyInfo initDataInfo = new DayInitDataPtyInfo();
		String fid = (String)row.getCell("id").getValue();
		if(fid!=null)	initDataInfo.setId(BOSUuid.read(fid));
		SellProjectInfo sellProInfo = new SellProjectInfo();
		sellProInfo.setId(BOSUuid.read(sellProId));
		initDataInfo.setSellProject(sellProInfo);
		ProductTypeInfo proTypeInfo = new ProductTypeInfo();
		proTypeInfo.setId(BOSUuid.read(proTypeId));
		initDataInfo.setProductType(proTypeInfo);

		Integer yearRoomCount = (Integer)row.getCell("yearRoomCount").getValue(); 
		if(yearRoomCount!=null)   initDataInfo.setYearRoomCount(yearRoomCount.intValue());	else	initDataInfo.setYearRoomCount(0);
		Integer initRoomCount = (Integer)row.getCell("initRoomCount").getValue();
		if(initRoomCount!=null)	 initDataInfo.setInitRoomCount(initRoomCount.intValue());	else	initDataInfo.setInitRoomCount(0);
		BigDecimal yearAreaAmount = (BigDecimal)row.getCell("yearAreaAmount").getValue();
		if(yearAreaAmount!=null) initDataInfo.setYearAreaAmount(yearAreaAmount);	else	initDataInfo.setYearAreaAmount(new BigDecimal(0.00));
		BigDecimal initAreaAmount = (BigDecimal)row.getCell("initAreaAmount").getValue();
		if(initAreaAmount!=null) initDataInfo.setInitAreaAmount(initAreaAmount);	else	initDataInfo.setInitAreaAmount(new BigDecimal(0.00));
		BigDecimal yearContractAmount = (BigDecimal)row.getCell("yearContractAmount").getValue();
		if(yearContractAmount!=null) initDataInfo.setYearContractAmount(yearContractAmount);	else	initDataInfo.setYearContractAmount(new BigDecimal(0.00));
		BigDecimal initContractAmount = (BigDecimal)row.getCell("initContractAmount").getValue();
		if(initContractAmount!=null) initDataInfo.setInitContractAmount(initContractAmount);	else	initDataInfo.setInitContractAmount(new BigDecimal(0.00));
		BigDecimal yearRsvAmount = (BigDecimal)row.getCell("yearRsvAmount").getValue();
		if(yearRsvAmount!=null) initDataInfo.setYearRsvAmount(yearRsvAmount);	else	initDataInfo.setYearRsvAmount(new BigDecimal(0.00));
		BigDecimal initRsvAmount = (BigDecimal)row.getCell("initRsvAmount").getValue();
		if(initRsvAmount!=null) initDataInfo.setInitRsvAmount(initRsvAmount);	else	initDataInfo.setInitRsvAmount(new BigDecimal(0.00));
		BigDecimal yearLoanAmount = (BigDecimal)row.getCell("yearLoanAmount").getValue();
		if(yearLoanAmount!=null) initDataInfo.setYearLoanAmount(yearLoanAmount);	else	initDataInfo.setYearLoanAmount(new BigDecimal(0.00));
		BigDecimal initLoanAmount = (BigDecimal)row.getCell("initLoanAmount").getValue();
		if(initLoanAmount!=null) initDataInfo.setInitLoanAmount(initLoanAmount);	else	initDataInfo.setInitLoanAmount(new BigDecimal(0.00));
		BigDecimal yearCosateAmount = (BigDecimal)row.getCell("yearCosateAmount").getValue();
		if(yearCosateAmount!=null) initDataInfo.setYearCosateAmount(yearCosateAmount);	else	initDataInfo.setYearCosateAmount(new BigDecimal(0.00));
		BigDecimal initCosateAmount = (BigDecimal)row.getCell("initCosateAmount").getValue();
		if(initCosateAmount!=null) initDataInfo.setInitCosateAmount(initCosateAmount);	else	initDataInfo.setInitCosateAmount(new BigDecimal(0.00));
		BigDecimal yearCstRsvAmount = (BigDecimal)row.getCell("yearCstRsvAmount").getValue();
		if(yearCstRsvAmount!=null) initDataInfo.setYearCstRsvAmount(yearCstRsvAmount);	else	initDataInfo.setYearCstRsvAmount(new BigDecimal(0.00));
		BigDecimal initCstRsvAmount = (BigDecimal)row.getCell("initCstRsvAmount").getValue();
		if(initCstRsvAmount!=null) initDataInfo.setInitCstRsvAmount(initCstRsvAmount);	else	initDataInfo.setInitCstRsvAmount(new BigDecimal(0.00));
		
		return initDataInfo;
	}
	
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		int rowCount = this.tblMain.getRowCount();
		CoreBaseCollection coreAddColl = new CoreBaseCollection();
		CoreBaseCollection coreUpdateColl = new CoreBaseCollection();
		for(int i=0;i<rowCount;i++) {	
			DayInitDataPtyInfo thisInfo = getRowInfo(this.tblMain.getRow(i));
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
			DayInitDataPtyFactory.getRemoteInstance().addnew(coreAddColl);
		if(coreUpdateColl.size()>0)
			DayInitDataPtyFactory.getRemoteInstance().update(coreUpdateColl);
		
		MsgBox.showInfo("保存成功！");
		
	}
	
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		return;
	}
	
	
	
	

}