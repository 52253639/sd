package com.kingdee.eas.fdc.basedata.client;

import java.util.Set;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;

public class ProjectTreeBuilderExt extends ProjectTreeBuilder {
	/**
	 * 
	 * 描述：构造工程项目查询条件
	 * 
	 * @param orgTreeModel
	 * @return
	 * @author:liupd 创建时间：2006-7-20
	 *               <p>
	 */
	protected FilterInfo getProjectFilter(Set leafNodesIdSet) {

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit", leafNodesIdSet,
						CompareType.INCLUDE));
//		 filter.getFilterItems().add(new FilterItemInfo("isEnabled",
//		 Boolean.TRUE));

		return filter;
	}
}
