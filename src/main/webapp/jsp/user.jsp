<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path" />
<script type="text/javascript">
    $(function(){
        $("#userTable").jqGrid(
            {
                url : "${path}/user/queryByPage",
                datatype : "json",
                colNames : [ 'ID', '手机', '照片', '名字', '昵称','签名','性别','地址','注册时间','最后登录时间','状态' ],
                colModel : [
                    {name : 'id',index : 'id',width : 55},
                    {name : 'phone',index : 'title',width : 90,editable:true,editrules:{required:true}},
                    {name : 'photo',index : 'photo',width : 100,editable:true,edittype:"file",editoptions: {enctype:"multipart/form-data"},formatter:function (data) {
                            return "<img src='"+data+"' style='width: 180px;height: 80px;'>";
                        }},
                    {name : 'name',index : 'name',width : 80,align : "right",editable:true},
                    {name : 'nickName',index : 'nickName',width : 80,align : "right",editable:true},
                    {name : 'sign',index : 'sign',width : 80,align : "right",editable:true},
                    {name : 'sex',index : 'sex',width : 80,align : "right",editable:true,formatter: function (data) {
                            if(data=="0")
                                return "男";
                            return "女";
                        },edittype:"select",editoptions:{value: "0:男;1:女"}},
                    {name : 'location',index : 'location',width : 80,align : "right",editable:true},
                    {name : 'registerDate',index : 'registerDate',width : 80,align : "right",editable:true,edittype: "date"},
                    {name : 'lastLogin',index : 'lastLogin',width : 80,align : "right",editable:true,edittype: "date"},
                    {name : 'status',index : 'status',width : 150,sortable : false,editable:true,formatter:function (data) {
                            if(data=="1"){
                                return "展示";
                            } return "冻结";
                        },edittype:"select",editoptions: {value:"1:展示;2:冻结"}}
                ],
                autowidth:true,
                rowNum : 2,
                rowList : [ 3, 5, 10 ],
                pager : '#userPage',
                sortname : 'id',
                mtype : "get",
                viewrecords : true,
                sortorder : "desc",
                styleUI: "Bootstrap",
                caption: "用户展示",
                multiselect : true,
                editurl: "${path}/user/editUser"
            });
        jQuery("#userTable").jqGrid('navGrid', '#userPage', {edit : true,add : true,del : true,edittext: "修改",addtext: "添加",deltext: "删除"}
            ,{
                closeAfterEdit: true
            },{
                closeAfterAdd: true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                //                   editurl 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var userId = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url:"${path}/user/upload",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{userId:userId},
                        // 指定上传的input框id
                        fileElementId:"photo",
                        success:function (data) {
                            $("#userTable").trigger('reloadGrid');
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            },{
                closeAfterDel: true
            });
    });


</script>

<table id="userTable"></table>
<div id="userPage" style="height: 50px"></div>