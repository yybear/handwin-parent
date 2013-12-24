<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../include.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />

<div class="container">
	<div class="row">
		<div class="col-md-2">
			<ul class="nav nav-pills nav-stacked">
				<li><a href="${ctx}/console">未回复用户反馈</a></li>
				<li class="active"><a href="#">已回复用户反馈</a></li>
			</ul>
		</div>
		<div class="col-md-10">
			<table class="table table-striped">
				<thead>
		          <tr>
		            <th>用户账号</th>
		            <th>反馈内容</th>
		            <th>反馈时间</th>
		            <th>回复内容</th>
		            <th>回复时间</th>
		            <th>操作</th>
		          </tr>
		        </thead>
		        <tbody>
		        <c:forEach var="fd" items="${page.list}">
			        <tr>
			            <td>${fd.columns.account}</td>
			            <td title="${fd.columns.content}">
			            <c:choose>
			            	<c:when test="${fn:length(fd.columns.content) > 20}">   
				      			<c:out value="${fn:substring(fd.columns.content, 0, 20)}..." />   
				     		</c:when>   
				     		<c:otherwise>   
				      			<c:out value="${fd.columns.content}" />   
				     		</c:otherwise>  	
			            </c:choose>
     					</td>
     					<td><fmt:formatDate value="${fd.columns.create_at}" type="date"/> </td>
     					<td title="${fd.columns.reply_content}">
			            <c:choose>
			            	<c:when test="${fn:length(fd.columns.reply_content) > 20}">   
				      			<c:out value="${fn:substring(fd.columns.reply_content, 0, 20)}..." />   
				     		</c:when>   
				     		<c:otherwise>   
				      			<c:out value="${fd.columns.reply_content}" />   
				     		</c:otherwise>  	
			            </c:choose>
     					</td>
			            <td><fmt:formatDate value="${fd.columns.reply_at}" type="date"/> </td>
			            <td>
			            	<a href="javascript:void(0);" class="replyLink" rel="${fd.columns.content}" fc="${fd.columns.reply_content}" fid="${fd.columns.id}">编辑</a>&nbsp;|&nbsp;
			            	<a class="delFdLink" rel="${fd.columns.id}" href="javascript:void(0);">删除</a>
			            </td>
			        </tr>
				</c:forEach>
		          
		        </tbody>
			</table>
			
			<%@ include file="../../../pagination.jsp"%>
		</div>
	</div>
	
	<div class="modal fade" id="replyDiv" tabindex="-1" role="dialog" aria-labelledby="replyDivLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="replyDivLabel">回复用户反馈</h4>
				</div>
				<div class="modal-body">
					<p id="feedback_content"></p>
					<input type="hidden" id="feedback_id">	
					<textarea id="replay_content" class="form-control" rows="3"></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="saveReply" class="btn btn-primary" data-dismiss="modal">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="delAlertDiv" tabindex="-1" role="dialog" aria-labelledby="delAlertLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="delAlertLabel">删除用户反馈</h4>
				</div>
				<div class="modal-body">
					是否确定删除该用户反馈？
					<input type="hidden" id="del_feedback_id">	
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="comfirmDel" class="btn btn-primary" data-dismiss="modal">确定</button>
				</div>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	var ctx = "${ctx}";
	
	$('.replyLink').click(function(e) {
		var content = $(this).attr("rel");
		var fid = $(this).attr("fid");
		var m = $('#replyDiv');
		var replyContent = $(this).attr("fc");
		m.on('show.bs.modal', function () {
			$("#feedback_content").text(content);
			$("#feedback_id").attr("value", fid);
			$("#replay_content").val(replyContent);
        }).modal();
		e.preventDefault();
	});
	
	$('#saveReply').click(function(e) {
		var content = $("#replay_content").val();
		var fid = $("#feedback_id").attr("value");
		$.post(ctx+"/console/feedback/reply/", {'fid':fid, 'content':content}, function(data) {
			window.location.reload(true);
		});
	});
	
	$('.delFdLink').click(function(e) {
		var fid = $(this).attr("rel");
		var m = $('#delAlertDiv');
		m.on('show.bs.modal', function () {
			$("#del_feedback_id").attr("value", fid);
        }).modal();
		e.preventDefault();
	});
	
	$('#comfirmDel').click(function(e) {
		var id = $("#del_feedback_id").attr("value");
		$.get(ctx+"/console/feedback/remove/" +id, function(data) {
			window.location.reload(true);
		});
	});
</script>
