package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProductTypeSellStateFacadeControllerBean extends AbstractProductTypeSellStateFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.ProductTypeSellStateFacadeControllerBean");
    
    /**
     * paramMap
     * 描述：
     * selectNode        = 所选树的节点，如果没有选，则设为当前组织
     * IncludeAttachment = 包括附属房产，为真是包括，为假时不包括
     * BuildArea         = 表示面积类型，为真是建筑面积，为假时为套内面积
     * BeginDate         = 开始日期
     * EndDate           = 结束日期
     * IncludeOrder      = 包括预订业务， 为真是包括，为假时不包括
     */
    protected Map _getProductSellDate(Context ctx, Map paramMap)throws BOSException, EASBizException
    {
    	Map result = new HashMap();
    	
    	/**
    	 * 获取已经启用的产品类型
    	 */
    	ProductTypeCollection proTypeCols = getProductTypeCols(ctx);
    	if(proTypeCols == null)
    		proTypeCols = new ProductTypeCollection();
    	
    	result.put("ProductType", proTypeCols);
    	
    	try {
    		Map tmpPrdToModel = new HashMap();
    		//获取报表前四行的值
			result.put("RptTotalSumData", getTotalRptDate(ctx, paramMap, tmpPrdToModel));
			
			//获取产品类型下的户型
			result.put("PrductToModel", tmpPrdToModel);
			
			//取已售房间的相关信息
			result.put("SelledRoomData", getSelledRoomData(ctx, paramMap));
			
			//取未售房间的相关信息
			result.put("UnselledRoomData", getUnselledRoomData(ctx, paramMap));
			
			//获取累计回款（没有区间限制）
			result.put("SumGathering", getSumGathering(ctx, paramMap));
			
			//获取没有时间过滤的补差金额
			result.put("SubAmtData", getSubAmtData(ctx, paramMap));
			
			//获取查询区间内相关数据（不含累计回款和补差款）
			result = getPeriodNoGatheringData(ctx, paramMap,result);
			
			//获取查询区间内的累计回款
//			result.put("PeriodGatheringData", getPeriodGatheringData(ctx, paramMap, validPurchaseIds));
			
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
    	
        return result;
    }
    /////////////////////////////////////////////共用方法部分-begin///////////////////////////////////////////////////////////
    
    /**
     * 共用方法部分
     */
    /**
     * 在过滤Sql中添加开始和结束日期
     */
    private static void appendDateFilter(FDCSQLBuilder builder, String filterName, Date beginDate, Date endDate)
    {
		if (beginDate != null)
		{
			builder.appendSql(" and " + filterName + " >= ? ");
			builder.addParam(FDCDateHelper.getSqlDate(beginDate));
		}
		if (endDate != null)
		{
			builder.appendSql(" and " + filterName + " < ? ");
			builder.addParam(FDCDateHelper.getNextDay(endDate));
		}
    }
    /**
     * 获取产品类型，结果集为空或者不为空，结果集不可能等于null;
     * @param ctx
     * @return
     * @throws BOSException
     */
    protected ProductTypeCollection getProductTypeCols(Context ctx) throws BOSException
    {
    	//获取已经启用的产品类型
    	EntityViewInfo viewType = new EntityViewInfo();
    	viewType.getSelector().add("id");
    	viewType.getSelector().add("number");
    	viewType.getSelector().add("name");
    	viewType.getSelector().add("isEnabled");
    	viewType.getSelector().add("description");
    	
    	FilterInfo filterType = new FilterInfo();
    	filterType.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	
    	SorterItemCollection sorterType = new SorterItemCollection();
    	sorterType.add(new SorterItemInfo("number"));
    	viewType.setSorter(sorterType);
    	
    	ProductTypeCollection cols = ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(viewType);
    	
    	return cols;
    }
    
    protected static void appendSelectedNodeFilter(FDCSQLBuilder builder, Map paramMap,Context ctx) throws EASBizException, BOSException
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
    		builder.appendSql("left join t_she_building building on building.fid = room.fbuildingid \n");
    		builder.appendSql("where building.fid = '" + buildId + "'");
    	}
    	else if(selNode.getUserObject() instanceof SubareaInfo)
    	{
    		SubareaInfo subAreaInfo = (SubareaInfo)selNode.getUserObject();
    		String subAreaId = subAreaInfo.getId().toString();
    		builder.appendSql("left join t_she_building building on building.fid = room.fbuildingid \n");
    		builder.appendSql("left join t_she_subarea subArea on subArea.Fid = building.fsubareaid \n");
    		builder.appendSql("where subArea.Fid = '" + subAreaId + "'");
    	}
    	else if(selNode.getUserObject() instanceof SellProjectInfo)
    	{
    		SellProjectInfo sellPrjInfo = (SellProjectInfo)selNode.getUserObject();
    		String selPrjId = sellPrjInfo.getId().toString();
    		builder.appendSql("left join t_she_building building on building.fid = room.fbuildingid \n");
    		builder.appendSql("left join t_she_sellproject selPrj on selPrj.Fid = building.fsellprojectid \n");
    		builder.appendSql("where selPrj.Fid = '" + selPrjId + "'");
    	}
    	else if(selNode.getUserObject() instanceof FullOrgUnitInfo)
    	{
    		//因为这里过滤出了能看到的项目所以组织的过滤不能要了。因为有组织的话会把共享的项目屏蔽掉
    		UserInfo user = (UserInfo)paramMap.get("userInfo");
    		Set projectSet = MarketingUnitFactory.getLocalInstance(ctx).getPermitSellProjectIdSet(user);
    		
    		/*如果销售项目id集合为空，则设置一个查询不到的值，解决查询报中断的问题
    		 * update by ye_liu
    		 * 2011-04-26 
    		 */
    		if(projectSet==null || projectSet.size()<=0){
    			projectSet.add("nullnullnull");
    		}
    		
//    		FullOrgUnitInfo orgInfo = (FullOrgUnitInfo)selNode.getUserObject();
//    		String orgLongNumber = orgInfo.getLongNumber() + '%';
    		builder.appendSql("left join t_she_building building on building.fid = room.fbuildingid \n");
    		builder.appendSql("left join t_she_sellproject selPrj on selPrj.Fid = building.fsellprojectid \n");
//    		builder.appendSql("inner join t_org_baseunit orgUnit on orgUnit.Fid = selPrj.Forgunitid \n");
//    		builder.appendSql("where orgUnit.Flongnumber like '" + orgLongNumber + "'");
    		builder.appendParam(" where selPrj.fid", projectSet.toArray());
    	}
    }
    /////////////////////////////////////////////共用方法部分-end///////////////////////////////////////////////////////////
    
    /////////////////////////////////////////////汇总列-begin///////////////////////////////////////////////////////////
    /**
     * 获取房间的相关信息（汇总列的值）
     * @throws EASBizException 
     */
    private Map getTotalRptDate(Context ctx, Map paramMap, Map roomModelMap) throws BOSException, SQLException, EASBizException
    {
    	Map result = new HashMap();
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	/**
    	 * 共用部分的sql，同时获取建筑面积和套内面积，值传值的时候选择不同的值即可
    	 */
    	builder.appendSql("select prdType.Fid as prdTypeId, prdType.Fname_L2 as prdTypeName, rmodel.fid as rmodelId, \n");
    	builder.appendSql("rmodel.fname_l2 as rmodelName, count(room.fid) as sumCount, \n");
    	builder.appendSql("sum(room.fbuildingarea) as sumBuildingArea, \n");
    	builder.appendSql("sum(room.froomarea) as sumRoomArea, \n");
    	builder.appendSql("sum(room.factualbuildingarea) as sumActualBuildingArea, \n");
    	builder.appendSql("sum(room.factualroomarea) as sunActualRoomArea, \n");
//    	builder.appendSql("sum(case when room.factualbuildingarea is null then room.fbuildingarea else room.factualbuildingarea end) as sumBuildArea,\n");
//    	builder.appendSql("sum(case when room.factualroomarea is null then room.factualroomarea else room.froomarea end) as sumRoomArea,  \n");
    	builder.appendSql("sum(room.fstandardtotalamount) as sumTotAmt \n");
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	
    	/**
    	 * 获取所选的树节点
    	 * 所选的树节点不能为空，客户端维护，如果为空则进行设置当前组织
    	 */
    	appendSelectedNodeFilter(builder, paramMap,ctx);
    	
    	/**
    	 * 是否包含附属房产
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    	/**
    	 * 获取总的结果是不考虑时间
    	 */
    	builder.appendSql("group by prdType.Fid, prdType.Fname_L2, rmodel.fid, rmodel.fname_l2 \n");
    	builder.appendSql("order by prdType.Fid, rmodel.fid asc\n");
    	
    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	
    	/**
    	 * 面积类型
    	 */
    	Boolean isBuildArea = (Boolean)paramMap.get("BuildArea");
    	Boolean isPreArea = (Boolean)paramMap.get("PreArea");
    	
    	/**
    	 * key = 产品类型 + 户型
    	 */
    	while(rowSet.next())
    	{
    		String prdTypeId = rowSet.getString("prdTypeId");
    		String rmodelId = rowSet.getString("rmodelId");
    		
    		if(roomModelMap.containsKey(prdTypeId))
    		{
    			ArrayList array = (ArrayList)roomModelMap.get(prdTypeId);
    			array.add(rmodelId);
    		}
    		else
    		{
    			ArrayList array = new ArrayList();
    			array.add(rmodelId);
    			roomModelMap.put(prdTypeId, array);
    		}
    		String tmpKey = prdTypeId + rmodelId;
    		
    		Object[] tmpArray = new Object[4];
    		tmpArray[0] = rowSet.getString("rmodelName");    //户型名称
    		tmpArray[1] = rowSet.getBigDecimal("sumCount");  //总套数
    		tmpArray[2] = this.getArea(isBuildArea, isPreArea, rowSet);//总 面积
//    		tmpArray[2] = isBuildArea.booleanValue()? rowSet.getBigDecimal("sumBuildArea"):rowSet.getBigDecimal("sumRoomArea"); //总面积
    		tmpArray[3] = rowSet.getBigDecimal("sumTotAmt"); //总金额
    		
    		result.put(tmpKey, tmpArray);
    		
    	}
    	
    	return result;
    }
    /////////////////////////////////////////////汇总列-end/////////////////////////////////////////////////////////////
  
   
    ///////////////////////////////////////////////已销房间-begin/////////////////////////////////////////////////////////
    /**
     * 获取已售房间的相关信息
     * @throws BOSException 
     * @throws SQLException 
     * @throws EASBizException 
     */
    private Map getSelledRoomData(Context ctx, Map paramMap) throws BOSException, SQLException, EASBizException
    {
    	Map selledRoomData = new HashMap();
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdType.Fid as prdTypeId, prdType.Fname_L2 as prdTypeName, rmodel.fid as rmodelId, \n");
    	builder.appendSql("rmodel.fname_l2 as rmodelName, count(room.fid) as sumCount, \n");
    	builder.appendSql("sum(room.fbuildingarea) as sumBuildingArea, \n");
    	builder.appendSql("sum(room.froomarea) as sumRoomArea, \n");
    	builder.appendSql("sum(room.factualbuildingarea) as sumActualBuildingArea, \n");
    	builder.appendSql("sum(room.factualroomarea) as sunActualRoomArea, \n");
//    	builder.appendSql("sum(case when room.factualbuildingarea is null then room.fbuildingarea else room.factualbuildingarea end) as sumBuildArea, \n");
//    	
//    	builder.appendSql("sum(case when room.factualroomarea is null then room.factualroomarea else room.froomarea end) as sumRoomArea, \n");
    	builder.appendSql("sum(room.fstandardtotalamount) as sumTotAmt, \n");
    	builder.appendSql("sum(room.fbasebuildingprice) as baseBuidAmt, sum(room.fbaseroomprice) as baseRoomAmt, sum(room.fdealtotalamount) as dealSumAmt,\n");
    	
    	builder.appendSql("sum(((case when room.fbasebuildingprice is null then 0 else room.fbasebuildingprice end)*(case when room.factualBuildingArea is null then 0 else room.factualBuildingArea end))) as buildActAmt,\n");
    	builder.appendSql("sum(((case when room.fbasebuildingprice is null then 0 else room.fbasebuildingprice end)*(case when room.fbuildingArea is null then 0 else room.fbuildingArea end))) as buildAmt,\n");
    	builder.appendSql("sum(((case when room.fbaseroomprice is null then 0 else room.fbaseroomprice end)*(case when room.froomArea is null then 0 else room.froomArea end))) as roomAmt,\n");
    	builder.appendSql("sum(((case when room.fbaseroomprice is null then 0 else room.fbaseroomprice end)*(case when room.factualRoomArea is null then 0 else room.factualRoomArea end))) as roomActAmt\n");

//    	builder.appendSql("sum(isnull(room.fbasebuildingprice,0)*isnull(room.buildingArea,0)) as buildAmt,sum(isnull(room.fbasebuildingprice,0)*isnull(room.actualBuildingArea,0)) as buildActAmt,\n");
//    	builder.appendSql("sum(isnull(room.fbaseroomprice,0)*isnull(room.roomArea,0)) as roomAmt,sum(isnull(room.fbaseroomprice,0)*isnull(room.actualRoomArea,0)) as roomActAmt,\n");
    	
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid  \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	
    	/**
    	 * 获取所选的树节点
    	 * 所选的树节点不能为空，客户端维护，如果为空则进行设置当前组织
    	 */
    	appendSelectedNodeFilter(builder, paramMap,ctx);
    	
    	/**
    	 * 是否包含附属房产
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    	/**
    	 * 是否包含预订业务
    	 */
    	Boolean includePreOrder = (Boolean)(paramMap.get("IncludeOrder"));
    	if(includePreOrder.booleanValue())
    	{
    		builder.appendSql("\n and room.fsellstate in ('PrePurchase', 'Purchase', 'Sign') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fsellstate in ('Purchase', 'Sign') \n");
    	}
    	
    	builder.appendSql("group by prdType.Fid, prdType.Fname_L2, rmodel.fid, rmodel.fname_l2 \n");
    	builder.appendSql("order by prdType.Fid, rmodel.fid asc \n");
    	
    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	/**
    	 * 面积类型
    	 */
    	Boolean isBuildArea = (Boolean)paramMap.get("BuildArea");
    	Boolean isPreArea = (Boolean)paramMap.get("PreArea");
    	
    	/**
    	 * key = 产品类型 + 户型
    	 */
    	while(rowSet.next())
    	{
    		String prdTypeId = rowSet.getString("prdTypeId");
    		String rmodelId = rowSet.getString("rmodelId");
    		String tmpKey = prdTypeId + rmodelId;
    		
    		Object[] tmpArray = new Object[6];
    		tmpArray[0] = rowSet.getBigDecimal("sumCount");  //总套数
    		tmpArray[1] = this.getArea(isBuildArea, isPreArea, rowSet);
//    		tmpArray[1] = isBuildArea.booleanValue()? rowSet.getBigDecimal("sumBuildArea"):rowSet.getBigDecimal("sumRoomArea"); //总面积
    		tmpArray[2] = rowSet.getBigDecimal("sumTotAmt"); //总金额
    		tmpArray[3] = isBuildArea.booleanValue()? rowSet.getBigDecimal("baseBuidAmt"):rowSet.getBigDecimal("baseRoomAmt");  //低价总价
    		tmpArray[4] = rowSet.getBigDecimal("dealSumAmt"); //成交总价
//    		添加了正确的低价总价算法
    		tmpArray[5] = this.getbaseprice(isBuildArea, isPreArea, rowSet);  //低价总价
    		
    		
    		selledRoomData.put(tmpKey, tmpArray);
    	}
    	
    	return selledRoomData;
    }
    ///////////////////////////////////////////////已销房间-end///////////////////////////////////////////////////////////
    
    
    ///////////////////////////////////////////////未售房间-begin/////////////////////////////////////////////////////////
    /**
     * 获取未售房间的相关信息
     * @throws BOSException 
     * @throws SQLException 
     * @throws EASBizException 
     */
    private Map getUnselledRoomData(Context ctx, Map paramMap) throws BOSException, SQLException, EASBizException
    {
    	Map unselledRoomData = new HashMap();
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdType.Fid as prdTypeId, prdType.Fname_L2 as prdTypeName, rmodel.fid as rmodelId, \n");
    	builder.appendSql("rmodel.fname_l2 as rmodelName, count(room.fid) as sumCount, \n");
    	builder.appendSql("sum(room.fbuildingarea) as sumBuildingArea, \n");
    	builder.appendSql("sum(room.froomarea) as sumRoomArea, \n");
    	builder.appendSql("sum(room.factualbuildingarea) as sumActualBuildingArea, \n");
    	builder.appendSql("sum(room.factualroomarea) as sunActualRoomArea, \n");
//    	builder.appendSql("sum(case when room.factualbuildingarea is null then room.fbuildingarea else room.factualbuildingarea end) as sumBuildArea, \n");
//    	
//    	builder.appendSql("sum(case when room.factualroomarea is null then room.factualroomarea else room.froomarea end) as sumRoomArea, \n");
    	builder.appendSql("sum(room.fstandardtotalamount) as sumTotAmt \n");
    	
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid  \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	
    	/**
    	 * 获取所选的树节点
    	 * 所选的树节点不能为空，客户端维护，如果为空则进行设置当前组织
    	 */
    	appendSelectedNodeFilter(builder, paramMap,ctx);
    	
    	/**
    	 * 是否包含附属房产
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    	/**
    	 * 是否包含预订业务
    	 */
    	Boolean includePreOrder = (Boolean)(paramMap.get("IncludeOrder"));
    	if(includePreOrder.booleanValue())
    	{
    		builder.appendSql("\n and room.fsellstate in ('Init', 'Onshow', 'KeepDown') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fsellstate in ('Init', 'Onshow', 'KeepDown', 'PrePurchase') \n");
    	}
    	
    	builder.appendSql("group by prdType.Fid, prdType.Fname_L2, rmodel.fid, rmodel.fname_l2 \n");
    	builder.appendSql("order by prdType.Fid, rmodel.fid asc \n");
    	
//    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	/**
    	 * 面积类型
    	 */
    	Boolean isBuildArea = (Boolean)paramMap.get("BuildArea");
    	Boolean isPreArea = (Boolean)paramMap.get("PreArea");
    	
    	/**
    	 * key = 产品类型 + 户型
    	 */
    	while(rowSet.next())
    	{
    		String prdTypeId = rowSet.getString("prdTypeId");
    		String rmodelId = rowSet.getString("rmodelId");
    		String tmpKey = prdTypeId + rmodelId;
    		
    		Object[] tmpArray = new Object[5];
    		tmpArray[0] = rowSet.getBigDecimal("sumCount");  //总套数
    		tmpArray[1] = this.getArea(isBuildArea, isPreArea, rowSet);
//    		tmpArray[1] = isBuildArea.booleanValue()? rowSet.getBigDecimal("sumBuildArea"):rowSet.getBigDecimal("sumRoomArea"); //总面积
    		tmpArray[2] = rowSet.getBigDecimal("sumTotAmt"); //标准总价
    		
    		unselledRoomData.put(tmpKey, tmpArray);
    	}
    	
    	return unselledRoomData;
    }
      
    ///////////////////////////////////////////////未售房间-end///////////////////////////////////////////////////////////
    
    /**
	 * 根据选择的面积类型来取面积值
	 */
    private Object getArea(Boolean isBuildArea,Boolean isPreArea,IRowSet rowSet) throws SQLException
    {
    	//总面积
		//如果选的建筑面积和预测面积，则总面积去预测建筑面积
		if(isBuildArea.booleanValue() && isPreArea.booleanValue())
		{
			return rowSet.getBigDecimal("sumBuildingArea");
			//如果选的建筑面积和实测面积，则总面积去实测建筑面积
		}else if(isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("sumActualBuildingArea");
			//如果选的套内面积和实测面积，则总面积去实测套内面积
		}else if(!isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("sunActualRoomArea");
			//如果选的套内面积和预测面积，则总面积去预测套内面积
		}else
		{
			return rowSet.getBigDecimal("sumRoomArea");
		}
    }
    /**
	 * 根据选择的面积类型来底价
	 */
    private Object getbaseprice(Boolean isBuildArea,Boolean isPreArea,IRowSet rowSet) throws SQLException
    {
    	//总面积
		//如果选的建筑面积和预测面积，则总面积去底价
		if(isBuildArea.booleanValue() && isPreArea.booleanValue())
		{
			return rowSet.getBigDecimal("buildAmt");
			//如果选的建筑面积和实测面积，则总面积底价
		}else if(isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("buildActAmt");
			//如果选的套内面积和实测面积，则总面积去底价
		}else if(!isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("roomActAmt");
			//如果选的套内面积和预测面积，则总面积去底价
		}else
		{
			return rowSet.getBigDecimal("roomAmt");
		}
    }
    
    ///////////////////////////////////////////////累计回款-begin/////////////////////////////////////////////////////////
    private Map getSumGathering(Context ctx, Map paramMap) throws BOSException, SQLException, EASBizException
    {
    	Map sumGathering = new HashMap();
    	
    	//获取已售房间的ID
    	Set selledRoomIds = getSelledRoomIdSet(ctx, paramMap);
    	
    	if(selledRoomIds == null || selledRoomIds.size() == 0)
    		return sumGathering;
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdTypeId as prdTypeId, rmodelId as rmodelId,sum(actamount) actamount, \n");
    	builder.appendSql("sum(transAmount) transAmount, sum(refundAmount) refundAmount,sum(justAmount) justAmount \n");
    	builder.appendSql("from ( \n");
    	
    	//获取已收房间下的应收下的实际回款
    	builder.appendSql("select  prdType.Fid as prdTypeId, rmodel.fid as rmodelId,  \n");
//    	builder.appendSql("sum(payLists.FactRevamount-payLists.Fhastransferredamount-payLists.Fhasrefundmentamount-payLists.Fhasadjustedamount) as gatheringAmt \n");
    	builder.appendSql("sum(payLists.factrevamount) actamount, \n");
    	builder.appendSql("sum(payLists.Fhastransferredamount) transAmount, \n");
    	builder.appendSql("sum(payLists.Fhasrefundmentamount) refundAmount, \n");
    	builder.appendSql("sum(payLists.Fhasadjustedamount) justAmount \n");
    	builder.appendSql("from t_she_purchasepaylistentry payLists \n");
    	//builder.appendSql("inner join t_she_moneydefine monDefine on payLists.Fmoneydefineid = monDefine.Fid \n");
    	builder.appendSql("left join t_she_purchase purchase on  payLists.Fheadid = purchase.fid \n");
    	builder.appendSql("left join t_she_room room on room.flastpurchaseid = purchase.fid \n"); //房间上面关联的是一个有效的收款单
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	
    	builder.appendSql("where \n");
    	builder.appendParam("room.fid", selledRoomIds.toArray());
    	builder.appendSql("\n group by prdType.Fid, rmodel.fid \n");
    	
//    	builder.appendSql("union \n");
//    	
//    	//获取已售房间下的其他应收明细下的实际回款
//    	builder.appendSql("select  prdType.Fid as prdTypeId, rmodel.fid as rmodelId,  \n");
////    	builder.appendSql("sum(elsePayLists.FactRevamount-elsePayLists.Fhastransferredamount-elsePayLists.Fhasrefundmentamount-elsePayLists.Fhasadjustedamount) as gatheringAmt \n");
//    	builder.appendSql("sum(elsePayLists.factrevamount) actamount, \n");
//    	builder.appendSql("sum(elsePayLists.Fhastransferredamount) transAmount, \n");
//    	builder.appendSql("sum(elsePayLists.Fhasrefundmentamount) refundAmount, \n");
//    	builder.appendSql("sum(elsePayLists.Fhasadjustedamount) justAmount \n");
//    	builder.appendSql("from t_she_purchaseelsepaylistentry elsePayLists \n");
//    	//builder.appendSql("inner join t_she_moneydefine monDefine on elsePayLists.Fmoneydefineid = monDefine.Fid \n");
//    	builder.appendSql("left join t_she_purchase purchase on  elsePayLists.Fheadid = purchase.fid \n");
//    	builder.appendSql("left join t_she_room room on room.flastpurchaseid = purchase.fid \n"); //房间上面关联的是一个有效的收款单
//    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
//    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
//    	builder.appendSql("where \n");
//    	builder.appendParam("room.fid", selledRoomIds.toArray());
//    	builder.appendSql("\n group by prdType.Fid, rmodel.fid \n");
    	
    	builder.appendSql(") PayListSum \n");
    	
    	builder.appendSql("group by prdTypeId, rmodelId \n");
    	builder.appendSql("order by prdTypeId, rmodelId asc \n");
    	
//    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	
    	while(rowSet.next())
    	{
    		String tmpKey = rowSet.getString("prdTypeId") + rowSet.getString("rmodelId");
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
    		sumGathering.put(tmpKey,actamount);
    	}
    	
    	return sumGathering;
    }
    
    private Set getSelledRoomIdSet(Context ctx, Map paramMap) throws BOSException, SQLException, EASBizException
    {
    	Set roomIds = new HashSet();
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select distinct room.fid as roomId \n");
    	builder.appendSql("from t_she_room room \n");
    	
    	/**
    	 * 获取所选的树节点
    	 * 所选的树节点不能为空，客户端维护，如果为空则进行设置当前组织
    	 */
    	appendSelectedNodeFilter(builder, paramMap,ctx);
    	
    	/**
    	 * 是否包含附属房产
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    	/**
    	 * 是否包含预订业务
    	 */
    	Boolean includePreOrder = (Boolean)(paramMap.get("IncludeOrder"));
    	if(includePreOrder.booleanValue())
    	{
    		builder.appendSql("\n and room.fsellstate in ('PrePurchase', 'Purchase', 'Sign') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fsellstate in ('Purchase', 'Sign') \n");
    	}
//    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	
    	while(rowSet.next())
    	{
    		roomIds.add(rowSet.getString("roomId"));
    	}
    	
    	return roomIds;
    }
    ///////////////////////////////////////////////累计回款-end///////////////////////////////////////////////////////////
    
    
    ///////////////////////////////////////////////区间查询-begin///////////////////////////////////////////////////////////
    /**
     * selectNode        = 所选树的节点，如果没有选，则设为当前组织
	 * IncludeAttachment = 包括附属房产，为真是包括，为假时不包括
	 * BuildArea         = 表示面积类型，为真是建筑面积，为假时为套内面积
	 * BeginDate         = 开始日期
	 * EndDate           = 结束日期
	 * IncludeOrder      = 包括预订业务， 为真是包括，为假时不包括
	 * IsAuditDate       = 已那个时间为标准（为真时以审批日期为标准，为假时已对应的日期为标准）
     * @throws EASBizException 
     */
    public static Map getValidPurchaseIdSet(Context ctx, Map paramMap) throws BOSException, SQLException, EASBizException
    {
    	Map purchaseMap = new HashMap();
    	Set purchaseIds = new HashSet();
//    	Set purchaseCompensateSet = new HashSet();
    	
//    	Boolean isAuditDate = (Boolean)paramMap.get("IsAuditDate");
    	Boolean isShowAll = (Boolean)paramMap.get("isShowAll");
    	Date beginDate = new Date();
    	Date endDate = new Date();
    	if(isShowAll==null)
    	{
    		beginDate = (Date)paramMap.get("BeginDate");
       	    endDate = (Date)paramMap.get("EndDate");
    	}else
    	{
    		if(isShowAll.booleanValue())
        	{
    			java.text.SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    			try {
					beginDate = df.parse("19000101");
					endDate = df.parse("29000101");
				} catch (ParseException e) {
					e.printStackTrace();
				};				
//				endDate = FDCDateHelper.getServerTimeStamp();

        	}else
        	{
        		 beginDate = (Date)paramMap.get("BeginDate");
            	 endDate = (Date)paramMap.get("EndDate");
        	}
    	}
   	
    	Boolean includeOrder = (Boolean)paramMap.get("IncludeOrder");
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select roomId, purchaseId, purchaseState, busDate \n");
    	builder.appendSql("from (  \n");   	
    	
    	/**
    	 * 非作废状态
    	 */
    	builder.appendSql("select purchase.froomid as roomId, purchase.fid as purchaseId,  \n");
    	builder.appendSql("purchase.fpurchasestate as purchaseState, \n");
    	if(includeOrder.booleanValue())
    	{
    		builder.appendSql("purchase.ftosaledate as busDate \n");
    	}
    	else
    	{
    		builder.appendSql("purchase.ftopurchasedate as busDate \n");
    	}
    	builder.appendSql("from t_she_purchase purchase \n");
    	builder.appendSql("left join t_she_room room on room.fid = purchase.froomid \n");
    	//添加房间过滤
    	appendSelectedNodeFilter(builder, paramMap,ctx);
    	if(includeOrder.booleanValue())
    	{
    		builder.appendSql("and purchase.fpurchasestate in ('PrePurchaseCheck', 'PurchaseApply', 'PurchaseAuditing', 'PurchaseAudit', 'PurchaseChange','QuitRoomBlankOut','ChangeRoomBlankOut')");
    	}
    	else
    	{
    		builder.appendSql("and purchase.fpurchasestate in ('PurchaseApply', 'PurchaseAuditing', 'PurchaseAudit', 'PurchaseChange','QuitRoomBlankOut','ChangeRoomBlankOut')");
    	}
    	builder.appendSql("and purchase.ftosaledate is not null \n");
    	if(includeOrder.booleanValue())
    	{
    		appendDateFilter(builder, "purchase.ftosaledate", beginDate, endDate);
    	}
    	else
    	{
    		appendDateFilter(builder, "purchase.ftopurchasedate", beginDate, endDate);
    	}
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    
    	builder.appendSql(") blankTable \n");
    	builder.appendSql("order by roomId, purchaseId asc \n");
    
    	logger.info(builder.getTestSql());
    	IRowSet rowSet = builder.executeQuery(ctx);
    	
    	/**
    	 * 存放业务日期最新的业务单据
    	 * key = roomId
    	 * 1 = 认购单ID
    	 * 2 = 认购单状态
    	 * 3 = 认购单的交易日期
    	 */
    	Map roomPurchaseMap = new HashMap();
    	while(rowSet.next())
    	{
    		//roomId, purchaseId, purchaseState, busDate
    		//因为可能同一个房间对应对个认购单，所以以房间为key的话会有问题的。认购单ID才是唯一的
    		String tmpKey = rowSet.getString("purchaseId");
    		String purchaseId = rowSet.getString("purchaseId");
    		String purchaseState = rowSet.getString("purchaseState");
    		Date  busDate = rowSet.getDate("busDate");
    		
//    		if(roomPurchaseMap.containsKey(tmpKey))
//    		{
//    			Object[] tmpArray = (Object[])roomPurchaseMap.get(tmpKey);
//    			Date tmpBusDate = (Date)tmpArray[2];
//    			if(busDate.after(tmpBusDate))
//    			{
//    				tmpArray[0] = purchaseId;
//    				tmpArray[1] = purchaseState;
//    				tmpArray[2] = busDate;
//    			}
//    		}
//    		else
//    		{
    			Object[] array = new Object[3];
    			array[0] = purchaseId;    //认购单ID
    			array[1] = purchaseState; //认购单状态
    			array[2] = busDate;       //认购单的交易日期
    			
    			roomPurchaseMap.put(tmpKey, array);
//    		}
    	}
    	
    	Set tmpKeySet = roomPurchaseMap.keySet();
    	Iterator keyIter = tmpKeySet.iterator();  	
    	/**
    	 * 获取有效的认购单
    	 */
    	while(keyIter.hasNext())
    	{
    		String tmpStr = (String)keyIter.next();
    		Object[] tmpArray = (Object[]) roomPurchaseMap.get(tmpStr);
    		purchaseIds.add(tmpArray[0]);
    		if(includeOrder.booleanValue())
    		{
    			String purchaseState = String.valueOf(tmpArray[1]);
    			//'PrePurchaseCheck', 'PurchaseAudit', 'PurchaseChange'
    			if(purchaseState.equals("PrePurchaseCheck") || 
    					purchaseState.equals("PurchaseAudit") ||
    					purchaseState.equals("PurchaseChange") ||
    					purchaseState.equals("QuitRoomBlankOut") ||
    					purchaseState.equals("ChangeRoomBlankOut"))
    			{
    				//对应的认购单还需要考虑到有没有对应的退房单或者换房单，如果没有则计入统计，如果有并且退房单据日期
    		    	//在所选日期范围内则不计入统计
    				//循环里调用效率太差，还是不用
//    				if(!vifyQuitAndChange(ctx, (String)tmpArray[0], beginDate, endDate))
//    				{
    					purchaseIds.add(tmpArray[0]);
//    				}   
    			}
    		}
    		else
    		{
    			String purchaseState = String.valueOf(tmpArray[1]);
    			//'PurchaseAudit', 'PurchaseChange'
    			if( purchaseState.equals("PurchaseAudit") ||
    					purchaseState.equals("PurchaseChange") ||
    					purchaseState.equals("QuitRoomBlankOut") ||
    					purchaseState.equals("ChangeRoomBlankOut"))
    			{
    				//对应的认购单还需要考虑到有没有对应的退房单或者换房单，如果没有则计入统计，如果有并且退房单据日期
    		    	//在所选日期范围内则不计入统计
    				//循环里调用效率太差
//    				if(!vifyQuitAndChange(ctx, (String)tmpArray[0], beginDate, endDate))
//    				{
    					purchaseIds.add(tmpArray[0]);
//    				} 
    			}
    		}
    	}   
    	//以已审批的退房单或换房单为准
    	if(purchaseIds.size()>0)
    	{
    		//对应的认购单还需要考虑到在时间范围内有没有对应的退房单，如果没有则计入统计
        	builder = new FDCSQLBuilder(ctx);
        	builder.appendSql("select pur.fid purchaseID ");
        	builder.appendSql("from t_she_purchase pur ");
        	builder.appendSql("left join t_she_quitroom quitroom on quitroom.fpurchaseid = pur.fid ");
        	builder.appendSql("where quitroom.fstate='4AUDITTED' ");
        	appendDateFilter(builder, "quitroom.Faudittime", beginDate, endDate);
        	builder.appendSql(" and ");
        	builder.appendParam("pur.fid", purchaseIds.toArray());
        	rowSet = builder.executeQuery(ctx);
        	while(rowSet.next())
        	{
        		String purchaseID = rowSet.getString("purchaseID");
        		purchaseIds.remove(purchaseID);
        	}
        	//当只有一张认购单符合条件并且有对应的退房单的时候，purchaseIds又会为空了所以这里再判断一下
        	if(purchaseIds.size()>0)
        	{
        		//对应的认购单还需要考虑到在时间范围内有没有对应的换房单，如果没有则计入统计
            	builder = new FDCSQLBuilder(ctx);
            	builder.appendSql("select pur.fid purchaseID ");
            	builder.appendSql("from t_she_purchase pur ");
            	builder.appendSql("left join t_she_changeroom change on change.FOldPurchaseID = pur.fid ");
            	builder.appendSql("where change.fstate='4AUDITTED' ");
            	appendDateFilter(builder, "change.Faudittime", beginDate, endDate);
            	builder.appendSql(" and "); 
            	builder.appendParam("pur.fid", purchaseIds.toArray());
            	rowSet = builder.executeQuery(ctx);
            	while(rowSet.next())
            	{
            		String purchaseID = rowSet.getString("purchaseID");
            		purchaseIds.remove(purchaseID);
            	}
        	}      	
    	}    	
    	
    	purchaseMap.put("purchase", purchaseIds);
    	return purchaseMap;
    }
    
    //校验认购单是否有对应的已审批状态的退房单和变更单
    private static boolean vifyQuitAndChange(Context ctx,String purchaseID,Date beginDate, Date endDate) throws BOSException
    {
    	boolean boo = false;
    	EntityViewInfo view = new EntityViewInfo();
    	EntityViewInfo view1 = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.getSelector().add("quitDate");
    	view1.getSelector().add("changeDate");
    	filter.getFilterItems().add(new FilterItemInfo("purchase.id",purchaseID));
    	filter.getFilterItems().add(new FilterItemInfo("state","4AUDITTED"));
    	view.setFilter(filter);
    	view1.setFilter(filter);
    	QuitRoomCollection quitRoomColl = QuitRoomFactory.getLocalInstance(ctx).getQuitRoomCollection(view);
    	
    	PurchaseChangeCollection purChangeColl = PurchaseChangeFactory.getLocalInstance(ctx).getPurchaseChangeCollection(view1);
    	
    	//如果既没有对应的已审批退房单也没有变更单那么计入统计
    	if(quitRoomColl.size()==0 && purChangeColl.size()==0)
    	{
    		boo = false;
    	}else
    	{
    		//因为同一张认购单只能同时对应一张退房单或者变更单
    		if(quitRoomColl.size()>0)
    		{
    			QuitRoomInfo quitRoom = quitRoomColl.get(0);
    			Date quitDate = quitRoom.getQuitDate();
    			//如果找到的退房单在时间选择范围内说明该认购单不计入统计
    			if(quitDate.after(beginDate) && quitDate.before(endDate))
    			{
    				boo = true;
    			}else
    			{
    				boo = false;
    			}
    		}
    		if(purChangeColl.size()>0)
    		{
    			PurchaseChangeInfo purChangeInfo = purChangeColl.get(0);
    			Date purChangeDate = purChangeInfo.getChangeDate();
    			//如果找到的变更单在时间选择范围内说明该认购单不计入统计
    			if(purChangeDate.after(beginDate) && purChangeDate.before(endDate))
    			{
    				boo = true;
    			}else 
    			{
    				boo = false;
    			}
    		}
    	}
    	return boo;
    }
    
    /**
     * 获取区间类
     * @param ctx
     * @param paramMap
     * @param validPurchaseIds
     * @return
     * @throws BOSException
     * @throws SQLException
     * @throws EASBizException 
     */
    protected Map getPeriodNoGatheringData(Context ctx, Map paramMap, Map result) throws BOSException, SQLException, EASBizException
    {   	
    	Map PeriodNoGatheringDataMap = new HashMap();
    	Map PeriodGatheringDataMap = new HashMap();
    	Map perCheckMap = new HashMap();
    	Map purchaseMap = new HashMap();
//    	purchaseMap = getValidPurchaseIdSet(ctx, paramMap);
    	String sql = getValidPurchaseIdSetNew(ctx,paramMap);
//    	Set validPurchaseIds = (Set)purchaseMap.get("purchase");
    	//包含面积补差的认购单ID(不需要取！前面的认购单肯定包含了有补差的认购单)
//    	Set purchaseCompensateSet = (Set)purchaseMap.get("purchaseCompensate");
//    	if(validPurchaseIds.size() <= 0)
//    	{
//    		return result;
//    	}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdTypeId,rmodelId,state,sum(roomNum) roomNum,sum(sumBuildingArea) sumBuildingArea, \n");
    	builder.appendSql("sum(sumRoomArea) sumRoomArea,sum(sumActualBuildingArea) sumActualBuildingArea, \n");
    	builder.appendSql("sum(sunActualRoomArea) sunActualRoomArea,sum(compentAmount) compentAmount, \n");
    	builder.appendSql("sum(dealAmt) dealAmt,sum(standAmt) standAmt, \n");
    	builder.appendSql("sum(contractAmt) contractAmt,sum(purchaseAmt) purchaseAmt \n");
    	builder.appendSql("from (");
    	builder.appendSql("select prdType.Fid as prdTypeId, rmodel.fid as rmodelId,purchase.fpurchasestate state,count(purchase.froomid) as roomNum, \n");
    	builder.appendSql("sum(room.fbuildingarea) as sumBuildingArea, \n");
    	builder.appendSql("sum(room.froomarea) as sumRoomArea, \n");
    	builder.appendSql("sum(room.factualbuildingarea) as sumActualBuildingArea, \n");
    	builder.appendSql("sum(room.factualroomarea) as sunActualRoomArea, \n");
    	builder.appendSql("sum(areaCompent.FCompensateAmount) as compentAmount, \n");
//    	builder.appendSql("sum(case when room.factualbuildingarea is null then room.fbuildingarea else room.factualbuildingarea end) as buildArea, \n");
//    	builder.appendSql("sum(case when room.factualroomarea is null then room.froomarea else room.factualroomarea end) as roomArea, \n");
    	builder.appendSql("sum(purchase.fdealamount ) as dealAmt, sum(purchase.fstandardtotalamount) as standAmt,  \n");
    	builder.appendSql("sum(purchase.fcontracttotalamount) as contractAmt,  \n");
    	builder.appendSql("sum(case when purchase.fpurchasestate in ('PurchaseApply', 'PurchaseAuditing', 'PurchaseAudit', 'PurchaseChange') ");
    	builder.appendSql("then purchase.fdealamount end) as purchaseAmt \n");
    	
    	builder.appendSql("from t_she_purchase purchase \n");
    	builder.appendSql("left join t_she_room room on room.fid = purchase.froomid \n");
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	builder.appendSql("left join t_she_RoomAreaCompensate areaCompent on room.fid = areaCompent.froomid \n");
//		builder.appendSql("left join T_SHE_AreaCompensateRevList comRevList on areaCompent.fid = comRevList.fheadid  \n");
		builder.appendSql("\n where ");
//    	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
    	builder.appendSql("purchase.fid in (" + sql + ")");
    	builder.appendSql("\n group by prdType.Fid, rmodel.fid,purchase.fpurchasestate \n");
    	/**
    	 * 是否包含附属房产
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("union \n");
    		builder.appendSql("select prdType.Fid as prdTypeId, rmodel.fid as rmodelId,purchase.fpurchasestate state,count(purchase.froomid) as roomNum, \n");
        	builder.appendSql("sum(room.fbuildingarea) as sumBuildingArea, \n");
        	builder.appendSql("sum(room.froomarea) as sumRoomArea, \n");
        	builder.appendSql("sum(room.factualbuildingarea) as sumActualBuildingArea, \n");
        	builder.appendSql("sum(room.factualroomarea) as sunActualRoomArea, \n");
        	builder.appendSql("sum(areaCompent.FCompensateAmount) as compentAmount, \n");
//        	builder.appendSql("sum(case when room.factualbuildingarea is null then room.fbuildingarea else room.factualbuildingarea end) as buildArea, \n");
//        	builder.appendSql("sum(case when room.factualroomarea is null then room.froomarea else room.factualroomarea end) as roomArea, \n");
        	builder.appendSql("sum(purchase.fdealamount ) as dealAmt, sum(purchase.fstandardtotalamount) as standAmt,  \n");
        	builder.appendSql("sum(purchase.fcontracttotalamount) as contractAmt,  \n");
        	builder.appendSql("sum(case when purchase.fpurchasestate in ('PurchaseApply', 'PurchaseAuditing', 'PurchaseAudit', 'PurchaseChange') ");
        	builder.appendSql("then purchase.fdealamount end) as purchaseAmt \n");
        	builder.appendSql("from T_SHE_PurchaseRoomAttachEntry attach \n");
        	builder.appendSql("left join T_SHE_RoomAttachmentEntry roomAttach on attach.FAttachmentEntryID = roomAttach.fid \n");
        	builder.appendSql("left join t_she_purchase purchase on attach.fheadid = purchase.fid \n");
        	builder.appendSql("left join t_she_room room on roomAttach.froomid = room.fid \n");
        	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
        	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
        	builder.appendSql("left join t_she_RoomAreaCompensate areaCompent on room.fid = areaCompent.froomid \n");
//    		builder.appendSql("left join T_SHE_AreaCompensateRevList comRevList on areaCompent.fid = comRevList.fheadid  \n");
        	builder.appendSql("\n where ");
//        	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
        	builder.appendSql("purchase.fid in (" + sql + ")");
        	builder.appendSql("\n group by prdType.Fid, rmodel.fid,purchase.fpurchasestate \n");      	
    	}
    	builder.appendSql(") aa");
    	builder.appendSql("\n group by prdTypeId, rmodelId,state ");
    	
    	logger.info(builder.getTestSql());
    	
    	/**
    	 * 面积类型
    	 */
    	Boolean isBuildArea = (Boolean)paramMap.get("BuildArea");
    	Boolean isPreArea = (Boolean)paramMap.get("PreArea");
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
    	{
    		String tmpPrdTypeId = rowSet.getString("prdTypeId");
    		String tmpRmodelId = rowSet.getString("rmodelId");
    		String state = rowSet.getString("state");
    		String tmpKey = tmpPrdTypeId + tmpRmodelId;
    		BigDecimal compentAmount = rowSet.getBigDecimal("compentAmount");
    		compentAmount = compentAmount==null?new BigDecimal(0):compentAmount;
    		if(PurchaseStateEnum.PREPURCHASECHECK_VALUE.equals(state))
    		{
    			//如果是预定复核，那么不统计成交总价合同总价等
    			if(PeriodNoGatheringDataMap.get(tmpKey)!=null)
    			{
    				Object[] obj = (Object[])PeriodNoGatheringDataMap.get(tmpKey);
    				BigDecimal roomNum = (BigDecimal)rowSet.getBigDecimal("roomNum");
    				obj[0] = getBigDecimal(obj[0]).add(CRMHelper.getBigDecimal(roomNum));
    				BigDecimal area = (BigDecimal)getArea(isBuildArea, isPreArea, rowSet);
    				obj[1] = getBigDecimal(obj[1]).add(CRMHelper.getBigDecimal(area));
    				BigDecimal standAmt = (BigDecimal)rowSet.getBigDecimal("standAmt");
    				obj[3] = getBigDecimal(obj[3]).add(CRMHelper.getBigDecimal(standAmt));
    			}else
    			{
    				Object[] tmpArray = new Object[9];
            		tmpArray[0] = rowSet.getBigDecimal("roomNum"); //房间套数
            		tmpArray[1] = this.getArea(isBuildArea, isPreArea, rowSet);
            		tmpArray[2] = FDCHelper.ZERO; //成交总价
            		tmpArray[3] = rowSet.getBigDecimal("standAmt"); //标准总价
            		tmpArray[4] = FDCHelper.ZERO; //合同总价
            		tmpArray[5] = FDCHelper.ZERO; //认购总价
            		tmpArray[6] = FDCHelper.ZERO; //销售总价=合同总价+补差款
            		tmpArray[8] = FDCHelper.ZERO;//优惠金额
            		PeriodNoGatheringDataMap.put(tmpKey, tmpArray);
    			}
    		}else
    		{
    			if(PeriodNoGatheringDataMap.get(tmpKey)!=null)
        		{
        			Object[] obj = (Object[])PeriodNoGatheringDataMap.get(tmpKey);
        			BigDecimal roomNum = (BigDecimal)rowSet.getBigDecimal("roomNum");
    				obj[0] = getBigDecimal(obj[0]).add(CRMHelper.getBigDecimal(roomNum));
    				BigDecimal area = (BigDecimal)getArea(isBuildArea, isPreArea, rowSet);
    				obj[1] = getBigDecimal(obj[1]).add(CRMHelper.getBigDecimal(area));
    				BigDecimal dealAmt = (BigDecimal)rowSet.getBigDecimal("dealAmt");
    				obj[2] = getBigDecimal(obj[2]).add(CRMHelper.getBigDecimal(dealAmt));
    				BigDecimal standAmt = (BigDecimal)rowSet.getBigDecimal("standAmt");
    				obj[3] = getBigDecimal(obj[3]).add(CRMHelper.getBigDecimal(standAmt));
    				BigDecimal contractAmt = (BigDecimal)rowSet.getBigDecimal("contractAmt");
    				obj[4] = getBigDecimal(obj[4]).add(CRMHelper.getBigDecimal(contractAmt));
    				BigDecimal purchaseAmt = (BigDecimal)rowSet.getBigDecimal("purchaseAmt");
    				obj[5] = getBigDecimal(obj[5]).add(CRMHelper.getBigDecimal(purchaseAmt));
        			BigDecimal contract = (BigDecimal)obj[4];
        			obj[6] = CRMHelper.getBigDecimal(contract).add(compentAmount);
        			BigDecimal youhui = getBigDecimal(obj[3]).subtract(getBigDecimal(obj[2]));
        			obj[8] = getBigDecimal(obj[8]).add(CRMHelper.getBigDecimal(youhui));
        			PeriodNoGatheringDataMap.put(tmpKey, obj);
        		}else
        		{
        			Object[] tmpArray = new Object[9];
            		tmpArray[0] = rowSet.getBigDecimal("roomNum"); //房间套数
            		tmpArray[1] = this.getArea(isBuildArea, isPreArea, rowSet);
            		BigDecimal dealAmt  = rowSet.getBigDecimal("dealAmt"); //成交总价
            		dealAmt = dealAmt==null?FDCHelper.ZERO:dealAmt;
            	    tmpArray[2] = dealAmt;
            		BigDecimal standAmt = rowSet.getBigDecimal("standAmt"); //标准总价
            		standAmt =  standAmt==null?FDCHelper.ZERO:standAmt;
            		tmpArray[3] = standAmt;
            		tmpArray[4] = rowSet.getBigDecimal("contractAmt"); //合同总价
            		tmpArray[5] = rowSet.getBigDecimal("purchaseAmt"); //认购总价
            		BigDecimal contract = rowSet.getBigDecimal("contractAmt");
            		contract = contract==null?new BigDecimal(0):contract;
            		tmpArray[6] = contract.add(compentAmount); //销售总价=合同总价+补差款
            		tmpArray[8] = standAmt.subtract(dealAmt);
            		PeriodNoGatheringDataMap.put(tmpKey, tmpArray);
        		}
    		}    		   		 	   		
    	}
    	
    	//已签约合同总价
    	builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdType.Fid as prdTypeId, rmodel.fid as rmodelId,sum(purchase.fcontracttotalamount) as contractAmt \n");
    	builder.appendSql("from t_she_purchase purchase \n");
    	builder.appendSql("left join t_she_room room on room.fid = purchase.froomid \n");
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
		builder.appendSql("left join T_SHE_RoomSignContract roomSign on roomSign.fpurchaseid = purchase.fid  \n");
		//签约单只要提交就生效
		builder.appendSql("where (roomSign.fstate='4AUDITTED' or roomSign.fstate='2SUBMITTED') ");
		builder.appendSql(" and ");
//    	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
    	builder.appendSql("purchase.fid in (" + sql + ")");
    	builder.appendSql("\n group by prdType.Fid, rmodel.fid");
    	    	
    	rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
    	{
    		String tmpPrdTypeId = rowSet.getString("prdTypeId");
    		String tmpRmodelId = rowSet.getString("rmodelId");
    		String tmpKey = tmpPrdTypeId + tmpRmodelId;
    		if(PeriodNoGatheringDataMap.get(tmpKey)!=null)
    		{
    			Object[] obj = (Object[])PeriodNoGatheringDataMap.get(tmpKey);
    			obj[7] = rowSet.getBigDecimal("contractAmt");
    			PeriodNoGatheringDataMap.put(tmpKey, obj);
    		}
    	}
    	
    	//获取已售房间下的应收下的实际回款，累计回款不包含其他应收的回款
    	builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdTypeId as prdTypeId, rmodelId as rmodelId,sum(actamount) actamount, \n");
    	builder.appendSql("sum(transAmount) transAmount, sum(refundAmount) refundAmount,sum(justAmount) justAmount \n");
    	builder.appendSql("from ( \n");
    	builder.appendSql("select  prdType.Fid as prdTypeId, rmodel.fid as rmodelId,  \n");
    	builder.appendSql("sum(payLists.factrevamount) actamount, \n");
    	builder.appendSql("sum(payLists.Fhastransferredamount) transAmount, \n");
    	builder.appendSql("sum(payLists.Fhasrefundmentamount) refundAmount, \n");
    	builder.appendSql("sum(payLists.Fhasadjustedamount) justAmount \n");
    	builder.appendSql("from t_she_purchasepaylistentry payLists \n");
    	//builder.appendSql("inner join t_she_moneydefine monDefine on payLists.Fmoneydefineid = monDefine.Fid \n");
    	builder.appendSql("left join t_she_purchase purchase on  payLists.Fheadid = purchase.fid \n");
    	builder.appendSql("left join t_she_room room on room.flastpurchaseid = purchase.fid \n"); //房间上面关联的是一个有效的收款单
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	
    	builder.appendSql("where \n");
//    	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
    	builder.appendSql("purchase.fid in (" + sql + ")");
    	builder.appendSql("group by prdType.Fid, rmodel.fid \n");
    	
//    	builder.appendSql("union \n");
//    	
//    	//获取已售房间下的其他应收明细下的实际回款
//    	builder.appendSql("select  prdType.Fid as prdTypeId, rmodel.fid as rmodelId,  \n");
////    	builder.appendSql("sum(elsePayLists.FactRevAmount-elsePayLists.Fhastransferredamount-elsePayLists.Fhasrefundmentamount-elsePayLists.Fhasadjustedamount) as gatheringAmt \n");
//    	builder.appendSql("sum(elsePayLists.factrevamount) actamount, \n");
//    	builder.appendSql("sum(elsePayLists.Fhastransferredamount) transAmount, \n");
//    	builder.appendSql("sum(elsePayLists.Fhasrefundmentamount) refundAmount, \n");
//    	builder.appendSql("sum(elsePayLists.Fhasadjustedamount) justAmount \n");
//    	builder.appendSql("from t_she_purchaseelsepaylistentry elsePayLists \n");
//    	//builder.appendSql("inner join t_she_moneydefine monDefine on elsePayLists.Fmoneydefineid = monDefine.Fid \n");
//    	builder.appendSql("left join t_she_purchase purchase on  elsePayLists.Fheadid = purchase.fid \n");
//    	builder.appendSql("left join t_she_room room on room.flastpurchaseid = purchase.fid \n"); //房间上面关联的是一个有效的收款单
//    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
//    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
//    	builder.appendSql("where \n");
//    	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
//    	builder.appendSql("group by prdType.Fid, rmodel.fid \n");
    	
    	builder.appendSql(") PayListSum \n");
    	builder.appendSql("group by prdTypeId, rmodelId \n");
    	builder.appendSql("order by prdTypeId, rmodelId asc \n");
    	
//    	logger.info(builder.getTestSql());
    	
    	rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
    	{
    		String tmpKey = rowSet.getString("prdTypeId") + rowSet.getString("rmodelId");
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
    		PeriodGatheringDataMap.put(tmpKey,actamount);
    	}
    	result.put("PeriodNoGatheringData", PeriodNoGatheringDataMap);
    	result.put("PeriodGatheringData", PeriodGatheringDataMap);   	
    	return result;
    }
    
    /**
     * 获取查询区间内的累计回款金额
     * @param ctx
     * @param paramMap
     * @param validPurchaseIds
     * @return
     * @throws BOSException
     * @throws SQLException
     * @throws EASBizException 
     */
    protected Map getPeriodGatheringData(Context ctx, Map paramMap, Set validPurchaseIds) throws BOSException, SQLException, EASBizException
    {
    	Map result = new HashMap();
    	Map purchaseMap = new HashMap();
//    	purchaseMap = getValidPurchaseIdSet(ctx, paramMap);
//    	validPurchaseIds = (Set)purchaseMap.get("purchase");
    	String sql = getValidPurchaseIdSetNew(ctx,paramMap);
    	if(validPurchaseIds.size() <= 0)
    	{
    		return result;
    	}
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdTypeId as prdTypeId, rmodelId as rmodelId, sum(gatheringAmt) as gathAmt \n");
    	builder.appendSql("from ( \n");
    	
    	//获取已收房间下的应收下的实际回款
    	builder.appendSql("select  prdType.Fid as prdTypeId, rmodel.fid as rmodelId,  \n");
    	builder.appendSql("sum(payLists.FactRevAmount-payLists.Fhastransferredamount-payLists.Fhasrefundmentamount-payLists.Fhasadjustedamount) as gatheringAmt \n");
    	builder.appendSql("from t_she_purchasepaylistentry payLists \n");
    	//builder.appendSql("inner join t_she_moneydefine monDefine on payLists.Fmoneydefineid = monDefine.Fid \n");
    	builder.appendSql("left join t_she_purchase purchase on  payLists.Fheadid = purchase.fid \n");
    	builder.appendSql("left join t_she_room room on room.flastpurchaseid = purchase.fid \n"); //房间上面关联的是一个有效的收款单
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	
    	builder.appendSql("where \n");
//    	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
		builder.appendSql("purchase.fid in (" + sql + ")");
    	builder.appendSql("group by prdType.Fid, rmodel.fid \n");
    	
    	builder.appendSql("union \n");
    	
    	//获取已售房间下的其他应收明细下的实际回款
    	builder.appendSql("select  prdType.Fid as prdTypeId, rmodel.fid as rmodelId,  \n");
    	builder.appendSql("sum(elsePayLists.FactRevAmount-elsePayLists.Fhastransferredamount-elsePayLists.Fhasrefundmentamount-elsePayLists.Fhasadjustedamount) as gatheringAmt \n");
    	builder.appendSql("from t_she_purchaseelsepaylistentry elsePayLists \n");
    	//builder.appendSql("inner join t_she_moneydefine monDefine on elsePayLists.Fmoneydefineid = monDefine.Fid \n");
    	builder.appendSql("left join t_she_purchase purchase on  elsePayLists.Fheadid = purchase.fid \n");
    	builder.appendSql("left join t_she_room room on room.flastpurchaseid = purchase.fid \n"); //房间上面关联的是一个有效的收款单
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	builder.appendSql("where \n");
//    	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
    	builder.appendSql("purchase.fid in (" + sql + ")");
    	builder.appendSql("group by prdType.Fid, rmodel.fid \n");
    	
    	builder.appendSql(") PayListSum \n");
    	builder.appendSql("group by prdTypeId, rmodelId \n");
    	builder.appendSql("order by prdTypeId, rmodelId asc \n");
    	
//    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
    	{
    		String tmpKey = rowSet.getString("prdTypeId") + rowSet.getString("rmodelId");
    		result.put(tmpKey, rowSet.getBigDecimal("gathAmt"));
    	}
    	
    	return result;
    }
    /**
     * 取值已售房间当前状态为签约的房间对应补差管理中的补差应收金额
     * @param ctx
     * @param paramMap
     * @param validPurchaseIds
     * @return
     * @throws SQLException 
     * @throws BOSException 
     * @throws EASBizException 
     */
    protected Map getSubAmtData(Context ctx, Map paramMap) throws BOSException, SQLException, EASBizException
    {
    	Map result = new HashMap();
    	
    	//获取已售房间的ID
    	Set selledRoomIds = getSelledRoomIdSet(ctx, paramMap);
    	if(selledRoomIds == null || selledRoomIds.size() == 0)
    	{
    		return result;
    	}
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdType.Fid as prdTypeId, rmodel.fid as rmodelId, sum(areaComp.FCompensateAmount) as compAmt \n");
    	builder.appendSql("from T_SHE_RoomAreaCompensate areaComp \n");
//    	builder.appendSql("left join T_SHE_AreaCompensateRevList comRevList on areaComp.fid = comRevList.fheadid  \n");
    	builder.appendSql("left join t_she_room room on room.fid = areaComp.Froomid \n");
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	builder.appendSql("where areaComp.Fcompensatestate in ('COMAUDITTED', 'COMRECEIVED') and \n");
    	builder.appendParam("room.fid", selledRoomIds.toArray());
//    	builder.appendSql("\n and room.FSellState = 'Sign'\n");
    	builder.appendSql("group by prdType.Fid, rmodel.fid");
    	
    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	
    	while(rowSet.next())
    	{
    		String tmpKey = rowSet.getString("prdTypeId") + rowSet.getString("rmodelId");
    		result.put(tmpKey, rowSet.getBigDecimal("compAmt"));
    	}
    	
    	return result;
    }
    
    public static BigDecimal getBigDecimal(Object obj){
    	if(obj==null)
    	{
    		return FDCHelper.ZERO;
    	}else
    	{
    		if(obj instanceof BigDecimal)
    		{
    			return (BigDecimal)obj;
    		}else
    		{
    			return FDCHelper.ZERO;
    		}
    	}
	}
    
    ///////////////////////////////////////////////区间查询-end/////////////////////////////////////////////////////////////

	/**
	 * selectNode = 所选树的节点，如果没有选，则设为当前组织<br>
	 * IncludeAttachment = 包括附属房产，为真是包括，为假时不包括<br>
	 * BuildArea = 表示面积类型，为真是建筑面积，为假时为套内面积<br>
	 * BeginDate = 开始日期<br>
	 * EndDate = 结束日期<br>
	 * IncludeOrder = 包括预订业务， 为真是包括，为假时不包括<br>
	 * IsAuditDate = 已那个时间为标准（为真时以审批日期为标准，为假时已对应的日期为标准）<br>
	 * 
	 * 原方法返回id集合，超过2000条时报错，现改为返回一个查询语句，在使用时用 in select 模式<br>
	 * 
	 * @see <code>getValidPurchaseIdSet(Context ctx, Map paramMap)</code><br>
	 * 
	 * @author jiyu_guan<br>
	 * @throws EASBizException
	 */
	public static String getValidPurchaseIdSetNew(Context ctx, Map paramMap)
			throws BOSException, SQLException, EASBizException {

		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select pch.FID ");
		sql.appendSql(" from t_she_purchase as pch ");
		sql.appendSql(" left join t_she_room room on room.fid = pch.FRoomId ");
		sql
				.appendSql(" left join t_she_quitroom qr on qr.fpurchaseid = pch.fid ");
		sql
				.appendSql(" left join t_she_changeroom cr on cr.FOldPurchaseID = pch.fid ");
		// 选择节点过滤
		appendSelectedNodeFilter(sql, paramMap, ctx);
		Boolean all = (Boolean) paramMap.get("isShowAll");
		boolean isShowAll = true;
		if (all == null || !all.booleanValue()) {
			isShowAll = false;
		}
//		boolean isShowAll = ((Boolean) paramMap.get("isShowAll"))
//				.booleanValue();
		boolean includeOrder = ((Boolean) paramMap.get("IncludeOrder"))
				.booleanValue();
		final DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = (Date) paramMap.get("BeginDate");
		Date endDate = (Date) paramMap.get("EndDate");
		String strBg;
		
		// 是否显示全部
		if (isShowAll || beginDate == null) {
			strBg = "1900-01-01";
		} else {
			strBg = FORMAT_DAY.format(beginDate);
		}
		String strEd;
		if (isShowAll || beginDate == null) {
			strEd = "2999-12-31";
		} else {
			strEd = FORMAT_DAY.format(endDate);
		}

		// 包括预定业务
		if (includeOrder) {
			sql.appendSql(" and pch.FTosaleDate >= {ts '");
			sql.appendSql(strBg);
			sql.appendSql("'} ");
			sql.appendSql(" and pch.FTosaleDate < {ts '");
			sql.appendSql(strEd);
			sql.appendSql("'} ");
		}
		// 不包括预定
		else {
			sql.appendSql(" and pch.FTopurchaseDate >= {ts '");
			sql.appendSql(strBg);
			sql.appendSql("'} ");
			sql.appendSql(" and pch.FTopurchaseDate < {ts '");
			sql.appendSql(strEd);
			sql.appendSql("'} ");
		}
		// 包括预定业务
		if (includeOrder) {
			sql
					.appendSql(" and pch.fpurchasestate in ('PurchaseApply', 'PurchaseAuditing','PrePurchaseCheck', 'PurchaseAudit', 'PurchaseChange','QuitRoomBlankOut','ChangeRoomBlankOut') ");
		} else {
			sql
					.appendSql(" and pch.fpurchasestate in ('PurchaseApply', 'PurchaseAuditing','PurchaseAudit', 'PurchaseChange','QuitRoomBlankOut','ChangeRoomBlankOut') ");
		}
		sql.appendSql("and pch.ftosaledate is not null \n");
		// 是否包括附属房产
		boolean includeAttach = ((Boolean) (paramMap.get("IncludeAttachment")))
				.booleanValue();
		if (includeAttach) {
			sql
					.appendSql(" and room.FHouseproperty in ('Attachment','NoAttachment') ");
		} else {
			sql.appendSql(" and room.FHouseproperty = 'NoAttachment' ");
		}
		// 无退房单或退房单未审批或审批时间不在选择时间内
		sql.appendSql(" and (qr.FID is null or qr.FState <> '4AUDITTED' ");
		sql.appendSql(" or qr.FAudittime < {ts '");
		sql.appendSql(strBg);
		sql.appendSql("'} or qr.FAudittime > {ts '");
		sql.appendSql(strEd);
		sql.appendSql("'}) ");
		// 对应的认购单还需要考虑到在时间范围内有没有对应的换房单，如果没有则计入统计
		sql.appendSql(" and (cr.FID is null or cr.FState <> '4AUDITTED' ");
		sql.appendSql(" or cr.FAudittime < {ts '");
		sql.appendSql(strBg);
		sql.appendSql("'} or cr.FAudittime > {ts '");
		sql.appendSql(strEd);
		sql.appendSql("'}) ");
		
		return sql.getTestSql();
	}
    
}