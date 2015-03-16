package com.kingdee.eas.fdc.invite.supplier.client;


	import javax.swing.event.TreeSelectionListener;
	import javax.swing.tree.DefaultTreeModel;

	import com.kingdee.bos.BOSException;
	import com.kingdee.bos.ctrl.swing.KDTree;
	import com.kingdee.bos.metadata.entity.FilterInfo;
	import com.kingdee.eas.framework.ITreeBase;
	import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
	import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
	import com.kingdee.eas.framework.client.tree.ITreeBuilder;
	import com.kingdee.eas.framework.client.tree.KDTreeNode;
	import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;

	/**
	 * 
	 * 金蝶国际软件集团有限公司版权所有
	 * 描述：材料信息库，树构建工具类
	 * @author 向晓帆  2010-11-5  <p>
	 *
	 * 修改人：<p>
	 * 修改时间：<p>
	 * 修改描述：<p>
	 *
	 * @version <EAS***>
	 * @see <相关类>
	 */
	public class AppraiseTypeTree {
		
		/**树根节点名字*/
		public String treeRootName;
		/**私有构造方法*/
		private AppraiseTypeTree(){};
		/**声明*/
		private static AppraiseTypeTree materialTreeUtil = null;
		
		/**
		 * 描述：单例模式
		 * 创建时间：2010-11-5<p>
		 * 
		 * 修改人：<p>
		 * 修改时间： <p>
		 * 修改描述： <p>
		 *
		 * @see  
		*/
		public static synchronized AppraiseTypeTree getMaterialTree(){
			/*
			 * 如果未被实例化则实例化
			 * 否则就直接返回
			 */
			if (materialTreeUtil  == null) {
				return materialTreeUtil = new AppraiseTypeTree();
			}else
				return materialTreeUtil ;
		}
		
		/**
		 * 描述：构建出材料信息树
		 * 创建时间：2010-11-5<p>
		 * 
		 * 修改人：<p>
		 * 修改时间： <p>
		 * 修改描述： <p>
		 *
		 *	@param KDTree tree 树控件
		 *	@param ITreeBase treeBaseFactoryRemoteInstance 树数据的远程调用接口
		 *	@param String treeRootName 树根节点名字
		 * @see  
		*/
		public void buiderTree(KDTree tree,ITreeBase treeBaseFactoryRemoteInstance,String treeRootName) throws Exception{
			this.setTreeRootName(treeRootName);
			this.initTree(tree,treeBaseFactoryRemoteInstance);
			
		}
		
		
		/**
		 * 描述：初始化树信息
		 * 创建时间：2010-11-5<p>
		 * 
		 * 修改人：<p>
		 * 修改时间： <p>
		 * 修改描述： <p>
		 * 
		 * @see  
		*/
		private void initTree(KDTree tree,ITreeBase treeBaseFactoryRemoteInstance) throws Exception{
			
			TreeSelectionListener treeSelectionListener = null;
	    	ITreeBuilder treeBuilder = null;
	    	KDTree treeMain = this.getMaterialTree(tree);
	    	TreeSelectionListener [] listeners = treeMain.getTreeSelectionListeners();
	    	/*
	    	 * 假如树点击监听器大于0就将监听器设置为第1个
	    	 * 并且移除整个树的监听事件
	    	 */
	    	if(listeners.length > 0 ){
	    		treeSelectionListener = listeners[0];
	    		treeMain.removeTreeSelectionListener(treeSelectionListener);
	    	}
	    	//得到树创建器
	    	treeBuilder = TreeBuilderFactory.createTreeBuilder(this.getLNTreeNodeCtrl(treeBaseFactoryRemoteInstance),	this.getTreeInitialLevel(), this.getTreeExpandLevel(), this.getDefaultFilterForTree());
	    	
	    	/*
	    	 * 如果根结点名字不为空
	    	 * 就得到根节点下的子节点
	    	 */
	    	if (null != getRootName() && !"".equals(getRootName())) {
				KDTreeNode rootNode = new KDTreeNode(this.getRootObject());
				((DefaultTreeModel) treeMain.getModel()).setRoot(rootNode);
				
			} else {//否则不显示任何数据
				((DefaultTreeModel) treeMain.getModel()).setRoot(null);
			}
	    	//创建树
	    	treeBuilder.buildTree(treeMain);
			treeMain.addTreeSelectionListener(treeSelectionListener);
			treeMain.setShowPopMenuDefaultItem(false);
		}
		
		 /**
		 * 描述：得到根节点名字
		 * 创建时间：2010-11-5<p>
		 * 
		 * 修改人：<p>
		 * 修改时间： <p>
		 * 修改描述： <p>
		 *
		 * @see  
		*/
		private String getRootName(){
		    	return this.getTreeRootName();
		 }
		 private Object getRootObject(){
		    	return this.getRootName();
		 }
		  
	    /**
	     * 描述：获得当前页面tree控件
	     * 创建时间：2010-11-5<p>
	     * 
	     * 修改人：<p>
	     * 修改时间： <p>
	     * 修改描述： <p>
	     *
	     * @param tree控件
	     * @see  
	    */
	    private KDTree getMaterialTree(KDTree tree){
	    	return tree;
	    }
	    
	    private ITreeBase getTreeInterface(ITreeBase treeBaseFactoryRemoteInstance) throws BOSException{
	    	ITreeBase treeBase = null;
			treeBase = treeBaseFactoryRemoteInstance;
			return treeBase;
	    }
	    
	    protected ILNTreeNodeCtrl getLNTreeNodeCtrl(ITreeBase treeBaseFactoryRemoteInstance) throws Exception {
			return new DefaultLNTreeNodeCtrl(this.getTreeInterface(treeBaseFactoryRemoteInstance));
		}
	    
	    protected int getTreeInitialLevel() {
			return TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
		}

		protected int getTreeExpandLevel() {
			return TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
		}
		
		private FilterInfo getDefaultFilterForTree() {
			FilterInfo filter = new FilterInfo();
			return filter;
		}
		
		private String getTreeRootName() {
			return treeRootName;
		}

		private void setTreeRootName(String treeRootName) {
			this.treeRootName = treeRootName;
		}
	}
