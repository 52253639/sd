package com.kingdee.eas.fdc.aimcost.app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.app.DbUtil;

public class DynCostSnapShotControllerBean extends AbstractDynCostSnapShotControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.DynCostSnapShotControllerBean");
    protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
    	DynCostSnapShotInfo info = (DynCostSnapShotInfo)super._getValue(ctx,pk);
    	String retValue = "";
    	if(info.getCostAccountId()!= null)
    	{   		
    		CostAccountInfo test = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(info.getCostAccountId()));
    		if(test.getNumber()!=null){
    			retValue = test.getNumber();
    			if(test.getName()!= null){
    				retValue = retValue + " " + test.getName();
    			}
    		}   		
    	}
    	return retValue;
    }
	protected Map _getHistoryByMonth(Context ctx, Map selectMonth) throws BOSException, EASBizException {
		Map returnMap = new HashMap();
//		Map temp = new HashMap();
		String projectId = selectMonth.get("projectId").toString();
		ArrayList al = (ArrayList)selectMonth.get("monthList");
		ArrayList tempAl = new ArrayList();
		Date date = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<al.size();i++){
			String y = al.get(i).toString().substring(0,4);
			String m;
			if(al.get(i).toString().length()==12){
				 m = al.get(i).toString().substring(5,7);
			}else{
				 m = al.get(i).toString().substring(5,6);
			}			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, (new Integer(y)).intValue());
			cal.set(Calendar.MONTH,(new Integer(m)).intValue());
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
			String dateStr = df.format(date);
			dateStr = dateStr + " 00:00:00";
			Date newDate;
			try {
				newDate = df.parse(dateStr);
				tempAl.add(i,newDate);
			} catch (ParseException e) {
			}
			
		}
		tempAl.iterator();
		///
		DynCostSnapShotCollection dcssc = new DynCostSnapShotCollection();
//		DynCostSnapShotInfo dcssi = new DynCostSnapShotInfo();
		for(int i=0;i<tempAl.size();i++){
			//获取指定月的最后保存日期先
			Map temp = new HashMap();
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("projectId",projectId));
			filter.getFilterItems().add(new FilterItemInfo("snapShotDate", (Date)tempAl.get(i), CompareType.getEnum("<")));//.getEnum("<")));
			evi.setFilter(filter);
			evi.getSelector().add(new SelectorItemInfo("*"));
			SorterItemInfo sorter = new SorterItemInfo("snapShotDate");
			sorter.setSortType(SortType.DESCEND);
			evi.getSorter().add(sorter);
			dcssc = getDynCostSnapShotCollection(ctx,evi);
			if(dcssc.size()!=0){
				DynCostSnapShotInfo dcssi = dcssc.get(0);//.getSnapShotDate();//.getSnapShotDate();
				Date selectDate = dcssi.getSnapShotDate();
				if((selectDate.getYear()==((Date)tempAl.get(i)).getYear())
						&&(selectDate.getMonth()==((Date)tempAl.get(i)).getMonth()-1)){//判断获取的日期是否属于指定月
//					根据指定月的最后保存日期,查询得出该日期的保存数据信息,加入Map i
					evi = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("projectId",projectId));
					filter.getFilterItems().add(new FilterItemInfo("snapShotDate", selectDate));//.getEnum("<")));
					filter.getFilterItems().add(new FilterItemInfo("savedType",CostMonthlySaveTypeEnum.AUTOSAVE_VALUE));
					evi.setFilter(filter);
					evi.getSelector().add(new SelectorItemInfo("*"));
					evi.getSelector().add(new SelectorItemInfo("settEntries.*"));// 结算分录
					evi.getSelector().add(new SelectorItemInfo("proTypEntries.*"));// 产品分录
					try {
						dcssc = getDynCostSnapShotCollection(ctx,evi);
					} catch (BOSException e) {
					}
					if (dcssc != null && dcssc.size() != 0) {
						for (int j = 0; j < dcssc.size(); j++) {
							if(dcssc.get(j).getCostAccountId()!=null)
								temp.put(dcssc.get(j).getCostAccountId().toString(), dcssc.get(j));
						}
					}
				}
			}			
			returnMap.put(al.get(i).toString(),temp);//将al中界面的key与map的key设置为一致,方便回传界面后的取数
		}
//		for(int i = 0;i<returnMap.size();i++){
//			System.out.println(i);
//		}
		return returnMap;
	}
}