<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="dashboard_header.jsp" />

<div class="main-card mb-3 card">
    <div class="card-body">
        <h4 class="card-title">Reservation Control Panel</h4>
        <div class="row mb-3">
            <div class="col-md-3">
                <label for="filterStartDate">Start Date</label>
                <input type="date" id="filterStartDate" class="form-control" />
            </div>
            <div class="col-md-3">
                <label for="filterEndDate">End Date</label>
                <input type="date" id="filterEndDate" class="form-control" />
            </div>
            <div class="col-md-2">
                <label for="filterStatus">Status</label>
                <select id="filterStatus" class="form-control">
                    <option value="">All</option>
                    <option value="ongoing">Ongoing</option>
                    <option value="completed">Completed</option>
                </select>
            </div>
            <div class="col-md-4">
                <label for="filterText">Search</label>
                <input type="text" id="filterText" class="form-control" placeholder="Username or Car Info" />
            </div>
        </div>
        <div class="mb-2">
            <span id="reservationCount" class="badge bg-info" style="font-size:16px;">
                Total Reservations: <span id="reservationCountNumber">${fn:length(reservations)}</span>
            </span>
        </div>
        <div class="main-card mb-3">
            <div class="card-body">
                <table id="reservationTable" class="mb-0 table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>User</th>
                            <th>Car Information</th>
                            <th>Car Price (€)</th>
                            <th>Reservation Date</th>
                            <th>Return Date</th>
                            <th>Total Revenue (€)</th>
                            <th>Reservation Status</th>
                            <th>Confirmation</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="reservation" items="${reservations}">
                            <tr>
                                <td style="color: black">${reservation.user.username}</td>
                                <td style="color: black">
                                    ${reservation.car.category.displayName} - ${reservation.car.brand.displayName}
                                </td>
                                <td style="color: black">${reservation.car.price}</td>
                                <td style="color: black">${reservation.startDay}</td>
                                <td style="color: black">${reservation.endDay}</td>
                                <td style="color: black">
                                    <c:set var="days" value="${(reservation.endDay.time - reservation.startDay.time) / (1000*60*60*24) + 1}" />
                                    <c:out value="${reservation.car.price * days}" />
                                </td>
                                <td style="color: black">
                                    <c:choose>
                                        <c:when test="${reservation.isOngoing()}">
                                            <span class="badge bg-warning text-dark">Ongoing</span>
                                        </c:when>
                                        <c:when test="${reservation.completed()}">
                                            <span class="badge bg-success">Completed</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">Pending</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${reservation.confirmed}">
                                            <span class="badge bg-success">Confirmed</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger">Not Confirmed</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${!reservation.confirmed}">
                                        <a href="<c:url value='/shared/reservation/accept/${reservation.car.id}/${reservation.user.username}'/>"
                                           class="btn btn-success btn-sm" title="Verify Reservation">
                                            <i class="fa fa-check"></i>
                                        </a>
                                    </c:if>
                                    <a href="<c:url value='/shared/reservation/delete/${reservation.car.id}/${reservation.user.username}'/>"
                                       class="btn btn-danger btn-sm" title="Delete Reservation" style="margin-left:5px;">
                                        <i class="fa fa-trash"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="dashboard_footer.jsp" />
<script src="<c:url value='/dashboard-design/js/reservation-filter.js'/>"></script>
