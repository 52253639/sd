/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Cursor;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ListSelectionModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.KDList;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.IBoAttchAsso;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.fdc.schedule.CommonValueInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class AttachmentUI extends AbstractAttachmentUI
{
	private static final Logger logger = CoreUIObject.getLogger(AttachmentUI.class);

    //附件管理的父对象
    private CoreUIObject owner = null;
    //业务对象
    private IObjectValue editData = null;
    
    //附件获取接口
    private FileGetter fg = null;
    /**
     * output class constructor
     */
    public AttachmentUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output listAttachement_mouseClicked method
     */
    protected void listAttachement_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    	if(e.getClickCount()==2){
    		showFile(e);
    	}
    }

    /**
     * output listAttachement_mouseMoved method
     */
    protected void listAttachement_mouseMoved(java.awt.event.MouseEvent e) throws Exception
    {
       this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * output actionAttachmentManager_actionPerformed
     */
    public void actionAttachmentManager_actionPerformed(ActionEvent e) throws Exception
    {
    	showAttachmentUI(e);
    	fillList();
    }

	public IObjectValue getEditData() {
		return editData;
	}

//	public void setEditData(IObjectValue editData) {
//		this.editData = editData;
//	}
	
    public void onLoad()   throws Exception	{
    	
         owner = (CoreUIObject)getUIContext().get("owner");
         editData = (IObjectValue)getUIContext().get("editData");       
        
	   super.onLoad();
	   initWorkButton();
	   initList();
	   
	   fillList();
	}
    
    public void loadData(IObjectValue editData){
    	this.editData = editData;
    }

    //装载附件名称
    private void initList(){
    	listAttachement.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	listAttachement.setLayoutOrientation(KDList.HORIZONTAL_WRAP);
    	listAttachement.setEnabled(true);
    }
    
    private void initWorkButton(){
    	this.remove(btnAttachList);
    	kDContainer1.addButton(btnAttachList);
    }
    
    /**
     * 填充附件列表
     * @throws Exception 
     *
     */
    private void fillList() throws Exception{
    	listAttachement.removeAllElements();
    	List fileList = fetchFileList();
    	//
    	if(fileList!=null){
    		for(int i=0;i<fileList.size();i++){
    			CommonValueInfo obj = (CommonValueInfo)fileList.get(i);    			
    			Icon icon = getIcon(obj.getNumber());
    			listAttachement.addElement(obj);
    			if(icon!=null){
    				listAttachement.setElementIcon( icon,  obj);
    			}
    		}
    	}
    }
    
    private String getID(){
    	String id = null;
    	if(this.getEditData()!=null && getEditData().get("id")!=null){
    		id =  getEditData().get("id").toString();
    	}
    	
    	return id;
    }    
    
    /**
     * 获取附件名称列表 
     * @return
     * @throws Exception 
     */
    private List fetchFileList() throws Exception{
    	List fileList = null;
    	String id = getID();
    	
        EntityViewInfo view = new EntityViewInfo();
        SelectorItemCollection sic = view.getSelector();
        sic.add(new SelectorItemInfo("attachment.id"));
        sic.add(new SelectorItemInfo("attachment.type"));
        sic.add(new SelectorItemInfo("attachment.name"));
        FilterInfo filter = new FilterInfo();
        FilterItemCollection fic = filter.getFilterItems();
        fic.add(new FilterItemInfo("boID",id));        
        view.setFilter(filter);
        
        IBoAttchAsso iBoAttchAsso = BoAttchAssoFactory.getRemoteInstance();
        BoAttchAssoCollection cols = iBoAttchAsso.getBoAttchAssoCollection(view);
        if(cols!=null){
        	fileList =  new ArrayList();
        	for(int i=0,size=cols.size();i<size;i++){
        		AttachmentInfo att = cols.get(i).getAttachment();
        		CommonValueInfo obj = new CommonValueInfo(att.getId().toString(),att.getType(),att.getName());//+"("+att.getType()+")");
        		
        		fileList.add(obj);
        	}
        }
        
    	return fileList;
    }
    
    /**
     * 显示文件
     * @param e
     * @throws Exception 
     */
    public void showFile(java.awt.event.MouseEvent e) throws Exception{
    	//e.get
    	CommonValueInfo obj = (CommonValueInfo)listAttachement.getSelectedValue();
    	if(obj==null){
    		return ;
    	}
    	String attchId = obj.getId();
    	getFileGetter().viewAttachment(attchId,this.owner);
    }
    
    /**
     * 显示附件管理界面
     * @param e
     */
    public void  showAttachmentUI(ActionEvent e){
    	
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = getID();
        if(boID == null)
        {
            return;
        } else
        {
            acm.showAttachmentListUIByBoID(boID, this.owner);
            return;
        }
    }
    
    private FileGetter getFileGetter() throws Exception
	{
	    if(fg == null)
	        fg = new FileGetter((IAttachment)AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
	    return fg;
	}
    
    /**
     * 获取文件图标
     * @param number
     * @return
     */
    private Icon getIcon(String number){
    	String iconName = null;
    	if(number.equals(EASResource.getString("",""))){
    		iconName = "";
    	}else if(number.equals(EASResource.getString("",""))){
    		iconName = "";
    	}
    	
    	Icon icon  = null;
    	if(iconName!=null){
    		icon =  EASResource.getIcon(iconName);
    	}
    	return icon;
    }
    
    //Microsoft Word 文档\Microsoft Excel 表格\JPEG 图像\未知文件类型（.b）
   
    public void setTitle(String title){
    	this.kDContainer1.setTitle(title+"列表");
    	btnAttachList.setText(title+"管理");
    }

}