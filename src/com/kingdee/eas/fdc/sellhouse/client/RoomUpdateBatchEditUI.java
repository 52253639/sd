/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo;
import com.kingdee.eas.fdc.sellhouse.EyeSignhtInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.NoiseInfo;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardInfo;
import com.kingdee.eas.fdc.sellhouse.ProductDetialInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFormInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomUsageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomWindowsEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public class RoomUpdateBatchEditUI extends AbstractRoomUpdateBatchEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomUpdateBatchEditUI.class);
    protected List idList=null; 
    Set setIds =null;
    
    SellProjectInfo sellPro = null;
    BuildingInfo buil= null;
    public RoomUpdateBatchEditUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    public void initAllF7() throws BOSException, EASBizException{
    	FilterInfo sellProjectFilter=new FilterInfo();
    	sellProjectFilter.getFilterItems().add(new FilterItemInfo("sellProject.id",((SellProjectInfo)SHEManageHelper.getParentSellProject(null, sellPro)).getId()));
    	sellProjectFilter.getFilterItems().add(new FilterItemInfo("orgUnit.id",SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId()));
    	sellProjectFilter.getFilterItems().add(new FilterItemInfo("sellProject.id",null,CompareType.IS));
    	
    	sellProjectFilter.setMaskString("#0 or (#1 and #2)");
    	
    	//户型
    	if(null!= sellPro){
    		
    		this.prmtRoomModel.setEntityViewInfo(SHEHelper.getModelBySellProjectView(sellPro));
    	}
    	//建筑结构
    	EntityViewInfo viewBuilStruct = new EntityViewInfo();
    	FilterInfo filterBuilStruct = new FilterInfo();
    	filterBuilStruct.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	viewBuilStruct.setFilter(filterBuilStruct);
     	this.prmtBuildStruct.setEntityViewInfo(viewBuilStruct);
    	//用途
    	EntityViewInfo viewUsage = new EntityViewInfo();
    	FilterInfo filterUsage = new FilterInfo();
    	filterUsage.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterUsage.getFilterItems().add(new FilterItemInfo("type.id","/uTPJ0sVSbOC8OFIdM+Nx7yNrUg="));
    	viewUsage.setFilter(filterUsage);
    	this.f7RoomUsage.setEntityViewInfo(viewUsage);
	    
    	
    	//视野 
    	EntityViewInfo viewEysSight = new EntityViewInfo();
    	FilterInfo filterEysSight = new FilterInfo();
    	filterEysSight.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
     	filterEysSight.getFilterItems().add(new FilterItemInfo("type.id","pj3Lru9+QVqK/PZoIaNxYLyNrUg="));
     	filterEysSight.mergeFilter(sellProjectFilter, "and");
    	viewEysSight.setFilter(filterEysSight);
    	this.f7EyeSignht.setEntityViewInfo(viewEysSight);
    	
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
    	
     	
     	//装修标准
    	EntityViewInfo viewDecoraStd = new EntityViewInfo();
    	FilterInfo filterDecoraStd = new FilterInfo();
    	filterDecoraStd.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterDecoraStd.getFilterItems().add(new FilterItemInfo("type.id","YPBFahQ6TY+RUDdxyiElfryNrUg="));
    	filterDecoraStd.mergeFilter(sellProjectFilter, "and");
    	viewDecoraStd.setFilter(filterDecoraStd);
    	this.f7DecoraStd.setEntityViewInfo(viewDecoraStd);
    
//    	this.prmtRoomModel.setEntityViewInfo(view);
    	
    	
      
//      	this.prmtRoomModel.setEntityViewInfo(view);
    
      	
    	//交房标准
    	EntityViewInfo viewPossStd = new EntityViewInfo();
    	FilterInfo filterPossStd  = new FilterInfo();
    	filterPossStd .getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterPossStd.getFilterItems().add(new FilterItemInfo("type.id","ByHexvLVSEusji3qeetJHLyNrUg="));
    	filterPossStd.mergeFilter(sellProjectFilter, "and");
    	viewPossStd .setFilter(filterPossStd );
      	this.f7PosseStd.setEntityViewInfo(viewPossStd);
   
//     	//产品描述
      	EntityViewInfo viewProductDetail = new EntityViewInfo();
    	FilterInfo filterProductDetail  = new FilterInfo();
    	filterProductDetail .getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterProductDetail.getFilterItems().add(new FilterItemInfo("type.id","/uTPJ0sVSbOC8OFIdM+Nx7yNrUg="));
    	filterProductDetail.mergeFilter(sellProjectFilter, "and");
    	viewProductDetail .setFilter(filterProductDetail );
      	this.f7ProductDetail.setEntityViewInfo(viewProductDetail);
//     	//房间用途
    	EntityViewInfo viewRoomUsage = new EntityViewInfo();
    	FilterInfo filterRoomUsage = new FilterInfo();
    	filterRoomUsage.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterRoomUsage.getFilterItems().add(new FilterItemInfo("type.id","zzYdGSPQSuWiAJXK3JKjcbyNrUg="));
    	filterRoomUsage.mergeFilter(sellProjectFilter, "and");
    	viewRoomUsage.setFilter(filterRoomUsage);
      	this.f7RoomUsage.setEntityViewInfo(viewRoomUsage);

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
    	
      	
    }
    
    /**
    *
    * <p>@author tim_gao
    * @description  增加售楼的判断
    * if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
           			
           		}else{
           			
           		}<p>
    */
    public void onLoad() throws Exception {
		super.onLoad();
	
		
		this.actionBtnDelLine.setEnabled(true);
		idList=(List) getUIContext().get("idList");
		if(null!=idList&&0<idList.size()){
			SelectorItemCollection select = new SelectorItemCollection();
			select.add("building.*");
			select.add("building.sellProject.*");
			RoomInfo room =RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(idList.get(0).toString())), select);
			if(null!=room.getBuilding()){
			this.buil=room.getBuilding();
				if(null!=room.getBuilding().getSellProject()){
				sellPro =room.getBuilding().getSellProject();
				}else{
					FDCMsgBox.showWarning("房间楼栋所属项目信息不能为空！");
					SysUtil.abort();
				}
			}else{
				FDCMsgBox.showWarning("房间楼栋信息不能为空！");
				SysUtil.abort();
			}
			
		}
		initAllF7();
		tblMain.checkParsed();
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	tblMain.getStyleAttributes().setLocked(true);
		initTblMain();
		comboHouseProperty.setSelectedIndex(-1);
		actionSubmit.setEnabled(true);
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		f7ProductType.setEntityViewInfo(view);
		this.comboWindow.removeAllItems();
		this.comboWindow.addItem("");
		this.comboWindow.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomWindowsEnum").toArray());
		
		//处理物业，租赁，售楼公用
		//不用销售组织判断,改为售楼组织
		
		this.tblMain.getColumn("number").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("roomNo").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("roomPropNo").getStyleAttributes().setHided(true);
		
		if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
			
			this.contRoomForm.setVisible(false);
			this.contFloorHeight.setVisible(false);
			this.contWindow.setVisible(false);
			this.contFitmentStandard.setVisible(false);
			this.contDeliverHouseStandard.setVisible(false);
			this.tblMain.getColumn("number").getStyleAttributes().setHided(false);
		}else{
			this.tblMain.getColumn("number").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("roomNo").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("roomPropNo").getStyleAttributes().setHided(false);
		}
	}
    
    public void onShow() throws Exception {
    	super.onShow();
    	txtFloorHeight.requestFocus();
    }
    /**
    *
    * <p>@author tim_gao
    * @description  增加售楼的判断
    * if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
           			
           		}else{
           			
           		}<p>
    */
    public void initTblMain() throws BOSException{
    	setIds=new HashSet();
    	for(int i =0;i<idList.size();i++){
    		setIds.add(idList.get(i));
    	}
    	EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id",setIds,CompareType.INCLUDE));
    	view.setFilter(filter);
    	SelectorItemCollection sic=new SelectorItemCollection();
    	sic.add("*");
    	view.setSelector(sic);
    	SorterItemCollection sorter = new SorterItemCollection();
    	SorterItemInfo sinfo = new SorterItemInfo("number");
    	sorter.add(sinfo);
    	view.setSorter(sorter);
    	IRoom ir=RoomFactory.getRemoteInstance();
    	RoomCollection rc= ir.getRoomCollection(view);
    	if(rc!=null && rc.size()>0){
    		for(int i =0;i<rc.size();i++){
    			RoomInfo roomInfo=rc.get(i);
    			IRow irow=tblMain.addRow();
        		irow.getCell("id").setValue(roomInfo.getId());
        		irow.getCell("number").setValue(roomInfo.getNumber());
        		if(FDCSysContext.getInstance().checkIsSHEOrg()){//售楼
        			
        		}else{
        			irow.getCell("roomNo").setValue(roomInfo.getRoomNo());
            		irow.getCell("roomPropNo").setValue(roomInfo.getRoomPropNo());
        		}
        	
        		irow.getCell("floor").setValue(new Integer(roomInfo.getFloor()));
        		irow.getCell("serialNumber").setValue(new Integer(roomInfo.getSerialNumber()));
    		}
    	}
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	Map map=new HashMap();
    	//层高
    	if(txtFloorHeight.getText()!=null && !txtFloorHeight.getText().trim().equals("")){
    		map.put("floorHeight", txtFloorHeight.getBigDecimalValue());
    	}
    	//朝向
    	if(f7Direction.getValue()!=null){
    		map.put("direction", ((HopedDirectionInfo)f7Direction.getValue()).getId());
    	}
    	//房屋形式
    	if(f7roomForm.getValue()!=null){
    		map.put("roomForm", ((RoomFormInfo)f7roomForm.getValue()).getId());
    	}
    	//景观
    	if(f7Sight.getValue()!=null){
    		map.put("sight", ((RoomAssistantInfo)f7Sight.getValue()).getId());
    	}
    	//装修标准描述
    	if(txtFitmentStandard.getText()!=null && !txtFitmentStandard.getText().trim().equals("")){
    		map.put("fitmentStandard", txtFitmentStandard.getText());
    	}
    	//建筑性质
    	if(f7BuildingProperty.getValue()!=null){
    		map.put("buildingProperty", ((BuildingPropertyInfo)f7BuildingProperty.getValue()).getId());
    	}
    	//交房标准描述
    	if(txtDeliverHouseStandard.getText()!=null && !txtDeliverHouseStandard.getText().trim().equals("")){
    		map.put("deliverHouseStandard", txtDeliverHouseStandard.getText());
    	}
    	//产品类型
    	if(f7ProductType.getValue()!=null){
    		map.put("productType", ((ProductTypeInfo)f7ProductType.getValue()).getId());
    	}
    	//产品描述
    	if(f7ProductDetail.getValue()!=null){
    		map.put("productDetail", ((RoomAssistantInfo)f7ProductDetail.getValue()).getId());
    	}
    	//房产性质
    	if(comboHouseProperty.getSelectedItem()!=null){
    		map.put("houseProperty",((HousePropertyEnum)comboHouseProperty.getSelectedItem()).getValue());
    	}
    	
    	//by tim_gao 
    	//户型
    	if(null!=this.prmtRoomModel.getValue()){
    		map.put("roomModel",(((RoomModelInfo)this.prmtRoomModel.getValue())).getId());
    	}
    	//建筑性质
    	if(null!=this.prmtBuildStruct.getValue()){
    		map.put("builStruct",(((BuildingStructureInfo)this.prmtBuildStruct.getValue())).getId());
    	}
    	
    	
    	
    	//视野
    	if(f7EyeSignht.getValue()!=null){
    		map.put("eyeSignht", ((RoomAssistantInfo)f7EyeSignht.getValue()).getId());
    	}
    	//噪音
    	if(f7Noise.getValue()!=null){
    		map.put("noise", ((RoomAssistantInfo)f7Noise.getValue()).getId());
    	}
    	//房间用途
    	if(f7RoomUsage.getValue()!=null){
    		map.put("roomUsage", ((RoomAssistantInfo)f7RoomUsage.getValue()).getId());
    	}
    	//交房标准
    	if(f7PosseStd.getValue()!=null){
    		map.put("posseStd", ((RoomAssistantInfo)f7PosseStd.getValue()).getId());
    	}
    	//装修标准
    	if(f7DecoraStd.getValue()!=null){
    		map.put("decoraStd", ((RoomAssistantInfo)f7DecoraStd.getValue()).getId());
    	}
    	//是否有窗
    	if(comboWindow.getSelectedItem()==null || comboWindow.getSelectedItem().equals("")){
    		map.put("window","");
    	}else{
    		map.put("window",((RoomWindowsEnum)comboWindow.getSelectedItem()).getValue());
    	}
    	if(map.size()>0){
    		IRoom ir=RoomFactory.getRemoteInstance();
    		ir.roomIpdateBatch(idList, map);
    		setMessageText("保存成功！");
            setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
            setIsShowTextOnly(false);
            showMessage();
    	}
    	
    	
    }
    
    
    /**
     * output actionBtnDelLine_actionPerformed method
     */
    public void actionBtnDelLine_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
    	if(selectRows.length<1){
    		return;
    	}
    	for(int i = 0 ; i<selectRows.length ; i++){
    		IRow row = this.tblMain.getRow(selectRows[i]);
    		String idRow = row.getCell("id").getValue().toString();
    		idList.remove(idRow);
    	}
    	this.tblMain.removeRows();
    	this.initTblMain();
    }
    /**
     * output actionAddLine_actionPerformed method
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    	KDCommonPromptDialog dlg = null;
		if(	this.getUIWindow() instanceof Frame) {
			dlg = new KDCommonPromptDialog((Frame)	this.getUIWindow());
		}else if(	this.getUIWindow() instanceof Dialog){
			dlg = new KDCommonPromptDialog((Dialog)	this.getUIWindow());
		}else{
			dlg = new KDCommonPromptDialog();
		}
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.RoomQuery")));

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",setIds,CompareType.NOTINCLUDE));
		if(null!=this.getUIContext().get("buildID")){
			filter.getFilterItems().add(new FilterItemInfo("building.Id",this.getUIContext().get("buildID").toString()));
		}
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("number"));
		//过滤已存在的
		dlg.setEnabledMultiSelection(true);
		dlg.setEntityViewInfo(view);
		
		dlg.show();
		Object[] object = (Object[]) dlg.getData();
		if (object != null && object.length > 0) {
		for(int i = 0 ; i <object.length ;i++){
			RoomInfo room = (RoomInfo)object[i];
			if(null!=room){
				this.idList.add(room.getId().toString());
			}
		}
		}
    	this.tblMain.removeRows();
    	this.initTblMain();
    }
}