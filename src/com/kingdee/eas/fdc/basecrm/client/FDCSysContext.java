package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;


public class FDCSysContext {
	private static final Logger logger = Logger.getLogger(FDCSysContext.class);
	private static FDCSysContext fdcSysContext;
	//<String,FDCOrgStructureInfo>组织id为key，SHEORG为entry 
	private Map OrgMap = new HashMap();
	private Map Properties = new HashMap();
	private Map cusMgeMap = new HashMap();
	
	private FDCSysContext(){
		synOrgmap();
	}
	
	public static synchronized FDCSysContext getInstance(){
		if(fdcSysContext ==null){
			 fdcSysContext = new FDCSysContext();
		}
			return fdcSysContext;
		
	}
	
	public  Map getOrgMap() {
		return OrgMap;
	}

	public  Map getCusMgeMap() {
		return cusMgeMap;
	}
	
	/**
	 * @param key
	 * @return
	 */
	public Object getProperty(Object key){
		return Properties.get(key);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setProperty(Object key, Object value){
		Properties.put(key, value);
	}
	
	public void synOrgmap(){
		OrgMap.clear();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("unit.id");
		sel.add("unit.isOUSealUp");
		sel.add("tree.id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellHouseStrut",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("unit.isOUSealUp",Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isHis",Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("tree.id",OrgConstants.SALE_VIEW_ID));
		view.setFilter(filter);
		view.setSelector(sel);
		try {
			FDCOrgStructureCollection orgColl = FDCOrgStructureFactory.getRemoteInstance().getFDCOrgStructureCollection(view);
			for(int i = 0 ; i < orgColl.size() ; i++){
				FDCOrgStructureInfo orgInfo = orgColl.get(i);
				if(orgInfo.getUnit()!=null){
					OrgMap.put(orgInfo.getUnit().getId().toString(),orgInfo );
				}
			}
		} catch (BOSException e) {
			logger.info("同步FDCSysContext出错！");
			e.printStackTrace();
		}
		
		cusMgeMap.clear();
		view = new EntityViewInfo();
		sel = new SelectorItemCollection();
		sel.add("unit.id");
		sel.add("unit.isOUSealUp");
		sel.add("tree.id");
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("custMangageUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("unit.isOUSealUp",Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isHis",Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("tree.id",OrgConstants.SALE_VIEW_ID));
		view.setFilter(filter);
		view.setSelector(sel);
		try {
			FDCOrgStructureCollection orgColl = FDCOrgStructureFactory.getRemoteInstance().getFDCOrgStructureCollection(view);
			for(int i = 0 ; i < orgColl.size() ; i++){
				FDCOrgStructureInfo orgInfo = orgColl.get(i);
				if(orgInfo.getUnit()!=null){
					cusMgeMap.put(orgInfo.getUnit().getId().toString(),orgInfo );
				}
			}
			
//			try {
//				//计算缓存数据大小
//				ByteArrayOutputStream bot = new ByteArrayOutputStream();
//				ObjectOutputStream  oos = new ObjectOutputStream(bot);
//				oos.writeObject(OrgMap);
//				System.out.println("orgmap 大小: " +bot.toByteArray().length+" B");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} catch (BOSException e) {
			logger.info("初始化FDCSysContext出错！");
			e.printStackTrace();
		}
	}
	
	/**
	 *任意组织，弹框的
	 * @return
	 */
	public void checkIsSHEOrg(Component comp, String orgId){
		if(!OrgMap.containsKey(orgId)){
			MsgBox.showInfo(comp, "非售楼组织不能操作");
			SysUtil.abort();
		}
	}
	
	/**
	 * 只是当前组织，弹框的
	 * @return
	 */
	public void checkIsSHEOrg(Component comp){
		this.checkIsSHEOrg(comp, SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
	}
	
	/**
	 * 只是当前组织
	 * @return
	 */
	public boolean checkIsSHEOrg(){
		return checkIsSHEOrg(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
	}
	
	/**
	 * 任意组织
	 * @return
	 */
	public boolean checkIsSHEOrg(String orgId){
		return this.OrgMap.containsKey(orgId );
	}
}
