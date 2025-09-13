<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <title>Login</title>
    <link href="<c:url value="/home/css/styles.css"/>" rel="stylesheet" />
    <link href="<c:url value="/home/css/additional.css"/>" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js" crossorigin="anonymous"></script>
</head>

<body>
    <%@ include file="navbar.jsp" %>
    <!-- Login Form -->
    <section class="page-section" style="margin-top:120px">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-6 form-container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Login</h2>
                    </div>
                    <div class="text-center">
                        <h3 class="section-subheading text-muted">Welcome back! Please log in</h3>
                    </div>

                    <!-- Error Box -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <c:out value="${error}" />
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="/login" method="post">
                        <div class="form-group mb-3">
                            <input class="form-control" type="text" name="username" placeholder="Username" required />
                        </div>
                        <div class="form-group mb-3">
                            <input class="form-control" type="password" name="password" placeholder="Password" required />
                        </div>
                        <div class="text-center">
                            <button class="btn btn-primary btn-xl text-uppercase" type="submit">Login</button>
                        </div>
                    </form>
                    <div class="text-center mt-3">
                        <p>Don't have an account? <a href="/signup">Sign up here</a></p>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
