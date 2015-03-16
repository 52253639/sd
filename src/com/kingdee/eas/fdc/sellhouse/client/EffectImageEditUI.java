/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.EffectImageElementEnum;
import com.kingdee.eas.fdc.sellhouse.EffectImageEnum;
import com.kingdee.eas.fdc.sellhouse.EffectImageFactory;
import com.kingdee.eas.fdc.sellhouse.EffectImageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

/**
 * output class name
 */
public class EffectImageEditUI extends AbstractEffectImageEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(EffectImageEditUI.class);
    private KDFileChooser chooser=new KDFileChooser();
    private File imgPath=null;//图片路径
    private JButton btnEditHotspot;
    private JButton btnSaveHotspot;
	private boolean isShowedImage=false;//是否有显示图片  这个属性 决定是否可以设置热点
	private boolean isShowHotspot=false;//是否加载已保存热点区域
	private int startX, startY, endX, endY;//确定热点区域的4个坐标值
	private Graphics og;
	MouseListener moListener;
	MouseMotionListener mmListener;
	
	SHEImagePanel pnlRoomPic = new SHEImagePanel(){
		public void paint(Graphics g) {
			super.paint(g);
			og=g.create();
			if(isShowHotspot && tblElementEntry.getRowCount()>0){
				for(int i=0;i<tblElementEntry.getRowCount();i++){
					//当前选中行的热点区域  在下面以红色绘制
					if(i==tblElementEntry.getSelectManager().getActiveRowIndex()){
						continue;
					}
					IRow row=tblElementEntry.getRow(i);
					if(row.getCell("hotspot1").getValue()!=null && row.getCell("hotspot2").getValue()!=null){
						String hotspot1=row.getCell("hotspot1").getValue().toString();
						int hotspot1_x=Integer.parseInt(hotspot1.substring(0,hotspot1.indexOf(',')));
						int hotspot1_y=Integer.parseInt(hotspot1.substring(hotspot1.indexOf(',')+1,hotspot1.length()));
						String hotspot2=row.getCell("hotspot2").getValue().toString();
						int hotspot2_x=Integer.parseInt(hotspot2.substring(0,hotspot2.indexOf(',')));
						int hotspot2_y=Integer.parseInt(hotspot2.substring(hotspot2.indexOf(',')+1,hotspot2.length()));
						if(hotspot1_x<hotspot2_x){
							if(hotspot1_y<hotspot2_y){
								g.drawRect(hotspot1_x, hotspot1_y, hotspot2_x-hotspot1_x, hotspot2_y-hotspot1_y);
							}
							else{
								g.drawRect(hotspot1_x, hotspot2_y, hotspot2_x-hotspot1_x, hotspot1_y-hotspot2_y);
							}
						}
						else{
							if(hotspot2_y<hotspot1_y){
								g.drawRect(hotspot2_x, hotspot2_y, hotspot1_x-hotspot2_x, hotspot1_y-hotspot2_y);
							}
							else{
								g.drawRect(hotspot2_x, hotspot1_y, hotspot1_x-hotspot2_x, hotspot2_y-hotspot1_y);
							}
						}
					}
				}
			}
			g.setColor(Color.RED);//这里更改了颜色 然后画当前的热点区域
			if(startX<endX){
				if(startY<endY){
					g.drawRect(startX, startY, endX-startX, endY-startY);
				}
				else{
					g.drawRect(startX, endY, endX-startX, startY-endY);
				}
			}
			else{
				if(endY<startY){
					g.drawRect(endX, endY, startX-endX, startY-endY);
				}
				else{
					g.drawRect(endX, startY, startX-endX, endY-startY);
				}
			}
		}
	};
    /**
     * output class constructor
     */
    public EffectImageEditUI() throws Exception
    {
        super();
        jbInit();
    }
    protected boolean isUseMainMenuAsTitle() {
    	return false;
    }
	private void jbInit() {
		titleMain = this.getUITitle();
	}
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected ICoreBase getBizInterface() throws Exception {
		return EffectImageFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	public void loadFields() {
		tblElementEntry.checkParsed();
		super.loadFields();
		if(this.oprtState.equals(OprtState.ADDNEW)){
			this.comboType.setSelectedIndex(0);//设置默认选中项目分布效果图
			setViewByType();
		}
		else{
			try {
				EffectImageInfo info=EffectImageFactory.getRemoteInstance()
					.getEffectImageInfo(new ObjectUuidPK(this.editData.getId()));
				if (info.getEffectImage() != null) {
					
					//Update by zhiyuan_tang 2010/10/21
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
					imgPath=file;
//					File file = null;
//					ByteInputStream stream = new ByteInputStream(info.getEffectImage(), info.getEffectImage().length);
//					ObjectInputStream s = null;
//					try {
//						s = new ObjectInputStream(stream);
//						file = (File) s.readObject();
//						imgPath=file;
//						s.close();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
					showImage(file);
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(this.comboType.getSelectedIndex()==0){//项目分布效果图
				prmtSellProject.setEnabled(false);
				prmtSellProject.setRequired(false);
				prmtBuilding.setEnabled(false);
				prmtBuilding.setRequired(false);
				chkIsShowUnit.setEnabled(false);
				chkIsShowUnit.setSelected(false);
				prmtBuildingUnit.setEnabled(false);
				prmtBuildingFloor.setEnabled(false);
				prmtBuildingFloor.setRequired(false);
			}
			else if(this.comboType.getSelectedIndex()==1){//楼栋方位效果图
				prmtSellProject.setEnabled(true);
				prmtSellProject.setRequired(true);
				prmtBuilding.setEnabled(false);
				prmtBuilding.setRequired(false);
				chkIsShowUnit.setEnabled(false);
				chkIsShowUnit.setSelected(false);
				prmtBuildingUnit.setEnabled(false);
				prmtBuildingFloor.setEnabled(false);
				prmtBuildingFloor.setRequired(false);
			}
			else if(this.comboType.getSelectedIndex()==2){//楼层效果图
				prmtSellProject.setEnabled(true);
				prmtSellProject.setRequired(true);
				prmtBuilding.setEnabled(true);
				prmtBuilding.setRequired(true);
				chkIsShowUnit.setEnabled(true);
				chkIsShowUnit.setSelected(false);
				prmtBuildingUnit.setEnabled(false);
				prmtBuildingFloor.setEnabled(true);
				prmtBuildingFloor.setRequired(true);
			}
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic=super.getSelectors();
    	sic.add("state");
    	sic.add("CU.id");
    	sic.add("orgUnit.id");
    	return sic;
	}
	
	protected void tblElementEntry_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		if(isShowHotspot){//当正在编辑热点区域时 分录选择发生改变
			IRow row=tblElementEntry.getRow(tblElementEntry.getSelectManager().getActiveRowIndex());
			if(row.getCell("hotspot1").getValue()!=null && row.getCell("hotspot2").getValue()!=null){
				String hotspot1=row.getCell("hotspot1").getValue().toString();
				startX=Integer.parseInt(hotspot1.substring(0,hotspot1.indexOf(',')));
				startY=Integer.parseInt(hotspot1.substring(hotspot1.indexOf(',')+1,hotspot1.length()));
				String hotspot2=row.getCell("hotspot2").getValue().toString();
				endX=Integer.parseInt(hotspot2.substring(0,hotspot2.indexOf(',')));
				endY=Integer.parseInt(hotspot2.substring(hotspot2.indexOf(',')+1,hotspot2.length()));
			}
			else{
				 startX=0;startY=0;endX=0;endY=0;
			}
			pnlRoomPic.repaint();
		}
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAttachment.setVisible(false);
		this.actionAuditResult.setVisible(false);
		//设置租售项目F7过滤条件 及其他所有F7控件一致为只能选择不接收输入 这里是为了防止手动清空选择
//		EntityViewInfo view=new EntityViewInfo();
//		FilterInfo filter=new FilterInfo();
//		String id=SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId().toString();
//		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",id));
////		String sql="select fheadid from T_SHE_ShareOrgUnit where FOrgUnitID="+id;
////		filter.getFilterItems().add(new FilterItemInfo("id",sql,CompareType.INNER));
//		filter.getFilterItems().add(new FilterItemInfo("id",MarketingUnitFactory.getRemoteInstance().
//				getPermitSellProjectIdSql(SysContext.getSysContext().getCurrentUserInfo()),CompareType.INNER));
//		filter.setMaskString("#0 or #1");
//		view.setFilter(filter);
		prmtSellProject.setEntityViewInfo(CommerceHelper.getPermitProjectView());
		prmtSellProject.setEditable(false);
		prmtBuilding.setEditable(false);
		prmtBuildingUnit.setEditable(false);
		prmtBuildingFloor.setEditable(false);
		
		tblElementEntry.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);//设置行选择模式
		tblElementEntry.setEnabled(false);
		btnEditHotspot=this.conImage.add(actionEditHotspot);
		btnEditHotspot.setText("编辑热点");
		btnEditHotspot.setSize(30, 19);
		btnSaveHotspot=this.conImage.add(actionSaveHotspot);
		btnSaveHotspot.setText("确定区域");
		btnSaveHotspot.setSize(30,19);
	}
	/**
	 * 判断是否有选择元素分录行
	 * @return
	 */
	private boolean isSelectedRow(){
		if(tblElementEntry.getRowCount()==0 || tblElementEntry.getSelectManager().size()==0)
			return false;
		else
			return true;
	}

	/**
	 * 热点区域编辑开始按钮
	 */
	public void actionEditHotspot_actionPerformed(ActionEvent e)
			throws Exception {
		//这里需要判断元素分录是否有选中行  isShowedImage=true才能开始设置热点
		if(!isSelectedRow()){
			MsgBox.showError("请先选择元素分录行！");
			abort();
		}
		if(!isShowedImage){
			MsgBox.showError("请先导入图片！");
			abort();
		}
		isShowHotspot=true;
		IRow row=tblElementEntry.getRow(tblElementEntry.getSelectManager().getActiveRowIndex());
		if(row.getCell("hotspot1").getValue()!=null && row.getCell("hotspot2").getValue()!=null){
			String hotspot1=row.getCell("hotspot1").getValue().toString();
			startX=Integer.parseInt(hotspot1.substring(0,hotspot1.indexOf(',')));
			startY=Integer.parseInt(hotspot1.substring(hotspot1.indexOf(',')+1,hotspot1.length()));
			String hotspot2=row.getCell("hotspot2").getValue().toString();
			endX=Integer.parseInt(hotspot2.substring(0,hotspot2.indexOf(',')));
			endY=Integer.parseInt(hotspot2.substring(hotspot2.indexOf(',')+1,hotspot2.length()));
		}
		pnlRoomPic.repaint();//显示以保存过的热点区域 
		
		//设置鼠标事件  画热点区域
		pnlRoomPic.addMouseListener(moListener=new MouseListener(){
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
				startX=e.getX();
				startY=e.getY();
			}
			public void mouseReleased(MouseEvent e) {
				if(startX<endX){
					if(startY<endY){
						og.drawRect(startX, startY, endX-startX, endY-startY);
					}
					else{
						og.drawRect(startX, endY, endX-startX, startY-endY);
					}
				}
				else{
					if(endY<startY){
						og.drawRect(endX, endY, startX-endX, startY-endY);
					}
					else{
						og.drawRect(endX, startY, startX-endX, endY-startY);
					}
				}
			}
		});
		pnlRoomPic.addMouseMotionListener(mmListener=new MouseMotionListener(){
			public void mouseDragged(MouseEvent e) {
				endX=e.getX();
				endY=e.getY();
				if(endX<0){
					endX=1;
				}
				if(endY<0){
					endY=1;
				}
				if(endX>pnlRoomPic.getWidth()){
					endX=pnlRoomPic.getWidth()-1;
				}
				if(endY>pnlRoomPic.getHeight()){
					endY=pnlRoomPic.getHeight()-1;
				}
				pnlRoomPic.repaint();
			}
			public void mouseMoved(MouseEvent e) {
				int x=e.getX();
				int y=e.getY();
				for(int i=0;i<tblElementEntry.getRowCount();i++){
					IRow row=tblElementEntry.getRow(i);
					if(row.getCell("hotspot1").getValue()==null || row.getCell("hotspot2").getValue()==null){
						continue;
					}
					String hotspot1=row.getCell("hotspot1").getValue().toString();
					String hotspot2=row.getCell("hotspot2").getValue().toString();
					if(hotspotInRec(x, y, hotspot1, hotspot2)){
						pnlRoomPic.setToolTipText(row.getCell("elementName").getValue().toString());
					}
				}
			}
		});
	}
	
	/**
	 * 热点区域确定并保存
	 */
	public void actionSaveHotspot_actionPerformed(ActionEvent e)
			throws Exception {
		if(!isShowHotspot || (startX==0&&startY==0&&endX==0&&endY==0)){
			MsgBox.showInfo("请先编辑热点区域！");
			abort();
		}
		IRow row=tblElementEntry.getRow(tblElementEntry.getSelectManager().getActiveRowIndex());
		row.getCell("hotspot1").setValue(startX+","+startY);
		row.getCell("hotspot2").setValue(endX+","+endY);
		isShowHotspot=false;
		pnlRoomPic.removeMouseListener(moListener);
		pnlRoomPic.removeMouseMotionListener(mmListener);
		startX=0;startY=0;endX=0;endY=0;
		pnlRoomPic.repaint();
	}
	
	/**
	 *  判断传入的点是否在矩形的范围内 <br>
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
	
	public void onShow() throws Exception {
		super.onShow();
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionAttachment.setVisible(false);
		if(this.oprtState.equals(OprtState.VIEW)){
			this.actionImage.setEnabled(false);
			this.actionSaveHotspot.setEnabled(false);
			this.actionEditHotspot.setEnabled(false);
		}
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		setViewByType();
		this.actionImage.setEnabled(true);
		this.actionSaveHotspot.setEnabled(true);
		this.actionEditHotspot.setEnabled(true);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if(this.comboType.getSelectedIndex()==0){//项目分布效果图
			prmtSellProject.setEnabled(false);
			prmtSellProject.setRequired(false);
			prmtBuilding.setEnabled(false);
			prmtBuilding.setRequired(false);
			chkIsShowUnit.setEnabled(false);
			chkIsShowUnit.setSelected(false);
			prmtBuildingUnit.setEnabled(false);
			prmtBuildingFloor.setEnabled(false);
			prmtBuildingFloor.setRequired(false);
		}
		else if(this.comboType.getSelectedIndex()==1){//楼栋方位效果图
			prmtSellProject.setEnabled(true);
			prmtSellProject.setRequired(true);
			prmtBuilding.setEnabled(false);
			prmtBuilding.setRequired(false);
			chkIsShowUnit.setEnabled(false);
			chkIsShowUnit.setSelected(false);
			prmtBuildingUnit.setEnabled(false);
			prmtBuildingFloor.setEnabled(false);
			prmtBuildingFloor.setRequired(false);
		}
		else if(this.comboType.getSelectedIndex()==2){//楼层效果图
			prmtSellProject.setEnabled(true);
			prmtSellProject.setRequired(true);
			prmtBuilding.setEnabled(true);
			prmtBuilding.setRequired(true);
			chkIsShowUnit.setEnabled(true);
			chkIsShowUnit.setSelected(false);
			prmtBuildingUnit.setEnabled(false);
			prmtBuildingFloor.setEnabled(true);
			prmtBuildingFloor.setRequired(true);
		}
		this.actionImage.setEnabled(true);
		this.actionSaveHotspot.setEnabled(true);
		this.actionEditHotspot.setEnabled(true);
	}
	
	protected IObjectValue createNewData() {
		EffectImageInfo info=new EffectImageInfo();
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		return info;
	}
	/**
	 * 导入图片
	 */
	public void actionImage_actionPerformed(ActionEvent e) throws Exception{
		chooser.resetChoosableFileFilters();
		chooser.removeChoosableFileFilter(chooser.getFileFilter());
    	chooser.addChoosableFileFilter(new MyFileFilter(".jpg","JPG格式图片"));
    	int i=chooser.showOpenDialog(null);
    	if(i==KDFileChooser.APPROVE_OPTION){//当选择了图片后才进行下面操作
	    	File file = chooser.getSelectedFile();
	        imgPath=file;
	        showImage(file);
	        startX=0;startY=0;endX=0;endY=0;//重新导入图片时去掉 已经画的热点区域
	        pnlRoomPic.repaint();
    	}
    }
	/**
	 * 设置显示图片
	 * @param file
	 */
	private void showImage(File file){
		
		pnlRoomPic.setImageFile(file);
		this.backgroundPanel.setViewportView(pnlRoomPic);
		this.isShowedImage=true;
	}
	/**
	 * 是否区分单元
	 */
	protected void chkIsShowUnit_actionPerformed(ActionEvent e)
			throws Exception {
		if(chkIsShowUnit.getSelected()==KDCheckBox.SELECTED){
			prmtBuildingUnit.setEnabled(true);
			prmtBuildingUnit.setRequired(true);
		}
		else{
			prmtBuildingUnit.setEnabled(false);
			prmtBuildingUnit.setRequired(false);
		}
	}
	/**
	 * 清除显示的图片
	 */
	private void clearImage(){
		this.backgroundPanel.setViewportView(null);
		this.backgroundPanel.repaint();
		this.backgroundPanel.validate();
		this.isShowedImage=false;
		if(isShowHotspot){//如果在编辑热点时 清除了图片则应该停止热点编辑
			isShowHotspot=false;
			pnlRoomPic.removeMouseListener(moListener);
			pnlRoomPic.removeMouseMotionListener(mmListener);
			startX=0;startY=0;endX=0;endY=0;
			pnlRoomPic.repaint();
		}
	}
	
	/**
	 * 选择图片类型
	 */
	protected void comboType_itemStateChanged(ItemEvent e) throws Exception {
		
		if(e.getStateChange()==ItemEvent.SELECTED){//只在状态改变为选中时调用 
			setViewByType();
			clearImage();
		}
	}

	private void setViewByType(){
		if(this.comboType.getSelectedIndex()==0){//项目分布效果图
			prmtSellProject.setEnabled(false);
			prmtSellProject.setRequired(false);
			prmtBuilding.setEnabled(false);
			prmtBuilding.setRequired(false);
			chkIsShowUnit.setEnabled(false);
			chkIsShowUnit.setSelected(false);
			prmtBuildingUnit.setEnabled(false);
			prmtBuildingFloor.setEnabled(false);
			prmtBuildingFloor.setRequired(false);
			prmtSellProject.setData(null);
			prmtBuilding.setData(null);
			prmtBuildingUnit.setData(null);
			prmtBuildingFloor.setData(null);
		}
		else if(this.comboType.getSelectedIndex()==1){//楼栋方位效果图
			prmtSellProject.setEnabled(true);
			prmtSellProject.setRequired(true);
			prmtBuilding.setEnabled(false);
			prmtBuilding.setRequired(false);
			chkIsShowUnit.setEnabled(false);
			chkIsShowUnit.setSelected(false);
			prmtBuildingUnit.setEnabled(false);
			prmtBuildingFloor.setEnabled(false);
			prmtBuildingFloor.setRequired(false);
			prmtSellProject.setData(null);
			prmtBuilding.setData(null);
			prmtBuildingUnit.setData(null);
			prmtBuildingFloor.setData(null);
		}
		else if(this.comboType.getSelectedIndex()==2){//楼层效果图
			prmtSellProject.setEnabled(true);
			prmtSellProject.setRequired(true);
			prmtBuilding.setEnabled(true);
			prmtBuilding.setRequired(true);
			chkIsShowUnit.setEnabled(true);
			chkIsShowUnit.setSelected(false);
			prmtBuildingUnit.setEnabled(false);
			prmtBuildingFloor.setEnabled(true);
			prmtBuildingFloor.setRequired(true);
			prmtSellProject.setData(null);
			prmtBuilding.setData(null);
			prmtBuildingUnit.setData(null);
			prmtBuildingFloor.setData(null);
		}
		tblElementEntry.removeRows();
		try {
			reflashElementEntry(getThisSellProjectCollection(),EffectImageElementEnum.SELLPROJECT);
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	protected void prmtSellProject_dataChanged(DataChangeEvent e) throws Exception {
		if(e.getOldValue()!= null && e.getNewValue() != null){//选择没有改变 直接返回
			SellProjectInfo info1=(SellProjectInfo)e.getOldValue();
			SellProjectInfo info2=(SellProjectInfo)e.getNewValue();
			if(info1.getId().equals(info2.getId())){
				return;
			}
		}
		if(e.getNewValue()==null){//选择为空时
			return;
		}
		SellProjectInfo info=(SellProjectInfo)e.getNewValue();
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",info.getId().toString()));
		view.setFilter(filter);
		prmtBuilding.setEntityViewInfo(view);
		reflashElementEntry(getThisBuildingCollection(info.getId().toString()),EffectImageElementEnum.BUILDING);
		prmtBuilding.setData(null);
		prmtBuildingUnit.setData(null);
		prmtBuildingFloor.setData(null);
		clearImage();
	}
	
	protected void prmtBuilding_dataChanged(DataChangeEvent e) throws Exception {
		if(e.getOldValue()!= null && e.getNewValue() != null){//选择没有改变 直接返回
			BuildingInfo info1=(BuildingInfo)e.getOldValue();
			BuildingInfo info2=(BuildingInfo)e.getNewValue();
			if(info1.getId().equals(info2.getId())){
				return;
			}
		}
		if(e.getNewValue()==null){//选择为空时
			return;
		}
		BuildingInfo info=(BuildingInfo)e.getNewValue();
		EntityViewInfo view=new EntityViewInfo();
		EntityViewInfo v=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.id",info.getId().toString()));
		view.setFilter(filter);
		v.setFilter(filter);
		prmtBuildingFloor.setEntityViewInfo(view);
		prmtBuildingUnit.setEntityViewInfo(v);
		reflashElementEntry(getThisRoomCollection(info.getId().toString(),"building.id"),EffectImageElementEnum.ROOM);
		prmtBuildingFloor.setData(null);
		prmtBuildingUnit.setData(null);
		clearImage();
	}
	
	protected void prmtBuildingFloor_dataChanged(DataChangeEvent e) throws Exception {
		if(e.getOldValue()!= null && e.getNewValue() != null){//选择没有改变 直接返回
			BuildingFloorEntryInfo info1=(BuildingFloorEntryInfo)e.getOldValue();
			BuildingFloorEntryInfo info2=(BuildingFloorEntryInfo)e.getNewValue();
			if(info1.getId().equals(info2.getId())){
				return;
			}
		}
		if(e.getNewValue()==null){//选择为空时
			return;
		}
		BuildingFloorEntryInfo info=(BuildingFloorEntryInfo)e.getNewValue();
		reflashElementEntry(getThisRoomCollection(info.getId().toString(),"buildingFloor.id"),EffectImageElementEnum.ROOM);
		clearImage();
	}
	
	protected void prmtBuildingUnit_dataChanged(DataChangeEvent e) throws Exception {
		if(e.getOldValue()!= null && e.getNewValue() != null){//选择没有改变 直接返回
			BuildingUnitInfo info1=(BuildingUnitInfo)e.getOldValue();
			BuildingUnitInfo info2=(BuildingUnitInfo)e.getNewValue();
			if(info1.getId().equals(info2.getId())){
				return;
			}
		}
		if(e.getNewValue()==null){//选择为空时
			return;
		}
		BuildingUnitInfo info=(BuildingUnitInfo)e.getNewValue();
		reflashElementEntry(getThisRoomCollection(info.getId().toString(),"buildUnit.id"),EffectImageElementEnum.ROOM);
		clearImage();
	}
	
	/**
	 * 获取当前登录组织下所有项目的集合
	 * @return
	 * @throws BOSException 
	 */
	private SellProjectCollection getThisSellProjectCollection() throws BOSException{
		
		EntityViewInfo view=new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("name");
		sic.add("id");
		FilterInfo filter=new FilterInfo();
		String fidStr = "'nullnull'";
		try {
			fidStr = MarketingUnitFactory.getRemoteInstance().getPermitSellProjectIdSql(SysContext.getSysContext().getCurrentUserInfo());
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		filter.getFilterItems().add(new FilterItemInfo("id",fidStr,CompareType.INNER));
		view.setSelector(sic);
		view.setFilter(filter);
		return SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
		
	}
	/**
	 * 获取租售项目下所有楼栋
	 * @param id 租售项目ID
	 * @return
	 * @throws BOSException 
	 */
	private BuildingCollection getThisBuildingCollection(String id) throws BOSException{
		EntityViewInfo view=new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("name");
		sic.add("id");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",id));
		view.setSelector(sic);
		view.setFilter(filter);
		return BuildingFactory.getRemoteInstance().getBuildingCollection(view);
	}
	/**
	 * 获取房间集合
	 * @param id ID
	 * @param param 对应房间实体的外键字段名称
	 * @return
	 * @throws BOSException 
	 */
	private RoomCollection getThisRoomCollection(String id,String param) throws BOSException{
		EntityViewInfo view=new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("name");
		sic.add("id");
		FilterInfo filter=new FilterInfo();
		view.setSelector(sic);
		if(param.equals("building.id")){
			filter.getFilterItems().add(new FilterItemInfo(param,id));
			view.setFilter(filter);
		}
		else if(param.equals("buildingFloor.id")){
			filter.getFilterItems().add(new FilterItemInfo(param,id));
			if(chkIsShowUnit.getSelected()==KDCheckBox.SELECTED && prmtBuildingUnit.getData()!=null){
				BuildingUnitInfo info=(BuildingUnitInfo)prmtBuildingUnit.getData();
				filter.getFilterItems().add(new FilterItemInfo("buildUnit.id",info.getId().toString()));
			}
			view.setFilter(filter);
		}
		else if(param.equals("buildUnit.id")){
			filter.getFilterItems().add(new FilterItemInfo(param,id));
			if(prmtBuildingFloor.getData()!=null){
				BuildingFloorEntryInfo info=(BuildingFloorEntryInfo)prmtBuildingFloor.getData();
				filter.getFilterItems().add(new FilterItemInfo("buildingFloor.id",info.getId().toString()));
			}
			view.setFilter(filter);
		}
		return RoomFactory.getRemoteInstance().getRoomCollection(view);
		
	}
	
	/**
	 * 刷新元素分录表格
	 * @param coll
	 * @param type
	 */
	private void reflashElementEntry(IObjectCollection coll,EffectImageElementEnum type){
		tblElementEntry.checkParsed();
		if(type.equals(EffectImageElementEnum.SELLPROJECT)){
			tblElementEntry.removeRows();
			for(Iterator it=coll.iterator();it.hasNext();){
				SellProjectInfo sp=(SellProjectInfo)it.next();
				IRow row=tblElementEntry.addRow();
				row.getCell("type").setValue(type);
				row.getCell("elementName").setValue(sp.getName());
				row.getCell("elementID").setValue(sp.getId().toString());;
			}
		}
		else if(type.equals(EffectImageElementEnum.BUILDING)){
			tblElementEntry.removeRows();
			for(Iterator it=coll.iterator();it.hasNext();){
				BuildingInfo building=(BuildingInfo)it.next();
				IRow row=tblElementEntry.addRow();
				row.getCell("type").setValue(type);
				row.getCell("elementName").setValue(building.getName());
				row.getCell("elementID").setValue(building.getId().toString());
			}
		}
		else if(type.equals(EffectImageElementEnum.ROOM)){
			tblElementEntry.removeRows();
			for(Iterator it=coll.iterator();it.hasNext();){
				RoomInfo room=(RoomInfo)it.next();
				IRow row=tblElementEntry.addRow();
				row.getCell("type").setValue(type);
				row.getCell("elementName").setValue(room.getName());
				row.getCell("elementID").setValue(room.getId().toString());
			}
		}
		tblElementEntry.getSelectManager().select(0,0);
	}
	/**
	 *  检查每个树节点只能保存一张效果图片
	 */
	 private void checkHaveImage() throws Exception {
		 if(this.getEditData().getId()!=null){
			 return;
		 }
		 EffectImageEnum parentType=(EffectImageEnum)this.comboType.getSelectedItem();
		 FilterInfo filter=new FilterInfo();
		 if(parentType.equals(EffectImageEnum.PIC_SELLPROJECT)){//项目方位效果图
			 filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_SELLPROJECT_VALUE));
			 filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",SysContext.getSysContext()
				.getCurrentOrgUnit().castToFullOrgUnitInfo().getId().toString()));
			 if(EffectImageFactory.getRemoteInstance().exists(filter)){
				 MsgBox.showError("该组织已经保存过项目方位效果图,如需替换请直接修改！");
				 abort();
			 }
		 }
		 if(parentType.equals(EffectImageEnum.PIC_BUILDING)){//楼栋分布效果图
			 filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDING_VALUE));
			 if(this.prmtSellProject.getData()!=null){
				 SellProjectInfo prj=(SellProjectInfo)this.prmtSellProject.getData();
				 filter.getFilterItems().add(new FilterItemInfo("sellProject.id",prj.getId().toString()));
				 if(EffectImageFactory.getRemoteInstance().exists(filter)){
					 MsgBox.showError("该项目已经保存过楼栋分布效果图,如需替换请直接修改！");
					 abort();
				 }
			 }
			 else{
				 MsgBox.showError("请选择租售项目！");
					abort();
			 }
		 }
		 if(parentType.equals(EffectImageEnum.PIC_BUILDINGFLOOR)){//楼层效果图
			 filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDINGFLOOR_VALUE));
			 if(this.prmtBuilding.getData()!=null){
				 BuildingInfo b=(BuildingInfo)this.prmtBuilding.getData();
				 filter.getFilterItems().add(new FilterItemInfo("building.id",b.getId().toString()));
			 }
			 else{
				 MsgBox.showError("请选择楼栋！");
					abort();
			 }
			 if(this.prmtBuildingFloor.getData()!=null){
				 BuildingFloorEntryInfo bf=(BuildingFloorEntryInfo)this.prmtBuildingFloor.getData();
				 filter.getFilterItems().add(new FilterItemInfo("buildingFloor.id",bf.getId().toString()));
			 }
			 else{
				 MsgBox.showError("请选择楼层！");
					abort();
			 }
			 if(chkIsShowUnit.isSelected()){
				 filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.TRUE));
				 if(this.prmtBuildingUnit.getData()!=null){
					 BuildingUnitInfo bu=(BuildingUnitInfo)this.prmtBuildingUnit.getData();
					 filter.getFilterItems().add(new FilterItemInfo("buildingUnit.id",bu.getId().toString()));
					 if(EffectImageFactory.getRemoteInstance().exists(filter)){
						 MsgBox.showError("该楼层已经保存过楼层效果图,如需替换请直接修改！");
						 abort();
					 }
				 }
				 else{
					 MsgBox.showError("请选择单元！");
						abort();
				 }
			 }
			 else{
				 filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.FALSE));
				 if(EffectImageFactory.getRemoteInstance().exists(filter)){
					 MsgBox.showError("该楼层已经保存过楼层效果图,如需替换请直接修改！");
					 abort();
				 }
			 }
		 }
	 }
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		checkHaveImage();
		if(imgPath!=null){//保存图片
			
			//Update by zhiyuan_tang 2010/10/21  读取文件内容
			long len = imgPath.length();
			byte[] bytes = new byte[(int)len];
			BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(imgPath));
			int r = bufferedInputStream.read(bytes);
			if (r != len) {
				throw new IOException("文件读取异常！");
			}
		    bufferedInputStream.close();
//		    ByteOutputStream stream = new ByteOutputStream();
//			ObjectOutputStream s = null;
//			try {
//				s = new ObjectOutputStream(stream);
//				s.writeObject(imgPath);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
			this.editData.setEffectImage(bytes);
		}
		super.actionSave_actionPerformed(e);
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
	}
	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
		if(txtName.getText().trim().equals("") || txtName.getText()==null){
			MsgBox.showError("名称不能为空！");
			abort();
		}
		if(prmtSellProject.isEnabled()){
			if(prmtSellProject.getData()==null){
				MsgBox.showError("请选择租售项目！");
				abort();
			}
		}
		if(prmtBuilding.isEnabled()){
			if(prmtBuilding.getData()==null){
				MsgBox.showError("请选择楼栋！");
				abort();
			}
		}
		if(prmtBuildingFloor.isEnabled()){
			if(prmtBuildingFloor.getData()==null){
				MsgBox.showError("请选择楼层！");
				abort();
			}
		}
		if(prmtBuildingUnit.isEnabled()){
			if(prmtBuildingUnit.getData()==null){
				MsgBox.showError("请选择楼层！");
				abort();
			}
		}
		if(imgPath==null){
			MsgBox.showError("请导入要保存的图片");
			abort();
		}
	}
}

class MyFileFilter extends FileFilter{
	private String ext;
	private String des;
    public MyFileFilter(String ext,String des){
    	this.ext=ext;
    	this.des=des;
    }
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        
        String[] s = ext.split(";");
        
        String fn = f.getName().toLowerCase();
        for(int i=0;i<s.length; i++){
        	if(fn.endsWith(s[i])){
        		return true;
        	}
        	
        }        
        return false;
    }

    public String getDescription() {
        return des;
    }
}