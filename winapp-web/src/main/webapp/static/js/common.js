//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//请求前缀
var baseURL = "/";

//登录token
var token = localStorage.getItem("token");
if(token == 'null'){
    parent.location.href = baseURL + 'login.html';
}

//jquery全局配置
$.ajaxSetup({
    dataType: "json",
    cache: false,
    headers: {
        "token": token
    },
    // xhrFields: {
    //     withCredentials: true
    // },
    complete: function(xhr) {
        //token过期，则跳转到登录页面
        // if(xhr.responseJSON.code == 401){
        //     parent.location.href = baseURL + 'login.html';
        // }
    }
});

//jqgrid全局配置
$.extend($.jgrid.defaults, {
    ajaxGridOptions : {
        headers: {
            "token": token
        }
    }
});

//权限判断
function hasPermission(permission) {
    if (window.parent.permissions.indexOf(permission) > -1) {
        return true;
    } else {
        return false;
    }
}

//重写alert
window.alert = function(msg, callback){
	parent.layer.alert(msg, function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//重写confirm式样框
window.confirm = function(msg, callback){
	parent.layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }
    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    return grid.getGridParam("selarrrow");
}


/*daiglog*/
dialogOpen = function(opt){
    var defaults = {
        id : 'layerForm',
        title : '',
        width: '',
        height: '',
        url : null,
        scroll : false,
        data : {},
        btn: ['确定', '取消'],
        success: function(){},
        yes: function(){}
    }
    var option = $.extend({}, defaults, opt), content = null;
    if(option.scroll){
        content = [option.url]
    }else{
        content = [option.url, 'no']
    }
    top.layer.open({
        type : 2,
        id : option.id,
        title : option.title,
        closeBtn : 1,
        anim: -1,
        isOutAnim: false,
        shadeClose : false,
        shade : 0.3,
        area : [option.width, option.height],
        content : content,
        btn: option.btn,
        success: function(){
            option.success(option.id);
        },
        yes: function(){
            option.yes(option.id);
        }
    });
}

dialogContent = function(opt){
    var defaults = {
        title : '系统窗口',
        width: '',
        height: '',
        content : null,
        data : {},
        btn: ['确定', '取消'],
        success: null,
        yes: null
    }
    var option = $.extend({}, defaults, opt);
    return top.layer.open({
        type : 1,
        title : option.title,
        closeBtn : 1,
        anim: -1,
        isOutAnim: false,
        shadeClose : false,
        shade : 0.3,
        area : [option.width, option.height],
        shift : 5,
        content : option.content,
        btn: option.btn,
        success: option.success,
        yes: option.yes
    });
}

dialogAjax = function(opt){
    var defaults = {
        title : '系统窗口',
        width: '',
        height: '',
        url : null,
        data : {},
        btn: ['确定', '取消'],
        success: null,
        yes: null
    }
    var option = $.extend({}, defaults, opt);
    $.post(option.url, null, function(content){
        layer.open({
            type : 1,
            title : option.title,
            closeBtn : 1,
            anim: -1,
            isOutAnim: false,
            shadeClose : false,
            shade : 0.3,
            area : [option.width, option.height],
            shift : 5,
            content : content,
            btn: option.btn,
            success: option.success,
            yes: option.yes
        });
    });
}

dialogAlert = function (content, type) {
    var msgType = {
        success:1,
        error:2,
        warn:3,
        info:7
    };
    if(isNullOrEmpty(type)){
        type='info';
    }
    top.layer.alert(content, {
        icon: msgType[type],
        title: "系统提示",
        anim: -1,
        btnAlign: 'c',
        isOutAnim: false
    });
}

dialogConfirm = function (content, callBack) {
    top.layer.confirm(content, {
        area: '338px',
        icon: 7,
        anim: -1,
        isOutAnim: false,
        title: "系统提示",
        btn: ['确认', '取消'],
        btnAlign: 'c',
        yes: callBack
    });
}

dialogMsg = function(msg, type) {
    var msgType = {
        success:1,
        error:2,
        warn:3,
        info:7
    };
    if(isNullOrEmpty(type)){
        type='info';
    }
    top.layer.msg(msg, {
        icon: msgType[type],
        time: 2000
    });
}

dialogClose = function() {
    var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    top.layer.close(index); //再执行关闭
}

dialogLoading = function(flag) {
    if(flag){
        top.layer.load(0, {
            shade: [0.1,'#fff'],
            time: 2000
        });
    }else{
        top.layer.closeAll('loading');
    }
}