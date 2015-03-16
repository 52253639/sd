/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardCollection;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardFactory;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo;
import com.kingdee.eas.fdc.sellhouse.EyeSignhtCollection;
import com.kingdee.eas.fdc.sellhouse.EyeSignhtFactory;
import com.kingdee.eas.fdc.sellhouse.EyeSignhtInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionCollection;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionFactory;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.LinkmanEntryInfo;
import com.kingdee.eas.fdc.sellhouse.NoiseCollection;
import com.kingdee.eas.fdc.sellhouse.NoiseFactory;
import com.kingdee.eas.fdc.sellhouse.NoiseInfo;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardCollection;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardFactory;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardInfo;
import com.kingdee.eas.fdc.sellhouse.ProductDetialCollection;
import com.kingdee.eas.fdc.sellhouse.ProductDetialFactory;
import com.kingdee.eas.fdc.sellhouse.ProductDetialInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFormCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFormFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFormInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomModelCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomUsageCollection;
import com.kingdee.eas.fdc.sellhouse.RoomUsageFactory;
import com.kingdee.eas.fdc.sellhouse.RoomUsageInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SightRequirementCollection;
import com.kingdee.eas.fdc.sellhouse.SightRequirementFactory;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ImportExcelRoomUIForSHE extends AbstractImportExcelRoomUIForSHE {
	private static final String COL_IS_FOR_PPM = "isForPPM";

	private static final String COL_IS_FOR_TEN = "isForTen";

	private static final String COL_IS_FOR_SHE = "isForSHE";

	private static final String COL_PRODUCT_DETAIL = "productDetail";

	private static final String COL_FITMENT_STANDARD = "fitmentStandard";

	private static final String COL_DELIVER_HOUSE_STANDARD = "deliverHouseStandard";

	private static final String COL_ROOM_NUMBER = "roomNumber";

	private static final String COL_IMPORT_STATUS = "importStatus";

	private static final String COL_ID = "id";

	private static final String COL_PRODUCT_TYPE = "productType";

	private static final String COL_HOUSE_PROPERTY = "houseProperty";

	private static final String COL_BUILDING_PROPERTY = "buildingProperty";

	private static final String COL_ROOM_FORM = "roomForm";

	private static final String COL_SIGHT = "sight";

	private static final String COL_DIRECTION = "direction";

	private static final String COL_FLOOR_HEIGHT = "floorHeight";

	private static final String COL_ACTUAL_ROOM_AREA = "actualRoomArea";

	private static final String COL_ACTUAL_BUILDING_AREA = "actualBuildingArea";

	private static final String COL_FLAT_AREA = "flatArea";

	private static final String COL_USE_AREA = "useArea";

	private static final String COL_CARBARN_AREA = "carbarnArea";

	private static final String COL_GUARDEN_AREA = "guardenArea";

	private static final String COL_BALCONY_AREA = "balconyArea";

	private static final String COL_ROOM_MODEL = "roomModel";

	private static final String COL_APPORTION_AREA = "apportionArea";

	private static final String COL_ROOM_AREA = "roomArea";

	private static final String COL_BUILDING_AREA = "buildingArea";

	private static final String COL_SEQ = "seq";

	private static final String COL_FLOOR = "floor";

	private static final String COL_UNIT = "unit";

	private static final String COL_BUILDING_NUMBER = "buildingNumber";

	private static final String COL_ROOMNO = "roomNo";

	private static final String COL_ROOMPROPNO = "roomPropNo";

	private static final String COL_EYESIGHT = "eyeSight";

	private static final String COL_NOISE = "noise";

	private static final String COL_ROOMUSAGE = "roomUsage";

	private static final Logger logger = CoreUIObject.getLogger(ImportExcelRoomUIForSHE.class);

	private static final String IMPORT_STATE_ADD = "校验通过,数据新增";
	private static final String IMPORT_STATE_EDIT = "校验通过,数据修改";

	private static final String INVALID_BUILD_NUMBER = "楼栋编码无法识别";
//	private static final String INVALID_ROOM_NUMBER = "预测房号不能为空";
	private static final String INVALID_ROOM_NUMBER = "房源编号不能为空";
	private static final String INVALID_UNIT = "单元无效";
	private static final String INVALID_FLOOR = "楼层无效";
	private static final String INVALID_SEQ = "序号无效";
	private static final String REPEATED_SEQ = "序号重复";

	boolean isSellHouse =	FDCSysContext.getInstance().checkIsSHEOrg();
	Map orgMap = FDCSysContext.getInstance().getOrgMap();
	/**
	 * output class constructor
	 */
	public ImportExcelRoomUIForSHE() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionView.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionRefresh.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);

		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);

		this.txtTotalCount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtAddNewCount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtEditCount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtCanNotImportCount.setHorizontalAlignment(JTextField.RIGHT);

		//租售项目过滤
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		UserInfo currUser = SysContext.getSysContext().getCurrentUserInfo();
//		filter.getFilterItems().add(new FilterItemInfo("id", CommerceHelper.getPermitProjectIdSql(currUser), CompareType.INNER));
//		view.setFilter(filter);
//		this.f7SellProject.setEntityViewInfo(view);

		initRoomTable();
		this.txtPath.setEditable(false);
		
		this.f7SellProject.setValue(this.getUIContext().get("sellProject"));
		this.f7SellProject.setEnabled(false);
	}

	private void setFormattedCol(String rowName, int pre) {
		this.tblMain.getColumn(rowName).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn(rowName).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(pre));
	}

	private ICellEditor getKDTDefaultCellEditor() {
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		return numberEditor;
	}

	private ICellEditor getIntegerCellEditor() {
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		// formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		return numberEditor;
	}

	private void initRoomTable() throws BOSException, EASBizException {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);

		// this.tblMain.getColumn(COL_IMPORT_STATUS).getStyleAttributes().
		// setLocked(true);
		this.tblMain.getColumn(COL_BUILDING_NUMBER).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery", null));
		//this.tblMain.getColumn(COL_ROOM_NUMBER).getStyleAttributes().setLocked
		// (true);

		// this.tblMain.getColumn(COL_UNIT).setEditor(getIntegerCellEditor());
		this.tblMain.getColumn(COL_UNIT).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.BuildingUnitQuery", null));
//		this.tblMain.getColumn(COL_FLOOR).setEditor(getIntegerCellEditor());

		this.tblMain.getColumn(COL_SEQ).setEditor(getIntegerCellEditor());
		this.tblMain.getColumn(COL_BUILDING_AREA).setEditor(getKDTDefaultCellEditor());
		this.tblMain.getColumn(COL_ROOM_AREA).setEditor(getKDTDefaultCellEditor());
		this.tblMain.getColumn(COL_APPORTION_AREA).setEditor(getKDTDefaultCellEditor());
		this.tblMain.getColumn(COL_ROOM_MODEL).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.RoomModelForSHEQuery", SHEHelper.getModelBySellProjectView((SellProjectInfo)this.f7SellProject.getValue())));

		this.tblMain.getColumn(COL_BALCONY_AREA).setEditor(getKDTDefaultCellEditor());
		this.tblMain.getColumn(COL_GUARDEN_AREA).setEditor(getKDTDefaultCellEditor());
		this.tblMain.getColumn(COL_CARBARN_AREA).setEditor(getKDTDefaultCellEditor());
		this.tblMain.getColumn(COL_USE_AREA).setEditor(getKDTDefaultCellEditor());
		this.tblMain.getColumn(COL_FLAT_AREA).setEditor(getKDTDefaultCellEditor());

		this.tblMain.getColumn(COL_ACTUAL_BUILDING_AREA).setEditor(getKDTDefaultCellEditor());
		this.tblMain.getColumn(COL_ACTUAL_ROOM_AREA).setEditor(getKDTDefaultCellEditor());

		this.tblMain.getColumn(COL_FLOOR_HEIGHT).setEditor(getKDTDefaultCellEditor());

		this.tblMain.getColumn(COL_DIRECTION).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery", null));
		//新售楼下，基础资料用的是一个
		
		this.tblMain.getColumn(COL_SIGHT).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery", CommerceHelper.getNewEysSightView()));
//		this.tblMain.getColumn(COL_SIGHT).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.SightRequirementQuery", null));
		
		this.tblMain.getColumn(COL_FITMENT_STANDARD).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery", CommerceHelper.getDecoraStdView()));
//		this.tblMain.getColumn(COL_FITMENT_STANDARD).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.DecorationStandardQuery", null));
		
		this.tblMain.getColumn(COL_DELIVER_HOUSE_STANDARD).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery", CommerceHelper.getPossStdView()));
//		this.tblMain.getColumn(COL_DELIVER_HOUSE_STANDARD).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.PossessionStandardQuery", null));
		
		this.tblMain.getColumn(COL_EYESIGHT).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.EyeSignhtQuery", null));
		this.tblMain.getColumn(COL_EYESIGHT).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery", CommerceHelper.getNewEysSightView()));
		
		this.tblMain.getColumn(COL_NOISE).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.NoiseQuery", null));
		this.tblMain.getColumn(COL_NOISE).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.F7RoomAssistantQuery", CommerceHelper.getNewNoiseView()));
		
		this.tblMain.getColumn(COL_ROOMUSAGE).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.RoomUsageQuery", null));

		this.tblMain.getColumn(COL_ROOM_FORM).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.RoomFormQuery", null));
		// this.tblMain.getColumn(COL_DELIVER_HOUSE_STANDARD).setEditor(
		// getKDTDefaultCellEditor());
		// this.tblMain.getColumn(COL_FITMENT_STANDARD).setEditor(
		// getKDTDefaultCellEditor());
		this.tblMain.getColumn(COL_BUILDING_PROPERTY).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery", null));
		this.tblMain.getColumn(COL_HOUSE_PROPERTY).setEditor(CommerceHelper.getKDComboBoxEditor(HousePropertyEnum.getEnumList()));

		EntityViewInfo proTypeView = new EntityViewInfo();
		FilterInfo proTypeFilter = new FilterInfo();
		proTypeView.setFilter(proTypeFilter);
		proTypeFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		this.tblMain.getColumn(COL_PRODUCT_TYPE).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery", proTypeView));

		this.tblMain.getColumn(COL_PRODUCT_DETAIL).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.ProductDetialQuery", null));

		// this.tblMain.getColumn(COL_IS_FOR_SHE).setEditor(CommerceHelper.
		// getKDBizPromptBoxEditor
		// ("com.kingdee.eas.fdc.sellhouse.app.ProductDetialQuery", null));
		// this.tblMain.getColumn(COL_IS_FOR_TEN).setEditor(CommerceHelper.
		// getKDBizPromptBoxEditor
		// ("com.kingdee.eas.fdc.sellhouse.app.ProductDetialQuery", null));
		// this.tblMain.getColumn(COL_IS_FOR_PPM).setEditor(CommerceHelper.
		// getKDBizPromptBoxEditor
		// ("com.kingdee.eas.fdc.sellhouse.app.ProductDetialQuery", null));

		if( FDCSysContext.getInstance().checkIsSHEOrg()){
			 this.tblMain.getColumn(COL_IS_FOR_SHE).getStyleAttributes().setHided(true);
			 this.tblMain.getColumn(COL_IS_FOR_TEN).getStyleAttributes().setHided(true);
			 this.tblMain.getColumn(COL_IS_FOR_PPM).getStyleAttributes().setHided(true);
		}
	
				
		// 不需要导入的列灰显
		this.tblMain.getStyleAttributes().setLocked(false);
		setColCanNotEdit(COL_ID);
		setColCanNotEdit(COL_IMPORT_STATUS);
		setColCanNotEdit(COL_BUILDING_NUMBER);
		setColCanNotEdit(COL_ROOM_NUMBER);

		setFormattedCol(COL_UNIT, 0);
		setFormattedCol(COL_FLOOR, 0);
		setFormattedCol(COL_SEQ, 0);
		setFormattedCol(COL_BUILDING_AREA, 2);
		setFormattedCol(COL_ROOM_AREA, 2);

		setFormattedCol(COL_APPORTION_AREA, 2);
		setFormattedCol(COL_BALCONY_AREA, 2);
		setFormattedCol(COL_GUARDEN_AREA, 2);
		setFormattedCol(COL_CARBARN_AREA, 2);
		setFormattedCol(COL_USE_AREA, 2);

		setFormattedCol(COL_FLAT_AREA, 2);
		setFormattedCol(COL_ACTUAL_BUILDING_AREA, 2);
		setFormattedCol(COL_ACTUAL_ROOM_AREA, 2);
		setFormattedCol(COL_FLOOR_HEIGHT, 2);

		// //必须导入的列
		// this.tblMain.getColumn("name").getStyleAttributes().setBackground(
		// FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		// this.tblMain.getColumn("phone").getStyleAttributes().setBackground(
		// FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		// this.tblMain.getColumn("salesman.name").getStyleAttributes().
		// setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		
		if(null!=this.getUIContext().get("sellProject")){
			EntityViewInfo viewSellPro = new EntityViewInfo();
			FilterInfo filterSellPro = new FilterInfo();
			filterSellPro.getFilterItems().add(new FilterItemInfo("id",SHEHelper.getAllSellProjectIds((SellProjectInfo)this.getUIContext().get("sellProject"),null),CompareType.INCLUDE));
			viewSellPro.setFilter(filterSellPro);
			this.f7SellProject.setEntityViewInfo(viewSellPro);
			
		}
	}

	/** 辅助函数，设置row行colName列对应的cell不可编辑,并置底色为灰色 */
	private void setCellNotEdit(IRow row, String colName) {
		ICell cell = row.getCell(colName);
		cell.getStyleAttributes().setLocked(true);
		cell.getStyleAttributes().setBackground(new Color(89,172,255));
	}

	/** 辅助函数，设置tblMain的colName列不可编辑,并置底色为灰色 */
	private void setColCanNotEdit(String colName) {
		this.tblMain.getColumn(colName).getStyleAttributes().setLocked(true);
		this.tblMain.getColumn(colName).getStyleAttributes().setBackground(new Color(89,172,255));
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		String key = this.getSelectedKeyValue();
		if (key == null)
			return;

		super.tblMain_tableClicked(e);
	}

	protected String getEditUIName() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		super.actionImport_actionPerformed(e);
		SellProjectInfo sellPro = (SellProjectInfo) this.f7SellProject.getValue();
		if (sellPro == null) {
			MsgBox.showInfo(this, "请先选择销售项目！");
			abort();
		}

		String path = SHEHelper.showExcelSelectDlg(this);
		if (path == null) {
			abort();
		}

		// --对产品描述增加项目过滤
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellPro.getId().toString()));
		this.tblMain.getColumn(COL_PRODUCT_DETAIL).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.ProductDetialQuery", view));
		// ---

		this.txtPath.setText(path);
		this.tblMain.removeRows(false);

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
			public void afterExec(Object arg0) throws Exception {
			}
			public Object exec() throws Exception {
				// 先将Excel文件的的数据机械的显示在Table上
				importExcelToTable(txtPath.getText(), tblMain);
				// 根据每列的楼栋编码进行识别.(?
				// 从Excel对应的楼栋编码列在数据库中的楼栋进行查找,如果不存在Excel中的楼栋编码则为未识别,不显示该行.房间编码为空,也算不能识别)
				// 根据该楼栋下的房间编码是否已在库中存在确认每行的导入类型是新增导入还是修改
				distinguishFromTableData();
				return null;
			}
        }  
        );
        dialog.show();
	}

	public void actionDoImport_actionPerformed(ActionEvent e) throws Exception {
		if (MsgBox.isYes(MsgBox.showConfirm2(this, "是否确认导入？"))) {
			
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
				public void afterExec(Object arg0) throws Exception {
				}
				public Object exec() throws Exception {
					doImport();
					return null;
				}
	        }  
	        );
	        dialog.show();
		}
	}

	/**
	 * 校验并执行
	 * */
	private void doImport() throws BOSException, EASBizException {
		SellProjectInfo sellProject = (SellProjectInfo) this.f7SellProject.getValue();
		if (this.tblMain.getRowCount() == 0) {
			MsgBox.showInfo(this, "没有需要导入的数据！");
			return;
		}

		// 朝向，景观，房屋形式，建筑性质，房产性质，产品类型。将这些基础资料缓存，避免在遍历房间时候循环重复查询
		// 户型有点特殊，户型编码名称是楼栋内唯一.故缓存户型结构为2级Map结构 key(楼栋id)-Map( key(户型名称)-户型对象 )
		Map roomModelMap = getRoomModelMap();
		Map buildUnitMap = getBuildInitMap();

		Map directionMap = getDirectionMap();
		Map sightMap = getSightMap();
		Map roomFormMap = getRoomFormMap();
		Map buildingProMap = getBuildingProMap();

		Map houseProEnumMap = getHouseProEnumMap();
		Map productTypeMap = getProductTypeMap();
		Map productDetailMap = getProductDetailMap();

		Map floorMap = this.getBuildingFloorMap();

		CoreBaseCollection rooms = new CoreBaseCollection();
		StringBuffer errMsg = new StringBuffer();
		Map BuildRoomPropNoExistMap = new HashMap();
//
//		Map existRoomNoMap = this.getExistRoomNoMap();
//		Map existRoomPropNoMap = this.getExistRoomPropNoMap();

		Map roomUsageMap = this.getRoomUsageMap();
		Map noiseMap = this.getNoiseMap();
		Map eyeSightMap = this.getEyeSightMap();
		Map possessionStdMap = this.getPossessionSTDMap();
		Map decorationMap = this.getDecorationSTDMap();
	
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			String importStatus = (String) row.getCell(COL_IMPORT_STATUS).getValue();
			if (!IMPORT_STATE_ADD.equals(importStatus) && !IMPORT_STATE_EDIT.equals(importStatus)) {
				continue;
			}

			RoomInfo room = (RoomInfo) row.getUserObject();
			if (room == null) {
				logger.error("逻辑错误.");
				continue;
			}

			BuildingInfo building = room.getBuilding();
			if (building == null) {
				logger.error("逻辑错误.");
				continue;
			}

//			int unitCount = building.getUnitCount();
			int floorCount = building.getFloorCount();

			Object obj = row.getCell(COL_UNIT).getValue();
			// 单元 已改为单元名称列，要录入单元的名称
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
		
			filter.getFilterItems().add(new FilterItemInfo("building.id",  building.getId().toString()));
	
			view.setFilter(filter);
			BuildingUnitCollection buildUnitCol = BuildingUnitFactory.getRemoteInstance().getBuildingUnitCollection(view);
			if(buildUnitCol.size()>0&&(null==obj||"".equals(obj))){
				if(1==buildUnitCol.size()&&!((BuildingUnitInfo)buildUnitCol.get(0)).isHaveUnit()){
					FDCMsgBox.showWarning("已有单元生成，请选择单元！ ");
					this.abort();
				}
				if(2<buildUnitCol.size()){
					FDCMsgBox.showWarning("已有单元生成，请选择单元！ ");
					this.abort();
				}
			}
				//by tim_gao 支持空的
				if(null==obj||"".equals(obj)){//给一个默认的
					obj = SHEHelper.getShowUnitWithRoomDes(building);
					//更新楼栋单元数量的值
					SelectorItemCollection select = new SelectorItemCollection();
					select.add("unitCount");
					building.setUnitCount(1);
					BuildingFactory.getRemoteInstance().updatePartial(building, select);
				}
			
				if (obj instanceof String) {
					Map map = (Map) buildUnitMap.get(building.getId().toString());
					BuildingUnitInfo buildUnit = null;
					if (map != null) {
						buildUnit = (BuildingUnitInfo) map.get((String) obj);
					}
					row.getCell(COL_UNIT).setValue(buildUnit);
					room.setBuildUnit(buildUnit);
				} else if (obj instanceof BuildingUnitInfo) {
					room.setBuildUnit((BuildingUnitInfo) obj);
				}
				
//				if (unitCount != 0 && room.getBuildUnit() == null) {
//					errMsg.append("\r\n第" + (i + 1) + "行单元不能为空！");
//				}

				if (room.getBuildUnit() != null) {
					room.setUnit(room.getBuildUnit().getSeq());
				}

			

			obj = row.getCell(COL_FLOOR).getValue();
			// 楼层可以支持负数
			if (obj == null) {
				errMsg.append("\r\n第" + (i + 1) + "行楼层不能为空！");
			} else {
				int floor = -1;
				if(obj instanceof String){
					String tmpS = (String) obj;
					floor = Integer.parseInt(tmpS);
					
				}else if(obj instanceof Integer){
					floor = ((Integer)obj).intValue();
				}
				room.setFloor(floor);
//				if(floor>0){
//					room.setFloor(floor);
//				}
//				else{
//						errMsg.append("\r\n第" + (i + 1) + "行楼层不能为空！");
//						this.abort();
//					}
			}
				
//				if (!row.getCell(COL_FLOOR).getStyleAttributes().isLocked()) {
//					if (obj instanceof String) {
//						String tmpS = (String) obj;
//						int floor = Integer.parseInt(tmpS);
//						if (floor > floorCount) {
//							errMsg.append("\r\n第" + (i + 1) + "行楼层不合理！");
//						} else {
//							room.setFloor(floor);
//
//							// 加上一个 楼层分录的 关联属性
//							Map map = (Map) floorMap.get(building.getId().toString());
//							if (map != null) {
//								if (map.get(new Integer(floor)) != null) {
//									Object o = map.get(new Integer(floor));
//									if (o instanceof BuildingFloorEntryInfo) {
//										room.setBuildingFloor((BuildingFloorEntryInfo) o);
//									}
//								}
//							}
//
//						}
//					} else if (obj instanceof Integer) {
//						int floor = ((Integer) obj).intValue();
//						if (floor > floorCount) {
//							errMsg.append("\r\n第" + (i + 1) + "行楼层不合理！");
//						} else {
//							room.setFloor(floor);
//
//							// 加上一个 楼层分录的 关联属性
//							Map map = (Map) floorMap.get(building.getId().toString());
//							if (map != null) {
//								if (map.get(new Integer(floor)) != null) {
//									Object o = map.get(new Integer(floor));
//									if (o instanceof BuildingFloorEntryInfo) {
//										room.setBuildingFloor((BuildingFloorEntryInfo) o);
//									}
//								}
//							}
//						}
//					}
//					else {
//						logger.error("unit楼层列类型: " + obj.getClass().getName());
//					}
//				}
//			}

			obj = row.getCell(COL_SEQ).getValue();
			if (obj == null) {
				errMsg.append("\r\n第" + (i + 1) + "行序号不能为空！");
			} else {
				if (!row.getCell(COL_SEQ).getStyleAttributes().isLocked()) {
					if (obj instanceof String) {
						String tmpS = (String) obj;
						int seq = Integer.parseInt(tmpS);
						if (seq <= 0) {
							errMsg.append("\r\n第" + (i + 1) + "行序号不合理！");
						} else {
							room.setSerialNumber(seq);
							room.setEndSerialNumber(seq);
						}
					} else if (obj instanceof Integer) {
						int seq = ((Integer) obj).intValue();
						if (seq <= 0) {
							errMsg.append("\r\n第" + (i + 1) + "行序号不合理！");
						} else {
							room.setSerialNumber(seq);
							room.setEndSerialNumber(seq);
						}
					} else {
						logger.error("unit序号列类型: " + obj.getClass().getName());
					}
				}
			}
		
			
			setRoomBigDecimalValue(room, row, COL_BUILDING_AREA, "buildingArea");
			setRoomBigDecimalValue(room, row, COL_ROOM_AREA, "roomArea");
			setRoomBigDecimalValue(room, row, COL_APPORTION_AREA, "apportionArea");

			// 户型
			// by tim_gao 当为售楼组织时跟据项目过滤
			if (!row.getCell(COL_ROOM_MODEL).getStyleAttributes().isLocked()) {
				obj = row.getCell(COL_ROOM_MODEL).getValue();
				if (obj instanceof String) {
					
					if(!FDCSysContext.getInstance().checkIsSHEOrg()){
						Map map = (Map) roomModelMap.get(building.getId().toString());
						RoomModelInfo roomModel = null;
						if (map != null) {
							roomModel = (RoomModelInfo) map.get((String) obj);
						}

						row.getCell(COL_ROOM_MODEL).setValue(roomModel);
						room.setRoomModel(roomModel);
					}else{
						if(null!=building.getSellProject()){
							Map map = (Map) roomModelMap.get(building.getSellProject().getId().toString());
							RoomModelInfo roomModel = null;
							if (map != null) {
								roomModel = (RoomModelInfo) map.get((String) obj);
							}

							row.getCell(COL_ROOM_MODEL).setValue(roomModel);
							room.setRoomModel(roomModel);
						}
						
					}
					
				} else if (obj instanceof RoomModelInfo) {
					room.setRoomModel((RoomModelInfo) obj);
				}

//				if (room.getRoomModel() == null) {
//					errMsg.append("\r\n第" + (i + 1) + "行户型不能为空！");
//				}
			}

			setRoomBigDecimalValue(room, row, COL_BALCONY_AREA, "balconyArea");
			setRoomBigDecimalValue(room, row, COL_GUARDEN_AREA, "guardenArea");
			setRoomBigDecimalValue(room, row, COL_CARBARN_AREA, "carbarnArea");
			setRoomBigDecimalValue(room, row, COL_USE_AREA, "useArea");

			setRoomBigDecimalValue(room, row, COL_FLAT_AREA, "flatArea");
			setRoomBigDecimalValue(room, row, COL_ACTUAL_BUILDING_AREA, "actualBuildingArea");
			setRoomBigDecimalValue(room, row, COL_ACTUAL_ROOM_AREA, "actualRoomArea");
			setRoomBigDecimalValue(room, row, COL_FLOOR_HEIGHT, "floorHeight");

			// 朝向，景观，房屋形式，
			if (!row.getCell(COL_DIRECTION).getStyleAttributes().isLocked()) {
				obj = row.getCell(COL_DIRECTION).getValue();
				if (obj instanceof String) {
					room.setDirection((HopedDirectionInfo) directionMap.get((String) obj));
				} else if (obj instanceof HopedDirectionInfo) {
					room.setDirection((HopedDirectionInfo) obj);
				}
			}

			if (!row.getCell(COL_SIGHT).getStyleAttributes().isLocked()) {
				obj = row.getCell(COL_SIGHT).getValue();
				if (obj instanceof String) {
					room.setSight((SightRequirementInfo) sightMap.get((String) obj));
				} else if (obj instanceof SightRequirementInfo) {
					room.setSight((SightRequirementInfo) obj);
				}
			}

			if (!row.getCell(COL_ROOM_FORM).getStyleAttributes().isLocked()) {
				obj = row.getCell(COL_ROOM_FORM).getValue();
				if (obj instanceof String) {
					room.setRoomForm((RoomFormInfo) roomFormMap.get((String) obj));
				} else if (obj instanceof RoomFormInfo) {
					room.setRoomForm((RoomFormInfo) obj);
				}
			}

//			String value = (String) row.getCell(COL_DELIVER_HOUSE_STANDARD).getValue();
//			if (value != null && value.trim().length() > 80) {
//				errMsg.append("\r\n第" + (i + 1) + "行交房标准长度不能大于80！");
//			}
//			value = (String) row.getCell(COL_FITMENT_STANDARD).getValue();
//			if (value != null && value.trim().length() > 80) {
//				errMsg.append("\r\n第" + (i + 1) + "行装修标准长度不能大于80！");
//			}

			// setRoomStringValue(room, row, COL_DELIVER_HOUSE_STANDARD,
			// "deliverHouseStandard");
			// setRoomStringValue(room, row, COL_FITMENT_STANDARD,
			// "fitmentStandard");
            //装修标准，交房标准，视野，噪音，房间用途
			if (!row.getCell(COL_FITMENT_STANDARD).getStyleAttributes().isLocked()) {
				obj = row.getCell(COL_FITMENT_STANDARD).getValue();
				if (obj instanceof String) {
					room.setDecoraStd((DecorationStandardInfo) decorationMap.get((String) obj));
				} else if (obj instanceof DecorationStandardInfo) {
					room.setDecoraStd((DecorationStandardInfo) obj);
				}
			}

			if (!row.getCell(COL_DELIVER_HOUSE_STANDARD).getStyleAttributes().isLocked()) {
				obj = row.getCell(COL_DELIVER_HOUSE_STANDARD).getValue();
				if (obj instanceof String) {
					room.setPosseStd((PossessionStandardInfo) possessionStdMap.get((String) obj));
				} else if (obj instanceof PossessionStandardInfo) {
					room.setPosseStd((PossessionStandardInfo) obj);
				}
			}

			if (!row.getCell(COL_EYESIGHT).getStyleAttributes().isLocked()) {
				obj = row.getCell(COL_EYESIGHT).getValue();
				if (obj instanceof String) {
					room.setEyeSignht((EyeSignhtInfo) eyeSightMap.get((String) obj));
				} else if (obj instanceof EyeSignhtInfo) {
					room.setEyeSignht((EyeSignhtInfo) obj);
				}
			}

			if (!row.getCell(COL_NOISE).getStyleAttributes().isLocked()) {
				obj = row.getCell(COL_NOISE).getValue();
				if (obj instanceof String) {
					room.setNoise((NoiseInfo) noiseMap.get((String) obj));
				} else if (obj instanceof NoiseInfo) {
					room.setNoise((NoiseInfo) obj);
				}
			}

			if (!row.getCell(COL_ROOMUSAGE).getStyleAttributes().isLocked()) {
				obj = row.getCell(COL_ROOMUSAGE).getValue();
				if (obj instanceof String) {
					room.setRoomUsage((RoomUsageInfo) roomUsageMap.get((String) obj));
				} else if (obj instanceof RoomUsageInfo) {
					room.setRoomUsage((RoomUsageInfo) obj);
				}
			}

			// 建筑性质，房产性质，产品类型,产品描述
			if (!row.getCell(COL_BUILDING_PROPERTY).getStyleAttributes().isLocked()) {
				obj = row.getCell(COL_BUILDING_PROPERTY).getValue();
				if (obj instanceof String) {
					BuildingPropertyInfo buPro = (BuildingPropertyInfo) buildingProMap.get((String) obj);
					row.getCell(COL_BUILDING_PROPERTY).setValue(buPro);
					room.setBuildingProperty(buPro);
				} else if (obj instanceof BuildingPropertyInfo) {
					room.setBuildingProperty((BuildingPropertyInfo) obj);
				}
				if (room.getBuildingProperty() == null) {
					errMsg.append("\r\n第" + (i + 1) + "行建筑性质不能为空！");
				}
			}

			if (!row.getCell(COL_HOUSE_PROPERTY).getStyleAttributes().isLocked()) {
				obj = row.getCell(COL_HOUSE_PROPERTY).getValue();
				if (obj instanceof String) {
					HousePropertyEnum housePro = (HousePropertyEnum) houseProEnumMap.get((String) obj);
					room.setHouseProperty(housePro);
					row.getCell(COL_HOUSE_PROPERTY).setValue(housePro);
				} else if (obj instanceof HousePropertyEnum) {
					room.setHouseProperty((HousePropertyEnum) obj);
				}
				if (room.getHouseProperty() == null) {
					errMsg.append("\r\n第" + (i + 1) + "行房产性质不能为空！");
				}
			}

			if (!row.getCell(COL_PRODUCT_TYPE).getStyleAttributes().isLocked()) {
				obj = row.getCell(COL_PRODUCT_TYPE).getValue();
				if (obj instanceof String) {
					ProductTypeInfo proType = (ProductTypeInfo) productTypeMap.get((String) obj);
					room.setProductType(proType);
					row.getCell(COL_PRODUCT_TYPE).setValue(proType);
				} else if (obj instanceof ProductTypeInfo) {
					room.setProductType((ProductTypeInfo) obj);
				}
				if (room.getProductType() == null) {
					errMsg.append("\r\n第" + (i + 1) + "行产品类型不能为空！");
				}
			}

			if (!row.getCell(COL_PRODUCT_DETAIL).getStyleAttributes().isLocked()) {
				obj = row.getCell(COL_PRODUCT_DETAIL).getValue();
				if (obj instanceof String) {
					ProductDetialInfo productDetial = (ProductDetialInfo) productDetailMap.get((String) obj);
					if (productDetial != null) {
						// 产品描述项目隔离了，如果此产品描述不在此项目内，则不予导入
						if (!sellProject.getId().equals(productDetial.getSellProject().getId())) {
							row.getCell(COL_PRODUCT_DETAIL).setValue(null);
							room.setProductDetail(null);
						} else {
							room.setProductDetail(productDetial);
						}
					}
				} else if (obj instanceof ProductDetialInfo) {
					room.setProductDetail((ProductDetialInfo) obj);
				}
			}

			//
			boolean existOnePro = false;
			obj = row.getCell(COL_IS_FOR_SHE).getValue();
			if (obj instanceof String && "是".equals(obj)) {
				existOnePro = true;
				room.setIsForSHE(true);
			} else {
				room.setIsForSHE(false);
			}
			obj = row.getCell(COL_IS_FOR_TEN).getValue();
			if (obj instanceof String && "是".equals(obj)) {
				existOnePro = true;
				room.setIsForTen(true);
			} else {
				room.setIsForTen(false);
			}
			obj = row.getCell(COL_IS_FOR_PPM).getValue();
			if (obj instanceof String && "是".equals(obj)) {
				existOnePro = true;
				room.setIsForPPM(true);
			} else {
				room.setIsForPPM(false);
			}
			if (!existOnePro) {
				errMsg.append("\r\n第" + (i + 1) + "行售楼属性，租赁属性，物业属性至少要选择一样！");
			}

			if (!sellProject.isIsForSHE() && room.isIsForSHE()) {
				errMsg.append("\r\n项目没有售楼属性，第" + (i + 1) + "行不能有售楼属性！");
			}
			if (!sellProject.isIsForTen() && room.isIsForTen()) {
				errMsg.append("\r\n项目没有租赁属性，第" + (i + 1) + "行不能有租赁属性！");
			}
			if (!sellProject.isIsForPPM() && room.isIsForPPM()) {
				errMsg.append("\r\n项目没有物业属性，第" + (i + 1) + "行不能有物业属性！");
			}
			//

//			Object o = (String) row.getCell(COL_ROOMNO).getValue();
//			if (o instanceof String && o.toString().length() > 0) {
//				String roomNo = (String) o;
//				room.setRoomNo(roomNo);
//			}
//
//			Object ooo = (String) row.getCell(COL_ROOMPROPNO).getValue();
//			if (ooo instanceof String && ooo.toString().length() > 0) {
//				String roomPropNo = (String) ooo;
//
//				room.setRoomPropNo(roomPropNo);
//			}

//			if (building.getId() != null) {
//				if (!row.getCell(COL_ROOMNO).getStyleAttributes().isLocked()) {
//					Object oo = (String) row.getCell(COL_ROOMNO).getValue();
//					if (oo != null && oo.toString().length() > 0) {
//						if (existRoomNoMap.containsKey(building.getId().toString())) {
//							Set set = (HashSet) existRoomNoMap.get(building.getId().toString());
//							if (set.contains(oo.toString())) {
//								if (!oo.toString().equalsIgnoreCase(room.getRoomNo()))
//									errMsg.append("\r\n第" + (i + 1) + "行的实测房号在数据库中已经存在！");
//							}
//						}
//					}
//				}

//				if (!row.getCell(COL_ROOMPROPNO).getStyleAttributes().isLocked()) {
//					Object oo = (String) row.getCell(COL_ROOMPROPNO).getValue();
//					if (oo != null && oo.toString().length() > 0) {
//						if (existRoomPropNoMap.containsKey(building.getId().toString())) {
//							Set set = (HashSet) existRoomPropNoMap.get(building.getId().toString());
//							if (set.contains(oo.toString())) {
//								if (!oo.toString().equalsIgnoreCase(room.getRoomPropNo()))
//									errMsg.append("\r\n第" + (i + 1) + "行的物业房号在数据库中已经存在！");
//							}
//						}
//					}
//				}
//			}

			/*
			 * //物业房号 luxiaoling 2009-12-7
			 * if(!BuildRoomPropNoExistMap.containsKey
			 * (building.getId().toString())){ EntityViewInfo roomView = new
			 * EntityViewInfo(); FilterInfo roomFilter = new FilterInfo();
			 * roomFilter.getFilterItems().add(new FilterItemInfo("building.id",
			 * building.getId().toString())); roomView.setFilter(roomFilter);
			 * 
			 * roomView.getSelector().add("buildUnit.name");
			 * roomView.getSelector().add("floor");
			 * roomView.getSelector().add("roomPropNo");
			 * 
			 * RoomCollection buildRooms =
			 * RoomFactory.getRemoteInstance().getRoomCollection(roomView);
			 * if(buildRooms.isEmpty()){
			 * BuildRoomPropNoExistMap.put(building.getId().toString(), new
			 * HashMap()); }else{ for(int j=0; j<buildRooms.size(); j++){
			 * RoomInfo buildRoom = buildRooms.get(j); String tempUnitName = "";
			 * if(buildRoom.getBuildUnit()!=null) tempUnitName =
			 * buildRoom.getBuildUnit().getName(); Set set = new HashSet();
			 * set.add(buildRoom.getRoomPropNo());
			 * BuildRoomPropNoExistMap.put(building.getId().toString(), set); }
			 * } }
			 */
//			 if (!row.getCell(COL_ROOMPROPNO).getStyleAttributes().isLocked())
//			{
//				Object o1 = (String) row.getCell(COL_ROOMPROPNO).getValue();
//				String roomPropNo = (String) o1;
//				/*if (o1 instanceof String && o1.toString().length() > 0)
//				{
//					String roomPropNo = (String) o1;
//					Set set = (Set) BuildRoomPropNoExistMap.get(building
//							.getId().toString());
//					if (!set.add(roomPropNo))
//					{
//						errMsg.append("\r\n第" + (i + 1) + "行物业房号已存在！");
//						continue;
//					}
//					room.setRoomPropNo(roomPropNo);
//				}*/
////				if (roomPropNo == null || roomPropNo.trim().length() < 1)
////				{
////					errMsg.append("\r\n第" + (i + 1) + "行物业房号不能为空！");
////				}
//			}
			 

			rooms.add(SHEHelper.getLongCodeRoom(room));
			
		}

		// 如果有错误信息，提示错误，不进行入库操作
		if (!errMsg.toString().trim().equals("")) {
			MsgBox.showInfo(this, errMsg.toString());
			return;
		}

		if (rooms.isEmpty()) {
			MsgBox.showInfo(this, "没有需要导入的数据！");
			return;
		}

	
			RoomFactory.getRemoteInstance().submit(rooms);
		
		CacheServiceFactory.getInstance().discardType(new RoomInfo().getBOSType());
		MsgBox.showInfo(this, "导入成功！");
        //释放缓存，防止内存溢出 yaoweiwen 2009-12-25
		//start
		roomModelMap.clear();
		roomModelMap = null;
		
		buildUnitMap.clear();
		buildUnitMap = null;	
		 
		directionMap.clear();
		directionMap = null;
		
		sightMap.clear();
		sightMap = null;

		roomFormMap.clear();
		roomFormMap = null;
		
		buildingProMap.clear();
		buildingProMap = null;
		
		houseProEnumMap.clear();
		houseProEnumMap = null;

		productTypeMap.clear();
		productTypeMap = null;
		
		productDetailMap.clear();
		productDetailMap = null;

		floorMap.clear();
		floorMap = null;	
		
		BuildRoomPropNoExistMap.clear();
		BuildRoomPropNoExistMap = null;
		
//		existRoomNoMap.clear();
//		existRoomNoMap = null;
//		
//		existRoomPropNoMap.clear();
//		existRoomPropNoMap = null;
		
		roomUsageMap.clear();
		roomUsageMap = null;	
		
		noiseMap.clear();
		noiseMap = null;
		
		eyeSightMap.clear();
		eyeSightMap = null;
		
		possessionStdMap.clear();
		possessionStdMap = null;
		
		decorationMap.clear();
		decorationMap = null;
		//end

		this.tblMain.removeRows();
	}

	private Map getProductDetailMap() throws BOSException {
		Map productDetailMap = new HashMap();
		ProductDetialCollection productDetials = ProductDetialFactory.getRemoteInstance().getProductDetialCollection();
		for (int i = 0; i < productDetials.size(); i++) {
			ProductDetialInfo productDetial = productDetials.get(i);
			productDetailMap.put(productDetial.getName().trim(), productDetial);
		}
		return productDetailMap;
	}

	private Map getProductTypeMap() throws BOSException {
		Map productTypeMap = new HashMap();
		ProductTypeCollection productTypes = ProductTypeFactory.getRemoteInstance().getProductTypeCollection();
		for (int i = 0; i < productTypes.size(); i++) {
			ProductTypeInfo productType = productTypes.get(i);
			productTypeMap.put(productType.getName().trim(), productType);
		}
		return productTypeMap;
	}

	private Map getBuildingProMap() throws BOSException {
		Map buildingProMap = new HashMap();
		BuildingPropertyCollection buildingPros = BuildingPropertyFactory.getRemoteInstance().getBuildingPropertyCollection();
		for (int i = 0; i < buildingPros.size(); i++) {
			BuildingPropertyInfo buildingPro = buildingPros.get(i);
			buildingProMap.put(buildingPro.getName().trim(), buildingPro);
		}
		return buildingProMap;
	}

	private Map getRoomFormMap() throws BOSException {
		Map roomFormMap = new HashMap();
		RoomFormCollection roomForms = RoomFormFactory.getRemoteInstance().getRoomFormCollection();
		for (int i = 0; i < roomForms.size(); i++) {
			RoomFormInfo roomForm = roomForms.get(i);
			roomFormMap.put(roomForm.getName().trim(), roomForm);
		}
		return roomFormMap;
	}

	private Map getSightMap() throws BOSException {
		Map sightMap = new HashMap();
		SightRequirementCollection sights = SightRequirementFactory.getRemoteInstance().getSightRequirementCollection();
		for (int i = 0; i < sights.size(); i++) {
			SightRequirementInfo sight = sights.get(i);
			sightMap.put(sight.getName().trim(), sight);
		}
		return sightMap;
	}

	/**
	 * 获得楼栋的楼层分录
	 * 
	 * @return
	 * @throws BOSException
	 */
	private Map getBuildingFloorMap() throws BOSException {
		Map floorMap = new HashMap();
		BuildingFloorEntryCollection coll = BuildingFloorEntryFactory.getRemoteInstance().getBuildingFloorEntryCollection();

		for (int i = 0; i < coll.size(); i++) {
			BuildingFloorEntryInfo info = coll.get(i);
			if (info == null || info.getBuilding() == null || info.getBuilding().getId() == null)
				continue;

			String buildingId = info.getBuilding().getId().toString();

			if (floorMap.containsKey(buildingId)) {
				Map map = (Map) floorMap.get(buildingId);

				map.put(new Integer(info.getFloor()), info);
			} else {
				Map map = new HashMap();

				map.put(new Integer(info.getFloor()), info);

				floorMap.put(buildingId, map);
			}
		}
		return floorMap;
	}

//	/**
//	 * 获取已经存在的实测房号
//	 * 
//	 * @return
//	 * @throws BOSException
//	 */
//	private Map getExistRoomNoMap() {
//		Map roomNoMap = new HashMap();
//		SellProjectInfo project = (SellProjectInfo) this.f7SellProject.getValue();
//		if (project == null)
//			return roomNoMap;
//
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);
//
//		view.getSelector().add("id");
//		view.getSelector().add("building.id");
//		view.getSelector().add("roomPropNo");
//		view.getSelector().add("roomNo");
//		filter.getFilterItems().add(new FilterItemInfo("building.sellProject", project.getId().toString()));
//
//		RoomCollection coll = null;
//		try {
//			coll = RoomFactory.getRemoteInstance().getRoomCollection(view);
//		} catch (BOSException e) {
//			e.printStackTrace();
//			return roomNoMap;
//		}
//
//		for (int i = 0; i < coll.size(); i++) {
//			RoomInfo info = coll.get(i);
//			if (info == null || info.getBuilding() == null || info.getBuilding().getId() == null)
//				continue;
//
//			String buildingId = info.getBuilding().getId().toString();
//
//			if (roomNoMap.containsKey(buildingId)) {
//				Set set = (Set) roomNoMap.get(buildingId);
//				set.add(info.getRoomNo());
//			} else {
//				Set set = new HashSet();
//				set.add(info.getRoomNo());
//
//				roomNoMap.put(buildingId, set);
//			}
//		}
//		return roomNoMap;
//
//	}

//	/**
//	 * 获取已经存在的物业房号
//	 * 
//	 * @return
//	 * @throws BOSException
//	 */
//	private Map getExistRoomPropNoMap() {
//		Map roomPropNoMap = new HashMap();
//		SellProjectInfo project = (SellProjectInfo) this.f7SellProject.getValue();
//		if (project == null)
//			return roomPropNoMap;
//
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);
//
//		view.getSelector().add("id");
//		view.getSelector().add("building.id");
//		view.getSelector().add("roomPropNo");
//		view.getSelector().add("roomNo");
//		filter.getFilterItems().add(new FilterItemInfo("building.sellProject", project.getId().toString()));
//
//		RoomCollection coll = null;
//		try {
//			coll = RoomFactory.getRemoteInstance().getRoomCollection(view);
//		} catch (BOSException e) {
//			e.printStackTrace();
//			return roomPropNoMap;
//		}
//
//		for (int i = 0; i < coll.size(); i++) {
//			RoomInfo info = coll.get(i);
//			if (info == null || info.getBuilding() == null || info.getBuilding().getId() == null)
//				continue;
//
//			String buildingId = info.getBuilding().getId().toString();
//
//			if (roomPropNoMap.containsKey(buildingId)) {
//				Set set = (Set) roomPropNoMap.get(buildingId);
//				set.add(info.getRoomPropNo());
//			} else {
//				Set set = new HashSet();
//				set.add(info.getRoomPropNo());
//
//				roomPropNoMap.put(buildingId, set);
//			}
//		}
//		return roomPropNoMap;
//
//	}

	private Map getDirectionMap() throws BOSException {
		Map directionMap = new HashMap();
		HopedDirectionCollection directions = HopedDirectionFactory.getRemoteInstance().getHopedDirectionCollection();
		for (int i = 0; i < directions.size(); i++) {
			HopedDirectionInfo direction = directions.get(i);
			directionMap.put(direction.getName().trim(), direction);
		}
		return directionMap;
	}

	private Map getRoomUsageMap() throws BOSException {
		Map roomUsageMap = new HashMap();
		RoomUsageCollection roomUsageCol = RoomUsageFactory.getRemoteInstance().getRoomUsageCollection();
		for (int i = 0; i < roomUsageCol.size(); i++) {
			RoomUsageInfo roomUsage = roomUsageCol.get(i);
			roomUsageMap.put(roomUsage.getName().trim(), roomUsage);
		}
		return roomUsageMap;
	}

	private Map getNoiseMap() throws BOSException {
		Map noiseMap = new HashMap();
		NoiseCollection noiseCol = NoiseFactory.getRemoteInstance().getNoiseCollection();
		for (int i = 0; i < noiseCol.size(); i++) {
			NoiseInfo noise = noiseCol.get(i);
			noiseMap.put(noise.getName().trim(), noise);
		}
		return noiseMap;
	}

	private Map getEyeSightMap() throws BOSException {
		Map eyeSightMap = new HashMap();
		EyeSignhtCollection eyeSightCol = EyeSignhtFactory.getRemoteInstance().getEyeSignhtCollection();
		for (int i = 0; i < eyeSightCol.size(); i++) {
			EyeSignhtInfo eyeSight = eyeSightCol.get(i);
			eyeSightMap.put(eyeSight.getName().trim(), eyeSight);
		}
		return eyeSightMap;
	}

	private Map getDecorationSTDMap() throws BOSException {
		Map decorationSTDMap = new HashMap();
		DecorationStandardCollection decotationSTDCol = DecorationStandardFactory.getRemoteInstance().getDecorationStandardCollection();
		for (int i = 0; i < decotationSTDCol.size(); i++) {
			DecorationStandardInfo decorationSTD = decotationSTDCol.get(i);
			decorationSTDMap.put(decorationSTD.getName().trim(), decorationSTD);
		}
		return decorationSTDMap;
	}

	private Map getPossessionSTDMap() throws BOSException {
		Map possessionMap = new HashMap();
		PossessionStandardCollection possessionSTDCol = PossessionStandardFactory.getRemoteInstance().getPossessionStandardCollection();
		for (int i = 0; i < possessionSTDCol.size(); i++) {
			PossessionStandardInfo possessionStd = possessionSTDCol.get(i);
			possessionMap.put(possessionStd.getName().trim(), possessionStd);
		}
		return possessionMap;
	}

	/**
	 * 户型有点特殊，户型编码名称是楼栋内唯一. 故缓存户型结构为2级Map结构 key(楼栋id)-Map( key(户型名称)-户型对象 )
	 * @author tim_gao
	 * @description 因为加入售楼组织，当为售楼组织用项目ID
	 * */
	private Map getRoomModelMap() throws BOSException {
		Map roomModelMap = new HashMap();
		String setId = ((SellProjectInfo)this.f7SellProject.getValue()).getId().toString();
		RoomModelCollection roomModels = RoomModelFactory.getRemoteInstance().getRoomModelCollection("select * from where sellProject.id='"+setId+"'");
		for (int i = 0; i < roomModels.size(); i++) {
			RoomModelInfo thisInfo = roomModels.get(i);
			if(!FDCSysContext.getInstance().checkIsSHEOrg()){
			
				setId = thisInfo.getBuilding().getId().toString();
			if (roomModelMap.containsKey(setId)) {
				Map map = (Map) roomModelMap.get(setId);
				map.put(thisInfo.getName(), thisInfo);
			} else {
				Map map = new HashMap();
				map.put(thisInfo.getName(), thisInfo);

				roomModelMap.put(setId, map);
			}
			
			
			}else{
				if(null==this.f7SellProject.getValue()){
					FDCMsgBox.showWarning("请选择项目！");
					SysUtil.abort();
				}
				if (roomModelMap.containsKey(setId)) {
					Map map = (Map) roomModelMap.get(setId);
					map.put(thisInfo.getName(), thisInfo);
				} else {
					Map map = new HashMap();
					map.put(thisInfo.getName(), thisInfo);

					roomModelMap.put(setId, map);
				}
			}
			
		}
		return roomModelMap;
	}

	/** 缓存单元为2级Map结构 key(楼栋id)-Map( key(单元名称)-单元对象 ) */
	private Map getBuildInitMap() throws BOSException {
		Map buildUnitMap = new HashMap();
		BuildingUnitCollection buildUnits = BuildingUnitFactory.getRemoteInstance().getBuildingUnitCollection();
		for (int i = 0; i < buildUnits.size(); i++) {
			BuildingUnitInfo thisInfo = buildUnits.get(i);
			String buildingId = thisInfo.getBuilding().getId().toString();

			if (buildUnitMap.containsKey(buildingId)) {
				Map map = (Map) buildUnitMap.get(buildingId);
				map.put(String.valueOf(thisInfo.getName()), thisInfo);
			} else {
				Map map = new HashMap();
				map.put(String.valueOf(thisInfo.getName()), thisInfo);

				buildUnitMap.put(buildingId, map);
			}
		}
		return buildUnitMap;
	}

	private Map getHouseProEnumMap() {
		Map houseProEnumMap = new HashMap();
		List enumlist = HousePropertyEnum.getEnumList();
		for (int i = 0; i < enumlist.size(); i++) {
			HousePropertyEnum houseProEnum = (HousePropertyEnum) enumlist.get(i);
			houseProEnumMap.put(houseProEnum.getAlias().trim(), houseProEnum);
		}
		return houseProEnumMap;
	}

	private void setRoomStringValue(RoomInfo room, IRow row, String colName, String paramKey) {
		ICell cell = row.getCell(colName);
		if (cell.getStyleAttributes().isLocked()) {
			return;
		}
		Object obj = cell.getValue();
		if (obj == null) {
			return;
		}
		if (obj instanceof String) {
			String tmpS = (String) obj;
			room.setString(paramKey, tmpS.trim());
		} else {
			logger.error(colName + " 列类型: " + obj.getClass().getName());
		}
	}

	private void setRoomBigDecimalValue(RoomInfo room, IRow row, String colName, String paramKey) {
		ICell cell = row.getCell(colName);
		if (cell.getStyleAttributes().isLocked()) {
			return;
		}
		Object obj = cell.getValue();
		if (obj == null) {
			return;
		}

		if (obj instanceof String) {
			String tmpS = (String) obj;
			if (tmpS.trim().equals("")) {
				return;
			} else {
				try {
					BigDecimal value = new BigDecimal(tmpS);
					value = value.divide(FDCHelper.ONE, 2, BigDecimal.ROUND_HALF_UP);
					room.setBigDecimal(paramKey, value);
				} catch (NumberFormatException e) {
					// 不做处理.
					return;
				}
			}
		} else if (obj instanceof BigDecimal) {
			BigDecimal value = (BigDecimal) obj;
			room.setBigDecimal(paramKey, value);
		} else {
			logger.error(colName + " 列类型: " + obj.getClass().getName());
		}
	}

	private void distinguishFromTableData() throws BOSException {
		SellProjectInfo sellProject = (SellProjectInfo) this.f7SellProject.getValue();
		if (sellProject == null) {
			this.tblMain.removeRows(false);
			return;
		}

		Map buildingNumExistMap = new HashMap();
		int totalCount = this.tblMain.getRowCount();

		Map roomSeqsMap = new HashMap();

		Map localBuildNumExistMap = new HashMap();
		Map localBuildSeqExistMap = new HashMap();

		Map localBuildRoomNoExistMap = new HashMap();
		Map localBuildRoomPropNoExistMap = new HashMap();

		for (int i = 0; i < totalCount; i++) {
			IRow row = tblMain.getRow(i);
			// by tim_gao
			if( FDCSysContext.getInstance().checkIsSHEOrg()){//售楼系统是售楼属性为true
				row.getCell(COL_IS_FOR_SHE).setValue("是");
				row.getCell(COL_IS_FOR_TEN).setValue("");
				row.getCell(COL_IS_FOR_PPM).setValue("");
			}
			Object building = row.getCell(COL_BUILDING_NUMBER).getValue();
			if (FDCHelper.isEmpty(building)) {
				row.getCell(COL_IMPORT_STATUS).setValue(INVALID_BUILD_NUMBER);
				continue;
			}

			String buildingNum = null;
			BuildingInfo buildingInfo = null;

			if (building instanceof String) {
				buildingNum = (String) building;
			} else if (building instanceof BuildingInfo) {
				buildingNum = ((BuildingInfo) building).getNumber();
			}
			// --户型过滤
			EntityViewInfo tmpRoomModelView = new EntityViewInfo();
			FilterInfo tmpRoomModelFilter = new FilterInfo();
			tmpRoomModelView.setFilter(tmpRoomModelFilter);
//			tmpRoomModelFilter.getFilterItems().add(new FilterItemInfo("building.id", buildingInfo.getId().toString()));
			ICell cell = row.getCell(COL_ROOM_MODEL);
			if(isSellHouse){
				cell.setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.RoomModelForSHEQuery", SHEHelper.getModelBySellProjectView(sellProject)));
			}else{
				cell.setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.RoomModelForSHEQuery", tmpRoomModelView));
			}
			// 存在该楼栋的编号说明该编码的楼栋之前进行过验证是否存在
			if (buildingNumExistMap.keySet().contains(buildingNum)) {
				Object obj = buildingNumExistMap.get(buildingNum);
				// 为空则表示该编号的楼栋在表中不存在
				if (obj == null) {
					row.getCell(COL_IMPORT_STATUS).setValue(INVALID_BUILD_NUMBER);
					continue;
				} else {
					buildingInfo = (BuildingInfo) obj;
				}
			} else {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("number", buildingNum));
				view.setFilter(filter);
				view.getSelector().add("*");
				view.getSelector().add("sellProject.name");
				view.getSelector().add("sellProject.number");
				view.getSelector().add("subArea.name");
				view.getSelector().add("units.*");

				BuildingCollection tmpBuildings = BuildingFactory.getRemoteInstance().getBuildingCollection(view);
				if (tmpBuildings == null || tmpBuildings.isEmpty()) {
					buildingNumExistMap.put(buildingNum, null);
					row.getCell(COL_IMPORT_STATUS).setValue(INVALID_BUILD_NUMBER);
					continue;
				} else {
					buildingInfo = tmpBuildings.get(0);
					buildingNumExistMap.put(buildingNum, buildingInfo);
				}
			}

			String roomNumber = (String) row.getCell(COL_ROOM_NUMBER).getValue();
			if (FDCHelper.isEmpty(roomNumber)) {
				row.getCell(COL_IMPORT_STATUS).setValue(INVALID_ROOM_NUMBER);// 编码为空
				// ,
				// 也算不能识别
				continue;
			}

			// 在要导入的房间列表中验证房间编码重复的
			String unitNumber = null;
			Object unitnb = row.getCell(COL_UNIT).getValue();
			if(unitnb instanceof String){
				unitNumber =(String) unitnb;
			}else if(unitnb instanceof BuildingUnitInfo){
				unitNumber = ((BuildingUnitInfo)unitnb).getNumber();
			}
			String roomLongNumber1 = unitNumber+roomNumber;
			if (localBuildNumExistMap.get(buildingInfo.getId().toString()) == null) {
				Set set = new HashSet();
				set.add(roomLongNumber1);
				localBuildNumExistMap.put(buildingInfo.getId().toString(), set);
			} else {
				Set set = (Set) localBuildNumExistMap.get(buildingInfo.getId().toString());
				if (!set.add(roomLongNumber1)) {
					row.getCell(COL_IMPORT_STATUS).setValue("房源编码与上面准备导入的房源编码重复！");
//					row.getCell(COL_IMPORT_STATUS).setValue("预测房号与上面准备导入的预测房号重复！");
					continue;
				}
			}

//			// 实测房号
//			String roomNo = (String) row.getCell(COL_ROOMNO).getValue();
//			String roomLongNumber2 = unitNumber + roomNo;
//			if (roomNo != null && roomNo.length() > 0) {
//				if (localBuildRoomNoExistMap.get(buildingInfo.getId().toString()) == null) {
//					Set set = new HashSet();
//					set.add(roomLongNumber2);
//					localBuildRoomNoExistMap.put(buildingInfo.getId().toString(), set);
//				} else {
//					Set set = (Set) localBuildRoomNoExistMap.get(buildingInfo.getId().toString());
//					if (!set.add(roomLongNumber2)) {
//						row.getCell(COL_IMPORT_STATUS).setValue("实测房号与上面准备导入的实测房号重复！");
//						continue;
//					}
//				}
//			}

//			// 物业房号
//			String roomPropNo = (String) row.getCell(COL_ROOMPROPNO).getValue();
//			String roomLongNumber3 = unitNumber + roomPropNo;
//			if (roomPropNo != null && roomPropNo.length() > 0) {
//				if (localBuildRoomPropNoExistMap.get(buildingInfo.getId().toString()) == null) {
//					Set set = new HashSet();
//					set.add(roomLongNumber3);
//					localBuildRoomPropNoExistMap.put(buildingInfo.getId().toString(), set);
//				} else {
//					Set set = (Set) localBuildRoomPropNoExistMap.get(buildingInfo.getId().toString());
//					if (!set.add(roomLongNumber3)) {
//						row.getCell(COL_IMPORT_STATUS).setValue("物业房号与上面准备导入的物业房号重复！");
//						continue;
//					}
//				}
//			}

			RoomInfo room = new RoomInfo();
			room.setBuilding(buildingInfo);
//			room.setNumber(roomNumber);
//			room.setName(roomNumber);
//			room.setRoomNo(roomNo);
//			room.setRoomPropNo(roomPropNo);
			room.setRoomNo(roomNumber);
			room.setRoomPropNo(roomNumber);
			room.setSellState(RoomSellStateEnum.Init);

			SellProjectInfo sellPro = (SellProjectInfo) this.f7SellProject.getValue();
			String longName = "";
			if(sellPro!=null) longName += sellPro.getName();
			if(buildingInfo.getSubarea()!=null) longName += "-" + buildingInfo.getSubarea().getName();
			if(buildingInfo!=null) longName += "-" + buildingInfo.getName();
			if(room.getBuildUnit()!=null) longName += "-" + room.getBuildUnit().getName(); 
			room.setDisplayName(roomNumber);				
			room.setName(longName + "-" + roomNumber);
			room.setNumber(longName + "-" + roomNumber);
		
			
			// --单元过滤
			EntityViewInfo tmpUnitView = new EntityViewInfo();
			FilterInfo tmpUnitFilter = new FilterInfo();
			tmpUnitView.setFilter(tmpUnitFilter);
			tmpUnitFilter.getFilterItems().add(new FilterItemInfo("building.id", buildingInfo.getId().toString()));

			ICell unitCell = row.getCell(COL_UNIT);
			unitCell.setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.BuildingUnitQuery", tmpUnitView));
			//

			int unitCount = buildingInfo.getUnitCount();
			String unitName = ""; // 单元名称
			BuildingUnitInfo unit =null;
			if (!row.getCell(COL_UNIT).getStyleAttributes().isLocked()) {
				Object obj = row.getCell(COL_UNIT).getValue();
//				if (obj == null && unitCount != 0) {
//					row.getCell(COL_IMPORT_STATUS).setValue(INVALID_UNIT);
//					continue;
//				}
				// 这里其实要对单元的有效性做检验
					if (obj instanceof String) {
						if(null!=obj&&!(("").equals(obj.toString()))){
							BuildingUnitCollection units = buildingInfo.getUnits();
							boolean isValidUnit = false;// 该单元名称是否是有效的
							for (int j = 0; j < units.size(); j++) {
								unit = units.get(j);
								if (obj.equals(unit.getName())) {
									isValidUnit = true;
									break;
								}
							}
							if (!isValidUnit) {
								row.getCell(COL_IMPORT_STATUS).setValue(INVALID_UNIT);
								continue;
							}

							unitName = (String) obj;
						}
					
				}
				else if (obj instanceof BuildingUnitInfo) {
					BuildingUnitCollection units = buildingInfo.getUnits();
					boolean isValidUnit = false;// 该单元名称是否是有效的
					for (int j = 0; j < units.size(); j++) {
						unit = units.get(j);
						if (((BuildingUnitInfo) obj).getId().toString().equals(unit.getId().toString())) {
							isValidUnit = true;
							break;
						}
					}
					if (!isValidUnit) {
						row.getCell(COL_IMPORT_STATUS).setValue(INVALID_UNIT);
						continue;
					}

					unitName = ((BuildingUnitInfo) obj).getName();
				} 
//				else {
					// logger.error("单元列类型: " + obj == null? "" :
					// (obj.getClass().getName()));
					// row.getCell(COL_IMPORT_STATUS).setValue("单元列类型: " /*+
					// obj.getClass().getName()*/);
					// continue;
//				}
			} else {// 为修改时，给unit赋值
//				if (room.getBuildUnit() != null)
//					unitName = room.getBuildUnit().getName();
				if(row.getCell(COL_UNIT).getValue()!=null){
					unitName = ((BuildingUnitInfo)row.getCell(COL_UNIT).getValue()).getName();
					unit = ((BuildingUnitInfo)row.getCell(COL_UNIT).getValue());
				}
			}	
			RoomCollection rooms =null;
			if (unitCount == 0) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("building.id", buildingInfo.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("number", roomNumber));
//				filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", unit.getId()));
				view.setFilter(filter);
				view.getSelector().add("*");
				view.getSelector().add("buildUnit.*");

				rooms = RoomFactory.getRemoteInstance().getRoomCollection(view);
			}else if(unit != null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("building.id", buildingInfo.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("number", roomNumber));
				filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", unit.getId()));
				view.setFilter(filter);
				view.getSelector().add("*");
				view.getSelector().add("buildUnit.*");
				
				rooms = RoomFactory.getRemoteInstance().getRoomCollection(view);
			}else{
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("building.id", buildingInfo.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("number", roomNumber));
				filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", null));
				view.setFilter(filter);
				view.getSelector().add("*");
				view.getSelector().add("buildUnit.*");
				
				rooms = RoomFactory.getRemoteInstance().getRoomCollection(view);
			}
			// 表里不存在该编码的房间，说明为新增导入
			if (rooms.isEmpty()) {
				row.getCell(COL_IMPORT_STATUS).setValue(IMPORT_STATE_ADD);
			} else {
				row.getCell(COL_IMPORT_STATUS).setValue(IMPORT_STATE_EDIT);
				room = rooms.get(0);

				if (RoomJoinStateEnum.JOINED.equals(room.getRoomJoinState())) {
					row.getCell("roomNo").setValue(room.getRoomNo());
					row.getCell("roomNo").getStyleAttributes().setLocked(true);
					row.getCell("roomNo").getStyleAttributes().setBackground(new Color(89,172,255));

					// row.getCell("roomPropNo").setValue(room.getRoomPropNo());
					////row.getCell("roomPropNo").getStyleAttributes().setLocked
					// (true);
					// row.getCell("roomPropNo").getStyleAttributes().
					// setBackground(Color.gray);
				}

				// 单元,新增数据行可以修改,修改数据行锁定,只显示数据库中的单元,即修改数据不能修改单元
				// ?楼层,序号, 同单元
				row.getCell(COL_UNIT).setValue(room.getBuildUnit());
				row.getCell(COL_FLOOR).setValue(new Integer(room.getFloor()));
				row.getCell(COL_SEQ).setValue(new Integer(room.getSerialNumber()));
				//当为空是不是毕录
				if(null!=row.getCell(COL_UNIT).getValue()){
					setCellNotEdit(row, COL_UNIT);
				}
				
				setCellNotEdit(row, COL_FLOOR);
				setCellNotEdit(row, COL_SEQ);

				// ?建筑面积,套内面积,公摊面积,户型,
				// 新增数据行自由录入或导入,修改数据行判断是否进行了售前复核,未复核可以自由录入或导入
				if (room.isIsAreaAudited()) {
					row.getCell(COL_BUILDING_AREA).setValue(room.getBuildingArea());
					row.getCell(COL_ROOM_AREA).setValue(room.getRoomArea());
					row.getCell(COL_APPORTION_AREA).setValue(room.getApportionArea());
					row.getCell(COL_ROOM_MODEL).setValue(room.getRoomModel());

					setCellNotEdit(row, COL_BUILDING_AREA);
					setCellNotEdit(row, COL_ROOM_AREA);
					setCellNotEdit(row, COL_APPORTION_AREA);
					setCellNotEdit(row, COL_ROOM_MODEL);
				}

				// ? 建筑实测,套内实测新增数据行自由,修改数据行要判断是否实测复核,未实测复核可以自由录入和导入
				if (room.isIsActualAreaAudited()) {
					row.getCell(COL_ACTUAL_BUILDING_AREA).setValue(room.getActualBuildingArea());
					row.getCell(COL_ACTUAL_ROOM_AREA).setValue(room.getActualRoomArea());

					setCellNotEdit(row, COL_ACTUAL_BUILDING_AREA);
					setCellNotEdit(row, COL_ACTUAL_ROOM_AREA);
				}
			}

			row.setUserObject(room);

			

			int floorCount = buildingInfo.getFloorCount();
			int subFloorCount = buildingInfo.getSubFloorCount();
			int floor = 0;
			if (!row.getCell(COL_FLOOR).getStyleAttributes().isLocked()) {
				Object obj = row.getCell(COL_FLOOR).getValue();
				if (obj == null) {
					row.getCell(COL_IMPORT_STATUS).setValue(INVALID_FLOOR);
					continue;
				} else {
					if (obj instanceof String) {
						String tmpS = (String) obj;
						try {
							floor = Integer.parseInt(tmpS);
						} catch (Exception e) {
							row.getCell(COL_IMPORT_STATUS).setValue(INVALID_FLOOR);
							continue;
						}
//						if (floor<subFloorCount || floor > floorCount) {
//							row.getCell(COL_IMPORT_STATUS).setValue(INVALID_FLOOR);
//							continue;
//						}
//						if (floor <= 0 || floor > floorCount) {
//							row.getCell(COL_IMPORT_STATUS).setValue(INVALID_FLOOR);
//							continue;
//						}
					} else if (obj instanceof Integer) {
						floor = ((Integer) obj).intValue();
//						if (floor <= 0 || floor > floorCount) {
//							row.getCell(COL_IMPORT_STATUS).setValue(INVALID_FLOOR);
//							continue;
//						}
					} else {
						logger.error("楼层列类型: " + obj.getClass().getName());
						row.getCell(COL_IMPORT_STATUS).setValue("楼层列类型: " + obj.getClass().getName());
						continue;
					}
				}
			} else {
				floor = room.getFloor();
			}

			// 对序号进行缓存处理,以排除序号重复的问题
			int seq = 0;
			if (!row.getCell(COL_SEQ).getStyleAttributes().isLocked()) {
				Object obj = row.getCell(COL_SEQ).getValue();
				if (obj == null) {
					row.getCell(COL_IMPORT_STATUS).setValue(INVALID_SEQ);//序号为空,
					// 也算不能识别
					continue;
				} else {
					if (obj instanceof String) {
						String tmpS = (String) obj;
						try {
							seq = Integer.parseInt(tmpS);
						} catch (Exception e) {
							row.getCell(COL_IMPORT_STATUS).setValue(INVALID_SEQ);
							continue;
						}
//						if (seq <= 0) {
//							row.getCell(COL_IMPORT_STATUS).setValue(INVALID_SEQ);
//							continue;
//						}
					} else if (obj instanceof Integer) {
						seq = ((Integer) obj).intValue();
//						if (seq <= 0) {
//							row.getCell(COL_IMPORT_STATUS).setValue(INVALID_SEQ);
//							continue;
//						}
					} else {
						logger.error("序号列类型: " + obj.getClass().getName());
						row.getCell(COL_IMPORT_STATUS).setValue("序号列类型: " + obj.getClass().getName());
						continue;
					}
				}
			} else {
				seq = room.getSerialNumber();
			}

			if (!roomSeqsMap.containsKey(buildingInfo.getId().toString())) {
				EntityViewInfo roomView = new EntityViewInfo();
				FilterInfo roomFilter = new FilterInfo();
				roomFilter.getFilterItems().add(new FilterItemInfo("building.id", buildingInfo.getId().toString()));
				roomView.setFilter(roomFilter);

				roomView.getSelector().add("buildUnit.name");
				roomView.getSelector().add("floor");
				roomView.getSelector().add("serialNumber");

				RoomCollection buildRooms = RoomFactory.getRemoteInstance().getRoomCollection(roomView);
				if (buildRooms.isEmpty()) {
					roomSeqsMap.put(buildingInfo.getId().toString(), new HashMap());
				} else {
					for (int j = 0; j < buildRooms.size(); j++) {
						RoomInfo buildRoom = buildRooms.get(j);
						String tempUnitName = "";
						if (buildRoom.getBuildUnit() != null)
							tempUnitName = buildRoom.getBuildUnit().getName();
						putSeqToMap(roomSeqsMap, buildingInfo.getId().toString(), tempUnitName, buildRoom.getFloor(), buildRoom.getSerialNumber(), true);
					}
				}
			}

			if (!row.getCell(COL_SEQ).getStyleAttributes().isLocked() && !putSeqToMap(roomSeqsMap, buildingInfo.getId().toString(), unitName, floor, seq, false)) {
				row.getCell(COL_IMPORT_STATUS).setValue(REPEATED_SEQ);
				continue;
			}

			// 在要导入的房间列表中验证房间编码重复的
			if (localBuildSeqExistMap.containsKey(buildingInfo.getId().toString())) {
				Map unitSeqMap = (Map) localBuildSeqExistMap.get(buildingInfo.getId().toString());
				if (unitSeqMap.containsKey(unitName)) {
					Map floorMap = (Map) unitSeqMap.get(unitName);
					if (floorMap.containsKey(new Integer(floor))) {
						Set set = (Set) floorMap.get(new Integer(floor));
						if (!set.add(new Integer(seq))) {
							row.getCell(COL_IMPORT_STATUS).setValue("房间序号与上面准备导入的房间序号重复！");
							continue;
						}
					} else {
						Set set = new HashSet();
						set.add(new Integer(seq));

						floorMap.put(new Integer(floor), set);
					}
				} else {
					Set set = new HashSet();
					set.add(new Integer(seq));

					Map floorMap = new HashMap();
					floorMap.put(new Integer(floor), set);

					unitSeqMap.put(unitName, floorMap);
				}
			} else {
				Set set = new HashSet();
				set.add(new Integer(seq));

				Map floorMap = new HashMap();
				floorMap.put(new Integer(floor), set);

				Map unitSeqMap = new HashMap();
				unitSeqMap.put(unitName, floorMap);

				localBuildSeqExistMap.put(buildingInfo.getId().toString(), unitSeqMap);
			}

			/*
			 * setBigDecimalParam(row, room, "buildingArea", "buildingArea");
			 * setBigDecimalParam(row, room, "roomArea", "roomArea");
			 * setBigDecimalParam(row, room, "apportionArea", "apportionArea");
			 * //roomModel
			 * 
			 * setBigDecimalParam(row, room, "balconyArea", "balconyArea");
			 * setBigDecimalParam(row, room, "guardenArea", "guardenArea");
			 * setBigDecimalParam(row, room, "carbarnArea", "carbarnArea");
			 * setBigDecimalParam(row, room, "useArea", "useArea");
			 * setBigDecimalParam(row, room, "flatArea", "flatArea");
			 * 
			 * setBigDecimalParam(row, room, "actualBuildingArea",
			 * "actualBuildingArea"); setBigDecimalParam(row, room,
			 * "actualRoomArea", "actualRoomArea"); setBigDecimalParam(row,
			 * room, "floorHeight", "floorHeight"); //direction //sight
			 * //roomForm //deliverHouseStandard //fitmentStandard
			 * //buildingProperty //houseProperty //productType
			 */
		}

		buildingNumExistMap.clear();
		buildingNumExistMap = null;
		roomSeqsMap.clear();
		roomSeqsMap = null;

		setCountInfo();
	}

	private void setCountInfo() {
		int totalCount = this.tblMain.getRowCount();
		int addNewCount = 0;
		int editCount = 0;
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			String importStatus = (String) row.getCell(COL_IMPORT_STATUS).getValue();
			if (IMPORT_STATE_ADD.equals(importStatus)) {
				addNewCount++;
			} else if (IMPORT_STATE_EDIT.equals(importStatus)) {
				editCount++;
			}
		}

		// 不能导入的行数
		int relax = totalCount - addNewCount - editCount;

		this.txtTotalCount.setValue(new Integer(totalCount));
		this.txtAddNewCount.setValue(new Integer(addNewCount));
		this.txtEditCount.setValue(new Integer(editCount));
		this.txtCanNotImportCount.setValue(new Integer(relax));
	}

	/**
	 * 将房间的序列号放入序列号缓存中 如果原有Map中存在重复的sql，则返回false
	 * 
	 * @param isPut
	 *            是否将当前判断的纪录放入map
	 * */
	private boolean putSeqToMap(Map roomSeqsMap, String buildingId, String unitName, int floor, int seq, boolean isPut) {
		if (roomSeqsMap.containsKey(buildingId)) {
			Map unitMap = (Map) roomSeqsMap.get(buildingId);
			if (unitMap.containsKey(unitName)) {
				Map floorMap = (Map) unitMap.get(unitName);
				if (floorMap.containsKey(new Integer(floor))) {
					Set seqSet = (Set) floorMap.get(new Integer(floor));
					if (isPut) {
						seqSet.add(new Integer(seq));
					} else {
						return !seqSet.contains(new Integer(seq));
					}
				} else {
					if (isPut) {
						Set seqSet = new HashSet();
						seqSet.add(new Integer(seq));
						floorMap.put(new Integer(floor), seqSet);
					}
				}
			} else {
				if (isPut) {
					Set seqSet = new HashSet();
					seqSet.add(new Integer(seq));
					Map floorMap = new HashMap();
					floorMap.put(new Integer(floor), seqSet);
					unitMap.put(unitName, floorMap);
				}
			}
		} else {
			if (isPut) {
				Set seqSet = new HashSet();
				seqSet.add(new Integer(seq));
				Map floorMap = new HashMap();
				floorMap.put(new Integer(floor), seqSet);

				Map unitMap = new HashMap();
				unitMap.put(unitName, floorMap);

				roomSeqsMap.put(buildingId, unitMap);
			}
		}
		return true;
	}

	private boolean putPropNoToMap(Map roomPropNoMap, String buildingId, String unitName, int floor, int seq, boolean isPut) {
		if (roomPropNoMap.containsKey(buildingId)) {
			Map unitMap = (Map) roomPropNoMap.get(buildingId);
			if (unitMap.containsKey(unitName)) {
				Map floorMap = (Map) unitMap.get(unitName);
				if (floorMap.containsKey(new Integer(floor))) {
					Set propNoSet = (Set) floorMap.get(new Integer(floor));
					if (isPut) {
						propNoSet.add(new Integer(seq));
					} else {
						return !propNoSet.contains(new Integer(seq));
					}
				} else {
					if (isPut) {
						Set propNoSet = new HashSet();
						propNoSet.add(new Integer(seq));
						floorMap.put(new Integer(floor), propNoSet);
					}
				}
			} else {
				if (isPut) {
					Set propNoSet = new HashSet();
					propNoSet.add(new Integer(seq));
					Map floorMap = new HashMap();
					floorMap.put(new Integer(floor), propNoSet);
					unitMap.put(unitName, floorMap);
				}
			}
		} else {
			if (isPut) {
				Set propNoSet = new HashSet();
				propNoSet.add(new Integer(seq));
				Map floorMap = new HashMap();
				floorMap.put(new Integer(floor), propNoSet);

				Map unitMap = new HashMap();
				unitMap.put(unitName, floorMap);

				roomPropNoMap.put(buildingId, unitMap);
			}
		}
		return true;
	}

	protected void btnDelRow_actionPerformed(ActionEvent e) throws Exception {
		super.btnDelRow_actionPerformed(e);
		checkSelected();

		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		/*
		 * IRow row = this.tblMain.getRow(rowIndex);
		 * 
		 * String importState = (String)
		 * row.getCell(COL_IMPORT_STATUS).getValue();
		 * 
		 * if(IMPORT_STATE_ADD.equals(importState)){ Integer count =
		 * this.txtAddNewCount.getIntegerValue();
		 * this.txtAddNewCount.setValue(new Integer(count.intValue() - 1));
		 * }else if(IMPORT_STATE_EDIT.equals(importState)){ Integer count =
		 * this.txtEditCount.getIntegerValue(); this.txtEditCount.setValue(new
		 * Integer(count.intValue() - 1)); }else{
		 * logger.error("btnDelRow_actionPerformed 逻辑错误。"); }
		 * 
		 * Integer totalCount = this.txtTotalCount.getIntegerValue();
		 * this.txtTotalCount.setValue(new Integer(totalCount.intValue() - 1));
		 */
		this.tblMain.removeRow(rowIndex);
		setCountInfo();
	}

	/**
	 * 把excel中的数据导入到table中 默认表头就一行 都以字符串读取
	 * 
	 * @param fileName
	 * @param table
	 * @return
	 * @throws Exception
	 */
	private int importExcelToTable(String fileName, KDTable table) throws Exception {
		KDSBook kdsbook = null;
		try {
			kdsbook = POIXlsReader.parse2(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			MsgBox.showError("读EXCEL出错,EXCEl格式不匹配!");
			SysUtil.abort();
		}
		if (kdsbook == null) {
			SysUtil.abort();
		}
		// 只读区excel中sheet0的数据
		Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(0);
		Map e_colNameMap = new HashMap(); // excel中的列名
		int e_maxRow = excelSheet.getMaxRowIndex();
		int e_maxColumn = excelSheet.getMaxColIndex();
		for (int col = 0; col <= e_maxColumn; col++) {
			String excelColName = excelSheet.getCell(0, col, true).getText();
			e_colNameMap.put(excelColName, new Integer(col));
		}

		for (int i = 0; i < table.getColumnCount(); i++) { // 检查table中的字段是否都在excel中存在
			if (table.getColumn(i).getStyleAttributes().isHided()) {
				continue;
			}
			String colName = (String) table.getHeadRow(0).getCell(i).getValue();
			if ("ID".equals(colName)) {
				continue;
			}
			Integer colInt = (Integer) e_colNameMap.get(colName);
			if (colInt == null) {
				MsgBox.showError("表头结构不一致!表格上的关键列:" + colName + "在EXCEL中没有出现!");
				SysUtil.abort();
			}
		}

		int successCount = 0;
		for (int rowIndex = 1; rowIndex <= e_maxRow; rowIndex++) {
			IRow row = table.addRow();
			row.setUserObject(new RoomInfo());
			// table.setRowCount(table.getRowCount()+1);
			// row.getStyleAttributes().setLocked(false);

			successCount++;
			for (int i = 0; i < table.getColumnCount(); i++) {
				if (table.getColumn(i).getStyleAttributes().isHided()) {
					continue;
				}
				// if (table.getColumn(i).getStyleAttributes().isLocked()) {
				// continue;
				// }

				ICell tblCell = row.getCell(i);
				String colName = (String) table.getHeadRow(0).getCell(i).getValue();
				Integer colInt = (Integer) e_colNameMap.get(colName);

				if (colInt == null) {
					continue;
				}
				Variant cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
				if (Variant.isNull(cellRawVal)) {
					continue;
				}

				// 修改导入的 001的楼栋编码变为 1的问题 BT337390
				String colValue = cellRawVal.toString().trim();
				if (!colValue.equals(""))
					tblCell.setValue(colValue);
				/*
				 * if (cellRawVal.isNumeric()) { BigDecimal colValue =
				 * cellRawVal.toBigDecimal();
				 * tblCell.setValue(colValue.toString().trim()); } else if
				 * (cellRawVal.isString()) { String colValue =
				 * cellRawVal.toString().trim(); if(!colValue.equals(""))
				 * tblCell.setValue(colValue); }
				 */
			}
		}
		return successCount;
	}

	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		super.tblMain_editStopped(e);
		int colIndex = e.getColIndex();
		IColumn col = this.tblMain.getColumn(colIndex);
		if (col == null) {
			return;
		}
		String colKey = col.getKey();
		if (colKey != null && (colKey.equals(COL_ROOMNO)||colKey.equals(COL_ROOMPROPNO)||colKey.equals(COL_UNIT) || colKey.equals(COL_FLOOR) || colKey.equals(COL_SEQ)) ) {
//		if (colKey != null && (colKey.equals(COL_UNIT) || colKey.equals(COL_FLOOR) || colKey.equals(COL_SEQ))) {
			if (e.getValue() == null) {
				if (e.getOldValue() == null) {
					return;
				}
			} else {
				if (e.getOldValue() != null && e.getValue().toString().equals(e.getOldValue().toString())) {
					return;
				}
			}
			distinguishFromTableData();
		}
	}

	protected void f7SellProject_dataChanged(DataChangeEvent e) throws Exception {
		distinguishFromTableData();
	}

	public int getRowCountFromDB(){
		return -1;
	}
}