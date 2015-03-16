/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.Action;
import javax.swing.ScrollPaneConstants;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.AttachDealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.AttachDealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.FreeTenancyTypeEnum;
import com.kingdee.eas.fdc.tenancy.IDealAmountInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyModification;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.IncreaseStyleEnum;
import com.kingdee.eas.fdc.tenancy.IncreasedRentEntryCollection;
import com.kingdee.eas.fdc.tenancy.IncreasedRentEntryInfo;
import com.kingdee.eas.fdc.tenancy.IncreasedRentTypeEnum;
import com.kingdee.eas.fdc.tenancy.MoreRoomsTypeEnum;
import com.kingdee.eas.fdc.tenancy.NewAttachDealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.NewAttachDealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.NewDealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.NewDealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.NewIncRentEntryCollection;
import com.kingdee.eas.fdc.tenancy.NewIncRentEntryInfo;
import com.kingdee.eas.fdc.tenancy.NewRentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.NewRentFreeEntryInfo;
import com.kingdee.eas.fdc.tenancy.OldAttachDealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.OldAttachDealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.OldDealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.OldDealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.OldIncRentEntryCollection;
import com.kingdee.eas.fdc.tenancy.OldIncRentEntryInfo;
import com.kingdee.eas.fdc.tenancy.OldRentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.OldRentFreeEntryInfo;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryInfo;
import com.kingdee.eas.fdc.tenancy.RentRemissionCollection;
import com.kingdee.eas.fdc.tenancy.RentRemissionFactory;
import com.kingdee.eas.fdc.tenancy.RentRemissionInfo;
import com.kingdee.eas.fdc.tenancy.RentStartTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyModeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyModification;
import com.kingdee.eas.fdc.tenancy.TenancyModificationCollection;
import com.kingdee.eas.fdc.tenancy.TenancyModificationFactory;
import com.kingdee.eas.fdc.tenancy.TenancyModificationInfo;
import com.kingdee.eas.fdc.tenancy.TenancyModificationPayCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class TenancyModificationEditUI extends AbstractTenancyModificationEditUI implements TenancyBillConstant {

	private static final Logger logger = CoreUIObject.getLogger(TenancyModificationEditUI.class);
	private KDWorkButton btnAddIncrease = null;
	private KDWorkButton btnRmIncrease = null;
	private KDWorkButton btnAddFree = null;
	private KDWorkButton btnRmFree = null;
	private MoneyDefineInfo depositMoneyName = null;// 押金款项类型
	private MoneyDefineInfo rentMoneyName = null;// 租金款项类型
	private MoneyDefineCollection monDefineColl = new MoneyDefineCollection();
	Map roomDisMap = new HashMap();
	boolean isFirstEdit = false;
	String oldState = "";
	
	//----------------------------------------------------
	private BigDecimal rentArea = FDCHelper.ZERO;//TODO 很恶心的变量
	private TenancyBillInfo newTenBill = null; 
	private TenancyModificationPayCollection oldTenancyMColl = new TenancyModificationPayCollection();//旧付款计划
	private TenancyModificationPayCollection newTenancyMColl = new TenancyModificationPayCollection();//新付款计划
	private RoomCollection newRoom = new RoomCollection();//房间集合

	
	//-----------------------------------------------------
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		// 合同变更头上的信息
		sels.add("state");
		sels.add("modificationReason");
		sels.add("remark");
		sels.add("newEndDate");
		sels.add("bizDate");
		sels.add("number");
		sels.add("name");
		sels.add("isFreeContract");
		// 租赁合同
		sels.add("tenancy.*");
		// 租赁合同房间分录
		sels.add("tenancy.tenancyRoomList.*");
		// 租赁合同配套资源分录
		sels.add("tenancy.tenAttachResourceList.*");

		// 新旧递增明细分录
		sels.add("newIncreasedRents.*");
		sels.add("oldIncreasedRents.*");
		// 销售项目
		sels.add("sellProject.*");
		// 新旧免租分录
		sels.add("newRentFrees.*");
		sels.add("oldRentFrees.*");
		// 房间新成交租价分录
		sels.add("newDealAmountEntry.*");
		sels.add("newDealAmountEntry.tenancyRoom.*");
		sels.add("newDealAmountEntry.moneyDefine.*");
		// 房间旧成交租价分录
		sels.add("oldDealAmountEntry.*");
		sels.add("oldDealAmountEntry.tenancyRoom.*");
		sels.add("oldDealAmountEntry.moneyDefine.*");
		// 配套资源新成交租价分录
		sels.add("newAttachDealAmountEntry.*");
		sels.add("newAttachDealAmountEntry.tenancyAtta.*");
		sels.add("newAttachDealAmountEntry.moneyDefine.*");
		// 配套资源旧成交租价分录
		sels.add("oldAttachDealAmountEntry.*");
		sels.add("oldAttachDealAmountEntry.tenancyAtta.*");
		sels.add("oldAttachDealAmountEntry.moneyDefine.*");
		//------------------------------------------
		//新付款计划
		sels.add("newPayList.*");
		sels.add("newPayList.room.*");
		sels.add("newPayList.moneyDefine.*");
		sels.add("oldIsFreeContract");
		sels.add("newIsFreeContract");
		//------------------------------------------
		return sels;
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		TenancyModificationInfo tenModifiInfo = (TenancyModificationInfo) this.editData;
		if (tenModifiInfo.getId() != null) {
			String id = tenModifiInfo.getId().toString();
			tenModifiInfo = TenancyModificationFactory.getRemoteInstance().getTenancyModificationInfo(new ObjectUuidPK(id));
			FDCBillStateEnum tenModifiState = tenModifiInfo.getState();
			if (!FDCBillStateEnum.SUBMITTED.equals(tenModifiState)) {
				MsgBox.showInfo(this, "只有已提交的合同变更单才能审批！");
				this.abort();
			}
			TenancyModificationFactory.getRemoteInstance().audit(BOSUuid.read(id));
			MsgBox.showInfo("审批成功！");
		}
	}

	protected void tblFree_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		String colKey = this.tblFree.getColumnKey(colIndex);
		Date startDate = (Date) this.pkStartDate.getValue();
		if(startDate==null){
			this.tblFree.getRow(e.getRowIndex()).getCell(C_FREE_START_DATE).setValue(null);
			this.tblFree.getRow(e.getRowIndex()).getCell(C_FREE_END_DATE).setValue(null);
			MsgBox.showInfo("请先选择租赁合同！");
			
			SysUtil.abort();
		}
		Date endDate = (Date) this.pkNewEndDate.getValue();
		if(endDate==null){
			MsgBox.showInfo("请输入新的结束日期！");
			this.tblFree.getRow(e.getRowIndex()).getCell(C_FREE_END_DATE).setValue(null);
			SysUtil.abort();
		}
		// 当值没有变化时,不进行更新操作
		if (!valueChanged(e)) {
			return;
		}
		IRow row = this.tblFree.getRow(e.getRowIndex());
		ICell cell = row.getCell(colIndex);
		if (cell == null) {
			return;
		}
		if (C_FREE_START_DATE.equals(colKey)) {
			Date freeNewStartDate = (Date) e.getValue();
			if (freeNewStartDate.before(startDate)) {
				if (e.getOldValue() != null) {
					this.tblFree.getRow(e.getRowIndex()).getCell(colIndex).setValue(e.getOldValue());
				}
				MsgBox.showConfirm2("免租开始日期必须大于开始日期");
				this.abort();
			}
			if (freeNewStartDate.after(endDate)) {
				if (e.getOldValue() != null) {
					this.tblFree.getRow(e.getRowIndex()).getCell(colIndex).setValue(e.getOldValue());
				}
				MsgBox.showConfirm2("免租开始日期必须小于新结束日期");
				this.abort();
			}

		}
		if (C_FREE_END_DATE.equals(colKey)) {
			if (this.tblFree.getRow(e.getRowIndex()).getCell(C_FREE_START_DATE).getValue() == null) {
				MsgBox.showConfirm2("请先输入免租开始日期");
				this.abort();
			}
			Date freeNewEndDate = (Date) e.getValue();
			Date freeNewStartDate = (Date) this.tblFree.getRow(e.getRowIndex()).getCell(C_FREE_START_DATE).getValue();
			if (freeNewEndDate.before(startDate) || freeNewEndDate.equals(startDate)) {
				if (e.getOldValue() != null) {
					this.tblFree.getRow(e.getRowIndex()).getCell(colIndex).setValue(e.getOldValue());
				}
				MsgBox.showConfirm2("免租结束日期必须大于开始日期");
				this.abort();
			}
			if (freeNewEndDate.after(endDate)) {
				if (e.getOldValue() != null) {
					this.tblFree.getRow(e.getRowIndex()).getCell(colIndex).setValue(e.getOldValue());
				}
				MsgBox.showConfirm2("免租结束日期必须小于新结束日期");
				this.abort();
			}
			if (freeNewStartDate.after(freeNewEndDate)) {
				if (e.getOldValue() != null) {
					this.tblFree.getRow(e.getRowIndex()).getCell(colIndex).setValue(e.getOldValue());
				} else {
					this.tblFree.getRow(e.getRowIndex()).getCell(colIndex).setValue(null);
				}
				MsgBox.showConfirm2("免租结束日期必须小于新结束日期");
				this.abort();
			}

		}
	}

	protected void tblIncrease_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		String colKey = this.tblIncrease.getColumnKey(colIndex);
		Date startDate = (Date) this.pkStartDate.getValue();
		if(startDate==null){
			this.tblIncrease.getRow(e.getRowIndex()).getCell(C_INC_INCREASE_DATE).setValue(null);
			MsgBox.showInfo("请先选择租赁合同！");
			
			SysUtil.abort();
		}
		Date endDate = (Date) this.pkNewEndDate.getValue();
		if(endDate==null){
			MsgBox.showInfo("请输入新的结束日期！");
			SysUtil.abort();
		}
		// 当值没有变化时,不进行更新操作
		if (!valueChanged(e)) {
			return;
		}
		IRow row = this.tblIncrease.getRow(e.getRowIndex());
		ICell cell = row.getCell(colIndex);
		if (cell == null) {
			return;
		}
		if (C_INC_INCREASE_DATE.equals(colKey)) {
			Date incNewDate = (Date) e.getValue();
			if (incNewDate.before(startDate) || incNewDate.equals(startDate)) {
				if (e.getOldValue() != null) {
					this.tblIncrease.getRow(e.getRowIndex()).getCell(colIndex).setValue(e.getOldValue());
				} else {
					this.tblIncrease.getRow(e.getRowIndex()).getCell(colIndex).setValue(null);
				}
				MsgBox.showConfirm2("递增日期必须大于开始日期");
				this.abort();
			}

			if (incNewDate.after(endDate)) {
				if (e.getOldValue() != null) {
					this.tblIncrease.getRow(e.getRowIndex()).getCell(colIndex).setValue(e.getOldValue());
				}
				MsgBox.showConfirm2("递增日期必须小于新结束日期");
				this.abort();
			}
			TenancyBillInfo billInfo = (TenancyBillInfo) this.prmtTenancy.getValue();
			if (billInfo == null) {
				return;
			}

			if (TenancyModificationFactory.getRemoteInstance().incNewAddCheck(billInfo.getId().toString(), incNewDate)) {
				if (e.getOldValue() != null) {
					this.tblIncrease.getRow(e.getRowIndex()).getCell(colIndex).setValue(e.getOldValue());
				}
				MsgBox.showConfirm2("租赁合同的该租期已经付款，不能增加和修改递增日期");
				this.abort();
			}
			if (this.tblIncrease.getRow(colIndex).getCell(C_INC_INCREASE_TYPE).getValue() == null) {
				return;
			}
			IncreasedRentTypeEnum increasedRentType = (IncreasedRentTypeEnum) row.getCell(C_INC_INCREASE_TYPE).getValue();
			BigDecimal value = (BigDecimal) row.getCell(C_INC_VALUE).getValue();
			if (!IncreasedRentTypeEnum.Handwork.equals(increasedRentType) && value == null) {
				return;
			}

		}

		if (C_INC_INCREASE_TYPE.equals(colKey) && IncreasedRentTypeEnum.Handwork.equals(cell.getValue())) {
			row.getCell(C_INC_VALUE).setValue(null);
			row.getCell(C_INC_VALUE).getStyleAttributes().setLocked(true);
		} else {
			row.getCell(C_INC_VALUE).getStyleAttributes().setLocked(false);
		}

		NewIncRentEntryCollection incNewCol = new NewIncRentEntryCollection();
		for (int i = 0; i < this.tblIncrease.getRowCount(); i++) {
			IRow rowInc = this.tblIncrease.getRow(i);
			Date increaseDate = (Date) rowInc.getCell(C_INC_INCREASE_DATE).getValue();
			if (increaseDate == null) {
				continue;
			}
			IncreasedRentTypeEnum increasedRentType = (IncreasedRentTypeEnum) rowInc.getCell(C_INC_INCREASE_TYPE).getValue();
			BigDecimal value = (BigDecimal) rowInc.getCell(C_INC_VALUE).getValue();

			if (!IncreasedRentTypeEnum.Handwork.equals(increasedRentType) && value == null) {
				continue;
			}
			NewIncRentEntryInfo incNewInfo = new NewIncRentEntryInfo();
			incNewInfo.setIncreaseDate((Date) rowInc.getCell(C_INC_INCREASE_DATE).getValue());
			incNewInfo.setIncreaseType((IncreasedRentTypeEnum) rowInc.getCell(C_INC_INCREASE_TYPE).getValue());
			incNewInfo.setValue(FDCHelper.toBigDecimal(rowInc.getCell(C_INC_VALUE).getValue()));
			incNewCol.add(incNewInfo);
		}
		//TenancyHelper.sortNewIncRentEntryCollectionByIncDate(incNewCol);
		reSetRentNewSetInfo(getTenRoomListFromView(),getTenAttachResListFromView());
		reSetRentSetInfo(getTenRoomListFromView(), getTenAttachResListFromView(),false);
		initRentTypeLock();
	}	
	
	/**
	 * 从界面上取数并封装成租金递增分录集合 <br>
	 * 未设置递增日期和数值的均视为无效记录舍弃
	 * */
	private IncreasedRentEntryCollection getIncreasedRentsFromView() {
		IncreasedRentEntryCollection increasedRents = new IncreasedRentEntryCollection();
		for (int i = 0; i < this.tblIncrease.getRowCount(); i++) {
			IRow row = this.tblIncrease.getRow(i);
			Date increaseDate = (Date) row.getCell(C_INC_INCREASE_DATE).getValue();
			if (increaseDate == null) {
				continue;
			}

			IncreasedRentTypeEnum increasedRentType = (IncreasedRentTypeEnum) row.getCell(C_INC_INCREASE_TYPE).getValue();
			BigDecimal value = (BigDecimal) row.getCell(C_INC_VALUE).getValue();

			if (!IncreasedRentTypeEnum.Handwork.equals(increasedRentType)  &&  value == null) {
				continue;
			}

			IncreaseStyleEnum increaseStyle = (IncreaseStyleEnum) row.getCell(C_INC_INCREASESTYLE).getValue();

			IncreasedRentEntryInfo increasedRent = new IncreasedRentEntryInfo();
			increasedRent.setIncreaseDate(increaseDate);
			increasedRent.setIncreaseType(increasedRentType);
			increasedRent.setValue(value);
			increasedRent.setIncreaseStyle(increaseStyle);

			increasedRents.add(increasedRent);
		}
		return increasedRents;
	}
	
	/**
	 * 重新根据定价时间期生成租金分录,挂在租赁房间下。并刷新到界面。 <br>
	 * 以下情况才需要调用：修改租赁起始，结束日期；修改租金递增列表的日期；增加和删除房间
	 * {@link #pkStartDate_dataChanged(DataChangeEvent)} {@link #pkEndDate_dataChanged(DataChangeEvent)}
	 * {@link #tblIncrease_editStopped(KDTEditEvent)}
	 * {@link #btnAddRoom_actionPerformed(ActionEvent)} {@link #btnRemoveRoom_actionPerformed(ActionEvent)} <br>
	 * 一般重置租金后，均需要刷新付款明细，即需要调用{@link #updatePayListInfo()}
	 * @throws EASBizException 
	 * */
	private void reSetRentSetInfo(TenancyRoomEntryCollection tenRooms, TenAttachResourceEntryCollection tenAttaches,boolean isNeedSynToOld) throws BOSException, EASBizException {
		if(prmtTenancy.getValue()==null){
			return;
		}
		TenancyBillInfo tenBill = (TenancyBillInfo)prmtTenancy.getValue();
		tenBill = TenancyHelper.getTenancyBillInfo(tenBill.getId().toString());
		if (tenBill == null) {
			return;
		}
		boolean isDynamicStartDate = isDynamicStartDate(tenBill.getRentStartType());
		IncreasedRentEntryCollection increasedRents = getIncreasedRentsFromView();
		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkNewEndDate.getValue();

		//保存已设置的周期性费用的值
		Map mapPrice = new HashMap();
		Map mapRent = new HashMap();	
		int m =0;		
		for(int i=0;i<tblRent.getRowCount();i++)
		{			
			IRow row = tblRent.getRow(i);
			String moneyName = null;
			for(int j=0;j<tblRent.getColumnCount();j++)
			{
				IColumn column = this.tblRent.getColumn(j);
				String colKey = column.getKey();
				//				if(colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE))
				//				{
				//					String str = colKey.substring(PREFIX_C_RENT_PERIODICITY_PRICE.length(),colKey.length());
				//					StringBuffer strBuff = new StringBuffer(str);
				//					strBuff.append(i);
				//					BigDecimal price = (BigDecimal)row.getCell(colKey).getValue();
				//					if(price==null)
				//					{
				//						price = new BigDecimal(0);
				//					}
				//					mapPrice.put(strBuff.toString(), price);
				//				}else 
				if(colKey.startsWith(PREFIX_C_RENT_PERIODICITY))
				{
					//因为递增会把时间分段，当有多个周期性费用递增时用时间无法和保存时的时间一致
					//所以用款项类型加上第几行第几列来判断才是唯一的
					int index1 =colKey.indexOf("-");
					int index = colKey.indexOf("(");
					String str = colKey.substring(index1+1,index);
					if(!str.equals(moneyName))
					{
						m=0;
					}else
					{
						m++;
					}
					moneyName = str;
					StringBuffer strBuff = new StringBuffer(str);
					strBuff.append(i);
					strBuff.append(m);
					BigDecimal rent = (BigDecimal)row.getCell(colKey).getValue();
					if(rent==null)
					{
						rent = new BigDecimal(0);
					}
					mapRent.put(strBuff.toString(), rent);
				}
			}
		}
		//增加周期型费用
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.PeriodicityAmount));
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.TenancySys));
		//原来没有是否启用状态，周期性费用需要处理了
		//		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		MoneyDefineCollection periodicityMoneys = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
				
		
		for(int i=0;i<tblRent.getRowCount();i++)
		{
			Map map = new HashMap();
			IRow row = tblRent.getRow(i);
			TenancyRoomEntryInfo tenRoom = new TenancyRoomEntryInfo();
			if(row.getUserObject()!=null)
			{
				tenRoom = (TenancyRoomEntryInfo)row.getUserObject();
			}else
			{
				break;
			}
			for(int j=0;j<monDefineColl.size();j++)
			{
				MoneyDefineInfo moneyInfo = monDefineColl.get(j);
				BigDecimal disAmount = new BigDecimal(0);
				if(row.getCell(moneyInfo.getNumber()).getValue()==null)
				{
//					BigDecimal disAmount = (BigDecimal)row.getCell(moneyInfo.getNumber()).getValue();
					map.put(moneyInfo, disAmount);
				}else
				{
					disAmount = (BigDecimal)row.getCell(moneyInfo.getNumber()).getValue();
					map.put(moneyInfo, disAmount);
				}
			}
			roomDisMap.put(tenRoom.getRoom().getId().toString(), map);
		}

		try {
			isFirstEdit = TenancyHelper.reSetRentSetInfo(tenRooms,roomDisMap,increasedRents, startDate, endDate, periodicityMoneys, 
					DealAmountEntryCollection.class, DealAmountEntryInfo.class, getRentMoneyDefine(), isDynamicStartDate,mapPrice,mapRent,this.getOprtState(),isFirstEdit,oldState);
			isFirstEdit = TenancyHelper.reSetRentSetInfo(tenAttaches,roomDisMap,increasedRents, startDate, endDate, periodicityMoneys, 
					AttachDealAmountEntryCollection.class, AttachDealAmountEntryInfo.class, getRentMoneyDefine(), isDynamicStartDate,mapPrice,mapRent,this.getOprtState(),isFirstEdit,oldState);
		} catch (InstantiationException e) {
			throw new BOSException(e);
		} catch (IllegalAccessException e) {
			throw new BOSException(e);
		}

		if(isDynamicStartDate  &&  startDate == null){
			loadTblRentSet2(tenRooms, tenAttaches,tblRent);
			if(isNeedSynToOld)loadTblRentSet2(tenRooms, tenAttaches,tblOriRent);
		}else{
			loadTblRentSet(tenRooms, tenAttaches,tblRent);
			if(isNeedSynToOld)loadTblRentSet(tenRooms, tenAttaches,tblOriRent);
		}	
	}
	
	/**
	 * 根据租赁房间和租赁房间的成交价格分录.更新租金设置Table
	 * @param tenAttaches 
	 * */
	private void loadTblRentSet(TenancyRoomEntryCollection tenRooms, TenAttachResourceEntryCollection tenAttaches,KDTable tblMain) {
		tblMain.removeRows();
		/* 汇总押金 */
		BigDecimal depositGather = FDCHelper.ZERO;
		/* 汇总首租 */
		BigDecimal firstRentGather = FDCHelper.ZERO;
		RentTypeEnum rentTypeGather = RentTypeEnum.RentByMonth;
		rentArea = FDCHelper.ZERO;
		Map rentMap = new HashMap();
		Map rentPriceMap = new HashMap();
		Map otherRentMap =  new HashMap();
		Map otherRentPriceMap = new HashMap();
		BigDecimal standardRentGateher = FDCHelper.ZERO;
		//先将租金列删除，再根据设置的递增列表动态生成tblRentSet列
		List toDelColIndexes = new ArrayList();
		for (int i = 0; i < tblMain.getColumnCount(); i++) {
			IColumn column = tblMain.getColumn(i);
			String colKey = column.getKey();
			if (colKey.startsWith(PREFIX_C_RENT_RENT_PRICE) || colKey.startsWith(PREFIX_C_RENT_RENT)  
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				toDelColIndexes.add(new Integer(i));
			}
		}

		for (int i = toDelColIndexes.size() - 1; i >=0 ; i--) {
			int colIndex = ((Integer) toDelColIndexes.get(i)).intValue();
			tblMain.removeColumn(colIndex);
		}

		Map despositMap = new HashMap();
		//增加第一行时进行动态增加列
		boolean isFirstRow = true;
		for(int i=0; i<tenRooms.size(); i++){
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			IRow row = tblMain.addRow();
			row.setUserObject(tenRoom);
			row.getCell(C_RENT_ROOM).setValue(tenRoom.getRoomLongNum());
			TenancyModeEnum tenancyMode = TenancyModeEnum.TenancyRentModel;
			if(tenRoom.getTenancyModel()!=null)
			{
				tenancyMode = tenRoom.getTenancyModel();
			}else
			{
				tenRoom.setTenancyModel(tenancyMode);
			}
			row.getCell(C_RENT_TENANCY_MODEL).setValue(tenancyMode);	
			//			RentTypeEnum rentType = RentTypeEnum.RentByMonth;
			//			if(tenRoom.getDealRentType() != null)
			//			{
			RentTypeEnum rentType = tenRoom.getDealRentType();
			//}
			row.getCell(C_RENT_RENT_TYPE).setValue(rentType);
//			row.getCell(C_RENT_DEPOSIT).setValue(tenRoom.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenRoom.getFirstPayAmount());
//			if(tenRoom.getDepositAmount()!=null)
//			{
//				depositGather = depositGather.add(tenRoom.getDepositAmount());
//			}
			if(tenRoom.getFirstPayAmount()!=null)
			{
				firstRentGather = firstRentGather.add(tenRoom.getFirstPayAmount());
			}

			if(tenRoom.getBuildingArea()!=null)
			{
				rentArea = rentArea.add(tenRoom.getBuildingArea());
			}	
			//因为汇总行的租金类型总会和房间行的一样
			//if(tenRoom.getDealRentType()!=null)
			//{
			rentTypeGather = tenRoom.getDealRentType();
			//}
			DealAmountEntryCollection dealAmounts = tenRoom.getDealAmounts();
			for(int k=0;k<dealAmounts.size();k++)
			{
				DealAmountEntryInfo dealAmount = dealAmounts.get(k);
				String moneyNumber = dealAmount.getMoneyDefine().getNumber();
				//把汇总行押金存起来
				if(despositMap.get(moneyNumber)==null)
				{
					depositGather = dealAmount.getAmount();
					despositMap.put(moneyNumber,depositGather);
				}else
				{
					BigDecimal amount = (BigDecimal)despositMap.get(moneyNumber);
					amount = amount ==null?new BigDecimal(0):amount;
					if(dealAmount.getAmount()==null)
					{
						depositGather = amount.add(new BigDecimal(0));
						despositMap.put(moneyNumber,depositGather);
					}else
					{
						depositGather = amount.add(dealAmount.getAmount());
						despositMap.put(moneyNumber,depositGather);
					}
					
				}
				//如果只有一种押金类型，赋值
//				if(monDefineColl.size()==1)
//				{
//					MoneyDefineInfo moneyInfo = monDefineColl.get(0);
//					if(moneyNumber.equals(moneyInfo.getNumber()))
//					{
//						if(dealAmount.getAmount()==null || dealAmount.getAmount().compareTo(new BigDecimal(0))==0)
//						{
//							row.getCell(moneyNumber).setValue(tenRoom.getDepositAmount());
//						}else
//						{
//							row.getCell(moneyNumber).setValue(dealAmount.getAmount());
//						}
//						
//					}
//				}else
				{
					for(int j=0;j<monDefineColl.size();j++)
					{
						MoneyDefineInfo moneyInfo = monDefineColl.get(j);
						if(moneyNumber.equals(moneyInfo.getNumber()))
						{
							row.getCell(moneyNumber).setValue(dealAmount.getAmount());
						}					
					}
				}				
			}
			if(isFirstRow){
				//周期性费用
				DealAmountEntryCollection periodicityMoneys = new DealAmountEntryCollection();
				for(int j=0; j<dealAmounts.size(); j++){
					DealAmountEntryInfo dealAmount = dealAmounts.get(j);
					if(MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType()))
					{
						continue;
					}
					String moneyName = dealAmount.getMoneyDefine().getName();
					if(MoneyTypeEnum.PeriodicityAmount.equals(dealAmount.getMoneyDefine().getMoneyType())){
						periodicityMoneys.add(dealAmount);
						continue;
					}
					String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
					String keyRent = PREFIX_C_RENT_RENT + dateDes;

					addTblRentSetColumn(keyRentPrice, keyRent, dateDes,tblMain);
				}

				for(int j=0; j<periodicityMoneys.size(); j++){
					DealAmountEntryInfo dealAmount = periodicityMoneys.get(j);
					String moneyName = dealAmount.getMoneyDefine().getName();
					String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());
					String key1 = PREFIX_C_RENT_PERIODICITY_PRICE + dateDes;
					String key2 = PREFIX_C_RENT_PERIODICITY + dateDes;

					addTblRentPeriodicityColumn(key1, key2, dateDes,tblMain);
				}
				isFirstRow = false;
			}

			for(int j=0; j<dealAmounts.size(); j++){
				DealAmountEntryInfo dealAmount = dealAmounts.get(j);
				if(MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType()))
				{
					continue;
				}
				String moneyName = dealAmount.getMoneyDefine().getName();
				String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
				String keyRent = PREFIX_C_RENT_RENT + dateDes;

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if(cellRentPrice != null  &&  cellRent != null){
					cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());

					//把租金汇总的键值和单价存起来在新增汇总行时调用
					if(rentMap.get(keyRent)!=null)
					{
						BigDecimal rent = (BigDecimal)rentMap.get(keyRent);
						rent = rent==null?new BigDecimal(0):rent;
						BigDecimal amount = new BigDecimal(0);
						if(dealAmount.getAmount()!=null)
						{
							amount = dealAmount.getAmount();
						}
						standardRentGateher = rent.add(amount);
						rentMap.put(keyRent, standardRentGateher);
						rentPriceMap.put(keyRent, keyRentPrice);
					}else
					{
						standardRentGateher = dealAmount.getAmount();
						standardRentGateher = standardRentGateher==null?new BigDecimal(0):standardRentGateher;
						rentMap.put(keyRent,standardRentGateher);
						rentPriceMap.put(keyRent, keyRentPrice);
					}
					//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
					cellRentPrice.setUserObject(dealAmount);
					cellRent.setUserObject(dealAmount);

					if(dealAmount.isIsHandwork()){
						//如果不是按总价计算，则锁定总价不可编辑
						if(!TenancyModeEnum.TenancyRentModel.equals(tenancyMode)){
							cellRent.getStyleAttributes().setLocked(true);
						}else{
							cellRentPrice.getStyleAttributes().setLocked(true);
						}
					}else{
						cellRentPrice.getStyleAttributes().setLocked(true);
						cellRent.getStyleAttributes().setLocked(true);
					}
				}else{
					keyRentPrice = PREFIX_C_RENT_PERIODICITY_PRICE + dateDes;
					keyRent = PREFIX_C_RENT_PERIODICITY + dateDes;

					cellRentPrice = row.getCell(keyRentPrice);
					cellRent = row.getCell(keyRent);

					if(cellRentPrice != null  &&  cellRent != null){
						cellRentPrice.setValue(dealAmount.getPriceAmount());
						cellRent.setValue(dealAmount.getAmount());

						if(otherRentMap.get(keyRent)!=null)
						{
							BigDecimal rent = (BigDecimal)otherRentMap.get(keyRent);
							rent = rent==null?new BigDecimal(0):rent;
							BigDecimal amount = new BigDecimal(0);
							if(dealAmount.getAmount()!=null)
							{
								amount = dealAmount.getAmount();
							}
							standardRentGateher = rent.add(amount);
							otherRentMap.put(keyRent, standardRentGateher);
							otherRentPriceMap.put(keyRent, keyRentPrice);
						}else
						{
							standardRentGateher = dealAmount.getAmount();
							standardRentGateher = standardRentGateher==null?new BigDecimal(0):standardRentGateher;
							otherRentMap.put(keyRent,standardRentGateher);
							otherRentPriceMap.put(keyRent, keyRentPrice);
						}

						//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
						cellRentPrice.setUserObject(dealAmount);
						cellRent.setUserObject(dealAmount);

						//如果不是按总价计算，则锁定总价不可编辑
						if(!TenancyModeEnum.TenancyRentModel.equals(tenancyMode)){
							cellRent.getStyleAttributes().setLocked(true);
						}else{
							cellRentPrice.getStyleAttributes().setLocked(true);
						}
					}
				}
			}
		}

		//刚开始加汇总行的时候默认是汇总房间的数据锁定非汇总的行
		if(isStandardRentSetting())
		{
			IRow rowGather = tblMain.addRow();
			rowGather.getStyleAttributes().setBackground(Color.YELLOW);
			rowGather.getCell(C_RENT_ROOM).setValue("租金汇总");
			rowGather.getCell(C_RENT_TENANCY_MODEL).setValue(TenancyModeEnum.TenancyRentModel);
			rowGather.getCell(C_RENT_RENT_TYPE).setValue(rentTypeGather);
			for(int i=0;i<monDefineColl.size();i++)
			{
				MoneyDefineInfo moneyInfo = monDefineColl.get(i);
				if(despositMap.get(moneyInfo.getNumber())!=null)
				{
					BigDecimal amount = (BigDecimal)despositMap.get(moneyInfo.getNumber());
					rowGather.getCell(moneyInfo.getNumber()).setValue(amount);
				}
			}
//			rowGather.getCell(C_RENT_DEPOSIT).setValue(depositGather);
			rowGather.getCell(C_RENT_FIRST_RENT).setValue(firstRentGather);
			rowGather.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(true);

			setRentAndPriceGather(rentMap,rentPriceMap,rowGather);
			setRentAndPriceGather(otherRentMap,otherRentPriceMap,rowGather);


			//			Set otherRentSet = otherRentMap.keySet();
			//			Iterator otherRent = otherRentSet.iterator();
			//			while(otherRent.hasNext())
			//			{
			//				String otherKey = (String)otherRent.next();
			//				rowGather.getCell(otherKey).setValue(otherRentMap.get(otherKey));
			//				String otherRentPriceKey = (String)otherRentPriceMap.get(otherKey);
			//				BigDecimal value = new BigDecimal(0);
			//				if(rentMap.get(otherKey)!=null)
			//				{
			//					value = (BigDecimal)rentMap.get(otherKey);
			//				}
			//				rowGather.getCell(otherRentPriceKey).setValue(value.divide(rentArea, 2,BigDecimal.ROUND_HALF_UP));
			//				rowGather.getCell(otherKey).setUserObject(value);
			////				rowGather.getCell(otherKey).getStyleAttributes().setLocked(true);
			//				rowGather.getCell(otherRentPriceKey).getStyleAttributes().setLocked(true);
			//			}
			lockTblRent();
		}

		for(int i=0; i<tenAttaches.size(); i++){
			TenAttachResourceEntryInfo tenAttach = tenAttaches.get(i);
			IRow row = tblMain.addRow();

			row.setUserObject(tenAttach);
			row.getCell(C_RENT_ROOM).setValue(tenAttach.getAttachLongNum());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(TenancyModeEnum.TenancyRentModel);
			row.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(true);
			row.getCell(C_RENT_RENT_TYPE).setValue(tenAttach.getDealRentType());
//			row.getCell(C_RENT_DEPOSIT).setValue(tenAttach.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenAttach.getFirstPayAmount());

			AttachDealAmountEntryCollection dealAmounts = tenAttach.getDealAmounts();
			if(isFirstRow){
				//周期性费用
				AttachDealAmountEntryCollection periodicityMoneys = new AttachDealAmountEntryCollection();
				for(int j=0; j<dealAmounts.size(); j++){
					AttachDealAmountEntryInfo dealAmount = dealAmounts.get(j);
					String moneyName = dealAmount.getMoneyDefine().getName();
					if(MoneyTypeEnum.PeriodicityAmount.equals(dealAmount.getMoneyDefine().getMoneyType())){
						periodicityMoneys.add(dealAmount);
						continue;
					}
					String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
					String keyRent = PREFIX_C_RENT_RENT + dateDes;

					addTblRentSetColumn(keyRentPrice, keyRent, dateDes,tblMain);
				}

				for(int j=0; j<periodicityMoneys.size(); j++){
					AttachDealAmountEntryInfo dealAmount = periodicityMoneys.get(j);
					String moneyName = dealAmount.getMoneyDefine().getName();
					String key1 = PREFIX_C_RENT_PERIODICITY_PRICE + moneyName;
					String key2 = PREFIX_C_RENT_PERIODICITY + moneyName;
					String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

					addTblRentPeriodicityColumn(key1, key2, dateDes,tblMain);
				}
				isFirstRow = false;
			}

			for(int j=0; j<dealAmounts.size(); j++){
				AttachDealAmountEntryInfo dealAmount = dealAmounts.get(j);
				String moneyName = dealAmount.getMoneyDefine().getName();
				String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
				String keyRent = PREFIX_C_RENT_RENT + dateDes;

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if(cellRentPrice != null  &&  cellRent != null){
					//					cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());

					//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
					//					cellRentPrice.setUserObject(dealAmount);
					cellRent.setUserObject(dealAmount);

					if(dealAmount.isIsHandwork()){
						//如果不是按总价计算，则锁定总价不可编辑
						cellRentPrice.getStyleAttributes().setLocked(true);
					}else{
						cellRentPrice.getStyleAttributes().setLocked(true);
						cellRent.getStyleAttributes().setLocked(true);
					}
				}else{
					keyRentPrice = PREFIX_C_RENT_PERIODICITY_PRICE + moneyName;
					keyRent = PREFIX_C_RENT_PERIODICITY + moneyName;

					cellRentPrice = row.getCell(keyRentPrice);
					cellRent = row.getCell(keyRent);

					if(cellRentPrice != null  &&  cellRent != null){
						//						cellRentPrice.setValue(dealAmount.getPriceAmount());
						cellRent.setValue(dealAmount.getAmount());

						//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
						cellRentPrice.setUserObject(dealAmount);
						cellRent.setUserObject(dealAmount);

						//如果不是按总价计算，则锁定总价不可编辑
						cellRentPrice.getStyleAttributes().setLocked(true);
					}
				}
			}
		}

		tblMain.getHeadMergeManager().mergeBlock(0, 0, 1, tblMain.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
		tblMain.setAutoscrolls(true);
	}
	
	//动态起租开始日期时候加载
	private void loadTblRentSet2(TenancyRoomEntryCollection tenRooms, TenAttachResourceEntryCollection tenAttaches,KDTable tblMain) {
		tblMain.removeRows();
		/* 汇总押金 */
		BigDecimal depositGather = FDCHelper.ZERO;
		/* 汇总首租 */
		BigDecimal firstRentGather = FDCHelper.ZERO;
		RentTypeEnum rentTypeGather = RentTypeEnum.RentByMonth;
		rentArea = FDCHelper.ZERO;
		Map rentMap = new HashMap();
		Map rentPriceMap = new HashMap();
		BigDecimal standardRentGateher = FDCHelper.ZERO;
		//先将租金列删除，再根据设置的递增列表动态生成tblRentSet列
		List toDelColIndexes = new ArrayList();
		for (int i = 0; i < tblMain.getColumnCount(); i++) {
			IColumn column = tblMain.getColumn(i);
			String colKey = column.getKey();
			if (colKey.startsWith(PREFIX_C_RENT_RENT_PRICE) || colKey.startsWith(PREFIX_C_RENT_RENT)  
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				toDelColIndexes.add(new Integer(i));
			}
		}

		for (int i = toDelColIndexes.size() - 1; i >=0 ; i--) {
			int colIndex = ((Integer) toDelColIndexes.get(i)).intValue();
			tblMain.removeColumn(colIndex);
		}
		Map despositMap = new HashMap();
		//增加第一行时进行动态增加列
		boolean isFirstRow = true;
		for(int i=0; i<tenRooms.size(); i++){
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			IRow row = tblMain.addRow();
			row.setUserObject(tenRoom);
			row.getCell(C_RENT_ROOM).setValue(tenRoom.getRoomLongNum());
			TenancyModeEnum tenancyMode = TenancyModeEnum.TenancyRentModel;
			if(tenRoom.getTenancyModel()!=null)
			{
				tenancyMode = tenRoom.getTenancyModel();
			}
			row.getCell(C_RENT_TENANCY_MODEL).setValue(tenancyMode);
			row.getCell(C_RENT_RENT_TYPE).setValue(tenRoom.getDealRentType());
//			row.getCell(C_RENT_DEPOSIT).setValue(tenRoom.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenRoom.getFirstPayAmount());
			if(tenRoom.getDepositAmount()!=null)
			{
				depositGather = depositGather.add(tenRoom.getDepositAmount());
			}
			if(tenRoom.getFirstPayAmount()!=null)
			{
				firstRentGather = firstRentGather.add(tenRoom.getFirstPayAmount());
			}

			if(tenRoom.getBuildingArea()!=null)
			{
				rentArea = rentArea.add(tenRoom.getBuildingArea());
			}	
			//因为汇总行的租金类型总会和房间行的一样
			if(tenRoom.getDealRentType()!=null)
			{
				rentTypeGather = tenRoom.getDealRentType();
			}
			DealAmountEntryCollection dealAmounts = tenRoom.getDealAmounts();
			for(int k=0;k<dealAmounts.size();k++)
			{
				DealAmountEntryInfo dealAmount = dealAmounts.get(k);
				String moneyNumber = dealAmount.getMoneyDefine().getNumber();
				//把汇总行押金存起来
				if(despositMap.get(moneyNumber)==null)
				{
					depositGather = dealAmount.getAmount();
					despositMap.put(moneyNumber,depositGather);
				}else
				{
					BigDecimal amount = (BigDecimal)despositMap.get(moneyNumber);
					amount = amount ==null?new BigDecimal(0):amount;
					if(dealAmount.getAmount()==null)
					{
						depositGather = amount.add(new BigDecimal(0));
						despositMap.put(moneyNumber,depositGather);
					}else
					{
						depositGather = amount.add(dealAmount.getAmount());
						despositMap.put(moneyNumber,depositGather);
					}
					
				}
				{
					for(int j=0;j<monDefineColl.size();j++)
					{
						MoneyDefineInfo moneyInfo = monDefineColl.get(j);
						if(moneyNumber.equals(moneyInfo.getNumber()))
						{
							row.getCell(moneyNumber).setValue(dealAmount.getAmount());
						}					
					}
				}				
			}
			if(isFirstRow){
				for(int j=0; j<dealAmounts.size(); j++){
					DealAmountEntryInfo dealAmount = dealAmounts.get(j);
					if(!MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType()))
					{
						String keyRentPrice = PREFIX_C_RENT_RENT_PRICE;
						String keyRent = PREFIX_C_RENT_RENT;
						addTblRentSetColumn(keyRentPrice, keyRent, "租金设置",tblMain);
					}					
				}
				isFirstRow = false;
			}

			for(int j=0; j<dealAmounts.size(); j++){
				DealAmountEntryInfo dealAmount = dealAmounts.get(j);
				if(!MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType()))
				{
				String moneyName = dealAmount.getMoneyDefine().getName();

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE;
				String keyRent = PREFIX_C_RENT_RENT;

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if(cellRentPrice != null  &&  cellRent != null){
					cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());

					if(rentMap.get(keyRent)!=null)
					{
						BigDecimal rent = (BigDecimal)rentMap.get(keyRent);
						rent = rent==null?new BigDecimal(0):rent;
						BigDecimal amount = new BigDecimal(0);
						if(dealAmount.getAmount()!=null)
						{
							amount = dealAmount.getAmount();
						}
						standardRentGateher = rent.add(amount);
						rentMap.put(keyRent, standardRentGateher);
						rentPriceMap.put(keyRent, keyRentPrice);
					}else
					{
						standardRentGateher = dealAmount.getAmount();
						standardRentGateher = standardRentGateher==null?new BigDecimal(0):standardRentGateher;
						rentMap.put(keyRent,standardRentGateher);
						rentPriceMap.put(keyRent, keyRentPrice);
					}

					//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
					cellRentPrice.setUserObject(dealAmount);
					cellRent.setUserObject(dealAmount);					

					//如果不是按总价计算，则锁定总价不可编辑
					if(!TenancyModeEnum.TenancyRentModel.equals(tenancyMode)){
						cellRent.getStyleAttributes().setLocked(true);
					}else{
						cellRentPrice.getStyleAttributes().setLocked(true);
					}
				}else{
					keyRentPrice = PREFIX_C_RENT_PERIODICITY_PRICE + moneyName;
					keyRent = PREFIX_C_RENT_PERIODICITY + moneyName;

					cellRentPrice = row.getCell(keyRentPrice);
					cellRent = row.getCell(keyRent);

					if(cellRentPrice != null  &&  cellRent != null){
						cellRentPrice.setValue(dealAmount.getPriceAmount());
						cellRent.setValue(dealAmount.getAmount());

						//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
						cellRentPrice.setUserObject(dealAmount);
						cellRent.setUserObject(dealAmount);

						//如果不是按总价计算，则锁定总价不可编辑
						if(!TenancyModeEnum.TenancyRentModel.equals(tenRoom.getTenancyModel())){
							cellRent.getStyleAttributes().setLocked(true);
						}else{
							cellRentPrice.getStyleAttributes().setLocked(true);
						}
					}
				}
			  }
			}
		}

		//刚开始加汇总行的时候默认是汇总房间的数据锁定非汇总的行
		if(isStandardRentSetting())
		{
			IRow rowGather = tblMain.addRow();
			rowGather.getStyleAttributes().setBackground(Color.YELLOW);
			rowGather.getCell(C_RENT_ROOM).setValue("租金汇总");
			rowGather.getCell(C_RENT_TENANCY_MODEL).setValue(TenancyModeEnum.TenancyRentModel);
			rowGather.getCell(C_RENT_RENT_TYPE).setValue(rentTypeGather);
			for(int i=0;i<monDefineColl.size();i++)
			{
				MoneyDefineInfo moneyInfo = monDefineColl.get(i);
				if(despositMap.get(moneyInfo.getNumber())!=null)
				{
					BigDecimal amount = (BigDecimal)despositMap.get(moneyInfo.getNumber());
					rowGather.getCell(moneyInfo.getNumber()).setValue(amount);
				}
			}
//			rowGather.getCell(C_RENT_DEPOSIT).setValue(depositGather);
			rowGather.getCell(C_RENT_FIRST_RENT).setValue(firstRentGather);
			rowGather.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(true);

			setRentAndPriceGather(rentMap,rentPriceMap,rowGather);
			lockTblRent();
		}

		for(int i=0; i<tenAttaches.size(); i++){
			TenAttachResourceEntryInfo tenAttach = tenAttaches.get(i);
			IRow row = tblMain.addRow();

			row.setUserObject(tenAttach);
			row.getCell(C_RENT_ROOM).setValue(tenAttach.getAttachLongNum());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(TenancyModeEnum.TenancyRentModel);
			row.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(true);
			row.getCell(C_RENT_RENT_TYPE).setValue(tenAttach.getDealRentType());
//			row.getCell(C_RENT_DEPOSIT).setValue(tenAttach.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenAttach.getFirstPayAmount());

			AttachDealAmountEntryCollection dealAmounts = tenAttach.getDealAmounts();

			if(isFirstRow){
				for(int j=0; j<dealAmounts.size(); j++){				
					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE;
					String keyRent = PREFIX_C_RENT_RENT;
					addTblRentSetColumn(keyRentPrice, keyRent, "租金设置",tblMain);
				}
				isFirstRow = false;
			}
			for(int j=0; j<dealAmounts.size(); j++){
				AttachDealAmountEntryInfo dealAmount = dealAmounts.get(j);
				String moneyName = dealAmount.getMoneyDefine().getName();
				//String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE;
				String keyRent = PREFIX_C_RENT_RENT;			

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if(cellRentPrice != null  &&  cellRent != null){
					//					cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());

					//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
					//					cellRentPrice.setUserObject(dealAmount);
					cellRent.setUserObject(dealAmount);

					if(dealAmount.isIsHandwork()){
						//如果不是按总价计算，则锁定总价不可编辑
						cellRentPrice.getStyleAttributes().setLocked(true);
					}else{
						cellRentPrice.getStyleAttributes().setLocked(true);
						cellRent.getStyleAttributes().setLocked(true);
					}
				}else{
					keyRentPrice = PREFIX_C_RENT_PERIODICITY_PRICE + moneyName;
					keyRent = PREFIX_C_RENT_PERIODICITY + moneyName;

					cellRentPrice = row.getCell(keyRentPrice);
					cellRent = row.getCell(keyRent);

					if(cellRentPrice != null  &&  cellRent != null){
						//						cellRentPrice.setValue(dealAmount.getPriceAmount());
						cellRent.setValue(dealAmount.getAmount());

						//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
						cellRentPrice.setUserObject(dealAmount);
						cellRent.setUserObject(dealAmount);

						//如果不是按总价计算，则锁定总价不可编辑
						cellRentPrice.getStyleAttributes().setLocked(true);
					}
				}
			}
		}

		tblMain.getHeadMergeManager().mergeBlock(0, 0, 1, tblMain.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
		tblMain.setAutoscrolls(true);
	}
	
	private void addTblRentPeriodicityColumn(String key1, String key2, String des,KDTable tblMain) {
		IColumn col = tblMain.addColumn();
		col.setKey(key1);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		col = tblMain.addColumn();
		col.setKey(key2);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		IRow headRow0 = tblMain.getHeadRow(0);
		headRow0.getCell(key1).setValue(des);
		headRow0.getCell(key2).setValue(des);

		IRow headRow1 = tblMain.getHeadRow(1);
		headRow1.getCell(key1).setValue("金额单价");
		headRow1.getCell(key2).setValue("金额");
	}
	
	//设置汇总行中租金和租金单价的值
	private void setRentAndPriceGather(Map rentMap,Map rentPriceMap,IRow rowGather)
	{
		Set rentSet = rentMap.keySet();
		Iterator rentIter = rentSet.iterator();
		while(rentIter.hasNext())
		{
			String rentKey = (String)rentIter.next();
			rowGather.getCell(rentKey).setValue(rentMap.get(rentKey));
			String rentPriceKey = (String)rentPriceMap.get(rentKey);
			BigDecimal value = new BigDecimal(0);
			if(rentMap.get(rentKey)!=null)
			{
				value = (BigDecimal)rentMap.get(rentKey);
			}
			rowGather.getCell(rentPriceKey).setValue(value.divide(rentArea, 2,BigDecimal.ROUND_HALF_UP));
			rowGather.getCell(rentKey).setUserObject(rentMap.get(rentKey));
			rowGather.getCell(rentPriceKey).getStyleAttributes().setLocked(true);
		}
	}

	private void addTblRentSetColumn(String keyRentType, String keyRent, String headRowDes,KDTable tblMain) {
		IColumn col = tblMain.addColumn();
		col.setKey(keyRentType);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		col = tblMain.addColumn();
		col.setKey(keyRent);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		IRow headRow0 = tblMain.getHeadRow(0);
		headRow0.getCell(keyRentType).setValue(headRowDes);
		headRow0.getCell(keyRent).setValue(headRowDes);

		IRow headRow1 = tblMain.getHeadRow(1);
		headRow1.getCell(keyRentType).setValue("租金单价");
		headRow1.getCell(keyRent).setValue("租金");
	}
	
	//是否需要增加汇总行
	private boolean isStandardRentSetting()
	{
		//当房间数量大于1并且房产计算方式是租金统一设置的时候才需要增加汇总行,配套资源不进行租金汇总设置
		if(MoreRoomsTypeEnum.StandardRentSetting.equals(newTenBill.getMoreRoomsType())
				&&  newTenBill.getTenancyRoomList().size()>1)
		{
			return true;
		}
		return false;
	}
	
	//锁定非汇总房间行
	private void lockTblRent()
	{
		for(int k=0;k<tblRent.getRowCount();k++)
		{
			IRow row2 = tblRent.getRow(k);
			if(row2.getUserObject() instanceof TenancyRoomEntryInfo)
			{
				for (int i = 0; i < this.tblRent.getColumnCount(); i++) {
					IColumn column = this.tblRent.getColumn(i);
					String colKey = column.getKey();
					row2.getCell(colKey).getStyleAttributes().setLocked(true);
				}
			}
		}
	}
	
	private boolean isDynamicStartDate(RentStartTypeEnum rentStartTypeEnum)
	{
		return RentStartTypeEnum.DynamicStartDate.equals(rentStartTypeEnum);
	}

//	/**
//	 * 根据开始日期、新结束日期和新递增明细生成新租金明细
//	 * */
//	private void reSetRentNewSetInfo(Date startDate, Date endDate, NewIncRentEntryCollection incNewCol) {
//		if (startDate == null || endDate == null) {
//			return;
//		}
//		startDate = FDCDateHelper.getDayBegin(startDate);
//		endDate = FDCDateHelper.getDayBegin(endDate);
//
//		// 重新设置租金分录.
//		Map tmp = parseIncreasedDate(startDate, endDate, incNewCol);
//		if (tmp == null) {
//			return;
//		}
//		TenancyBillInfo tenBill = (TenancyBillInfo) this.prmtTenancy.getValue();
//		try {
//			tenBill = TenancyHelper.getTenancyBillInfo(tenBill.getId().toString());
//		} catch (BOSException ex) {
//			handleException(ex);
//		} catch (EASBizException e) {
//			handleException(e);
//		}
//		if (tenBill == null) {
//			return;
//		}
//		// 根据房间分录和定价时间段生成租赁房间的成交租金分录
//		NewDealAmountEntryCollection dealNewAmounts = new NewDealAmountEntryCollection();
//		for (int i = 0; i < tenBill.getTenancyRoomList().size(); i++) {
//			TenancyRoomEntryInfo tenRoom = tenBill.getTenancyRoomList().get(i);
//			RentTypeEnum currentRentType = tenRoom.getDealRentType();
//			BigDecimal currentRent = null;
//			TenancyModeEnum tenancyModel = tenRoom.getTenancyModel();
//			BigDecimal area = tenRoom.getBuildingArea();
//			if (TenancyModeEnum.RoomAreaMode.equals(tenancyModel)) {
//				area = tenRoom.getRoomArea();
//			}
//			if (area == null) {
//				area = FDCHelper.ZERO;
//			}
//			for (Iterator itor = tmp.keySet().iterator(); itor.hasNext();) {
//				Date[] dates = (Date[]) itor.next();
//				NewIncRentEntryInfo increased = (NewIncRentEntryInfo) tmp.get(dates);
//				boolean isHandwork = true;
//				if (increased == null) {
//					currentRent = tenRoom.getDealRoomRent();
//				} else {
//					IncreasedRentTypeEnum increasedType = increased.getIncreaseType();
//					BigDecimal value = increased.getValue();
//					if (value == null) {// 手输的情况下该值为0
//						value = FDCHelper.ZERO;
//					}
//					if (IncreasedRentTypeEnum.Percent.equals(increasedType)) {
//						currentRent = currentRent.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
//						isHandwork = false;
//					} else if (IncreasedRentTypeEnum.totalRent.equals(increasedType)) {
//						currentRent = currentRent.add(value);
//						isHandwork = false;
//					} else if (IncreasedRentTypeEnum.rentPrice.equals(increasedType)) {
//						currentRent = currentRent.add(value.multiply(area));
//						isHandwork = false;
//					} else if (IncreasedRentTypeEnum.Handwork.equals(increasedType)) {
//						// 手工填写的，默认租金为原租金
//					}
//				}
//				NewDealAmountEntryInfo dealAmount = new NewDealAmountEntryInfo();
//				dealAmount.setIsHandwork(isHandwork);
//				try {
//					dealAmount.setMoneyDefine(getRentMoneyDefine());
//				} catch (BOSException e) {
//					handleException(e);
//				}
//				dealAmount.setStartDate(dates[0]);
//				dealAmount.setEndDate(dates[1]);
//				dealAmount.setRentType(currentRentType);
//				dealAmount.setAmount(currentRent);
//				dealAmount.setTenancyRoom(tenRoom);
//				if (currentRent != null) {
//					BigDecimal buildingArea = tenRoom.getBuildingArea();
//					if (buildingArea != null && buildingArea.compareTo(FDCHelper.ZERO) != 0) {
//						BigDecimal rentPrice = currentRent.divide(buildingArea, 2, BigDecimal.ROUND_HALF_UP);
//						dealAmount.setPriceAmount(rentPrice);
//					}
//				}
//				dealNewAmounts.add(dealAmount);
//			}
//		}
//		// 根据配套资源分录和定价时间段生成配套资源的成交租金分录
//		NewAttachDealAmountEntryCollection attaNewAmounts = new NewAttachDealAmountEntryCollection();
//		for (int i = 0; i < tenBill.getTenAttachResourceList().size(); i++) {
//			TenAttachResourceEntryInfo tenAtta = tenBill.getTenAttachResourceList().get(i);
//			RentTypeEnum currentRentType = tenAtta.getDealRentType();
//			BigDecimal currentRent = null;
//			for (Iterator itor = tmp.keySet().iterator(); itor.hasNext();) {
//				Date[] dates = (Date[]) itor.next();
//				NewIncRentEntryInfo increased = (NewIncRentEntryInfo) tmp.get(dates);
//				boolean isHandwork = true;
//				if (increased == null) {
//					currentRent = tenAtta.getDealAttachResRent();
//				} else {
//					IncreasedRentTypeEnum increasedType = increased.getIncreaseType();
//					BigDecimal value = increased.getValue();
//					if (value == null) {// 手输的情况下该值为0
//						value = FDCHelper.ZERO;
//					}
//					if (IncreasedRentTypeEnum.Percent.equals(increasedType)) {
//						currentRent = currentRent.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
//						isHandwork = false;
//					} else if (IncreasedRentTypeEnum.totalRent.equals(increasedType)) {
//						currentRent = currentRent.add(value);
//						isHandwork = false;
//					} else if (IncreasedRentTypeEnum.Handwork.equals(increasedType)) {
//						// 手工填写的，默认租金为原租金
//					}
//				}
//				NewAttachDealAmountEntryInfo attaAmount = new NewAttachDealAmountEntryInfo();
//				attaAmount.setIsHandwork(isHandwork);
//				try {
//					attaAmount.setMoneyDefine(getRentMoneyDefine());
//				} catch (BOSException e) {
//					handleException(e);
//				}
//				attaAmount.setStartDate(dates[0]);
//				attaAmount.setEndDate(dates[1]);
//				attaAmount.setRentType(currentRentType);
//				attaAmount.setAmount(currentRent);
//				attaAmount.setTenancyAtta(tenAtta);
//				attaNewAmounts.add(attaAmount);
//			}
//		}
//		loadDealAmount(this.tblRent, tenBill.getTenancyRoomList(), dealNewAmounts, tenBill.getTenAttachResourceList(), attaNewAmounts);
//	}

	private MoneyDefineInfo getRentMoneyDefine() throws BOSException {
		if (rentMoneyName == null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.RentAmount));
			view.setFilter(filter);
			MoneyDefineCollection moneyNames = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
			if (!moneyNames.isEmpty()) {
				rentMoneyName = moneyNames.get(0);
			}
		}
		if (rentMoneyName == null) {
			MsgBox.showInfo(this, "请先定义租金类型的款项类型！");
			abort();
		}
		return rentMoneyName;
	}

	/**
	 * 根据起始日期,结束日期以及递增日期.以Map形式将时间分段. 时间分段为key,每个段内的递增信息为value.无递增方案的value为null
	 * */
	private Map parseIncreasedDate(Date startDate, Date endDate, NewIncRentEntryCollection incNewRents) {
		if (startDate == null || endDate == null) {
			return null;
		}

		Date firstPriceSetDate = FDCDateHelper.getDayBegin(startDate);
		Date lastPriceSetDate = FDCDateHelper.getDayBegin(endDate);

		Map increadeDates = new TreeMap();
		for (int i = 0; i < incNewRents.size(); i++) {
			NewIncRentEntryInfo incNewRent = incNewRents.get(i);

			// 上面已经判断过，increasedRent.getIncreaseDate()肯定不为空
			Date increaseDate = FDCDateHelper.getDayBegin(incNewRent.getIncreaseDate());

			if (increaseDate.before(firstPriceSetDate) || increaseDate.after(lastPriceSetDate)) {
				// 递增日期不在租赁日期范围内，舍去该数据。 TODO 是否需要提示数据不对
				continue;
			}
			increadeDates.put(increaseDate, incNewRent);
		}

		// 将定价期分块，分别表示各个时间段的租价
		Map tmp = new LinkedHashMap();
		if (increadeDates.isEmpty()) {
			tmp.put(new Date[] { firstPriceSetDate, lastPriceSetDate }, null);
		} else {
			Set keySet = increadeDates.keySet();
			boolean containsStartDate = keySet.contains(firstPriceSetDate);
			int count = 0;
			Date tmpLastDate = firstPriceSetDate;
			NewIncRentEntryInfo tmpLastIncreasedRent = null;
			for (Iterator itor = keySet.iterator(); itor.hasNext();) {
				Date date = (Date) itor.next();

				Date tmpEndDate = TenancyHelper.addCalendar(date, Calendar.DATE, -1);
				if (!containsStartDate && count == 0) {
					tmp.put(new Date[] { firstPriceSetDate, tmpEndDate }, null);
				} else {
					tmp.put(new Date[] { tmpLastDate, tmpEndDate }, tmpLastIncreasedRent);
				}

				tmpLastIncreasedRent = (NewIncRentEntryInfo) increadeDates.get(date);
				tmpLastDate = date;
				count++;
			}
			tmp.put(new Date[] { tmpLastDate, lastPriceSetDate }, tmpLastIncreasedRent);
		}
		return tmp;
	}

	private boolean valueChanged(KDTEditEvent e) {
		if (e.getOldValue() != null) {
			if (e.getOldValue().equals(e.getValue())) {
				return false;
			}
		} else {
			if (e.getValue() == null) {
				return false;
			}
		}
		return true;
	}

	public void actionFeeAdd_actionPerformed(ActionEvent e) throws Exception {
		super.actionFeeAdd_actionPerformed(e);
	}

	public void actionFeeDel_actionPerformed(ActionEvent e) throws Exception {
		super.actionFeeDel_actionPerformed(e);
	}

	public void actionIncAdd_actionPerformed(ActionEvent e) throws Exception {
		super.actionIncAdd_actionPerformed(e);
	}

	public void actionIncDel_actionPerformed(ActionEvent e) throws Exception {
		super.actionIncDel_actionPerformed(e);
	}

	public void loadFields() {
		EventListener[] listeners = this.prmtTenancy.getListeners(DataChangeListener.class);
		for (int i = 0; i < listeners.length; i++) {
			this.prmtTenancy.removeDataChangeListener((DataChangeListener) listeners[i]);
		}

		EventListener[] newEndDateListeners = this.pkNewEndDate.getListeners(DataChangeListener.class);
		for (int i = 0; i < newEndDateListeners.length; i++) {
			this.pkNewEndDate.removeDataChangeListener((DataChangeListener) newEndDateListeners[i]);
		}

		super.loadFields();
		TenancyModificationInfo info = this.editData;
		if (info.getTenancy() != null) {
			this.txtCustomerDesc.setText(info.getTenancy().getTenCustomerDes());
			this.txtRoomDesc.setText(info.getTenancy().getTenRoomsDes());
			this.txtAttResourceDesc.setText(info.getTenancy().getTenAttachesDes());
			this.pkStartDate.setValue(info.getTenancy().getStartDate());
			this.pkOriEndDate.setValue(info.getTenancy().getEndDate());
		}

		if (info.getOldIncreasedRents().size() > 0) {
			loadOldRentIncreaded(this.tblOriIncrease, info.getOldIncreasedRents());
		}
		if (info.getNewIncreasedRents().size() > 0) {
			loadNewRentIncreaded(this.tblIncrease, info.getNewIncreasedRents());
		}
		if (info.getOldRentFrees().size() > 0) {
			loadOldRentFree(this.tblOriFree, info.getOldRentFrees());
		}
		if (info.getNewRentFrees().size() > 0) {
			loadNewRentFree(this.tblFree, info.getNewRentFrees());
		}

		if (info.getTenancy() != null) {

			TenancyBillInfo tenBillInfo = info.getTenancy();
			
				try {
					tenBillInfo = TenancyHelper.getTenancyBillInfo(tenBillInfo.getId().toString());
				} catch (EASBizException e) {
					handleException(e);
					e.printStackTrace();
				} catch (BOSException e) {
					handleException(e);
					e.printStackTrace();
				}
			
			if (tenBillInfo != null) {
				newTenBill=tenBillInfo;
				TenancyRoomEntryCollection tenRooms = tenBillInfo.getTenancyRoomList();
				TenAttachResourceEntryCollection tenAttas = tenBillInfo.getTenAttachResourceList();
				if (tenRooms.size() > 0) {
					loadDealAmount(this.tblRent, tenRooms, info.getNewDealAmountEntry(), tenAttas, info.getNewAttachDealAmountEntry());
					loadDealAmount(this.tblOriRent, tenRooms, info.getOldDealAmountEntry(), tenAttas, info.getOldAttachDealAmountEntry());
				}
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			this.prmtTenancy.addDataChangeListener((DataChangeListener) listeners[i]);
		}
		for (int i = 0; i < newEndDateListeners.length; i++) {
			this.pkNewEndDate.addDataChangeListener((DataChangeListener) newEndDateListeners[i]);
		}
	}

	private void loadDealAmount(KDTable tbl, TenancyRoomEntryCollection tenRooms, NewDealAmountEntryCollection newDealAmountEntry, TenAttachResourceEntryCollection tenAttas,
			NewAttachDealAmountEntryCollection newAttachDealAmountEntry) {
		tbl.removeRows();
		tbl.checkParsed();
		// 先将租金列删除，再根据设置的递增列表动态生成tblRentSet列
		List toDelColIndexes = new ArrayList();
		for (int i = 0; i < tbl.getColumnCount(); i++) {
			IColumn column = tbl.getColumn(i);
			String colKey = column.getKey();
			if (colKey.startsWith(PREFIX_C_RENT_RENT_PRICE) || colKey.startsWith(PREFIX_C_RENT_RENT) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				toDelColIndexes.add(new Integer(i));
			}
		}
		for (int i = toDelColIndexes.size() - 1; i >= 0; i--) {
			int colIndex = ((Integer) toDelColIndexes.get(i)).intValue();
			tbl.removeColumn(colIndex);
		}
		// 增加第一行时进行动态增加列
		boolean isFirstRow = true;
		for (int i = 0; i < tenRooms.size(); i++) {
			// 增加房间信息
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			IRow row = tbl.addRow();
			row.setUserObject(tenRoom);
			row.getCell(C_RENT_ROOM).setValue(tenRoom.getRoomLongNum());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(tenRoom.getTenancyModel());
			row.getCell(C_RENT_RENT_TYPE).setValue(tenRoom.getDealRentType());
			for(int k=0;k<newDealAmountEntry.size();k++)
			{
				NewDealAmountEntryInfo dealAmount = newDealAmountEntry.get(k);
				String moneyNumber = dealAmount.getMoneyDefine().getNumber();
				{
					for(int j=0;j<monDefineColl.size();j++)
					{
						MoneyDefineInfo moneyInfo = monDefineColl.get(j);
						if(moneyNumber.equals(moneyInfo.getNumber()))
						{
							row.getCell(moneyNumber).setValue(dealAmount.getAmount());
						}					
					}
				}				
			}
//			row.getCell(C_RENT_DEPOSIT).setValue(tenRoom.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenRoom.getFirstPayAmount());
			if (isFirstRow) {
				for (int j = 0; j < newDealAmountEntry.size(); j++) {
					NewDealAmountEntryInfo newDealAmount = newDealAmountEntry.get(j);
					if (newDealAmount.getTenancyRoom().getId().toString().equals(tenRoom.getId().toString())) {
						String moneyName = newDealAmount.getMoneyDefine().getName();
						if (MoneyTypeEnum.DepositAmount.equals(newDealAmount.getMoneyDefine().getMoneyType())) {
							continue;
						}
						String dateDes = getDateDes(moneyName, newDealAmount.getStartDate(), newDealAmount.getEndDate());
						String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
						String keyRent = PREFIX_C_RENT_RENT + dateDes;
						addTblRentSetColumn(tbl, keyRentPrice, keyRent, dateDes);
					}
				}
				isFirstRow = false;
			}
			// 增加房间租价分录
			for (int j = 0; j < newDealAmountEntry.size(); j++) {
				NewDealAmountEntryInfo newDealAmount = newDealAmountEntry.get(j);
				if (newDealAmount.getTenancyRoom().getId().toString().equals(tenRoom.getId().toString())) {
					if(MoneyTypeEnum.DepositAmount.equals(newDealAmount.getMoneyDefine().getMoneyType()))
					{
						continue;
					}
					String moneyName = newDealAmount.getMoneyDefine().getName();
					String dateDes = getDateDes(moneyName, newDealAmount.getStartDate(), newDealAmount.getEndDate());
					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
					String keyRent = PREFIX_C_RENT_RENT + dateDes;
					ICell cellRentPrice = row.getCell(keyRentPrice);
					ICell cellRent = row.getCell(keyRent);
					if (cellRentPrice != null && cellRent != null) {
						cellRentPrice.setValue(newDealAmount.getPriceAmount());
						cellRent.setValue(newDealAmount.getAmount());
						cellRentPrice.setUserObject(newDealAmount);
						cellRent.setUserObject(newDealAmount);

						if (newDealAmount.isIsHandwork()) {
							// 如果不是按总价计算，则锁定总价不可编辑
							if (!TenancyModeEnum.TenancyRentModel.equals(tenRoom.getTenancyModel())) {
								cellRent.getStyleAttributes().setLocked(true);
							} else {
								cellRentPrice.getStyleAttributes().setLocked(true);
							}
						} else {
							cellRentPrice.getStyleAttributes().setLocked(true);
							cellRent.getStyleAttributes().setLocked(true);
						}
					}
				}
			}
		}
		for (int i = 0; i < tenAttas.size(); i++) {
			// 增加附属资源分录
			TenAttachResourceEntryInfo tenAtta = tenAttas.get(i);
			IRow row = tbl.addRow();
			row.setUserObject(tenAtta);
			row.getCell(C_RENT_ROOM).setValue(tenAtta.getAttachLongNum());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(TenancyModeEnum.TenancyRentModel);
			row.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(true);
			row.getCell(C_RENT_RENT_TYPE).setValue(tenAtta.getDealRentType());
//			row.getCell(C_RENT_DEPOSIT).setValue(tenAtta.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenAtta.getFirstPayAmount());
			for (int j = 0; j < newAttachDealAmountEntry.size(); j++) {
				NewAttachDealAmountEntryInfo newAttaAmount = newAttachDealAmountEntry.get(j);
				if (newAttaAmount.getTenancyAtta().getId().toString().equals(tenAtta.getId().toString())) {
					// 增加附属资源租价分录
					String moneyName = newAttaAmount.getMoneyDefine().getName();
					String dateDes = getDateDes(moneyName, newAttaAmount.getStartDate(), newAttaAmount.getEndDate());
					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
					String keyRent = PREFIX_C_RENT_RENT + dateDes;
					ICell cellRentPrice = row.getCell(keyRentPrice);
					ICell cellRent = row.getCell(keyRent);
					if (cellRentPrice != null && cellRent != null) {
						cellRentPrice.setValue(newAttaAmount.getPriceAmount());
						cellRent.setValue(newAttaAmount.getAmount());
						cellRentPrice.setUserObject(newAttaAmount);
						cellRent.setUserObject(newAttaAmount);
						if (newAttaAmount.isIsHandwork()) {
							//如果不是按总价计算，则锁定总价不可编辑
							cellRentPrice.getStyleAttributes().setLocked(true);
						}else
						{
							cellRentPrice.getStyleAttributes().setLocked(true);
							cellRent.getStyleAttributes().setLocked(true);
						}
					}else{
						keyRentPrice = PREFIX_C_RENT_PERIODICITY_PRICE + moneyName;
						keyRent = PREFIX_C_RENT_PERIODICITY + moneyName;
						
						cellRentPrice = row.getCell(keyRentPrice);
						cellRent = row.getCell(keyRent);
						
						if(cellRentPrice != null  &&  cellRent != null){
//							cellRentPrice.setValue(dealAmount.getPriceAmount());
							cellRent.setValue(newAttaAmount.getAmount());
							
							//将 dealAmount对象放在cell中，以便支持tblRentSet的手动设置
							cellRentPrice.setUserObject(newAttaAmount);
							cellRent.setUserObject(newAttaAmount);
							
							//如果不是按总价计算，则锁定总价不可编辑
							cellRentPrice.getStyleAttributes().setLocked(true);
						}
					}
				}
			}
		}
		tbl.getHeadMergeManager().mergeBlock(0, 0, 1, tbl.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);

	}

	private void loadDealAmount(KDTable tbl, TenancyRoomEntryCollection tenRooms, OldDealAmountEntryCollection oldDealAmountEntry, TenAttachResourceEntryCollection tenAttas,
			OldAttachDealAmountEntryCollection oldAttachDealAmountEntry) {

		tbl.removeRows();
		tbl.checkParsed();
		// 先将租金列删除，再根据设置的递增列表动态生成tblRentSet列
		List toDelColIndexes = new ArrayList();
		for (int i = 0; i < tbl.getColumnCount(); i++) {
			IColumn column = tbl.getColumn(i);
			String colKey = column.getKey();
			if (colKey.startsWith(PREFIX_C_RENT_RENT_PRICE) || colKey.startsWith(PREFIX_C_RENT_RENT) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				toDelColIndexes.add(new Integer(i));
			}
		}
		for (int i = toDelColIndexes.size() - 1; i >= 0; i--) {
			int colIndex = ((Integer) toDelColIndexes.get(i)).intValue();
			tbl.removeColumn(colIndex);
		}
		// 增加第一行时进行动态增加列
		boolean isFirstRow = true;
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			IRow row = tbl.addRow();
			row.setUserObject(tenRoom);
			row.getCell(C_RENT_ROOM).setValue(tenRoom.getRoomLongNum());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(tenRoom.getTenancyModel());
			row.getCell(C_RENT_RENT_TYPE).setValue(tenRoom.getDealRentType());
			for(int k=0;k<oldDealAmountEntry.size();k++)
			{
				OldDealAmountEntryInfo dealAmount = oldDealAmountEntry.get(k);
				String moneyNumber = dealAmount.getMoneyDefine().getNumber();
				{
					for(int j=0;j<monDefineColl.size();j++)
					{
						MoneyDefineInfo moneyInfo = monDefineColl.get(j);
						if(moneyNumber.equals(moneyInfo.getNumber()))
						{
							row.getCell(moneyNumber).setValue(dealAmount.getAmount());
						}					
					}
				}				
			}
//			row.getCell(C_RENT_DEPOSIT).setValue(tenRoom.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenRoom.getFirstPayAmount());
			if (isFirstRow) {
				for (int j = 0; j < oldDealAmountEntry.size(); j++) {
					OldDealAmountEntryInfo oldDealAmount = oldDealAmountEntry.get(j);
					if (oldDealAmount.getTenancyRoom().getId().toString().equals(tenRoom.getId().toString())) {
						String moneyName = oldDealAmount.getMoneyDefine().getName();
						if (MoneyTypeEnum.DepositAmount.equals(oldDealAmount.getMoneyDefine().getMoneyType())) {
							continue;
						}
						String dateDes = getDateDes(moneyName, oldDealAmount.getStartDate(), oldDealAmount.getEndDate());
						String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
						String keyRent = PREFIX_C_RENT_RENT + dateDes;
						addTblRentSetColumn(tbl, keyRentPrice, keyRent, dateDes);
					}
				}
				isFirstRow = false;
			}
			for (int j = 0; j < oldDealAmountEntry.size(); j++) {
				OldDealAmountEntryInfo oldDealAmount = oldDealAmountEntry.get(j);
				if (oldDealAmount.getTenancyRoom().getId().toString().equals(tenRoom.getId().toString())) {
					if(MoneyTypeEnum.DepositAmount.equals(oldDealAmount.getMoneyDefine().getMoneyType()))
					{
					   continue;
					}
					String moneyName = oldDealAmount.getMoneyDefine().getName();
					String dateDes = getDateDes(moneyName, oldDealAmount.getStartDate(), oldDealAmount.getEndDate());
					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
					String keyRent = PREFIX_C_RENT_RENT + dateDes;
					ICell cellRentPrice = row.getCell(keyRentPrice);
					ICell cellRent = row.getCell(keyRent);
					if (cellRentPrice != null && cellRent != null) {
						cellRentPrice.setValue(oldDealAmount.getPriceAmount());
						cellRent.setValue(oldDealAmount.getAmount());
						cellRentPrice.setUserObject(oldDealAmount);
						cellRent.setUserObject(oldDealAmount);

						if (oldDealAmount.isIsHandwork()) {
							// 如果不是按总价计算，则锁定总价不可编辑
							if (!TenancyModeEnum.TenancyRentModel.equals(tenRoom.getTenancyModel())) {
								cellRent.getStyleAttributes().setLocked(true);
							} else {
								cellRentPrice.getStyleAttributes().setLocked(true);
							}
						} else {
							cellRentPrice.getStyleAttributes().setLocked(true);
							cellRent.getStyleAttributes().setLocked(true);
						}
					}
				}
			}
		}
		for (int i = 0; i < tenAttas.size(); i++) {
			TenAttachResourceEntryInfo tenAtta = tenAttas.get(i);
			IRow row = tbl.addRow();
			row.setUserObject(tenAtta);
			row.getCell(C_RENT_ROOM).setValue(tenAtta.getAttachLongNum());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(TenancyModeEnum.TenancyRentModel);
			row.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(true);
			row.getCell(C_RENT_RENT_TYPE).setValue(tenAtta.getDealRentType());
//			row.getCell(C_RENT_DEPOSIT).setValue(tenAtta.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenAtta.getFirstPayAmount());
			for (int j = 0; j < oldAttachDealAmountEntry.size(); j++) {
				OldAttachDealAmountEntryInfo oldAttaAmount = oldAttachDealAmountEntry.get(j);
				if (oldAttaAmount.getTenancyAtta().getId().toString().equals(tenAtta.getId().toString())) {
					String moneyName = oldAttaAmount.getMoneyDefine().getName();
					String dateDes = getDateDes(moneyName, oldAttaAmount.getStartDate(), oldAttaAmount.getEndDate());
					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
					String keyRent = PREFIX_C_RENT_RENT + dateDes;
					ICell cellRentPrice = row.getCell(keyRentPrice);
					ICell cellRent = row.getCell(keyRent);
					if (cellRentPrice != null && cellRent != null) {
						cellRentPrice.setValue(oldAttaAmount.getPriceAmount());
						cellRent.setValue(oldAttaAmount.getAmount());
						cellRentPrice.setUserObject(oldAttaAmount);
						cellRent.setUserObject(oldAttaAmount);
						if (oldAttaAmount.isIsHandwork()) {
							cellRentPrice.getStyleAttributes().setLocked(true);
							cellRent.getStyleAttributes().setLocked(true);
						}
					}
				}
			}
		}
		tbl.getHeadMergeManager().mergeBlock(0, 0, 1, tbl.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);
	}

	private void addTblRentSetColumn(KDTable tbl, String keyRentType, String keyRent, String headRowDes) {
		IColumn col = tbl.addColumn();
		col.setKey(keyRentType);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		col = tbl.addColumn();
		col.setKey(keyRent);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		IRow headRow0 = tbl.getHeadRow(0);
		headRow0.getCell(keyRentType).setValue(headRowDes);
		headRow0.getCell(keyRent).setValue(headRowDes);

		IRow headRow1 = tbl.getHeadRow(1);
		headRow1.getCell(keyRentType).setValue("租金单价");
		headRow1.getCell(keyRent).setValue("租金");
	}

	/**
	 * 加载新免租分录
	 * */
	private void loadNewRentFree(KDTable tbl, NewRentFreeEntryCollection newRentfrees) {
		tbl.removeRows();
		tbl.checkParsed();
		for (int i = 0; i < newRentfrees.size(); i++) {
			NewRentFreeEntryInfo newRentfree = (NewRentFreeEntryInfo) newRentfrees.get(i);
			IRow row = tbl.addRow();
			row.setUserObject(newRentfree);
			row.getCell(C_FREE_START_DATE).setValue(newRentfree.getFreeStartDate());
			row.getCell(C_FREE_END_DATE).setValue(newRentfree.getFreeEndDate());
			row.getCell(C_FREE_TENANCY_TYPE).setValue(newRentfree.getFreeTenancyType());
			row.getCell(C_FREE_DES).setValue(newRentfree.getDescription());
		}
	}

	/**
	 * 加载合同免租分录
	 * */
	private void loadTenBillRentFree(KDTable tbl, RentFreeEntryCollection tenBillfrees) {
		tbl.removeRows();
		tbl.checkParsed();
		for (int i = 0; i < tenBillfrees.size(); i++) {
			RentFreeEntryInfo tenBillfree = (RentFreeEntryInfo) tenBillfrees.get(i);
			IRow row = tbl.addRow();
			row.setUserObject(tenBillfree);
			row.getCell(C_FREE_START_DATE).setValue(tenBillfree.getFreeStartDate());
			row.getCell(C_FREE_END_DATE).setValue(tenBillfree.getFreeEndDate());
			row.getCell(C_FREE_TENANCY_TYPE).setValue(tenBillfree.getFreeTenancyType());
			row.getCell(C_FREE_DES).setValue(tenBillfree.getDescription());
		}
	}

	/**
	 * 加载原免租分录
	 * */
	private void loadOldRentFree(KDTable tbl, OldRentFreeEntryCollection oldfrees) {
		tbl.removeRows();
		tbl.checkParsed();
		for (int i = 0; i < oldfrees.size(); i++) {
			OldRentFreeEntryInfo oldfree = (OldRentFreeEntryInfo) oldfrees.get(i);
			IRow row = tbl.addRow();
			row.setUserObject(oldfree);
			row.getCell(C_FREE_START_DATE).setValue(oldfree.getFreeStartDate());
			row.getCell(C_FREE_END_DATE).setValue(oldfree.getFreeEndDate());
			row.getCell(C_FREE_TENANCY_TYPE).setValue(oldfree.getFreeTenancyType());
			row.getCell(C_FREE_DES).setValue(oldfree.getDescription());
		}
	}

	/**
	 * 加载新租金递增分录
	 * */
	private void loadNewRentIncreaded(KDTable tbl, NewIncRentEntryCollection newIncRents) {
		tbl.removeRows();
		tbl.checkParsed();
		for (int i = 0; i < newIncRents.size(); i++) {
			NewIncRentEntryInfo newIncRent = (NewIncRentEntryInfo) newIncRents.get(i);
			IRow row = tbl.addRow();
			row.setUserObject(newIncRent);
			row.getCell(C_INC_INCREASE_DATE).setValue(newIncRent.getIncreaseDate());
			row.getCell(C_INC_INCREASE_TYPE).setValue(newIncRent.getIncreaseType());
			row.getCell(C_INC_INCREASESTYLE).setValue(newIncRent.getIncreaseStyle());
			row.getCell(C_INC_VALUE).setValue(newIncRent.getValue());
		}
	}

	/**
	 * 加载合同租金递增分录
	 * */
	private void loadTenBillRentIncreaded(KDTable tbl, IncreasedRentEntryCollection TenBillIncRents) {
		tbl.removeRows();
		tbl.checkParsed();

		for (int i = 0; i < TenBillIncRents.size(); i++) {
			IncreasedRentEntryInfo TenBillIncRent = (IncreasedRentEntryInfo) TenBillIncRents.get(i);
			IRow row = tbl.addRow();
			row.setUserObject(TenBillIncRent);
			row.getCell(C_INC_INCREASE_DATE).setValue(TenBillIncRent.getIncreaseDate());
			row.getCell(C_INC_INCREASE_TYPE).setValue(TenBillIncRent.getIncreaseType());
			row.getCell(C_INC_INCREASESTYLE).setValue(TenBillIncRent.getIncreaseStyle());
			row.getCell(C_INC_VALUE).setValue(TenBillIncRent.getValue());
		}
	}

	/**
	 * 加载原租金递增分录
	 * */
	private void loadOldRentIncreaded(KDTable tbl, OldIncRentEntryCollection oldIncRents) {
		tbl.removeRows();
		tbl.checkParsed();
		for (int i = 0; i < oldIncRents.size(); i++) {
			OldIncRentEntryInfo oldIncRent = (OldIncRentEntryInfo) oldIncRents.get(i);
			IRow row = tbl.addRow();
			row.setUserObject(oldIncRent);
			row.getCell(C_INC_INCREASE_DATE).setValue(oldIncRent.getIncreaseDate());
			row.getCell(C_INC_INCREASE_TYPE).setValue(oldIncRent.getIncreaseType());
			row.getCell(C_INC_INCREASESTYLE).setValue(oldIncRent.getIncreaseStyle());
			row.getCell(C_INC_VALUE).setValue(oldIncRent.getValue());
		}
	}

	protected IObjectValue createNewData() {
		TenancyModificationInfo info = new TenancyModificationInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		info.setBizDate(new Date());
		info.setSellProject(sellProject);
		TenancyBillInfo tenancy = info.getTenancy();
		if (tenancy != null) {
			setModificationByTenancyBill(info, tenancy);
		}
//		initRentSetTable();
		return info;
	}

	/**
	 * 根据租赁合同设置变更单的值
	 * */
	private void setModificationByTenancyBill(TenancyModificationInfo info, TenancyBillInfo tenancy) {

		info.setTenancy(tenancy);
		// 租赁合同房间租价成交分录和合同变更房间租价成交分录
		OldDealAmountEntryCollection oldDealAmounts = new OldDealAmountEntryCollection();
		NewDealAmountEntryCollection newDealAmounts = new NewDealAmountEntryCollection();
		TenancyRoomEntryCollection tenRooms = tenancy.getTenancyRoomList();
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			DealAmountEntryCollection dealAmounts = tenRoom.getDealAmounts();
			for (int j = 0; j < dealAmounts.size(); j++) {
				DealAmountEntryInfo dealAmount = dealAmounts.get(j);
				OldDealAmountEntryInfo oldDealAmount = new OldDealAmountEntryInfo();
				NewDealAmountEntryInfo newDealAmount = new NewDealAmountEntryInfo();
				oldDealAmount.setStartDate(dealAmount.getStartDate());
				oldDealAmount.setEndDate(dealAmount.getEndDate());
				oldDealAmount.setAmount(dealAmount.getAmount());
				oldDealAmount.setPriceAmount(dealAmount.getPriceAmount());
				oldDealAmount.setRentType(dealAmount.getRentType());
				oldDealAmount.setIsHandwork(dealAmount.isIsHandwork());
				oldDealAmount.setTenancyRoom(dealAmount.getTenancyRoom());
				oldDealAmount.setMoneyDefine(dealAmount.getMoneyDefine());
				oldDealAmounts.add(oldDealAmount);
				newDealAmount.setStartDate(dealAmount.getStartDate());
				newDealAmount.setEndDate(dealAmount.getEndDate());
				newDealAmount.setAmount(dealAmount.getAmount());
				newDealAmount.setPriceAmount(dealAmount.getPriceAmount());
				newDealAmount.setRentType(dealAmount.getRentType());
				newDealAmount.setIsHandwork(dealAmount.isIsHandwork());
				newDealAmount.setTenancyRoom(dealAmount.getTenancyRoom());
				newDealAmount.setMoneyDefine(dealAmount.getMoneyDefine());
				newDealAmounts.add(newDealAmount);
			}
		}
		info.getOldDealAmountEntry().clear();
		info.getOldDealAmountEntry().addCollection(oldDealAmounts);
		info.getNewDealAmountEntry().clear();
		info.getNewDealAmountEntry().addCollection(newDealAmounts);
		// 租赁合同配套租价成交分录和合同变更配套租价成交分录
		OldAttachDealAmountEntryCollection oldAttaAmounts = new OldAttachDealAmountEntryCollection();
		NewAttachDealAmountEntryCollection newAttaAmounts = new NewAttachDealAmountEntryCollection();
		TenAttachResourceEntryCollection tenAttas = tenancy.getTenAttachResourceList();
		for (int i = 0; i < tenAttas.size(); i++) {
			TenAttachResourceEntryInfo tenAtta = tenAttas.get(i);
			AttachDealAmountEntryCollection tenDealAmouts = tenAtta.getDealAmounts();
			for (int j = 0; j < tenDealAmouts.size(); j++) {
				AttachDealAmountEntryInfo tenDealAmout = tenDealAmouts.get(j);
				OldAttachDealAmountEntryInfo oldAttaDealAmount = new OldAttachDealAmountEntryInfo();
				NewAttachDealAmountEntryInfo newAttaDealAmount = new NewAttachDealAmountEntryInfo();
				oldAttaDealAmount.setStartDate(tenDealAmout.getStartDate());
				oldAttaDealAmount.setStartDate(tenDealAmout.getStartDate());
				oldAttaDealAmount.setEndDate(tenDealAmout.getEndDate());
				oldAttaDealAmount.setAmount(tenDealAmout.getAmount());
				oldAttaDealAmount.setPriceAmount(tenDealAmout.getPriceAmount());
				oldAttaDealAmount.setRentType(tenDealAmout.getRentType());
				oldAttaDealAmount.setIsHandwork(tenDealAmout.isIsHandwork());
				oldAttaDealAmount.setTenancyAtta(tenDealAmout.getTenancyAttach());
				oldAttaDealAmount.setMoneyDefine(tenDealAmout.getMoneyDefine());
				oldAttaAmounts.add(oldAttaDealAmount);
				newAttaDealAmount.setStartDate(tenDealAmout.getStartDate());
				newAttaDealAmount.setStartDate(tenDealAmout.getStartDate());
				newAttaDealAmount.setEndDate(tenDealAmout.getEndDate());
				newAttaDealAmount.setAmount(tenDealAmout.getAmount());
				newAttaDealAmount.setPriceAmount(tenDealAmout.getPriceAmount());
				newAttaDealAmount.setRentType(tenDealAmout.getRentType());
				newAttaDealAmount.setIsHandwork(tenDealAmout.isIsHandwork());
				newAttaDealAmount.setTenancyAtta(tenDealAmout.getTenancyAttach());
				newAttaDealAmount.setMoneyDefine(tenDealAmout.getMoneyDefine());
				newAttaAmounts.add(newAttaDealAmount);
			}
		}
		info.getOldDealAmountEntry().clear();
		info.getOldDealAmountEntry().addCollection(oldDealAmounts);
		info.getNewDealAmountEntry().clear();
		info.getNewDealAmountEntry().addCollection(newDealAmounts);
		info.getOldAttachDealAmountEntry().clear();
		info.getOldAttachDealAmountEntry().addCollection(oldAttaAmounts);
		info.getNewAttachDealAmountEntry().clear();
		info.getNewAttachDealAmountEntry().addCollection(newAttaAmounts);
		// 合同递增分录和合同变更递增分录
		NewIncRentEntryCollection newIncRents = new NewIncRentEntryCollection();
		OldIncRentEntryCollection oldIncRents = new OldIncRentEntryCollection();
		IncreasedRentEntryCollection incRents = tenancy.getIncreasedRents();
		for (int j = 0; j < incRents.size(); j++) {
			IncreasedRentEntryInfo incRent = incRents.get(j);
			NewIncRentEntryInfo newIncRent = new NewIncRentEntryInfo();
			OldIncRentEntryInfo oldIncRent = new OldIncRentEntryInfo();
			newIncRent.setIncreaseDate(incRent.getIncreaseDate());
			oldIncRent.setIncreaseDate(incRent.getIncreaseDate());
			newIncRent.setIncreaseType(incRent.getIncreaseType());
			oldIncRent.setIncreaseType(incRent.getIncreaseType());
			newIncRent.setValue(incRent.getValue());
			oldIncRent.setValue(incRent.getValue());
			newIncRents.add(newIncRent);
			oldIncRents.add(oldIncRent);
		}
		info.getOldIncreasedRents().clear();
		info.getOldIncreasedRents().addCollection(oldIncRents);
		info.getNewIncreasedRents().clear();
		info.getNewIncreasedRents().addCollection(newIncRents);
		// 合同免租分录和合同变更免租分录
		NewRentFreeEntryCollection newRentFrees = new NewRentFreeEntryCollection();
		OldRentFreeEntryCollection oldRentFrees = new OldRentFreeEntryCollection();
		RentFreeEntryCollection rentFrees = tenancy.getRentFrees();
		for (int j = 0; j < rentFrees.size(); j++) {
			RentFreeEntryInfo rentFree = rentFrees.get(j);
			NewRentFreeEntryInfo newRentFree = new NewRentFreeEntryInfo();
			OldRentFreeEntryInfo oldRentFree = new OldRentFreeEntryInfo();
			newRentFree.setFreeStartDate(rentFree.getFreeStartDate());
			oldRentFree.setFreeStartDate(rentFree.getFreeStartDate());
			newRentFree.setFreeEndDate(rentFree.getFreeEndDate());
			oldRentFree.setFreeEndDate(rentFree.getFreeEndDate());
			newRentFree.setDescription(rentFree.getDescription());
			oldRentFree.setDescription(rentFree.getDescription());
			newRentFrees.add(newRentFree);
			oldRentFrees.add(oldRentFree);
		}
		info.getNewRentFrees().clear();
		info.getNewRentFrees().addCollection(newRentFrees);
		info.getOldRentFrees().clear();
		info.getOldRentFrees().addCollection(oldRentFrees);
	}

	private void initRentSetTable(){

		this.txtCustomerDesc.setEnabled(false);
		this.txtRoomDesc.setEnabled(false);
		this.txtAttResourceDesc.setEnabled(false);
		this.pkStartDate.setEnabled(false);
		this.pkOriEndDate.setEnabled(false);

		this.txtCustomerDesc.setText(null);
		this.txtRoomDesc.setText(null);
		this.txtAttResourceDesc.setText(null);
		this.pkStartDate.setValue(null);
		this.pkOriEndDate.setValue(null);

		this.tblOriFree.removeRows();
		this.tblOriIncrease.removeRows();
		this.tblOriRent.removeRows();

		this.tblFree.removeRows();
		this.tblIncrease.removeRows();
		this.tblRent.removeRows();

		this.tblIncrease.checkParsed();
		this.tblFree.checkParsed();
		this.tblRent.checkParsed();
		this.tblOriRent.checkParsed();

		this.tblIncrease.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblFree.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblRent.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblOriRent.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("moneyType", "DepositAmount"));
		filter.getFilterItems().add(new FilterItemInfo("sysType", "TenancySys"));
		view.setFilter(filter);
		try {
			monDefineColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
		} catch (BOSException e2) {
			e2.printStackTrace();
		}
		if(monDefineColl.size()>1)
		{
			for(int i=0;i<monDefineColl.size();i++)
			{					
				MoneyDefineInfo moneyInfo = monDefineColl.get(i);
				if(i==0)
				{
					IColumn columnMoney = this.tblRent.getColumn("deposit");
					columnMoney.setKey(moneyInfo.getNumber());
					IRow headRow0 = this.tblRent.getHeadRow(0);
					IRow headRow1 = this.tblRent.getHeadRow(1);
					headRow0.getCell(moneyInfo.getNumber()).setValue("押金");
					headRow1.getCell(moneyInfo.getNumber()).setValue(moneyInfo);
				}else
				{
					IColumn columnMoney = this.tblRent.addColumn(2);
					columnMoney.setKey(moneyInfo.getNumber());
					IRow headRow0 = this.tblRent.getHeadRow(0);
					IRow headRow1 = this.tblRent.getHeadRow(1);
					headRow0.getCell(moneyInfo.getNumber()).setValue("押金");
					headRow1.getCell(moneyInfo.getNumber()).setValue(moneyInfo);
				}											
			}
		}else if(monDefineColl.size()==1)
		{
			IColumn columnMoney = this.tblRent.getColumn("deposit");
			columnMoney.setKey(((MoneyDefineInfo)monDefineColl.getObject(0)).getNumber());
		}
		
		this.tblRent.getHeadMergeManager().mergeBlock(0, 0, 1, this.tblRent.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);
		for(int i=0;i<monDefineColl.size();i++)
		{
			MoneyDefineInfo moneyInfo = monDefineColl.get(i);
			this.tblRent.getColumn(moneyInfo.getNumber()).setEditor(createFormattedCellEditor());
			this.tblRent.getColumn(moneyInfo.getNumber()).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			this.tblRent.getColumn(moneyInfo.getNumber()).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		}
//		this.tblRent.getColumn(C_RENT_DEPOSIT).setEditor(createFormattedCellEditor());
//		this.tblRent.getColumn(C_RENT_DEPOSIT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblRent.getColumn(C_RENT_DEPOSIT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRent.getColumn(C_RENT_FIRST_RENT).setEditor(createFormattedCellEditor());
		this.tblRent.getColumn(C_RENT_FIRST_RENT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRent.getColumn(C_RENT_FIRST_RENT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRent.getColumn(C_RENT_TENANCY_MODEL).setEditor(createComboCellEditor(TenancyModeEnum.getEnumList()));
		this.tblRent.getColumn(C_RENT_RENT_TYPE).setEditor(createComboCellEditor(RentTypeEnum.getEnumList()));

		if(monDefineColl.size()>1)
		{
			for(int i=0;i<monDefineColl.size();i++)
			{					
				MoneyDefineInfo moneyInfo = monDefineColl.get(i);
				if(i==0)
				{
					IColumn columnMoney = this.tblOriRent.getColumn("deposit");
					columnMoney.setKey(moneyInfo.getNumber());
					IRow headRow0 = this.tblOriRent.getHeadRow(0);
					IRow headRow1 = this.tblOriRent.getHeadRow(1);
					headRow0.getCell(moneyInfo.getNumber()).setValue("押金");
					headRow1.getCell(moneyInfo.getNumber()).setValue(moneyInfo);
				}else
				{
					IColumn columnMoney = this.tblOriRent.addColumn(2);
					columnMoney.setKey(moneyInfo.getNumber());
					IRow headRow0 = this.tblOriRent.getHeadRow(0);
					IRow headRow1 = this.tblOriRent.getHeadRow(1);
					headRow0.getCell(moneyInfo.getNumber()).setValue("押金");
					headRow1.getCell(moneyInfo.getNumber()).setValue(moneyInfo);
				}											
			}
		}else if(monDefineColl.size()==1)
		{
			IColumn columnMoney = this.tblOriRent.getColumn("deposit");
			columnMoney.setKey(((MoneyDefineInfo)monDefineColl.getObject(0)).getNumber());
		}
		
		this.tblOriRent.getHeadMergeManager().mergeBlock(0, 0, 1, this.tblOriRent.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);
		this.tblOriRent.getStyleAttributes().setLocked(true);
		this.tblOriFree.getStyleAttributes().setLocked(true);
		this.tblOriIncrease.getStyleAttributes().setLocked(true);
		for(int i=0;i<monDefineColl.size();i++)
		{
			MoneyDefineInfo moneyInfo = monDefineColl.get(i);
			this.tblOriRent.getColumn(moneyInfo.getNumber()).setEditor(createFormattedCellEditor());
			this.tblOriRent.getColumn(moneyInfo.getNumber()).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			this.tblOriRent.getColumn(moneyInfo.getNumber()).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		}

		this.tblIncrease.getColumn(C_INC_INCREASE_DATE).setEditor(createDateCellEditor());
		this.tblIncrease.getColumn(C_INC_INCREASE_DATE).getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.tblIncrease.getColumn(C_INC_INCREASE_TYPE).setEditor(createComboCellEditor(IncreasedRentTypeEnum.getEnumList()));
		this.tblIncrease.getColumn(C_INC_INCREASESTYLE).setEditor(createComboCellEditor(IncreaseStyleEnum.getEnumList()));
		this.tblIncrease.getColumn(C_INC_VALUE).setEditor(createFormattedCellEditor());
		this.tblIncrease.getColumn(C_INC_VALUE).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblIncrease.getColumn(C_INC_VALUE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblFree.getColumn(C_FREE_START_DATE).setEditor(createDateCellEditor());
		this.tblFree.getColumn(C_FREE_START_DATE).getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.tblFree.getColumn(C_FREE_END_DATE).setEditor(createDateCellEditor());
		this.tblFree.getColumn(C_FREE_END_DATE).getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.tblFree.getColumn(C_FREE_TENANCY_TYPE).setEditor(createComboCellEditor(FreeTenancyTypeEnum.getEnumList()));
		this.tblFree.getColumn(C_FREE_DES).setEditor(createTxtCellEditor(255, true));

		// 删除原租金明细
		List toDelColIndexes = new ArrayList();
		for (int i = 0; i < this.tblOriRent.getColumnCount(); i++) {
			IColumn column = this.tblOriRent.getColumn(i);
			String colKey = column.getKey();
			if (colKey.startsWith(PREFIX_C_RENT_RENT_PRICE) || colKey.startsWith(PREFIX_C_RENT_RENT) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				toDelColIndexes.add(new Integer(i));
			}
		}
		for (int i = toDelColIndexes.size() - 1; i >= 0; i--) {
			int colIndex = ((Integer) toDelColIndexes.get(i)).intValue();
			this.tblOriRent.removeColumn(colIndex);
		}

		// 删除新租金明细
		toDelColIndexes.clear();
		for (int i = 0; i < this.tblRent.getColumnCount(); i++) {
			IColumn column = this.tblRent.getColumn(i);
			String colKey = column.getKey();
			if (colKey.startsWith(PREFIX_C_RENT_RENT_PRICE) || colKey.startsWith(PREFIX_C_RENT_RENT) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				toDelColIndexes.add(new Integer(i));
			}
		}
		for (int i = toDelColIndexes.size() - 1; i >= 0; i--) {
			int colIndex = ((Integer) toDelColIndexes.get(i)).intValue();
			this.tblRent.removeColumn(colIndex);
		}

		ItemAction actionAddIncrease = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddIncrease_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};
		ItemAction actionRmIncrease = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRmIncrease_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};
		ItemAction actionAddFree = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddFree_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};
		ItemAction actionRmFree = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRmFree_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};

		btnAddIncrease = initWorkBtn1(actionAddIncrease, "imgTbtn_sortstandard", this.containerIncrease, "添加");
		btnRmIncrease = initWorkBtn1(actionRmIncrease, "imgTbtn_sortstandard", this.containerIncrease, "删除");
		btnAddFree = initWorkBtn1(actionAddFree, "imgTbtn_sortstandard", this.containerFree, "添加");
		btnRmFree = initWorkBtn1(actionRmFree, "imgTbtn_sortstandard", this.containerFree, "删除");
	}

	protected void setPrmtTenancyView(TenancyModificationInfo tenModifiInfo) throws Exception {
		/**
		 * 先找出提交状态下的租赁减免单的合同编号集tenBillIds
		 * */
		String tenBillIds = "null";
		EntityViewInfo rentRemisView = new EntityViewInfo();
		FilterInfo rentRemisFilter = new FilterInfo();
		RentRemissionCollection rentRemisCol = new RentRemissionCollection();
		rentRemisFilter.getFilterItems().add(new FilterItemInfo("state", TenancyBillStateEnum.SUBMITTED_VALUE));
		rentRemisView.setFilter(rentRemisFilter);
		try {
			rentRemisCol = RentRemissionFactory.getRemoteInstance().getRentRemissionCollection(rentRemisView);
		} catch (UIException ex) {
			handleException(ex);
		}
		if (rentRemisCol.size() > 0) {
			for (int i = 0; i < rentRemisCol.size(); i++) {
				RentRemissionInfo info = (RentRemissionInfo) rentRemisCol.get(i);
				if (info != null) {
					TenancyBillInfo tenBillInfo = (TenancyBillInfo) info.getTenancy();
					if (tenBillInfo != null) {
						tenBillIds = tenBillIds + "," + tenBillInfo.getId().toString();
					}
				}
			}
		}
		/***
		 * 过滤执行状态下和部分执行状态下的合同，再去除tenBillIds
		 * */
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.EXECUTING_VALUE));
		// filter.getFilterItems().add(new FilterItemInfo("tenancyState",
		// TenancyBillStateEnum.PARTEXECUTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.AUDITED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("id", tenBillIds, CompareType.NOTINCLUDE));
		SellProjectInfo sp = tenModifiInfo.getSellProject();
		filter.setMaskString(" (#0 OR #1) AND #2 ");
		if (sp != null) {
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sp.getId().toString()));
			filter.setMaskString(" (#0 OR #1) AND #2 AND #3 ");
		}
		view.setFilter(filter);
		this.prmtTenancy.setEntityViewInfo(view);
	}

	private KDTDefaultCellEditor createTxtCellEditor(int length, boolean editable) {
		return TenancyClientHelper.createTxtCellEditor(length, editable);
	}

	private KDTDefaultCellEditor createComboCellEditor(List enumList) {
		return TenancyClientHelper.createComboCellEditor(enumList);
	}

	private ICellEditor createFormattedCellEditor() {
		return TenancyClientHelper.createFormattedCellEditor(true);
	}

	private ICellEditor createFormattedCellEditor(boolean editable) {
		return TenancyClientHelper.createFormattedCellEditor(editable);
	}

	private KDTDefaultCellEditor createDateCellEditor() {
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		return dateEditor;
	}

	private KDWorkButton initWorkBtn1(Action action, String iconName, KDContainer parentContainer, String text) {
		action.putValue(Action.SMALL_ICON, EASResource.getIcon(iconName));
		KDWorkButton btn = new KDWorkButton();
		btn = (KDWorkButton) parentContainer.add(action);
		btn.setToolTipText(text);
		btn.setText(text);
		return btn;
	}

	private void actionAddIncrease_actionPerformed(ActionEvent e) {
		IRow row = this.tblIncrease.addRow();
		row.getCell("increaseType").setValue(IncreasedRentTypeEnum.Percent);
	}

	private void actionRmIncrease_actionPerformed(ActionEvent e) throws BOSException, EASBizException {
		int activeRowIndex = this.tblIncrease.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblIncrease.getRowCount();
		}
		this.tblIncrease.removeRow(activeRowIndex);

		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkNewEndDate.getValue();
		NewIncRentEntryCollection incNewCol = new NewIncRentEntryCollection();
		for (int i = 0; i < this.tblIncrease.getRowCount(); i++) {
			IRow rowInc = this.tblIncrease.getRow(i);

			Date increaseDate = (Date) rowInc.getCell(C_INC_INCREASE_DATE).getValue();
			if (increaseDate == null) {
				continue;
			}
			IncreasedRentTypeEnum increasedRentType = (IncreasedRentTypeEnum) rowInc.getCell(C_INC_INCREASE_TYPE).getValue();
			BigDecimal value = (BigDecimal) rowInc.getCell(C_INC_VALUE).getValue();
			if (!IncreasedRentTypeEnum.Handwork.equals(increasedRentType) && value == null) {
				continue;
			}
			NewIncRentEntryInfo incNewInfo = new NewIncRentEntryInfo();
			incNewInfo.setIncreaseDate((Date) rowInc.getCell(C_INC_INCREASE_DATE).getValue());
			incNewInfo.setIncreaseType((IncreasedRentTypeEnum) rowInc.getCell(C_INC_INCREASE_TYPE).getValue());
			incNewInfo.setValue(FDCHelper.toBigDecimal(rowInc.getCell(C_INC_VALUE).getValue()));
			incNewCol.add(incNewInfo);
		}
		reSetRentNewSetInfo(getTenRoomListFromView(),getTenAttachResListFromView());
		reSetRentSetInfo(getTenRoomListFromView(), getTenAttachResListFromView(),false);
	}

	private void actionAddFree_actionPerformed(ActionEvent e) {
		IRow row=tblFree.addRow();
		row.getCell(C_FREE_TENANCY_TYPE).setValue(FreeTenancyTypeEnum.FreeTenNotMoney);
	}

	private void actionRmFree_actionPerformed(ActionEvent e) throws EASBizException, BOSException {
		int activeRowIndex = this.tblFree.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblFree.getRowCount();
		}
		this.tblFree.removeRow(activeRowIndex);
	}

	public void onLoad() throws Exception {
		oldState = this.oprtState;
		initRentSetTable();
		super.onLoad();
		TenancyModificationInfo tenModifiInfo = (TenancyModificationInfo) this.editData;
		this.actionAudit.setEnabled(true);
		this.actionAudit.setVisible(true);
		this.menuItemAudit.setEnabled(true);
		this.menuItemAudit.setVisible(true);
		this.btnAttachment.setEnabled(false);
		this.btnAttachment.setVisible(true);
		this.btnUnAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.prmtTenancy.setEditable(false);
		intControl(tenModifiInfo);
		
		if (!this.getOprtState().equals(OprtState.ADDNEW)) {			
			if (this.getOprtState().equals(OprtState.VIEW)) {
				this.actionAudit.setEnabled(false);
				this.actionAudit.setVisible(false);
				this.menuItemAudit.setEnabled(false);
				this.menuItemAudit.setVisible(false);
				this.btnSubmit.setEnabled(false);
				this.btnSave.setEnabled(false);
				this.actionSubmit.setEnabled(false);
				this.actionSave.setEnabled(false);


			}

			setPrmtTenancyView(tenModifiInfo);
			this.loadFields();
		} else {
			setPrmtTenancyView(tenModifiInfo);

		}
		
		if(!getOprtState().equals(this.STATUS_ADDNEW)){
			TenancyBillInfo enBill=(TenancyBillInfo) prmtTenancy.getValue();
			try {
				newTenBill=TenancyHelper.getTenancyBillInfo(enBill.getId().toString());
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		txtModificationReason.setRequired(true);
		
	}
	
	private void isTenancyBill(Object o){
		TenancyBillInfo billInfo  = (TenancyBillInfo)o;
	}
	
	protected void initDataStatus() {
		super.initDataStatus();
		
		TenancyModificationInfo info = (TenancyModificationInfo) getDataObject();
		
		
		if(OprtState.VIEW.equals(getOprtState())){
			this.btnAddIncrease.setEnabled(false);
			this.btnRmIncrease.setEnabled(false);
			this.btnAddFree.setEnabled(false);
			this.btnRmFree.setEnabled(false);
		}else if(OprtState.EDIT.equals(getOprtState())){
			
			boolean isCanEditOrRemove = isCanEditOrRemove(info);
			boolean isChkIsFreeContract=info.isNewIsFreeContract();
			
			this.btnAddIncrease.setEnabled(isCanEditOrRemove && !isChkIsFreeContract);
			this.btnRmIncrease.setEnabled(isCanEditOrRemove&& !isChkIsFreeContract);
			this.btnAddFree.setEnabled(isCanEditOrRemove&& !isChkIsFreeContract);
			this.btnRmFree.setEnabled(isCanEditOrRemove&& !isChkIsFreeContract);
		}else if(OprtState.ADDNEW.equals(getOprtState())){
			this.btnAddIncrease.setEnabled(true);
			this.btnRmIncrease.setEnabled(true);
			this.btnAddFree.setEnabled(true);
			this.btnRmFree.setEnabled(true);
		}
	}
	
	private boolean isCanEditOrRemove(TenancyModificationInfo info) {
		FDCBillStateEnum state = info.getState();
		return !(state != null && (state == FDCBillStateEnum.AUDITTING
				|| state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL));
	}

	private void intControl(TenancyModificationInfo tenModifiInfo) {
		if (tenModifiInfo != null) {
			FDCBillStateEnum tenModifiState = tenModifiInfo.getState();
			if (FDCBillStateEnum.AUDITTED.equals(tenModifiState)) {
				this.btnAudit.setEnabled(false);
				this.btnSave.setEnabled(false);
				this.btnSubmit.setEnabled(false);
			} else if (FDCBillStateEnum.SAVED.equals(tenModifiState)) {
				this.actionAudit.setEnabled(true);
				this.btnAudit.setEnabled(true);
				this.btnSave.setEnabled(true);
				this.btnSubmit.setEnabled(true);
			} else if (FDCBillStateEnum.SUBMITTED.equals(tenModifiState)) {
				this.actionAudit.setEnabled(true);
				this.btnAudit.setEnabled(true);
				this.btnSave.setEnabled(false);
				this.btnSubmit.setEnabled(true);
			}
		}

	}

	/*
	 * 选择租赁合同时，或许租赁合同的信息到合同变更界面，包括租赁合同、客户、房间、配套资源、开始日期、原结束日期 原递增明细、原免租明细和原租金明细。
	 * 选择后初始化新递增明细、新免租明细和新租金明细 yaowei_wen 2009-07-17
	 */
	protected void prmtTenancy_dataChanged(DataChangeEvent e) throws Exception {
		// super.prmtTenancy_dataChanged(e);
		TenancyBillInfo tenBill = (TenancyBillInfo) e.getNewValue();
		
		/**在新增的合同变更单据中选择租赁合同时，判断所选合同下是否存在未审批通过的合同变更单据（单据状态不为“已审批”），
		 * 如果存在则不允许选择，并提示“该合同下存在【变更单据状态】的合同变更单据，不允许选择！”
		 * xiaoao_liu
		 */
		//如果合同被删除，所有合同带出来信息重置
		if(tenBill==null){
			txtCustomerDesc.setText(null);
			txtRoomDesc.setText(null);
			tblOriRent.removeRows(false);
			tblRent.removeRows(false);
			pkStartDate.setValue(null);
			pkOriEndDate.setValue(null);
			pkNewEndDate.setValue(null);
			tblOriIncrease.removeRows(false);
			tblOriFree.removeRows(false);
			tblIncrease.removeRows(false);
			tblFree.removeRows(false);
			return;
			
		}
		isExistsTenancyChange(tenBill);
		
		tenBill = TenancyHelper.getTenancyBillInfo(tenBill.getId().toString());
		if (tenBill == null) {
			return;
		}
		newTenBill=TenancyHelper.getTenancyBillInfo(tenBill.getId().toString());
		if(tenBill.isIsFreeContract())
		{
			MsgBox.showInfo("自由式合同不允许变更");
			this.abort();
		}
		if(tenBill.getRentStartType().equals(RentStartTypeEnum.DynamicStartDate) && tenBill.getStartDate()==null)
    	{
    		MsgBox.showInfo("动态收租起始日期的合同开始日期还未补录不允许变更!");
    		this.abort();
    	}
		TenancyModificationByTenancyBill(tenBill);
	}

	private void isExistsTenancyChange(TenancyBillInfo tenBill) throws BOSException{
		ITenancyModification tenModiy = TenancyModificationFactory.getRemoteInstance();

		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("tenancy.id", tenBill.getId().toString(), CompareType.EQUALS));
		filterInfo.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED, CompareType.NOTEQUALS));
		evi.setFilter(filterInfo);

		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("state"));

		evi.setSelector(coll);

		TenancyModificationCollection collection = tenModiy.getTenancyModificationCollection(evi);

		if (collection != null && collection.size() > 0) {
			for (int j = 0; j < collection.size(); j++) {
				TenancyModificationInfo tencyModifInfo = collection.get(j);
				MsgBox.showWarning(this, "该合同下存在【"+tencyModifInfo.getState()+"】的合同变更单据，不允许选择！");
				SysUtil.abort();
			}
		} 
	}
	private void TenancyModificationByTenancyBill(TenancyBillInfo tenBill) throws EASBizException, BOSException {

		if (tenBill != null) {
			this.txtCustomerDesc.setText(tenBill.getTenCustomerDes());
			this.txtRoomDesc.setText(tenBill.getTenRoomsDes());
			this.txtAttResourceDesc.setText(tenBill.getTenAttachesDes());
			this.pkStartDate.setValue(tenBill.getStartDate());
			this.pkOriEndDate.setValue(tenBill.getEndDate());
			this.pkNewEndDate.setValue(tenBill.getEndDate());

			this.txtCustomerDesc.setEnabled(false);
			this.txtRoomDesc.setEnabled(false);
			this.txtAttResourceDesc.setEnabled(false);
			this.pkStartDate.setEnabled(false);
			this.pkOriEndDate.setEnabled(false);
			
			loadTenBillRentIncreaded(this.tblOriIncrease, tenBill.getIncreasedRents());
			//by tim_gao
			//原合同只有旧明细，没有新增
//			loadTenBillRentIncreaded(this.tblIncrease, tenBill.getIncreasedRents());
			
			loadTenBillRentFree(this.tblOriFree, tenBill.getRentFrees());
//			loadTenBillRentFree(this.tblFree, tenBill.getRentFrees());

//			loadTenBillRoomRent(this.tblOriRent, tenBill.getTenancyRoomList(), "ori");
//			loadTenBillRoomRent(this.tblRent, tenBill.getTenancyRoomList(), "new");
//
//			loadTenBillRoomRent(this.tblOriRent, tenBill.getTenAttachResourceList(), "ori");
//			loadTenBillRoomRent(this.tblRent, tenBill.getTenAttachResourceList(), "new");
			
			if(isDynamicStartDate(tenBill.getRentStartType()) && tenBill.getStartDate()==null)
			{
				loadTblRentSet2(tenBill.getTenancyRoomList(), tenBill.getTenAttachResourceList(),tblRent);
				loadTblRentSet2(tenBill.getTenancyRoomList(), tenBill.getTenAttachResourceList(),tblOriRent);
			}
			//这里这么写是因为如果直接写else在租赁控制新增租赁合同时会调用loadTblRentSet，
			else if(this.pkStartDate.getValue()!=null && tenBill.getEndDate()!=null)
			{
				try {
					reSetRentSetInfo(getTenRoomListFromView(), getTenAttachResListFromView(),true);
				} catch (BOSException e) {
					e.printStackTrace();
				}
				loadTblRentSet(tenBill.getTenancyRoomList(), tenBill.getTenAttachResourceList(),tblRent);
				loadTblRentSet(tenBill.getTenancyRoomList(), tenBill.getTenAttachResourceList(),tblOriRent);
			}
			initRentTypeLock();
		}
	}

	/**
	 * 设置租金类型列不可编辑
	 */
	public void initRentTypeLock(){
		for(int i=0;i<tblRent.getRowCount();i++)
		{
			IRow row = tblRent.getRow(i);
			for(int j=0;j<this.tblRent.getColumnCount();j++)
			{
				IColumn column = this.tblRent.getColumn(j);
				String colKey = column.getKey();
				if(C_RENT_RENT_TYPE.equals(colKey)){
					row.getCell(C_RENT_RENT_TYPE).getStyleAttributes().setLocked(true);
				}
			}
		}
	}
	
	private void loadTenBillRoomRent(KDTable tbl, TenAttachResourceEntryCollection tenAttas, String dealType) {
		for (int i = 0; i < tenAttas.size(); i++) {
			TenAttachResourceEntryInfo tenAtta = tenAttas.get(i);
			IRow row = tbl.addRow();
			row.setUserObject(tenAtta);
			row.getCell(C_RENT_ROOM).setValue(tenAtta.getAttachLongNum());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(TenancyModeEnum.TenancyRentModel);
			row.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(true);
			row.getCell(C_RENT_RENT_TYPE).setValue(tenAtta.getDealRentType());
//			row.getCell(C_RENT_DEPOSIT).setValue(tenAtta.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenAtta.getFirstPayAmount());

			AttachDealAmountEntryCollection dealAmounts = tenAtta.getDealAmounts();
			for (int j = 0; j < dealAmounts.size(); j++) {
				AttachDealAmountEntryInfo dealAmount = (AttachDealAmountEntryInfo) dealAmounts.get(j);
				String moneyName = dealAmount.getMoneyDefine().getName();
				String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
				String keyRent = PREFIX_C_RENT_RENT + dateDes;

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if (cellRentPrice != null && cellRent != null) {
					cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());

					if ("ori".equals(dealType)) {
						// 将 dealAmount对象转换成oldDealAmount对象放在cell中，
						// 以便支持tblRentSet的手动设置
						OldAttachDealAmountEntryInfo dealOriAmount = new OldAttachDealAmountEntryInfo();
						dealOriAmount.setStartDate(dealAmount.getStartDate());
						dealOriAmount.setEndDate(dealAmount.getEndDate());
						dealOriAmount.setAmount(dealAmount.getAmount());
						dealOriAmount.setPriceAmount(dealAmount.getPriceAmount());
						dealOriAmount.setRentType(dealAmount.getRentType());
						dealOriAmount.setIsHandwork(dealAmount.isIsHandwork());
						dealOriAmount.setMoneyDefine(dealAmount.getMoneyDefine());
						dealOriAmount.setTenancyAtta(tenAtta);

						cellRentPrice.setUserObject(dealOriAmount);
						cellRent.setUserObject(dealOriAmount);
					}
					if ("new".equals(dealType)) {
						// 将 dealAmount对象转换成newDealAmount对象放在cell中，
						// 以便支持tblRentSet的手动设置
						NewAttachDealAmountEntryInfo dealNewAmount = new NewAttachDealAmountEntryInfo();

						dealNewAmount.setStartDate(dealAmount.getStartDate());
						dealNewAmount.setEndDate(dealAmount.getEndDate());
						dealNewAmount.setAmount(dealAmount.getAmount());
						dealNewAmount.setPriceAmount(dealAmount.getPriceAmount());
						dealNewAmount.setRentType(dealAmount.getRentType());
						dealNewAmount.setIsHandwork(dealAmount.isIsHandwork());
						dealNewAmount.setMoneyDefine(dealAmount.getMoneyDefine());
						dealNewAmount.setTenancyAtta(tenAtta);
						cellRentPrice.setUserObject(dealNewAmount);
						cellRent.setUserObject(dealNewAmount);
					}

					cellRentPrice.getStyleAttributes().setLocked(true);
					cellRent.getStyleAttributes().setLocked(true);

				}
			}
		}

	}

	private void loadTenBillRoomRent(KDTable tbl, TenancyRoomEntryCollection tenRooms, String dealType) {
		tbl.removeRows();
		// 先将租金列删除，再根据设置的递增列表动态生成tblRentSet列
		List toDelColIndexes = new ArrayList();
		for (int i = 0; i < tbl.getColumnCount(); i++) {
			IColumn column = tbl.getColumn(i);
			String colKey = column.getKey();
			if (colKey.startsWith(PREFIX_C_RENT_RENT_PRICE) || colKey.startsWith(PREFIX_C_RENT_RENT) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				toDelColIndexes.add(new Integer(i));
			}
		}

		for (int i = toDelColIndexes.size() - 1; i >= 0; i--) {
			int colIndex = ((Integer) toDelColIndexes.get(i)).intValue();
			tbl.removeColumn(colIndex);
		}
		// 增加第一行时进行动态增加列
		boolean isFirstRow = true;
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			IRow row = tbl.addRow();
			row.setUserObject(tenRoom);
			row.getCell(C_RENT_ROOM).setValue(tenRoom.getRoomLongNum());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(tenRoom.getTenancyModel());
			row.getCell(C_RENT_RENT_TYPE).setValue(tenRoom.getDealRentType());
//			row.getCell(C_RENT_DEPOSIT).setValue(tenRoom.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenRoom.getFirstPayAmount());

			DealAmountEntryCollection dealAmounts = tenRoom.getDealAmounts();
			for(int k=0;k<dealAmounts.size();k++)
			{
				DealAmountEntryInfo dealAmount = dealAmounts.get(k);
				String moneyNumber = dealAmount.getMoneyDefine().getNumber();
				{
					for(int j=0;j<monDefineColl.size();j++)
					{
						MoneyDefineInfo moneyInfo = monDefineColl.get(j);
						if(moneyNumber.equals(moneyInfo.getNumber()))
						{
							row.getCell(moneyNumber).setValue(dealAmount.getAmount());
						}					
					}
				}				
			}
			if (isFirstRow) {
				for (int j = 0; j < dealAmounts.size(); j++) {
					DealAmountEntryInfo dealAmount = (DealAmountEntryInfo) dealAmounts.get(j);
					String moneyName = dealAmount.getMoneyDefine().getName();
					if (MoneyTypeEnum.PeriodicityAmount.equals(dealAmount.getMoneyDefine().getMoneyType())
							|| MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType())) {
						continue;
					}
					String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
					String keyRent = PREFIX_C_RENT_RENT + dateDes;
					addTblRentSetColumn(tbl, keyRentPrice, keyRent, dateDes);
				}
				isFirstRow = false;
			}

			for (int j = 0; j < dealAmounts.size(); j++) {
				DealAmountEntryInfo dealAmount = (DealAmountEntryInfo) dealAmounts.get(j);
				String moneyName = dealAmount.getMoneyDefine().getName();
//				if(MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType()))
//				{
//					continue;
//				}
				String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
				String keyRent = PREFIX_C_RENT_RENT + dateDes;

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if (cellRentPrice != null && cellRent != null) {
					cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());

					if ("ori".equals(dealType)) {
						// 将 dealAmount对象转换成oldDealAmount对象放在cell中，
						// 以便支持tblRentSet的手动设置
						OldDealAmountEntryInfo dealOriAmount = new OldDealAmountEntryInfo();
						dealOriAmount.setStartDate(dealAmount.getStartDate());
						dealOriAmount.setEndDate(dealAmount.getEndDate());
						dealOriAmount.setAmount(dealAmount.getAmount());
						dealOriAmount.setPriceAmount(dealAmount.getPriceAmount());
						dealOriAmount.setRentType(dealAmount.getRentType());
						dealOriAmount.setIsHandwork(dealAmount.isIsHandwork());
						dealOriAmount.setMoneyDefine(dealAmount.getMoneyDefine());
						dealOriAmount.setTenancyRoom(tenRoom);

						cellRentPrice.setUserObject(dealOriAmount);
						cellRent.setUserObject(dealOriAmount);
					}
					if ("new".equals(dealType)) {
						// 将 dealAmount对象转换成newDealAmount对象放在cell中，
						// 以便支持tblRentSet的手动设置
						NewDealAmountEntryInfo dealNewAmount = new NewDealAmountEntryInfo();
						dealNewAmount.setStartDate(dealAmount.getStartDate());
						dealNewAmount.setEndDate(dealAmount.getEndDate());
						dealNewAmount.setAmount(dealAmount.getAmount());
						dealNewAmount.setPriceAmount(dealAmount.getPriceAmount());
						dealNewAmount.setRentType(dealAmount.getRentType());
						dealNewAmount.setIsHandwork(dealAmount.isIsHandwork());
						dealNewAmount.setMoneyDefine(dealAmount.getMoneyDefine());
						dealNewAmount.setTenancyRoom(tenRoom);
						cellRentPrice.setUserObject(dealNewAmount);
						cellRent.setUserObject(dealNewAmount);
					}

					if (dealAmount.isIsHandwork()) {
						// 如果不是按总价计算，则锁定总价不可编辑
						if (!TenancyModeEnum.TenancyRentModel.equals(tenRoom.getTenancyModel())) {
							cellRent.getStyleAttributes().setLocked(true);
						} else {
							cellRentPrice.getStyleAttributes().setLocked(true);
						}
					} else {
						cellRentPrice.getStyleAttributes().setLocked(true);
						cellRent.getStyleAttributes().setLocked(true);
					}
				}
			}
		}

		tbl.getHeadMergeManager().mergeBlock(0, 0, 1, tbl.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);

	}

	private void loadDealAmount(KDTable tbl, TenancyRoomEntryCollection tenRooms, TenAttachResourceEntryCollection tenAttas, String dealType) {
		tbl.removeRows();
		// 先将租金列删除，再根据设置的递增列表动态生成tblRentSet列
		List toDelColIndexes = new ArrayList();
		for (int i = 0; i < tbl.getColumnCount(); i++) {
			IColumn column = tbl.getColumn(i);
			String colKey = column.getKey();
			if (colKey.startsWith(PREFIX_C_RENT_RENT_PRICE) || colKey.startsWith(PREFIX_C_RENT_RENT) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				toDelColIndexes.add(new Integer(i));
			}
		}

		for (int i = toDelColIndexes.size() - 1; i >= 0; i--) {
			int colIndex = ((Integer) toDelColIndexes.get(i)).intValue();
			tbl.removeColumn(colIndex);
		}
		// 增加第一行时进行动态增加列
		boolean isFirstRow = true;
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			IRow row = tbl.addRow();
			row.setUserObject(tenRoom);
			row.getCell(C_RENT_ROOM).setValue(tenRoom.getRoomLongNum());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(tenRoom.getTenancyModel());
			row.getCell(C_RENT_RENT_TYPE).setValue(tenRoom.getDealRentType());
//			row.getCell(C_RENT_DEPOSIT).setValue(tenRoom.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT).setValue(tenRoom.getFirstPayAmount());

			DealAmountEntryCollection dealAmounts = tenRoom.getDealAmounts();
			if (isFirstRow) {
				for (int j = 0; j < dealAmounts.size(); j++) {
					DealAmountEntryInfo dealAmount = (DealAmountEntryInfo) dealAmounts.get(j);
					String moneyName = dealAmount.getMoneyDefine().getName();
					if (MoneyTypeEnum.PeriodicityAmount.equals(dealAmount.getMoneyDefine().getMoneyType())) {
						continue;
					}
					String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
					String keyRent = PREFIX_C_RENT_RENT + dateDes;
					addTblRentSetColumn(tbl, keyRentPrice, keyRent, dateDes);
				}
				isFirstRow = false;
			}

			for (int j = 0; j < dealAmounts.size(); j++) {
				DealAmountEntryInfo dealAmount = (DealAmountEntryInfo) dealAmounts.get(j);
				String moneyName = dealAmount.getMoneyDefine().getName();
				String dateDes = getDateDes(moneyName, dealAmount.getStartDate(), dealAmount.getEndDate());

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
				String keyRent = PREFIX_C_RENT_RENT + dateDes;

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if (cellRentPrice != null && cellRent != null) {
					cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());

					if ("ori".equals(dealType)) {
						// 将 dealAmount对象转换成oldDealAmount对象放在cell中，
						// 以便支持tblRentSet的手动设置
						OldDealAmountEntryInfo dealOriAmount = new OldDealAmountEntryInfo();
						dealOriAmount.setStartDate(dealAmount.getStartDate());
						dealOriAmount.setEndDate(dealAmount.getEndDate());
						dealOriAmount.setAmount(dealAmount.getAmount());
						dealOriAmount.setPriceAmount(dealAmount.getPriceAmount());
						dealOriAmount.setRentType(dealAmount.getRentType());
						dealOriAmount.setIsHandwork(dealAmount.isIsHandwork());
						dealOriAmount.setMoneyDefine(dealAmount.getMoneyDefine());
						dealOriAmount.setTenancyRoom(tenRoom);

						cellRentPrice.setUserObject(dealOriAmount);
						cellRent.setUserObject(dealOriAmount);
					}
					if ("new".equals(dealType)) {
						// 将 dealAmount对象转换成newDealAmount对象放在cell中，
						// 以便支持tblRentSet的手动设置
						NewDealAmountEntryInfo dealNewAmount = new NewDealAmountEntryInfo();
						dealNewAmount.setStartDate(dealAmount.getStartDate());
						dealNewAmount.setEndDate(dealAmount.getEndDate());
						dealNewAmount.setAmount(dealAmount.getAmount());
						dealNewAmount.setPriceAmount(dealAmount.getPriceAmount());
						dealNewAmount.setRentType(dealAmount.getRentType());
						dealNewAmount.setIsHandwork(dealAmount.isIsHandwork());
						dealNewAmount.setMoneyDefine(dealAmount.getMoneyDefine());
						dealNewAmount.setTenancyRoom(tenRoom);
						cellRentPrice.setUserObject(dealNewAmount);
						cellRent.setUserObject(dealNewAmount);
					}

					if (dealAmount.isIsHandwork()) {
						// 如果不是按总价计算，则锁定总价不可编辑
						if (!TenancyModeEnum.TenancyRentModel.equals(tenRoom.getTenancyModel())) {
							cellRent.getStyleAttributes().setLocked(true);
						} else {
							cellRentPrice.getStyleAttributes().setLocked(true);
						}
					} else {
						cellRentPrice.getStyleAttributes().setLocked(true);
						cellRent.getStyleAttributes().setLocked(true);
					}
				}
			}
		}

		tbl.getHeadMergeManager().mergeBlock(0, 0, 1, tbl.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);

	}

	private String getDateDes(String moneyName, Date startDate, Date endDate) {
		String dateDes = null;
		if (startDate == null || endDate == null) {
			return null;
		}
		if (startDate.equals(endDate)) {
			dateDes = FDCDateHelper.DateToString(startDate);
		} else {
			dateDes = FDCDateHelper.DateToString(startDate) + "至" + FDCDateHelper.DateToString(endDate);
		}
		return moneyName + "(" + dateDes + ")";
	}

	/**
	 * output class constructor
	 */
	public TenancyModificationEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		TenancyModificationInfo info = (TenancyModificationInfo) this.editData;
		if (info != null) {
			NewIncRentEntryCollection incNewCol = new NewIncRentEntryCollection();
			for (int i = 0; i < this.tblIncrease.getRowCount(); i++) {
				IRow row = this.tblIncrease.getRow(i);
				Date increaseDate = (Date) row.getCell(C_INC_INCREASE_DATE).getValue();
				if (increaseDate == null) {
					continue;
				}
				IncreasedRentTypeEnum increasedRentType = (IncreasedRentTypeEnum) row.getCell(C_INC_INCREASE_TYPE).getValue();
				BigDecimal value = (BigDecimal) row.getCell(C_INC_VALUE).getValue();
				if (!IncreasedRentTypeEnum.Handwork.equals(increasedRentType) && value == null) {
					continue;
				}
				NewIncRentEntryInfo incNewInfo = new NewIncRentEntryInfo();
				incNewInfo.setHead(info);
				incNewInfo.setIncreaseDate((Date) row.getCell("increaseDate").getValue());
				incNewInfo.setIncreaseType((IncreasedRentTypeEnum) row.getCell("increaseType").getValue());
				incNewInfo.setIncreaseStyle((IncreaseStyleEnum)row.getCell("increaseStyle").getValue());
				incNewInfo.setValue(FDCHelper.toBigDecimal(row.getCell("value").getValue()));
				incNewCol.add(incNewInfo);
			}
			info.getNewIncreasedRents().clear();
			info.getNewIncreasedRents().addCollection(incNewCol);

			// 装载原递增分录
			OldIncRentEntryCollection incOriCol = new OldIncRentEntryCollection();
			for (int i = 0; i < this.tblOriIncrease.getRowCount(); i++) {
				IRow row = this.tblOriIncrease.getRow(i);
				OldIncRentEntryInfo incOriInfo = new OldIncRentEntryInfo();
				incOriInfo.setHead(info);
				incOriInfo.setIncreaseDate((Date) row.getCell("increaseDate").getValue());
				incOriInfo.setIncreaseType((IncreasedRentTypeEnum) row.getCell("increaseType").getValue());
				incOriInfo.setIncreaseStyle((IncreaseStyleEnum)row.getCell("increaseStyle").getValue());
				incOriInfo.setValue(FDCHelper.toBigDecimal(row.getCell("value").getValue()));
				incOriCol.add(incOriInfo);
			}
			info.getOldIncreasedRents().clear();
			info.getOldIncreasedRents().addCollection(incOriCol);

			// 装载新免租分录
			NewRentFreeEntryCollection freeCol = new NewRentFreeEntryCollection();
			for (int i = 0; i < this.tblFree.getRowCount(); i++) {
				IRow row = this.tblFree.getRow(i);
				NewRentFreeEntryInfo freeInfo = new NewRentFreeEntryInfo();
				freeInfo.setHead(info);
				freeInfo.setFreeStartDate((Date) row.getCell("freeStartDate").getValue());
				freeInfo.setFreeEndDate((Date) row.getCell("freeEndDate").getValue());
				freeInfo.setFreeTenancyType((FreeTenancyTypeEnum) row.getCell("freeTenancyType").getValue());
				freeInfo.setDescription((String) row.getCell("description").getValue());
				freeCol.add(freeInfo);
			}
			info.getNewRentFrees().clear();
			info.getNewRentFrees().addCollection(freeCol);

			// 装载原免租分录
			OldRentFreeEntryCollection freeOriCol = new OldRentFreeEntryCollection();
			for (int i = 0; i < this.tblOriFree.getRowCount(); i++) {
				IRow row = this.tblOriFree.getRow(i);
				OldRentFreeEntryInfo freeOriInfo = new OldRentFreeEntryInfo();
				freeOriInfo.setHead(info);
				freeOriInfo.setFreeStartDate((Date) row.getCell("freeStartDate").getValue());
				freeOriInfo.setFreeEndDate((Date) row.getCell("freeEndDate").getValue());
				freeOriInfo.setFreeTenancyType((FreeTenancyTypeEnum) row.getCell("freeTenancyType").getValue());
				freeOriInfo.setDescription((String) row.getCell("description").getValue());
				freeOriCol.add(freeOriInfo);
			}
			info.getOldRentFrees().clear();
			info.getOldRentFrees().addCollection(freeOriCol);

			//保存新租金分录值
			Map newMap = new HashMap();
			for(int i=0;i<tblRent.getRowCount();i++)
			{
				Map map = new HashMap();
				IRow row = tblRent.getRow(i);
				TenancyRoomEntryInfo tenRoom = new TenancyRoomEntryInfo();
				if(row.getUserObject()!=null)
				{
					tenRoom = (TenancyRoomEntryInfo)row.getUserObject();
				}else
				{
					break;
				}
				for(int j=0;j<monDefineColl.size();j++)
				{
					MoneyDefineInfo moneyInfo = monDefineColl.get(j);
					BigDecimal disAmount = new BigDecimal(0);
					if(row.getCell(moneyInfo.getNumber()).getValue()==null)
					{
						map.put(moneyInfo, disAmount);
					}else
					{
						disAmount = (BigDecimal)row.getCell(moneyInfo.getNumber()).getValue();
						map.put(moneyInfo, disAmount);
					}
				}
				newMap.put(tenRoom.getRoom().getId().toString(), map);
			}
			// 装载新租金分录，包括房间分录和附属资源分录
			NewDealAmountEntryCollection dealNewCol = new NewDealAmountEntryCollection();
			NewAttachDealAmountEntryCollection attaNewCol = new NewAttachDealAmountEntryCollection();
			for (int i = 0; i < this.tblRent.getRowCount(); i++) {
				IRow oldRow = this.tblRent.getRow(i);
				if(oldRow.getUserObject() instanceof TenancyRoomEntryInfo)
				{
					TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo)oldRow.getUserObject();
					if(newMap.get(tenRoom.getRoom().getId().toString())!=null)
					{
						Map map = (Map)newMap.get(tenRoom.getRoom().getId().toString());
						for(Iterator itor = map.keySet().iterator(); itor.hasNext();)
						{
							MoneyDefineInfo moneyInfo = (MoneyDefineInfo)itor.next();
							BigDecimal disAmount = (BigDecimal)map.get(moneyInfo);
							NewDealAmountEntryInfo dealOld = new NewDealAmountEntryInfo();
							dealOld.setTenancyRoom(tenRoom);
							dealOld.setMoneyDefine(moneyInfo);
							dealOld.setAmount(disAmount);
							dealNewCol.addObject(dealOld);
						}
					}
//					if(tenRoom.getFirstPayAmount() instanceof BigDecimal){
//						BigDecimal disAmount = tenRoom.getFirstPayAmount();
//						NewDealAmountEntryInfo dealOld = new NewDealAmountEntryInfo();
//						dealOld.setAmount(disAmount);
//						dealNewCol.addObject(dealOld);
//					}
//					
				}
				
				for (int j = 5+monDefineColl.size(); j < this.tblRent.getColumnCount(); j=j+2) {
					if (this.tblRent.getRow(i).getCell(j).getUserObject() instanceof DealAmountEntryInfo) {
						DealAmountEntryInfo deal = (DealAmountEntryInfo) this.tblRent.getRow(i).getCell(j).getUserObject();
						if (deal != null) {
							NewDealAmountEntryInfo dealOld = new NewDealAmountEntryInfo();
							dealOld.setHead(info);
							dealOld.setTenancyRoom(deal.getTenancyRoom());
							dealOld.setStartDate(deal.getStartDate());
							dealOld.setEndDate(deal.getEndDate());
							dealOld.setAmount(deal.getAmount());
							dealOld.setPriceAmount(deal.getPriceAmount());
							dealOld.setRentType(deal.getRentType());
							dealOld.setIsHandwork(deal.isIsHandwork());
							dealOld.setMoneyDefine(deal.getMoneyDefine());
							dealNewCol.add(dealOld);
						}
					}
					if (this.tblRent.getRow(i).getCell(j).getUserObject() instanceof NewDealAmountEntryInfo) {
						NewDealAmountEntryInfo deal =  (NewDealAmountEntryInfo)this.tblRent.getRow(i).getCell(j).getUserObject();
						if (deal != null) {
							NewDealAmountEntryInfo dealOld = new NewDealAmountEntryInfo();
							dealOld.setHead(info);
							dealOld.setTenancyRoom(deal.getTenancyRoom());
							dealOld.setStartDate(deal.getStartDate());
							dealOld.setEndDate(deal.getEndDate());
							dealOld.setAmount(deal.getAmount());
							dealOld.setPriceAmount(deal.getPriceAmount());
							dealOld.setRentType(deal.getRentType());
							dealOld.setIsHandwork(deal.isIsHandwork());
							dealOld.setMoneyDefine(deal.getMoneyDefine());
							dealNewCol.add(dealOld);
						}
					}
					if (this.tblRent.getRow(i).getCell(j).getUserObject() instanceof AttachDealAmountEntryInfo) {
						AttachDealAmountEntryInfo deal = (AttachDealAmountEntryInfo) this.tblRent.getRow(i).getCell(j).getUserObject();
						if (deal != null) {
							NewAttachDealAmountEntryInfo dealOld = new NewAttachDealAmountEntryInfo();
							dealOld.setHead(info);
							dealOld.setTenancyAtta(deal.getTenancyAttach());
							dealOld.setStartDate(deal.getStartDate());
							dealOld.setEndDate(deal.getEndDate());
							dealOld.setAmount(deal.getAmount());
							dealOld.setPriceAmount(deal.getPriceAmount());
							dealOld.setRentType(deal.getRentType());
							dealOld.setIsHandwork(deal.isIsHandwork());
							dealOld.setMoneyDefine(deal.getMoneyDefine());
							attaNewCol.add(dealOld);
						}
					}

				}
			}
			info.getNewDealAmountEntry().clear();
			info.getNewDealAmountEntry().addCollection(dealNewCol);
			info.getNewAttachDealAmountEntry().clear();
			info.getNewAttachDealAmountEntry().addCollection(attaNewCol);
			
			//保存原租金分录值
			Map oldMap = new HashMap();
			for(int i=0;i<tblOriRent.getRowCount();i++)
			{
				Map map = new HashMap();
				IRow row = tblOriRent.getRow(i);
				TenancyRoomEntryInfo tenRoom = new TenancyRoomEntryInfo();
				if(row.getUserObject()!=null)
				{
					tenRoom = (TenancyRoomEntryInfo)row.getUserObject();
				}else
				{
					break;
				}
				for(int j=0;j<monDefineColl.size();j++)
				{
					MoneyDefineInfo moneyInfo = monDefineColl.get(j);
					BigDecimal disAmount = new BigDecimal(0);
					if(row.getCell(moneyInfo.getNumber()).getValue()==null)
					{
						map.put(moneyInfo, disAmount);
					}else
					{
						disAmount = (BigDecimal)row.getCell(moneyInfo.getNumber()).getValue();
						map.put(moneyInfo, disAmount);
					}
				}
				oldMap.put(tenRoom.getRoom().getId().toString(), map);
			}
			// 装载原租金分录，包括房间分录和附属资源分录
			OldDealAmountEntryCollection dealOldCol = new OldDealAmountEntryCollection();
			OldAttachDealAmountEntryCollection attaOldCol = new OldAttachDealAmountEntryCollection();
			for (int i = 0; i < this.tblOriRent.getRowCount(); i++) {
				IRow oldRow = this.tblOriRent.getRow(i);
				if(oldRow.getUserObject() instanceof TenancyRoomEntryInfo)
				{
					TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo)oldRow.getUserObject();
					if(oldMap.get(tenRoom.getRoom().getId().toString())!=null)
					{
						Map map = (Map)oldMap.get(tenRoom.getRoom().getId().toString());
						for(Iterator itor = map.keySet().iterator(); itor.hasNext();)
						{
							MoneyDefineInfo moneyInfo = (MoneyDefineInfo)itor.next();
							BigDecimal disAmount = (BigDecimal)map.get(moneyInfo);
							OldDealAmountEntryInfo dealOld = new OldDealAmountEntryInfo();
							dealOld.setTenancyRoom(tenRoom);
							dealOld.setMoneyDefine(moneyInfo);
							dealOld.setAmount(disAmount);
							dealOldCol.addObject(dealOld);
						}
					}
				}
				for (int j = 5+monDefineColl.size(); j < this.tblOriRent.getColumnCount(); j=j+2) {
					if (this.tblOriRent.getRow(i).getCell(j).getUserObject() instanceof DealAmountEntryInfo) {
						DealAmountEntryInfo deal = (DealAmountEntryInfo) this.tblOriRent.getRow(i).getCell(j).getUserObject();
						if (deal != null) {
							OldDealAmountEntryInfo dealOld = new OldDealAmountEntryInfo();
							dealOld.setHead(info);
							dealOld.setTenancyRoom(deal.getTenancyRoom());
							dealOld.setStartDate(deal.getStartDate());
							dealOld.setEndDate(deal.getEndDate());
							dealOld.setAmount(deal.getAmount());
							dealOld.setPriceAmount(deal.getPriceAmount());
							dealOld.setRentType(deal.getRentType());
							dealOld.setIsHandwork(deal.isIsHandwork());
							dealOld.setMoneyDefine(deal.getMoneyDefine());
							dealOldCol.add(dealOld);
						}
					}
					if (this.tblOriRent.getRow(i).getCell(j).getUserObject() instanceof OldDealAmountEntryInfo) {
						OldDealAmountEntryInfo deal = (OldDealAmountEntryInfo) this.tblOriRent.getRow(i).getCell(j).getUserObject();
						if (deal != null) {
							OldDealAmountEntryInfo dealOld = new OldDealAmountEntryInfo();
							dealOld.setHead(info);
							dealOld.setTenancyRoom(deal.getTenancyRoom());
							dealOld.setStartDate(deal.getStartDate());
							dealOld.setEndDate(deal.getEndDate());
							dealOld.setAmount(deal.getAmount());
							dealOld.setPriceAmount(deal.getPriceAmount());
							dealOld.setRentType(deal.getRentType());
							dealOld.setIsHandwork(deal.isIsHandwork());
							dealOld.setMoneyDefine(deal.getMoneyDefine());
							dealOldCol.add(dealOld);
						}
					}
					if (this.tblOriRent.getRow(i).getCell(j).getUserObject() instanceof AttachDealAmountEntryInfo) {
						AttachDealAmountEntryInfo deal = (AttachDealAmountEntryInfo) this.tblOriRent.getRow(i).getCell(j).getUserObject();
						if (deal != null) {
							OldAttachDealAmountEntryInfo dealOld = new OldAttachDealAmountEntryInfo();
							dealOld.setHead(info);
							dealOld.setTenancyAtta(deal.getTenancyAttach());
							dealOld.setStartDate(deal.getStartDate());
							dealOld.setEndDate(deal.getEndDate());
							dealOld.setAmount(deal.getAmount());
							dealOld.setPriceAmount(deal.getPriceAmount());
							dealOld.setRentType(deal.getRentType());
							dealOld.setIsHandwork(deal.isIsHandwork());
							dealOld.setMoneyDefine(deal.getMoneyDefine());
							attaOldCol.add(dealOld);
						}
					}

				}
			}
			info.getOldDealAmountEntry().clear();
			info.getOldDealAmountEntry().addCollection(dealOldCol);
			info.getOldAttachDealAmountEntry().clear();
			info.getOldAttachDealAmountEntry().addCollection(attaOldCol);
		}
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (this.txtNumber.getText() == null || this.txtNumber.getText() == "") {
			MsgBox.showConfirm2("单据编号不能为空!");
			this.txtNumber.setFocusable(true);
			abort();
		}
		if (this.prmtTenancy.getValue() == null || this.prmtTenancy.getValue() == "") {
			MsgBox.showConfirm2("租赁合同不能为空！");
			this.prmtTenancy.setFocusable(true);
			abort();
		}
		if (this.txtModificationReason.getText() == null || this.txtModificationReason.getText().trim() == "") {
			MsgBox.showConfirm2("变更原因不能为空！");
			this.txtModificationReason.setFocusable(true);
			abort();
		}
		if (this.pkNewEndDate.getText() == null || this.pkNewEndDate.getText() == "") {
			MsgBox.showConfirm2("新结束日期不能为空！");
			this.pkNewEndDate.setFocusable(true);
			abort();
		}
		Date newEndDate = DateTimeUtils.parseDate(this.pkNewEndDate.getText());
		Date startDate = DateTimeUtils.parseDate(this.pkStartDate.getText());
		if (DateTimeUtils.dayBefore(newEndDate, startDate)) {
			MsgBox.showConfirm2("新结束日期不能小于开始日期！");
			this.pkNewEndDate.setFocusable(true);
			abort();
		}
	}

	protected void pkNewEndDate_dataChanged(DataChangeEvent e) throws Exception {
		super.pkNewEndDate_dataChanged(e);
		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkNewEndDate.getValue();
		if (startDate == null || endDate == null) {
			return;
		}

		if (endDate.before(startDate)) {
			MsgBox.showWarning(this,"新结束日期不能小于开始日期！");
			this.pkNewEndDate.setValue(e.getOldValue());
			this.pkNewEndDate.setFocusable(true);
			SysUtil.abort();
		}

		TenancyBillInfo billInfo = (TenancyBillInfo) this.prmtTenancy.getValue();
		if (billInfo == null) {
			return;
		}
		if (billInfo.getId() != null) {
			String billId = billInfo.getId().toString();
			if (billId != null) {
				Date leastPaidDate = TenancyModificationFactory.getRemoteInstance().getLeastPaidDate(billId);
				if (leastPaidDate != null) {
					if (leastPaidDate.after((Date) this.pkNewEndDate.getValue())) {
						MsgBox.showWarning(this,"新结束日期不能小于最后付款明细的结束日期！");
						this.pkNewEndDate.setValue(e.getOldValue());
						this.pkNewEndDate.setFocusable(true);
						SysUtil.abort();
					}
				}
			}
		}
		NewIncRentEntryCollection incNewCol = new NewIncRentEntryCollection();
		for (int i = 0; i < this.tblIncrease.getRowCount(); i++) {
			IRow rowInc = this.tblIncrease.getRow(i);
			Date increaseDate = (Date) rowInc.getCell(C_INC_INCREASE_DATE).getValue();
			if (increaseDate == null) {
				continue;
			}
			IncreasedRentTypeEnum increasedRentType = (IncreasedRentTypeEnum) rowInc.getCell(C_INC_INCREASE_TYPE).getValue();
			BigDecimal value = (BigDecimal) rowInc.getCell(C_INC_VALUE).getValue();

			if (!IncreasedRentTypeEnum.Handwork.equals(increasedRentType) && value == null) {
				continue;
			}
			NewIncRentEntryInfo incNewInfo = new NewIncRentEntryInfo();
			incNewInfo.setIncreaseDate((Date) rowInc.getCell(C_INC_INCREASE_DATE).getValue());
			incNewInfo.setIncreaseType((IncreasedRentTypeEnum) rowInc.getCell(C_INC_INCREASE_TYPE).getValue());
			incNewInfo.setValue(FDCHelper.toBigDecimal(rowInc.getCell(C_INC_VALUE).getValue()));
			incNewCol.add(incNewInfo);
		}
		reSetRentNewSetInfo(getTenRoomListFromView(),getTenAttachResListFromView());
		reSetRentSetInfo(newTenBill.getTenancyRoomList(), getTenAttachResListFromView(),false);
		initRentTypeLock();
	}
	
	protected void tblRent_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		String colKey = this.tblRent.getColumnKey(colIndex);
		// 当值没有变化时,不进行更新操作
		if (!valueChanged(e)) {
			return;
		}
		IRow row = this.tblRent.getRow(e.getRowIndex());
		if(row.getUserObject()==null)
		{
			for(int k=0;k<monDefineColl.size();k++)
			{
				MoneyDefineInfo moneyInfo = monDefineColl.get(k);
				if(moneyInfo.getNumber().equals(colKey)){
					BigDecimal deposit = (BigDecimal) e.getValue();
					BigDecimal rentGather = new BigDecimal(0);
					int j=0;
					for(int i=0;i<tblRent.getRowCount();i++)
					{
						IRow row2 = tblRent.getRow(i);
						if(row2.getUserObject() instanceof TenancyRoomEntryInfo)
						{
							++j;
							ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2.getUserObject();
							BigDecimal area = tenEntry.getBuildingArea();
							//BigDecimal desposit2 = deposit.multiply(area).divide(rentArea, 2,BigDecimal.ROUND_HALF_UP);
							BigDecimal desposit2 = new BigDecimal(0);
							//rentGather = rentGather.add(desposit2);
							if(j!=newRoom.size())
							{
								desposit2 = deposit.multiply(area).divide(rentArea,2,BigDecimal.ROUND_HALF_UP);
								rentGather = rentGather.add(desposit2);

							}else
							{
								desposit2 = deposit.subtract(rentGather);
							}
							row2.getCell(colKey).setValue(desposit2);
							tenEntry.setDepositAmount(desposit2);
						}
					}
				}
			}
			if(C_RENT_FIRST_RENT.equals(colKey))
			{
				BigDecimal firstRent = (BigDecimal) e.getValue();
				BigDecimal rentGather = new BigDecimal(0);
				int j=0;
				for(int i=0;i<tblRent.getRowCount();i++)
				{
					IRow row2 = tblRent.getRow(i);
					if(row2.getUserObject() instanceof TenancyRoomEntryInfo)
					{
						++j;
						ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2.getUserObject();
						BigDecimal area = tenEntry.getBuildingArea();
						//BigDecimal firstRent2 = firstRent.multiply(area).divide(rentArea, 2,BigDecimal.ROUND_HALF_UP);
						BigDecimal firstRent2 = new BigDecimal(0);
						rentGather = rentGather.add(firstRent2);
						if(j!=newRoom.size())
						{
							firstRent2 = firstRent.multiply(area).divide(rentArea,2,BigDecimal.ROUND_HALF_UP);
							rentGather = rentGather.add(firstRent2);

						}else
						{
							firstRent2 = firstRent.subtract(rentGather);
						}
						row2.getCell(C_RENT_FIRST_RENT).setValue(firstRent2);
						tenEntry.setFirstPayAmount(firstRent2);
					}
				}
			}else if(C_RENT_RENT_TYPE.equals(colKey))
			{
				RentTypeEnum rentType= (RentTypeEnum) e.getValue();
				for(int i=0;i<tblRent.getRowCount();i++)
				{
					IRow row2 = tblRent.getRow(i);
					if(row2.getUserObject() instanceof TenancyRoomEntryInfo)
					{
						ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2.getUserObject();
						row2.getCell(C_RENT_RENT_TYPE).setValue(rentType);
						tenEntry.setDealRentType(rentType);
					}
				}
				reSetRentSetInfo(getTenRoomListFromView(), getTenAttachResListFromView(),false);
			}else if(colKey.startsWith(PREFIX_C_RENT_RENT))
			{
				BigDecimal rentGather = new BigDecimal(0);
				BigDecimal amount = (BigDecimal) e.getValue();
				ICell priceCell = row.getCell(colIndex - 1);
				BigDecimal price = amount.divide(rentArea,2,BigDecimal.ROUND_HALF_UP);
				priceCell.setValue(price);
				int j=0;
				for(int i=0;i<tblRent.getRowCount();i++)
				{
					IRow row2 = tblRent.getRow(i);					
					if(row2.getUserObject() instanceof TenancyRoomEntryInfo)
					{
						//把尾数问题在最后一个房间解决
						++j;
						ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2.getUserObject();
						BigDecimal area = tenEntry.getBuildingArea();
						BigDecimal amount2 = new BigDecimal(0);
						//如果是最后一个房间。租金就不是乘百分比了。而是用汇总租金减去上面房间的租金之和，这样
						//租金汇总起来就不会有小数问题了
						if(j!=newRoom.size())
						{
							amount2 = amount.multiply(area).divide(rentArea,2,BigDecimal.ROUND_HALF_UP);
							rentGather = rentGather.add(amount2);

						}else
						{
							amount2 = amount.subtract(rentGather);
						}
						row2.getCell(colKey).setValue(amount2);
						BigDecimal amountPrice = amount2.divide(area, 2,BigDecimal.ROUND_HALF_UP);
						tenEntry.setDealRent(amount2);
						tenEntry.setDealRentPrice(amountPrice);
					}

				}
				reSetRentSetInfo(getTenRoomListFromView(), getTenAttachResListFromView(),false);
			}else if(colKey.startsWith(PREFIX_C_RENT_PERIODICITY))
			{
				BigDecimal periodictityGather = new BigDecimal(0);
				BigDecimal amount = (BigDecimal) e.getValue();
				row.getCell(colKey).setValue(amount);
				ICell priceCell = row.getCell(colIndex - 1);
				BigDecimal price = amount.divide(rentArea,2,BigDecimal.ROUND_HALF_UP);
				priceCell.setValue(price);
				int j=0;
				for(int i=0;i<tblRent.getRowCount();i++)
				{					
					IRow row2 = tblRent.getRow(i);					
					if(row2.getUserObject() instanceof TenancyRoomEntryInfo)
					{
						++j;
						IDealAmountInfo deal = (IDealAmountInfo)row2.getCell(colKey).getUserObject();
						ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2.getUserObject();
						BigDecimal area = tenEntry.getBuildingArea();
						BigDecimal amount2 = new BigDecimal(0);
						if(j!=newRoom.size())
						{
							amount2 = amount.multiply(area).divide(rentArea,2,BigDecimal.ROUND_HALF_UP);
							periodictityGather = periodictityGather.add(amount2);
						}else
						{
							amount2 = amount.subtract(periodictityGather);
						}
						row2.getCell(colKey).setValue(amount2);
						BigDecimal amountPrice = amount2.divide(area, 2,BigDecimal.ROUND_HALF_UP);
						deal.setAmount(amount2);
						deal.setPriceAmount(amountPrice);
						//						tenEntry.setDealRent(amount2);
						//						tenEntry.setDealRentPrice(amountPrice);
					}

				}
				reSetRentSetInfo(getTenRoomListFromView(), getTenAttachResListFromView(),false);
			}
		}else
		{
			ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row.getUserObject();

			for(int i=0;i<monDefineColl.size();i++)
			{
				MoneyDefineInfo moneyInfo = monDefineColl.get(i);
				if(moneyInfo.getNumber().equals(colKey))
				{
					BigDecimal depositAmount = (BigDecimal) e.getValue();
					row.getCell(colKey).setValue(depositAmount);
					IObjectCollection dealAmounts = tenEntry.getDealAmounts_();
					for(int j=0; j<dealAmounts.size(); j++){
						IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(j);
						MoneyDefineInfo money = dealAmount.getMoneyDefine();
						if(money.equals(moneyInfo))
						{
							dealAmount.setAmount(depositAmount);
						}						
					}	
					reSetRentSetInfo(getTenRoomListFromView(), getTenAttachResListFromView(),false);
				}			
			}
			 if(C_RENT_FIRST_RENT.equals(colKey)){
				tenEntry.setFirstPayAmount((BigDecimal) e.getValue());
//								return;
			}else if(C_RENT_RENT_TYPE.equals(colKey)){
				RentTypeEnum rentType= (RentTypeEnum) e.getValue();
				tenEntry.setDealRentType(rentType);

				//虽然一个租赁房间多时间段的租金类别是统一设置的，但数据结构上仍是一个租金分录设置1个租金类别

				IObjectCollection dealAmounts = tenEntry.getDealAmounts_();
				for(int i=0; i<dealAmounts.size(); i++){
					IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(i);
					dealAmount.setRentType(rentType);
				}
			}else if(C_RENT_TENANCY_MODEL.equals(colKey)){//配套资源的这列被锁了，能修改的肯定是房间列
				TenancyModeEnum tenModel = (TenancyModeEnum) e.getValue();
				tenEntry.setTenancyModel(tenModel);
				//还要设置该行的单价列和总价列哪个可编辑
				if(TenancyModeEnum.TenancyRentModel.equals(tenModel)){
					for(int i=0; i<this.tblRent.getColumnCount(); i++){
						String tColKey = this.tblRent.getColumnKey(i);
						ICell cell = row.getCell(tColKey);
						if(tColKey.startsWith(PREFIX_C_RENT_RENT_PRICE)){
							cell.getStyleAttributes().setLocked(true);
						}else if(tColKey.startsWith(PREFIX_C_RENT_RENT)){
							DealAmountEntryInfo dealAmount = (DealAmountEntryInfo) cell.getUserObject();
							if(dealAmount.isIsHandwork()){
								cell.getStyleAttributes().setLocked(false);
							}
						}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY)){
							cell.getStyleAttributes().setLocked(false);
						}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)){
							cell.getStyleAttributes().setLocked(true);
						}
					}
				}else{
					for(int i=0; i<this.tblRent.getColumnCount(); i++){
						String tColKey = this.tblRent.getColumnKey(i);
						ICell cell = row.getCell(tColKey);
						if(tColKey.startsWith(PREFIX_C_RENT_RENT_PRICE)){
							DealAmountEntryInfo dealAmount = (DealAmountEntryInfo) cell.getUserObject();
							if(dealAmount.isIsHandwork()){
								cell.getStyleAttributes().setLocked(false);
							}
						}else if(tColKey.startsWith(PREFIX_C_RENT_RENT)){
							cell.getStyleAttributes().setLocked(true);
						}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY)){
							cell.getStyleAttributes().setLocked(true);
						}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)){
							cell.getStyleAttributes().setLocked(false);
						}
					}
				}
				reSetRentSetInfo(getTenRoomListFromView(), getTenAttachResListFromView(),false);
			}else if(colKey.startsWith(PREFIX_C_RENT_RENT) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY)){
				ICell cell = row.getCell(colIndex);
				IDealAmountInfo dealAmount = (IDealAmountInfo) cell.getUserObject();

				BigDecimal amount = (BigDecimal) e.getValue();
				if(amount==null)amount = new BigDecimal(0);
				dealAmount.setAmount(amount);

				BigDecimal area = tenEntry.getBuildingArea();
				BigDecimal price = null;
				//还要反算单价
				if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
					if(amount == null){
						amount = FDCHelper.ZERO;
					}
					price = amount.divide(area, 2, BigDecimal.ROUND_HALF_UP);

					ICell priceCell = row.getCell(colIndex - 1);
					priceCell.setValue(price);
					dealAmount.setPriceAmount(price);
				}

				//如果是第一定价期的列，需要更新到租赁房间的成交总价上去. TODO 这里用colIndex=6来判断不太好 
				if(colIndex == 5+monDefineColl.size()){
					tenEntry.setDealRent(amount);
					tenEntry.setDealRentPrice(price);
					//这里不好处理，简单处理，修改了第一定价期的列，才重置租金分录	
					//					reSetRentSetInfo(getTenRoomListFromView());
				}
				reSetRentSetInfo(getTenRoomListFromView(), getTenAttachResListFromView(),false);
			}else if(colKey.startsWith(PREFIX_C_RENT_RENT_PRICE) || colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)){
				ICell cell = row.getCell(colIndex);
				IDealAmountInfo dealAmount = (IDealAmountInfo) cell.getUserObject();

				BigDecimal price = (BigDecimal) e.getValue();
				dealAmount.setPriceAmount(price);

				TenancyModeEnum tenancyModel = dealAmount.getTenancyRoom().getTenancyModel();
				BigDecimal area = tenEntry.getBuildingArea();
				
				//改 by zhendui_ai   也就是现在没有了
//				if(TenancyModeEnum.RoomAreaMode.equals(tenancyModel)){
//					area = tenEntry.getRoomArea();
//				}
				if(area == null) area = FDCHelper.ZERO;
				BigDecimal amount = null;
				//还要反算总价
				if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
					if(price == null){
						price = FDCHelper.ZERO;
					}

					amount = price.multiply(area).setScale(2, BigDecimal.ROUND_HALF_UP);

					ICell amountCell = row.getCell(colIndex + 1);
					amountCell.setValue(amount);
					dealAmount.setAmount(amount);
				}

				//如果是第一定价期的列，需要更新到租赁房间的成交总价上去. TODO 这里用colIndex=6来判断不太好 
				if(colIndex == 4+monDefineColl.size()){
					tenEntry.setDealRent(amount);
					tenEntry.setDealRentPrice(price);
					//这里不好处理，简单处理，修改了第一定价期的列，才重置租金分录	
					//					reSetRentSetInfo(getTenRoomListFromView());
				}
				//因为周期性费用也需要递增，所以改了周期性费用也需要重置租金分录
				reSetRentSetInfo(getTenRoomListFromView(), getTenAttachResListFromView(),false);
			}
		}	
		initRentTypeLock();
	}
	
	/**
	 * 根据开始日期、新结束日期和新递增明细生成新租金明细
	 * */
	private void reSetRentNewSetInfo(TenancyRoomEntryCollection tenRooms,TenAttachResourceEntryCollection tenAttaches) {
		//取从界面上的值封装递增列表分录集合
		NewIncRentEntryCollection incNewCol = getNewIncRentEntryCollection();
		TenancyHelper.sortNewIncRentEntryCollectionByIncDate(incNewCol);
		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkNewEndDate.getValue();
		if (startDate == null || endDate == null) {
			return;
		}
		startDate = FDCDateHelper.getDayBegin(startDate);
		endDate = FDCDateHelper.getDayBegin(endDate);

		// 重新设置租金分录.
		Map tmp = parseIncreasedDate(startDate, endDate, incNewCol);
		if (tmp == null) {
			return;
		}
		TenancyBillInfo tenBill = (TenancyBillInfo) this.prmtTenancy.getValue();
		try {
			tenBill = TenancyHelper.getTenancyBillInfo(tenBill.getId().toString());
		} catch (BOSException ex) {
			handleException(ex);
		} catch (EASBizException e) {
			handleException(e);
		}
		if (tenBill == null) {
			return;
		}
		
		for(int i=0;i<tblRent.getRowCount();i++)
		{
			Map map = new HashMap();
			IRow row = tblRent.getRow(i);
			TenancyRoomEntryInfo tenRoom = new TenancyRoomEntryInfo();
			if(row.getUserObject()!=null)
			{
				tenRoom = (TenancyRoomEntryInfo)row.getUserObject();
			}else
			{
				break;
			}
			for(int j=0;j<monDefineColl.size();j++)
			{
				MoneyDefineInfo moneyInfo = monDefineColl.get(j);
				BigDecimal disAmount = new BigDecimal(0);
				if(row.getCell(moneyInfo.getNumber()).getValue()==null)
				{
//					BigDecimal disAmount = (BigDecimal)row.getCell(moneyInfo.getNumber()).getValue();
					map.put(moneyInfo, disAmount);
				}else
				{
					disAmount = (BigDecimal)row.getCell(moneyInfo.getNumber()).getValue();
					map.put(moneyInfo, disAmount);
				}
			}
			roomDisMap.put(tenRoom.getRoom().getId().toString(), map);
		}
		
		// 根据房间分录和定价时间段生成租赁房间的成交租金分录
		NewDealAmountEntryCollection dealNewAmounts = new NewDealAmountEntryCollection();
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			RentTypeEnum currentRentType = tenRoom.getDealRentType();
			BigDecimal currentRent = FDCHelper.ZERO;
			TenancyModeEnum tenancyModel = tenRoom.getTenancyModel();
			BigDecimal area = tenRoom.getBuildingArea();
//			if (TenancyModeEnum.RoomAreaMode.equals(tenancyModel)) {
//				area = tenRoom.getRoomArea();
//			}
			if (area == null) {
				area = FDCHelper.ZERO;
			}
			if(roomDisMap.get(tenRoom.getRoom().getId().toString())!=null)
			{
				Map map = (Map)roomDisMap.get(tenRoom.getRoom().getId().toString());
				for(Iterator itor = map.keySet().iterator(); itor.hasNext();)
				{
					MoneyDefineInfo moneyInfo = (MoneyDefineInfo)itor.next();
					BigDecimal disAmount = (BigDecimal)map.get(moneyInfo);
					NewDealAmountEntryInfo dealAmount = new NewDealAmountEntryInfo();
					dealAmount.setTenancyRoom(tenRoom);
					dealAmount.setMoneyDefine(moneyInfo);
					dealAmount.setAmount(disAmount);
					dealNewAmounts.addObject(dealAmount);
				}
			}
			for (Iterator itor = tmp.keySet().iterator(); itor.hasNext();) {
				Date[] dates = (Date[]) itor.next();
				NewIncRentEntryInfo increased = (NewIncRentEntryInfo) tmp.get(dates);
				boolean isHandwork = true;
				if (increased == null) {
					currentRent = tenRoom.getDealRoomRent();
				} else {
					if(currentRent==null){
						currentRent= FDCHelper.ZERO;
					}
					IncreasedRentTypeEnum increasedType = increased.getIncreaseType();
					BigDecimal value = increased.getValue();
					if (value == null) {// 手输的情况下该值为0
						value = FDCHelper.ZERO;
					}
					if (IncreasedRentTypeEnum.Percent.equals(increasedType)) {
						currentRent = currentRent.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
						isHandwork = false;
					} else if (IncreasedRentTypeEnum.totalRent.equals(increasedType)) {
						currentRent = currentRent.add(value);
						isHandwork = false;
					} else if (IncreasedRentTypeEnum.rentPrice.equals(increasedType)) {
						currentRent = currentRent.add(value.multiply(area));
						isHandwork = false;
					} else if (IncreasedRentTypeEnum.Handwork.equals(increasedType)) {
						// 手工填写的，默认租金为原租金
					}
				}
				NewDealAmountEntryInfo dealAmount = new NewDealAmountEntryInfo();
				dealAmount.setIsHandwork(isHandwork);
				try {
					dealAmount.setMoneyDefine(getRentMoneyDefine());
				} catch (BOSException e) {
					handleException(e);
				}
				dealAmount.setStartDate(dates[0]);
				dealAmount.setEndDate(dates[1]);
				dealAmount.setRentType(currentRentType);
				dealAmount.setAmount(currentRent);
				dealAmount.setTenancyRoom(tenRoom);
				if (currentRent != null) {
					BigDecimal buildingArea = tenRoom.getBuildingArea();
					if (buildingArea != null && buildingArea.compareTo(FDCHelper.ZERO) != 0) {
						BigDecimal rentPrice = currentRent.divide(buildingArea, 2, BigDecimal.ROUND_HALF_UP);
						dealAmount.setPriceAmount(rentPrice);
					}
				}
				dealNewAmounts.add(dealAmount);
			}
			this.editData.getNewDealAmountEntry().clear();
			this.editData.getNewDealAmountEntry().addCollection(dealNewAmounts);
		}
		
		// 根据配套资源分录和定价时间段生成配套资源的成交租金分录
		NewAttachDealAmountEntryCollection attaNewAmounts = new NewAttachDealAmountEntryCollection();
		for (int i = 0; i < tenAttaches.size(); i++) {
			TenAttachResourceEntryInfo tenAtta = tenAttaches.get(i);
			RentTypeEnum currentRentType = tenAtta.getDealRentType();
			BigDecimal currentRent = null;
			for (Iterator itor = tmp.keySet().iterator(); itor.hasNext();) {
				Date[] dates = (Date[]) itor.next();
				NewIncRentEntryInfo increased = (NewIncRentEntryInfo) tmp.get(dates);
				boolean isHandwork = true;
				if (increased == null) {
					currentRent = tenAtta.getDealAttachResRent();
				} else {
					IncreasedRentTypeEnum increasedType = increased.getIncreaseType();
					BigDecimal value = increased.getValue();
					if (value == null) {// 手输的情况下该值为0
						value = FDCHelper.ZERO;
					}
					if (IncreasedRentTypeEnum.Percent.equals(increasedType)) {
						currentRent = currentRent.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
						isHandwork = false;
					} else if (IncreasedRentTypeEnum.totalRent.equals(increasedType)) {
						currentRent = currentRent.add(value);
						isHandwork = false;
					} else if (IncreasedRentTypeEnum.Handwork.equals(increasedType)) {
						// 手工填写的，默认租金为原租金
					}
				}
				NewAttachDealAmountEntryInfo attaAmount = new NewAttachDealAmountEntryInfo();
				attaAmount.setIsHandwork(isHandwork);
				try {
					attaAmount.setMoneyDefine(getRentMoneyDefine());
				} catch (BOSException e) {
					handleException(e);
				}
				attaAmount.setStartDate(dates[0]);
				attaAmount.setEndDate(dates[1]);
				attaAmount.setRentType(currentRentType);
				attaAmount.setAmount(currentRent);
				attaAmount.setTenancyAtta(tenAtta);
				attaNewAmounts.add(attaAmount);
			}
		}
//		loadDealAmount(this.tblRent, getTenRoomListFromView(), dealNewAmounts, getTenAttachResListFromView(), attaNewAmounts);
	}

	
	private Date getStartDate()
	{
		Date startDate = (Date) this.pkStartDate.getValue();
		startDate = FDCDateHelper.getDayBegin(startDate);
		return startDate;
	}
	
	private Date getEndate()
	{
		Date endDate = (Date) this.pkNewEndDate.getValue();
		endDate = FDCDateHelper.getDayBegin(endDate);
		return endDate;
	}
	
	private NewIncRentEntryCollection getNewIncRentEntryCollection()
	{
		NewIncRentEntryCollection incNewCol = new NewIncRentEntryCollection();
		for (int i = 0; i < this.tblIncrease.getRowCount(); i++) {
			IRow rowInc = this.tblIncrease.getRow(i);
			Date increaseDate = (Date) rowInc.getCell(C_INC_INCREASE_DATE).getValue();
			if (increaseDate == null) {
				continue;
			}
			IncreasedRentTypeEnum increasedRentType = (IncreasedRentTypeEnum) rowInc.getCell(C_INC_INCREASE_TYPE).getValue();
			BigDecimal value = (BigDecimal) rowInc.getCell(C_INC_VALUE).getValue();

			if (!IncreasedRentTypeEnum.Handwork.equals(increasedRentType) && value == null) {
				continue;
			}
			NewIncRentEntryInfo incNewInfo = new NewIncRentEntryInfo();
			incNewInfo.setIncreaseDate((Date) rowInc.getCell(C_INC_INCREASE_DATE).getValue());
			incNewInfo.setIncreaseType((IncreasedRentTypeEnum) rowInc.getCell(C_INC_INCREASE_TYPE).getValue());
			incNewInfo.setValue(FDCHelper.toBigDecimal(rowInc.getCell(C_INC_VALUE).getValue()));
			incNewCol.add(incNewInfo);
		}
		return incNewCol;
	}
	
	/**
	 * 从tblRoom界面上获取租赁房间集合
	 * */
	private TenancyRoomEntryCollection getTenRoomListFromView() {
		TenancyRoomEntryCollection tenancyRooms = new TenancyRoomEntryCollection();// 房间

		for (int i = 0; i < this.tblRent.getRowCount(); i++) {
			IRow row = this.tblRent.getRow(i);
			if(row.getUserObject() instanceof TenancyRoomEntryInfo)
			{
				TenancyRoomEntryInfo tenancyRoom = (TenancyRoomEntryInfo) row.getUserObject();
				tenancyRooms.add(tenancyRoom);
			}
		}

		return tenancyRooms;
	}

	/**
	 * 从tblAttachRes界面上获得附属资源集合
	 * */
	private TenAttachResourceEntryCollection getTenAttachResListFromView() {
		TenAttachResourceEntryCollection tenAttachReses = new TenAttachResourceEntryCollection();
		for (int i = 0; i < this.tblRent.getRowCount(); i++) {
			IRow row = this.tblRent.getRow(i);
			if (row.getUserObject() instanceof TenAttachResourceEntryInfo) {
				TenAttachResourceEntryInfo tenAttachRes = (TenAttachResourceEntryInfo) row
						.getUserObject();
				tenAttachReses.add(tenAttachRes);
			}

		}
		return tenAttachReses;
	}
	

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return TenancyModificationFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	public boolean useScrollPane() {
		return true;
	}
	
	public int getVerticalScrollPolicy() {
		return ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
	}
	
	private boolean isMoney(String colKey,MoneyDefineCollection monDefineColl)
	{
		boolean boo = false;
		for(int i=0;i<monDefineColl.size();i++)
		{
			MoneyDefineInfo money = monDefineColl.get(i);
			if(money.getNumber().equals(colKey))
			{
				boo = true;
				break;
			}
		}
		return boo;
	}
	
	private void rentSetStyle()
	{
		for(int i=0;i<tblRent.getRowCount();i++)
		{
			IRow row = tblRent.getRow(i);
			for(int j=0;j<this.tblRent.getColumnCount();j++)
			{
				IColumn column = this.tblRent.getColumn(j);
				String colKey = column.getKey();
				if(C_RENT_RENT_TYPE.equals(colKey)){
					row.getCell(C_RENT_RENT_TYPE).getStyleAttributes().setLocked(true);
				}else if(C_RENT_TENANCY_MODEL.equals(colKey))
				{
					row.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(false);
					TenancyModeEnum tenModel = (TenancyModeEnum) row.getCell(C_RENT_TENANCY_MODEL).getValue();
					if(TenancyModeEnum.TenancyRentModel.equals(tenModel))
					{
						for(int m=0;m<tblRent.getColumnCount();m++)
						{
							String tColKey = this.tblRent.getColumnKey(m);
							ICell cell = row.getCell(tColKey);
							if(tColKey.startsWith(PREFIX_C_RENT_RENT_PRICE)){
								cell.getStyleAttributes().setLocked(true);
							}else if(tColKey.startsWith(PREFIX_C_RENT_RENT)){
								cell.getStyleAttributes().setLocked(false);
							}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY)){
								cell.getStyleAttributes().setLocked(false);
							}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)){
								cell.getStyleAttributes().setLocked(true);
							}
						}
					}else{
						for(int m=0; m<this.tblRent.getColumnCount(); m++){
							String tColKey = this.tblRent.getColumnKey(m);
							ICell cell = row.getCell(tColKey);
							if(tColKey.startsWith(PREFIX_C_RENT_RENT_PRICE)){
								cell.getStyleAttributes().setLocked(false);
							}else if(tColKey.startsWith(PREFIX_C_RENT_RENT)){
								cell.getStyleAttributes().setLocked(true);
							}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY)){
								cell.getStyleAttributes().setLocked(true);
							}else if(tColKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)){
								cell.getStyleAttributes().setLocked(false);
							}
						}
					}
				}
			}

		}
	}
	
	private void setIsbtnPayList(boolean isFreeContract)
	{

		for(int i=0;i<tblIncrease.getRowCount();i++)
		{
			IRow row = tblIncrease.getRow(i);
			row.getStyleAttributes().setLocked(isFreeContract);
		}
		
		for(int i=0;i<tblFree.getRowCount();i++)
		{
			IRow row = tblFree.getRow(i);
			row.getStyleAttributes().setLocked(isFreeContract);
		}
	}
	
	private void setOtherFreeContract(boolean isFreeContract)
	{
		this.btnAddIncrease.setEnabled(isFreeContract);
		this.btnRmIncrease.setEnabled(isFreeContract);
		this.btnAddFree.setEnabled(isFreeContract);
		this.btnRmFree.setEnabled(isFreeContract);
	}
	
	private ITenancyPayListInfo toSale(ITenancyPayListInfo object,ITenancyPayListInfo roomPay)
	{
		roomPay.setMoneyDefine(object.getMoneyDefine());
		roomPay.setStartDate(object.getStartDate());
		roomPay.setEndDate(object.getEndDate());
		roomPay.setAppPayDate(object.getAppPayDate());
		return roomPay;
	}
	 public void setOprtState(String oprtType)
	    {
	        super.setOprtState(oprtType);
	        if (STATUS_ADDNEW.equals(this.oprtState)) {
			            this.actionCancel.setEnabled(false);
			            this.actionCancelCancel.setEnabled(false);
			            this.actionRemove.setEnabled(false);
			            this.actionEdit.setEnabled(false);
	        } else if (STATUS_EDIT.equals(this.oprtState)) {
			            this.actionEdit.setEnabled(false);
	        } else if (STATUS_VIEW.equals(this.oprtState)) {
			            this.actionSave.setEnabled(false);
			            this.actionSubmit.setEnabled(false);
			            this.actionCancel.setEnabled(false);
			            this.actionCancelCancel.setEnabled(false);
			            this.actionUnAudit.setVisible(false);
			            this.actionRemove.setEnabled(true);
			            this.actionEdit.setEnabled(true);
			            this.actionCopy.setEnabled(true);
	        }
	    }
}