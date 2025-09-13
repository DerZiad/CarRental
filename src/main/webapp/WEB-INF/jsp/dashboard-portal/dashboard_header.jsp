<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="user" scope="request" type="com.coding.app.models.User"/>

<!DOCTYPE html>
<html lang="en">
<head>

    <title>Dashboard</title>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Language" content="en">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, shrink-to-fit=no"/>
    <meta name="description" content="This is an example dashboard created using built-in elements and components.">
    <meta name="msapplication-tap-highlight" content="no">

    <link href="<c:url value="/dashboard-design/css/main.css"/>" rel="stylesheet">
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/fav.ico"/>"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <style>
        .app-header {
            background: #222 !important; /* matte black */
            color: #f5f5f5 !important;
        }
        .app-header__logo,
        .app-header__content,
        .app-header__mobile-menu,
        .app-header__menu {
            background: transparent !important;
            color: #f5f5f5 !important;
        }
        .app-header__logo img {
            filter: brightness(0.85);
        }
        .header-user-info .widget-heading,
        .header-user-info .widget-subheading,
        .app-header-right,
        .app-header__content * {
            color: #f5f5f5 !important;
        }
        .btn-group .dropdown-menu {
            background: #333;
            color: #f5f5f5;
        }
        .btn-group .dropdown-menu a {
            color: #f5f5f5;
        }
        .btn-group .dropdown-menu a:hover {
            background: #444;
        }
        /* Sidebar Anpassung */
        .app-sidebar, .app-sidebar__inner, .vertical-nav-menu, .scrollbar-sidebar {
            background: #222 !important;
            color: #f5f5f5 !important;
        }
        .vertical-nav-menu li a,
        .vertical-nav-menu li.app-sidebar__heading {
            color: #f5f5f5 !important;
        }
        .vertical-nav-menu li a.active,
        .vertical-nav-menu li a:hover {
            background: #333 !important;
            color: #fff !important;
        }
    </style>
</head>
<body>
<div class="app-container app-theme-white body-tabs-shadow fixed-sidebar fixed-header">
    <div class="app-header header-shadow">
        <div class="app-header__logo">
            <div>
                <img src="<c:url value="/images/logo.svg"/>" style="height:40px;" alt="logo">
            </div>
            <div class="header__pane ml-auto">
                <div>
                    <button type="button"
                            class="hamburger close-sidebar-btn hamburger--elastic"
                            data-class="closed-sidebar">
        					<span class="hamburger-box"> <span class="hamburger-inner"></span>
        					</span>
                    </button>
                </div>
            </div>
        </div>
        <div class="app-header__mobile-menu">
            <div>
                <button type="button"
                        class="hamburger hamburger--elastic mobile-toggle-nav">
        				<span class="hamburger-box"> <span class="hamburger-inner"></span>
        				</span>
                </button>
            </div>
        </div>
        <div class="app-header__menu">
        		<span>
        			<button type="button"
                            class="btn-icon btn-icon-only btn btn-primary btn-sm mobile-toggle-header-nav">
        				<span class="btn-icon-wrapper"> <i
                                class="fa fa-ellipsis-v fa-w-6"></i>
        				</span>
        			</button>
        		</span>
        </div>
        <div class="app-header__content">
            <div class="app-header-right">
                <div class="header-btn-lg pr-0">
                    <div class="widget-content p-0">
                        <div class="widget-content-wrapper">
                            <div class="widget-content-left">
                                <div class="widget-heading"><c:out value="${user.username}"/></div>
                            </div>
                            <div class="widget-content-right  ml-3 header-user-info">
                                <div class="btn-group">
                                    <a data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="p-0 btn"><i class="fa fa-angle-down ml-2 opacity-8"></i></a>
                                    <div tabindex="-1" role="menu" aria-hidden="true" class="dropdown-menu dropdown-menu-right">
                                        <a href="<c:url value="/logout"/>" tabindex="0" class="dropdown-item">Logout</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="app-main">
        <div class="app-sidebar sidebar-shadow">
            <div class="scrollbar-sidebar">
                <div class="app-sidebar__inner">
                    <ul class="vertical-nav-menu">
                        <li class="app-sidebar__heading">Home</li>
                        <li><a href="<c:url value="/shared"/>" class="<c:out value="${dashboardActive}"/>"><i class="metismenu-icon pe-7s-rocket"></i> Dashboard</a></li>
                        <li><a href="<c:url value="/shared/car"/>" class="<c:out value="${carActive}"/>"><i class="metismenu-icon pe-7s-id"></i> Cars</a></li>
						<li><a href="<c:url value="/shared/reservation"/>" aria-expanded="false" class="<c:out value="${reservationActive}"/>"> <i class="metismenu-icon pe-7s-id"></i> Reservations</a></li>
                        <c:if test="${user.isAdmin()}">
                            <li><a href="<c:url value="/admin/manager"/>" aria-expanded="false" class="<c:out value="${managerActive}"/>"> <i class="metismenu-icon pe-7s-id"></i> Managers</a></li>
                            <li><a href="<c:url value="/admin/client"/>" aria-expanded="false" class="<c:out value="${clientActive}"/>"> <i class="metismenu-icon pe-7s-id"></i> Clients</a></li>
                            <li><a href="<c:url value="/admin/history"/>" aria-expanded="false" class="<c:out value="${historyActive}"/>"> <i class="metismenu-icon pe-7s-id"></i> History</a></li>
                        </c:if>
                    </ul>
                </div>
            </div>
            <button type="button"
                    class="hamburger close-sidebar-btn hamburger--elastic"
                    data-class="closed-sidebar"
                    style="position: absolute; top: 10px; left: 10px; z-index: 1000;">
                <span class="hamburger-box">
                    <span class="hamburger-inner"></span>
                </span>
            </button>
        </div>
        <div class="app-main__outer">
            <div class="app-main__inner">