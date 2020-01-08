<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#bannerTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/banner/queryByPage",
                datatype : "json",
                colNames : [ 'ID', '标题', '图片','超链接','创建日期','描述','状态'],
                colModel : [
                    {name : 'id',align:"center",hidden:true},
                    {name : 'title',align:"center",editable:true,editrules:{required:true}},

                    {name : 'url',align:"center",formatter:function(data){
                            return"<img style='width: 180px;height: 80px' src='"+data+"'>"
                        },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}},
                    {name : 'href',align:"center",editable:true},
                    {name : 'createDate',align:"center",editable:true,editrules:{required:true},edittype:"date"},
                    {name : 'description',align:"center",editable:true,editrules:{required:true}},
                    {name : 'status',align:"center",editable:true,formatter:function(data){
                            if(data=="1"){
                                return "展示";

                            }else return "冻结";

                        },editable:true,editrules:{
                            required:true},edittype:"select",editoptions:{value:"1:展示;2:冻结"}}
                ],
                rowNum : 5,
                rowList : [ 10, 20, 30 ],
                pager : '#bannerPage',
                sortname : 'id',
                mtype : "post",
                viewrecords : "true",
                sortorder : "desc",
                caption : "轮播图详细信息",
                styleUI: "Bootstrap",
                autowidth: true,
                multiselect:true,
                height:"500px",
                editurl: "${pageContext.request.contextPath}/banner/save"
            });
        $("#bannerTable").jqGrid('navGrid', '#bannerPage', {edit : true,add : true,del : true,edittext:"编辑",addtext:"添加",deltext:"删除"},
            {
                closeAfterEdit:true,
            },{
                closeAfterAdd:true,
                afterSubmit:function(response,postData){
                    var bannerId=response.responseJSON.bannerId;

                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/uploadBanner",
                        type:"post",
                        datatype:"json",
                        data:{bannerId:bannerId},
                        fileElementId:"url",
                        success:function(data){
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },{
                closeAfterDel:true
            });
    });

    function exportBanner() {
        location.href = "${pageContext.request.contextPath}/banner/exportBanner"
    }

    function importBanner(){
        $.ajaxFileUpload({
                url: "${pageContext.request.contextPath}/banner/importBanner",
                datatype:"json",
                type: "post",
                fileElementId:"inputBanner",
                success :function (data) {

                        alert("导入成功");
                        $("#myModal2").modal("hide");
                        $("#bannerTable").trigger("reloadGrid");
                }
            }
        )
    }


</script>
<div class="page-header">
    <h4>轮播图管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>轮播图信息</a></li>
    <li><a onclick="exportBanner()">轮播图导出</a></li>
    <li><a data-toggle="modal" data-target="#myModal2">轮播图导入</a></li>
</ul>
<table id="bannerTable"></table>
<div id="bannerPage" style="height: 50px"></div>