/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractBoutiqueRoomTypeEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBoutiqueRoomTypeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane paneBIZLayerControl10;
    protected com.kingdee.bos.ctrl.swing.KDPanel mainInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contflatLayerType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtflatLayerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdeveloper;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtdeveloper;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contarchitectArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtarchitectArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer controomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtroomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer controomType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer controomStyle;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtprice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdirection;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contwideSide;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtwideSide;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbuildTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkbuildTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer controomTypeExplain;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneroomTypeExplain;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtroomTypeExplain;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer controomTypeReview;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneroomTypeReview;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtroomTypeReview;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtroomType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtdirection;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtroomStyle;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contboutiqueHouseTy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtboutiqueHouseTy;
    protected com.kingdee.eas.fdc.market.BoutiqueRoomTypeInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractBoutiqueRoomTypeEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractBoutiqueRoomTypeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.paneBIZLayerControl10 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.mainInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contflatLayerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtflatLayerType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contdeveloper = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtdeveloper = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contarchitectArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtarchitectArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.controomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtroomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.controomType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.controomStyle = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtprice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contdirection = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contwideSide = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtwideSide = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contbuildTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkbuildTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.controomTypeExplain = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPaneroomTypeExplain = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtroomTypeExplain = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.controomTypeReview = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPaneroomTypeReview = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtroomTypeReview = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtroomType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtdirection = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtroomStyle = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contboutiqueHouseTy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtboutiqueHouseTy = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.paneBIZLayerControl10.setName("paneBIZLayerControl10");
        this.mainInfo.setName("mainInfo");
        this.contflatLayerType.setName("contflatLayerType");
        this.prmtflatLayerType.setName("prmtflatLayerType");
        this.contdeveloper.setName("contdeveloper");
        this.prmtdeveloper.setName("prmtdeveloper");
        this.contarchitectArea.setName("contarchitectArea");
        this.txtarchitectArea.setName("txtarchitectArea");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.txtName.setName("txtName");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtNumber.setName("txtNumber");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.txtSimpleName.setName("txtSimpleName");
        this.controomArea.setName("controomArea");
        this.txtroomArea.setName("txtroomArea");
        this.controomType.setName("controomType");
        this.controomStyle.setName("controomStyle");
        this.contprice.setName("contprice");
        this.txtprice.setName("txtprice");
        this.contdirection.setName("contdirection");
        this.contwideSide.setName("contwideSide");
        this.txtwideSide.setName("txtwideSide");
        this.contbuildTime.setName("contbuildTime");
        this.pkbuildTime.setName("pkbuildTime");
        this.controomTypeExplain.setName("controomTypeExplain");
        this.scrollPaneroomTypeExplain.setName("scrollPaneroomTypeExplain");
        this.txtroomTypeExplain.setName("txtroomTypeExplain");
        this.controomTypeReview.setName("controomTypeReview");
        this.scrollPaneroomTypeReview.setName("scrollPaneroomTypeReview");
        this.txtroomTypeReview.setName("txtroomTypeReview");
        this.prmtroomType.setName("prmtroomType");
        this.prmtdirection.setName("prmtdirection");
        this.prmtroomStyle.setName("prmtroomStyle");
        this.txtDescription.setName("txtDescription");
        this.contboutiqueHouseTy.setName("contboutiqueHouseTy");
        this.txtboutiqueHouseTy.setName("txtboutiqueHouseTy");
        // CoreUI		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // paneBIZLayerControl10		
        this.paneBIZLayerControl10.setVisible(true);		
        this.paneBIZLayerControl10.setPreferredSize(new Dimension(10,10));
        // mainInfo		
        this.mainInfo.setVisible(true);		
        this.mainInfo.setBorder(BorderFactory.createEtchedBorder());
        // contflatLayerType		
        this.contflatLayerType.setBoundLabelText(resHelper.getString("contflatLayerType.boundLabelText"));		
        this.contflatLayerType.setBoundLabelLength(100);		
        this.contflatLayerType.setBoundLabelUnderline(true);		
        this.contflatLayerType.setVisible(true);		
        this.contflatLayerType.setBoundLabelAlignment(7);		
        this.contflatLayerType.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // prmtflatLayerType		
        this.prmtflatLayerType.setQueryInfo("com.kingdee.eas.fdc.market.app.FlatLayerTypeQuery");		
        this.prmtflatLayerType.setVisible(true);		
        this.prmtflatLayerType.setEditable(true);		
        this.prmtflatLayerType.setDisplayFormat("$name$");		
        this.prmtflatLayerType.setEditFormat("$number$");		
        this.prmtflatLayerType.setCommitFormat("$number$");		
        this.prmtflatLayerType.setRequired(false);		
        this.prmtflatLayerType.setEnabled(true);		
        this.prmtflatLayerType.setForeground(new java.awt.Color(0,0,0));
        // contdeveloper		
        this.contdeveloper.setBoundLabelText(resHelper.getString("contdeveloper.boundLabelText"));		
        this.contdeveloper.setBoundLabelLength(100);		
        this.contdeveloper.setBoundLabelUnderline(true);		
        this.contdeveloper.setVisible(true);		
        this.contdeveloper.getBoundLabel().setForeground(new java.awt.Color(0,0,0));		
        this.contdeveloper.setBoundLabelAlignment(7);
        // prmtdeveloper		
        this.prmtdeveloper.setQueryInfo("com.kingdee.eas.fdc.market.app.DeveloperManageQuery");		
        this.prmtdeveloper.setVisible(true);		
        this.prmtdeveloper.setEditable(true);		
        this.prmtdeveloper.setForeground(new java.awt.Color(0,0,0));		
        this.prmtdeveloper.setDisplayFormat("$name$");		
        this.prmtdeveloper.setEditFormat("$number$");		
        this.prmtdeveloper.setCommitFormat("$number$");		
        this.prmtdeveloper.setRequired(false);		
        this.prmtdeveloper.setEnabled(true);
        // contarchitectArea		
        this.contarchitectArea.setBoundLabelText(resHelper.getString("contarchitectArea.boundLabelText"));		
        this.contarchitectArea.setBoundLabelLength(100);		
        this.contarchitectArea.setBoundLabelUnderline(true);		
        this.contarchitectArea.setVisible(true);		
        this.contarchitectArea.setBoundLabelAlignment(7);		
        this.contarchitectArea.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtarchitectArea		
        this.txtarchitectArea.setVisible(true);		
        this.txtarchitectArea.setHorizontalAlignment(2);		
        this.txtarchitectArea.setDataType(1);		
        this.txtarchitectArea.setSupportedEmpty(true);		
        this.txtarchitectArea.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtarchitectArea.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtarchitectArea.setPrecision(2);		
        this.txtarchitectArea.setRequired(false);		
        this.txtarchitectArea.setEnabled(true);		
        this.txtarchitectArea.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelAlignment(7);		
        this.kDLabelContainer2.setVisible(true);		
        this.kDLabelContainer2.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtName		
        this.txtName.setVisible(true);		
        this.txtName.setEnabled(true);		
        this.txtName.setForeground(new java.awt.Color(0,0,0));		
        this.txtName.setRequired(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);		
        this.kDLabelContainer1.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setCustomForegroundColor(new java.awt.Color(0,0,0));		
        this.txtNumber.setRequired(false);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);		
        this.kDLabelContainer4.setVisible(true);		
        this.kDLabelContainer4.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelAlignment(7);		
        this.kDLabelContainer3.setVisible(true);		
        this.kDLabelContainer3.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtSimpleName		
        this.txtSimpleName.setMaxLength(80);		
        this.txtSimpleName.setVisible(true);		
        this.txtSimpleName.setEnabled(true);		
        this.txtSimpleName.setHorizontalAlignment(2);		
        this.txtSimpleName.setCustomForegroundColor(new java.awt.Color(0,0,0));		
        this.txtSimpleName.setRequired(false);
        // controomArea		
        this.controomArea.setBoundLabelText(resHelper.getString("controomArea.boundLabelText"));		
        this.controomArea.setBoundLabelLength(100);		
        this.controomArea.setBoundLabelUnderline(true);		
        this.controomArea.setVisible(true);		
        this.controomArea.setBoundLabelAlignment(7);		
        this.controomArea.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtroomArea		
        this.txtroomArea.setVisible(true);		
        this.txtroomArea.setHorizontalAlignment(2);		
        this.txtroomArea.setDataType(1);		
        this.txtroomArea.setSupportedEmpty(true);		
        this.txtroomArea.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtroomArea.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtroomArea.setPrecision(2);		
        this.txtroomArea.setRequired(false);		
        this.txtroomArea.setEnabled(true);		
        this.txtroomArea.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // controomType		
        this.controomType.setBoundLabelText(resHelper.getString("controomType.boundLabelText"));		
        this.controomType.setBoundLabelLength(100);		
        this.controomType.setBoundLabelUnderline(true);		
        this.controomType.setVisible(true);		
        this.controomType.setBoundLabelAlignment(7);		
        this.controomType.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // controomStyle		
        this.controomStyle.setBoundLabelText(resHelper.getString("controomStyle.boundLabelText"));		
        this.controomStyle.setBoundLabelLength(100);		
        this.controomStyle.setBoundLabelUnderline(true);		
        this.controomStyle.setVisible(true);		
        this.controomStyle.setBoundLabelAlignment(7);		
        this.controomStyle.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // contprice		
        this.contprice.setBoundLabelText(resHelper.getString("contprice.boundLabelText"));		
        this.contprice.setBoundLabelLength(100);		
        this.contprice.setBoundLabelUnderline(true);		
        this.contprice.setVisible(true);		
        this.contprice.setBoundLabelAlignment(7);		
        this.contprice.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtprice		
        this.txtprice.setVisible(true);		
        this.txtprice.setHorizontalAlignment(2);		
        this.txtprice.setDataType(1);		
        this.txtprice.setSupportedEmpty(true);		
        this.txtprice.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtprice.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtprice.setPrecision(2);		
        this.txtprice.setRequired(false);		
        this.txtprice.setEnabled(true);		
        this.txtprice.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // contdirection		
        this.contdirection.setBoundLabelText(resHelper.getString("contdirection.boundLabelText"));		
        this.contdirection.setBoundLabelLength(100);		
        this.contdirection.setBoundLabelUnderline(true);		
        this.contdirection.setVisible(true);		
        this.contdirection.setBoundLabelAlignment(7);		
        this.contdirection.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // contwideSide		
        this.contwideSide.setBoundLabelText(resHelper.getString("contwideSide.boundLabelText"));		
        this.contwideSide.setBoundLabelLength(100);		
        this.contwideSide.setBoundLabelUnderline(true);		
        this.contwideSide.setVisible(true);		
        this.contwideSide.setBoundLabelAlignment(7);		
        this.contwideSide.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtwideSide		
        this.txtwideSide.setVisible(true);		
        this.txtwideSide.setHorizontalAlignment(2);		
        this.txtwideSide.setDataType(1);		
        this.txtwideSide.setSupportedEmpty(true);		
        this.txtwideSide.setMinimumValue( new java.math.BigDecimal("-1.0E18"));		
        this.txtwideSide.setMaximumValue( new java.math.BigDecimal("1.0E18"));		
        this.txtwideSide.setPrecision(2);		
        this.txtwideSide.setRequired(false);		
        this.txtwideSide.setEnabled(true);		
        this.txtwideSide.setCustomForegroundColor(new java.awt.Color(0,0,0));
        // contbuildTime		
        this.contbuildTime.setBoundLabelText(resHelper.getString("contbuildTime.boundLabelText"));		
        this.contbuildTime.setBoundLabelLength(100);		
        this.contbuildTime.setBoundLabelUnderline(true);		
        this.contbuildTime.setVisible(true);		
        this.contbuildTime.setBoundLabelAlignment(7);		
        this.contbuildTime.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // pkbuildTime		
        this.pkbuildTime.setVisible(true);		
        this.pkbuildTime.setRequired(false);		
        this.pkbuildTime.setEnabled(true);		
        ((com.kingdee.bos.ctrl.swing.BasicFormattedTextField)this.pkbuildTime.getEditor().getEditorComponent()).setCustomForegroundColor(new java.awt.Color(0,0,0));
        // controomTypeExplain		
        this.controomTypeExplain.setBoundLabelText(resHelper.getString("controomTypeExplain.boundLabelText"));		
        this.controomTypeExplain.setBoundLabelLength(100);		
        this.controomTypeExplain.setBoundLabelUnderline(true);		
        this.controomTypeExplain.setVisible(true);		
        this.controomTypeExplain.setBoundLabelAlignment(7);		
        this.controomTypeExplain.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // scrollPaneroomTypeExplain
        // txtroomTypeExplain		
        this.txtroomTypeExplain.setVisible(true);		
        this.txtroomTypeExplain.setRequired(false);		
        this.txtroomTypeExplain.setMaxLength(255);		
        this.txtroomTypeExplain.setEnabled(true);		
        this.txtroomTypeExplain.setForeground(new java.awt.Color(0,0,0));
        // controomTypeReview		
        this.controomTypeReview.setBoundLabelText(resHelper.getString("controomTypeReview.boundLabelText"));		
        this.controomTypeReview.setBoundLabelLength(100);		
        this.controomTypeReview.setBoundLabelUnderline(true);		
        this.controomTypeReview.setVisible(true);		
        this.controomTypeReview.setBoundLabelAlignment(7);		
        this.controomTypeReview.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // scrollPaneroomTypeReview
        // txtroomTypeReview		
        this.txtroomTypeReview.setVisible(true);		
        this.txtroomTypeReview.setRequired(false);		
        this.txtroomTypeReview.setMaxLength(255);		
        this.txtroomTypeReview.setEnabled(true);		
        this.txtroomTypeReview.setForeground(new java.awt.Color(0,0,0));
        // prmtroomType		
        this.prmtroomType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery");		
        this.prmtroomType.setDisplayFormat("$name$");		
        this.prmtroomType.setEditFormat("$number$");		
        this.prmtroomType.setCommitFormat("$number$");
        // prmtdirection		
        this.prmtdirection.setDisplayFormat("$name$");		
        this.prmtdirection.setEditFormat("$number$");		
        this.prmtdirection.setCommitFormat("$number$");		
        this.prmtdirection.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery");
        // prmtroomStyle		
        this.prmtroomStyle.setDisplayFormat("$name$");		
        this.prmtroomStyle.setEditFormat("$number$");		
        this.prmtroomStyle.setCommitFormat("$number$");		
        this.prmtroomStyle.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");
        // txtDescription		
        this.txtDescription.setVisible(true);		
        this.txtDescription.setEnabled(true);		
        this.txtDescription.setForeground(new java.awt.Color(0,0,0));		
        this.txtDescription.setRequired(false);		
        this.txtDescription.setMaxLength(255);
        // contboutiqueHouseTy		
        this.contboutiqueHouseTy.setBoundLabelText(resHelper.getString("contboutiqueHouseTy.boundLabelText"));		
        this.contboutiqueHouseTy.setBoundLabelLength(100);		
        this.contboutiqueHouseTy.setBoundLabelUnderline(true);		
        this.contboutiqueHouseTy.setVisible(true);		
        this.contboutiqueHouseTy.getBoundLabel().setForeground(new java.awt.Color(0,0,0));		
        this.contboutiqueHouseTy.setBoundLabelAlignment(7);
        // txtboutiqueHouseTy		
        this.txtboutiqueHouseTy.setVisible(true);		
        this.txtboutiqueHouseTy.setHorizontalAlignment(2);		
        this.txtboutiqueHouseTy.setCustomForegroundColor(new java.awt.Color(0,0,0));		
        this.txtboutiqueHouseTy.setMaxLength(100);		
        this.txtboutiqueHouseTy.setRequired(false);		
        this.txtboutiqueHouseTy.setEnabled(true);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtdeveloper,prmtflatLayerType,txtarchitectArea,txtroomArea,txtprice,txtwideSide,pkbuildTime,txtroomTypeExplain,txtroomTypeReview}));
        this.setFocusCycleRoot(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(0, 0, 1024, 872));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1024, 872));
        paneBIZLayerControl10.setBounds(new Rectangle(7, 6, 1007, 823));
        this.add(paneBIZLayerControl10, new KDLayout.Constraints(7, 6, 1007, 823, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //paneBIZLayerControl10
        paneBIZLayerControl10.add(mainInfo, resHelper.getString("mainInfo.constraints"));
        //mainInfo
        mainInfo.setLayout(null);        contflatLayerType.setBounds(new Rectangle(718, 36, 270, 19));
        mainInfo.add(contflatLayerType, null);
        contdeveloper.setBounds(new Rectangle(367, 11, 270, 19));
        mainInfo.add(contdeveloper, null);
        contarchitectArea.setBounds(new Rectangle(6, 63, 270, 19));
        mainInfo.add(contarchitectArea, null);
        kDLabelContainer2.setBounds(new Rectangle(367, 36, 270, 19));
        mainInfo.add(kDLabelContainer2, null);
        kDLabelContainer1.setBounds(new Rectangle(6, 36, 270, 19));
        mainInfo.add(kDLabelContainer1, null);
        kDLabelContainer4.setBounds(new Rectangle(7, 547, 974, 211));
        mainInfo.add(kDLabelContainer4, null);
        kDLabelContainer3.setBounds(new Rectangle(718, 11, 270, 19));
        mainInfo.add(kDLabelContainer3, null);
        controomArea.setBounds(new Rectangle(367, 63, 270, 19));
        mainInfo.add(controomArea, null);
        controomType.setBounds(new Rectangle(718, 63, 270, 19));
        mainInfo.add(controomType, null);
        controomStyle.setBounds(new Rectangle(6, 88, 270, 19));
        mainInfo.add(controomStyle, null);
        contprice.setBounds(new Rectangle(367, 88, 270, 19));
        mainInfo.add(contprice, null);
        contdirection.setBounds(new Rectangle(718, 88, 270, 19));
        mainInfo.add(contdirection, null);
        contwideSide.setBounds(new Rectangle(6, 112, 270, 19));
        mainInfo.add(contwideSide, null);
        contbuildTime.setBounds(new Rectangle(367, 112, 270, 19));
        mainInfo.add(contbuildTime, null);
        controomTypeExplain.setBounds(new Rectangle(6, 147, 979, 180));
        mainInfo.add(controomTypeExplain, null);
        controomTypeReview.setBounds(new Rectangle(6, 334, 976, 206));
        mainInfo.add(controomTypeReview, null);
        contboutiqueHouseTy.setBounds(new Rectangle(6, 11, 270, 19));
        mainInfo.add(contboutiqueHouseTy, null);
        //contflatLayerType
        contflatLayerType.setBoundEditor(prmtflatLayerType);
        //contdeveloper
        contdeveloper.setBoundEditor(prmtdeveloper);
        //contarchitectArea
        contarchitectArea.setBoundEditor(txtarchitectArea);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtName);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtDescription);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtSimpleName);
        //controomArea
        controomArea.setBoundEditor(txtroomArea);
        //controomType
        controomType.setBoundEditor(prmtroomType);
        //controomStyle
        controomStyle.setBoundEditor(prmtroomStyle);
        //contprice
        contprice.setBoundEditor(txtprice);
        //contdirection
        contdirection.setBoundEditor(prmtdirection);
        //contwideSide
        contwideSide.setBoundEditor(txtwideSide);
        //contbuildTime
        contbuildTime.setBoundEditor(pkbuildTime);
        //controomTypeExplain
        controomTypeExplain.setBoundEditor(scrollPaneroomTypeExplain);
        //scrollPaneroomTypeExplain
        scrollPaneroomTypeExplain.getViewport().add(txtroomTypeExplain, null);
        //controomTypeReview
        controomTypeReview.setBoundEditor(scrollPaneroomTypeReview);
        //scrollPaneroomTypeReview
        scrollPaneroomTypeReview.getViewport().add(txtroomTypeReview, null);
        //contboutiqueHouseTy
        contboutiqueHouseTy.setBoundEditor(txtboutiqueHouseTy);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);

    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("flatLayerType", com.kingdee.eas.fdc.market.FlatLayerTypeInfo.class, this.prmtflatLayerType, "data");
		dataBinder.registerBinding("developer", com.kingdee.eas.fdc.market.DeveloperManageInfo.class, this.prmtdeveloper, "data");
		dataBinder.registerBinding("architectArea", java.math.BigDecimal.class, this.txtarchitectArea, "value");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("roomArea", java.math.BigDecimal.class, this.txtroomArea, "value");
		dataBinder.registerBinding("price", java.math.BigDecimal.class, this.txtprice, "value");
		dataBinder.registerBinding("wideSide", java.math.BigDecimal.class, this.txtwideSide, "value");
		dataBinder.registerBinding("buildTime", java.util.Date.class, this.pkbuildTime, "value");
		dataBinder.registerBinding("roomTypeExplain", String.class, this.txtroomTypeExplain, "text");
		dataBinder.registerBinding("roomTypeReview", String.class, this.txtroomTypeReview, "text");
		dataBinder.registerBinding("roomType", com.kingdee.eas.fdc.sellhouse.RoomModelInfo.class, this.prmtroomType, "data");
		dataBinder.registerBinding("direction", com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo.class, this.prmtdirection, "data");
		dataBinder.registerBinding("roomStyle", com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo.class, this.prmtroomStyle, "data");
		dataBinder.registerBinding("treeid.name", String.class, this.txtboutiqueHouseTy, "text");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtSimpleName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtSimpleName, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtSimpleName, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.BoutiqueRoomTypeEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }


    /**
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.prmtdeveloper.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.market.BoutiqueRoomTypeInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("NONE");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
		}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("flatLayerType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("developer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("architectArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("wideSide", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomTypeExplain", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomTypeReview", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("direction", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomStyle", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("treeid.name", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(false);
		            this.txtNumber.setEnabled(false);
		            this.txtSimpleName.setEnabled(false);
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("flatLayerType.*"));
        sic.add(new SelectorItemInfo("developer.*"));
        sic.add(new SelectorItemInfo("architectArea"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("roomArea"));
        sic.add(new SelectorItemInfo("price"));
        sic.add(new SelectorItemInfo("wideSide"));
        sic.add(new SelectorItemInfo("buildTime"));
        sic.add(new SelectorItemInfo("roomTypeExplain"));
        sic.add(new SelectorItemInfo("roomTypeReview"));
        sic.add(new SelectorItemInfo("roomType.*"));
        sic.add(new SelectorItemInfo("direction.*"));
        sic.add(new SelectorItemInfo("roomStyle.*"));
        sic.add(new SelectorItemInfo("treeid.name"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "BoutiqueRoomTypeEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.BoutiqueRoomTypeEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.BoutiqueRoomTypeFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.BoutiqueRoomTypeInfo objectValue = new com.kingdee.eas.fdc.market.BoutiqueRoomTypeInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {        
        return null;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}