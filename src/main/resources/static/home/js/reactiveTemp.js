let cars = [];

function showReservationModal(carId) {
    fetch(`/api/cars/${carId}`, {
        method: "GET",
        headers: { "Accept": "application/json" }
    })
    .then(response => response.json())
    .then(car => {
        document.getElementById('carImageModal').src = `data:image/jpeg;base64,${car.base64Image}`;
        document.getElementById('brandModal').textContent = car.brand;
        document.getElementById('categoryModal').textContent = car.category;
        document.getElementById('priceModal').textContent = car.price;
        document.getElementById('carId').value = carId;
        document.getElementById('yearModal').textContent = car.year || '';
    });
}

function isValidDateFormat(dateStr) {
    return /^\d{4}-\d{2}-\d{2}$/.test(dateStr);
}

function reserveCar() {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    const carId = document.getElementById('carId').value;

    document.getElementById('errorReservation').innerHTML = '';
    document.getElementById('confirmationReservation').innerHTML = '';

    let errorMsg = '';
    if (!startDate || !endDate) {
        errorMsg = 'Both start and end dates are required.';
    } else if (!isValidDateFormat(startDate) || !isValidDateFormat(endDate)) {
        errorMsg = 'Dates must be in the format YYYY-MM-DD.';
    } else if (startDate > endDate) {
        errorMsg = 'Start date cannot be after end date.';
    }

    if (errorMsg) {
        document.getElementById('errorReservation').innerHTML =
            `<div class="alert alert-danger" role="alert">${errorMsg}</div>`;
        return;
    }

    const reservationData = {
        startDate: startDate,
        endDate: endDate,
        carId: carId
    };

    fetch("/api/reserve", {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(reservationData)
    })
    .then(async response => {
        if (!response.ok) {
            let message = "Reservation failed.";
            try {
                const error = await response.json();
                message = error.message || message;
            } catch {}
            document.getElementById('errorReservation').innerHTML =
                `<div class="alert alert-danger" role="alert">${message}</div>`;
        } else {
            document.getElementById('confirmationReservation').innerHTML =
                `<div class="alert alert-success" role="alert">Reservation successful!</div>`;
        }
    })
    .catch(() => {
        document.getElementById('errorReservation').innerHTML =
            `<div class="alert alert-danger" role="alert">Network error. Please try again.</div>`;
    });
}