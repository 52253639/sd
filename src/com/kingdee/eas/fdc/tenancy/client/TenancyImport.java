package com.kingdee.eas.fdc.tenancy.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardFactory;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionFactory;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SightRequirementFactory;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryFactory;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.FlagAtTermEnum;
import com.kingdee.eas.fdc.tenancy.FreeTenancyTypeEnum;
import com.kingdee.eas.fdc.tenancy.HandleStateEnum;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryFactory;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryInfo;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;

public class TenancyImport {
	public static boolean tenancyUpdata() throws EASBizException, BOSException{
		boolean flag=false;
		//客户分录处理
		String sql2="select * from where  propertyPercent is  null";
		TenancyCustomerEntryCollection tceColl =getTenancyCustomerEntryCollection(sql2);
		for(int i=0;i<tceColl.size();i++){
			TenancyCustomerEntryInfo tc=tceColl.get(i);
			tc.setPropertyPercent(new BigDecimal(100));//百分比
			try {
				TenancyCustomerEntryFactory.getRemoteInstance().update(new ObjectUuidPK(tc.getId().toString()), tc);
				flag=true;
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block propertyPercent 比例
				e.printStackTrace();
			}
		}
		//房间分录
		String sql1="select *,tenancy.startDate,tenancy.endDate from where  roomLongNum is  null and room is not null";
		TenancyRoomEntryCollection epColl =getTenancyRoomEntryCol(sql1);
		for(int i=0;i<epColl.size();i++){
			TenancyRoomEntryInfo tenancyRoom=epColl.get(i);
			tenancyRoom=roomToTenRoomEntry(tenancyRoom);
			tenancyRoom.setDealRentType(RentTypeEnum.RentByMonth);//成交租金类型   ==计租周期
			try {
				TenancyRoomEntryFactory.getRemoteInstance().update(new ObjectUuidPK(tenancyRoom.getId().toString()), tenancyRoom);
				flag=true;
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block propertyPercent 比例
				e.printStackTrace();
			}
		}
		//免租分录
		com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection rfeColl =getRentFreeEntryCol();
		for(int i=0;i<rfeColl.size();i++){
			RentFreeEntryInfo rfeinfo=rfeColl.get(i);
			String string =rfeinfo.getDescription();
			if(string!=null&&!"".equals(string))
			if(string.equals("1FREETENNOTMONEY")||string.equals("2FREEMONEYNOTTEN")||string.equals("3FREETENANDMONEY")){
				
			}else{
				string="1FREETENNOTMONEY";
			}
			FreeTenancyTypeEnum freeTenancyType=FreeTenancyTypeEnum.getEnum(string);
			rfeinfo.setFreeTenancyType(freeTenancyType);
			rfeinfo.setDescription(null);
			try {
				RentFreeEntryFactory.getRemoteInstance().update(new ObjectUuidPK(rfeinfo.getId().toString()), rfeinfo);
				flag=true;
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block propertyPercent 比例
				e.printStackTrace();
			}
		}
		//合同     处理
		String sql0="select * from where tenRoomsDes is  null or tenCustomerDes is null or tenRoomsDes ='' or tenCustomerDes='' ";//or tenAttachesDes  null  (tenRoomsDes is  null or tenCustomerDes is null or tenRoomsDes ='' or tenCustomerDes='') and 
		TenancyBillCollection tColl =getTenancyBillCollection(sql0);
		String tenRoomsDesis="";//房间
		for(int i=0;i<tColl.size();i++){
			TenancyBillInfo t=tColl.get(i);
			//删除房间重复行
			HashMap hm = new HashMap();   
			for(int j=0;j<t.getTenancyRoomList().size();j++){
				TenancyRoomEntryInfo treinfo=TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryInfo(new ObjectUuidPK(t.getTenancyRoomList().get(j).getId()));
				if(treinfo.getRoom()!=null){
					if(!hm.containsValue(treinfo.getRoom().getId())){
						hm.put(treinfo.getId(), treinfo.getRoom().getId());
					}
				}
			}
			Iterator it = hm.keySet().iterator();
			while (it.hasNext()){
				TenancyRoomEntryInfo treinfo=TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryInfo(new ObjectUuidPK(it.next().toString()));
				t.getTenancyRoomList().clear();
				t.getTenancyRoomList().add(treinfo);
			}
			//删除客户重复行
			HashMap hm2 = new HashMap();   
			for(int j=0;j<t.getTenCustomerList().size();j++){
				TenancyCustomerEntryInfo tceinfo=TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryInfo(new ObjectUuidPK(t.getTenCustomerList().get(j).getId()));
				if(tceinfo.getFdcCustomer()!=null){
					if(!hm2.containsValue(tceinfo.getFdcCustomer().getId())){
						hm2.put(tceinfo.getId(), tceinfo.getFdcCustomer().getId());
					}
				}
			}
			Iterator itC = hm2.keySet().iterator();
			while (itC.hasNext()){
				TenancyCustomerEntryInfo tceinfo=TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryInfo(new ObjectUuidPK(itC.next().toString()));
				t.getTenCustomerList().clear();
				t.getTenCustomerList().add(tceinfo);
			}
			//删除无效 其他应付明细分录
			ArrayList otherPay=new ArrayList();
			for(int j=0;j<t.getOtherPayList().size();j++){
				TenBillOtherPayInfo tbopinfo=TenBillOtherPayFactory.getRemoteInstance().getTenBillOtherPayInfo(new ObjectUuidPK(t.getOtherPayList().get(j).getId()));
				if(tbopinfo.getMoneyDefine()!=null){
					otherPay.add(tbopinfo);
				}
			}
			t.getOtherPayList().clear();
			for(int j=0;j<otherPay.size();j++){
				t.getOtherPayList().add((TenBillOtherPayInfo)otherPay.get(j));
			}
			//删除免租不全信息rentFrees
			ArrayList rent=new ArrayList();
			for(int j=0;j<t.getRentFrees().size();j++){
				RentFreeEntryInfo rentinfo=RentFreeEntryFactory.getRemoteInstance().getRentFreeEntryInfo(new ObjectUuidPK(t.getRentFrees().get(j).getId()));
				if(rentinfo.getFreeStartDate()!=null&&rentinfo.getFreeEndDate()!=null){
					rent.add(rentinfo);
				}
			}
			t.getRentFrees().clear();
			for(int j=0;j<rent.size();j++){
				t.getRentFrees().add((RentFreeEntryInfo)rent.get(j));
			}
			//添加状态：
			t.setState(FDCBillStateEnum.SUBMITTED);
			TenancyBillFactory.getRemoteInstance().update(new ObjectUuidPK(t.getId()),t);
			
			try {
				//房间
				String sql01="select RoomLongNum from where  tenancy ='"+t.getId().toString()+"'";
				TenancyRoomEntryCollection treColl01 =getTenancyRoomEntryCol(sql01);
				if(treColl01!=null&&treColl01.size()>0){
					for(int j=0;j<treColl01.size()-1;j++){
						tenRoomsDesis=((TenancyRoomEntryInfo)treColl01.get(j)).getRoomLongNum()+","+tenRoomsDesis;
					}
					tenRoomsDesis=tenRoomsDesis+((TenancyRoomEntryInfo)treColl01.get(treColl01.size()-1)).getRoomLongNum();
					t.setTenRoomsDes(tenRoomsDesis);
					tenRoomsDesis="";
				}
				//客户
				String sql02="select name from where  tenancyBill ='"+t.getId().toString()+"'";
				TenancyCustomerEntryCollection tceColl02 =getTenancyCustomerEntryCollection(sql02);
				String tenCustomerDes="";//客户
				if(tceColl02!=null&&tceColl02.size()>0){
//					for(int j=0;j<tceColl02.size()-1;j++){
//						TenancyCustomerEntryInfo tceinfo1=tceColl02.get(j);
//						tceinfo1=TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryInfo(new ObjectUuidPK(tceinfo1.getId()));
//						if(tceinfo1.getFdcCustomer()!=null&&tceinfo1.getFdcCustomer().getId()!=null){
//							FDCCustomerInfo fdccinfo1=tceinfo1.getFdcCustomer();
//							fdccinfo1=FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(fdccinfo1.getId()));
//						tenCustomerDes=fdccinfo1.getName()+","+tenCustomerDes;
//						}
//					}
//					TenancyCustomerEntryInfo tceinfo=tceColl02.get(tceColl02.size()-1);
//					tceinfo=TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryInfo(new ObjectUuidPK(tceinfo.getId()));
//					FDCCustomerInfo fdccinfo2=tceinfo.getFdcCustomer();
//					if(fdccinfo2!=null&&fdccinfo2.getId()!=null){
//						fdccinfo2=FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(fdccinfo2.getId()));
//						if(tceinfo.getFdcCustomer()!=null){
//							tenCustomerDes=tenCustomerDes+fdccinfo2.getName();
//						}
//					}
//					t.setTenCustomerDes(tenCustomerDes);
//					tenCustomerDes="";
					for(int j=0;j<tceColl02.size();j++){
						TenancyCustomerEntryInfo tceinfo1=tceColl02.get(j);
						tceinfo1=TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryInfo(new ObjectUuidPK(tceinfo1.getId()));
						if(tceinfo1.getFdcCustomer()!=null&&tceinfo1.getFdcCustomer().getId()!=null){
							FDCCustomerInfo fdccinfo1=tceinfo1.getFdcCustomer();
							fdccinfo1=FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(fdccinfo1.getId()));
							tenCustomerDes=fdccinfo1.getName()+","+tenCustomerDes;
						}
					}
					if(tenCustomerDes.contains(",")){
						tenCustomerDes=tenCustomerDes.substring(0,tenCustomerDes.lastIndexOf(","));
					}
					t.setTenCustomerDes(tenCustomerDes);
				}
				
				TenancyBillFactory.getRemoteInstance().update(new ObjectUuidPK(t.getId().toString()), t);
				flag=true;
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block propertyPercent 比例
				e.printStackTrace();
			}
		}
		return flag;
	}
	//租赁合同
	private static TenancyBillCollection getTenancyBillCollection(String sql0) {
		TenancyBillCollection epColl =null;
		try {
			epColl = TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(sql0);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return epColl;
	}
	//免租集
	private static RentFreeEntryCollection getRentFreeEntryCol() {
		RentFreeEntryCollection epColl = null;
		String sql2="select * from where  tenancy is not null and description is not null and freeTenancyType is null ";
		try {
			epColl = RentFreeEntryFactory.getRemoteInstance().getRentFreeEntryCollection(sql2);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return epColl;
	}
	//客户集
	private static TenancyCustomerEntryCollection getTenancyCustomerEntryCollection(String sql2) {
		TenancyCustomerEntryCollection epColl = null;
		try {
			epColl = TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryCollection(sql2);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return epColl;
	}
	//房间分录集
	public static	TenancyRoomEntryCollection getTenancyRoomEntryCol(String sql1){
//		EntityViewInfo evi = new EntityViewInfo();
//		FilterInfo filterInfo = new FilterInfo(); //建立过滤条件
//		filterInfo.getFilterItems().add(new FilterItemInfo("roomLongNum",null));
//		filterInfo.getFilterItems().add(new FilterItemInfo("tenancy.id",null));
//		filterInfo.getFilterItems().add(new FilterItemInfo("tenancy.id",null));
//		evi.setFilter(filterInfo);
		TenancyRoomEntryCollection epColl = null;
		try {
			epColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(sql1);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return epColl;
	}
	 //根据房间 填充 房间分录的 冗余字段
	private static TenancyRoomEntryInfo roomToTenRoomEntry(TenancyRoomEntryInfo tenRoom) {
		if(tenRoom==null||tenRoom.getRoom()==null){
			return null;
		}
		RoomInfo room;
		try {
			room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK((tenRoom.getRoom().getId()).toString()));
			if (room == null) {
				return null;
			}
			//根据房间分录来更新 其他字段
			tenRoom =OthertenRoom(tenRoom);
			tenRoom.setRoom(room);
			tenRoom.setTenancyModel(room.getTenancyModel());
	
			tenRoom.setHandleState(HandleStateEnum.NoHandleRoom);
	
			tenRoom.setStandardRentType(room.getRentType());
			tenRoom.setStandardRoomRent(room.getStandardRent());
	//		tenRoom.setStandardRoomRentPrice(room.getStandardRentPrice());
	
			// 执行租价默认赋标准租金价格
			tenRoom.setDealRentType(room.getRentType());
			if(tenRoom.getDealRoomRent() != null){
				tenRoom.setDepositAmount(new BigDecimal("2").multiply(tenRoom.getDealRoomRent()));
			}
//			tenRoom.setFirstPayAmount(tenRoom.getDealRoomRent());
	
			// 快照房间的建筑面积和实测面积
			// 优先取实测建筑,如果没有则取建筑面积
			//取房间的计租面积  by zhendui_ai (上面两句话的逻辑不要了)
			BigDecimal buildingArea = room.getTenancyArea();
	//		if (buildingArea == null || buildingArea.compareTo(FDCHelper.ZERO) == 0) {
	//			buildingArea = room.getTenancyArea();
	//		}
			tenRoom.setBuildingArea(buildingArea);
			//租金
			tenRoom.setDealRoomRent(room.getStandardRent());
			
			//成交租金单价
			
			if(room.getStandardRent()!=null&&buildingArea!=null){
				tenRoom.setDealRoomRentPrice(room.getStandardRent().divide(buildingArea, 2,BigDecimal.ROUND_HALF_UP));
			}
			//标准租金单价
			if(room.getStandardRent()!=null&&buildingArea!=null){
				tenRoom.setStandardRoomRentPrice(room.getStandardRent().divide(buildingArea, 2,BigDecimal.ROUND_HALF_UP));
			}
			
			BigDecimal roomArea = room.getActualRoomArea();
			if (roomArea == null || roomArea.compareTo(FDCHelper.ZERO) == 0) {
				roomArea = room.getRoomArea();
			}
			tenRoom.setRoomArea(roomArea);
	
			//快照房间  装修标准，朝向，景观，户型，房屋形式，建筑性质，产品类型，产品描述，房屋用途
			if(room.getDecoraStd()!=null&&room.getDecoraStd().getId()!=null)
			{
				DecorationStandardInfo dsInfo=DecorationStandardFactory.getRemoteInstance().getDecorationStandardInfo(new ObjectUuidPK(room.getDecoraStd().getId().toString()));
				tenRoom.setFitmentStandard(dsInfo.getName());
			}	
			if(room.getDirection()!=null&&room.getDirection().getId()!=null){
				HopedDirectionInfo dinfo=HopedDirectionFactory.getRemoteInstance().getHopedDirectionInfo(new ObjectUuidPK(room.getDirection().getId().toString()));
				tenRoom.setDirection(dinfo);
			}
			if(room.getSight()!=null&&room.getSight().getId()!=null){
				com.kingdee.eas.fdc.sellhouse.SightRequirementInfo srinfo=SightRequirementFactory.getRemoteInstance().getSightRequirementInfo(new ObjectUuidPK(room.getSight().getId().toString()));
				tenRoom.setSight(srinfo);
			}
			if(room.getRoomModel()!=null&&room.getRoomModel().getId()!=null){
				com.kingdee.eas.fdc.sellhouse.RoomModelInfo rmInfo=RoomModelFactory.getRemoteInstance().getRoomModelInfo(new ObjectUuidPK(room.getRoomModel().getId().toString()));
				tenRoom.setRoomModel(rmInfo);
			}
			tenRoom.setRoomModel(room.getRoomModel());
			tenRoom.setRoomForm(room.getRoomForm());
	
			tenRoom.setBuildingProperty(room.getBuildingProperty());
			tenRoom.setProductType(room.getProductType());
			tenRoom.setProductDetail(room.getProductDetail());
			tenRoom.setRoomUsage(room.getRoomUsage());
	
			//设置默认的房间到期标志和房间租赁状态
			tenRoom.setFlagAtTerm(FlagAtTermEnum.Unknown);
			tenRoom.setTenRoomState(TenancyStateEnum.newTenancy);
	
			// 获得长编码 //该长编码可能要在Room上加字段,此处直接从房间信息上getLongNumber
			StringBuffer sbRoomLongNum = new StringBuffer();
			BuildingInfo building = BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(room.getBuilding().getId().toString()));
			
			SellProjectInfo sellPro = (building == null) ? null : building.getSellProject();
			sellPro = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellPro.getId().toString()));
			
//			final String spitStr = "-";
//			if (sellPro != null) {
//				sbRoomLongNum.append(sellPro.getName());
//				sbRoomLongNum.append(spitStr);
//			}
//			if (building != null) {
//				sbRoomLongNum.append(building.getName());
//				sbRoomLongNum.append(spitStr);
//			}
//			if (room.getUnit() != 0) {
//				sbRoomLongNum.append(room.getUnit());
//				sbRoomLongNum.append(spitStr);
//			}
//			sbRoomLongNum.append(room.getNumber());
	
			tenRoom.setRoomLongNum(room.getName());
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tenRoom;
	
	}
	// 根据 房间分录 ADD成交价分录
	private static TenancyRoomEntryInfo OthertenRoom(
			TenancyRoomEntryInfo tenRoom) {
		if(tenRoom==null){
			return null;
		}
		TenancyRoomEntryInfo treInfo=null;
		try {
			treInfo=TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryInfo(new ObjectUuidPK(tenRoom.getId()));
		} catch (EASBizException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (BOSException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//押金
		DealAmountEntryInfo  daYinfo=new DealAmountEntryInfo();
		daYinfo.setAmount(tenRoom.getDepositAmount());
		tenRoom.setDepositAmount(null);//清空
		daYinfo.setRentType(RentTypeEnum.RentByQuarter);
//		daYinfo.setTenancyRoom(treInfo);
		daYinfo.setIsHandwork(true);
//		daYinfo.setPriceAmount(tenRoom.getDepositAmount());
		MoneyDefineCollection mdYCollection;
		try {
			mdYCollection = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection("select * where moneyType ='DepositAmount'");
			for(int i=0;i<mdYCollection.size();i++){
				daYinfo.setMoneyDefine(mdYCollection.get(0));
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		tenRoom.getDealAmounts().add(daYinfo);
		//租金
		DealAmountEntryInfo  daZinfo=new DealAmountEntryInfo();
		daZinfo.setAmount(tenRoom.getRoomTotalRent());
		tenRoom.setDealRoomRent(tenRoom.getRoomTotalRent());
//		tenRoom.setDepositAmount(null);//清空
		daZinfo.setRentType(RentTypeEnum.RentByQuarter);
		
		if(tenRoom.getStartDate()!=null&&tenRoom.getEndDate()!=null){
			daZinfo.setStartDate(tenRoom.getStartDate());
			daZinfo.setEndDate(tenRoom.getEndDate());
		}
			daZinfo.setIsHandwork(true);
		MoneyDefineCollection mdZCollection;
		try {
			mdZCollection = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection("select * where moneyType ='RentAmount'");
			for(int i=0;i<mdZCollection.size();i++){
				daZinfo.setMoneyDefine(mdZCollection.get(0));
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		tenRoom.getDealAmounts().add(daZinfo);
		return tenRoom;
	}
	//EDIT界面 点  修改触发
	public static void editUpdata(BigDecimal zj, TenancyBillEditUI tenancyBillEditUI) {
		// TODO Auto-generated method stub
		
	}

}
