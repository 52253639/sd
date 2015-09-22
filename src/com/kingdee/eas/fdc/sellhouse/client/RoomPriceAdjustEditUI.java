/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.exception.AlreadyBindProcessInstanceException;
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.client.AttachmentEditUI;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.MyKDCommonPromptDialog;
import com.kingdee.eas.fdc.basedata.BanInformationEntrysNewInfo;
import com.kingdee.eas.fdc.basedata.BanInformationFactory;
import com.kingdee.eas.fdc.basedata.BanInformationInfo;
import com.kingdee.eas.fdc.basedata.CalculateTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.HouseTypeFactory;
import com.kingdee.eas.fdc.basedata.IndexVersionInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeDateEntryInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeDateFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeDateInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseFactory;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectCollection;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectFactory;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectInfo;
import com.kingdee.eas.fdc.basedata.SchedulePlanEntryInfo;
import com.kingdee.eas.fdc.basedata.SchedulePlanFactory;
import com.kingdee.eas.fdc.basedata.SchedulePlanInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.RoomPriceManagePrintProvider;
import com.kingdee.eas.fdc.market.DecorateEnum;
import com.kingdee.eas.fdc.market.ValueInputCollection;
import com.kingdee.eas.fdc.market.ValueInputEntryFactory;
import com.kingdee.eas.fdc.market.ValueInputEntryInfo;
import com.kingdee.eas.fdc.market.ValueInputFactory;
import com.kingdee.eas.fdc.market.client.DynamicTotalValue;
import com.kingdee.eas.fdc.market.client.RoomPriceDynamicTotalValueUI;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListCollection;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListFactory;
import com.kingdee.eas.fdc.sellhouse.BankPaymentInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.CalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CalculateMethodEnum;
import com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBuildingEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBuildingEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceManageFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPriceManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.config.TableListPreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RoomPriceAdjustEditUI extends AbstractRoomPriceAdjustEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomPriceAdjustEditUI.class);
	 private Set shBuildId=null;
	/**
	 * 价格的保留位数和取整方式，按顺序为：单价保留位数和取整方式，总价保留位数和取整方式
	 */
	private int[] paramsArray = new int[4];

	/**
	 * 已选中楼栋 重复选中同一个楼栋，不清空该楼栋页签
	 */
	private HashMap selectedBuildingMap = new HashMap();
	

	/**
	 * 标示更新总览页签的数据
	 */
	private static final int UPDATE_VIEW = 0;

	/**
	 * 标示更新楼栋页签的数据
	 */
	private static final int UPDATE_BUILDING = 1;

	private BuildingPriceSetInfo priceSet = new BuildingPriceSetInfo();

	private BuildingPriceSetCollection priceSetCollection = new BuildingPriceSetCollection();
	
	private List deletedRows = new ArrayList();
	
	/**
	 * 是否已选择已售房源是否参与定价
	 */
	private boolean isSelectAdjustSoldRoom = false;
	
	/**
	 * 是否调整已售房间的价格
	 */
	private boolean isAdjustSoldRoom = false;

	public RoomPriceAdjustEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		this.kdtEntry.checkParsed();
		FDCTableHelper.disableDelete(this.kdtEntry);
		KDComboBox combo = new KDComboBox();
        for(int i = 0; i < DecorateEnum.getEnumList().size(); i++){
        	combo.addItem(DecorateEnum.getEnumList().get(i));
        }
        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.kdtEntry.getColumn("decorate").setEditor(comboEditor);
		
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setNegatived(false);
		txtWeight.setPrecision(2);
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		this.kdtEntry.getColumn("price").setEditor(weight);
		this.kdtEntry.getColumn("price").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtEntry.getColumn("amount").setEditor(weight);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtEntry.getColumn("decorate").getStyleAttributes().setHided(true);
		
		this.kdtDiffEntry.checkParsed();
		this.kdtDiffEntry.getColumn("oldPrice").setEditor(weight);
		this.kdtDiffEntry.getColumn("oldPrice").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtDiffEntry.getColumn("oldPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtDiffEntry.getColumn("oldAmount").setEditor(weight);
		this.kdtDiffEntry.getColumn("oldAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtDiffEntry.getColumn("oldAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtDiffEntry.getColumn("newPrice").setEditor(weight);
		this.kdtDiffEntry.getColumn("newPrice").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtDiffEntry.getColumn("newPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtDiffEntry.getColumn("newAmount").setEditor(weight);
		this.kdtDiffEntry.getColumn("newAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtDiffEntry.getColumn("newAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtPriceAdjustEntry.checkParsed();
		this.tblAttach.checkParsed();
		this.tblAttach.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.setPriceParams();
		super.onLoad();
//		this.comboalesareatype.setSelectedItem(null);
		if (getOprtState().equals(STATUS_VIEW)) {
			lockUIForViewStatus();
			this.btnRoomDelete.setEnabled(false);
			for (int i = 0; i < this.kDTabbedPanel.getTabCount(); i++) {
				KDPanel panel = (KDPanel) this.kDTabbedPanel.getComponentAt(i);
				KDTable table = (KDTable) panel.getComponent(0);
				table.setEnabled(false);
			}
		}
		
		this.initButtonState();
		this.initViewTab();
		this.initPrmtBuilding();
		this.initPrmtRoomSelect();

		this.kDTabbedPanel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (getOprtState().equals(STATUS_VIEW)) {
					return;
				}
				int tabIndex = kDTabbedPanel.getSelectedIndex();
				if (0 == tabIndex) { // 总览页签
					btnBatchAdjust.setEnabled(false);
					btnPriceScheme.setEnabled(false);
				} else { // 房间价格
					btnBatchAdjust.setEnabled(true);
					btnPriceScheme.setEnabled(true);
				}
			}

		});
		
//		handleCodingRule();
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		this.btnAttachment.setVisible(false);
		
//		this.managerAgio.setVisible(false);
//		this.contManagerAgio.setVisible(false);
		
		this.kdtPriceAdjustEntry.getColumn("oldManagerAgio").getStyleAttributes().setHided(true);
		this.kdtPriceAdjustEntry.getColumn("newManagerAgio").getStyleAttributes().setHided(true);
		
		this.kdtPriceAdjustEntry.getColumn("oldSceneManagerAgio").getStyleAttributes().setHided(true);
		this.kdtPriceAdjustEntry.getColumn("newSceneManagerAgio").getStyleAttributes().setHided(true);
		
		this.kdtPriceAdjustEntry.getColumn("oldSalesDirectorAgio").getStyleAttributes().setHided(true);
		this.kdtPriceAdjustEntry.getColumn("newSalesDirectorAgio").getStyleAttributes().setHided(true);
		
		this.txtDescription.setMaxLength(500);
	}
	
	public void onShow() throws Exception {
		super.onShow();
	}

	public void loadFields() {
		super.loadFields();

		RoomPriceManageInfo RoomPriceManageInfo = this.editData;
		PriceTypeForPriceBillEnum priceEnum = RoomPriceManageInfo.getPriceBillType();
		if (priceEnum == null) {
			priceEnum = PriceTypeForPriceBillEnum.UseBuildingArea;
		}
		this.comboPriceBillType.setSelectedItem(priceEnum);
		this.kdtPriceAdjustEntry.removeRows();  //清空表格，后续重新填充
		
		//填充总览页签数据
		if(RoomPriceManageInfo.getPriceAdjustEntry()!=null && !RoomPriceManageInfo.getPriceAdjustEntry().isEmpty()){
			RoomPriceAdjustEntryCollection priceEntryCol = RoomPriceManageInfo.getPriceAdjustEntry();
			for(int i=0; i<priceEntryCol.size(); i++){
				IRow row = this.kdtPriceAdjustEntry.addRow();
				this.setRowData(row, priceEntryCol.get(i));
				row.setUserObject(priceEntryCol.get(i));
				System.out.println(row.getCell("newRoomPrice").getValue());
			}
		}
		// 锁定单元格
		for(int i=0; i<this.kdtPriceAdjustEntry.getRowCount(); i++){
			IRow row = this.kdtPriceAdjustEntry.getRow(i);
			//是否调整已售房源，若不调整，则锁定行
			RoomSellStateEnum sellState = ((RoomPriceAdjustEntryInfo)row.getUserObject()).getRoom().getSellState();
			if(RoomSellStateEnum.PrePurchase.equals(sellState) || RoomSellStateEnum.Purchase.equals(sellState) 
					|| RoomSellStateEnum.Sign.equals(sellState)){
				this.checkSoldRoomAdjustable();
				
				if(isSelectAdjustSoldRoom && !isAdjustSoldRoom){
					row.getStyleAttributes().setLocked(true);
					continue;
				}
			}
			if(row.getCell("Salesareatype").getValue() == null){  //面积状态为空，表示房间没有录入面积
				row.getCell("priceType").getStyleAttributes().setLocked(true);
			}
			this.lockCellByPriceType(row, (PriceTypeForPriceBillEnum) row.getCell("priceType").getValue());
		}
		
		//总览页签合计
		this.addTotal(this.kdtPriceAdjustEntry);

		// 加载楼栋页签
		// 保存之后后再进入修改状态，会新增重复的楼栋页签，所以先删除楼栋页签
		for (int i = 1; i < this.kDTabbedPanel.getTabCount(); i++) {
			this.kDTabbedPanel.remove(i);
			i = 0;
		}
		// 再新增页签
		RoomPriceBuildingEntryCollection buildingEntryColl = this.editData.getBuildingEntry();
		if (buildingEntryColl != null && !buildingEntryColl.isEmpty()) {
			BuildingInfo[] buildingArray = new BuildingInfo[buildingEntryColl.size()];

			// 遍历楼栋分录，创建楼栋页签
			for (int i = 0; i < buildingEntryColl.size(); i++) {
				RoomPriceBuildingEntryInfo buildingEntry = (RoomPriceBuildingEntryInfo) buildingEntryColl.get(i);
				BuildingInfo building = buildingEntry.getBuilding();
				// 将楼栋添加到已选楼栋map中
				selectedBuildingMap.put(building.getId().toString(), building);
				// 创建楼栋页签
				KDTable buildingTable = this.createBuildingTable(building.getName(), building.getId().toString());
				// 填充楼栋表格
				RoomPriceAdjustEntryCollection entryCol = this.getPriceEntryByBuilding(building);
				if (entryCol != null && !entryCol.isEmpty()) {
					for (int j = 0; j < entryCol.size(); j++) {
						IRow row = buildingTable.addRow();
						row.setUserObject(entryCol.get(j));
						this.setRowData(row, entryCol.get(j));
					}
				}
				this.setColHideState(buildingTable);

				// 根据定价类型，锁定单元格
				for(int r=0; r<buildingTable.getRowCount(); r++){
					IRow row = buildingTable.getRow(r);
					
					//是否调整已售房源，若不调整，则锁定行
					RoomSellStateEnum sellState = ((RoomPriceAdjustEntryInfo)row.getUserObject()).getRoom().getSellState();
					if(RoomSellStateEnum.PrePurchase.equals(sellState) || RoomSellStateEnum.Purchase.equals(sellState) 
							|| RoomSellStateEnum.Sign.equals(sellState)){
						this.checkSoldRoomAdjustable();
						
						if(isSelectAdjustSoldRoom && !isAdjustSoldRoom){
							row.getStyleAttributes().setLocked(true);
							continue;
						}
					}
//					if(row.getCell("sellType").getValue() == null){
//						row.getCell("priceType").getStyleAttributes().setLocked(true);
//					}
					this.lockCellByPriceType(row, (PriceTypeForPriceBillEnum) row.getCell("priceType").getValue());
				}
				
				//楼栋页签合计
				this.addTotal(buildingTable);

				// 添加到楼栋数组
				buildingArray[i] = building;
			}

			// 设置楼栋f7控件初始值
			this.prmtBuilding.setDataNoNotify(buildingArray);
			this.setPriceSetScheme(buildingArray);
			this.setStatPrice();
		}
		
		try {
			if(shBuildId==null){
				shBuildId=new HashSet();
				SellProjectInfo psp=SHEManageHelper.getParentSellProject(null, this.editData.getSellProject());
				Set spId=SHEManageHelper.getAllSellProjectCollection(null, psp);
				if(spId.size()>0){
					SelectorItemCollection sel=new SelectorItemCollection();
					sel.add("banBasedataEntry.id");
					EntityViewInfo view=new EntityViewInfo();
					FilterInfo filter=new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("head.sellProject.id",spId,CompareType.INCLUDE));
//					filter.getFilterItems().add(new FilterItemInfo("head.isGetCertificated",Boolean.TRUE));
					view.setSelector(sel);
					BanBasedataEntryListCollection banEntryCol=BanBasedataEntryListFactory.getRemoteInstance().getBanBasedataEntryListCollection(view);
					for(int i=0;i<banEntryCol.size();i++){
						shBuildId.add(banEntryCol.get(i).getBanBasedataEntry().getId().toString());
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		if(shBuildId!=null&&shBuildId.size()>0){
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				IRow row=this.kdtEntry.getRow(i);
				ValueInputEntryInfo entry=(ValueInputEntryInfo) row.getUserObject();
				if(entry.getBuildId()!=null&&shBuildId.contains(entry.getBuildId().toString())){
					row.getStyleAttributes().setLocked(true);
					row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
				}
			}
		}
		this.kdtDiffEntry.removeRows();
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow newRow=this.kdtEntry.getRow(i);
			ValueInputEntryInfo newEntry=(ValueInputEntryInfo) newRow.getUserObject();
			IRow row=this.kdtDiffEntry.addRow();
			row.getCell("project").setValue(newRow.getCell("project").getValue());
			row.getCell("batch").setValue(newRow.getCell("batch").getValue());
			row.getCell("build").setValue(newRow.getCell("build").getValue());
			row.getCell("productType").setValue(newRow.getCell("productType").getValue());
			
			BigDecimal newPrice=(BigDecimal) newRow.getCell("price").getValue();
			BigDecimal newAmount=(BigDecimal) newRow.getCell("amount").getValue();
			
			row.getCell("newPrice").setValue(newPrice);
			row.getCell("newAmount").setValue(newAmount);
			
			try {
				ValueInputEntryInfo entry=ValueInputEntryFactory.getRemoteInstance().getValueInputEntryInfo(new ObjectUuidPK(newEntry.getSrcId()));
				row.getCell("oldPrice").setValue(entry.getPrice());
				row.getCell("oldAmount").setValue(entry.getAmount());
				
				row.getCell("subPrice").setValue(FDCHelper.subtract(newPrice, entry.getPrice()));
				row.getCell("subAmount").setValue(FDCHelper.subtract(newAmount, entry.getAmount()));
				if(newPrice!=null&&entry.getPrice()!=null&&newPrice.compareTo(entry.getPrice())!=0){
					row.getStyleAttributes().setBackground(new Color(248,171,166));
				}
				if(newAmount!=null&&entry.getAmount()!=null&&newAmount.compareTo(entry.getAmount())!=0){
					row.getStyleAttributes().setBackground(new Color(248,171,166));
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		loadAttachTable();
		
		this.comboalesareatype.setSelectedItem(this.editData.getSalesareatype());
	}

	public void storeFields() {
		//处理已售房源的记录
		deletedRows.clear();
		for(int i=0; i<this.kdtPriceAdjustEntry.getRowCount(); i++){
			IRow row = this.kdtPriceAdjustEntry.getRow(i);
			if(row.getStyleAttributes().isLocked()){
				if(row.getUserObject() != null){
					this.kdtPriceAdjustEntry.removeRow(i);
					deletedRows.add(row);
					i = -1;
				}
			}
		}
		
		//保存定价分录
		RoomPriceAdjustEntryCollection priceEntryCol = new RoomPriceAdjustEntryCollection();
		if(this.kdtPriceAdjustEntry.getRowCount() > 0){
			for(int p=0; p<this.kdtPriceAdjustEntry.getRowCount(); p++){
				IRow row = this.kdtPriceAdjustEntry.getRow(p);
				if(row.getStyleAttributes().isLocked()){  //不参与定价的房源
					continue;
				}
				else if(row.getUserObject() == null){  //合计行
					continue;
				}
				else{
					priceEntryCol.add((RoomPriceAdjustEntryInfo)row.getUserObject());
				}
			}
		}
		this.editData.getPriceAdjustEntry().clear();
		this.editData.getPriceAdjustEntry().addCollection(priceEntryCol);
		
		// 保存楼栋分录
		Object[] tempBuilding = (Object[]) this.prmtBuilding.getValue();
		RoomPriceBuildingEntryCollection buildingEntryCol = new RoomPriceBuildingEntryCollection();
		if (tempBuilding != null && tempBuilding.length > 0) {
			for (int i = 0; i < tempBuilding.length; i++) {
				RoomPriceBuildingEntryInfo buildingEntryInfo = new RoomPriceBuildingEntryInfo();
				BuildingInfo buildingInfo = (BuildingInfo) tempBuilding[i];
				if(buildingInfo == null){
					continue;
				}
				buildingEntryInfo.setBuilding(buildingInfo);
				buildingEntryCol.add(buildingEntryInfo);
			}
		}
		this.editData.getBuildingEntry().clear();
		this.editData.getBuildingEntry().addCollection(buildingEntryCol);

		this.editData.setSalesareatype((SellAreaTypeEnum) this.comboalesareatype.getSelectedItem());
		super.storeFields();
	}

	public boolean destroyWindow() {
		boolean result = super.destroyWindow();
        if(result)
        {
            if(attachMentTempID != null && (editData == null || editData.getId() == null))
                try
                {
                    AttachmentManagerFactory.getClientManager().deleteAttachemtsByBoID(attachMentTempID);
                    attachMentTempID = null;
                }
                catch(Exception e)
                {
                    handUIException(e);
                }
            stopTempSave();
        }
		if(!result){
			//重新显示被删除的房间
			for(int r=0; r<deletedRows.size(); r++){
				IRow row = (IRow)deletedRows.get(r);
				this.editData.getPriceAdjustEntry().add((RoomPriceAdjustEntryInfo)row.getUserObject());
			}
			this.loadFields();
		}
		return result;
	}
	
	protected IObjectValue createNewData() {
		RoomPriceManageInfo priceAdjustInfo = new RoomPriceManageInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		if (sellProject == null) {
			FDCMsgBox.showError("请先选择销售项目");
			this.abort();
		}

		priceAdjustInfo.setSellProject(sellProject);
		priceAdjustInfo.setIsExecuted(false);
		try {
			priceAdjustInfo.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance().getServerTime());
		} catch (BOSException e) {
			this.handleException(e);
		}
		priceAdjustInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		priceAdjustInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		priceAdjustInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		priceAdjustInfo.setBookedDate(new Date());
		priceAdjustInfo.setPriceBillType(PriceTypeForPriceBillEnum.UseStandPrice); // 默认按建筑面积
		priceAdjustInfo.setPriceBillMode(PriceBillTypeEnum.SetPrice);
		priceAdjustInfo.setSalesareatype(SellAreaTypeEnum.PRESELL);
		try {
			Set roomIdList = (Set)getUIContext().get("roomIdSet");
			if (roomIdList != null && !roomIdList.isEmpty()) {
				// 找出对应的房间集合
				RoomCollection roomCol = this.getRoomCollection(null, roomIdList);
				if (roomCol != null && !roomCol.isEmpty()) {
					HashMap buildingMap = new HashMap();  //已建楼栋map

					RoomPriceAdjustEntryCollection priceEntryCol = new RoomPriceAdjustEntryCollection();
					RoomPriceBuildingEntryCollection buildingEntryCol = new RoomPriceBuildingEntryCollection();

					// 遍历房间，组装定价分录，过滤楼栋
					Iterator roomColIt = roomCol.iterator();
					while (roomColIt.hasNext()) {
						RoomInfo room = (RoomInfo) roomColIt.next();
						if(!this.isAdjustable(room)){
							continue;
						}
						RoomPriceAdjustEntryInfo priceAdjustEntry = this.setPriceEntryInfo(room);
						priceEntryCol.add(priceAdjustEntry);
						// 楼栋在buildingMap中不存在，表示该楼栋与前面的楼栋不同，需要新增一个楼栋分录
						if (buildingMap.get(room.getBuilding().getId()) == null) {
							buildingMap.put(room.getBuilding().getId(), room.getBuilding());

							RoomPriceBuildingEntryInfo buildingEntry = new RoomPriceBuildingEntryInfo();
							buildingEntry.setBuilding(room.getBuilding());
							buildingEntryCol.add(buildingEntry);
						}
					}
					// 将定价分录添加到头里
					priceAdjustInfo.getPriceAdjustEntry().clear();
					priceAdjustInfo.getPriceAdjustEntry().addCollection(priceEntryCol);

					// 将楼栋添加到头里
					priceAdjustInfo.getBuildingEntry().clear();
					priceAdjustInfo.getBuildingEntry().addCollection(buildingEntryCol);
				}
			}
		} catch (BOSException e) {
		}
		return priceAdjustInfo;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
		sels.add("creator.*");
		sels.add("priceAdjustEntry.room.id");
		sels.add("priceAdjustEntry.room.displayName");
		sels.add("priceAdjustEntry.room.roomNo");
		sels.add("priceAdjustEntry.room.number");
		sels.add("priceAdjustEntry.room.unit");
		sels.add("priceAdjustEntry.room.building.*");
		sels.add("priceAdjustEntry.*");
		sels.add("priceAdjustEntry.room.*");
		sels.add("priceAdjustEntry.room.direction.*");
		sels.add("priceAdjustEntry.room.sellOrder.*");
		sels.add("priceAdjustEntry.room.newSight.*");
		sels.add("priceAdjustEntry.room.roomModel.*");
		sels.add("priceAdjustEntry.room.buildingProperty.*");
		sels.add("priceAdjustEntry.room.newDecorastd.*");
		sels.add("priceAdjustEntry.room.newPossStd.*");
		sels.add("priceAdjustEntry.room.newNoise.*");
		sels.add("priceAdjustEntry.room.newEyeSignht.*");
		sels.add("priceAdjustEntry.room.productDetail.*");
		sels.add("priceAdjustEntry.room.newProduceRemark.*");
		sels.add("buildingEntry.*");
		sels.add("buildingEntry.building.sellProject.*");
		sels.add("buildingEntry.building.*");
		
		sels.add("priceAdjustEntry.room.building.name");
		sels.add("priceAdjustEntry.room.buildUnit.name");
		sels.add("CU.*");
		
		sels.add("valueEntry.*");
		return sels;
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
//		this.verifyBeforeSave();
		super.actionSave_actionPerformed(e);
//		this.comboalesareatype.setSelectedItem(null);
		if (getOprtState().equals(this.STATUS_EDIT)) {
			setOprtState(STATUS_VIEW);
			lockUIForViewStatus();
			this.btnRoomSelect.setEnabled(false);
			this.btnRoomDelete.setEnabled(false);
			
			this.btnSubmit.setEnabled(true);
			this.actionSubmit.setEnabled(true);
		}
	}
	
	protected void btnForecast_actionPerformed(ActionEvent e) throws Exception {
//		if("".equals(this.managerAgio.getText())){
//			MsgBox.showWarning("请先填写总经理折扣！");
//			SysUtil.abort();
//		}
		UIContext uiContext = new UIContext(this);
//		uiContext.put("discount", this.managerAgio.getText());
		uiContext.put("projectId", this.editData.getSellProject().getId().toString());
		uiContext.put("kdtPriceAdjustEntry", this.kdtPriceAdjustEntry);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomPriceDynamicTotalValueUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		this.verifyBeforeSave();
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		if(this.tblAttach.getRowCount()==0){
			FDCMsgBox.showWarning(this,"定/调价依据为空！");
			SysUtil.abort();
		}
		super.actionSubmit_actionPerformed(e);
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
//		this.comboalesareatype.setSelectedItem(null);
		if (getOprtState().equals(this.STATUS_EDIT)) {
			setOprtState(STATUS_VIEW);
			lockUIForViewStatus();
			this.btnRoomSelect.setEnabled(false);
			this.btnRoomDelete.setEnabled(false);
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		this.setPriceParams();
		this.selectedBuildingMap = new HashMap();
		this.priceSet = new BuildingPriceSetInfo();
		this.priceSetCollection = new BuildingPriceSetCollection();
		this.deletedRows = new ArrayList();
		this.isSelectAdjustSoldRoom = false;
		this.isAdjustSoldRoom = false;
//		this.comboalesareatype.setSelectedItem(null);
		super.actionAddNew_actionPerformed(e);
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		this.prmtBuilding.setDataNoNotify(null);
		this.prmtRoomSelect.setDataNoNotify(null);
		
		this.btnRoomSelect.setEnabled(true);
		this.btnRoomDelete.setEnabled(true);
		this.btnPriceImport.setEnabled(true);
		this.kdtPriceAdjustEntry.setEnabled(true);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (!this.editData.getState().equals(FDCBillStateEnum.SAVED) && !this.editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			FDCMsgBox.showInfo("不能修改当前状态下的定价单!");
			return;
		}
		super.actionEdit_actionPerformed(e);
		this.btnSave.setEnabled(true);
		this.btnRoomSelect.setEnabled(true);
		this.btnRoomDelete.setEnabled(true);
		this.btnPriceImport.setEnabled(true);
		this.kdtPriceAdjustEntry.setEnabled(true);
		this.loadFields();
	}

	public void actionExecute_actionPerformed(ActionEvent e) throws Exception {
		super.actionExecute_actionPerformed(e);
		
		boolean isExecuted = this.editData.isIsExecuted();
		if (isExecuted) {
			MsgBox.showInfo("定价单已经执行!");
			return;
		}
		FDCClientUtils.checkBillInWorkflow(this, this.editData.getId().toString());
		if (!FDCBillStateEnum.AUDITTED.equals(this.editData.getState())) {
			FDCMsgBox.showWarning("定价单没有审核!");
			return;
		}

		if (MsgBox.showConfirm2New(this, "是否执行?") == MsgBox.YES) {
			String id = this.editData.getId().toString();

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id));
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("room.*");
			view.getSelector().add("room.sellState");

			RoomPriceAdjustEntryCollection entrys = RoomPriceAdjustEntryFactory.getRemoteInstance().getRoomPriceAdjustEntryCollection(view);
			for (int i = 0; i < entrys.size(); i++) {
				RoomPriceAdjustEntryInfo entry = entrys.get(i);
				RoomInfo room = entry.getRoom();

				BigDecimal entryBuildingArea = entry.getNewBuildingArea();
				BigDecimal entryRoomArea = entry.getNewRoomArea();
				if (entryBuildingArea == null) {
					entryBuildingArea = FDCHelper.ZERO;
				}
				if (entryRoomArea == null) {
					entryRoomArea = FDCHelper.ZERO;
				}

				BigDecimal buildingArea = null;
				BigDecimal roomArea = null;
				// 根据面积状态取面积
				if (entry.getSellType().equals(SellTypeEnum.LocaleSell)) { 
					buildingArea = room.getActualBuildingArea();
					roomArea = room.getActualRoomArea();
				} else if (entry.getSellType().equals(SellTypeEnum.PreSell)) {
					buildingArea = room.getBuildingArea();
					roomArea = room.getRoomArea();
				} else {  //预估
					buildingArea = room.getPlanBuildingArea();
					roomArea = room.getPlanRoomArea();
				}

				if (buildingArea == null) {
					buildingArea = FDCHelper.ZERO;
				}
				if (roomArea == null) {
					roomArea = FDCHelper.ZERO;
				}
				if (roomArea.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(entryRoomArea.setScale(2, BigDecimal.ROUND_HALF_UP)) != 0) {
					FDCMsgBox.showInfo("房间"+room.getName()+"套内面积与房价面积录入面积不符！");
					SysUtil.abort();
				}

				if (buildingArea.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(entryBuildingArea.setScale(2, BigDecimal.ROUND_HALF_UP)) != 0) {
					FDCMsgBox.showInfo("房间"+room.getName()+"建筑面积与房价面积录入面积不符！");
					SysUtil.abort();
				}
			}
			if (RoomPriceManageFactory.getRemoteInstance().excute(id)) {
				MsgBox.showInfo("执行成功!");
			} else {
				MsgBox.showInfo("定价单没有审核!");
			}
		}
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}
	
//	public void actionPrintPreview_actionPerformed(ActionEvent e)
//			throws Exception {
//		KDTable table = new KDTable();
//		table.addColumns(6);
//		for(int i=0;i<this.kdtPriceAdjustEntry.getRowCount();i++){
//			IRow entryRow = (IRow)kdtPriceAdjustEntry.getRow(i);
//			IRow newRow = table.addRow(i);
//			newRow.setCell(0, (ICell)entryRow.getCell("roomNo").clone());
//			newRow.setCell(1, (ICell)entryRow.getCell("newSumAmount").clone());
//			newRow.setCell(2, (ICell)entryRow.getCell("buildingArea").clone());
//			newRow.setCell(3, (ICell)entryRow.getCell("newBuildingPrice").clone());
//			newRow.setCell(4, (ICell)entryRow.getCell("roomArea").clone());
//			newRow.setCell(5, (ICell)entryRow.getCell("newRoomPrice").clone());
//		}
//		this.getRoomPrice(table);
//		table.getPrintManager().printPreview();
//		
//		super.actionPrintPreview_actionPerformed(e);
//	}
//	
//	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
//		super.actionPrint_actionPerformed(e);
//	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		RoomPriceManagePrintProvider data = new RoomPriceManagePrintProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		RoomPriceManagePrintProvider data = new RoomPriceManagePrintProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	protected String getTDFileName() {
		return "/bim/fdc/sellhouse/RoomPriceManage";
	}

	// 对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.sellhouse.app.RoomPriceManagePrintQuery");
	}
	protected KDTable getDetailTable() {
		return kdtPriceAdjustEntry;
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomPriceManageFactory.getRemoteInstance();
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void prmtRoomSelect_dataChanged(DataChangeEvent e)
			throws Exception {
		initPrmtRoomSelect();
		
		super.prmtRoomSelect_dataChanged(e);

		Object[] rooms = (Object[]) this.prmtRoomSelect.getValue();
		prmtRoomSelect.setValue(null);
		
		if (rooms == null || rooms.length == 0 || rooms[0] == null) {
			return;
		}

		RoomCollection roomCollection = new RoomCollection();
		HashMap newRoomsMap = new HashMap(); // 不在已选楼栋下的房间集合
		for (int i = 0; i < rooms.length; i++) {
			RoomInfo room = (RoomInfo) rooms[i];
			if(!this.isAdjustable(room)){
				continue;
			}
			if (isExist(this.kdtPriceAdjustEntry, room)) {
				continue;
			} else {
				String buildingId = room.getBuilding().getId().toString();
				//重新获取楼栋完整的信息
				BuildingInfo building = this.getBuildingInfo(buildingId);
				room.setBuilding(building);
				if(room.getBuildUnit()!=null){
					room.setBuildUnit(BuildingUnitFactory.getRemoteInstance().getBuildingUnitInfo(new ObjectUuidPK(room.getBuildUnit().getId())));
				}
				RoomCollection newRooms = null;
				if (newRoomsMap.get(buildingId) == null) {
					newRooms = new RoomCollection();
				} else {
					newRooms = (RoomCollection) newRoomsMap.get(buildingId);
				}
				newRooms.add(room);
				newRoomsMap.put(buildingId, newRooms);

				roomCollection.add(room);
			}
			
		}
		// 加入总览页签中
		this.addTableRow(this.kdtPriceAdjustEntry, roomCollection);
		
		//总览页签合计
		this.addTotal(this.kdtPriceAdjustEntry);

		// 加入楼栋页签中
		if (newRoomsMap != null && !newRoomsMap.isEmpty()) {
			Iterator roomsKeyIt = newRoomsMap.keySet().iterator();
			while (roomsKeyIt.hasNext()) {
				String buildingId = (String) roomsKeyIt.next();
				RoomCollection roomCol = (RoomCollection) newRoomsMap.get(buildingId);
				boolean isFound = false;
				for (int i = 1; i < this.kDTabbedPanel.getTabCount(); i++) {
					KDPanel panel = (KDPanel) this.kDTabbedPanel.getComponentAt(i);
					KDTable table = (KDTable) panel.getComponent(0);
					// 找到对应的页签，再查找对应的记录，如果找不到，则创建一条新的记录
					if (table.getName().equals(buildingId)) {
						this.addTableRow(table, roomCol);
						isFound = true;
						break;
					}
				}
				// 房间所属楼栋页签不存在，则创建
				if (!isFound) {
					selectedBuildingMap.put(buildingId, roomCol.get(0).getBuilding());
					KDTable table = this.createBuildingTable(roomCol.get(0).getBuilding().getName(), buildingId);
					this.addTableRow(table, roomCol);
				}
				
			}

			//将已选楼栋反写到楼栋f7选择控件框中
			BuildingInfo[] buildingArray = new BuildingInfo[selectedBuildingMap.size()];
			int index = 0;
			Iterator mapIt = selectedBuildingMap.keySet().iterator();
			while (mapIt.hasNext()) {
				buildingArray[index++] = (BuildingInfo) selectedBuildingMap.get((String) mapIt.next());
			}
			this.prmtBuilding.setDataNoNotify(buildingArray);
			
			//楼栋页签合计
			for(int t=1; t<this.kDTabbedPanel.getTabCount(); t++){
				KDPanel panel = (KDPanel) this.kDTabbedPanel.getComponentAt(t);
				KDTable table = (KDTable) panel.getComponent(0);
				this.addTotal(table);
			}
			
			//设置定价方案
			this.setPriceSetScheme(buildingArray);
		}

		this.setStatPrice();
	}

	protected void prmtBuilding_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtBuilding_dataChanged(e);

//		comboPriceBillType.setSelectedItem(PriceTypeForPriceBillEnum.UseBuildingArea);
		Object[] tempBuilding = (Object[]) this.prmtBuilding.getValue();
		if (tempBuilding == null) {
			return;
		}
		BuildingInfo[] schemeBuilding = new BuildingInfo[tempBuilding.length]; // 方案定价楼栋
		
		// 清空旧的已选楼栋
		selectedBuildingMap.clear();
		
		List buildingList = new ArrayList();
		for (int i = 0; i < tempBuilding.length; i++) {
			
			BuildingInfo buildingInfo = (BuildingInfo) tempBuilding[i];
			schemeBuilding[i] = buildingInfo;
			buildingList.add(buildingInfo);
			// 将选中楼栋添加到已选楼栋中
			selectedBuildingMap.put(buildingInfo.getId().toString(), buildingInfo);
		}

		// 设置选中楼栋的定价方案
		this.setPriceSetScheme(schemeBuilding);
		
		//填充楼栋数据
		if (buildingList.size() > 0) {
			
			BuildingInfo[] buildingArray = new BuildingInfo[buildingList.size()];
			for (int i = 0; i < buildingList.size(); i++) {
				buildingArray[i] = (BuildingInfo) buildingList.get(i);
				
			}

			this.addBuildingTab(buildingArray);
		}

	}

	protected void comboPriceBillMode_itemStateChanged(ItemEvent e)
			throws Exception {
		super.comboPriceBillMode_itemStateChanged(e);
		// 遍历页签，控制表格相关列的隐藏状态
		for (int i = 0; i < this.kDTabbedPanel.getTabCount(); i++) {
			KDPanel panel = (KDPanel) this.kDTabbedPanel.getComponentAt(i);
			KDTable table = (KDTable) panel.getComponent(0);
			this.setColHideState(table);
		}
	}
	protected void salesareatype_itemStateChanged(ItemEvent e) throws Exception {  //add  by shilei
		super.salesareatype_itemStateChanged(e);
		List rowList = new ArrayList();
		SellAreaTypeEnum sellt = (SellAreaTypeEnum)this.comboalesareatype.getSelectedItem();
		for (int i = 0; i < this.kDTabbedPanel.getTabCount(); i++) {
			KDPanel panel = (KDPanel) this.kDTabbedPanel.getComponentAt(i);
			KDTable table = (KDTable) panel.getComponent(0);
			rowList.clear();
			for(int j=0; j<table.getRowCount(); j++){
				IRow row = table.getRow(j);
				RoomInfo room = ((RoomPriceAdjustEntryInfo)row.getUserObject()).getRoom();
				RoomPriceAdjustEntryInfo priceAdjustEntry = new RoomPriceAdjustEntryInfo(); //sss
				priceAdjustEntry.setRoom(room);
				priceAdjustEntry.setPriceType((PriceTypeForPriceBillEnum) this.comboPriceBillType.getSelectedItem());
				priceAdjustEntry.setSellType(getSellTypeEnum(((SellAreaTypeEnum)this.comboalesareatype.getSelectedItem())));
				RoomPriceAdjustEntryInfo priceEntry = (RoomPriceAdjustEntryInfo)row.getUserObject();
				if(this.comboalesareatype.getSelectedItem()!=null){
					if(sellt.equals(sellt.PRESELL)){
						table.getHead().getRow(0).getCell(table.getColumnIndex("roomArea")).setValue("预售套内面积");
						table.getHead().getRow(0).getCell(table.getColumnIndex("buildingArea")).setValue("预售建筑面积");
						row.getCell("roomArea").setValue(room.getRoomArea()); 
						row.getCell("buildingArea").setValue(room.getBuildingArea());
						this.dealRowData(table.getRow(j));
						row.getCell("Salesareatype").setValue(SellAreaTypeEnum.PRESELL);
					}else if(sellt.equals(sellt.PLANNING)){
						table.getHead().getRow(0).getCell(table.getColumnIndex("roomArea")).setValue("预估套内面积");
						table.getHead().getRow(0).getCell(table.getColumnIndex("buildingArea")).setValue("预估建筑面积");
						row.getCell("roomArea").setValue(room.getPlanRoomArea());
						row.getCell("buildingArea").setValue(room.getPlanBuildingArea());
						this.dealRowData(table.getRow(j));
						row.getCell("Salesareatype").setValue(SellAreaTypeEnum.PLANNING);
//						}
					}else{
						table.getHead().getRow(0).getCell(table.getColumnIndex("roomArea")).setValue("实测套内面积");
						table.getHead().getRow(0).getCell(table.getColumnIndex("buildingArea")).setValue("实测建筑面积");
						row.getCell("roomArea").setValue(room.getActualRoomArea());
						row.getCell("buildingArea").setValue(room.getActualBuildingArea());
						this.dealRowData(table.getRow(j));
						row.getCell("Salesareatype").setValue(SellAreaTypeEnum.ACTUAL);
					}
				}
				priceEntry.setPriceType((PriceTypeForPriceBillEnum) row.getCell("priceType").getValue());
				priceEntry.setSellType(getSellTypeEnum((SellAreaTypeEnum)row.getCell("Salesareatype").getValue()));  
				priceEntry.setNewSumAmount((BigDecimal) row.getCell("newSumAmount").getValue());
				priceEntry.setNewBuildingArea((BigDecimal) row.getCell("buildingArea").getValue());
				priceEntry.setNewRoomArea((BigDecimal) row.getCell("roomArea").getValue());
				priceEntry.setNewBuildingPrice((BigDecimal) row.getCell("newBuildingPrice").getValue());
				priceEntry.setNewRoomPrice((BigDecimal) row.getCell("newRoomPrice").getValue());
			}
		}
	}
	/**
	 * 销售方式 转成  销售面积类型 
	 * */
	public SellTypeEnum getSellTypeEnum(SellAreaTypeEnum sellAreaTypeEnum){
		SellTypeEnum sellType = SellTypeEnum.PreSell;
		if(SellAreaTypeEnum.PRESELL.equals(sellAreaTypeEnum))
			sellType = SellTypeEnum.PreSell;
		if(SellAreaTypeEnum.PLANNING.equals(sellAreaTypeEnum))
			sellType = SellTypeEnum.PlanningSell;
		if(SellAreaTypeEnum.ACTUAL.equals(sellAreaTypeEnum))
			sellType = SellTypeEnum.LocaleSell;
		return sellType;
	}
	/**
	 * 销售面积类型 转成 销售方式
	 * */
	public SellAreaTypeEnum getSellAreaTypeEnum(SellTypeEnum sellTypeEnum){
		SellAreaTypeEnum sellAresType = SellAreaTypeEnum.PRESELL;
		if(SellTypeEnum.PreSell.equals(sellTypeEnum))
			sellAresType = SellAreaTypeEnum.PRESELL;
		if(SellTypeEnum.PlanningSell.equals(sellTypeEnum))
			sellAresType = SellAreaTypeEnum.PLANNING;
		if(SellTypeEnum.LocaleSell.equals(sellTypeEnum))
			sellAresType = SellAreaTypeEnum.ACTUAL;
		return sellAresType;
	}
//		String type = row.getCell("sellAreaType").getValue().toString();
//		if(SellAreaTypeEnum.PLANNING.getAlias().equals(type)){
//			room.setSellAreaType(SellAreaTypeEnum.PLANNING);
//		}else if(SellAreaTypeEnum.PRESELL.getAlias().equals(type)){
//			room.setSellAreaType(SellAreaTypeEnum.PRESELL);
//		}else{
//			room.setSellAreaType(SellAreaTypeEnum.ACTUAL);
//		}
//		
	protected void comboPriceBillType_itemStateChanged(ItemEvent e)
			throws Exception {
		super.comboPriceBillType_itemStateChanged(e);
		PriceTypeForPriceBillEnum headPriceType = (PriceTypeForPriceBillEnum) this.comboPriceBillType.getSelectedItem();
		// 遍历页签，根据主体的定价类型，更新自身的定价类型，并控制表格相关列的锁定状态
		List rowList = new ArrayList();
		for (int i = 0; i < this.kDTabbedPanel.getTabCount(); i++) {
			if(!(this.kDTabbedPanel.getComponentAt(i) instanceof KDPanel))continue;
			KDPanel panel = (KDPanel) this.kDTabbedPanel.getComponentAt(i);
			KDTable table = (KDTable) panel.getComponent(0);
			rowList.clear();
			for(int j=0; j<table.getRowCount(); j++){
				if(table.getRow(j).getStyleAttributes().isLocked()){
					continue;
				}
				else if(!table.getRow(j).getCell("priceType").getStyleAttributes().isLocked()){
					table.getRow(j).getCell("priceType").setValue(headPriceType);
					this.lockCellByPriceType(table.getRow(j), headPriceType);
				}
				else{
					rowList.add(table.getRow(j));
				}
				RoomPriceAdjustEntryInfo priceEntry = (RoomPriceAdjustEntryInfo)table.getRow(j).getUserObject();
				if(this.comboPriceBillType.getSelectedItem()!=null){
				if(headPriceType.equals(headPriceType.UseBuildingArea)){  //add by shilei
					if(this.Basisprice.getValue()!=null){
						table.getRow(j).getCell("newBuildingPrice").setValue(Basisprice.getValue());
						priceEntry.setNewBuildingPrice((BigDecimal)table.getRow(j).getCell("newBuildingPrice").getValue());
						priceEntry.setNewBuildingPrice((BigDecimal) table.getRow(j).getCell("newBuildingPrice").getValue());
						priceEntry.setNewRoomPrice((BigDecimal) table.getRow(j).getCell("newRoomPrice").getValue());
						priceEntry.setNewSumAmount((BigDecimal) table.getRow(j).getCell("newSumAmount").getValue());
					}
				}else if(headPriceType.equals(PriceTypeForPriceBillEnum.UseRoomArea)){
					if(this.Basisprice.getValue()!=null){
						table.getRow(j).getCell("newRoomPrice").setValue(Basisprice.getValue());
						priceEntry.setNewRoomPrice((BigDecimal)table.getRow(j).getCell("newRoomPrice").getValue());
						priceEntry.setNewBuildingPrice((BigDecimal) table.getRow(j).getCell("newBuildingPrice").getValue());
						priceEntry.setNewRoomPrice((BigDecimal) table.getRow(j).getCell("newRoomPrice").getValue());
						priceEntry.setNewSumAmount((BigDecimal) table.getRow(j).getCell("newSumAmount").getValue());
					}
				}else if(headPriceType.equals(headPriceType.UseStandPrice)){
					if(this.Basisprice.getValue()!=null){
						table.getRow(j).getCell("newSumAmount").setValue(Basisprice.getValue());
						priceEntry.setNewSumAmount((BigDecimal)table.getRow(j).getCell("newSumAmount").getValue());
						priceEntry.setNewBuildingPrice((BigDecimal) table.getRow(j).getCell("newBuildingPrice").getValue());
						priceEntry.setNewRoomPrice((BigDecimal) table.getRow(j).getCell("newRoomPrice").getValue());
						priceEntry.setNewSumAmount((BigDecimal) table.getRow(j).getCell("newSumAmount").getValue());
						this.dealRowData(table.getRow(j));
						}
					}
				}
			}
			for(int r=0; r<rowList.size(); r++){
				IRow row = (IRow)rowList.get(r);
				this.lockCellByPriceType(row, (PriceTypeForPriceBillEnum)row.getCell("priceType").getValue());
			}
		}
	}

	protected void kdtPriceAdjustEntry_editStopped(KDTEditEvent e)
			throws Exception {
		super.kdtPriceAdjustEntry_editStopped(e);
		// 更新价格的同时，更新楼栋页签对应的数据
		this.table_editStopped(this.kdtPriceAdjustEntry, e, UPDATE_BUILDING);
	}

	protected void kdtPriceAdjustEntry_tableClicked(KDTMouseEvent e)
			throws Exception {
		System.err.println("aaaaaaaa");
		this.kdtPriceAdjustEntry.removeRow(this.kdtPriceAdjustEntry.getRowCount());
	}
	
	protected void btnRoomSelect_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnRoomSelect_actionPerformed(e);

		this.prmtRoomSelect.setDataBySelector();
	}

	protected void btnBatchAdjust_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnBatchAdjust_actionPerformed(e);

		int selectedIndex = this.kDTabbedPanel.getSelectedIndex();
		KDPanel panel = (KDPanel) this.kDTabbedPanel.getComponentAt(selectedIndex);
		KDTable table = (KDTable) panel.getComponent(0);

		if(!this.checkBuildingRoom(table)){
			FDCMsgBox.showInfo("页签下的房间数量与楼栋拥有的房间数量不符合，不能批量调整");
			return;
		}
		// 获取每行的价格分录对象，组装分录集合
		RoomPriceAdjustEntryCollection entryCollection = new RoomPriceAdjustEntryCollection();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			Object rowObj = row.getUserObject();
			if (rowObj != null) {
				RoomPriceAdjustEntryInfo rowPriceEntry = (RoomPriceAdjustEntryInfo) rowObj;
				// 重新组合定价分录，供批量调整使用
				RoomPriceAdjustEntryInfo priceEntry = new RoomPriceAdjustEntryInfo();
				priceEntry.setRoom(rowPriceEntry.getRoom());
//				priceEntry.setSellType((SellTypeEnum) row.getCell("Salesareatype").getValue()); //shilei
				priceEntry.setSellType(getSellTypeEnum((SellAreaTypeEnum)row.getCell("Salesareatype").getValue()));  
				priceEntry.setPriceType((PriceTypeForPriceBillEnum) row.getCell("priceType").getValue());
				priceEntry.setOldSumAmount(KDTableTools.getBigDecimal(row.getCell("oldSumAmount").getValue()));
				priceEntry.setOldBuildingPrice(KDTableTools.getBigDecimal(row.getCell("oldBuildingPrice").getValue()));
				priceEntry.setOldRoomPrice(KDTableTools.getBigDecimal(row.getCell("oldRoomPrice").getValue()));
				priceEntry.setNewSumAmount(KDTableTools.getBigDecimal(row.getCell("newSumAmount").getValue()));
				priceEntry.setNewBuildingArea(KDTableTools.getBigDecimal(row.getCell("buildingArea").getValue()));
				priceEntry.setNewBuildingPrice(KDTableTools.getBigDecimal(row.getCell("newBuildingPrice").getValue()));
				priceEntry.setNewRoomArea(KDTableTools.getBigDecimal(row.getCell("roomArea").getValue()));
				priceEntry.setNewRoomPrice(KDTableTools.getBigDecimal(row.getCell("newRoomPrice").getValue()));
				
				priceEntry.setNewBaseStandardPrice(KDTableTools.getBigDecimal(row.getCell("newBaseStandardPrice").getValue()));
				priceEntry.setNewManagerAgio(KDTableTools.getBigDecimal(row.getCell("newManagerAgio").getValue()));
				priceEntry.setNewSceneManagerAgio(KDTableTools.getBigDecimal(row.getCell("newSceneManagerAgio").getValue()));
				priceEntry.setNewSalesDirectorAgio(KDTableTools.getBigDecimal(row.getCell("newSalesDirectorAgio").getValue()));
				this.dealRowData(row);
				entryCollection.add(priceEntry);
			}
		}
		// 批量调整价格
		RoomPriceAdjustEntryCollection retEntryCollection = BatchRoomPriceAdjustUI.showUI(this, isSelectAdjustSoldRoom,
				isAdjustSoldRoom, entryCollection, paramsArray);

		// 反写修改后的价格
		if (retEntryCollection != null && !retEntryCollection.isEmpty()) {
			table.removeRows();
			for (int i = 0; i < retEntryCollection.size(); i++) {
				IRow row = table.addRow();
				row.setUserObject(retEntryCollection.get(i));
				this.setRowData(row, retEntryCollection.get(i));
				
				this.checkDataChanged(row);
				
				// 更新总览数据
				this.updateViewRowData(row, false);
			}
		}
		//总览合计
		this.addTotal(this.kdtPriceAdjustEntry);
		
		//楼栋合计
		this.addTotal(table);
		
		// 修改统计价格
		this.setStatPrice();
	}

	protected void btnPriceScheme_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnPriceScheme_actionPerformed(e);

		if (this.prmtBuilding.getValue() == null) {
			MsgBox.showInfo("请先选择楼栋!");
			return;
		}

		// 选择方案
		BuildingPriceSetCollection priceSetColl = RoomPriceSchemeBatchSetUI
				.showUI(this, priceSetCollection, prmtBuilding.getValue(), this
						.getOprtState());

		if (priceSetColl == null) {
			return;
		}

		/*
		 * 根据 集合 中的每个方案信息来修改 表格数据
		 * 由于总览页签包含了所有楼栋的数据，所以这里只需修改总览页签，然后同步楼栋页签即可
		 */
		for (int j = 0; j < priceSetColl.size(); j++) {
			this.priceSet = priceSetColl.get(j);
			BigDecimal basePointAmount = this.priceSet.getBasePointAmount();
			PriceSetSchemeInfo scheme = this.priceSet.getPriceScheme();
			BuildingPriceSetEntryCollection setEntrys = this.priceSet.getPriceSetEntry();

			// 得到表格中的每一行
			for (int i = 0; i < this.kdtPriceAdjustEntry.getRowCount(); i++) {
				IRow row = this.kdtPriceAdjustEntry.getRow(i);

				if(row.getStyleAttributes().isLocked()){  //跳过锁定行，已售房间不参与调整
					continue;
				}
				if (row.getUserObject() == null) { // 跳过合计行
					continue;
				}
				RoomPriceAdjustEntryInfo roomEntry = (RoomPriceAdjustEntryInfo) row.getUserObject();

				if (roomEntry == null) {
					continue;
				}
				RoomInfo room = roomEntry.getRoom();

				// 判断该行的这个房间，是否是属于这个楼栋的。
				if (priceSet.getBuilding().toString().equalsIgnoreCase(room.getBuilding().toString())) {
					BigDecimal price = getCalRoomPrice(room, basePointAmount, setEntrys);
					if (price.compareTo(FDCHelper.ZERO) < 0) {
						MsgBox.showInfo("方案计算有房间为负数,请修改方案!");
						return;
					}
					if (scheme.isIsCalByRoomArea()) {
						row.getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseRoomArea);
						row.getCell("newRoomPrice").setValue(price);
						row.getCell("newBuildingPrice").getStyleAttributes().setLocked(true);
						row.getCell("newRoomPrice").getStyleAttributes().setLocked(false);
					} else {
						row.getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseBuildingArea);
						row.getCell("newBuildingPrice").setValue(price);
						row.getCell("newBuildingPrice").getStyleAttributes().setLocked(false);
						row.getCell("newRoomPrice").getStyleAttributes().setLocked(true);
					}
					
					this.dealRowData(row);
					//同步楼栋页签
					this.updateBuildingRowData(row, false);
				}
			}
		}
		//总览合计
		this.addTotal(this.kdtPriceAdjustEntry);
		
		//楼栋页签合计
		for(int t=1; t<this.kDTabbedPanel.getTabCount(); t++){
			KDPanel panel = (KDPanel) this.kDTabbedPanel.getComponentAt(t);
			KDTable table = (KDTable) panel.getComponent(0);
			this.addTotal(table);
		}
		
		//设置主体的统计数据
		this.setStatPrice();
	}

	protected void btnPriceImport_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnPriceImport_actionPerformed(e);
        
		KDPanel panel = (KDPanel) this.kDTabbedPanel.getComponentAt(this.kDTabbedPanel.getSelectedIndex());
		KDTable table = (KDTable) panel.getComponent(0);
		if(table.getRowCount()>0){
			this.importPrice(table);
		}else{
			MsgBox.showInfo("表格中未有记录行，不能导入修改！");
		}
	}

	private void importPrice(KDTable table) throws Exception {
		String fileName = SHEHelper.showExcelSelectDlg(this);

		// excel导入工具会过滤锁定状态的列，所以需暂时解锁
		boolean priceTypeLockState = table.getColumn("priceType").getStyleAttributes().isLocked();
		boolean SalesareatypeLockState = table.getColumn("Salesareatype").getStyleAttributes().isLocked();
		boolean sumAmountLockState = table.getColumn("newSumAmount").getStyleAttributes().isLocked();
		boolean buildingPriceLockState = table.getColumn("newBuildingPrice").getStyleAttributes().isLocked();
		boolean buildingAreaLockState = table.getColumn("buildingArea").getStyleAttributes().isLocked();
		boolean roomPriceColLockState = table.getColumn("newRoomPrice").getStyleAttributes().isLocked();
		boolean roomAreaLockState = table.getColumn("roomArea").getStyleAttributes().isLocked();
		
		boolean newBaseStandardPriceState = table.getColumn("newBaseStandardPrice").getStyleAttributes().isLocked();
		if (sumAmountLockState) {
			table.getColumn("priceType").getStyleAttributes().setLocked(false);
		}
		if (sumAmountLockState) {
			table.getColumn("Salesareatype").getStyleAttributes().setLocked(false);
		}
		if (sumAmountLockState) {
			table.getColumn("newSumAmount").getStyleAttributes().setLocked(false);
		}
		if (roomAreaLockState) {
			table.getColumn("roomArea").getStyleAttributes().setLocked(false);
		}
		if (buildingAreaLockState) {
			table.getColumn("buildingArea").getStyleAttributes().setLocked(false);
		}
		if (buildingPriceLockState) {
			table.getColumn("newBuildingPrice").getStyleAttributes().setLocked(false);
		}
		if (roomPriceColLockState) {
			table.getColumn("newRoomPrice").getStyleAttributes().setLocked(false);
		}

		if (newBaseStandardPriceState) {
			table.getColumn("newRoomPrice").getStyleAttributes().setLocked(false);
		}
		table.getColumn("newManagerAgio").getStyleAttributes().setLocked(false);
		table.getColumn("newSceneManagerAgio").getStyleAttributes().setLocked(false);
		table.getColumn("newSalesDirectorAgio").getStyleAttributes().setLocked(false);
		
		int count = SHEHelper.importExcelToTable(fileName, table, 1, 3);
		table.getColumn("priceType").getStyleAttributes().setLocked(priceTypeLockState);
		table.getColumn("priceType").getStyleAttributes().setLocked(SalesareatypeLockState);
		table.getColumn("priceType").getStyleAttributes().setLocked(false);
		table.getColumn("Salesareatype").getStyleAttributes().setLocked(false);
		table.getColumn("newSumAmount").getStyleAttributes().setLocked(sumAmountLockState);
		table.getColumn("newBuildingPrice").getStyleAttributes().setLocked(buildingPriceLockState);
		table.getColumn("newRoomPrice").getStyleAttributes().setLocked(roomPriceColLockState);

		table.getColumn("newBaseStandardPrice").getStyleAttributes().setLocked(newBaseStandardPriceState);
		table.getColumn("newManagerAgio").getStyleAttributes().setLocked(true);
		table.getColumn("newSceneManagerAgio").getStyleAttributes().setLocked(false);
		table.getColumn("newSalesDirectorAgio").getStyleAttributes().setLocked(false);
		
		MsgBox.showInfo("已经成功导入" + count + "条数据!");
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			RoomPriceAdjustEntryInfo priceEntry = (RoomPriceAdjustEntryInfo)row.getUserObject(); 
			
			if(row.getCell("priceType").getValue()!=null&&priceEntry!=null){
				if(row.getCell("priceType").getValue() instanceof PriceTypeForPriceBillEnum ){
					priceEntry.setPriceType((PriceTypeForPriceBillEnum)row.getCell("priceType").getValue());//add by xiaominWang
				}else if(row.getCell("priceType").getValue() instanceof String ){
					if(row.getCell("priceType").getValue().toString().equals(CalcTypeEnum.PriceByBuildingArea.getAlias())){
						priceEntry.setPriceType(PriceTypeForPriceBillEnum.UseBuildingArea);
						row.getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseBuildingArea);
					}else if(row.getCell("priceType").getValue().toString().equals(CalcTypeEnum.PriceByRoomArea.getAlias())){
						priceEntry.setPriceType(PriceTypeForPriceBillEnum.UseRoomArea);
						row.getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseRoomArea);
					}else if(row.getCell("priceType").getValue().toString().equals(CalcTypeEnum.PriceByTotalAmount.getAlias())){
						priceEntry.setPriceType(PriceTypeForPriceBillEnum.UseStandPrice);
						row.getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseStandPrice);
					}else if(row.getCell("priceType").getValue().toString().equals(PriceTypeForPriceBillEnum.UseRoomArea.getAlias())){
						priceEntry.setPriceType(PriceTypeForPriceBillEnum.UseRoomArea);
						row.getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseRoomArea);
					}else if(row.getCell("priceType").getValue().toString().equals(PriceTypeForPriceBillEnum.UseStandPrice.getAlias())){
						priceEntry.setPriceType(PriceTypeForPriceBillEnum.UseStandPrice);
						row.getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseStandPrice);
					}else if(row.getCell("priceType").getValue().toString().equals(PriceTypeForPriceBillEnum.UseBuildingArea.getAlias())){
						priceEntry.setPriceType(PriceTypeForPriceBillEnum.UseBuildingArea);
						row.getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseBuildingArea);
					}else{
						priceEntry.setPriceType(PriceTypeForPriceBillEnum.UseStandPrice);
						row.getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseStandPrice);
					}
				}
			}
			BigDecimal checkManagerAgio=(BigDecimal) row.getCell("newManagerAgio").getValue();
			BigDecimal checkSceneManagerAgio=(BigDecimal) row.getCell("newSceneManagerAgio").getValue();
			BigDecimal checkSalesDirectorAgio=(BigDecimal) row.getCell("newSalesDirectorAgio").getValue();
			
			// 处理新价格
			dealRowData(row);
			
			BigDecimal newManagerAgio=(BigDecimal) row.getCell("newManagerAgio").getValue();
			BigDecimal newSceneManagerAgio=(BigDecimal) row.getCell("newSceneManagerAgio").getValue();
			BigDecimal newSalesDirectorAgio=(BigDecimal) row.getCell("newSalesDirectorAgio").getValue();
			
			if(newManagerAgio!=null){
				if(checkManagerAgio==null||checkManagerAgio.compareTo(newManagerAgio)!=0){
					row.getStyleAttributes().setBackground(Color.PINK);
				}
			}
			if(newSceneManagerAgio!=null){
				if(checkSceneManagerAgio==null||checkSceneManagerAgio.compareTo(newSceneManagerAgio)!=0){
					row.getStyleAttributes().setBackground(Color.PINK);
				}
			}
			if(newSalesDirectorAgio!=null){
				if(checkSalesDirectorAgio==null||checkSalesDirectorAgio.compareTo(newSalesDirectorAgio)!=0){
					row.getStyleAttributes().setBackground(Color.PINK);
				}
			}
			//保存数据到价格分录对象中
			if(priceEntry!=null){
				if(row.getCell("newSumAmount").getValue()!=null){
					priceEntry.setNewSumAmount((BigDecimal) row.getCell("newSumAmount").getValue());	
				}
				if(row.getCell("newBuildingPrice").getValue()!=null){
					priceEntry.setNewBuildingPrice((BigDecimal) row.getCell("newBuildingPrice").getValue());
				}
				if(row.getCell("newRoomPrice").getValue()!=null){
					priceEntry.setNewRoomPrice((BigDecimal) row.getCell("newRoomPrice").getValue());
				}
				if(row.getCell("Salesareatype").getValue() instanceof SellAreaTypeEnum ){
					priceEntry.setSellType(getSellTypeEnum((SellAreaTypeEnum)row.getCell("Salesareatype").getValue()));  
				}else if(row.getCell("Salesareatype").getValue() instanceof String ){
					List list=SellAreaTypeEnum.getEnumList();
					for(int j=0;j<list.size();j++){
						if(row.getCell("Salesareatype").getValue().equals(((SellAreaTypeEnum)list.get(j)).getAlias())){
							priceEntry.setSellType(getSellTypeEnum((SellAreaTypeEnum)list.get(j)));
							row.getCell("Salesareatype").setValue((SellAreaTypeEnum)list.get(j));
						}
					}
				}
				priceEntry.setNewRoomArea((BigDecimal) row.getCell("roomArea").getValue());
				priceEntry.setNewBuildingArea((BigDecimal) row.getCell("buildingArea").getValue());
				
				priceEntry.setNewBaseStandardPrice((BigDecimal) row.getCell("newBaseStandardPrice").getValue());
				priceEntry.setNewManagerAgio((BigDecimal) row.getCell("newManagerAgio").getValue());
				priceEntry.setNewSceneManagerAgio((BigDecimal) row.getCell("newSceneManagerAgio").getValue());
				priceEntry.setNewSalesDirectorAgio((BigDecimal) row.getCell("newSalesDirectorAgio").getValue());
			}
			
			// 更新相应的页签
			if (this.kDTabbedPanel.getSelectedIndex() != 0) {
				this.updateViewRowData(row, false);
			} else {
				this.updateBuildingRowData(row, false);
			}
		}

		this.addTotal(this.kdtPriceAdjustEntry);
		
		this.addTotal(table);
	}

	protected void btnRoomDelete_actionPerformed(ActionEvent e)
			throws Exception {
		int selTabIndex = this.kDTabbedPanel.getSelectedIndex();
		KDPanel panel = (KDPanel) this.kDTabbedPanel.getComponentAt(selTabIndex);
		KDTable table = (KDTable) panel.getComponent(0);

		//int activeRowIndex = table.getSelectManager().getActiveRowIndex();
		int [] selectRows = KDTableUtil.getSelectedRows(table);
		if (selectRows.length <=0) {
			FDCMsgBox.showWarning(this,"请选择行!");
			this.abort();
		} else {
			
			int select = 0;
			for (int i = selectRows.length ; i >0; i--) {
				select = selectRows[i-1];
				IRow row = table.removeRow(select);
				if (selTabIndex != 0) { // 当前页签为楼栋页签，更新总览页签
					this.updateViewRowData(row, true);
					this.updateBuildingTab(selTabIndex, table);
					this.addTotal(table);  //楼栋合计
				} else { // 当前页签为总览页签，更新楼栋页签
					KDTable buildingTable = this.updateBuildingRowData(row, true);
					this.addTotal(buildingTable);  //楼栋合计
				}
				
				this.addTotal(this.kdtPriceAdjustEntry);
				
				setStatPrice();

			}
		}
	}
	
	/**
	 * 判断是否需要做底价控制
	 * @return
	 */
	private boolean isForceBasePrice(){
		boolean result = false;
		
//		RoomDisplaySetting setting = new RoomDisplaySetting(null, SHEParamConstant.PROJECT_SET_MAP);
//		if(setting != null){
			SellProjectInfo sellProject = this.editData.getSellProject();
			Map projectSet = RoomDisplaySetting.getNewProjectSet(null,sellProject.getId().toString());
			if(projectSet != null){
				result = ((Boolean)projectSet.get(SHEParamConstant.T2_IS_FORCE_LIMIT_PRICE)).booleanValue();
			}
//		}
		return result;
	}
	
	/**
	 * 如果强制底价控制，则检查检测房间底价是否已设置，判断是否可进行定价调价
	 * @param room
	 * @return
	 */
	private boolean isAdjustable(RoomInfo room){
		boolean result = true;
		
		/*boolean isForceBasePrice = this.isForceBasePrice();
		if(isForceBasePrice){
			if(room.getBaseStandardPrice() == null){
				result = false;
			}
		}*/
		
		return result;
	}
	
	/**
	 * 读取系统设置，获取价格的保留位和取整方式
	 * @return Map
	 */
	private void setPriceParams(){
		//默认值，保留2位，四舍五入
		this.paramsArray[0] = 2;
		this.paramsArray[1] = BigDecimal.ROUND_HALF_UP;
		this.paramsArray[2] = 2;
		this.paramsArray[3] = BigDecimal.ROUND_HALF_UP;
		
		try{
			String orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
			IObjectPK comPK = new ObjectUuidPK(orgId);
			
			HashMap hmParamIn = new HashMap();
			hmParamIn.put(CRMConstants.FDCSHE_PARAM_PRICE_BIT, comPK);
		    hmParamIn.put(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE, comPK);
		    hmParamIn.put(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT, comPK);
		    hmParamIn.put(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE, comPK);
		    
	        HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
	        
	        if(hmAllParam != null){
	        	if(hmAllParam.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT) != null){
	        		paramsArray[0] =SHEManageHelper.setPrecision(hmAllParam.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString()).intValue();
	        	}
	        	if(hmAllParam.get(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE) != null){
	        		paramsArray[1] = SHEManageHelper.setRoundingMode(hmAllParam.get(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE).toString()).intValue();
	        	}
	        	if(hmAllParam.get(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT) != null){
	        		paramsArray[2] = SHEManageHelper.setPrecision(hmAllParam.get(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT).toString()).intValue();
	        	}
	        	if(hmAllParam.get(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE) != null){
	        		paramsArray[3] = SHEManageHelper.setRoundingMode(hmAllParam.get(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE).toString()).intValue();
	        	}
	        }
	        
		}catch(Exception e){
			logger.error("读取定价的系统参数时发生错误。" + e);
		}
	}
	
	/**
	 * 根据系统参数设置价格的取整方式
	 * @param toIntegerType - 系统参数对应的取整方式
	 * @return Bigdecmal对应的取整方式
	 */
	private int getRoundMode(int toIntegerType){
		int ROUND_UP = 1;
		int ROUND_DOWN = 2;
		
		if(ROUND_DOWN == toIntegerType){
			return BigDecimal.ROUND_DOWN;
		}
		else if(ROUND_UP == toIntegerType){
			return BigDecimal.ROUND_UP;
		}
		else{
			return BigDecimal.ROUND_HALF_UP;
		}
		
	}
	
	/**
	 * 已售房间是否参与调整
	 */
	private void checkSoldRoomAdjustable(){
		try {
			if(!getOprtState().equals(this.STATUS_VIEW) && !isSelectAdjustSoldRoom){
				if(editData.getId()!=null&&FDCUtils.isRunningWorkflow(editData.getId().toString())){
					return;
				}
				if (MsgBox.showConfirm2New(this, "已售房间是否参与定价调价？") == MsgBox.YES) {
					this.isAdjustSoldRoom = true;
				}else{
					this.isAdjustSoldRoom = false;
				}
				this.isSelectAdjustSoldRoom = true;  //标识已设置过已售房源是否参与调整
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 当f7中选到楼栋的时候，就找出楼栋对应的定价方案
	 * 
	 * @param building
	 */
	private void setPriceSetScheme(BuildingInfo[] building) {
		if (building == null) {
			return;
		}
		if (priceSetCollection != null) {
			priceSetCollection.clear();
		}

		// 过来的楼栋有一些是有定价方案，有一些是没有的，分别考虑
		for (int i = 0; i < building.length; i++) {
			// 根据楼栋ID找出该楼栋所对应的定价方案
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("*");
			view.getSelector().add("building.sellProject.id");
			view.getSelector().add("priceSetEntry.*");
			view.getSelector().add("priceSetEntry.schemeEntry.*");

			view.getSorter().add(new SorterItemInfo("number"));
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", building[i].getId()
							.toString()));

			BuildingPriceSetCollection priceSets = null;
			try {
				priceSets = BuildingPriceSetFactory.getRemoteInstance()
						.getBuildingPriceSetCollection(view);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			// 如果该楼栋有对应的定价方案
			if (priceSets.size() >= 1) {
				this.priceSet = priceSets.get(0);
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("*");
				sels.add("entrys.*");

				PriceSetSchemeInfo scheme = null;
				try {
					scheme = PriceSetSchemeFactory.getRemoteInstance()
							.getPriceSetSchemeInfo(
									new ObjectUuidPK(priceSet.getPriceScheme()
											.getId()), sels);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
				this.priceSet.setPriceScheme(scheme);

				this.priceSet.setBuilding(building[i]);
				// 存放进集合
				priceSetCollection.add(priceSet);

			} // 如果没有对应的定价方案，则存进楼栋信息进去
			else {
				BuildingPriceSetInfo temp = new BuildingPriceSetInfo();
				temp.setBuilding(building[i]);
				priceSetCollection.add(temp);
			}
		}
	}

	/**
	 * 根据方案计算房间价格
	 * 
	 * @param room
	 * @param basePointAmount
	 * @param setEntrys
	 * @return
	 */
	private BigDecimal getCalRoomPrice(RoomInfo room,
			BigDecimal basePointAmount,
			BuildingPriceSetEntryCollection setEntrys) {
		BigDecimal price = basePointAmount;
		if (price == null) {
			price = FDCHelper.ZERO;
		}
		Map buildingSetMap = new TreeMap();
		Map schemeEntryMap = new TreeMap();
		for (int j = 0; j < setEntrys.size(); j++) {
			BuildingPriceSetEntryInfo buildingSetEntry = setEntrys.get(j);
			String entryId = buildingSetEntry.getSchemeEntry().getId().toString();
			if (buildingSetMap.containsKey(entryId)) {
				BuildingPriceSetEntryCollection buildingSetEntrys = (BuildingPriceSetEntryCollection) buildingSetMap.get(entryId);
				buildingSetEntrys.add(buildingSetEntry);
			} else {
				BuildingPriceSetEntryCollection buildingSetEntrys = new BuildingPriceSetEntryCollection();
				buildingSetEntrys.add(buildingSetEntry);
				buildingSetMap.put(entryId, buildingSetEntrys);
			}
			schemeEntryMap.put(entryId, buildingSetEntry.getSchemeEntry());
		}

		Set keys = buildingSetMap.keySet();
		BigDecimal offset = FDCHelper.ZERO;
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			PriceSetSchemeEntryInfo entry = (PriceSetSchemeEntryInfo) schemeEntryMap.get(key);
			BuildingPriceSetEntryCollection setValues = (BuildingPriceSetEntryCollection) buildingSetMap.get(key);
			String field = entry.getFactorField();
			if (field == null) {
				continue;
			}
			Object value = room.get(field);
			if (field.equals("houseProperty")) {
				value = room.getHouseProperty();
			}
			if (value instanceof BigDecimal) {
				if (value == null) {
					value = FDCHelper.ZERO;
				}
				for (int j = 0; j < setValues.size(); j++) {
					BuildingPriceSetEntryInfo valueEntry = setValues.get(j);
					if (valueEntry.getFactorContentA().compareTo((BigDecimal) value) == 0) {
						if (entry.getCalculateMethod().equals(CalculateMethodEnum.Coefficient)) {
							price = price.multiply(valueEntry.getValue());
						} else {
							offset = offset.add(valueEntry.getValue());
						}
						break;
					}
				}
			} else if (value instanceof Integer) {
				BigDecimal bValue = new BigDecimal(((Integer) value).intValue());
				for (int j = 0; j < setValues.size(); j++) {
					BuildingPriceSetEntryInfo valueEntry = setValues.get(j);
					if (valueEntry.getFactorContentA().compareTo(bValue) == 0) {
						if (entry.getCalculateMethod().equals(CalculateMethodEnum.Coefficient)) {
							price = price.multiply(valueEntry.getValue());
						} else {
							offset = offset.add(valueEntry.getValue());
						}
						break;
					}
				}
			} else {
				if (value == null) {
					continue;
				}
				String valueS = value.toString();
				for (int j = 0; j < setValues.size(); j++) {
					BuildingPriceSetEntryInfo valueEntry = setValues.get(j);
					if (valueEntry.getFactorContentS().equals(valueS)) {
						if (entry.getCalculateMethod().equals(CalculateMethodEnum.Coefficient)) {
							price = price.multiply(valueEntry.getValue());
						} else {
							offset = offset.add(valueEntry.getValue());
						}
						break;
					}
				}
			}
		}
		price = price.add(offset);
		return price;
	}

	/**
	 * 初始化按钮
	 */
	private void initButtonState() {
		this.btnExecute.setIcon(EASResource.getIcon("imgTbtn_execute"));
		
		/*SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!FDCSysContext.getInstance().checkIsSHEOrg()) { // 非售楼组织不能操作
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionExecute.setEnabled(false);
		}*/

		this.btnBatchAdjust.setEnabled(false);
		this.btnPriceScheme.setEnabled(false);

		this.actionAuditResult.setVisible(true);
		this.btnAuditResult.setVisible(true);
		this.actionNextPerson.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionCopyFrom.setEnabled(false);

		if (this.getOprtState().equals("VIEW")) { // 查看状态
			this.btnRoomSelect.setEnabled(false);
			this.btnPriceImport.setEnabled(false);
			this.btnPriceScheme.setEnabled(false);
			this.btnBatchAdjust.setEnabled(false);
		}
	}

	/**
	 * 初始化楼栋F7控件
	 */
	private void initPrmtBuilding() {
		SellProjectInfo sellPro = this.editData.getSellProject();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (sellPro != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", sellPro.getId()
							.toString()));
		}
		// 售楼属性
		filter.getFilterItems().add(
				new FilterItemInfo("sellProject.isForSHE", "true"));
		view.setFilter(filter);

		this.prmtBuilding.setEntityViewInfo(view);

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
		this.prmtBuilding.setSelectorCollection(sels);
	}

	/**
	 * 初始化房间选择f7控件
	 */
	private void initPrmtRoomSelect() {
		EntityViewInfo view = new EntityViewInfo();

		// 过滤条件
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("isForSHE", Boolean.TRUE)); // 房间的售楼属性
		filterItems.add(new FilterItemInfo("sellProject.isForSHE", Boolean.TRUE)); // 项目的售楼属性
		filterItems.add(new FilterItemInfo("sellProject.id", editData.getSellProject().getId()));
		view.getSorter().add(new SorterItemInfo("sellProject.name"));
		view.getSorter().add(new SorterItemInfo("building.name"));
		view.getSorter().add(new SorterItemInfo("unit"));
		view.getSorter().add(new SorterItemInfo("floor"));
		view.getSorter().add(new SorterItemInfo("number"));
		view.setFilter(filter);

		MyKDCommonPromptDialog dlg = new MyKDCommonPromptDialog() {
			protected void handleDisplayCol(KDTDataRequestEvent e, KDTable table) {
				super.handleDisplayCol(e, table);
				
				int begin = e.getFirstRow();
				int last = e.getLastRow();
				
				for (int i = begin; i < last+1 ; i++) {
					ICell cellP = table.getRow(i).getCell("standardTotalAmount");
					
					if(cellP != null  &&  cellP.getValue() != null){
						BigDecimal v = (BigDecimal) cellP.getValue();
						if(v.compareTo(FDCHelper.ZERO) != 0){
							table.getRow(i).getStyleAttributes().setBackground(Color.YELLOW);
						}
					}
				}
			}
		};
		dlg.setEnabledMultiSelection(true);
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.RoomSelectByListQuery")));
		dlg.setEntityViewInfo(view);
		
		prmtRoomSelect.setEntityViewInfo(view);
		prmtRoomSelect.setEditFormat("$number$");
		prmtRoomSelect.setDisplayFormat("$name$");
		prmtRoomSelect.setCommitFormat("$number$");
		
		
		prmtRoomSelect.setSelector(dlg);
	}

	/**
	 * 初始化表格的列，设置编辑器
	 * 
	 * @param table
	 *            - 当前表格
	 */
	private void initTableColumn(KDTable table) {
		//设置粘贴的类型：仅单元格的值
		KDTTransferAction tranAction = (KDTTransferAction)table.getActionMap().get(KDTAction.PASTE);
		tranAction.setPasteMode(KDTEditHelper.VALUE);
		
		// 设置显示格式
		table.getColumn("oldSumAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[2]));
		table.getColumn("newSumAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[2]));
		table.getColumn("buildingArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		table.getColumn("roomArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		table.getColumn("oldBuildingPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[0]));
		table.getColumn("newBuildingPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[0]));
		table.getColumn("oldRoomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[0]));
		table.getColumn("newRoomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[0]));
		table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);

		table.getColumn("oldBaseStandardPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[2]));
		table.getColumn("newBaseStandardPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[2]));
		
		table.getColumn("oldManagerAgio").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		table.getColumn("newManagerAgio").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		table.getColumn("newManagerAgio").getStyleAttributes().setLocked(true);
		table.getColumn("oldManagerAgio").getStyleAttributes().setLocked(true);
		
		table.getColumn("oldSceneManagerAgio").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		table.getColumn("newSceneManagerAgio").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		table.getColumn("oldSceneManagerAgio").getStyleAttributes().setLocked(true);
		table.getColumn("newSceneManagerAgio").getStyleAttributes().setLocked(false);
		
		table.getColumn("oldSalesDirectorAgio").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		table.getColumn("newSalesDirectorAgio").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		table.getColumn("oldSalesDirectorAgio").getStyleAttributes().setLocked(true);
		table.getColumn("newSalesDirectorAgio").getStyleAttributes().setLocked(false);
		
		// 设置编辑格式
		FDCFormattedTextField areaFormatted = new FDCFormattedTextField(KDFormattedTextField.DECIMAL);
		areaFormatted.setPrecision(2);
		areaFormatted.setRoundingMode(4);
		areaFormatted.setSupportedEmpty(true);
		areaFormatted.setNegatived(true);
		ICellEditor areaEditor = new KDTDefaultCellEditor(areaFormatted);
		table.getColumn("buildingArea").setEditor(areaEditor);
		table.getColumn("roomArea").setEditor(areaEditor);
//		table.getColumn("actualRoomArea").setEditor(areaEditor);
//		table.getColumn("preRoomArea").setEditor(areaEditor);
//		table.getColumn("planRoomArea").setEditor(areaEditor);
		
		FDCFormattedTextField sumFormatted = new FDCFormattedTextField(KDFormattedTextField.DECIMAL);
		sumFormatted.setPrecision(this.paramsArray[2]);
		sumFormatted.setRoundingMode(this.paramsArray[3]);
		sumFormatted.setSupportedEmpty(true);
		sumFormatted.setNegatived(true);
		ICellEditor sumEditor = new KDTDefaultCellEditor(sumFormatted);
		table.getColumn("newSumAmount").setEditor(sumEditor);
		table.getColumn("oldSumAmount").setEditor(sumEditor);
		table.getColumn("oldBaseStandardPrice").setEditor(sumEditor);
		table.getColumn("newBaseStandardPrice").setEditor(sumEditor);
		
		FDCFormattedTextField  singleFormatted = new FDCFormattedTextField(KDFormattedTextField.DECIMAL);
		singleFormatted.setPrecision(this.paramsArray[0]);
		singleFormatted.setRoundingMode(this.paramsArray[1]);
		singleFormatted.setSupportedEmpty(true);
		singleFormatted.setNegatived(true);
		ICellEditor singleEditor = new KDTDefaultCellEditor(singleFormatted);
		table.getColumn("newBuildingPrice").setEditor(singleEditor);
		table.getColumn("newRoomPrice").setEditor(singleEditor);
		table.getColumn("oldBuildingPrice").setEditor(singleEditor);
		table.getColumn("oldRoomPrice").setEditor(singleEditor);
		
		FDCFormattedTextField  agioFormatted = new FDCFormattedTextField(KDFormattedTextField.DECIMAL);
		agioFormatted.setPrecision(4);
		agioFormatted.setRoundingMode(BigDecimal.ROUND_HALF_UP);
		agioFormatted.setSupportedEmpty(true);
		agioFormatted.setNegatived(true);
		ICellEditor agioEditor = new KDTDefaultCellEditor(agioFormatted);
		table.getColumn("newManagerAgio").setEditor(agioEditor);
		table.getColumn("oldManagerAgio").setEditor(agioEditor);

		table.getColumn("oldSceneManagerAgio").setEditor(agioEditor);
		table.getColumn("newSceneManagerAgio").setEditor(agioEditor);
		
		table.getColumn("oldSalesDirectorAgio").setEditor(agioEditor);
		table.getColumn("newSalesDirectorAgio").setEditor(agioEditor);
		//设置销售面积类型列
		KDComboBox comboSalesareatype = new KDComboBox();		//add by shilei
		comboSalesareatype.addItems(SellAreaTypeEnum.getEnumList().toArray());
		KDTDefaultCellEditor sellSalesareatypecellEditor = new KDTDefaultCellEditor(comboSalesareatype);
		table.getColumn("Salesareatype").setEditor(sellSalesareatypecellEditor);
		// 设置定价类型列
		KDComboBox comboPriceType = new KDComboBox();
		comboPriceType.addItems(PriceTypeForPriceBillEnum.getEnumList()
				.toArray());
		KDTDefaultCellEditor priceTypeCellEditor = new KDTDefaultCellEditor(
				comboPriceType);
		table.getColumn("priceType").setEditor(priceTypeCellEditor);
		table.getColumn("Salesareatype").setEditor(sellSalesareatypecellEditor);
		
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				if(o==null){
					return null;
				}else{
					String str = o.toString();
					return str + "%";
				}
				
			}
		});
		table.getColumn("newManagerAgio").setRenderer(render_scale);
		table.getColumn("oldManagerAgio").setRenderer(render_scale);
		
		table.getColumn("oldSceneManagerAgio").setRenderer(render_scale);
		table.getColumn("newSceneManagerAgio").setRenderer(render_scale);
		
		table.getColumn("oldSalesDirectorAgio").setRenderer(render_scale);
		table.getColumn("newSalesDirectorAgio").setRenderer(render_scale);
	}

	/**
	 * 初始化总览页签
	 */
	private void initViewTab() {
		// 锁定列，只供查看
		String[] colNameArray = new String[] { "roomId", "buildUnit","building", "roomNo",
				"oldSumAmount", "buildingArea", "oldBuildingPrice", "roomArea",
				"oldRoomPrice","oldManagerAgio","oldBaseStandardPrice" };
		this.setColumnLockState(kdtPriceAdjustEntry, colNameArray, true);

		setColHideState(kdtPriceAdjustEntry);

		initTableColumn(kdtPriceAdjustEntry);
	}

	/**
	 * 根据定/调价方式，设置【原总价】、【原建筑单价】、【原套内单价】列的隐藏状态
	 * 
	 * @param table
	 *            - 当前表格
	 */
	private void setColHideState(KDTable table) {
		boolean isHide = true;
		PriceBillTypeEnum priceBillMode = (PriceBillTypeEnum) this.comboPriceBillMode.getSelectedItem();
		if (!PriceBillTypeEnum.SetPrice.equals(priceBillMode)) { // 非定价，显示列
			isHide = false;
		}
		table.getColumn("oldSumAmount").getStyleAttributes().setHided(isHide);
		table.getColumn("oldBuildingPrice").getStyleAttributes().setHided(isHide);
		table.getColumn("oldRoomPrice").getStyleAttributes().setHided(isHide);
		table.getColumn("oldBaseStandardPrice").getStyleAttributes().setHided(isHide);
//		table.getColumn("oldManagerAgio").getStyleAttributes().setHided(isHide);
//		table.getColumn("oldSceneManagerAgio").getStyleAttributes().setHided(isHide);
//		table.getColumn("oldSalesDirectorAgio").getStyleAttributes().setHided(isHide);
	}

	/**
	 * 根据定价类型，设置相关列的可录入状态
	 * 
	 * @param table
	 *            - 当前表格
	 */
	private void setColLockStateByPriceType(KDTable table, PriceTypeForPriceBillEnum priceBillType) {
		if (PriceTypeForPriceBillEnum.UseBuildingArea.equals(priceBillType)) {
			table.getColumn("newBuildingPrice").getStyleAttributes().setLocked(false);
			table.getColumn("newRoomPrice").getStyleAttributes().setLocked(true);

		} else if (PriceTypeForPriceBillEnum.UseRoomArea.equals(priceBillType)) {
			table.getColumn("newBuildingPrice").getStyleAttributes().setLocked(true);
			table.getColumn("newRoomPrice").getStyleAttributes().setLocked(false);
		} else if (PriceTypeForPriceBillEnum.UseStandPrice.equals(priceBillType)) {
			table.getColumn("newBuildingPrice").getStyleAttributes().setLocked(true);
			table.getColumn("newRoomPrice").getStyleAttributes().setLocked(true);
		}
	}
	
	/**
	 * 根据定价类型，锁定相应的价格单元格
	 * @param row
	 * @param priceBillType
	 */
	private void lockCellByPriceType(IRow row, PriceTypeForPriceBillEnum priceBillType) {
		//if(row.getUserObject()==null){
			//return;
		//}
		RoomInfo room = ((RoomPriceAdjustEntryInfo)row.getUserObject()).getRoom();
//		SellTypeEnum sellType = (SellTypeEnum)row.getCell("sellType").getValue();
		SellAreaTypeEnum selltt = (SellAreaTypeEnum)row.getCell("Salesareatype").getValue();
		//根据面积状态判断对应面积是否存在，不存在的只能按总价定价
//		if(selltt != null){
//			if(SellTypeEnum.PlanningSell.equals(selltt)){  //规划
//				if(room.getPlanRoomArea()==null || room.getPlanBuildingArea()==null){
//					row.getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseStandPrice);
//					priceBillType = PriceTypeForPriceBillEnum.UseStandPrice;
//				}
//			}
//			else if(SellTypeEnum.PreSell.equals(selltt)){  //预售，取预售面积
//				if(room.getRoomArea()==null || room.getBuildingArea()==null){
//					row.getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseStandPrice);
//					priceBillType = PriceTypeForPriceBillEnum.UseStandPrice;
//				}
//			}
//			else{  //现售
//				if(room.getActualRoomArea()==null || room.getActualBuildingArea()==null){
//					row.getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseStandPrice);
//					priceBillType = PriceTypeForPriceBillEnum.UseStandPrice;
//				}
//			}
//		}
		
		if (PriceTypeForPriceBillEnum.UseBuildingArea.equals(priceBillType)) {
			row.getCell("newBuildingPrice").getStyleAttributes().setLocked(false);
			row.getCell("newRoomPrice").getStyleAttributes().setLocked(true);

		} else if (PriceTypeForPriceBillEnum.UseRoomArea.equals(priceBillType)) {
			row.getCell("newBuildingPrice").getStyleAttributes().setLocked(true);
			row.getCell("newRoomPrice").getStyleAttributes().setLocked(false);
		} else if (PriceTypeForPriceBillEnum.UseStandPrice.equals(priceBillType)) {
			row.getCell("newBuildingPrice").getStyleAttributes().setLocked(true);
			row.getCell("newRoomPrice").getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * 增加楼栋页签，并将数据追加到总览页签
	 * 
	 * @throws BOSException
	 */
	private void addBuildingTab(BuildingInfo[] buildingArray)
			throws BOSException {
		if (buildingArray == null || buildingArray.length == 0) {
			return;
		}
		for (int i = 0; i < buildingArray.length; i++) {
			BuildingInfo building = buildingArray[i];
			// 获取楼栋下的房间
			RoomCollection roomCollection = this.getRoomCollection(building.getId().toString(), null);
			if(roomCollection==null || roomCollection.isEmpty()){
				FDCMsgBox.showInfo("楼栋"+building.getName()+"没有房间，已忽略");
				continue;
			}
			
			//处理已有楼栋页签，若不在选择的楼栋里，则删除
			for (int t = 1; t < this.kDTabbedPanel.getTabCount(); t++) {
				KDPanel panel = (KDPanel) this.kDTabbedPanel.getComponentAt(t);
				KDTable table = (KDTable) panel.getComponent(0);
				if(selectedBuildingMap.get(table.getName()) == null){
					//先删除总览中对应的房间
					for(int r=0; r<table.getRowCount(); r++){
						if(table.getRow(r).getUserObject() == null){
							continue;
						}
						this.updateViewRowData(table.getRow(r), true);
					}
					this.kDTabbedPanel.remove(t);
				}
			}
			//如果楼栋页签已存在，则追加房间，否则创建新的楼栋页签
			KDTable buildingTable = null;
			if(this.kDTabbedPanel.getTabCount() > 1){
				boolean isExist = false;
				for(int t=1; t<this.kDTabbedPanel.getTabCount(); t++){
					KDPanel panel = (KDPanel) this.kDTabbedPanel.getComponentAt(t);
					KDTable table = (KDTable) panel.getComponent(0);
					if(table.getName().equals(building.getId().toString())){
						buildingTable = table;
						isExist = true;
						break;
					}
				}
				if(!isExist){
					// 创建楼栋页签
					buildingTable = this.createBuildingTable(building.getName(), building.getId().toString());
				}
			}
			else{
				// 创建楼栋页签
				buildingTable = this.createBuildingTable(building.getName(), building.getId().toString());
			}
			
			// 填充楼栋表格
			this.addTableRow(buildingTable, roomCollection);
			
			//如果楼栋下没有房间，则删除页签，最后一个页签即为新增的页签
			this.updateBuildingTab(this.kDTabbedPanel.getTabCount()-1, buildingTable);
			
			// 追加总览表格
			this.addTableRow(this.kdtPriceAdjustEntry, roomCollection);
			
			this.addTotal(buildingTable);
			this.addTotal(this.kdtPriceAdjustEntry);
			
			this.setStatPrice();
			
		}
		
	}

	/**
	 * 创建楼栋页签
	 * 
	 * @param panelName
	 *            - 面板名称
	 * @param tableName
	 *            - 楼栋表格名称
	 * @return
	 */
	private KDTable createBuildingTable(String panelName, String tableName) {
		KDPanel buildingPanel = new KDPanel();
		buildingPanel.setName(panelName);

		KDTable buildingTable = new KDTable();
		buildingTable.setName(tableName);

		// 表格格式与总览一样
		String kdtPriceAdjustEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"roomId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"buildUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"Salesareatype\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"priceType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"oldSumAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"newSumAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"oldBaseStandardPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"newBaseStandardPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"oldSceneManagerAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"newSceneManagerAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"oldSalesDirectorAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"newSalesDirectorAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"oldManagerAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"newManagerAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"oldBuildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"newBuildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"oldRoomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"newRoomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{roomId}</t:Cell><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{buildUnit}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{Salesareatype}</t:Cell><t:Cell>$Resource{priceType}</t:Cell><t:Cell>$Resource{oldSumAmount}</t:Cell><t:Cell>$Resource{newSumAmount}</t:Cell><t:Cell>$Resource{oldBaseStandardPrice}</t:Cell><t:Cell>$Resource{newBaseStandardPrice}</t:Cell><t:Cell>$Resource{oldSceneManagerAgio}</t:Cell><t:Cell>$Resource{newSceneManagerAgio}</t:Cell><t:Cell>$Resource{oldSalesDirectorAgio}</t:Cell><t:Cell>$Resource{newSalesDirectorAgio}</t:Cell><t:Cell>$Resource{oldManagerAgio}</t:Cell><t:Cell>$Resource{newManagerAgio}</t:Cell><t:Cell>$Resource{oldBuildingPrice}</t:Cell><t:Cell>$Resource{newBuildingPrice}</t:Cell><t:Cell>$Resource{oldRoomPrice}</t:Cell><t:Cell>$Resource{newRoomPrice}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
//		String kdtPriceAdjustEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"roomId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"buildUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"Salesareatype\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"priceType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"oldSumAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"newSumAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"oldBaseStandardPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"newBaseStandardPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"oldManagerAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"newManagerAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"oldBuildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"newBuildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"oldRoomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"newRoomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{roomId}</t:Cell><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{buildUnit}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{Salesareatype}</t:Cell><t:Cell>$Resource{priceType}</t:Cell><t:Cell>$Resource{oldSumAmount}</t:Cell><t:Cell>$Resource{newSumAmount}</t:Cell><t:Cell>$Resource{oldBaseStandardPrice}</t:Cell><t:Cell>$Resource{newBaseStandardPrice}</t:Cell><t:Cell>$Resource{oldManagerAgio}</t:Cell><t:Cell>$Resource{newManagerAgio}</t:Cell><t:Cell>$Resource{oldBuildingPrice}</t:Cell><t:Cell>$Resource{newBuildingPrice}</t:Cell><t:Cell>$Resource{oldRoomPrice}</t:Cell><t:Cell>$Resource{newRoomPrice}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
		buildingTable.setFormatXml(resHelper.translateString("kdtPriceAdjustEntry",kdtPriceAdjustEntryStrXML));
       
//		buildingTable.setFormatXml(this.kdtPriceAdjustEntry.getFormatXml());
//		buildingTable.setBounds(new Rectangle(1, 1, 986, 265));
		
		buildingPanel.setLayout(new BorderLayout(0, 0));       
		buildingPanel.add(buildingTable, BorderLayout.CENTER);

		buildingTable.checkParsed();
		
		//设置右键菜单
		TableListPreferencesHelper tblHelper =  new TableListPreferencesHelper(this);
		tblHelper.addMenuToTable(buildingTable);

		// 锁定列，只供查看
		String[] colNameArray = new String[] { "roomId", "buildUnit","building", "roomNo",
				"oldSumAmount", "buildingArea", "oldBuildingPrice", "roomArea",
				"oldRoomPrice","oldBaseStandardPrice" };
		this.setColumnLockState(buildingTable, colNameArray, true);
		
		// 初始化列的编辑器
		this.initTableColumn(buildingTable);

		// 将表格添加至面板
//		buildingPanel.setLayout(new KDLayout());
//		buildingPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0,
//				992, 297));
//		buildingPanel.add(buildingTable, new KDLayout.Constraints(1, 1, 986,
//				265, 0));

		// 先将楼栋页签加到总页签中，再设置监听器
		this.kDTabbedPanel.add(buildingPanel, panelName);

		// 为表格添加编辑监听器
		buildingTable.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				// 最后一个为新添加的页签
				KDPanel panel = (KDPanel) kDTabbedPanel
						.getComponentAt(kDTabbedPanel.getTabCount() - 1);
				KDTable table = (KDTable) panel.getComponent(0);
				// 更新价格的同时，更新总览页签的数据
				table_editStopped(table, e, UPDATE_VIEW);
			}
		});

		//隐藏列
		this.setColHideState(buildingTable);
		
		return buildingTable;
	}

	/**
	 * 填充页签表格，绑定定价分录实体
	 * 
	 * @param roomCollection
	 *            - 房间集合
	 */
	private void addTableRow(KDTable table, RoomCollection roomCollection) {
		if (roomCollection == null || roomCollection.isEmpty()) {
			return;
		}
		for (int i = 0; i < roomCollection.size(); i++) {
			
			RoomInfo room = roomCollection.get(i);
			//检查房间底价
			if(!this.isAdjustable(room)){
				continue;
			}
			//不改动已有的房间
			if(this.isExist(table, room)){
				continue;
			}

			IRow row = table.addRow();

			RoomPriceAdjustEntryInfo priceAdjustEntry = this.setPriceEntryInfo(room);
			row.setUserObject(priceAdjustEntry);
			
			this.lockCellByPriceType(row, priceAdjustEntry.getPriceType());
			
			if(priceAdjustEntry.getSellType() == null){
				row.getCell("priceType").getStyleAttributes().setLocked(true);
			}
			this.setRowData(row, priceAdjustEntry);
			this.dealRowData(row);
		}
	}

	/**
	 * 根据房间信息，设置定价分录
	 * 
	 * @param room
	 *            - 房间信息
	 * @return 定价分录
	 */
	private RoomPriceAdjustEntryInfo setPriceEntryInfo(RoomInfo room) {
		RoomPriceAdjustEntryInfo priceAdjustEntry = new RoomPriceAdjustEntryInfo();
		priceAdjustEntry.setRoom(room);
		priceAdjustEntry.setPriceType((PriceTypeForPriceBillEnum) this.comboPriceBillType.getSelectedItem());
//		priceAdjustEntry.setSalesareatype((SellAreaTypeEnum) this.comboalesareatype.getSelectedItem());
		if(room.getActualBuildingArea()!=null && room.getActualRoomArea() != null){
//			priceAdjustEntry.setSellType(SellTypeEnum.LocaleSell);
//			priceAdjustEntry.setSalesareatype(SellAreaTypeEnum.ACTUAL);
			priceAdjustEntry.setOldBuildingArea(room.getActualBuildingArea());
			priceAdjustEntry.setNewBuildingArea(room.getActualBuildingArea());
			
			priceAdjustEntry.setOldRoomArea(room.getActualRoomArea());
			priceAdjustEntry.setNewRoomArea(room.getActualRoomArea());
		}
		else if(room.getBuildingArea()!=null && room.getRoomArea() != null){
//			priceAdjustEntry.setSellType(SellTypeEnum.PreSell);
//			priceAdjustEntry.setSalesareatype(SellAreaTypeEnum.PRESELL);
			priceAdjustEntry.setOldBuildingArea(room.getBuildingArea());
			priceAdjustEntry.setNewBuildingArea(room.getBuildingArea());
			
			priceAdjustEntry.setOldRoomArea(room.getRoomArea());
			priceAdjustEntry.setNewRoomArea(room.getRoomArea());
		}
		else if(room.getPlanBuildingArea()!=null && room.getPlanRoomArea() != null){
//			priceAdjustEntry.setSellType(SellTypeEnum.PlanningSell);
//			priceAdjustEntry.setSalesareatype(SellAreaTypeEnum.PLANNING);
			priceAdjustEntry.setOldBuildingArea(room.getPlanBuildingArea());
			priceAdjustEntry.setNewBuildingArea(room.getPlanBuildingArea());
			
			priceAdjustEntry.setOldRoomArea(room.getPlanRoomArea());
			priceAdjustEntry.setNewRoomArea(room.getPlanRoomArea());
		}
		else{  //房间所有面积为空，只能按总价定价
//			priceAdjustEntry.setSellType(null);
			priceAdjustEntry.setOldBuildingArea(FDCHelper.ZERO);
			priceAdjustEntry.setNewBuildingArea(FDCHelper.ZERO);
			
			priceAdjustEntry.setOldRoomArea(FDCHelper.ZERO);
			priceAdjustEntry.setNewRoomArea(FDCHelper.ZERO);
			
			priceAdjustEntry.setPriceType(PriceTypeForPriceBillEnum.UseStandPrice);
		}
		if(room.getSellAreaType()!=null){
			if(room.getSellAreaType().equals(SellAreaTypeEnum.PRESELL)){
				priceAdjustEntry.setSellType(SellTypeEnum.PreSell);
				priceAdjustEntry.setOldBuildingArea(room.getBuildingArea());
				priceAdjustEntry.setNewBuildingArea(room.getBuildingArea());
				priceAdjustEntry.setOldRoomArea(room.getRoomArea());
				priceAdjustEntry.setNewRoomArea(room.getRoomArea());
				
			}else if(room.getSellAreaType().equals(SellAreaTypeEnum.PLANNING)){
				priceAdjustEntry.setSellType(SellTypeEnum.PlanningSell);
				priceAdjustEntry.setOldBuildingArea(room.getPlanBuildingArea());
				priceAdjustEntry.setNewBuildingArea(room.getPlanBuildingArea());
				priceAdjustEntry.setOldRoomArea(room.getPlanRoomArea());
				priceAdjustEntry.setNewRoomArea(room.getPlanRoomArea());
			}else if(room.getSellAreaType().equals(SellAreaTypeEnum.ACTUAL)){
				priceAdjustEntry.setSellType(SellTypeEnum.LocaleSell);
				priceAdjustEntry.setOldBuildingArea(room.getActualBuildingArea());
				priceAdjustEntry.setNewBuildingArea(room.getActualBuildingArea());
				priceAdjustEntry.setOldRoomArea(room.getActualRoomArea());
				priceAdjustEntry.setNewRoomArea(room.getActualRoomArea());
			}
		}else{  //房间所有面积为空，只能按总价定价
			priceAdjustEntry.setSellType(null);
			priceAdjustEntry.setOldBuildingArea(FDCHelper.ZERO);
			priceAdjustEntry.setNewBuildingArea(FDCHelper.ZERO);
			
			priceAdjustEntry.setOldRoomArea(FDCHelper.ZERO);
			priceAdjustEntry.setNewRoomArea(FDCHelper.ZERO);
			
			priceAdjustEntry.setPriceType(PriceTypeForPriceBillEnum.UseStandPrice);
		}
		priceAdjustEntry.setOldSumAmount(room.getStandardTotalAmount());
		priceAdjustEntry.setNewSumAmount(room.getStandardTotalAmount());
		priceAdjustEntry.setOldBuildingPrice(room.getBuildPrice());
		priceAdjustEntry.setNewBuildingPrice(room.getBuildPrice());
		priceAdjustEntry.setOldRoomPrice(room.getRoomPrice());
		priceAdjustEntry.setNewRoomPrice(room.getRoomPrice());

		priceAdjustEntry.setNewBaseStandardPrice(room.getBaseStandardPrice());
		priceAdjustEntry.setOldBaseStandardPrice(room.getBaseStandardPrice());
		
		priceAdjustEntry.setNewManagerAgio(room.getManagerAgio());
		priceAdjustEntry.setOldManagerAgio(room.getManagerAgio());
		
		priceAdjustEntry.setNewSalesDirectorAgio(room.getSalesDirectorAgio());
		priceAdjustEntry.setOldSalesDirectorAgio(room.getSalesDirectorAgio());
		
		priceAdjustEntry.setNewSceneManagerAgio(room.getSceneManagerAgio());
		priceAdjustEntry.setOldSceneManagerAgio(room.getSceneManagerAgio());
		return priceAdjustEntry;
	}

	/**
	 * 填充行数据
	 * 
	 * @param row
	 *            - 填充行
	 * @param room
	 *            - 房间信息
	 */
	private void setRowData(IRow row, RoomPriceAdjustEntryInfo priceAdjustEntry) {
		RoomInfo room = priceAdjustEntry.getRoom();
		row.getCell("roomId").setValue(room.getId());
//		row.getCell("roomNumber").setValue(room.getNumber());
		row.getCell("roomNo").setValue(room.getDisplayName());
		row.getCell("building").setValue(room.getBuilding().getName());
		if(room.getBuildUnit()!=null){
			row.getCell("buildUnit").setValue(room.getBuildUnit().getName());
		}
//		row.getCell("sellType").setValue(priceAdjustEntry.getSellType());
		row.getCell("priceType").setValue(priceAdjustEntry.getPriceType()); 
		row.getCell("oldSumAmount").setValue(priceAdjustEntry.getOldSumAmount());
		row.getCell("newSumAmount").setValue(priceAdjustEntry.getNewSumAmount());
		row.getCell("buildingArea").setValue(priceAdjustEntry.getNewBuildingArea());
		row.getCell("oldBuildingPrice").setValue(priceAdjustEntry.getOldBuildingPrice());
		row.getCell("newBuildingPrice").setValue(priceAdjustEntry.getNewBuildingPrice());
		row.getCell("roomArea").setValue(priceAdjustEntry.getNewRoomArea());
		row.getCell("oldRoomPrice").setValue(priceAdjustEntry.getOldRoomPrice());
		row.getCell("newRoomPrice").setValue(priceAdjustEntry.getNewRoomPrice());
		row.getCell("Salesareatype").setValue(getSellAreaTypeEnum(priceAdjustEntry.getSellType()));  //add by  shilei
		
		
		row.getCell("oldBaseStandardPrice").setValue(priceAdjustEntry.getOldBaseStandardPrice());
		row.getCell("newManagerAgio").setValue(priceAdjustEntry.getNewManagerAgio());
		row.getCell("newBaseStandardPrice").setValue(priceAdjustEntry.getNewBaseStandardPrice());
		row.getCell("oldManagerAgio").setValue(priceAdjustEntry.getOldManagerAgio());
		
		row.getCell("oldSalesDirectorAgio").setValue(priceAdjustEntry.getOldSalesDirectorAgio());
		row.getCell("newSalesDirectorAgio").setValue(priceAdjustEntry.getNewSalesDirectorAgio());
		row.getCell("oldSceneManagerAgio").setValue(priceAdjustEntry.getOldSceneManagerAgio());
		row.getCell("newSceneManagerAgio").setValue(priceAdjustEntry.getNewSceneManagerAgio());
		
		//是否调整已售房源，若不调整，则锁定行
		RoomSellStateEnum sellState = room.getSellState();
		if(RoomSellStateEnum.PrePurchase.equals(sellState) || RoomSellStateEnum.Purchase.equals(sellState) 
				|| RoomSellStateEnum.Sign.equals(sellState)){
			this.checkSoldRoomAdjustable();
			
			if(isSelectAdjustSoldRoom && !isAdjustSoldRoom){
				row.getStyleAttributes().setLocked(true);
			}
		}
	}

	/**
	 * 获取房间集合
	 * 
	 * @param buildingId
	 *            - 楼栋Id
	 * @param roomIdSet
	 *            - 房间Id集合
	 * @return 房间信息集合
	 * @throws BOSException
	 */
	private RoomCollection getRoomCollection(String buildingId, Set roomIdSet)
			throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("direction.*");
		view.getSelector().add("sellOrder.*");
		view.getSelector().add("newSight.*");
		view.getSelector().add("roomModel.*");
		view.getSelector().add("buildingProperty.*");
		view.getSelector().add("newDecorastd.*");
		view.getSelector().add("newPossStd.*");
		view.getSelector().add("newNoise.*");
		view.getSelector().add("newEyeSignht.*");
		view.getSelector().add("productDetail.*");
		view.getSelector().add("newProduceRemark.*");
		view.getSelector().add("building.*");
		view.getSelector().add("building.sellProject.*");
		view.getSelector().add("building.units.*");
		view.getSelector().add("buildUnit.name");
		view.getSelector().add("buildUnit.number");
		view.getSelector().add("buildUnit.seq");
		view.getSorter().add(new SorterItemInfo("unit"));
		view.getSorter().add(new SorterItemInfo("floor"));
		view.getSorter().add(new SorterItemInfo("number"));
		
		FilterInfo filter = new FilterInfo();
		if (buildingId != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
		}

		if (roomIdSet != null && !roomIdSet.isEmpty()) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", roomIdSet, CompareType.INCLUDE));
		}

		view.setFilter(filter);

		RoomCollection rooms = RoomFactory.getRemoteInstance()
				.getRoomCollection(view);
//		CRMHelper.sortCollection(rooms, "displayname", true);

		return rooms;
	}

	/**
	 * 获取楼栋信息
	 * @param buildingId
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private BuildingInfo getBuildingInfo(String buildingId) throws EASBizException, BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", buildingId));
		view.setFilter(filter);
		
		BuildingInfo building = BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(buildingId));
		
		return building;
	}
	
	/**
	 * 根据楼栋分录中的楼栋，从头实体获取房间分录
	 * 
	 * @param building
	 *            - 楼栋分录中的楼栋
	 * @return 已定价的房间分录
	 */
	private RoomPriceAdjustEntryCollection getPriceEntryByBuilding(
			BuildingInfo building) {
		RoomPriceAdjustEntryCollection entryCol = new RoomPriceAdjustEntryCollection();
		// 获取指定楼栋下的定价分录
		RoomPriceAdjustEntryCollection priceAdjustEntryCol = this.editData.getPriceAdjustEntry();
		if (priceAdjustEntryCol != null && !priceAdjustEntryCol.isEmpty()) {
			for (int i = 0; i < priceAdjustEntryCol.size(); i++) {
				RoomPriceAdjustEntryInfo entry = priceAdjustEntryCol.get(i);
				RoomInfo room = entry.getRoom();
				if (room.getBuilding().getId().toString().equals(building.getId().toString())) {
					entryCol.add(entry);
				}
			}
		}

		return entryCol;
	}

	/**
	 * 设置表格指定列的锁定状态
	 * 
	 * @param table
	 *            - 表名
	 * @param colNameArray
	 *            - 列名数组
	 * @param isLock
	 *            - 是否锁定
	 */
	private void setColumnLockState(KDTable table, String[] colNameArray,
			boolean isLock) {
		if (colNameArray == null || colNameArray.length == 0) {
			return;
		} else {
			for (int i = 0; i < colNameArray.length; i++) {
				table.getColumn(colNameArray[i]).getStyleAttributes()
						.setLocked(isLock);
			}
		}
	}
	/**
	 * 表格编辑结束后，更新相关价格信息
	 * 
	 * @param table
	 *            - 当前表格
	 * @param e
	 *            - 表格编辑事件
	 * @param e
	 *            - 更新标志
	 */
	private void table_editStopped(KDTable table, KDTEditEvent e, int updateFlag) {
		table=(KDTable) e.getSource();
		int rowIndex = e.getRowIndex();
		IRow currentRow = table.getRow(rowIndex);
		if(currentRow.getUserObject() == null){
			return;
		}
		RoomPriceAdjustEntryInfo priceEntry = (RoomPriceAdjustEntryInfo)currentRow.getUserObject();

//		SellTypeEnum sellType = (SellTypeEnum)table.getRow(rowIndex).getCell("sellType").getValue();
		PriceTypeForPriceBillEnum priceBillType = (PriceTypeForPriceBillEnum) table.getRow(rowIndex).getCell("priceType").getValue();
		SellAreaTypeEnum sellt = (SellAreaTypeEnum) table.getRow(rowIndex).getCell("Salesareatype").getValue();  //add by shilei
		BigDecimal sumAmount = KDTableTools.getBigDecimal(table.getRow(rowIndex).getCell("newSumAmount").getValue());
		BigDecimal buildingArea = KDTableTools.getBigDecimal(table.getRow(rowIndex).getCell("buildingArea").getValue());
		BigDecimal buildingPrice = KDTableTools.getBigDecimal(table.getRow(rowIndex).getCell("newBuildingPrice").getValue());
		BigDecimal roomArea = KDTableTools.getBigDecimal(table.getRow(rowIndex).getCell("roomArea").getValue());
		BigDecimal roomPrice = KDTableTools.getBigDecimal(table.getRow(rowIndex).getCell("newRoomPrice").getValue());

		//销售面积类型变更，变更建筑和套内面积
		if(e.getColIndex() == table.getRow(rowIndex).getCell("Salesareatype").getColumnIndex()
				&& table.getRow(rowIndex).getCell("Salesareatype").getValue()!=null){
			RoomInfo room = priceEntry.getRoom();
			if(SellAreaTypeEnum.PLANNING.equals(sellt)){  //规划，取预估面积
				if(room.getPlanRoomArea()!=null && room.getPlanBuildingArea()!=null){
					table.getRow(rowIndex).getCell("buildingArea").setValue(room.getPlanBuildingArea());
					table.getRow(rowIndex).getCell("roomArea").setValue(room.getPlanRoomArea());
				}
				else{
					table.getRow(rowIndex).getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseStandPrice);
					table.getRow(rowIndex).getCell("buildingArea").setValue(FDCHelper.ZERO);
					table.getRow(rowIndex).getCell("newBuildingPrice").setValue(FDCHelper.ZERO);
					table.getRow(rowIndex).getCell("roomArea").setValue(FDCHelper.ZERO);
					table.getRow(rowIndex).getCell("newRoomPrice").setValue(FDCHelper.ZERO);
					
					table.getRow(rowIndex).getCell("newBaseStandardPrice").setValue(FDCHelper.ZERO);
					table.getRow(rowIndex).getCell("newManagerAgio").setValue(FDCHelper.ZERO);
				}
			}
			else if(SellAreaTypeEnum.PRESELL.equals(sellt)){  //预售，取预售面积
				if(room.getRoomArea()!=null && room.getBuildingArea()!=null){
					table.getRow(rowIndex).getCell("buildingArea").setValue(room.getBuildingArea());
					table.getRow(rowIndex).getCell("roomArea").setValue(room.getRoomArea());
				}
				else{
					table.getRow(rowIndex).getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseStandPrice);
					table.getRow(rowIndex).getCell("buildingArea").setValue(FDCHelper.ZERO);
					table.getRow(rowIndex).getCell("newBuildingPrice").setValue(FDCHelper.ZERO);
					table.getRow(rowIndex).getCell("roomArea").setValue(FDCHelper.ZERO);
					table.getRow(rowIndex).getCell("newRoomPrice").setValue(FDCHelper.ZERO);
					
					table.getRow(rowIndex).getCell("newBaseStandardPrice").setValue(FDCHelper.ZERO);
					table.getRow(rowIndex).getCell("newManagerAgio").setValue(FDCHelper.ZERO);
				}
			}
			else{  //现售，取实测面积
				if(room.getActualRoomArea()!=null && room.getActualBuildingArea()!=null){
					table.getRow(rowIndex).getCell("buildingArea").setValue(room.getActualBuildingArea());
					table.getRow(rowIndex).getCell("roomArea").setValue(room.getActualRoomArea());
				}
				else{
					table.getRow(rowIndex).getCell("priceType").setValue(PriceTypeForPriceBillEnum.UseStandPrice);
					table.getRow(rowIndex).getCell("buildingArea").setValue(FDCHelper.ZERO);
					table.getRow(rowIndex).getCell("newBuildingPrice").setValue(FDCHelper.ZERO);
					table.getRow(rowIndex).getCell("roomArea").setValue(FDCHelper.ZERO);
					table.getRow(rowIndex).getCell("newRoomPrice").setValue(FDCHelper.ZERO);
					
					table.getRow(rowIndex).getCell("newBaseStandardPrice").setValue(FDCHelper.ZERO);
					table.getRow(rowIndex).getCell("newManagerAgio").setValue(FDCHelper.ZERO);
				}
			}
			this.lockCellByPriceType(currentRow, priceBillType);
		}
		//定价类型变更，锁定相关列
		 if(e.getColIndex() == table.getRow(rowIndex).getCell("priceType").getColumnIndex()){
			this.lockCellByPriceType(currentRow, priceBillType);
		}
		
		// 不管是哪种定价类型，只要修改总价，处理方法都一样，故单独抽取出来
		 if (e.getColIndex() == table.getColumnIndex("newSumAmount") && sumAmount!=null) {
			 BigDecimal checkBuildingAmount = null;
			 BigDecimal checkRoomAmount = null;
			 BigDecimal newBuildingPrice = null;
			 BigDecimal newRoomPrice = null;
			 BigDecimal newManagerAgio =null;
			 // 新建筑单价和套内单价
			 if(roomArea != null && roomArea.compareTo(FDCHelper.ZERO) != 0){
				 newRoomPrice = sumAmount.divide(roomArea, this.paramsArray[0], this.paramsArray[1]);
				 table.getRow(rowIndex).getCell("newRoomPrice").setValue(newRoomPrice);
			 }
			 else{
				 table.getRow(rowIndex).getCell("newRoomPrice").setValue(FDCHelper.ZERO);
			 }
			 if(buildingArea != null && buildingArea.compareTo(FDCHelper.ZERO) != 0){
				 newBuildingPrice = sumAmount.divide(buildingArea, this.paramsArray[0], this.paramsArray[1]);
				 table.getRow(rowIndex).getCell("newBuildingPrice").setValue(newBuildingPrice);
			 }
			 else{
				 table.getRow(rowIndex).getCell("newBuildingPrice").setValue(FDCHelper.ZERO);
			 }
			 if(buildingArea != null && buildingArea.compareTo(FDCHelper.ZERO) != 0){
				 checkBuildingAmount = buildingArea.multiply(newBuildingPrice);
			 }
			 if(roomArea != null && roomArea.compareTo(FDCHelper.ZERO) != 0){
				 checkRoomAmount = roomArea.multiply(newRoomPrice);
			 }
			 /*if(checkBuildingAmount.compareTo(sumAmount)!=0 || checkRoomAmount.compareTo(sumAmount)!=0){
				FDCMsgBox.showInfo("修改后，面积和单价的乘积跟总价不相等");
			}*/
			 
			 BigDecimal totalsumAmount = KDTableTools.getBigDecimal(table.getRow(rowIndex).getCell("newSumAmount").getValue());
			 BigDecimal baseStandardPrice = KDTableTools.getBigDecimal(table.getRow(rowIndex).getCell("newBaseStandardPrice").getValue());
			 if(baseStandardPrice!=null&&totalsumAmount!=null&&totalsumAmount.compareTo(FDCHelper.ZERO)!=0){
				 table.getRow(rowIndex).getCell("newManagerAgio").setValue(baseStandardPrice.divide(totalsumAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
			 }else{
				 table.getRow(rowIndex).getCell("newManagerAgio").setValue(FDCHelper.ZERO);
			 }
		 }
		else if(e.getColIndex() != table.getRow(rowIndex).getCell("newManagerAgio").getColumnIndex()){
			this.dealRowData(currentRow);
		}
		 
		//检查值是否发生改变
		this.checkDataChanged(currentRow);
		
		priceEntry.setSellType(getSellTypeEnum((SellAreaTypeEnum)currentRow.getCell("Salesareatype").getValue()));  
		priceEntry.setPriceType((PriceTypeForPriceBillEnum) currentRow.getCell("priceType").getValue());
//		priceEntry.setSalesareatype((SellAreaTypeEnum)currentRow.getCell("Salesareatype").getValue());  //add by shilei
		priceEntry.setNewSumAmount((BigDecimal) currentRow.getCell("newSumAmount").getValue());
		priceEntry.setNewBuildingArea((BigDecimal) currentRow.getCell("buildingArea").getValue());
		priceEntry.setNewBuildingPrice((BigDecimal) currentRow.getCell("newBuildingPrice").getValue());
		priceEntry.setNewRoomArea((BigDecimal) currentRow.getCell("roomArea").getValue());
		priceEntry.setNewRoomPrice((BigDecimal) currentRow.getCell("newRoomPrice").getValue());
		
		priceEntry.setNewBaseStandardPrice((BigDecimal) currentRow.getCell("newBaseStandardPrice").getValue());
		priceEntry.setNewManagerAgio((BigDecimal) currentRow.getCell("newManagerAgio").getValue());
		priceEntry.setNewSalesDirectorAgio((BigDecimal) currentRow.getCell("newSalesDirectorAgio").getValue());
		priceEntry.setNewSceneManagerAgio((BigDecimal) currentRow.getCell("newSceneManagerAgio").getValue());
		
//		currentRow.getStyleAttributes().setBackground(Color.GREEN);
		if (this.UPDATE_VIEW == updateFlag) {
			this.updateViewRowData(table.getRow(e.getRowIndex()), false);
			this.addTotal(table);
		} else if (this.UPDATE_BUILDING == updateFlag) {
			KDTable retTable = this.updateBuildingRowData(table.getRow(e.getRowIndex()), false);
			if(retTable != null){
				this.addTotal(retTable);
			}
		}
		
		this.addTotal(this.kdtPriceAdjustEntry);

		// 修改主表价格
		this.setStatPrice();
		
		KDTableUtil.setSelectedRow(table, e.getRowIndex() + 1);
	}
	
	/**
	 * 检查价格是否发生变化
	 * @return
	 */
	private void checkDataChanged(IRow row){
		RoomPriceAdjustEntryInfo priceEntry = (RoomPriceAdjustEntryInfo)row.getUserObject();
		if(priceEntry==null) return;
//		SellTypeEnum sellType = (SellTypeEnum)row.getCell("sellType").getValue();
		PriceTypeForPriceBillEnum priceBillType = (PriceTypeForPriceBillEnum)row.getCell("priceType").getValue();
		SellAreaTypeEnum sellt =(SellAreaTypeEnum)row.getCell("Salesareatype").getValue();   //add by shilei
		BigDecimal sumAmount = KDTableTools.getBigDecimal(row.getCell("newSumAmount").getValue());
		BigDecimal buildingPrice = KDTableTools.getBigDecimal(row.getCell("newBuildingPrice").getValue());
		BigDecimal roomPrice = KDTableTools.getBigDecimal(row.getCell("newRoomPrice").getValue());
		
		BigDecimal baseStandardPrice = KDTableTools.getBigDecimal(row.getCell("newBaseStandardPrice").getValue());
		
		BigDecimal newSalesDirectorAgio = KDTableTools.getBigDecimal(row.getCell("newSalesDirectorAgio").getValue());
		BigDecimal newSceneManagerAgio = KDTableTools.getBigDecimal(row.getCell("newSceneManagerAgio").getValue());
			
		
		//值是否改变
		if(sellt!=null && !sellt.getAlias().equals(priceEntry.getSellType().getAlias())){
			priceEntry.setModyfied(true);
		}
		if(priceEntry.getPriceType()!=null && !priceBillType.equals(priceEntry.getPriceType())){
			priceEntry.setModyfied(true);
		}
		if(sumAmount!=null && sumAmount.compareTo(FDCHelper.ZERO)!=0 && priceEntry.getNewSumAmount()==null
				|| sumAmount==null && priceEntry.getNewSumAmount()!=null){
			priceEntry.setModyfied(true);
		}
		if(sumAmount != null && priceEntry.getNewSumAmount()!=null){
			if(priceEntry.getNewSumAmount().compareTo(sumAmount) != 0){
				priceEntry.setModyfied(true);
			}
		}
		if(buildingPrice!=null && buildingPrice.compareTo(FDCHelper.ZERO)!=0 && priceEntry.getNewBuildingPrice()==null
				|| buildingPrice==null && priceEntry.getNewBuildingPrice()!=null){
			priceEntry.setModyfied(true);
		}
		if(buildingPrice != null && priceEntry.getNewBuildingPrice()!=null){
			if(priceEntry.getNewBuildingPrice().compareTo(buildingPrice) != 0){
				priceEntry.setModyfied(true);
			}
		}
		if(roomPrice!=null && roomPrice.compareTo(FDCHelper.ZERO)!=0 && priceEntry.getNewRoomPrice()==null
				|| roomPrice==null && priceEntry.getNewRoomPrice()!=null){
			priceEntry.setModyfied(true);
		}
		if(roomPrice != null && priceEntry.getNewRoomPrice()!=null){
			if(priceEntry.getNewRoomPrice().compareTo(roomPrice) != 0){
				priceEntry.setModyfied(true);
			}
		}
		if(baseStandardPrice!=null && baseStandardPrice.compareTo(FDCHelper.ZERO)!=0 && priceEntry.getNewBaseStandardPrice()==null
				|| baseStandardPrice==null && priceEntry.getNewBaseStandardPrice()!=null){
			priceEntry.setModyfied(true);
		}
		if(baseStandardPrice != null && priceEntry.getNewBaseStandardPrice()!=null){
			if(priceEntry.getNewBaseStandardPrice().compareTo(baseStandardPrice) != 0){
				priceEntry.setModyfied(true);
			}
		}
		if(newSalesDirectorAgio != null && priceEntry.getNewSalesDirectorAgio()!=null){
			if(priceEntry.getNewSalesDirectorAgio().compareTo(newSalesDirectorAgio) != 0){
				priceEntry.setModyfied(true);
			}
		}
		if(newSceneManagerAgio != null && priceEntry.getNewSceneManagerAgio()!=null){
			if(priceEntry.getNewSceneManagerAgio().compareTo(newSceneManagerAgio) != 0){
				priceEntry.setModyfied(true);
			}
		}
		if(priceEntry.isModyfied()){
			row.getStyleAttributes().setBackground(new Color(200, 255, 160));
		}else{
			row.getStyleAttributes().setBackground(Color.WHITE);
		}
	}

	private void dealRowData(IRow row) {
		if (!(row.getCell("priceType").getValue() instanceof PriceTypeForPriceBillEnum))
			return;

		PriceTypeForPriceBillEnum priceTypeEnum = (PriceTypeForPriceBillEnum) row.getCell("priceType").getValue();

		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal roomArea = (BigDecimal) row.getCell("roomArea").getValue(); // 套内面积
		if (roomArea == null)
			roomArea = new BigDecimal(0);

		BigDecimal buildingArea = (BigDecimal) row.getCell("buildingArea").getValue(); // 建筑面积
		if (buildingArea == null)
			buildingArea = new BigDecimal(0);

		if (row.getCell("newRoomPrice").getValue() == null) {
			row.getCell("newRoomPrice").setValue(new BigDecimal(0));
		}
		if (row.getCell("newBuildingPrice").getValue() == null) {
			row.getCell("newBuildingPrice").setValue(new BigDecimal(0));
		}

		if (priceTypeEnum.equals(PriceTypeForPriceBillEnum.UseBuildingArea)) {
			// 按建筑面积定价 标准总价 = 建筑面积×建筑单价
			// 套内单价 = （标准总价）/套内面积
			amount = buildingArea.multiply((BigDecimal) row.getCell("newBuildingPrice").getValue());
			if (roomArea.compareTo(FDCHelper.ZERO) != 0)
				row.getCell("newRoomPrice").setValue(amount.divide(roomArea, this.paramsArray[0], this.paramsArray[1]));

			row.getCell("newSumAmount").setValue(amount.setScale(this.paramsArray[2], this.paramsArray[3]));
		} else if (priceTypeEnum.equals(PriceTypeForPriceBillEnum.UseRoomArea)) {
			// 按套内面积定价 标准总价 = 套内面积×套内单价
			// 建筑单价 = （标准总价）/建筑单价
			amount = roomArea.multiply((BigDecimal) row.getCell("newRoomPrice").getValue());
			if (buildingArea.compareTo(FDCHelper.ZERO) != 0)
				row.getCell("newBuildingPrice").setValue(amount.divide(buildingArea, this.paramsArray[0], this.paramsArray[1]));

			row.getCell("newSumAmount").setValue(amount.setScale(this.paramsArray[2], this.paramsArray[3]));
		} else if (priceTypeEnum.equals(PriceTypeForPriceBillEnum.UseStandPrice)) {
			// 按标准总价定价 套内单价 = （标准总价）/套内面积
			// 建筑单价 = （标准总价）/建筑面积
			if (row.getCell("newSumAmount").getValue() != null && row.getCell("newSumAmount").getValue() instanceof BigDecimal)
				amount = (BigDecimal) row.getCell("newSumAmount").getValue();
			if (buildingArea.compareTo(FDCHelper.ZERO) != 0)
				row.getCell("newBuildingPrice").setValue(amount.divide(buildingArea, this.paramsArray[0], this.paramsArray[1]));
			if (roomArea.compareTo(FDCHelper.ZERO) != 0)
				row.getCell("newRoomPrice").setValue(amount.divide(roomArea, this.paramsArray[0], this.paramsArray[1]));
		}
		
		BigDecimal sumAmount = KDTableTools.getBigDecimal(row.getCell("newSumAmount").getValue());
		 BigDecimal baseStandardPrice = KDTableTools.getBigDecimal(row.getCell("newBaseStandardPrice").getValue());
		 if(baseStandardPrice!=null&&sumAmount!=null&&sumAmount.compareTo(FDCHelper.ZERO)!=0){
			 row.getCell("newManagerAgio").setValue(baseStandardPrice.divide(sumAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
		 }else{
			 row.getCell("newManagerAgio").setValue(FDCHelper.ZERO);
		 }
	}

	private void updateViewRowData(IRow buildingRow, boolean isDelete) {
		RoomInfo room = ((RoomPriceAdjustEntryInfo) buildingRow.getUserObject()).getRoom();
		// 遍历总览的价格表，更新对应的数据
		for (int i = 0; i < this.kdtPriceAdjustEntry.getRowCount(); i++) {
			IRow entryRow = this.kdtPriceAdjustEntry.getRow(i);
			RoomPriceAdjustEntryInfo priceEntry = (RoomPriceAdjustEntryInfo) entryRow.getUserObject();
			RoomInfo entryRoom = priceEntry.getRoom();
			// 找到对应的总览记录，更新数据
			if (entryRoom.getId().toString().equals(room.getId().toString())) {
				if (isDelete) {
					this.kdtPriceAdjustEntry.removeRow(i);
				} else {
//					entryRow.getCell("sellType").setValue(buildingRow.getCell("sellType").getValue());
					entryRow.getCell("priceType").setValue(buildingRow.getCell("priceType").getValue());
					entryRow.getCell("Salesareatype").setValue(buildingRow.getCell("Salesareatype").getValue());  //add by shilei
					entryRow.getCell("newSumAmount").setValue(buildingRow.getCell("newSumAmount").getValue());
					entryRow.getCell("buildingArea").setValue(buildingRow.getCell("buildingArea").getValue());
					entryRow.getCell("newBuildingPrice").setValue(buildingRow.getCell("newBuildingPrice").getValue());
					entryRow.getCell("roomArea").setValue(buildingRow.getCell("roomArea").getValue());
					entryRow.getCell("newRoomPrice").setValue(buildingRow.getCell("newRoomPrice").getValue());
					
					entryRow.getCell("newBaseStandardPrice").setValue(buildingRow.getCell("newBaseStandardPrice").getValue());
					entryRow.getCell("newManagerAgio").setValue(buildingRow.getCell("newManagerAgio").getValue());
					entryRow.getCell("newSalesDirectorAgio").setValue(buildingRow.getCell("newSalesDirectorAgio").getValue());
					entryRow.getCell("newSceneManagerAgio").setValue(buildingRow.getCell("newSceneManagerAgio").getValue());
					
					this.checkDataChanged(entryRow);
					
					//根据定价类型，锁定相关列
					PriceTypeForPriceBillEnum priceType = (PriceTypeForPriceBillEnum)entryRow.getCell("priceType").getValue();
					this.lockCellByPriceType(entryRow, priceType);

//					priceEntry.setSellType((SellTypeEnum) buildingRow.getCell("sellType").getValue());
					priceEntry.setSellType(getSellTypeEnum((SellAreaTypeEnum)buildingRow.getCell("Salesareatype").getValue()));  
					priceEntry.setPriceType((PriceTypeForPriceBillEnum) buildingRow.getCell("priceType").getValue());
//					priceEntry.setSalesareatype((SellAreaTypeEnum)buildingRow.getCell("Salesareatype").getValue());  //add by shieli
					priceEntry.setNewSumAmount((BigDecimal) buildingRow.getCell("newSumAmount").getValue());
					priceEntry.setNewBuildingArea((BigDecimal) buildingRow.getCell("buildingArea").getValue());
					priceEntry.setNewBuildingPrice((BigDecimal) buildingRow.getCell("newBuildingPrice").getValue());
					priceEntry.setNewRoomArea((BigDecimal) buildingRow.getCell("roomArea").getValue());
					priceEntry.setNewRoomPrice((BigDecimal) buildingRow.getCell("newRoomPrice").getValue());
					
					priceEntry.setNewBaseStandardPrice((BigDecimal) buildingRow.getCell("newBaseStandardPrice").getValue());
					priceEntry.setNewManagerAgio((BigDecimal) buildingRow.getCell("newManagerAgio").getValue());
					priceEntry.setNewSalesDirectorAgio((BigDecimal) buildingRow.getCell("newSalesDirectorAgio").getValue());
					priceEntry.setNewSceneManagerAgio((BigDecimal) buildingRow.getCell("newSceneManagerAgio").getValue());
				}
				break;
			}
		}
	}

	/**
	 * 总览页签数据发生变化时，更新楼栋页签的数据
	 * 
	 * @param viewRow
	 *            - 总览页签的变更行
	 * @param isDelete
	 *            - 是否为删除操作
	 */
	private KDTable updateBuildingRowData(IRow viewRow, boolean isDelete) {
		if(viewRow.getUserObject()==null){
			return null;
		}
		RoomInfo room = ((RoomPriceAdjustEntryInfo) viewRow.getUserObject()).getRoom();
		KDTable retTable = null;
		// 遍历页签，根据楼栋Id找出对应的楼栋页签
		for (int i = 1; i < this.kDTabbedPanel.getTabCount(); i++) {
			KDPanel panel = (KDPanel) this.kDTabbedPanel.getComponentAt(i);
			KDTable table = (KDTable) panel.getComponent(0);
			// 找到对应的页签，再查找对应的记录，如果找不到，则创建一条新的记录
			if (table.getName().equals(room.getBuilding().getId().toString())) {
				boolean isFound = false;
				for (int j = 0; j < table.getRowCount(); j++) {
					IRow buildingRow = table.getRow(j);
					RoomPriceAdjustEntryInfo priceEntry = (RoomPriceAdjustEntryInfo) buildingRow.getUserObject();
					RoomInfo buildingRoom = priceEntry.getRoom();
					// 找到对应的总览记录，更新数据
					if (buildingRoom.getId().toString().equals(room.getId().toString())) {
						// 删除房间
						if (isDelete) {
							table.removeRow(j);
							this.updateBuildingTab(i, table);
						} else {
//							buildingRow.getCell("sellType").setValue(viewRow.getCell("sellType").getValue());
							buildingRow.getCell("priceType").setValue(viewRow.getCell("priceType").getValue());
							buildingRow.getCell("Salesareatype").setValue(viewRow.getCell("Salesareatype").getValue()); //add by shilei
							buildingRow.getCell("newSumAmount").setValue(viewRow.getCell("newSumAmount").getValue());
							buildingRow.getCell("buildingArea").setValue(viewRow.getCell("buildingArea").getValue());
							buildingRow.getCell("newBuildingPrice").setValue(viewRow.getCell("newBuildingPrice").getValue());
							buildingRow.getCell("roomArea").setValue(viewRow.getCell("roomArea").getValue());
							buildingRow.getCell("newRoomPrice").setValue(viewRow.getCell("newRoomPrice").getValue());

							this.checkDataChanged(buildingRow);
							
							//根据定价类型，锁定相关列
							PriceTypeForPriceBillEnum priceType = (PriceTypeForPriceBillEnum)buildingRow.getCell("priceType").getValue();
							this.lockCellByPriceType(buildingRow, priceType);
							
							priceEntry.setSellType(getSellTypeEnum((SellAreaTypeEnum)viewRow.getCell("Salesareatype").getValue()));  
							priceEntry.setPriceType((PriceTypeForPriceBillEnum) viewRow.getCell("priceType").getValue());
//							priceEntry.setSalesareatype((SellAreaTypeEnum)viewRow.getCell("Salesareatype").getValue());  // add by shilei
							priceEntry.setNewSumAmount((BigDecimal) viewRow.getCell("newSumAmount").getValue());
							priceEntry.setNewBuildingArea((BigDecimal) viewRow.getCell("buildingArea").getValue());
							priceEntry.setNewBuildingPrice((BigDecimal) viewRow.getCell("newBuildingPrice").getValue());
							priceEntry.setNewRoomArea((BigDecimal) viewRow.getCell("roomArea").getValue());
							priceEntry.setNewRoomPrice((BigDecimal) viewRow.getCell("newRoomPrice").getValue());
						}

						isFound = true;
						break;
					}
				}
				// 找不到对应记录，新增
				if (!isFound && !isDelete) {
					table.addRow(table.getRowCount(), viewRow);
				}
				retTable = table;
				break;
			}
		}
		return retTable;
	}

	/**
	 * 如果楼栋页签下没有数据，则删除页签，并更新已选楼栋map
	 * 
	 * @param tabIndex
	 * @param table - 当前页签下的表格
	 */
	private void updateBuildingTab(int tabIndex, KDTable table) {
		// 楼栋页签下没有数据，删除楼栋
		if (table.getRowCount() < 1) {
			IRow row = table.getRow(table.getRowCount()-1);
			//if(row.getUserObject() != null){  //最后一行非合计，说明还有数据
				//return;
			//}
			selectedBuildingMap.remove(table.getName()); // 已选楼栋map
			BuildingInfo[] buildingArray = new BuildingInfo[selectedBuildingMap.values().size()];
			Iterator buildingIt = selectedBuildingMap.values().iterator();
			int index = 0;
			while (buildingIt.hasNext()) {
				buildingArray[index++] = (BuildingInfo) buildingIt.next();
			}
			this.prmtBuilding.setDataNoNotify(buildingArray);
			this.setPriceSetScheme(buildingArray);
			this.kDTabbedPanel.remove(tabIndex);
		}
	}
	
	private void addTotal(KDTable table){
		//清除旧合计
//		for(int i=0; i<table.getRowCount(); i++){
//			if(table.getRow(i).getUserObject() == null){
//				table.removeRow(i);
//				i = -1;
//			}
//		}
		
		if(table== null || table.getRowCount() <= 0){
			table = this.kdtPriceAdjustEntry;
		}
		
		BigDecimal totalOldSumAmount = FDCHelper.ZERO;
		BigDecimal totalNewSumAmount = FDCHelper.ZERO;
		BigDecimal totalBuildingArea = FDCHelper.ZERO;
		BigDecimal totalOldBuildingPrice = FDCHelper.ZERO;
		BigDecimal totalNewBuildingPrice = FDCHelper.ZERO;
		BigDecimal totalRoomArea = FDCHelper.ZERO;
		BigDecimal totalOldRoomPrice = FDCHelper.ZERO;
		BigDecimal totalNewRoomPrice = FDCHelper.ZERO;
		BigDecimal totalOldBaseStandardPrice = FDCHelper.ZERO;
		BigDecimal totalNewBaseStandardPrice = FDCHelper.ZERO;
		for(int r=0; r<table.getRowCount(); r++){
			ICell oldSumAmount = table.getCell(r, "oldSumAmount");
			if(oldSumAmount.getValue() != null){
				totalOldSumAmount = totalOldSumAmount.add(KDTableTools.getBigDecimal(oldSumAmount.getValue()));
			}
			ICell newSumAmount = table.getCell(r, "newSumAmount");
			if(newSumAmount.getValue() != null){
				totalNewSumAmount = totalNewSumAmount.add(KDTableTools.getBigDecimal(newSumAmount.getValue()));
			}
			ICell buildingArea = table.getCell(r, "buildingArea");
			if(buildingArea.getValue() != null){
				totalBuildingArea = totalBuildingArea.add(KDTableTools.getBigDecimal(buildingArea.getValue()));
			}
			ICell oldBuildingPrice = table.getCell(r, "oldBuildingPrice");
			if(oldBuildingPrice.getValue() != null){
				totalOldBuildingPrice = totalOldBuildingPrice.add(KDTableTools.getBigDecimal(oldBuildingPrice.getValue()));
			}
			ICell newBuildingPrice = table.getCell(r, "newBuildingPrice");
			if(newBuildingPrice.getValue() != null){
				totalNewBuildingPrice = totalNewBuildingPrice.add(KDTableTools.getBigDecimal(newBuildingPrice.getValue()));
			}
			ICell roomArea = table.getCell(r, "roomArea");
			if(roomArea.getValue() != null){
				totalRoomArea = totalRoomArea.add(KDTableTools.getBigDecimal(roomArea.getValue()));
			}
			ICell oldRoomPrice = table.getCell(r, "oldRoomPrice");
			if(oldRoomPrice.getValue() != null){
				totalOldRoomPrice = totalOldRoomPrice.add(KDTableTools.getBigDecimal(oldRoomPrice.getValue()));
			}
			ICell newRoomPrice = table.getCell(r, "newRoomPrice");
			if (newRoomPrice.getValue() != null){
				totalNewRoomPrice = totalNewRoomPrice.add(KDTableTools.getBigDecimal(newRoomPrice.getValue()));
			}
			ICell oldBaseStandardPrice = table.getCell(r, "oldBaseStandardPrice");
			if (oldBaseStandardPrice.getValue() != null){
				totalOldBaseStandardPrice = totalOldBaseStandardPrice.add(KDTableTools.getBigDecimal(oldBaseStandardPrice.getValue()));
			}
			ICell newBaseStandardPrice = table.getCell(r, "newBaseStandardPrice");
			if (newBaseStandardPrice.getValue() != null){
				totalNewBaseStandardPrice = totalNewBaseStandardPrice.add(KDTableTools.getBigDecimal(newBaseStandardPrice.getValue()));
			}
			
			
		}
		
		// 在KDTable上添加合计行
		KDTFootManager footRowManager = table.getFootManager();
		IRow footRow = null;
		if (footRowManager == null) {
			// 获得合计字符的资源
			String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(table);
            footRowManager.addFootView();
            table.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.setUserObject("FDC_PARAM_TOTALCOST");
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            table.getIndexColumn().setWidthAdjustMode((short)1);
            table.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);

		} else {
			footRow = table.getFootRow(0);
			if (footRow.getUserObject() == null|| !footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")) {
				footRow = table.addFootRow(0);
			}
		}
	
		footRow.setUserObject("FDC_PARAM_TOTALCOST");
		
		footRow.getCell("oldSumAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[2]));
		footRow.getCell("oldSumAmount").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
		footRow.getCell("oldSumAmount").getStyleAttributes().setFontColor(java.awt.Color.BLACK);
		footRow.getCell("oldSumAmount").setValue(totalOldSumAmount);
		
		footRow.getCell("newSumAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[2]));
		footRow.getCell("newSumAmount").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
		footRow.getCell("newSumAmount").getStyleAttributes().setFontColor(java.awt.Color.BLACK);
		footRow.getCell("newSumAmount").setValue(totalNewSumAmount);
		
		footRow.getCell("oldBaseStandardPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[2]));
		footRow.getCell("oldBaseStandardPrice").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
		footRow.getCell("oldBaseStandardPrice").getStyleAttributes().setFontColor(java.awt.Color.BLACK);
		footRow.getCell("oldBaseStandardPrice").setValue(totalOldBaseStandardPrice);
		
		footRow.getCell("newBaseStandardPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[2]));
		footRow.getCell("newBaseStandardPrice").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
		footRow.getCell("newBaseStandardPrice").getStyleAttributes().setFontColor(java.awt.Color.BLACK);
		footRow.getCell("newBaseStandardPrice").setValue(totalNewBaseStandardPrice);
		
		footRow.getCell("buildingArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		footRow.getCell("buildingArea").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
		footRow.getCell("buildingArea").getStyleAttributes().setFontColor(java.awt.Color.BLACK);
		footRow.getCell("buildingArea").setValue(totalBuildingArea);
		
		footRow.getCell("roomArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		footRow.getCell("roomArea").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
		footRow.getCell("roomArea").getStyleAttributes().setFontColor(java.awt.Color.BLACK);
		footRow.getCell("roomArea").setValue(totalRoomArea);
		
		footRow.getCell("oldBuildingPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[0]));
		footRow.getCell("oldBuildingPrice").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
		footRow.getCell("oldBuildingPrice").getStyleAttributes().setFontColor(java.awt.Color.BLACK);
		footRow.getCell("oldBuildingPrice").setValue(totalOldBuildingPrice);
		
		footRow.getCell("newBuildingPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[0]));
		footRow.getCell("newBuildingPrice").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
		footRow.getCell("newBuildingPrice").getStyleAttributes().setFontColor(java.awt.Color.BLACK);
		footRow.getCell("newBuildingPrice").setValue(totalNewBuildingPrice);
        
		footRow.getCell("oldRoomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[0]));
		footRow.getCell("oldRoomPrice").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
		footRow.getCell("oldRoomPrice").getStyleAttributes().setFontColor(java.awt.Color.BLACK);
		footRow.getCell("oldRoomPrice").setValue(totalOldRoomPrice);
       
		footRow.getCell("newRoomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[0]));
		footRow.getCell("newRoomPrice").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
		footRow.getCell("newRoomPrice").getStyleAttributes().setFontColor(java.awt.Color.BLACK);
		footRow.getCell("newRoomPrice").setValue(totalNewRoomPrice);
       
		if(totalBuildingArea!=null){
			if(totalBuildingArea.compareTo(FDCHelper.ZERO)!=0){ //add by shilei
				footRow.getCell("newBuildingPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[0]));
				footRow.getCell("newBuildingPrice").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
				footRow.getCell("newBuildingPrice").getStyleAttributes().setFontColor(java.awt.Color.BLACK);
				footRow.getCell("newBuildingPrice").setValue(totalNewSumAmount.divide(totalBuildingArea, this.paramsArray[0], this.paramsArray[1]));
			}
		}
		if(totalRoomArea!=null){
			if(totalRoomArea.compareTo(FDCHelper.ZERO)!=0){
				footRow.getCell("newRoomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(this.paramsArray[0]));
				footRow.getCell("newRoomPrice").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
				footRow.getCell("newRoomPrice").getStyleAttributes().setFontColor(java.awt.Color.BLACK);
				footRow.getCell("newRoomPrice").setValue(totalNewSumAmount.divide(totalRoomArea, this.paramsArray[0], this.paramsArray[1]));
			}
			footRow.getStyleAttributes().setLocked(true);
		}
		footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}

	/**
	 * 设置主表中的统计价格
	 */
	private void setStatPrice() {
		BigDecimal sumSum = FDCHelper.ZERO;
		BigDecimal sumBuildingArea = FDCHelper.ZERO;
		BigDecimal sumRoomArea = FDCHelper.ZERO;

		BigDecimal highestBuildingPrice = FDCHelper.ZERO;
		BigDecimal lowestBuildingPrice = FDCHelper.ZERO;

		BigDecimal highestRoomPrice = FDCHelper.ZERO;
		BigDecimal lowestRoomPrice = FDCHelper.ZERO;

		// 将第一行数据作为最低建筑单价和最低套内单价的初始值
		if (this.kdtPriceAdjustEntry.getRow(0) != null
				&& kdtPriceAdjustEntry.getRow(0).getCell("newBuildingPrice")
						.getValue() != null)
			lowestBuildingPrice = KDTableTools
					.getBigDecimal(kdtPriceAdjustEntry.getRow(0).getCell(
							"newBuildingPrice").getValue());

		if (kdtPriceAdjustEntry.getRow(0) != null
				&& kdtPriceAdjustEntry.getRow(0).getCell("newRoomPrice")
						.getValue() != null)
			lowestRoomPrice = KDTableTools.getBigDecimal(kdtPriceAdjustEntry
					.getRow(0).getCell("newRoomPrice").getValue());

		for (int i = 0; i < this.kdtPriceAdjustEntry.getRowCount(); i++) {
			IRow row = this.kdtPriceAdjustEntry.getRow(i);
			if(row.getUserObject() == null){
				continue;
			}
			
			// 累加总价
			BigDecimal sumAmount = KDTableTools.getBigDecimal(row.getCell(
					"newSumAmount").getValue());
			if (sumAmount != null
					&& row.getCell("roomId").getValue() != null
					&& !"".equals(row.getCell("roomId").getValue()
							.toString())) {
				sumSum = sumSum.add(sumAmount);
			}
			// 累加建筑面积
			BigDecimal buildingArea = KDTableTools.getBigDecimal(row.getCell(
					"buildingArea").getValue());
			if (buildingArea != null
					&& row.getCell("roomId").getValue() != null
					&& !"".equals(row.getCell("roomId").getValue()
							.toString())) {
				sumBuildingArea = sumBuildingArea.add(buildingArea);
			}
			// 累加套内面积
			BigDecimal roomArea = KDTableTools.getBigDecimal(row.getCell(
					"roomArea").getValue());
			if (roomArea != null
					&& row.getCell("roomId").getValue() != null
					&& !"".equals(row.getCell("roomId").getValue()
							.toString())) {
				sumRoomArea = sumRoomArea.add(roomArea);
			}
			// 判断最高建筑单价
			BigDecimal buildingPrice = KDTableTools.getBigDecimal(row.getCell(
					"newBuildingPrice").getValue());
			if (buildingPrice != null
					&& buildingPrice.compareTo(highestBuildingPrice) > 0) {
				highestBuildingPrice = buildingPrice;
			}
			// 判断最低建筑单价
			if (buildingPrice != null
					&& buildingPrice.compareTo(lowestBuildingPrice) < 0) {
				lowestBuildingPrice = buildingPrice;
			}
			// 判断最高套内单价
			BigDecimal roomPrice = KDTableTools.getBigDecimal(row.getCell(
					"newRoomPrice").getValue());
			if (roomPrice != null && roomPrice.compareTo(highestRoomPrice) > 0) {
				highestRoomPrice = roomPrice;
			}
			// 判断最低套内单价
			if (roomPrice != null && roomPrice.compareTo(lowestRoomPrice) < 0) {
				lowestRoomPrice = roomPrice;
			}
		}
		this.txtMaxBuildingPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtMinBuildingPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtAvgBuildingPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);

		this.txtMaxRoomPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtMinRoomPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtAvgRoomPrice.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);

		if (FDCHelper.MAX_TOTAL_VALUE2.compareTo(sumSum) < 0) {
			MsgBox.showInfo(this, "定价后总价超出系统限制，计算结果错误，请修改。");
			this.abort();
		}
		// 计算新价格
		this.txtMaxBuildingPrice.setValue(highestBuildingPrice.divide(FDCHelper.ONE, this.paramsArray[0], this.paramsArray[1]));
		this.txtMinBuildingPrice.setValue(lowestBuildingPrice.divide(FDCHelper.ONE, this.paramsArray[0], this.paramsArray[1]));
		if (sumBuildingArea != null && sumBuildingArea.compareTo(FDCHelper.ZERO) != 0) {
			this.txtAvgBuildingPrice.setValue(sumSum.divide(sumBuildingArea, this.paramsArray[0], this.paramsArray[1]));
		} else {
			this.txtAvgBuildingPrice.setValue(null);
		}

		this.txtMaxRoomPrice.setValue(highestRoomPrice.divide(FDCHelper.ONE, this.paramsArray[0], this.paramsArray[1]));
		this.txtMinRoomPrice.setValue(lowestRoomPrice.divide(FDCHelper.ONE, this.paramsArray[0], this.paramsArray[1]));
		if (sumRoomArea != null && sumRoomArea.compareTo(FDCHelper.ZERO) != 0) {
			this.txtAvgRoomPrice.setValue(sumSum.divide(sumRoomArea, this.paramsArray[0], this.paramsArray[1]));
		} else {
			this.txtAvgRoomPrice.setValue(null);
		}
	}

	/**
	 * 判断选择的房间是否在列表中
	 * 
	 * @param table
	 *            - 当前表格
	 * @param room
	 *            - 选择的房间
	 * @return true - 已存在，false - 不存在
	 */
	private boolean isExist(KDTable table, RoomInfo room) {
		for (int j = 0; j < table.getRowCount(); j++) {
			// 忽略汇总行
			if (table.getRow(j).getUserObject() == null) {
				continue;
			}
			RoomPriceAdjustEntryInfo roomEntry = (RoomPriceAdjustEntryInfo) table.getRow(j).getUserObject();
			if (roomEntry.getRoom().getId().toString().equals(room.getId().toString())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检查楼栋页签下的房间是否和楼栋的房间数相同
	 * @return
	 * @throws BOSException 
	 */
	private boolean checkBuildingRoom(KDTable table) throws BOSException{
		String buildingId = table.getName();
		int roomCount = 0;
		RoomCollection rooms = this.getRoomCollection(buildingId, null);
		if(rooms!=null && !rooms.isEmpty()){
			roomCount = rooms.size();
		}
		//房间数比较，排除合计行
		if(roomCount!=0 && roomCount == table.getRowCount()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 验证单据数据的合法性
	 */
	private void verifyBeforeSave(){
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		if (this.txtNumber.getText() != null
				&& !"".equals(this.txtNumber.getText().toString())
				&& this.txtNumber.getText().length() > 80) {
			FDCMsgBox.showInfo("编码长度不能超过80个字符!");
			this.txtNumber.requestFocus();
			this.abort();
		}
		if(StringUtil.isEmptyString(this.txtName.getText())){
			FDCMsgBox.showInfo("名称不能为空!");
			this.txtName.requestFocus();
			this.abort();
		}else if(this.txtName.getText().length() > 80){
			FDCMsgBox.showInfo("名称长度不能超过80个字符!");
			this.txtName.requestFocus();
			this.abort();
		}
//		if(this.SceneManagerAgio.getBigDecimalValue()==null||this.SceneManagerAgio.getBigDecimalValue().compareTo(FDCHelper.ZERO)<=0){
//			FDCMsgBox.showInfo("案场经理折扣必须大于0!");
//			this.SceneManagerAgio.requestFocus();
//			this.abort();
//		}
//		if(this.salesDirectorAgio.getBigDecimalValue()==null||this.salesDirectorAgio.getBigDecimalValue().compareTo(FDCHelper.ZERO)<=0){
//			FDCMsgBox.showInfo("营销总监折扣必须大于0!");
//			this.salesDirectorAgio.requestFocus();
//			this.abort();
//		}
//		if(this.managerAgio.getBigDecimalValue()==null||this.managerAgio.getBigDecimalValue().compareTo(FDCHelper.ZERO)<=0){
//			FDCMsgBox.showInfo("总经理折扣%必须大于0!");
//			this.managerAgio.requestFocus();
//			this.abort();
//		}
//		if(this.txtDescription.getText()!=null && this.txtDescription.getText().length()>80){
//			FDCMsgBox.showInfo("参考消息长度不能超过80个字符!");
//			this.txtDescription.requestFocus();
//			this.abort();
//		}
		FDCClientVerifyHelper.verifyEmpty(this, this.cbOpenBatch);
		//忽略合计行和不参与定价调价的房源
		if(this.kdtPriceAdjustEntry.getRowCount() <= 0){
			FDCMsgBox.showInfo("定价房间不能为空");
			SysUtil.abort();
		}
		for (int i = 0; i < this.kdtPriceAdjustEntry.getRowCount(); i++) {
			IRow row = this.kdtPriceAdjustEntry.getRow(i);
			if(row.getUserObject() == null || row.getStyleAttributes().isLocked()){
				continue;
			}
			RoomPriceAdjustEntryInfo entry=(RoomPriceAdjustEntryInfo)row.getUserObject();
			RoomInfo room = entry.getRoom();
			if(room != null){
				if(row.getCell("newSumAmount").getValue() == null){
					FDCMsgBox.showInfo("第" + (i + 1) + "行,定价单现表总价不能为空!");
					SysUtil.abort();
				}
				BigDecimal newSumAmount = (BigDecimal)row.getCell("newSumAmount").getValue();
				if(newSumAmount.compareTo(FDCHelper.ZERO) < 0){
					FDCMsgBox.showInfo("第" + (i + 1) + "行,定价单现表总价不能小于0!");
					SysUtil.abort();
				}
				BigDecimal newBaseStandardPrice = (BigDecimal)row.getCell("newBaseStandardPrice").getValue();
				if(newBaseStandardPrice == null){
					FDCMsgBox.showInfo("第" + (i + 1) + "行,定价单现底总价不能为空!");
					SysUtil.abort();
				}
				if(newBaseStandardPrice.compareTo(FDCHelper.ZERO) < 0){
					FDCMsgBox.showInfo("第" + (i + 1) + "行,定价单现底价不能小于0!");
					SysUtil.abort();
				}
//				BigDecimal newSceneManagerAgio = (BigDecimal)row.getCell("newSceneManagerAgio").getValue();
//				if(newSceneManagerAgio == null){
//					FDCMsgBox.showInfo("第" + (i + 1) + "行,定价单现案场经理折扣不能为空!");
//					SysUtil.abort();
//				}
//				BigDecimal newSalesDirectorAgio = (BigDecimal)row.getCell("newSalesDirectorAgio").getValue();
//				if(newSalesDirectorAgio == null){
//					FDCMsgBox.showInfo("第" + (i + 1) + "行,定价单现营销总监折扣不能为空!");
//					SysUtil.abort();
//				}
//				BigDecimal newManagerAgio = (BigDecimal)row.getCell("newManagerAgio").getValue();
//				if(newManagerAgio == null){
//					FDCMsgBox.showInfo("第" + (i + 1) + "行,定价单现总经理折扣不能为空!");
//					SysUtil.abort();
//				}
				//检验底价，待改
//				if(this.isForceBasePrice()){
//					if(room.getBaseStandardPrice() == null){
//						room.setBaseStandardPrice(FDCHelper.ZERO);
//					}
					if(newSumAmount.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(newBaseStandardPrice.setScale(2, BigDecimal.ROUND_HALF_UP)) < 0){
						FDCMsgBox.showInfo("第" + (i + 1) + "行,定价单现表总价不能低于现底总价!");
						SysUtil.abort();
					}
//				}
//				BigDecimal checkManagerAgio=FDCHelper.ZERO;
//				if(newSumAmount.compareTo(FDCHelper.ZERO)!=0){
//					checkManagerAgio=newBaseStandardPrice.divide(newSumAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
//				}
//				if(newManagerAgio.compareTo(checkManagerAgio)!=0){
//					FDCMsgBox.showInfo("第" + (i + 1) + "行,定价单现总经理折扣有误，请调整!");
//					SysUtil.abort();
//				}
				if(((SellAreaTypeEnum)row.getCell("Salesareatype").getValue()).equals(SellAreaTypeEnum.PLANNING)&&!room.isIsPlan()){
					FDCMsgBox.showInfo("第" + (i + 1) + "行,房间没有进行预估复核!");
					SysUtil.abort();
				}else if(((SellAreaTypeEnum)row.getCell("Salesareatype").getValue()).equals(SellAreaTypeEnum.PRESELL)&&!room.isIsPre()){
					FDCMsgBox.showInfo("第" + (i + 1) + "行,房间没有进行预售复核!");
					SysUtil.abort();
				}else if(((SellAreaTypeEnum)row.getCell("Salesareatype").getValue()).equals(SellAreaTypeEnum.ACTUAL)&&!room.isIsActualAreaAudited()){
					FDCMsgBox.showInfo("第" + (i + 1) + "行,房间没有进行实测复核!");
					SysUtil.abort();
				}
				BigDecimal entryBuildingArea = entry.getNewBuildingArea();
				BigDecimal entryRoomArea = entry.getNewRoomArea();
				if (entryBuildingArea == null) {
					entryBuildingArea = FDCHelper.ZERO;
				}
				if (entryRoomArea == null) {
					entryRoomArea = FDCHelper.ZERO;
				}

				BigDecimal buildingArea = null;
				BigDecimal roomArea = null;
				// 根据面积状态取面积
				if (entry.getSellType().equals(SellTypeEnum.LocaleSell)) { 
					buildingArea = room.getActualBuildingArea();
					roomArea = room.getActualRoomArea();
				} else if (entry.getSellType().equals(SellTypeEnum.PreSell)) {
					buildingArea = room.getBuildingArea();
					roomArea = room.getRoomArea();
				} else {  //预估
					buildingArea = room.getPlanBuildingArea();
					roomArea = room.getPlanRoomArea();
				}

				if (buildingArea == null) {
					buildingArea = FDCHelper.ZERO;
				}
				if (roomArea == null) {
					roomArea = FDCHelper.ZERO;
				}
				if (roomArea.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(entryRoomArea.setScale(2, BigDecimal.ROUND_HALF_UP)) != 0) {
					FDCMsgBox.showInfo("第" + (i + 1) + "行,房间套内面积与房价面积录入面积不符!");
					SysUtil.abort();
				}

				if (buildingArea.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(entryBuildingArea.setScale(2, BigDecimal.ROUND_HALF_UP)) != 0) {
					FDCMsgBox.showInfo("第" + (i + 1) + "行,房间建筑面积与房价面积录入面积不符!");
					SysUtil.abort();
				}
			}
		}
		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
			IRow row = this.kdtEntry.getRow(i);
			if(row.getStyleAttributes().getBackground().equals(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR)) continue;
			
//			if (row.getCell("decorate").getValue() == null) {
//				FDCMsgBox.showWarning(this,"装修标准不能为空！");
//				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("decorate"));
//				SysUtil.abort();
//			} 
			if (row.getCell("price").getValue() == null) {
				FDCMsgBox.showWarning(this,"销售单价(元/M2或元/套)不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("decorate"));
				SysUtil.abort();
			} 
			if (row.getCell("amount").getValue() == null) {
				FDCMsgBox.showWarning(this,"销售金额(元)不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("decorate"));
				SysUtil.abort();
			} 
		}
	}
	
	/**
	 * 组装打印的内容
	 * @param table
	 */
	private void getRoomPrice(KDTable table) {
		table.removeHeadRows();
		
		IRow row = table.addHeadRow();
		row.getCell(0).setValue("单据编号");
		row.getCell(1).setValue(this.txtNumber.getText());
		row.getCell(2).setValue("名称");
		row.getCell(3).setValue(this.txtName.getText());
		row.getCell(4).setValue("制单人");
		row.getCell(5).setValue(this.prmtCreator.getValue());
		
		IRow row1 = table.addHeadRow();
		row1.getCell(0).setValue("建筑单价均价");
		row1.getCell(1).setValue(this.txtAvgBuildingPrice.getNumberValue());
		row1.getCell(2).setValue("最高建筑单价");
		row1.getCell(3).setValue(this.txtMaxBuildingPrice.getNumberValue());
		row1.getCell(4).setValue("最低建筑单价");
		row1.getCell(5).setValue(this.txtMinBuildingPrice.getNumberValue());
		
		IRow row2 = table.addHeadRow();
		row2.getCell(0).setValue("套内单价均价");
		row2.getCell(1).setValue(this.txtAvgRoomPrice.getNumberValue());
		row2.getCell(2).setValue("最高套内均价");
		row2.getCell(3).setValue(this.txtMaxRoomPrice.getNumberValue());
		row2.getCell(4).setValue("最低套内单价");
		row2.getCell(5).setValue(this.txtMinRoomPrice.getNumberValue());
		
		table.addHeadRow();
		
		IRow row4 = table.addHeadRow();
		row4.getCell(0).setValue("预测房号");
		row4.getCell(1).setValue("现总价");
		row4.getCell(2).setValue("建筑面积");
		row4.getCell(3).setValue("现建筑单价");
		row4.getCell(4).setValue("套内面积");
		row4.getCell(5).setValue("现套内单价");
	}
	
	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException {
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			return;
		}
		boolean isExist = true;
		RoomPriceManageInfo pur = new RoomPriceManageInfo();
		if (currentOrgId.length() == 0
				|| !iCodingRuleManager.isExist(pur, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(pur, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(pur,
					currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(pur, currentOrgId);
			} else {
				String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(pur, currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(pur, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								pur, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager.isDHExist(pur, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}
			setNumberTextEnabled();
		}
	}
	
	protected void setNumberTextEnabled() {
		if (getNumberCtrl() != null) {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if (org != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(
						new RoomPriceManageInfo(), org.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
				getNumberCtrl().setRequired(isAllowModify);
			}
		}
	}
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		getNumberCtrl().setText(number);
	}

	protected void tblAttach_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = this.tblAttach.getRow(e.getRowIndex());
			if (row.getUserObject() == null) {
				return;
			}
			FileGetter fg=new FileGetter(AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
			fg.viewAttachment(((AttachmentInfo)row.getUserObject()).getId().toString(), this, this.tblAttach);
		}
	}
	public void actionAttachment_actionPerformed(ActionEvent e)throws Exception{
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        boolean isEdit = false;
        if(OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState()))
            isEdit = true;
        AttachmentUIContextInfo info = getAttacheInfo();
        if(info == null)
            info = new AttachmentUIContextInfo();
        if(info.getBoID() == null || info.getBoID().trim().equals(""))
        {
            String boID = getSelectBOID();
            if(boID == null)
                if(OprtState.ADDNEW.equals(getOprtState()))
                {
                    if(attachMentTempID == null)
                    {
                        boID = acm.getAttID().toString();
                        attachMentTempID = boID;
                    } else
                    {
                        boID = attachMentTempID;
                    }
                } else
                {
                    return;
                }
            info.setBoID(boID);
        }
        info.setEdit(isEdit);
        String multi = (String)getUIContext().get("MultiapproveAttachment");
        if(multi != null && multi.equals("true"))
            acm.showAttachmentListUIByBoIDNoAlready(this, info);
        else
            acm.showAttachmentListUIByBoID(this, info);
        
        loadAttachTable();
	}
	private void attachmentPerform(String tempId, String pk)
    {
        if(pk == null || tempId == null)
            return;
        try
        {
            BizobjectFacadeFactory.getRemoteInstance().upAttID2BosID(tempId, pk);
            BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(tempId);
            attachMentTempID = null;
        }
        catch(Exception e)
        {
            logger.error(e);
        }
        return;
    }
	private String attachMentTempID=null;
	
	public boolean checkBeforeWindowClosing()
	{
        if(editData != null && editData.getId() != null)
            attachmentPerform(attachMentTempID, editData.getId().toString());
        return super.checkBeforeWindowClosing();
	}
	public void setDataObject(IObjectValue dataObject)
	{
	    if(dataObject != null)
	    {
	        CoreBaseInfo baseInfo = (CoreBaseInfo)dataObject;
	        if(baseInfo.getId() != null)
	            attachmentPerform(attachMentTempID, baseInfo.getId().toString());
	    }
        super.setDataObject(dataObject);
    }
	protected void doAfterSubmit(IObjectPK pk)throws Exception{
	     if(OprtState.ADDNEW.equals(getOprtState()))
	     {
	         int size = idList.size();
	         idList.add(size, pk.toString());
	         attachmentPerform(attachMentTempID, pk.toString());
	     }
	     super.doAfterSubmit(pk);
	}
	protected void loadAttachTable(){
		this.tblAttach.removeRows();
		if(editData.getId()!=null||attachMentTempID!=null){
			String id=null;
			if(editData.getId()==null){
				id=attachMentTempID;
			}else{
				id=editData.getId().toString();
			}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", id));
			view.setFilter(filter);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("attachment.*");
			sels.add("*");
			view.setSelector(sels);
			try {
				BoAttchAssoCollection col = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
				for(int i=0;i<col.size();i++){
					BoAttchAssoInfo attAsso=col.get(i);
					AttachmentInfo att=attAsso.getAttachment();
					IRow row=this.tblAttach.addRow();
					row.setUserObject(att);
					row.getCell("id").setValue(att.getId());
					row.getCell("name").setValue(att.getName());
					row.getCell("size").setValue(att.getSize());
					row.getCell("createTime").setValue(att.getCreateTime());
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}
	protected void btnValueInput_actionPerformed(ActionEvent e)throws Exception {
		this.storeFields();
		this.editData.getValueEntry().clear();
		SellProjectInfo psp=SHEManageHelper.getParentSellProject(null, this.editData.getSellProject());
		if(psp!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("projectBase.id");
			psp=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(psp.getId()), sel);
			if(psp.getProjectBase()!=null){
				ProjectBaseInfo pb=psp.getProjectBase();
				
				ValueInputCollection vaCol=ValueInputFactory.getRemoteInstance().getValueInputCollection("select indexVersion.*,entry.* from where project.id='"+pb.getId().toString()+"' and isLatest=1 order by indexVersion.number desc");
				if(vaCol.size()>0){
					for(int i=0;i<vaCol.get(0).getEntry().size();i++){
						ValueInputEntryInfo entry=vaCol.get(0).getEntry().get(i);
						entry.setSrcId(entry.getId());
						entry.setHead(null);
						entry.setId(null);
						if(entry.getBuildId()!=null&&entry.getProductTypeId()!=null){
							try {
								getSellHouseAmount(entry.getBuildId().toString(),entry.getProductTypeId().toString(),entry);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							if(entry.getCalculateType().equals(CalculateTypeEnum.NOW)){
								entry.setPrice(FDCHelper.divide(entry.getAmount(), entry.getArea(), 2, BigDecimal.ROUND_HALF_UP));
							}else{
								entry.setPrice(FDCHelper.divide(entry.getAmount(), entry.getAccount(), 2, BigDecimal.ROUND_HALF_UP));
							}
						}
						this.editData.getValueEntry().add(entry);
					}
				}
			}
		}
		this.loadFields();
	}
	protected void getSellHouseAmount(String id,String productTypeId,ValueInputEntryInfo entry) throws SQLException, BOSException, EASBizException{
    	BanBasedataEntryListCollection col=BanBasedataEntryListFactory.getRemoteInstance().getBanBasedataEntryListCollection("select head.id from where banBasedataEntry.id='"+id+"'");
    	if(col.size()>0){
//    		FilterInfo filter=new FilterInfo();
//        	filter.getFilterItems().add(new FilterItemInfo("room.building.id",col.get(0).getHead().getId().toString()));
//        	filter.getFilterItems().add(new FilterItemInfo("head.isExecuted",Boolean.TRUE));
//        	if(RoomPriceAdjustEntryFactory.getRemoteInstance().exists(filter)){
        		FDCSQLBuilder builder = new FDCSQLBuilder();
        		IRowSet rowSet = null;
        		builder.appendSql(" select sum(t.account) account,sum(t.area) area,sum(t.amount) amount from (select banEntry.FBanBasedataEntryId buildId,room.fproductTypeId productTypeId,count(*) account,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) area,sum(sign.fcontractTotalAmount) amount");
        		builder.appendSql(" from t_she_signManage sign left join t_she_room room on room.fid=sign.froomId left join T_SHE_BanBasedataEntryList banEntry on banEntry.fheadId=room.fbuildingId where sign.fbizState in('SignApple','SignAudit') and banEntry.FBanBasedataEntryId is not null group by banEntry.FBanBasedataEntryId,room.fproductTypeId");
        		builder.appendSql(" union all select banEntry.FBanBasedataEntryId buildId,room.fproductTypeId productTypeId,count(*) account,sum(case room.fsellType when 'PlanningSell' then room.fPlanBuildingArea when 'PreSell' then room.FBuildingArea else room.fActualBuildingArea end) area,sum(room.fbaseStandardPrice) amount");
        		builder.appendSql(" from t_she_room room left join T_SHE_BanBasedataEntryList banEntry on banEntry.fheadid=room.fbuildingid where room.FSellState!='Sign' and banEntry.FBanBasedataEntryId is not null group by banEntry.FBanBasedataEntryId,room.fproductTypeId) t where t.buildId='"+id+"' and t.productTypeId='"+productTypeId+"'");
        		rowSet = builder.executeQuery();
        		while (rowSet.next()) {
        			entry.setAmount(rowSet.getBigDecimal("amount"));
        			entry.setArea(rowSet.getBigDecimal("area"));
        			entry.setAccount(rowSet.getInt("account"));
        		}
//        	}
    	}
    }
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("price")){
			if(CalculateTypeEnum.NOW.equals(this.kdtEntry.getRow(e.getRowIndex()).getCell("calculateType").getValue())){
				this.kdtEntry.getRow(e.getRowIndex()).getCell("amount").setValue(FDCHelper.multiply(this.kdtEntry.getRow(e.getRowIndex()).getCell("price").getValue(), this.kdtEntry.getRow(e.getRowIndex()).getCell("area").getValue()));
			}else{
				this.kdtEntry.getRow(e.getRowIndex()).getCell("amount").setValue(FDCHelper.multiply(this.kdtEntry.getRow(e.getRowIndex()).getCell("price").getValue(), this.kdtEntry.getRow(e.getRowIndex()).getCell("account").getValue()));
			}
		}else if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("amount")){
			if(CalculateTypeEnum.NOW.equals(this.kdtEntry.getRow(e.getRowIndex()).getCell("calculateType").getValue())){
				this.kdtEntry.getRow(e.getRowIndex()).getCell("price").setValue(FDCHelper.divide(this.kdtEntry.getRow(e.getRowIndex()).getCell("amount").getValue(), this.kdtEntry.getRow(e.getRowIndex()).getCell("area").getValue(),2, BigDecimal.ROUND_HALF_UP));
			}else{
				this.kdtEntry.getRow(e.getRowIndex()).getCell("price").setValue(FDCHelper.divide(this.kdtEntry.getRow(e.getRowIndex()).getCell("amount").getValue(), this.kdtEntry.getRow(e.getRowIndex()).getCell("account").getValue(),2, BigDecimal.ROUND_HALF_UP));
			}
		}
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.btnValueInput.setEnabled(false);
		} else {
			this.unLockUI();
			this.btnValueInput.setEnabled(true);
		}
	}
}