/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.BanBasedataCollection;
import com.kingdee.eas.fdc.basedata.BanBasedataEntryInfo;
import com.kingdee.eas.fdc.basedata.BanBasedataFactory;
import com.kingdee.eas.fdc.basedata.BanBasedataInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.OperationPhasesInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectCollection;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectFactory;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectInfo;
import com.kingdee.eas.fdc.basedata.SchedulePlanEntryCollection;
import com.kingdee.eas.fdc.basedata.SchedulePlanEntryFactory;
import com.kingdee.eas.fdc.basedata.SchedulePlanEntryInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingAreaEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ConditionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IBuilding;
import com.kingdee.eas.fdc.sellhouse.NetWorkTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class BuildingNewEditUI extends AbstractBuildingNewEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(BuildingNewEditUI.class);
	Map orgMap = FDCSysContext.getInstance().getOrgMap();
	boolean isShowNoEditWar = false;
	
	//存储楼层分录的信息
	Map floorMap = new HashMap();
	/**
	 * output class constructor
	 */
	public BuildingNewEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		
		
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		//f7 过滤 启用状态
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter  = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		this.f7ProductType.setEntityViewInfo(view);
		this.f7BuildingProperty.setEntityViewInfo(view);
		this.comboBuildingStructure.setEntityViewInfo(view);
		
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		
		
		KDBizPromptBox f7Product = new KDBizPromptBox();
		f7Product
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery");
		f7Product.setEditable(true);
		f7Product.setDisplayFormat("$name$");
		f7Product.setEditFormat("$number$");
		f7Product.setCommitFormat("$number$");
		//设置户型类别的集团管控过滤
		SellProjectInfo sp = (SellProjectInfo) this.getUIContext().get("sellProject");
		if(sp!=null){
			SHEHelper.SetGroupFilters(f7Product, sp.getId().toString(), "户型类别", "RoomArch");
			SHEHelper.SetGroupFilters(this.f7ProductType, sp.getId().toString(), "产品类型", "RoomArch");			
			SHEHelper.SetGroupFilters(this.comboBuildingStructure, sp.getId().toString(), "建筑结构", "RoomArch");
		}		
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Product);
		
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		
		/*  亿达5.9售楼改动  ?	删除租售项目,楼栋中的建筑性质，采用界面隐藏即可.  20081223
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems()
				.add(new FilterItemInfo("level", new Integer(2)));
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		BuildingPropertyInfo buildingProperty = sellProject
				.getBuildingProperty();
		if (buildingProperty != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("parent.id", buildingProperty.getId()
							.toString()));
		}
		this.f7BuildingProperty.setEntityViewInfo(view);
		*/
		
		if(OprtState.ADDNEW.equalsIgnoreCase(this.getOprtState()))
		{
			int up = this.spiFloorCount.getIntegerVlaue().intValue();
			int down = this.spSubFloor.getIntegerVlaue().intValue();
			
			
		}
		
		
		this.storeFields();
		this.initOldData(this.editData);
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
//		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
//		if (!saleOrg.isIsBizUnit()) {
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//		}
		
		if(true==((BuildingInfo)this.editData).isIsGetCertificated()){
			this.contSellCertifiNum.setVisible(true);
			this.contSellCertifiDate.setVisible(true);
		}else{
			this.contSellCertifiNum.setVisible(false);
			this.contSellCertifiDate.setVisible(false);
		}
		SellProjectInfo psp=SHEManageHelper.getParentSellProject(null, ((BuildingInfo)this.editData).getSellProject());
		if(psp!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("projectBase.id");
			psp=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(psp.getId()), sel);
			if(psp.getProjectBase()!=null){
				BanBasedataCollection col=BanBasedataFactory.getRemoteInstance().getBanBasedataCollection("select project.*,indexVersion.*,* from where billState='4AUDITTED' and project.projectBase.id='"+psp.getProjectBase().getId().toString()+"' order by IndexVersion.number desc,description desc ");
				Map map=new HashMap();
				for(int j=0;j<col.size();j++){
					if(map.get(col.get(j).getProject().getLongNumber())==null){
						map.put(col.get(j).getProject().getLongNumber(), col.get(j));
					}else{
						BanBasedataInfo pd=(BanBasedataInfo) map.get(col.get(j).getProject().getLongNumber());
						int number=Integer.parseInt(pd.getIndexVersion().getNumber().replaceFirst("^0*", ""));
						int nowNumber=Integer.parseInt(col.get(j).getIndexVersion().getNumber().replaceFirst("^0*", ""));
						if(nowNumber>number){
							map.put(col.get(j).getProject().getLongNumber(), col.get(j));
						}
					}
				}
				Set idSet=new HashSet();
				Object[] key = map.keySet().toArray();
				for (int k = 0; k < key.length; k++) {
					idSet.add(((BanBasedataInfo)map.get(key[k])).getId().toString());
				}
					
				view=new EntityViewInfo();
				filter=new FilterInfo();
				if(idSet.size()>0){
					filter.getFilterItems().add(new FilterItemInfo("parent.id",idSet,CompareType.INCLUDE));
				}else{
					filter.getFilterItems().add(new FilterItemInfo("parent.id","'null'"));
				}
				view.setFilter(filter);
				this.prmtBanBasedataEntry.setEntityViewInfo(view);
			}
		}
		boolean isBand=false;
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("CIFI_BUILDINGBAND",SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
		if(hmAllParam.get("CIFI_BUILDINGBAND")!=null){
			isBand=Boolean.parseBoolean(hmAllParam.get("CIFI_BUILDINGBAND").toString());
		}
		if(isBand){
			this.prmtBanBasedataEntry.setRequired(true);
		}
	}

	public void lockNoEdit() {
		if (!this.getOprtState().equals("EDIT")) {
			return;
		}
		String buildingId = this.editData.getId().toString();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("building.id", buildingId));
//		try {
//			if (RoomDesFactory.getRemoteInstance().exists(filter)
//					|| RoomFactory.getRemoteInstance().exists(filter)) {
//				if (!isShowNoEditWar) {
//					MsgBox.showInfo("已经建立了房间或房间定义,将锁定相关属性!");
//				}
//				this.comboCodingType.setEnabled(false);
//				this.spiFloorCount.setEnabled(false);
//				this.spSubFloor.setEnabled(false);
//			
//				this.isShowNoEditWar = true;
//				
//				
//			}
//		} catch (EASBizException e) {
//			this.handleException(e);
//		} catch (BOSException e) {
//			this.handleException(e);
//		}
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_VIEW.equals(this.getOprtState())) {
			
		}else if(STATUS_ADDNEW.equals(this.getOprtState())){
		
			
			this.comboCodingType.setEnabled(true);
			//this.spiFloorCount.setEnabled(true);
		}
		
	}
	
	private void initControl() {
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.txtProjectNumber.setEnabled(false);
		this.txtProjectName.setEnabled(false);
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		
		this.txtNumber.setMaxLength(80);
		this.txtName.setMaxLength(80);
		
//		亿达5.9售楼改动  ?	删除租售项目,楼栋中的建筑性质，采用界面隐藏即可.  20081223
		//this.f7BuildingProperty.setRequired(true);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
//		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
//				.get("sellProject");
//		
//		filter.getFilterItems().add(
//				new FilterItemInfo("sellProject.id", sellProject.getId()
//						.toString()));
//		this.f7Subarea.setEntityViewInfo(view);
		
		this.txtBuildingHeight.setRemoveingZeroInDispaly(false);
		this.txtBuildingHeight.setRemoveingZeroInEdit(false);
		this.txtBuildingHeight.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingHeight.setSupportedEmpty(true);
		this.txtBuildingHeight.setNegatived(false);
		this.txtBuildingHeight.setPrecision(2);
		
		this.tfUserRate.setRemoveingZeroInDispaly(false);
		this.tfUserRate.setRemoveingZeroInEdit(false);
		this.tfUserRate.setHorizontalAlignment(JTextField.RIGHT);
		this.tfUserRate.setSupportedEmpty(true);
		this.tfUserRate.setNegatived(false);
		this.tfUserRate.setPrecision(2);
		
		
		this.txtBuildingTerraArea.setRemoveingZeroInDispaly(false);
		this.txtBuildingTerraArea.setRemoveingZeroInEdit(false);
		this.txtBuildingTerraArea.setPrecision(2);
		this.txtBuildingTerraArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingTerraArea.setSupportedEmpty(true);
		this.txtBuildingTerraArea.setNegatived(false);
		
		SpinnerNumberModel model = new SpinnerNumberModel(1, 0, 1000, 1);
		this.spiFloorCount.setModel(model);
		model = new SpinnerNumberModel(0, 0, 100, 1);
	
//		this.comboBuildingStructure.setRequired(true);
		
		model = new SpinnerNumberModel(0,-1000,0,1);
		this.spSubFloor.setModel(model);
		this.spSubFloor.setRequired(true);

		this.prmtBanBasedataEntry.setEnabledMultiSelection(true);
		
		this.kdtEntry.checkParsed();
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor cellEditor = new KDTDefaultCellEditor(formattedTextField);
		this.kdtEntry.getColumn("area").setEditor(cellEditor);
		this.kdtEntry.getColumn("area").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("area").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("floor").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.storeFields();
		this.initOldData(this.editData);
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
		if(null==sellProject&&null!=((BuildingInfo)editData).getSellProject()){
			 sellProject = ((BuildingInfo)editData).getSellProject();
		}
		CacheServiceFactory.getInstance().discardType(sellProject.getBOSType());
		
		CacheServiceFactory.getInstance().discardType(new RoomModelInfo().getBOSType());
	}

    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
		CacheServiceFactory.getInstance().discardType(sellProject.getBOSType());
		
		CacheServiceFactory.getInstance().discardType(new RoomModelInfo().getBOSType());
	}
    
	protected void comboCodingType_actionPerformed(ActionEvent e)
			throws Exception {
		super.comboCodingType_actionPerformed(e);
		CodingTypeEnum codingType = (CodingTypeEnum) this.comboCodingType
				.getSelectedItem();

	
		this.spSubFloor.setEnabled(true);
		this.spiFloorCount.setEnabled(true);
	
		
		
//		if(codingType.equals(CodingTypeEnum.Num)){
//			this.spSubFloor.setEnabled(false);
//			this.spiFloorCount.setEnabled(false);
//			this.spSubFloor.setValue(new Integer(0));
//			this.spiFloorCount.setValue(new Integer(1));
//		}
	}

	public void loadFields() {
		super.loadFields();
		SHEHelper.setNumberEnabled(editData,getOprtState(),txtNumber);
		BuildingInfo buildingInfo = (BuildingInfo) this.editData;
		this.txtProjectNumber
				.setText(buildingInfo.getSellProject().getNumber());
		this.txtProjectName.setText(buildingInfo.getSellProject().getName());
		this.txtName.setText(buildingInfo.getName());
		this.txtNumber.setText(buildingInfo.getNumber());
		this.f7Subarea.setValue(buildingInfo.getSubarea());
		this.spiFloorCount.setValue(new Integer(buildingInfo.getFloorCount()));
		this.spSubFloor.setValue(new Integer(buildingInfo.getSubFloorCount()));
		this.pkJoinInDate.setValue(buildingInfo.getJoinInDate());
		this.pkCompleteDate.setValue(buildingInfo.getCompleteDate());
		this.f7ConstructPart.setValue(buildingInfo.getConstructPart());
		this.txtBuildingHeight.setValue(buildingInfo.getBuildingHeight());
		this.f7BuildingProperty.setValue(buildingInfo.getBuildingProperty());
		this.txtBuildingTerraArea.setValue(buildingInfo.getBuildingTerraArea());
		this.comboCodingType.setSelectedItem(buildingInfo.getCodingType());
		this.comboBuildingStructure.setValue(buildingInfo.getBuildingStructure());
		
		this.f7ProductType.setValue(buildingInfo.getProductType());
		this.txtAdministrativeNumber.setText(buildingInfo.getAdministrativeNumber());
		
		this.kDStartDate.setValue(buildingInfo.getStartDate());
		this.tfBuildingArea.setValue(buildingInfo.getBuildingArea());
		this.tfUserRate.setValue(buildingInfo.getUseRate());
		this.isGetCertificated.setSelected(buildingInfo.isIsGetCertificated());
		this.kDSellCertifiNum.setText(buildingInfo.getSellCertification());
		this.tfFloorHeight.setValue(buildingInfo.getFloorHeight());
		this.pkSellCertifiDate.setValue(buildingInfo.getSellCertifiDate());
		
		this.pkStructureDate.setValue(buildingInfo.getStructureDate());
//		this.prmtBanBasedataEntry.setValue(buildingInfo.getBanBasedataEntry());
		lockNoEdit();
	
//		if(buildingInfo.getBanBasedataEntry()!=null){
//			BanBasedataEntryInfo build=buildingInfo.getBanBasedataEntry();
//			this.f7ProductType.setValue(build.getProductType());
//			this.f7ProductType.setEnabled(false);
//			this.f7ProductType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//			
//			this.tfBuildingArea.setValue(FDCHelper.add(build.getGroundFloorArea(), build.getUndergroundArea()));
//			this.tfBuildingArea.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//			this.tfBuildingArea.setEnabled(false);
//			
//			this.spiFloorCount.setValue(build.getGroundFloor());
//			this.spiFloorCount.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//			this.spiFloorCount.setEnabled(false);
//			
//			this.spSubFloor.setValue(build.getUndergroundFloor());
//			this.spSubFloor.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//			this.spSubFloor.setEnabled(false);
//			
//			try {
//				SchedulePlanEntryInfo spInfo=null;
//				ProjectDataCollectInfo pd=null;
//				if(build!=null){
//					SellProjectInfo psp=SHEManageHelper.getParentSellProject(null, ((BuildingInfo)this.editData).getSellProject());
//					if(psp!=null){
//						SelectorItemCollection sel=new SelectorItemCollection();
//						sel.add("projectBase.id");
//						psp=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(psp.getId()), sel);
//						if(psp.getProjectBase()!=null){
//							ProjectDataCollectCollection pdCol = ProjectDataCollectFactory.getRemoteInstance().getProjectDataCollectCollection("select indexVersion.number,areaId,planId from where project.projectBase.id='"+psp.getProjectBase().getId().toString()+"' and isLatest=1 order by indexVersion.number desc");
//							if(pdCol.size()>0){
//								pd=pdCol.get(0);
//								if(pd.getPlanId()!=null){
//									SchedulePlanEntryCollection bulidCol = SchedulePlanEntryFactory.getRemoteInstance().getSchedulePlanEntryCollection("select * from where parent.id='"+pd.getPlanId()+"' and banNumber.id='"+build.getId().toString()+"'");
//									if(bulidCol.size()>0){
//										spInfo=bulidCol.get(0);
//									}
//								}
//							}
//						}
//					}
//					if(spInfo!=null){
//						this.kDStartDate.setValue(spInfo.getStartDate());
//						this.pkCompleteDate.setValue(spInfo.getCompletionDate());
//					}
//					this.kDStartDate.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//					this.pkCompleteDate.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//					this.kDStartDate.setEnabled(false);
//					this.pkCompleteDate.setEnabled(false);
//				}
//			} catch (BOSException e1) {
//				e1.printStackTrace();
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			}
//		}
		if(buildingInfo.getBanBasedataEntryList().size()>0){
			Object entry[]=new Object[buildingInfo.getBanBasedataEntryList().size()];
			for(int i=0;i<buildingInfo.getBanBasedataEntryList().size();i++){
				entry[i]=buildingInfo.getBanBasedataEntryList().get(i).getBanBasedataEntry();
			}
			this.prmtBanBasedataEntry.setValue(entry);
		}else{
			this.prmtBanBasedataEntry.setValue(null);
		}
		this.kdtEntry.removeRows();
		for(int i=0;i<buildingInfo.getAreaEntry().size();i++){
			IRow row=this.kdtEntry.addRow();
			row.setUserObject(buildingInfo.getAreaEntry().get(i));
			row.getCell("area").setValue(buildingInfo.getAreaEntry().get(i).getArea());
			row.getCell("floor").setValue(i+1);
		}
		CRMClientHelper.getFootRow(this.kdtEntry, new String[]{"area"});
	}
	
	private BuildingFloorEntryCollection getBuildingFloorEntryColl(BuildingInfo building)
	{
		BuildingFloorEntryCollection coll = null;
		
		if(building == null || building.getId() == null)
			return new BuildingFloorEntryCollection();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		SorterItemCollection sorColl = new SorterItemCollection();
		
		
		SorterItemInfo item = new SorterItemInfo("floor");
		item.setSortType(SortType.DESCEND);
		
		sorColl.add(item);
		
		view.setSorter(sorColl);
		
		filter.getFilterItems().add(new FilterItemInfo("building.id",building.getId().toString()));
		
		try
		{
			coll = BuildingFloorEntryFactory.getRemoteInstance().getBuildingFloorEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIExceptionAndAbort(e);
		}
		return coll;
	}
	
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionEdit_actionPerformed(arg0);

		lockNoEdit();
		//清除缓存
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
		if(null!=sellProject){
			CacheServiceFactory.getInstance().discardType(sellProject.getBOSType());
			
		}
		BuildingInfo buildingInfo = (BuildingInfo)this.editData;
		CacheServiceFactory.getInstance().discardType(buildingInfo.getBOSType());
		
	}
	  public void checkModified() throws Exception {
		  
	  }
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		super.actionAddNew_actionPerformed(e);
//		清除缓存
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
		if(null!=sellProject){
			CacheServiceFactory.getInstance().discardType(sellProject.getBOSType());
			
		}
	
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		
		BuildingInfo buildingInfo = (BuildingInfo) this.editData;
		buildingInfo.setNumber(this.txtNumber.getText());
		buildingInfo.setName(this.txtName.getText());	
		
		//这里可能会导致 simpleName 长度过长，数据库保存时提示长度超出异常 -zhicheng_jin 090702
//		buildingInfo.setSimpleName(buildingInfo.getSellProject().getNumber()
//				+ "." + buildingInfo.getNumber());
		buildingInfo.setSubarea((SubareaInfo) this.f7Subarea.getValue());
		buildingInfo.setFloorCount(((Integer) this.spiFloorCount.getValue()).intValue());
		buildingInfo.setSubFloorCount(((Integer)this.spSubFloor.getValue()).intValue());
		buildingInfo.setJoinInDate((Date) this.pkJoinInDate.getValue());
		buildingInfo.setCompleteDate((Date) this.pkCompleteDate.getValue());
		buildingInfo.setConstructPart((SupplierInfo) this.f7ConstructPart
				.getValue());
		buildingInfo.setBuildingHeight(this.txtBuildingHeight
				.getBigDecimalValue());
		buildingInfo
				.setBuildingProperty((BuildingPropertyInfo) this.f7BuildingProperty
						.getValue());
		buildingInfo.setBuildingTerraArea(this.txtBuildingTerraArea
				.getBigDecimalValue());
		if (this.comboBuildingStructure.getValue() != null) {
			buildingInfo.setBuildingStructure((BuildingStructureInfo) this.comboBuildingStructure
							.getValue());
		}
		buildingInfo.setProductType((ProductTypeInfo) this.f7ProductType.getValue());
		buildingInfo.setAdministrativeNumber(this.txtAdministrativeNumber.getText());
		
		//默认永远为单元形式
		buildingInfo.setCodingType(CodingTypeEnum.UnitFloorNum);
		
		buildingInfo.setStartDate(this.kDStartDate.getTimestamp());
		buildingInfo.setBuildingArea(this.tfBuildingArea.getBigDecimalValue());
		buildingInfo.setUseRate(this.tfUserRate.getBigDecimalValue());
		buildingInfo.setIsGetCertificated(this.isGetCertificated.isSelected());
		buildingInfo.setSellCertification(this.kDSellCertifiNum.getText());
		buildingInfo.setFloorHeight(this.tfFloorHeight.getBigDecimalValue());
	
		//长名称 项目名称-分区名称-楼栋名称
		buildingInfo.setDisplayName(buildingInfo.getSellProject()==null?"":buildingInfo.getSellProject().getName()
					+(buildingInfo.getSubarea()==null?"":"-"+buildingInfo.getSubarea().getName())+"-"+buildingInfo.getName());
		
		buildingInfo.setSellCertifiDate((Date) this.pkSellCertifiDate.getValue());
		buildingInfo.setStructureDate((Date) this.pkStructureDate.getValue());
		
//		buildingInfo.setBanBasedataEntry((BanBasedataEntryInfo) this.prmtBanBasedataEntry.getValue());
		
		String entryStr="";
		Object entry[]=(Object[])this.prmtBanBasedataEntry.getValue();
		buildingInfo.getBanBasedataEntryList().clear();
		if(entry!=null){
			for(int i=0;i<entry.length;i++){
				if(entry[i]==null) continue;
				BanBasedataEntryListInfo entryInfo=new BanBasedataEntryListInfo();
				if(i==entry.length-1){
					entryStr=entryStr+((BanBasedataEntryInfo) entry[i]).getBanNumber();
				}else{
					entryStr=entryStr+((BanBasedataEntryInfo) entry[i]).getBanNumber()+";";
				}
				entryInfo.setBanBasedataEntry((BanBasedataEntryInfo) entry[i]);
				buildingInfo.getBanBasedataEntryList().add(entryInfo);
			}
		}
		buildingInfo.setDescription(entryStr);
		
		buildingInfo.getAreaEntry().clear();
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row=this.kdtEntry.getRow(i);
			BuildingAreaEntryInfo areaEntry=(BuildingAreaEntryInfo) row.getUserObject();
			areaEntry.setArea((BigDecimal) row.getCell("area").getValue());
			buildingInfo.getAreaEntry().add(areaEntry);
		}
	}

	protected IObjectValue createNewData() {
		BuildingInfo buildingInfo = new BuildingInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		if(null==sellProject){
			FDCMsgBox.showWarning("请选择项目节点进行此操作！") ;
			SysUtil.abort();

		}
		FilterInfo filterParent = new FilterInfo();
		filterParent.getFilterItems().add(new FilterItemInfo("parent.id",sellProject.getId()
				.toString()));
		try {
			if(SellProjectFactory.getRemoteInstance().exists(filterParent)){
				//末节点才能新增新增
				FDCMsgBox.showWarning("请在末级节点项目下新增楼栋！") ;
				SysUtil.abort();
			}
		} catch (EASBizException e1) {
			// TODO Auto-generated catch block
			logger.warn("楼栋编辑失败！");
			FDCMsgBox.showWarning("楼栋编辑失败！");
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			logger.warn("楼栋编辑失败！");
			FDCMsgBox.showWarning("楼栋编辑失败！");
		}
//		SubareaInfo subarea = (SubareaInfo) this.getUIContext().get("subarea");
		buildingInfo.setSellProject(sellProject);
//		buildingInfo.setSubarea(subarea);
//		buildingInfo.setCodingType(CodingTypeEnum.FloorNum);
		buildingInfo.setUnitCount(0);
		buildingInfo.setFloorCount(0);
		buildingInfo.setSubFloorCount(0);
//		buildingInfo.setCustomerLiftCount(0);
//		buildingInfo.setCargoLiftCount(0);
//		buildingInfo.setConditionType(ConditionTypeEnum.CENTER);
//		buildingInfo.setNetWorkType(NetWorkTypeEnum.TELECOM);
		buildingInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		//buildingInfo.setBuildingStructure(BuildingStructureEnum.KuangJia);
		if(Boolean.TRUE==(Boolean)this.getUIContext().get("COPY")){
			if(null!= this.getUIContext().get("cId")){
				try {
					SelectorItemCollection selector = new SelectorItemCollection();
					
					selector.add("sellProject.*");
					selector.add("buildingProperty.*");
					selector.add("buildingStructure.*");
					selector.add("productType.*");
					selector.add("*");
//					selector.add("completeDate");
//					selector.add("startDate");
//					selector.add("joinInDate");
//					selector.add("buildingTerraArea");
//					selector.add("buildingArea");
//					selector.add("buildingHeight");
//					selector.add("floorHeight");
//					selector.add("subFloorCount");
//					selector.add("floorCount");
//					selector.add("useRate");
//					selector.add("sellCertification");
//					selector.add("isGetCertificated");
//					selector.add("buildingHeight");
				BuildingInfo	buildingCopy = ((IBuilding)this.getBizInterface()).getBuildingInfo(
							new ObjectUuidPK(BOSUuid.read(this.getUIContext().get("cId").toString())),selector);
					buildingCopy.setId(null);
					buildingCopy.setName(null);
					buildingCopy.setNumber(null);
					buildingInfo.setCompleteDate(buildingCopy.getCompleteDate());
					buildingInfo.setSellProject(buildingCopy.getSellProject());
					buildingInfo.setBuildingProperty(buildingCopy.getBuildingProperty());
					buildingInfo.setBuildingStructure(buildingCopy.getBuildingStructure());
					buildingInfo.setProductType(buildingCopy.getProductType());
					buildingInfo.setStartDate(buildingCopy.getStartDate());
					buildingInfo.setJoinInDate(buildingCopy.getJoinInDate());
					buildingInfo.setBuildingTerraArea(buildingCopy.getBuildingTerraArea());
					buildingInfo.setBuildingArea(buildingCopy.getBuildingArea());
					buildingInfo.setFloorHeight(buildingCopy.getFloorHeight());
					buildingInfo.setSubFloorCount(buildingCopy.getSubFloorCount());
					buildingInfo.setFloorCount(buildingCopy.getFloorCount());
					buildingInfo.setFloorHeight(buildingCopy.getFloorHeight());
					buildingInfo.setUseRate(buildingCopy.getUseRate());
					buildingInfo.setSellCertification(buildingCopy.getSellCertification());
					buildingInfo.setIsGetCertificated(buildingCopy.isIsGetCertificated());
					buildingInfo.setBuildingHeight(buildingCopy.getBuildingHeight());
					buildingInfo.setSellCertifiDate(buildingCopy.getSellCertifiDate());
					buildingInfo.setStructureDate(buildingCopy.getStructureDate());
				} catch (EASBizException e) {
					// TODO Auto-generated catch block
					logger.warn("复制楼栋失败！");
					FDCMsgBox.showWarning("复制楼栋失败！");
				} catch (BOSException e) {
					// TODO Auto-generated catch block
					logger.warn("复制楼栋失败！");
					FDCMsgBox.showWarning("复制楼栋失败！");
				} catch (UuidException e) {
					// TODO Auto-generated catch block
					logger.warn("复制楼栋失败！");
					FDCMsgBox.showWarning("复制楼栋失败！");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.warn("复制楼栋失败！");
					FDCMsgBox.showWarning("复制楼栋失败！");
				}
			}
			
		}
		return buildingInfo;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("*");
		selectors.add("floorHeight");
		selectors.add("sellProject.*");
		selectors.add("productType.*");
		selectors.add("buildingProperty.*");
		selectors.add("constructPart.*");
		selectors.add("subarea.*");
		
		selectors.add("buildingStructure.*");
		selectors.add("roomModels.roomModelType.*");
		selectors.add("units.*");
		selectors.add("floorEntry.*");
		
		selectors.add("banBasedataEntry.productType.*");
		selectors.add("banBasedataEntry.*");
		selectors.add("banBasedataEntryList.banBasedataEntry.*");
		selectors.add("areaEntry.*");
		return selectors;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return BuildingFactory.getRemoteInstance();
	}


	

	public boolean isModify() {
		boolean isModify = super.isModify();
		
		return isModify;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
	  
	   this.tabPanel.setSelectedIndex(0);
	   if(null==((BuildingInfo)editData).getSellProject()){
			MsgBox.showInfo("请选择项目节点！");
			abort();
	   }
	   if (this.txtNumber.isEditable()) {
		if (StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("楼栋编码必须录入！");
			abort();
		 }
		}
		if (StringUtils.isEmpty(this.txtName.getText())) {
			MsgBox.showInfo("楼栋名称必须录入！");
			abort();
		}
		
//		if (this.f7BuildingProperty.getValue() == null) {
//			MsgBox.showInfo("建筑性质必须录入！");
//			abort();
//		}
		if (this.f7ProductType.getValue() == null) {
			MsgBox.showInfo("产品类型必须录入！");
			abort();
		}
		
//		if(this.comboBuildingStructure.getValue() == null)
//		{
//			MsgBox.showInfo("建筑结构必须录入！");
//			abort();
//		}
	
		
		Date joinInDate = this.pkJoinInDate.getTimestamp();
		Date completeDate = this.pkCompleteDate.getTimestamp();
		if(joinInDate != null  &&  completeDate != null){
			if(joinInDate.before(completeDate)){
				MsgBox.showInfo("入住日期不能早于竣工日期！");
				abort();
			}
		}
		
		if(null!=this.kDStartDate.getValue()&&null!=this.pkCompleteDate.getValue()){
			if(((Date)this.kDStartDate.getTimestamp()).after(this.pkCompleteDate.getTimestamp()))
			{
				MsgBox.showInfo("开工日期不能晚于竣工日期！");
				abort();
			}
		}
		if (this.isGetCertificated.isSelected()&&this.pkSellCertifiDate.getValue() == null) {
			MsgBox.showInfo("取得预证日期必须录入！");
			abort();
		}
		if(this.prmtBanBasedataEntry.isRequired()&&this.prmtBanBasedataEntry.getValue()==null){
			MsgBox.showInfo("主数据楼栋必须录入！");
			abort();
		}
		Map numberMap = new HashMap();
		Map nameMap = new HashMap();
		
	}
	
	/**
     * output isGetCertificated_stateChanged method
     */
	public void isGetCertificated_actionPerformed(java.awt.event.ActionEvent e){
	      //write your code here
		if(true==this.isGetCertificated.isSelected()){
			this.contSellCertifiNum.setVisible(true);
			this.contSellCertifiDate.setVisible(true);
    	}else{
    		this.contSellCertifiNum.setVisible(false);
    		this.contSellCertifiDate.setVisible(false);
    	}
	}
	

	protected void spSubFloor_stateChanged(ChangeEvent e) throws Exception
	{
		 int downCount = ((Integer)this.spSubFloor.getValue()).intValue();
		 int upCount = ((Integer)this.spiFloorCount.getValue()).intValue();
	}
	protected void spiFloorCount_stateChanged(ChangeEvent e) throws Exception
	{
		 int count = ((Integer)this.spiFloorCount.getValue()).intValue();
		 this.kdtEntry.removeRows();
		 for(int i=0;i<count;i++){
			 IRow row=this.kdtEntry.addRow();
			 row.getCell("floor").setValue(i+1);
			 row.setUserObject(new BuildingAreaEntryInfo());
		 }
		 CRMClientHelper.getFootRow(this.kdtEntry, new String[]{"area"});
	}
	
	  /**
     * output tfUserRate_dataChanged method
     */
    protected void tfUserRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	//显示百分号
//    	NumberFormat num = NumberFormat.getPercentInstance(); 
//    	num.setMaximumIntegerDigits(4); 
//    	num.setMaximumFractionDigits(2); 
//    	this.tfUserRate.setText(num.format(this.tfUserRate.getValue(Double.class)));
    }
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
    	
    	((BuildingInfo)this.editData).getSellProject().setName(((SellProjectInfo) this.getUIContext()
				.get("sellProject")).getName());
    	super.actionCopy_actionPerformed(e);
    }
}