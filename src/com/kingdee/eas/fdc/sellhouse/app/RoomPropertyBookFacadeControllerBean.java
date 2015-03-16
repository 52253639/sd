package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchBooksFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaFactory;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.jdbc.rowset.IRowSet;

public class RoomPropertyBookFacadeControllerBean extends AbstractRoomPropertyBookFacadeControllerBean
{
	private static Logger logger =
		Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomPropertyBookFacadeControllerBean");

	private final String main_id = "id";
	private final String main_orgUnit = "orgUnit.name";
	private final String main_sellProject = "sellProject.name";
	private final String main_subArea = "subarea.name";
	private final String main_build = "building.name";
	private final String main_roomUnit = "room.unit";

	private final String main_number = "displayName";
	private final String main_roomNo = "roomNo";
	private final String main_batch = "batch.number";
	private final String main_scheme = "propertyDoScheme.name";
	private final String main_bookNum = "roomPropertyBook.number";
	private final String main_bookState = "roomBookState";

	private final String main_step = "step";
	private final String main_bookTranDate = "roomPropertyBook.transactDate";
	private final String main_customer = "purchase.customerNames";
	private final String main_customerPhone = "purchase.customerPhones";
	private final String main_customerCard = "purchase.customerIDCards";
	private final String main_propertyState = "propertyState";

	private final String main_transactor = "transactor.name";
	private final String main_buildId = "building.id";
	private final String main_bookId = "roomPropertyBook.id";
	private final String main_sellProjectId = "sellProject.id";
	
	private final String main_schemeId = "scheme.id";
	private final String main_orgUnitLongNumber = "orgUnit.longNumber";
	private final String main_batchId = "batch.id";

	protected Map _getMutilRoomPropertyCollection(Context ctx, Object selectedObj, 
			Map paramMap)throws BOSException, EASBizException
	{
		Map result = new HashMap();

		try {
			List bookList = getMutilRoomBook(ctx, selectedObj, paramMap);
			result.put("MutilBook", bookList);

			RoomPropertyBatchCollection batchCols = getRoomBookBatch(ctx, selectedObj, paramMap);
			result.put("BatchBook", batchCols);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private List getMutilRoomBook(Context ctx, Object selectedObj, Map paramMap) throws BOSException, SQLException, EASBizException
	{
		//根据选择的节点不同，获取不同范围的值
		UserInfo user = (UserInfo)paramMap.get("userInfo");
		Set sellPrjSet = MarketingUnitFactory.getLocalInstance(ctx).getPermitSellProjectIdSet(user);
		List sellPrjIdList = new ArrayList();
		Iterator sellIter = sellPrjSet.iterator();
		while(sellIter.hasNext())
		{
			String tmpId = String.valueOf(sellIter.next());
			sellPrjIdList.add(tmpId);
		}
		
		FDCSQLBuilder bookBuilder = new FDCSQLBuilder(ctx);

		bookBuilder.appendSql("SELECT \n");
		bookBuilder.appendSql("ROOM.FID AS ID, \n");
		bookBuilder.appendSql("ORGUNIT.FName_l2 AS ORGUNITNAME, \n");
		bookBuilder.appendSql("SELLPROJECT.FName_l2 AS SELLPROJECTNAME, \n");
		bookBuilder.appendSql("SUBAREA.FName_l2 AS SUBAREANAME, \n");
		bookBuilder.appendSql("BUILDUNIT.FName_l2 AS BUILDUNITNAME, \n");
		bookBuilder.appendSql("BUILDING.FName_l2 AS BUILDINGNAME, \n");
		bookBuilder.appendSql("ROOM.FDisplayName AS RoomNUMBER, \n");
		bookBuilder.appendSql("ROOM.FRoomNo AS ROOMNO, \n");
		bookBuilder.appendSql("BATCH.FNumber AS BATCHNUMBER, \n");
		bookBuilder.appendSql("PROPERTYDOSCHEME.FName_l2 AS PROPERTYDOSCHEMENAME, \n");
		bookBuilder.appendSql("ROOMPROPERTYBOOK.FNumber AS ROOMPROPERTYBOOKNUMBER, \n");
		bookBuilder.appendSql("ROOM.FRoomBookState AS ROOMBOOKSTATE, \n");
		bookBuilder.appendSql("ROOMPROPERTYBOOK.FStep AS STEP, \n");
		bookBuilder.appendSql("ROOMPROPERTYBOOK.FTransactDate AS ROOMPROPERTYBOOKTRANSACTDATE, \n");
		bookBuilder.appendSql("PURCHASE.FCustomerNames AS PURCHASECUSTOMERNAMES, \n");
		bookBuilder.appendSql("PURCHASE.FCustomerPhones AS PURCHASECUSTOMERPHONES, \n");
		bookBuilder.appendSql("PURCHASE.FCustomerIDCards AS PURCHASECUSTOMERIDCARDS, \n");
		bookBuilder.appendSql("ROOMPROPERTYBOOK.FPropertyState AS PROPERTYSTATE, \n");
		bookBuilder.appendSql("TRANSACTOR.FName_l2 AS TRANSACTORNAME, \n");
		bookBuilder.appendSql("ROOM.FBuildingID AS BUILDINGID, \n");
		bookBuilder.appendSql("ROOMPROPERTYBOOK.FID AS ROOMPROPERTYBOOKID, \n");
		bookBuilder.appendSql("BUILDING.FSellProjectID AS SELLPROJECTID, \n");
		bookBuilder.appendSql("ROOM.FSellState AS SELLSTATE, \n");
		bookBuilder.appendSql("ROOMSIGNCONTRACT.FIsBlankOut AS ROOMSIGNCONTRACTISBLANKOUT, \n");
		bookBuilder.appendSql("PURCHASE.FPurchaseState AS PURCHASEPURCHASESTATE, \n");
		bookBuilder.appendSql("PURCHASE.FID AS PURCHASEID, \n");
		bookBuilder.appendSql("ORGUNIT.FLongNumber AS ORGUNITLONGNUMBER, \n");
		bookBuilder.appendSql("BUILDING.FSubareaID AS SUBAREAID, \n");
		bookBuilder.appendSql("PROPERTYDOSCHEME.FID AS SCHEMEID, \n");
		bookBuilder.appendSql("BATCH.FID AS BATCHID \n");
		bookBuilder.appendSql("FROM T_SHE_Room  ROOM \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_SHE_Building  BUILDING ON ROOM.FBuildingID = BUILDING.FID \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_SHE_Purchase  PURCHASE ON ROOM.FID = PURCHASE.FRoomID \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_SHE_RoomSignContract  ROOMSIGNCONTRACT ON ROOM.FID = ROOMSIGNCONTRACT.FRoomID \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_SHE_RoomPropertyBook  ROOMPROPERTYBOOK ON ROOM.FID = ROOMPROPERTYBOOK.FRoomID \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_SHE_BuildingUnit BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_SHE_SellProject  SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_SHE_Subarea  SUBAREA ON BUILDING.FSubareaID = SUBAREA.FID \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_ORG_BaseUnit  ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_PM_User  SALESMAN ON PURCHASE.FSalesmanID = SALESMAN.FID \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_SHE_SHEPayType  PAYTYPE ON PURCHASE.FPayTypeID = PAYTYPE.FID \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_PM_User  TRANSACTOR ON ROOMPROPERTYBOOK.FTransactorID = TRANSACTOR.FID \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_PM_User  CREATOR ON ROOMPROPERTYBOOK.FCreatorID = CREATOR.FID \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_SHE_RoomPropertyBatch  BATCH ON ROOMPROPERTYBOOK.FBatchID = BATCH.FID \n");
		bookBuilder.appendSql("LEFT OUTER JOIN T_SHE_PropertyDoScheme  PROPERTYDOSCHEME ON ROOMPROPERTYBOOK.FPropertyDoSchemeId = PROPERTYDOSCHEME.FID \n");
		bookBuilder.appendSql("WHERE ROOM.FSellState = 'Sign' AND PURCHASE.FPurchaseState = 'PurchaseAudit' AND ROOMSIGNCONTRACT.FIsBlankOut = 0 \n");

		if(selectedObj instanceof OrgStructureInfo)
		{
			String longNumber = ((OrgStructureInfo)selectedObj).getLongNumber();
			longNumber = longNumber + '%'; 
			bookBuilder.appendSql(" and ORGUNIT.FLongNumber like '" + longNumber + "'");
			
			if(sellPrjIdList.size() > 0)
			{
				bookBuilder.appendSql(" and ");
				bookBuilder.appendParam("BUILDING.FSellProjectID", sellPrjIdList.toArray());
			}
			
		}
		else if(selectedObj instanceof SellProjectInfo)
		{
			String sellPrjId = ((SellProjectInfo)selectedObj).getId().toString();
			bookBuilder.appendSql(" and ");
			bookBuilder.appendParam("BUILDING.FSellProjectID", sellPrjId);
			if(((SellProjectInfo)selectedObj).getOrgUnit() != null && ((SellProjectInfo)selectedObj).getOrgUnit().getId() != null)
			{
				String orgUnitId = String.valueOf(((SellProjectInfo)selectedObj).getOrgUnit().getId());
				bookBuilder.appendSql(" and ORGUNIT.FID = '" + orgUnitId + "'");
			}
			else
			{
				String longNumber = String.valueOf(paramMap.get("orgUnitLongNumber"));
				longNumber = longNumber + '%'; 
				bookBuilder.appendSql(" and ORGUNIT.FLongNumber like '" + longNumber + "'");
			}

		}
		else if(selectedObj instanceof SubareaInfo)
		{
			String subAreaId = ((SubareaInfo)selectedObj).getId().toString();
			bookBuilder.appendSql(" and ");
			bookBuilder.appendParam("BUILDING.FSubareaID", subAreaId);
			
			String longNumber = String.valueOf(paramMap.get("orgUnitLongNumber"));
			longNumber = longNumber + '%'; 
			bookBuilder.appendSql(" and ORGUNIT.FLongNumber like '" + longNumber + "'");
		}
		else if(selectedObj instanceof BuildingUnitInfo)
		{
			String unitId = ((BuildingUnitInfo)selectedObj).getId().toString();
			bookBuilder.appendSql(" and ");
			bookBuilder.appendParam("BUILDUNIT.FID", unitId);
			
			String longNumber = String.valueOf(paramMap.get("orgUnitLongNumber"));
			longNumber = longNumber + '%'; 
			bookBuilder.appendSql(" and ORGUNIT.FLongNumber like '" + longNumber + "'");
		}
		else if(selectedObj instanceof BuildingInfo)
		{
			String unitId = ((BuildingInfo)selectedObj).getId().toString();
			bookBuilder.appendSql(" and ");
			bookBuilder.appendParam("BUILDING.FID", unitId);
			String longNumber = String.valueOf(paramMap.get("orgUnitLongNumber"));
			longNumber = longNumber + '%'; 
			bookBuilder.appendSql(" and ORGUNIT.FLongNumber like '" + longNumber + "'");
		}
		else 
		{

		}

		bookBuilder.appendSql(" \n ");
		bookBuilder.appendSql("ORDER BY \n");
		bookBuilder.appendSql("ORGUNITLONGNUMBER , SELLPROJECT.FNumber, SUBAREA.FNumber, BUILDUNIT.FNumber, BUILDING.FNumber, RoomNUMBER ASC \n");

		IRowSet rowSet = bookBuilder.executeQuery(ctx);

		List bookList = new ArrayList();
		while(rowSet.next())
		{
			Map book = new HashMap();
			
			book.put(main_id, rowSet.getString("ID"));
			book.put(main_orgUnit, rowSet.getString("ORGUNITNAME"));
			book.put(main_sellProject, rowSet.getString("SELLPROJECTNAME"));
			book.put(main_subArea, rowSet.getString("SUBAREANAME"));
			book.put(main_roomUnit, rowSet.getString("BUILDUNITNAME"));
			
			book.put(main_build, rowSet.getString("BUILDINGNAME"));
			book.put(main_number, rowSet.getString("RoomNUMBER"));
			book.put(main_roomNo, rowSet.getString("ROOMNO"));
			book.put(main_batch, rowSet.getString("BATCHNUMBER"));
			book.put(main_scheme, rowSet.getString("PROPERTYDOSCHEMENAME"));
			
			book.put(main_bookNum, rowSet.getString("ROOMPROPERTYBOOKNUMBER"));
			
			//更新为别名
			RoomBookStateEnum bookState = RoomBookStateEnum.getEnum(rowSet.getString("ROOMBOOKSTATE"));
			book.put(main_bookState, bookState);
			
			book.put(main_step, rowSet.getString("STEP"));
			book.put(main_bookTranDate, rowSet.getDate("ROOMPROPERTYBOOKTRANSACTDATE"));
			book.put(main_customer, rowSet.getString("PURCHASECUSTOMERNAMES"));
			
			book.put(main_customerPhone, rowSet.getString("PURCHASECUSTOMERPHONES"));
			book.put(main_customerCard, rowSet.getString("PURCHASECUSTOMERIDCARDS"));
			
			//更新为别名
			PropertyStateEnum propertyState = PropertyStateEnum.getEnum(rowSet.getString("PROPERTYSTATE"));
			book.put(main_propertyState, propertyState);
			
			book.put(main_transactor, rowSet.getString("TRANSACTORNAME"));
			book.put(main_buildId, rowSet.getString("BUILDINGID"));
			
			book.put(main_bookId, rowSet.getString("ROOMPROPERTYBOOKID"));
			book.put(main_sellProjectId, rowSet.getString("SELLPROJECTID"));
			book.put(main_schemeId, rowSet.getString("SCHEMEID"));
			book.put(main_orgUnitLongNumber, rowSet.getString("ORGUNITLONGNUMBER"));
			
			book.put(main_batchId, rowSet.getString("BATCHID"));
			
			bookList.add(book);
		}

		return bookList;
	}

	private RoomPropertyBatchCollection getRoomBookBatch(Context ctx, Object selectedObj, Map paramMap) throws BOSException, EASBizException
	{
		UserInfo user = (UserInfo)paramMap.get("userInfo");
		Set sellPrjSet = MarketingUnitFactory.getLocalInstance(ctx).getPermitSellProjectIdSet(user);
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("orgUnit.id");
		view.getSelector().add("orgUnit.name");
		view.getSelector().add("orgUnit.longNumber");
		view.getSelector().add("sellProject.id");
		view.getSelector().add("sellProject.name");
		view.getSelector().add("transactor.id");
		view.getSelector().add("transactor.name");

		FilterInfo filter = new FilterInfo();
		
		if(selectedObj instanceof OrgStructureInfo)
		{
			String longNumber = ((OrgStructureInfo)selectedObj).getLongNumber();

			filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", longNumber + '%', CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellPrjSet, CompareType.INCLUDE));
		}
		else if(selectedObj instanceof SellProjectInfo)
		{
			String sellPrjId = ((SellProjectInfo)selectedObj).getId().toString();
			if(((SellProjectInfo)selectedObj).getOrgUnit() != null && ((SellProjectInfo)selectedObj).getOrgUnit().getId() != null)
			{
				String orgUnitId = String.valueOf(((SellProjectInfo)selectedObj).getOrgUnit().getId());
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgUnitId));
			}
			else
			{
				String longNumber = String.valueOf(paramMap.get("orgUnitLongNumber"));
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", longNumber + '%', CompareType.LIKE));
			}
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellPrjId));
			

		}
		else if(selectedObj instanceof SubareaInfo)
		{
			IObjectPK pk = new ObjectUuidPK(((SubareaInfo)selectedObj).getId());
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("sellProject.id");
			sic.add("sellProject.number");
			sic.add("sellProject.orgUnit.id");
			sic.add("sellProject.orgUnit.number");
			
			SubareaInfo areaFullInfo = SubareaFactory.getLocalInstance(ctx).getSubareaInfo(pk, sic);
			
			String sellPrjId = String.valueOf(areaFullInfo.getSellProject().getId());
			String orgUnitId = String.valueOf(areaFullInfo.getSellProject().getOrgUnit().getId());
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellPrjId));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgUnitId));
			
		}
		else if(selectedObj instanceof BuildingUnitInfo)
		{
			IObjectPK pk = new ObjectUuidPK(((BuildingUnitInfo)selectedObj).getId());
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("building.id");
			sic.add("building.sellProject.id");
			
			BuildingUnitInfo buildUnit = BuildingUnitFactory.getLocalInstance(ctx).getBuildingUnitInfo(pk, sic);
			String sellPrjId = String.valueOf(buildUnit.getBuilding().getSellProject().getId());
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellPrjId));
		}
		else if(selectedObj instanceof BuildingInfo)
		{
			IObjectPK pk = new ObjectUuidPK(((BuildingInfo)selectedObj).getId());
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("sellProject.id");
			
			BuildingInfo build = BuildingFactory.getLocalInstance(ctx).getBuildingInfo(pk, sic);
			String sellPrjId = String.valueOf(build.getSellProject().getId());
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellPrjId));
		}
		else
		{
			return null;
		}
		
		view.setFilter(filter);
		
		RoomPropertyBatchCollection batches = RoomPropertyBatchFactory.getLocalInstance(ctx).getRoomPropertyBatchCollection(view);

		return batches;
	}

	protected void _updateRoomProperty(Context ctx, Map paramMap)
			throws BOSException, EASBizException {
		String roomId = String.valueOf(paramMap.get("RoomId"));
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("room.id");
		view.getSelector().add("room.number");
		view.getSelector().add("room.name");
		view.getSelector().add("batch.id");
		view.getSelector().add("batch.number");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", roomId));
		view.setFilter(filter);
		
		RoomPropertyBookCollection bookCols = RoomPropertyBookFactory.getLocalInstance(ctx).getRoomPropertyBookCollection(view);
		if(bookCols != null && bookCols.size() == 1)
		{
			RoomPropertyBookInfo tmpBookInfo = bookCols.get(0);
			
			RoomPropertyBookInfo tmpUpdateBookInfo = new RoomPropertyBookInfo();
			tmpUpdateBookInfo.setId(tmpBookInfo.getId());
			tmpUpdateBookInfo.setBatch(null);
			
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("batch");
			
			RoomPropertyBookFactory.getLocalInstance(ctx).updatePartial(tmpUpdateBookInfo, sic);
			
			String bookId = String.valueOf(tmpBookInfo.getId());
			FilterInfo deleteFilter = new FilterInfo();
			deleteFilter.getFilterItems().add(new FilterItemInfo("book.id", bookId));
			if(RoomPropertyBatchBooksFactory.getLocalInstance(ctx).exists(deleteFilter))
			{
				RoomPropertyBatchBooksFactory.getLocalInstance(ctx).delete(deleteFilter);
			}
		}
		
	}

	protected Map _getStepAndMatarilState(Context ctx, Map paramMap)
			throws BOSException, EASBizException {
		Set roomIdSet = (HashSet)paramMap.get("RoomId");
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("room.id");
		view.getSelector().add("room.number");
		view.getSelector().add("room.name");
		view.getSelector().add("entry.*");
		view.getSelector().add("entryTwo.*");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", roomIdSet, CompareType.INCLUDE));
		view.setFilter(filter);
		
		RoomPropertyBookCollection bookCols = RoomPropertyBookFactory.getLocalInstance(ctx).getRoomPropertyBookCollection(view);
		
		Map selectMap = new HashMap();
		for(int index = 0; index < bookCols.size(); ++index)
		{
			RoomPropertyBookInfo tmpBookInfo = bookCols.get(index);
			//步骤
			RoomPropertyBookEntryCollection stepCols = tmpBookInfo.getEntry();
			for(int stepIndex = 0; stepIndex < stepCols.size(); ++stepIndex)
			{
				String tmpKey = "Step"+stepCols.get(stepIndex).getName();
				if(selectMap.containsKey(tmpKey))
				{
					Boolean isFinish = (Boolean)selectMap.get(tmpKey);
					if(!isFinish.booleanValue() || !stepCols.get(stepIndex).isIsFinish())
					{
						selectMap.put(tmpKey, Boolean.FALSE);
					}
				}
				else
				{
					Boolean isFinish = Boolean.valueOf(stepCols.get(stepIndex).isIsFinish());
					selectMap.put(tmpKey, isFinish);
				}
			}
			
			//资料
			RoomPropertyBookEntryTwoCollection matCols = tmpBookInfo.getEntryTwo();
			
			for(int stepIndex = 0; stepIndex < matCols.size(); ++stepIndex)
			{
				String tmpKey = "Material"+matCols.get(stepIndex).getName();
				if(selectMap.containsKey(tmpKey))
				{
					Boolean isFinish = (Boolean)selectMap.get(tmpKey);
					if(!isFinish.booleanValue() || !matCols.get(stepIndex).isIsFinish())
					{
						selectMap.put(tmpKey, Boolean.FALSE);
					}
				}
				else
				{
					Boolean isFinish = Boolean.valueOf(matCols.get(stepIndex).isIsFinish());
					selectMap.put(tmpKey, isFinish);
				}
			}
		}
		
		return selectMap;
	}
}