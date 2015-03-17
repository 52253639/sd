/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IBlock;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.market.client.TableUtils;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.Enum;

/**
 * output class name
 */
public class RoomAreaInputNewUI extends AbstractRoomAreaInputNewUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2223435778234731142L;
	private static final Logger logger = CoreUIObject
	.getLogger(RoomAreaInputNewUI.class);

	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	private Map roomMap = new HashMap();
	private static final String AUDIT_CHECK_TYPE_PLAN = "plan";
	private static final String AUDIT_CHECK_TYPE_PRE = "pre";
	private static final String AUDIT_CHECK_TYPE_ACT = "act";

	/**
	 * output class constructor
	 */
	public RoomAreaInputNewUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected void initTree() throws Exception {

		this.treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(actionOnLoad,
				MoneySysTypeEnum.SalehouseSys));
		this.treeMain.setShowsRootHandles(true);
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.execQuery();
		TableUtils.getFootRow(tblMain,new String[]{"tenancyArea","planBuildingArea","planRoomArea","preBuildingArea","preRoomArea","actualBuildingArea","actualRoomArea"});
	}
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		TableUtils.getFootRow(tblMain,new String[]{"tenancyArea","planBuildingArea","planRoomArea","preBuildingArea","preRoomArea","actualBuildingArea","actualRoomArea"});
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
//		if(isOrderForClickTableHead() && e.getType() == 0 && e.getButton() == 1 && e.getClickCount() == 1)
//        {
//            super.tblMain_tableClicked(e);
//        }else{
//        	return;
//        }
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProject = (SellProjectInfo) node
						.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("building.sellProject.id",
								sellProject.getId().toString(),
								CompareType.EQUALS));
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				filter.getFilterItems().add(
						new FilterItemInfo("CU.id", null, CompareType.EQUALS));
			} else if (node.getUserObject() instanceof BuildingInfo) {// 楼栋
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				String buildingId = building.getId().toString();
				filter.getFilterItems().add(
						new FilterItemInfo("building.id", buildingId,
								CompareType.EQUALS));
			} else if (node.getUserObject() instanceof BuildingUnitInfo) {// 单元
				BuildingUnitInfo unit = (BuildingUnitInfo) node.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("buildUnit.id", unit.getId()
								.toString(), CompareType.EQUALS));
			} else if (node.getUserObject() instanceof SubareaInfo) { // 分区
				SubareaInfo subarea = (SubareaInfo) node.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("building.subarea.id", subarea
								.getId().toString(), CompareType.EQUALS));
			}
		}
		// 合并查询条件
		viewInfo = (EntityViewInfo) mainQuery.clone();
		try {
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
	}

	protected void refresh(ActionEvent e) throws Exception {
		if (this.isEditedTable()) {
			if (MsgBox.showConfirm2New(this, "是否保存?") == MsgBox.YES) {
				this.actionSave_actionPerformed(e);
			}
		}
		this.execQuery();
		TableUtils.getFootRow(tblMain,new String[]{"tenancyArea","planBuildingArea","planRoomArea","preBuildingArea","preRoomArea","actualBuildingArea","actualRoomArea"});
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected String[] getLocateNames() {

		String[] locateNames = new String[1];
		locateNames[0] = "name";
		return locateNames;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTable();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);

		initButton(false);

		if (saleOrg.isIsBizUnit()) {
			initButton(true);
		} else if (isSellOrg()) {
			initButton(true);
		}

		this.tblMain.addKDTEditListener(new KDTEditListener() {

			public void editCanceled(KDTEditEvent e) {
			}

			public void editStarted(KDTEditEvent e) {
			}

			public void editStarting(KDTEditEvent e) {
			}

			public void editStopped(KDTEditEvent e) {
				if (e.getColIndex() == tblMain.getColumn("planBuildingArea")
						.getColumnIndex()
						|| e.getColIndex() == tblMain.getColumn("planRoomArea")
								.getColumnIndex()) {

					planDataChange(e.getRowIndex());
				} else if (e.getColIndex() == tblMain.getColumn(
						"preBuildingArea").getColumnIndex()
						|| e.getColIndex() == tblMain.getColumn("preRoomArea")
								.getColumnIndex()) {
					preDataChange(e.getRowIndex());
				} else if (e.getColIndex() == tblMain.getColumn(
						"actualBuildingArea").getColumnIndex()
						|| e.getColIndex() == tblMain.getColumn(
								"actualRoomArea").getColumnIndex()) {
					actualDataChange(e.getRowIndex());
				} else if (e.getColIndex() == tblMain.getColumn(
						"sellAreaType").getColumnIndex()) {
				   sellAreaTypeDataChange(e.getOldValue(),e.getRowIndex());
				}

			}

			public void editStopping(KDTEditEvent e) {
			}

			public void editValueChanged(KDTEditEvent e) {
			}

		});
		
		this.tblMain.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				
			}

			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==86){
					changeSellTypeState();
				}
				
			}
			public void keyTyped(KeyEvent e) {
			}
		});
		
		TableUtils.getFootRow(tblMain,new String[]{"tenancyArea","planBuildingArea","planRoomArea","preBuildingArea","preRoomArea","actualBuildingArea","actualRoomArea"});
		String[] fields=new String[tblMain.getColumnCount()];
		for(int i=0;i<tblMain.getColumnCount();i++){
			fields[i]=tblMain.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(tblMain,fields);
	}
	
	private void changeSellTypeState(){
		int activeCol = this.tblMain.getSelectManager().getActiveColumnIndex();
		if(activeCol == tblMain.getColumn("planBuildingArea").getColumnIndex()
		 || activeCol == tblMain.getColumn("planRoomArea").getColumnIndex()){
			planDataChange(this.tblMain.getSelectManager().getActiveRowIndex());
		}else if(activeCol == tblMain.getColumn("preBuildingArea").getColumnIndex()
				 || activeCol == tblMain.getColumn("preRoomArea").getColumnIndex()){
			preDataChange(this.tblMain.getSelectManager().getActiveRowIndex());
		}else if(activeCol == tblMain.getColumn("actualBuildingArea").getColumnIndex()
				 || activeCol == tblMain.getColumn("actualRoomArea").getColumnIndex()){
			actualDataChange(this.tblMain.getSelectManager().getActiveRowIndex());
		}else{
			planDataChange(this.tblMain.getSelectManager().getActiveRowIndex());
		}
	}
	
	private void sellAreaTypeDataChange(Object oldValue, int rowIndex) {
		IRow  row = this.tblMain.getRow(rowIndex);
		if (row == null) {
			return;
		}
	
		if(row.getCell("planBuildingArea").getValue()==null
		  &&row.getCell("planRoomArea").getValue()==null
		  &&row.getCell("planBuildingArea").getValue()==null
		  &&row.getCell("preBuildingArea").getValue()==null
		  &&row.getCell("preRoomArea").getValue()==null
		  &&row.getCell("actualBuildingArea").getValue()==null
		  &&row.getCell("actualRoomArea").getValue()==null){
		  row.getCell("isChangeSellType").setValue(Boolean.FALSE);
		}else{
			if (row.getCell("sellAreaType").getValue() != null) {
				String type = row.getCell("sellAreaType").getValue().toString();
				if (type != null
						&& !type.equals(oldValue.toString())) {
					row.getCell("isChangeSellType").setValue(Boolean.TRUE);
				}
			}
		}
	}

	private void planDataChange(int rowIndex) {
		IRow row = this.tblMain.getRow(rowIndex);
		if (row == null) {
			return;
		}
		if(row.getCell("planBuildingArea").getValue()==null && row.getCell("planRoomArea").getValue()==null){
			return;
		}
	}

	private void preDataChange(int rowIndex) {
		IRow row = this.tblMain.getRow(rowIndex);
		if (row == null) {
			return;
		}
		if(row.getCell("preBuildingArea").getValue()==null && row.getCell("preRoomArea").getValue()==null){
			return;
		}
		if (row.getCell("sellAreaType").getValue() != null) {
			String type = row.getCell("sellAreaType").getValue().toString();
			if (type != null
					&& type.equals(SellAreaTypeEnum.PLANNING.getAlias())) {
				row.getCell("sellAreaType").setValue(SellAreaTypeEnum.PRESELL);
			}
		}
	}

	private void actualDataChange(int rowIndex) {
		IRow row = this.tblMain.getRow(rowIndex);
		if (row == null) {
			return;
		}
		if(row.getCell("actualBuildingArea").getValue()==null && row.getCell("actualRoomArea").getValue()==null){
			return;
		}
		if (row.getCell("sellAreaType").getValue() != null) {
			String type = row.getCell("sellAreaType").getValue().toString();
			if (type != null
					&& type.equals(SellAreaTypeEnum.PLANNING.getAlias())) {
				row.getCell("sellAreaType").setValue(SellAreaTypeEnum.ACTUAL);
			} else if (type.equals(SellAreaTypeEnum.PRESELL.getAlias())) {
				row.getCell("sellAreaType").setValue(SellAreaTypeEnum.ACTUAL);
			}
		}
	}

	private boolean isSellOrg() {
		boolean res = false;
		try {
			FullOrgUnitInfo fullOrginfo = SysContext.getSysContext()
					.getCurrentOrgUnit().castToFullOrgUnitInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sellHouseStrut", Boolean.TRUE,
							CompareType.EQUALS));
			if (fullOrginfo != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("unit.id", fullOrginfo.getId()
								.toString(), CompareType.EQUALS));
			}
			filter.getFilterItems().add(
					new FilterItemInfo("tree.id", OrgConstants.SALE_VIEW_ID,
							CompareType.EQUALS));
			res = FDCOrgStructureFactory.getRemoteInstance().exists(filter);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "获得售楼组织失败!");
		}

		return res;
	}

	private void initButton(boolean b) {
		this.actionSave.setEnabled(b);
		this.actionAreaImport.setEnabled(b);
		this.actionPlanAudit.setEnabled(b);
		this.actionRePlanAudit.setEnabled(b);
		this.actionPreAudit.setEnabled(b);
		this.actionRePreAudit.setEnabled(b);
		this.actionActureAudit.setEnabled(b);
		this.actionReActureAudit.setEnabled(b);
		this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnAreaImport.setIcon(EASResource.getIcon("imgTbtn_importexcel"));
		this.btnPlanAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.btnRePlanAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.btnPreAudit.setIcon(EASResource.getIcon("imgTbtn_review"));
		this.btnRePreAudit.setIcon(EASResource.getIcon("imgTbtn_unreview"));
		this.btnActAudit.setIcon(EASResource.getIcon("imgTbtn_makeknown"));
		this.btnReActAudit.setIcon(EASResource.getIcon("imgTbtn_fmakeknown"));
	}

	private void initTable() {
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(
				formattedTextField);
		if (saleOrg.isIsBizUnit() || isSellOrg()) {
			if (this.tblMain.getColumn("name") != null) {
				this.tblMain.getColumn("name").getStyleAttributes().setLocked(
						true);
			}
			KDComboBox combo = new KDComboBox();
			Iterator iterator = SellAreaTypeEnum.iterator();
			while (iterator.hasNext()) {
				combo.addItem(iterator.next());
			}
			KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
			this.tblMain.getColumn("sellAreaType").setEditor(comboEditor);
			this.tblMain.getColumn("sellAreaType").getStyleAttributes()
					.setLocked(false);

			if (this.tblMain.getColumn("roomModel.name") != null) {
				this.tblMain.getColumn("roomModel.name").getStyleAttributes()
						.setLocked(true);
			}
			if (this.tblMain.getColumn("planBuildingArea") != null) {
				this.tblMain.getColumn("planBuildingArea").getStyleAttributes()
						.setLocked(false);
				this.tblMain.getColumn("planBuildingArea").getStyleAttributes()
						.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);

				this.tblMain.getColumn("planBuildingArea").setEditor(
						numberEditor);
			}
			if (this.tblMain.getColumn("planRoomArea") != null) {
				this.tblMain.getColumn("planRoomArea").getStyleAttributes()
						.setLocked(false);
				this.tblMain.getColumn("planRoomArea").getStyleAttributes()
						.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);

				this.tblMain.getColumn("planRoomArea").setEditor(numberEditor);
			}
			if (this.tblMain.getColumn("isPlan") != null) {
				this.tblMain.getColumn("isPlan").getStyleAttributes()
						.setLocked(true);
			}
			if (this.tblMain.getColumn("preBuildingArea") != null) {
				this.tblMain.getColumn("preBuildingArea").getStyleAttributes()
						.setLocked(false);
				this.tblMain.getColumn("preBuildingArea").getStyleAttributes()
						.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);

				this.tblMain.getColumn("preBuildingArea").setEditor(
						numberEditor);
			}
			if (this.tblMain.getColumn("preRoomArea") != null) {
				this.tblMain.getColumn("preRoomArea").getStyleAttributes()
						.setLocked(false);
				this.tblMain.getColumn("preRoomArea").getStyleAttributes()
						.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);

				this.tblMain.getColumn("preRoomArea").setEditor(numberEditor);

			}
			if (this.tblMain.getColumn("isPre") != null) {
				this.tblMain.getColumn("isPre").getStyleAttributes().setLocked(
						true);
			}
			if (this.tblMain.getColumn("actualBuildingArea") != null) {
				this.tblMain.getColumn("actualBuildingArea")
						.getStyleAttributes().setLocked(false);
				this.tblMain.getColumn("actualBuildingArea")
						.getStyleAttributes().setNumberFormat(
								FDCClientHelper.KDTABLE_NUMBER_FTM);

				this.tblMain.getColumn("actualBuildingArea").setEditor(
						numberEditor);
			}
			if (this.tblMain.getColumn("actualRoomArea") != null) {
				this.tblMain.getColumn("actualRoomArea").getStyleAttributes()
						.setLocked(false);
				this.tblMain.getColumn("actualRoomArea").getStyleAttributes()
						.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);

				this.tblMain.getColumn("actualRoomArea")
						.setEditor(numberEditor);
			}
			if (this.tblMain.getColumn("isActualAreaAudited") != null) {
				this.tblMain.getColumn("isActualAreaAudited")
						.getStyleAttributes().setLocked(true);
			}
		} else {
			this.tblMain.getStyleAttributes().setLocked(true);
		}
		this.tblMain.getColumn("tenancyArea").getStyleAttributes().setLocked(false);
		this.tblMain.getColumn("tenancyArea").getStyleAttributes().setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);
		this.tblMain.getColumn("tenancyArea").setEditor(numberEditor);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (!this.verify()) {
			return;
		}

		List roomList = new ArrayList();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			RoomInfo room = new RoomInfo();
			room.setId(BOSUuid.read(row.getCell("id").getValue().toString()));
			
			if(row.getCell("planBuildingArea").getValue() instanceof String){
				room.setPlanBuildingArea(new BigDecimal(row.getCell("planBuildingArea").getValue().toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}else if(row.getCell("planBuildingArea").getValue() instanceof BigDecimal){
				room.setPlanBuildingArea(((BigDecimal) row.getCell(
				"planBuildingArea").getValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			
			if(row.getCell("planRoomArea").getValue() instanceof String){
				room.setPlanRoomArea(new BigDecimal(row.getCell("planRoomArea").getValue().toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}else if(row.getCell("planRoomArea").getValue() instanceof BigDecimal){
				room.setPlanRoomArea(((BigDecimal) row.getCell(
				"planRoomArea").getValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			
			if(row.getCell("preBuildingArea").getValue() instanceof String){
				room.setBuildingArea(new BigDecimal(row.getCell("preBuildingArea").getValue().toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}else if(row.getCell("preBuildingArea").getValue() instanceof BigDecimal){
				room.setBuildingArea(((BigDecimal) row.getCell(
				"preBuildingArea").getValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			
			if(row.getCell("preRoomArea").getValue() instanceof String){
				room.setRoomArea(new BigDecimal(row.getCell("preRoomArea").getValue().toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}else if(row.getCell("preRoomArea").getValue() instanceof BigDecimal){
				room.setRoomArea(((BigDecimal) row.getCell(
				"preRoomArea").getValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			
			if(row.getCell("actualBuildingArea").getValue() instanceof String){
				room.setActualBuildingArea(new BigDecimal(row.getCell("actualBuildingArea").getValue().toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}else if(row.getCell("actualBuildingArea").getValue() instanceof BigDecimal){
				room.setActualBuildingArea(((BigDecimal) row.getCell(
				"actualBuildingArea").getValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			
			if(row.getCell("actualRoomArea").getValue() instanceof String){
				room.setActualRoomArea(new BigDecimal(row.getCell("actualRoomArea").getValue().toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}else if(row.getCell("actualRoomArea").getValue() instanceof BigDecimal){
				room.setActualRoomArea(((BigDecimal) row.getCell(
				"actualRoomArea").getValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		
			room.setIsActAudited(((Boolean) row.getCell("isActAudited")
					.getValue()).booleanValue());
			room.setIsPreAudited(((Boolean) row.getCell("isPreAudited")
					.getValue()).booleanValue());
			room.setIsPlanAudited(((Boolean) row.getCell("isPlanAudited")
					.getValue()).booleanValue());
			if (row.getCell("sellAreaType").getValue() != null) {
				String type = row.getCell("sellAreaType").getValue().toString();
				if (type != null
						&& type.equals(SellAreaTypeEnum.PLANNING.getAlias())) {
					room.setSellAreaType(SellAreaTypeEnum.PLANNING);
				} else if (type != null
						&& type.equals(SellAreaTypeEnum.PRESELL.getAlias())) {
					room.setSellAreaType(SellAreaTypeEnum.PRESELL);
				} else if (type != null
						&& type.equals(SellAreaTypeEnum.ACTUAL.getAlias())) {
					room.setSellAreaType(SellAreaTypeEnum.ACTUAL);
				} else {
					room.setSellAreaType(SellAreaTypeEnum.PLANNING);
				}

			} else {
				room.setSellAreaType(SellAreaTypeEnum.PLANNING);
			}
			if(row.getCell("tenancyArea").getValue() instanceof String){
				room.setTenancyArea(new BigDecimal(row.getCell("tenancyArea").getValue().toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}else if(row.getCell("tenancyArea").getValue() instanceof BigDecimal){
				room.setTenancyArea(((BigDecimal) row.getCell(
				"tenancyArea").getValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			roomList.add(room);
		}
		try {
			RoomFactory.getRemoteInstance().updateAreaInfo(roomList);
			MsgBox.showInfo("保存成功!");
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "更新房间面积失败!");
		}
		super.actionSave_actionPerformed(e);
		this.execQuery();
	}

	public boolean verify() {

		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			BigDecimal planBuildingArea = null;
			if(row.getCell("planBuildingArea").getValue() instanceof String){
				planBuildingArea = new BigDecimal(row.getCell("planBuildingArea").getValue().toString());
			}else if(row.getCell("planBuildingArea").getValue() instanceof BigDecimal){
				planBuildingArea = (BigDecimal) row.getCell(
				"planBuildingArea").getValue();
			}
		
			if (planBuildingArea == null) {
				planBuildingArea = FDCHelper.ZERO;
			} else if (planBuildingArea != null
					&& planBuildingArea.compareTo(FDCHelper.ZERO) == -1) {
				MsgBox.showInfo("预估建筑面积不能为负数!");
				return false;
			}
			
			BigDecimal planRoomArea = null;
			if(row.getCell("planRoomArea").getValue() instanceof String){
				planRoomArea = new BigDecimal(row.getCell("planRoomArea").getValue().toString());
			}else if(row.getCell("planRoomArea").getValue() instanceof BigDecimal){
				planRoomArea = (BigDecimal) row.getCell(
				"planRoomArea").getValue();
			}
			if (planRoomArea == null) {
				planRoomArea = FDCHelper.ZERO;
			} else if (planRoomArea != null
					&& planRoomArea.compareTo(FDCHelper.ZERO) == -1) {
				MsgBox.showInfo("预估套内面积不能为负数!");
				return false;
			}
			if (planBuildingArea != null && planRoomArea != null
					&& planRoomArea.compareTo(planBuildingArea) > 0) {
				MsgBox.showInfo("第" + (i + 1) + "行" + "预估建筑面积必须大于等于套内面积!");
				return false;
			}
		
			BigDecimal preBuildingArea = null;
			if(row.getCell("preBuildingArea").getValue() instanceof String){
				preBuildingArea = new BigDecimal(row.getCell("preBuildingArea").getValue().toString());
			}else if(row.getCell("preBuildingArea").getValue() instanceof BigDecimal){
				preBuildingArea = (BigDecimal) row.getCell(
				"preBuildingArea").getValue();
			}
			
			if (preBuildingArea == null) {
				preBuildingArea = FDCHelper.ZERO;
			} else if (preBuildingArea != null
					&& preBuildingArea.compareTo(FDCHelper.ZERO) == -1) {
				MsgBox.showInfo("预售建筑面积不能为负数!");
				return false;
			}
		
			BigDecimal preRoomArea = null;
			if(row.getCell("preRoomArea").getValue() instanceof String){
				preRoomArea = new BigDecimal(row.getCell("preRoomArea").getValue().toString());
			}else if(row.getCell("preRoomArea").getValue() instanceof BigDecimal){
				preRoomArea = (BigDecimal) row.getCell(
				"preRoomArea").getValue();
			}
			
			if (preRoomArea == null) {
				preRoomArea = FDCHelper.ZERO;
			} else if (preRoomArea != null
					&& preRoomArea.compareTo(FDCHelper.ZERO) == -1) {
				MsgBox.showInfo("预售套内面积不能为负数!");
				return false;
			}
			if (preBuildingArea != null && preRoomArea != null
					&& preRoomArea.compareTo(preBuildingArea) > 0) {
				MsgBox.showInfo("第" + (i + 1) + "行" + "预售建筑面积必须大于等于套内面积!");
				return false;
			}
					
			BigDecimal actualBuildingArea = null;
			if(row.getCell("actualBuildingArea").getValue() instanceof String){
				actualBuildingArea = new BigDecimal(row.getCell("actualBuildingArea").getValue().toString());
			}else if(row.getCell("actualBuildingArea").getValue() instanceof BigDecimal){
				actualBuildingArea = (BigDecimal) row.getCell(
				"actualBuildingArea").getValue();
			}
			
			if (actualBuildingArea == null) {
				actualBuildingArea = FDCHelper.ZERO;
			} else if (actualBuildingArea != null
					&& actualBuildingArea.compareTo(FDCHelper.ZERO) == -1) {
				MsgBox.showInfo("实测建筑面积不能为负数!");
				return false;
			}
				
			BigDecimal actualRoomArea = null;
			if(row.getCell("actualRoomArea").getValue() instanceof String){
				actualRoomArea = new BigDecimal(row.getCell("actualRoomArea").getValue().toString());
			}else if(row.getCell("actualRoomArea").getValue() instanceof BigDecimal){
				actualRoomArea = (BigDecimal) row.getCell(
				"actualRoomArea").getValue();
			}
			
			if (actualRoomArea == null) {
				actualRoomArea = FDCHelper.ZERO;
			} else if (actualRoomArea != null
					&& actualRoomArea.compareTo(FDCHelper.ZERO) == -1) {
				MsgBox.showInfo("实测套内面积不能为负数!");
				return false;
			}
			if (actualBuildingArea != null && actualRoomArea != null
					&& actualRoomArea.compareTo(actualBuildingArea) > 0) {
				MsgBox.showInfo("第" + (i + 1) + "行" + "实测建筑面积必须大于等于套内面积!");
				return false;
			}
			
			BigDecimal tenancyArea = null;
			if(row.getCell("tenancyArea").getValue() instanceof String){
				tenancyArea = new BigDecimal(row.getCell("tenancyArea").getValue().toString());
			}else if(row.getCell("tenancyArea").getValue() instanceof BigDecimal){
				tenancyArea = (BigDecimal) row.getCell(
				"tenancyArea").getValue();
			}
			
			if (tenancyArea == null) {
				tenancyArea = FDCHelper.ZERO;
			} else if (tenancyArea != null
					&& tenancyArea.compareTo(FDCHelper.ZERO) == -1) {
				MsgBox.showInfo("计租面积不能为负数!");
				return false;
			}
		}

		return true;
	}

	public void actionPlanAudit_actionPerformed(ActionEvent e) throws Exception {
		List roomList = new ArrayList();
		roomList.clear();
		if (isEditedTable()) {
			MsgBox.showWarning("请先保存!");
			this.abort();
		}
		super.actionPlanAudit_actionPerformed(e);
		checkAudit(AUDIT_CHECK_TYPE_PLAN);
		roomList = getRoomList();
		try {
			RoomFactory.getRemoteInstance().planAudit(roomList);
			MsgBox.showInfo("预估复核成功!");
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "预估复核失败!");
		}

		this.execQuery();
	}

	private void checkAudit(String type) {
		String alertMsg = "";

		int selectRows[] = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows == null || selectRows.length <= 0) {
			MsgBox.showInfo("请先选择记录!");
			this.abort();
		}
		for (int i = 0; i < selectRows.length; i++) {
			int select = selectRows[i];
			IRow row = this.tblMain.getRow(select);
			if (row == null) {
				continue;
			}

			if (type.equals(AUDIT_CHECK_TYPE_PLAN)) {
				if (row.getCell("planBuildingArea").getValue() == null) {
					alertMsg = alertMsg + "预估建筑面积为空!";
				}
				if (row.getCell("planRoomArea").getValue() == null) {
					alertMsg = alertMsg + "预估套内面积为空!";
				}
			} else if (type.equals(AUDIT_CHECK_TYPE_PRE)) {

				if (row.getCell("preBuildingArea").getValue() == null) {
					alertMsg = alertMsg + "预售建筑内面积为空!";
				}
				if (row.getCell("preRoomArea").getValue() == null) {
					alertMsg = alertMsg + "预售套内面积为空!";
				}

			} else if (type.equals(AUDIT_CHECK_TYPE_ACT)) {

				if (row.getCell("actualBuildingArea").getValue() == null) {
					alertMsg = alertMsg + "实测建筑面积为空!";
				}
				if (row.getCell("actualRoomArea").getValue() == null) {
					alertMsg = alertMsg + "实测套内面积为空!";
				}
			}
		}

		if (!"".equals(alertMsg) && alertMsg.length() > 0) {
			if (type.equals(AUDIT_CHECK_TYPE_PLAN)) {
				MsgBox.showWarning("预估建筑面积或预估套内面积为空，请检查!");
				this.abort();
			} else if (type.equals(AUDIT_CHECK_TYPE_PRE)) {
				MsgBox.showWarning("预售建筑面积或预售套内面积为空，请检查!");
				this.abort();
			} else if (type.equals(AUDIT_CHECK_TYPE_ACT)) {
				MsgBox.showWarning("实测建筑面积或实测套内面积为空，请检查!");
				this.abort();
			}
		}
	}

	public void actionRePlanAudit_actionPerformed(ActionEvent e)
			throws Exception {
		if (isEditedTable()) {
			MsgBox.showWarning("请先保存!");
			this.abort();
		}

		super.actionRePlanAudit_actionPerformed(e);
		List roomList = getRoomList();
		try {
			RoomFactory.getRemoteInstance().planUnAudit(roomList);
			MsgBox.showInfo("反预估复核成功!");
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "反预估复核失败!");
		}
		this.execQuery();
	}

	public void actionPreAudit_actionPerformed(ActionEvent e) throws Exception {
		if (isEditedTable()) {
			MsgBox.showWarning("请先保存!");
			this.abort();
		}
		checkAudit(AUDIT_CHECK_TYPE_PRE);
		super.actionPreAudit_actionPerformed(e);
		List roomList = getRoomList();
		try {
			RoomFactory.getRemoteInstance().preAudit(roomList);
			MsgBox.showInfo("预售复核成功!");
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "预售复核失败!");
		}
		this.execQuery();
	}

	public void actionRePreAudit_actionPerformed(ActionEvent e)
			throws Exception {
		if (isEditedTable()) {
			MsgBox.showWarning("请先保存!");
			this.abort();
		}

		super.actionRePreAudit_actionPerformed(e);
		List roomList = getRoomList();
		try {
			RoomFactory.getRemoteInstance().preUnAudit(roomList);
			MsgBox.showInfo("反预售复核成功!");
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "反预售复核失败!");
		}
		this.execQuery();
	}

	public void actionActureAudit_actionPerformed(ActionEvent e)
			throws Exception {

		if (isEditedTable()) {
			MsgBox.showWarning("请先保存!");
			this.abort();
		}
		checkAudit(AUDIT_CHECK_TYPE_ACT);
		super.actionActureAudit_actionPerformed(e);
		List roomList = getRoomList();
		try {
			RoomFactory.getRemoteInstance().actAudit(roomList);
			MsgBox.showInfo("实测复核成功!");
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "实测复核失败!");
		}
		this.execQuery();

	}

	public void actionReActureAudit_actionPerformed(ActionEvent e)
			throws Exception {
		if (isEditedTable()) {
			MsgBox.showWarning("请先保存!");
			this.abort();
		}

		super.actionReActureAudit_actionPerformed(e);
		List roomList = getRoomList();
		try {
			RoomFactory.getRemoteInstance().actUnAudit(roomList);
			MsgBox.showInfo("反实测复核成功!");
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "反实测复核失败!");
		}
		this.execQuery();
	}

	
	/**
	 * 获得所选择的行
	 * @return
	 */
	  private int[] getSelectedRows() {
		int mode = 0;
		List blockList = this.tblMain.getSelectManager().getBlocks();
		// 判断是整表选取还是分块选取
		if (blockList != null && blockList.size() == 1) {
			mode = ((IBlock) this.tblMain.getSelectManager().getBlocks().get(0))
					.getMode();
		}
		if (isIgnoreRowCount() && mode == KDTSelectManager.TABLE_SELECT) {
			this.tblMain.setRowCount(getRowCountFromDB());
		}

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);

		return selectRows;
	}
	  
	private List getRoomList() {
		
		List roomList = new ArrayList();

		int selectRows[] = KDTableUtil.getSelectedRows(this.tblMain);
		
		if (selectRows == null || selectRows.length <= 0) {
			MsgBox.showInfo("请先选择记录!");
			this.abort();
		}
		this.getSelectedIdValues();
		
		for (int i = 0; i < selectRows.length; i++) {
			int select = selectRows[i];
			IRow row = this.tblMain.getRow(select);
			if (row == null) {
				continue;
			}

			RoomInfo room = new RoomInfo();
			if (row.getCell("id").getValue() != null) {
				room.setId(BOSUuid
						.read(row.getCell("id").getValue().toString()));

			}
			if (row.getCell("isPlanAudited").getValue() != null) {
				room.setIsPlanAudited(((Boolean) row.getCell("isPlanAudited")
						.getValue()).booleanValue());
			}
			if (row.getCell("isPreAudited").getValue() != null) {
				room.setIsPreAudited(((Boolean) row.getCell("isPreAudited")
						.getValue()).booleanValue());

			}
			if (row.getCell("isActAudited").getValue() != null) {
				room.setIsActAudited(((Boolean) row.getCell("isActAudited")
						.getValue()).booleanValue());

			}
			if (row.getCell("isChangeSellType").getValue() != null) {
				room.setIsChangeSellArea(((Boolean) row.getCell("isChangeSellType")
						.getValue()).booleanValue());

			}
			if(row.getCell("sellAreaType").getValue()!=null){
				String type = row.getCell("sellAreaType").getValue().toString();
				if(SellAreaTypeEnum.PLANNING.getAlias().equals(type)){
					room.setSellAreaType(SellAreaTypeEnum.PLANNING);
				}else if(SellAreaTypeEnum.PRESELL.getAlias().equals(type)){
					room.setSellAreaType(SellAreaTypeEnum.PRESELL);
				}else{
					room.setSellAreaType(SellAreaTypeEnum.ACTUAL);
				}
				
				
				
			}
			roomList.add(room);

		}
		return roomList;
	}

	protected void initListener() {
		super.initListener();
		this.tblMain.getDataRequestManager().addDataFillListener(
				new KDTDataFillListener() {
					public void afterDataFill(KDTDataRequestEvent e) {
						tblMain_afterDataFill(e);
					}
				});
	}

	private void tblMain_afterDataFill(KDTDataRequestEvent e) {

		if (saleOrg.isIsBizUnit() || isSellOrg()) {
			this.roomMap.clear();

			// 取得当前页的第一行
			int sr = e.getFirstRow();
			if (e.getFirstRow() > 0) {
				sr = sr - 1;
			}
			// 取得当前页的最后一行
			int lr = e.getLastRow();
			boolean isPlan = true;
			boolean isPre = true;
			boolean isAct = true;
			for (int i = sr; i <= lr; i++) {
				IRow row = tblMain.getRow(i);
				if (row == null) {
					continue;
				}

				RoomInfo room = new RoomInfo();

				room.setId(BOSUuid
						.read(row.getCell("id").getValue().toString()));
				if(row.getCell("planBuildingArea").getValue()!=null){
					room.setPlanBuildingArea(new BigDecimal(row.getCell("planBuildingArea").getValue().toString()));
				}
				if(row.getCell("planRoomArea").getValue()!=null){
					room.setPlanRoomArea(new BigDecimal(row.getCell("planRoomArea").getValue().toString()));
				}
				if(row.getCell("preBuildingArea").getValue()!=null){
					room.setBuildingArea(new BigDecimal(row.getCell("preBuildingArea").getValue().toString()));
				}
				if(row.getCell("preRoomArea").getValue()!=null){
					room.setRoomArea(new BigDecimal(row.getCell("preRoomArea").getValue().toString()));
				}
				if(row.getCell("actualBuildingArea").getValue()!=null){
					room.setActualBuildingArea(new BigDecimal(row.getCell("actualBuildingArea").getValue().toString()));
				}
				if(row.getCell("actualRoomArea").getValue()!=null){
					room.setActualRoomArea(new BigDecimal(row.getCell("actualRoomArea").getValue().toString()));
				}
				if(row.getCell("tenancyArea").getValue()!=null){
					room.setTenancyArea(new BigDecimal(row.getCell("tenancyArea").getValue().toString()));
				}
				this.roomMap.put(room.getId().toString(), room);

				if (row.getCell("isPlan").getValue() != null) {
					isPlan = ((Boolean) row.getCell("isPlan").getValue())
							.booleanValue();
				}
				if (row.getCell("isPre").getValue() != null) {
					isPre = ((Boolean) row.getCell("isPre").getValue())
							.booleanValue();
				}
				if (row.getCell("isActualAreaAudited").getValue() != null) {
					isAct = ((Boolean) row.getCell("isActualAreaAudited")
							.getValue()).booleanValue();
				}

				setLockAndColor(row, isPlan, isPre, isAct);
				if (row.getCell("sellAreaType").getValue() == null) {
					row.getCell("sellAreaType").setValue(
							SellAreaTypeEnum.PLANNING);
				} else {
					String type = row.getCell("sellAreaType").getValue()
							.toString();
					if (type != null && type.equals(SellAreaTypeEnum.PLANNING.getAlias())) {
						row.getCell("sellAreaType").setValue(
								SellAreaTypeEnum.PLANNING);
					} else if (type != null
							&& type.equals(SellAreaTypeEnum.PRESELL.getAlias())) {
						row.getCell("sellAreaType").setValue(
								SellAreaTypeEnum.PRESELL);
					} else if (type != null
							&& type.equals(SellAreaTypeEnum.ACTUAL.getAlias())) {
						row.getCell("sellAreaType").setValue(
								SellAreaTypeEnum.ACTUAL);
					}
				}
			}
		} else {
			this.tblMain.getStyleAttributes().setLocked(true);
		}
	}

	private void setLockAndColor(IRow row, boolean isPlan, boolean isPre,
			boolean isAct) {
		if (isPlan) {
			row.getCell("planBuildingArea").getStyleAttributes()
					.setLocked(true);
			row.getCell("planBuildingArea").getStyleAttributes()
					.setLocked(true);
			row.getCell("planBuildingArea").getStyleAttributes().setBackground(
					null);

			row.getCell("planRoomArea").getStyleAttributes().setLocked(true);
			row.getCell("planRoomArea").getStyleAttributes()
					.setBackground(null);

		} else {
			row.getCell("planBuildingArea").getStyleAttributes().setLocked(
					false);
			row.getCell("planBuildingArea").getStyleAttributes().setBackground(
					FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);

			row.getCell("planRoomArea").getStyleAttributes().setLocked(false);
			row.getCell("planRoomArea").getStyleAttributes().setBackground(
					FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
		}
		if (isPre) {
			row.getCell("preBuildingArea").getStyleAttributes().setLocked(true);
			row.getCell("preBuildingArea").getStyleAttributes().setBackground(
					null);

			row.getCell("preRoomArea").getStyleAttributes().setLocked(true);
			row.getCell("preRoomArea").getStyleAttributes().setBackground(null);

		} else {
			row.getCell("preBuildingArea").getStyleAttributes()
					.setLocked(false);
			row.getCell("preBuildingArea").getStyleAttributes().setBackground(
					FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);

			row.getCell("preRoomArea").getStyleAttributes().setLocked(false);
			row.getCell("preRoomArea").getStyleAttributes().setBackground(
					FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
		}
		if (isAct) {
			row.getCell("actualBuildingArea").getStyleAttributes().setLocked(
					true);
			row.getCell("actualBuildingArea").getStyleAttributes()
					.setBackground(null);

			row.getCell("actualRoomArea").getStyleAttributes().setLocked(true);
			row.getCell("actualRoomArea").getStyleAttributes().setBackground(
					null);

		} else {
			row.getCell("actualBuildingArea").getStyleAttributes().setLocked(
					false);
			row.getCell("actualBuildingArea").getStyleAttributes()
					.setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);

			row.getCell("actualRoomArea").getStyleAttributes().setLocked(false);
			row.getCell("actualRoomArea").getStyleAttributes().setBackground(
					FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
		}
		row.getCell("tenancyArea").getStyleAttributes().setLocked(
				false);
		row.getCell("tenancyArea").getStyleAttributes()
				.setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
		
		if (isPlan && isPre && isAct) {
			row.getCell("sellAreaType").getStyleAttributes().setLocked(true);
		}
	}

	public void actionAreaImport_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAreaImport_actionPerformed(e);
		this.importArea(this.tblMain);
	}

	private void importArea(KDTable table) throws Exception {

		String fileName = SHEHelper.showExcelSelectDlg(this);

		table.getColumn("id").getStyleAttributes().setLocked(true);
		//table.getColumn("sellAreaType").getStyleAttributes().setLocked(true);
		table.getColumn("roomNo").getStyleAttributes().setLocked(true);
		table.getColumn("number").getStyleAttributes().setLocked(true);
		table.getColumn("roomModel.name").getStyleAttributes().setLocked(true);
		table.getColumn("planBuildingArea").getStyleAttributes().setLocked(
				false);
		table.getColumn("planRoomArea").getStyleAttributes().setLocked(false);
		table.getColumn("isPlan").getStyleAttributes().setLocked(true);
		table.getColumn("preBuildingArea").getStyleAttributes()
				.setLocked(false);
		table.getColumn("preRoomArea").getStyleAttributes().setLocked(false);
		table.getColumn("isPre").getStyleAttributes().setLocked(true);
		table.getColumn("actualBuildingArea").getStyleAttributes().setLocked(
				false);
		table.getColumn("actualRoomArea").getStyleAttributes().setLocked(false);
		table.getColumn("isActualAreaAudited").getStyleAttributes().setLocked(
				true);
		table.getColumn("roomModel.id").getStyleAttributes().setLocked(true);

		boolean isPlan = true;
		boolean isPre = true;
		boolean isAct = true;
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row == null) {
				continue;
			}

			if (row.getCell("isPlan").getValue() != null) {
				boolean temp1 = ((Boolean) row.getCell("isPlan").getValue())
						.booleanValue();
				if (!temp1) {
					isPlan = false;
					break;
				}
			}
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row == null) {
				continue;
			}
			if (row.getCell("isPre").getValue() != null) {
				boolean temp2 = ((Boolean) row.getCell("isPre").getValue())
						.booleanValue();
				if (!temp2) {
					isPre = false;
					break;
				}
			}
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row == null) {
				continue;
			}
			if (row.getCell("isActualAreaAudited").getValue() != null) {
				boolean temp3 = ((Boolean) row.getCell("isActualAreaAudited")
						.getValue()).booleanValue();
				if (!temp3) {
					isAct = false;
					break;
				}
			}
		}
		if (isPlan) {
			this.tblMain.getColumn("planBuildingArea").getStyleAttributes()
					.setLocked(true);
			this.tblMain.getColumn("planRoomArea").getStyleAttributes()
					.setLocked(true);
		}
		if (isPre) {
			this.tblMain.getColumn("preBuildingArea").getStyleAttributes()
					.setLocked(true);
			this.tblMain.getColumn("preRoomArea").getStyleAttributes()
					.setLocked(true);
		}
		if (isAct) {
			this.tblMain.getColumn("actualBuildingArea").getStyleAttributes()
					.setLocked(true);
			this.tblMain.getColumn("actualRoomArea").getStyleAttributes()
					.setLocked(true);

		}
		int count = SHEHelper.importExcelToTableForRoomArea(fileName, table, 1, 2);
		MsgBox.showInfo("已经成功导入" + count + "条数据!");
		//table.getColumn("sellAreaType").getStyleAttributes().setLocked(false);

	}

	public boolean isEditedTable() {
		KDTable table = this.tblMain;
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (this.isEditedRow(row)) {
				return true;
			}

		}
		return false;
	}

	public boolean isEditedRow(IRow row) {
		if (!this.roomMap.isEmpty()) {

			RoomInfo room = (RoomInfo) this.roomMap.get(row.getCell("id")
					.getValue().toString());
			if (room == null) {
				return false;
			}
			String[] compareyKeys = new String[] {"tenancyArea", "planBuildingArea",
					"planRoomArea", "buildingArea", "roomArea",
					"actualBuildingArea", "actualRoomArea" };
			String[] columnKeys = new String[] {"tenancyArea", "planBuildingArea",
					"planRoomArea", "preBuildingArea", "preRoomArea",
					"actualBuildingArea", "actualRoomArea" };
			for (int i = 0; i < compareyKeys.length; i++) {
				if (row.getCell(columnKeys[i]).getValue()!=null&&room.get(compareyKeys[i])!=null){
					if (!FDCHelper.isEqual(room.get(compareyKeys[i]),new BigDecimal(row.getCell(
							columnKeys[i]).getValue().toString()))) {
						return true;
					}
				}else if(row.getCell(columnKeys[i]).getValue()==null&&room.get(compareyKeys[i])!=null){
					return true;
				}else if(row.getCell(columnKeys[i]).getValue()!=null&&room.get(compareyKeys[i])==null){
					return true;
				}
			}
		}
		return false;
	}
}