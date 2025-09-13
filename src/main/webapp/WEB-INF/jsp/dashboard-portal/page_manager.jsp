<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="dashboard_header.jsp"/>

<div class="container">
    <div class="row">
        <!-- Add Manager Card -->
        <div class="col-md-6">
            <div class="main-card mb-3 card">
                <div class="card-body">
                    <!-- Add Manager Form -->
                    <form class="form-group" action="<c:url value="/admin/manager"/>" enctype="multipart/form-data" method="POST">
                        <h5 class="card-title">Add Manager</h5>
                        <div class="form-group row">
                            <label for="username" class="col-sm-3 col-form-label">Username</label>
                            <div class="col-sm-9">
                                <input value="<c:out value="${manager.username != null ? manager.username : ''}"/>" name="username" id="username" class="form-control"
                                       pattern=".{8,}" title="At least 8 characters" required/>
                                <p style="color: red;">
                                    <c:out value="${errors.username != null ? errors.username : ''}"/>
                                </p>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="email" class="col-sm-3 col-form-label">Email</label>
                            <div class="col-sm-9">
                                <input name="email" id="email" class="form-control" value="<c:out value="${manager.email != null ? manager.email : ''}"/>" type="email" required/>
                                <p style="color: red;">
                                    <c:out value="${errors.email != null ? errors.email : ''}"/>
                                </p>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-12 text-center">
                                <button
                                    class="mt-2 btn btn-primary"
                                    type="submit"
                                    style="background-color: #222 !important; color: #fff !important; font-weight: bold !important; border: none;"
                                >
                                    Save
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- Manager List Card -->
        <div class="col-md-6">
            <div class="main-card mb-3 card">
                <div class="card-body">
                    <h5 class="card-title">Manager List</h5>
                    <form id="managerFilterForm" onsubmit="return false;">
                        <div class="form-row">
                            <div class="col-md-12">
                                <input type="text" id="filterInput" class="form-control" placeholder="Search Username or Email">
                            </div>
                        </div>
                    </form>
                    <table id="managerTable" class="mb-0 table table-striped">
                        <thead>
                        <tr>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="manager" items="${managers}">
                            <tr>
                                <td style="color: black">${manager.username}</td>
                                <td style="color: black">${manager.email}</td>
                                <c:if test="${not manager.enabled}">
                                    <td style="color: red">BANNED</td>
                                </c:if>
                                <c:if test="${manager.enabled}">
                                    <td>
                                        <a href="<c:url value='/admin/ban?username=${manager.username}&amp;userType=manager'/>" class="delete">
                                            <i class="material-icons" data-toggle="tooltip" title="Ban Manager" style="color: #f44336;">&#xE872;</i>
                                        </a>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <script>
                        document.getElementById('filterInput').addEventListener('input', function() {
                            var filter = this.value.toLowerCase();
                            var rows = document.querySelectorAll('#managerTable tbody tr');
                            rows.forEach(function(row) {
                                // Select the first two td elements for username and email
                                var tds = row.querySelectorAll('td');
                                if (tds.length < 2) {
                                    row.style.display = '';
                                    return;
                                }
                                var username = tds[0].textContent.toLowerCase();
                                var email = tds[1].textContent.toLowerCase();
                                if (username.includes(filter) || email.includes(filter)) {
                                    row.style.display = '';
                                } else {
                                    row.style.display = 'none';
                                }
                            });
                        });
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="dashboard_footer.jsp"/>
