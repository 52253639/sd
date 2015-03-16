/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.market.IRoomPhoto;
import com.kingdee.eas.fdc.market.RoomPhotoCollection;
import com.kingdee.eas.fdc.market.RoomPhotoFactory;
import com.kingdee.eas.fdc.market.RoomPhotoInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.common.client.UIContext;

/**
 * 
 * @author liangfx
 *
 */
public class BoutiqueRoomTypeEditUI extends AbstractBoutiqueRoomTypeEditUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4636821458961381632L;
	private static final Logger logger = CoreUIObject.getLogger(BoutiqueRoomTypeEditUI.class);
    private static BOSUuid BRTID = null;    
    private roomPicPanel pPanel = new roomPicPanel();// 定义照片
    private roomPicPanel mPanel = new roomPicPanel();

    
	private boolean existPhoto = false;
	
	/**
     * 页面状态变化时会调用此方法，在这里控制自定义按钮的状态
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) 
        {
        	this.btnSubmit.setVisible(false);
        	this.actionSave.setVisible(true);
        	paneBIZLayerControl10.remove(pPanel);
        	paneBIZLayerControl10.remove(mPanel);
        	
        } else if (STATUS_EDIT.equals(this.oprtState)) 
        {
        	this.paneBIZLayerControl10.add(pPanel,"户型图片");
			this.paneBIZLayerControl10.add(mPanel,"平层图片");
        	this.actionSave.setVisible(false);
        	this.btnSubmit.setVisible(true);
        	 try {
				setPhoto();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else if (STATUS_VIEW.equals(this.oprtState)) 
        {
        	this.paneBIZLayerControl10.add(pPanel,"户型图片");
			this.paneBIZLayerControl10.add(mPanel,"平层图片");
        } else if (actionSave.equals(this.oprtState)) 
        {
        	this.paneBIZLayerControl10.add(pPanel,"户型图片");
			this.paneBIZLayerControl10.add(mPanel,"平层图片");
        	this.btnSubmit.setVisible(true);
        	this.actionSave.setVisible(false);
        }
    }
    /**
	 * 描述：初始化界面布局管理器.
	 * @see com.kingdee.bos.ui.face.CoreUIObject#initUIContentLayout()
	 */
	public void initUIContentLayout() {

		super.initUIContentLayout();
		// 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
//		mainInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 996, 500));
//		paneBIZLayerControl10.putClientProperty("OriginalBounds", new Rectangle(0, 0, 800, 100));
	}
    /**
     * output loadFields method
     */
	   public void loadFields()
	    {
	        super.loadFields();
	      //photo
			pPanel.setOprtStat(this.getOprtState());
			mPanel.setOprtStat(this.getOprtState());
			try {
				setPhoto();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		/**
		 * 装载图片
		 */
		private void loadPhoto() {
			// TODO

		}
		//图片展现
		private void setPhoto() throws Exception {
			// TODO Auto-generated method stub
			IRoomPhoto iPhoto = RoomPhotoFactory.getRemoteInstance();
			if (this.editData != null) {
				if (this.editData.getId() != null) {
					RoomPhotoCollection rpColl = iPhoto.
							getRoomPhotoCollection(" select roomTypePicData,flatTypePicData  where head='"
									+ this.editData.getId() + "'");
					/*RoomPhotoCollection rpCollection = iPhoto.
					getRoomPhotoCollection(" select flatTypePicData  where head='"
							+ this.editData.getId() + "'");*/
					
					if (rpColl != null && rpColl.size() > 0	&& rpColl.get(0).getRoomTypePicData() != null && rpColl.get(0).getFlatTypePicData() != null) {
						
						RoomPhotoInfo roomPhoto = rpColl.get(0);
						byte[] flatTypePicData = roomPhoto.getFlatTypePicData();
						byte[] roomTypePicData = roomPhoto.getRoomTypePicData();
						
						ByteArrayInputStream flatbais = new ByteArrayInputStream(flatTypePicData);
						ByteArrayInputStream roombais = new ByteArrayInputStream(roomTypePicData);
						
						BufferedImage flatbfg = ImageIO.read(flatbais); 
						BufferedImage roombfg= ImageIO.read(roombais);
						mPanel.setSelectImage(flatbfg);
						pPanel.setSelectImage(roombfg);
						existPhoto = true;
						roombais.close();
						flatbais.close();
					} else {
						existPhoto = false;
						pPanel.setSelectImage(null);
						mPanel.setSelectImage(null);
						pPanel.repaint();
						mPanel.repaint();
					}
				}
			}
		}
		/**
		 * 把一个文件转换成二进制字符流
		 * 
		 * @param file
		 * @return
		 * @throws Exception
		 */
		private byte[] convertFileTOBytes(File file) throws Exception {
			if (file != null) {
				int size = (int) file.length();
				FileInputStream fin = new FileInputStream(file);
				byte[] bts = null;
				if (size > 0) {
					bts = new byte[size];
					fin.read(bts);
					return bts;
				}
				if(fin!=null){
					fin.close();
				}
			}
			return null;
		}
		/**
		 * 存储照片到editdata
		 * 
		 * @throws Exception
		 */
		private void storePhotoFile() throws Exception {
			RoomPhotoInfo photoInfo = new RoomPhotoInfo();
			// 保存照片
			photoInfo.put("IS_UPDATE_PHOTO", "TRUE");
			if ( pPanel.getSelectImageBytes() != null && mPanel.getSelectImageBytes() != null) {
				photoInfo.put("roomPhotofile", pPanel.getSelectImageBytes());
				photoInfo.put("flatPhotofile", mPanel.getSelectImageBytes());
				photoInfo.put("boutiqueRoomTypefile", this.editData);
				photoInfo.put("BRTID", this.editData.getId().toString());
			} else if (existPhoto) {
				if (pPanel.getSelectImage() != null && mPanel.getSelectImage() != null) {
					photoInfo.put("IS_UPDATE_PHOTO", "FALSE");
				} else {
					photoInfo.put("roomPhotofile", null);
					photoInfo.put("flatPhotofile", null);
					photoInfo.put("BRTID", this.editData.getId().toString());
				}
			}
			else if(pPanel.getSelectFile()==null){
				    MsgBox.showInfo("户型图片不能为空,请选择!");
				    SysUtil.abort();
			}
			else if(mPanel.getSelectFile() == null){
				
				 MsgBox.showInfo("平层图片不能为空,请选择!");
				    SysUtil.abort();
			}
			editData.put("photoInfo", photoInfo);
			// if (photoInfo.get("photofile") != null) {
			RoomPhotoFactory.getRemoteInstance().submit(photoInfo);
			
		}
		
		public void onShow() throws Exception {
			
			super.onShow();

		}
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output class constructor
     */
    public BoutiqueRoomTypeEditUI() throws Exception
    {
        super();
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
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	BRTID = this.editData.getId();
    	if(BRTID!=null){
        	storePhotoFile(); //存储照片
    	}
        super.actionSubmit_actionPerformed(e);
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
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
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
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
//    	BRTID = this.editData.getId();
//    	if(BRTID!=null){
//        	storePhotoFile(); //存储照片
//    	}
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
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.BoutiqueRoomTypeFactory.getRemoteInstance();
    }

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject) 
    {
        super.setDataObject(dataObject);
        if(STATUS_ADDNEW.equals(getOprtState())) {
            editData.put("treeid",(com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeInfo)getUIContext().get(UIContext.PARENTNODE));
        }
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.BoutiqueRoomTypeInfo objectValue = new com.kingdee.eas.fdc.market.BoutiqueRoomTypeInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
//    	Map uiContext = this.getUIContext();
//    	BoutiqueRoomTypeTreeInfo roomtypeinfo=(BoutiqueRoomTypeTreeInfo)uiContext.get("roomtype");
        //objectValue.setbsetBoutiqueType(roomtypeinfo);
    	this.txtboutiqueHouseTy.enable(false);
        return objectValue;
    }

}