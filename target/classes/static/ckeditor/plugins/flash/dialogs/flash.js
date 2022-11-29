/*
 Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 For licensing, see LICENSE.md or http://ckeditor.com/license
*/
function enc(a){return encodeURIComponent(encodeURIComponent(a).replace(/%2F/g,'/').replace(/%3A/g,':'));}

(function(){

function b(a,b,c)
{
  var k=n[this.id];
  if(k)for(var f=this instanceof CKEDITOR.ui.dialog.checkbox,e=0;e<k.length;e++)
  {
    var d=k[e];
    switch(d.type)
    {
    case g:if(!a)continue;
    if(null!==a.getAttribute(d.name))
    {
    a=a.getAttribute(d.name);
    f?this.setValue("true"==a.toLowerCase()):this.setValue(a);return
    }
    f&&this.setValue(!!d["default"]);
    break;
    case o:
    if(!a)continue;
    if(d.name in c)//BUG,在IE下,c永远无属性
    {
    a=c[d.name];
    f?this.setValue("true"==a.toLowerCase()):this.setValue(a);
    return
    }
    f&&this.setValue(!!d["default"]);
    break;
    case i:
    if(!b)continue;
    if(b.getAttribute(d.name))
    {
      a=b.getAttribute(d.name);
      //iwlk 加载
      if(d.name=="flashvars")
      {
        var dl=CKEDITOR.dialog.getCurrent();
        if(fv=/(CuPlayerFile|f|soundFile)=([^&]+)&/g.exec(a))
          dl.setValueOf("info","src",decodeURIComponent(decodeURIComponent(fv[2])));
        if((fv=/CuPlayerImage=([^&]+)&/g.exec(a))||(fv=/i=([^&]+)&/g.exec(a)))
          dl.setValueOf("Upload","image",decodeURIComponent(decodeURIComponent(fv[1])));
      }
      f?this.setValue("true"==a.toLowerCase()):this.setValue(a);
      return
    }
    f&&this.setValue(!!d["default"])
    }
  }
}
//a:cke:object  b:cke:embed  c:swf object
function c(a,b,c)
{
  var k=n[this.id];
  if(k)for(var f=""===this.getValue(),e=this instanceof CKEDITOR.ui.dialog.checkbox,d=0;d<k.length;d++)
  {
    var h=k[d];
	//iwlk 保存
    l=this.getValue();
	if(h.name=="src"||h.name=="movie"||h.name=="data")
	{
	  if(/(.flv|.mp4|.mov|.f4v|.3gp)$/i.test(l))l="/tea/image/flv/ckplayer.swf";
	  if(/(.mp3)$/i.test(l))l="/tea/image/flv/audio.swf";
	}
    switch(h.type)
    {
    case g://1,object属性
	if(!a||"data"==h.name&&b&&!a.hasAttribute("data"))continue;
    f||e&&l===h["default"]?a.removeAttribute(h.name):a.setAttribute(h.name,l);
    break;
    case o://2,param属性
    if(!a)continue;
    if(f||e&&l===h["default"])
	{
	  h.name in c&&c[h.name].remove();
	}else if(h.name in c)c[h.name].setAttribute("value",l);
    else
    {
      var p=CKEDITOR.dom.element.createFromHtml("<cke:param></cke:param>",a.getDocument());
  	  p.setAttributes({name:h.name,value:l});
	  1>a.getChildCount()?p.appendTo(a):p.insertBefore(a.getFirst())
    }break;
    case i://4,embed属性
    if(!b)continue;
    f||e&&l===h["default"]?b.removeAttribute(h.name):b.setAttribute(h.name,l)
    }
  }
}

for(var g=1,o=2,i=4,n={id:[{type:i,name:"id"}],classid:[{type:g,name:"classid"}],codebase:[{type:g,name:"codebase"}],pluginspage:[{type:i,name:"pluginspage"}],src:[{type:o,name:"movie"},{type:i,name:"src"},{type:g,name:"data"}],name:[{type:i,name:"name"}],align:[{type:g,name:"align"}],"class":[{type:g,name:"class"},{type:i,name:"class"}],width:[{type:g,name:"width"},{type:i,name:"width"}],height:[{type:g,name:"height"},{type:i,name:"height"}],hSpace:[{type:g,name:"hSpace"},{type:i,name:"hSpace"}],vSpace:[{type:g,name:"vSpace"},{type:i,name:"vSpace"}],style:[{type:g,
name:"style"},{type:i,name:"style"}],type:[{type:i,name:"type"}]
//iwlk
,flashvars:[{type:o,name:"flashvars"},{type:i,name:"flashvars"}]},m="play loop menu quality scale salign wmode bgcolor base flashvars allowScriptAccess allowFullScreen".split(" "),j=0;j<m.length;j++)
n[m[j]]=[{type:i,name:m[j]},{type:o,name:m[j]}];
m=["play","loop","menu"];
for(j=0;j<m.length;j++)n[m[j]][0]["default"]=n[m[j]][1]["default"]=!0;

CKEDITOR.dialog.add("flash",function(a)
{
  var g=!a.config.flashEmbedTagOnly,i=a.config.flashAddEmbedTag||a.config.flashEmbedTagOnly,k,f="<div>"+CKEDITOR.tools.htmlEncode(a.lang.common.preview)+'<br><div id="cke_FlashPreviewLoader'+CKEDITOR.tools.getNextNumber()+'" style="display:none"><div class="loading">&nbsp;</div></div><div id="cke_FlashPreviewBox'+CKEDITOR.tools.getNextNumber()+'" class="FlashPreviewBox"></div></div>';
  return{title:a.lang.flash.title,minWidth:420,minHeight:310

,onShow:function()
{
this.fakeImage=this.objectNode=this.embedNode=null;
k=new CKEDITOR.dom.element("embed",a.document);
var e=this.getSelectedElement();
if(e&&e.data("cke-real-element-type")&&"flash"==e.data("cke-real-element-type"))
{
this.fakeImage=e;var d=a.restoreRealElement(e),h=null,b=null,c={};if("cke:object"==d.getName()){h=d;d=h.getElementsByTag("embed","cke");0<d.count()&&(b=d.getItem(0));for(var d=h.getElementsByTag("param","cke"),g=0,i=d.count();g<i;g++){var f=d.getItem(g),j=f.getAttribute("name"),f=f.getAttribute("value");c[j]=f}}else"cke:embed"==d.getName()&&(b=d);this.objectNode=h;this.embedNode=b;this.setupContent(h,b,c,e)
}
}

,onOk:function()
{
var e=null,d=null,b=null;
if(this.fakeImage)
  e=this.objectNode,d=this.embedNode;
else if(g&&(e=CKEDITOR.dom.element.createFromHtml("<cke:object></cke:object>",a.document),e.setAttributes({type:"application/x-shockwave-flash",codebase:"#version=1"})),i)
  d=CKEDITOR.dom.element.createFromHtml("<cke:embed></cke:embed>",a.document),d.setAttributes({type:"application/x-shockwave-flash",pluginspage:"http://www.macromedia.com/go/getflashplayer"}),e&&d.appendTo(e);

if(e)for(var b={},c=e.getElementsByTag("param","cke"),f=0,j=c.count();f<j;f++)
b[c.getItem(f).getAttribute("name")]=c.getItem(f);
c={};
f={};
this.commitContent(e,d,b,c,f);
e=a.createFakeElement(e||d,"cke_flash","flash",!0);
e.setAttributes(f);
e.setStyles(c);
this.fakeImage?(e.replace(this.fakeImage),a.getSelection().selectElement(e)):a.insertElement(e)
}
,onHide:function(){this.preview&&this.preview.setHtml("")}

,contents:[{id:"info",label:a.lang.common.generalTab,accessKey:"I",elements:[{type:"vbox",padding:0,
children:[{type:"hbox",widths:["280px","110px"],align:"right",children:[

{id:"src",type:"text",label:a.lang.common.url,required:!0,validate:CKEDITOR.dialog.validate.notEmpty(a.lang.flash.validateSrc),setup:b,commit:c
,onLoad:function()
{
  var a=this.getDialog(),b=function(b)
  {
    k.setAttribute("src",b);
	//iwlk 预览
	var fv;
    var dl=CKEDITOR.dialog.getCurrent();
	if(/(.flv|.mp4|.mov|.f4v|.3gp)$/i.test(b))
	{
	  fv="f="+enc(b)+"&i="+enc(dl.getValueOf("Upload","image"))+"&x=%2FPlayers.do%3Fact%3Dconf";
	  b="/tea/image/flv/ckplayer.swf";
	  arr[0].onchange();
	}else if(/(.mp3)$/i.test(b))
	{
	  fv="soundFile="+enc(b)+"&bg=0x97B7C8&leftbg=0x8C0000&lefticon=0xF2F2F2&rightbg=0x3E5F7D&rightbghover=0xF9BC00&righticon=0xF2F2F2&righticonhover=0xFFFFFF&text=0x333333&slider=0x8C0000&track=0x97B7C8&border=0x97B7C8&loader=0x3E5F7D&autostart=0&loop=no";
	  b="/tea/image/flv/audio.swf";
	  //dl.setValueOf("info","width",290);
	  //dl.setValueOf("info","height",24);
	  arr[0].onchange();
	}
    a.preview.setHtml('<embed style="width:300px;height:180px" src="'+b+'" type="application/x-shockwave-flash" allowFullScreen="true" flashvars="'+fv+'"></embed>');
  };
  a.preview=a.getContentElement("info","preview").getElement().getChild(3);
  this.on("change",function(a){a.data&&a.data.value&&b(a.data.value)});
  this.getInputElement().on("change",function(){b(this.getValue())},this)
  //iwlk 拼接
  var arr=a.getElement().getElementsByTag("INPUT").$;
  for(var i=0;i<arr.length;i++)
  {
    arr[i].onchange=function()
	{
	  var dl=CKEDITOR.dialog.getCurrent();
	  //
	  var w=dl.getValueOf("info","width").trim(),h=dl.getValueOf("info","height").trim();
	  localStorage.setItem('fck.swfWidth',w);
	  localStorage.setItem('fck.swfHeight',h);

	  var src=dl.getValueOf("info","src").trim();
	  var fv=dl.getValueOf("advanced","flashvars");
	  if(/(.flv|.mp4|.mov|.f4v|.3gp)$/i.test(src))
	  {
	    fv="f="+enc(src)+"&p="+(dl.getValueOf("properties","play")?1:0)+"&e="+(dl.getValueOf("properties","loop")?1:0)+"&i="+enc(dl.getValueOf("Upload","image"))+"&x=%2FPlayers.do%3Fact%3Dconf";
	  }else if(/(.mp3)$/i.test(src))
	  {
	    fv="soundFile="+enc(src)+"&bg=0x97B7C8&leftbg=0x8C0000&lefticon=0xF2F2F2&rightbg=0x3E5F7D&rightbghover=0xF9BC00&righticon=0xF2F2F2&righticonhover=0xFFFFFF&text=0x333333&slider=0x8C0000&track=0x97B7C8&border=0x97B7C8&loader=0x3E5F7D&autostart="+(dl.getValueOf("properties","play")?"yes":"no")+"&loop="+(dl.getValueOf("properties","loop")?"yes":"no")+"";
	  }else
	    return;
	  dl.setValueOf("advanced","flashvars",fv);
	  dl.setValueOf("advanced","id","ckplayer");
	};
  }
  mt.ftr($$(a.getContentElement("Upload","upload").domId)).style.display='none';
  mt.ftr($$(a.getContentElement("Upload","uploadButton").domId)).style.display='none';
}
},
{type:"button",id:"browse",filebrowser:"info:src",hidden:!0,style:"display:inline-block;margin-top:10px;",label:a.lang.common.browseServer}]}]},{type:"hbox",widths:["25%","25%","25%","25%","25%"],children:[
{type:"text",id:"width",requiredContent:"embed[width]",style:"width:95px",label:a.lang.common.width,validate:CKEDITOR.dialog.validate.htmlLength(a.lang.common.invalidHtmlLength.replace("%1",a.lang.common.width)),setup:b,commit:c,"default":localStorage.getItem('fck.swfWidth')||"540"},
{type:"text",id:"height",requiredContent:"embed[height]",style:"width:95px",label:a.lang.common.height,validate:CKEDITOR.dialog.validate.htmlLength(a.lang.common.invalidHtmlLength.replace("%1",a.lang.common.height)),setup:b,commit:c,"default":localStorage.getItem('fck.swfHeight')||"360"},
{type:"text",id:"hSpace",requiredContent:"embed[hspace]",style:"width:95px",label:a.lang.flash.hSpace,validate:CKEDITOR.dialog.validate.integer(a.lang.flash.validateHSpace),setup:b,commit:c},
{type:"text",id:"vSpace",requiredContent:"embed[vspace]",style:"width:95px",label:a.lang.flash.vSpace,validate:CKEDITOR.dialog.validate.integer(a.lang.flash.validateVSpace),setup:b,commit:c}]},{type:"vbox",children:[{type:"html",id:"preview",style:"width:95%;",html:f}]}]}

,{id:"Upload",hidden:!0,filebrowser:"uploadButton",label:a.lang.common.upload,elements:[
{type:"file",id:"upload",label:a.lang.common.upload,size:38},
{type:"fileButton",id:"uploadButton",label:a.lang.common.uploadSubmit,filebrowser:"info:src","for":["Upload","upload"]},
//iwlk
{type:"html",html:"<div style=position:absolute>"+a.lang.common.upload+"</div><iframe src=/tea/ckeditor/plugins/CFUpload.htm?type=flash&t=1 frameborder=0 scrolling=no style=width:100%;height:59px;margin-left:-20px;></iframe>"},
{type:"text",id:"image",label:"缩略图",setup:b,commit:c}]}

,{id:"properties",label:a.lang.flash.propertiesTab,elements:[{type:"hbox",widths:["50%","50%"],children:[{id:"scale",type:"select",requiredContent:"embed[scale]",label:a.lang.flash.scale,"default":"",style:"width : 100%;",items:[[a.lang.common.notSet,""],[a.lang.flash.scaleAll,"showall"],[a.lang.flash.scaleNoBorder,"noborder"],[a.lang.flash.scaleFit,"exactfit"]],setup:b,commit:c},{id:"allowScriptAccess",type:"select",requiredContent:"embed[allowscriptaccess]",label:a.lang.flash.access,"default":"",style:"width : 100%;",
items:[[a.lang.common.notSet,""],[a.lang.flash.accessAlways,"always"],[a.lang.flash.accessSameDomain,"samedomain"],[a.lang.flash.accessNever,"never"]],setup:b,commit:c}]},{type:"hbox",widths:["50%","50%"],children:[{id:"wmode",type:"select",requiredContent:"embed[wmode]",label:a.lang.flash.windowMode,"default":"",style:"width : 100%;",items:[[a.lang.common.notSet,""],[a.lang.flash.windowModeWindow,"window"],[a.lang.flash.windowModeOpaque,"opaque"],[a.lang.flash.windowModeTransparent,"transparent"]],
setup:b,commit:c},{id:"quality",type:"select",requiredContent:"embed[quality]",label:a.lang.flash.quality,"default":"high",style:"width : 100%;",items:[[a.lang.common.notSet,""],[a.lang.flash.qualityBest,"best"],[a.lang.flash.qualityHigh,"high"],[a.lang.flash.qualityAutoHigh,"autohigh"],[a.lang.flash.qualityMedium,"medium"],[a.lang.flash.qualityAutoLow,"autolow"],[a.lang.flash.qualityLow,"low"]],setup:b,commit:c}]},{type:"hbox",widths:["50%","50%"],children:[{id:"align",type:"select",requiredContent:"object[align]",
label:a.lang.common.align,"default":"",style:"width : 100%;",items:[[a.lang.common.notSet,""],[a.lang.common.alignLeft,"left"],[a.lang.flash.alignAbsBottom,"absBottom"],[a.lang.flash.alignAbsMiddle,"absMiddle"],[a.lang.flash.alignBaseline,"baseline"],[a.lang.common.alignBottom,"bottom"],[a.lang.common.alignMiddle,"middle"],[a.lang.common.alignRight,"right"],[a.lang.flash.alignTextTop,"textTop"],[a.lang.common.alignTop,"top"]],setup:b,commit:function(a,b,f,g,i){var j=this.getValue();c.apply(this,arguments);
j&&(i.align=j)}},{type:"html",html:"<div></div>"}]},{type:"fieldset",label:CKEDITOR.tools.htmlEncode(a.lang.flash.flashvars),children:[{type:"vbox",padding:0,children:[{type:"checkbox",id:"menu",label:a.lang.flash.chkMenu,"default":!0,setup:b,commit:c},{type:"checkbox",id:"play",label:a.lang.flash.chkPlay,"default":!0,setup:b,commit:c},{type:"checkbox",id:"loop",label:a.lang.flash.chkLoop,"default":!0,setup:b,commit:c},{type:"checkbox",id:"allowFullScreen",label:a.lang.flash.chkFull,"default":!0,
setup:b,commit:c}]}]}]}

,{id:"advanced",label:a.lang.common.advancedTab,elements:[{type:"hbox",children:[
{type:"text",id:"id",requiredContent:"object[id]",label:a.lang.common.id,setup:b,commit:c}]},
{type:"hbox",widths:["45%","55%"],children:[
{type:"text",id:"bgcolor",requiredContent:"embed[bgcolor]",label:a.lang.flash.bgcolor,setup:b,commit:c},
{type:"text",id:"class",requiredContent:"embed(cke-xyz)",label:a.lang.common.cssClass,setup:b,commit:c}]},
{type:"text",id:"style",requiredContent:"embed{cke-xyz}",validate:CKEDITOR.dialog.validate.inlineStyle(a.lang.common.invalidInlineStyle),label:a.lang.common.cssStyle,setup:b,commit:c},
//iwlk
{type:"text",id:"flashvars",requiredContent:"embed(flashvars)",label:"Flash参数",setup:b,commit:c}
]}]}})})();
