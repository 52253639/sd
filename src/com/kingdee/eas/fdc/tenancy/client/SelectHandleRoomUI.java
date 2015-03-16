package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryFactory;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.HandleRoomEntrysInfo;
import com.kingdee.eas.fdc.tenancy.HandleStateEnum;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

public class SelectHandleRoomUI extends AbstractSelectHandleRoomUI
{
	private static final Logger logger = CoreUIObject.getLogger(SelectHandleRoomUI.class);

	public SelectHandleRoomUI() throws Exception
	{
		super();
	}

	protected void inOnload() throws Exception {
		// super.inOnload();
	}

	public void onLoad() throws Exception {
		initControl();
		this.tblRooms.checkParsed();
		initUI();
		if(pnlMain.getTabCount() == 2){
			this.pnlMain.remove(1);
		}
		TenancyBillInfo tenancyBillInfo = (TenancyBillInfo)this.getUIContext().get("tenancyBillInfo");
		if(tenancyBillInfo!=null)
		{
			TenancyRoomEntryCollection tenRoomColl = tenancyBillInfo.getTenancyRoomList();
			TenAttachResourceEntryCollection tenAttachColl = tenancyBillInfo.getTenAttachResourceList();
			if(tenAttachColl.size()+tenRoomColl.size()==0)
			{
				logger.warn("该合同所对应的房间和配套资源没了！晕倒...");
			}else
			{
				for(int i=0;i<tenRoomColl.size();i++)
				{
					if(tenRoomColl.get(i).getRoom() != null)
					{
						TenancyRoomEntryInfo tenancyRoomEntryInfo = tenRoomColl.get(i);
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("*");
						sels.add("tenancy.*");
						TenancyRoomEntryInfo tenRoomEntryInfo = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryInfo(new ObjectUuidPK(tenancyRoomEntryInfo.getId()),sels);
						tenRoomEntryInfo.setTenancy(tenancyBillInfo);
						this.tblRooms.checkParsed();
						IRow row = this.tblRooms.addRow();

						row.setUserObject(tenRoomEntryInfo);

						setRoomHandleStateOnRow(row, tenRoomEntryInfo,tenancyBillInfo);
					}
				}
				for(int i=0;i<tenAttachColl.size();i++)
				{
					if(tenAttachColl.get(i).getAttachResource()!=null)
					{
						TenAttachResourceEntryInfo tenAttachEntryInfo = tenAttachColl.get(i);
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("*");
						sels.add("tenancyBill.*");
						TenAttachResourceEntryInfo tenAttEntryInfo = TenAttachResourceEntryFactory.getRemoteInstance().getTenAttachResourceEntryInfo(new ObjectUuidPK(tenAttachEntryInfo.getId()),sels);
						tenAttEntryInfo.setTenancyBill(tenancyBillInfo);
						this.tblAttach.checkParsed();
						IRow row = this.tblAttach.addRow();
						
						row.setUserObject(tenAttEntryInfo);
						setAttachHandleStateOnRow(row,tenAttEntryInfo);
					}
				}
			}
			
		}
	}
	
	/*
	 * 显示合同中的配套资源信息
	 * */
	private void setAttachHandleStateOnRow(IRow row,TenAttachResourceEntryInfo info)
	{
		row.getCell("attachNumber").setValue(info.getAttachLongNum());
		row.getCell("item").setValue(Boolean.valueOf(false));
		row.getCell("tenancyState").setValue(info.getTenAttachState());
		if(info.getActDeliveryAttachResDate()==null)
		{
			row.getCell("handleDate").setValue(null);
		}else
		{
			row.getCell("handleDate").setValue(info.getActDeliveryAttachResDate());
		}
		row.getCell("handleState").setValue(info.getHandleState());
	}

	/*
	 * 显示合同中的房间信息
	 * */
	private void setRoomHandleStateOnRow(IRow row, TenancyRoomEntryInfo tenancyRoomEntryInfo,TenancyBillInfo billInfo) throws BOSException, EASBizException{
		row.setUserObject(tenancyRoomEntryInfo);
		row.getCell("roomNumber").setValue(tenancyRoomEntryInfo.getRoomLongNum());
		row.getCell("item").setValue(Boolean.valueOf(false));
		row.getCell("tenancyState").setValue(tenancyRoomEntryInfo.getTenRoomState());
		if(tenancyRoomEntryInfo.getActDeliveryRoomDate()==null)
		{
			row.getCell("handleDate").setValue(null);
		}else
		{
			row.getCell("handleDate").setValue(tenancyRoomEntryInfo.getActDeliveryRoomDate());
		}
		row.getCell("handleState").setValue(tenancyRoomEntryInfo.getHandleState());
	}

	public void storeFields()
	{
		super.storeFields();
	}

	/**
	 * 初始化单元
	 *
	 */
	private void initUI()
	{
		KDCheckBox roomItem = new KDCheckBox();
		roomItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				//这个用法太神奇了，只是为了让复选框的 焦点转移，以便第二次单击的时候，来触发 table_Clicked事件
				pnlMain.requestFocus();
			}
		});
		ICellEditor roomEditorItem = new KDTDefaultCellEditor(roomItem);
		this.tblRooms.getColumn("item").setEditor(roomEditorItem);
		this.tblRooms.getColumn("handleDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		for(int i = 1; i < this.tblRooms.getColumnCount(); i ++)
		{
			this.tblRooms.getColumn(i).getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * 隐藏多余按钮
	 */
	private void initControl() {
		this.btnPrint.setEnabled(true);
		this.btnPrintPreview.setEnabled(true);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemSave.setVisible(false);
		this.menuItemSubmit.setVisible(false);
		this.rMenuItemSubmit.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.rMenuItemSubmitAndPrint.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnSave.setVisible(false);
		this.btnSubmit.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnLast.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionPrint.setVisible(false);

	}

	/**
	 * 描述：提交需要交接的房间和配套资源信息
	 * <p>
	 * 
	 * 修改人：<tim_gao> <p>
	 * 修改时间：<2010/8/29> <p>
	 * 修改描述：<488行,把要提交的房但不在新合同的房从循环中提出> <p>
	 */

	protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		super.btnConfirm_actionPerformed(e);
		List list = new ArrayList();
		List attachList = new ArrayList();
		if(this.tblRooms.getRowCount()>0)
		{
			for(int i=0;i<this.tblRooms.getRowCount();i++)
			{
				IRow row = tblRooms.getRow(i);
				if(row.getCell("item").getValue().equals(Boolean.valueOf(true)))
				{
					//租赁房间分录
					TenancyRoomEntryInfo tenRoomEntrys = (TenancyRoomEntryInfo)row.getUserObject();
					//交接分录
					HandleRoomEntrysInfo handleRoomEntrys = new HandleRoomEntrysInfo();
					String roomid = tenRoomEntrys.getRoom().getId().toString();
					DealAmountEntryCollection dealColl = tenRoomEntrys.getDealAmounts();
					BigDecimal depositAmount = new BigDecimal(0);
					for(int j=0;j<dealColl.size();j++)
					{
						DealAmountEntryInfo dealInfo = dealColl.get(j);
						SelectorItemCollection sel = new SelectorItemCollection();
						sel.add("*");
						sel.add("moneyDefine.*");
						dealInfo = DealAmountEntryFactory.getRemoteInstance().getDealAmountEntryInfo(new ObjectUuidPK(dealInfo.getId()), sel);
						if(MoneyTypeEnum.DepositAmount.equals(dealInfo.getMoneyDefine().getMoneyType()))
						{
							BigDecimal amount = dealInfo.getAmount();
							amount = amount==null?new BigDecimal(0):amount;
							depositAmount = depositAmount.add(amount);
						}
					}
					//押金和首付
//					BigDecimal depositAmount = tenRoomEntrys.getDepositAmount();
//					depositAmount = depositAmount==null?new BigDecimal(0):depositAmount;
					BigDecimal firstPayRent = tenRoomEntrys.getFirstPayAmount();
					firstPayRent = firstPayRent==null?new BigDecimal(0):firstPayRent;
					BigDecimal rent = depositAmount.add(firstPayRent);
					//租赁合同
					TenancyBillInfo info = tenRoomEntrys.getTenancy();
					//该房间已交租金金额
					BigDecimal recRent = TenancyHelper.getRecAmount(roomid,info.getId().toString());
					if(recRent==null)
					{
						recRent = new BigDecimal(0);
					}
					TenancyBillStateEnum tenState = info.getTenancyState();
					//交接类型为交房的只有已审批并且收够款的合同和部分执行且要交接的房间收够款的合同
					if(TenancyBillStateEnum.Audited.equals(tenState) || TenancyBillStateEnum.PartExecuted.equals(tenState))
					{
							TenancyBillInfo oldBillInfo = info.getOldTenancyBill();
							//如果新合同是转名的话看看房间收回来没有
							if(TenancyContractTypeEnum.ChangeName.equals(info.getTenancyType()))
							{
								//如果原合同不是已终止状态说明还有房间没有收回来
								if(!TenancyBillStateEnum.Expiration.equals(oldBillInfo.getTenancyState()))
								{
									MsgBox.showInfo("原合同房间还没有收回来");
									this.abort();
								}else
								{
									//找到原合同
									TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(oldBillInfo.getId().toString());
									TenancyRoomEntryCollection tenEntryColl = targetTen.getTenancyRoomList();
									//查看交接的房间是否在原合同房间范围内
									for(int k=0;k<tenEntryColl.size();k++)
									{
										TenancyRoomEntryInfo tenRoomInfo = tenEntryColl.get(k);
										if(tenRoomInfo.getRoom().getId().toString().equals(roomid))
										{
//											//看看收够钱了没有
//											if(recRent.equals(new BigDecimal(0)))
//											{
//												MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"还没有交租");
//												this.abort();
//											}else 
//											if(recRent.compareTo(rent)<0)
//											{
//												MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"没有交齐首租押金");
//												this.abort();
//											}else 
											if(tenRoomEntrys.getActDeliveryRoomDate()!=null)
											{
												MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"已经交过房了");
												this.abort();
											}else if(tenRoomEntrys.getActDeliveryRoomDate()==null)
											{
//											else if(recRent.compareTo(rent)>=0 && tenRoomEntrys.getActDeliveryRoomDate()==null)
//											{
												handleRoomEntrys.setHandleType("交房");
												handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
												handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
												list.add(handleRoomEntrys);
												break;
											}              					
										}
									}
								}
								//如果原合同是转租或者续租的合同的话，交接类型就为转租交接和续租交接
							}else if(oldBillInfo!=null && (TenancyBillStateEnum.ContinueTenancying.equals(oldBillInfo.getTenancyState())
									|| TenancyBillStateEnum.RejiggerTenancying.equals(oldBillInfo.getTenancyState())))
							{
								//转租或者续租的话只能对新合同来操作，在满足交房条件的同时把原合同的房间收了，把租赁状态反写到租赁合同房间分录和房间上
								TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(oldBillInfo.getId().toString());
								TenancyRoomEntryCollection tenEntryColl = targetTen.getTenancyRoomList();
								List roomidlist = new ArrayList();
								for(int k=0;k<tenEntryColl.size();k++)
								{
									TenancyRoomEntryInfo tenRoomEnInfo = tenEntryColl.get(k);
									roomidlist.add(tenRoomEnInfo.getRoom().getId().toString());
								}
								for(int k=0;k<tenEntryColl.size();k++)
								{
									//TenancyRoomEntryInfo tenRoomEnInfo = tenEntryColl.get(k);
									//转租或者续租的房间在原合同里也有的叫转租或续租交接
									if(TenancyClientHelper.isInclude(roomid, roomidlist))
									//if(tenRoomEnInfo.getRoom().getId().toString().equals(roomid))
									{
										//看看收够钱了没有
//										if(recRent.equals(new BigDecimal(0)))
//										{
//											MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"还没有交租");
//											this.abort();
//										}else 
//										if(recRent.compareTo(rent)<0)
//										{
//											MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"没有交齐首租押金");
//											this.abort();
//										}else 
										if(tenRoomEntrys.getActDeliveryRoomDate()!=null)
										{
											MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"已经交过房了");
											this.abort();
										}else if(tenRoomEntrys.getActDeliveryRoomDate()==null)
										{
//										else if(recRent.compareTo(rent)>=0 && tenRoomEntrys.getActDeliveryRoomDate()==null)
//										{
											handleRoomEntrys.setHandleType(info.getTenancyType()+"交接");
											handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
											handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
											list.add(handleRoomEntrys);
											break;
										}  
									}else
									{
										//原合同中没有房间就是普通的交房操作
										//看看收够钱了没有
//										if(recRent.equals(new BigDecimal(0)))
//										{
//											MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"还没有交租");
//											this.abort();
//										}else 
//										if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//										{
//											MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"没有交齐首租押金");
//											this.abort();
//										}
//										if(recRent.compareTo(rent)<0)
//										{
//											MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"没有交齐首租押金");
//											this.abort();
//										}else 
										if(tenRoomEntrys.getActDeliveryRoomDate()!=null)
										{
											MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"已经交过房了");
											this.abort();
										}else if(tenRoomEntrys.getActDeliveryRoomDate()==null)
										{
//										else if(recRent.compareTo(rent)>=0 && tenRoomEntrys.getActDeliveryRoomDate()==null)
//										{
											handleRoomEntrys.setHandleType("交房");
											handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
											handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
											list.add(handleRoomEntrys);
											break;
										}  
									}

								}
							}else
							{
								//这里说明是直接新增的合同，要交房的话需要满足收购款的条件
//								if(recRent.equals(new BigDecimal(0)))
//								{
//									MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"还没有交租");
//									this.abort();
//								}else 
//								if(recRent.compareTo(rent)<0)
//								{
//									MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"没有交齐首租押金");
//									this.abort();
//								}else 
								if(tenRoomEntrys.getActDeliveryRoomDate()!=null)
								{
									MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"已经交过房了");
									this.abort();
								}
								else if(tenRoomEntrys.getActDeliveryRoomDate()==null)
								{
//								else if(recRent.compareTo(rent)>=0 && tenRoomEntrys.getActDeliveryRoomDate()==null)
//								{
									handleRoomEntrys.setHandleType("交房");
									handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
									handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
									list.add(handleRoomEntrys);
								}
							}
					}else if(TenancyBillStateEnum.RejiggerTenancying.equals(tenState)
							|| TenancyBillStateEnum.ContinueTenancying.equals(tenState))
					{
						String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(info.getId().toString());
						TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(targetTenId);
						TenancyRoomEntryCollection tenEntryColl = targetTen.getTenancyRoomList();
						//如果新合同中房间个数为0.说明新合同只租了配套资源，那么原合同的中房间可以直接收回
						if(tenEntryColl.size()==0)
						{
							handleRoomEntrys.setHandleType("收房");
							handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
							handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
							list.add(handleRoomEntrys);
						}else
						{
							if(this.getQuitTenancyInfoByTenancyBill(targetTen))
							{
								for(int m=0;m<tenEntryColl.size();m++)
								{
									TenancyRoomEntryInfo tenInfo = tenEntryColl.get(m);
									if(roomid.equals(tenInfo.getRoom().getId().toString()) && (HandleStateEnum.AlreadyCallBack.equals(tenInfo.getHandleState())
											|| HandleStateEnum.livingHouse.equals(tenInfo.getHandleState())))
									{
										MsgBox.showInfo("该房间已经在新合同中交接了");
										this.abort();
									}else if(roomid.equals(tenInfo.getRoom().getId().toString()))
									{
										handleRoomEntrys.setHandleType("收房");
										handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
										handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
										list.add(handleRoomEntrys);
									}
								}
								
							}else
							{
								for(int k=0;k<tenEntryColl.size();k++)
								{
									TenancyRoomEntryInfo tenRoomInfo = tenEntryColl.get(k);
									if(tenRoomInfo.getRoom().getId().toString().equals(roomid))
									{
										MsgBox.showInfo(targetTen.getTenancyType()+"交接的房间只能到新合同中去交接");
										this.abort();
									}else
									{
										if(TenancyBillStateEnum.Saved.equals(targetTen.getTenancyState()) || TenancyBillStateEnum.Submitted.equals(targetTen.getTenancyState())
												|| TenancyBillStateEnum.BlankOut.equals(targetTen.getTenancyState()))
										{
											MsgBox.showInfo("目标合同必须是已审批或审批过的合同才能收房");
											this.abort();
										}
									}
								} //除了新合同中要交接的房，其他的房应该放在循环外，不然要跟着新合同中的交接方走循环     tim_gao
								handleRoomEntrys.setHandleType("收房");
								handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
								handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
								list.add(handleRoomEntrys);
							}
						}
						//添加价格变更 xin_wang 2010.12.22										             		
					}else if(TenancyBillStateEnum.ChangeNaming.equals(tenState)||TenancyBillStateEnum.PriceChanging.equals(tenState))
					{
						if(HandleStateEnum.AlreadyCallBack.equals(tenRoomEntrys.getHandleState()))
						{
							MsgBox.showInfo("房间已经收回");
							this.abort();
						}else if(HandleStateEnum.CallBacking.equals(tenRoomEntrys.getHandleState()))
						{
							MsgBox.showInfo("房间正在回收");
							this.abort();
						}else
						{
							handleRoomEntrys.setHandleType("收房");
							handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
							handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
							list.add(handleRoomEntrys);
						}              		
					}
					else if(TenancyBillStateEnum.QuitTenancying.equals(tenState))
					{
						if(HandleStateEnum.AlreadyCallBack.equals(tenRoomEntrys.getHandleState()))
						{
							MsgBox.showInfo("房间已经收回");
							this.abort();
						}else if(HandleStateEnum.CallBacking.equals(tenRoomEntrys.getHandleState()))
						{
							MsgBox.showInfo("房间正在回收");
							this.abort();
						}
						else
						{
							if(this.getQuitTenancyInfoByTenancyBill(info) && tenRoomEntrys.getHandleState().equals(HandleStateEnum.NoHandleRoom))
							{
								MsgBox.showInfo("存在审批状态的退租单不能再进行交房");
								this.abort();
							}else if(!this.getQuitTenancyInfoByTenancyBill(info) && tenRoomEntrys.getHandleState().equals(HandleStateEnum.NoHandleRoom))
							{
								TenancyRoomEntryCollection tenEntryColl = info.getTenancyRoomList();
								for(int m=0;m<tenEntryColl.size();m++)
								{
									TenancyRoomEntryInfo tenInfo = tenEntryColl.get(m);
									if(tenInfo.getRoom().getId().toString().equals(roomid))
									{
										//看看收够钱了没有
//										if(recRent.equals(new BigDecimal(0)))
//										{
//											MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"还没有交租");
//											this.abort();
//										}else 
//										if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//										{
//											MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"没有交齐首租押金");
//											this.abort();
//										}else 
										if(tenRoomEntrys.getActDeliveryRoomDate()!=null)
										{
											MsgBox.showInfo("房间"+tenRoomEntrys.getRoomLongNum()+"已经交过房了");
											this.abort();
										}else if(tenRoomEntrys.getActDeliveryRoomDate()==null)
										{
//										else if(recRent.compareTo(rent)>=0 && tenRoomEntrys.getActDeliveryRoomDate()==null)
//										{
											handleRoomEntrys.setHandleType(info.getTenancyType()+"交接");
											handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
											handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
											list.add(handleRoomEntrys);
											break;
										}  
									}
								}
							}else
							{
								handleRoomEntrys.setHandleType("收房");
								handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
								handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
								list.add(handleRoomEntrys);
							}
						}
					}
					else if(TenancyBillStateEnum.TenancyChanging.equals(tenState))
					{
						handleRoomEntrys.setHandleType("收房");
						handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
						handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
						list.add(handleRoomEntrys);
					}else
					{
						MsgBox.showInfo(tenRoomEntrys.getRoomLongNum()+"不符合房间交接的条件！");
						abort();
					}
				}
			}
			this.getUIContext().put("list", list);
		}
		if(this.tblAttach.getRowCount()>0)
		{
			for(int i=0;i<this.tblAttach.getRowCount();i++)
			{
				IRow row = tblAttach.getRow(i);
				if(row.getCell("item").getValue().equals(Boolean.valueOf(true)))
				{
					TenAttachResourceEntryInfo tenAttEntryInfo = (TenAttachResourceEntryInfo)row.getUserObject();
					this.setAttachHandle(tenAttEntryInfo,attachList);
				}				
			}
			this.getUIContext().put("attachList", attachList);
		}
		this.destroyWindow();       
	}
	
	/*
	 * 抽出来的方法，有时间再写
	 * */
//	private boolean validate(BigDecimal recRent,BigDecimal rent,HandleRoomEntrysInfo handleRoomEntrys,ITenancyEntryInfo tenancyEntryInfo,List list,TenancyContractTypeEnum tenConType)
//	{
//		boolean boo = false;
//		//看看收够钱了没有
//		if(recRent.equals(new BigDecimal(0)))
//		{
//			MsgBox.showInfo(tenancyEntryInfo.getLongNumber()+"还没有交租");
//			this.abort();
//		}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//		{
//			MsgBox.showInfo(tenancyEntryInfo.getLongNumber()+"没有交齐首租押金");
//			this.abort();
//		}else if(tenancyEntryInfo.getActDeliveryDate()!=null)
//		{
//			MsgBox.showInfo(tenancyEntryInfo.getLongNumber()+"已经交过房了");
//			this.abort();
//		}else if(recRent.compareTo(rent)>=0 && tenancyEntryInfo.getActDeliveryDate()==null)
//		{
//			handleRoomEntrys.setHandleType(tenConType+"交接");
//			handleRoomEntrys.setOldHandleState(tenancyEntryInfo.getHandleState());
//			if(tenancyEntryInfo instanceof TenancyRoomEntryInfo)
//			{
//				handleRoomEntrys.setTenancyRoom((TenancyRoomEntryInfo)tenancyEntryInfo);
//			}else
//			{
//				handleRoomEntrys.setAttach((TenAttachResourceEntryInfo)tenancyEntryInfo);
//			}			
//			list.add(handleRoomEntrys);
//			boo = true;
//		} 
//		return boo;
//	}
//	
//	private BigDecimal getRecRent(ITenancyEntryInfo itenEntryInfo,String tenancyBillID)
//	{
//		if(itenEntryInfo instanceof TenancyRoomEntryInfo)
//		{
//			return TenancyHelper.getRecAmount(((TenancyRoomEntryInfo)itenEntryInfo).getRoom().getId().toString(), tenancyBillID);
//		}else
//		{
//			return TenancyHelper.getRecAttachAmount(((TenAttachResourceEntryInfo)itenEntryInfo).getAttachResource().getId().toString(), tenancyBillID);
//		}
//	}
	
	//设置配套资源在各种合同下的交接状态
	private void setAttachHandle(TenAttachResourceEntryInfo tenAttEntryInfo,List attachList) throws EASBizException, BOSException, SQLException
	{
		HandleRoomEntrysInfo handleRoomEntrys = new HandleRoomEntrysInfo();
		String attachid = tenAttEntryInfo.getAttachResource().getId().toString();
		BigDecimal depositAmount = tenAttEntryInfo.getDepositAmount();
		depositAmount = depositAmount==null?new BigDecimal(0):depositAmount;
		BigDecimal firstPayRent = tenAttEntryInfo.getFirstPayAmount();
		firstPayRent = firstPayRent==null?new BigDecimal(0):firstPayRent;
		BigDecimal rent = depositAmount.add(firstPayRent);
		TenancyBillInfo info = tenAttEntryInfo.getTenancyBill();
		//该房间已交租金金额
		BigDecimal recRent = TenancyHelper.getRecAttachAmount(attachid,info.getId().toString());
		if(recRent==null)
		{
			recRent = new BigDecimal(0);
		}
		TenancyBillStateEnum tenState = info.getTenancyState();
		//交接类型为交房的只有已审批并且收够款的合同和部分执行且要交接的房间收够款的合同
		if(TenancyBillStateEnum.Audited.equals(tenState) || TenancyBillStateEnum.PartExecuted.equals(tenState))
		{
				TenancyBillInfo oldBillInfo = info.getOldTenancyBill();
				//如果新合同是转名的话看看房间收回来没有
				if(TenancyContractTypeEnum.ChangeName.equals(info.getTenancyType()))
				{
					if(!TenancyBillStateEnum.Expiration.equals(oldBillInfo.getTenancyState()))
					{
						MsgBox.showInfo("原合同配套资源还没有收回来");
						this.abort();
					}else
					{
						TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(oldBillInfo.getId().toString());
						TenAttachResourceEntryCollection tenEntryColl = targetTen.getTenAttachResourceList();
						for(int k=0;k<tenEntryColl.size();k++)
						{
							TenAttachResourceEntryInfo tenRoomInfo = tenEntryColl.get(k);
							if(tenRoomInfo.getAttachResource().getId().toString().equals(attachid))
							{
								//看看收够钱了没有
//								if(recRent.equals(new BigDecimal(0)))
//								{
//									MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"还没有交租");
//									this.abort();
//								}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//								{
//									MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"没有交齐首租押金");
//									this.abort();
//								}else 
								if(tenAttEntryInfo.getActDeliveryAttachResDate()!=null)
								{
									MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"已经交过房了");
									this.abort();
								}else if(tenAttEntryInfo.getActDeliveryAttachResDate()==null)
								{
//								else if(recRent.compareTo(rent)>=0 && tenAttEntryInfo.getActDeliveryAttachResDate()==null)
//								{
									handleRoomEntrys.setHandleType("交房");
									handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
									handleRoomEntrys.setAttach(tenAttEntryInfo);
									attachList.add(handleRoomEntrys);
									break;
								}              					
							}
						}
					}
					//如果原合同是转租或者续租的合同的话，交接类型就为转租交接和续租交接
				}else if(oldBillInfo!=null && (TenancyBillStateEnum.ContinueTenancying.equals(oldBillInfo.getTenancyState())
						|| TenancyBillStateEnum.RejiggerTenancying.equals(oldBillInfo.getTenancyState())))
				{
					//转租或者续租的话只能对新合同来操作，在满足交房条件的同时把原合同的房间收了，把租赁状态反写到租赁合同房间分录和房间上
					TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(oldBillInfo.getId().toString());
					TenAttachResourceEntryCollection tenEntryColl = targetTen.getTenAttachResourceList();
					List attachidlist = new ArrayList();
					for(int k=0;k<tenEntryColl.size();k++)
					{
						TenAttachResourceEntryInfo tenRoomEnInfo = tenEntryColl.get(k);
						attachidlist.add(tenRoomEnInfo.getAttachResource().getId().toString());
					}
					for(int k=0;k<tenEntryColl.size();k++)
					{
						//TenancyRoomEntryInfo tenRoomEnInfo = tenEntryColl.get(k);
						//转租或者续租的房间在原合同里也有的叫转租或续租交接
						if(TenancyClientHelper.isInclude(attachid, attachidlist))
						//if(tenRoomEnInfo.getRoom().getId().toString().equals(roomid))
						{
							//看看收够钱了没有
//							if(recRent.equals(new BigDecimal(0)))
//							{
//								MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"还没有交租");
//								this.abort();
//							}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//							{
//								MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"没有交齐首租押金");
//								this.abort();
//							}else 
							if(tenAttEntryInfo.getActDeliveryAttachResDate()!=null)
							{
								MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"已经交过房了");
								this.abort();
							}else if(tenAttEntryInfo.getActDeliveryAttachResDate()==null)
							{
//							else if(recRent.compareTo(rent)>=0 && tenAttEntryInfo.getActDeliveryAttachResDate()==null)
//							{
								handleRoomEntrys.setHandleType(info.getTenancyType()+"交接");
								handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
								handleRoomEntrys.setAttach(tenAttEntryInfo);
								attachList.add(handleRoomEntrys);
								break;
							}  
						}else
						{
							//原合同中没有房间就是普通的交房操作
							//看看收够钱了没有
//							if(recRent.equals(new BigDecimal(0)))
//							{
//								MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"还没有交租");
//								this.abort();
//							}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//							{
//								MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"没有交齐首租押金");
//								this.abort();
//							}else 
							if(tenAttEntryInfo.getActDeliveryAttachResDate()!=null)
							{
								MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"已经交过房了");
								this.abort();
							}else if(tenAttEntryInfo.getActDeliveryAttachResDate()==null)
							{
//							else if(recRent.compareTo(rent)>=0 && tenAttEntryInfo.getActDeliveryAttachResDate()==null)
//							{
								handleRoomEntrys.setHandleType("交房");
								handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
								handleRoomEntrys.setAttach(tenAttEntryInfo);
								attachList.add(handleRoomEntrys);
								break;
							}  
						}

					}
				}else
				{
					//这里说明是直接新增的合同，要交房的话需要满足收购款的条件
//					if(recRent.equals(new BigDecimal(0)))
//					{
//						MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"还没有交租");
//						this.abort();
//					}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//					{
//						MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"没有交齐首租押金");
//						this.abort();
//					}else 
					if(tenAttEntryInfo.getActDeliveryAttachResDate()!=null)
					{
						MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"已经交过房了");
						this.abort();
					}else if(tenAttEntryInfo.getActDeliveryAttachResDate()==null)
					{
//					else if(recRent.compareTo(rent)>=0 && tenAttEntryInfo.getActDeliveryAttachResDate()==null)
//					{
						handleRoomEntrys.setHandleType("交房");
						handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
						handleRoomEntrys.setAttach(tenAttEntryInfo);
						attachList.add(handleRoomEntrys);
					}
				}
		}else if(TenancyBillStateEnum.RejiggerTenancying.equals(tenState)
				|| TenancyBillStateEnum.ContinueTenancying.equals(tenState))
		{
			String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(info.getId().toString());
			TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(targetTenId);
			TenAttachResourceEntryCollection tenEntryColl = targetTen.getTenAttachResourceList();
			//如果新合同中配套资源的个数为0.那么说明配套资源在新合同中 没有出租那么在原合同中直接收回就行了
			if(tenEntryColl.size()==0)
			{
				handleRoomEntrys.setHandleType("收房");
				handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
				handleRoomEntrys.setAttach(tenAttEntryInfo);
				attachList.add(handleRoomEntrys);
			}else
			{
				if(this.getQuitTenancyInfoByTenancyBill(targetTen))
				{
					for(int m=0;m<tenEntryColl.size();m++)
					{
						TenAttachResourceEntryInfo tenInfo = tenEntryColl.get(m);
						if(attachid.equals(tenInfo.getAttachResource().getId().toString()) && (HandleStateEnum.AlreadyCallBack.equals(tenInfo.getHandleState())
								|| HandleStateEnum.livingHouse.equals(tenInfo.getHandleState())))
						{
							MsgBox.showInfo("该房间已经在新合同中交接了");
							this.abort();
						}else if(attachid.equals(tenInfo.getAttachResource().getId().toString()))
						{
							handleRoomEntrys.setHandleType("收房");
							handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
							handleRoomEntrys.setAttach(tenAttEntryInfo);
							attachList.add(handleRoomEntrys);
						}
					}
					
				}else
				{
					for(int k=0;k<tenEntryColl.size();k++)
					{
						TenAttachResourceEntryInfo tenAttachInfo = tenEntryColl.get(k);
						if(tenAttachInfo.getAttachResource().getId().toString().equals(attachid))
						{
							MsgBox.showInfo(targetTen.getTenancyType()+"交接的房间只能到新合同中去交接");
							this.abort();
						}else
						{
							if(TenancyBillStateEnum.Saved.equals(targetTen.getTenancyState()) || TenancyBillStateEnum.Submitted.equals(targetTen.getTenancyState())
									|| TenancyBillStateEnum.BlankOut.equals(targetTen.getTenancyState()))
							{
								MsgBox.showInfo("目标合同必须是已审批或审批过的合同才能收房");
								this.abort();
							}else
							{
								handleRoomEntrys.setHandleType("收房");
								handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
								handleRoomEntrys.setAttach(tenAttEntryInfo);
								attachList.add(handleRoomEntrys);
							}																
						}
					}  
				}
			}															             		
		}else if(TenancyBillStateEnum.ChangeNaming.equals(tenState))
		{
			if(HandleStateEnum.AlreadyCallBack.equals(tenAttEntryInfo.getHandleState()))
			{
				MsgBox.showInfo("房间已经收回");
				this.abort();
			}else if(HandleStateEnum.CallBacking.equals(tenAttEntryInfo.getHandleState()))
			{
				MsgBox.showInfo("房间正在回收");
				this.abort();
			}else
			{
				handleRoomEntrys.setHandleType("收房");
				handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
				handleRoomEntrys.setAttach(tenAttEntryInfo);
				attachList.add(handleRoomEntrys);
			}              		
		}else if(TenancyBillStateEnum.QuitTenancying.equals(tenState))
		{
			if(HandleStateEnum.AlreadyCallBack.equals(tenAttEntryInfo.getHandleState()))
			{
				MsgBox.showInfo("房间已经收回");
				this.abort();
			}else if(HandleStateEnum.CallBacking.equals(tenAttEntryInfo.getHandleState()))
			{
				MsgBox.showInfo("房间正在回收");
				this.abort();
			}
			else
			{
				if(this.getQuitTenancyInfoByTenancyBill(info) && tenAttEntryInfo.getHandleState().equals(HandleStateEnum.NoHandleRoom))
				{
					MsgBox.showInfo("存在审批状态的退租单不能再进行交房");
					this.abort();
				}else if(!this.getQuitTenancyInfoByTenancyBill(info) && tenAttEntryInfo.getHandleState().equals(HandleStateEnum.NoHandleRoom))
				{
					TenAttachResourceEntryCollection tenEntryColl = info.getTenAttachResourceList();
					for(int m=0;m<tenEntryColl.size();m++)
					{
						TenAttachResourceEntryInfo tenInfo = tenEntryColl.get(m);
						if(tenInfo.getAttachResource().getId().toString().equals(attachid))
						{
							//看看收够钱了没有
//							if(recRent.equals(new BigDecimal(0)))
//							{
//								MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"还没有交租");
//								this.abort();
//							}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//							{
//								MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"没有交齐首租押金");
//								this.abort();
//							}else 
							if(tenAttEntryInfo.getActDeliveryAttachResDate()!=null)
							{
								MsgBox.showInfo("配套资源"+tenAttEntryInfo.getAttachLongNum()+"已经交过房了");
								this.abort();
							}else if(tenAttEntryInfo.getActDeliveryAttachResDate()==null)
							{
//							else if(recRent.compareTo(rent)>=0 && tenAttEntryInfo.getActDeliveryAttachResDate()==null)
//							{
								handleRoomEntrys.setHandleType(info.getTenancyType()+"交接");
								handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
								handleRoomEntrys.setAttach(tenAttEntryInfo);
								attachList.add(handleRoomEntrys);
								break;
							}  
						}
					}
				}else
				{
					handleRoomEntrys.setHandleType("收房");
					handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
					handleRoomEntrys.setAttach(tenAttEntryInfo);
					attachList.add(handleRoomEntrys);
				}
			}
		}
		else if(TenancyBillStateEnum.TenancyChanging.equals(tenState))
		{
			handleRoomEntrys.setHandleType("收房");
			handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
			handleRoomEntrys.setAttach(tenAttEntryInfo);
			attachList.add(handleRoomEntrys);
		}else
		{
			MsgBox.showInfo(tenAttEntryInfo.getAttachLongNum()+"不符合房间交接的条件！");
			abort();
		}
	}

	/**
	 * 查找该合同是否存在审批状态的退租单
	 * @param tenancyBillInfo
	 * @return
	 */
	private boolean getQuitTenancyInfoByTenancyBill(TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyBillInfo == null)
			return false;
		boolean debug = false;

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",tenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));

		try
		{
			if(QuitTenancyFactory.getRemoteInstance().exists(filter))
				debug = true;
			else
				debug = false;
		} catch (EASBizException e)
		{
			super.handUIException(e);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		return debug;
	}

	protected void btnCancel2_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		this.destroyWindow();
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

}