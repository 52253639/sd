package com.kingdee.eas.fdc.tenancy.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.FDCReceivingBillEditUI;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.EffectImageCollection;
import com.kingdee.eas.fdc.sellhouse.EffectImageElementEntryCollection;
import com.kingdee.eas.fdc.sellhouse.EffectImageElementEntryInfo;
import com.kingdee.eas.fdc.sellhouse.EffectImageEnum;
import com.kingdee.eas.fdc.sellhouse.EffectImageFactory;
import com.kingdee.eas.fdc.sellhouse.EffectImageInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEnum;
import com.kingdee.eas.fdc.sellhouse.PlanisphereInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.EffectImageAndPlanisphereInfo;
import com.kingdee.eas.fdc.sellhouse.client.ISolidSideImageListener;
import com.kingdee.eas.fdc.sellhouse.client.RoomEditUI;
import com.kingdee.eas.fdc.sellhouse.client.RoomSourceEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEImagePanel;
import com.kingdee.eas.fdc.sellhouse.client.SolidSideImage;
import com.kingdee.eas.fdc.sellhouse.client.SolidSideInfo;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.FlagAtTermEnum;
import com.kingdee.eas.fdc.tenancy.KeepRoomDownFactory;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentRemissionFactory;
import com.kingdee.eas.fdc.tenancy.SincerObligateCollection;
import com.kingdee.eas.fdc.tenancy.SincerObligateInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyDisPlaySetting;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyModificationFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

/**
 * 租赁控制界面
 * 
 * @author laiquan_luo
 */
public class TenancyControlUI extends AbstractTenancyControlUI implements ISolidSideImageListener
{

	private RoomInfo roomInfo=null;
	private static final Logger logger = CoreUIObject.getLogger(TenancyControlUI.class);
	TenancyDisPlaySetting setting = new TenancyDisPlaySetting();
	private MoneyDefineInfo rentMoneyName = null;// 租金款项类型
	private SolidSideImage ssi = null; //立面图构建对象
	private KDScrollPane solidSideImagePanel=new KDScrollPane();
	private BigDecimal selectedZoom=null; //立面图比例
	
	private KDScrollPane effectImagePanel=new KDScrollPane();
	private int topX,topY,downX,downY;// 当前选择热点区域边框坐标
	private SHEImagePanel pnlRoomPic=new SHEImagePanel(){
		public void paint(Graphics g) {
			super.paint(g);
			
			//这里加载 热点区域矩形 背景颜色根据 所对应房间的租赁属性设置  目前只处理楼层效果图 其他类型效果图后期处理
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			
			//Update by zhiyuan_tang 2010/10/26 处理效果图显示不出来的问题
			//树构建时节点变更，之前是在单元树的基础上增加了平面示意图节点，但是这样的话，效果图就必须依赖于平面示意图
			//如果一个楼层设置了效果图而没有设置平面示意图，这个节点显示不出来，效果图就没有办法查看。
			//所以现在不再增加平面示意图节点，而是增加一个平面示意图和效果图的合并节点。
			if(node.getUserObject() instanceof EffectImageAndPlanisphereInfo){
//			if(node.getUserObject() instanceof PlanisphereInfo){
//				FilterInfo filter=new FilterInfo();
//				BuildingFloorEntryInfo bf=getSelectBF((PlanisphereInfo)node.getUserObject());
//	    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDINGFLOOR_VALUE));
//	    		filter.getFilterItems().add(new FilterItemInfo("buildingFloor.id",bf.getId().toString()));
//				DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode)node.getParent();
//				//父节点是单元时加载区分单元的楼层效果图
//				if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingUnitInfo){
//					filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.TRUE));
//				}
//				//父节点是楼栋时加载不区分单元的楼层效果图
//				if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingInfo){
//					filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.FALSE));
//				}
//				EffectImageInfo info=getEffectImageInfo(filter);
				EffectImageAndPlanisphereInfo effectPlaininfo = (EffectImageAndPlanisphereInfo)node.getUserObject();
				EffectImageInfo info = effectPlaininfo.getEffectImageInfo();
				if(info==null || info.getElementEntry()==null){//如果没有设置热点或者没有导入效果图则不做任何处理
					return;
				}
				else{
					EffectImageElementEntryCollection entryColl=info.getElementEntry();
					Set set=new HashSet();//这里装 所有房间的ID
					for(int i=0;i<entryColl.size();i++){
						EffectImageElementEntryInfo entry=entryColl.get(i);
						set.add(entry.getElementID());
					}
					RoomCollection rc=null;
					try {
						rc=getRoomCollectionByEffect(set);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					for(int i=0;i<entryColl.size();i++){
						EffectImageElementEntryInfo entry=entryColl.get(i);
						if(entry.getHotspot1()==null || entry.getHotspot2()==null){
							continue;
						}
//						try {
//							RoomInfo room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(entry.getElementID()));
//							roomInfo=SHEHelper.queryRoomInfo(room.getId().toString());
//							TenancyDisPlaySetting tdps=new TenancyDisPlaySetting();
//							Color c=Color.GRAY;
//							if(roomInfo.getTenancyState()!=null){
//								c=tdps.getCellBackgroundColor(roomInfo.getTenancyState().getValue());
//							}
//							g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),255));//预留可设置房间租赁属性取得的背景颜色透明度的数字 
//						} catch (EASBizException e) {
//							e.printStackTrace();
//						} catch (BOSException e) {
//							e.printStackTrace();
//						}
						//这里改成这样 减少RPC次数提高性能。。
						if(rc!=null){
							for(int j=0;j<rc.size();j++){
								RoomInfo roomInfo=rc.get(j);
								if(entry.getElementID().equals(roomInfo.getId().toString())){
									TenancyDisPlaySetting tdps=new TenancyDisPlaySetting();
									Color c=Color.GRAY;
									if(roomInfo.getTenancyState()!=null){
										c=tdps.getCellBackgroundColor(roomInfo.getTenancyState().getValue());
									}
									g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),255));//预留可设置房间租赁属性取得的背景颜色透明度的数字
									break;
								}
							}
						}
						String hotspot1=entry.getHotspot1();
						String hotspot2=entry.getHotspot2();
						int hotspot1_x=Integer.parseInt(hotspot1.substring(0,hotspot1.indexOf(',')));
						int hotspot1_y=Integer.parseInt(hotspot1.substring(hotspot1.indexOf(',')+1,hotspot1.length()));
						int hotspot2_x=Integer.parseInt(hotspot2.substring(0,hotspot2.indexOf(',')));
						int hotspot2_y=Integer.parseInt(hotspot2.substring(hotspot2.indexOf(',')+1,hotspot2.length()));
						int x=0,y=0;
						if(hotspot1_x<hotspot2_x){
							if(hotspot1_y<hotspot2_y){
								g.fillRect(hotspot1_x, hotspot1_y, hotspot2_x-hotspot1_x, hotspot2_y-hotspot1_y);
								g.setColor(Color.BLACK);
								x=(hotspot2_x-hotspot1_x)/2+hotspot1_x-16;
								y=(hotspot2_y-hotspot1_y)/2+hotspot1_y+4;
								g.drawString(entry.getElementName(), x, y);
							}
							else{
								g.fillRect(hotspot1_x, hotspot2_y, hotspot2_x-hotspot1_x, hotspot1_y-hotspot2_y);
								g.setColor(Color.BLACK);
								x=(hotspot2_x-hotspot1_x)/2+hotspot1_x-16;
								y=(hotspot1_y-hotspot2_y)/2+hotspot1_y+4;
								g.drawString(entry.getElementName(), x, y);
							}
						}
						else{
							if(hotspot2_y<hotspot1_y){
								g.fillRect(hotspot2_x, hotspot2_y, hotspot1_x-hotspot2_x, hotspot1_y-hotspot2_y);
								g.setColor(Color.BLACK);
								x=(hotspot1_x-hotspot2_x)/2+hotspot1_x-16;
								y=(hotspot1_y-hotspot2_y)/2+hotspot1_y+4;
								g.drawString(entry.getElementName(), x, y);
							}
							else{
								g.fillRect(hotspot2_x, hotspot1_y, hotspot1_x-hotspot2_x, hotspot2_y-hotspot1_y);
								g.setColor(Color.BLACK);
								x=(hotspot1_x-hotspot2_x)/2+hotspot1_x-16;
								y=(hotspot2_y-hotspot1_y)/2+hotspot1_y+4;
								g.drawString(entry.getElementName(), x, y);
							}
						}
						
					}
				}
			}
			//先画当前选择热点区域边框
			g.setColor(new Color(255,0,255));
			if(topX<downX){
				if(topY<downY){
					g.drawRect(topX, topY, downX-topX, downY-topY);
					
				}
				else{
					g.drawRect(topX, downY, downX-topX, topY-downY);
				}
			}
			else{
				if(downY<topY){
					g.drawRect(downX, downY, topX-downX, topY-downY);
				}
				else{
					g.drawRect(downX, topY, topX-downX, downY-topY);
				}
			}
		}
	};
	MouseListener moListener;
	MouseMotionListener mmListener;
	public TenancyControlUI() throws Exception
	{
		super();
		
	}

	CoreUIObject detailUI = null;

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	public void onLoad() throws Exception
	{
		initControl();
		initTable();
		super.onLoad();
	if (!saleOrg.isIsBizUnit())
		{
			this.actionReceiveBill.setEnabled(false);
			this.actionCancelTenancy.setEnabled(false);
			this.actionHandleTenancy.setEnabled(false);
			this.actionApplyTenancy.setEnabled(false);
			this.actionContinueTenancy.setEnabled(false);
			this.actionRejiggerTenancy.setEnabled(false);
			this.actionQuitTenancy.setEnabled(false);
			this.actionKeepRoom.setEnabled(false);
			this.actionChangeName.setEnabled(false);
			this.actionReceiveBill.setEnabled(false);
		}
		//房间状态颜色标记
		RoomStateColorUI.insertUIToScrollPanel(this.kDScrollPane1);
		setComboBoxZoomItem();
		actionLocate.setVisible(true);
		actionLocate.setEnabled(true);
		
		this.solidSideRadioBtn.setVisible(false);
		this.planeRadioBtn.setVisible(false);
		this.effectRadioBtn.setVisible(false);
		this.actionReceiveBill.setVisible(false);
	}
	
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null ||node.getUserObject() instanceof OrgStructureInfo
				||node.getUserObject() instanceof SellProjectInfo
				||node.getUserObject() instanceof SubareaInfo  
				||node.getUserObject() instanceof EffectImageAndPlanisphereInfo ){
//				||node.getUserObject() instanceof PlanisphereInfo ){
			return;
		}
		//选择 单元节点  或者 楼栋节点并且楼栋下无单元节点时
		if(node.getUserObject() instanceof BuildingUnitInfo
				|| (node.getUserObject() instanceof BuildingInfo && node.getChildCount()==0)){
			if(this.planeRadioBtn.isSelected()){//选择平面图
				String number=JOptionPane.showInputDialog(this,"房间编码:","定位",JOptionPane.PLAIN_MESSAGE);
				if(number!=null && !number.trim().equals("")){
					for(int i=0;i<tblMain.getRowCount();i++){
						for(int j=0;j<tblMain.getColumnCount();j++){
							if(tblMain.getCell(i, j).getValue()!=null && number.equals(tblMain.getCell(i, j).getValue().toString())){
								tblMain.getSelectManager().select(i, j);
								tblMain.scrollToVisible(i,j);

							}
						}
					}
				}
			}
		}
	}
	
	//这里最主要带出 租赁状态就行了。。为了性能。。
	private RoomCollection getRoomCollectionByEffect(Set set) throws Exception{
		EntityViewInfo view=new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("tenancyState");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		view.setSelector(sic);
		view.setFilter(filter);
		return RoomFactory.getRemoteInstance().getRoomCollection(view);
	}
	
	/**
	 *  判断单击的点是否在矩形的范围内 <br>
	 *  
	 * @param x 要判断的点的X坐标
	 * @param y 要判断的点的Y坐标
	 * @param hotspot1 矩形的对角顶点坐标1
	 * @param hotspot2 矩形的对角顶点坐标2
	 * @return
	 */
	public boolean hotspotInRec(int x,int y,String hotspot1,String hotspot2){
		int hotspot1_x=Integer.parseInt(hotspot1.substring(0,hotspot1.indexOf(',')));
		int hotspot1_y=Integer.parseInt(hotspot1.substring(hotspot1.indexOf(',')+1,hotspot1.length()));
		int hotspot2_x=Integer.parseInt(hotspot2.substring(0,hotspot2.indexOf(',')));
		int hotspot2_y=Integer.parseInt(hotspot2.substring(hotspot2.indexOf(',')+1,hotspot2.length()));
		//因为传进来的对角点不一定是矩形的左上角和右下角 所以
		//首先计算出矩形的 左上角顶点坐标和右下角顶点坐标 悲剧
		//的分了四种情况m.m ....
		if(hotspot1_x<hotspot2_x){
			if(hotspot1_y<hotspot2_y){ //这时左上顶点坐标hotspot1_x，hotspot1_y
				if(x>=hotspot1_x && y>=hotspot1_y && x<=hotspot2_x && y<=hotspot2_y){
					return true;
				}
				else{
					return false;
				}
			}
			else{//这时左上顶点坐标hotspot1_x，hotspot2_y
				if(x>=hotspot1_x && y>=hotspot2_y && x<=hotspot2_x && y<=hotspot1_y){
					return true;
				}
				else{
					return false;
				}
			}
		}
		else{
			if(hotspot2_y<hotspot1_y){//这时左上顶点坐标hotspot2_x，hotspot2_y
				if(x>=hotspot2_x && y>=hotspot2_y && x<=hotspot1_x && y<=hotspot1_y){
					return true;
				}
				else{
					return false;
				}
			}
			else{//这时左上顶点坐标hotspot2_x，hotspot1_y
				if(x>=hotspot2_x && y>=hotspot1_y && x<=hotspot1_x && y<=hotspot2_y){
					return true;
				}
				else{
					return false;
				}
			}
		}
	}

	public void onShow() throws Exception
	{
		super.onShow();
	}

	protected void initTable()
	{
		this.tblTenStat.checkParsed();
		this.tblTenStat
		.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);	

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblTenStat.getColumn("tenType").setEditor(txtEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblTenStat.getColumn("allArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblTenStat.getColumn("allArea").setEditor(txtEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblTenStat.getColumn("tenArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblTenStat.getColumn("tenArea").setEditor(txtEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblTenStat.getColumn("alreadyArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblTenStat.getColumn("alreadyArea").setEditor(txtEditor);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblTenStat.getColumn("ten").setEditor(
				numberEditor);
		this.tblTenStat.getColumn("ten").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblTenStat.getColumn("ten").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblTenStat.getColumn("appAmount").setEditor(
				numberEditor);
		this.tblTenStat.getColumn("appAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblTenStat.getColumn("appAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblTenStat.getColumn("actAmount").setEditor(
				numberEditor);
		this.tblTenStat.getColumn("actAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblTenStat.getColumn("actAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));


		this.tblTenStat.getColumn("tenType").getStyleAttributes()
		.setLocked(true);
		this.tblTenStat.getColumn("allArea").getStyleAttributes()
		.setLocked(true);
		this.tblTenStat.getColumn("tenArea").getStyleAttributes()
		.setLocked(true);
		this.tblTenStat.getColumn("alreadyArea").getStyleAttributes()
		.setLocked(true);
		this.tblTenStat.getColumn("ten").getStyleAttributes()
		.setLocked(true);
		this.tblTenStat.getColumn("appAmount").getStyleAttributes()
		.setLocked(true);
		this.tblTenStat.getColumn("actAmount").getStyleAttributes()
		.setLocked(true);
	}

	public void initControl()
	{
		this.menuItemImportData.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionImportData.setVisible(true);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionQuery.setEnabled(false);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.treeView.setShowControlPanel(true);

		this.menuItemChangeTenancy.setVisible(false);
		this.btnChangeTenancy.setVisible(false);
	}

	/**
	 * 主要用于双击房间表格的时候，弹出房间的界面来。
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
		if (e.getButton() == 1 && e.getClickCount() == 2)
		{
			RoomInfo room = this.getSelectRoom(false);
			if (room != null)
			{
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", room.getId().toString());
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.NEWTAB).create(RoomSourceEditUI.class.getName(),
								uiContext, null, "VIEW");
				uiWindow.show();
			}
		}
		System.out.println(tblMain.getCell(e.getRowIndex(), e.getColIndex()).getValue()!=null?tblMain.getCell(e.getRowIndex(), e.getColIndex()).getValue().toString():"123456789");
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
	throws Exception
	{
		//进来的是销售组织，重新设置action状态 
		if(saleOrg.isIsBizUnit()){
			changeButtonState();
		}
//		this.actionReceiveBill.setEnabled(true);
		RoomInfo room = this.getSelectRoom(true);
		if (room != null)
		{
			if (detailUI == null)
			{
				UIContext uiContext = new UIContext(ui);
				uiContext.put(UIContext.ID, room.getId().toString());
				detailUI = (CoreUIObject) UIFactoryHelper
				.initUIObject(RoomDetailInfoUI.class.getName(),
						uiContext, null, "VIEW");
				detailUI.setDataObject(room);
				sclPanel.setViewportView(detailUI);
				sclPanel.setKeyBoardControl(true);
				return;
			}
			detailUI.getUIContext().put(UIContext.ID, room.getId().toString());
			detailUI.setDataObject(room);
			detailUI.loadFields();
		}
	}

	/**
	 * 根据房间的状态来改变工具栏的业务按钮的状态
	 * @throws UuidException 
	 * @throws BOSException 
	 * @throws EASBizException 
	 *
	 */
	private void changeButtonState() throws EASBizException, BOSException, UuidException
	{
		RoomInfo room = this.getSelectRoom(false);
		
//		if (!saleOrg.isIsBizUnit() || room == null)
//		{
//			this.actionReceiveBill.setEnabled(false);
//			return;
//		}
		if ( room == null){
				this.actionReceiveBill.setEnabled(false);
				return;
			}
		TenancyStateEnum state = room.getTenancyState();
		//未放租
		if (TenancyStateEnum.unTenancy.equals(state) || state == null)
		{
			this.actionCancelTenancy.setEnabled(false);
			this.actionHandleTenancy.setEnabled(false);
			this.actionApplyTenancy.setEnabled(false);
			this.actionContinueTenancy.setEnabled(false);
			this.actionRejiggerTenancy.setEnabled(false);
			this.actionQuitTenancy.setEnabled(false);
			this.actionKeepRoom.setEnabled(false);
			this.actionChangeName.setEnabled(false);
		} //如果是放租状态的
		else if (TenancyStateEnum.waitTenancy.equals(state) || TenancyStateEnum.sincerObligate.equals(state))
		{
			this.btnKeepRoom.setText("保留");
			this.actionCancelTenancy.setEnabled(true);
			this.actionHandleTenancy.setEnabled(true);
			this.actionApplyTenancy.setEnabled(true);
			this.actionReceiveBill.setEnabled(true);
			this.actionContinueTenancy.setEnabled(false);
			this.actionRejiggerTenancy.setEnabled(false);
			this.actionQuitTenancy.setEnabled(false);
			this.actionChangeName.setEnabled(false);
			this.actionKeepRoom.setEnabled(true);
		}
		//已租 
		else if (TenancyStateEnum.newTenancy.equals(state) ||
				TenancyStateEnum.continueTenancy.equals(state) ||
				TenancyStateEnum.enlargeTenancy.equals(state))
		{
			this.actionCancelTenancy.setEnabled(false);
			this.actionHandleTenancy.setEnabled(true);
			this.actionApplyTenancy.setEnabled(true);
			this.actionContinueTenancy.setEnabled(true);
			this.actionRejiggerTenancy.setEnabled(true);
			this.actionQuitTenancy.setEnabled(true);
			this.actionChangeName.setEnabled(true);
			this.actionReceiveBill.setEnabled(true);
			this.actionKeepRoom.setEnabled(false);
		} //如果房间是保留的状态 
		else if (TenancyStateEnum.keepTenancy.equals(state))
		{
			this.btnKeepRoom.setEnabled(true);
			this.btnKeepRoom.setText("取消保留");
			this.actionReceiveBill.setEnabled(true);
			this.actionCancelTenancy.setEnabled(false);
			this.actionHandleTenancy.setEnabled(false);
			this.actionApplyTenancy.setEnabled(false);
			this.actionContinueTenancy.setEnabled(false);
		}
	}

	/**
	 * 获得选择的房间
	 */
	public RoomInfo getSelectRoom(boolean reQuery) throws EASBizException, BOSException, UuidException{
		if(this.solidSideRadioBtn.isSelected() || this.effectRadioBtn.isSelected()){
			return roomInfo;
		}
		if(this.planeRadioBtn.isSelected()){
			KDTSelectBlock block = this.tblMain.getSelectManager().get();
			if (block == null) {
				return null;
			}
			int left = block.getLeft();
			int top = block.getTop();
			ICell cell = this.tblMain.getCell(top, left);
			if (cell == null) {
				return null;
			}
			RoomInfo room = null;
			if(cell.getUserObject()!=null && cell.getUserObject() instanceof RoomInfo)
				room = (RoomInfo) cell.getUserObject();		
			if(room == null){
				return null;
			}
	
			//为了效率从userObject中只放了一个ID，所以需要再查一遍
			if(reQuery)	{
				room = SHEHelper.queryRoomInfo(room.getId().toString());
				cell.setUserObject(room);
			}
			return room;
		}
		return null;
	}

	protected void setActionState()
	{
		// super.setActionState();
	}

	protected void initTree() throws Exception
	{
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		this.treeMain.setModel(SHEHelper.getFloorTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
	throws Exception
	{
		refresh(null);
		if(effectRadioBtn.isSelected()){
			topX=0;topY=0;downX=0;downY=0;
			pnlRoomPic.repaint();
		}
	}
	 public void actionRefresh_actionPerformed(ActionEvent e)
     throws Exception
 {
		 refresh(e);
 }
	protected void refresh(ActionEvent e) throws BOSException
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null)
		{
			return;
		}
		//当选中 平面图时
		if(this.planeRadioBtn.isSelected()){
			//由于平面图树是在单元树的基础上增加平面图节点生成的，会对单元树的depth产生影响,并影响房间表头的显示，
			//因此需把所选节点克隆一份，并除去所有的平面图节点，然后再传递进去构建房间列表
			if(node.getUserObject() instanceof EffectImageAndPlanisphereInfo) {
				//显示平面图
				//Update by zhiyuan_tang 2010/10/26 增加的节点从PlanisphereInfo变成了EffectImageAndPlanisphereInfo
				EffectImageAndPlanisphereInfo info = (EffectImageAndPlanisphereInfo)node.getUserObject();
//				PlanisphereInfo phInfo = (PlanisphereInfo)node.getUserObject();
				PlanisphereInfo phInfo = info.getPlanisphereInfo();
				if (phInfo != null) {
					
					if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildPlane)) {
						this.kDSplitPane1.setDividerLocation(550);
						this.kDScrollPane1.setVisible(true);
					}else {
						this.kDSplitPane1.setDividerLocation(this.kDSplitPane1.getWidth());
						this.kDScrollPane1.setVisible(false);
					}
					SHEHelper.showPlanisphereTable(this.tblMain,phInfo,setting);
					
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("planisphere.id",phInfo.getId().toString()));
					view.setFilter(filter);
					view.getSelector().add(new SelectorItemInfo("*"));
					if(setting.getSysType().equals(MoneySysTypeEnum.SalehouseSys)) {
						view.getSelector().add(new SelectorItemInfo("roomEntry.sellState"));
						view.getSelector().add(new SelectorItemInfo("roomEntry.isForSHE"));			
					}else if(setting.getSysType().equals(MoneySysTypeEnum.TenancySys)) {
						view.getSelector().add(new SelectorItemInfo("roomEntry.tenancyState"));
						view.getSelector().add(new SelectorItemInfo("roomEntry.isForTen"));
					}
					
					//Object object = ((DefaultMutableTreeNode)node.getParent()).getUserObject();
					PlanisphereElementEntryCollection elementColl = PlanisphereElementEntryFactory.getRemoteInstance().getPlanisphereElementEntryCollection(view);
					this.tblTenStat.removeRows();
					Map buildProMap = new HashMap();
					String[] roomIDSet = new String[100];
					String[] buildIDSet = new String[100];
					String[] projectIDSet = new String[100];
					
					FDCSQLBuilder builder = new FDCSQLBuilder();
					this.setBeforeBuilder(builder);
					
					FDCSQLBuilder builder2 = new FDCSQLBuilder();
					this.setBeforeTenState(builder2);
					//this.setBuilder(builder2, object);
					
					FDCSQLBuilder builder3 = new FDCSQLBuilder();
					this.setBeforeRecAmountBuilder(builder3);
					//this.setReceiveBuilder(builder3, object);
					
					FDCSQLBuilder builder4 = new FDCSQLBuilder();
					this.setBeforeAppAmount(builder4);
					//楼栋平面图
					if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildPlane)) {
						for(int i=0;i<elementColl.size();i++) {
							PlanisphereElementEntryInfo phElementInfo = elementColl.get(i);
							if(phElementInfo.getType().equals(PlanisphereElementEnum.room))	{  //房间类型
								RoomInfo roomInfo = phElementInfo.getRoomEntry();
								roomIDSet[i] = roomInfo.getId().toString();
								//roomIDSet.add(roomInfo.getId().toString());
							}
						}				
						builder.appendSql(" where room.fisforten=1 and ");
						builder.appendParam("room.fid", roomIDSet);
						builder.appendSql(" group by buildPro.fid,buildPro.fname_l2");
						this.getRow(buildProMap,builder);
						
						builder2.appendSql(" where room.fisforten=1 and ");
						builder2.appendParam("room.fid", roomIDSet);
						builder2.appendSql(" group by buildPro.fid,room.ftenancyState,buildPro.fname_l2");
						this.getRowSet(buildProMap,builder2);
						
						builder3.appendSql(" where room.fisforten=1 and ");
						builder3.appendParam("room.fid", roomIDSet);
						this.appendFilterSql(builder3, "cas.FBizDate");
						builder3.appendSql(" group by buildPro.fid");			
						this.getRecAmount(buildProMap,builder3);
						
						builder4.appendSql(" where room.fisforten=1 and ");
						builder4.appendParam("room.fid", roomIDSet);
						builder4.appendSql(" and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
						builder4.appendSql(" group by buildPro.fid,tenBill.fid");
						this.getAppAmount(buildProMap,builder4);
					}else if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildDist)) {  /*  楼栋分布图 */
						for(int i=0;i<elementColl.size();i++) {
							PlanisphereElementEntryInfo phElementInfo = elementColl.get(i);
							if(phElementInfo.getType().equals(PlanisphereElementEnum.building)) {
								BuildingInfo buildInfo = phElementInfo.getBuildingEntry();
								buildIDSet[i] = buildInfo.getId().toString();
								//buildIDSet.add(buildInfo.getId().toString());
							}
						}			
						builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");				
						builder.appendSql(" where room.fisforten=1 and ");
						builder.appendParam("build.fid", buildIDSet);
						builder.appendSql(" group by buildPro.fid,buildPro.fname_l2");
						this.getRow(buildProMap,builder);
						
						builder2.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder2.appendSql(" where room.fisforten=1 and ");
						builder2.appendParam("build.fid", buildIDSet);
						builder2.appendSql(" group by buildPro.fid,room.ftenancyState,buildPro.fname_l2");
						this.getRowSet(buildProMap,builder2);
						
						builder3.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder3.appendSql(" where room.fisforten=1 and ");
						builder3.appendParam("build.fid", buildIDSet);
						this.appendFilterSql(builder3, "cas.FBizDate");
						builder3.appendSql(" group by buildPro.fid");
						this.getRecAmount(buildProMap,builder3);
						
						builder4.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder4.appendSql(" where room.fisforten=1 and ");
						builder4.appendParam("build.fid", buildIDSet);
						builder4.appendSql(" and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
						builder4.appendSql(" group by buildPro.fid,tenBill.fid");
						this.getAppAmount(buildProMap,builder4);
					}else if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicSellProject)) {   /* 项目图 */
						for(int i=0;i<elementColl.size();i++) {
							PlanisphereElementEntryInfo phElementInfo = elementColl.get(i);
							if(phElementInfo.getType().equals(PlanisphereElementEnum.sellProject)) {
								SellProjectInfo proInfo = phElementInfo.getSellProjectEntry();
								projectIDSet[i] = proInfo.getId().toString();
								//projectIDSet.add(proInfo.getId().toString());
							}
						}
						builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");
						builder.appendSql(" where room.fisforten=1 and ");
						builder.appendParam("project.fid", projectIDSet);
						builder.appendSql(" group by buildPro.fid,buildPro.fname_l2");
						this.getRow(buildProMap,builder);
						
						builder2.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder2.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");
						builder2.appendSql(" where room.fisforten=1 and ");
						builder2.appendParam("project.fid", projectIDSet);
						builder2.appendSql(" group by buildPro.fid,room.ftenancyState,buildPro.fname_l2");
						this.getRowSet(buildProMap,builder2);
						
						builder3.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder3.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");
						builder3.appendSql(" where room.fisforten=1 and ");
						builder3.appendParam("project.fid", projectIDSet);
						this.appendFilterSql(builder3, "cas.FBizDate");
						builder3.appendSql(" group by buildPro.fid");
						this.getRecAmount(buildProMap,builder3);
						
						builder4.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder4.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");
						builder4.appendSql(" where room.fisforten=1 and ");
						builder4.appendParam("project.fid", projectIDSet);
						builder4.appendSql(" and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
						builder4.appendSql(" group by buildPro.fid,tenBill.fid");
						this.getAppAmount(buildProMap,builder4);
					}
				} else {
					this.kDSplitPane1.setDividerLocation(550);
					this.kDScrollPane1.setVisible(true);
					DefaultKingdeeTreeNode newNode = (DefaultKingdeeTreeNode)node.clone();
					CommerceHelper.cloneTree(newNode,node);
					newNode.setParent((DefaultKingdeeTreeNode)node.getParent());  //调用接口时会查询其父节点的对象　
					CommerceHelper.removePlanisphereNode(newNode);
					SHEHelper.fillRoomTableByNode(this.tblMain,newNode, MoneySysTypeEnum.TenancySys, null,setting);
					Object object = node.getUserObject();
					this.tblTenStat.removeRows();
					Map buildProMap = new HashMap();
					this.getRow(buildProMap, this.getAllSql(object));
					this.getRowSet(buildProMap,this.getTenStateSql(object));
					this.getRecAmount(buildProMap,this.getRecAmountSql(object));
					this.getAppAmount(buildProMap,this.getTenancyIDSet(object));
				}
			}else{
				this.kDSplitPane1.setDividerLocation(550);
				this.kDScrollPane1.setVisible(true);
				DefaultKingdeeTreeNode newNode = (DefaultKingdeeTreeNode)node.clone();
				CommerceHelper.cloneTree(newNode,node);
				newNode.setParent((DefaultKingdeeTreeNode)node.getParent());  //调用接口时会查询其父节点的对象　
				CommerceHelper.removePlanisphereNode(newNode);
				SHEHelper.fillRoomTableByNode(this.tblMain,newNode, MoneySysTypeEnum.TenancySys, null,setting);
				Object object = node.getUserObject();
				this.tblTenStat.removeRows();
				Map buildProMap = new HashMap();
				this.getRow(buildProMap, this.getAllSql(object));
				this.getRowSet(buildProMap,this.getTenStateSql(object));
				this.getRecAmount(buildProMap,this.getRecAmountSql(object));
				this.getAppAmount(buildProMap,this.getTenancyIDSet(object));
			}
			//RoomStateColorUI.insertUIToScrollPanel(this.kDScrollPane1);
		}
		//当选中立面图时
		if(this.solidSideRadioBtn.isSelected()){
			this.kDPanel1.remove(solidSideImagePanel);
			//选择组织时
			if(node.getUserObject() instanceof OrgStructureInfo){
				solidSideImagePanel.removeAll();
			}
			//选择工程项目时
			if(node.getUserObject() instanceof SellProjectInfo){
				solidSideImagePanel.removeAll();	
			}
			//选择分区时
			if(node.getUserObject() instanceof SubareaInfo){
				solidSideImagePanel.removeAll();
			}
			//选择楼栋时
			if(node.getUserObject() instanceof BuildingInfo){
				BuildingInfo info=(BuildingInfo)node.getUserObject();
				BuildingUnitCollection coll=getBuildingUnits(info);
				if(coll!=null && coll.size()>0){//楼栋 有单元时
					KDPanel building=new KDPanel();
					int x=0;
					for(Iterator it=coll.iterator();it.hasNext();){
						BuildingUnitInfo buildingUnitInfo=(BuildingUnitInfo)it.next();
						KDPanel unit=loadBuildingUnit(buildingUnitInfo,selectedZoom);					
						unit.setBounds(x, 0, unit.getWidth(),unit.getHeight());
						building.add(unit);
						x+=(unit.getWidth()+10);
					}
					solidSideImagePanel=null;
					solidSideImagePanel=new KDScrollPane(building);
				}
				else{//楼栋 没有区分单元时
					EntityViewInfo view=new EntityViewInfo();
					view.getSelector().add("id");
					view.getSelector().add("buildingArea");
					view.getSelector().add("floorHeight");
					view.getSelector().add("buildingFloor.floor");
					view.getSelector().add("buildingFloor.floorAlias");	
					view.getSelector().add("building.floorCount");
					view.getSelector().add("building.subFloorCount");
					view.getSelector().add("serialNumber");
					view.getSelector().add("buildUnit.name");
					view.getSelector().add("number");
					view.getSelector().add("tenancyState");
					FilterInfo filter=new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("building.id",info.getId()));
					view.setFilter(filter);
					RoomCollection roomColl=RoomFactory.getRemoteInstance().getRoomCollection(view);
					int floor=0;
					if(roomColl.size()>0){
						floor=roomColl.get(0).getBuilding().getFloorCount()+Math.abs(roomColl.get(0).getBuilding().getSubFloorCount());
					}else{
						MsgBox.showWarning("所选择楼栋没有录入房间！");
						solidSideImagePanel=null;
						solidSideImagePanel=new KDScrollPane();
						this.kDPanel1.add(solidSideImagePanel,BorderLayout.CENTER);
						kDPanel1.repaint();
						kDPanel1.validate();
						kDPanel1.revalidate();
						abort();
					}
					
					List list= getSolidSideInfoList(roomColl);
					TenancyDisPlaySetting tdps=new TenancyDisPlaySetting();
					ssi=new SolidSideImage(list,floor,tdps,this);
					if(selectedZoom!=null){
						ssi.setScale(selectedZoom);
					}
					solidSideImagePanel = null;
					solidSideImagePanel=new KDScrollPane(ssi.getPanel());
				}
			}
			//选择单元时
			if(node.getUserObject() instanceof BuildingUnitInfo){
				BuildingUnitInfo info=(BuildingUnitInfo)node.getUserObject();
				solidSideImagePanel=null;
				solidSideImagePanel=new KDScrollPane(loadBuildingUnit(info,selectedZoom));
			}
			//选择楼层时
//			if(node.getUserObject() instanceof PlanisphereInfo){
			if(node.getUserObject() instanceof EffectImageAndPlanisphereInfo){
				solidSideImagePanel.removeAll();
			}
			this.kDPanel1.add(solidSideImagePanel,BorderLayout.CENTER);
			kDPanel1.repaint();
			kDPanel1.validate();
			kDPanel1.revalidate();
		}
		if(this.effectRadioBtn.isSelected()){//选中效果图时
			this.kDPanel1.remove(effectImagePanel);
			//选择组织时  加载当前组织的项目分布图
			if(node.getUserObject() instanceof OrgStructureInfo){
				OrgStructureInfo orgStructInfo = (OrgStructureInfo)node.getUserObject();
	    		if(orgStructInfo.getUnit()!=null) {
		    		FullOrgUnitInfo info=orgStructInfo.getUnit();
		    		FilterInfo filter=new FilterInfo();
		    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_SELLPROJECT_VALUE));
		    		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",info.getId().toString()));
		    		setEffectImagePanelView(getEffectImageInfo(filter));
	    		}
			}
			//选择工程项目时 加载楼栋方位图
			if(node.getUserObject() instanceof SellProjectInfo){
				SellProjectInfo prj=(SellProjectInfo)node.getUserObject();
	    		FilterInfo filter=new FilterInfo();
	    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDING_VALUE));
	    		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",prj.getId().toString()));
	    		setEffectImagePanelView(getEffectImageInfo(filter));
			}
			//选择分区时
			if(node.getUserObject() instanceof SubareaInfo){
				effectImagePanel.setViewportView(null);
			}
			//选择楼栋时 
			if(node.getUserObject() instanceof BuildingInfo){
				effectImagePanel.setViewportView(null);
			}
			//选择单元时
			if(node.getUserObject() instanceof BuildingUnitInfo){
				effectImagePanel.setViewportView(null);
			}
			//选择楼层时加载 楼层效果图   因为会出现一个楼层 即有整体楼层效果图 又有 区分单元的楼层效果图
			//这时树结构 也成为  楼栋下面 挂楼层 和单元 然后单元下面还有楼层。。于是这里又多了个判断。。。。
			//Update by zhiyuan_tang 2010/10/26 增加的节点从PlanisphereInfo变成了EffectImageAndPlanisphereInfo
			if(node.getUserObject() instanceof EffectImageAndPlanisphereInfo){
				EffectImageAndPlanisphereInfo info = (EffectImageAndPlanisphereInfo)node.getUserObject();
				if (info.getEffectImageInfo() == null) {
					effectImagePanel.setViewportView(null);
				} else {
					EffectImageInfo effectInfo = info.getEffectImageInfo();
					FilterInfo filter=new FilterInfo();
		    		filter.getFilterItems().add(new FilterItemInfo("id", effectInfo.getId()));
					setEffectImagePanelView(getEffectImageInfo(filter));
				}
				
//				if(getSelectBF((PlanisphereInfo)node.getUserObject())==null){
//					return;
//				}
//				BuildingFloorEntryInfo bf=getSelectBF((PlanisphereInfo)node.getUserObject());
//				FilterInfo filter=new FilterInfo();
//	    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDINGFLOOR_VALUE));
//	    		filter.getFilterItems().add(new FilterItemInfo("buildingFloor.id",bf.getId().toString()));
//				DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode)node.getParent();
//				//父节点是单元时加载区分单元的楼层效果图
//				if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingUnitInfo){
//					filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.TRUE));
//				}
//				//父节点是楼栋时加载不区分单元的楼层效果图
//				if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingInfo){
//					filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.FALSE));
//				}
//				setEffectImagePanelView(getEffectImageInfo(filter));
			}
    		this.kDPanel1.add(effectImagePanel,BorderLayout.CENTER);
    		kDPanel1.repaint();
			kDPanel1.validate();
			kDPanel1.revalidate();
		}
	}
	
	private BuildingFloorEntryInfo getSelectBF(PlanisphereInfo plInfo){
		EntityViewInfo view=new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("id");
		sic.add("floor");
		sic.add("building.id");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.id",plInfo.getBuilding().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("floor",new Integer(plInfo.getFloor())));
		view.setSelector(sic);
		view.setFilter(filter);
		try {
			BuildingFloorEntryCollection coll=BuildingFloorEntryFactory.getRemoteInstance().getBuildingFloorEntryCollection(view);
			if(coll!=null && coll.size()>0){
				return coll.get(0);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  根据传入的filter 查询出效果图对象
	 * @param filter
	 * @return
	 */
	private EffectImageInfo getEffectImageInfo(FilterInfo filter){
		EntityViewInfo view =new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("id");
		sic.add("effectImage");
		sic.add("elementEntry.id");
		sic.add("elementEntry.hotspot1");
		sic.add("elementEntry.hotspot2");
		sic.add("elementEntry.type");
		sic.add("elementEntry.elementID");
		sic.add("elementEntry.elementName");
		view.setSelector(sic);
		view.setFilter(filter);
		try {
			EffectImageCollection coll=EffectImageFactory.getRemoteInstance().getEffectImageCollection(view);
			return coll.get(0);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据传入的效果图对象设置效果图
	 * @param info
	 */
	private void setEffectImagePanelView(EffectImageInfo info){
		if (info !=null && info.getEffectImage() != null) {
			try {
				File file = File.createTempFile("tmp1111", "a.jpg");
				ByteInputStream stream = new ByteInputStream(info.getEffectImage(), info.getEffectImage().length);
				FileOutputStream out = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int size = -1;
				while((size = stream.read(buffer)) != -1){
					out.write(buffer, 0, size);
				}
				out.flush();
				stream.close();
				out.close();
				pnlRoomPic.setImageFile(file);
				effectImagePanel.setViewportView(pnlRoomPic);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//Add by zhiyuan_tang 2010/10/21 当没有导入图片时，显示空
			effectImagePanel.setViewportView(null);
		}
	}
	
	/**
	 *  获取楼栋的单元集合
	 */
	private BuildingUnitCollection getBuildingUnits(BuildingInfo info) throws BOSException{
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("id");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building",info.getId().toString()));
		view.setFilter(filter);
		return BuildingUnitFactory.getRemoteInstance().getBuildingUnitCollection(view);
	}
	
	/**
	 *  获取立面图显示的基本单元面板
	 */
	private KDPanel loadBuildingUnit(BuildingUnitInfo info,BigDecimal zoom) throws BOSException{
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("id");
		//房间建筑面积    显示宽度
		view.getSelector().add("buildingArea");
		//层高   显示高度
		view.getSelector().add("floorHeight");
		//楼层  
		view.getSelector().add("buildingFloor.floor");
		//楼层显示名称
		view.getSelector().add("buildingFloor.floorAlias");	
		//地上楼层数   用于计算楼栋有多少层
		view.getSelector().add("building.floorCount");
		//地下楼层数   用于计算楼栋有多少层
		view.getSelector().add("building.subFloorCount");
		//房间序号    房间排序用 
		view.getSelector().add("serialNumber");
		//单元名称
		view.getSelector().add("buildUnit.name");
		//房间编号   显示用
		view.getSelector().add("number");
		//房间租赁状态   决定背景颜色
		view.getSelector().add("tenancyState");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("buildUnit",info.getId()));
		view.setFilter(filter);
		RoomCollection coll=RoomFactory.getRemoteInstance().getRoomCollection(view);
		int floor=0;
		if(coll.size()>0){
			floor=coll.get(0).getBuilding().getFloorCount()+Math.abs(coll.get(0).getBuilding().getSubFloorCount());
		}else{
			MsgBox.showWarning("所选择单元没有录入房间！");
			abort();
		}
		
		List list= getSolidSideInfoList(coll);
		TenancyDisPlaySetting tdps=new TenancyDisPlaySetting();
		ssi=new SolidSideImage(list,floor,tdps,this);
		if(zoom!=null){
			ssi.setScale(zoom);
		}
		return ssi.getPanel();
	}
	/**
	 *  调用立面图时 把集合转换成 符合构建立面图的结构
	 * @param coll
	 * @return
	 */
	private List getSolidSideInfoList(RoomCollection coll){
		List list=new ArrayList();
		if(coll.size()>0){
			for(int i=0;i<coll.size();i++){
				RoomInfo room=(RoomInfo)coll.get(i);
				if(room!=null && room.getId()!=null){
					SolidSideInfo info=new SolidSideInfo();
					info.setBizObj(room);
					info.setId(room.getId().toString());
					info.setExtent(room.getBuildingArea());
					info.setColIndex(room.getSerialNumber());
					info.setRowhigh(room.getFloorHeight());
					info.setRowIndex(room.getBuildingFloor().getFloor());
					info.setRowName(room.getBuildingFloor().getFloorAlias());
					if(room.getBuildUnit()!=null && room.getBuildUnit().getName()!=null){
						info.setName(room.getBuildUnit().getName());
					}else{
						info.setName("一单元");
					}
					if(room.getTenancyState()==null){
						info.setState(null);
					}else{
						info.setState(room.getTenancyState().getValue());
					}
					if(room.getBuildingArea()!=null){
						info.setText("<html>"+room.getNumber()+"<br><font align='right'>"+room.getBuildingArea()+" m2</font></html>");
					}
					else{
						info.setText(room.getNumber());
					}
					list.add(info);
				}
			}
		}
		return list;
	}
	
	//本月应付金额
	protected void getAppAmount(Map buildProMap,FDCSQLBuilder builder)
	{
		Set tenancySet = new HashSet();
//		Map buildingProMap = new HashMap();
		IRowSet tenBillSet = null;
		//存不重复的建筑类型ID,用来最后做比较  xin_wang 2010.11.17
		Set buildingProIDSet = new HashSet();
		try {
			tenBillSet = builder.executeQuery();
			while(tenBillSet.next())
			{
				String buildingProID = tenBillSet.getString("buildingProID");
				String tenBillID = tenBillSet.getString("tenBillID");
				tenancySet.add(tenBillID);
//				buildingProMap.put(tenBillID, buildingProID);
				buildingProIDSet.add(buildingProID);
			}
		}catch(Exception e)
		{
			this.handleException(e);
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("rentFrees.*");
		sel.add("tenancyRoomList.*");
		sel.add("tenancyRoomList.room.*");
		sel.add("tenancyRoomList.room.buildingProperty.*");		
		sel.add("tenancyRoomList.dealAmounts.*");	
		sel.add("tenancyRoomList.dealAmounts.moneyDefine.*");	
		filterInfo.getFilterItems().add(new FilterItemInfo("id",tenancySet,CompareType.INCLUDE));
		view.setSelector(sel);
		view.setFilter(filterInfo);
		Date beginDate = FDCDateHelper.getFirstDayOfCurMonth();
		Date endDate = FDCDateHelper.getLastDayOfCurMonth();
		DealAmountEntryCollection newDealAmount = new DealAmountEntryCollection();
		BigDecimal totalRentAmount = FDCHelper.ZERO;
		Date[] date1 =null;
		Date[] date2  =null;
		try {
			TenancyBillCollection tenBillColl = TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(view);
			for(Iterator itor =buildingProIDSet.iterator(); itor.hasNext(); ){
				String buildingId = (String)itor.next();
				IRow row = (IRow)buildProMap.get(buildingId);
				for(int i=0;i<tenBillColl.size();i++){
					TenancyBillInfo tenBill = tenBillColl.get(i);
					Date tenBillStartDate = tenBill.getStartDate();
					date1 = new Date[]{beginDate,endDate};
					date2 = new Date[]{tenBillStartDate,tenBillStartDate};
					//免租分录
					RentFreeEntryCollection rentFreeColl =  tenBill.getRentFrees();
					//租赁房间分录
					TenancyRoomEntryCollection tenRoomColl = tenBill.getTenancyRoomList();
					for(int j=0;j<tenRoomColl.size();j++){
						TenancyRoomEntryInfo tenRoomEntryInfo = tenRoomColl.get(j);
						if(tenRoomEntryInfo.getRoom().getBuildingProperty().getId()!=null){
							//成交价格分录
							DealAmountEntryCollection dealAmountColl = tenRoomEntryInfo.getDealAmounts();
							if(buildingId.equals(tenRoomEntryInfo.getRoom().getBuildingProperty().getId().toString())){
								//如果合同的起始时间再查询时间范围内我们计算应收租金+押金+周期性费用
								if(TenancyHelper.getWrapDates(date1,date2)!=null){
									//租金
									totalRentAmount = totalRentAmount.add(TenancyHelper.getRentBetweenDate(this.getRentMoneyDefine(),beginDate, endDate, dealAmountColl, rentFreeColl));
									
									for(int z =0; z<dealAmountColl.size();z++){
										DealAmountEntryInfo dealAmounteEntry = dealAmountColl.get(z);
										if(MoneyTypeEnum.DepositAmount.equals(dealAmounteEntry.getMoneyDefine().getMoneyType())){//押金
											totalRentAmount = totalRentAmount.add(dealAmounteEntry.getAmount());
										}else if(MoneyTypeEnum.PeriodicityAmount.equals(dealAmounteEntry.getMoneyDefine().getMoneyType())){//周期性费用
											totalRentAmount = totalRentAmount.add(TenancyHelper.getRentBetweenDate(dealAmounteEntry.getMoneyDefine(),beginDate, endDate, dealAmountColl, rentFreeColl));
										}
									}
								}else{
									totalRentAmount=totalRentAmount.add(TenancyHelper.getRentBetweenDate(this.getRentMoneyDefine(),beginDate, endDate, newDealAmount, rentFreeColl));
									for(int z =0; z<dealAmountColl.size();z++){
										DealAmountEntryInfo dealAmounteEntry = dealAmountColl.get(z);
										if(MoneyTypeEnum.PeriodicityAmount.equals(dealAmounteEntry.getMoneyDefine().getMoneyType())){//周期性费用
											totalRentAmount = totalRentAmount.add(TenancyHelper.getRentBetweenDate(dealAmounteEntry.getMoneyDefine(),beginDate, endDate, dealAmountColl, rentFreeColl));
										}
									}
								}
							}else{
								break;
							}
						}
					}
					
				}
//				row.getCell("appAmount").setValue(totalRentAmount);
			}
//			for(int i=0;i<tenBillColl.size();i++)
//			{
//				TenancyBillInfo tenBill = tenBillColl.get(i);				
//				String buildingProID = (String)buildingProMap.get(tenBill.getId().toString());
//				IRow row = (IRow)buildProMap.get(buildingProID);
//				Date tenBillStartDate = tenBill.getStartDate();
//				Date[] date1 = new Date[]{beginDate,endDate};
//				Date[] date2 = new Date[]{tenBillStartDate,tenBillStartDate};
//				//免租分录
//				RentFreeEntryCollection rentFreeColl =  tenBill.getRentFrees();
//				//租赁房间分录
//				TenancyRoomEntryCollection tenRoomColl = tenBill.getTenancyRoomList();
//				for(int j=0;j<tenRoomColl.size();j++)
//				{
//					TenancyRoomEntryInfo tenRoomEntryInfo = tenRoomColl.get(j);
//					//成交价格分录
//					DealAmountEntryCollection dealAmountColl = tenRoomEntryInfo.getDealAmounts();
//					//如果合同的起始时间不在查询时间范围内我们只计算应收租金
//					if(TenancyHelper.getWrapDates(date1,date2)==null)
//					{
//						totalRentAmount = totalRentAmount.add(TenancyHelper.getRentBetweenDate(this.getRentMoneyDefine(),beginDate, endDate, dealAmountColl, rentFreeColl));
//						row.getCell("appAmount").setValue(totalRentAmount);
//					}else
//					{
//						//如果合同的起始时间再查询时间范围内我们除了计算应收租金还应该计算押金
//						totalRentAmount = totalRentAmount.add(TenancyHelper.getRentBetweenDate(this.getRentMoneyDefine(),beginDate, endDate, dealAmountColl, rentFreeColl));
//						totalDepositAmount = totalDepositAmount.add(tenRoomEntryInfo.getDepositAmount()==null?new BigDecimal(0):tenRoomEntryInfo.getDepositAmount());
//						totalRentAmount = totalRentAmount.add(totalDepositAmount);
//						row.getCell("appAmount").setValue(totalRentAmount);
//					}					
//				}								
//			}
		} catch (BOSException e) {
			this.handleException(e);
		}
		
	}
	
	private MoneyDefineInfo getRentMoneyDefine() throws BOSException {
		if(rentMoneyName == null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.RentAmount));
			filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.TenancySys));
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
	protected void setBeforeAppAmount(FDCSQLBuilder builder)
	{
		builder.appendSql("select buildPro.fid buildingProID,tenBill.fid tenBillID from t_she_room room ");
		builder.appendSql("left join t_she_buildingProperty buildPro on room.fbuildingPropertyid = buildPro.fid ");
		builder.appendSql("left join t_ten_tenancybill tenBill on room.FLastTenancyID = tenBill.fid");
	}
	
	protected FDCSQLBuilder getTenancyIDSet(Object object)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		this.setBeforeAppAmount(builder);
		this.setTenancyBuilder(builder, object);
		builder.appendSql(" group by buildPro.fid,tenBill.fid");
		return builder;
	}
	
	protected void setTenancyBuilder(FDCSQLBuilder builder,Object object)
	{
		if(object instanceof SellProjectInfo)
		{
			SellProjectInfo project = (SellProjectInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");
			builder.appendSql(" where room.fisforten=1 and project.fid ='"+project.getId().toString()+"'");
			builder.appendSql(" and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
		}else if(object instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql("  where room.fisforten=1 and build.fid ='"+building.getId().toString()+"'");
			builder.appendSql("  and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
		}else if(object instanceof SubareaInfo)
		{
			SubareaInfo subarea = (SubareaInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" left join t_she_subarea subarea on build.FSubareaID = subarea.fid ");
			builder.appendSql("  where room.fisforten=1 and subarea.fid ='"+subarea.getId().toString()+"'");
			builder.appendSql("  and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
		}else if(object instanceof BuildingUnitInfo)
		{
			BuildingUnitInfo buildingUnit = (BuildingUnitInfo)object;
			builder.appendSql(" left join t_she_buildingUnit buildUnit on room.FBuildUnitID = buildUnit.fid ");
			builder.appendSql(" where room.fisforten=1 and buildUnit.fid ='"+buildingUnit.getId().toString()+"'");
			builder.appendSql("  and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
		}else
		{
			builder.appendSql(" where room.fid is null");
		}
	}

	//查询总套数和总面积的SQL
	protected FDCSQLBuilder getAllSql(Object object)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder(); 
		this.setBeforeBuilder(builder);
		this.setBuilder(builder, object);
		builder.appendSql(" group by buildPro.fid,buildPro.fname_l2");
		return builder;
	}


	protected void setBeforeBuilder(FDCSQLBuilder builder)
	{
		builder.appendSql("select buildPro.fid buildingProID,buildPro.fname_l2 name,");
		builder.appendSql(" sum(FbuildingArea) buildingArea ,count(room.fid) count from t_she_room room");
		builder.appendSql(" left join t_she_buildingProperty buildPro on room.fbuildingPropertyid = buildPro.fid ");
	}

	//设置在各种情况下求面积和套数的条件
	protected void setBuilder(FDCSQLBuilder builder,Object object)
	{
		if(object instanceof SellProjectInfo)
		{
			SellProjectInfo project = (SellProjectInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");
			builder.appendSql("  where room.fisforten=1 and project.fid ='"+project.getId().toString()+"'");
		}else if(object instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql("  where room.fisforten=1 and build.fid ='"+building.getId().toString()+"'");
		}else if(object instanceof SubareaInfo)
		{
			SubareaInfo subarea = (SubareaInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" left join t_she_subarea subarea on build.FSubareaID = subarea.fid ");
			builder.appendSql("  where room.fisforten=1 and subarea.fid ='"+subarea.getId().toString()+"'");
		}else if(object instanceof BuildingUnitInfo)
		{
			BuildingUnitInfo buildingUnit = (BuildingUnitInfo)object;
			builder.appendSql(" left join t_she_buildingUnit buildUnit on room.FBuildUnitID = buildUnit.fid ");
			builder.appendSql(" where room.fisforten=1 and buildUnit.fid ='"+buildingUnit.getId().toString()+"'");
		}else
		{
			builder.appendSql(" where room.fid is null");
		}
	}

	//设置在各种情况下求收款总额的条件
	protected void setReceiveBuilder(FDCSQLBuilder builder,Object object)
	{
		if(object instanceof SellProjectInfo)
		{			
			SellProjectInfo project = (SellProjectInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");			
			builder.appendSql(" where (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount' or money.FMoneyType ='PeriodicityAmount' and money.FSysType ='TenancySys')");
			builder.appendSql(" and project.fid ='"+project.getId().toString()+"'");
		}else if(object instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" where (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount' or money.FMoneyType ='PeriodicityAmount'  and money.FSysType ='TenancySys')");
			builder.appendSql(" and build.fid ='"+building.getId().toString()+"'");
		}else if(object instanceof SubareaInfo)
		{
			SubareaInfo subarea = (SubareaInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" left join t_she_subarea subarea on build.FSubareaID = subarea.fid ");
			builder.appendSql(" where (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount' or money.FMoneyType ='PeriodicityAmount'  and money.FSysType ='TenancySys')");
			builder.appendSql(" and subarea.fid ='"+subarea.getId().toString()+"'");
		}else if(object instanceof BuildingUnitInfo)
		{
			BuildingUnitInfo buildingUnit = (BuildingUnitInfo)object;
			builder.appendSql(" left join t_she_buildingUnit buildUnit on room.FBuildUnitID = buildUnit.fid ");
			builder.appendSql(" where (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount' or money.FMoneyType ='PeriodicityAmount'  and money.FSysType ='TenancySys')");
			builder.appendSql(" and buildUnit.fid ='"+buildingUnit.getId().toString()+"'");
		}else
		{
			builder.appendSql(" where room.fid is null");
		}
	}

	protected void setBeforeTenState(FDCSQLBuilder builder)
	{
		builder.appendSql("select buildPro.fid buildingProID,buildPro.fname_l2 name,room.ftenancyState tenState,");
		builder.appendSql(" sum(FTenancyArea) buildingArea ,count(room.fid) count from t_she_room room");
		builder.appendSql(" left join t_she_buildingProperty buildPro on room.fbuildingPropertyid = buildPro.fid ");
	}

	//统计不同租赁状态的套数和面积
	protected FDCSQLBuilder getTenStateSql(Object object)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		this.setBeforeTenState(builder);
		this.setBuilder(builder, object);
		builder.appendSql(" group by buildPro.fid,room.ftenancyState,buildPro.fname_l2");
		return builder;
	}

	protected void setBeforeRecAmountBuilder(FDCSQLBuilder builder)
	{
		builder.appendSql("select buildPro.fid buildingProID,sum(fdcEntry.frevAmount) sumRevAmount from T_BDC_FDCReceivingBillEntry fdcEntry ");
//		builder.appendSql(" left join t_cas_receivingbill cas on cas.fid=fdcentry.FReceivingBillID ");
		builder.appendSql(" left join T_BDC_FDCReceivingBill fdc on fdcEntry.fheadid=fdc.fid ");
		builder.appendSql(" left join t_she_room room on fdc.froomid=room.fid  ");
		builder.appendSql(" left join t_she_buildingProperty buildPro on room.fbuildingPropertyid = buildPro.fid ");
		builder.appendSql(" left join t_ten_TenancyBill as tenBill on room.FLastTenancyID= tenBill.fid");
		builder.appendSql(" left join t_she_moneyDefine money on  fdcentry.FMoneyDefineId=money.fid ");	
	}
	
	//本月已收款总额的SQL
	protected FDCSQLBuilder getRecAmountSql(Object object)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		this.setBeforeRecAmountBuilder(builder);
		this.setReceiveBuilder(builder, object);
		this.appendFilterSql(builder, "fdc.FBizDate");
		builder.appendSql(" group by buildPro.fid");
		//return builder.getSql();
		return builder;
	}

	
	public void appendFilterSql(FDCSQLBuilder builder, String proName) {
		Date beginDate = FDCDateHelper.getFirstDayOfCurMonth();
		if (beginDate != null) {
			builder.appendSql(" and " + proName + " >= ? ");
			builder.addParam(FDCDateHelper.getSqlDate(beginDate));
		}
		Date endDate = FDCDateHelper.getLastDayOfCurMonth();
		if (endDate != null) {
			builder.appendSql(" and " + proName + " < ? ");
			builder.addParam(FDCDateHelper.getNextDay(endDate));
		}
	}

	//类别和总面积
	protected void getRow(Map buildProMap,FDCSQLBuilder builder)	
	{
		IRowSet countSet = null;			
		try {
			countSet =  builder.executeQuery();
			while(countSet.next())
			{
				String buildPro = countSet.getString("buildingProID");					
				String name = countSet.getString("name");
				BigDecimal buildingArea = countSet.getBigDecimal("buildingArea");
				if(buildingArea==null)
				{
					buildingArea = FDCHelper.ZERO;
				}else
				{
					buildingArea = buildingArea.divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
				}
				int totalCount = countSet.getInt("count");
				IRow row = this.tblTenStat.addRow();
				buildProMap.put(buildPro, row);
				row.getCell("tenType").setValue(name);
				row.getCell("allArea").setValue(totalCount+"/"+buildingArea+"m2");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	//本月已收款总额
	protected void getRecAmount(Map buildProMap,FDCSQLBuilder builder)
	{
		Map recAmountMap = new HashMap();
		IRowSet countSet3 = null;
		try {
			countSet3 =  builder.executeQuery();
			while(countSet3.next())
			{
				String buildPro = countSet3.getString("buildingProID");	
				BigDecimal sumRecAmount = countSet3.getBigDecimal("sumRevAmount");
				recAmountMap.put(buildPro, sumRecAmount);

			}

			Set keySet = buildProMap.keySet();
			Iterator iterator = keySet.iterator();
			while(iterator.hasNext()) {
				String buidIdStr = (String)iterator.next();
				IRow thisRow = (IRow)buildProMap.get(buidIdStr);
				BigDecimal sumRecAmount = (BigDecimal)recAmountMap.get(buidIdStr);
				if(sumRecAmount==null)
				{
					sumRecAmount = new BigDecimal(0);
				}
				thisRow.getCell("actAmount").setValue(sumRecAmount);
			}
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	//放租面积，已租面积和出租率
	protected void getRowSet(Map buildProMap,FDCSQLBuilder builder)
	{
		//未放租套数,面积
		Map UNTenancyMap = new HashMap();
		Map unAreaMap = new HashMap();
		//待租套数,面积
		Map WaitTenancyMap = new HashMap();
		Map waitAreaMap = new HashMap();
		//保留套数,面积
		Map KeepTenancyMap = new HashMap();
		Map keepAreaMap = new HashMap();
		//新租套数,面积
		Map newTenancyMap = new HashMap();
		Map newAreaMap = new HashMap();
		//续租套数,面积
		Map conTenaycyMap = new HashMap();
		Map conAreaMap = new HashMap();
		//扩租套数,面积
		Map enlargTenMap = new HashMap();
		Map enlargAreaMap = new HashMap();
		IRowSet countSet2 = null;
		try {
			countSet2 = builder.executeQuery();
			while(countSet2.next())
			{
				String buildPro = countSet2.getString("buildingProID");					
				String tenState = countSet2.getString("tenState");
				BigDecimal buildingArea = countSet2.getBigDecimal("buildingArea");
				if(buildingArea==null)
				{
					buildingArea = FDCHelper.ZERO;
				}
				int totalCount = countSet2.getInt("count");
				if(newTenancyMap.get(buildPro)==null || conTenaycyMap.get(buildPro)==null || enlargTenMap.get(buildPro)==null
						|| UNTenancyMap.get(buildPro)==null || WaitTenancyMap.get(buildPro)==null || KeepTenancyMap.get(buildPro)==null) {
					if(tenState !=null && (TenancyStateEnum.NEWTENANCY_VALUE).equals(tenState))
					{
						newTenancyMap.put(buildPro,new Integer(totalCount));
						newAreaMap.put(buildPro,buildingArea);
					}
					else if(tenState !=null && (TenancyStateEnum.CONTINUETENANCY_VALUE).equals(tenState))
					{
						conTenaycyMap.put(buildPro,new Integer(totalCount));
						conAreaMap.put(buildPro,buildingArea);
					}else if(tenState !=null && (TenancyStateEnum.ENLARGETENANCY_VALUE).equals(tenState))
					{
						enlargTenMap.put(buildPro,new Integer(totalCount));
						enlargAreaMap.put(buildPro,buildingArea);
					}else if(tenState !=null && (TenancyStateEnum.UNTENANCY_VALUE).equals(tenState))
					{
						UNTenancyMap.put(buildPro,new Integer(totalCount));
						unAreaMap.put(buildPro,buildingArea);
					}else if(tenState !=null && (TenancyStateEnum.WAITTENANCY_VALUE).equals(tenState))
					{
						WaitTenancyMap.put(buildPro,new Integer(totalCount));
						waitAreaMap.put(buildPro,buildingArea);
					}else if(tenState !=null && (TenancyStateEnum.KEEPTENANCY_VALUE).equals(tenState))
					{
						KeepTenancyMap.put(buildPro,new Integer(totalCount));
						keepAreaMap.put(buildPro,buildingArea);
					}
				}else
				{
					if(tenState!=null && (TenancyStateEnum.NEWTENANCY_VALUE).equals(tenState))
					{
						int newCount = ((Integer)newTenancyMap.get(buildPro)).intValue();
						newTenancyMap.put(buildPro,new Integer(newCount+totalCount));
						BigDecimal area = (BigDecimal)newAreaMap.get(buildPro);
						newAreaMap.put(buildPro,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.CONTINUETENANCY_VALUE).equals(tenState))
					{
						int continueCount = ((Integer)conTenaycyMap.get(buildPro)).intValue();
						conTenaycyMap.put(buildPro,new Integer(continueCount+totalCount));
						BigDecimal area = (BigDecimal)conAreaMap.get(buildPro);
						conAreaMap.put(buildPro,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.ENLARGETENANCY_VALUE).equals(tenState))
					{
						int enlargCount = ((Integer)enlargTenMap.get(buildPro)).intValue();
						enlargTenMap.put(buildPro,new Integer(enlargCount+totalCount));
						BigDecimal area = (BigDecimal)enlargAreaMap.get(buildPro);
						enlargAreaMap.put(buildPro,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.UNTENANCY_VALUE).equals(tenState))
					{
						int unTenCount = ((Integer)UNTenancyMap.get(buildPro)).intValue();
						UNTenancyMap.put(buildPro,new Integer(unTenCount+totalCount));
						BigDecimal area = (BigDecimal)unAreaMap.get(buildPro);
						unAreaMap.put(buildPro,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.WAITTENANCY_VALUE).equals(tenState))
					{
						int waitTenCount = ((Integer)WaitTenancyMap.get(buildPro)).intValue();
						WaitTenancyMap.put(buildPro,new Integer(waitTenCount+totalCount));
						BigDecimal area = (BigDecimal)waitAreaMap.get(buildPro);
						waitAreaMap.put(buildPro,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.KEEPTENANCY_VALUE).equals(tenState))
					{
						int keepTenCount = ((Integer)KeepTenancyMap.get(buildPro)).intValue();
						KeepTenancyMap.put(buildPro,new Integer(keepTenCount+totalCount));
						BigDecimal area = (BigDecimal)keepAreaMap.get(buildPro);
						keepAreaMap.put(buildPro,area.add(buildingArea));
					}
				}	

				Set keySet = buildProMap.keySet();
				Iterator iterator = keySet.iterator();
				while(iterator.hasNext()) {
					String buidIdStr = (String)iterator.next();
					IRow thisRow = (IRow)buildProMap.get(buidIdStr);
					//新租套数,面积
					Integer newTenCount = (Integer)newTenancyMap.get(buidIdStr);
					BigDecimal newArea = (BigDecimal)newAreaMap.get(buidIdStr);
					//扩租套数,面积
					Integer expandTenCount = (Integer)enlargTenMap.get(buidIdStr);
					BigDecimal enlargArea = (BigDecimal)enlargAreaMap.get(buidIdStr);
					//续租套数,面积
					Integer continueCount = (Integer)conTenaycyMap.get(buidIdStr);
					BigDecimal conArea = (BigDecimal)conAreaMap.get(buidIdStr);
					//未放租套数,面积
					Integer unTenCount = (Integer)UNTenancyMap.get(buidIdStr);
					BigDecimal unArea = (BigDecimal)unAreaMap.get(buidIdStr);
					//待租套数,面积
					Integer waitTenCount = (Integer)WaitTenancyMap.get(buidIdStr);
					BigDecimal waitArea = (BigDecimal)waitAreaMap.get(buidIdStr);
					//保留套数,面积
					Integer keepTenCount = (Integer)KeepTenancyMap.get(buidIdStr);
					BigDecimal keepArea = (BigDecimal)keepAreaMap.get(buidIdStr);
					if(newTenCount ==null)
					{
						newTenCount = new Integer(0);
					}if(expandTenCount ==null)
					{
						expandTenCount = new Integer(0);
					}
					if(continueCount ==null)
					{
						continueCount = new Integer(0);
					}if(unTenCount ==null)
					{
						unTenCount = new Integer(0);
					}
					if(waitTenCount ==null)
					{
						waitTenCount = new Integer(0);
					}if(keepTenCount ==null)
					{
						keepTenCount = new Integer(0);
					}if(newArea == null)
					{
						newArea = new BigDecimal(0);
					}if(enlargArea == null)
					{
						enlargArea = new BigDecimal(0);
					}
					if(conArea == null)
					{
						conArea = new BigDecimal(0);
					}
					if(unArea == null)
					{
						unArea = new BigDecimal(0);
					}
					if(waitArea == null)
					{
						waitArea = new BigDecimal(0);
					}if(keepArea == null)
					{
						keepArea = new BigDecimal(0);
					}
					//总面积
					BigDecimal allArea = newArea.add(enlargArea).add(conArea).add(unArea).add(waitArea).add(keepArea);
					//放租套数
					int tenCount = newTenCount.intValue()+expandTenCount.intValue()+continueCount.intValue()
					+waitTenCount.intValue()+keepTenCount.intValue();
					Integer tenCount2 = new Integer(tenCount);
					//放租面积
					BigDecimal tenArea = newArea.add(enlargArea).add(conArea).add(waitArea).add(keepArea);
					tenArea = tenArea.divide(new BigDecimal(1), 2,BigDecimal.ROUND_HALF_UP);
					//已租套数
					int alreadyCount = newTenCount.intValue()+expandTenCount.intValue()+continueCount.intValue();
					//已租面积
					BigDecimal alreadyArea = newArea.add(enlargArea).add(conArea);
					alreadyArea = alreadyArea.divide(new BigDecimal(1), 2,BigDecimal.ROUND_HALF_UP);
					BigDecimal area = allArea;
					if(area.equals(new BigDecimal(0)))
					{
						area = new BigDecimal(1);
					}
					//先保留4位再乘100再除1保留2位这样是为了精度问题
					BigDecimal tenRate =  alreadyArea.divide(area,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).divide(new BigDecimal(1), 2,BigDecimal.ROUND_HALF_UP);
					String tenArea2 = tenCount2+"/"+tenArea+"m2";
					thisRow.getCell("tenArea").setValue(tenArea2);
					thisRow.getCell("alreadyArea").setValue(alreadyCount +"/"+alreadyArea+"m2");
					thisRow.getCell("ten").setValue(tenRate+"%");
				}
			}					
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}

	protected String getEditUIName()
	{
		return null;
	}

	/**
	 * 房间保留
	 */
	public void actionKeepRoom_actionPerformed(ActionEvent e) throws Exception
	{
		RoomInfo room = this.getSelectRoom(false);

		if(room == null)
			return;

		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("room", room.getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("tenancy.state",FDCBillStateEnum.SAVED,CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("tenancy.tenancyState",TenancyBillStateEnum.BlankOut,CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("tenancy.tenancyState",TenancyBillStateEnum.Expiration,CompareType.NOTEQUALS));

		if (TenancyRoomEntryFactory.getRemoteInstance().exists(filter))
		{
			MsgBox.showInfo("该房间存在未处理的合同,不能进行此项操作!");
			return;
		}
		TenancyStateEnum tenancyState = room.getTenancyState();
		//保留
		if (TenancyStateEnum.waitTenancy.equals(tenancyState))
		{
			UIContext uiContext = new UIContext(this);
			uiContext.put("room", room);
			uiContext.put("building", room.getBuilding());
			uiContext.put("buildUnit", room.getBuildUnit());
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
			.create(KeepRoomDownEditUI.class.getName(), uiContext,
					null, "ADDNEW");
			uiWindow.show();
		} else if (TenancyStateEnum.keepTenancy.equals(tenancyState))
		{
			int result = MsgBox.showConfirm2("是否确定取消保留该房间？");
			if (result != MsgBox.YES)
			{
				SysUtil.abort();
			}
			KeepRoomDownFactory.getRemoteInstance().cancelKeepRoom(room.getId().toString());
		}
		this.refresh(null);
	}

	public void actionReceiveBill_actionPerformed(ActionEvent e)
	throws Exception
	{
		super.actionReceiveBill_actionPerformed(e);
		RoomInfo room = this.getSelectRoom(false);
		if (room == null)
		{
			return;
		}
		UIContext uiContext = new UIContext(this);
		if(TenancyStateEnum.sincerObligate.equals(room.getTenancyState()))
		{
			Set billStateSet = TenancyHelper.getQueryBillStates(RevBizTypeEnum.obligate,RevBillTypeEnum.gathering);
			SincerObligateCollection sincerColl = TenancyHelper.getValidObligateByRoomAndState(room, billStateSet);
			if(sincerColl.size()==1)
			{				
				SincerObligateInfo obligateInfo = sincerColl.get(0);
				uiContext.put(FDCReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.obligate);		    
		    	uiContext.put(FDCReceivingBillEditUI.KEY_SINCER_OBLIGATE, obligateInfo);
			}else if(sincerColl.size()==0)//没找到对应的诚意预留单可能是转认租了继续找满足条件条件的合同
			{
				Set tenbillStateSet = TenancyHelper.getQueryBillStates(RevBizTypeEnum.tenancy,RevBillTypeEnum.gathering);
				TenancyBillCollection tenancyBillColl = TenancyHelper.getValidTenancyContractByRoomAndState(room, tenbillStateSet);
				if(tenancyBillColl != null && tenancyBillColl.size()<1)
				{
					logger.warn("既未取得房间号为 "+ room.getNumber() +" 的有效预留单也未取得有效合同！");
					if(this.getOprtState() != OprtState.VIEW && this.getOprtState() != OprtState.EDIT)
					{
						MsgBox.showInfo("该房间既没有进行收款操作的预留单也没有进行收款操作的合同！");
					}
					this.abort();
				}else{
					TenancyBillInfo tenancy = new TenancyBillInfo();
					if(tenancyBillColl != null){
						//如果客户在右边的房间信息的‘业务’页签里选中了一条数据 且是可以进行收款动作的！则在收款单界面默认带出的是选中的合同 eirc_wang 2010.08.23
						if(this.detailUI!=null&&getSelectTenancyBillInfo()!=null){
								boolean flag =true;
								for(int i =0;i<tenancyBillColl.size();i++){
									TenancyBillInfo tenancy1 = tenancyBillColl.get(i);
									if(tenancy1.getId().toString().equals(getSelectTenancyBillInfo())){
										tenancy =tenancy1;
										flag =false;
										break;
									}
								}
									if(flag){
										//你选择的合同不能做收款操作
										MsgBox.showInfo("您所选择的合同不允许进行收款");
										this.abort();
									}
								
						}else{
							tenancy = tenancyBillColl.get(0);
						}
					}
					uiContext.put(FDCReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.tenancy);
			    	uiContext.put(FDCReceivingBillEditUI.KEY_TENANCY_BILL, tenancy);			
				}
			}
		}else{
			Set billStateSet = TenancyHelper.getQueryBillStates(RevBizTypeEnum.tenancy,RevBillTypeEnum.gathering);
			TenancyBillCollection tenancyBillColl = TenancyHelper.getValidTenancyContractByRoomAndState(room, billStateSet);
			if(tenancyBillColl != null && tenancyBillColl.size() < 1)
			{
				logger.warn("未取得房间号为 "+ room.getNumber() +" 的有效合同！");
				if(this.getOprtState() != OprtState.VIEW && this.getOprtState() != OprtState.EDIT)
				{
					MsgBox.showInfo("该房间没有进行收款操作的合同！");
				}
				this.abort();
			}else{
					TenancyBillInfo tenancy = new TenancyBillInfo();
					if(tenancyBillColl != null){
						//如果客户在右边的房间信息的‘业务’页签里选中了一条数据 且是可以进行收款动作的！则在收款单界面默认带出的是选中的合同 eirc_wang 2010.08.23
						if(this.detailUI!=null&&getSelectTenancyBillInfo()!=null){
								boolean flag =true;
								for(int i =0;i<tenancyBillColl.size();i++){
									TenancyBillInfo tenancy1 = tenancyBillColl.get(i);
									if(tenancy1.getId().toString().equals(getSelectTenancyBillInfo())){
										tenancy =tenancy1;
										flag =false;
										break;
									}
								}
									if(flag){
										//你选择的合同不能做收款操作
										MsgBox.showInfo("您所选择的合同不允许进行收款");
										this.abort();
									}
								
						}else{
							tenancy = tenancyBillColl.get(0);
						}
					}
					uiContext.put(FDCReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.tenancy);
			    	uiContext.put(FDCReceivingBillEditUI.KEY_TENANCY_BILL, tenancy);			
				}
			}
		
		uiContext.put("sellProject", room.getBuilding().getSellProject());
		uiContext.put(FDCReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
    	uiContext.put(FDCReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, Boolean.TRUE);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
		.create(TENReceivingBillEditUI.class.getName(), uiContext, null,
		"ADDNEW");
		uiWindow.show();
		this.refresh(null);
		
	}


	public void actionView_actionPerformed(ActionEvent e) throws Exception
	{
		RoomInfo room = this.getSelectRoom(false);
		if (room != null)
		{
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", room.getId().toString());
			IUIWindow uiWindow = UIFactory
			.createUIFactory(UIFactoryName.MODEL)
			.create(RoomEditUI.class.getName(), uiContext, null, "VIEW");
			uiWindow.show();
		}
	}

	/**
	 * 撤租操作 
	 * i. 只有待租状态的房间可以撤租. 
	 * ii. 校验包含该房间的合同都是作废或终止状态. 
	 * iii.给予提示”是否撤租?”,是则将房间置为未放租状态.
	 */
	public void actionCancelTenancy_actionPerformed(ActionEvent e)
	throws Exception
	{
		RoomInfo room = this.getSelectRoom(true);

		if (room == null)
			return;

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("room", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",TenancyBillStateEnum.Expiration,
				CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",TenancyBillStateEnum.BlankOut,
				CompareType.NOTEQUALS));
		if (TenancyRoomEntryFactory.getRemoteInstance().exists(filter))
		{
			MsgBox.showInfo("该房间存在未处理完成的合同，不能进行撤租操作!");
			return;
		} else
		{
			int result = MsgBox.showConfirm2("是否确定对所选择的房间进行撤租操作？");
			if (result != MsgBox.YES)
			{
				this.abort();
			}
			room.setTenancyState(TenancyStateEnum.unTenancy);

			SelectorItemCollection selectorItemColl = new SelectorItemCollection();
			selectorItemColl.add("tenancyState");

			RoomFactory.getRemoteInstance().updatePartial(room,selectorItemColl);
		}
		this.refresh(null);
	}

	protected TenancyRoomEntryCollection getTenRoomColl(RoomInfo room,Set tempSet) throws BOSException
	{
		FilterInfo filter = new FilterInfo();		
		filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",tempSet,CompareType.INCLUDE));

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("tenancy.*");
		view.getSelector().add("tenancy.sellProject.*");
		view.setFilter(filter);

		TenancyRoomEntryCollection tenancyRoomEntryColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
		return tenancyRoomEntryColl;
	}
	
	/**
	 * 房间交接的操作
	 */
	public void actionHandleTenancy_actionPerformed(ActionEvent e) throws Exception
	{
		RoomInfo room = this.getSelectRoom(true);

		if (room == null)
			return;

		//如果房间是待租的状态，则直接找是否包含该房间的已交押金首租或者半执行的合同
		if(TenancyStateEnum.waitTenancy.equals(room.getTenancyState())||TenancyStateEnum.sincerObligate.equals(room.getTenancyState()))
		{
			Set tempSet = new HashSet();
			tempSet.add(TenancyBillStateEnum.PARTEXECUTED_VALUE);
			tempSet.add(TenancyBillStateEnum.AUDITED_VALUE);
			TenancyRoomEntryCollection tenancyRoomEntryColl = this.getTenRoomColl(room,tempSet);
			if(tenancyRoomEntryColl == null)
			{
				logger.warn("调用远程接口取数发生错误"+e);
				MsgBox.showInfo("调用远程接口查找合同发生系统错误！");
				return;
			}
			if (tenancyRoomEntryColl.size()<1)
			{
				MsgBox.showInfo("该房间没有存在交接条件的合同！");
				return;
			}else if(tenancyRoomEntryColl.size() > 1)
			{
				logger.warn("待租状态的房间，找到了一个以上的 已审批 状态的合同，请检查租赁合同模块程序逻辑....");
				MsgBox.showInfo("系统逻辑错误，已记录日志，请告知管理员！");
				return;
			}else// 在找到了合同的情况下，做如下的这些操作
			{
				//找到这个房间在合同里是否交够了押金首付
				TenancyRoomEntryInfo tenEntryInfo = tenancyRoomEntryColl.get(0);
				BigDecimal depositAmount = tenEntryInfo.getDepositAmount();
				depositAmount = depositAmount==null?new BigDecimal(0):depositAmount;
				BigDecimal firstPayRent = tenEntryInfo.getFirstPayAmount();
				firstPayRent = firstPayRent==null?new BigDecimal(0):firstPayRent;
				BigDecimal rent = depositAmount.add(firstPayRent);
				TenancyBillInfo tenBillInfo = tenEntryInfo.getTenancy();
				BigDecimal recRent = TenancyHelper.getRecAmount(room.getId().toString(),tenBillInfo.getId().toString());
				if(recRent==null)
				{
					recRent = new BigDecimal(0);
				}
				//看看收够钱了没有
//				if(recRent.equals(new BigDecimal(0)))
//				{
//					MsgBox.showInfo("房间"+tenEntryInfo.getRoomLongNum()+"还没有交租");
//					this.abort();
//				}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//				{
//					MsgBox.showInfo("房间"+tenEntryInfo.getRoomLongNum()+"没有交齐首租押金");
//					this.abort();
//				}else 
				if(tenEntryInfo.getActDeliveryRoomDate()!=null)
				{
					MsgBox.showInfo("房间"+tenEntryInfo.getRoomLongNum()+"已经交过房了");
					this.abort();
				}else
				{
					// 弹出交接界面的操作
					if(!tenEntryInfo.getTenancy().getTenancyType().equals(TenancyContractTypeEnum.NewTenancy)
							&&(TenancyStateEnum.sincerObligate.equals(room.getTenancyState()))){
						MsgBox.showInfo("房间"+tenEntryInfo.getRoomLongNum()+"必须为新租状态");
						this.abort();
						
					}
					UIContext uiContext = new UIContext(this);
					uiContext.put("ID", room.getId().toString());
					uiContext.put("sellProject", tenEntryInfo.getTenancy().getSellProject());
					uiContext.put("tenancyBillInfo",tenEntryInfo.getTenancy());
					uiContext.put("tenancyBillId",tenEntryInfo.getTenancy().getId().toString());
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(HandleRoomTenancyEditUI.class.getName(), uiContext,
							null, OprtState.ADDNEW);
					uiWindow.show();
				}				
			}

		}else if(TenancyStateEnum.sincerObligate.equals(room.getTenancyState()))
		{
			MsgBox.showInfo("该房间已经预留不需要交接！");
			return;
		}
		//房间是已租的状态，则查找正在执行的合同----------------这里还没有考虑到半执行状态的合同能否进行续租，改租，转名等操作
		else 
		{
			Set tempSet = new HashSet();
			tempSet.add(TenancyBillStateEnum.CONTINUETENANCYING_VALUE);
			tempSet.add(TenancyBillStateEnum.REJIGGERTENANCYING_VALUE);
			tempSet.add(TenancyBillStateEnum.CHANGENAMING_VALUE);
			tempSet.add(TenancyBillStateEnum.TENANCYCHANGING_VALUE);
			tempSet.add(TenancyBillStateEnum.QUITTENANCYING_VALUE);
			tempSet.add(TenancyBillStateEnum.AUDITED_VALUE);
			tempSet.add(TenancyBillStateEnum.PARTEXECUTED_VALUE);
			TenancyRoomEntryCollection tenancyRoomEntryColl = this.getTenRoomColl(room,tempSet);
			if(tenancyRoomEntryColl == null)
			{
				logger.warn("调用远程接口取数发生错误"+e);
				MsgBox.showInfo("调用远程接口查找合同发生系统错误！");
				return;
			}
			if (tenancyRoomEntryColl.size()<1)
			{
				MsgBox.showInfo("该房间对应的合同状态是<执行中>且已完成交接，不允许再对此房间进行交接操作！");
				return;
			}
			// 在找到了合同的情况下，做如下的这些操作
			else
			{
				TenancyRoomEntryInfo tenEntryInfo = tenancyRoomEntryColl.get(0);
				TenancyBillInfo tenBillInfo = tenEntryInfo.getTenancy();
				// 弹出交接界面的操作
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", room.getId().toString());
				uiContext.put("sellProject", tenEntryInfo.getTenancy().getSellProject());
				uiContext.put("tenancyBillInfo",tenBillInfo);
				uiContext.put("tenancyBillId",tenEntryInfo.getTenancy().getId().toString());
				uiContext.put("tenancyRoomEntryColl",tenancyRoomEntryColl);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(HandleRoomTenancyEditUI.class.getName(), uiContext,
						null, OprtState.ADDNEW);
				uiWindow.show();
			}
		}

	}

	/**
	 * 认租申请的操作
	 */
	public void actionApplyTenancy_actionPerformed(ActionEvent e) throws Exception 
	{
		RoomInfo room = this.getSelectRoom(true);

		if (room == null)
			return;

		/*
		 * 分不同的房间租赁状态去处理
		 */
		//放租状态的房间，直接调用合同界面
		if(TenancyStateEnum.waitTenancy.equals(room.getTenancyState())||(TenancyStateEnum.sincerObligate.equals(room.getTenancyState())))
		{
			//			 弹出交接界面的操作
			RoomCollection roomColl = new RoomCollection();
			roomColl.add(room);
			UIContext uiContext = new UIContext(this);
			uiContext.put(TenancyBillConstant.KEY_ROOMS, roomColl);
			uiContext.put(TenancyBillConstant.KEY_SELL_PROJECT, room.getBuilding().getSellProject());
			uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE,TenancyContractTypeEnum.NewTenancy);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
			.create(TenancyBillEditUI.class.getName(), uiContext,
					null, OprtState.ADDNEW);
			uiWindow.show();
		}//查找该房间当前合同
		else
		{
			TenancyBillInfo tenancy = room.getLastTenancy();
			if(tenancy == null){
				logger.error("程序逻辑错误或脏数据，已租的房间lastTenancy怎么会为空呢.");
				abort();
			}

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("room", room.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("tenancy.id", tenancy.getId().toString()));

			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("flagAtTerm");
			view.setFilter(filter);

			TenancyRoomEntryCollection tenancyRoomEntryColl = TenancyRoomEntryFactory
			.getRemoteInstance().getTenancyRoomEntryCollection(view);
			if (tenancyRoomEntryColl.size() < 1)
			{
				logger.error("逻辑或数据错误");
				abort();
			}
			//			当前合同对应多个正在执行的合同，理论上应该不存在的。 
			else if(tenancyRoomEntryColl.size()>1)
			{
				logger.error("逻辑或数据错误");
				abort();
			}
			// 在找到了合同的情况下，判断改房间的到期标记
			else
			{
				TenancyRoomEntryInfo tenancyRoomEntryInfo = tenancyRoomEntryColl
				.get(0);
				if (FlagAtTermEnum.QuitAtTerm.equals(tenancyRoomEntryInfo
						.getFlagAtTerm()))
				{

					// 弹出交接界面的操作
					RoomCollection roomColl = new RoomCollection();
					roomColl.add(room);

					UIContext uiContext = new UIContext(this);
					uiContext.put(TenancyBillConstant.KEY_ROOMS, roomColl);
					uiContext.put(TenancyBillConstant.KEY_SELL_PROJECT, room.getBuilding().getSellProject());

					uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE,TenancyContractTypeEnum.NewTenancy);
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
					.create(TenancyBillEditUI.class.getName(), uiContext,
							null, OprtState.ADDNEW);
					uiWindow.show();
				}else{
					MsgBox.showInfo("正在执行的租赁合同中,该房间到期标记不为到期退租,不适合租赁!");
					return;
				}
			}
		}
	}

	/**
	 * 续租申请的操作
	 */
	public void actionContinueTenancy_actionPerformed(ActionEvent e) throws Exception
	{
		RoomInfo room = this.getSelectRoom(true);
		if (room == null)
			return;

		//查找当前正在执行的合同
		TenancyBillInfo tenancy = room.getLastTenancy();
		if(tenancy == null){
			logger.error("已租的房间lastTenancy为空.");
			abort();
		}

		commonVerify2(tenancy.getId().toString());

		UIContext uiContext = new UIContext(this);
		uiContext.put(TenancyBillConstant.KEY_OLD_TENANCY_BILL_ID,tenancy.getId().toString());
		uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE,TenancyContractTypeEnum.ContinueTenancy);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
		.create(TenancyBillEditUI.class.getName(), uiContext,
				null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * 判断该合同是否存在非保存状态的退租单,非保存状态的改租或续租或更名的合同
	 * 如果存在,对话框提示并中断操作
	 * */
	private void commonVerify2(String tenId) throws EASBizException, BOSException {
		if (TenancyHelper.existQuitTenBillByTenBill(QuitTenancyFactory.getRemoteInstance(), tenId, null)) {
			MsgBox.showInfo(this, "当前房间存在有效状态的退租单，不能进行该操作！！");
			this.abort();
		}

		String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(tenId);
		if (targetTenId != null) {
			MsgBox.showInfo(this, "该房间存在有效状态的续租或改租或转名的合同，不能进行该操作！");
			this.abort();
		}

		if(TenancyHelper.existRentRemissionByTenBill(RentRemissionFactory.getRemoteInstance(), tenId)){
			MsgBox.showInfo(this, "该房间正在进行租金减免，不能进行该操作！");
			this.abort();
		}

		if(TenancyHelper.existTenancyModificationByTenBill(TenancyModificationFactory.getRemoteInstance(), tenId)){
			MsgBox.showInfo(this, "该房间正在进行合同变更，不能进行该操作！");
			this.abort();
		}
	}

	/**
	 * 改租操作
	 */
	public void actionRejiggerTenancy_actionPerformed(ActionEvent e) throws Exception
	{

		RoomInfo room = this.getSelectRoom(true);
		if (room == null)
			return;

		//查找当前正在执行的合同
		TenancyBillInfo tenancy = room.getLastTenancy();
		if(tenancy == null){
			logger.error("已租的房间lastTenancy为空.");
			abort();
		}

		commonVerify2(tenancy.getId().toString());

		// 弹出改租界面的操作
		UIContext uiContext = new UIContext(this);
		uiContext.put(TenancyBillConstant.KEY_OLD_TENANCY_BILL_ID, tenancy.getId().toString());
		uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE, TenancyContractTypeEnum.RejiggerTenancy);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(TenancyBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * 退租申请 逻辑同续租操作
	 */
	public void actionQuitTenancy_actionPerformed(ActionEvent e) throws Exception
	{
		RoomInfo room = this.getSelectRoom(true);
		if (room == null)
			return;

		//查找当前正在执行的合同
		TenancyBillInfo tenancy = room.getLastTenancy();
		if(tenancy == null){
			logger.error("已租的房间lastTenancy为空.");
			abort();
		}
		commonVerify2(tenancy.getId().toString());
		// 弹出退租界面的操作
		UIContext uiContext = new UIContext(this);
		uiContext.put(QuitTenancyEditUI.KEY_TENANCY_ID, tenancy.getId().toString());
		uiContext.put("sellProject",tenancy.getSellProject());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(QuitTenancyEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * 转名操作 逻辑同改租操作
	 */
	public void actionChangeName_actionPerformed(ActionEvent e) throws Exception
	{
		RoomInfo room = this.getSelectRoom(true);
		if (room == null)
			return;

		//查找当前正在执行的合同
		TenancyBillInfo tenancy = room.getLastTenancy();
		if(tenancy == null){
			logger.error("已租的房间lastTenancy为空.");
			abort();
		}
		commonVerify2(tenancy.getId().toString());
		UIContext uiContext = new UIContext(this);
		uiContext.put(TenancyBillConstant.KEY_OLD_TENANCY_BILL_ID, tenancy.getId().toString());
		uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE, TenancyContractTypeEnum.ChangeName);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(TenancyBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	
	protected void solidSideRadioBtn_actionPerformed(ActionEvent e)
			throws Exception {
		refresh(null);
		this.kDPanel1.remove(tblMain);
		this.kDPanel1.remove(effectImagePanel);
		this.kDPanel1.add(solidSideImagePanel,BorderLayout.CENTER);
		kDPanel1.repaint();
		kDPanel1.validate();
		kDPanel1.revalidate();
		refresh(null);
		kDLabelContainerZoom.setVisible(true);
		pnlRoomPic.removeMouseListener(moListener);
		pnlRoomPic.removeMouseMotionListener(mmListener);
		topX=0;topY=0;downX=0;downY=0;
	}
	
	protected void planeRadioBtn_actionPerformed(ActionEvent e)
			throws Exception {
		refresh(null);
		this.kDPanel1.remove(solidSideImagePanel);
		this.kDPanel1.remove(effectImagePanel);
		this.kDPanel1. add(tblMain,BorderLayout.CENTER);
		kDPanel1.repaint();
		kDPanel1.validate();
		kDPanel1.revalidate();
		refresh(null);
		kDLabelContainerZoom.setVisible(false);
		pnlRoomPic.removeMouseListener(moListener);
		pnlRoomPic.removeMouseMotionListener(mmListener);
		topX=0;topY=0;downX=0;downY=0;
	}
	
	protected void effectRadioBtn_actionPerformed(ActionEvent e)
			throws Exception {
		refresh(null);
		this.kDPanel1.remove(tblMain);
		this.kDPanel1.remove(solidSideImagePanel);
		this.kDPanel1.add(effectImagePanel,BorderLayout.CENTER);
		refresh(null);
		kDLabelContainerZoom.setVisible(false);
		pnlRoomPic.repaint();
		pnlRoomPic.addMouseListener(moListener=new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
				//  目前只处理楼层效果图 其他类型效果图后期处理
//				if(node.getUserObject() instanceof PlanisphereInfo){//只有楼层效果图时 单击才会加载房间信息
				if(node.getUserObject() instanceof EffectImageAndPlanisphereInfo){//只有楼层效果图时 单击才会加载房间信息
					int x=e.getX();
					int y=e.getY();
//					FilterInfo filter=new FilterInfo();
//					BuildingFloorEntryInfo bf=getSelectBF((PlanisphereInfo)node.getUserObject());
//		    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDINGFLOOR_VALUE));
//		    		filter.getFilterItems().add(new FilterItemInfo("buildingFloor.id",bf.getId().toString()));
//					DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode)node.getParent();
//					//父节点是单元时加载区分单元的楼层效果图
//					if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingUnitInfo){
//						filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.TRUE));
//					}
//					//父节点是楼栋时加载不区分单元的楼层效果图
//					if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingInfo){
//						filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.FALSE));
//					}
//					EffectImageInfo info=getEffectImageInfo(filter);
					EffectImageAndPlanisphereInfo effectPlainInfo = (EffectImageAndPlanisphereInfo)node.getUserObject();
					EffectImageInfo info = effectPlainInfo.getEffectImageInfo();
					if(info==null || info.getElementEntry()==null){//如果没有设置热点或者没有导入效果图则不做任何处理
						return;
					}
					else{
						EffectImageElementEntryCollection entryColl=info.getElementEntry();
						for(int i=0;i<entryColl.size();i++){
							EffectImageElementEntryInfo entry=entryColl.get(i);
							if(entry.getHotspot1()==null || entry.getHotspot2()==null){
								continue;
							}
							String hotspot1=entry.getHotspot1();
							String hotspot2=entry.getHotspot2();
							if(hotspotInRec(x, y, hotspot1, hotspot2)){//单击在哪个热点区域就加载相关房间信息
								topX=Integer.parseInt(hotspot1.substring(0,hotspot1.indexOf(',')));
								topY=Integer.parseInt(hotspot1.substring(hotspot1.indexOf(',')+1,hotspot1.length()));
								downX=Integer.parseInt(hotspot2.substring(0,hotspot2.indexOf(',')));
								downY=Integer.parseInt(hotspot2.substring(hotspot2.indexOf(',')+1,hotspot2.length()));
								try {
									RoomInfo room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(entry.getElementID()));
									roomInfo=SHEHelper.queryRoomInfo(room.getId().toString());
									changeButtonState();
									actionReceiveBill.setEnabled(true);
									if (roomInfo != null)
									{
										if (detailUI == null)
										{
											UIContext uiContext = new UIContext(ui);
											uiContext.put(UIContext.ID, roomInfo.getId().toString());
											detailUI = (CoreUIObject) UIFactoryHelper
											.initUIObject(RoomDetailInfoUI.class.getName(),
													uiContext, null, "VIEW");
											detailUI.setDataObject(roomInfo);
											sclPanel.setViewportView(detailUI);
											sclPanel.setKeyBoardControl(true);
											return;
										}
										detailUI.getUIContext().put(UIContext.ID, roomInfo.getId().toString());
										detailUI.setDataObject(roomInfo);
										detailUI.loadFields();
									}
								} catch (EASBizException e1) {
									e1.printStackTrace();
								} catch (BOSException e1) {
									e1.printStackTrace();
								}
								break;//减少无谓的循环
							}
						}
					}
				}
				pnlRoomPic.repaint();
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
			}
			
		});
		pnlRoomPic.addMouseMotionListener(mmListener=new MouseMotionListener(){

			public void mouseDragged(MouseEvent e) {
			}

			public void mouseMoved(MouseEvent e) {
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
				//  目前只处理楼层效果图 其他类型效果图后期处理
//				if(node.getUserObject() instanceof PlanisphereInfo){//只有楼层效果图时 悬停才会加载房间名称
				if(node.getUserObject() instanceof EffectImageAndPlanisphereInfo){//只有楼层效果图时 悬停才会加载房间名称
					int x=e.getX();
					int y=e.getY();
//					FilterInfo filter=new FilterInfo();
//					BuildingFloorEntryInfo bf=getSelectBF((PlanisphereInfo)node.getUserObject());
//		    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDINGFLOOR_VALUE));
//		    		filter.getFilterItems().add(new FilterItemInfo("buildingFloor.id",bf.getId().toString()));
//					DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode)node.getParent();
//					//父节点是单元时加载区分单元的楼层效果图
//					if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingUnitInfo){
//						filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.TRUE));
//					}
//					//父节点是楼栋时加载不区分单元的楼层效果图
//					if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingInfo){
//						filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.FALSE));
//					}
//					EffectImageInfo info=getEffectImageInfo(filter);
					EffectImageAndPlanisphereInfo effectPlainInfo = (EffectImageAndPlanisphereInfo)node.getUserObject();
					EffectImageInfo info = effectPlainInfo.getEffectImageInfo();
					if(info==null || info.getElementEntry()==null){//如果没有设置热点或者没有导入效果图则不做任何处理
						return;
					}
					else{
						EffectImageElementEntryCollection entryColl=info.getElementEntry();
						for(int i=0;i<entryColl.size();i++){
							EffectImageElementEntryInfo entry=entryColl.get(i);
							if(entry.getHotspot1()==null || entry.getHotspot2()==null){
								continue;
							}
							String hotspot1=entry.getHotspot1();
							String hotspot2=entry.getHotspot2();
							if(hotspotInRec(x, y, hotspot1, hotspot2)){//悬停在哪个热点区域就加载相关房间名称
								try {
									RoomInfo room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(entry.getElementID()));
									roomInfo=SHEHelper.queryRoomInfo(room.getId().toString());
									pnlRoomPic.setToolTipText(roomInfo.getName());
								} catch (EASBizException e1) {
									e1.printStackTrace();
								} catch (BOSException e1) {
									e1.printStackTrace();
								}
							}
						}
					}
				}
			}
			
		});
	}

	public void actionResponse(Object obj) throws Exception {
		RoomInfo room=(RoomInfo)obj;
		roomInfo=SHEHelper.queryRoomInfo(room.getId().toString());
		changeButtonState();
		this.actionReceiveBill.setEnabled(true);
		if (roomInfo != null)
		{
			if (detailUI == null)
			{
				UIContext uiContext = new UIContext(ui);
				uiContext.put(UIContext.ID, roomInfo.getId().toString());
				detailUI = (CoreUIObject) UIFactoryHelper
				.initUIObject(RoomDetailInfoUI.class.getName(),
						uiContext, null, "VIEW");
				detailUI.setDataObject(roomInfo);
				sclPanel.setViewportView(detailUI);
				sclPanel.setKeyBoardControl(true);
				return;
			}
			detailUI.getUIContext().put(UIContext.ID, roomInfo.getId().toString());
			detailUI.setDataObject(roomInfo);
			detailUI.loadFields();
		}
	}

	public void actionBothClicked(Object obj) throws Exception {
		RoomInfo room=(RoomInfo)obj;
		roomInfo=SHEHelper.queryRoomInfo(room.getId().toString());
		if (roomInfo != null)
		{
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", roomInfo.getId().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(
					UIFactoryName.NEWTAB).create(RoomEditUI.class.getName(),
							uiContext, null, "VIEW");
			uiWindow.show();
		}
	}
	/**
	 *  立面图缩放比例设置
	 */
	protected void comboBoxZoom_itemStateChanged(ItemEvent e) throws Exception
    {
		if(e.getStateChange()==ItemEvent.SELECTED){
			String zoom=this.comboBoxZoom.getSelectedItem().toString();
			if(zoom.equals("1 m2:32 px")){
				selectedZoom=new BigDecimal("32");
				refresh(null);
			}
			if(zoom.equals("1 m2:16 px")){
				selectedZoom=new BigDecimal("16");
				refresh(null);
			}
			if(zoom.equals("1 m2:8 px")){
				selectedZoom=new BigDecimal("8");
				refresh(null);
			}
			if(zoom.equals("1 m2:4 px")){
				selectedZoom=new BigDecimal("4");
				refresh(null);
			}
			if(zoom.equals("1 m2:2 px")){
				selectedZoom=new BigDecimal("2");
				refresh(null);
			}
			if(zoom.equals("1 m2:1 px")){
				selectedZoom=new BigDecimal("1");
				refresh(null);								
			}
			if(zoom.equals("2 m2:1 px")){
				selectedZoom=new BigDecimal("0.5");
				refresh(null);								
			}
			if(zoom.equals("4 m2:1 px")){
				selectedZoom=new BigDecimal("0.25");
				refresh(null);								
			}
			if(zoom.equals("8 m2:1 px")){
				selectedZoom=new BigDecimal("0.125");
				refresh(null);								
			}
			if(zoom.equals("16 m2:1 px")){
				selectedZoom=new BigDecimal("0.063");
				refresh(null);								
			}
			if(zoom.equals("32 m2:1 px")){
				selectedZoom=new BigDecimal("0.032");
				refresh(null);
			}
		}
    }
	/**
	 * 设置可选比例
	 */
	private void setComboBoxZoomItem(){
		this.kDLabelContainerZoom.setVisible(false);
		this.comboBoxZoom.addItem("1 m2:32 px");
		this.comboBoxZoom.addItem("1 m2:16 px");
		this.comboBoxZoom.addItem("1 m2:8 px");
		this.comboBoxZoom.addItem("1 m2:4 px");
		this.comboBoxZoom.addItem("1 m2:2 px");
		this.comboBoxZoom.addItem("1 m2:1 px");
		this.comboBoxZoom.addItem("2 m2:1 px");
		this.comboBoxZoom.addItem("4 m2:1 px");
		this.comboBoxZoom.addItem("8 m2:1 px");
		this.comboBoxZoom.addItem("16 m2:1 px");
		this.comboBoxZoom.addItem("32 m2:1 px");
		this.comboBoxZoom.setSelectedItem("1 m2:1 px");
		
	}
	
	private JButton lastBtn=null; //临时用来记录被单击的单元格
	private Color lastColor;//记录上个被点击的按钮背景色	
	
	public void setColor(JButton btn, Color c) throws Exception {
		if(lastBtn!=null){
			lastBtn.setForeground(lastColor);
		}
		Color test=new Color(255,0,255);
		btn.setForeground(test);
//		btn.setBackground(test);
		if(c.getRGB()!=test.getRGB()){
			lastColor=Color.BLACK;
		}
		lastBtn=btn;
	}
	
	/**
	 * 获得房屋信息明细下的业务页签的一条合同记录
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private String getSelectTenancyBillInfo() throws EASBizException, BOSException{
		RoomDetailInfoUI rdiUI =(RoomDetailInfoUI)this.detailUI;
		String id=null;
		id = FDCClientHelper.getSelectedKeyValue(rdiUI.getBizTable(), "id");
		return id;
	}
}
