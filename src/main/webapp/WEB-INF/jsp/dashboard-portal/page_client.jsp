<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="dashboard_header.jsp" />

<div class="main-card mb-3 card">
    <div class="card-body"></div>
    <div class="main-card mb-3">
        <div class="card-body">
            <h5 class="card-title">Client List</h5>
            <table class="mb-0 table table-striped">
                <thead>
                    <tr>
                        <th>User</th>
                        <th>Email</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="client" items="${clients}">
                        <tr>
                            <td style="color: black">${client.username}</td>
                            <td style="color: black">${client.email}</td>
                            <c:if test="${not client.enabled}">
                                <td style="color: red">BANNED</td>
                            </c:if>
                            <c:if test="${client.enabled}">
                                <td>
                                    <a href="/admin/manager/ban/${client.id}" class="delete">
                                        <i class="material-icons" data-toggle="tooltip" title="Delete" style="color: #f44336;">&#xE872;</i>
                                    </a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Scripts -->


<jsp:include page="dashboard_footer.jsp" />
