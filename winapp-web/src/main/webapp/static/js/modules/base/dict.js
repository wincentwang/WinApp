/**
 * 通用字典js
 */

$(function () {
	initialPage();
	getGrid();
});


var setting = {
    data : {
        simpleData : {
            enable : true,
            idKey : "dictId",
            pIdKey : "parentId",
            rootPId : -1
        },
        key : {
            url : "nourl"
        }
    }
};
var ztree;


function initialPage() {
	$(window).resize(function() {
	//	TreeGrid.table.resetHeight({height: $(window).height()-100});
	});
}

function getGrid() {
	var colunms = TreeGrid.initColumn();
    var table = new TreeTable(TreeGrid.id, '/sys/dict/list?_' + $.now(), colunms);
    table.setExpandColumn(2);
    table.setIdField("dictId");
    table.setCodeField("dictId");
    table.setParentCodeField("parentId");
    table.setExpandAll(true);
    table.setHeight($(window).height()-100);
    table.init();
    TreeGrid.table = table;
}

var vm = new Vue({
	el:'#WinApp',
	data:{
        dict:{
            parentName: null,
            parentId: 0,
            type: 1,
            orderNum: 0,
            status: 1
        }
	},
	methods : {
		load: function() {
			TreeGrid.table.refresh();
		},
		save: function() {
            layer.open({
                type: 1,
                offset: '50px',
                title: "增加",
                area: ['600px', '550px'],
                shade: 0,
                shadeClose: false,
                content:$("#saveUpdateLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    vm.saveOrUpdate();
                    layer.close(index);
                }
            });
            this.getDict();
			// dialogOpen({
			// 	title: '新增字典',
			// 	content:$("#saveUpdateForm"),
			// 	width: '600px',
			// 	height: '420px',
			// 	scroll : true,
			// 	yes : function(iframeId) {
			// 		top.frames[iframeId].vm.acceptClick();
			// 	},
			// });
		},
		edit: function() {
			var ck = TreeGrid.table.getSelectedRow();
			if(checkedRow(ck)){
				dialogOpen({
					title: '编辑字典',
					url: 'base/dict/edit.html?_' + $.now(),
					width: '600px',
					height: '420px',
					scroll : true,
					success: function(iframeId){
						top.frames[iframeId].vm.dict.dictId = ck[0].id;
						top.frames[iframeId].vm.setForm();
					},
					yes : function(iframeId) {
						top.frames[iframeId].vm.acceptClick();
					},
				});
			}
		},
		remove: function() {
			var ck = TreeGrid.table.getSelectedRow(), ids = [];
			if(checkedArray(ck)){
				$.each(ck, function(idx, item){
					ids[idx] = item.id;
				});
				$.RemoveForm({
					url: '/sys/dict/remove?_' + $.now(),
			    	param: ids,
			    	success: function(data) {
			    		vm.load();
			    	}
				});
			}
		},
        dictTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择目录",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#dictTree"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztreeSelect.getSelectedNodes();
                    //选择上级部门
                    vm.dict.dictId = node[0].deptId;
                    vm.dict.name = node[0].name;
                    layer.close(index);
                }
            });
        },
        saveUpdate: function() {
            if (!$('#form').Validform()) {
                return false;
            }
            $.SaveForm({
                url: '/sys/dict/save?_' + $.now(),
                param: vm.dict,
                success: function(data) {
                    $.currentIframe().vm.load();
                }
            });
        },
        getDict : function() {
            $.get('/sys/dict/select', function(r) {
                ztree = $.fn.zTree
                    .init($("#dictTree"), setting, r);
                var node = ztree.getNodeByParam("dictId", vm.dict.parentId);
                ztree.selectNode(node);
                //vm.dict.typeName = node.name;
            })
        },
        acceptClick: function() {
            var node = ztree.getSelectedNodes();
            top.layerForm.vm.macro.typeId = node[0].dictId;
            top.layerForm.vm.macro.typeName = node[0].name;
            dialogClose();
        }
	}
})

var TreeGrid = {
    id: "dataGrid",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TreeGrid.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '编号', field: 'dictId', visible: false, align: 'center', valign: 'middle', width: '50px'},
        {title: '参数名', field: 'name', align: 'center', valign: 'middle', width: '180px'},
        {title: '参数值', field: 'value', align: 'center', valign: 'middle', width: '180px'},
        {title: '所属类别', field: 'typeName', align: 'center', valign: 'middle', width: '100px'},
        {title: '类型', field: 'type', align: 'center', valign: 'middle', width: '60px', formatter: function(item, index){
        	 if(item.type === 0){
                 return '<span class="label label-primary">目录</span>';
             }
             if(item.type === 1){
                 return '<span class="label label-warning">参数</span>';
             }
        }},
        {title: '排序', field: 'orderNum', align: 'center', valign: 'middle', width: '50px'},
        {title: '可用', field: 'status', align: 'center', valign: 'middle', width: '60px', formatter: function(item, index){
            if(item.status === 0){
                return '<i class="fa fa-toggle-off"></i>';
            }
            if(item.status === 1){
                return '<i class="fa fa-toggle-on"></i>';
            }
        }},
        {title: '备注', field: 'remark', align: 'left', valign: 'middle'}
    ]
    return columns;
};