package com.kingdee.eas.fdc.schedule.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryCollection;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryFactory;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryInfo;

public class WBSTemplatePrefixEntryControllerBean extends
		AbstractWBSTemplatePrefixEntryControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.schedule.app.WBSTemplatePrefixEntryControllerBean");
	
	/**
	 * 根据WBS模板ID获取对应的前置任务
	 * Map的结构：WBS模板分录ID-对应前置任务分录Collection
	 */
	protected Map _getPreTasks(Context ctx, Set WBSTMPEntryIDs)
			throws BOSException, ScheduleException {
		Map preTasks = new HashMap();
//		先处理Map的结构
		Iterator ite = WBSTMPEntryIDs.iterator();
		while(ite.hasNext()){
			WBSTemplatePrefixEntryCollection tempCollection = new WBSTemplatePrefixEntryCollection();
			preTasks.put(ite.next().toString(), tempCollection);
		}
		
		WBSTemplatePrefixEntryCollection wbsTMPPreTaskInfos = new WBSTemplatePrefixEntryCollection();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent",WBSTMPEntryIDs,CompareType.INCLUDE));
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo("parent"));
		view.setSorter(sorter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("linkDay"));
		sic.add(new SelectorItemInfo("linkType"));
		sic.add(new SelectorItemInfo("prefixNode.id"));
		sic.add(new SelectorItemInfo("prefixNode.name"));
		sic.add(new SelectorItemInfo("prefixNode.number"));
		sic.add(new SelectorItemInfo("parent"));
		view.setSelector(sic);
		wbsTMPPreTaskInfos = WBSTemplatePrefixEntryFactory.getLocalInstance(ctx).
				getWBSTemplatePrefixEntryCollection(view);
		for(int i=0;i<wbsTMPPreTaskInfos.size();i++){
			WBSTemplatePrefixEntryInfo tempInfo = wbsTMPPreTaskInfos.get(i);
			((WBSTemplatePrefixEntryCollection)preTasks.
					get(tempInfo.getParent().getId().toString())).add(tempInfo);
		}
		return preTasks;
	}
}