package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK; //import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean; //import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.market.ValueInputCollection;
import com.kingdee.eas.fdc.market.ValueInputEntryInfo;
import com.kingdee.eas.fdc.market.ValueInputFactory;
import com.kingdee.eas.fdc.market.ValueInputInfo;
import com.kingdee.eas.fdc.market.ValueInputPriceEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListCollection;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListFactory;
import com.kingdee.eas.fdc.sellhouse.BankPaymentInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum;
import com.kingdee.eas.fdc.sellhouse.RoomActChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPlanChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPreChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustHisFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustHisInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceManageFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPriceManageInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceManageCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.BanInformationEntrysNewInfo;
import com.kingdee.eas.fdc.basedata.BanInformationFactory;
import com.kingdee.eas.fdc.basedata.BanInformationInfo;
import com.kingdee.eas.fdc.basedata.CalculateTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.HouseTypeFactory;
import com.kingdee.eas.fdc.basedata.IndexVersionFactory;
import com.kingdee.eas.fdc.basedata.IndexVersionInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeDateEntryInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeDateFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeDateInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseFactory;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectCollection;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectFactory;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectInfo;
import com.kingdee.eas.fdc.basedata.SchedulePlanEntryInfo;
import com.kingdee.eas.fdc.basedata.SchedulePlanFactory;
import com.kingdee.eas.fdc.basedata.SchedulePlanInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class RoomPriceManageControllerBean extends
		AbstractRoomPriceManageControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomPriceManageControllerBean");

	protected boolean _excute(Context ctx, String id) throws BOSException,
			EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
		sels.add("priceAdjustEntry.*");
		sels.add("priceAdjustEntry.room.*");
		sels.add("priceAdjustEntry.room.building.*");
		sels.add("priceAdjustEntry.room.buildUnit.*");
		sels.add("managerAigo");
		sels.add("sceneManagerAgio");
		sels.add("salesDirectorAgio");
		sels.add("valueEntry.*");
		RoomPriceManageInfo bill = (RoomPriceManageInfo) this.getValue(ctx,
				new ObjectUuidPK(BOSUuid.read(id)), sels);
		if (bill.getAuditor() == null) {
			return false;
		}

		RoomPriceAdjustEntryCollection entrys = bill.getPriceAdjustEntry();
		for (int i = 0; i < entrys.size(); i++) {
			RoomPriceAdjustEntryInfo entry = entrys.get(i);

			RoomInfo room = entry.getRoom();

			// 面积改变检测
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
			// 根据销售方式取面积
			if (entry.getSellType() != null) {
				if (entry.getSellType().equals(SellTypeEnum.LocaleSell)) { // 现售
					buildingArea = room.getActualBuildingArea();
					roomArea = room.getActualRoomArea();
				} else if (entry.getSellType().equals(SellTypeEnum.PreSell)) { // 预售
					buildingArea = room.getBuildingArea();
					roomArea = room.getRoomArea();
				} else { // 规划
					buildingArea = room.getPlanBuildingArea();
					roomArea = room.getPlanRoomArea();
				}
			}
			if (buildingArea == null) {
				buildingArea = FDCHelper.ZERO;
			}
			if (roomArea == null) {
				roomArea = FDCHelper.ZERO;
			}
			
			if (roomArea.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(entryRoomArea.setScale(2, BigDecimal.ROUND_HALF_UP)) != 0) {
				throw new EASBizException(new NumericExceptionSubItem("100","房间"+room.getName()+"套内面积与房价面积录入面积不符！"));
			}

			if (buildingArea.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(entryBuildingArea.setScale(2, BigDecimal.ROUND_HALF_UP)) != 0) {
				throw new EASBizException(new NumericExceptionSubItem("100","房间"+room.getName()+"套内面积与房价面积录入面积不符！"));
			}
			
			PriceTypeForPriceBillEnum priceType = entry.getPriceType();
			if (priceType != null
					&& priceType.equals(PriceTypeForPriceBillEnum.UseRoomArea)) {
				room.setIsCalByRoomArea(true);
			} else {
				room.setIsCalByRoomArea(false);
			}

			// 将定价类型反写到房间上
			if (priceType != null) {
				if (priceType.equals(PriceTypeForPriceBillEnum.UseBuildingArea)) {
					room.setCalcType(CalcTypeEnum.PriceByBuildingArea);
				} else if (priceType
						.equals(PriceTypeForPriceBillEnum.UseRoomArea)) {
					room.setCalcType(CalcTypeEnum.PriceByRoomArea);
				} else if (priceType
						.equals(PriceTypeForPriceBillEnum.UseStandPrice)) {
					room.setCalcType(CalcTypeEnum.PriceByTotalAmount);
				}
			}

			// 将定价单上的价格反写到房间上
			room.setRoomPrice(entry.getNewRoomPrice());
			room.setBuildPrice(entry.getNewBuildingPrice());
			room.setStandardTotalAmount(entry.getNewSumAmount());
			
			room.setBaseStandardPrice(entry.getNewBaseStandardPrice());
			room.setManagerAgio(entry.getNewManagerAgio());
			if(entry.getNewBaseStandardPrice()!=null&&buildingArea!=null&&buildingArea.compareTo(FDCHelper.ZERO)!=0){
				room.setBaseBuildingPrice(entry.getNewBaseStandardPrice().divide(buildingArea, 2,BigDecimal.ROUND_HALF_UP));
			}
			if(entry.getNewBaseStandardPrice()!=null&&roomArea!=null&&roomArea.compareTo(FDCHelper.ZERO)!=0){
				room.setBaseRoomPrice(entry.getNewBaseStandardPrice().divide(roomArea, 2,BigDecimal.ROUND_HALF_UP));
			}
			SellTypeEnum sellType = entry.getSellType();
			if (sellType != null) {
				room.setSellType(sellType);
			}

			// 执行后清空房间的变更状态
			room.setChangeState(null);
			room.setIsChangeSellArea(false);
			room.setPlanChangeState(RoomPlanChangeStateEnum.NOCHANCE);
			room.setPreChangeState(RoomPreChangeStateEnum.NOCHANGE);
			room.setActChangeState(RoomActChangeStateEnum.NOCHANGE);
			
			if(bill.getManagerAigo() != null){
				room.setManagerAgio(bill.getManagerAigo());
			}
			else{
				room.setManagerAgio(new BigDecimal("0.00"));
			}
			if(entry.getNewSalesDirectorAgio()!= null){
				room.setSalesDirectorAgio(entry.getNewSalesDirectorAgio());
			}
			else{
				room.setSalesDirectorAgio(new BigDecimal("0.00"));
			}
			if(entry.getNewSceneManagerAgio() != null){
				room.setSceneManagerAgio(entry.getNewSceneManagerAgio());
			}
			else{
				room.setSceneManagerAgio(new BigDecimal("0.00"));
			}			
			sels = new SelectorItemCollection();
			sels.add("calcType");
			sels.add("buildPrice");
			sels.add("roomPrice");
			sels.add("standardTotalAmount");
			sels.add("isCalByRoomArea");
			sels.add("sellType");
			sels.add("changeState");
			sels.add("isChangeSellArea");
			sels.add("planChangeState");
			sels.add("preChangeState");
			sels.add("actChangeState");
			sels.add("salesDirectorAgio");
			sels.add("sceneManagerAgio");
			sels.add("managerAgio");
			sels.add("baseStandardPrice");
			sels.add("baseRoomPrice");
			sels.add("baseBuildingPrice");
			
			
			
			RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);

			// 添加到历史记录
			this.addToRoomPriceHistoryEntry(ctx, bill, entry.getNewSumAmount(),
					entry);
		}

		bill.setIsExecuted(true);
		sels = new SelectorItemCollection();
		sels.add("isExecuted");
		this.updatePartial(ctx, bill, sels);
		
//		genValueInputInfo(ctx,bill);
		return true;
	}
	protected void genValueInputInfo(Context ctx,RoomPriceManageInfo priceInfo) throws EASBizException, BOSException{
		SellProjectInfo psp=SHEManageHelper.getParentSellProject(ctx, priceInfo.getSellProject());
		if(psp!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("projectBase.id");
			psp=SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(psp.getId()), sel);
			if(psp.getProjectBase()!=null){
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("sourceBillId",priceInfo.getId().toString()));
				ValueInputFactory.getLocalInstance(ctx).delete(filter);
				
				ValueInputInfo info=new ValueInputInfo();
				info.setSourceBillId(priceInfo.getId().toString());
				Date now=SysUtil.getAppServerTime(ctx);
				info.setBizDate(now);
				
				sel=new SelectorItemCollection();
				sel.add("name");
				sel.add("engineeProject.*");
				ProjectBaseInfo pb = ProjectBaseFactory.getLocalInstance(ctx).getProjectBaseInfo(new ObjectUuidPK(psp.getProjectBase().getId()),sel);
				info.setProject(pb);
				info.setOrgUnit(pb.getEngineeProject());
				
				IndexVersionInfo indexVersion=IndexVersionFactory.getLocalInstance(ctx).getIndexVersionInfo("select * from where number='005'");
				info.setIndexVersion(indexVersion);
				Set allSp=SHEManageHelper.getAllSellProjectCollection(ctx,psp);
				filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",allSp,CompareType.INCLUDE));
				filter.getFilterItems().add(new FilterItemInfo("isExecuted",Boolean.TRUE));
				if(RoomPriceManageFactory.getLocalInstance(ctx).exists(filter)){
					if(priceInfo.getPriceBillMode().equals(PriceBillTypeEnum.SetPrice)){
						info.setName("定价_"+FDCDateHelper.DateToString(now));
					}else{
						info.setName("调价_"+FDCDateHelper.DateToString(now));
					}
				}else{
					info.setName(indexVersion.getName()+"_"+FDCDateHelper.DateToString(now));
				}
				
				if(priceInfo.getValueEntry().size()==0){
					ValueInputCollection vaCol=ValueInputFactory.getLocalInstance(ctx).getValueInputCollection("select indexVersion.*,entry.* from where project.id='"+info.getProject().getId().toString()+"' and isLatest=1 order by indexVersion.number desc");
					if(vaCol.size()>0){
						for(int i=0;i<vaCol.get(0).getEntry().size();i++){
							ValueInputEntryInfo entry=vaCol.get(0).getEntry().get(i);
							entry.setPriceHead(null);
							entry.setId(null);
							if(entry.getBuildId()!=null&&entry.getProductTypeId()!=null){
								try {
									getSellHouseAmount(ctx,entry.getBuildId().toString(),entry.getProductTypeId().toString(),entry);
								} catch (SQLException e) {
									e.printStackTrace();
								}
								if(entry.getCalculateType().equals(CalculateTypeEnum.NOW)){
									entry.setPrice(FDCHelper.divide(entry.getAmount(), entry.getArea(), 2, BigDecimal.ROUND_HALF_UP));
								}else{
									entry.setPrice(FDCHelper.divide(entry.getAmount(), entry.getAccount(), 2, BigDecimal.ROUND_HALF_UP));
								}
							}
							info.getEntry().add(entry);
						}
					}
				}else{
					for(int i=0;i<priceInfo.getValueEntry().size();i++){
						ValueInputEntryInfo entry=(ValueInputEntryInfo) priceInfo.getValueEntry().get(i).clone();
						entry.setPriceHead(null);
						entry.setId(null);
						if(entry.getBuildId()!=null&&entry.getProductTypeId()!=null){
							try {
								getSellHouseAmount(ctx,entry.getBuildId().toString(),entry.getProductTypeId().toString(),entry);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							if(entry.getCalculateType().equals(CalculateTypeEnum.NOW)){
								entry.setPrice(FDCHelper.divide(entry.getAmount(), entry.getArea(), 2, BigDecimal.ROUND_HALF_UP));
							}else{
								entry.setPrice(FDCHelper.divide(entry.getAmount(), entry.getAccount(), 2, BigDecimal.ROUND_HALF_UP));
							}
						}
						info.getEntry().add(entry);
					}
				}
				
				ValueInputCollection vaCol=ValueInputFactory.getLocalInstance(ctx).getValueInputCollection("select id from where project.id='"+info.getProject().getId().toString()+"' and indexVersion.id='"+info.getIndexVersion().getId().toString()+"'");
				
				info.setVersion(vaCol.size()+1);
				info.setState(FDCBillStateEnum.SAVED);
				info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
				
				info.setCreator(null);
				info.setCreateTime(null);
				info.setAuditor(null);
				info.setAuditTime(null);
				info.setLastUpdateUser(null);
				info.setLastUpdateTime(null);
				
				getValueInputNumber(ctx,info);
				if(info.getNumber()==null) info.setNumber(priceInfo.getNumber()); 
				
				IObjectPK pk=ValueInputFactory.getLocalInstance(ctx).submit(info);
				ValueInputFactory.getLocalInstance(ctx).audit(BOSUuid.read(pk.toString()));
			}
		}
	}
	protected void getSellHouseAmount(Context ctx,String id,String productTypeId,ValueInputEntryInfo entry) throws SQLException, BOSException, EASBizException{
    	BanBasedataEntryListCollection col=BanBasedataEntryListFactory.getLocalInstance(ctx).getBanBasedataEntryListCollection("select head.id from where banBasedataEntry.id='"+id+"'");
    	if(col.size()>0){
//    		FilterInfo filter=new FilterInfo();
//        	filter.getFilterItems().add(new FilterItemInfo("room.building.id",col.get(0).getHead().getId().toString()));
//        	filter.getFilterItems().add(new FilterItemInfo("head.isExecuted",Boolean.TRUE));
//        	if(RoomPriceAdjustEntryFactory.getRemoteInstance().exists(filter)){
        		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
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
	private void getValueInputNumber(Context ctx,ValueInputInfo info) throws EASBizException, BOSException{
    	ICodingRuleManager iCodingRuleManager= CodingRuleManagerFactory.getLocalInstance(ctx);
		OrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		String retNumber ="";
		if(iCodingRuleManager.isExist(info, orgUnit.getId().toString()))
		     retNumber = iCodingRuleManager.getNumber(info, orgUnit.getId().toString());
		if(retNumber!=null && !"".equals(retNumber)){
			info.setNumber(retNumber);
		}
    }
	/**
	 * 将定价信息放入到定价历史记录里面去
	 */
	private void addToRoomPriceHistoryEntry(Context ctx,
			RoomPriceManageInfo bill, BigDecimal totalAmount,
			RoomPriceAdjustEntryInfo entry) throws EASBizException,
			BOSException {
		RoomPriceAdjustHisInfo history = new RoomPriceAdjustHisInfo();

		history.setBillId(bill.getId());
		history.setRoom(entry.getRoom());
		history.setNumber(String.valueOf(new Date().getTime()));
		history.setRoomNo(entry.getRoom().getDisplayName());
		history.setSellType(entry.getSellType());
		history.setStandAmount(totalAmount);
		history.setBuildingArea(entry.getNewBuildingArea());
		history.setBuildingPrice(entry.getNewBuildingPrice());
		history.setRoomArea(entry.getNewRoomArea());
		history.setRoomPrice(entry.getNewRoomPrice());
		history.setSellState(entry.getRoom().getSellState());
		history.setChangeState(entry.getRoom().getChangeState());
		history.setBizDate(new Date());
		history.setPriceMode(bill.getPriceBillMode());
		history.setPriceType(entry.getPriceType());

		RoomPriceAdjustHisFactory.getLocalInstance(ctx).submit(history);
	}
	
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
		if(billInfo.getState().equals(FDCBillStateEnum.SAVED)){
			return;
		}
		else{
			super.checkNameDup(ctx, billInfo);
		}
	}
	protected boolean _isExistDown(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("priceAdjustEntry.*");
		sels.add("priceAdjustEntry.room.*");
		RoomPriceManageInfo bill = (RoomPriceManageInfo) this.getValue(ctx,new ObjectUuidPK(billId), sels);

		RoomPriceAdjustEntryCollection entrys = bill.getPriceAdjustEntry();
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
			// 根据销售方式取面积
			if (entry.getSellType() != null) {
				if (entry.getSellType().equals(SellTypeEnum.LocaleSell)) { // 现售
					buildingArea = room.getActualBuildingArea();
					roomArea = room.getActualRoomArea();
				} else if (entry.getSellType().equals(SellTypeEnum.PreSell)) { // 预售
					buildingArea = room.getBuildingArea();
					roomArea = room.getRoomArea();
				} else { // 规划
					buildingArea = room.getPlanBuildingArea();
					roomArea = room.getPlanRoomArea();
				}
			}
			if (buildingArea == null) {
				buildingArea = FDCHelper.ZERO;
			}
			if (roomArea == null) {
				roomArea = FDCHelper.ZERO;
			}
			if(room.isIsChangeSellArea()){
				continue;
			}else{
				if (roomArea.compareTo(entryRoomArea) != 0) {
					continue;
				}
				if (buildingArea.compareTo(entryBuildingArea) != 0) {
					continue;
				}
			}
			if(entry.getNewSumAmount()!=null&&room.getStandardTotalAmount()!=null){
				if(entry.getNewSumAmount().compareTo(room.getStandardTotalAmount())<0){
					return true;
				}
			}
		}
		return false;
	}
}