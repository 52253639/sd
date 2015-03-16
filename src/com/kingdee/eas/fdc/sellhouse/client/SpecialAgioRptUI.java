/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;
import com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEComHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class SpecialAgioRptUI extends AbstractSpecialAgioRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(SpecialAgioRptUI.class);
    boolean executeFlag = false;
    FDCCustomerParams para =null;
    SpecialAgioFilterUI spFilterUI =null;
    
    /**
     * output class constructor
     */
    public SpecialAgioRptUI() throws Exception
    {
        super();
    }
    public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
    public void onLoad() throws Exception {
		super.onLoad();		
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionView.setVisible(false);
		this.menuEdit.setVisible(false);
		spFilterUI =this.getFilterUI();
		para = new FDCCustomerParams(spFilterUI.getCustomerParams());
		this.execmyQuery();
		SimpleKDTSortManager.setTableSortable(tblMain);
		
	}

    protected void refresh(ActionEvent e) throws Exception {
    	spFilterUI =this.getFilterUI();
		para = new FDCCustomerParams(spFilterUI.getCustomerParams());
    	this.execmyQuery();
	}
    
    protected boolean initDefaultFilter() {
		return true;
	}
    private CommonQueryDialog commonQueryDialog = null;
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(500);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}
	
	private SpecialAgioFilterUI filterUI = null;
	private SpecialAgioFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new SpecialAgioFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	public void execmyQuery() {
		if(!executeFlag) {
			executeFlag = true;
			return;
		}
		this.tblMain.removeRows(false);
		try {
			
			PurchaseCollection purchasecoll =GetPurChase();
			//填充数据
			 GetAgio(purchasecoll);
			
			this.tblMain.getGroupManager().reInitialize();
			this.tblMain.getGroupManager().setGroup(true);
			this.tblMain.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
			for(int i=1,n=this.tblMain.getColumnCount()-4;i<=n;i++){
				this.tblMain.getColumn(i).setGroup(true);	
			}
			this.tblMain.getColumn("specialAgio").setGroup(true);
			this.tblMain.getColumn("specialAgioAmount").setGroup(true);
			
			this.tblMain.getGroupManager().group();	
			
			for(int i=1,n=this.tblMain.getColumnCount()-4;i<=n;i++){
				this.tblMain.getColumn(i).setMergeable(true);	
			}
			this.tblMain.getColumn("specialAgioAmount").setMergeable(true);
			this.tblMain.getColumn("specialAgio").setMergeable(true);	
			//特殊折扣是否统计 不选中影藏特殊折扣
			if(!para.getBoolean(spFilterUI.ISSpecialAgio)){
				this.tblMain.getColumn("specialAgio").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("specialAgioAmount").getStyleAttributes().setHided(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
    private void GetAgio(PurchaseCollection purchasecoll)  throws Exception{
		
		for (int i = 0; i < purchasecoll.size(); i++) {
			PurchaseInfo purinfo=purchasecoll.get(i);
			
			PurchaseAgioEntryCollection purchaseagioentycoll = purinfo.getAgioEntrys();
			
			if(para.getString(spFilterUI.Agio)!=null && purchaseagioentycoll.size()<=0){
				continue;
			}
			if(purchaseagioentycoll.size()>0){
				for (int j = 0; j < purchaseagioentycoll.size(); j++) {//PVX5deXDQoe5rirzHRLNisk+1c4= PVX5deXDQoe5rirzHRLNisk+1c4=
					PurchaseAgioEntryInfo puraigoentryinfo=	purchaseagioentycoll.get(j);
					if(puraigoentryinfo.getAgio()!=null){
						if(para.getString(spFilterUI.Agio)!=null && !para.getString(spFilterUI.Agio).equals(puraigoentryinfo.getAgio().getId().toString()) ){
							continue;
						}
						
						IRow row = this.tblMain.addRow();
						
						fillDate(purinfo, row);
						
						if(puraigoentryinfo.getAgio()!=null){//折扣方案
							row.getCell("agio").setValue(puraigoentryinfo.getAgio().getName());
							
							if(puraigoentryinfo.getAgio().getAmount()!=null){
								row.getCell("agioAmount").setValue(puraigoentryinfo.getAgio().getAmount());//折扣值
							}
							if(puraigoentryinfo.getAgio().getPro()!=null){
								row.getCell("agioAmount").setValue(puraigoentryinfo.getAgio().getPro().setScale(2, BigDecimal.ROUND_HALF_UP)+"%");//折扣值
							}
						}
						
						if(para.getBoolean(spFilterUI.ISSpecialAgio)){
							row.getCell("specialAgio").setValue(purinfo.getSpecialAgioType());//特殊折扣类型
							if(purinfo.getSpecialAgioType()!=null){
								if(purinfo.getSpecialAgioType().equals(SpecialAgioEnum.DaZhe) && purinfo.getSpecialAgio()!=null){
									row.getCell("specialAgioAmount").setValue(purinfo.getSpecialAgio().setScale(2, BigDecimal.ROUND_HALF_UP)+"%");//特殊折扣值
								}else{
									row.getCell("specialAgioAmount").setValue(purinfo.getSpecialAgio());
								}
							}
						}
					}
				}
				//如果没有选中特殊折扣是否统计
				if(!para.getBoolean(spFilterUI.ISSpecialAgio)){
					if(purinfo.getSpecialAgioType()!=null ||purinfo.getSpecialAgio()!=null ){
						IRow row = this.tblMain.addRow();
						
						fillDate( purinfo, row);
						
						row.getCell("agio").setValue(purinfo.getSpecialAgioType());//特殊折扣类型
						if(purinfo.getSpecialAgioType()!=null){
							if(purinfo.getSpecialAgioType().equals(SpecialAgioEnum.DaZhe) && purinfo.getSpecialAgio()!=null ){
								row.getCell("agioAmount").setValue(purinfo.getSpecialAgio().setScale(2, BigDecimal.ROUND_HALF_UP)+"%");//折扣值
							}else{
								row.getCell("agioAmount").setValue(purinfo.getSpecialAgio());
							}
						}
					}
				}
			}else{				
				IRow row = this.tblMain.addRow();
				
				fillDate(purinfo, row);
				if(para.getBoolean(spFilterUI.ISSpecialAgio)){
					row.getCell("specialAgio").setValue(purinfo.getSpecialAgioType());//特殊折扣类型
					
					if(purinfo.getSpecialAgioType()!=null){
						if(purinfo.getSpecialAgioType().equals(SpecialAgioEnum.DaZhe)&& purinfo.getSpecialAgio()!=null){
							row.getCell("specialAgioAmount").setValue(purinfo.getSpecialAgio().setScale(2,BigDecimal.ROUND_HALF_UP)+"%");//特殊折扣值
						}else{
							row.getCell("specialAgioAmount").setValue(purinfo.getSpecialAgio());
						}
					}
				}else{
                    row.getCell("agio").setValue(purinfo.getSpecialAgioType());//特殊折扣类型
					
					if(purinfo.getSpecialAgioType()!=null){
						if(purinfo.getSpecialAgioType().equals(SpecialAgioEnum.DaZhe)&& purinfo.getSpecialAgio()!=null){
							row.getCell("agioAmount").setValue(purinfo.getSpecialAgio().setScale(2,BigDecimal.ROUND_HALF_UP)+"%");//折扣值
						}else{
							row.getCell("agioAmount").setValue(purinfo.getSpecialAgio());
						}
					}
				}
				
				
			}
			
		}
		


		
		
//		row.getCell("sellproject").setValue(purinfo.getSellProject().getName());
//		row.getCell("building").setValue(purinfo.getRoom().getBuilding().getName());
//		if (purinfo.getRoom().getBuildUnit()!=null ){
//			row.getCell("unit").setValue(purinfo.getRoom().getBuildUnit().getName());
//		}
//		
//		row.getCell("room").setValue(purinfo.getRoom().getNumber());
//		row.getCell("customer").setValue(purinfo.getCustomerNames());
//		row.getCell("SellState").setValue(purinfo.getRoom().getSellState().toString()); 
//		if(purinfo.getPayType()!=null){
//			row.getCell("payStyle").setValue(purinfo.getPayType().getName());
//		}
//		row.getCell("buildingArea").setValue(purinfo.getRoom().getBuildingArea());
//		row.getCell("roomArea").setValue(purinfo.getRoom().getRoomArea());
//		row.getCell("buildPrice").setValue(purinfo.getRoom().getBuildPrice());
//		row.getCell("roomPrice").setValue(purinfo.getRoom().getRoomPrice());
//		if (purinfo.getRoom().isIsCalByRoomArea())
//		{
//			row.getCell("dealBuildPrice").setValue(purinfo.getRoom().getRoomPrice());
//		}
//		else row.getCell("dealBuildPrice").setValue(purinfo.getRoom().getBuildPrice());
//		row.getCell("standardTotalAmount").setValue(purinfo.getStandardTotalAmount());
//		row.getCell("agio").setValue(null);
//		row.getCell("agioAmount").setValue(null);
//		row.getCell("specialAgio").setValue(purinfo.getSpecialAgio());
//		//TODO 折扣金额需要计算，待补充
//		//row.getCell("specialAgioAmount").setValue(purinfo.getSellProject().getName());
//		BigDecimal specialAgioAmount =GetSepialAgio(purinfo);
//		row.getCell("specialAgioAmount").setValue(specialAgioAmount);
//		row.getCell("dealRoomPrice").setValue(purinfo.getDealPrice());
//		row.getCell("dealTotalAmount").setValue(purinfo.getDealAmount());
//		row.getCell("purchiaseDate").setValue(purinfo.getPurchaseDate());
//		row.getCell("signDate").setValue(purinfo.getToSignDate());
//		if (purinfo.getSalesman()!=null){
//		row.getCell("saleman").setValue(purinfo.getSalesman().getName());
//		}
//		if (purinfo.getAuditor()!=null){
//			row.getCell("dealperson").setValue(purinfo.getAuditor().getName());	
//		}
//		else row.getCell("dealperson").setValue(null);
		
	}
    //TODO 折扣金额需要计算，待补充
	private BigDecimal GetSepialAgio(PurchaseInfo purinfo) {
		boolean isLocaleSell = SellTypeEnum.LocaleSell.equals(purinfo.getSellType());
		BigDecimal SpecialAmount = new BigDecimal("0");
	
		if (purinfo!=null){
			// 如果不是以单价反算总价的计算方式，那么保持原来的逻辑不变
			BigDecimal specialAgio = purinfo.getSpecialAgio();
			if (specialAgio==null||specialAgio.equals(new BigDecimal("0"))||specialAgio.equals(new BigDecimal("100"))){
				SpecialAmount =new BigDecimal("0");
			}
			else
			{
			if (PriceAccountTypeEnum.StandSetPrice.equals(purinfo.getPriceAccountType())) {
				
				BigDecimal mainRoomTotalAmountAfterAgio = null;
				// 进行特殊折扣
				BigDecimal finalMainRoomTotalAmount = purinfo.getDealAmount();
				mainRoomTotalAmountAfterAgio =finalMainRoomTotalAmount.divide(specialAgio, 10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
				SpecialAmount=mainRoomTotalAmountAfterAgio.subtract(finalMainRoomTotalAmount);
			}
			else
			{
				// 房间单价和面积
				BigDecimal price = getValidPrice(purinfo.getRoom());
				BigDecimal area = getValidArea(purinfo.getRoom(),isLocaleSell);
				PurchaseAgioEntryCollection agios = null;
//				if (purinfo.getAgioEntrys() != null) {
//					agios = (PurchaseAgioEntryCollection) purinfo.getAgioEntrys();
//				}
				EntityViewInfo evi = new EntityViewInfo();
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(
						new FilterItemInfo("head.id", purinfo.getId().toString(),
								CompareType.EQUALS));
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("*"));
				sic.add(new SelectorItemInfo("agio.*"));
				evi.setSelector(sic);
				evi.setFilter(filterInfo);
				try {
					agios = PurchaseAgioEntryFactory.getRemoteInstance().getPurchaseAgioEntryCollection(evi);
				} catch (BOSException e) {
					e.printStackTrace();
				}
				for (int i = 0; agios != null  &&  i < agios.size(); i++) {
					PurchaseAgioEntryInfo entry = agios.get(i);
					AgioBillInfo aAgio = entry.getAgio();

					BigDecimal amount = entry.getAmount() == null ? FDCHelper.ZERO : entry.getAmount();
					BigDecimal pro = entry.getPro() == null ? FDCHelper.ZERO : entry.getPro();

					// 按照选择折扣的先后顺序算出单价
					if (aAgio.getCalType().equals(AgioCalTypeEnum.DanJia)) {
						price = price.subtract(amount);

					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.Dazhe)) {
						price = price.multiply(pro).divide(FDCHelper.ONE_HUNDRED, 10, BigDecimal.ROUND_HALF_UP);
	
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.JianDian)) {
						//减点是对最原始的价格减点，打折时可能是折扣再打折算法是不一样的
						BigDecimal jianPrice = getValidPrice(purinfo.getRoom());
						BigDecimal jiandianAmount = jianPrice.multiply(pro).divide(FDCHelper.ONE_HUNDRED, 10, BigDecimal.ROUND_HALF_UP);
						price = price.subtract(jiandianAmount);
	
					}
				}
				BigDecimal specialPrice =null;
				if (specialAgio == null) {
					specialPrice=FDCHelper.ZERO;
				} else {
					specialPrice =price.multiply(new BigDecimal("1").subtract(specialAgio.divide(new BigDecimal("100"), 10, BigDecimal.ROUND_HALF_UP)));
				}
				
				SpecialAmount = specialPrice.multiply(area);
			}
		}
	}
		
		return SpecialAmount;
	}
	
	

	private BigDecimal getValidArea(RoomInfo room, boolean isLocaleSell) {
		boolean isCalByRoomArea = room.isIsCalByRoomArea();
		BigDecimal area = null;
		if (isCalByRoomArea) {
			// 如果使现售,成交单价按照实测面积计算
			if (isLocaleSell) {
				area = room.getActualRoomArea();
			} else {
				area = room.getRoomArea();
			}
		} else {
			if (isLocaleSell) {
				area = room.getActualBuildingArea();
			} else {
				area = room.getBuildingArea();
			}
		}
		if (area == null) {
			area = FDCHelper.ZERO;
		}
		return area;
	}

	private BigDecimal getValidPrice(RoomInfo room) {
		boolean isCalByRoomArea = room.isIsCalByRoomArea();
		BigDecimal price = null;
		if (isCalByRoomArea) {
			price = room.getRoomPrice();
		} else {
			price = room.getBuildPrice();
		}
		if (price == null) {
			price = FDCHelper.ZERO;
		}
		return price;
	}
	/**
	 * 填充数据
	 */
	private void fillDate(PurchaseInfo purinfo,IRow row){
		row.getCell("sellproject").setValue(purinfo.getSellProject().getName());//项目
		row.getCell("building").setValue(purinfo.getRoom().getBuilding().getName());//楼栋
		if (purinfo.getRoom().getBuildUnit()!=null ){
			row.getCell("unit").setValue(purinfo.getRoom().getBuildUnit().getName());//单元
		}
		row.getCell("room").setValue(purinfo.getRoom().getDisplayName());//房间
		
		String customer = purinfo.getCustomerNames();
		customer = customer.replace(',', ';');
		row.getCell("customer").setValue(customer);//客户
		
		row.getCell("SellState").setValue(purinfo.getRoom().getSellState().toString()); //销售状态
		
		row.getCell("purchiaseDate").setValue(purinfo.getPurchaseDate());//认购日期
		
		row.getCell("signDate").setValue(purinfo.getToSignDate());//签约日期
		
		row.getCell("buildingArea").setValue(purinfo.getRoom().getBuildingArea());//建筑面积
		
		row.getCell("roomArea").setValue(purinfo.getRoom().getRoomArea());//套内面积
		
		row.getCell("standardTotalAmount").setValue(purinfo.getStandardTotalAmount());//标准总价
		
		row.getCell("buildPrice").setValue(purinfo.getRoom().getBuildPrice());//标准建筑单价
		
		row.getCell("roomPrice").setValue(purinfo.getRoom().getRoomPrice());//标准套内单价
		
		row.getCell("standardTotalAmount").setValue(purinfo.getStandardTotalAmount());//标准总价
		
		if(purinfo.getPayType()!=null){
			row.getCell("payStyle").setValue(purinfo.getPayType().getName());//付款方式   
		}
		
		//合同套内单价是 合同总价/套内面积
		if(purinfo.getRoom().getBuildingArea()!=null){
			row.getCell("dealBuildPrice").setValue(purinfo.getDealAmount().divide(purinfo.getRoom().getBuildingArea(),2,BigDecimal.ROUND_HALF_UP));//成交建筑单价  DealBuildPrice
		}
		
		//成交套内单价是 成交总价/套内面积
		if(purinfo.getRoom().getRoomArea()!=null){
			row.getCell("dealRoomPrice").setValue(purinfo.getDealAmount().divide(purinfo.getRoom().getRoomArea(),2,BigDecimal.ROUND_HALF_UP));//成交套内单价 DealRoomPrice
		}
		
		if (purinfo.getSalesman()!=null){//销售顾问
			row.getCell("saleman").setValue(purinfo.getSalesman().getName());
		}
		if (purinfo.getCreator()!=null){//制单人
			row.getCell("dealperson").setValue(purinfo.getCreator().getName());	
		}else {
			row.getCell("dealperson").setValue(null);
		}
		row.getCell("dealTotalAmount").setValue(purinfo.getDealAmount());//成交总价
	}
	

	private PurchaseCollection GetPurChase() throws Exception{
    	
		//项目
		String Sellprojectid =null;
		if (para.isNotNull(spFilterUI.SELLPROJECT)&&para.getString(spFilterUI.SELLPROJECT)!=null){
			Sellprojectid =	para.getString(spFilterUI.SELLPROJECT);
		}
		
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		//折扣方案
//		if (para.getString(spFilterUI.Agio)!=null){
//			filter.getFilterItems().add(new FilterItemInfo("agioEntrys.agio.id", para.getString(spFilterUI.Agio),CompareType.EQUALS));
//		}
		
		Set sellStateCol = new HashSet();
		sellStateCol.add(RoomSellStateEnum.PURCHASE_VALUE);
		sellStateCol.add(RoomSellStateEnum.SIGN_VALUE);
		
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", Sellprojectid,CompareType.EQUALS));
		//付款方式
		if (para.getString(spFilterUI.PayStyle)!=null){
		 filter.getFilterItems().add(new FilterItemInfo("payType.id", para.getString(spFilterUI.PayStyle),CompareType.EQUALS));
		}
		//房间的房号
		if (para.getString(spFilterUI.Room_Number)!=null&&!para.getString(spFilterUI.Room_Number).equals("")){
		 filter.getFilterItems().add(new FilterItemInfo("room.number", "%"+para.getString(spFilterUI.Room_Number)+"%",CompareType.LIKE));
		}
		//只查签约和认购状态的
		filter.getFilterItems().add(new FilterItemInfo("room.sellState",sellStateCol,CompareType.INCLUDE));
		
		//楼栋
		if (para.getString(spFilterUI.Building)!=null){
		 filter.getFilterItems().add(new FilterItemInfo("room.building.id", para.getString(spFilterUI.Building),CompareType.EQUALS));
		}
		
		//时间
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		if (para.getString(spFilterUI.PUR_FROM)!=null){
			filter.getFilterItems().add(new FilterItemInfo("purchaseDate", FORMAT_DAY.format(para.getDate(spFilterUI.PUR_FROM)),CompareType.GREATER_EQUALS));
		}
		if (para.getString(spFilterUI.PUR_TO)!=null){
			filter.getFilterItems().add(new FilterItemInfo("purchaseDate", FORMAT_DAY.format(addDays(para.getDate(spFilterUI.PUR_TO))),CompareType.LESS_EQUALS));
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("room.*"));
		sic.add(new SelectorItemInfo("salesman.*"));
		sic.add(new SelectorItemInfo("auditor.*"));
		
		sic.add(new SelectorItemInfo("payType.*"));
		sic.add(new SelectorItemInfo("room.*"));
		sic.add(new SelectorItemInfo("room.building.*"));
		sic.add(new SelectorItemInfo("room.buildUnit.*"));
		sic.add(new SelectorItemInfo("sellProject.*"));
		sic.add(new SelectorItemInfo("agioEntrys.agio.*"));
		sic.add(new SelectorItemInfo("agioEntrys.agio.id"));
		sic.add(new SelectorItemInfo("agioEntrys.agio.name"));
		
		//报表默认按照项楼栋、单元、房号升序排列
		SorterItemCollection sortc=new SorterItemCollection();
		sortc.add(new SorterItemInfo("room.building.name"));
		sortc.add(new SorterItemInfo("room.buildUnit.name"));
		sortc.add(new SorterItemInfo("room.number"));
		
		view.setSelector(sic);
		view.setFilter(filter);
		view.setSorter(sortc);//purchaseState
		PurchaseCollection purchasecoll = PurchaseFactory.getRemoteInstance().getPurchaseCollection(view);
		for(int i=0,n=purchasecoll.size();i<n;i++){
			PurchaseInfo info=purchasecoll.get(i);
			if(info!=null){
				if(info.getRoom()!=null){
					if(info.getRoom().getLastPurchase()!=null){
						if(!info.getId().equals(info.getRoom().getLastPurchase().getId())){
							purchasecoll.removeObject(info);
						}
					}
				}
			}
		}
		return purchasecoll;
	}
	   private Date addDays(Date date)
	    {
	    	Calendar calendar = new GregorianCalendar();
	    	calendar.setTime(date);
	    	calendar.add(Calendar.DATE,1);
	    	return calendar.getTime();
	    }
	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

	protected ICoreBase getBizInterface() throws Exception {
		return PurchaseFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return PurchaseEditUI.class.getName();
	}
	
	protected String getEditUIModal() {
		return  UIFactoryName.NEWTAB;
	}
	protected String getKeyFieldName()
	{
		return "sellproject";
	}
	
}