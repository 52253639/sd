package com.kingdee.eas.fdc.schedule.app;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ScheduleBizTypeControllerBean extends AbstractScheduleBizTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ScheduleBizTypeControllerBean");
    // 得到最大编码
	protected String _getBiggestNumber(Context ctx) throws BOSException {
		String strNum = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// 直接写SQL查询出最大编码
		builder.appendSql("select max(fnumber) as fnumber from T_SCH_ScheduleBizType");
		IRowSet rowSet = builder.executeQuery();
		try {
			// 是否有值
			while (rowSet.next()) {
				strNum = rowSet.getString("fnumber");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (strNum == "" || strNum == null) {
			strNum = "0000";
		}
		return strNum;
	}
	
	public void delete(Context ctx, IObjectPK[] arg1) throws BOSException, EASBizException {
		 // 转化成set
		 Set idSet = new HashSet();
		 for (int i = 0; i < arg1.length; i++) {
			 idSet.add(arg1[i].toString());
		 }
		 // 判断是否被计划引用
		 EntityViewInfo view = new EntityViewInfo();
		 view.getSelector().clear();
		 view.getSelector().clear();
		 view.getSelector().add("id");
		 view.getSelector().add("bizType.*");
		 FilterInfo filter=new FilterInfo();
		 filter.getFilterItems().add(new FilterItemInfo("bizType.id", idSet, CompareType.INCLUDE));
		 view.setFilter(filter);
		 ScheduleTaskBizTypeCollection taskBizTypeCol=ScheduleTaskBizTypeFactory.getLocalInstance(ctx).getScheduleTaskBizTypeCollection(view);
		 if (taskBizTypeCol!=null&&taskBizTypeCol.size() > 0) {
			 for (int i = 0; i < taskBizTypeCol.size(); i++) {
				ScheduleTaskBizTypeInfo scheduleTaskBizTypeInfo = taskBizTypeCol.get(i);
				ScheduleBizTypeInfo bizType = scheduleTaskBizTypeInfo.getBizType();
				throw new EASBizException(new NumericExceptionSubItem("100", bizType.getName() + "已经被引用，不能删除"));
				}
			}
		 
		 // 判断是否被模板引用
		EntityViewInfo view2 = new EntityViewInfo();
		view2.getSelector().clear();
		SelectorItemCollection sic2 = new SelectorItemCollection();
		sic2.add(new SelectorItemInfo("bizType.id"));
		sic2.add(new SelectorItemInfo("bizType.name"));
		view2.setSelector(sic2);
		FilterInfo info3 = new FilterInfo();
		info3.getFilterItems().add(new FilterItemInfo("bizType.id", idSet, CompareType.INCLUDE));
		view2.setFilter(info3);
		RESchTemplateTaskBizTypeCollection col2 = RESchTemplateTaskBizTypeFactory.getLocalInstance(ctx).getRESchTemplateTaskBizTypeCollection(view2);
		if (col2.size() > 0) {
			for (int i = 0; i < col2.size(); i++) {
				  RESchTemplateTaskBizTypeInfo schTemplateTaskBizTypeInfo = col2.get(i);
				// String name = info2.getBusinessType();
				// TODO 业务类型需要修改
				ScheduleBizTypeInfo bizType2 = schTemplateTaskBizTypeInfo.getBizType();
				String name = bizType2.getName();
				throw new EASBizException(new NumericExceptionSubItem("100", name + "已经被引用，不能删除"));
				
			}
		}
		super.delete(ctx, arg1);
	}
	
}