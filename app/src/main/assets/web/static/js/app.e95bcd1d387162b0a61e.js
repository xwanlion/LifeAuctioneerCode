webpackJsonp([1],{"2Tn4":function(t,e){},"4X5F":function(t,e){},F4Ug:function(t,e){},Ho9h:function(t,e){},NHnr:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n("7+uW"),i={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{attrs:{id:"app"}},[e("router-view")],1)},staticRenderFns:[]};var s=n("VU/8")({name:"App"},i,!1,function(t){n("4X5F")},null,null).exports,r=n("/ocq"),a=n("pFYg"),c=n.n(a),l=n("mw3O"),u=n.n(l),d=n("mtWM"),h=n.n(d).a.create({headers:{"Content-Type":"application/json"}}),m={name:"ToaskMessage",data:function(){return{visible:!1}},computed:{isShown:function(){return this.count%2==0}},props:{header:{type:String,default:"Title"},content:{type:String,default:"here is content"},shown:{type:Number,default:0}},methods:{autoClosed:function(){var t=this;setTimeout(function(){t.visible=!1},3e3)}},watch:{shown:function(t,e){this.visible=!0,this.autoClosed()}}},v={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{directives:[{name:"show",rawName:"v-show",value:this.visible,expression:"this.visible"}],staticClass:"alert alert-warning alert-dismissible fade show",attrs:{role:"alert",id:"iToask"}},[this._v("\n  "+this._s(this.content)+"\n  "),e("button",{staticClass:"btn-close",attrs:{type:"button","data-bs-dismiss":"alert","aria-label":"Close"}})])},staticRenderFns:[]};var g=n("VU/8")(m,v,!1,function(t){n("h7Ht")},"data-v-41ec9208",null).exports,f={name:"MyDream",data:function(){return{lotsArray:[{index:0,gitem:"my_dream_0",g_options_tag:"g0",gprice:"my_price_0"},{index:1,gitem:"my_dream_1",g_options_tag:"g1",gprice:"my_price_1"},{index:2,gitem:"my_dream_2",g_options_tag:"g2",gprice:"my_price_2"}],lotsList:[],errorContent:"got error when query lots!",showError:!1,showCount:0,activity:{}}},methods:{changeSelect:function(t){if(0===t){document.getElementById(this.lotsArray[1].gitem).value=null,document.getElementById(this.lotsArray[2].gitem).value=null,this.showElement(this.lotsArray[1].g_options_tag),this.showElement(this.lotsArray[2].g_options_tag);var e=document.getElementById(this.lotsArray[0].gitem).value,n=document.getElementById(this.lotsArray[1].gitem).value,o=document.getElementById(this.lotsArray[2].gitem).value;this.hideSelection(this.lotsArray[1].g_options_tag,e,o),this.hideSelection(this.lotsArray[2].g_options_tag,e,n)}else if(1===t){document.getElementById(this.lotsArray[2].gitem).value=null,this.showElement(this.lotsArray[2].g_options_tag);var i=document.getElementById(this.lotsArray[0].gitem).value,s=document.getElementById(this.lotsArray[1].gitem).value;this.hideSelection(this.lotsArray[2].g_options_tag,i,s)}},getActivity:function(){var t=this;h.post("/api/activity/get").then(function(e){if(0!==e.data.code)return t.errorContent="get activity faile",void t.showCount++;t.activity=e.data.data,t.$commonUtil.saveActivity(t.activity),t.listLots(t.activity.id)}).catch(function(e){console.log(e),t.errorContent="get activity status faile",t.showCount++})},listLots:function(t){var e=this;h.post("/api/lot/list/"+t).then(function(t){0!==t.data.code?(e.errorContent="got error when query lots!",e.showCount++):(e.lotsList=u.a.parse(t.data.data),e.$commonUtil.saveLots(e.lotsList),setTimeout(function(){e.changeSelect(0)},1e3))}).catch(function(t){console.log(t),e.errorContent="got error when query lots!",e.showCount++})},showElement:function(t){var e=document.getElementsByName(t);for(var n in e){var o=e[n];"object"===(void 0===o?"undefined":c()(o))&&(o.style.display="block")}},hideSelection:function(t,e,n){this.hideSelected(t,e),this.hideSelected(t,n)},hideSelected:function(t,e){if(void 0!==t&&void 0!==e){var n=document.getElementsByName(t);for(var o in n){var i=n[o];"object"===(void 0===i?"undefined":c()(i))&&(i.value===e&&(i.style.display="none"))}}},toSubmit:function(){var t=[];for(var e in this.lotsArray){var n=this.getBidderExpectingLot(e);if(null!=n){if(null===n.lotId||0===n.lotId.length||0===n.lotId)break;if(null===n.expectingPrice||0===n.expectingPrice.length)return this.errorContent="A dream's expecting price cannot be null",void this.showCount++;if(isNaN(n.expectingPrice-0))return this.errorContent="A dream's expecting price must be a number",void this.showCount++;if(n.expectingPrice-0<0)return this.errorContent="A dream's expecting price cannot less than zero",void this.showCount++;t.push(n)}}if(console.log("lots:"),console.log(t),void 0===t||0===t.length||void 0===t[0].lotId||0===t[0].lotId.length)return this.errorContent="you must choose a dream",void this.showCount++;var o={},i=this.$commonUtil.getUser();o.bidderId=i.id,o.lots=this.lotArrToString(t),console.log("lots Json:"),console.log(o.lots);var s=this;h.post("/api/lot/new/expectLots",u.a.stringify(o),{headers:{"Content-Type":"application/x-www-form-urlencoded"}}).then(function(t){if(0!==t.data.code)return s.errorContent="save your dream faile!",void s.showCount++;var e=s.$commonUtil.getNextPageKey("myDream");Z.replace({path:e,query:{money:s.activity.money,moneyCreationWay:s.activity.moneyCreationWay,amountPerAge:s.activity.amountPerAge}})}).catch(function(t){console.log(t),s.errorContent="save your dream faile!",s.showCount++})},getBidderExpectingLot:function(t){if(t<0)return null;var e={};return e.lotId=document.getElementById(this.lotsArray[t].gitem).value-0,e.expectingPrice=document.getElementById(this.lotsArray[t].gprice).value-0,e},lotArrToString:function(t){if(void 0===t||null===t||0===t.length)return"";var e="";for(var n in t)e+=t[n].lotId+","+t[n].expectingPrice+";";return e}},components:{ToaskMessage:g},mounted:function(){this.getActivity()}},p={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"card card-dream card-container"},[t._m(0),t._v(" "),n("div",{staticClass:"card-body"},t._l(t.lotsArray,function(e){return n("div",{key:e.g_options_tag,staticClass:"row dream-item"},[n("select",{staticClass:"form-select select-expecting-item col col-30",attrs:{"aria-label":"Default select",id:e.gitem},on:{change:function(n){return t.changeSelect(e.index)}}},t._l(t.lotsList,function(o){return n("option",{key:o.id,attrs:{name:e.g_options_tag},domProps:{value:o.id}},[t._v(t._s(o.name))])}),0),t._v(" "),n("input",{staticClass:"col col-60 txt-expecting-price form-control",attrs:{id:e.gprice,type:"number",name:e.gprice,value:"",min:"0",step:"1","data-bind":"value:replyNumber",placeholder:"expecting price"}})])}),0),t._v(" "),n("ToaskMessage",{attrs:{content:this.errorContent,shown:this.showCount}}),t._v(" "),n("div",{staticClass:"card-footer footer-container"},[n("button",{staticClass:"btn btn-primary btn-next",attrs:{type:"button",name:"button",id:"btn_dream_to_next"},on:{click:this.toSubmit}},[t._v("NEXT")])])],1)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"card-header"},[e("span",{staticClass:"dream-tips"},[this._v("choose 3 item for your dream")])])}]};var y=n("VU/8")(f,p,!1,function(t){n("RDWL")},"data-v-3048e577",null).exports,_={name:"ReceiveMoney",data:function(){return{errorContent:"query data error",showCount:0,money:0,amountPerAge:0,moneyCreationWay:0}},methods:{toNext:function(){var t=this;if("0"===this.moneyCreationWay){var e=t.$commonUtil.getUser();t.saveBidderMoney(e.id,function(){t.toNextPage()})}else t.toNextPage()},saveBidderMoney:function(t,e){var n=this,o={};o.bidderId=t,h.post("/api/bidderMoney/create",u.a.stringify(o),{headers:{"Content-Type":"application/x-www-form-urlencoded"}}).then(function(t){0!==t.data.code?(n.errorContent="create money faile",n.showCount++):e()}).catch(function(t){console.log(t),n.errorContent="create money faile",n.showCount++})},toNextPage:function(){var t=this.$commonUtil.getNextPageKey("receiveMoney",this.moneyCreationWay);Z.replace({path:t,query:{money:this.money,amountPerAge:this.amountPerAge}})}},components:{ToaskMessage:g},mounted:function(){var t=this;this.money=t.$route.query.money,this.amountPerAge=t.$route.query.amountPerAge,this.moneyCreationWay=t.$route.query.moneyCreationWay,setTimeout(function(){t.toNext()},2e3)}},C={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"card  card-container",attrs:{id:"receive_money"}},[this._m(0),this._v(" "),e("ToaskMessage",{attrs:{content:this.errorContent,shown:this.showCount}})],1)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"card-body"},[e("p",[this._v("wait a minute please")])])}]};var b=n("VU/8")(_,C,!1,function(t){n("TGf1")},"data-v-dd2fe7ae",null).exports,w={name:"ReceiveMoney1",data:function(){return{age:30,errorContent:"get your money faile",showCount:0}},methods:{toNext:function(){var t=this;if(!1!==t.checkAge()){var e=this.$commonUtil.getUser(),n={};n.bidderId=e.id,n.age=document.getElementById("txt_age").value,h.post("/api/bidderMoney/create2",u.a.stringify(n),{headers:{"Content-Type":"application/x-www-form-urlencoded"}}).then(function(e){if(0!==e.data.code)return t.errorContent="create money faile",void t.showCount++;var n=t.$commonUtil.getNextPageKey("receiveMoneyByAge");Z.replace({path:n,query:{money:e.data.data}})}).catch(function(e){console.log(e),t.errorContent="create money faile",t.showCount++})}},checkAge:function(){var t=document.getElementById("txt_age").value-0;return 0===t||isNaN(t)?(this.errorContent="age must be a number",this.showCount++,!1):!(t<0)||(this.errorContent="age must be greater than zero",this.showCount++,!1)}},components:{ToaskMessage:g}},x={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"card  card-container",attrs:{id:"receive_money"}},[t._m(0),t._v(" "),n("div",{staticClass:"card-body"},[n("p",[t._v("the years number of rest of your lifetime:")]),t._v(" "),n("input",{staticClass:"form-control txt-age",attrs:{id:"txt_age",type:"number",step:"1",min:"0",name:"",value:"30"},on:{blur:t.checkAge}})]),t._v(" "),n("ToaskMessage",{attrs:{content:this.errorContent,shown:this.showCount}}),t._v(" "),n("div",{staticClass:"card-footer footer-container"},[n("button",{staticClass:"btn btn-primary btn-next",attrs:{type:"button",name:"button",id:"btn_money_to_next"},on:{click:this.toNext}},[t._v("NEXT")])])],1)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"card-header"},[e("h1",[this._v("Rest Of Your Lifetime")])])}]};var T=n("VU/8")(w,x,!1,function(t){n("Ho9h")},"data-v-296fab0b",null).exports,A={name:"ReceiveMoney2",data:function(){return{amount:0,errorContent:"get your money faile",showCount:0}},props:{money:{type:Number,default:0}},methods:{getMoney:function(){var t=this.$route.query.money;if(void 0===t||0===t.length)return this.errorContent="get your money faile",void this.showCount++;this.amount=t},toNext:function(){var t=this;h.post("/api/activity/get").then(function(e){if(console.log(e),0!==e.data.code)return t.errorContent="get activity status faile",void t.showCount++;if(e.data.data.state<4)return t.errorContent=" waiting for begining, you can tell auctioneer!",t.showCount++,void console.log("show error ok");t.$commonUtil.saveActivity(e.data.data);var n=t.$commonUtil.getNextPageKey("receiveMoneyByFixedAmount");Z.replace({path:n})}).catch(function(e){console.log(e),t.errorContent="get activity status faile",t.showCount++})}},components:{ToaskMessage:g},mounted:function(){this.getMoney()}},U={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"card  card-container",attrs:{id:"receive_money"}},[t._m(0),t._v(" "),n("div",{staticClass:"card-body money_tips"},[n("h1",[t._v("you got "),n("strong",[t._v(" $"+t._s(this.amount))])]),t._v(" "),n("p",[t._v("It represent All your time and energy")]),t._v(" "),n("p",[t._v("You will use it in the auction activity")])]),t._v(" "),n("ToaskMessage",{attrs:{content:this.errorContent,shown:this.showCount}}),t._v(" "),n("div",{staticClass:"card-footer footer-container"},[n("button",{staticClass:"btn btn-primary btn-next",attrs:{type:"button",name:"button",id:"btn_money_to_next"},on:{click:this.toNext}},[t._v("NEXT")])])],1)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"card-header"},[e("h1",[this._v("your money")])])}]};var E=n("VU/8")(A,U,!1,function(t){n("Wz4U")},"data-v-58528be8",null).exports,k={name:"Testimonial",data:function(){return{errorContent:"netword error",showCount:0}},methods:{toSubmit:function(){var t=document.getElementById("txt_testimonials").value;if(null!=t&&0!==t.length){var e=this.$commonUtil.getUser(),n=this.$commonUtil.getActivity(),o={};o.activityId=n.id,o.bidderId=e.id,o.content=t;var i=this;h.post("/api/testimonials/new",u.a.stringify(o),{headers:{"Content-Type":"application/x-www-form-urlencoded"}}).then(function(t){if(0!==t.data.code)return i.errorContent=t.data.msg,void i.showCount++;var e=i.$commonUtil.getNextPageKey("testimonial");Z.replace({path:e})}).catch(function(t){console.log(t),i.errorContent="occur error when post data",i.showCount++})}else document.getElementById("txt_testimonials").focus()}},components:{ToaskMessage:g},mounted:function(){}},S={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"card  card-container",attrs:{id:"auction_lots"}},[e("div",{staticClass:"card-header"},[this._v("\n    Summary\n  ")]),this._v(" "),this._m(0),this._v(" "),e("ToaskMessage",{attrs:{content:this.errorContent,shown:this.showCount}}),this._v(" "),e("div",{staticClass:"card-footer footer-container"},[e("button",{staticClass:"btn btn-primary",attrs:{type:"button",name:"button",id:"btn_testimonials_submit"},on:{click:this.toSubmit}},[this._v("SUBMIT")])])],1)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"card-body"},[e("div",{staticClass:"form-floating testimonials-form"},[e("textarea",{staticClass:"form-control testimonials-content",attrs:{placeholder:"Leave a comment here",id:"txt_testimonials"}}),this._v(" "),e("label",{attrs:{for:"floatingTextarea"}},[this._v("what were you expected and what's the result?")])])])}]};var I=n("VU/8")(k,S,!1,function(t){n("2Tn4")},"data-v-5bdc28d7",null).exports,N={name:"Rules",data:function(){return{content:"reuqest data error!",errorCount:0,button_text:"START AUCTION"}},methods:{getActivity:function(){var t=this;h.post("/api/activity/get").then(function(e){if(0!==e.data.code)return t.errorContent="get activity faile",void t.showCount++;t.activity=e.data.data,t.$commonUtil.saveActivity(t.activity),t.listLots(t.activity.id)}).catch(function(e){console.log(e),t.errorContent="get activity status faile",t.showCount++})},listLots:function(t){var e=this;h.post("/api/lot/list/"+t).then(function(t){0!==t.data.code?(e.errorContent="got error when query lots!",e.showCount++):(e.lotsList=u.a.parse(t.data.data),e.$commonUtil.saveLots(e.lotsList))}).catch(function(t){console.log(t),e.errorContent="got error when query lots!",e.showCount++})},toNext:function(){var t=this.$commonUtil.getLot(0);if(null===t)return this.content="Lot's data not loaded!",void this.errorCount++;var e=this.$commonUtil.getUserSession();if(void 0===e||0===e.length)return this.content="please login first!",void this.errorCount++;var n=this;h.get("/api/lot/point/"+t.id,{headers:{JSESSIONID:e}}).then(function(t){if(0!==t.data.code)return n.content=t.data.msg,void n.errorCount++;var e=n.$commonUtil.getNextPageKey("rules");Z.replace({path:e})}).catch(function(t){console.log(t),n.content="starting activity occur error",n.errorCount++})}},components:{ToaskMessage:g},mounted:function(){this.getActivity()}},L={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"card login-card card-container"},[e("h5",{staticClass:"card-header login-card-header"},[this._v("Activity Rules")]),this._v(" "),this._m(0),this._v(" "),e("ToaskMessage",{attrs:{content:this.content,shown:this.errorCount}}),this._v(" "),e("div",{staticClass:"card-footer footer-container"},[e("button",{staticClass:"btn btn-primary btn-rule-next",attrs:{type:"submit"},on:{click:this.toNext}},[this._v(this._s(this.button_text))])])],1)},staticRenderFns:[function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"card-body"},[n("ul",{staticClass:"rules-content"},[n("li",[t._v("highest offer win the lot")]),n("br"),t._v(" "),n("li",[t._v("each lot has own starting price and increments, you must offer amount that greater than it,  formula is:")]),n("br"),t._v(" "),n("p",[n("strong",[t._v("offer = starting + N * increments")])]),t._v(" "),n("br"),t._v(" "),n("li",[t._v("auctioneer decide the order of lots")]),n("br")])])}]};var $=n("VU/8")(N,L,!1,function(t){n("Owwz")},"data-v-034d3480",null).exports,M={name:"Lots",data:function(){return{content:"reuqest data error!",errorCount:0,lotsList:[]}},methods:{listLots:function(t){var e=this;h.post("/api/lot/list/"+t).then(function(t){0!==t.data.code?(e.errorContent="got error when query lots!",e.showCount++):(e.lotsList=u.a.parse(t.data.data),e.$commonUtil.saveLots(e.lotsList))}).catch(function(t){console.log(t),e.errorContent="got error when query lots!",e.showCount++})},toNext:function(){var t=this.$commonUtil.getNextPageKey("lots");Z.replace({path:t})}},components:{ToaskMessage:g},mounted:function(){var t=this.$commonUtil.getActivity();this.listLots(t.id)}},O={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"card login-card card-container"},[n("h5",{staticClass:"card-header login-card-header"},[t._v("Auction Lots")]),t._v(" "),n("div",{staticClass:"card-body"},[n("ul",{staticClass:"list-group"},t._l(t.lotsList,function(e){return n("li",{key:e.id,staticClass:"list-group-item d-flex justify-content-between align-items-center lots-item"},[n("img",{staticClass:"col-4 lots-item-image",attrs:{src:e.imageFile,alt:"item picture"}}),t._v(" "),n("ul",{staticClass:"col-8 lots-item-info"},[n("li",{staticClass:"row lots-item-name"},[t._v(t._s(e.name))]),t._v(" "),n("li",{staticClass:"row"},[n("div",{staticClass:"col-12"},[n("span",{staticClass:"lots-item-price"},[t._v("$"+t._s(e.purchasePrice))]),t._v(" "),n("span",[t._v(t._s(e.buyer)+" ")]),t._v(" "),null!==e.buyer&&e.buyer.length>0?n("span",[t._v("got it")]):t._e()])])])])}),0)]),t._v(" "),n("ToaskMessage",{attrs:{content:this.content,shown:this.errorCount}}),t._v(" "),n("div",{staticClass:"card-footer footer-container"},[n("button",{staticClass:"btn btn-primary btn-rule-next",attrs:{type:"submit"},on:{click:this.toNext}},[t._v("NEXT")])])],1)},staticRenderFns:[]};var B=n("VU/8")(M,O,!1,function(t){n("Z7w2")},"data-v-7cd8b178",null).exports,P={name:"testimonials",data:function(){return{errorContent:"reuqest data error!",showCount:0,testimonials:[]}},methods:{listTestimonials:function(){var t=this,e=t.$commonUtil.getActivity();if(null===e)return t.errorContent="activity data did not loaded",void t.showCount++;h.get("/api/testimonials/list/"+e.id).then(function(e){0!==e.data.code?(t.errorContent="got error when query testimonials!",t.showCount++):t.testimonials=u.a.parse(e.data.data)}).catch(function(e){console.log(e),t.errorContent="got error when query testimonials!",t.showCount++})},toNext:function(){var t=this.$commonUtil.getNextPageKey("testimonials");Z.replace({path:t})}},components:{ToaskMessage:g},mounted:function(){this.listTestimonials()}},R={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"card card-container"},[n("h5",{staticClass:"card-header"},[t._v("Activity Testimonials")]),t._v(" "),n("div",{staticClass:"card-body"},[n("div",{staticClass:"testi-container"},t._l(t.testimonials,function(e){return n("div",{key:e.id,staticClass:"row testi-item"},[n("span",{staticClass:"col-xs-9 col-sm-10 testi-item-content"},[t._v(t._s(e.content))]),t._v(" "),n("span",{staticClass:"col-xs-3 col-sm-2  testi-item-bidder"},[t._v(t._s(e.bidder))])])}),0)]),t._v(" "),n("ToaskMessage",{attrs:{content:this.errorContent,shown:this.showCount}}),t._v(" "),n("div",{staticClass:"card-footer footer-container"},[n("button",{staticClass:"btn btn-primary btn-rule-next",attrs:{type:"submit"},on:{click:this.toNext}},[t._v("NEXT")])])],1)},staticRenderFns:[]};var W=n("VU/8")(P,R,!1,function(t){n("gbbp")},"data-v-a7c554fa",null).exports,F={name:"Welcome",data:function(){return{content:"reuqest data error!",errorCount:0}},methods:{needToLogin:function(){var t=this;h.get("/api/activity/needLogin").then(function(t){!0===t.data.data.needLogin?Z.replace({path:"/login"}):Z.replace({path:"/myDream"})}).catch(function(e){console.log(e),t.errorCount++})}},components:{ToaskMessage:g},mounted:function(){var t=this;setTimeout(function(){t.needToLogin()},3e3)}},K={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{attrs:{id:"home"}},[e("h1",[this._v("to find your dream, find your future")]),this._v(" "),e("ToaskMessage",{attrs:{content:this.content,shown:this.errorCount}})],1)},staticRenderFns:[]};var q=n("VU/8")(F,K,!1,function(t){n("OPYA")},"data-v-77d20cbd",null).exports,Y={name:"Login",data:function(){return{content:"reuqest data error",errorCount:0}},methods:{toSubmit:function(){var t={};if(t.username=document.getElementById("txt_account").value,t.password=document.getElementById("txt_password").value,null===t.username||0===t.username.length)return this.content="username cannot be null",this.errorCount++,void document.getElementById("txt_account").focus();var e=this;h.post("/api/user/login",u.a.stringify(t),{headers:{"Content-Type":"application/x-www-form-urlencoded"}}).then(function(t){if(0!==t.data.code)return e.content="login faile!",void e.errorCount++;var n=t.headers.jsessionid;e.$commonUtil.saveUser(t.data.data),e.$commonUtil.saveUserSession(n);var o=e.$commonUtil.getNextPageKey("login");Z.replace({path:o})}).catch(function(t){console.log(t),e.content="post submit data occur error",e.errorCount++})}},components:{ToaskMessage:g}},V={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"card login-card card-container"},[e("h5",{staticClass:"card-header login-card-header"},[this._v("login please")]),this._v(" "),this._m(0),this._v(" "),e("ToaskMessage",{attrs:{content:this.content,shown:this.errorCount}}),this._v(" "),e("div",{staticClass:"card-footer footer-container"},[e("button",{staticClass:"btn btn-primary btn-login",attrs:{type:"submit"},on:{click:this.toSubmit}},[this._v("LOGIN")])])],1)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"card-body"},[e("div",{staticClass:"input-group mb-3"},[e("i",{staticClass:"bi bi-file-person-fill input-group-text",attrs:{id:"login-account"}},[e("span",{staticClass:"required"},[this._v("*")])]),this._v(" "),e("input",{staticClass:"form-control",attrs:{id:"txt_account",type:"text",placeholder:"username","aria-label":"Username","aria-describedby":"login-account"}})]),this._v(" "),e("div",{staticClass:"input-group mb-3"},[e("i",{staticClass:"bi bi-file-lock-fill input-group-text",attrs:{id:"login-password"}},[this._v(" ")]),this._v(" "),e("input",{staticClass:"form-control",attrs:{id:"txt_password",type:"password",placeholder:"password","aria-label":"Password","aria-describedby":"login-password"}})])])}]};var D=n("VU/8")(Y,V,!1,function(t){n("sPYf")},"data-v-322827c4",null).exports,j=n("vVTm"),H=n.n(j),J={name:"AuctionLots",data:function(){return{errorContent:"netword error",showCount:0,pic:H.a,lot:{id:0,name:"the best wife in the world",startPrice:156e3,increment:1e3,imageFile:""},bidList:[{id:0,name:"赵海米",amount:16e5}],bidderOfferPrice:0,activity:{},lotIndex:0}},computed:{isBidder:function(){var t=this.$commonUtil.getUser();return 1===t.userType||"1"===t.userType}},methods:{toOffer:function(){var t=document.getElementById("txt_offer_price").value-0;if(isNaN(t)||t<this.lot.startPrice)return this.errorContent="invalid input",void this.showCount++;var e=this.$commonUtil.getUser(),n=this,o={};o.activityId=this.activity.id,o.bidderId=e.id,o.lotId=this.lot.id,o.price=t,h.post("/api/bidLog/new",u.a.stringify(o),{headers:{"Content-Type":"application/x-www-form-urlencoded"}}).then(function(t){n.errorContent="bid success",0!==t.data.code&&(n.errorContent=t.data.msg),n.showCount++}).catch(function(t){console.log(t),n.errorContent="occur error when post offer price",n.showCount++})},toNext:function(){var t=this.lotIndex+1,e=this.$commonUtil.getLot(t);if(null===e||void 0===e){var n=this.$commonUtil.listLots();if(null!==n&&(void 0===n.length||n.length<=t)){var o=this.$commonUtil.getNextPageKey("auction");return void Z.replace({path:o})}return this.errorContent="Lot's data not loaded",void this.showCount++}var i=this;h.get("/api/lot/point/"+e.id).then(function(e){if(0!==e.data.code)return i.errorContent=e.data.msg,void i.showCount++;i.lotIndex=t,i.onChangeLot(i.lotIndex)}).catch(function(t){console.log(t),i.errorContent="get next Lot's data occur error",i.showCount++})},increment:function(){var t=document.getElementById("txt_offer_price").value-0;if(isNaN(t)||t<this.lot.startPrice)return this.errorContent="invalid input",void this.showCount++;this.bidderOfferPrice=this.bidderOfferPrice+this.lot.increment},initData:function(){this.activity=this.$commonUtil.getActivity(),this.onChangeLot(this.lotIndex),this.bidList=[],this.$messageUtils.init(),this.$messageUtils.onChangeLot=this.onChangeLot,this.$messageUtils.onAuctionOver=this.onAuctionOver},onChangeLot:function(t){console.log("onChangeLot index:",t),this.lot=this.$commonUtil.getLot(t),console.log("onChangeLot:",this.lot),null==this.lot&&(this.lot={}),this.pic=this.lot.imageFile,this.bidderOfferPrice=this.lot.startPrice+this.lot.increment},onAuctionOver:function(){var t=this.$commonUtil.getNextPageKey("auction");Z.replace({path:t})}},components:{ToaskMessage:g},mounted:function(){this.initData()}},X={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"card  card-container",attrs:{id:"auction_lots"}},[n("div",{staticClass:"lots-img",style:{backgroundImage:"url("+t.pic+")"},attrs:{id:"lots-image"}}),t._v(" "),n("ul",{staticClass:"bid-list"},t._l(t.bidList,function(e){return n("li",{key:e.id},[t._v(t._s(e.name)+": "+t._s(e.amount))])}),0),t._v(" "),n("div",{staticClass:"card-body"},[n("div",{staticClass:"row"},[n("div",{staticClass:"col col-7 auction-item"},[n("div",{staticClass:"row lot-item-name"},[n("strong",[t._v(t._s(this.lot.name))])]),t._v(" "),n("div",{staticClass:"row"},[n("div",{staticClass:"col-6"},[n("span",{staticClass:"d-none d-sm-block"},[t._v("Start:")]),t._v(t._s(this.lot.startPrice))]),t._v(" "),n("div",{staticClass:"col-6"},[n("span",{staticClass:"d-none d-sm-block"},[t._v("increment:")]),t._v(" "+t._s(this.lot.increment))])])]),t._v(" "),this.isBidder?n("div",{staticClass:"col col-5 btn-group auction-action"},[n("input",{staticClass:"form-control",attrs:{type:"number",id:"txt_offer_price",min:this.lot.startPrice,step:this.lot.increment},domProps:{value:this.bidderOfferPrice}}),t._v(" "),n("button",{staticClass:"btn btn-dark",attrs:{type:"button",id:"btn-incremet"},on:{click:t.increment}},[t._v("+")])]):t._e()])]),t._v(" "),n("ToaskMessage",{attrs:{content:this.errorContent,shown:this.showCount}}),t._v(" "),n("div",{staticClass:"card-footer footer-container"},[this.isBidder?n("button",{staticClass:"btn btn-primary btn-next",attrs:{type:"button",name:"button",id:"btn_auction_offer"},on:{click:this.toOffer}},[t._v("OFFER")]):n("button",{staticClass:"btn btn-primary btn-next",attrs:{type:"button",name:"button",id:"btn_auction_next"},on:{click:this.toNext}},[t._v("NEXT")])])],1)},staticRenderFns:[]};var z=n("VU/8")(J,X,!1,function(t){n("yDhl")},"data-v-bc8c7974",null).exports,G={render:function(){this.$createElement;this._self._c;return this._m(0)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"card-container",attrs:{id:"over"}},[e("h1",[this._v("Thanks, Bye!")])])}]};var Q=n("VU/8")({name:"AuctionLots",data:function(){return{}},methods:{},components:{},mounted:function(){}},G,!1,function(t){n("r8Ed")},"data-v-19b61c15",null).exports;o.a.use(r.a);var Z=new r.a({routes:[{path:"/",name:"welcome",component:q},{path:"/login",name:"login",component:D},{path:"/myDream",name:"MyDream",component:y},{path:"/receiveMoney",name:"ReceiveMoney",component:b},{path:"/receiveMoneyByAge",name:"ReceiveMoneyByAge",component:T},{path:"/receiveMoneyByFixedAmount",name:"ReceiveMoneyByFixedAmount",component:E},{path:"/auction",name:"Auction",component:z},{path:"/testimonial",name:"Testimonial",component:I},{path:"/over",name:"Over",component:Q},{path:"/rules",name:"Rules",component:$},{path:"/lots",name:"Lots",component:B},{path:"/testimonials",name:"Testimonials",component:W}]}),tt=(n("jW8Q"),n("qb6w"),n("F4Ug"),n("Q0/0"),n("mvHQ")),et=n.n(tt),nt={USER_KEY:"USER_KEY",USER_SISSION_KEY:"JSESSIONID",ACTIVITY_KEY:"ACTIVITY_KEY",LOTS_KEY:"LOTS_KEY",getUser:function(){var t=localStorage.getItem(this.USER_KEY);return null===t||"undefined"===t?null:JSON.parse(t)},saveUser:function(t){localStorage.setItem(this.USER_KEY,et()(t))},saveUserSession:function(t){localStorage.setItem(this.USER_SISSION_KEY,t)},getUserSession:function(){return localStorage.getItem(this.USER_SISSION_KEY)},getActivity:function(){var t=localStorage.getItem(this.ACTIVITY_KEY);return null===t||"undefined"===t?null:JSON.parse(t)},saveActivity:function(t){localStorage.setItem(this.ACTIVITY_KEY,et()(t))},getLot:function(t){var e=this.listLots();return null===e?null:0===e.length?null:e[t]},listLots:function(){var t=localStorage.getItem(this.LOTS_KEY);return null===t||"undefined"===t?null:JSON.parse(t)},saveLots:function(t){localStorage.setItem(this.LOTS_KEY,et()(t))},getNextPageKey:function(t,e){if(null===t||0===t.length)return"/Welcome";var n=this.getUser();if(null===n||0===n.length)return"/Welcome";if("1"===n.userType||1===n.userType){if(t.endsWith("login"))return"/myDream";if(t.endsWith("myDream"))return"/receiveMoney";if(t.endsWith("receiveMoney")&&"0"===e)return"/receiveMoneyByFixedAmount";if(t.endsWith("receiveMoney")&&"1"===e)return"/receiveMoneyByAge";if(t.endsWith("receiveMoneyByAge"))return"/receiveMoneyByFixedAmount";if(t.endsWith("receiveMoneyByFixedAmount"))return"/auction";if(t.endsWith("auction"))return"/testimonial";if(t.endsWith("testimonial"))return"/over"}else if("2"===n.userType||2===n.userType){if(t.endsWith("login"))return"/rules";if(t.endsWith("rules"))return"/auction";if(t.endsWith("auction"))return"/lots";if(t.endsWith("lots"))return"/testimonials";if(t.endsWith("testimonials"))return"/over"}return"/Welcome"}},ot={socket:null,messages:[],lockConnection:!1,socketTimeoutObject:null,getUrl:function(){return"ws://"+window.location.host.split(":")[0]+":8886/"},init:function(){var t=this;t.socket.onopen=function(){null!=t.messages&&t.messages.length>0&&t.resend(),t.heartBit.check()},t.socket.onclose=function(){t.reconnect(t.getUrl())},t.socket.onmessage=function(e){t.messageArrival(e.data),t.heartBit.check()},t.socket.onerror=function(){t.reconnect(t.getUrl())}},messageArrival:function(t){},reconnect:function(t){var e=this;e.lockConnection||(e.lockConnection=!0,clearTimeout(e.socketTimeoutObject),e.socketTimeoutObject=setTimeout(function(){e.createSocket(),e.lockConnection=!1},4e3))},heartBit:{timeout:3e3,pingTimeoutObject:null,closeTimeoutObject:null,check:function(){var t=this;clearTimeout(this.pingTimeoutObject),clearTimeout(this.closeTimeoutObject);var e=function(){ot.socket.close()};this.pingTimeoutObject=setTimeout(function(){ot.send(rt.generateHeartBeatMessage()),t.closeTimeoutObject=setTimeout(e,t.timeout)},t.timeout)}},send:function(t){1===this.socket.readyState?this.socket.send(t):this.messages.push(t)},resend:function(){var t=this.messages;this.messages=[];for(var e=0;e<t.length;e++)this.send(t[e])},createSocket:function(){try{this.socket=new WebSocket(this.getUrl()),this.init()}catch(t){this.reconnect(this.getUrl())}}},it=ot,st={HEART_BEAT:0,CHANGE_LOT:1,AUCTION_OVER:1,onMessageArrival:function(t){},onChangeLot:function(t){},onAuctionOver:function(){},init:function(){var t=this;it.createSocket(),it.messageArrival=function(e){t.onMessageArrival(e);var n=JSON.parse(e);n.msgType===st.CHANGE_LOT?t.onChangeLot(n.text):n.msgType===st.AUCTION_OVER&&t.onAuctionOver()}},send:function(t){var e=et()(t);it.send(e)},sendMessage:function(t,e,n,o){var i=(new Date).getTime(),s=this.generateMessage(i,t,e,n,o);this.send(s)},testNextLotMessage:function(t){var e=(new Date).getTime(),n=this.CHANGE_LOT,o=nt.getUserSession(),i=this.generateMessage(e,n,o,0,t);console.log("send test message",i),this.send(i)},testAuctionOverMessage:function(t){var e=(new Date).getTime(),n=this.AUCTION_OVER,o=nt.getUserSession(),i=this.generateMessage(e,n,o,0,t);console.log("send test message",i),this.send(i)},generateHeartBeatMessage:function(){var t=nt.getUser(),e=(new Date).getTime(),n=t.userType,o=nt.getUserSession(),i=this.generateMessage(e,this.HEART_BEAT,o,n,"");return et()(i)},generateMessage:function(t,e,n,o,i){var s={};return s.id=t,s.msgType=e,s.senderSessionId=n,s.senderType=o,s.text=i,s}},rt=st;o.a.prototype.$commonUtil=nt,o.a.prototype.$messageUtils=rt,window.$messageUtils=rt,new o.a({el:"#app",router:Z,components:{App:s},template:"<App/>"})},OPYA:function(t,e){},Owwz:function(t,e){},"Q0/0":function(t,e){},RDWL:function(t,e){},TGf1:function(t,e){},Wz4U:function(t,e){},Z7w2:function(t,e){},gbbp:function(t,e){},h7Ht:function(t,e){},qb6w:function(t,e){},r8Ed:function(t,e){},sPYf:function(t,e){},vVTm:function(t,e,n){t.exports=n.p+"static/img/zyt.d44f699.jpg"},yDhl:function(t,e){}},["NHnr"]);
//# sourceMappingURL=app.e95bcd1d387162b0a61e.js.map