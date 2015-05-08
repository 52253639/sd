/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.tree.TreeNode;
import org.apache.log4j.Logger;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.RESchTemplateFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;

/**
 * 
 * @(#)						
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		计划模板列表界面
 *		
 * @author		罗小龙、车忠伟
 * @version		EAS7.0		
 * @createDate	2011-08-311
 * @see
 */
public class RESchTemplateTreeNewListUI extends AbstractRESchTemplateTreeNewListUI
{
	
    /** 缺少版本号 */
	private static final long serialVersionUID = 1L;
	
	/** 日志文件 */
	private static final Logger logger = CoreUIObject.getLogger(RESchTemplateTreeNewListUI.class);
	
	/** 构建树变量 */
    protected ITreeBuilder treeBuilder;
    
    /**
     * output class constructor
     */
    public RESchTemplateTreeNewListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	initTree();
    	initDisplayStyle();
    	
    	/* 加点击事件 */
    	this.btnOk.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				confrim();
			}
			
			public void mouseEntered(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}

			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}
    		
    	});
    	
    	/* 加点击事件 */
    	this.btnNo.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				destroyWindow();
			}
			
			public void mouseEntered(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}

			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}
    		
    	});
    }

    /**
     * @discription  <确定>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/31> <p>
     * @param        <空>
     * @return       <返回值描述>
     * 
     * modifier      <空> <p>
     * modifyDate    <空> <p>
     * modifyInfo	 <空> <p>
     * @see          <相关的类>
     */
    public void confrim() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		Object obj = node.getUserObject();
		RESchTemplateInfo info = (RESchTemplateInfo) obj;
		if (!info.isIsLeaf()) {
			FDCMsgBox.showWarning("请选择明细");
			SysUtil.abort();
		}
		getUIContext().put("template", obj);

		destroyWindow();
	}
    
    /**
     * @discription  <初始化显示样式>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/31> <p>
     * @param        <空>
     * @return       <返回值描述>
     * 
     * modifier      <空> <p>
     * modifyDate    <空> <p>
     * modifyInfo	 <空> <p>
     * @see          <相关的类>
     */
    public void initDisplayStyle(){
    	this.tblMain.setVisible(false);
    	this.btnOk.setEnabled(true);
    	this.btnNo.setEnabled(true);
    	
    	//设置根节点不显示
    	this.treeMain.setRootVisible(false);
    	
    	//设置树节点全展开
    	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
    }

    /**
     * @discription  <构建树>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/31> <p>
     * @param        <空>
     * @return       <返回值描述>
     * 
     * modifier      <空> <p>
     * modifyDate    <空> <p>
     * modifyInfo	 <空> <p>
     * @see          <相关的类>
     */
    protected void initTree() throws Exception {

		buildTemplateTree();
		treeMain.setRootVisible(false);
		treeMain.setShowsRootHandles(true);
		treeMain.expandRow(0);
		treeMain.setSelectionRow(0);
	}
    
    /**
     * 不显示工具栏
     */
    public void initUIToolBarLayout() {
//    	super.initUIToolBarLayout();
    }
    
    /**
     * @discription  <构建树>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/31> <p>
     * @param        <空>
     * @return       <返回值描述>
     * 
     * modifier      <空> <p>
     * modifyDate    <空> <p>
     * modifyInfo	 <空> <p>
     * @throws Exception 
     * @see          <相关的类>
     */
    private void buildTemplateTree() throws Exception{
    	treeBuilder = TreeBuilderFactory.createTreeBuilder(getTempLNTreeNodeCtrl(), 50, 5, new FilterInfo(), new SelectorItemCollection());
    	treeBuilder.buildTree(treeMain);
    }
    
    
    private ILNTreeNodeCtrl getTempLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(RESchTemplateFactory.getRemoteInstance());
	}

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionView_actionPerformed(e);
    }


	protected ICoreBase getBizInterface() throws Exception {
		return RESchTemplateFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}
		

}