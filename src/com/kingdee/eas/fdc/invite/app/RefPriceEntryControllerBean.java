package com.kingdee.eas.fdc.invite.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.invite.HeadColumnFactory;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.RefPriceCollection;
import com.kingdee.eas.fdc.invite.RefPriceEntryCollection;
import com.kingdee.eas.fdc.invite.RefPriceEntryFactory;
import com.kingdee.eas.fdc.invite.RefPriceEntryInfo;
import com.kingdee.eas.fdc.invite.RefPriceFactory;
import com.kingdee.eas.fdc.invite.RefPriceInfo;

public class RefPriceEntryControllerBean extends AbstractRefPriceEntryControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.RefPriceEntryControllerBean");
    
    public final static String EXTEND_VALUE = "extend_value_";
	public final static String EXTEND_TEXT = "extend_text_";
	public final static String EXTEND_DATE = "extend_date_";

	protected IObjectCollection _getCollection(Context ctx, EntityViewInfo view, HashMap cols) throws BOSException {
		// TODO 自动生成方法存根
		FilterInfo filter = view.getFilter();
		
		if(filter == null )
			return null;

		for (Iterator it = filter.getFilterItems().iterator(); it.hasNext();) {

			FilterItemInfo filterItemInfo = (FilterItemInfo) it.next();
			String property = filterItemInfo.getPropertyName();
			
			/**
			 * 6.1查询方案做了优化，对于条件为 = 与 like 的参数增加了转化为大写函数,
			 * 通过查询方案进入时，如原getPropertyName()取值为name则现在为UPPER(name)函数未取掉，经与BOS人员沟通，暂特殊处理
			 */
			if(property.startsWith("UPPER")){
				property = property.substring(6, property.length()-1);
				filterItemInfo.setPropertyName(property);
			}
			if(filterItemInfo.getCompareType().equals(CompareType.IS)
					&&filterItemInfo.getCompareValue().equals("EMPTY")){
				filterItemInfo.setCompareType(CompareType.EQUALS);
				filterItemInfo.setCompareValue(null);
			}
			else if(filterItemInfo.getCompareType().equals(CompareType.ISNOT)
					&&filterItemInfo.getCompareValue().equals("EMPTY")){
				filterItemInfo.setCompareType(CompareType.NOTEQUALS);
				filterItemInfo.setCompareValue(null);
			}

			if (cols.containsKey(property)) {
				//
				/***
				 * 此处的核心目的就是为了将查找的参数换掉
				 * 无论是金额,还是文本,还是日期,他们的值均对应一个refPriceId
				 * 所以先将对于的refPriceId找到
				 * 然后替换为in (refPriceIds)
				 * 如果是一般的等于,in,上面的方法都没有问题
				 * 但是当碰到不等于,不类似的时候,因为在数据库中没有此行数据,
				 * 所以就会把所有此字段为空的数据过滤掉;这里就需要把为空的数据加进去
				 * 对于为空,同样
				 */
				String colid = (String) cols.get(property);
				if (property.startsWith(EXTEND_VALUE)) {
					// 金额
					filterItemInfo.setPropertyName("value");
				} else if (property.startsWith(EXTEND_TEXT)) {
					filterItemInfo.setPropertyName("text");
				} else if (property.startsWith(EXTEND_DATE)) {
					filterItemInfo.setPropertyName("datevalue");
				}

				EntityViewInfo tempView = new EntityViewInfo();
				Set itemIdSet = new HashSet();
				if ((filterItemInfo.getCompareType().equals(CompareType.EQUALS)
						&&filterItemInfo.getCompareValue() == null)||
						(filterItemInfo.getCompareType().equals(CompareType.IS)
								&&filterItemInfo.getCompareValue().equals("EMPTY"))) {
					//如果是为空查询
					tempView.getSelector().add("head.id");
					FilterInfo tempFilter = new FilterInfo();
					
					tempFilter.getFilterItems().add(
							new FilterItemInfo("column", colid));

					tempView.setFilter(tempFilter);
					RefPriceEntryCollection tempprice = RefPriceEntryFactory.getLocalInstance(ctx)
					.getRefPriceEntryCollection(tempView);
					
					for (int priceIndex = 0; priceIndex < tempprice.size(); priceIndex++) {
						RefPriceEntryInfo entryinfo = (RefPriceEntryInfo) tempprice
								.get(priceIndex);
						itemIdSet.add(entryinfo.getHead().getId().toString());
					}
				}
				else {
					tempView = new EntityViewInfo();
					tempView.getSelector().add("*");
					tempView.getSelector().add("head.id");
					FilterInfo tempFilter = new FilterInfo();
					tempFilter.getFilterItems().add(
							new FilterItemInfo("column", colid));
					tempFilter.getFilterItems().add(filterItemInfo);

					tempView.setFilter(tempFilter);
					RefPriceEntryCollection tempprice = RefPriceEntryFactory
							.getLocalInstance(ctx).getRefPriceEntryCollection(
									tempView);
					for (int priceIndex = 0; priceIndex < tempprice.size(); priceIndex++) {
						RefPriceEntryInfo entryinfo = (RefPriceEntryInfo) tempprice
								.get(priceIndex);
						itemIdSet.add(entryinfo.getHead().getId().toString());
					}
					if((filterItemInfo.getCompareType().equals(CompareType.NOTEQUALS)||
							filterItemInfo.getCompareType().equals(CompareType.NOTLIKE))
							&&filterItemInfo.getCompareValue() != null){
						tempView.getSelector().clear();
						tempView.getSelector().add("head.id");
						tempFilter = new FilterInfo();
						
						tempFilter.getFilterItems().add(
								new FilterItemInfo("column", colid));

						tempView.setFilter(tempFilter);
						RefPriceEntryCollection temppricenull = RefPriceEntryFactory.getLocalInstance(ctx)
						.getRefPriceEntryCollection(tempView);
						Set notInIds = new HashSet();
						for (int priceIndex = 0; priceIndex < temppricenull.size(); priceIndex++) {
							RefPriceEntryInfo entryinfo = (RefPriceEntryInfo) temppricenull
									.get(priceIndex);
							notInIds.add(entryinfo.getHead().getId().toString());
						}
						Set inIds  = new HashSet();
						//先找到这个column的表头类型
						try {
							HeadColumnInfo headColumnInfo = HeadColumnFactory.getLocalInstance(ctx)
							.getHeadColumnInfo("select * where id='"+colid+"'");
							tempView.getSelector().clear();
							tempView.getSelector().add("head.id");
							tempFilter = new FilterInfo();
							tempFilter.getFilterItems().add(new FilterItemInfo("column.headtype",headColumnInfo.getHeadType().getId().toString()));
							
							tempView.setFilter(tempFilter);
							temppricenull = RefPriceEntryFactory.getLocalInstance(ctx)
							.getRefPriceEntryCollection(tempView);
							
							for (int priceIndex = 0; priceIndex < temppricenull.size(); priceIndex++) {
								RefPriceEntryInfo entryinfo = (RefPriceEntryInfo) temppricenull
										.get(priceIndex);
								inIds.add(entryinfo.getHead().getId().toString());
							}
						} catch (EASBizException e) {
							// TODO 自动生成 catch 块
							e.printStackTrace();
						} catch (BOSException e) {
							// TODO 自动生成 catch 块
							e.printStackTrace();
						}
						//not in () and in()
						if(notInIds.size()>0||inIds.size()>0){
							tempView.getSelector().clear();
							tempView.getSelector().add("id");
							tempFilter = new FilterInfo();
							if(notInIds.size()>0)
								tempFilter.getFilterItems().add(new FilterItemInfo("id",notInIds,CompareType.NOTINCLUDE));
							if(inIds.size()>0)
								tempFilter.getFilterItems().add(new FilterItemInfo("id",inIds,CompareType.INCLUDE));
							tempView.setFilter(tempFilter);
							RefPriceCollection refs = RefPriceFactory.getLocalInstance(ctx)
							.getRefPriceCollection(tempView);
							for(int i=0;i<refs.size();i++){
								RefPriceInfo ref = refs.get(i);
								itemIdSet.add(ref.getId());
							}
						}
					}
				}
				
				if(itemIdSet.size()>0){
					if (filterItemInfo.getCompareType().equals(
							CompareType.EQUALS)
							&& filterItemInfo.getCompareValue() == null) {
						filterItemInfo.setPropertyName("head.id");
						filterItemInfo.setCompareValue(itemIdSet);
						filterItemInfo.setCompareType(CompareType.NOTINCLUDE);
					} else {
						filterItemInfo.setPropertyName("head.id");
						filterItemInfo.setCompareValue(itemIdSet);
						filterItemInfo.setCompareType(CompareType.INCLUDE);
					}
				}
				else{
					filterItemInfo.setPropertyName("head.id");
					filterItemInfo.setCompareValue(null);
					filterItemInfo.setCompareType(CompareType.EQUALS);
				}
				
				// reset extendcol to id
				
				
			} else if (property.equals("head.quotingContent.supplier.name") || property.endsWith("head.listing.name")) {
				if(filterItemInfo.getCompareValue()!=null){
					String value = (String) filterItemInfo.getCompareValue();
					if(property.equals("head.quotingContent.supplier.name")&&
							(
									value.endsWith("外界导入")||
									value.startsWith("外界导入")||
									value.startsWith("%外界导入")
							)
					  ) {
						// 报价单位 不等于,不类似 外界导入
						if (filterItemInfo.getCompareType().equals(
								CompareType.NOTEQUALS)
								|| filterItemInfo.getCompareType().equals(
										CompareType.NOTLIKE)) {
							filterItemInfo
									.setPropertyName("head.quotingContent");
							filterItemInfo.setCompareValue(null);
							filterItemInfo
									.setCompareType(CompareType.NOTEQUALS);
						} else {
							filterItemInfo
									.setPropertyName("head.quotingContent");
							filterItemInfo.setCompareValue(null);
							filterItemInfo.setCompareType(CompareType.EQUALS);
						}
						
					}
					else if(filterItemInfo.getCompareType().equals(
							CompareType.NOTEQUALS)
							|| filterItemInfo.getCompareType().equals(
									CompareType.NOTLIKE)){
						//对于不等于，不类似，需要把null值加入到结果中
						Set itemIdSet = new HashSet();
						EntityViewInfo tempView = new EntityViewInfo();
						tempView.getSelector().add("id");
						FilterInfo tempFilter = new FilterInfo();
						if(property.equals("head.quotingContent.supplier.name")){
							tempFilter.getFilterItems().add(new FilterItemInfo("quotingContent",null,CompareType.EQUALS));
							filterItemInfo.setPropertyName("quotingContent.supplier.name");
						}
						else{
							tempFilter.getFilterItems().add(new FilterItemInfo("listing",null,CompareType.EQUALS));
							filterItemInfo.setPropertyName("listing.name");
						}
						
						tempFilter.getFilterItems().add(filterItemInfo);
						tempFilter.setMaskString("#0 or #1");
						tempView.setFilter(tempFilter);
						
						RefPriceCollection refs = RefPriceFactory.getLocalInstance(ctx)
						.getRefPriceCollection(tempView);
						for(int i=0;i<refs.size();i++){
							RefPriceInfo ref = refs.get(i);
							itemIdSet.add(ref.getId());
						}
						if(itemIdSet.size()>0){
							filterItemInfo.setPropertyName("head.id");
							filterItemInfo.setCompareValue(itemIdSet);
							filterItemInfo.setCompareType(CompareType.INCLUDE);
						}
						else{
							filterItemInfo.setPropertyName("head.id");
							filterItemInfo.setCompareValue(null);
							filterItemInfo.setCompareType(CompareType.EQUALS);
						}
					}
				
				}
				
			} 
		}
		return RefPriceEntryFactory.getLocalInstance(ctx).getRefPriceEntryCollection(view);
	}
}