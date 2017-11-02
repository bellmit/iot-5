
 var accountVue = new Vue({
	 el:'.u-panel',
	 data:{
		 online:'登录'
	 },
	 methods:{
		 setOnline:function(){
			 var _this=this
			 $.get('/account/session', function(data){
				 if('0'===data.code){
					 _this.online=data.data.account
				 }else{
					 window.location='login.html'
				 }
			 })
		 },
		 logout:function(){
			 $.post('/account/logout', function(data){
				 window.location.reload()
			 })
		 }
	 },
	 mounted:function(){
		 this.setOnline()
	 }
 })