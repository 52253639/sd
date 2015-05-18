/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.plaf.basic.BasicBorders;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDSplitPane;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.assistant.BankFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.sellhouse.BirdEyeCollection;
import com.kingdee.eas.fdc.sellhouse.BirdEyeFactory;
import com.kingdee.eas.fdc.sellhouse.BirdEyeInfo;
import com.kingdee.eas.fdc.sellhouse.BirdEyePicCollection;
import com.kingdee.eas.fdc.sellhouse.BirdEyePicFactory;
import com.kingdee.eas.fdc.sellhouse.BirdEyePicInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

/**
 * output class name
 */
public class BirdEyeShowUI extends AbstractBirdEyeShowUI
{
    private static final Logger logger = CoreUIObject.getLogger(BirdEyeShowUI.class);
    private ITreeBuilder treeBuilder;
    /**
     * output class constructor
     */
    public BirdEyeShowUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initTree();
    }
    public void initTree() throws Exception{
//    	this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
//    	treeBuilder = TreeBuilderFactory.createTreeBuilder(
//				getProjLNTreeNodeCtrl(), getTreeInitialLevel(),
//				getTreeExpandLevel(),null);
//
//    	treeMain.setModel(treeBuilder.buildTree(null).getModel());
//    	treeMain.setSelectionRow(0);
    }
    private int getTreeInitialLevel() {
		return 50;
	}

	private int getTreeExpandLevel() {
		return 5;
	}

	private ILNTreeNodeCtrl getProjLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getProjTreeInterface());
	}

	private ITreeBase getProjTreeInterface() throws Exception {
		ITreeBase treeBase = BankFactory.getRemoteInstance();
		return treeBase;
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
//		showBirdEyeLable(kDPanel1,node,null,null);
	}
	/**
	 * 获取当前组织下所有的项目节点
	 * */
	public static Set getAllProjectNodeId(DefaultKingdeeTreeNode root){
		Set idSet = new HashSet();
		//取出销售组织下所有的一级节点ID，取得projectID
		String id = null;
		for(int i = 0 ; i < root.getChildCount() ; i++){
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)root.getChildAt(i);
			if(node!=null && node.getUserObject() instanceof OrgStructureInfo){
				getAllProjectNodeId(node);
			}
			else if(node!=null && node.getUserObject() instanceof SellProjectInfo){
				id = (((SellProjectInfo)node.getUserObject())).getId().toString();
				idSet.add(id);
				getAllProjectNodeId(node);
			}
		}
		if(idSet.size()==0)
			idSet.add("AAAAAAAA");
		return idSet;
	}
	/**
	 * 根据选择项目节点，展示项目或楼栋标签
	 * */
    public  void showBirdEyeLable(KDPanel kDPanel,DefaultKingdeeTreeNode node,JComponent com1,JComponent com2)throws Exception{
    	jcom1 = com1;
    	jcom2 = com2;
    	jkDPanel = kDPanel;
    	if(node!=null && node.getUserObject() instanceof SellProjectInfo){
    		SellProjectInfo project = (SellProjectInfo)node.getUserObject();
    		Set idSet = getAllProjectNodeId(node);
        	EntityViewInfo view = new EntityViewInfo();
        	FilterInfo filter = new FilterInfo();
        	view.setFilter(filter);
        	filter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.INCLUDE));
        	SellProjectCollection projectColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
        	index = 0;
        	x = 100;
        	if(projectColl.size()>0){//显示项目分期
        		lab = new KDLabel[projectColl.size()];
            	kDPanel.removeAll();
//            	kDPanel.repaint();
            	if(projectColl.size()>0){
            		for(int i=0;i<projectColl.size();i++){
            			lab[i] = new KDLabel();
            			index = i;
            			initLable(kDPanel,projectColl.get(i));
            		}
            	}
        	}else{ //显示项目楼栋
        		BuildingCollection buildColl = getBuildingCollection(project.getId().toString());
        		lab = new KDLabel[buildColl.size()];
            	kDPanel.removeAll();
//            	kDPanel.repaint();
            	if(buildColl.size()>0){
            		for(int i=0;i<buildColl.size();i++){
            			lab[i] = new KDLabel();
            			index = i;
            			initLable(kDPanel,buildColl.get(i));
            		}
            	}
        	}
        	BirdEyePicInfo picInfo = getBirdEyePicInfo(project.getId().toString());
        	if(kDPanel!=null && kDPanel instanceof SHE2ImagePanel && picInfo.getId()!=null)
        		addImage((SHE2ImagePanel)kDPanel,picInfo.getPicFile());
        	else
        		addImage((SHE2ImagePanel)kDPanel,new byte[100]);
        		
    	}
    }
     int x = 100;
     int y = 100;
     int index = 0;
     KDLabel[] lab = null;
     JComponent jcom1 ; //标签穿透,父Panel
     JComponent jcom2 ; //标签穿透,其他子Panel
     KDPanel jkDPanel; //标签Panel
    public  void initLable(KDPanel kDPanel,Object obj) throws Exception{
    	String labName = "";
    	String labText = "";
    	BirdEyeInfo birdInfo = new BirdEyeInfo();
    	if(obj instanceof SellProjectInfo){
    		SellProjectInfo project = ((SellProjectInfo)obj);
    		labName = project.getId().toString();
    		labText = project.getName();
    		birdInfo = getLabeltBirdEyeInfo(project.getId().toString());
    	}
    	if(obj instanceof BuildingInfo){
    		BuildingInfo build = ((BuildingInfo)obj);
    		labName = build.getId().toString();
    		labText = build.getName();
    		birdInfo = getLabeltBirdEyeInfo(build.getId().toString());
    	}
    	if(birdInfo.getId()!=null){
    		x = birdInfo.getLocationX();
    		y = birdInfo.getLocationY();
    	}
    	lab[index].setFont(new Font(lab[index].getFont().getName(),Font.BOLD,15));
    	lab[index].setForeground(Color.WHITE);
    	lab[index].setName(labName);
    	lab[index].setText(labText);	
//    	RoomDisplaySetting setting = new RoomDisplaySetting();
    	lab[index].setBackground(Color.RED);
    	lab[index].setOpaque(true);
    	lab[index].setBounds(new Rectangle(x, y, labText.length()*20, 25));
        lab[index].setBorder(BasicBorders.getButtonBorder());
    	lab[index].addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2){
					KDLabel kdLabel = (KDLabel)e.getSource(); 
					try {
						DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode();
						Object billInfo = (Object) DynamicObjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(kdLabel.getName()).getObjectType(),new ObjectUuidPK(kdLabel.getName()));
						if(billInfo instanceof SellProjectInfo){
							SellProjectInfo project = (SellProjectInfo)billInfo;
							node.setUserObject(project);
							BirdEyeShowUI birdUI = new BirdEyeShowUI();
							birdUI.showBirdEyeLable((KDPanel)kdLabel.getParent(), node,jcom1,jcom2);
							if(jcom1!=null && jcom1.getParent() instanceof SellControlUI){//销售控制
								SellControlUI ui = (SellControlUI)jcom1.getParent();
								ui.setTreeMain_valueChanged(ui.getTreeRoot(),(SellProjectInfo)billInfo);
							}
							else if(jcom1!=null && jcom1.getParent() instanceof VirtualSellControlUI){//房源查询
								VirtualSellControlUI ui = (VirtualSellControlUI)jcom1.getParent();
								ui.setTreeMain_valueChanged(ui.getTreeRoot(),(SellProjectInfo)billInfo);
							}
							else if(jcom1!=null && jcom1.getParent() instanceof RoomSourceListUI){//房源管理
								RoomSourceListUI ui = (RoomSourceListUI)jcom1.getParent();
								ui.setTreeMain_valueChanged(ui.getTreeRoot(),(SellProjectInfo)billInfo);
							}
						}
						if(jcom1!=null && billInfo instanceof BuildingInfo){
							if(jcom1!=null && jcom1.getParent() instanceof SellControlUI){//销售控制
								((KDSplitPane)jcom1).remove(jkDPanel);
								((KDSplitPane)jcom1).add(jcom2, "right");
								SellControlUI ui = (SellControlUI)jcom2.getParent().getParent();
								ui.setTreeMain_valueChanged(ui.getTreeRoot(),(BuildingInfo)billInfo);
							}
							else if(jcom1!=null && jcom1.getParent() instanceof VirtualSellControlUI){//房源查询
								((KDSplitPane)jcom1).remove(jkDPanel);
								((KDSplitPane)jcom1).add(jcom2, "right");
								VirtualSellControlUI ui = (VirtualSellControlUI)jcom2.getParent().getParent();
								ui.setTreeMain_valueChanged(ui.getTreeRoot(),(BuildingInfo)billInfo);
							}
							else if(jcom1!=null && jcom1.getParent() instanceof RoomSourceListUI){//房源管理
								RoomSourceListUI ui = (RoomSourceListUI)jcom2.getParent().getParent().getParent();
								ui.setTreeMain_valueChanged(ui.getTreeRoot(),(BuildingInfo)billInfo);
							}
							
							/*
							if(jcom2.getParent().getParent() instanceof SellControlUI){//销售控制
								((KDSplitPane)jcom1).remove(jkDPanel);
								((KDSplitPane)jcom1).add(jcom2, "right");
								SellControlUI ui = (SellControlUI)jcom2.getParent().getParent();
								ui.setTreeMain_valueChanged(ui.getTreeRoot(),(BuildingInfo)billInfo);
							}
							else if(jcom2.getParent().getParent() instanceof VirtualSellControlUI){//房源查询
								((KDSplitPane)jcom1).remove(jkDPanel);
								((KDSplitPane)jcom1).add(jcom2, "right");
								VirtualSellControlUI ui = (VirtualSellControlUI)jcom2.getParent().getParent();
								ui.setTreeMain_valueChanged(ui.getTreeRoot(),(BuildingInfo)billInfo);
							}
							else if(jcom2.getParent().getParent().getParent() instanceof RoomSourceListUI){//房源管理
								RoomSourceListUI ui = (RoomSourceListUI)jcom2.getParent().getParent().getParent();
								ui.setTreeMain_valueChanged(ui.getTreeRoot(),(BuildingInfo)billInfo);
							}
							*/
						}
					} catch (Exception e1) {
						BirdEyeShowUI.this.handleException(e1);
					}
				}
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			//锚点
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}
			public void mouseReleased(MouseEvent e) {
			}
    	});
    	kDPanel.add(lab[index], null);
    	lab[index].addMouseMotionListener(new MouseMotionListener(){
    		//拖拽标签
			public void mouseDragged(MouseEvent e) {
				int diffX = e.getX() -x;
				int diffY = e.getY() -y;
				KDLabel kdLabel = (KDLabel)e.getSource(); 
				kdLabel.setBounds(kdLabel.getX()+diffX, kdLabel.getY()+diffY,kdLabel.getWidth(),kdLabel.getHeight());
			}
			public void mouseMoved(MouseEvent e) {
			}
    	});
    }
    /**
     * 根据项目ID去获取该项目下的项目标签或者楼栋标签
     * */
    public static BirdEyeInfo getLabeltBirdEyeInfo(String sellProjectOrBuildId)throws Exception{
    	BirdEyeInfo info = new BirdEyeInfo();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("sellProjectOrBuildingId",sellProjectOrBuildId));
    	BirdEyeCollection coll = BirdEyeFactory.getRemoteInstance().getBirdEyeCollection(view);
    	if(coll!=null && coll.size()>0)
    		info = coll.get(0);
     	return info;
    }
    /**
     * 根据项目ID去获取该项目下的鸟瞰图
     * */
    public static BirdEyePicInfo getBirdEyePicInfo(String sellProjectOrBuildId)throws Exception{
    	BirdEyePicInfo info = new BirdEyePicInfo();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("sellProject",sellProjectOrBuildId));
    	BirdEyePicCollection coll = BirdEyePicFactory.getRemoteInstance().getBirdEyePicCollection(view);
    	if(coll!=null && coll.size()>0)
    		info = coll.get(0);
     	return info;
    }
    /**
     * 根据项目ID去获取该项目下的项目标签或者楼栋标签
     * */
    public static BuildingCollection getBuildingCollection(String sellProjectId)throws Exception{
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("sellProject",sellProjectId));
    	BuildingCollection coll = BuildingFactory.getRemoteInstance().getBuildingCollection(view);
     	return coll;
    }
    /**
     * 弹出图片选择框
     * */
    public static BirdEyePicInfo showImgChooser(KDPanel kDPanel)throws Exception{
    	BirdEyePicInfo birdEyePicInfo = new BirdEyePicInfo();
    	chooser.resetChoosableFileFilters();
		chooser.removeChoosableFileFilter(chooser.getFileFilter());
    	chooser.addChoosableFileFilter(new MyFileFilter(".jpg;.gif","JPG/GIF格式图片"));
    	int i=chooser.showOpenDialog(null);
    	if(i==KDFileChooser.APPROVE_OPTION){//当选择了图片后才进行下面操作
	    	File file = chooser.getSelectedFile();
	        if(file!=null)
	        {
	        	imgPath=file;
//	        	kDPanel.repaint();
		        showImage(kDPanel,file);
		        kDPanel.repaint();
		        if(imgPath!=null)//保存图片到分录中
		        {
					long len = imgPath.length();
					byte[] bytes = new byte[(int)len];
					BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(imgPath));
					int r = bufferedInputStream.read(bytes);
					if (r != len) {
						throw new IOException("文件读取异常！");
					}
				    bufferedInputStream.close();
				    birdEyePicInfo.setPicFile(bytes);
				    birdEyePicInfo.setPicPath(file.getPath());
				    birdEyePicInfo.setName(file.getName());
				}
	        }
    	}
    	return birdEyePicInfo ;
    }
    /**
	 * 加载图片
	 * */
	private static void addImage(KDPanel kDPanel,byte[] imageFile)
	{
		File file;
		try {
			if(imageFile!=null)
			{
				file = File.createTempFile("tmp1111", "a.jpg");
				ByteInputStream stream = new ByteInputStream(imageFile, imageFile.length);
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
				showImage(kDPanel,file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 设置显示图片
	 * @param file
	 */
	private static void showImage(KDPanel kDPanel,File file){
		((SHE2ImagePanel)kDPanel).setImageFile(file);
//		((SHE2ImagePanel)kDPanel).repaint();
	}
	
	private static KDFileChooser chooser=new KDFileChooser();
    private static File imgPath=null;//图片路径
	MouseListener moListener;
	MouseMotionListener mmListener;
}