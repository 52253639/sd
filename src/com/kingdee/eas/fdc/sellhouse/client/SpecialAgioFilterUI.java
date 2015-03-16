/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.sellhouse.AgioBillFactory;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class SpecialAgioFilterUI extends AbstractSpecialAgioFilterUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1250820143943437660L;
	private static final Logger logger = CoreUIObject.getLogger(SpecialAgioFilterUI.class);
	public static final String PUR_FROM = "purBeginDate";//认购开始日期
	public static final String PUR_TO = "purEndDate";//认购结束日期
	public static final String SELLPROJECT="sellpoject";//项目
	public static final String Room_SellState = "roomSellState"; //销售状态
	public static final String Building="building";//楼栋
	public static final String PayStyle="payStyle";//付款方案
	public static final String Agio="agio";//折扣方案
	public static final String Room_Number="roomnumber";//折扣方案
	
	public static final String ISSpecialAgio="specialAgio";//特殊折扣是否统计
	
	
	 
    /**
     * output class constructor
     */
    public SpecialAgioFilterUI() throws Exception
    {
        super();
    }
    
    
	protected void prmtPayStyle_willCommit(CommitEvent e) throws Exception {
		if (this.prmtsellproject.getData()==null){
			e.setCanceled(true);
			return;
		}
		else{
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("project.id", ((SellProjectInfo)prmtsellproject.getData()).getId(), CompareType.EQUALS));
			evi.setFilter(filterInfo);
			prmtPayStyle.setEntityViewInfo(evi);
			//prmtpatroldev.getQueryAgent().getRuntimeEntityView().setFilter(filterInfo);
			prmtPayStyle.getQueryAgent().resetRuntimeEntityView();
		}
		super.prmtPayStyle_willCommit(e);
	}


	protected void prmtPayStyle_willShow(SelectorEvent e) throws Exception {
		if (this.prmtsellproject.getData()==null){
			e.setCanceled(true);
			return;
		}
		else{
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("project.id", ((SellProjectInfo)prmtsellproject.getData()).getId(), CompareType.EQUALS));
			evi.setFilter(filterInfo);
			prmtPayStyle.setEntityViewInfo(evi);
			prmtPayStyle.getQueryAgent().resetRuntimeEntityView();
		}
		super.prmtPayStyle_willShow(e);
	}


	protected void prmtAgio_willCommit(CommitEvent e) throws Exception {
		if (this.prmtsellproject.getData()==null){
			e.setCanceled(true);
			return;
		}
		else{
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("project.id", ((SellProjectInfo)prmtsellproject.getData()).getId(), CompareType.EQUALS));
			filterInfo.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
			evi.setFilter(filterInfo);
			prmtAgio.setEntityViewInfo(evi);
			prmtAgio.getQueryAgent().resetRuntimeEntityView();
		}
		super.prmtAgio_willCommit(e);
	}


	protected void prmtAgio_willShow(SelectorEvent e) throws Exception {
		if (this.prmtsellproject.getData()==null){
			e.setCanceled(true);
			return;
		}
		else{
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("project.id", ((SellProjectInfo)prmtsellproject.getData()).getId(), CompareType.EQUALS));
			filterInfo.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
			evi.setFilter(filterInfo);
			prmtAgio.setEntityViewInfo(evi);
			prmtAgio.getQueryAgent().resetRuntimeEntityView();
		}
		super.prmtAgio_willShow(e);
	}


	protected void prmtBuilding_willCommit(CommitEvent e) throws Exception {
		if (this.prmtsellproject.getData()==null){
			e.setCanceled(true);
			return;
		}
		else{
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("sellproject.id", ((SellProjectInfo)prmtsellproject.getData()).getId(), CompareType.EQUALS));
			evi.setFilter(filterInfo);
			prmtBuilding.setEntityViewInfo(evi);
			prmtBuilding.getQueryAgent().resetRuntimeEntityView();
		}
		super.prmtBuilding_willCommit(e);
	}


	protected void prmtBuilding_willShow(SelectorEvent e) throws Exception {
		if (this.prmtsellproject.getData()==null){
			e.setCanceled(true);
			return;
		}
		else{
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("sellproject.id", ((SellProjectInfo)prmtsellproject.getData()).getId(), CompareType.EQUALS));
			evi.setFilter(filterInfo);
			prmtBuilding.setEntityViewInfo(evi);
			prmtBuilding.getQueryAgent().resetRuntimeEntityView();
		}
		super.prmtBuilding_willShow(e);
	}


	public void clear() {
		this.kDDatePicker1.setValue(new Date());
		this.kDDatePicker2.setValue(new Date());
		this.prmtAgio.setValue(null);
		this.prmtBuilding.setValue(null);
		this.prmtPayStyle.setValue(null);
		this.kDTextField1.setText("");
//		Calendar c = Calendar.getInstance();
//		c.setTime(new java.util.Date(System.currentTimeMillis()));
//		c.add(Calendar.MONTH, 0);
//		c.set(Calendar.DATE, 1);
//
//		c.getTime();
//		this.kDDatePicker1.setValue(c.getTime());
//		c.add(Calendar.MONTH, 1);
//		c.add(Calendar.DATE, -1);
//
//		this.kDDatePicker2.setValue(c.getTime());
		
		
		try {
			this.prmtsellproject.setEntityViewInfo(CommerceHelper.getPermitProjectView());
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
	}


	public void onLoad() throws Exception {
		// TODO 待补充
		super.onLoad();
		this.prmtsellproject.setForeground(new java.awt.Color(0,0,0));	
		//过滤权限项目。
	}


	public boolean verify() {
		FDCCustomerParams param = new FDCCustomerParams(getCustomerParams());

		Date purBeginDate = param.getDate(PUR_FROM);
		Date purEndDate = param.getDate(PUR_TO);		
		if(purBeginDate!=null && purEndDate!=null) {
			if(purBeginDate.after(purEndDate)) {
				MsgBox.showWarning("统计开始日期大于结束日期！");
				return false;
			}
		}
		if(this.prmtsellproject.getData()==null) {
			MsgBox.showWarning("项目不能为空！");
			return false;
		}
		return true;
	}
	public FilterInfo getFilterInfo() {
		//TODO 待补充,需要的过滤条件
		FilterInfo filter = new FilterInfo();
		FDCCustomerParams param = new FDCCustomerParams(getCustomerParams());

		Date purBeginDate = param.getDate(PUR_FROM);
		Date purEndDate = param.getDate(PUR_TO);
		String agioInfoId = param.getString(Agio);
		String sellInfoId = param.getString(SELLPROJECT);
		String buildInfoId = param.getString(Building);
		String paystyinfoId = param.getString(PayStyle);
		String roomnumber =param.getString(Room_Number);
		//只做传递参数用
		if(purBeginDate!=null)
			filter.getFilterItems().add(new FilterItemInfo(PUR_FROM,purBeginDate==null?null:new Timestamp(purBeginDate.getTime())));
		if(purEndDate!=null) {
			purEndDate = FDCDateHelper.getNextDay(purEndDate);
			filter.getFilterItems().add(new FilterItemInfo(PUR_TO,purEndDate==null?null:new Timestamp(purEndDate.getTime())));
		}
		if (sellInfoId!=null){
			filter.getFilterItems().add(new FilterItemInfo(SELLPROJECT,sellInfoId));
		}
		filter.getFilterItems().add(new FilterItemInfo(Agio,agioInfoId));
		filter.getFilterItems().add(new FilterItemInfo(Building,buildInfoId));
		filter.getFilterItems().add(new FilterItemInfo(PayStyle,paystyinfoId));
		filter.getFilterItems().add(new FilterItemInfo(Room_Number,roomnumber));
		return filter;
	}
	
	public CustomerParams getCustomerParams(){
		FDCCustomerParams param = new FDCCustomerParams();
		//SELLPROJECT  Room_SellState Building PayStyle Agio Room_Number
		param.add(PUR_FROM, (Date)this.kDDatePicker1.getValue());
		param.add(PUR_TO, (Date)this.kDDatePicker2.getValue());
		
		AgioBillInfo agioInfo = (AgioBillInfo)this.prmtAgio.getValue(); 
		param.add(Agio, agioInfo==null?null:agioInfo.getId().toString());
		
		SellProjectInfo sellInfo = (SellProjectInfo)this.prmtsellproject.getValue(); 
		param.add(SELLPROJECT, sellInfo==null?null:sellInfo.getId().toString());
		
		BuildingInfo buildInfo = (BuildingInfo)this.prmtBuilding.getValue(); 
		param.add(Building, buildInfo==null?null:buildInfo.getId().toString());
		
		SHEPayTypeInfo paystyinfo = (SHEPayTypeInfo)this.prmtPayStyle.getValue(); 
		param.add(PayStyle, paystyinfo==null?null:paystyinfo.getId().toString());
		
		String roomnumber =this.kDTextField1.getText();
		if (roomnumber.equals("")){
			roomnumber=null;
		}
		param.add(Room_Number, roomnumber);
		//TODO 销售状态取值，参数传递。
		//this.cmbSellState.getSelectedItem();
		RoomSellStateEnum sellState =(RoomSellStateEnum)this.cmbSellState.getSelectedItem();
		String RoomSellState =sellState==null?null:sellState.getValue().toString();
		param.add(Room_SellState,RoomSellState);
		
		//特殊折扣是否统计ISSpecialAgio
		param.add(ISSpecialAgio, this.kdcSpecialAgio.isSelected());
		
		return param.getCustomerParams();
	}
	
	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);

		this.kDDatePicker1.setValue(para.getDate(PUR_FROM));
		this.kDDatePicker2.setValue(para.getDate(PUR_TO));
		
		this.kdcSpecialAgio.setSelected(para.getBoolean(ISSpecialAgio));
		
		String agioInfoId = para.getString(Agio);
		if(agioInfoId!=null) {
			try {
				AgioBillInfo agioInfo = AgioBillFactory.getRemoteInstance().getAgioBillInfo(new ObjectUuidPK(BOSUuid.read(agioInfoId)));
				if(agioInfo!=null) this.prmtAgio.setValue(agioInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (UuidException e) {
				e.printStackTrace();
			}			
		}	
		String sellInfoId = para.getString(SELLPROJECT);
		if(sellInfoId!=null) {
			try {
				SellProjectInfo sellInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(sellInfoId)));
				if(sellInfo!=null) this.prmtsellproject.setValue(sellInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (UuidException e) {
				e.printStackTrace();
			}			
		}
		
		String buildInfoId = para.getString(Building);
		if(buildInfoId!=null) {
			try {
				BuildingInfo buildInfo = BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(BOSUuid.read(buildInfoId)));
				if(buildInfo!=null) this.prmtBuilding.setValue(buildInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (UuidException e) {
				e.printStackTrace();
			}			
		}
		String paystyinfoId = para.getString(PayStyle);
		if(paystyinfoId!=null) {
			try {
				SHEPayTypeInfo paystyinfo = SHEPayTypeFactory.getRemoteInstance().getSHEPayTypeInfo(new ObjectUuidPK(BOSUuid.read(paystyinfoId)));
				if(paystyinfo!=null) this.prmtPayStyle.setValue(paystyinfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (UuidException e) {
				e.printStackTrace();
			}			
		}
		String roomnumber =para.getString(Room_Number);
		this.kDTextField1.setText(roomnumber);
		//String sellState =para.getString(Room_SellState);
		//RoomSellStateEnum SellStateEnum =RoomSellStateEnum.getEnum(sellState);
		//this.cmbSellState.setSelectedItem(SellStateEnum);
		super.setCustomerParams(para.getCustomerParams());
	}


	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

}