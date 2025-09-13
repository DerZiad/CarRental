<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>Sign Up</title>
    <link href="<c:url value="/home/css/styles.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/home/css/additional.css"/>" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="navbar.jsp" %>
<!-- Signup Form -->
<section class="page-section" style="margin-top:100px">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-6 form-container">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase">Sign Up</h2>
                    <h3 class="section-subheading text-muted">Create your account</h3>
                </div>
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <c:out value="${errorMessage}"/>
                    </div>
                </c:if>
                <form action="<c:url value="/signup"/>" method="post">
                    <div class="form-group mb-3">
                        <input class="form-control" type="email" name="email" placeholder="Email" required/>
                        <c:if test="${not empty errors.email}">
                            <span class="text-danger"><c:out value="${errors.email}"/></span>
                        </c:if>
                    </div>
                    <div class="form-group mb-3">
                        <input class="form-control" type="text" name="username" placeholder="Username" required/>
                        <c:if test="${not empty errors.username}">
                            <span class="text-danger"><c:out value="${errors.username}"/></span>
                        </c:if>
                    </div>
                    <div class="form-group mb-3">
                        <input class="form-control" type="password" name="password" placeholder="Password" required/>
                        <c:if test="${not empty errors.password}">
                            <span class="text-danger"><c:out value="${errors.password}"/></span>
                        </c:if>
                    </div>
                    <div class="text-center">
                        <button class="btn btn-primary btn-xl text-uppercase" type="submit">Sign Up</button>
                    </div>
                </form>
                <div class="text-center mt-3">
                    <p>Already have an account? <a href="<c:url value="/login"/>">Login here</a></p>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
