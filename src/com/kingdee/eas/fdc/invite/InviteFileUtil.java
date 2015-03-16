package com.kingdee.eas.fdc.invite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.INewListingColumn;
import com.kingdee.eas.fdc.invite.INewListingEntry;
import com.kingdee.eas.fdc.invite.NewListingColumnCollection;
import com.kingdee.eas.fdc.invite.NewListingColumnFactory;
import com.kingdee.eas.fdc.invite.NewListingColumnInfo;
import com.kingdee.eas.fdc.invite.NewListingEntryCollection;
import com.kingdee.eas.fdc.invite.NewListingEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.NewListingPageCollection;
import com.kingdee.eas.fdc.invite.NewListingPageFactory;
import com.kingdee.eas.fdc.invite.NewListingPageInfo;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.eas.fdc.invite.NewListingValueFactory;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.eas.fdc.invite.NewQuotingPriceCollection;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum;

public class InviteFileUtil {

	public static EntityViewInfo getEntityView(String[] strs){
		if(strs==null||strs.length<1){
			return new EntityViewInfo();
		}
		EntityViewInfo view=new EntityViewInfo();
		for(int i=0;i<strs.length;i++){
			view.getSelector().add(strs[i]);
		}
		return view;
	}
	public static  boolean isIn0to1(BigDecimal rate){
		if(rate==null){
			return false;
		}
		return rate.floatValue()<1&&rate.floatValue()>0;
	}
	public static String  getFID(Object obj2){
		if(!(obj2 instanceof IObjectValue)){
			return null;
		}
		Object obj=((IObjectValue)obj2).get("id");
		return obj.toString();
	}
	
	public Map getPM(String id){
		Map map=new HashMap();
		
		
		return map;
	}
	

	
	static private void setRankingsRow(Map re_Map,NewQuotingPriceCollection quotingPrices, Map values, NewListingEntryInfo entry, NewListingColumnInfo aimCol) {
	
		Map map = new TreeMap();
		Iterator iter_columnMap=quotingPrices.iterator();
		
		while(iter_columnMap.hasNext()){
			
		        NewQuotingPriceInfo quoting = (NewQuotingPriceInfo)iter_columnMap.next();
				if (quoting.getId() != null) {
					NewListingValueInfo valueInfo = (NewListingValueInfo) values
							.get(entry.getId().toString()
									+ aimCol.getId().toString()
									+ quoting.getId().toString());
					BigDecimal value = FDCHelper.ZERO;
					if (valueInfo != null
							&& valueInfo.getAmount() != null) {
						value = valueInfo.getAmount();
					}
					List sameList = null;
					
					//TODO  genggai
					if(value.floatValue()==0||value==null){
						value=new BigDecimal(Integer.MAX_VALUE);
					}
					//TODO genggai
					
					
					if (map.containsKey(value)) {
						sameList = (List) map.get(value);
					} else {
						sameList = new ArrayList();
						map.put(value, sameList);
					}
					sameList.add(quoting.getSupplier().getId());
				}
	 }
		int compositor = quotingPrices.size();
		Collection valCol = map.values();
		for (Iterator iter = valCol.iterator(); iter.hasNext();) {
			List sameList = (List) iter.next();
			for (int j = 0; j < sameList.size(); j++) {
				Object index = sameList.get(j);
				if(re_Map.containsKey(index)){
					List list=(List) re_Map.get(index);
					list.add(new Integer(compositor));
				}else{
					List list=new ArrayList();
					re_Map.put(index,list);
					list.add(new Integer(compositor));
				}
				//TODO genggai
//				row.getCell(index.intValue()).setValue(new Integer(compositor));
//				row.getCell(index.intValue()).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
			}
			compositor -= sameList.size();
		}
	}
	static public  Map  loadPages(String fileID) {
		try {
			NewListingPageCollection tables= getlisting(fileID).getPages();
			NewQuotingPriceCollection quotingPrices=getQuotingPrice(fileID);
			if(tables==null){
				return null;
			}
			
			for(int i=0;i<tables.size();i++){
				NewListingPageInfo info=tables.get(i);
				return setTableRow(info,quotingPrices);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return null;
	}
	static private Map setTableRow(NewListingPageInfo page,NewQuotingPriceCollection quotingPrices) {
		NewListingValueCollection values1 = getListPageValues(page,quotingPrices);
		Map values = new HashMap();
		for (int i = 0; i < values1.size(); i++) {
			NewListingValueInfo valueInfo = values1.get(i);
			String entryId = valueInfo.getEntry().getId().toString();
			String colId = valueInfo.getColumn().getId().toString();
			String quotingId = "";
			if (valueInfo.getQuotingPrice() != null) {
				quotingId = valueInfo.getQuotingPrice().getId().toString();
			}
			String key = entryId + colId + quotingId;
			values.put(key, valueInfo);
		}

		NewListingEntryCollection collEntry = page.getEntrys();
		NewListingColumnCollection quotingCol = new NewListingColumnCollection();
		NewListingColumnInfo totalCol = null;
//		NewListingColumnInfo projectAmtCol = null;
		NewListingColumnInfo priceSumCol = null;
		for (int i = 0; i < page.getColumns().size(); i++) {
			NewListingColumnInfo colInfo = page.getColumns().get(i);
			if (colInfo.isIsQuoting()) {
				quotingCol.add(colInfo);
			}
			if (colInfo.getHeadColumn().getProperty().equals(
					DescriptionEnum.AmountSum)) {
				totalCol = colInfo;
			}
			if (colInfo.getHeadColumn().getProperty().equals(
					DescriptionEnum.TotalPriceSum)) {
				priceSumCol = colInfo;
			}
//			if (colInfo.getHeadColumn().getProperty().equals(
//					DescriptionEnum.ProjectAmtSum)) {
//				projectAmtCol = colInfo;
//			}
		}
		Map map_order=new HashMap();
		int sizeEntry = collEntry.size();
		for (int i = 0; i < sizeEntry; i++) {
			NewListingEntryInfo entry = collEntry.get(i);
			NewListingColumnInfo aimCol = null;
			if (entry.isIsLeaf()) {
				aimCol = priceSumCol;
			} else {
				aimCol = totalCol;
			}
			//ÅÅÃû
			
			setRankingsRow(map_order,quotingPrices,values, entry, aimCol);

		}
	
		return map_order;

	}
	static private NewListingInfo getlisting(String inviteListingId) throws Exception {
		NewListingInfo listing = null;
		listing = NewListingFactory.getRemoteInstance().getNewListingInfo(new ObjectUuidPK(BOSUuid.read(inviteListingId)));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", listing.getId().toString()));
		SelectorItemCollection sic = view.getSelector();
		sic.add("*");
		sic.add("pageHead.*");
		sic.add("headType.*");
		sic.add("columns.*");
		sic.add("columns.headColumn.*");
		sic.add("entrys.*");
		sic.add("sumEntry.*");
		view.getSorter().add(new SorterItemInfo("seq"));
		NewListingPageCollection sheets = NewListingPageFactory
				.getRemoteInstance().getNewListingPageCollection(view);
		listing.getPages().clear();
		listing.getPages().addCollection(sheets);

		INewListingColumn iListingCol = NewListingColumnFactory
				.getRemoteInstance();
		INewListingEntry iListingEntry = NewListingEntryFactory
				.getRemoteInstance();
		for (int i = 0; i < sheets.size(); i++) {
			NewListingPageInfo sheet = sheets.get(i);
			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("page.id", sheet.getId().toString()));
			sic = view.getSelector();
			sic.add("*");
			sic.add("headColumn.*");
			sic.add("headColumn.parent.*");
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingColumnCollection columns = iListingCol
					.getNewListingColumnCollection(view);
			sheet.getColumns().clear();
			sheet.getColumns().addCollection(columns);

			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("item.*");
			view.getSelector().add("values.*");
			view.getSelector().add("values.column.*");
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", sheet.getId().toString()));
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingEntryCollection entrys = iListingEntry
					.getNewListingEntryCollection(view);
			sheet.getEntrys().clear();
			sheet.getEntrys().addCollection(entrys);
		}
		return listing;
	}
	static	private NewQuotingPriceCollection getQuotingPrice(String inviteListingId) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("supplier.*");
		filter.getFilterItems().add(new FilterItemInfo("listing.id", inviteListingId));
		filter.getFilterItems().add(new FilterItemInfo("hasEffected", Boolean.TRUE));
		NewQuotingPriceCollection quotingPrices = NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceCollection(view);
		return quotingPrices;
	}
	static private NewListingValueCollection getListPageValues(NewListingPageInfo page,NewQuotingPriceCollection quotingPrices) {
		NewListingValueCollection values1 = null;
		EntityViewInfo view1 = new EntityViewInfo();
		FilterInfo filter1 = new FilterInfo();
		filter1.getFilterItems().add(
				new FilterItemInfo("entry.head", page.getId().toString()));
		filter1.getFilterItems().add(
				new FilterItemInfo("quotingPrice.id", null));
		view1.setFilter(filter1);
		view1.getSelector().add(new SelectorItemInfo("*"));
		view1.getSelector().add(new SelectorItemInfo("entry.*"));
		view1.getSelector().add(new SelectorItemInfo("column.*"));

		NewListingValueCollection values2 = null;
		EntityViewInfo view2 = new EntityViewInfo();
		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(
				new FilterItemInfo("entry.head", page.getId().toString()));
		filter2.getFilterItems().add(
				new FilterItemInfo("type", QuotingPriceTypeEnum.modify));
		Set quotingSet=new HashSet();
		for (int i = 0; i < quotingPrices.size(); i++) {
			quotingSet.add(quotingPrices.get(i).getId().toString());
		}
		filter2.getFilterItems().add(new FilterItemInfo("quotingPrice.id", quotingSet,CompareType.INCLUDE));
		view2.setFilter(filter2);
		view2.getSelector().add(new SelectorItemInfo("*"));
		view2.getSelector().add(new SelectorItemInfo("entry.*"));
		view2.getSelector().add(new SelectorItemInfo("column.*"));

		try {
			values1 = NewListingValueFactory.getRemoteInstance()
					.getNewListingValueCollection(view1);
			values2 = NewListingValueFactory.getRemoteInstance()
					.getNewListingValueCollection(view2);
			values1.addCollection(values2);

		} catch (BOSException e) {
			e.printStackTrace();
		}
		return values1;
	}

	
	
	
	
	
	
	
	
	
}
