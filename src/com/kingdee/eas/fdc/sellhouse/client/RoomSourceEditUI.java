/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.SHERevBillCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BankPaymentInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo;
import com.kingdee.eas.fdc.sellhouse.EyeSignhtInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.NoiseInfo;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ProductDetialInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaChangeHisCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAreaChangeHisFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaChangeHisInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFormInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomModePicCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModePicFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModePicInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustHisCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustHisFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustHisInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomUsageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomWindowsEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaFactory;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.fi.newrpt.client.designer.io.WizzardIO;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.enums.EnumUtils;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

/**
 * output class name
 */
public class RoomSourceEditUI extends AbstractRoomSourceEditUI {
	
	private static Logger logger = Logger
	.getLogger("com.kingdee.eas.fdc.sellhouse.client.RoomEditUI");

	private SellProjectInfo sellProject = null;

	private SubareaInfo subarea = null;
	private Graphics og; //add by shilei
	private BuildingInfo buildingInfo = null;
	Map orgMap = FDCSysContext.getInstance().getOrgMap();
	SHEImagePanel pnlRoomPic = null;
	protected com.kingdee.bos.ctrl.swing.KDTree treeMain;

	/**
	 * output class constructor
	 */
	public RoomSourceEditUI() throws Exception {
		super();
	}

	 public void initAllF7() throws EASBizException, BOSException{
    	//视野 
    	EntityViewInfo viewEysSight = new EntityViewInfo();
    	FilterInfo filterEysSight = new FilterInfo();
    	
    	FilterInfo sellProjectFilter=new FilterInfo();
    	sellProjectFilter.getFilterItems().add(new FilterItemInfo("sellProject.id",SHEManageHelper.getParentSellProject(null, ((RoomInfo)editData).getBuilding().getSellProject()).getId()));
    	sellProjectFilter.getFilterItems().add(new FilterItemInfo("orgUnit.id",SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId()));
    	sellProjectFilter.getFilterItems().add(new FilterItemInfo("sellProject.id",null,CompareType.IS));
    	
    	sellProjectFilter.setMaskString("#0 or (#1 and #2)");
    	
    	filterEysSight.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
     	filterEysSight.getFilterItems().add(new FilterItemInfo("type.id","pj3Lru9+QVqK/PZoIaNxYLyNrUg="));
     	filterEysSight.mergeFilter(sellProjectFilter, "and");
    	viewEysSight.setFilter(filterEysSight);
    	this.f7EyeSihnht.setEntityViewInfo(viewEysSight);
    	
    	//景观
    	EntityViewInfo viewSight = new EntityViewInfo();
    	FilterInfo filterSight = new FilterInfo();
    	filterSight.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterSight.getFilterItems().add(new FilterItemInfo("type.id","aDya2NbITr+ymXXU7/kCW7yNrUg="));
    	filterSight.mergeFilter(sellProjectFilter, "and");
    	viewSight.setFilter(filterSight);
       	this.f7Sight.setEntityViewInfo(viewSight);
    	
       	//噪音
    	EntityViewInfo viewNose = new EntityViewInfo();
    	FilterInfo filterNose = new FilterInfo();
    	filterNose.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterNose.getFilterItems().add(new FilterItemInfo("type.id","Cr7p6ri/QWWy+vdLTV1erLyNrUg="));
    	filterNose.mergeFilter(sellProjectFilter, "and");
    	viewNose.setFilter(filterNose);
     	this.f7Noise.setEntityViewInfo(viewNose);
    	
     	//建筑结构
    	EntityViewInfo viewBuilStruct = new EntityViewInfo();
    	FilterInfo filterBuilStruct = new FilterInfo();
    	filterBuilStruct.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	viewBuilStruct.setFilter(filterBuilStruct);
     	this.prmtBuildStruct.setEntityViewInfo(viewBuilStruct);
    	
     	//装修标准
    	EntityViewInfo viewDecoraStd = new EntityViewInfo();
    	FilterInfo filterDecoraStd = new FilterInfo();
    	filterDecoraStd.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterDecoraStd.getFilterItems().add(new FilterItemInfo("type.id","YPBFahQ6TY+RUDdxyiElfryNrUg="));
    	filterDecoraStd.mergeFilter(sellProjectFilter, "and");
    	viewDecoraStd.setFilter(filterDecoraStd);
    	this.f7DecoraStd.setEntityViewInfo(viewDecoraStd);
    
//	    	this.prmtRoomModel.setEntityViewInfo(view);
    	
    	
      
//	      	this.prmtRoomModel.setEntityViewInfo(view);
    	//交房标准
    	EntityViewInfo viewPossStd = new EntityViewInfo();
    	FilterInfo filterPossStd  = new FilterInfo();
    	filterPossStd .getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterPossStd.getFilterItems().add(new FilterItemInfo("type.id","ByHexvLVSEusji3qeetJHLyNrUg="));
    	filterPossStd.mergeFilter(sellProjectFilter, "and");
    	viewPossStd .setFilter(filterPossStd );
      	this.f7PosseStd.setEntityViewInfo(viewPossStd);
   
//	     	//产品描述
      	EntityViewInfo viewProductDetail = new EntityViewInfo();
    	FilterInfo filterProductDetail  = new FilterInfo();
    	filterProductDetail .getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterProductDetail.getFilterItems().add(new FilterItemInfo("type.id","/uTPJ0sVSbOC8OFIdM+Nx7yNrUg="));
    	filterProductDetail.mergeFilter(sellProjectFilter, "and");
    	viewProductDetail .setFilter(filterProductDetail );
      	this.f7ProductDetail.setEntityViewInfo(viewProductDetail);
//	     	//房间用途
//	    	EntityViewInfo viewRoomUsage = new EntityViewInfo();
//	    	FilterInfo filterRoomUsage = new FilterInfo();
//	    	filterRoomUsage.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
//	    	filterRoomUsage.getFilterItems().add(new FilterItemInfo("type.id","zzYdGSPQSuWiAJXK3JKjcbyNrUg="));
//	    	viewRoomUsage.setFilter(filterRoomUsage);
//	      	this.pmr.setEntityViewInfo(viewRoomUsage);

    	//有效 
    	EntityViewInfo view =new EntityViewInfo();
    	FilterInfo filter =new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	view.setFilter(filter);
    	this.f7Direction.setEntityViewInfo(view);
    	//楼栋属性
 
    	this.f7BuildingProperty.setEntityViewInfo(view);
    	
    	//产品类型

    	this.f7ProductType.setEntityViewInfo(view);
    	
    	if(null!=SHEHelper.getShowUnitWithRoomDes(buildingInfo)){
    		this.prmtBuildUnit.setEnabled(false);
    	}
    }
	 /**
		 * 加载图片
		 * */
	private void addImage(byte[] imageFile) //add by shilei
	{
		File file;
		try {
			if(imageFile!=null)
			{
				file = File.createTempFile("tmp1111", "a.jpg");
				ByteInputStream stream = new ByteInputStream(imageFile, imageFile.length);
				FileOutputStream out = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int size = -1;
				while((size = stream.read(buffer)) != -1){
					out.write(buffer, 0, size);
				}
				out.flush();
				stream.close();
				out.close();
				showImage(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * 显示图片Panel
	 * */
	SHEImagePanel RoomPic = new SHEImagePanel(){ //add by shilei
		public void paint(Graphics g) {
			super.paint(g);
			og=g.create();
		}
	};
	/**
	 * 设置显示图片
	 * @param file
	 */
	private void showImage(File file){ //add by shilei
		RoomPic.setImageFile(file);
		this.scPanel.setViewportView(RoomPic);
	}
	public void loadFields() {
		this.setImage();
		super.loadFields();
		RoomInfo room = (RoomInfo)this.editData;
		try {
			String roomModelId = room.getRoomModel().getId().toString();
			RoomModelInfo mInfo = RoomModelFactory.getRemoteInstance().getRoomModelInfo(new ObjectUuidPK(roomModelId));
			String  picColl= mInfo.getPic().get(0).getId().toString();
			RoomModePicInfo info = RoomModePicFactory.getRemoteInstance().getRoomModePicInfo(new ObjectUuidPK(picColl));
			addImage(info.getImgData());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		if (!CodingTypeEnum.UnitFloorNum.equals(buildingInfo.getCodingType())) {
//			this.prmtBuildUnit.setEnabled(false);
//		} else {
			this.prmtBuildUnit.setRequired(true);
//		}
		SpinnerNumberModel model = null;
//		if(null!=this.getUIContext().get("maxfloor")&&
//				null!=this.getUIContext().get("minfloor")){
//			model = new SpinnerNumberModel(1,  ((Integer)this.getUIContext().get("minfloor")).intValue(), ((Integer)this.getUIContext().get("maxfloor")).intValue(), 1);
//		}else{
//			model = new SpinnerNumberModel(1, 0, buildingInfo.getFloorCount(), 1);
//		}
//		
//		spiFloor.setModel(model);
		
		
//		if(null!=this.getUIContext().get("minseq")&&
//				null!=this.getUIContext().get("maxseq")){
//			model = new SpinnerNumberModel(1,  ((Integer)this.getUIContext().get("minseq")).intValue(),
//					((Integer)this.getUIContext().get("maxseq")).intValue(), 1);
//		}else{
//			model = new SpinnerNumberModel(1, 0, 10000, 1);
//		}
//	
//		spiSerialNumber.setModel(model);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		//filter.getFilterItems().add(new FilterItemInfo("building.id", this.buildingInfo.getId().toString()));
		this.txtName.setText(room.getName());
		this.txthoursnumber.setText(room.getNumber()); //add by shilei
		this.f7RoomModel.setEntityViewInfo(view);

		this.txtProjectName.setText(sellProject.getName());
	

		this.txtBuilding.setText(room.getBuilding().getName());
		this.txtBuilding.setUserObject(room.getBuilding());
		
		
		this.txtDisplayName.setText(room.getDisplayName());
		this.spiFloor.setValue(new Integer(room.getFloor()));
		this.spiSerialNumber.setValue(new Integer(room.getSerialNumber()));
		this.txtBuildingArea.setValue(room.getBuildingArea());
		this.txtRoomArea.setValue(room.getRoomArea());
		
		this.txtBalconyArea.setValue(room.getBalconyArea());
		this.txtApportionArea.setValue(room.getApportionArea());
		this.txtGuardenArea.setValue(room.getGuardenArea());
		this.txtCarbarnArea.setValue(room.getCarbarnArea());
		this.txtUseArea.setValue(room.getUseArea());
		this.txtFlatArea.setValue(room.getFlatArea());
		this.txtActualBuildingArea.setValue(room.getActualBuildingArea());
		this.txtActualRoomArea.setValue(room.getActualRoomArea());
		
		this.f7Direction.setValue(room.getDirection());
//		this.f7Sight.setValue(room.getSight());
		this.f7RoomModel.setValue(room.getRoomModel());
		
		this.f7BuildingProperty.setValue(room.getBuildingProperty());
		this.f7ProductType.setValue(room.getProductType());
//		this.f7ProductDetail.setValue(room.getProductDetail());
	
		this.prmtBuildUnit.setValue(room.getBuildUnit());

//		this.f7EyeSihnht.setValue(room.getEyeSignht());
//		this.f7DecoraStd.setValue(room.getDecoraStd());
//		this.f7Noise.setValue(room.getNoise());
//		this.f7PosseStd.setValue(room.getPosseStd());
		this.f7DecoraStd.setValue(room.getNewDecorastd());
		this.f7EyeSihnht.setValue(room.getNewEyeSight());
		this.f7Noise.setValue(room.getNewNoise());
		this.f7PosseStd.setValue(room.getNewPossStd());
		this.f7ProductDetail.setValue(room.getNewProduceRemark());
		this.f7Sight.setValue(room.getNewSight());
		//by tim_gao新加字段
		this.txtRoomCertifiName.setText(room.getRoomCertifiName());
		this.txtPlanBuildingArea.setValue(room.getPlanBuildingArea());
		this.txtPlanRoomArea.setValue(room.getPlanRoomArea());
		this.prmtBuildStruct.setValue(room.getBuilStruct());
		this.comboHouseProperty.setSelectedItem(room.getHouseProperty());
		this.txtIbaInnside.setValue(room.getIbaInnside()); //add by shilei
		this.txtIbameasured.setValue(room.getIbameasured());
		this.txtIbasement.setValue(room.getIbasement());
		this.txtInsside.setValue(room.getInsside());
		
	
			
		 
		if (room.getImgData() != null) {
			this.pnlRoomPic.setImageData(room.getImgData());
		} else {
			this.pnlRoomPic.setImageData(null);
//			this.txtRoomPic.setText(null);
		}
		if (this.getOprtState().equals("VIEW")) {
//			this.btnRoomPic.setVisible(false);
//			this.contRoomPic.setVisible(false);
		}
//		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
//		if (!saleOrg.isIsBizUnit()) {
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//		}
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
		
		}else{
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
		}
//		loadRoomBill(room);
	}

		

	

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		RoomInfo room = (RoomInfo) this.editData;
		if (!room.getSellState().equals(RoomSellStateEnum.Init) && !room.getSellState().equals(RoomSellStateEnum.OnShow)) {
			MsgBox.showInfo("未推盘或待售房间才能修改!");
			return;
		}
		if(room.getTenancyState()!=null){
			if (!(room.getTenancyState().equals(TenancyStateEnum.unTenancy) 
					|| room.getTenancyState().equals(TenancyStateEnum.waitTenancy))) {
				MsgBox.showInfo("只有未推盘、未放租、待售或待租的房间才能修改!");
				return;
			}
		}
		super.actionEdit_actionPerformed(e);
//		this.btnRoomPic.setVisible(true);
//		this.contRoomPic.setVisible(true);
		if (!buildingInfo.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
			this.prmtBuildUnit.setEnabled(false);
		}
		if (this.getOprtState().equals("EDIT")) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
			
		}

		setAreaEditable();
	}

//	protected void btnRoomPic_actionPerformed(ActionEvent e) throws Exception {
//		super.btnRoomPic_actionPerformed(e);
//		KDFileChooser chsFile = new KDFileChooser();
//		String JPG = "jpg";
//		String Key_File = "Key_File";
//		SimpleFileFilter Filter_JPG = new SimpleFileFilter(JPG, "JPG" + LanguageManager.getLangMessage(Key_File, WizzardIO.class, "操作失败"));
//		chsFile.addChoosableFileFilter(Filter_JPG);
//		int ret = chsFile.showOpenDialog(this);
//		if (ret != JFileChooser.APPROVE_OPTION) {
//			SysUtil.abort();
//		} else {
//			File file = chsFile.getSelectedFile();
//			if (file == null)
//				return;
//			String path = file.getPath();
//			String fileType = path.substring(path.lastIndexOf(".") + 1, path.length());
//			if (JPG.equals(fileType) || fileType.equals("JPG")) {
//				this.setImage();
//				this.txtRoomPic.setText(file.getPath());
//				this.pnlRoomPic.setImageFile(file);
//			} else {
//				MsgBox.showInfo("格式不正确，请插入正确的图片文件!");
//				this.abort();
//			}
//		}
//	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		RoomInfo room = (RoomInfo) this.editData;
		

		BuildingUnitInfo unitInfo = (BuildingUnitInfo) this.prmtBuildUnit.getValue();
		room.setBuildUnit(unitInfo);
		room.setFloor(((Integer)this.spiFloor.getValue()).intValue());
		room.setSerialNumber(((Integer)this.spiSerialNumber.getValue()).intValue());
		if(room.getEndSerialNumber()==0){
			room.setEndSerialNumber(((Integer)this.spiSerialNumber.getValue()).intValue());
		}
		if(null==this.getUIContext().get("ROOMFORDES")){
			String longName = this.sellProject.getName();
			if(this.subarea!=null) longName += "-" + this.subarea.getName();
			longName += "-" + this.buildingInfo.getName();
			if(unitInfo!=null) longName += "-" + unitInfo.getName(); 
			room.setName(longName + "-" + room.getNumber());		
		}
		
		
		if (unitInfo != null) {
			room.setUnit(unitInfo.getSeq());
		}

		// room.setFloor(((Integer) spiFloor.getValue()).intValue()); 已经替换为对象楼层
		room.setSerialNumber(((Integer) spiSerialNumber.getValue()).intValue());
		if (room.getEndSerialNumber() < room.getSerialNumber()) {
			room.setEndSerialNumber(room.getSerialNumber());
		}
		
		if(this.getUIContext().get("ROOMFORDES") != null){
			if(Boolean.TRUE.equals(this.getUIContext().get("ISFIXED"))){
				//会在得到context的时候计算的
				room.setBuilding(buildingInfo);
				room.setUnit(((BuildingUnitInfo)this.prmtBuildUnit.getValue()).getSeq());
				room.setFloor(((Integer)this.spiFloor.getValue()).intValue());
//				room.setName(this.txtName.getText());
				room.setNumber(this.txthoursnumber.getText());  //add by shilei
				room.setSerialNumber(((Integer)this.spiSerialNumber.getValue()).intValue());
				room.setEndSerialNumber(((Integer)this.spiSerialNumber.getValue()).intValue());
			}else{
				RoomInfo roomTrans = (RoomInfo) this.getUIContext().get("ROOMFORDES");
				room.setBuilding(roomTrans.getBuilding());
				room.setUnit(roomTrans.getUnit());
				room.setFloor(roomTrans.getFloor());
				room.setName(this.txtName.getText());
				room.setNumber(this.txthoursnumber.getText()); //add by shilei
				room.setNumber(roomTrans.getNumber());
				room.setDisplayName(roomTrans.getDisplayFormat());
				room.setSerialNumber(roomTrans.getSerialNumber());
				room.setEndSerialNumber(roomTrans.getEndSerialNumber());
			}
		
		}
		//房源名称
		room.setName(this.txtName.getText());
		room.setNumber(this.txthoursnumber.getText());  //add by shilei
		room.setDisplayName(this.txtDisplayName.getText());
		room.setBuildingArea(this.txtBuildingArea.getBigDecimalValue());
		room.setRoomArea(this.txtRoomArea.getBigDecimalValue());
		room.setBalconyArea(this.txtBalconyArea.getBigDecimalValue());
		room.setApportionArea(this.txtApportionArea.getBigDecimalValue());
		room.setGuardenArea(this.txtGuardenArea.getBigDecimalValue());
		room.setCarbarnArea(this.txtCarbarnArea.getBigDecimalValue());
		room.setUseArea(this.txtUseArea.getBigDecimalValue());
		room.setFlatArea(this.txtFlatArea.getBigDecimalValue());
		room.setActualBuildingArea(this.txtActualBuildingArea.getBigDecimalValue());
		room.setActualRoomArea(this.txtActualRoomArea.getBigDecimalValue());
		room.setDirection((HopedDirectionInfo) f7Direction.getValue());
//		room.setSight((SightRequirementInfo) f7Sight.getValue());
		room.setRoomModel((RoomModelInfo) this.f7RoomModel.getValue());
		room.setBuildingProperty((BuildingPropertyInfo) this.f7BuildingProperty.getValue());
		room.setProductType((ProductTypeInfo) this.f7ProductType.getValue());
//		room.setProductDetail((ProductDetialInfo) this.f7ProductDetail.getValue());
//		room.setEyeSignht((EyeSignhtInfo) this.f7EyeSihnht.getValue());
//		room.setNoise((NoiseInfo) this.f7Noise.getValue());
//		room.setPosseStd((PossessionStandardInfo) this.f7PosseStd.getValue());
//		room.setDecoraStd((DecorationStandardInfo) this.f7DecoraStd.getValue());

		room.setImgData(this.pnlRoomPic.getImageData());
		room.setHouseProperty((HousePropertyEnum) this.comboHouseProperty.getSelectedItem());
		
		room.setRoomCertifiName(this.txtRoomCertifiName.getText());
		room.setPlanBuildingArea(FDCHelper.toBigDecimal(this.txtPlanBuildingArea.getBigDecimalValue()));
		room.setPlanRoomArea(FDCHelper.toBigDecimal(this.txtPlanRoomArea.getBigDecimalValue()));
		room.setBuilStruct((BuildingStructureInfo)this.prmtBuildStruct.getValue());
		
		room.setIbaInnside((BigDecimal) this.txtIbaInnside.getValue()); //add by shilei
		room.setIbameasured((BigDecimal)this.txtIbameasured.getValue());
		room.setIbasement((BigDecimal)this.txtIbasement.getValue());
		room.setInsside((BigDecimal)this.txtInsside.getValue());
		
		room.setNewDecorastd((RoomAssistantInfo)this.f7DecoraStd.getValue());
		room.setNewEyeSight((RoomAssistantInfo)this.f7EyeSihnht.getValue());
		room.setNewNoise((RoomAssistantInfo)this.f7Noise.getValue());
		room.setNewPossStd((RoomAssistantInfo)this.f7PosseStd.getValue());
		room.setNewProduceRemark((RoomAssistantInfo)this.f7ProductDetail.getValue());
		room.setNewSight((RoomAssistantInfo)this.f7Sight.getValue());
		
		if(room.getBuilding()!=null&&room.getBuildUnit()!=null){
			try {
				SHEHelper.getLongCodeRoom(room);
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}

	private void setImage() {
		pnlRoomPic = new SHEImagePanel();
//		this.scPanel.setViewportView(this.pnlRoomPic);
	}

	protected IObjectValue createNewData() {
		//为双击界面
		
		this.buildingInfo = (BuildingInfo) this.getUIContext().get("building");
		try {
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("project.fullOrgUnit.name");
			this.sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(buildingInfo.getSellProject().getId().toString())), sels);
			if (this.buildingInfo.getSubarea() != null) {
				this.subarea = SubareaFactory.getRemoteInstance().getSubareaInfo(new ObjectUuidPK(BOSUuid.read(buildingInfo.getSubarea().getId().toString())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		//当界面为生成图双击界面 加载房间信息
//		if(this.getUIContext().get("ROOMFORDES") != null){
//			RoomInfo roomInfo = (RoomInfo) this.getUIContext().get("ROOMFORDES");
//			try {
//				
//				if(null!=roomInfo.getId()){
//					roomInfo = (RoomInfo)getBizInterface().getValue(new ObjectUuidPK(roomInfo.getId().toString()),SHEHelper.getRoomSelector(new SelectorItemCollection()));
//					return roomInfo;
//				}
//			} catch (EASBizException e) {
//				// TODO Auto-generated catch block
//				logger.warn("获取房间信息失败！");
//				FDCMsgBox.showWarning("获取房间信息失败！");
//			} catch (BOSException e) {
//				// TODO Auto-generated catch block
//				logger.warn("获取房间信息失败！");
//				FDCMsgBox.showWarning("获取房间信息失败！");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				logger.warn("获取房间信息失败！");
//				FDCMsgBox.showWarning("获取房间信息失败！");
//			}
//			
//		
//		}
		if(this.getUIContext().get("ROOMFORDES") != null){
			
			return  (RoomInfo) this.getUIContext().get("ROOMFORDES");
			
		}

		
		RoomInfo roomInfo = new RoomInfo();
		roomInfo.setSellState(RoomSellStateEnum.Init);
		roomInfo.setHouseProperty(HousePropertyEnum.NoAttachment);
		if (buildingInfo != null) {
			roomInfo.setBuilding(buildingInfo);
//			if (CodingTypeEnum.UnitFloorNum.equals(buildingInfo.getCodingType())) {
				BuildingUnitInfo unitInfo = (BuildingUnitInfo) this.getUIContext().get("unit");
				if (unitInfo != null) {
					roomInfo.setBuildUnit(unitInfo);
					this.prmtBuildUnit.setValue(unitInfo);
				}
//			}
			if(buildingInfo.getProductType()!=null){
				try {
					roomInfo.setProductType(ProductTypeFactory.getRemoteInstance().getProductTypeInfo(new ObjectUuidPK(buildingInfo.getProductType().getId())));
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		roomInfo.setFloor(1);
		roomInfo.setSerialNumber(1);

		// add by yaowei_wen 初始化房间的销售项目租售属性2009-06-09
		// ---------start
		roomInfo.setIsForSHE(sellProject.isIsForSHE());
		roomInfo.setIsForTen(sellProject.isIsForTen());
		roomInfo.setIsForPPM(sellProject.isIsForPPM());
//		if(FDCSysContext.getInstance().checkIsSHEOrg()){
//			roomInfo.setIsForSHE(true);
//			roomInfo.setIsForTen(false);
//			roomInfo.setIsForPPM(false);
//		}
		
		// ---------end
		return roomInfo;

	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}

	protected void checkIsOUSealUp() throws Exception {
		
	}
	
	public void onLoad() throws Exception {
		
		this.contBuildingProperty.setVisible(false);
		this.contFace.setVisible(false);
		this.kDLabelContainer4.setVisible(false);
		this.contCarbarnArea.setVisible(false);
		this.contFlatArea.setVisible(false);
		this.kDLabelContainer6.setVisible(false);
		this.contOutlook.setVisible(false);
		this.kDLabelContainer1.setVisible(false);
		this.contUseArea.setVisible(false);
		this.contBuildConstruct.setVisible(false);
		this.kDLabelContainer5.setVisible(false);
		this.kDLabelContainer7.setVisible(false);
		this.contRoomCertifiName.setVisible(false);
		this.contGuardenArea.setVisible(false);
	
		this.contRoomPic.setVisible(false);
		this.btnRoomPic.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		
		this.txtProjectName.setEnabled(false);
//		this.txthoursnumber.setEnabled(false);
		this.txtBuilding.setEnabled(false);
	

		this.txtBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtBuildingArea.setRemoveingZeroInEdit(false);
		this.txtBuildingArea.setNegatived(false);
		this.txtBuildingArea.setPrecision(3);
		this.txtBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingArea.setSupportedEmpty(true);

		this.txtRoomArea.setRemoveingZeroInDispaly(false);
		this.txtRoomArea.setRemoveingZeroInEdit(false);
		this.txtRoomArea.setNegatived(false);
		this.txtRoomArea.setPrecision(3);
		this.txtRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRoomArea.setSupportedEmpty(true);
		
		this.txtPlanRoomArea.setRemoveingZeroInDispaly(false);
		this.txtPlanRoomArea.setRemoveingZeroInEdit(false);
		this.txtPlanRoomArea.setNegatived(false);
		this.txtPlanRoomArea.setPrecision(3);
		this.txtPlanRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtPlanRoomArea.setSupportedEmpty(true);
		
		this.txtPlanBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtPlanBuildingArea.setRemoveingZeroInEdit(false);
		this.txtPlanBuildingArea.setNegatived(false);
		this.txtPlanBuildingArea.setPrecision(3);
		this.txtPlanBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtPlanBuildingArea.setSupportedEmpty(true);

		this.txtBalconyArea.setRemoveingZeroInDispaly(false);
		this.txtBalconyArea.setRemoveingZeroInEdit(false);
		this.txtBalconyArea.setNegatived(false);
		this.txtBalconyArea.setPrecision(3);
		this.txtBalconyArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBalconyArea.setSupportedEmpty(true);

		this.txtApportionArea.setRemoveingZeroInDispaly(false);
		this.txtApportionArea.setRemoveingZeroInEdit(false);
		this.txtApportionArea.setNegatived(false);
		this.txtApportionArea.setPrecision(3);
		this.txtApportionArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtApportionArea.setSupportedEmpty(true);
		
		this.txtGuardenArea.setRemoveingZeroInDispaly(false);
		this.txtGuardenArea.setRemoveingZeroInEdit(false);
		this.txtGuardenArea.setNegatived(false);
		this.txtGuardenArea.setPrecision(3);
		this.txtGuardenArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtGuardenArea.setSupportedEmpty(true);

		this.txtCarbarnArea.setRemoveingZeroInDispaly(false);
		this.txtCarbarnArea.setRemoveingZeroInEdit(false);
		this.txtCarbarnArea.setNegatived(false);
		this.txtCarbarnArea.setPrecision(3);
		this.txtCarbarnArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtCarbarnArea.setSupportedEmpty(true);

		this.txtUseArea.setRemoveingZeroInDispaly(false);
		this.txtUseArea.setRemoveingZeroInEdit(false);
		this.txtUseArea.setNegatived(false);
		this.txtUseArea.setPrecision(3);
		this.txtUseArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtUseArea.setSupportedEmpty(true);

		this.txtFlatArea.setRemoveingZeroInDispaly(false);
		this.txtFlatArea.setRemoveingZeroInEdit(false);
		this.txtFlatArea.setNegatived(false);
		this.txtFlatArea.setPrecision(3);
		this.txtFlatArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFlatArea.setSupportedEmpty(true);

		this.txtActualBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtActualBuildingArea.setRemoveingZeroInEdit(false);
		this.txtActualBuildingArea.setNegatived(false);
		this.txtActualBuildingArea.setPrecision(3);
		this.txtActualBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActualBuildingArea.setSupportedEmpty(true);

		this.txtActualRoomArea.setRemoveingZeroInDispaly(false);
		this.txtActualRoomArea.setRemoveingZeroInEdit(false);
		this.txtActualRoomArea.setNegatived(false);
		this.txtActualRoomArea.setPrecision(3);
		this.txtActualRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActualRoomArea.setSupportedEmpty(true);
		
		this.txtIbasement.setRemoveingZeroInDispaly(false);  //add by shilei
		this.txtIbasement.setRemoveingZeroInEdit(false);
		this.txtIbasement.setNegatived(false);
		this.txtIbasement.setPrecision(3);
		this.txtIbasement.setHorizontalAlignment(JTextField.RIGHT);
		this.txtIbasement.setSupportedEmpty(true);
		
		this.txtIbaInnside.setRemoveingZeroInDispaly(false);  //add by shilei
		this.txtIbaInnside.setRemoveingZeroInEdit(false);
		this.txtIbaInnside.setNegatived(false);
		this.txtIbaInnside.setPrecision(3);
		this.txtIbaInnside.setHorizontalAlignment(JTextField.RIGHT);
		this.txtIbaInnside.setSupportedEmpty(true);
		
		this.txtIbameasured.setRemoveingZeroInDispaly(false);  //add by shilei
		this.txtIbameasured.setRemoveingZeroInEdit(false);
		this.txtIbameasured.setNegatived(false);
		this.txtIbameasured.setPrecision(3);
		this.txtIbameasured.setHorizontalAlignment(JTextField.RIGHT);
		this.txtIbameasured.setSupportedEmpty(true);
		
		this.txtInsside.setRemoveingZeroInDispaly(false);  //add by shilei
		this.txtInsside.setRemoveingZeroInEdit(false);
		this.txtInsside.setNegatived(false);
		this.txtInsside.setPrecision(3);
		this.txtInsside.setHorizontalAlignment(JTextField.RIGHT);
		this.txtInsside.setSupportedEmpty(true);

//		if(null==this.getUIContext().get("ROOMDISPLAY")){
			super.onLoad();
//		}
			initAllF7();
		this.menuSubmitOption.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.rMenuItemSubmitAndPrint.setVisible(false);
	

		this.spiFloor.setRequired(true);
		this.spiSerialNumber.setRequired(true);
//		this.f7RoomModel.setRequired(true);
		this.f7BuildingProperty.setRequired(true);
		this.f7ProductDetail.setRequired(false);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("level", new Integer(2)));
		if(this.sellProject!=null){

			BuildingPropertyInfo buildingProperty = this.sellProject.getBuildingProperty();
		}
		RoomInfo room = (RoomInfo) this.editData;

		if(this.sellProject!=null){
			this.f7RoomModel.setEntityViewInfo(SHEHelper.getModelBySellProjectView(sellProject));
		}

		// ----------
	
		// 没复核过并且没有楼栋ID的说明是意向查询的房间不能修改
		if (this.getUIContext().get("buildingID") == null) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionAddNew.setVisible(false);
		}
		// 没复核过但是有楼栋ID的说明是房间面积录入的房间可以修改
		if (!room.isIsAreaAudited() && this.getUIContext().get("buildingID") != null) {
			this.actionEdit.setEnabled(true);
			this.actionEdit.setVisible(true);
			this.actionAddNew.setEnabled(false);
			this.actionAddNew.setVisible(false);
		}
		if (room.isIsAreaAudited()) {
			this.actionAddNew.setEnabled(false);
			this.actionAddNew.setVisible(false);
		}

		

		setAreaEditable();
		this.storeFields();
		this.initOldData(this.editData);
//
//		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
//		if (!saleOrg.isIsBizUnit()) {
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//		}
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
		
		}else{
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
		}

		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);

		// this.setF7ProdcutDetailEntityViewAsOnload();
		// this.setF7RoomUsageEntityViewAsOnload();// add BY yaowei_wen
		// 2009-06-05
		// this.setF7PeoductTypeOnload();// add by yaowei_wen 2009-06-08

		setF7BuildUnitEntityView();
		this.groupFilterOnload();
		//当界面为生成图双击界面 处理几个控件
		if(this.getUIContext().get("ROOMFORDES") != null){
			this.contSerialNumber.setEnabled(false);
			this.spiFloor.setEditable(false);
			this.contFloor.setEnabled(false);
			this.spiSerialNumber.setEditable(false);
			this.btnRemove.setVisible(false);
			
//			this.kDTabbedPane1.remove(kDPanel2);
			
		}
		//当激活单个房间时，可以选择填入 
		if(null!=this.getUIContext().get("ISFIXED")&&	Boolean.TRUE.equals(this.getUIContext().get("ISFIXED"))){
			this.spiFloor.setEnabled(true);
			this.spiSerialNumber.setEnabled(true);
			this.spiFloor.setValue(new Integer(1));
			this.contSerialNumber.setEnabled(true);
			this.contFloor.setEnabled(true);
			this.spiSerialNumber.setValue(new Integer(1));
			this.txtName.setText(null);
			this.txthoursnumber.setText(null);  //add by shilei
			this.txtName.setEnabled(false);
			this.txthoursnumber.setEnabled(false);//add by shilei
			this.btnRemove.setVisible(false);
		}
		if(OprtState.VIEW.equals(this.getOprtState())){
		this.btnRemove.setEnabled(false);	
		if(null==this.getUIContext().get("ROOMFORDES")){
			this.actionEdit.setEnabled(true);
		}else{
			this.actionEdit.setEnabled(false);
		}
		
		}
		
		kDTabbedPane1.remove(this.panelBizBill);
		kDTabbedPane1.remove(this.panelPriceBill);
		kDTabbedPane1.remove(this.panelReceiveBill);
		
		if(OprtState.ADDNEW.equals(this.getOprtState())){
			this.actionEdit.setEnabled(false);
		}
		this.f7ProductType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//		this.f7ProductType.setEnabled(false);
		
		KDFileChooser chsFile = new KDFileChooser();
//		String JPG = "jpg";
//		String Key_File = "Key_File";
//		SimpleFileFilter Filter_JPG = new SimpleFileFilter(JPG, "JPG" + LanguageManager.getLangMessage(Key_File, WizzardIO.class, "操作失败"));
//		chsFile.addChoosableFileFilter(Filter_JPG);
//		int ret = chsFile.showOpenDialog(this);
//		if (ret != JFileChooser.APPROVE_OPTION) {
//			SysUtil.abort();
//		} else {
		if(room.getName()!=null){
			File file = chsFile.getSelectedFile();
			if (file == null)
				return;
//			String path = file.getPath();
//			String fileType = path.substring(path.lastIndexOf(".") + 1, path.length());
//			if (JPG.equals(fileType) || fileType.equals("JPG")) {
				this.setImage();
				this.txtRoomPic.setText(file.getPath());
				this.pnlRoomPic.setImageFile(file);
//			} 
		}
	}

	private void groupFilterOnload() {
		if (this.sellProject == null)
			return;
		String spId = sellProject.getId().toString();
		// 设置房间景观的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7Sight, spId, "房间景观", "RoomArch");
		// 设置产品描述的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7ProductDetail, spId, "产品描述", "RoomArch");
		// 设置房间朝向的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7Direction, spId, "房间朝向", "RoomArch");
		// 设置建筑性质的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7BuildingProperty, spId, "建筑性质", "RoomArch");
		// //设置户型类别的集团管控过滤
		// SHEHelper.SetGroupFilters(this.f7RoomModel, spId, "户型类别",
		// "RoomArch");

		// 设置产品类型的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7ProductType, spId, "产品类型", "RoomArch");	
		
		SHEHelper.SetGroupFilters(this.f7EyeSihnht, spId, "视野", "RoomArch");
		SHEHelper.SetGroupFilters(this.f7DecoraStd, spId, "装修标准", "RoomArch");
		SHEHelper.SetGroupFilters(this.f7PosseStd, spId, "交房标准", "RoomArch");
		SHEHelper.SetGroupFilters(this.f7Noise, spId, "噪音", "RoomArch");
	}

	// 单元控件过滤条件
	private void setF7BuildUnitEntityView() {
		String buildId = "null";
		if (buildingInfo != null)
			buildId = buildingInfo.getId().toString();
		EntityViewInfo unitView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.id", buildId));
		unitView.setFilter(filter);
		this.prmtBuildUnit.setEntityViewInfo(unitView);

//		if (!buildingInfo.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
//			this.contUnit.setEnabled(false);
//			this.prmtBuildUnit.setEnabled(false);
//		} else {
//			this.prmtBuildUnit.setRequired(true);
//		}
	}

	/**
	 * 设置产品描述的过滤
	 * 
	 * @author laiquan_luo
	 */
	private void setF7ProdcutDetailEntityViewAsOnload() {
		SellProjectInfo sp = (SellProjectInfo) this.getUIContext().get("sellProject");
		if (sp == null)
			return;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sp.getId().toString()));

		this.f7ProductDetail.setEntityViewInfo(view);
	}

	

	/**
	 * 设置产品类型的过滤
	 * 
	 * @author yaowei_wen
	 */
	private void setF7PeoductTypeOnload() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		this.f7ProductType.setEntityViewInfo(view);
	}

	/**
	 * 根据面积和实测面积是否复核来设置编辑房间界面的相应面积是否可编辑 面积复核后户型也不让改
	 * */
	protected void setAreaEditable() {
		if (!this.oprtState.equals("EDIT")) {
			return;
		}
		if (this.oprtState.equalsIgnoreCase("EDIT")) {
			this.prmtBuildUnit.setEnabled(false);
			this.spiFloor.setEnabled(false);
			this.spiSerialNumber.setEnabled(false);
		}
		RoomInfo room = (RoomInfo) this.editData;
		if (room.isIsAreaAudited()) {
			f7RoomModel.setRequired(false);
			f7RoomModel.setReadOnly(true);
			txtBuildingArea.setEditable(false);
			txtRoomArea.setEditable(false);
		}
		if (room.isIsActualAreaAudited()) {
			f7RoomModel.setRequired(false);
			f7RoomModel.setReadOnly(true);
			txtActualBuildingArea.setEditable(false);
			txtActualRoomArea.setEditable(false);
		}
		if (room.isIsActualAreaAudited() && room.isIsAreaAudited()) {
			f7RoomModel.setRequired(false);
			f7RoomModel.setReadOnly(true);
			txtBuildingArea.setEditable(false);
			txtRoomArea.setEditable(false);
			txtActualBuildingArea.setEditable(false);
			txtActualRoomArea.setEditable(false);
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("*");
		selectors.add("name");
		selectors.add("building.*");
		selectors.add("buildUnit.*");
		selectors.add("building.sellProject.*");
		selectors.add("buildingProperty.*");
		selectors.add("roomModel.*");
		selectors.add("direction.*");
		selectors.add("sight.*");
		selectors.add("roomForm.*");
		selectors.add("productType.*");
		selectors.add("productDetail.*");
		selectors.add("buildingFloor.*");
		selectors.add("roomUsage.*");
		selectors.add("noise.*");
		selectors.add("decoraStd.*");
		selectors.add("eyeSignht.*");
		selectors.add("posseStd.*");
		selectors.add("newNoise.*");
		selectors.add("newSight.*");
		selectors.add("newEyeSight.*");
		selectors.add("newDecorastd.*");
		selectors.add("newPossStd.*");
		selectors.add("newRoomUsage.*");
		selectors.add("balconyArea");
		selectors.add("newProduceRemark.*");
		selectors.add("builStruct.*");
		return selectors;
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		IObjectValue objectValue = super.getValue(pk);
		this.buildingInfo = ((RoomInfo) objectValue).getBuilding();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("orgUnit.name");
		this.sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(buildingInfo.getSellProject().getId().toString())), sels);
		if (this.buildingInfo.getSubarea() != null) {
			this.subarea = SubareaFactory.getRemoteInstance().getSubareaInfo(new ObjectUuidPK(BOSUuid.read(buildingInfo.getSubarea().getId().toString())));
		}
		return objectValue;
	}
	protected void initOldData(IObjectValue dataObject)
    {
    	
    }
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		
		if(null!=this.getUIContext().get("ROOMFORDES")){
			this.storeFields();
			this.getUIContext().put("editRoom", this.editData);
			
			this.destroyWindow();
			//释放
			RoomInfo room = (RoomInfo) this.getUIContext().get("ROOMFORDES");
			CacheServiceFactory.getInstance().discardType(room.getBOSType());
			
			return;
		}
		
		
		super.actionSubmit_actionPerformed(e);

//		this.setOprtState(OprtState.VIEW);
//		this.setOprtState(OprtState.ADDNEW);

		// this.storeFields();
		// this.initOldData(this.editData);

	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
		boolean isEdit = false;
		RoomInfo room = (RoomInfo) this.editData;
		if (room.getStandardTotalAmount() != null) {
			BigDecimal newRoomArea = this.txtRoomArea.getBigDecimalValue();
			if (newRoomArea == null) {
				newRoomArea = FDCHelper.ZERO;
			}
			BigDecimal oldRoomArea = room.getRoomArea();
			if (oldRoomArea == null) {
				oldRoomArea = FDCHelper.ZERO;
			}
			if (oldRoomArea.compareTo(newRoomArea) != 0) {
				isEdit = true;
			}
			BigDecimal newBuildingArea = this.txtBuildingArea.getBigDecimalValue();
			if (newBuildingArea == null) {
				newBuildingArea = FDCHelper.ZERO;
			}
			BigDecimal oldBuildingArea = room.getBuildingArea();
			if (oldBuildingArea == null) {
				oldBuildingArea = FDCHelper.ZERO;
			}
			if (oldBuildingArea.compareTo(newBuildingArea) != 0) {
				isEdit = true;
			}
			if (isEdit) {
				if (MsgBox.showConfirm2New(this, "已经定价,更改面积将清除原来价格,是否继续?") == MsgBox.NO) {
					this.abort();
				} else {
					room.setStandardTotalAmount(null);
					room.setBuildPrice(null);
					room.setRoomPrice(null);
				}
			}
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
//		if (StringUtils.isEmpty(this.txtName.getText())) {
//			MsgBox.showInfo("房间名称不能为空!");
//			this.abort();
//		}
//		if(StringUtils.isEmpty(this.txthoursnumber.getText())){
//			MsgBox.showInfo("房源编号不能为空");
//			this.abort();
//		}
		if(StringUtils.isEmpty(this.txtDisplayName.getText())){
			MsgBox.showInfo("房号不能为空");
			this.abort();
		}
		if(this.prmtBuildUnit.getValue()==null){
			MsgBox.showInfo("单元不能为空");
			this.abort();
		}
		RoomInfo room = (RoomInfo) this.editData;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.id", buildingInfo.getId().toString()));
		if (this.prmtBuildUnit.getValue() != null) {
			filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", ((BuildingUnitInfo) this.prmtBuildUnit.getValue()).getId().toString()));
		}

		filter.getFilterItems().add(new FilterItemInfo("floor", new Integer(room.getFloor())));
		filter.getFilterItems().add(new FilterItemInfo("serialNumber", new Integer(room.getSerialNumber())));
		if (room.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", room.getId().toString(), CompareType.NOTEQUALS));
		}
		if ((filter != null) && RoomFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("序号:" + room.getSerialNumber() + "已存在!");
			this.abort();
		}
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building", buildingInfo.getId().toString()));
		if (room.getBuildUnit() != null)
			filter.getFilterItems().add(new FilterItemInfo("buildUnit", room.getBuildUnit().getId()));
		filter.getFilterItems().add(new FilterItemInfo("name", room.getName()));
		if (RoomFactory.getRemoteInstance().exists(filter)) {
			if (room.getId() != null)
				filter.getFilterItems().add(new FilterItemInfo("id", room.getId().toString(), CompareType.NOTEQUALS));
		}
		if (RoomFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("房号:" + room.getName() + "已存在!");
			this.abort();
		}
		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building", buildingInfo.getId().toString()));
		if (room.getBuildUnit() != null)
			filter.getFilterItems().add(new FilterItemInfo("buildUnit", room.getBuildUnit().getId()));
		filter.getFilterItems().add(new FilterItemInfo("number", room.getNumber()));
		if (RoomFactory.getRemoteInstance().exists(filter)) {
			if (room.getId() != null)
				filter.getFilterItems().add(new FilterItemInfo("id", room.getId().toString(), CompareType.NOTEQUALS));
		}
		if (RoomFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("房源编码:" + room.getNumber() + "已存在!");
			this.abort();
		}
		if (this.spiFloor.getValue() == null) {
			MsgBox.showInfo("楼层不能为空！	");
			this.abort();
		}
		if (this.spiSerialNumber.getValue() == null) {
			MsgBox.showInfo("序号不能为空！	");
			this.abort();
		}
	
		if (this.f7ProductType.getValue() == null) {
			MsgBox.showInfo("产品类型不能为空！	");
			this.abort();
		}
//		if (this.f7RoomModel.getValue() == null) {
//			MsgBox.showInfo("户型不能为空!");
//			this.abort();
//		}
		
		
		BuildingUnitInfo buildUnit = (BuildingUnitInfo) this.prmtBuildUnit.getValue();
		
	
		if (buildUnit == null) {
			buildUnit = SHEHelper.getShowUnitWithRoomDes(buildingInfo);
			if(buildUnit == null){
				MsgBox.showInfo("单元不能为空！");
				this.abort();
			}else{//这个要都要加成像用
				((RoomInfo)this.editData).setBuildUnit(buildUnit);
				((RoomInfo)this.editData).setUnit(buildUnit.getSeq());
			}
			
		}
		if (this.f7BuildingProperty.getValue() == null) {
			MsgBox.showInfo("建筑性质不能为空!");
			this.abort();
		}
		// if(this.f7ProductDetail.getValue() == null)
		// {
		// MsgBox.showInfo("产品描述不能为空！");
		// this.abort();
		// }
	

//		if (this.lcBuildingFloor.isVisible() && this.f7BuildingFloor.getValue() == null) {
//			MsgBox.showInfo("楼层不能为空！	");
//			this.abort();
//		}

		// if(this.f7RoomUsage.getValue()==null){
		//			
		// MsgBox.showInfo("房间用途不能为空!");
		// this.abort();
		// }

//		if (buildingInfo.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) { //
			
//		}

//		if (StringUtils.isEmpty(this.txtRoomPropNo.getText())) {
//			MsgBox.showInfo("物业房号不能为空!");
//			this.abort();
//		}

//		if (!this.chkIsForSHE.isSelected() && !this.chkIsForTen.isSelected() && !this.chkIsForPPM.isSelected()) {
//			MsgBox.showInfo(this, "售楼,租赁,物业属性至少选一项!");
//			this.abort();
//		}

//		if (!sellProject.isIsForSHE() && this.chkIsForSHE.isSelected()) {
//			MsgBox.showWarning(this, "该项目没有售楼属性，房间不能大于项目的范围！");
//			this.abort();
//		}
//
//		if (!sellProject.isIsForTen() && this.chkIsForTen.isSelected()) {
//			MsgBox.showWarning(this, "该项目没有租赁属性，房间不能大于项目的范围！");
//			this.abort();
//		}
//
//		if (!sellProject.isIsForPPM() && this.chkIsForPPM.isSelected()) {
//			MsgBox.showWarning(this, "该项目没有物业属性，房间不能大于项目的范围！");
//			this.abort();
//		}

		BigDecimal buildingArea = this.txtBuildingArea.getBigDecimalValue();
		BigDecimal roomArea = this.txtRoomArea.getBigDecimalValue();
		if (buildingArea == null) {
			buildingArea = FDCHelper.ZERO;
		}
		if (roomArea == null) {
			roomArea = FDCHelper.ZERO;
		}
		// if (buildingArea == null || buildingArea.compareTo(FDCHelper.ZERO) ==
		// 0) {
		// MsgBox.showInfo("建筑面积不能为空!");
		// this.abort();
		// }
		// if (roomArea == null || roomArea.compareTo(FDCHelper.ZERO) == 0) {
		// MsgBox.showInfo("套内面积不能为空!");
		// this.abort();
		// }
		BigDecimal appArea = buildingArea.subtract(roomArea);
		if (appArea.compareTo(FDCHelper.ZERO) < 0) {
			MsgBox.showInfo("建筑面积不能小于套内面积!");
			this.abort();
		}
		
//		if (room.getRoomNo() != null && room.getRoomNo().trim().length() > 0) {
//			filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("building", buildingInfo.getId().toString()));
//			if (room.getBuildUnit() != null)
//				filter.getFilterItems().add(new FilterItemInfo("buildUnit", room.getBuildUnit().getId()));
//			filter.getFilterItems().add(new FilterItemInfo("roomNo", room.getRoomNo()));
//			if (room.getId() != null)
//				filter.getFilterItems().add(new FilterItemInfo("id", room.getId().toString(), CompareType.NOTEQUALS));
//			if (RoomFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("实测房号:" + room.getRoomNo() + "已存在!");
//				this.abort();
//			}
//		}

//		if (room.getRoomPropNo() != null && room.getRoomPropNo().trim().length() > 0) {
//			filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("building", buildingInfo.getId().toString()));
//			if (room.getBuildUnit() != null)
//				filter.getFilterItems().add(new FilterItemInfo("buildUnit", room.getBuildUnit().getId()));
//			filter.getFilterItems().add(new FilterItemInfo("roomNo", room.getRoomPropNo()));
//			if (room.getId() != null)
//				filter.getFilterItems().add(new FilterItemInfo("id", room.getId().toString(), CompareType.NOTEQUALS));
//			if (RoomFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("物业房号:" + room.getRoomPropNo() + "已存在!");
//				this.abort();
//			}
//		}

	}

//	protected void updateAppArea() {
//		BigDecimal buildingArea = this.txtBuildingArea.getBigDecimalValue();
//		BigDecimal roomArea = this.txtRoomArea.getBigDecimalValue();
//		if (buildingArea != null && roomArea != null) {
//			BigDecimal appArea = buildingArea.subtract(roomArea);
//			if (appArea.compareTo(FDCHelper.ZERO) >= 0) {
//				this.txtApportionArea.setValue(appArea);
//			} else {
//				this.txtApportionArea.setValue(null);
//			}
//		} else {
//			this.txtApportionArea.setValue(null);
//		}
//	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		
		RoomInfo room = (RoomInfo) this.editData;
		if (room == null) {
			return;
		}
		if(null==room.getId()){
			return;
		}
		if (!room.getSellState().equals(RoomSellStateEnum.Init)) {
			MsgBox.showInfo("非初始房间不能删除!");
			return;
		}
		if(room.getTenancyState()!=null){
			if(!(room.getTenancyState().equals(TenancyStateEnum.unTenancy) 
					||room.getTenancyState().equals(TenancyStateEnum.waitTenancy))){
				MsgBox.showInfo("只有未推盘、未放租、待售或待租的房间才能删除!");
				return;
			}
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		if (RoomPriceBillEntryFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("房间已参与定价,不能删除!");
			return;
		}

		if (PriceAdjustEntryFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("房间已参与调价,不能删除!");
			return;
		}
		if (room.getAttachmentEntry() != null && !room.getAttachmentEntry().isEmpty()) {
			MsgBox.showInfo("已绑定其他房间,不能删除!");
			return;
		}

		if (RoomAttachmentEntryFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("已被其他房间绑定,不能删除！");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}

//	protected void txtBuildingArea_dataChanged(DataChangeEvent e) throws Exception {
//		super.txtBuildingArea_dataChanged(e);
//		updateAppArea();
//	}
//
//	protected void chkIsForSHE_actionPerformed(ActionEvent e) throws Exception {
//		super.chkIsForSHE_actionPerformed(e);
//		boolean bo = this.chkIsForSHE.isSelected();
//		this.contRoomState.setVisible(bo);
//	}
//
//	protected void chkIsForTen_actionPerformed(ActionEvent e) throws Exception {
//		super.chkIsForTen_actionPerformed(e);
//		this.contTenancyState.setVisible(this.chkIsForTen.isSelected());
//	}
//
//	protected void txtRoomArea_dataChanged(DataChangeEvent e) throws Exception {
//		super.txtRoomArea_dataChanged(e);
//		updateAppArea();
//	}

	protected void f7BuildingFloor_dataChanged(DataChangeEvent e) throws Exception {
		
	}
	
	 /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {	
        return com.kingdee.eas.fdc.sellhouse.client.RoomSourceEditUI.class.getName();
    }

	protected void tblBizBill_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = tblBizBill.getRow(e.getRowIndex());
			if (row.getCell("id").getValue() == null) {
				return;
			}else {
				String UI="";
				ObjectUuidPK pk=new ObjectUuidPK(row.getCell("id").getValue().toString());
				IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk);
				if(objectValue instanceof PrePurchaseManageInfo){
					UI=PrePurchaseManageEditUI.class.getName();
				}else if(objectValue instanceof PurchaseManageInfo){
					UI=PurchaseManageEditUI.class.getName();
				}else{
					UI=SignManageEditUI.class.getName();
				}
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", row.getCell("id").getValue().toString());
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(UI, uiContext,null, "VIEW");
				uiWindow.show();
			}
		}
	}

	protected void tblReceiveBill_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = tblReceiveBill.getRow(e.getRowIndex());
			if (row.getCell("id").getValue() == null) {
				return;
			}else {
				CRMClientHelper.openTheGatherRevBillWindow(this,row.getCell("id").getValue().toString(), null,null ,null,null);
			}
		}
	}
    protected void loadRoomBill(RoomInfo room){
    	this.tblPriceBill.getStyleAttributes().setLocked(true);
    	this.tblBizBill.getStyleAttributes().setLocked(true);
    	this.tblReceiveBill.getStyleAttributes().setLocked(true);
    	
    	this.tblPriceBill.checkParsed();
    	this.tblBizBill.checkParsed();
    	this.tblReceiveBill.checkParsed();
    	
    	this.tblPriceBill.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	this.tblBizBill.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	this.tblReceiveBill.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	
    	HashMap value = SHEManageHelper.getCRMConstants(SysContext.getSysContext().getCurrentOrgUnit().getId());
		
		this.tblPriceBill.getColumn("standardTotalAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(Integer.parseInt(value.get(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT).toString())));
		this.tblPriceBill.getColumn("roomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(Integer.parseInt(value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString())));
		this.tblPriceBill.getColumn("buildPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(Integer.parseInt(value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString())));
	
    	if(room!=null&&room.getId()!=null){
    		try {
    			Set billId=new HashSet();
    			
	    		String per=NewCommerceHelper.getPermitSaleManIdSql(((RoomInfo)editData).getBuilding().getSellProject(),SysContext.getSysContext().getCurrentUserInfo());
	    		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
	    		
	    		sqlBuilder.appendSql("select * from ( ");
	    		sqlBuilder.appendSql("select distinct bill.fid ID,bill.fnumber NUMBER,bill.fbizState bizState,bill.fbizDate bizDate,bill.fcustomerNames customerNames,bill.froomid ROOMID from t_she_prePurchaseManage bill ");
	    		sqlBuilder.appendSql("left join T_SHE_PrePurchaseSaleManEntry saleMan on saleMan.fheadid=bill.fid where saleMan.fuserid in ("+per+") and bill.froomid='"+room.getId()+"' ");
	    		sqlBuilder.appendSql("union ");
	    		sqlBuilder.appendSql("select distinct bill.fid ID,bill.fnumber NUMBER,bill.fbizState bizState,bill.fbizDate bizDate,bill.fcustomerNames customerNames,bill.froomid ROOMID from t_she_purchaseManage bill ");
	    		sqlBuilder.appendSql("left join T_SHE_PurSaleManEntry saleMan on saleMan.fheadid=bill.fid where saleMan.fuserid in ("+per+") and bill.froomid='"+room.getId()+"' ");
	    		sqlBuilder.appendSql("union ");
	    		sqlBuilder.appendSql("select distinct bill.fid ID,bill.fnumber NUMBER,bill.fbizState bizState,bill.fbizDate bizDate,bill.fcustomerNames customerNames,bill.froomid ROOMID from t_she_signManage bill ");
	    		sqlBuilder.appendSql("left join T_SHE_SignSaleManEntry saleMan on saleMan.fheadid=bill.fid where saleMan.fuserid in ("+per+") and bill.froomid='"+room.getId()+"' )");
			
				IRowSet rs = sqlBuilder.executeQuery();
				while(rs.next()){
					IRow row=this.tblBizBill.addRow();
					billId.add(rs.getString("id"));
					row.getCell("id").setValue(rs.getString("id"));
					row.getCell("number").setValue(rs.getString("number"));
					row.getCell("bizState").setValue(TransactionStateEnum.getEnum(rs.getString("bizState")));
					row.getCell("bizDate").setValue(rs.getDate("bizDate"));
					row.getCell("customerNames").setValue(rs.getString("customerNames"));
				}
				
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("id");
				sic.add("number");
				sic.add("bizDate");
				sic.add("revBillType");
				sic.add("revAmount");
				sic.add("currency.name");
				sic.add("creator.name");
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("room.id",room.getId()));
				filter.getFilterItems().add(new FilterItemInfo("isGathered", new Boolean(false)));
				filter.getFilterItems().add(new FilterItemInfo("relateBizBillId", billId,CompareType.INCLUDE));
				view.setFilter(filter);
				view.setSelector(sic);
				
				SHERevBillCollection col = SHERevBillFactory.getRemoteInstance().getSHERevBillCollection(view);
				for(int i=0;i<col.size();i++){
					SHERevBillInfo entry = col.get(i);
					IRow row = this.tblReceiveBill.addRow();
					row.getCell("id").setValue(entry.getId().toString());
					row.getCell("number").setValue(entry.getNumber());
					row.getCell("revDate").setValue(entry.getBizDate());
					row.getCell("billType").setValue(entry.getRevBillType().getAlias());
					row.getCell("revPerson").setValue(entry.getCreator().getName());
					if(entry.getCurrency()!=null){
						row.getCell("currency").setValue(entry.getCurrency().getName());
					}
					row.getCell("amount").setValue(entry.getRevAmount());
				}
				
				sic = new SelectorItemCollection();
				sic.add("billid");
				sic.add("priceType");
				sic.add("priceMode");
				sic.add("bizDate");
				sic.add("standAmount");
				sic.add("buildingPrice");
				sic.add("roomPrice");
				view=new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("room.id",room.getId()));
				view.setFilter(filter);
				view.setSelector(sic);
				
				RoomPriceAdjustHisCollection priceCol = RoomPriceAdjustHisFactory.getRemoteInstance().getRoomPriceAdjustHisCollection(view);
				for(int i=0;i<priceCol.size();i++){
					RoomPriceAdjustHisInfo entry = priceCol.get(i);
					IRow row = this.tblPriceBill.addRow();
					row.getCell("id").setValue(entry.getBillId().toString());
					row.getCell("priceType").setValue(entry.getPriceType().getAlias());
					row.getCell("priceMode").setValue(entry.getPriceMode().getAlias());
					row.getCell("bizDate").setValue(entry.getBizDate());
					row.getCell("standardTotalAmount").setValue(entry.getStandAmount());
					row.getCell("roomPrice").setValue(entry.getRoomPrice());
					row.getCell("buildPrice").setValue(entry.getBuildingPrice());
				}
				
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			}
    	}
    }
}