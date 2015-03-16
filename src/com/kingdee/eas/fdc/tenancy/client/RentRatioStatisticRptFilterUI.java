/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.olap.util.Sort;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProject;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaFactory;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.tenancy.RentRatioFacade;
import com.kingdee.eas.fdc.tenancy.RentRatioFacadeFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述：出租率统计报表过滤页面
 * @version 1.0
 * @author pu_zhang @date 2010-08-10
 *
 */
public class RentRatioStatisticRptFilterUI extends AbstractRentRatioStatisticRptFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(RentRatioStatisticRptFilterUI.class);
    private static final String KEY_F7Project = "F7Project";
    private static final String KEY_F7Area = "F7Area";
    private static final String KEY_F7Building = "F7Building";
    protected ListUI listUI;
    protected ItemAction actionListOnLoad;
    
    String[] areaIds=null;
	String[] areaNames =null;
	String[] buildingIds =null;
	String[] buildingNames=null;
	
    /**
     * output class constructor
     */
    public RentRatioStatisticRptFilterUI() throws Exception
    {
        super();
    }
    
	/**
	 * output class constructor
	 */
	public RentRatioStatisticRptFilterUI(ListUI listUI, ItemAction actionListOnLoad)
			throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
	}
    
    
	public void onLoad() throws Exception {
		super.onLoad();
		this.F7Project.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				F7Area.setValue(null);
				F7Building.setValue(null);
			}
		});
		this.F7Area.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				F7Building.setValue(null);
			}
		});
		
		this.F7Project.addDataChangeListener( new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				f7Project_dataChanged(eventObj);
			}
		});
		
		this.F7Area.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				f7Area_dataChanged(eventObj);
			}
		});
		
		init();
	}

	/**
	 * 描述：初始化信息
	 *@author pu_zhang 
	 * @throws BOSException 
	 * @throws EASBizException 
	 *@date   2010-7-20
	 */
	private void init() throws EASBizException, BOSException {
		//初始化 F7Project
		initF7Project(F7Project);
		//初始化 F7Area
		initF7Area(F7Area);
		//初始化 F7Building
		initF7Building(F7Building);
	}
	/**
	 * 描述：初始化 F7Project
	 * @param box
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void initF7Project(KDBizPromptBox box) throws EASBizException, BOSException{
    	box.setEditable(false);
    	this.F7Project.setRequired(true);
		box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
		box.setDisplayFormat("$name$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",CommerceHelper.getPermitProjectIdSql(currentUserInfo),CompareType.INNER));
		filter.getFilterItems().add(new FilterItemInfo("isForTen", new Integer(1)));
		view.setFilter(filter);
		
		SorterItemCollection coll = new SorterItemCollection();
		coll.add(new SorterItemInfo("number"));
		view.setSorter(coll);
		
		box.setEntityViewInfo(view);
	}
	/**
	 * 描述：获取有权限的，并且是租赁系统的PorjectId的集合
	 * @return
	 */
	private Set getProjectIdSet(){
		//获取当前用户
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		
		Set set = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		FilterInfo filter = new FilterInfo();
		try {
			//获取当前有权限的项目
			filter.getFilterItems().add(new FilterItemInfo("id",CommerceHelper.getPermitProjectIdSql(currentUserInfo),CompareType.INNER));
			filter.getFilterItems().add(new FilterItemInfo("isForTen", Boolean.TRUE));
			view.setFilter(filter);
			SellProjectCollection projectColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
			for (int i = 0; i < projectColl.size(); i++) {
				set.add(((SellProjectInfo)projectColl.get(i)).getId().toString());
			}
		} catch (EASBizException e) {
			super.handUIException(e);
		} catch (BOSException e) {
			super.handUIException(e);
		}
		return set;
	}

	private void f7Project_dataChanged(DataChangeEvent e) {
		initF7Area(F7Area);
		initF7Building(F7Building);
	}
	
	/**
	 * 描述：初始化F7Area
	 * @param box
	 */
	private void initF7Area(KDBizPromptBox box) {
		box.setEditable(false);
		box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SubareaQuery");
		box.setDisplayFormat("$name$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$id$");
		
		Object v = this.F7Project.getValue();
		EntityViewInfo evi = new EntityViewInfo();
		

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		sic.add("sellProject.id");
		box.setSelectorCollection(sic);
		FilterInfo filter = new FilterInfo();
		
		if(v!=null){
			if (v instanceof SellProjectInfo) {
				SellProjectInfo sellProjectInfo = (SellProjectInfo) v;
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProjectInfo.getId()));
			}
		}else{
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",getProjectIdSet(),CompareType.INCLUDE));
		}
		SorterItemCollection coll = new SorterItemCollection();
		coll.add(new SorterItemInfo("number"));
		evi.setSorter(coll);
		evi.setFilter(filter);
		
		box.setEnabledMultiSelection(true);
		box.setEntityViewInfo(evi);
	}

	private void f7Area_dataChanged(DataChangeEvent e){
		initF7Building(F7Building);
	}
	/**
	 * 描述：初始化F7Building
	 * @param box
	 */
	private void initF7Building(KDBizPromptBox box) {
		box.setEditable(false);
		box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");
		box.setDisplayFormat("$name$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$id$");
		

		EntityViewInfo evi = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		sic.add("sellProject.id");
		sic.add("subarea.id");
		box.setSelectorCollection(sic);
		FilterInfo filter = new FilterInfo();

		Object vp = this.F7Project.getValue();
		Object va = this.F7Area.getValue();
		List  aidList=new ArrayList();
		if(vp!=null){
			if (vp instanceof SellProjectInfo) {
				SellProjectInfo sellProjectInfo = (SellProjectInfo) vp;
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectInfo.getId()));
			}
		}else{
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", getProjectIdSet(),CompareType.INCLUDE));
		}
		
		if(va!=null){
			if(((Object[])va).length>0&&((Object[])va)[0]!=null){
				Object[] v = (Object[])va;
				for(int i =0;i<v.length;i++){
					aidList.add(((SubareaInfo)v[i]).getId().toString());
				}
				Set aidSet = new HashSet(aidList);
				filter.getFilterItems().add(new FilterItemInfo("subarea.id",aidSet,CompareType.INCLUDE));
			}	
		}
		SorterItemCollection coll = new SorterItemCollection();
		coll.add(new SorterItemInfo("number"));
		evi.setSorter(coll);
		evi.setFilter(filter);
		box.setEnabledMultiSelection(true);
		box.setEntityViewInfo(evi);

	}

	/**
     * 描述：初始化销售项目
     * @param box
     * @throws EASBizException
     * @throws BOSException
     */
    private void initPrmtSellProject(KDBizPromptBox box) throws EASBizException, BOSException{
    	box.setEditable(false);
		box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
		box.setDisplayFormat("$name$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");
		box.setEntityViewInfo(CommerceHelper.getPermitProjectView());
	}
	/**
	 * 描述：重写CustomerParams方法
	 */
	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

		param.add(KEY_F7Project, ((SellProjectInfo) this.F7Project
				.getValue()).getId().toString());
	
		//地区的id，name
	   if(this.F7Area.getValue()!=null){
		   if(((Object[])F7Area.getValue()).length>0&&((Object[])F7Area.getValue())[0]!=null){
	   		Object[] v=(Object[])F7Area.getValue();
	   		 	areaIds =new String[v.length];
	   		 	areaNames =new String[v.length];
	   		for(int i=0;i<v.length;i++){
	   			SubareaInfo ai=(SubareaInfo)v[i];
	   			String aid=ai.getId().toString();
	   			String aname=ai.getName();
	   			areaIds[i]=aid;
	   			areaNames[i]=aname;
	   		}
	   		param.add("areaIds",areaIds);
	   		param.add("areaNames",areaNames);
		   }
	   }
		
		//楼栋的id，name
	   if(this.F7Building.getValue()!=null){
		   if(((Object[])F7Building.getValue()).length>0&&((Object[])F7Building.getValue())[0]!=null){
	   		Object[] v=(Object[])F7Building.getValue();
	   		 	buildingIds =new String[v.length];
	   		 	buildingNames =new String[v.length];
	   		for(int i=0;i<v.length;i++){
	   			BuildingInfo bi=(BuildingInfo)v[i];
	   			String bid=bi.getId().toString();
	   			String bname=bi.getName();
	   			buildingIds[i]=bid;
	   			buildingNames[i]=bname;
	   		}
	   		param.add("buildingIds",buildingIds);
	   		param.add("buildingNames",buildingNames);
		   }
	   }
		
		return param.getCustomerParams();
	}
	/**
	 * 描述：重写setCustomerParams
	 */
	public void setCustomerParams(CustomerParams cp) {

	   if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp); 
		Map mapFilterParam = new HashMap(); 
		Map mapFilterInfValue = null;
		try {
			
			Map resultMap = new HashMap();
			
				
				if (para.getString("F7Project") != null) {
//					resultMap.put("F7Project", SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(para.getString("F7Project"))));
					mapFilterParam.put("F7Project", para.getString("F7Project"));
				}
				//地区id
				if (para.isNotNull("areaIds")) {
					String[] costArray = para.getStringArray("areaIds");
//					Object[] oa = new Object[costArray.length];
//					for (int i = 0; i < costArray.length; i++) {
//							oa[i]=SubareaFactory.getRemoteInstance().getSubareaInfo(new ObjectUuidPK(costArray[i]));
//					}
//					resultMap.put("areaIds", oa);
					mapFilterParam.put("areaIds", costArray);
				}
				//楼栋id
				if (para.isNotNull("buildingIds")) {
					String[] costArray = para.getStringArray("buildingIds");
//					Object[] ba = new Object[costArray.length];
//					for (int i = 0; i < costArray.length; i++) {
//						ba[i]=BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(costArray[i]));
//					}
//					resultMap.put("buildingIds", ba);
					mapFilterParam.put("buildingIds", costArray);
				}
			
			mapFilterInfValue = RentRatioFacadeFactory.getRemoteInstance().getBatchFilterInfo(mapFilterParam);
		} catch (BOSException e1) {
			this.handUIException(e1);
		}
		
		//项目
		if(para.getString(KEY_F7Project)!=null){
			try {
				if(mapFilterInfValue!=null && mapFilterInfValue.get(KEY_F7Project)!=null){
					this.F7Project.setValue(mapFilterInfValue.get(KEY_F7Project));
				}
				
			} catch (Exception e) {
				this.handUIException(e);
			}
		}
		//地区name
		if (para.isNotNull("areaNames")) {
			
			StringBuffer sbuffer = new StringBuffer();
			
			String[] costArray = para.getStringArray("areaNames");
			for (int i = 0; i < costArray.length; i++) {
				if (i > 0) {
					sbuffer.append(";");
				}
				sbuffer.append(costArray[i]);
			}
			this.F7Area.setText(sbuffer.toString().replace('!', '.'));
		}
		//地区id
		if (para.isNotNull("areaIds")) {
			
//			String[] costArray = para.getStringArray("areaIds");
//			Object[] oa = new Object[costArray.length];
//			try {
//				for (int i = 0; i < costArray.length; i++) {
//					
//						oa[i]=SubareaFactory.getRemoteInstance().getSubareaInfo(new ObjectUuidPK(costArray[i]));
//					
//				}
//			} catch (EASBizException e) {
//				logger.error(e);
//			} catch (BOSException e) {
//				logger.error(e);
//			}
			if(mapFilterInfValue!=null && mapFilterInfValue.get("areaIds")!=null){
				this.F7Area.setValue(mapFilterInfValue.get("areaIds"));
			}
//			this.F7Area.setValue(oa);
		}
		
		//楼栋name
		if (para.isNotNull("buildingNames")) {
			StringBuffer sbuffer = new StringBuffer();
			String[] costArray = para.getStringArray("buildingNames");
			for (int i = 0; i < costArray.length; i++) {
				if (i > 0) {
					sbuffer.append(";");
				}
				sbuffer.append(costArray[i]);
			}
			this.F7Building.setText(sbuffer.toString().replace('!', '.'));
		}
		
		//楼栋id
		if (para.isNotNull("buildingIds")) {
			
//			String[] costArray = para.getStringArray("buildingIds");
//			Object[] ba = new Object[costArray.length];
//			try{
//				for (int i = 0; i < costArray.length; i++) {
//					ba[i]=BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(costArray[i]));
//				}
//			} catch (EASBizException e) {
//				logger.error(e);
//			} catch (BOSException e) {
//				logger.error(e);
//			}
			if(mapFilterInfValue!=null && mapFilterInfValue.get("buildingIds")!=null){
				this.F7Building.setValue(mapFilterInfValue.get("buildingIds"));
			}
//			this.F7Building.setValue(ba);
		}
		super.setCustomerParams(para.getCustomerParams());
	}
	/**
	 * 描述：获取过滤信息  重写getFilterInfo
	 */
	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		FilterInfo filter = new FilterInfo();
		// 项目
		if (para.isNotNull(KEY_F7Project)) {
			try {
				filter.getFilterItems().add(new FilterItemInfo(KEY_F7Project,para.getString(KEY_F7Project)));
			} catch (Exception e) {
				this.handUIException(e);
			}
		} else {// 如果没有选择工程项目，给出提示
			MsgBox.showError(this, "请选择工程项目");
		}
		// 客户ID
		if (para.isNotNull("areaIds")) {
			filter.getFilterItems().add(new FilterItemInfo("areaIds", FDCHelper.getSetByArray(para.getStringArray("areaIds")), CompareType.INCLUDE));
		}
		// 款项类型
		if (para.isNotNull("buildingIds")) {
			filter.getFilterItems().add(new FilterItemInfo("buildingIds", FDCHelper.getSetByArray(para.getStringArray("buildingIds")), CompareType.INCLUDE));
		}
		return filter;
	}
	
	 public void clear() {
		   try {
			   this.F7Project.setValue(null);
			   this.F7Area.setValue(null);
			   this.F7Building.setValue(null);
			init();
		} catch (Exception e) {
			this.handUIException(e);
		}
	   }
	
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    /**
     * 描述：校验项目不能为空
     */
    public boolean verify() {
    	if (this.F7Project.getValue() == null) {
    		MsgBox.showWarning("请先选择项目");
    		return false;
    	}
    	return true;
    }
    
    
    /**
     * 功能：获取页面的参数
     * @author pu_zhang
     * @return Map
     * @return
     */
	public Map getParam() {
		Map param = new HashMap();
		if (this.F7Project.getValue() != null) {
			param.put(KEY_F7Project, ((SellProjectInfo) this.F7Project
					.getValue()).getId().toString());
		}
		if (this.F7Area.getValue() != null) {
			param.put(KEY_F7Area, ((SubareaInfo) this.F7Area
					.getValue()).getId().toString());
		}
		if (this.F7Building.getValue() != null) {
			param.put(KEY_F7Building, ((BuildingInfo) this.F7Building
					.getValue()).getId().toString());
		}
		return param;
	}
	

}