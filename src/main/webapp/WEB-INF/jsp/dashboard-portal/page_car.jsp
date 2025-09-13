<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="dashboard_header.jsp" />

<style>
    .card-equal {
        height: 100%;
        min-height: 420px;
        display: flex;
        flex-direction: column;
        justify-content: center;
    }
    .car-preview-card {
        background: #222;
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
        min-height: 420px;
        position: relative;
    }
    .car-preview-card-inner {
        width: 100%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    @media (max-width: 900px) {
        .card-equal, .car-preview-card {
            min-height: 220px;
        }
    }
</style>

<div class="row" style="max-width: 1100px; margin: 40px auto;">
    <div class="col-md-6">
        <div class="main-card mb-3 card card-equal">
            <div class="card-body">
                <form class="form-group" action="<c:url value="/shared/car"/>" enctype="multipart/form-data" method="POST" id="carForm">
                    <h4 class="card-title mb-4 text-center">Add New Car</h4>
                    <div class="form-row">
                        <div class="col-md-6 mb-3">
                            <label for="brand">Car Brand</label>
                            <select name="brand" id="brand" class="form-control">
                                <c:forEach items="${brands}" var="brand">
                                    <option value="${brand}">${brand.displayName}</option>
                                </c:forEach>
                            </select>
                            <small class="text-danger"><c:out value="${errors.brand}"/></small>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="category">Car Category</label>
                            <select name="category" id="category" class="form-control">
                                <c:forEach items="${categories}" var="category">
                                    <option value="${category}">${category.displayName}</option>
                                </c:forEach>
                            </select>
                            <small class="text-danger"><c:out value="${errors.category}"/></small>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-md-6 mb-3">
                            <label for="year">Manufacturing Year</label>
                            <select name="year" id="year" class="form-control">
                                <c:forEach items="${years}" var="year">
                                    <option value="${year}">${year}</option>
                                </c:forEach>
                            </select>
                            <small class="text-danger"><c:out value="${errors.year}"/></small>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="price">Rental Price (€ per day)</label>
                            <div style="display: flex; align-items: center;">
                                <input type="number" name="price" id="price" class="form-control" value="${car.price}" min="0" step="0.01" style="flex:1;" />
                                <span style="margin-left:8px;font-size:1.2em;">€</span>
                            </div>
                            <small class="text-danger"><c:out value="${errors.price}"/></small>
                        </div>
                    </div>
                    <div class="form-row align-items-center mb-3">
                        <div class="col-md-12">
                            <label for="partFile">Car Image</label>
                            <input type="file" id="partFile" name="partFile" class="form-control" accept="image/*" />
                            <small class="text-danger" id="imageError"><c:out value="${errors.image}"/></small><br>
                            <small style="color: #888;">Max. file size: 10MB</small>
                        </div>
                    </div>
                    <div class="text-center">
                        <button class="btn btn-primary px-5" type="submit" style="background-color: #222 !important; color: #fff !important; font-weight: bold !important; border: none;">Add Car</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="main-card mb-3 card car-preview-card">
            <div class="car-preview-card-inner" id="previewBg"></div>
        </div>
    </div>
</div>

<!-- Car List mit Filter -->
<div class="main-card mb-3 card">
    <div class="card-body">
        <h5 class="card-title">Car List</h5>
        <div class="mb-3">
            <input type="text" id="carSearchInput" class="form-control" placeholder="Search by brand, category, year..." onkeyup="filterCarTable()" />
        </div>
        <table class="mb-0 table table-striped" id="carTable">
            <thead>
                <tr>
                    <th>Image</th>
                    <th>Brand</th>
                    <th>Category</th>
                    <th>Year</th>
                    <th>Rental Price</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="carItem" items="${cars}">
                    <tr>
                        <td>
                            <img src="data:image/jpeg;base64,${carItem.base64Image}" alt="Car" style="width:60px; height:40px; object-fit:contain; border:1px solid #ddd; background:#fafafa;" />
                        </td>
                        <td style="color: black">${carItem.brand.displayName}</td>
                        <td style="color: black">${carItem.category.displayName}</td>
                        <td style="color: black">${carItem.year}</td>
                        <td style="color: black">${carItem.price} €</td>
                        <td>
                            <a href="<c:url value='/shared/car/delete/${carItem.id}'/>" class="delete" title="Delete Car">
                                <i class="material-icons" style="color: #f44336;" data-toggle="tooltip">&#xE872;</i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="dashboard_footer.jsp" />

<script>
function setPreviewBackground(src) {
    const previewBg = document.getElementById('previewBg');
    if (src) {
        previewBg.style.backgroundImage = 'url(' + src + ')';
        previewBg.style.backgroundSize = 'contain';
        previewBg.style.backgroundRepeat = 'no-repeat';
        previewBg.style.backgroundPosition = 'center';
    } else {
        previewBg.style.backgroundImage = '';
    }
}

document.getElementById('partFile').addEventListener('change', function(event) {
    const file = event.target.files[0];
    const imageError = document.getElementById('imageError');
    imageError.textContent = "";
    if (file) {
        if (file.size > 10 * 1024 * 1024) { // 10MB
            imageError.textContent = "Image is too large. Maximum size is 10MB.";
            event.target.value = "";
            setPreviewBackground("");
            return;
        }
        const objectUrl = URL.createObjectURL(file);
        setPreviewBackground(objectUrl);
    } else {
        setPreviewBackground("");
    }
});

window.addEventListener('DOMContentLoaded', function() {
    // If editing, show existing image
    var existingImage = "${car.base64Image}";
    if (existingImage) {
        setPreviewBackground("data:image/jpeg;base64," + existingImage);
    }
});

function filterCarTable() {
    var input = document.getElementById("carSearchInput");
    var filter = input.value.toLowerCase();
    var table = document.getElementById("carTable");
    var trs = table.getElementsByTagName("tr");
    for (var i = 1; i < trs.length; i++) { // skip header
        var tds = trs[i].getElementsByTagName("td");
        var show = false;
        for (var j = 1; j < tds.length; j++) { // skip image column
            if (tds[j].textContent.toLowerCase().indexOf(filter) > -1) {
                show = true;
                break;
            }
        }
        trs[i].style.display = show ? "" : "none";
    }
}
</script>
