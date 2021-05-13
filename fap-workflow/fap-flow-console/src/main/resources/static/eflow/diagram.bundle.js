(()=>{"use strict";window.ProcessDiagram=function(){},ProcessDiagram.prototype.show=function(t,r,e){this.containerId=t,r||alert("parameters can not be null.");var a=this;e||(e=""),this.contextPath=e;var n=e+"/diagram/loadDiagramData";$.ajax({url:n,data:r,type:"POST",success:function(t){a._buildDiagram(t)},error:function(t,r,e){alert("Error:"+r)}})},ProcessDiagram.prototype._buildDiagram=function(t){var r={};this.paper=new Raphael(document.getElementById(this.containerId),t.width,t.height);for(var e=t.nodeDiagrams,a=0;a<e.length;a++){var n=e[a],i=this._buildNode(n,t.showTime);r[n.name]=i}for(a=0;a<e.length;a++){var o=(n=e[a]).sequenceFlowDiagrams;if(o)for(var s=0;s<o.length;s++){var h=o[s],c=h.to,l=r[c];if(!l)throw new Error("Node "+c+" is not exist.");var u=r[n.name];if(!u)throw new Error("Node "+u+" is not exist.");this._buildConnection(u,l,h)}}},ProcessDiagram.prototype._buildConnection=function(t,r,e){var a=t.attr("x")+t.attr("width")/2,n=t.attr("y")+t.attr("height")/2,i=r.attr("x")+r.attr("width")/2,o=r.attr("y")+r.attr("height")/2,s=i,h=o,c=e.points;c&&c.length>0&&(s=c[0].x,h=c[0].y);var l="M "+a+" "+n+" L "+s+" "+h,u=this._getRectEdgePathInfo(t),g=Raphael.pathIntersection(l,u),p="M "+g[0].x+" "+g[0].y,d=[];if(d.push({x:g[0].x,y:g[0].y}),c)for(var x=0;x<c.length;x++)p+=" L "+(w=c[x]).x+" "+w.y,d.push(w);c&&c.length>0&&(a=(B=c[c.length-1]).x,n=B.y);var f="M "+a+" "+n+" L "+i+" "+o,y=this._getRectEdgePathInfo(r),v=Raphael.pathIntersection(f,y);d.push({x:v[0].x,y:v[0].y}),p+=" L "+v[0].x+" "+v[0].y;var w,b=e.labelPosition;if(b&&2==(w=b.split(",")).length){var m,I,D=d.length;if(D%2==0){var P=d[D/2-1],_=d[D/2];m=Math.abs(P.x-_.x)/2+(P.x>_.x?_.x:P.x)+parseInt(w[0]),I=Math.abs(P.y-_.y)/2+(P.y>_.y?_.y:P.y)+parseInt(w[1])}else{var B;m=(B=d[parseInt(D/2)]).x+parseInt(w[0]),I=B.y+parseInt(w[1])}var L=e.name;if(L){var E=this.paper.text(m,I,L);E.attr("font-size",parseInt(e.fontSize));var M=E.getBBox().width,C=E.getBBox().height;E.attr({x:m+M/2,y:I+C/2,fill:"rgb("+e.borderColor+")"})}}var R=this.paper.path(p);return R.attr({"arrow-end":"block-wide-long","stroke-width":e.borderWidth,stroke:"rgb("+e.borderColor+")"}),R},ProcessDiagram.prototype._getRectEdgePathInfo=function(t){var r=t.attr("x"),e=t.attr("y");return"M "+r+" "+e+" L "+(t.attr("x")+t.attr("width"))+" "+t.attr("y")+" L "+(t.attr("x")+t.attr("width"))+" "+(t.attr("y")+t.attr("height"))+" L "+t.attr("x")+"  "+(t.attr("y")+t.attr("height"))+" L "+r+"  "+e},ProcessDiagram.prototype._buildNode=function(t,r){var e=t.width,a=t.height,n=t.icon;n="/eflow"+n;var i,o,s=this.paper.rect(t.x,t.y,e,a,5),h=t.x+e/2,c=t.y+a/2,l=function(t){switch(t){case 0:return 0;case 1:return"①";case 2:return"②";case 3:return"③";case 4:return"④";case 5:return"⑤";case 6:return"⑥";case 7:return"⑦";case 8:return"⑧";case 9:return"⑨";case 10:return"⑩";case 11:return"⑪";case 12:return"⑫";case 13:return"⑬";case 14:return"⑭";case 15:return"⑮";case 16:return"⑯";case 17:return"⑰";case 18:return"⑱";case 19:return"⑲";case 20:return"⑳"}return t}(t.time),u=t.name;t.label&&t.label.length>0&&(u=t.label),s.attr("stroke-width","0"),(i=this.paper.text(h,c+16,u)).attr("font-size",parseInt(t.fontSize));var g=h-16,p=c-i.getBBox().height/2-16;return o=this.paper.image(n,g,p,32,32),0!=l&&r&&this.paper.text(t.x+e-15,t.y,l).attr("font-size","16"),i.attr({fill:"rgb("+t.fontColor+")"}),t.fontBold&&this._buildTextUnderline(i).attr("stroke","rgb("+t.fontColor+")"),t.info&&(o.attr("title",t.info),s.attr("title",t.info)),s},ProcessDiagram.prototype._buildTextUnderline=function(t){var r=t.getBBox();return this.paper.path("M"+r.x+" "+(r.y+r.height)+"L"+(r.x+r.width)+" "+(r.y+r.height))}})();