/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.IBuildingUnit;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomCreatePrincipleEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDes;
import com.kingdee.eas.fdc.sellhouse.RoomDesCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDesInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomListSeparatorEnum;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * @description 生成房间
 * @author tim_gao
 * @date 2011-07-15
 * 
 * ****几个房间加减的方法,激活房间可能有一定的性能问题，有循环套多循环的。
 *            想优化，直接改，伤不起         ****
 *           	代码已经看不下去
 *            不想写这么乱，但是.................还是写的这么乱了
 *            <p> 其实是可以不需要节点node的，但之前写的用了node 所有一开始做的时候没有改就用了，
 *            到后期修改因为这个，维护node越来越麻烦。有很大优化空间<p>
 */
public class RoomNewCreateUI extends AbstractRoomNewCreateUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomCreateUI.class);

	BuildingInfo building = null;
	KDTable table =null;
	//当前生效房间，构图
	RoomCollection roomCol = null;
	RoomDisplaySetting setting = null;
	//单元
	BuildingUnitCollection unitCol  =null;
	String unitName =null;
	String sellProStr ="";
	String sellProNumberStr ="";
	Map allSellPro = SHEHelper.getAllSellProjectForRoom(null);
	//对比用标准房间
	RoomInfo basRoom = null;
	Boolean isSubmit = null;
	//修改时所需删减的房间，暂存
	RoomCollection delRoomCol = null;
	boolean isConvert = false;
	//用于加减计算临时加
	RoomCollection roomTempColAdd =null;
	//用于加减计算临时减
	RoomCollection roomTempColDel=null;
	
	//不显示节点
	Map unShowChildrenNode =new HashMap();;
	 //节点
	DefaultMutableTreeNode node = null;
	
	//需要提交的单元

	  CoreBaseCollection tempUnit = new CoreBaseCollection();
	/**
	 * output class constructor
	 */
	public RoomNewCreateUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	public IBuildingUnit getBuildingUnitInterface() throws BOSException{
		return BuildingUnitFactory.getRemoteInstance();
	}
	/**
	 * output btnYes_actionPerformed method
	 */
	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		if(KDTSelectManager.CELL_SELECT!=table.getSelectManager().getSelectMode()){
			FDCMsgBox.showWarning("请处理未激活房间!");
			return;
		}
		//有房间未改变属性
		for(int i =0 ; i<table.getRowCount() ; i++){
			for(int j = 0 ; j < table.getColumnCount() ; j++){
				ICell cell = table.getCell(i, j);
				RoomInfo room = (RoomInfo) cell.getUserObject();
				if(null!=room){
					if(false == room.isIsForSHE()||Color.LIGHT_GRAY.equals(cell.getStyleAttributes().getBackground())){
						FDCMsgBox.showWarning("请处理未激活房间！");
						return;
					}
				}
			}
		}
		
//		KDTable table = (KDTable) this.pnlGraph.getSelectedComponent();
//		ICell cell = table.getCell(
//				table.getSelectManager().getActiveRowIndex(), table
//						.getSelectManager().getActiveColumnIndex());
//		if (cell != null) {
//			if(!verify()){
//				return;
//			}
//			storeData(cell);
//		}
		//这里要修改成根据页面取得房间  by tim_gao
		//因为加入了列层的变动是临时的
		RoomCollection rooms = roomCol;
		
		if (rooms.size() == 0) {
			MsgBox.showInfo("无房间可生成！");
			return;
		}
		boolean createFlag = true;
		
	
		if(null!=this.getUIContext().get("isSubmit")){
		
			if(Boolean.TRUE.equals(isSubmit)){
				if(RoomFactory.getRemoteInstance().exists("where building.id ='"+building.getId().toString()+"' and sellState != 'Init' ")) {
					
					MsgBox.showInfo("有房间已经推盘或者销售,不能再生成！");
					return;
				}
				if (RoomFactory.getRemoteInstance().exists("where building.id ='"+building.getId().toString()+"'")) {
					if (MsgBox.showConfirm2(this, "已经有房间，是否重新生成?") == MsgBox.YES) {
						createFlag = true;
						RoomFactory.getRemoteInstance().delete("where building.id ='"+building.getId().toString()+"'");
					} else{
						createFlag = false;
					}
				}
			}
		}
		
		
		
		
		
		if(createFlag) {
			//因为序号要取界面的所以直接重新生成一个新的房间集合
			//而不是先删除，再将修改的加入
			rooms = new RoomCollection();
			
			
		
//			//循环遍历 sellProject
//			if(null!=this.building.getSellProject()){
//				Map allSellPro = SHEHelper.getAllSellProjectForRoom();
//				sellProStr = getSellProjIteratorName(sellProStr,this.building.getSellProject().getId().toString(),allSellPro);
//				sellProNumberStr = getSellProjIteratorName(sellProNumberStr,this.building.getSellProject().getId().toString(),allSellPro);
//			}
			for(int i =0 ;i<table.getRowCount();i++){
				for(int j =0 ;j<table.getColumnCount();j++){
					ICell cellRoom = table.getCell(i, j);
					RoomInfo createRoom = (RoomInfo) cellRoom.getUserObject();
					if(null!= createRoom &&( Color.cyan.equals(cellRoom.getStyleAttributes().getBackground())
							   ||Color.YELLOW.equals(cellRoom.getStyleAttributes().getBackground()))){
						//新房间加入
						//房间号组合
						if(cellRoom.getValue().toString()==null){
							FDCMsgBox.showWarning("第"+createRoom.getFloor()+"层"+","+"第"+createRoom.getSerialNumber()+"列"+"名称不能为空！");
							return ;
						}
						rooms.add(getRoomSourcePrincipleName(cellRoom.getValue().toString(),createRoom,sellProStr,sellProNumberStr));
					}
				}
				
			}
//			if(null==room){
//				return;
//			}
			
			//批量更新不给力
//			CoreBaseCollection coreBaseUptColl = new CoreBaseCollection();
			CoreBaseCollection coreBaseSubColl = new CoreBaseCollection();
			for (int i = 0; i < rooms.size(); i++) {
				RoomInfo roomInfo = rooms.get(i);
				roomInfo.setBuilding(building);
				roomInfo.setIsForSHE(building.getSellProject().isIsForSHE());
				roomInfo.setIsForTen(building.getSellProject().isIsForTen());
				roomInfo.setIsForPPM(building.getSellProject().isIsForPPM());
				if(null!=roomInfo.getId()){
					//批量更新不给力
//					coreBaseUptColl.add(roomInfo);
//					SelectorItemCollection selector = new SelectorItemCollection();
//					selector.add("building.*");
//					selector.add("buildUnit.*");
//					selector.add("unit");
//					selector.add("floor");
//					selector.add("serialNumber");
//					selector.add("endSerialNumber");
//					selector.add("number");
//					selector.add("name");
//					selector.add("displayName");
//					
//					selector.add("roomModel.*");
//					selector.add("buildingArea");
//					selector.add("roomArea");
//					
//				
//					selector.add("balconyArea");
//					selector.add("guardenArea");
//					selector.add("carbarnArea");
//					selector.add("useArea");
//					selector.add("flatArea");
//					selector.add("floorHeight");
//					selector.add("buildingProperty.*");
//					selector.add("houseProperty");
//					selector.add("deliverHouseStandard");
//					selector.add("actualBuildingArea");
//					selector.add("actualRoomArea");
//					selector.add("newNoise.*");
//					selector.add("newSight.*");
//					selector.add("newEyeSight.*");
//					selector.add("newDecorastd.*");
//					selector.add("newPossstd.*");
//					selector.add("newRoomUsage.*");
//					selector.add("newProduceRemark.*");
//					selector.add("planBuildingArea");
//					selector.add("planRoomArea");
//					selector.add("isForSHE");	
//					selector.add("isForTen");	
//					selector.add("isForPPM");	
//					selector.add("listSeparatorState");
				
					
					this.getRoomInterface().updatePartial(roomInfo, SHEHelper.getRoomSelector(new SelectorItemCollection()));
				}else{
					if(null==roomInfo.getSellState()){
						roomInfo.setSellState(RoomSellStateEnum.Init);
					}
					
					coreBaseSubColl.add(roomInfo);
				}
			
			}				
			this.getRoomInterface().addnew(coreBaseSubColl);
			if(!isSubmit.booleanValue()){//修改时删除房间
			Set ids = new HashSet();
			
				for (int i = 0; i < this.delRoomCol.size(); i++) {
					if(null!= delRoomCol.get(0).getId()){
						ids.add(delRoomCol.get(i).getId().toString());
					}
				}
				if(null!=ids&&ids.size()>0){
					FilterInfo filterDel = new FilterInfo();
					filterDel.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.INCLUDE));
					this.getRoomInterface().delete(filterDel);
				}
			}
			
			if(Boolean.FALSE.equals(isSubmit)){
				MsgBox.showInfo("更新成功!");
			}else{
				MsgBox.showInfo("生成成功!");
			}
//----------------------------------------------------------------------------------------------------------------------------------------------------------------
			//处理后续需要更新的，单元新增，单元显示更新，楼栋单元数，房间定义新增
			
			
			//新增所需单元
			if(null!=tempUnit&&tempUnit.size()>0){
				getBuildingUnitInterface().submit(tempUnit);
				
				Iterator it = tempUnit.iterator();
				//根据单元再提交房间定义
				 CoreBaseCollection dess = new CoreBaseCollection();
				while(it.hasNext()){
					BuildingUnitInfo unit = (BuildingUnitInfo) it.next();
					RoomDesInfo roomDes = new RoomDesInfo();
					roomDes.setUnit(unit);
					roomDes.setBuilding(building);
					roomDes.setSerialNumberBegin(1);
					roomDes.setSerialNumberEnd(1);
					roomDes.setFloorBegin(1);
					roomDes.setFloorEnd(1);
					roomDes.setProductType(building.getProductType() != null ? building.getProductType() : null);
					roomDes.setBuildingProperty(building.getBuildingProperty() != null ? building.getBuildingProperty() : null);
					dess.add(roomDes);
				}
				RoomDesFactory.getRemoteInstance().submit(dess);
			}
			//更新掉单元,房间定义,更新单元数量
			if(null!=unitCol&&unitCol.size()>0){
			FilterInfo setFilter = new FilterInfo();
			Set ids = new HashSet();
			Iterator itUpdUnit = unitCol.iterator();
			while(itUpdUnit.hasNext()){
				BuildingUnitInfo unit = (BuildingUnitInfo) itUpdUnit.next();
				ids.add(unit.getId().toString());
			}
			setFilter.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.NOTINCLUDE));
			setFilter.getFilterItems().add(new FilterItemInfo("building.id",building.getId().toString()));
			IObjectPK[] delIds= getBuildingUnitInterface().delete(setFilter);
			//房间定义更新掉
			if(null!=delIds&&delIds.length>0){
				//更新单元显示值
				String sql = "delete from t_she_roomdes where fbuildingid = ? and FUnitID = ?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
				for (int i = 0; i < delIds.length; i++) {
					
					sqlBuilder.addParam(building.getId().toString());
					sqlBuilder.addParam(delIds[i].toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();	
			}
			//更新楼栋单元数量的值
			SelectorItemCollection select = new SelectorItemCollection();
			select.add("unitCount");
			building.setUnitCount(unitCol.size());
			BuildingFactory.getRemoteInstance().updatePartial(building, select);
			}
			//更新单元显示，这里如果做成多个是可以多个更新的,目前是只能1个
			if(null!=this.unShowChildrenNode&&unShowChildrenNode.size()>0){
					if(null!=unitCol&&unitCol.size()>1){//如果多个
						
					SelectorItemCollection select = new SelectorItemCollection();
					select.add("haveUnit");
					Collection col = unShowChildrenNode.values();
					Iterator it = col.iterator();
					while(it.hasNext()){
						DefaultMutableTreeNode upnode = (DefaultMutableTreeNode) it.next();
						
							BuildingUnitInfo showUnit = (BuildingUnitInfo) upnode.getUserObject();
							showUnit.setHaveUnit(false);
							getBuildingUnitInterface().updatePartial(showUnit,select);
						
					}
					
				}
			}
			
		
				
		}
		
		this.destroyWindow();
	}
	
	
	public  void getTreeNode(DefaultKingdeeTreeNode knode){
		node = knode;
		try {
			node = 	SHEHelper.setUnitSonNodeUpNode((DefaultMutableTreeNode)node);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("非显示单元节点加载失败！");
			FDCMsgBox.showWarning("非显示单元节点加载失败！");
		}
	}
	
	protected String getSellProjIteratorName(String sellProStr,String sellProId,Map allSellPro) throws BOSException{
		sellProStr =((SellProjectInfo)allSellPro.get(sellProId)).getName()+getListSepartor()+sellProStr;
		if(allSellPro.get(sellProId) instanceof SellProjectInfo && null!=allSellPro.get(sellProId)&&null!=((SellProjectInfo)allSellPro.get(sellProId)).getParent()){
		
		getSellProjIteratorName(sellProStr,((SellProjectInfo)allSellPro.get(sellProId)).getParent().getId().toString(),allSellPro);
		}
		return sellProStr;	
	}
	
		protected String getSellProjIteratorNumber(String sellProStr,String sellProId,Map allSellPro) throws BOSException{
			sellProStr = ((SellProjectInfo)allSellPro.get(sellProId)).getNumber()+getListSepartor()+sellProStr;
			if(allSellPro.get(sellProId) instanceof SellProjectInfo && null!=allSellPro.get(sellProId)&&null!=((SellProjectInfo)allSellPro.get(sellProId)).getParent()){
			getSellProjIteratorNumber(sellProStr,((SellProjectInfo)allSellPro.get(sellProId)).getParent().getId().toString(),allSellPro);
		}
		return sellProStr;	
	}
	
	
	protected String getListSepartor(){
		String listSeperator = " ";
		if(null!=(RoomListSeparatorEnum)this.cBListSeparator.getSelectedItem()){
			if(RoomListSeparatorEnum.LittleRail.equals((RoomListSeparatorEnum)this.cBListSeparator.getSelectedItem())){
				listSeperator = "-";
			}else if(RoomListSeparatorEnum.LeftSeparator.equals((RoomListSeparatorEnum)this.cBListSeparator.getSelectedItem())){
				listSeperator = "/";
			}else if(RoomListSeparatorEnum.RightSeparator.equals((RoomListSeparatorEnum)this.cBListSeparator.getSelectedItem())){
				listSeperator = "\\";
			}
		}
		
		return listSeperator;
		
	}
	
	
	protected RoomInfo getRoomSourcePrincipleName(String txtName,RoomInfo room,String sellProStrName,String sellProNumberStr){
		if (room.getBuildUnit() != null) {
			room.setUnit(room.getBuildUnit().getSeq());
		}
	
		int maxSeq = 0;
		maxSeq = room.getSerialNumber();
		
		for(int i = 0 ; i<roomCol.size() ; i++){
			RoomInfo roomComp = roomCol.get(i);
			if(room.getBuildUnit().getId().equals(roomComp.getBuildUnit().getId())){
				if(roomComp.getSerialNumber()>maxSeq){
					maxSeq = roomComp.getSerialNumber();
				}
			}
		}
		String txtNumber =room.getFloor()
		+SHEHelper.setFillZeroInTxt(room.getSerialNumber(), maxSeq);
	
		if(null==txtName||("").equals(txtNumber)){
			
			 if(this.isConvert==true){
					txtName =room.getFloor() + SHEHelper.getSerialNumberStr(room.getSerialNumber(), true);
				}else{
					 txtName=txtNumber;
				}
		}
		
		room.setName(sellProStrName + room.getBuilding().getName() +getListSepartor()+
				(room.getBuildUnit().isHaveUnit()==false ? room.getBuildUnit().getName()+ getListSepartor():"") + txtName);
		room.setNumber(sellProNumberStr + room.getBuilding().getNumber() + getListSepartor() + 
				(room.getBuildUnit().isHaveUnit()==false ? room.getBuildUnit().getSeq()+ getListSepartor():"")+txtNumber);
		
		room.setDisplayName(txtName);
		room.setRoomPropNo(room.getDisplayName());
		room.setListSeparatorState((RoomListSeparatorEnum)this.cBListSeparator.getSelectedItem());
//		String floors = String.valueOf(room.getFloor());
//		room.setFloor(room.getFloor()+1);
//		room.setUnit(0);
//	
//		NumberFormat nf = NumberFormat.getInstance();
//        //设置是否使用分组
//        nf.setGroupingUsed(false);
//        //设置最大整数位数
//        int len = 2;
//       if(String.valueOf(room.getSerialNumber()).length()>2){
//    	   len = String.valueOf(room.getSerialNumber()).length();
//       }
//        nf.setMaximumIntegerDigits(len);
//        //设置最小整数位数    
//        nf.setMinimumIntegerDigits(len);
//		String colum = nf.format(i);
//		room.setDisplayName(floors+colum);
		//因为房间生成有问题，总会带有租赁属性，所以强制去掉
		room.setIsForTen(false);
		room.setIsForPPM(false);
		
		return room;
	}
	
	
	/**
	 * output btnNo_actionPerformed method
	 */
	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		this.destroyWindow();
	}

	public void roomeSelectChanged(KDTable table) {
		int row = table.getSelectManager().getActiveRowIndex();
		if (row < 0) {
			return;
		}
		int column = table.getSelectManager().getActiveColumnIndex();
		if (column < 0) {
			return;
		}
		if (table.getCell(row, column) == null) {
			return;
		}
		RoomInfo room = (RoomInfo) table.getCell(row, column).getUserObject();
		if (room == null) {
			return;
		}
		this.txtCompany.setText(building.getSellProject().getOrgUnit()
				.getName());
		this.txtProject.setText(building.getSellProject().getName());
		if (building.getSubarea() != null) {
			this.txtSubarea.setText(building.getSubarea().getName());
		}
		this.txtBuilding.setText(building.getName());
		this.txtNumber.setText(room.getNumber());
		this.spiUnit.setValue(new Integer(room.getBuildUnit()==null?0:room.getBuildUnit().getSeq()));
		this.spiFloor.setValue(new Integer(room.getFloor()));
		this.spiSerialNumber.setValue(new Integer(room.getSerialNumber()));
		this.txtBuildingArea.setValue(room.getBuildingArea());
		this.txtRoomArea.setValue(room.getRoomArea());
		this.txtApportionArea.setValue(room.getApportionArea());
		this.txtBalconyArea.setValue(room.getBalconyArea());
		this.txtActualBuildingArea.setValue(room.getActualBuildingArea());
		this.txtActualRoomArea.setValue(room.getActualRoomArea());
		this.txtFloorHeight.setValue(building.getFloorHeight());
		this.f7Direction.setValue(room.getDirection());
		this.f7Sight.setValue(room.getNewSight());
		this.f7RoomModel.setValue(room.getRoomModel());
		this.f7BuildingProperty.setValue(room.getBuildingProperty());
		this.f7ProductType.setValue(room.getProductType());
		this.comboHouseProperty.setSelectedItem(room.getHouseProperty());
		this.txtDisplayName.setText(room.getDisplayName());
	}

	public void onLoad() throws Exception {
//		table.checkParsed();
		//加载节点
		getTreeNode((DefaultKingdeeTreeNode) this.getUIContext().get("root"));
		
		this.comboHouseProperty.setSelectedItem(null);
		this.txtBuilding.setEnabled(false);
		this.txtBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtBuildingArea.setRemoveingZeroInEdit(false);
		this.txtBuildingArea.setNegatived(false);
		this.txtBuildingArea.setPrecision(2);
		this.txtBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingArea.setSupportedEmpty(true);

		this.txtRoomArea.setRemoveingZeroInDispaly(false);
		this.txtRoomArea.setRemoveingZeroInEdit(false);
		this.txtRoomArea.setNegatived(false);
		this.txtRoomArea.setPrecision(2);
		this.txtRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRoomArea.setSupportedEmpty(true);

		this.txtApportionArea.setRemoveingZeroInDispaly(false);
		this.txtApportionArea.setRemoveingZeroInEdit(false);
		this.txtApportionArea.setNegatived(false);
		this.txtApportionArea.setPrecision(2);
		this.txtApportionArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtApportionArea.setSupportedEmpty(true);

		this.txtBalconyArea.setRemoveingZeroInDispaly(false);
		this.txtBalconyArea.setRemoveingZeroInEdit(false);
		this.txtBalconyArea.setNegatived(false);
		this.txtBalconyArea.setPrecision(2);
		this.txtBalconyArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBalconyArea.setSupportedEmpty(true);

		this.txtActualBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtActualBuildingArea.setRemoveingZeroInEdit(false);
		this.txtActualBuildingArea.setNegatived(false);
		this.txtActualBuildingArea.setPrecision(2);
		this.txtActualBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActualBuildingArea.setSupportedEmpty(true);

		this.txtActualRoomArea.setRemoveingZeroInDispaly(false);
		this.txtActualRoomArea.setRemoveingZeroInEdit(false);
		this.txtActualRoomArea.setNegatived(false);
		this.txtActualRoomArea.setPrecision(2);
		this.txtActualRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActualRoomArea.setSupportedEmpty(true);

		this.txtFloorHeight.setRemoveingZeroInDispaly(false);
		this.txtFloorHeight.setRemoveingZeroInEdit(false);
		this.txtFloorHeight.setNegatived(false);
		this.txtFloorHeight.setPrecision(2);
		this.txtFloorHeight.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFloorHeight.setSupportedEmpty(true);
		RoomCollection rooms = (RoomCollection) this.getUIContext()
				.get("rooms");
		
		roomCol =rooms;
		String buildingId = rooms.get(0).getBuilding().getId().toString();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("roomDes.*");
		sels.add("rooms.*");
		sels.add("sellProject.name");
		sels.add("sellProject.isForSHE");
		sels.add("sellProject.isForTen");
		sels.add("sellProject.isForPPM");
		sels.add("subarea.name");
		sels.add("sellProject.orgUnit.name");
		sels.add("building.sellProject.projectResource");
		sels.add("name");
		sels.add("number");
		sels.add("floorHeight");
		building = BuildingFactory.getRemoteInstance().getBuildingInfo(
				new ObjectUuidPK(BOSUuid.read(buildingId)), sels);
		//拼装字段
		if(null!=this.building){
		if(null!=this.building.getSellProject()){
			sellProStr ="";
			sellProNumberStr = "";
			sellProStr = getSellProjIteratorName(sellProStr,this.building.getSellProject().getId().toString(),allSellPro);
			sellProNumberStr = getSellProjIteratorNumber(sellProNumberStr,this.building.getSellProject().getId().toString(),allSellPro);
		}
		}
//		if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
//			final KDTable table = new KDTable();
//			table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
//			RoomDisplaySetting setting = new RoomDisplaySetting();
//			SHEHelper.fillRoomTableByCol(table, rooms, setting);
//			table.addKDTSelectListener(new KDTSelectListener() {
//				public void tableSelectChanged(KDTSelectEvent event) {
//					if(isIgnore)
//						return;
//					KDTSelectBlock prevSelectBlock = event.getPrevSelectBlock();
//					if (prevSelectBlock != null) {
//						ICell cell = table.getCell(prevSelectBlock
//								.getBeginRow(), prevSelectBlock.getBeginCol());
//						if (cell != null) {
//							if(!verify()){
//								isIgnore = true;
//								table.getSelectManager().select(prevSelectBlock);
//								isIgnore = false;
//								return;
//							}
//							storeData(cell);
//						}
//					}
//					roomeSelectChanged(table);
//				}
//			});
//			this.pnlGraph.add(table, "");
//		} else {
//			//如果选择了单元个数，但是生成房间时开始单元到结束单元的个数并不等于楼栋的单元个数，那么就需要按客户选择的单元来生成房间
//			
			RoomDesCollection roomColl = building.getRoomDes(); //
			int minUnitBegin = 1;
			int maxUnitEnd = 1;
			if(roomColl.size()==1)
			{
				minUnitBegin = roomColl.get(0).getUnitBegin();
				maxUnitEnd = roomColl.get(0).getUnitEnd();
			}else
			{
				for(int n=0;n<roomColl.size();n++)
				{
					RoomDesInfo rdInfo = roomColl.get(n);
					//不是每个客户生成的房子都是从第一单元开始的 所以要赋初始值  by xin_wang 2010.09.06
					if(n==0){
						minUnitBegin =rdInfo.getUnitBegin();
						maxUnitEnd =rdInfo.getUnitEnd();
					}
					if(rdInfo.getUnitBegin()<minUnitBegin)
					{
						minUnitBegin = rdInfo.getUnitBegin();
					}if(rdInfo.getUnitEnd()>maxUnitEnd)
					{
						maxUnitEnd = rdInfo.getUnitEnd();
					}
				}
			}
//			int unitCount = maxUnitEnd-minUnitBegin+1;
			RoomCollection unitRooms = new RoomCollection();
			unitName = "房间定义";
//			for (int i = minUnitBegin; i <=maxUnitEnd; i++) {
//				
//				for (int j = 0; j < rooms.size(); j++) {
//					int unit = rooms.get(j).getBuildUnit()==null?0:rooms.get(j).getBuildUnit().getSeq();
//					if (unit == i) {//对根据用户做的房间的定义 生成的房间做遍历，得到需要画的房间生成图  by xin_wang 2010.09.06
//						unitRooms.add(rooms.get(j));
//						unitName = rooms.get(j).getBuildUnit().getName();
//					}
//				}
//			}
			//是否转化字符
			this.isConvert = SHEHelper.getIsCovertByRoomdes(building);
			table = new KDTable();
			table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
			setting = new RoomDisplaySetting();
			//单元集合信息
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building",this.building.getId().toString()));
			SorterItemCollection sortCol = new SorterItemCollection();
			SorterItemInfo sortInfo = new SorterItemInfo("seq");
			 sortInfo.setSortType(SortType.ASCEND);//按照升序找到单元信息，用于生成图内的单元排序展示  by tim_gao 2011-07-02
			 sortCol.add(sortInfo);
			 view.setSorter(sortCol);
			 view.setFilter(filter);
			 //在这里要加*不然会对象比较不相同equals问题
			 view.getSelector().add("*");
			 unitCol = getBuildingUnitInterface().getBuildingUnitCollection(view);
				addTableListener(table);
			 SHEHelper.addtableListener(table);
			 SHEHelper.fillRoomTableByUnitCol(table, rooms, setting,unitCol,(DefaultKingdeeTreeNode)node);
			table.addKDTSelectListener(new KDTSelectListener() {
				public void tableSelectChanged(KDTSelectEvent event) {
					roomeSelectChanged(table);
				}
			});
			this.pnlGraph.add(table,unitName);
		
			
//			for (int i = 1; i <=unitCount; i++) {
//				String unitName = "";
//				RoomCollection unitRooms = new RoomCollection();
//				for (int j = 0; j < rooms.size(); j++) {
//					int unit = rooms.get(j).getBuildUnit()==null?0:rooms.get(j).getBuildUnit().getSeq();
//					if (unit == i) {//对根据楼栋的定义 生成的房间做遍历，得到用户的房间定义的房间
//						unitRooms.add(rooms.get(j));
//						unitName = rooms.get(j).getBuildUnit().getName();
//					}
//				}
//				final KDTable table = new KDTable();
//				table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
//				RoomDisplaySetting setting = new RoomDisplaySetting();
//				SHEHelper.fillRoomTableByCol(table, unitRooms, setting);
//				table.addKDTSelectListener(new KDTSelectListener() {
//					public void tableSelectChanged(KDTSelectEvent event) {
//						roomeSelectChanged(table);
//					}
//				});
//				this.pnlGraph.add(table, unitName);
//			}
//		}
		super.onLoad();
		this.txtNumber.setEnabled(false);
		//得到基础房间
		for(int i =0 ; i<table.getRowCount() ; i++){
			for(int j =0 ; j<table.getColumnCount() ; j++){
				ICell cell = table.getCell(i, j);
				if(cell.getUserObject()!=null&&cell.getUserObject() instanceof RoomInfo){
					basRoom = (RoomInfo) cell.getUserObject();
					break;
				}
				
			}
		}
		
	
		
		isSubmit =(Boolean) this.getUIContext().get("isSubmit");
		if(Boolean.FALSE.equals(isSubmit)){
			this.btnYes.setText("确定");
			//为分隔符
			setPrinciple(roomCol);
		}else{
			this.btnYes.setText("生成");
		}
		if(!isSubmit.booleanValue()){
			this.delRoomCol = new RoomCollection();
		}
	}
	public void setPrinciple(RoomCollection roomCol){
		Iterator it = roomCol.iterator();
		while(it.hasNext()){
			RoomInfo room = (RoomInfo) it.next();
			RoomListSeparatorEnum roomListSep = room.getListSeparatorState();
			if(null!=roomListSep){
				this.cBListSeparator.setSelectedItem(roomListSep);
				return;
			}
		}
		
	}
	
	
	//是否忽略房间选择变化事件，防止在事件的事件处理中触发房间选择变化事件导致死循环
	private boolean isIgnore = false;

	private boolean verify() {
		if (this.f7BuildingProperty.getValue() == null) {
			MsgBox.showInfo("建筑性质不能为空!");
			return false;
		}
		if (this.f7RoomModel.getValue() == null) {
			MsgBox.showInfo("户型不能为空!");
			return false;
		}
		BigDecimal buildAreaTxtValue = this.txtBuildingArea.getBigDecimalValue();
		BigDecimal buildArea = buildAreaTxtValue == null ? FDCHelper.ZERO : buildAreaTxtValue;				
		BigDecimal roomArea = this.txtRoomArea.getBigDecimalValue();
		//录入建筑面积的情况下，套内面积不允许为空 
		if(buildAreaTxtValue != null  &&  roomArea == null){
			MsgBox.showInfo("请录入套内面积");
			return false;
		}
		if (roomArea == null) {
			roomArea = FDCHelper.ZERO;
		}
		if (roomArea.compareTo(buildArea) > 0) {
			MsgBox.showInfo("套内面积大于建筑面积");
			return false;
		}
		BigDecimal actBuildArea = this.txtActualBuildingArea.getBigDecimalValue();
		if (actBuildArea == null) {
			actBuildArea = FDCHelper.ZERO;
		}
		BigDecimal actRoomArea = this.txtActualRoomArea.getBigDecimalValue();
		if (actRoomArea == null) {
			actRoomArea = FDCHelper.ZERO;
		}
		if (actRoomArea.compareTo(actBuildArea) > 0) {
			MsgBox.showInfo("实测套内面积大于实测建筑面积");
			return false;
		}
		
		return true;
		/*
		BigDecimal buildAreaTxtValue = this.txtBuildingArea.getBigDecimalValue();
		BigDecimal buildArea = buildAreaTxtValue == null ? FDCHelper.ZERO : buildAreaTxtValue;				
		BigDecimal roomArea = this.txtRoomArea.getBigDecimalValue();
		//录入建筑面积的情况下，套内面积不允许为空 
		if(buildAreaTxtValue != null  &&  roomArea == null){
			MsgBox.showInfo("请录入套内面积");
			return false;
		}
		if (roomArea == null) {
			roomArea = FDCHelper.ZERO;
		}
		if (roomArea.compareTo(buildArea) > 0) {
			MsgBox.showInfo("套内面积大于建筑面积");
			return false;
		}
		BigDecimal actBuildArea = this.txtActualBuildingArea.getBigDecimalValue();
		if (actBuildArea == null) {
			actBuildArea = FDCHelper.ZERO;
		}
		BigDecimal actRoomArea = this.txtActualRoomArea.getBigDecimalValue();
		if (actRoomArea == null) {
			actRoomArea = FDCHelper.ZERO;
		}
		if (actRoomArea.compareTo(actBuildArea) > 0) {
			MsgBox.showInfo("实测套内面积大于实测建筑面积");
			return false;
		}
		return true;
		*/
	}

	
	/**
	 * 根据id显示窗体
	 */
	public static boolean showWindows(IUIObject ui, RoomCollection rooms,DefaultKingdeeTreeNode node,Boolean isSubmit)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("rooms", rooms);
	
		uiContext.put("root",node);
		uiContext.put("isSubmit", isSubmit);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomNewCreateUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
		return true;
	}

	public void storeData(ICell cell) {
		RoomInfo room = (RoomInfo) cell.getUserObject();
		if (room == null) {
			return;
		}
//		room.setNumber(this.txtNumber.getText());
		room.setBuildingArea(this.txtBuildingArea.getBigDecimalValue());
		room.setRoomArea(this.txtRoomArea.getBigDecimalValue());
		room.setApportionArea(this.txtApportionArea.getBigDecimalValue());
		room.setBalconyArea(this.txtBalconyArea.getBigDecimalValue());
		room.setActualBuildingArea(this.txtActualBuildingArea
				.getBigDecimalValue());
		room.setActualRoomArea(this.txtActualRoomArea.getBigDecimalValue());
		room.setFloorHeight(this.txtFloorHeight.getBigDecimalValue());
		room.setDirection((HopedDirectionInfo) f7Direction.getValue());
		room.setSight((SightRequirementInfo) f7Sight.getValue());
		room.setRoomModel((RoomModelInfo) this.f7RoomModel.getValue());
		room.setBuildingProperty((BuildingPropertyInfo) this.f7BuildingProperty
				.getValue());
		room.setHouseProperty((HousePropertyEnum) this.comboHouseProperty
				.getSelectedItem());
		cell.setValue(room.getNumber());
	}

	protected void txtBuildingArea_dataChanged(DataChangeEvent e)
			throws Exception {
		super.txtBuildingArea_dataChanged(e);
		updateAppArea();
	}

	protected void txtRoomArea_dataChanged(DataChangeEvent e) throws Exception {
		super.txtRoomArea_dataChanged(e);
		updateAppArea();
	}

	protected void updateAppArea() {
		BigDecimal buildingArea = this.txtBuildingArea.getBigDecimalValue();
		BigDecimal roomArea = this.txtRoomArea.getBigDecimalValue();
		if (buildingArea != null && roomArea != null) {
			BigDecimal appArea = buildingArea.subtract(roomArea);
			if (appArea.compareTo(FDCHelper.ZERO) >= 0) {
				this.txtApportionArea.setValue(appArea);
			} else {
				this.txtApportionArea.setValue(null);
			}
		} else {
			this.txtApportionArea.setValue(null);
		}
	}
	
	
	
	
//	public void ConeRoom(){
//		
//	}
//	
	/**
	 * 
     * @author tim_gao
     */
	//本来做成界面上进层增删改，现在看来不可取
//    protected void addFloor_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
//		table.checkParsed();
//		int index = this.table.getSelectManager().getActiveRowIndex();
//		IRow rowOld = this.table.getRow(index);
//		IRow rowNew = null;
//		RoomInfo roomTemp =null;//得到选取层的房间信息
//		IRow rowTemp = this.table.getRow(index);
//		for(int t = 0 ; t < this.table.getColumnCount() ; t++){
//			ICell cell = rowTemp.getCell(t);
//			if(Color.cyan.equals(cell.getStyleAttributes().getBackground())){
//				 roomTemp = (RoomInfo) cell.getUserObject();
//				 break;
//			}
//			
//		}
//		
//	
//		if (rowOld.getCell("floor").getValue() != null) {
//		
////			if (new Integer(rowOld.getCell("floor").getValue().toString()).intValue() >0) {
//			
//			
//			rowNew = this.table.addRow(index);
//				rowNew.setHeight(rowOld.getHeight());
//			
//				for (int i = 0; i < index+1; i++) {
//					IRow row = this.table.getRow(i);
//					
//
//					//当到取到新加层时做的操作
//					if(i==index){
//						int comFloor=0;
//						//得到比较层号
//						if(null==roomTemp){//取不到当前层的层号报错，取下一层的
////							comFloor =((Integer)row.getCell("floor").getValue()).intValue();
//						comFloor = ((Integer)this.table.getRow(i+1).getCell("floor").getValue()).intValue()-1;
//						}else{
//							comFloor = roomTemp.getFloor();
//						}
//						if(comFloor==-1){//当选区的为-1层时
//							row.getCell("floor").setValue(new Integer(((Integer) rowOld.getCell("floor").getValue()).intValue() + 2));
//							
//						}else
//						{
//							// 代替旧层的层标
//							rowNew.getCell("floor").setValue(new Integer(((Integer)rowOld.getCell("floor").getValue()).intValue()+1));
//						}
//						
//					}else{
//					if (!row.getStyleAttributes().isHided() || 0 != ((Integer) row.getCell("floor").getValue()).intValue()) {
//						//增加层数
//						int addfloor = 1;
//						if(-1==((Integer)row.getCell("floor").getValue()).intValue()){
//							addfloor =2;
//						}
//						row.getCell("floor").setValue(new Integer(((Integer) row.getCell("floor").getValue()).intValue() + addfloor));
//						
//						for (int c = 1; c < this.table.getColumnCount(); c++) {
//							ICell cell = row.getCell(c);
//							ICell newRowcell = rowNew.getCell(c);
//							RoomInfo room = null;
//							if (Color.cyan.equals(cell.getStyleAttributes().getBackground())) {
//
//								room = (RoomInfo) cell.getUserObject();
//								
//								if (null != room) {
//									
//									String floor = String.valueOf(room.getFloor()+ addfloor);
//									room.setFloor(room.getFloor() + addfloor);
//									room.setUnit(0);
//									NumberFormat nf = NumberFormat.getInstance();
//									// 设置是否使用分组
//									nf.setGroupingUsed(false);
//									// 设置最大整数位数
//									int len = 2;
//									if (String.valueOf(room.getSerialNumber()).length() > 2) {
//										len = String.valueOf(room.getSerialNumber()).length();
//									}
//									nf.setMaximumIntegerDigits(len);
//									// 设置最小整数位数
//									nf.setMinimumIntegerDigits(len);
//									String colum = nf.format(room.getSerialNumber());
//									room.setDisplayName(floor + colum);
//									//room.setRoomPropNo(room.getDisplayName());
//									room.setName(floor + colum);
//									room.setNumber(floor + colum);
//								}
//								cell.setValue(room.getDisplayName());
//							}
//							
//							
//							// 放回变化完的room
//							cell.setUserObject(room);
//							// 当为最后一层可以考虑加listener
//						}
//					}
//					}
//				
//				}
////
////				rowNew.getStyleAttributes().setBackground(Color.LIGHT_GRAY);
////				rowNew.getCell("floor").getStyleAttributes().setLocked(false);
////				rowNew.getCell("floor").getStyleAttributes().setBackground(Color.orange);
//
////			} 
//			
////			else if (new Integer(rowOld.getCell("floor").getValue().toString()).intValue() <0) {// 第1层向上加
////				rowNew = this.table.addRow(index);
////				SHEHelper.addtableListener(table);
////				rowNew.setHeight(rowOld.getHeight());
////				
////				// 当为最后一层可以考虑加listener
////				for (int i = this.table.getRowCount() - 1; i > index-1; i--) {
////					IRow row = this.table.getRow(i);
////					//当到取到新加层时做的操作
////					if(i==index){
////						// 代替旧层的层标
////						rowNew.getCell("floor").setValue(new Integer(((Integer) rowOld.getCell("floor").getValue()).intValue() - 1));
////					}else{
////					if (!row.getStyleAttributes().isHided() || 0 != ((Integer) row.getCell("floor").getValue()).intValue()) {
////						row.getCell("floor").setValue(new Integer(((Integer) row.getCell("floor").getValue()).intValue() - 1));
////						for (int c = 1; c < this.table.getColumnCount(); c++) {
////							ICell cell = row.getCell(c);
////
////							RoomInfo room = null;
////							if (Color.cyan.equals(cell.getStyleAttributes().getBackground())) {
////
////								room = (RoomInfo) cell.getUserObject();
////								if (null != room) {
////									String floor = String.valueOf(room.getFloor());
////									room.setFloor(room.getFloor() - 1);
////									room.setUnit(0);
////
////									NumberFormat nf = NumberFormat.getInstance();
////									// 设置是否使用分组
////									nf.setGroupingUsed(false);
////									// 设置最大整数位数
////									int len = 2;
////									if (String.valueOf(room.getSerialNumber()).length() > 2) {
////										len = String.valueOf(room.getSerialNumber()).length();
////									}
////									nf.setMaximumIntegerDigits(len);
////									// 设置最小整数位数
////									nf.setMinimumIntegerDigits(len);
////									String colum = nf.format(room.getSerialNumber());
////									room.setDisplayName(floor + colum);
////									//room.setRoomPropNo(room.getDisplayName());
////									room.setName(floor + colum);
////									room.setNumber(floor + colum);
////								}
////							}
////							cell.setUserObject(room);
////							// 当为最后一层可以考虑加listener
////						}
////					}
////					}
////				}
////			
////			}
//
//		}
//		
//		rowNew.getStyleAttributes().setBackground(Color.LIGHT_GRAY);
//		rowNew.getCell("floor").getStyleAttributes().setLocked(false);
//		rowNew.getCell("floor").getStyleAttributes().setBackground(Color.orange);
//		SHEHelper.addtableListener(table);
//	}
	  /**
     * @author tim_gao
	 * @throws BOSException 
     * @description 添加层抽出方法
     */
    public void setAddFloor(RoomInfo baseRoom) throws BOSException{
    	int maxFloor = 0;
		int minFloor = 1;
		int maxSeq = 0;
		int minSeq = 0;
		maxFloor = baseRoom.getFloor();
//		minFloor = baseRoom.getFloor();
		maxSeq = baseRoom.getSerialNumber();
		minSeq = baseRoom.getSerialNumber();
		
		// for (BuildingUnitInfo unit: unitCol){
		//			
		// }
		// 单元的循环比，这里要用拷贝的，能用roomCol来

		// for (Iterator itt = unitCol.iterator(); itt.hasNext();) {

		// // 层数要那所有房间比
		// if (roomTemp.getFloor() > maxFloor) {
		// maxFloor = roomTemp.getFloor();
		// } else if (roomTemp.getFloor() < minFloor) {
		// minFloor = roomTemp.getFloor();
		// }
		
		//增加层数
		int addFloor = 1;
		RoomCollection roomTempCol = (RoomCollection) roomCol.clone();
		
		for (int u = 0; u < unitCol.size(); u++) {
			
			boolean isDefault = true;
			BuildingUnitInfo unit = (BuildingUnitInfo) unitCol.get(u);
			Iterator it =  roomTempCol.iterator();
			while (it.hasNext()) {// 其他列的变化
				
				RoomInfo roomTemp = (RoomInfo) it.next();
				RoomInfo newRoom = null;
				
				// 当前单元下的房间
				//不用单元对象比用ID比
				
				if (unit.getId().equals(roomTemp.getBuildUnit().getId())) {
					
					//得到当前单元初始的数值
					if(isDefault){
						maxFloor = roomTemp.getFloor();
						minFloor = roomTemp.getFloor();
						maxSeq = roomTemp.getSerialNumber();
						minSeq = roomTemp.getSerialNumber();
						isDefault =false;
					}
				if(roomTemp.getFloor()!=0){
					//当选中层未-1层时，要过0所以加2
					//每次要改变，不然会影响下面的层增加
					if(roomTemp.getFloor()==-1){
						 addFloor = 2;
					}else{
						 addFloor = 1;
					}
					// 大于选中层数的房间都加+层数
					if (baseRoom.getFloor() < roomTemp.getFloor()) {
						// 本单元内大于选取层打标准房间的层数的房间层数都加1
						newRoom = (RoomInfo) roomTemp.clone();
						if (roomCol.remove(roomTemp)) {
							newRoom.setFloor(roomTemp.getFloor() + addFloor);
							
//							String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),maxSeq);
//							
//							newRoom.setNumber(newRoom.getFloor() + "" + colum);
//							newRoom.setDisplayName(newRoom.getFloor() + "" + colum);
//							// if(newRoom.getBuildUnit()!=null)
//							//newRoom.setName(longName+"-"+newRoom.getBuildUnit(
//							// ).
//							// getName()+"-"+newRoom.getDisplayName());
//							// else
//							//newRoom.setName(longName+"-"+newRoom.getDisplayName
//							// ())
//							// ;
//							if (newRoom.getBuildUnit() != null)
//								newRoom.setName(newRoom.getBuildUnit().getName() + "-" + newRoom.getDisplayName());
//							else
//								newRoom.setName(newRoom.getDisplayName());
//							
//							if(newRoom.getName().length()<11){
//								newRoom.setNumber(newRoom.getName());
//							}
////							getRoomSourcePrincipleName(String txtName,RoomInfo room,String sellProStrName,String sellProNumberStr)
//							newRoom.setNumber(newRoom.getName());
							
							roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
						}
					}
				}
					// 求本单元的序号大小
					if (unit.getId().equals(roomTemp.getBuildUnit().getId())) {
						if (roomTemp.getSerialNumber() > maxSeq) {
							maxSeq = roomTemp.getSerialNumber();
						} else if (roomTemp.getSerialNumber() < minSeq) {
							minSeq = roomTemp.getSerialNumber();

						}
					}

				}

			}
			
			
			//当选中层未-1层时，要过0所以加2
			if(baseRoom.getFloor()==-1){
				 addFloor = 2;
			}else{
				 addFloor = 1;
			}
			
			//添加当前单元下增一层
			//新增层时一定同事要改变房间所对应单元
			for(int s =minSeq ; s<maxSeq+1 ; s++){
				RoomInfo newRoom = (RoomInfo) baseRoom.clone();
				//因为在修改时其他房间都有ID，新增的应该没有ID
				newRoom.setId(null);
				
				
				//填入序号
				newRoom.setUnit(unit.getSeq());
				//这个单元的变化很重要
				newRoom.setBuildUnit(unit);
				newRoom.setSerialNumber(s);
				newRoom.setEndSerialNumber(s);
				newRoom.setFloor(baseRoom.getFloor()+addFloor);
				//根据单元得到房间定义信息是否转化字母
				String strNum = null;
				strNum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),maxSeq);
				
				if (null != newRoom.getBuildUnit()) {
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("unit", newRoom.getBuildUnit().getId().toString()));
					view.setFilter(filter);
					RoomDesCollection roomDes = RoomDesFactory.getRemoteInstance().getRoomDesCollection(view);

					for (int r = 0; r < roomDes.size(); r++) {
						if(roomDes.get(r).getUnit().getId().equals(newRoom.getBuildUnit().getId())){
							if (newRoom.getSerialNumber() >= roomDes.get(r).getSerialNumberBegin() && newRoom.getSerialNumber() <= roomDes.get(r).getSerialNumberEnd()) {
								strNum = SHEHelper.getSerialNumberStr(s, roomDes.get(r).isIsConvertToChar());
							}
						}
						
					}
				}
//				newRoom.setNumber(newRoom.getFloor()+""+strNum);
//				newRoom.setDisplayName(newRoom.getFloor()+""+strNum);
////				if(newRoom.getBuildUnit()!=null)
////					newRoom.setName(longName+"-"+newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
////				else
////					newRoom.setName(longName+"-"+newRoom.getDisplayName());
//				if(newRoom.getBuildUnit()!=null)
//					newRoom.setName(newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
//				else{
//					newRoom.setName(newRoom.getDisplayName());
//				}
//				newRoom.setNumber(newRoom.getName());
				newRoom.setIsForSHE(false);
				if(null==newRoom.getSellState()){

					newRoom.setSellState(RoomSellStateEnum.Init);
				}
				roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
				
			}
		}
				
    }
	
	/**
	 * 
     * @author tim_gao
     */
    protected void addFloor_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		table.checkParsed();
		
		// 当前选取层

		// 当前选取列
		int index = this.table.getSelectManager().getActiveRowIndex();
		if (index < 0) {
			FDCMsgBox.showWarning("请先选取层！");
			// 用abort会抛异常为什么
			// SysUtil.abort();
			return;
		}
		RoomInfo baseRoom = null;
		//点击时,如果有房间就是标准房间
		ICell cellbaseRoom = this.table.getCell(table.getSelectManager().getActiveRowIndex(),table.getSelectManager().getActiveColumnIndex());
		RoomInfo roomTemp =null;
		if(cellbaseRoom.getUserObject() instanceof RoomInfo){
			roomTemp =	(RoomInfo) cellbaseRoom.getUserObject();
		}
		
		if(roomTemp!=null){
			baseRoom = roomTemp;
			if(roomTemp.getSub()!=null&&(roomTemp.getSub().indexOf(",")>0)){//不能选择合并房间
				baseRoom = null;
			}
			
		}
	
	
			for (int i = 0; i < this.table.getColumnCount(); i++) {
				ICell cell = this.table.getCell(index, i);
				if (null != (RoomInfo) cell.getUserObject()) {
					RoomInfo tRoom = (RoomInfo) cell.getUserObject();
					//得到标准对比房间
					if(baseRoom==null&&(tRoom.getSub()==null||(tRoom.getSub().indexOf(",")<0))){
						
						baseRoom = tRoom;
					}
					//因为不能用 SysUtil.abort() 会异常 所以用return 
					//检查本行下会不会有合并的
					if(this.checkMergeRoomRow(baseRoom,tRoom)){
						return;
					}
					
				
				}
			}
		
		//依然为空时，最后加列，加行，取得一个值
		if(null==baseRoom){
			//得到一个基础数据的房间
			RoomInfo roomRow = null;
//			RoomInfo roomColum = null;
			int colIndex = table.getSelectManager().getActiveColumnIndex();
			int rowIndex = table.getSelectManager().getActiveRowIndex();
//			for(int i =0 ; i<table.getRowCount() ; i++){
//				ICell cellColum = table.getCell(i, colIndex);
//				if(null!=cellColum.getUserObject()&&cellColum.getUserObject() instanceof RoomInfo){
//					roomColum = (RoomInfo) cellColum.getUserObject();
//					break;
//				}
//			}
				for(int j = 0 ; j < table.getColumnCount() ; j++){
					ICell cellRow = table.getCell( rowIndex,j);
					if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
						roomRow = (RoomInfo) cellRow.getUserObject();
						break;
					}
				}
				RoomInfo newRoom =null;
				
				
					//同删除方法  增加出层与列 得到房间定位再删除行列 
					//先屏蔽图像
					table.getStyleAttributes().setHided(true);
					RoomInfo lastColRoom = null;
					RoomInfo lastRowRoom = null;
					if(null==roomRow){//层取不到增加列
						//删除层先在最后一列后加一列房间
						
						
						for(int i = 0 ; i<table.getRowCount() ; i++){
							ICell cell = table.getCell(i, table.getColumnCount()-1);
							lastColRoom = (RoomInfo) cell.getUserObject();
							if(null!=lastColRoom){
								break;
							}
						}
						//增加最后一列
						this.setAddColumn(lastColRoom);
					}
					
//					if(null==roomColum){//列取不到增加层
//						//删除层先在最后一层后加一层房间
//						
//						for(int i = 0 ; i<table.getColumnCount() ; i++){
//							ICell cell = table.getCell(0, i);
//							lastRowRoom = (RoomInfo) cell.getUserObject();
//							if(null!=lastRowRoom){
//								break;
//							}
//						}
//						
//						this.setAddFloor(lastRowRoom);
//					}
					
				
					

					//图展示才能取房间
				
					SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
					
					
					//写到这里的这个变化，已经想吐了，这是对1+1,1-1纯脑力的摧残，.............哥，要脱发了
					if(null==roomRow/*&&null==roomColum*/){//层有变化
					
						for(int j = 0 ; j < table.getColumnCount() ; j++){
							ICell cellRow = table.getCell( rowIndex,j);
							if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
								roomRow = (RoomInfo) cellRow.getUserObject();
								break;
							}
						}
						
						this.setDelColumn(roomRow, table.getColumnCount()-1);
					}
//					else if(null==roomRow &&null!=roomColum){//层无变化
//						for(int j = 0 ; j < table.getColumnCount() ; j++){
//							ICell cellRow = table.getCell( rowIndex,j);
//							if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
//								roomRow = (RoomInfo) cellRow.getUserObject();
//								break;
//							}
//						}
//						
//						this.setDelColumn(roomRow, table.getColumnCount()-1);
//					}
//					//再取
//					if(null==roomColum){//先删在取的不会乱
//					
//						for(int i =0 ; i<table.getRowCount() ; i++){
//							ICell cellColum = table.getCell(i, colIndex);
//							if(null!=cellColum.getUserObject()&&cellColum.getUserObject() instanceof RoomInfo){
//								roomColum = (RoomInfo) cellColum.getUserObject();
//								break;
//							}
//						}
//					this.setDelFloor(roomColum, 0);
//					
//					}
					if(null!=roomRow){
						baseRoom=roomRow;
					}
					table.getStyleAttributes().setHided(false);
		}
		if (null == baseRoom) {
			FDCMsgBox.showWarning("插入楼层有误，请选择有已激活状态下房间的楼层进层本操作！");
			// 用abort会抛异常为什么
			// SysUtil.abort();
			return;
		}
		//添加层算法
		setAddFloor(baseRoom);
		
				

			table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
		//
		// table.addKDTSelectListener(new KDTSelectListener() {
		// public void tableSelectChanged(KDTSelectEvent event) {
		// roomeSelectChanged(table);
		// }
		// });
		// this.pnlGraph.add(table,unitName);
		//加一次就层了
//		SHEHelper.addtableListener(table);
	}
    
    /**
     * @author tim_gao
     * @description 添加列抽出方法
     */
    public void setAddColumn(RoomInfo baseRoom){
    	int maxFloor = 0;
    	int minFloor = 1;//最小行要默认从1开始
    	int maxSeq = 0;
    	int minSeq = 0;
    	maxFloor =  baseRoom.getFloor();
//    	minFloor = baseRoom.getFloor();
    	maxSeq = baseRoom.getSerialNumber();
    	minSeq = baseRoom.getSerialNumber();
    	RoomCollection roomtemp = (RoomCollection) roomCol.clone();
    	Iterator it = roomtemp.iterator();
    	while(it.hasNext()){//其他列的变化
    		RoomInfo roomTemp = (RoomInfo) it.next();
    		RoomInfo newRoom =null;
    		//层数要那所有房间比
    		if(roomTemp.getFloor()>maxFloor){
    			maxFloor = roomTemp.getFloor();
    		}else if(roomTemp.getFloor()<minFloor){
    			minFloor = roomTemp.getFloor();
    		}
    		if(roomTemp.getBuildUnit().getId().equals(baseRoom.getBuildUnit().getId())){
    			if(roomTemp.getSerialNumber()>maxSeq){
    				maxSeq = roomTemp.getSerialNumber();
    			}else if(roomTemp.getSerialNumber()<minSeq){
    				minSeq = roomTemp.getSerialNumber();
    				
    			}
    		
    			
    			//相同单元下如果大于baseRoom序号的序号+1 注意是变化为符号
    			if(roomTemp.getSerialNumber()>baseRoom.getSerialNumber()){
    				newRoom = (RoomInfo) roomTemp.clone();
    				if(roomCol.remove(roomTemp)){
//    					StringBuffer longName = new StringBuffer(); 
//    					longName.append(buildInfo.getSellProject().getName());
//    					if(buildInfo.getSubarea()!=null) {
//    						longName.append("-" + buildInfo.getSubarea().getName());
//    					}
//    					if(buildInfo.getName()!=null){
//    						longName.append( "-" + buildInfo.getName());
//    					}
        				newRoom.setSerialNumber(newRoom.getSerialNumber()+1);
        				newRoom.setEndSerialNumber(newRoom.getEndSerialNumber()+1);
        				
//    					String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),maxSeq);
//        				newRoom.setNumber(newRoom.getFloor()+""+colum);
//        				newRoom.setDisplayName(newRoom.getFloor()+""+colum);
////        				if(newRoom.getBuildUnit()!=null)
////        					newRoom.setName(longName+"-"+newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
////    					else
////    						newRoom.setName(longName+"-"+newRoom.getDisplayName());
//        				if(newRoom.getBuildUnit()!=null)
//        					newRoom.setName(newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
//    					else
//    						newRoom.setName(newRoom.getDisplayName());
//        				newRoom.setNumber(newRoom.getName());
        				roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
        			}
    			}
    			
    		}
    	}
    	//添加列
    	for(int i =minFloor ; i<maxFloor+1 ; i++){
    		if(i!=0){//这里注意不能增加 0层房间
    		RoomInfo newRoom = (RoomInfo) baseRoom.clone();
    		newRoom.setId(null);
    		newRoom.setFloor(i);
    		newRoom.setSerialNumber(newRoom.getSerialNumber()+1);
    		newRoom.setEndSerialNumber(newRoom.getEndSerialNumber()+1);
    		//根据单元得到房间定义信息是否转化字母
    		//没做，没想好，因为如果新增列超出了房间定义范围不知道怎么解决
//    		EntityViewInfo view =new  EntityViewInfo();
//    		RoomDesFactory.getRemoteInstance().getDataBaseCollection(view);
    		
//    		String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),maxSeq);
//    		newRoom.setNumber(newRoom.getFloor()+""+colum);
//    		newRoom.setDisplayName(newRoom.getFloor()+""+colum);
////    		if(newRoom.getBuildUnit()!=null)
////    			newRoom.setName(longName+"-"+newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
////    		else
////    			newRoom.setName(longName+"-"+newRoom.getDisplayName());
//    		if(newRoom.getBuildUnit()!=null)
//    			newRoom.setName(newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
//    		else
//    			newRoom.setName(newRoom.getDisplayName());
//    		newRoom.setNumber(newRoom.getName());
    		newRoom.setIsForSHE(false);
    		if(null==newRoom.getSellState()){

        		newRoom.setSellState(RoomSellStateEnum.Init);
    		}
    		roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
    		}
    	}
    }
    
    
    /**
     * @author tim_gao
     * 
     */
    protected void addColumn_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {table.checkParsed();

	//当前选取列
	int index = this.table.getSelectManager().getActiveColumnIndex();
	
	if(index<0){
		FDCMsgBox.showWarning("请先选取列！");
		//用abort会抛异常为什么
//		SysUtil.abort();
		return;
	}else if(0==index){
		FDCMsgBox.showWarning("不能在楼层选择插入列！");
		//用abort会抛异常为什么
//		SysUtil.abort();
		return;
	}
	IColumn oldColumn = this.table.getColumn(index);
	RoomInfo baseRoom = null;
	
	//点击时,如果有房间就是标准房间
	ICell cellbaseRoom = this.table.getCell(table.getSelectManager().getActiveRowIndex(),table.getSelectManager().getActiveColumnIndex());
	RoomInfo roomTemp =null;
	if(cellbaseRoom.getUserObject() instanceof RoomInfo){
		roomTemp =	(RoomInfo) cellbaseRoom.getUserObject();
	}
	
	if(roomTemp!=null){
		baseRoom = roomTemp;
		if(roomTemp.getSub()!=null&&(roomTemp.getSub().indexOf(",")>0)){//不能选择合并房间
			baseRoom = null;
		}
		
	}
	for(int i = 0 ; i < this.table.getRowCount() ; i++){
		ICell cell = this.table.getCell(i, index);
		if(null!=(RoomInfo) cell.getUserObject()){
			//因为不能用 SysUtil.abort() 会异常 所以用return 
//			if(this.checkMergeRoom((RoomInfo) cell.getUserObject(), "本列")){
//				return;
//			}
//			if(baseRoom==null){
//				 baseRoom = (RoomInfo) cell.getUserObject();	
//			}
			RoomInfo tRoom = (RoomInfo) cell.getUserObject();
			
			//得到标准对比房间
			if(baseRoom==null&&(tRoom.getSub()==null||(tRoom.getSub().indexOf(",")<0))){
				
				baseRoom = tRoom;
			}
			//因为不能用 SysUtil.abort() 会异常 所以用return 
			//检查本列下会不会有合并的
			if(this.checkMergeRoomColumn(baseRoom,tRoom)){
				return;
			}
		}
	}
	
	
	
	/*
	 * by tim_gao
	 * 描述：在经过上面取值后，依然没有取到baseRoom 标准对照房间时，选用的方法是
	 * 		先在0行增一行房间，然后取点击的列，再这一行上的房间，因为是第0行，这样可以最小减少其他行本不需要的变动
	 * 问题：因为是多增加的一行，超出本来的房间行，这样的话，在后面拼装新增列时，会在0行多一个房间
	 * 解决：加一个isOutOfFloorBaseRoom 参数 如果 用到上述方法取baseRoom房间时，参数赋值,然后将baseRoom的房间Floor-1
	 * 		用于之后的拼装房间正确计算
	 *	 
	 *  按照这样计算 的出来的标注对照房间，在本列没有房间时会超出
	 */
	//依然为空时，最后加列，加行，取得一个值
	boolean isOutOfFloorBaseRoom = false;
	if(null==baseRoom){
		//得到一个基础数据的房间
//		RoomInfo roomRow = null;
		RoomInfo roomColum = null;
		int colIndex = table.getSelectManager().getActiveColumnIndex();
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		for(int i =0 ; i<table.getRowCount() ; i++){
			ICell cellColum = table.getCell(i, colIndex);
			if(null!=cellColum.getUserObject()&&cellColum.getUserObject() instanceof RoomInfo){
				roomColum = (RoomInfo) cellColum.getUserObject();
				break;
			}
		}
//			for(int j = 0 ; j < table.getColumnCount() ; j++){
//				ICell cellRow = table.getCell( rowIndex,j);
//				if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
//					roomRow = (RoomInfo) cellRow.getUserObject();
//					break;
//				}
//			}
			RoomInfo newRoom =null;
			
			
				//同删除方法  增加出层与列 得到房间定位再删除行列 
				//先屏蔽图像
				table.getStyleAttributes().setHided(true);
				RoomInfo lastColRoom = null;
				RoomInfo lastRowRoom = null;
//				if(null==roomRow){//层取不到增加列
//					//删除层先在最后一列后加一列房间
//					
//					
//					for(int i = 0 ; i<table.getRowCount() ; i++){
//						ICell cell = table.getCell(i, table.getColumnCount()-1);
//						lastColRoom = (RoomInfo) cell.getUserObject();
//						if(null!=lastColRoom){
//							break;
//						}
//					}
//					//增加最后一列
//					this.setAddColumn(lastColRoom);
//				}
				
				if(null==roomColum){//列取不到增加层
					//删除层先在最后一层后加一层房间
					
					for(int i = 0 ; i<table.getColumnCount() ; i++){
						ICell cell = table.getCell(0, i);
						lastRowRoom = (RoomInfo) cell.getUserObject();
						if(null!=lastRowRoom){
							break;
						}
					}
					
					this.setAddFloor(lastRowRoom);
				}
				
			
				

				//图展示才能取房间
			
				SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
			
//				if(null==roomRow&&null==roomColum){//层有变化
//				
//					for(int j = 0 ; j < table.getColumnCount() ; j++){
//						ICell cellRow = table.getCell( rowIndex,j);
//						if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
//							roomRow = (RoomInfo) cellRow.getUserObject();
//							break;
//						}
//					}
//					
//					this.setDelColumn(roomRow, table.getColumnCount()-1);
//				}
//				else if(null==roomRow &&null!=roomColum){//层无变化
//					for(int j = 0 ; j < table.getColumnCount() ; j++){
//						ICell cellRow = table.getCell( rowIndex,j);
//						if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
//							roomRow = (RoomInfo) cellRow.getUserObject();
//							break;
//						}
//					}
//					
//					this.setDelColumn(roomRow, table.getColumnCount()-1);
//				}
				//再取
				if(null==roomColum){//先删在取的不会乱
				
					for(int i =0 ; i<table.getRowCount() ; i++){
						ICell cellColum = table.getCell(i, colIndex);
						if(null!=cellColum.getUserObject()&&cellColum.getUserObject() instanceof RoomInfo){
							roomColum = (RoomInfo) cellColum.getUserObject();
							break;
						}
					}
				
					if(null!=roomColum){
						this.setDelFloor(roomColum, 0);
					}
						
					
				isOutOfFloorBaseRoom = true;
				}
				if(null!=roomColum){
					baseRoom=roomColum;
				}
				table.getStyleAttributes().setHided(false);
	}
	if(null==baseRoom){
		FDCMsgBox.showWarning("插入列序号有误，请选择有已激活状态下房间的序号列进层本操作！");
		//用abort会抛异常为什么
//		SysUtil.abort();
		return;
	}
	
	if(isOutOfFloorBaseRoom){
		if(baseRoom!=null){
			baseRoom.setFloor(baseRoom.getFloor()-1);
		}
		
	}
	//添加列算法
	setAddColumn(baseRoom);
	
	
//	this.pnlGraph.remove(table);
//	table = new KDTable();
	table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
	SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
//
//	table.addKDTSelectListener(new KDTSelectListener() {
//		public void tableSelectChanged(KDTSelectEvent event) {
//			roomeSelectChanged(table);
//		}
//	});
//	this.pnlGraph.add(table,unitName);
//	SHEHelper.addtableListener(table);
	
    }
    
    
    

	/**
	 * 根据所删除的房间矫正单元数量
	 * @author tim_gao
	 * @throws BOSException 
	 * @throws EASBizException 
	 * 
	 */
	public void setUnitsBybaseRoom(RoomInfo baseRoom) throws EASBizException, BOSException{
		if(null==baseRoom){
			return;
		}
		boolean isDel = true;
		Iterator itRooms = roomCol.iterator();
		while(itRooms.hasNext()){
			RoomInfo roomtemp = (RoomInfo) itRooms.next();
			if(baseRoom.getBuildUnit().getId().equals(roomtemp.getBuildUnit().getId())){
				isDel = false;
			}
		}
		if(isDel){
			BuildingUnitCollection tempUnitColl = (BuildingUnitCollection) unitCol.clone();
			Iterator units = tempUnitColl.iterator();
			while(units.hasNext()){
				BuildingUnitInfo unitTemp = (BuildingUnitInfo) units.next();
				if(baseRoom.getBuildUnit().getId().equals(unitTemp.getId())){
					unitCol.remove(unitTemp);
					tempUnit.remove(unitTemp);
					//从节点上也取下来
					DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)node;
					Enumeration enumeration = buildNode.children();
					while (enumeration.hasMoreElements()) {
						DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration.nextElement();
						Object object = element.getUserObject();
						if(object instanceof BuildingUnitInfo&&((BuildingUnitInfo)object).getId().equals(unitTemp.getId())){
							node.remove(element);
						}
					}
				}
			}
		}
		//如果单元数量等于1时,产看是否是显示节点如果有，需要回到不显示状态 变回去yes
		
		if(1==unitCol.size()){
			DefaultMutableTreeNode unShowNode = (DefaultMutableTreeNode) this.unShowChildrenNode.
				get(((BuildingUnitInfo)unitCol.get(0)).getId().toString());
			if(null!=unShowNode){
				((BuildingUnitInfo)unShowNode.getUserObject()).setHaveUnit(true);
			}
			
		}
	}
    /**
     * @author tim_gao
     * @description 删除列方法
     * @param baseRoom 标准参照房间 index 删除的列
     * @throws BOSException 
     * @throws EASBizException 
     */
    
    public void setDelColumn(RoomInfo baseRoom,int index) throws EASBizException, BOSException{
    	//先所有的房间都减
    	RoomCollection roomtemp = (RoomCollection) roomCol.clone();
    	Iterator it = roomtemp.iterator();
    	while(it.hasNext()){//其他列的变化
    		RoomInfo roomTemp = (RoomInfo) it.next();
    		RoomInfo newRoom =(RoomInfo) roomTemp.clone();
    		//单元，层，对应上，列大于
    		if(roomTemp.getBuildUnit().getId().equals(baseRoom.getBuildUnit().getId())){
    		if(baseRoom.getSerialNumber()<roomTemp.getSerialNumber()){
    			if(roomCol.remove(roomTemp)){
    				newRoom.setSerialNumber(newRoom.getSerialNumber()-1);
    				newRoom.setEndSerialNumber(newRoom.getEndSerialNumber()-1);
    				
    				//取不到最大就这样吧
//					String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),0);
//    				newRoom.setNumber(newRoom.getFloor()+""+colum);
//    				newRoom.setDisplayName(newRoom.getFloor()+""+colum);
////    				if(newRoom.getBuildUnit()!=null)
////    					newRoom.setName(longName+"-"+newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
////    				else
////    					newRoom.setName(longName+"-"+newRoom.getDisplayName());
//    				if(newRoom.getBuildUnit()!=null)
//    					newRoom.setName(newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
//    				else
//    					newRoom.setName(newRoom.getDisplayName());
//    				newRoom.setNumber(newRoom.getName());
    				roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
    			}
    		}
    		}
    	}
    	
    	//判断删除房间与是否加上
    	String warn = "";
    	for(int i =0 ; i<this.table.getRowCount() ; i++){
    		ICell cell = this.table.getCell(i, index);
    		RoomInfo room = (RoomInfo)cell.getUserObject();
    		if(null!=room){
    			//有ID是，修改界面要校验是否有业务状态
//    			if(null!=room.getId()){
//    				FilterInfo filter = new FilterInfo();
//    				filter.getFilterItems().add(new FilterItemInfo("building.id",room.getBuilding().getId()));
//    				filter.getFilterItems().add(new FilterItemInfo("sellState",RoomSellStateEnum.INIT_VALUE,CompareType.NOTEQUALS));
//    				filter.getFilterItems().add(new FilterItemInfo("isPush",Boolean.FALSE));
//    				if(getRoomInterface().exists(filter)){
//    					warn +="第"+room.getFloor()+"层-"+"序号"+room.getSerialNumber()+"-"+"房间代码："+room.getDisplayName()+"不能删除;\n";
//    				}else{
//    					roomCol.remove(room);
//    					getRoomInterface().delete(room.getId().toString());
//    				}
//    				
//    			}else{
//    				roomCol.remove(room);
//    			}
    			//有ID是，修改界面要校验是否有业务状态
    			if(null!=room.getId()){
    				if(checkRoomIsExistForDel(room)){
    				warn +="第"+room.getFloor()+"层-"+"序号"+room.getSerialNumber()+"-"+"房间代码："+room.getDisplayName()+"不能删除;\n";	
    				//放方法外，不然减2遍算错
    				roomTempColDel = (RoomCollection) roomCol.clone();
    				setRoomFloorAddColumn(room);
    				}
    			}else{
    				roomCol.remove(room);
    			
    			}
    		}
    	}
    	if((!("").equals(warn))||(warn.length()>1)){
    		FDCMsgBox.showWarning(warn);
    	}	
    }
    /**
     * @author tim_gao
     */
    protected void delColumn_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	table.checkParsed();
    	
    	//当前选取列
    	int index = this.table.getSelectManager().getActiveColumnIndex();
    	if(index<0){
    		FDCMsgBox.showWarning("请先选取列！");
			//用abort会抛异常为什么
//			SysUtil.abort();
			return;
    	}
    	RoomInfo baseRoom = null;
    	for(int i = 0 ; i < this.table.getRowCount() ; i++){
    		ICell cell = this.table.getCell(i, index);
        		if(null!=(RoomInfo) cell.getUserObject()){
        			 baseRoom = (RoomInfo) cell.getUserObject();
         			break;
        		}
    	}
    	if(null==baseRoom){
//    		FDCMsgBox.showWarning("插入列序号有误，请选择有已激活状态下房间的序号列进层本操作！");
			//用abort会抛异常为什么
//			SysUtil.abort();
    		
    		//逼出来的，这么恶心的方法我也不想用
    		
    		//先屏蔽图像
    		table.getStyleAttributes().setHided(true);
    		//删除列现在第一层加一层房间
    		RoomInfo lastRowRoom = null;
    		for(int i = 0 ; i<table.getColumnCount() ; i++){
    			ICell cell = table.getCell(0,i);
    			lastRowRoom = (RoomInfo) cell.getUserObject();
    			if(null!=lastRowRoom){
    				break;
    			}
    		}
    		//增加最后一层
    		this.setAddFloor(lastRowRoom);
    		SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
    		//再去获得本列的房间
    		for(int i = 0 ; i < this.table.getRowCount() ; i++){
    			ICell cell = this.table.getCell(i, index);
    			baseRoom = (RoomInfo) cell.getUserObject();
    			if(null!=baseRoom){
    				
    				break;
    			}
    		}
    		
    		//再删除增加层
    		if(null==lastRowRoom){
    			FDCMsgBox.showWarning("删除操作错误，请使用【方法2】删除本层:\n新插入层后->选择要删除列->点击删除列。");
    			return;
    		}
    		this.setDelFloor(baseRoom, 0);
    		if(null==baseRoom){
    			FDCMsgBox.showWarning("删除操作错误，请使用【方法2】删除本层:\n新插入层后->选择要删除列->点击删除列。");
    			return;
    		}
    		//删除列算法
    		setDelColumn(baseRoom,index);
    	
//    		table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
    		table.getStyleAttributes().setHided(false);
    		//校验单元，不看图可以放在生成图前面
    		setUnitsBybaseRoom(baseRoom);
    		SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
    		checkDelAllActiveRoom();
    		
    		return;
			
    	}
    	//删除算法
    	setDelColumn(baseRoom , index);
//    	table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
    	//校验单元，不看图可以放在生成图前面
    	setUnitsBybaseRoom(baseRoom);
    	SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
    	checkDelAllActiveRoom();
    	
    //   	SHEHelper.addtableListener(table);
    	
    }
    
    /**
     * @description 因为没有删掉相对应的房间所有房间都+1
     * @author tim_gao
     */
    public void setRoomFloorAddColumn(RoomInfo baseRoom){
    	Iterator it = roomTempColDel.iterator();
    	while(it.hasNext()){//其他列的变化
    		RoomInfo roomTemp = (RoomInfo) it.next();
    		RoomInfo newRoom =(RoomInfo) roomTemp.clone();
    		//单元，层，对应上，列大于
    		if(roomTemp.getBuildUnit().getId().equals(baseRoom.getBuildUnit().getId())){
    		if(baseRoom.getSerialNumber()<=roomTemp.getSerialNumber()&&baseRoom.getFloor()==roomTemp.getFloor()&&!baseRoom.getId().toString().equals(roomTemp.getId().toString())){//并且不是本层判断的房间
    			if(roomCol.remove(roomTemp)){
    				newRoom.setSerialNumber(newRoom.getSerialNumber()+1);
    				newRoom.setEndSerialNumber(newRoom.getEndSerialNumber()+1);
    				
    				//取不到最大就这样吧
//					String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),0);
//    				newRoom.setNumber(newRoom.getFloor()+""+colum);
//    				newRoom.setDisplayName(newRoom.getFloor()+""+colum);
////    				if(newRoom.getBuildUnit()!=null)
////    					newRoom.setName(longName+"-"+newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
////    				else
////    					newRoom.setName(longName+"-"+newRoom.getDisplayName());
//    				if(newRoom.getBuildUnit()!=null)
//    					newRoom.setName(newRoom.getBuildUnit().getName()+"-"+newRoom.getDisplayName());
//    				else
//    					newRoom.setName(newRoom.getDisplayName());
//    				newRoom.setNumber(newRoom.getName());
    				roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
    			}
    		}
    		}
    	}
    }
    
    
    public boolean checkRoomIsExistForDel(RoomInfo room) throws EASBizException, BOSException{
    	
			FilterInfo filter = new FilterInfo();
			
			filter.getFilterItems().add(new FilterItemInfo("building.id",room.getBuilding().getId()));
			filter.getFilterItems().add(new FilterItemInfo("sellState",RoomSellStateEnum.INIT_VALUE,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("isPush",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("id",room.getId().toString()));
			filter.setMaskString("#0 and (#1 or #2) and #3");
			if(getRoomInterface().exists(filter)){
				
				return true;
			}else{
				roomCol.remove(room);
				if(!this.isSubmit.booleanValue()){
					this.delRoomCol.add(room);
					//点击按钮后再处理删除，不然会有数据问题，房屋重叠
					//因为其他房间是点击后才处理数据
//					getRoomInterface().delete(new ObjectUuidPK(BOSUuid.read(room.getId().toString())));
				}
			
				return false;
			}
	}
    public IRoom getRoomInterface() throws BOSException{
    	return RoomFactory.getRemoteInstance();
    }
    
    /**
     * @author tim_gao
     * @throws BOSException 
     * @throws EASBizException 
     * @description 删除层算法
     * @param baseRoom 标准对比房间 index 要删除的层
     * 
     * 
     */
    
    public void setDelFloor(RoomInfo baseRoom,int index) throws EASBizException, BOSException{
    	
    	//先所有大于层号的减-1
    	//减少层数
    	int delFloor = 1;
    	RoomCollection roomTempCol = (RoomCollection) roomCol.clone();
    	
    		Iterator it =  roomTempCol.iterator();
    		while (it.hasNext()) {// 其他列的变化
    			
    			RoomInfo roomTemp = (RoomInfo) it.next();
    			RoomInfo newRoom = null;
    			//0层不管
    			if(roomTemp.getFloor()!=0){
    				//当选中层未1层时，要过0所以减2
    				//每次要改变，不然会影响下面的层增加
    				if(roomTemp.getFloor()==1){
    					delFloor = 2;
    				}else{
    					delFloor = 1;
    				}
    				// 对应房间的列
    				
    				if (baseRoom.getFloor() < roomTemp.getFloor()) {
    					// 本单元内大于选取层打标准房间的层数的房间层数减1
    					newRoom = (RoomInfo) roomTemp.clone();
    					if (roomCol.remove(roomTemp)) {
    						newRoom.setFloor(roomTemp.getFloor() - delFloor);
    						
    						//取不到最大就这样吧
//    						String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),0);
//    						newRoom.setNumber(newRoom.getFloor() + "" + colum);
//    						newRoom.setDisplayName(newRoom.getFloor() + "" + colum);
//    						// if(newRoom.getBuildUnit()!=null)
//    						//newRoom.setName(longName+"-"+newRoom.getBuildUnit(
//    						// ).
//    						// getName()+"-"+newRoom.getDisplayName());
//    						// else
//    						//newRoom.setName(longName+"-"+newRoom.getDisplayName
//    						// ())
//    						// ;
//    						if (newRoom.getBuildUnit() != null)
//    							newRoom.setName(newRoom.getBuildUnit().getName() + "-" + newRoom.getDisplayName());
//    						else
//    							newRoom.setName(newRoom.getDisplayName());
//    						
//    						if(newRoom.getName().length()<11){
//    							newRoom.setNumber(newRoom.getName());
//    						}
//    						newRoom.setNumber(newRoom.getName());
    						roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
    					}
    				}
    			}
    		
    		}
    	//删除本层房间
    	String warn = "";
    	for(int i = 0 ; i<this.table.getColumnCount() ; i++){
    		ICell cell = this.table.getCell(index,i);
    		RoomInfo room = (RoomInfo) cell.getUserObject();
    		if(null!=room){
    			//有ID是，修改界面要校验是否有业务状态
    			if(null!=room.getId()){
    				if(checkRoomIsExistForDel(room)){//本列的房间不做变化
     				warn +="第"+room.getFloor()+"层-"+"序号"+room.getSerialNumber()+"-"+"房间代码："+room.getDisplayName()+"已发生业务不能删除;\n";	
     				
     				//临时的，要放在外面，不然每次克隆，roomCol会加两遍，算错
     		    	roomTempColAdd = (RoomCollection) roomCol.clone();
     				setRoomColumAddFloor(room);
    				}
    					
    				
    			}else{//删除了本列都-1，其他房间的变化
    				roomCol.remove(room);
    			}
    		}
    	}
    	if((!("").equals(warn))||(warn.length()>1)){
    		FDCMsgBox.showWarning(warn);
    	}

    }
    
   
    /**
     * @author tim_gao
     */
    protected void delFloor_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {table.checkParsed();
//    int maxFloor = 0;
//	int minFloor = 0;
//	int maxSeq = 0;
//	int minSeq = 0;
	//当前选取列
	int index = this.table.getSelectManager().getActiveRowIndex();
	if(index<0){
		FDCMsgBox.showWarning("请先选取层！");
		//用abort会抛异常为什么
//		SysUtil.abort();
		return;
	
    }
	RoomInfo baseRoom = null;
	for(int i = 0 ; i < this.table.getColumnCount() ; i++){
		ICell cell = this.table.getCell(index, i);
		if(null!=(RoomInfo) cell.getUserObject()){
			 baseRoom = (RoomInfo) cell.getUserObject();
			break;
		}
	}
	if(null==baseRoom){
//		FDCMsgBox.showWarning("插入列序号有误，请选择有已激活状态下房间的序号列进层本操作！");
		//用abort会抛异常为什么
//		SysUtil.abort();
		
		//被逼的，这么恶心的方法我也不想用
		//解释：因为当本层没有房间是，没有找到标准就会无法删除所以默认的删除层的时候在最后面加一列，然后本层有房屋值了，在去掉那列房间
		//		同理当删除列的时候没有就在最上面加1层回来再删除
		
		//先屏蔽图像
		table.getStyleAttributes().setHided(true);
		//删除层先在最后一列后加一列房间
		RoomInfo lastColRoom = null;
		for(int i = 0 ; i<table.getRowCount() ; i++){
			ICell cell = table.getCell(i, table.getColumnCount()-1);
			lastColRoom = (RoomInfo) cell.getUserObject();
			if(null!=lastColRoom){
				break;
			}
		}
		//增加最后一列
		this.setAddColumn(lastColRoom);
		SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
		//再去获得本层的房间
		for(int i = 0 ; i < this.table.getColumnCount() ; i++){
			ICell cell = this.table.getCell(index, i);
			baseRoom = (RoomInfo) cell.getUserObject();
			if(null!=baseRoom){
				
				break;
			}
		}
		
		//在删除增加列
		if(null==lastColRoom){
			FDCMsgBox.showWarning("删除操作错误，请使用【方法2】删除本层:\n新插入列后->选择要删除层->点击删除层。");
			return;
		}
		this.setDelColumn(baseRoom,  table.getColumnCount()-1);
		if(null==baseRoom){
			FDCMsgBox.showWarning("删除操作错误，请使用【方法2】删除本层:\n新插入列后->选择要删除层->点击删除层。");
			return;
		}
		//删除层算法
		setDelFloor(baseRoom,index);
	
//		table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		table.getStyleAttributes().setHided(false);
	
		SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
		checkDelAllActiveRoom();
		return;
	}
//    
//    if(null==baseRoom){
//    	FDCMsgBox.showWarning("删除楼层有误，请选择有已激活状态下房间的楼层进层本操作！");
//		//用abort会抛异常为什么
////		SysUtil.abort();
//		return;
//	}
//    maxFloor = baseRoom.getFloor();
//	minFloor = baseRoom.getFloor();
//	maxSeq = baseRoom.getSerialNumber();
//	minSeq = baseRoom.getSerialNumber();
	
	// for (BuildingUnitInfo unit: unitCol){
	//			
	// }
	// 单元的循环比，这里要用拷贝的，能用roomCol来

	// for (Iterator itt = unitCol.iterator(); itt.hasNext();) {

	// // 层数要那所有房间比
	// if (roomTemp.getFloor() > maxFloor) {
	// maxFloor = roomTemp.getFloor();
	// } else if (roomTemp.getFloor() < minFloor) {
	// minFloor = roomTemp.getFloor();
	// }
	
	//删除层算法
	setDelFloor(baseRoom,index);
//	table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
	
	SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol,(DefaultKingdeeTreeNode) node);
	checkDelAllActiveRoom();
//	SHEHelper.addtableListener(table);
		
    }
    
    public void checkDelAllActiveRoom(){
    	RoomCollection desRoomCol = new RoomCollection();
    
    	
    		for (int i = 0; i < table.getRowCount(); i++){
				for (int j =0; j < table.getColumnCount(); j++) {
					ICell cell = table.getCell(i, j);
					RoomInfo room =  (RoomInfo) cell.getUserObject();
					if(null!=room&&Color.LIGHT_GRAY.equals(cell.getStyleAttributes().getBackground())){
						RoomInfo delRoom = (RoomInfo) room.clone();
					
					if(null==desRoomCol.get(room)){
						
						desRoomCol.add(room);
						}
						
					}
				
				}
    	
    		}
    	
    	if(null==desRoomCol||desRoomCol.size()<=0){
    		table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
    	}
    }
    
    /**
     * @description 本房间对应列上的房间都加1层
     * @author tim_gao
     */
    public void setRoomColumAddFloor(RoomInfo baseRoom){
    	//减少层数
    	int delFloor = 1;
    		Iterator it =  roomTempColAdd.iterator();
    		while (it.hasNext()) {// 其他列的变化
    			
    			RoomInfo roomTemp = (RoomInfo) it.next();
    			RoomInfo newRoom = null;
    			//0层不管
    			if(roomTemp.getFloor()!=0){
    				//当选中层未1层时，要过0所以减2
    				//每次要改变，不然会影响下面的层增加
    				if(roomTemp.getFloor()==-1){
    					delFloor = 2;
    				}else{
    					delFloor = 1;
    				}
    				// 对应房间的列，对应单元，大于选中层数的房间都加+层数
    				
    				if (baseRoom.getFloor() <=roomTemp.getFloor()
    						&&baseRoom.getBuildUnit().getId().equals(roomTemp.getBuildUnit().getId())
    						&&baseRoom.getSerialNumber()==roomTemp.getSerialNumber()&&!(baseRoom.getId().toString().equals(roomTemp.getId().toString()))) {
    					// 本单元内大于选取层打标准房间的层数的房间层数都加1,并且不是本层判断的房间
    					newRoom = (RoomInfo) roomTemp.clone();
    					if (roomCol.remove(roomTemp)) {
    						newRoom.setFloor(roomTemp.getFloor() + delFloor);
    						
    						//取不到最大就这样吧
//    						String colum =SHEHelper.setFillZeroInTxt(newRoom.getSerialNumber(),0);
//    						newRoom.setNumber(newRoom.getFloor() + "" + colum);
//    						newRoom.setDisplayName(newRoom.getFloor() + "" + colum);
//    						// if(newRoom.getBuildUnit()!=null)
//    						//newRoom.setName(longName+"-"+newRoom.getBuildUnit(
//    						// ).
//    						// getName()+"-"+newRoom.getDisplayName());
//    						// else
//    						//newRoom.setName(longName+"-"+newRoom.getDisplayName
//    						// ())
//    						// ;
//    						if (newRoom.getBuildUnit() != null)
//    							newRoom.setName(newRoom.getBuildUnit().getName() + "-" + newRoom.getDisplayName());
//    						else
//    							newRoom.setName(newRoom.getDisplayName());
//    						
//    						if(newRoom.getName().length()<11){
//    							newRoom.setNumber(newRoom.getName());
//    						}
//    						newRoom.setNumber(newRoom.getName());
    						roomCol.add(getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr));
    					}
    				}
    			}
    		
    		}
    		
    }

	

    /**
     * output btnActivateRoomsNo_actionPerformed method
     */
    protected void btnActivateRoomsNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {	
    	//为单选时，没有可激活房间
    	if(table.getSelectManager().getActiveRowIndex()<0|| table.getSelectManager().getActiveColumnIndex()<0){
    		return;
    	}
    	if(KDTSelectManager.CELL_SELECT==table.getSelectManager().getSelectMode()){
    		ICell activeCell = table.getCell(table.getSelectManager().getActiveRowIndex(), table.getSelectManager().getActiveColumnIndex());
    		
    		
    		if(Color.GRAY.equals(activeCell.getStyleAttributes().getBackground())){
    			//得到一个基础数据的房间
				RoomInfo roomRow = null;
				RoomInfo roomColum = null;
				int colIndex = table.getSelectManager().getActiveColumnIndex();
				int rowIndex = table.getSelectManager().getActiveRowIndex();
				for(int i =0 ; i<table.getRowCount() ; i++){
					ICell cellColum = table.getCell(i, colIndex);
					if(null!=cellColum.getUserObject()&&cellColum.getUserObject() instanceof RoomInfo){
						roomColum = (RoomInfo) cellColum.getUserObject();
						break;
					}
				}
					for(int j = 0 ; j < table.getColumnCount() ; j++){
						ICell cellRow = table.getCell( rowIndex,j);
						if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
							roomRow = (RoomInfo) cellRow.getUserObject();
							break;
						}
					}
					RoomInfo newRoom =null;
					
					
						//同删除方法  增加出层与列 得到房间定位再删除行列 
						//先屏蔽图像
						table.getStyleAttributes().setHided(true);
						RoomInfo lastColRoom = null;
						RoomInfo lastRowRoom = null;
						if(null==roomRow){//层取不到增加列
							//删除层先在最后一列后加一列房间
							
							
							for(int i = 0 ; i<table.getRowCount() ; i++){
								ICell cell = table.getCell(i, table.getColumnCount()-1);
								lastColRoom = (RoomInfo) cell.getUserObject();
								if(null!=lastColRoom){
									break;
								}
							}
							//增加最后一列
							this.setAddColumn(lastColRoom);
						}
						
						if(null==roomColum){//列取不到增加层
							//删除层先在最后一层后加一层房间
							
							for(int i = 0 ; i<table.getColumnCount() ; i++){
								ICell cell = table.getCell(0, i);
								lastRowRoom = (RoomInfo) cell.getUserObject();
								if(null!=lastRowRoom){
									break;
								}
							}
							
							this.setAddFloor(lastRowRoom);
						}
						
					
						

						//图展示才能取房间
					
						SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
						
						
						//写到这里的这个变化，已经想吐了，这是对1+1,1-1纯脑力的摧残，.............哥，要脱发了
						if(null==roomRow&&null==roomColum){//层有变化
						
							for(int j = 0 ; j < table.getColumnCount() ; j++){
								ICell cellRow = table.getCell( rowIndex+1,j);
								if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
									roomRow = (RoomInfo) cellRow.getUserObject();
									break;
								}
							}
							
							this.setDelColumn(roomRow, table.getColumnCount()-1);
						}else if(null==roomRow &&null!=roomColum){//层无变化
							for(int j = 0 ; j < table.getColumnCount() ; j++){
								ICell cellRow = table.getCell( rowIndex,j);
								if(null!=cellRow.getUserObject()&&cellRow.getUserObject() instanceof RoomInfo){
									roomRow = (RoomInfo) cellRow.getUserObject();
									break;
								}
							}
							
							this.setDelColumn(roomRow, table.getColumnCount()-1);
						}
						//再取
						if(null==roomColum){//先删在取的不会乱
						
							for(int i =0 ; i<table.getRowCount() ; i++){
								ICell cellColum = table.getCell(i, colIndex);
								if(null!=cellColum.getUserObject()&&cellColum.getUserObject() instanceof RoomInfo){
									roomColum = (RoomInfo) cellColum.getUserObject();
									break;
								}
							}
						this.setDelFloor(roomColum, 0);
						
						}
						
//						//取竖列的有单元信息
					newRoom = (RoomInfo) roomColum.clone();
					newRoom.setId(null);
					newRoom.setFloor(roomRow.getFloor());
					newRoom.setIsForSHE(true);
					RoomInfo disRoom = getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr);
					//这里还要在加上跟据排序规则计算号码么 
					if(roomCol.add(disRoom)){
					activeCell.setValue(disRoom.getDisplayName());
					activeCell.getStyleAttributes().setBackground(Color.CYAN);
					}
					table.getStyleAttributes().setHided(false);
					SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
					
					return;
//						warn = warn+"请填写单元，序号，楼栋信息！";
//						newRoom = (RoomInfo) basRoom.clone();
//						newRoom.setFloor(0);
//						newRoom.setSerialNumber(1);
//						newRoom.setEndSerialNumber(1);
//						if (MsgBox.showConfirm2(this, warn) == MsgBox.YES) {//提示
//							
//							//得到楼层的最大和最小
//							int minfloor =newRoom.getFloor();
//							int maxfloor = newRoom.getFloor();
//							int minseq =newRoom.getSerialNumber();
//							int maxseq = newRoom.getSerialNumber();
//							for(Iterator it = roomCol.iterator(); it.hasNext();){
//								RoomInfo compRoom = (RoomInfo) it.next();
//								if(maxfloor <compRoom.getFloor()){
//									maxfloor = compRoom.getFloor();
//								}
//								if(minfloor >compRoom.getFloor()){
//									minfloor = compRoom.getFloor();
//								}
//								if(minseq >compRoom.getSerialNumber()){
//									minseq  = compRoom.getSerialNumber();
//								}
//								if(maxseq <compRoom.getSerialNumber()){
//									maxseq = compRoom.getSerialNumber();
//								}
//								
//							}
//						if (newRoom != null) {
//							this.toolBar.setVisible(false);
//						UIContext uiContext = new UIContext(this);
//						uiContext.put("building",this.building);
//						uiContext.put("ROOMFORDES", newRoom);
//						uiContext.put("ISFIXED", Boolean.TRUE);
//						uiContext.put("maxfloor",new Integer( maxfloor));
//						uiContext.put("minfloor",new Integer( minfloor));
//						uiContext.put("minseq",new Integer( minseq));
//						uiContext.put("maxseq",new Integer( maxseq));
////						uiContext.put("ROOMDIS", ..);
////						uiContext.put("editData1", room);
//						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSourceEditUI.class.getName(), uiContext, null, "ADDNEW");
//						((CoreUIObject)uiWindow.getUIObject()).setPreferredSize(new Dimension(600,400));
//						uiWindow.getUIObject().getUIToolBar().getActionMap();
//						uiWindow.show();
//						Map roomContext = uiWindow.getUIObject().getUIContext();
//						 newRoom = (RoomInfo) roomContext.get("editRoom");
//						if(newRoom==null){
//							
//							return;
//						}else{
//							//校验层与序号是否覆盖
//							for(Iterator it = roomCol.iterator();it.hasNext();){
//								RoomInfo checkRoom = (RoomInfo) it.next();
//								if(checkRoom.getBuildUnit().getId().equals(newRoom.getBuildUnit().getId())
//										&&checkRoom.getFloor()==newRoom.getFloor()&&checkRoom.getSerialNumber()==newRoom.getSerialNumber()){
//									FDCMsgBox.showWarning("与房间重叠,"+"第"+checkRoom.getFloor()+"层-序号" +checkRoom.getSerialNumber()+"-房间编码："+checkRoom.getDisplayName()+",请重新编辑生成！");
//									return;
//								}
//								
//							}
//						}
//						RoomInfo setRoom =getRoomSourcePrincipleName(null,newRoom,sellProStr,sellProNumberStr);
//						//这里还要在加上跟据排序规则计算号码么 
//						
//						setRoom.setId(null);
//						if(roomCol.add(setRoom)){
////							activeCell.setValue(setRoom.getDisplayName());
////							activeCell.setUserObject(setRoom);
////							activeCell.getStyleAttributes().setBackground(Color.CYAN);
//							  SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
//						}
//					
//						}
//					}
				
//			}
					
					
    		}
    		return;
    	}
    	//没有选到房间，没有可激活房间
    	int flag=1;
    	if(table.getSelectManager().size()<=0){
    		flag = FDCMsgBox.showConfirm2("未选中激活房间，是否全部激活？");
    		if(flag==1)
    		{
    			return;
    		}else{
    			for (int i = 0; i < table.getRowCount(); i++) {
					for (int j = 0; j < table.getColumnCount(); j++) {
						ICell cell = table.getCell(i, j);
						RoomInfo room = (RoomInfo) cell.getUserObject();
						if (null != room && Color.LIGHT_GRAY.equals(cell.getStyleAttributes().getBackground())) {
//							if(0!=room.getFloor()){
								RoomInfo newRoom = (RoomInfo) room.clone();
								if (roomCol.remove(room)) {
									newRoom.setIsForSHE(true);
									roomCol.add(newRoom);
								}
								cell.getStyleAttributes().setBackground(Color.cyan);
//							}else{
//								if (roomCol.remove(room)){
//									cell.setUserObject(null);
//									cell.getStyleAttributes().setBackground(Color.dr);
//								}
								
//							}
						}
					}
				}
    			return;
			}
    		
    	}
    	boolean isAllNull=true;
//    	if(){}
    	//颜色不对，没有可激活房间
    	RoomInfo roomCheck  =null;
    	for(int c = 0 ; c < table.getSelectManager().size() ; c++){
    		KDTSelectBlock block = this.table.getSelectManager().get(c);
    		for (int i = block.getBeginRow(); i <= block.getEndRow(); i++){
				for (int j = block.getBeginCol(); j <= block.getEndCol(); j++) {
					ICell cell = table.getCell(i, j);
					roomCheck = (RoomInfo) cell.getUserObject();
					if(null!=roomCheck&&Color.LIGHT_GRAY.equals(cell.getStyleAttributes().getBackground())){
						RoomInfo newRoom = (RoomInfo) roomCheck.clone();
						if(roomCol.remove(roomCheck)){
							newRoom.setIsForSHE(true);
							roomCol.add(newRoom);
						}
						isAllNull=false;
					}
				
				}
    	
    		}
    	}
    	if(true==isAllNull){
    		FDCMsgBox.showWarning("未选中可激活房间!");
    		return;
    	}
    	for(int i =0 ; i<table.getRowCount();i++){
    		for(int j =0 ; j<table.getColumnCount();j++){
    			ICell cell = table.getCell(i, j);
    			RoomInfo room = (RoomInfo) cell.getUserObject();
    			if(null!=room){
    				if(Color.LIGHT_GRAY.equals(cell.getStyleAttributes().getBackground())){
    					if(false==room.isIsForSHE()){
    						roomCol.remove(room);
    					}
    				}
    			}
    			
    			
    		}
    	}
    	
   	SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting, unitCol, (DefaultKingdeeTreeNode) node);
   
   //这里的增加listener以为不是静态的只能调用1次，不然每次点击调用，会多一个监听，会头疼	
 //  	addTableListener(table);
    	//回到单选
        this.table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
    }
    
   protected void addTableListener(final KDTable table){
	  table.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
	        public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
	            try {
	                table_tableClicked(e,table);
	            } catch (Exception exc) {
	                handUIException(exc);
	            } finally {
	            }
	        }
	    });
   }
   protected void table_tableClicked(KDTMouseEvent e,KDTable table) throws Exception {
	   
	  if( e.getType() != KDTStyleConstants.BODY_ROW ){
		  return;
	  }if(e.getButton() == MouseEvent.BUTTON3 ){
		  if(table.getSelectManager().size()!=1){
				FDCMsgBox.showWarning("请选择单个房间编辑!");
				
				return;
			}
		  if(0==table.getSelectManager().getActiveColumnIndex()){
				return;
			}
		ICell activeCell = table.getCell( table.getSelectManager().getActiveRowIndex(),table.getSelectManager().getActiveColumnIndex());
		if(Color.cyan.equals(activeCell.getStyleAttributes().getBackground())||Color.YELLOW.equals(activeCell.getStyleAttributes().getBackground())){
			activeCell.getStyleAttributes().setBackground(Color.WHITE);
			activeCell.getStyleAttributes().setLocked(false);
		
		}
	
		  return;
	  }
	   
	   
		if (e.getButton() == 1) {
			KDTSelectBlock block = table.getSelectManager().get();
			if (block == null) {
				return;
			}
			int left = block.getLeft();
			int top = block.getTop();
			ICell cell = table.getCell(top, left);
			if(Color.GRAY.equals(cell.getStyleAttributes().getBackground())){
				return;
			}
			if (cell == null) {
				return;
			}
			RoomInfo room = null;
			if (cell.getUserObject() != null && cell.getUserObject() instanceof RoomInfo)
				room = (RoomInfo) cell.getUserObject();
			if (room == null) {
				return;
			}

		
			if (e.getType() == KDTStyleConstants.BODY_ROW
					&& e.getButton() == MouseEvent.BUTTON1
					&& e.getClickCount() == 2) {

				if (room != null) {this.toolBar.setVisible(false);
					UIContext uiContext = new UIContext(this);
					uiContext.put("building",room.getBuilding());
					uiContext.put("ROOMFORDES", room);
				
//					uiContext.put("editData1", room);
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSourceEditUI.class.getName(), uiContext, null, "ADDNEW");
					uiWindow.getUIObject().getUIToolBar().getActionMap();
			
					uiWindow.show();
					uiWindow.getUIObject().destroyWindow();
					Map roomContext = uiWindow.getUIObject().getUIContext();
					RoomInfo newRoom = (RoomInfo) roomContext.get("editRoom");
					if(newRoom==null){
						
						return;
					}
					roomCol.remove(room);
					cell.setUserObject(newRoom);
					roomCol.add(newRoom);
				}
			}
//			if (e.getClickCount() == 2) {
//				
//
//			
//			}
		}
	}
   
   
   /**
    * output cBPrinciple_itemStateChanged method
    */
   protected void cBPrinciple_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
   {	
	  
//	   boolean isEditedAll = false;
//	   RoomCreatePrincipleEnum roomPrin = (RoomCreatePrincipleEnum)(e.getItem());
//	  if(!isSubmit.booleanValue()){//修改状态
//		  if(MsgBox.showConfirm2(this, "是否同时更改已生成的房间序号？") == MsgBox.YES){
//			  isEditedAll = true;
//		  }
//	  }
//	   //为单选时，因为没有激活的房间不显示房间序号，排序后在生成的话会乱
//   	if(KDTSelectManager.CELL_SELECT!=table.getSelectManager().getSelectMode()){
//		FDCMsgBox.showWarning("请激活房间后，再进层房间排序!");
//		return;
//	}
//	//没有选到房间，因为没有激活的房间不显示房间序号，排序后在生成的话会乱
//	if(table.getSelectManager().size()<0){
//		FDCMsgBox.showWarning("请激活房间后，再进层房间排序!");
//		return;
//	}
//	   if(RoomCreatePrincipleEnum.BuildingHorizontal.equals(roomPrin)){
//		   setBuildingHorizontalTable(table,isEditedAll );
//	   }else  if(RoomCreatePrincipleEnum.NormalModel.equals(roomPrin)){
//		   SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
//	   }else  if(RoomCreatePrincipleEnum.UnitHorizontal.equals(roomPrin)){
//		   setUnitHorizontalTable(table,isEditedAll );
//	   }else  if(RoomCreatePrincipleEnum.UnitVertical.equals(roomPrin)){
//		   setUnitVerticalTable(table,isEditedAll);
//	   }
	   
   }
   
   protected void setBuildingHorizontalTable(KDTable table,boolean isEditedAll){
	  for(int i = table.getRowCount()-1 ; i >=0  ; i--){
		  for(int j = 1 ; j<table.getColumnCount() ; j++){
			   ICell cell = table.getCell(i, j);
			   RoomInfo room  = (RoomInfo) cell.getUserObject();
			   if(isEditedAll){
				   if(null!= room &&( Color.cyan.equals(cell.getStyleAttributes().getBackground())
						   ||Color.YELLOW.equals(cell.getStyleAttributes().getBackground()))){
						
						String colum =SHEHelper.setFillZeroInTxt(j,table.getRowCount());
						cell.setValue(room.getFloor()+colum);
				   }
			   }else{
				   if(null!= room &&Color.cyan.equals(cell.getStyleAttributes().getBackground())){
						
						String colum =SHEHelper.setFillZeroInTxt(j,table.getRowCount());
						cell.setValue(room.getFloor()+colum);
				   }
			   }
			  
		   }
	  }
	 
   }
   protected void setUnitHorizontalTable(KDTable table,boolean isEditedAll) {

		for (Iterator it = unitCol.iterator(); it.hasNext();) {//单元遍历
			int se = 1;
			BuildingUnitInfo unit = (BuildingUnitInfo) it.next();
			for (int i = table.getRowCount()-1; i >=0; i--) {//从第一层开始找出所有对应单元的
				
				for (int j = 1; j < table.getColumnCount(); j++) {
					ICell cell = table.getCell(i, j);
					RoomInfo room = (RoomInfo) cell.getUserObject();
					if(isEditedAll){
						if (null!= room &&( Color.cyan.equals(cell.getStyleAttributes().getBackground())
								   ||Color.YELLOW.equals(cell.getStyleAttributes().getBackground()))) {
							if(unit.getId().equals(room.getBuildUnit().getId())){
								//取不到最大就这样吧
								String colum =SHEHelper.setFillZeroInTxt(se,0);
								cell.setValue(room.getFloor()+colum);
								se++;//房屋后加1
							}
						}
					}else{
						
						if (null!= room &&( Color.cyan.equals(cell.getStyleAttributes().getBackground()))) {
							if(unit.getId().equals(room.getBuildUnit().getId())){
								//取不到最大就这样吧
								String colum =SHEHelper.setFillZeroInTxt(se,0);
								cell.setValue(room.getFloor()+colum);
								se++;//房屋后加1
							}
						}
					}
					
				
				}
			}
		}
	}
   protected void setUnitVerticalTable(KDTable table,boolean isEditedAll){
	for (Iterator it = unitCol.iterator(); it.hasNext();) {//单元遍历
		int se = 1;
			BuildingUnitInfo unit = (BuildingUnitInfo) it.next();
			for (int i = 1; i < table.getColumnCount(); i++) {//从第一列开始找出所有对应单元的
			
				for (int j = table.getRowCount()-1; j >=0; j--) {
					ICell cell = table.getCell(j, i);
					RoomInfo room = (RoomInfo) cell.getUserObject();
					
					if(isEditedAll){
					
						if (null!= room &&( Color.cyan.equals(cell.getStyleAttributes().getBackground())
								   ||Color.YELLOW.equals(cell.getStyleAttributes().getBackground()))) {
							if(unit.getId().equals(room.getBuildUnit().getId())){
								//取不到最大就这样吧
								String colum =SHEHelper.setFillZeroInTxt(se,0);
								cell.setValue(room.getFloor()+colum);
								se++;//房屋后加1
							}
						}
					}else{
						if (null!= room &&( Color.cyan.equals(cell.getStyleAttributes().getBackground()))) {
							if(unit.getId().equals(room.getBuildUnit().getId())){
								//取不到最大就这样吧
								String colum =SHEHelper.setFillZeroInTxt(se,0);
								cell.setValue(room.getFloor()+colum);
								se++;//房屋后加1
							}
						}
					}
					
					
				}
			}
		}
	}
   /**
    * output cBListSeparator_itemStateChanged method
    */
   protected void cBListSeparator_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
   {	sellProStr ="";
		sellProNumberStr = "";
		sellProStr = getSellProjIteratorName(sellProStr,this.building.getSellProject().getId().toString(),allSellPro);
		sellProNumberStr = getSellProjIteratorNumber(sellProNumberStr,this.building.getSellProject().getId().toString(),allSellPro);
   }
   public boolean checkMergeRoomRow(RoomInfo baseRoom , RoomInfo mergeRoom){
		if(baseRoom!=null&&mergeRoom.getSub()!=null&&(mergeRoom.getSub().indexOf(",")>0)){
		
			if(baseRoom.getFloor()==mergeRoom.getFloor()){
				FDCMsgBox.showWarning("本行已有合并房间禁止增加操作！");
				return true;
			}
			
		}
	  return false;
   }
   public boolean checkMergeRoomColumn(RoomInfo baseRoom , RoomInfo mergeRoom){
		if(baseRoom!=null&&mergeRoom.getSub()!=null&&(mergeRoom.getSub().indexOf(",")>0)){
			if(baseRoom.getSerialNumber()==mergeRoom.getSerialNumber()){
				FDCMsgBox.showWarning("本列已有合并房间禁止增加操作！");
				return true;
			}
			
		}
	  return false;
  }
   

   /**
    * output cBPrinciple_actionPerformed method
    */
   protected void cBPrinciple_actionPerformed(java.awt.event.ActionEvent e) throws Exception
   {  boolean isEditedAll = false;
   RoomCreatePrincipleEnum roomPrin = (RoomCreatePrincipleEnum)(this.cBPrinciple.getSelectedItem());
	  if(!isSubmit.booleanValue()){//修改状态
		  if(MsgBox.showConfirm2(this, "是否同时更改已生成的房间序号？") == MsgBox.YES){
			  isEditedAll = true;
		  }
	  }
	   //为单选时，因为没有激活的房间不显示房间序号，排序后在生成的话会乱
	if(KDTSelectManager.CELL_SELECT!=table.getSelectManager().getSelectMode()){
		FDCMsgBox.showWarning("请激活房间后，再进层房间排序!");
		return;
	}
	//没有选到房间，因为没有激活的房间不显示房间序号，排序后在生成的话会乱
	if(table.getSelectManager().size()<0){
		FDCMsgBox.showWarning("请激活房间后，再进层房间排序!");
		return;
	}
	   if(RoomCreatePrincipleEnum.BuildingHorizontal.equals(roomPrin)){
		   setBuildingHorizontalTable(table,isEditedAll );
	   }else  if(RoomCreatePrincipleEnum.NormalModel.equals(roomPrin)){
		   SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
	   }else  if(RoomCreatePrincipleEnum.UnitHorizontal.equals(roomPrin)){
		   setUnitHorizontalTable(table,isEditedAll );
	   }else  if(RoomCreatePrincipleEnum.UnitVertical.equals(roomPrin)){
		   setUnitVerticalTable(table,isEditedAll);
	   }
   }
//   /**
//    * output btnAddUnit_actionPerformed method
//    */
//   protected void btnAddUnit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
//   {	
//		  //临时生成一个拼装房间
//		  RoomInfo tempRoom =null;
//			for (int i = 0; i < table.getRowCount(); i++){
//				for (int j =0; j < table.getColumnCount(); j++) {
//					ICell cell = table.getCell(i, j);
//					
//						tempRoom =  (RoomInfo) cell.getUserObject();
//						if(null!=tempRoom){
//							break;
//						}
//					}
//					
//					if(null!=tempRoom){
//						break;
//					}
//				}
//		
//		if(null!=tempRoom){
//			 //如果是有不显示的要先确定是否不显示的因为，变化图像如果删除只剩不显示单元了，还是要变回来的
//			 unShowChildrenNode = new HashMap();
//			DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)node;
//			Enumeration enumer = buildNode.children();
//			while (enumer.hasMoreElements()) {
//				DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumer.nextElement();
//				Object object = element.getUserObject();
//				if(object instanceof BuildingUnitInfo){
//					BuildingUnitInfo unit = (BuildingUnitInfo)object;
//					if(true == unit.isHaveUnit()){
//						unShowChildrenNode.put(unit.getId().toString(), element);
//					}
//				}
//			}
//			
//			
//			 //生成单元
//			  BuildingUnitInfo addUnit =  SHEHelper.getShowUnitWithRoomDes(building, false);
//			  unitCol.add(addUnit);
//			  
////			  BuildingUnitInfo buildunit = new BuildingUnitInfo();
////			  buildunit.setSeq(6);
////			  buildunit.setName("测试单元");
////			  buildunit.setBuilding(building);
////			  buildunit.setHaveUnit(false);
//			//装入
//			tempRoom = (RoomInfo) tempRoom.clone();
//			tempRoom.setId(null);
//			  tempRoom.setBuildUnit(addUnit);
//			  tempRoom.setSerialNumber(1);
//			  tempRoom.setFloor(1);
//			  tempRoom.setEndSerialNumber(1);
//			  tempRoom.setIsForSHE(true);
//			  tempRoom.setBuilding(building);
////			  RoomInfo addRoom = getRoomSourcePrincipleName(1+SHEHelper.getSerialNumberStr(1,isConvert),tempRoom,sellProStr,sellProNumberStr);
////			  addRoom = (RoomInfo) addRoom.clone();
//			
//				  roomCol.add(tempRoom);
////			   sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
////			  sqlBuilder.addParam(building.getId().toString());
////				sqlBuilder.addBatch();
////			  sqlBuilder.executeBatch();
////			  sqlBuilder.addParam(building.getId().toString());
////			  
////			  sqlBuilder.executeUpdate();
//			//拼装节点
//				
//			 			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(addUnit);
////			 			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(buildunit);
//							Enumeration enumeration = buildNode.children();
//							while (enumeration.hasMoreElements()) {
//								DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration.nextElement();
//								Object object = element.getUserObject();
//								if(object instanceof BuildingUnitInfo){
//									((BuildingUnitInfo)object).setHaveUnit(false);
//								}
//							}
//							if(buildNode!=null) {
//								buildNode.add(sonNode);
//							}
//						
//				
//			  //生成图
//			   SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
//		}
////		  tempRoom = null;
//		
//	}
	
   /**
    * output btnAddUnit_actionPerformed method
    */
   protected void btnAddUnit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
   {	
		  //得到选择新增单元的房间
	   /*
	    *by tim_gao
	    *不随机选一个，而是添加一个选定的房间可以做判断，比较合适 
	    */
		  RoomInfo tempRoom =null;
		ICell activeCell =  table.getCell(table.getSelectManager().getActiveRowIndex(), table.getSelectManager().getActiveColumnIndex());
		if(null==activeCell){
			 FDCMsgBox.showWarning("请点击需要增加单元的房间！");
				return; 
		}
				 if( null!=activeCell.getUserObject()){
					
					 if(activeCell.getUserObject() instanceof RoomInfo){
						tempRoom = (RoomInfo) activeCell.getUserObject();
					 }else{
						 FDCMsgBox.showWarning("请选择需要增加单元的房间！");
							return; 
					 }
				 }else{
					 FDCMsgBox.showWarning("请选择需要增加单元的房间！");
						return; 
				 }
				 if(null==tempRoom){
					 FDCMsgBox.showWarning("请选择需要增加单元的房间！");
					 return;
				 }
		if(null!=tempRoom){
			 //如果是有不显示的要先确定是否不显示的因为，变化图像如果删除只剩不显示单元了，还是要变回来的
			
			DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)node;
			Enumeration enumer = buildNode.children();
			while (enumer.hasMoreElements()) {
				DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumer.nextElement();
				Object object = element.getUserObject();
				if(object instanceof BuildingUnitInfo){
					BuildingUnitInfo unit = (BuildingUnitInfo)object;
					if(true == unit.isHaveUnit()){
						if(null==unShowChildrenNode||unShowChildrenNode.size()<=0){//目前只装一个
							unShowChildrenNode.put(unit.getId().toString(), element);
						}
						
					}
				}
			}
			
			int num = -1 ; 
			BuildingUnitInfo addUnit = new BuildingUnitInfo();
			 //生成单元,拼装一个假的单元,用于成像，这个图处理了才在后面正式生成
			/*
			 *  by tim_gao
			 * 描述： 点击新增单元 ，要生成一个单元，
			 * 问题： 点击生成后这个单元要生成，但是如果点退出，或者取消，这个单元是不应写如数据库的，所以在生成单元的时候
			 * 		 是拼装一个假的，
			 * 解决：所有的数据交互，都写在，生成按钮里面，只有点击生成才会处理所有的东西
			 * <p> 一开始是在数据库里生成一个单元，然后不管如果在销毁生成界面的时候都根据现有值去处理数据库里的值
			 * 		但是这样太麻烦，交互多，而且容易报错，考虑不周全，不能这么做，所以先做一个假的，这样比较合适
			 */
//				CRMHelper.sortCollection(unitCol, "seq", true);
			boolean isAdd = true;
				Iterator it = unitCol.iterator();
				while(it.hasNext()){
					BuildingUnitInfo unit = (BuildingUnitInfo) it.next();
					if(unit.getSeq()==tempRoom.getBuildUnit().getSeq()+1){
						isAdd = false;
						break;
					}
				}
				if(!isAdd){
					FDCMsgBox.showWarning("所选房间后已有连续单元，不能插入新单元，请重新选择新增单元的房间！");
					return;
				}
			//加完后要在提交后处理提交，生成相对应的房间定义 因为单元与房间定义1对1
				num = tempRoom.getBuildUnit().getSeq()+1;
			addUnit.setId(BOSUuid.create("FDBCDCB3"));
			addUnit.setBuilding(building);
			addUnit.setSeq(num);
			addUnit.setName(num+"单元");
			addUnit.setHaveUnit(false);
//			  BuildingUnitInfo addUnit =  SHEHelper.getShowUnitWithRoomDes(building, false);
			  unitCol.add(addUnit);
			  //装入map 方便提交
			  tempUnit.add(addUnit);
//			  BuildingUnitInfo buildunit = new BuildingUnitInfo();
//			  buildunit.setSeq(6);
//			  buildunit.setName("测试单元");
//			  buildunit.setBuilding(building);
//			  buildunit.setHaveUnit(false);
			//装入
			tempRoom = (RoomInfo) tempRoom.clone();
			tempRoom.setId(null);
			  tempRoom.setBuildUnit(addUnit);
//			  tempRoom.setSerialNumber(1);
//			  tempRoom.setFloor(1);
//			  tempRoom.setEndSerialNumber(1);
//			  tempRoom.setIsForSHE(true);
//			  tempRoom.setBuilding(building);
//		  RoomInfo addRoom =;
//			  addRoom = (RoomInfo) addRoom.clone();
			if(null==tempRoom.getSellState()){
				tempRoom.setSellState(RoomSellStateEnum.Init);
			}
				  roomCol.add( getRoomSourcePrincipleName(1+SHEHelper.getSerialNumberStr(1,isConvert),tempRoom,sellProStr,sellProNumberStr));
//			   sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
//			  sqlBuilder.addParam(building.getId().toString());
//				sqlBuilder.addBatch();
//			  sqlBuilder.executeBatch();
//			  sqlBuilder.addParam(building.getId().toString());
//			  
//			  sqlBuilder.executeUpdate();
			//拼装节点
				
			 			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(addUnit);
//			 			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(buildunit);
							Enumeration enumeration = buildNode.children();
							while (enumeration.hasMoreElements()) {
								DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration.nextElement();
								Object object = element.getUserObject();
								if(object instanceof BuildingUnitInfo){
									((BuildingUnitInfo)object).setHaveUnit(false);
								}
							}
							if(buildNode!=null) {
								//增加的节点的顺序
								int addNum =addUnit.getSeq()-1;
								if(addNum>buildNode.getChildCount()){
									addNum = buildNode.getChildCount();
								}
								buildNode.insert(sonNode, addNum);
							}
						
				
			  //生成图
			   SHEHelper.fillRoomTableByUnitCol(table, roomCol, setting,unitCol,(DefaultKingdeeTreeNode)node);
		}
		  tempRoom = null;
		
	}

}