<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="dashboard_header.jsp" />

<div class="container-fluid py-4" style="background-color: #f8f9fa;">
    <div class="row mb-4">
        <div class="col-12">
            <div class="d-flex align-items-center mb-2">
                <img src="<c:url value='/images/logo.png'/>" alt="Logo" style="height:48px;width:48px;" class="me-3 rounded shadow-sm">
                <div>
                    <h2 class="fw-bold mb-0 text-primary">Car Rental Admin Dashboard</h2>
                    <p class="text-muted mb-0">Comprehensive overview and management of your car rental business. Track performance, monitor reservations, and manage clients efficiently.</p>
                </div>
            </div>
        </div>
    </div>
    <div class="row g-4">
        <div class="col-md-3">
            <div class="card shadow border-0 text-center bg-primary text-white">
                <div class="card-body">
                    <div class="mb-2">
                        <i class="bi bi-car-front-fill fs-2 text-white"></i>
                    </div>
                    <h6 class="card-title fw-bold">Total Cars</h6>
                    <p class="card-text text-white-50 small">Current fleet size</p>
                    <span class="display-6 fw-bold"><c:out value="${dashboardData.totalCars()}"/></span>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card shadow border-0 text-center bg-success text-white">
                <div class="card-body">
                    <div class="mb-2">
                        <i class="bi bi-people-fill fs-2 text-white"></i>
                    </div>
                    <h6 class="card-title fw-bold">Clients</h6>
                    <p class="card-text text-white-50 small">Registered users</p>
                    <span class="display-6 fw-bold"><c:out value="${dashboardData.totalUsers()}"/></span>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card shadow border-0 text-center bg-warning text-dark">
                <div class="card-body">
                    <div class="mb-2">
                        <i class="bi bi-calendar-check-fill fs-2 text-dark"></i>
                    </div>
                    <h6 class="card-title fw-bold">Reservations</h6>
                    <p class="card-text text-dark small">Total bookings</p>
                    <span class="display-6 fw-bold">${dashboardData.totalReservations()}</span>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card shadow border-0 text-center bg-danger text-white">
                <div class="card-body">
                    <div class="mb-2">
                        <i class="bi bi-cash-coin fs-2 text-white"></i>
                    </div>
                    <h6 class="card-title fw-bold">Monthly Revenue</h6>
                    <p class="card-text text-white-50 small">Current month's earnings</p>
                    <span class="display-6 fw-bold">${dashboardData.totalGainOfTheMonth()} €</span>
                </div>
            </div>
        </div>
    </div>
    <div class="row g-4 mt-4">
        <div class="col-md-6">
            <div class="card border-0 shadow bg-success text-white">
                <div class="card-body">
                    <div class="d-flex align-items-center">
                        <i class="bi bi-check-circle-fill fs-3 text-white me-3"></i>
                        <div>
                            <h6 class="fw-bold mb-1">Completed Reservations</h6>
                            <p class="mb-0 text-white-50 small">Successfully finalized bookings</p>
                        </div>
                        <span class="ms-auto badge bg-light text-success fs-5">${dashboardData.totalCompletedReservations()}</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card border-0 shadow bg-warning text-dark">
                <div class="card-body">
                    <div class="d-flex align-items-center">
                        <i class="bi bi-hourglass-split fs-3 text-dark me-3"></i>
                        <div>
                            <h6 class="fw-bold mb-1">Active Reservations</h6>
                            <p class="mb-0 text-dark small">Currently ongoing bookings</p>
                        </div>
                        <span class="ms-auto badge bg-light text-warning fs-5">${dashboardData.totalInProgressReservations()}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row g-4 mt-4">
        <div class="col-12">
            <div class="card border-0 shadow bg-white">
                <div class="card-header fw-bold bg-primary text-white">
                    Top Clients by Reservations
                </div>
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead>
                            <tr>
                                <th class="text-center">Username</th>
                                <th class="text-center">Email</th>
                                <th class="text-center">Reservations</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${dashboardData.topFiveUsersByReservations().keySet()}" var="user">
                            <tr>
                                <td class="text-center">${user.username}</td>
                                <td class="text-center">${user.email}</td>
                                <td class="text-center">
                                    <span class="badge bg-primary text-white">${dashboardData.topFiveUsersByReservations().get(user)}</span>
                                </td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="dashboard_footer.jsp" />
