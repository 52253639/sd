package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.AgioBillFactory;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.CalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MortagageControlFactory;
import com.kingdee.eas.fdc.sellhouse.MortagageEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurAgioEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPreChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fdc.sellhouse.AgioParam;
import com.kingdee.eas.fdc.sellhouse.client.AgioSelectUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseParam;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.app.ContextUtil;

import org.apache.log4j.Logger;

public class PurchaseManageTransmission extends AbstractDataTransmission{

	private static final Logger logger=CoreUIObject.getLogger(com.kingdee.eas.fdc.sellhouse.app.PurchaseManageTransmission.class);
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try
        {
            return PurchaseManageFactory.getLocalInstance(ctx);
        }
        catch(BOSException e)
        {
            logger.debug(e.getMessage());
            throw new TaskExternalException(e.getMessage(), e);
        }
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		try
        {
            check(hsData, ctx);
        }
        catch(EASBizException e)
        {
            logger.debug(e.getMessage());
            throw new TaskExternalException(e.getMessage(), e);
        }
        catch(BOSException e)
        {
            logger.debug(e.getMessage());
            throw new TaskExternalException(e.getMessage(), e);
        }
        return null;
	}
	private void check(Hashtable hsData, Context ctx) throws TaskExternalException, EASBizException, BOSException{
	     if(hsData.get("FSellProject_name_l2") == null || hsData.get("FSellProject_name_l2") != null && hsData.get("FSellProject_name_l2").toString().trim().length() == 0)
	         throw new TaskExternalException("销售项目不能为空！");
	     
	     FilterInfo filter = new FilterInfo();
	     filter.getFilterItems().add(new FilterItemInfo("parent.name", hsData.get("FSellProject_name_l2")));
	     if(SellProjectFactory.getLocalInstance(ctx).exists(filter)){
	    	 throw new TaskExternalException("请选择子销售项目！");
	     }
	     if(hsData.get("FNumber") == null || hsData.get("FNumber") != null && hsData.get("FNumber").toString().trim().length() == 0)
	         throw new TaskExternalException("单据编号不能为空！");
	     
	}
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		PurchaseManageInfo pur = (PurchaseManageInfo)coreBaseInfo;
		pur.setBizState(TransactionStateEnum.PURSAVED);
		pur.setState(FDCBillStateEnum.SAVED);
		pur.setIsValid(false);
		pur.setIsFitmentToContract(false);
		pur.setOrgUnit(ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo());
		pur.setIsEarnestInHouseAmount(true);
		pur.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
		
		Map detailSet = RoomDisplaySetting.getNewProjectSet(ctx,pur.getSellProject().getId().toString());
		boolean isBasePriceSell=false;
		boolean isQuitRoomAudit=false;
		boolean isChangeRoomAuidt=false;
		boolean isChangeAreaAuidt=false;
		
		if(detailSet!=null){
			isBasePriceSell = ((Boolean)detailSet.get(SHEParamConstant.T2_IS_FORCE_LIMIT_PRICE)).booleanValue();
			isQuitRoomAudit=((Boolean)detailSet.get(SHEParamConstant.T2_IS_RE_PRICE_OF_QUIT_ROOM)).booleanValue();
			isChangeRoomAuidt=((Boolean)detailSet.get(SHEParamConstant.T2_IS_RE_PRICE_OF_CHANGE_ROOM)).booleanValue();
			isChangeAreaAuidt=((Boolean)detailSet.get(SHEParamConstant.T2_IS_AREA_CHANGE_NEED_AUDIT)).booleanValue();
		}
		pur.setIsBasePriceSell(isBasePriceSell);
		
		HashMap param = new HashMap();
		IObjectPK pk=new ObjectUuidPK(ContextUtil.getCurrentOrgUnit(ctx).getId());
		param.put(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE, pk);
		param.put(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT, pk);
		param.put(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE, pk);
		param.put(CRMConstants.FDCSHE_PARAM_PRICE_BIT, pk);
		try {
			HashMap value = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(param);
			
			String totalIntegerType=SHEManageHelper.ROUND_HALF_UP;
			String totalDigit=SHEManageHelper.FEN;
			String priceIntegerType=SHEManageHelper.ROUND_HALF_UP;
			String priceDigit=SHEManageHelper.FEN;
			
			if(value!=null){
				totalIntegerType=value.get(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE).toString();
				totalDigit=value.get(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT).toString();
				priceIntegerType=value.get(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE).toString();
				priceDigit=value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString();
			}else{
				pk=new ObjectUuidPK(OrgConstants.DEF_CU_ID);
				param.put(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE, pk);
				param.put(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT, pk);
				param.put(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE, pk);
				param.put(CRMConstants.FDCSHE_PARAM_PRICE_BIT, pk);
				
				value=ParamControlFactory.getRemoteInstance().getParamHashMap(param);
				if(value!=null){
					totalIntegerType=value.get(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE).toString();
					totalDigit=value.get(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT).toString();
					priceIntegerType=value.get(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE).toString();
					priceDigit=value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString();
				}
			}
			pur.setToIntegerType(totalIntegerType);
			pur.setDigit(totalDigit);
			pur.setPriceToIntegerType(priceIntegerType);
			pur.setPriceDigit(priceDigit);
			
		}catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}

		if(pur.getRoom()!=null){
			try {
				pur.setRoom(RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(pur.getRoom().getId())));
				
				verifyAddNewRoom(pur.getRoom(),isBasePriceSell,isQuitRoomAudit,isChangeRoomAuidt,isChangeAreaAuidt,ctx);
				
				if(pur.getRoom().getSellState().equals(RoomSellStateEnum.PrePurchase)){
					EntityViewInfo view =new EntityViewInfo();
					FilterInfo filter = new FilterInfo();		
					filter.getFilterItems().add(new FilterItemInfo("room.id",pur.getRoom().getId()));
					filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.PURAUDIT_VALUE));
					view.setFilter(filter);
					
					PrePurchaseManageCollection col=PrePurchaseManageFactory.getLocalInstance(ctx).getPrePurchaseManageCollection(view);
					if(col.size()==1){
						pur.setSrcId(col.get(0).getId());
						pur.setTransactionID(col.get(0).getTransactionID());
						pur.setNewCommerceChance(col.get(0).getNewCommerceChance());
					}else{
						throw new TaskExternalException("房间"+pur.getRoom().getName()+"预定单据还未审批！");
					}
				}else{
					if(!RoomSellStateEnum.OnShow.equals(pur.getRoom().getSellState())){
			    		throw new TaskExternalException("房间"+pur.getRoom().getName()+"已经发生业务单据！");
			    	}
				}
				
				SellTypeEnum sellType=pur.getSellType();
		    	CalcTypeEnum valuationType=pur.getValuationType();
//		    	if(CalcTypeEnum.PriceByBuildingArea.equals(valuationType)||CalcTypeEnum.PriceByTotalAmount.equals(valuationType)){
//		    		if(SellTypeEnum.PlanningSell.equals(sellType)){
//		        		if(!pur.getRoom().isIsPlan()){
//		        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"没有预估复核！");
//		        		}
//		        		if(pur.getRoom().getPlanBuildingArea()==null||pur.getRoom().getPlanBuildingArea().compareTo(FDCHelper.ZERO)<=0){
//		        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"预估建筑面积必须大于0！");
//		        		}
//		        	}else if(SellTypeEnum.PreSell.equals(sellType)){
//		        		if(!pur.getRoom().isIsPre()){
//		        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"没有预售复核！");
//		        		}
//		        		if(pur.getRoom().getBuildingArea()==null||pur.getRoom().getBuildingArea().compareTo(FDCHelper.ZERO)<=0){
//		        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"预售建筑面积必须大于0！");
//		        		}
//		        	}else if(SellTypeEnum.LocaleSell.equals(sellType)){
//		        		if(!pur.getRoom().isIsActualAreaAudited()){
//		        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"没有实测复核！");
//		        		}
//		        		if(pur.getRoom().getActualBuildingArea()==null||pur.getRoom().getActualBuildingArea().compareTo(FDCHelper.ZERO)<=0){
//		        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"现售建筑面积必须大于0！");
//		        		}
//		        	}
//				}else if(CalcTypeEnum.PriceByRoomArea.equals(valuationType)){
//					if(SellTypeEnum.PlanningSell.equals(sellType)){
//		        		if(!pur.getRoom().isIsPlan()){
//		        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"没有预估复核！");
//		        		}
//		        		if(pur.getRoom().getPlanRoomArea()==null||pur.getRoom().getPlanRoomArea().compareTo(FDCHelper.ZERO)<=0){
//		        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"预估套内面积必须大于0！");
//		        		}
//		        	}else if(SellTypeEnum.PreSell.equals(sellType)){
//		        		if(!pur.getRoom().isIsPre()){
//		        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"没有预售复核！");
//		        		}
//		        		if(pur.getRoom().getRoomArea()==null||pur.getRoom().getRoomArea().compareTo(FDCHelper.ZERO)<=0){
//		        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"预售套内面积必须大于0！");
//		        		}
//		        	}else if(SellTypeEnum.LocaleSell.equals(sellType)){
//		        		if(!pur.getRoom().isIsActualAreaAudited()){
//		        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"没有实测复核！");
//		        		}
//		        		if(pur.getRoom().getActualRoomArea()==null||pur.getRoom().getActualRoomArea().compareTo(FDCHelper.ZERO)<=0){
//		        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"现售套内面积必须大于0！");
//		        		}
//		        	}
//				}
		    	if(pur.getRoom().getRoomArea()==null||pur.getRoom().getRoomArea().compareTo(FDCHelper.ZERO)<=0){
        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"预售套内面积必须大于0！");
        		}
		    	if(pur.getRoom().getBuildingArea()==null||pur.getRoom().getBuildingArea().compareTo(FDCHelper.ZERO)<=0){
        			throw new TaskExternalException("房间"+pur.getRoom().getName()+"预售建筑面积必须大于0！");
        		}
		    	pur.setStrdRoomPrice(pur.getRoom().getStandardTotalAmount().divide(pur.getRoom().getRoomArea(), 2, BigDecimal.ROUND_HALF_UP));
				pur.setStrdBuildingPrice(pur.getRoom().getStandardTotalAmount().divide(pur.getRoom().getBuildingArea(), 2, BigDecimal.ROUND_HALF_UP));
				
				pur.setStrdPlanBuildingArea(pur.getRoom().getPlanBuildingArea());
				pur.setStrdPlanRoomArea(pur.getRoom().getPlanRoomArea());
				
				pur.setBulidingArea(pur.getRoom().getBuildingArea());
				pur.setRoomArea(pur.getRoom().getRoomArea());
				
				pur.setStrdActualBuildingArea(pur.getRoom().getActualBuildingArea());
				pur.setStrdActualRoomArea(pur.getRoom().getActualRoomArea());
				
	    		if(CalcTypeEnum.PriceByBuildingArea.equals(valuationType)||CalcTypeEnum.PriceByTotalAmount.equals(valuationType)){
	    			pur.setPlanningArea(pur.getStrdPlanBuildingArea());
	    			pur.setPreArea(pur.getBulidingArea());
	    			pur.setActualArea(pur.getStrdActualBuildingArea());
	    		}else if(CalcTypeEnum.PriceByRoomArea.equals(valuationType)){
	    			pur.setPlanningArea(pur.getStrdPlanRoomArea());
	    			pur.setPreArea(pur.getRoomArea());
	    			pur.setActualArea(pur.getStrdActualRoomArea());
	    		}
	    		
	    		BigDecimal roomArea=FDCHelper.ZERO;
	    		BigDecimal buildingArea=FDCHelper.ZERO;
//	    		if(SellTypeEnum.LocaleSell.equals(sellType)){	
//	    			buildingArea=pur.getStrdActualBuildingArea();
//	    			roomArea=pur.getStrdActualRoomArea();
//	    		} else if(SellTypeEnum.PreSell.equals(sellType)){
//	    			buildingArea=pur.getBulidingArea();
//	    			roomArea=pur.getRoomArea();
//	    		}else if(SellTypeEnum.PlanningSell.equals(sellType)){
//	    			buildingArea=pur.getStrdPlanBuildingArea();
//	    			roomArea=pur.getStrdPlanRoomArea();
//	    		}
	    		roomArea =pur.getRoomArea();
	    		buildingArea=pur.getBulidingArea();
	    		
	    		pur.getPurAgioEntry().clear();
	    		PurAgioEntryInfo entryInfo = new PurAgioEntryInfo();
				entryInfo.setAmount(pur.getStrdTotalAmount().subtract(pur.getDealTotalAmount()));
				entryInfo.setSeq(0);
				pur.getPurAgioEntry().add(entryInfo);
				
	    		if(pur.getContractTotalAmount()==null){
	    			AgioParam currAgioParam=new AgioParam();
			    	AgioEntryCollection agioEntryColl = new AgioEntryCollection(); 
			    	currAgioParam.setPriceAccountType(PriceAccountTypeEnum.StandSetPrice);
					currAgioParam.setBasePriceSell(pur.isIsBasePriceSell());
					
					agioEntryColl.add(entryInfo);
					currAgioParam.setAgios(agioEntryColl);
					
			    	PurchaseParam purParam = SHEManageHelper.getAgioParam(currAgioParam, pur.getRoom(), 
							 sellType,valuationType,false,roomArea,buildingArea,pur.getStrdRoomPrice(),pur.getStrdBuildingPrice(),pur.getStrdTotalAmount(),null, null, null ,SpecialAgioEnum.DaZhe, FDCHelper.ONE_HUNDRED,null);
					if(purParam!=null) {
						pur.setAgioDesc(purParam.getAgioDes());
						pur.setLastAgio(purParam.getFinalAgio());
						pur.setDealTotalAmount(purParam.getDealTotalAmount());
						pur.setDealBuildPrice(purParam.getDealBuildPrice());
						pur.setDealRoomPrice(purParam.getDealRoomPrice());
						pur.setContractTotalAmount(purParam.getContractTotalAmount());
						pur.setContractBuildPrice(purParam.getContractBuildPrice());
						pur.setContractRoomPrice(purParam.getContractRoomPrice());
					}
	    		}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		String customerNames="";
		String customerPhone="";
		String recommendeds="";
		
		PurCustomerEntryCollection delCol=new PurCustomerEntryCollection();
		for(int i=0;i<pur.getPurCustomerEntry().size();i++){
			PurCustomerEntryInfo info = pur.getPurCustomerEntry().get(i);
			SHECustomerInfo sheCI = info.getCustomer();
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("customer.phone");
			sel.add("customer.name");
			
			try {
				if(info.getId()!=null){
					sheCI=PurCustomerEntryFactory.getLocalInstance(ctx).getPurCustomerEntryInfo(new ObjectUuidPK(info.getId()),sel).getCustomer();
				}else if(info.getCustomer()==null){
					delCol.add(pur.getPurCustomerEntry().get(i));
					continue;
				}else{
					sheCI=SHECustomerFactory.getLocalInstance(ctx).getSHECustomerInfo(new ObjectUuidPK(info.getCustomer().getId()));
				}
				EntityViewInfo view =getPermitCustomerView(ctx,pur.getSellProject(),ContextUtil.getCurrentUserInfo(ctx));
				EntityViewInfo newView=(EntityViewInfo)view.clone();
				FilterInfo filter = new FilterInfo();		
				filter.getFilterItems().add(new FilterItemInfo("name",sheCI.getName()));
				filter.getFilterItems().add(new FilterItemInfo("phone",sheCI.getPhone()));
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",SHEManageHelper.getParentSellProject(ctx, pur.getSellProject()).getId().toString()));
				newView.getFilter().mergeFilter(filter, "and");
				SHECustomerCollection col = SHECustomerFactory.getLocalInstance(ctx).getSHECustomerCollection(newView);
				if(col.size()>1||col.size()==0){
					delCol.add(pur.getPurCustomerEntry().get(i));
					continue;
				}else{
					pur.getPurCustomerEntry().get(i).setCustomer(col.get(0));
				}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}
		for(int i=0;i<delCol.size();i++){
			pur.getPurCustomerEntry().remove(delCol.get(i));
		}
		if(pur.getPurCustomerEntry().size()>1){
			PurCustomerEntryInfo scinfo=pur.getPurCustomerEntry().get(0);
			pur.getPurCustomerEntry().clear();
			pur.getPurCustomerEntry().add(scinfo);
		}
		for(int i=0;i<pur.getPurCustomerEntry().size();i++){
			PurCustomerEntryInfo info = pur.getPurCustomerEntry().get(i);
			SHECustomerInfo sheCI = info.getCustomer();
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("customer.certificateNumber");
			sel.add("customer.certificateType.*");
			sel.add("customer.mailAddress");
			sel.add("customer.name");
			sel.add("customer.phone");
			sel.add("customer.tel");
			
			SelectorItemCollection cusSel=new SelectorItemCollection();
			cusSel.add("certificateNumber");
			cusSel.add("certificateType.*");
			cusSel.add("mailAddress");
			cusSel.add("name");
			cusSel.add("phone");
			cusSel.add("tel");
			try {
				if(info.getId()!=null){
					sheCI=PurCustomerEntryFactory.getLocalInstance(ctx).getPurCustomerEntryInfo(new ObjectUuidPK(info.getId()),sel).getCustomer();
				}else{
					sheCI=SHECustomerFactory.getLocalInstance(ctx).getSHECustomerInfo(new ObjectUuidPK(info.getCustomer().getId()));
				}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			}
			String phone="";
			String mob="";
			String tel="";
			String recommended="";
			if(sheCI.getPhone()!=null&&!sheCI.getPhone().trim().equals("")){
				mob=sheCI.getPhone()+"(M)";
			}
			if(sheCI.getTel()!=null&&!sheCI.getTel().trim().equals("")){
				tel=sheCI.getTel()+"(T)";
			}
			if(!mob.equals("")&&!tel.equals("")){
				phone=mob+","+tel;
			}else if(!mob.equals("")){
				phone=mob;
			}else if(!tel.equals("")){
				phone=tel;
			}
			if(i==pur.getPurCustomerEntry().size()-1){
				customerNames=customerNames+sheCI.getName();
				customerPhone=customerPhone+phone;
				recommendeds=recommendeds+recommended;
			}else{
				customerNames=customerNames+sheCI.getName()+";";
				customerPhone=customerPhone+phone+";";
				if(sheCI.getRecommended()!=null&&!sheCI.getRecommended().trim().equals("")){
					recommendeds=recommended+";";
				}
			}
			info.setTel(sheCI.getTel());
			info.setPhone(sheCI.getPhone());
			info.setName(sheCI.getName());
			info.setAddress(sheCI.getMailAddress());
			info.setCertificate(sheCI.getCertificateType());
			info.setCertificateNumber(sheCI.getCertificateNumber());
		}
		pur.setCustomerNames(customerNames);
		pur.setCustomerPhone(customerPhone);
		pur.setRecommended(recommendeds);
		
		String saleManNames="";
		
		PurSaleManEntryCollection delManCol=new PurSaleManEntryCollection();
		for(int i=0;i<pur.getPurSaleManEntry().size();i++){
			if(pur.getPurSaleManEntry().get(i).getUser()==null){
				delManCol.add(pur.getPurSaleManEntry().get(i));
				continue;
			}
		}
		for(int i=0;i<delManCol.size();i++){
			pur.getPurSaleManEntry().remove(delManCol.get(i));
		}
		if(pur.getPurSaleManEntry().size()>1){
			PurSaleManEntryInfo scinfo=pur.getPurSaleManEntry().get(0);
			pur.getPurSaleManEntry().clear();
			pur.getPurSaleManEntry().add(scinfo);
		}
		for(int i=0;i<pur.getPurSaleManEntry().size();i++){
			UserInfo user=pur.getPurSaleManEntry().get(i).getUser();
			try {
				if(pur.getPurSaleManEntry().get(i).getId()!=null){
					user=PurSaleManEntryFactory.getLocalInstance(ctx).getPurSaleManEntryInfo("select user.name from where id='"+pur.getPurSaleManEntry().get(i).getId()+"'").getUser();
				}else{
					user=UserFactory.getLocalInstance(ctx).getUserInfo(new ObjectUuidPK(pur.getPurSaleManEntry().get(i).getUser().getId()));
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(user!=null){
				if(i==pur.getPurSaleManEntry().size()-1){
					saleManNames=saleManNames+user.getName();
				}else{
					saleManNames=saleManNames+user.getName()+";";
				}
			}
		}
		pur.setSaleManNames(saleManNames);
		
		if(pur.getPurSaleManEntry().size()==0){
			PurSaleManEntryInfo entry=new PurSaleManEntryInfo();
			entry.setUser(ContextUtil.getCurrentUserInfo(ctx));
			pur.getPurSaleManEntry().add(entry);
			pur.setSaleManNames(ContextUtil.getCurrentUserInfo(ctx).getName());
		}
		
		BigDecimal loanAmount = FDCHelper.ZERO;
		BigDecimal afAmount = FDCHelper.ZERO;

		PurPayListEntryCollection delPayCol=new PurPayListEntryCollection();
		for (int i = 0; i < pur.getPurPayListEntry().size(); i++) {
			MoneyDefineInfo moneyName =pur.getPurPayListEntry().get(i).getMoneyDefine();
			
			try {
				if(pur.getPurPayListEntry().get(i).getId()!=null){
					moneyName=PurPayListEntryFactory.getLocalInstance(ctx).getPurPayListEntryInfo("select moneyDefine.moneyType,amount from where id='"+pur.getPurPayListEntry().get(i).getId()+"'").getMoneyDefine();
				}else if(pur.getPurPayListEntry().get(i).getMoneyDefine()==null){
					delPayCol.add(pur.getPurPayListEntry().get(i));
					continue;
				}else{
					 moneyName = MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineInfo(new ObjectUuidPK(pur.getPurPayListEntry().get(i).getMoneyDefine().getId()));
				}
				BigDecimal amount = pur.getPurPayListEntry().get(i).getAppAmount();
				if (amount == null) {
					amount = FDCHelper.ZERO;
				}
				if (moneyName != null) {
					if (moneyName.getMoneyType().equals(MoneyTypeEnum.LoanAmount)) {
						loanAmount = loanAmount.add(amount);
					} else if (moneyName.getMoneyType().equals(MoneyTypeEnum.AccFundAmount)) {
						afAmount = afAmount.add(amount);
					}
				}
				pur.getPurPayListEntry().get(i).setCurrency(SHEManageHelper.getCurrencyInfo(ctx));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		pur.setLoanAmount(loanAmount);
		pur.setAccFundAmount(afAmount);
		
		for(int i=0;i<delPayCol.size();i++){
			pur.getPurPayListEntry().remove(delPayCol.get(i));
		}
		super.submit(pur, ctx);
	}
	protected  EntityViewInfo  getPermitCustomerView(Context ctx,SellProjectInfo sellPorjct,UserInfo userInfo) throws EASBizException, BOSException{
		SaleOrgUnitInfo orgUnitInfo = ContextUtil.getCurrentSaleUnit(ctx);
		
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();		
		if(sellPorjct==null) {
			String permitSaleIdStr = MarketingUnitFactory.getLocalInstance(ctx).getPermitSaleManIdSql(userInfo,sellPorjct);
			String permitProIdStr = MarketingUnitFactory.getLocalInstance(ctx).getPermitSellProjectIdSql(userInfo);
			filter.getFilterItems().add(new FilterItemInfo("createUnit.id",orgUnitInfo.getId().toString()));
			
			filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id",permitSaleIdStr,CompareType.INNER));			
			//共享给营销顾问的客户
			String saleSaleIdStr = "select FCustomerID from T_SHE_ShareProperty where FUserID in ("+permitSaleIdStr+")";
			filter.getFilterItems().add(new FilterItemInfo("id",saleSaleIdStr,CompareType.INNER));			
			//共享给项目的客户
			String sellProIdStr = "select FCustomerID from T_SHE_ShareSellProject where FSellProjectID in ("+permitProIdStr+") ";
			filter.getFilterItems().add(new FilterItemInfo("id",sellProIdStr,CompareType.INNER));
			
			filter.setMaskString("#0 and (#1 or #2 or #3)");
		}else{
			sellPorjct=SHEManageHelper.getParentSellProject(ctx,sellPorjct);
			
			String permitSaleIdStr = MarketingUnitFactory.getLocalInstance(ctx).getPermitSaleManIdSql(userInfo,sellPorjct);
			//permitSaleIdStr += " and FSellProjectID = '"+sellPorjct.getId()+"' ";
			
			filter.getFilterItems().add(new FilterItemInfo("createUnit.id",orgUnitInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellPorjct.getId().toString()));
			
			filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id",permitSaleIdStr,CompareType.INNER));			
			//共享给营销顾问的客户
			String saleSaleIdStr = "select FCustomerID from T_SHE_ShareProperty where FUserID in ("+permitSaleIdStr+")";
			filter.getFilterItems().add(new FilterItemInfo("id",saleSaleIdStr,CompareType.INNER));			
			//共享给项目的客户
			String sellProIdStr = "select FCustomerID from T_SHE_ShareSellProject where FSellProjectID = '"+sellPorjct.getId()+"' ";
			filter.getFilterItems().add(new FilterItemInfo("id",sellProIdStr,CompareType.INNER));
			
			filter.setMaskString("#0 and ((#1 and (#2 or #3)) or #4)  ");			
		}
		viewInfo.setFilter(filter);
		return viewInfo;
	}
	protected void verifyAddNewRoom(RoomInfo room,boolean isIsBasePriceSell,boolean isQuitRoomAudit,boolean isChangeRoomAuidt,boolean isChangeAreaAuidt,Context ctx) throws TaskExternalException, EASBizException, BOSException {
    	if(room==null) return;
    	
    	if (RoomSellStateEnum.Init.equals(room.getSellState())) {
    		throw new TaskExternalException("房间"+room.getName()+"没有开盘！");
		}
    	if (RoomSellStateEnum.KeepDown.equals(room.getSellState())) {
    		throw new TaskExternalException("房间"+room.getName()+"已经被预留！");
		}
    	if (room.isIsMortagaged()) {
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
    		filter.getFilterItems().add(new FilterItemInfo("isSell", new Boolean(false)));
    		filter.getFilterItems().add(new FilterItemInfo("mortagageState", MortagageEnum.AUDITTED_VALUE));
    		
    		if(MortagageControlFactory.getLocalInstance(ctx).exists(filter)){
    			throw new TaskExternalException("房间"+room.getName()+"已被抵押，需解除抵押后才可销售！");
    		}
		}
    	if (room.getStandardTotalAmount() == null || room.getStandardTotalAmount().compareTo(FDCHelper.ZERO) == 0) {
    		throw new TaskExternalException("房间"+room.getName()+"尚未定价，请选择其它房间操作！");
		}
    	if (HousePropertyEnum.Attachment.equals(room.getHouseProperty())) {
    		throw new TaskExternalException("房间"+room.getName()+"为附属房产不能单独销售！");
		}
    	if(isQuitRoomAudit&&ChangeStateEnum.QuitRoom.equals(room.getChangeState())){
    		throw new TaskExternalException("房间"+room.getName()+"退房后必须进行价格审核！");
    	}
    	if(isChangeRoomAuidt&&ChangeStateEnum.ChangeRoom.equals(room.getChangeState())){
    		throw new TaskExternalException("房间"+room.getName()+"换房后必须进行价格审核！");
    	}
		if(isChangeAreaAuidt&&(RoomPreChangeStateEnum.CHANGE.equals(room.getPreChangeState())||
				RoomPreChangeStateEnum.CHANGE.equals(room.getPlanChangeState())||RoomPreChangeStateEnum.CHANGE.equals(room.getActChangeState()))){
			throw new TaskExternalException("房间"+room.getName()+"面积变更后房间必须进行价格审核！");
		}
    	if(isIsBasePriceSell){	
    		if(room.getBaseStandardPrice()==null) {
    			throw new TaskExternalException("已启用强制底价控制参数，房间"+room.getName()+"总价底价不存在，请检查！");
    		}
    	}
	}
}
