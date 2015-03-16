/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 动态成本信息界面
 * output class name
 */
public class FullDyDetailInfoUI extends AbstractFullDyDetailInfoUI {
	
	public static final String resourcePath = "com.kingdee.eas.fdc.aimcost.client.FullDyDetailResource";
	
	private static final Logger logger = CoreUIObject
			.getLogger(FullDyDetailInfoUI.class);

	/**
	 * output class constructor
	 */
	public FullDyDetailInfoUI() throws Exception {
		super();
	}

	private void addPanel(Map dataMap, String uiClass, String title)
			throws UIException {
		if (dataMap == null || dataMap.isEmpty()) {
			return;
		}
		UIContext uiContext = new UIContext(ui);
		uiContext.put("dataMap", dataMap);
		CoreUIObject plUI = null;
		plUI = (CoreUIObject) UIFactoryHelper.initUIObject(uiClass, uiContext,
				null, "VIEW");
		this.kDTabbedPane1.add(plUI, title);
	}

	public void onLoad() throws Exception {

		super.onLoad();
		kDTabbedPane1.removeAll();

		Map dataMap = (Map) this.getUIContext().get("dataMap");
		Object title = dataMap.get("FullDyDetailInfoTitle");
		if(title!=null){
			setUITitle(title.toString());
		}
		//动态成本
		addPanel(dataMap, FullDyCostInfoUI.class.getName(), this
				.getResouce("dy"));
		//合同信息
		addPanel(dataMap, FullDyContractInfoUI.class.getName(), this
				.getResouce("con"));
		//变更信息
		addPanel(dataMap, FullDyChangeInfoUI.class.getName(), this
				.getResouce("chg"));
		//非合同性成本(无文本)
		addPanel(dataMap, FullDyNoContractInfoUI.class.getName(), this
				.getResouce("nocon"));
		//待发生成本明细
		addPanel(dataMap, FullDyWillHappenInfoUI.class.getName(), this
				.getResouce("willhappen"));
		//付款明细
		addPanel(dataMap, FullDyPayInfoUI.class.getName(), this
				.getResouce("pay"));
		
//		if(dataMap.get("ShowContractFirst")!=null){
//			kDTabbedPane1.setSelectedIndex(1);
//		}

	}

	private String getResouce(String resName) {
		return EASResource.getString(resourcePath, resName);
	}

	/**
	 * 根据id显示窗体
	 */
	public static boolean showDialogWindows(IUIObject ui, Map dataMap)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("dataMap", dataMap);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(FullDyDetailInfoUI.class.getName(), uiContext, null,
						"VIEW");
		uiWindow.show();
		return true;
	}
	
	/**
	 * 抽出一个方法以便其它地方调用,以后willHappen也应该在这里面取值,而不是传进来
	 * 当will_happen 为0或者Null时，将从数据库中重新取will_happen，如果在传入的时候能够传入will_happen，请传入
	 * by sxhong  by sxhong 2009-06-10 20:46:32
	 * @param ui
	 * @param acct
	 * @param will_happen 
	 * @throws UIException 
	 */
	public static void showDialogWindows(IUIObject ui,CostAccountInfo acct,BigDecimal will_happen) throws UIException{
		if(acct==null||acct.getId()==null){
			return;
		}
		if(acct.getCurProject()==null||acct.getCurProject().getId()==null){
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("id");
			selector.add("curProject.id");
			try {
				acct=CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(acct.getId()),selector);
			} catch (EASBizException e) {
				throw new UIException(e);
			} catch (BOSException e) {
				throw new UIException(e);
			}
		}
		Map map = new HashMap();

		String acctId = acct.getId().toString();
		map.put("acctId", acctId);
		String projId = acct.getCurProject().getId().toString();


		HappenDataGetter happenGetter = new HappenDataGetter(projId, true, true,false);
		BigDecimal no_set_total = FDCConstants.B0;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSelector().add("id");
		view.getSelector().add("name");
		try{
			ChangeTypeCollection changeTypes = ChangeTypeFactory.getRemoteInstance()
				.getChangeTypeCollection(view);
			for(int i=0;i<changeTypes.size();i++){
				ChangeTypeInfo type=(ChangeTypeInfo)changeTypes.get(i);
				String changeTypeId=type.getId().toString();
				HappenDataInfo temp = (HappenDataInfo) happenGetter.changeSplitMap.get(acctId + changeTypeId + 0);
				if(temp!=null){
					map.put(changeTypeId+"NoSettle", temp.getAmount());
					no_set_total=FDCHelper.add(no_set_total, temp.getAmount());
				}
				temp = (HappenDataInfo) happenGetter.changeSplitMap.get(acctId + changeTypeId + 1);
				if(temp!=null){
					map.put(changeTypeId+"Settle", temp.getAmount());
				}
			}
		}catch(Exception e){
			throw new UIException(e);
		}

		BigDecimal no_set_amt = null;
		HappenDataInfo happenDataInfo = (HappenDataInfo) happenGetter.conSplitMap
				.get(acctId + 0);
		
		if (happenDataInfo != null) {
			no_set_amt = happenDataInfo.getAmount();

			no_set_total = no_set_total.add(no_set_amt);
		}

		BigDecimal set_amt = null;

		happenDataInfo = (HappenDataInfo) happenGetter.conSplitMap
				.get(acctId + 1);
		if (happenDataInfo != null) {
			set_amt = happenDataInfo.getAmount();
		}

		BigDecimal set_total = null;
		happenDataInfo = (HappenDataInfo) happenGetter.settleSplitMap
				.get(acctId);
		if (happenDataInfo != null) {
			set_total = happenDataInfo.getAmount();
		}

		BigDecimal no_text = null;
		happenDataInfo = (HappenDataInfo) happenGetter.noTextSplitMap
				.get(acctId);
		if (happenDataInfo != null) {
			no_text = happenDataInfo.getAmount();
		}

		if(FDCHelper.toBigDecimal(will_happen).signum()==0){
			//取待发生成本
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select sum(amount) as amount from ( ");
			builder.appendSql("select FCostAmount  as amount from T_AIM_CostEntry entry ");
			builder.appendSql("inner join T_AIM_AimCost head on head.fid=entry.fheadId ");
			builder.appendSql(" where head.FIsLastVersion=1 and entry.fcostaccountid=? ");
			builder.appendSql(" union all ");
			builder.appendSql("select FAdjustSumAmount as amount from T_AIM_DynamicCost dyn where dyn.faccountid=? ");
			builder.appendSql(")t ");
			builder.addParam(acctId);
			builder.addParam(acctId);
			try{
				IRowSet rowSet=builder.executeQuery();
				if(rowSet.size()>0){
					rowSet.next();
					BigDecimal dynCost=rowSet.getBigDecimal("amount");
//					HappenDataInfo happen = happenGetter.getHappenInfo(acctId);
			    	BigDecimal happen = FDCHelper.ZERO;
			    	if(set_total != null) {
			    		happen = happen.add(set_total);
			    	}
			    	if(no_set_total != null) {
			    		happen = happen.add(no_set_total);
			    	}
			    	if(no_text != null) {
			    		happen = happen.add(no_text);
			    	}
			    	
					if(happen==null){
						will_happen=dynCost;
					}else{
						will_happen=FDCHelper.subtract(dynCost, happen);
					}
				}
			}catch(Exception e){
				throw new UIException(e);
			}
		}
		
		
		map.put("no_set_amt", no_set_amt);
		map.put("set_amt", set_amt);
		map.put("no_set_total", no_set_total);
		map.put("set_total", set_total);

		map.put("no_text", no_text);
		map.put("will_happen", will_happen);
		map.put("FullDyDetailInfoTitle", "动态成本信息");
		
		//合同信息先显示
		map.put("ShowContractFirst",Boolean.TRUE);
		FullDyDetailInfoUI.showDialogWindows(ui, map);
	}

}