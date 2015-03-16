///**
// * output package name
// */
//package com.kingdee.eas.fdc.market.client;
//
//import java.awt.Dimension;
//import java.awt.Rectangle;
//import java.awt.event.ActionEvent;
//import java.awt.event.ItemListener;
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.EventListener;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.swing.JComponent;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//
//import org.apache.log4j.Logger;
//
//import com.kingdee.bos.BOSException;
//import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
//import com.kingdee.bos.ctrl.kdf.table.IColumn;
//import com.kingdee.bos.ctrl.kdf.table.IRow;
//import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
//import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
//import com.kingdee.bos.ctrl.kdf.table.KDTable;
//import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
//import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
//import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
//import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
//import com.kingdee.bos.ctrl.swing.KDComboBox;
//import com.kingdee.bos.ctrl.swing.KDDatePicker;
//import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
//import com.kingdee.bos.ctrl.swing.KDPromptBox;
//import com.kingdee.bos.ctrl.swing.KDSpinner;
//import com.kingdee.bos.ctrl.swing.KDTextField;
//import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
//import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
//import com.kingdee.bos.dao.IObjectValue;
//import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
//import com.kingdee.bos.metadata.entity.FilterInfo;
//import com.kingdee.bos.metadata.entity.FilterItemInfo;
//import com.kingdee.bos.metadata.entity.SelectorItemCollection;
//import com.kingdee.bos.metadata.entity.SelectorItemInfo;
//import com.kingdee.bos.ui.face.CoreUIObject;
//import com.kingdee.eas.common.EASBizException;
//import com.kingdee.eas.common.client.OprtState;
//import com.kingdee.eas.common.client.SysContext;
//import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
//import com.kingdee.eas.fdc.basecrm.CRMHelper;
//import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
//import com.kingdee.eas.fdc.basedata.FDCHelper;
//import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
//import com.kingdee.eas.fdc.basedata.MeasurePhaseFactory;
//import com.kingdee.eas.fdc.basedata.MeasurePhaseInfo;
//import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
//import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
//import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
//import com.kingdee.eas.fdc.market.AreaSetInfo;
//import com.kingdee.eas.fdc.market.MeasurePlanTargetCollection;
//import com.kingdee.eas.fdc.market.MeasurePlanTargetEntryCollection;
//import com.kingdee.eas.fdc.market.MeasurePlanTargetEntryInfo;
//import com.kingdee.eas.fdc.market.MeasurePlanTargetFactory;
//import com.kingdee.eas.fdc.market.MeasurePlanTargetInfo;
//import com.kingdee.eas.fdc.market.ValueBreakCollection;
//import com.kingdee.eas.fdc.market.ValueBreakEntryCollection;
//import com.kingdee.eas.fdc.market.ValueBreakEntryInfo;
//import com.kingdee.eas.fdc.market.ValueBreakFactory;
//import com.kingdee.eas.fdc.market.ValueBreakInfo;
//import com.kingdee.eas.fdc.market.VersionTypeEnum;
//import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
//import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
//import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
//import com.kingdee.eas.framework.ICoreBase;
//import com.kingdee.eas.util.SysUtil;
//import com.kingdee.eas.util.client.MsgBox;
//import com.kingdee.jdbc.rowset.IRowSet;
//
///**
// * output class name
// */
//public class ValueBreakEditUIbak extends AbstractValueBreakEditUI
//{
//    private static final Logger logger = CoreUIObject.getLogger(ValueBreakEditUIbak.class);
//    
//    /**
//     * output class constructor
//     */
//    public ValueBreakEditUIbak() throws Exception
//    {
//        super();
//        now = SysUtil.getAppServerTime(null);
//        
//    }
//
//
//	protected ICoreBase getBizInterface() throws Exception {
//		return ValueBreakFactory.getRemoteInstance();
//	}
//
//	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
//		IRow row = table.getFootManager().getFootRow(0);
//		BigDecimal totalAmount = (BigDecimal)row.getCell(5).getValue();
//		BigDecimal subAmount = (BigDecimal)row.getCell(9).getValue();
//		if(totalAmount.compareTo(subAmount)!=0){
//			MsgBox.showInfo("货值分解总金额不等于项目总货值总金额，请调整相等后提交！");
//			SysUtil.abort();
//		}
//		this.setOprtState("STATUS_VIEW");
//		super.actionSubmit_actionPerformed(e);
//	}
//	protected void afterSubmitAddNew(){
//	}
//	protected boolean isContinueAddNew() {
//		return false;
//	}
//	public MeasurePlanTargetCollection getMeasurePlanTargetColl(){
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("projectAgin.id",sellProjectID));
//		filter.getFilterItems().add(new FilterItemInfo("isNew",new Boolean(true)));
//		view.setFilter(filter);
//		MeasurePlanTargetCollection coll = new MeasurePlanTargetCollection();
//		view.setSelector(getMeasurePlanTarget());
//		
//		try {
//			coll = MeasurePlanTargetFactory.getRemoteInstance().getMeasurePlanTargetCollection(view);
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		return coll;
//	}
//
//	public SelectorItemCollection getMeasurePlanTarget(){
//		SelectorItemCollection sic = new SelectorItemCollection();
//        sic.add(new SelectorItemInfo("isNew"));
//        sic.add(new SelectorItemInfo("creator.*"));
//        sic.add(new SelectorItemInfo("createTime"));
//        sic.add(new SelectorItemInfo("number"));
//        sic.add(new SelectorItemInfo("bizDate"));
//        sic.add(new SelectorItemInfo("auditor.*"));
//        sic.add(new SelectorItemInfo("orgUnit.*"));
//        sic.add(new SelectorItemInfo("state"));
//        sic.add(new SelectorItemInfo("auditTime"));
//        sic.add(new SelectorItemInfo("measurePhases.*"));
//        sic.add(new SelectorItemInfo("projectAgin.*"));
//        sic.add(new SelectorItemInfo("versions"));
//        sic.add(new SelectorItemInfo("versionsName"));
//        sic.add(new SelectorItemInfo("measureType"));
//        sic.add(new SelectorItemInfo("adjustCause"));
//        sic.add(new SelectorItemInfo("totalAmount"));
//        sic.add(new SelectorItemInfo("remarks"));
//        sic.add(new SelectorItemInfo("versionType"));
//        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
//        sic.add(new SelectorItemInfo("lastUpdateTime"));
//        sic.add(new SelectorItemInfo("entry.*"));
//        sic.add(new SelectorItemInfo("entry.areaRange"));
//        sic.add(new SelectorItemInfo("entry.productType.*"));
//        sic.add(new SelectorItemInfo("entry.newAreaRange.*"));
//        sic.add(new SelectorItemInfo("entry.productType.*"));
//		return sic;
//	}
//
//	protected IObjectValue createNewData() {
//		if((SellProjectInfo)getUIContext().get("sellPorject")!=null){
//			sellProjectID = ((SellProjectInfo)getUIContext().get("sellPorject")).getId().toString();
//		}
//		ValueBreakInfo valueBreakInfo = new ValueBreakInfo();
//		MeasurePlanTargetCollection coll = getMeasurePlanTargetColl();
//		if(coll.size()==0){
//			MsgBox.showInfo("未有生效的目标货值测算！");
//			SysUtil.abort();
//		}else{
//			CRMHelper.sortCollection(coll, "versions", false);
//			CRMHelper.sortCollection(coll, "createTime", false);
//			String measurePlanTargetID = coll.get(0).getId().toString();
//			String measurePhaseID  = coll.get(0).getMeasurePhases().getId().toString();
//			ValueBreakCollection valueBreakColl = new ValueBreakCollection();
//			try {
//				valueBreakColl = getValueBreakColl(measurePlanTargetID,measurePhaseID);
//				//获取该项目最新的记录数据   即页面上显示是最新版本的记录
//				ValueBreakCollection newColl = getNewVersionValueBreakColl(sellProjectID);
////				if(newColl!=null && newColl.size()>0){
////					String newVersoin = newColl.get(0).getVersion();
////					MonthPlanInfo monthInfo = MonthPlanEditUI.getMonthPlanInfo(newVersoin,sellProjectID);
////					if(monthInfo==null ||monthInfo.getId()==null){
////						MsgBox.showInfo("最新版本的年度货值分解还未生成销售月计划，请先编写销售月计划！");
////						SysUtil.abort();
////					}
////				}
//				if(newColl.size()!=0){//修订
//					CRMHelper.sortCollection(valueBreakColl, "version", false);
//					CRMHelper.sortCollection(valueBreakColl, "createTime", false);
//					String valueBreakID =  newColl.get(0).getId().toString();
//					valueBreakInfo = ValueBreakFactory.getRemoteInstance().getValueBreakInfo(new ObjectUuidPK(valueBreakID),this.getSelectors());
//					if(valueBreakColl.size()>0){
//						String oldVersoin = valueBreakColl.get(0).getVersion();
//						String last = oldVersoin.substring(oldVersoin.length()-1);
//						String verIndex = String.valueOf(Integer.parseInt(last)+1);
//						valueBreakInfo.setVersion(oldVersoin.substring(0,oldVersoin.length()-1)+verIndex);
//					}else{
//						MeasurePlanTargetEntryCollection measurePlanTargetEntryColl =  coll.get(0).getEntry();
//		    			CRMHelper.sortCollection(measurePlanTargetEntryColl, "seq", true);
//		    			ValueBreakEntryCollection valueBreakEntryCol=(ValueBreakEntryCollection) valueBreakInfo.getEntrys().clone();
//		    			CRMHelper.sortCollection(valueBreakEntryCol, "seq", true);
//		    			
//		    			valueBreakInfo.getEntrys().clear();
//		    			
//		    			for(int i = 0;i<measurePlanTargetEntryColl.size();i++){
//		    				if(measurePlanTargetEntryColl.get(i).getProductType()==null) continue;
//		    				boolean isAdd=true;
//		    				ValueBreakEntryInfo valueBreakEntryInfo=null;
//		    				int cycle=0;
//		    				for(int j=0;j<valueBreakEntryCol.size();j++){
//		    					if(valueBreakEntryCol.get(j).getProductType()==null||valueBreakEntryCol.get(j).getType()==null||valueBreakEntryCol.get(j).getYear()!=0) continue;
//		    					if(valueBreakEntryCol.get(j).getNewAreaRange()==null){
//		    						if(valueBreakEntryCol.get(j).getProductType().getId().equals(measurePlanTargetEntryColl.get(i).getProductType().getId())
//			    							&&valueBreakEntryCol.get(j).getType().equals(measurePlanTargetEntryColl.get(i).getProductConstitute())){
//			    						isAdd=false;
//			    						valueBreakEntryInfo=valueBreakEntryCol.get(j);
//			    						MeasurePlanTargetEntryInfo measurePlanTargetEntryInfo = measurePlanTargetEntryColl.get(i);
//			    						//wyh
////			    						valueBreakEntryInfo.setAreaRange(measurePlanTargetEntryInfo.getAreaRange());//面积段
//			    						valueBreakEntryInfo.setNewAreaRange(measurePlanTargetEntryInfo.getNewAreaRange());//自定义面积段
//					    				valueBreakEntryInfo.setArea(measurePlanTargetEntryInfo.getAcreage());//面积
//					    				valueBreakEntryInfo.setPloidy(measurePlanTargetEntryInfo.getQuantity());//套数
//					    				valueBreakEntryInfo.setPrice(measurePlanTargetEntryInfo.getPrice());//均价
//					    				valueBreakEntryInfo.setAmount(measurePlanTargetEntryInfo.getSumAmount());//金额
//			    						valueBreakInfo.getEntrys().add(valueBreakEntryInfo);
//			    						cycle=j;
//			    						break;
//			    					}
//		    					}else{
//		    						if(valueBreakEntryCol.get(j).getProductType().getId().equals(measurePlanTargetEntryColl.get(i).getProductType().getId())
//			    							&&valueBreakEntryCol.get(j).getType().equals(measurePlanTargetEntryColl.get(i).getProductConstitute())
//			    								&&valueBreakEntryCol.get(j).getNewAreaRange().getId().toString().equals(measurePlanTargetEntryColl.get(i).getNewAreaRange().getId().toString())){
//			    						isAdd=false;
//			    						valueBreakEntryInfo=valueBreakEntryCol.get(j);
//			    						MeasurePlanTargetEntryInfo measurePlanTargetEntryInfo = measurePlanTargetEntryColl.get(i);
//			    						//wyh
//			    						valueBreakEntryInfo.setNewAreaRange(measurePlanTargetEntryInfo.getNewAreaRange());//自定义面积段
//					    				valueBreakEntryInfo.setArea(measurePlanTargetEntryInfo.getAcreage());//面积
//					    				valueBreakEntryInfo.setPloidy(measurePlanTargetEntryInfo.getQuantity());//套数
//					    				valueBreakEntryInfo.setPrice(measurePlanTargetEntryInfo.getPrice());//均价
//					    				valueBreakEntryInfo.setAmount(measurePlanTargetEntryInfo.getSumAmount());//金额
//			    						valueBreakInfo.getEntrys().add(valueBreakEntryInfo);
//			    						cycle=j;
//			    						break;
//			    					}
//		    					}
//		    				}
//		    				if(isAdd){
//		    					valueBreakEntryInfo=new ValueBreakEntryInfo();
//		    					MeasurePlanTargetEntryInfo measurePlanTargetEntryInfo = measurePlanTargetEntryColl.get(i);
//			    				valueBreakEntryInfo.setType(measurePlanTargetEntryInfo.getProductConstitute());//产品构成
////			    				if(measurePlanTargetEntryInfo.getProductType()!=null){
////									String productTypeID = measurePlanTargetEntryInfo.getProductType().getId().toString();
////									
////									ProductTypeInfo productTypeInfo =  ProductTypeFactory.getRemoteInstance().getProductTypeInfo(new ObjectUuidPK(productTypeID));
////									valueBreakEntryInfo.setProductType(productTypeInfo);//产品类型
////			    				}
//			    				//wyh
//			    				valueBreakEntryInfo.setProductType(measurePlanTargetEntryInfo.getProductType());
//	    						valueBreakEntryInfo.setAreaRange(measurePlanTargetEntryInfo.getAreaRange());//面积段
//	    						valueBreakEntryInfo.setNewAreaRange(measurePlanTargetEntryInfo.getNewAreaRange());//自定义面积段
//			    				valueBreakEntryInfo.setArea(measurePlanTargetEntryInfo.getAcreage());//面积
//			    				valueBreakEntryInfo.setPloidy(measurePlanTargetEntryInfo.getQuantity());//套数
//			    				valueBreakEntryInfo.setPrice(measurePlanTargetEntryInfo.getPrice());//均价
//			    				valueBreakEntryInfo.setAmount(measurePlanTargetEntryInfo.getSumAmount());//金额
//			    				valueBreakEntryInfo.setYear(0);
//			    				valueBreakInfo.getEntrys().add(valueBreakEntryInfo);
//		    				}else{
//		    					for(int j=1;j<valueBreakInfo.getCycle();j++){
//		    						valueBreakInfo.getEntrys().add(valueBreakEntryCol.get(cycle+j));
//		    					}
//		    				}
//		    			}
//						
//						valueBreakInfo.setVersion(coll.get(0).getVersions()+".0");
//					}
//					valueBreakInfo.setTotalAmount(coll.get(0));
//					String sellProjectID = valueBreakInfo.getSellProject().getId().toString();
//					SellProjectInfo sellProjectInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectID));
//					valueBreakInfo.setSellProject(sellProjectInfo);
//					valueBreakInfo.setId(null);
//					valueBreakInfo.setAuditTime(null);
//					valueBreakInfo.setCreateTime(null);
//					valueBreakInfo.setLastUpdateTime(null);
//					valueBreakInfo.setAuditor(null);
//					valueBreakInfo.setCreator(null);
//					valueBreakInfo.setLastUpdateUser(null);
//				}else{//新增
//					valueBreakInfo.setVersion(coll.get(0).getVersions()+".0");
//					valueBreakInfo.setYear(getYear(now));
//					valueBreakInfo.setTotalAmount(coll.get(0));
//					String sellProjectID = coll.get(0).getProjectAgin().getId().toString();
//					SellProjectInfo sellProjectInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectID));
//					valueBreakInfo.setSellProject(sellProjectInfo);
//	    			MeasurePlanTargetEntryCollection measurePlanTargetEntryColl =  coll.get(0).getEntry();
//	    			CRMHelper.sortCollection(measurePlanTargetEntryColl, "seq", true);
//	    			for(int i = 0;i<measurePlanTargetEntryColl.size();i++){
//	    				MeasurePlanTargetEntryInfo measurePlanTargetEntryInfo = measurePlanTargetEntryColl.get(i);
//	    				ValueBreakEntryInfo valueBreakEntryInfo = new ValueBreakEntryInfo();
//	    				valueBreakEntryInfo.setType(measurePlanTargetEntryInfo.getProductConstitute());//产品构成
////	    				if(measurePlanTargetEntryInfo.getProductType()!=null){
////							String productTypeID = measurePlanTargetEntryInfo.getProductType().getId().toString();
////							ProductTypeInfo productTypeInfo =  ProductTypeFactory.getRemoteInstance().getProductTypeInfo(new ObjectUuidPK(productTypeID));
////							valueBreakEntryInfo.setProductType(productTypeInfo);//产品类型
////	    				}
//	    				//wyh
//	    				valueBreakEntryInfo.setProductType(measurePlanTargetEntryInfo.getProductType());
//						valueBreakEntryInfo.setAreaRange(measurePlanTargetEntryInfo.getAreaRange());//面积段
//						valueBreakEntryInfo.setNewAreaRange(measurePlanTargetEntryInfo.getNewAreaRange());//自定义面积段
//	    				valueBreakEntryInfo.setArea(measurePlanTargetEntryInfo.getAcreage());//面积
//	    				valueBreakEntryInfo.setPloidy(measurePlanTargetEntryInfo.getQuantity());//套数
//	    				valueBreakEntryInfo.setPrice(measurePlanTargetEntryInfo.getPrice());//均价
//	    				valueBreakEntryInfo.setAmount(measurePlanTargetEntryInfo.getSumAmount());//金额
//	    				valueBreakEntryInfo.setYear(0);//新增时候年设置为0
//	    				valueBreakInfo.getEntrys().add(valueBreakEntryInfo);
//	    			}
//
//				}
//				valueBreakInfo.setIsNewVersoin(false);
//				valueBreakInfo.setVersionType(coll.get(0).getVersionType());
//				valueBreakInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
//				String measurePhasesID = coll.get(0).getMeasurePhases().getId().toString();
//				MeasurePhaseInfo measurePhasesInfo = MeasurePhaseFactory.getRemoteInstance().getMeasurePhaseInfo(new ObjectUuidPK(measurePhasesID));
//				valueBreakInfo.setMeasureStage(measurePhasesInfo);
//				valueBreakInfo.setMeasureType(measurePhasesInfo.getPhaseType());
//				valueBreakInfo.setState(FDCBillStateEnum.SAVED);
//				valueBreakInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
//				
//			} catch (BOSException e) {
//				e.printStackTrace();
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			}
//		}
//		valueBreakInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
//		return valueBreakInfo;
//	}
//
//	//构建分录产品类型为f7控件
//	private KDBizPromptBox f7productType=null;
//	public KDBizPromptBox  getF7productType(){
//		if(f7productType==null){
//			f7productType = new KDBizPromptBox();
//			f7productType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("isEnabled", new Integer(1)));
//			view.setFilter(filter);
//			f7productType.setEntityViewInfo(view);
//			f7productType.setEditable(true);
//			f7productType.setDisplayFormat("$name$");
//			f7productType.setEditFormat("$number$");
//			f7productType.setCommitFormat("$number$");	
//		}
//		return f7productType;
//	}
//	
//	public void loadFields() {
//        
//		detachListeners();
//		super.loadFields();
//		setSaveActionStatus();
//
//        int idx = idList.getCurrentIndex();
//        if (idx >= 0) {
//            billIndex = "(" + (idx + 1) + ")";
//        } else {
//        	billIndex = "";
//        }
//		refreshUITitle();
//		setAuditButtonStatus(this.getOprtState());
//		
//		cycle = editData.getCycle();
//		year = editData.getYear();
//		
//		txtYear.setValue(Integer.valueOf(editData.getYear()));
//		txtCycle.setValue(Integer.valueOf(editData.getCycle()));
//		measurePlanTargetInfo = editData.getTotalAmount();
//		sellProjectID = ((SellProjectInfo)editData.getSellProject()).getId().toString();
//
//		try {
//			initTable();
//			loadEntry();
//		} catch (EASBizException e) {
//			logger.error(e.getMessage());
//		} catch (BOSException e) {
//			logger.error(e.getMessage());
//		}
//		attachListeners();
//	}
//	//加载分录数据
//	public void loadEntry() throws EASBizException, BOSException{
//		table.removeRows();
//		table.checkParsed();
//		table.getColumnCount();
//		ValueBreakEntryCollection valueBreakEntryColl = editData.getEntrys();
//		CRMHelper.sortCollection(valueBreakEntryColl, "seq", true);
//		IRow row = null;
//		//将目标预测值填写到table里
//		for(int i = 0;i<valueBreakEntryColl.size();i++){
//			ValueBreakEntryInfo valueBreakEntryInfo = valueBreakEntryColl.get(i);
//			if(valueBreakEntryInfo.getYear()==0){
//				if(valueBreakEntryInfo.getProductType()==null){
//					continue;
//				}
//				row = table.addRow();
//				row.getCell(0).setValue(valueBreakEntryInfo.getType());
//				if(valueBreakEntryInfo.getProductType()!=null){
//					String productTypeID = valueBreakEntryInfo.getProductType().getId().toString();
//					//产品类型
//					ProductTypeInfo productTypeInfo = ProductTypeFactory.getRemoteInstance().getProductTypeInfo(new ObjectUuidPK(productTypeID));
//					//年度分解分录
//					valueBreakEntryInfo.setProductType(productTypeInfo);
//					row.getCell(1).setValue(valueBreakEntryInfo.getProductType());
//				}
////				if(valueBreakEntryInfo.getAreaRange() != null){
////					row.getCell(2).setValue(valueBreakEntryInfo.getAreaRange());
////				}
//				if(valueBreakEntryInfo.getNewAreaRange() != null){
//					row.getCell(2).setValue(valueBreakEntryInfo.getNewAreaRange());
//				}
//				row.getCell(3).setValue(valueBreakEntryInfo.getArea().setScale(2, 4));
//				row.getCell(4).setValue(Integer.valueOf(valueBreakEntryInfo.getPloidy()));
//				row.getCell(5).setValue(valueBreakEntryInfo.getPrice().setScale(2, 4));
//				row.getCell(6).setValue(valueBreakEntryInfo.getAmount().setScale(2, 4));
//			}
//			
//			for(int j = 0;j<cycle;j++){
//				if(valueBreakEntryInfo.getYear()==year+j){
//					if(valueBreakEntryInfo.getYear()<getYear(now)){
////						row.getCell(11+j*gapColumn).getStyleAttributes().setLocked(true);
////						row.getCell(11+j*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
////						row.getCell(12+j*gapColumn).getStyleAttributes().setLocked(true);
////						row.getCell(12+j*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
////						row.getCell(13+j*gapColumn).getStyleAttributes().setLocked(true);
////						row.getCell(13+j*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
////						row.getCell(14+j*gapColumn).getStyleAttributes().setLocked(true);
////						row.getCell(14+j*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//						
//					}
//					if(valueBreakEntryInfo.getArea()!=null&&valueBreakEntryInfo.getArea().compareTo(FDCHelper.ZERO)==0){
//						row.getCell(11+j*gapColumn).setValue(null);
//					}else{
//						if(valueBreakEntryInfo.getPrice() == null){
//							row.getCell(11+j*gapColumn).setValue(null);
//						}
//						else{
//							row.getCell(11+j*gapColumn).setValue(((BigDecimal)valueBreakEntryInfo.getArea()).setScale(2,BigDecimal.ROUND_HALF_UP));
//						}
//					}
//					if(valueBreakEntryInfo.getPloidy()==0){
//						row.getCell(12+j*gapColumn).setValue(null);
//					}else{
//						row.getCell(12+j*gapColumn).setValue(Integer.valueOf(valueBreakEntryInfo.getPloidy()));
//					}
//					if(valueBreakEntryInfo.getPrice()!=null&&valueBreakEntryInfo.getPrice().compareTo(FDCHelper.ZERO)==0){
//						row.getCell(13+j*gapColumn).setValue(null);
//					}else{
//						if(valueBreakEntryInfo.getPrice() == null){
//							row.getCell(13+j*gapColumn).setValue(null);
//						}
//						else{
//							row.getCell(13+j*gapColumn).setValue(((BigDecimal)valueBreakEntryInfo.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP));
//						}
//					}
//					if(valueBreakEntryInfo.getAmount()!=null&&valueBreakEntryInfo.getAmount().compareTo(FDCHelper.ZERO)==0){
//						row.getCell(14+j*gapColumn).setValue(null);
//					}else{
//						if(valueBreakEntryInfo.getPrice() == null){
//							row.getCell(14+j*gapColumn).setValue(null);
//						}
//						else{
//							row.getCell(14+j*gapColumn).setValue(((BigDecimal)valueBreakEntryInfo.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP));
//						}
//					}
//				}
//			}
//		}
//
//		for(int i=0;i<table.getRowCount();i++){
//			for(int j=3;j<table.getColumnCount();j++){
//				updateTable(j,i);
//			}
//		}		
//		addSumRow();
//	}
//
//    public void storeFields(){
//    	storeEntry();
//        super.storeFields(); 
//    }
//    //存分录值
//    public void storeEntry(){
//		editData.getEntrys().clear();
//		int k = 0;
//    	for (int i=0; i<table.getRowCount();i++){
//    		IRow row = table.getRow(i);
//    		if(row.getCell(1).getValue() instanceof String)
//    			continue;
//    		if(row.getCell(2).getValue() != null){
//    			if(row.getCell(2).getValue().equals("小计")){
//    				continue;
//    			}
//    		}
//    		ValueBreakEntryInfo valueBreakEntryInfo = null;
//    		for(int j=0;j<cycle+2;j++){
//    			if(j==1)
//    				continue;
//    			valueBreakEntryInfo = new ValueBreakEntryInfo();
//    			k++;
//    			valueBreakEntryInfo.setType((PlanIndexTypeEnum)row.getCell(0).getValue());
//    			valueBreakEntryInfo.setProductType((ProductTypeInfo)row.getCell(1).getValue());
//    			if(row.getCell(2).getValue() instanceof AreaSetInfo){
//    				valueBreakEntryInfo.setNewAreaRange((AreaSetInfo)row.getCell(2).getValue());
//    			}
//    			if(j==0){
//	    			valueBreakEntryInfo.setYear(j);
//    			}else{
//    				valueBreakEntryInfo.setYear(year+j-2);
//    			}
//    			if(row.getCell(3+j*gapColumn).getValue() != null){
//        			valueBreakEntryInfo.setArea((BigDecimal)row.getCell(3+j*gapColumn).getValue());
//    			}
//    			else{
//    				valueBreakEntryInfo.setArea(new BigDecimal("0.00"));
//    			}
//    			if(row.getCell(4+j*gapColumn).getValue()!=null){
//    				if(row.getCell(4+j*gapColumn).getValue() instanceof Integer){
//    					valueBreakEntryInfo.setPloidy(((Integer)row.getCell(4+j*gapColumn).getValue()).intValue());
//    				}
//    				else{
//    					valueBreakEntryInfo.setPloidy(((BigDecimal)row.getCell(4+j*gapColumn).getValue()).intValue());
//    				}
//    			}
//    			else{
//    				valueBreakEntryInfo.setPloidy(0);
//    			}
//    			if(row.getCell(5+j*gapColumn).getValue() != null){
//        			valueBreakEntryInfo.setPrice((BigDecimal)row.getCell(5+j*gapColumn).getValue());
//    			}
//    			else{
//    				valueBreakEntryInfo.setPrice(new BigDecimal("0.00"));
//    			}
//    			if(row.getCell(6+j*gapColumn).getValue() != null){
//        			valueBreakEntryInfo.setAmount((BigDecimal)row.getCell(6+j*gapColumn).getValue());
//    			}
//    			else{
//    				valueBreakEntryInfo.setAmount(new BigDecimal("0.00"));
//    			}
//    			valueBreakEntryInfo.setSeq(k);
//    			editData.getEntrys().add(valueBreakEntryInfo);
//    		}
//    	}
//		editData.setCycle(cycle);
//		editData.setYear(year);
//    }
//    public ValueBreakCollection getValueBreakColl(String measurePlanTargetId,String measurePhaseId) throws BOSException{
//    	ValueBreakCollection valueBreakColl = ValueBreakFactory.getRemoteInstance()
//    	    .getValueBreakCollection("select * from where totalAmount ='"+measurePlanTargetId+"' and measureStage ='"+measurePhaseId+"'");
//    	return valueBreakColl;
//    }
//    
//    public ValueBreakCollection getNewVersionValueBreakColl(String sellProjectId) throws BOSException{
//    	ValueBreakCollection valueBreakColl = ValueBreakFactory.getRemoteInstance()
//    	    .getValueBreakCollection("select * from where sellProject='"+sellProjectId+"' and isNewVersoin='1' ");
//    	return valueBreakColl;
//    }
//    
//    public void addSumRow(){
//		//把需要合计的列加到set里面
//		Set columnName = new HashSet();
//		for(int i=0;i<table.getColumnCount();i++){
//			if(i==0||i==1||i==2){
//				continue;
//			}
//			columnName.add(String.valueOf(i));
//		}
//		
//		MarketHelpForSec.getYearFootRow(table, columnName);//总合计
//		MarketHelpForSec.setTotalRow2(table,true);//各产品构成合计
//    }
//	public void onLoad() throws Exception {
//		super.onLoad();
//		this.setPreferredSize(getMaximumSize());
//		
//		this.menuTable1.setVisible(false);
//		this.btnAddLine.setVisible(false);
//		this.btnInsertLine.setVisible(false);
//		this.btnRemoveLine.setVisible(false);
//		this.actionCreateFrom.setVisible(false);
//		this.actionTraceDown.setVisible(false);
//		this.actionTraceUp.setVisible(false);
//		this.actionCopy.setVisible(false);
//		this.actionCopyFrom.setVisible(false);
//		this.chkMenuItemSubmitAndAddNew.setVisible(false);
//		this.chkMenuItemSubmitAndAddNew.setSelected(false);
//		this.chkMenuItemSubmitAndPrint.setVisible(false);
//		this.chkMenuItemSubmitAndPrint.setSelected(false);
//		this.menuBiz.setVisible(false);
//		this.menuSubmitOption.setVisible(false);
//		
//		this.actionAddNew.setVisible(false);
//		this.txtNumber.setRequired(true);
//		
//		if(getYear(now)>editData.getYear()){
//			txtYear.setEnabled(false);
//			contYear.setEnabled(false);
//		}
//		this.txtYear.setEditor(new com.kingdee.bos.ctrl.swing.KDSpinner.DefaultNumberEditor(this.txtYear,""));
//		this.txtCycle.setEditor(new com.kingdee.bos.ctrl.swing.KDSpinner.DefaultNumberEditor(this.txtCycle,""));
//		
//		versionType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//		versionType.setEnabled(false);
//
//		if(this.oprtState.equals(OprtState.ADDNEW)){
//			//取历史数据
//			int sapYear = getYear(now)-editData.getYear();
//			for (int i=0;i<=sapYear;i++){
//				getPastYear(editData.getYear()+i,i+1);
//			}
//		}
//		if(this.oprtState.equals(OprtState.EDIT)){
//			txtYear.setEnabled(true);
//			contYear.setEnabled(true);
//		}
//	}
//	//获取日期年份
//    public static int getYear(Date dateTime)
//    {
//       Calendar c=Calendar.getInstance();
//       c.setTime(dateTime);
//       int year=c.get(c.YEAR);
//       return year;
//    }
//    private void updateTableSelf(){
//		boolean isNew=true;
//		for(int i = 0;i<cycle;i++){
//			if(cycle>1&&i==cycle-2){
//				for(int j=0;j<table.getRowCount();j++){
//					if(table.getRow(j).getCell(1).getValue().toString().equals("合计")){
//						continue;
//					}
//					if(table.getRow(j).getCell(2).getValue() != null){
//						if(table.getRow(j).getCell(2).getValue().toString().equals("小计"))
//							{continue;}
//					}
//					if(table.getRow(j).getCell(11+i*gapColumn).getValue()==null||
//							!table.getRow(j).getCell(11+i*gapColumn).getValue().equals(table.getRow(j).getCell(3).getValue())){
//						isNew=false;
//						break;
//					}
//					if(table.getRow(j).getCell(12+i*gapColumn).getValue()==null||
//							!table.getRow(j).getCell(12+i*gapColumn).getValue().equals(table.getRow(j).getCell(4).getValue())){
//						isNew=false;
//						break;
//					}
//					if(table.getRow(j).getCell(13+i*gapColumn).getValue()==null||
//							!table.getRow(j).getCell(13+i*gapColumn).getValue().equals(table.getRow(j).getCell(5).getValue())){
//						isNew=false;
//						break;
//					}
//					if(table.getRow(j).getCell(14+i*gapColumn).getValue()==null||
//							!table.getRow(j).getCell(14+i*gapColumn).getValue().equals(table.getRow(j).getCell(6).getValue())){
//						isNew=false;
//						break;
//					}
//				}
//			}else if(i<cycle-1){
//				for(int j=0;j<table.getRowCount();j++){
//					if(table.getRow(j).getCell(1).getValue().toString().equals("合计")){
//						continue;
//					}
//					if(table.getRow(j).getCell(2).getValue() != null){
//						if(table.getRow(j).getCell(2).getValue().toString().equals("小计"))
//							{continue;}
//					}
//					if(!(table.getRow(j).getCell(11+i*gapColumn).getValue()==null||
//							table.getRow(j).getCell(11+i*gapColumn).getValue().equals(FDCHelper.ZERO))){
//						isNew=false;
//						break;
//					}
//					if(!(table.getRow(j).getCell(12+i*gapColumn).getValue()==null
//							||table.getRow(j).getCell(12+i*gapColumn).getValue().equals(new Integer(0)))){
//						isNew=false;
//						break;
//					}
//					if(!(table.getRow(j).getCell(13+i*gapColumn).getValue()==null
//							||table.getRow(j).getCell(13+i*gapColumn).getValue().equals(FDCHelper.ZERO))){
//						isNew=false;
//						break;
//					}
//					if(!(table.getRow(j).getCell(14+i*gapColumn).getValue()==null
//							||table.getRow(j).getCell(14+i*gapColumn).getValue().equals(FDCHelper.ZERO))){
//						isNew=false;
//						break;
//					}
//				}
//			}
//		}
//		if(isNew){
//			if(cycle>1){
//				for(int i=0;i<table.getRowCount();i++){
//					for(int j=0;j<cycle-1;j++){
//						if(table.getRow(i).getCell(1).getValue().toString().equals("合计")){
//							table.getRow(i).getCell(11+j*gapColumn).setValue(new Long(0));
//							table.getRow(i).getCell(12+j*gapColumn).setValue(new Long(0));
//							table.getRow(i).getCell(13+j*gapColumn).setValue(null);
//							table.getRow(i).getCell(14+j*gapColumn).setValue(new Long(0));
//							continue;
//						}
//						if(table.getRow(i).getCell(2).getValue() != null){
//							if(table.getRow(i).getCell(2).getValue().toString().equals("小计")){
//								table.getRow(i).getCell(11+j*gapColumn).setValue(new Long(0));
//								table.getRow(i).getCell(12+j*gapColumn).setValue(new Long(0));
//								table.getRow(i).getCell(13+j*gapColumn).setValue(null);
//								table.getRow(i).getCell(14+j*gapColumn).setValue(new Long(0));
//								continue;
//							}
//						}
//						table.getRow(i).getCell(11+j*gapColumn).setValue(null);
//						table.getRow(i).getCell(12+j*gapColumn).setValue(null);
//						table.getRow(i).getCell(13+j*gapColumn).setValue(null);
//						table.getRow(i).getCell(14+j*gapColumn).setValue(null);
//					}
//				}
//			}
//			for(int i=0;i<table.getRowCount();i++){
//				table.getRow(i).getCell(11+(cycle-1)*gapColumn).setValue(table.getRow(i).getCell(3).getValue());
//				table.getRow(i).getCell(12+(cycle-1)*gapColumn).setValue(table.getRow(i).getCell(4).getValue());
//				table.getRow(i).getCell(13+(cycle-1)*gapColumn).setValue(table.getRow(i).getCell(5).getValue());
//				table.getRow(i).getCell(14+(cycle-1)*gapColumn).setValue(table.getRow(i).getCell(6).getValue());
//			}
//		}
//		for(int i=0;i<table.getRowCount();i++){
//			if(table.getRow(i).getCell(1).getValue().toString().equals("合计")){
//				continue;
//			}
//			if(table.getRow(i).getCell(2).getValue() != null){
//				if(table.getRow(i).getCell(2).getValue().toString().equals("小计")){
//					continue;
//				}
//			}
//			for(int j=2;j<table.getColumnCount();j++){
//				updateTable(j,i);
//			}
//		}
//		Set columnName = new HashSet();
//		for(int i=6;i<table.getColumnCount();i++){
//			columnName.add(String.valueOf(i));
//		}
//		MarketHelpForSec.getYearFootRow(table, columnName);
//    }
//    //周期变化
//	protected void txtCycle_stateChanged(ChangeEvent e) throws Exception {
//		storeEntry();
//		if(((Integer)txtCycle.getValue()).intValue()<0){
//			txtCycle.setValue(new Integer(0));
//		}
//		cycle = ((Integer)txtCycle.getValue()).intValue();
//		kdtPanel.removeAll();
//		initTable();
//		table.repaint();
//		table.refresh();
//		loadEntry();
//		updateTableSelf();
//	}
//	//年份改变
//	protected void txtYear_stateChanged(ChangeEvent e) throws Exception {
//		storeEntry();
//		year = ((Integer)txtYear.getValue()).intValue();
//		kdtPanel.removeAll();
//		initTable();
//		table.repaint();
//		table.refresh();
//		loadEntry();
//		getPastYear(year,1);
//		updateTableSelf();
//	}
//	private void getPastYear(int year,int gapYear) throws EASBizException, BOSException, SQLException{
//		if(cycle!=0&&year<getYear(now)&&sellProjectID!=null&&measurePlanTargetInfo!=null){
//			for(int i=0;i<table.getRowCount();i++){
//				IRow row = table.getRow(i);
//				if(row.getCell(1).getValue() instanceof String){
//					continue;
//				}
//
//				if(row.getCell(2).getValue()  != null){
//					if(row.getCell(2).getValue().toString().equals("小计")){
//						continue;
//					}
//				}
//				String productTypeID = ((ProductTypeInfo)row.getCell(1).getValue()).getId().toString();
//				//设置货值分解合计
//				activityAmount(row,sellProjectID,productTypeID,year,gapYear);
//			}
//		}
//	}
//	//构建table
//	public void initTable(){
//		table=new KDTable(11+cycle*gapColumn,2,0);
//		KDTMergeManager mm = table.getHeadMergeManager();
//		for(int i=0;i<3;i++){
//			mm.mergeBlock(0, i, 1, i, KDTMergeManager.SPECIFY_MERGE);
//			mm.mergeBlock(0, i, 1, i, KDTMergeManager.SPECIFY_MERGE);
//		}
//		for(int i=0;i<cycle+2;i++){
//			mm.mergeBlock(0, 3+i*gapColumn, 0, 6+i*gapColumn, KDTMergeManager.SPECIFY_MERGE);
//		}
//		firstHead = table.getHeadRow(0);
//
//		firstHead.getCell(0).setValue("产品构成");
//		firstHead.getCell(1).setValue("产品类型");
//		//wyh
//		firstHead.getCell(2).setValue("面积段");
//		firstHead.getCell(3).setValue("项目总货值");
//		firstHead.getCell(7).setValue("货值分解合计");
//		for(int i=0;i<cycle;i++){
//			firstHead.getCell(11+i*gapColumn).setValue(year+i+"年货值");
//		}
//		secondHead = table.getHeadRow(1);
//		secondHead.getCell(0).setValue("");
//		secondHead.getCell(1).setValue("");
//		secondHead.getCell(2).setValue("");
//		for(int i=0;i<cycle+2;i++){
//			secondHead.getCell(3+i*gapColumn).setValue("销售面积");
//			secondHead.getCell(4+i*gapColumn).setValue("销售套数");
//			secondHead.getCell(5+i*gapColumn).setValue("销售均价");
//			secondHead.getCell(6+i*gapColumn).setValue("销售金额");
//		}
//		for(int i = 0;i<table.getColumnCount();i++){
//			IColumn column = table.getColumn(i);
//			if(i==0){
//				//产品构成
//				ICellEditor combEditor = CommerceHelper.getKDComboColorEditor();
//				column.setEditor(combEditor);
//				column.getStyleAttributes().setLocked(true);
//				column.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//			}else if(i==1){
//				//产品类型 F7
//				ICellEditor f7Editor = new KDTDefaultCellEditor(getF7productType());
//				column.setEditor(f7Editor);
//				column.getStyleAttributes().setLocked(true);
//				column.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//			}else if(i==2){
//				//面积段
//				KDBizPromptBox f7AreaRange = new KDBizPromptBox();
//		    	EntityViewInfo view = new EntityViewInfo();
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
//				view.setFilter(filter);
//				f7AreaRange.setQueryInfo("com.kingdee.eas.fdc.market.app.AreaSetQuery");
//				f7AreaRange.setEntityViewInfo(view);
//				f7AreaRange.setEditable(true);
//				f7AreaRange.setDisplayFormat("$description$");
//				f7AreaRange.setEditFormat("$number$");
//				f7AreaRange.setCommitFormat("$number$");
//		    	ICellEditor f7Area = new KDTDefaultCellEditor(f7AreaRange);
//		    	column.setEditor(f7Area);
//				column.getStyleAttributes().setLocked(true);
//				column.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//			}
//			else if(i%4==0||i==4){
//				//套数 整数
//				ICellEditor integerEditor = getCellIntegerNumberEdit();
//				column.setEditor(integerEditor);
//				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//			}
//			else{
//				//面积,均价,金额 
//				ICellEditor bigDecimalEditor = CommerceHelper.getKDFormattedTextDecimalEditor();
//				column.setEditor(bigDecimalEditor);
//				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//				if(i>11&&i%4==2){
//					column.getStyleAttributes().setLocked(true);
//					column.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//				}
//				
//			}
//			if(i==3||i==5||i==6){
//				ICellEditor bigDecimalEditor = CommerceHelper.getKDFormattedTextDecimalEditor();
//				column.setEditor(bigDecimalEditor);
//				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//			}
//			
//			if(i>=3&&i<=10){
//				column.getStyleAttributes().setLocked(true);
//				column.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//			}
//		}
//		kdtPanel.setPreferredSize(new Dimension(800,300));
//		table.setBounds(new Rectangle(0, 0, kdtPanel.getWidth(), kdtPanel.getHeight()-10));
//		kdtPanel.add(table);
//		table.addKDTEditListener(new KDTEditAdapter() {
//			public void editStopped(KDTEditEvent e) {
//				table_editStopped(e);
//			}
//		});
//	}
//	
////	private void setTableColumnStyle(){
////		for(int i = 3;i<table.getColumnCount();i++){
////			IColumn column = table.getColumn(i);
////			if(i%4==0){
////				//套数 整数
////				ICellEditor integerEditor = getCellIntegerNumberEdit();
////				column.setEditor(integerEditor);
////				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
////			}
////			else{
////				//面积,均价,金额 
////				ICellEditor bigDecimalEditor = CommerceHelper.getKDFormattedTextDecimalEditor();
////				column.setEditor(bigDecimalEditor);
////				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
////			}
////		}
////	}
//	
//	private void updateTable(int columnIndex,int rowIndex){
//		if(columnIndex == 15 && rowIndex == 3){
//			System.out.println("15");
//		}
//		if(columnIndex%gapColumn==3){//面积
//			sumTotal(0,rowIndex,true);//把年度面积累加到货值分解合计的面积中
//			if(!PlanIndexTypeEnum.parking.equals((PlanIndexTypeEnum)table.getCell(rowIndex, 0).getValue())){
//				getTotalAmount(rowIndex,columnIndex,true);//面积*均价=金额
//			}
//			sumTotal(3,rowIndex,true);//把年度金额累加到货值分解合计的金额中,还有求货值分解合计的均价
//			//计算合计中的均价
//			getToatlPrice(columnIndex,true);
//		}else if(columnIndex%gapColumn==0){//套数
//			if(PlanIndexTypeEnum.parking.equals((PlanIndexTypeEnum)table.getCell(rowIndex, 0).getValue())){
//				getParkTotalAmount(rowIndex, columnIndex, true);
//			}
//			sumTotal(1,rowIndex,false);
//		}else if(columnIndex%gapColumn==1){
//			sumTotal(2,rowIndex,true); //均价
//			if(PlanIndexTypeEnum.parking.equals((PlanIndexTypeEnum)table.getCell(rowIndex, 0).getValue())){
//				getParkTotalAmount(rowIndex, columnIndex, false);
//			}
//			else{
//				getTotalAmount(rowIndex,columnIndex,false);
//			}
//			sumTotal(3,rowIndex,true);
//		}else if(columnIndex%gapColumn==2){
//			sumTotal(3,rowIndex,true); //金额
//		}
//	}
//	// 分录编辑后事件
//	protected void table_editStopped(KDTEditEvent e) {
//		int columnIndex = e.getColIndex();
//		int rowIndex = e.getRowIndex();
//		table = (KDTable) e.getSource();
//		if(cycle!=0&&e.getColIndex()>9){
//			updateTable(columnIndex,rowIndex);
//		}
//		Set columnName = new HashSet();
//		for(int i=7;i<table.getColumnCount();i++){
//			columnName.add(String.valueOf(i));
//		}
//		resetRowRules();//各产品构成合计，小计规则
//		MarketHelpForSec.getYearFootRow(table, columnName);//合计
//		table.repaint();
//	}
//	
//	public void resetRowRules(){
//		HashMap XJ_MAP = new HashMap();//存放小计行号
//		int num = 0;
//		for(int i=0;i<table.getRowCount()-1;i++){
//			IRow row_xj=table.getRow(i);
//			//获取小计行号
//			if(row_xj.getCell(0).getValue()==PlanIndexTypeEnum.house && row_xj.getCell(2).getValue() != null){
//				if(row_xj.getCell(2).getValue().equals("小计")){
//					row_xj.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//					XJ_MAP.put(String.valueOf(num),String.valueOf(i));
//					num++;
//				}
//			}
//		}
//		
//		//有小计行数据小计计算
//		for(int i=0;i<XJ_MAP.size();i++){
//			int rowNum = Integer.parseInt(XJ_MAP.get(String.valueOf(i)).toString());
//			int old_rowNum = 0;
//			if(i>0){
//				old_rowNum = Integer.parseInt(XJ_MAP.get(String.valueOf(i-1)).toString()) + 1;
//			}
//			IRow houseRow = table.getRow(rowNum);
//			for(int j = 11; j<table.getColumnCount();j++){
//				if(j%4==1){//除去产品构成、产品类型、面积段、销售均价外的均价
//					continue;
//				}
//				Number TotalArea = null;
//				for(int z=old_rowNum;z<rowNum;z++){
//					TotalArea = FDCHelper.add(TotalArea, table.getRow(z).getCell(j).getValue());
//				}
//				houseRow.getCell(j).setValue(TotalArea);
//				if(j%4==2){//小计的销售均价
//					Number subTotalArea = (Number)houseRow.getCell(j-3).getValue();
//					Number subTotalAmount = (Number)houseRow.getCell(j).getValue();
//					BigDecimal price = FDCHelper.divide(subTotalAmount,subTotalArea );
//					houseRow.getCell(j-1).setValue(price);
//				}
//			}
//		}
//		//合计计算
//		HashMap HJ_MAP = new HashMap();
//		num = 0;
//		for(int i=0;i<table.getRowCount();i++){
//			IRow row_hj=table.getRow(i);
//			//获取小计行号
//			if(row_hj.getCell(1).getValue() instanceof String){
//				row_hj.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//				HJ_MAP.put(String.valueOf(num),String.valueOf(i));
//				num++;
//			}
//		}
//		for(int i=0;i<HJ_MAP.size();i++){
//			int rowNum = Integer.parseInt(HJ_MAP.get(String.valueOf(i)).toString());
//			int old_rowNum = 1;
//			if(i>0){
//				old_rowNum = Integer.parseInt(HJ_MAP.get(String.valueOf(i-1)).toString()) + 1;
//			}
//			IRow houseRow = table.getRow(rowNum);
//			if(PlanIndexTypeEnum.house.equals((PlanIndexTypeEnum)houseRow.getCell(0).getValue())){
//				for(int j = 11; j<table.getColumnCount();j++){
//					if(j%4==1){//除去产品构成、产品类型、面积段、销售均价外的均价
//						continue;
//					}
//					Number TotalArea = null;
//					for(int z=0;z<XJ_MAP.size();z++){
//						TotalArea = FDCHelper.add(TotalArea, table.getRow(Integer.parseInt(XJ_MAP.get(String.valueOf(z)).toString())).getCell(j).getValue());
//					}
//					houseRow.getCell(j).setValue(TotalArea);
//					if(j%4==2){//小计的销售均价
//						Number subTotalArea = (Number)houseRow.getCell(j-3).getValue();
//						Number subTotalAmount = (Number)houseRow.getCell(j).getValue();
//						BigDecimal price = FDCHelper.divide(subTotalAmount,subTotalArea );
//						houseRow.getCell(j-1).setValue(price);
//					}
//				}
//			}
//			else{
//				for(int j = 11; j<table.getColumnCount();j++){
//					if(j%4==1){//除去产品构成、产品类型、面积段、销售均价外的均价
//						continue;
//					}
//					Number TotalArea = null;
//					for(int z=old_rowNum;z<rowNum;z++){
//						TotalArea = FDCHelper.add(TotalArea, table.getRow(z).getCell(j).getValue());
//					}
//					houseRow.getCell(j).setValue(TotalArea);
//					if(j%4==2){
//						Number subTotalArea = (Number)houseRow.getCell(j-3).getValue();
//						Number subTotalAmount = (Number)houseRow.getCell(j).getValue();
//						BigDecimal price = FDCHelper.divide(subTotalAmount,subTotalArea );
//						houseRow.getCell(j-1).setValue(price);
//					}
//				}
//			}
//		}
//	}
//	
//	//计算属于同种产品构成类型合计均价
//	public void getToatlPrice(int columnIndex,boolean flag){
//		for(int i = 0 ;i<table.getRowCount();i++){
//			IRow row = table.getRow(i);
//			if(row.getCell(1).getValue() instanceof String){
//				Number area =(Number)row.getCell(6).getValue();
//				Number amount =(Number)row.getCell(9).getValue();
//				if(area!=null&&amount!=null){
//					row.getCell(8).setValue(FDCHelper.divide(amount,area ));
//				}
//				if(flag){
//					Number areaYear = (Number)row.getCell(columnIndex).getValue();
//					Number amountYear = (Number)row.getCell(columnIndex+3).getValue();
//					if(areaYear!=null&&amountYear!=null){
//						row.getCell(columnIndex+2).setValue(FDCHelper.divide(amountYear,areaYear ));
//					}
//				}else{
//					Number areaYear = (Number)row.getCell(columnIndex-2).getValue();
//					Number amountYear =(Number)row.getCell(columnIndex+1).getValue();
//					if(areaYear!=null&&amountYear!=null){
//						row.getCell(columnIndex).setValue(FDCHelper.divide(amountYear,areaYear ));
//					}
//				}
//			}
//		}
//	}
//	//分解值累加到货值分解合计中 flag 标识数据类型,true为bigdecimal类型,false为int类型
//	public void sumTotal(int index,int rowIndex,boolean flag){
//		int columnIndex = table.getColumnCount();
//		if(flag){
//			BigDecimal beforeLastaccount = new BigDecimal("0.00");
//			//第一行记录的年度销售面积开始计算
//			for(int i=11+index; i<table.getColumnCount();){
//				BigDecimal value = new BigDecimal("0.00");
//				if(table.getCell(rowIndex, i).getValue()!=null){
//					//获取各年度销售面积,销售均价,销售金额
//					value =(BigDecimal)table.getCell(rowIndex, i).getValue();
//				}
//				if(i<table.getColumnCount()-4){
//					beforeLastaccount = beforeLastaccount.add(value);
//				}
//				i+=4;//下一年度
//			}
//			//待分解的最后一年数字自动算出(分解选择2年以上数据)
//			if(table.getColumnCount()>15){
//				BigDecimal totalValue = new BigDecimal("0.00");
//				//取最后一年的销售套数
//				if(table.getCell(rowIndex, 3+index).getValue()!=null){//有项目总货值信息
//					if(table.getCell(rowIndex, 3+index).getValue() instanceof Integer){
//						totalValue =new BigDecimal(((Integer)table.getCell(rowIndex, 3+index).getValue()).intValue());
//					}
//					if(table.getCell(rowIndex, 3+index).getValue() instanceof BigDecimal){
//						totalValue =(BigDecimal)table.getCell(rowIndex, 3+index).getValue();
//					}
//				}
//				if(table.getCell(rowIndex, columnIndex-4+index)!=null && index!=1 && index!=2){
//					table.getCell(rowIndex, columnIndex-4+index).setValue(totalValue.subtract(beforeLastaccount));
//				}
//			}
//		}else{
//			int beforeLastaccount = 0;
//			for(int i=11+index; i<table.getColumnCount();i+=4){
//				int value = 0;
//				if(table.getCell(rowIndex, i).getValue()!=null)
//					value =((Integer)table.getCell(rowIndex, i).getValue()).intValue();
//				if(i<table.getColumnCount()-4)
//					beforeLastaccount = beforeLastaccount+value;
//			}
//			//待分解的最后一年数字自动算出
//			if(table.getColumnCount()>15){
//				int totalValue = 0;
//				if(table.getCell(rowIndex, 3+index).getValue()!=null){
//					if(table.getCell(rowIndex, 3+index).getValue() instanceof Integer){
//						totalValue =((Integer)table.getCell(rowIndex, 3+index).getValue()).intValue();
//					}
//					if(table.getCell(rowIndex, 3+index).getValue() instanceof BigDecimal){
//						totalValue =((BigDecimal)table.getCell(rowIndex, 3+index).getValue()).intValue();
//					}
//				}
//				if(table.getCell(rowIndex, columnIndex-4+index)!=null && index !=2)
//					table.getCell(rowIndex, columnIndex-4+index).setValue(new Integer(totalValue-beforeLastaccount));
//			}
//		}
//		
//		//反写回货值分解合计中去
//		if(flag){
//			BigDecimal account = new BigDecimal("0.00");
//			for(int i=11+index; i<table.getColumnCount();){
//				BigDecimal value = new BigDecimal("0.00");
//				if(table.getCell(rowIndex, i).getValue()!=null){
//					value =(BigDecimal)table.getCell(rowIndex, i).getValue();
//				}
//				account=account.add(value);
//				i+=4;
//			}
//			table.getCell(rowIndex, 7+index).setValue(account);
//		}else{
//			int account = 0;
//			for(int i=11+index; i<table.getColumnCount();i+=4){
//				int value = 0;
//				if(table.getCell(rowIndex, i).getValue()!=null){
//					value =((Integer)table.getCell(rowIndex, i).getValue()).intValue();
//				}
//				account+=value;
//			}
//			table.getCell(rowIndex, 7+index).setValue(Integer.valueOf(account));
//		}
//		if(!PlanIndexTypeEnum.parking.equals((PlanIndexTypeEnum)table.getCell(rowIndex, 0).getValue())){//不是车位
//			setLastYearPrice(table.getRow(rowIndex),4);
//			setAddYearPrice(table.getRow(rowIndex),4);
//		}else{//是车位
//			setLastYearPrice(table.getRow(rowIndex),3);
//			setAddYearPrice(table.getRow(rowIndex),3);
//		}
//	}
//	/**
//	 * 分解货值累计的均价，自动算出
//	 * */
//	public void setAddYearPrice(IRow row,int devCount){
//		BigDecimal count = new BigDecimal("0.00");//面积或者套数，取决于devCount（就是是否是车位）
//		BigDecimal account = new BigDecimal("0.00");//金额
//		int columnCount = 11;//货值分解合计
//		if(row.getCell(columnCount-1).getValue() instanceof Integer){
//			account =new BigDecimal(((Integer)row.getCell(columnCount-1).getValue()).intValue());
//		}
//		else if(row.getCell(columnCount-1).getValue() instanceof BigDecimal){
//			account = ((BigDecimal)row.getCell(columnCount-1).getValue());
//		}
//		if(row.getCell(columnCount-devCount).getValue() instanceof Integer){
//			count =new BigDecimal(((Integer)row.getCell(columnCount-devCount).getValue()).intValue());
//		}
//		else if(row.getCell(columnCount-devCount).getValue() instanceof BigDecimal){
//			count = ((BigDecimal)row.getCell(columnCount-devCount).getValue());
//		}
//		if(count.intValue()!=0){
//			row.getCell(columnCount-2).setValue(account.divide(count,2).setScale(2,BigDecimal.ROUND_HALF_UP));
//		}
//	}
//	/**
//	 * 待分解的最后一年的均价，自动算出
//	 * */
//	public void setLastYearPrice(IRow row,int devCount){
//		BigDecimal count = new BigDecimal("0.00");//面积或者套数，取决于devCount（就是是否是车位）
//		BigDecimal account = new BigDecimal("0.00");//金额
//		int columnCount = table.getColumnCount();
//		
//		if(row.getCell(columnCount-1).getValue() instanceof Integer){//最后一年份销售金额(columnCount-1)
//			account = new BigDecimal(((Integer)row.getCell(columnCount-1).getValue()).intValue());
//		}
//		else if(row.getCell(columnCount-1).getValue() instanceof BigDecimal){
//			account = ((BigDecimal)row.getCell(columnCount-1).getValue());
//		}
//		//最后一年的devCount为4表示最后一年销售面积,devCount为3表示最后一年的销售套数
//		if(row.getCell(columnCount-devCount).getValue() instanceof Integer){
//			count =new BigDecimal(((Integer)row.getCell(columnCount-devCount).getValue()).intValue());
//		}
//		else if(row.getCell(columnCount-devCount).getValue() instanceof BigDecimal){
//			count = ((BigDecimal)row.getCell(columnCount-devCount).getValue());
//		}
//		if(count.intValue()!=0){
//			row.getCell(columnCount-2).setValue(account.divide(count,2).setScale(2,BigDecimal.ROUND_HALF_UP));
//		}
//	}
//	//面积*均价=金额   flag标识  面积触发为true，价格触发false
//	public void  getTotalAmount(int rowIndex,int columnIndex,boolean flag){
//		if(flag){
//			BigDecimal area = (BigDecimal)table.getCell(rowIndex, columnIndex).getValue();
//			BigDecimal price = (BigDecimal)table.getCell(rowIndex, columnIndex+2).getValue();
//			if(area!=null&&price!=null){
//				table.getCell(rowIndex,columnIndex+3).setValue(area.multiply(price).setScale(2,BigDecimal.ROUND_HALF_UP));
//			}
//		}else{
//			BigDecimal area = (BigDecimal)table.getCell(rowIndex, columnIndex-2).getValue();
//			BigDecimal price = (BigDecimal)table.getCell(rowIndex, columnIndex).getValue();
//			if(area!=null&&price!=null){
//				table.getCell(rowIndex,columnIndex+1).setValue(area.multiply(price).setScale(2,BigDecimal.ROUND_HALF_UP));
//			}
//		}
//		
//	}
//	
//	//套数*均价=金额   flag标识  套数触发为true，价格触发false
//	public void  getParkTotalAmount(int rowIndex,int columnIndex,boolean flag){
//		if(flag){
//			Integer ploidy = (Integer)table.getCell(rowIndex, columnIndex).getValue();
//			BigDecimal price = (BigDecimal)table.getCell(rowIndex, columnIndex+1).getValue();
//			if(ploidy!=null&&price!=null){
//				BigDecimal bPloidy = BigDecimal.valueOf(ploidy.intValue());
//				//设置车位销售金额
//				table.getCell(rowIndex,columnIndex+2).setValue(bPloidy.multiply(price).setScale(2,BigDecimal.ROUND_HALF_UP));
//			}
//		}else{
//			Number ploidy = (Number)table.getCell(rowIndex, columnIndex-1).getValue();
//			BigDecimal price = (BigDecimal)table.getCell(rowIndex, columnIndex).getValue();
//			if(ploidy!=null&&price!=null){
//				BigDecimal bPloidy = BigDecimal.valueOf(ploidy.intValue());
//				//设置非车位销售金额
//				table.getCell(rowIndex,columnIndex+1).setValue(bPloidy.multiply(price).setScale(2,BigDecimal.ROUND_HALF_UP));
//			}
//		}
//		
//	}
//	
//	/**
//	 * 得到一个0－－1.0E17的Integer CellEditor
//	 * @author chetao 		Date 2011-08-18
//	 * @return
//	 */
//	public  KDTDefaultCellEditor getCellIntegerNumberEdit(){
//		KDFormattedTextField kdc = new KDFormattedTextField();
//        kdc.setDataType(KDFormattedTextField.INTEGER_TYPE);
//        kdc.setPrecision(0);
//        kdc.setMinimumValue(FDCHelper.ZERO);
//        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
//        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
//        kdc.setSupportedEmpty(true);
//        kdc.setVisible(true);
//        kdc.setEnabled(true);
//        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
//        return editor;
//	}
//	
////	protected void prmtMeasureStage_dataChanged(DataChangeEvent e)
////			throws Exception {
////		super.prmtMeasureStage_dataChanged(e);
////		if(this.prmtMeasureStage.getValue()!=null){
////			if(!e.getNewValue().equals(e.getOldValue())){
////				MeasurePhaseInfo measurePhaseInfo = (MeasurePhaseInfo)prmtMeasureStage.getValue();
////				comboMeasureType.setSelectedItem(measurePhaseInfo.getPhaseType());
////			}
////		}
////	}
//
//	protected void attachListeners() {
//		addDataChangeListener(this.txtYear);
//		addDataChangeListener(this.txtCycle);
//		addDataChangeListener(this.prmtTotalAmount);
//	}
//	protected void detachListeners() {
//		removeDataChangeListener(this.txtYear);
//		removeDataChangeListener(this.txtCycle);
//		removeDataChangeListener(this.prmtTotalAmount);
//	}
//	protected void removeDataChangeListener(JComponent com) {
//		EventListener[] listeners = null;	
//  	
//		if(com instanceof KDPromptBox){
//			listeners = com.getListeners(DataChangeListener.class);	
//    		for(int i=0;i<listeners.length;i++){
//    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
//    		}
//    	}else if(com instanceof KDFormattedTextField){
//    		listeners = com.getListeners(DataChangeListener.class);	
//    		for(int i=0;i<listeners.length;i++){
//    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
//    		}
//    	}else if(com instanceof KDDatePicker){
//    		listeners = com.getListeners(DataChangeListener.class);	
//    		for(int i=0;i<listeners.length;i++){
//    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
//    		}
//    	}else if(com instanceof KDComboBox){
//    		listeners = com.getListeners(ItemListener.class);	
//    		for(int i=0;i<listeners.length;i++){
//    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
//    		}
//    	}else if(com instanceof KDSpinner){
//    		listeners = com.getListeners(ChangeListener.class);	
//    		for(int i=0;i<listeners.length;i++){
//    			((KDSpinner)com).removeChangeListener((ChangeListener)listeners[i]);
//    		}
//    	} 
//		if(listeners!=null && listeners.length>0){
//			listenersMap.put(com,listeners );
//		}
//    }
//	 protected void addDataChangeListener(JComponent com) {
//	    	
//    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
//    	
//    	if(listeners!=null && listeners.length>0){
//	    	if(com instanceof KDPromptBox){
//	    		for(int i=0;i<listeners.length;i++){
//	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
//	    		}
//	    	}else if(com instanceof KDFormattedTextField){
//	    		for(int i=0;i<listeners.length;i++){
//	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
//	    		}
//	    	}else if(com instanceof KDDatePicker){
//	    		for(int i=0;i<listeners.length;i++){
//	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
//	    		}
//	    	}else if(com instanceof KDComboBox){
//	    		for(int i=0;i<listeners.length;i++){
//	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
//	    		}
//	    	}else if(com instanceof KDSpinner){
//	    		for(int i=0;i<listeners.length;i++){
//	    			((KDSpinner)com).addChangeListener((ChangeListener)listeners[i]);
//	    		}
//	    	}
//    	}
//    }
//	protected KDTable getDetailTable() {
//		return null;
//	}
//
//	protected KDTextField getNumberCtrl() {
//		return this.txtNumber;
//	}
//	protected void verifyInputForSave() throws Exception {
//		if(getNumberCtrl().isEnabled()) {
//			VerifyInputUtil.verifyNull(this, txtNumber,"单据编号" );
//		}
//		VerifyInputUtil.verifyNull(this, versionType,"版本类型" );
//		SellProjectInfo sellProjectInfo = (SellProjectInfo)prmtSellProject.getValue();
//		int last = txtVersion.getText().lastIndexOf(".");
//		String version = txtVersion.getText().substring(0,last);
//		if(VersionTypeEnum.check.equals(versionType.getSelectedItem())){
//			if(MonthPlanEditUI.checkVersionType(editData.getId(),sellProjectInfo.getId().toString(),version,new ValueBreakInfo())){
//				MsgBox.showWarning(this,"当前阶段的考核版已经存在，不能再新增考核版！");
//				SysUtil.abort();
//			}
//		}
//	}
//	protected void verifyInputForSubmint() throws Exception {
//		verifyInputForSave();
//	}
//	
//    public SelectorItemCollection getSelectors()
//    {
//        SelectorItemCollection sic = new SelectorItemCollection();
//        sic.add(new SelectorItemInfo("creator.*"));
//        sic.add(new SelectorItemInfo("createTime"));
//        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
//        sic.add(new SelectorItemInfo("lastUpdateTime"));
//        sic.add(new SelectorItemInfo("auditor.*"));
//        sic.add(new SelectorItemInfo("orgUnit.*"));
//        sic.add(new SelectorItemInfo("auditTime"));
//        sic.add(new SelectorItemInfo("measureType"));
//        sic.add(new SelectorItemInfo("version"));
//        sic.add(new SelectorItemInfo("versionName"));
//        sic.add(new SelectorItemInfo("sellProject.*"));
//        sic.add(new SelectorItemInfo("measureStage.*"));
//        sic.add(new SelectorItemInfo("totalAmount.*"));
//        sic.add(new SelectorItemInfo("remark"));
//        sic.add(new SelectorItemInfo("year"));
//        sic.add(new SelectorItemInfo("cycle"));
//        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.productType.*"));
//        sic.add(new SelectorItemInfo("entrys.newAreaRange.*"));
//        sic.add(new SelectorItemInfo("state"));
//        sic.add(new SelectorItemInfo("versionType"));
//        sic.add(new SelectorItemInfo("number"));
//        sic.add("CU.*");
//        return sic;
//    }   
//    
//    public void activityAmount(IRow row ,String sellProjectID,String productTypeID,int year,int gapYear) throws BOSException, SQLException{
//    	FDCSQLBuilder sql = new FDCSQLBuilder();
//    	String startDate = year+"-01-01";
//    	String endDate = year+"-12-31";
////    	if(10+gapYear*gapColumn>table.getColumnCount())//取值只取到开始年度+周期
////    		return;
//    	StringBuffer sb = new StringBuffer();
//    	sb.append(" select project.fid as projectid,type.fname_l2 as typename, ");
//		sb.append(" sum(isnull(FActualArea,0)+isnull(FAreaCompensate,0)) as area,count(*) as count,");
//		sb.append(" round(sum(signman.FStrdTotalAmount)/sum(isnull(FActualArea,0)+isnull(FAreaCompensate,0)),4) as unitprice,");
//		sb.append(" sum(signman.FStrdTotalAmount) as amount");
//		sb.append(" from T_SHE_Room room");
//		sb.append(" left join T_FDC_ProductType type ");
//		sb.append(" on type.fid=room.fproducttypeid ");
//		sb.append(" left join T_SHE_Building build ");
//		sb.append(" on build.fid=room.FBuildingID ");
//		sb.append(" left join T_SHE_SellProject project");
//		sb.append(" on build.FSellProjectID=project.fid");
//		sb.append(" left join T_SHE_SignManage signman ");
//		sb.append(" on signman.froomid=room.fid and signman.fbizState='SignAudit'");
//		sb.append(" where room.fsellstate='Sign' ");
//		sb.append(" and project.fid='"+sellProjectID+"'");
//		sb.append(" and type.fid='"+productTypeID+"'");  //TO_CHAR("roomSign".FSignDate,'YYYY-MM-DD')>='@quitFromDate'
//		sb.append(" and TO_CHAR(signman.fbookeddate,'YYYY-MM-DD')>='"+startDate+"'");
//		sb.append(" and TO_CHAR(signman.fbookeddate,'YYYY-MM-DD')<='"+endDate+"'");
//		sb.append(" group by project.fid,type.fname_l2");
//		sql.appendSql(sb.toString());
//		sql.execute();
//		IRowSet rowSet = sql.executeQuery();
//		if(rowSet.next()){
//			BigDecimal area = rowSet.getBigDecimal("area");
//			int count = rowSet.getInt("count");
//			BigDecimal unitprice = rowSet.getBigDecimal("unitprice");
//			BigDecimal amount = rowSet.getBigDecimal("amount");
//			row.getCell(7+gapYear*gapColumn).setValue(area);
//			row.getCell(8+gapYear*gapColumn).setValue(Integer.valueOf(count));
//			row.getCell(9+gapYear*gapColumn).setValue(unitprice);
//			row.getCell(10+gapYear*gapColumn).setValue(amount);
//
//		}else{
//			row.getCell(7+gapYear*gapColumn).setValue(new BigDecimal(0.00));
//			row.getCell(8+gapYear*gapColumn).setValue(Integer.valueOf(0));
//			row.getCell(9+gapYear*gapColumn).setValue(new BigDecimal(0.00));
//			row.getCell(10+gapYear*gapColumn).setValue(new BigDecimal(0.00));
//		}
////		row.getCell(6+gapYear*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
////		row.getCell(7+gapYear*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
////		row.getCell(8+gapYear*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
////		row.getCell(9+gapYear*gapColumn).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
////		table.getColumn(6+gapYear*gapColumn).getStyleAttributes().setLocked(true);
////		table.getColumn(7+gapYear*gapColumn).getStyleAttributes().setLocked(true);
////		table.getColumn(8+gapYear*gapColumn).getStyleAttributes().setLocked(true);
////		table.getColumn(9+gapYear*gapColumn).getStyleAttributes().setLocked(true);//销售均价
//    }
//    
////	protected void verifyInput(ActionEvent e) throws Exception {
////		VerifyInputUtil.verifyNull(this, prmtTotalAmount,"总货值");
////	}
//	private KDTable table=null;
//	private IRow firstHead = null;
//	private IRow secondHead = null;
//	private int cycle = 0;
//	private int year = 1999;
//	final int gapColumn = 4;
//	String sellProjectID = null;
//	Date now = null;
//	MeasurePlanTargetInfo measurePlanTargetInfo = null;
//	
////  protected void prmtTotalAmount_dataChanged(DataChangeEvent e)
////	throws Exception {
////if(prmtTotalAmount.getValue()!=null){
////	if(!e.getNewValue().equals(e.getOldValue())){
////		measurePlanTargetInfo = (MeasurePlanTargetInfo)prmtTotalAmount.getValue();
////		String MeasurePhasesId =  measurePlanTargetInfo.getMeasurePhases().getId().toString();
////		MeasurePhaseInfo measurePhaseInfo = MeasurePhaseFactory.getRemoteInstance().getMeasurePhaseInfo(new ObjectUuidPK(MeasurePhasesId));
////		prmtMeasureStage.setValue(measurePhaseInfo);
////		comboMeasureType.setSelectedItem(measurePhaseInfo.getPhaseType());
////		table.removeRows();
////		table.checkParsed();
////		//获取版本
////		ValueBreakCollection valueBreakColl = getValueBreakColl(measurePlanTargetInfo.getId().toString(),measurePhaseInfo.getId().toString());
////		if(valueBreakColl.size()==0){
////			txtVersion.setText(measurePlanTargetInfo.getVersions()+".0");
////			MeasurePlanTargetEntryCollection measurePlanTargetEntryColl =  measurePlanTargetInfo.getEntry();
////			CRMHelper.sortCollection(measurePlanTargetEntryColl, "fseq", true);
////			for(int i = 0;i<measurePlanTargetEntryColl.size();i++){
////				MeasurePlanTargetEntryInfo measurePlanTargetEntryInfo = measurePlanTargetEntryColl.get(i);
////				IRow row = table.addRow();
////				row.getCell(0).setValue(measurePlanTargetEntryInfo.getProductConstitute());//产品构成
////				if(measurePlanTargetEntryInfo.getProductType()!=null){
////					String productTypeID = measurePlanTargetEntryInfo.getProductType().getId().toString();
////					ProductTypeInfo productTypeInfo =  ProductTypeFactory.getRemoteInstance().getProductTypeInfo(new ObjectUuidPK(productTypeID));
////    				row.getCell(1).setValue(productTypeInfo);//产品类型
////				}
////				row.getCell(2).setValue(measurePlanTargetEntryInfo.getAcreage());//面积
////				row.getCell(3).setValue(Integer.valueOf(measurePlanTargetEntryInfo.getQuantity()));//套数
////				row.getCell(4).setValue(measurePlanTargetEntryInfo.getPrice());//均价
////				row.getCell(5).setValue(measurePlanTargetEntryInfo.getSumAmount());//金额
////				
////			}
////			addSumRow();
////		}else{
////			CRMHelper.sortCollection(valueBreakColl, "createTime", false);
////			String valueBreakID =  valueBreakColl.get(0).getId().toString();
////			ValueBreakInfo valueBreakInfo = ValueBreakFactory.getRemoteInstance().getValueBreakInfo(new ObjectUuidPK(valueBreakID));
////			editData = valueBreakInfo;
//////			for(int j = 0;j<valueBreakInfo.getEntrys().size();j++){
//////				editData.getEntrys().add(valueBreakInfo.getEntrys().get(j)) ;
//////			}
////			String oldVersoin = valueBreakColl.get(0).getVersion();
////			String last = oldVersoin.substring(oldVersoin.length()-1);
////			String verIndex = String.valueOf(Integer.parseInt(last)+1);
////			txtVersion.setText(measurePlanTargetInfo.getVersions()+"."+verIndex);
////			txtYear.setValue(Integer.valueOf(editData.getYear()));
////			txtCycle.setValue(Integer.valueOf(editData.getCycle()));
//////			loadEntry();
////		}
////	}
////}
////}
//}