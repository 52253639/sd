package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomLoanStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.SellOrderRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SellOrderRoomEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.LocaleUtils;

public class SellStatRoomRptNewFacadeControllerBean extends AbstractSellStatRoomRptNewFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.SellStatRoomRptNewFacadeControllerBean");
   
    protected Map _getSellStatRoomData(Context ctx, Map paramMap)throws BOSException, EASBizException
    {
    	Map result = new HashMap();
    	//认购单信息
    	try {
    		result = getBaseMap(ctx,paramMap);
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}   	
        return result;
    }   

    protected Map getBaseMap(Context ctx, Map paramMap) throws EASBizException, BOSException, SQLException
    {
    	RoomDisplaySetting setting = new RoomDisplaySetting(ctx);
    	Map result = new HashMap();
        Map baseMap = new HashMap(); 
        Map dingJinMap = new HashMap();
        Map huikuanMap = new HashMap();
        List list = new ArrayList();
        List dingJinList = new ArrayList();
        List huiKuanList = new ArrayList();
        List statelist = new ArrayList();
    	Map stateMap = new HashMap();
		//补差款
		List compentList = new ArrayList();
		Map compentMap = new HashMap();
        //获取满足条件的认购单
//        Map purchaseMap = ProductTypeSellStateFacadeControllerBean.getValidPurchaseIdSet(ctx, paramMap);
        String sql = ProductTypeSellStateFacadeControllerBean.getValidPurchaseIdSetNew(ctx, paramMap);
//        Set validPurchaseIds = (Set)purchaseMap.get("purchase");
//        if(validPurchaseIds.size()==0)return baseMap;
        
        String fnameloc = "FName_" + LocaleUtils.getLocaleString(ctx.getLocale());
        
        /**
    	 * 是否包含附属房产
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	builder.appendSql("select purID,roomID,projectName,buildingName,unit,number,roomNo,customerNames,customerPhones,payType,salesMan, ");
    	builder.appendSql("toPrePurchaseDate,toPurchaseDate,signDate,baseBuildPrice,baseRoomPrice,isCalByRoomArea, ");
    	builder.appendSql("isBaseAudit,buildPrice,roomPrice,standAmount,dealBuildPrice,dealRoomPrice,dealAmount,fitmentAmount,isFisToContract,contractAmount, ");
    	builder.appendSql("sellType,compent,buildingArea,roomArea,actBuildArea,actRoomArea,buildProperty,productType,direction, ");
    	builder.appendSql("roomModel,modelType,sight,apportionArea,balconyArea,guardenArea,carbarnArea,useArea,flatArea, ");
    	builder.appendSql("serialNumber,houseProperty,roomForm,productDetail,roomJoinState,roomBookState,roomLoanState, ");
//    	builder.appendSql("roomCompensateState, orderDate,compentAmount from ( ");
    	builder.appendSql("roomCompensateState,compentAmount,subArea, fitStd from ( ");  
    	
    	builder.appendSql("select pur.fid purID,room.fid roomID,selPrj."+fnameloc+" projectName,building."+fnameloc+" buildingName,buildUnit."+fnameloc+" unit, \n");
    	builder.appendSql("room.fDisplayName number,room.FRoomNo roomNo,pur.FCustomerNames customerNames,pur.FCustomerPhones customerPhones, \n");
    	builder.appendSql("payType."+fnameloc+" payType,userMan."+fnameloc+" salesMan,pur.FPrePurchaseDate toPrePurchaseDate, \n");
    	builder.appendSql("pur.fpurchaseDate toPurchaseDate,pur.FToSignDate signDate, \n");
    	builder.appendSql("room.FBaseBuildingPrice baseBuildPrice,room.FBaseRoomPrice baseRoomPrice,room.FIsCalByRoomArea isCalByRoomArea, \n");
    	builder.appendSql("room.FIsBasePriceAudited isBaseAudit,room.FBuildPrice buildPrice,room.FRoomPrice roomPrice, \n");
    	builder.appendSql("room.FStandardTotalAmount standAmount,pur.FDealBuildPrice dealBuildPrice,pur.FDealRoomPrice dealRoomPrice, \n");
    	builder.appendSql("pur.FDealAmount dealAmount,pur.FFitmentAmount fitmentAmount,pur.FIsFitmentToContract isFisToContract,pur.FDealAmount contractAmount,pur.FSellType sellType,pur.FAreaCompensateAmount compent, \n");
    	builder.appendSql("room.FBuildingArea buildingArea,room.FRoomArea roomArea,room.FActualBuildingArea actBuildArea, \n");
    	builder.appendSql("room.FActualRoomArea actRoomArea,buildpro."+fnameloc+" buildProperty,proType."+fnameloc+" productType, \n");
    	builder.appendSql("direction."+fnameloc+" direction,roomModel."+fnameloc+" roomModel,modelType."+fnameloc+" modelType,sight."+fnameloc+" sight, \n");
    	builder.appendSql("room.FApportionArea apportionArea,room.FBalconyArea balconyArea,room.FGuardenArea guardenArea, \n");
    	builder.appendSql("room.FCarbarnArea carbarnArea,room.FUseArea useArea,room.FFlatArea flatArea, \n");
    	builder.appendSql("room.FSerialNumber serialNumber,room.FHouseProperty houseProperty,roomForm."+fnameloc+" roomForm, productDetail."+fnameloc+" productDetail, \n");
    	builder.appendSql("room.FRoomJoinState roomJoinState,room.FRoomBookState roomBookState,room.FRoomLoanState roomLoanState, room.FRoomCompensateState roomCompensateState, \n");
    	builder.appendSql("com.FCompensateAmount compentAmount,subArea."+fnameloc+" subArea, decoraStd."+fnameloc+" fitStd \n");
//    	builder.appendSql("sellorder.FOrderDate orderDate,com.FCompensateAmount compentAmount \n");
    	builder.appendSql("from t_she_purchase pur  \n");
    	builder.appendSql("left join t_pm_user userMan on pur.FSalesmanID = userMan.fid \n");
    	builder.appendSql("left join t_she_room room on room.fid = pur.froomid \n");
    	builder.appendSql("left join t_she_building building on room.fbuildingid = building.fid \n");
    	builder.appendSql("left join t_she_sellProject selPrj on building.fsellprojectid = selPrj.fid \n");
    	builder.appendSql("left join t_she_buildingproperty buildpro on room.FBuildingPropertyID = buildpro.fid \n");
    	builder.appendSql("left join t_fdc_producttype proType on room.FProductTypeID = proType.fid \n");
    	builder.appendSql("left join t_she_hopeddirection direction on room.FDirectionID = direction.fid \n");
    	builder.appendSql("left join t_she_roommodel roomModel on room.FRoomModelID = roomModel.fid \n");
    	builder.appendSql("left join T_SHE_RoomModelType modelType on roomModel.FRoomModelTypeID = modelType.fid \n");
    	builder.appendSql("left join t_she_roomForm roomForm on room.FRoomFormID = roomForm.fid \n");
    	builder.appendSql("left join T_SHE_ProductDetial productDetail on room.FProductDetailID = productDetail.fid \n");
    	builder.appendSql("left join T_SHE_SHEPayType payType on pur.FPayTypeID = payType.fid \n");   	
    	builder.appendSql("left join t_she_buildingUnit buildUnit on room.FBuildUnitID = buildUnit.fid \n");
    	builder.appendSql("left join t_she_sightrequirement sight on room.FSightID = sight.fid \n");
    	//当一个推盘多次的时候查推盘时间会造成多条除推盘时间不同其他都相同的重复数据
//    	builder.appendSql("left join T_SHE_SellOrderRoomEntry orderEntry on orderEntry.FRoomID = room.fid \n");
//    	builder.appendSql("left join t_she_sellOrder sellorder on orderEntry.Fheadid = sellorder.fid \n");
    	builder.appendSql("left join T_SHE_RoomAreaCompensate com on room.fid = com.froomid \n");
//    	
//    	builder.appendSql("left join T_SHE_PurchaseRoomAttachEntry attach on pur.fid = attach.fheadid \n");
//    	builder.appendSql("left join T_SHE_RoomAttachmentEntry roomAttach on attach.FAttachmentEntryID = roomAttach.fid \n");
    	
    	/**
    	 * 提出分区字段在外面，为分区字段提供数据
    	 * update by renliang at 2011-1-12
    	 */
    	builder.appendSql("left join t_she_subarea subArea on subArea.Fid = building.fsubareaid \n");
    	
    	/**
    	 * 销售明细表增加一列：装修标准
    	 * update by ye_liu at 2011-04-22
    	 */
    	builder.appendSql("left join T_SHE_DecorationStandare decoraStd on room.FDecoraStdID = decoraStd.fid \n");
    	
    	/**
    	 * 获取所选的树节点   	
    	 * 所选的树节点不能为空，客户端维护，如果为空则进行设置当前组织
    	 */
    	appendSelectedNodeFilter(builder, paramMap,ctx);
    	builder.appendSql(" and room.fhouseproperty in ('NoAttachment') and ");
		// builder.appendParam("pur.fid", validPurchaseIds.toArray());
		builder.appendSql("pur.FID in (" + sql + ")");
    	//是否包含附属房产
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n union \n");
        	
        	builder.appendSql("select pur.fid purID,room.fid roomID,selPrj."+fnameloc+" projectName,building."+fnameloc+" buildingName,buildUnit."+fnameloc+" unit, \n");
        	builder.appendSql("room.fDisplayName number,room.FRoomNo roomNo,pur.FCustomerNames customerNames,pur.FCustomerPhones customerPhones, \n");
        	builder.appendSql("payType."+fnameloc+" payType,userMan."+fnameloc+" salesMan,pur.FPrePurchaseDate toPrePurchaseDate, \n");
        	builder.appendSql("pur.fpurchaseDate toPurchaseDate,pur.FToSignDate signDate, \n");
        	builder.appendSql("room.FBaseBuildingPrice baseBuildPrice,room.FBaseRoomPrice baseRoomPrice,room.FIsCalByRoomArea isCalByRoomArea, \n");
        	builder.appendSql("room.FIsBasePriceAudited isBaseAudit,room.FBuildPrice buildPrice,room.FRoomPrice roomPrice, \n");
        	builder.appendSql("room.FStandardTotalAmount standAmount,pur.FDealBuildPrice dealBuildPrice,pur.FDealRoomPrice dealRoomPrice, \n");
        	builder.appendSql("pur.FDealAmount dealAmount,pur.FFitmentAmount fitmentAmount,pur.FIsFitmentToContract isFisToContract,attach.FMergeAmount contractAmount,pur.FSellType sellType,pur.FAreaCompensateAmount compent, \n");
        	builder.appendSql("room.FBuildingArea buildingArea,room.FRoomArea roomArea,room.FActualBuildingArea actBuildArea, \n");
        	builder.appendSql("room.FActualRoomArea actRoomArea,buildpro."+fnameloc+" buildProperty,proType."+fnameloc+" productType, \n");
        	builder.appendSql("direction."+fnameloc+" direction,roomModel."+fnameloc+" roomModel,modelType."+fnameloc+" modelType,sight."+fnameloc+" sight, \n");
        	builder.appendSql("room.FApportionArea apportionArea,room.FBalconyArea balconyArea,room.FGuardenArea guardenArea, \n");
        	builder.appendSql("room.FCarbarnArea carbarnArea,room.FUseArea useArea,room.FFlatArea flatArea, \n");
        	builder.appendSql("room.FSerialNumber serialNumber,room.FHouseProperty houseProperty,roomForm."+fnameloc+" roomForm, productDetail."+fnameloc+" productDetail, \n");
        	builder.appendSql("room.FRoomJoinState roomJoinState,room.FRoomBookState roomBookState,room.FRoomLoanState roomLoanState, room.FRoomCompensateState roomCompensateState, \n");
        	builder.appendSql("com.FCompensateAmount compentAmount,subArea."+fnameloc+" subArea, decoraStd."+fnameloc+" fitStd \n");
//        	builder.appendSql("sellorder.FOrderDate orderDate,com.FCompensateAmount compentAmount \n");
        	builder.appendSql("from T_SHE_PurchaseRoomAttachEntry attach \n");
        	builder.appendSql("left join T_SHE_RoomAttachmentEntry roomAttach on attach.FAttachmentEntryID = roomAttach.fid \n");
        	builder.appendSql("left join t_she_purchase pur on attach.fheadid = pur.fid \n");
        	builder.appendSql("left join t_she_room room on roomAttach.froomid = room.fid \n");
        	builder.appendSql("left join t_she_building building on room.fbuildingid = building.fid \n");
        	builder.appendSql("left join t_she_sellProject selPrj on building.fsellprojectid = selPrj.fid  \n");
        	builder.appendSql("left join t_she_buildingproperty buildpro on room.FBuildingPropertyID = buildpro.fid \n");
        	builder.appendSql("left join t_fdc_producttype proType on room.FProductTypeID = proType.fid \n");
        	builder.appendSql("left join t_she_hopeddirection direction on room.FDirectionID = direction.fid \n");
        	builder.appendSql("left join t_she_roommodel roomModel on room.FRoomModelID = roomModel.fid \n");
        	builder.appendSql("left join T_SHE_RoomModelType modelType on roomModel.FRoomModelTypeID = modelType.fid \n");
        	builder.appendSql("left join t_she_roomForm roomForm on room.FRoomFormID = roomForm.fid \n");
        	builder.appendSql("left join T_SHE_ProductDetial productDetail on room.FProductDetailID = productDetail.fid \n");
        	builder.appendSql("left join T_SHE_SHEPayType payType on pur.FPayTypeID = payType.fid \n");   	
        	builder.appendSql("left join t_she_buildingUnit buildUnit on room.FBuildUnitID = buildUnit.fid \n");
        	builder.appendSql("left join t_she_sightrequirement sight on room.FSightID = sight.fid \n");
//        	builder.appendSql("left join T_SHE_SellOrderRoomEntry orderEntry on orderEntry.FRoomID = room.fid \n");
//        	builder.appendSql("left join t_she_sellOrder sellorder on orderEntry.Fheadid = sellorder.fid \n");
        	builder.appendSql("left join t_pm_user userMan on pur.FSalesmanID = userMan.fid \n");   
        	builder.appendSql("left join T_SHE_RoomAreaCompensate com on room.fid = com.froomid \n");
        	/**
        	 * 提出分区字段在外面，为分区字段提供数据
        	 * update by renliang at 2011-1-12
        	 */
        	builder.appendSql("left join t_she_subarea subArea on subArea.Fid = building.fsubareaid \n");
        	
        	/**
        	 * 销售明细表增加一列：装修标准
        	 * update by ye_liu at 2011-04-22
        	 */
        	builder.appendSql("left join T_SHE_DecorationStandare decoraStd on room.FDecoraStdID = decoraStd.fid \n");
        	
            appendSelectedNodeFilter(builder, paramMap,ctx);
            builder.appendSql(" and ");
//        	builder.appendParam("pur.fid", validPurchaseIds.toArray());
            builder.appendSql("pur.FID in (" + sql + ")");
    	}
    	builder.appendSql(") a "); 
    	builder.appendSql("order by projectName,buildingName,unit,number");    	    	         	
    	
//    	builder.appendSql("\n  order by selPrj."+fnameloc+",building."+fnameloc+",buildUnit."+fnameloc+",room.fnumber");
    	
    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	Boolean includeOrder = (Boolean)paramMap.get("IncludeOrder");
    	while(rowSet.next())
    	{ 		
    		Object[] baseObj = new Object[56];
    		String purID = rowSet.getString("purID");
    		String roomID = rowSet.getString("roomID");
    		String projectName = rowSet.getString("projectName");
    		String buildingName = rowSet.getString("buildingName");
    		String unitName = rowSet.getString("unit");   		
    		String number = rowSet.getString("number");
    		String roomNo = rowSet.getString("roomNo");
    		String customerNames = rowSet.getString("customerNames");
    		String customerPhones = rowSet.getString("customerPhones");
    		String payType = rowSet.getString("payType");
    		String salesMan = rowSet.getString("salesMan");
    		String toPrePurchaseDate = rowSet.getString("toPrePurchaseDate");
    		Date prePurchaseDate = null;
    		if(toPrePurchaseDate!=null)
    		{
    			prePurchaseDate = FDCDateHelper.stringToDate(toPrePurchaseDate);
    		}  
    		String toPurchaseDate = rowSet.getString("toPurchaseDate");
    		Date purchaseDate = null;
    		if(toPurchaseDate!=null)
    		{
    			purchaseDate = FDCDateHelper.stringToDate(toPurchaseDate);
    		}
    		   		
    		String signDate = rowSet.getString("signDate");
    		BigDecimal baseBuildPrice = rowSet.getBigDecimal("baseBuildPrice");
    		BigDecimal baseRoomPrice = rowSet.getBigDecimal("baseRoomPrice");
    		boolean isBaseAudit = rowSet.getBoolean("isBaseAudit");
    		boolean isCalByRoomArea = rowSet.getBoolean("isCalByRoomArea");
    		BigDecimal buildPrice = rowSet.getBigDecimal("buildPrice");
    		BigDecimal roomPrice = rowSet.getBigDecimal("roomPrice");
    		BigDecimal standAmount = rowSet.getBigDecimal("standAmount");
    		BigDecimal dealBuildPrice = rowSet.getBigDecimal("dealBuildPrice");
    		BigDecimal dealRoomPrice = rowSet.getBigDecimal("dealRoomPrice");
    		BigDecimal dealAmount = rowSet.getBigDecimal("dealAmount");
    		BigDecimal fitmentAmount = rowSet.getBigDecimal("fitmentAmount");
    		fitmentAmount = fitmentAmount==null?new BigDecimal(0):fitmentAmount;
    		boolean isFisToContract = rowSet.getBoolean("isFisToContract");
    		BigDecimal contractAmount = rowSet.getBigDecimal("contractAmount");
    		contractAmount = contractAmount==null?new BigDecimal(0):contractAmount;
    		if(isFisToContract)
    		{
    			contractAmount = contractAmount.add(fitmentAmount);
    		}
    	
    		String sellType = rowSet.getString("sellType");
    		BigDecimal compent = rowSet.getBigDecimal("compent");
    		BigDecimal buildingArea = rowSet.getBigDecimal("buildingArea");
    		BigDecimal roomArea = rowSet.getBigDecimal("roomArea");
    		BigDecimal actBuildArea = rowSet.getBigDecimal("actBuildArea");   		
    		BigDecimal actRoomArea = rowSet.getBigDecimal("actRoomArea");
    		String buildProperty = rowSet.getString("buildProperty");
    		String productType = rowSet.getString("productType");
    		String direction = rowSet.getString("direction");
    		String roomModel = rowSet.getString("roomModel");
    		String modelType = rowSet.getString("modelType");
    		String sight = rowSet.getString("sight");
    		BigDecimal apportionArea = rowSet.getBigDecimal("apportionArea");
    		BigDecimal balconyArea = rowSet.getBigDecimal("balconyArea");
    		BigDecimal guardenArea = rowSet.getBigDecimal("guardenArea");
    		BigDecimal carbarnArea = rowSet.getBigDecimal("carbarnArea");
    		BigDecimal useArea = rowSet.getBigDecimal("useArea");
    		BigDecimal flatArea = rowSet.getBigDecimal("flatArea");
    		String serialNumber = rowSet.getString("serialNumber");
    		String houseProperty = rowSet.getString("houseProperty");
    		String roomForm = rowSet.getString("roomForm");
    		String productDetail = rowSet.getString("productDetail");  		 
    		String roomJoinState = rowSet.getString("roomJoinState");
    		String roomBookState=  rowSet.getString("roomBookState");
    		String roomLoanState=  rowSet.getString("roomLoanState");
    		String roomComState=  rowSet.getString("roomCompensateState");
//    		Date orderDate = rowSet.getDate("orderDate");
    		//应收补差
    		BigDecimal compentAmount = rowSet.getBigDecimal("compentAmount");
    		//新增加分区字段 by renliang at 2011-1-12
    		String subArea = rowSet.getString("subArea");
    		String fitStd = rowSet.getString("fitStd");
    		baseObj[0] = roomID;
    		baseObj[1]= projectName;
    		baseObj[2]= buildingName;
    		baseObj[3]= unitName;
    		baseObj[4]= number;
    		baseObj[5]= roomNo;
    		baseObj[6]= customerNames;
    		baseObj[7]= customerPhones;  
    		baseObj[8]= payType; 
    		baseObj[9]= salesMan; 
    		baseObj[10]= prePurchaseDate; 
    		baseObj[11]= purchaseDate; 
    		baseObj[12]= signDate; 
    		baseObj[13] = baseBuildPrice;
    		baseObj[14] = baseRoomPrice;
    		baseObj[15] = new Boolean(isBaseAudit);
    		baseObj[16] = buildPrice;
    		baseObj[17] = roomPrice;
    		baseObj[18] = standAmount;
    		if(HousePropertyEnum.ATTACHMENT_VALUE.equals(houseProperty))
    		{
    			baseObj[19] = null;
        		baseObj[20] = null;
        		baseObj[21] = null;
    		}else
    		{
    			baseObj[19] = dealBuildPrice;
        		baseObj[20] = dealRoomPrice;
        		baseObj[21] = dealAmount;
    		}
    		
    		baseObj[22] = contractAmount;
    		baseObj[23] = sellType;  
    		
    		baseObj[24]= buildingArea;
    		baseObj[25]= roomArea;
    		baseObj[26]= actBuildArea;
    		baseObj[27]= actRoomArea;
    		baseObj[28]= buildProperty;
    		baseObj[29]= productType;
    		baseObj[30]= direction;  
    		baseObj[31]= roomModel; 
    		baseObj[32]= sight; 
    		baseObj[33]= apportionArea; 
    		baseObj[34]= balconyArea; 
    		baseObj[35]= guardenArea; 
    		baseObj[36] = carbarnArea;
    		baseObj[37] = useArea;
    		baseObj[38] = flatArea;
    		baseObj[39] = serialNumber;
    		baseObj[40] = houseProperty;
    		baseObj[41] = roomForm;
    		baseObj[42] = productDetail;
    		baseObj[43] =  new Boolean(isCalByRoomArea);;
    		baseObj[44] = modelType;
//    		baseObj[45] = state;
    		baseObj[46] = roomJoinState;
    		baseObj[47] = roomBookState;
    		baseObj[48] = roomLoanState;
    		baseObj[49] = roomComState;
//    		baseObj[50] = orderDate;
    		baseObj[51] = purID;
    		baseObj[52] = compentAmount;
    		//new add subarea by renliang at 2011-1-12
    		baseObj[54] = subArea;
    		baseObj[55] = fitStd;
    		list.add(baseObj);
    	}   	  	  

    	baseMap.put("baseList", list); 
    	builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select pur.fid purID,pur.froomid roomID,roomSign.fstate signState,pur.ftopurchasedate topurchaseDate, \n");
    	builder.appendSql("roomSign.FSignDate signDate \n");
    	builder.appendSql("from t_she_purchase pur \n");
    	builder.appendSql("left join t_she_room room on pur.froomid = room.flastpurchaseid \n");
    	builder.appendSql("left join T_SHE_RoomSignContract roomSign on roomSign.fpurchaseid = pur.fid  \n");
    	builder.appendSql("where ");
//    	builder.appendParam("pur.fid", validPurchaseIds.toArray()); 	
    	builder.appendSql("pur.FID in (" + sql + ")");
    	rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
    	{
    		Object[] obj = new Object[5];   		
    		RoomSellStateEnum state = RoomSellStateEnum.Purchase; 
    		String purID = rowSet.getString("purID");
    		String roomID = rowSet.getString("roomID");
    		String signState = rowSet.getString("signState");
    		Date topurchaseDate = rowSet.getDate("topurchaseDate");
    		Date date = rowSet.getDate("signDate");
    		Date signDate = new Date();
    		if(FDCBillStateEnum.AUDITTED_VALUE.equals(signState) || FDCBillStateEnum.SUBMITTED_VALUE.equals(signState))
    		{
    			state = RoomSellStateEnum.Sign;
    			signDate = date;
    		}else 
    			{
    			
    			if(!includeOrder.booleanValue())
        		{
        				state = RoomSellStateEnum.Purchase;
        		}else
        		{
        			//如果计入统计的话，转认购日期为空说明是预定状态
    				if(topurchaseDate==null)
    				{
    					state = RoomSellStateEnum.PrePurchase;
    				}else
    				{
    					state = RoomSellStateEnum.Purchase;
    				}
        		}
    			signDate = null;
			}
    		obj[0] = roomID;
    		obj[1] = state;
    		obj[2] = signDate;
    		obj[3] = purID;
    		statelist.add(obj);
    	}
    	stateMap.put("statelistMap", statelist);
    	
    	builder = new FDCSQLBuilder(ctx);
    	//认购单定金类回款
    	builder.appendSql("select pur.fid purID,room.fid roomID, \n");
    	builder.appendSql("sum(paylist.factrevamount) actamount, \n");
    	builder.appendSql("sum(paylist.Fhastransferredamount) transAmount, \n");
    	builder.appendSql("sum(paylist.Fhasrefundmentamount) refundAmount, \n");
    	builder.appendSql("sum(paylist.Fhasadjustedamount) justAmount \n");
    	builder.appendSql("from t_she_purchase pur \n");
    	builder.appendSql("left join t_she_purchasepaylistentry payList on payList.fheadid = pur.fid \n");
    	//builder.appendSql("left join T_SHE_PurchaseElsePayListEntry elsepayList on elsepayList.fheadid = pur.fid \n");
    	builder.appendSql("left join t_she_moneyDefine money on payList.fmoneydefineid = money.fid \n");
    	builder.appendSql("left join t_she_room room on pur.froomid = room.fid \n"); 
    	if(includeOrder.booleanValue())
    	{
    		builder.appendSql("where room.fsellstate in ('PrePurchase', 'Purchase', 'Sign') and \n");
    	}
    	else
    	{
    		builder.appendSql("\n where room.fsellstate in ('Purchase', 'Sign') and \n");
    	}
    	builder.appendSql(" money.FMoneyType ='PurchaseAmount' and \n");
//    	builder.appendParam("pur.fid", validPurchaseIds.toArray());
    	builder.appendSql("pur.FID in (" + sql + ")");
    	builder.appendSql("\n group by pur.fid,room.fid \n");
    	builder.appendSql("order by room.fid");
    	
    	logger.info(builder.getTestSql());
    	rowSet = builder.executeQuery(ctx);
     	while(rowSet.next())
     	{
     		String roomID = rowSet.getString("roomID");
     		
     		//实收金额
     		BigDecimal revamount = rowSet.getBigDecimal("actamount")==null?new BigDecimal(0):rowSet.getBigDecimal("actamount");
     		//已转金额
     		BigDecimal transAmount = rowSet.getBigDecimal("transAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("transAmount");
     		//已退金额
     		BigDecimal refundAmount = rowSet.getBigDecimal("refundAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("refundAmount");
     		//已调金额
     		BigDecimal justAmount = rowSet.getBigDecimal("justAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("justAmount");
     		//已经金额=实收金额-已转金额-已退金额-已调金额
     		BigDecimal actamount = revamount.subtract(transAmount).subtract(refundAmount).subtract(justAmount);
     		dingJinMap.put(roomID, actamount);
     	}
     	
     	builder = new FDCSQLBuilder(ctx);
     	//认购单非定金类回款
     	builder.appendSql("select purID,roomID,projectID,sum(actamount) actamount,sum(transAmount) transAmount,sum(refundAmount) refundAmount,sum(justAmount) justAmount ");
     	builder.appendSql(",sum(elseActamount) elseActamount,sum(elseTransAmount) elseTransAmount,sum(elseRefundAmount) elseRefundAmount,sum(elseJustAmount) elseJustAmount ");
     	builder.appendSql("from (");     	
    	builder.appendSql("select pur.fid purID,room.fid roomID,project.fid projectID,room.fsellstate,money.FMoneyType, \n");
    	builder.appendSql("paylist.factrevamount actamount,paylist.Fhastransferredamount transAmount,paylist.Fhasrefundmentamount refundAmount, \n");
    	builder.appendSql("paylist.Fhasadjustedamount justAmount,0 elseActamount,0 elseTransAmount,0 elseRefundAmount,0 elseJustAmount \n");
    	builder.appendSql("from t_she_purchase pur \n");
    	builder.appendSql("left join t_she_sellproject project on pur.fsellprojectid = project.fid \n");
    	builder.appendSql("left join t_she_purchasepaylistentry payList on payList.fheadid = pur.fid \n");
    	builder.appendSql("left join t_she_moneyDefine money on payList.fmoneydefineid = money.fid \n");
    	builder.appendSql("left join t_she_room room on pur.froomid = room.fid \n"); 
    	builder.appendSql(" union ");  
    	builder.appendSql("select pur.fid purID,room.fid roomID,project.fid projectID,room.fsellstate,money.FMoneyType, \n");
    	builder.appendSql("0 actamount,0 transAmount,0 refundAmount,0 justAmount, \n");
    	builder.appendSql("elsepayList.factrevamount elseActamount,elsepayList.Fhastransferredamount elseTransAmount, \n");
    	builder.appendSql("elsepayList.Fhasrefundmentamount elseRefundAmount,elsepayList.Fhasadjustedamount elseJustAmount \n");
    	builder.appendSql("from t_she_purchase pur \n");
    	builder.appendSql("left join t_she_sellproject project on pur.fsellprojectid = project.fid \n");
    	builder.appendSql("left join T_SHE_PurchaseElsePayListEntry elsepayList on elsepayList.fheadid = pur.fid \n");
    	builder.appendSql("left join t_she_moneyDefine money on elsepayList.fmoneydefineid = money.fid \n");    	
    	builder.appendSql("left join t_she_room room on pur.froomid = room.fid \n"); 
    	builder.appendSql(") AA  ");
    	
    	//如果预定不计入销售统计的话，那么预定金也不计入销售回款
    	if(includeOrder.booleanValue())    	{
    		builder.appendSql("where AA.fsellstate in ('PrePurchase', 'Purchase', 'Sign') and \n");
    		builder.appendSql(" AA.FMoneyType !='PurchaseAmount' and \n");
    	}else{
    		builder.appendSql("\n where AA.fsellstate in ('Purchase', 'Sign') and \n");
    		builder.appendSql(" AA.FMoneyType !='PurchaseAmount' and AA.FMoneyType!='PreconcertMoney' and \n");
    	}
//    	builder.appendSql(" AA.FMoneyType !='PurchaseAmount' and \n");
//    	builder.appendParam("purID", validPurchaseIds.toArray());
    	builder.appendSql("purID in (" + sql + ")");
    	builder.appendSql("\n group by AA.purID,AA.roomID ,AA.projectID \n");
    	builder.appendSql("order by AA.roomID");
    	logger.info(builder.getTestSql());
    	rowSet = builder.executeQuery(ctx);
    	boolean isHouseMoney = false;
    	while(rowSet.next())
     	{
    		Object[] obj = new Object[5];
     		String purID = rowSet.getString("purID");
     		String roomID = rowSet.getString("roomID");
     		String projectID = rowSet.getString("projectID");
     		//实收金额
     		BigDecimal revamount = rowSet.getBigDecimal("actamount")==null?new BigDecimal(0):rowSet.getBigDecimal("actamount");
     		//已转金额
     		BigDecimal transAmount = rowSet.getBigDecimal("transAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("transAmount");
     		//已退金额
     		BigDecimal refundAmount = rowSet.getBigDecimal("refundAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("refundAmount");
     		//已调金额
     		BigDecimal justAmount = rowSet.getBigDecimal("justAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("justAmount");
     		//已经金额=实收金额-已转金额-已退金额-已调金额
     		BigDecimal actamount = revamount.subtract(transAmount).subtract(refundAmount).subtract(justAmount);
     		//实收金额
     		BigDecimal elseRevamount = rowSet.getBigDecimal("elseActamount")==null?new BigDecimal(0):rowSet.getBigDecimal("elseActamount");
     		//已转金额
     		BigDecimal elseTransAmount = rowSet.getBigDecimal("elseTransAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("elseTransAmount");
     		//已退金额
     		BigDecimal elseRefundAmount = rowSet.getBigDecimal("elseRefundAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("elseRefundAmount");
     		//已调金额
     		BigDecimal elseJustAmount = rowSet.getBigDecimal("elseJustAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("elseJustAmount");
     		//其他已经金额=实收金额-已转金额-已退金额-已调金额
     		BigDecimal elseActamount = elseRevamount.subtract(elseTransAmount).subtract(elseRefundAmount).subtract(elseJustAmount);
     		Map functionSetMap = setting.getFunctionSetMap();
     		//查看该项目下定金是否隶属于房款
			FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(projectID);			
			if(funcSet!=null) {
				isHouseMoney = ((Boolean)funcSet.getIsHouseMoney()).booleanValue();
			}
			if(isHouseMoney)
			{
				obj[0] = roomID;
				//如果是的话就把房间定金加上				
				if(dingJinMap.get(roomID)!=null)
				{
					BigDecimal amount = (BigDecimal)dingJinMap.get(roomID);
					obj[1] = amount.add(actamount);
					obj[2] = elseActamount;
		     		obj[4] = purID;
		     		huiKuanList.add(obj);
				}else
				{
					obj[0] = roomID;
		     		obj[1] = actamount;
		     		obj[2] = elseActamount;
		     		obj[4] = purID;
		     		huiKuanList.add(obj);
				}
			}else
			{
				obj[0] = roomID;
	     		obj[1] = actamount;
	     		obj[2] = elseActamount;
	     		obj[4] = purID;
	     		huiKuanList.add(obj);
			}
     	}
    	huikuanMap.put("huikuanMap", huiKuanList);
    	
     	//实收补差款
     	builder = new FDCSQLBuilder(ctx);
     	builder.appendSql("select pur.fid purID,room.fid roomID, \n");
    	builder.appendSql("sum(comRevList.factrevamount) actamount, \n");
    	builder.appendSql("sum(comRevList.Fhastransferredamount) transAmount, \n");
    	builder.appendSql("sum(comRevList.Fhasrefundmentamount) refundAmount, \n");
    	builder.appendSql("sum(comRevList.Fhasadjustedamount) justAmount \n");
    	builder.appendSql("from t_she_purchase pur \n");
    	builder.appendSql("left join t_she_room room on pur.froomid = room.fid \n"); 
    	builder.appendSql("left join T_SHE_RoomAreaCompensate com on room.fid = com.froomid \n");
    	builder.appendSql("left join T_SHE_AreaCompensateRevList comRevList on com.fid = comRevList.fheadid  \n");
    	builder.appendSql("where \n");
//    	builder.appendParam("pur.fid", validPurchaseIds.toArray());
    	builder.appendSql("pur.FID in (" + sql + ")");
    	builder.appendSql("\n group by pur.fid,room.fid \n");
    	builder.appendSql("order by room.fid");
    	
    	logger.info(builder.getTestSql());
    	rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
     	{
     		Object[] obj = new Object[5];
     		String purID = rowSet.getString("purID");
     		String roomID = rowSet.getString("roomID");
     		//实收金额
     		BigDecimal revamount = rowSet.getBigDecimal("actamount")==null?new BigDecimal(0):rowSet.getBigDecimal("actamount");
     		//已转金额
     		BigDecimal transAmount = rowSet.getBigDecimal("transAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("transAmount");
     		//已退金额
     		BigDecimal refundAmount = rowSet.getBigDecimal("refundAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("refundAmount");
     		//已调金额
     		BigDecimal justAmount = rowSet.getBigDecimal("justAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("justAmount");
     		//已经金额=实收金额-已转金额-已退金额-已调金额
     		BigDecimal actamount = revamount.subtract(transAmount).subtract(refundAmount).subtract(justAmount);
     		obj[0] = roomID;
     		obj[1] = actamount;
     		obj[2] = purID;
     		compentList.add(obj);
     	}
    	compentMap.put("compentList", compentList);
    	
     	result.put("baseData", baseMap);
     	result.put("statList", stateMap);
    	result.put("huiKuan", huikuanMap);
    	result.put("compent", compentMap);
    	return result;
    }
    
    protected void appendSelectedNodeFilter(FDCSQLBuilder builder, Map paramMap,Context ctx) throws EASBizException, BOSException
    {
    	
    	/**
    	 * 获取所选的树节点
    	 * 所选的树节点不能为空，客户端维护，如果为空则进行设置当前组织
    	 */
    	DefaultKingdeeTreeNode selNode = (DefaultKingdeeTreeNode)paramMap.get("selectNode");
    	//所选节点为单元时
    	if(selNode.getUserObject() instanceof BuildingUnitInfo)
    	{
    		BuildingUnitInfo unitInfo = (BuildingUnitInfo)selNode.getUserObject();
    		String unitId = unitInfo.getId().toString();
    		builder.appendSql("left join t_she_buildingunit unit on room.fbuildunitid = unit.fid \n");
    		builder.appendSql("where unit.fid = '" + unitId + "'");
    	}
    	else if(selNode.getUserObject() instanceof BuildingInfo)
    	{
    		BuildingInfo buildInfo = (BuildingInfo)selNode.getUserObject();
    		String buildId = buildInfo.getId().toString();
//    		builder.appendSql("inner join t_she_building building on building.fid = room.fbuildingid \n");
    		builder.appendSql("where building.fid = '" + buildId + "'");
    	}
    	else if(selNode.getUserObject() instanceof SubareaInfo)
    	{
    		SubareaInfo subAreaInfo = (SubareaInfo)selNode.getUserObject();
    		String subAreaId = subAreaInfo.getId().toString();
//    		builder.appendSql("inner join t_she_building building on building.fid = room.fbuildingid \n");
    	//	builder.appendSql("left join t_she_subarea subArea on subArea.Fid = building.fsubareaid \n");
    		builder.appendSql("where subArea.Fid = '" + subAreaId + "'");
    	}
    	else if(selNode.getUserObject() instanceof SellProjectInfo)
    	{
    		SellProjectInfo sellPrjInfo = (SellProjectInfo)selNode.getUserObject();
    		String selPrjId = sellPrjInfo.getId().toString();
//    		builder.appendSql("inner join t_she_building building on building.fid = room.fbuildingid \n");
//    		builder.appendSql("inner join t_she_sellproject selPrj on selPrj.Fid = building.fsellprojectid \n");
    		builder.appendSql("where selPrj.Fid = '" + selPrjId + "'");
    	}
    	else if(selNode.getUserObject() instanceof FullOrgUnitInfo)
    	{
    		UserInfo user = (UserInfo)paramMap.get("userInfo");
    		//如果是共享过来的项目需要在这过滤一下，如果外面项目没有加到营销单元里的，在这却计算了(这个会出现没有树节点的销售项目（2010-11-8之前的）)
//    		Set projectSet = MarketingUnitFactory.getLocalInstance(ctx).getPermitSellProjectIdSet(user);
    		
    		//计算出所有根节点的所有ProjectIds  pu_zhang 2010-11-8
    		Set projectSet = (Set) paramMap.get("childNodeProjectIdSet");
//    		FullOrgUnitInfo orgInfo = (FullOrgUnitInfo)selNode.getUserObject();
//    		String orgLongNumber = orgInfo.getLongNumber() + '%';
//    		builder.appendSql("inner join t_she_building building on building.fid = room.fbuildingid \n");
//    		builder.appendSql("inner join t_she_sellproject selPrj on selPrj.Fid = building.fsellprojectid \n");
//    		builder.appendSql("left join t_org_baseunit orgUnit on orgUnit.Fid = selPrj.Forgunitid \n");
//    		builder.appendSql("where orgUnit.Flongnumber like '" + orgLongNumber + "'");
    		if(projectSet!=null && projectSet.size()>0){
    			builder.appendParam(" where selPrj.fid", projectSet.toArray());
    		}else{
    			builder.appendParam(" where selPrj.fid", "nullnull");
    		}
    		
    	}
    }
}