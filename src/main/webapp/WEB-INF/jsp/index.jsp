<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="DerZiad" />
    <title>Agency - Start Bootstrap Theme</title>
    <link rel="icon" type="image/x-icon" href="<c:url value="/images/fav.ico"/>" />
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/home/css/styles.css"/>" rel="stylesheet" />
    <style>
        .portfolio-item img.img-fluid {
            width: 100%;
            height: 220px;
            object-fit: cover;
        }
    </style>
</head>
<body id="page-top">
    <c:if test="${username ne null}">
        <input id="username" type="hidden" name="username" value="${username}" />
    </c:if>
    <%@ include file="navbar.jsp" %>
    <!-- Masthead-->
    <header class="masthead">
        <div class="container">
            <div class="masthead-subheading">Welcome to Our Car Rental Service!</div>
            <div class="masthead-heading text-uppercase">Find Your Perfect Ride</div>
            <p class="lead mt-4" style="font-size:1.25rem;">
                This website was created as a university project during a coding day. It demonstrates a simple car rental platform built for educational purposes.
            </p>
            <a class="btn btn-primary btn-xl text-uppercase" href="#portfolio">Rent Now</a>
        </div>
    </header>
    <!-- Portfolio Grid-->
    <section class="page-section bg-light" id="portfolio">
        <div class="container">
            <div class="text-center">
                <h2 class="section-heading text-uppercase">Available Cars</h2>
                <h3 class="section-subheading text-muted">Browse our current stock and choose the car that fits your needs.</h3>
            </div>
            <div class="row">
                <c:forEach items="${cars}" var="car">
                    <div class="col-lg-4 col-sm-6 mb-4">
                        <div class="portfolio-item">
                            <a class="portfolio-link" data-bs-toggle="modal" href="#carDataModal" onclick="showReservationModal(${car.id})">
                                <div class="portfolio-hover">
                                    <div class="portfolio-hover-content">
                                        <i class="fas fa-plus fa-3x"></i>
                                    </div>
                                </div>
                                <img class="img-fluid" src="data:image/jpeg;base64,${car.base64Image}" alt="..." />
                            </a>
                            <div class="portfolio-caption">
                                <div class="portfolio-caption-heading">
                                    <c:out value="${car.brand.displayName}"/> - <c:out value="${car.category.displayName}"/>
                                </div>
                                <div class="portfolio-caption-subheading text-muted">
                                    Rental Price per Day : ${car.price} €
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
    <!-- About-->
    <section class="page-section" id="about">
        <div class="container">
            <div class="text-center">
                <h2 class="section-heading text-uppercase">About Us</h2>
                <h3 class="section-subheading text-muted">Discover our journey and commitment to quality service.</h3>
            </div>
            <ul class="timeline">
                <li>
                    <div class="timeline-image">
                        <img class="rounded-circle img-fluid" src="<c:url value="/home/assets/img/about/1.jpg"/>" alt="..." />
                    </div>
                    <div class="timeline-panel">
                        <div class="timeline-heading">
                            <h4>2009-2011</h4>
                            <h4 class="subheading">Our Humble Beginnings</h4>
                        </div>
                        <div class="timeline-body">
                            <p class="text-muted">We started with a small fleet and a big vision: to make car rental easy, affordable, and reliable for everyone.</p>
                        </div>
                    </div>
                </li>
                <li class="timeline-inverted">
                    <div class="timeline-image">
                        <img class="rounded-circle img-fluid" src="<c:url value="/home/assets/img/about/2.jpg"/>" alt="..." />
                    </div>
                    <div class="timeline-panel">
                        <div class="timeline-heading">
                            <h4>March 2011</h4>
                            <h4 class="subheading">An Agency is Born</h4>
                        </div>
                        <div class="timeline-body">
                            <p class="text-muted">Our agency was officially launched, expanding our services and reaching more customers every year.</p>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="timeline-image">
                        <img class="rounded-circle img-fluid" src="<c:url value="/home/assets/img/about/3.jpg"/>" alt="..." />
                    </div>
                    <div class="timeline-panel">
                        <div class="timeline-heading">
                            <h4>December 2015</h4>
                            <h4 class="subheading">Transition to Full Service</h4>
                        </div>
                        <div class="timeline-body">
                            <p class="text-muted">We expanded our fleet and introduced new services, making us a one-stop solution for all your car rental needs.</p>
                        </div>
                    </div>
                </li>
                <li class="timeline-inverted">
                    <div class="timeline-image">
                        <img class="rounded-circle img-fluid" src="<c:url value="/home/assets/img/about/4.jpg"/>" alt="..." />
                    </div>
                    <div class="timeline-panel">
                        <div class="timeline-heading">
                            <h4>July 2020</h4>
                            <h4 class="subheading">Phase Two Expansion</h4>
                        </div>
                        <div class="timeline-body">
                            <p class="text-muted">We embraced technology and innovation, offering online booking and seamless customer experiences.</p>
                        </div>
                    </div>
                </li>
                <li class="timeline-inverted">
                    <div class="timeline-image">
                        <h4>
                            Become<br /> Part of<br /> Our Story!
                        </h4>
                    </div>
                </li>
            </ul>
        </div>
    </section>
    <!-- Footer-->
    <footer class="footer py-4">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-4 text-lg-start">Copyright &copy; Car Rental Agency 2022</div>
                <div class="col-lg-4 my-3 my-lg-0">
                    <a class="btn btn-dark btn-social mx-2" href="#!"><i class="fab fa-twitter"></i></a>
                    <a class="btn btn-dark btn-social mx-2" href="#!"><i class="fab fa-facebook-f"></i></a>
                    <a class="btn btn-dark btn-social mx-2" href="#!"><i class="fab fa-linkedin-in"></i></a>
                </div>
                <div class="col-lg-4 text-lg-end">
                    <a class="link-dark text-decoration-none me-3" href="#!">Privacy Policy</a>
                    <a class="link-dark text-decoration-none" href="#!">Terms of Use</a>
                </div>
            </div>
        </div>
    </footer>
    <div class="portfolio-modal modal fade" id="carDataModal" tabindex="-1" role="dialog" aria-hidden="true">
        <input id="carId" type="hidden" name="carId" value="" />
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content rounded-4">
                <div class="close-modal position-absolute end-0 mt-2 me-2" data-bs-dismiss="modal" style="z-index:2;">
                    <img src="<c:url value="/home/assets/img/close-icon.svg"/>" alt="Close modal" />
                </div>
                <div class="container py-4">
                    <div class="row g-4 justify-content-center align-items-stretch">
                        <c:if test="${username ne null}">
                            <div class="col-12 col-md-6 d-flex align-items-stretch">
                                <div class="card shadow-lg rounded-3 w-100 h-100 d-flex flex-column justify-content-center" style="padding:2rem; min-height:370px;">
                                    <h5 class="card-title mb-4 text-center">Reservation Dates</h5>
                                    <div class="mb-3">
                                        <label for="startDate" class="form-label">Start Date</label>
                                        <input type="date" class="form-control" id="startDate" name="startDate" />
                                    </div>
                                    <div class="mb-3">
                                        <label for="endDate" class="form-label">End Date</label>
                                        <input type="date" class="form-control" id="endDate" name="endDate" />
                                    </div>
                                    <div id="errorReservation"></div>
                                    <div id="confirmationReservation"></div>
                                    <div class="d-flex justify-content-center mt-4">
                                        <button id="reserveButton" class="btn btn-primary btn-lg text-uppercase px-5" onclick="reserveCar()">Reserve</button>
                                        <button class="btn btn-secondary btn-lg text-uppercase px-5 ms-3" data-bs-dismiss="modal" type="button">
                                            <i class="fas fa-times me-1"></i> Close
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <div class="col-12 col-md-6 d-flex align-items-stretch">
                            <div class="card shadow-lg rounded-4 w-100 h-100 d-flex flex-column justify-content-center" style="padding:2rem; background:#f8f9fa; min-height:370px;">
                                <div class="d-flex flex-row align-items-center h-100">
                                    <div class="d-flex justify-content-center align-items-center" style="height:100%;">
                                        <img id="carImageModal"
                                             class="img-fluid rounded-3 border"
                                             style="max-height:170px; width:auto; object-fit:cover; box-shadow:0 2px 8px rgba(0,0,0,0.08);"
                                             src="<c:url value="/home/assets/img/portfolio/1.jpg"/>" alt="Car Image" />
                                    </div>
                                    <div class="ms-4 flex-grow-1 d-flex align-items-center" style="height:100%;">
                                        <div class="px-3 py-3 border rounded-3 bg-white w-100" style="max-width:340px; font-size:14px;">
                                            <div class="row mb-3">
                                                <div class="col-5 text-muted fw-semibold">Brand</div>
                                                <div class="col-7 fw-bold" id="brandModal"></div>
                                            </div>
                                            <div class="row mb-3">
                                                <div class="col-5 text-muted fw-semibold">Category</div>
                                                <div class="col-7 fw-bold" id="categoryModal"></div>
                                            </div>
                                            <div class="row mb-3">
                                                <div class="col-5 text-muted fw-semibold">Year</div>
                                                <div class="col-7 fw-bold" id="yearModal"></div>
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-5 text-muted fw-semibold">Rental Price</div>
                                                <div class="col-7 fw-bold"><span id="priceModal"></span> €</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="<c:url value="/home/js/scripts.js"/>"></script>
    <script src="<c:url value="/home/js/reactiveTemp.js"/>"></script>
    <script src="<c:url value="/assets/js/jquery.js"/>"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
