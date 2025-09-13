<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/"/>"><img src="<c:url value="/home/assets/img/navbar-logo.svg"/>" alt="..." /></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive"
            aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            Menu <i class="fas fa-bars ms-1"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
                <li class="nav-item"><a class="nav-link" href="<c:url value="/#about"/>">About</a></li>
                <li class="nav-item"><a class="nav-link" href="<c:url value="/#contact"/>">Contact</a></li>
                <c:if test="${user eq null}">
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/login"/>">Login</a></li>
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/signup"/>">Sign up</a></li>
                </c:if>
                <c:if test="${user ne null}">
                    <li class="nav-item"><a class="nav-link"><c:out value="${user.username}"/></a></li>
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/logout"/>">Logout</a></li>
                </c:if>
                <c:if test="${user.isAdmin()}">
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/shared"/>">Dashboard ></a></li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>

