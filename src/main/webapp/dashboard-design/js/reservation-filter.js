document.addEventListener('DOMContentLoaded', function () {
    const startDateInput = document.getElementById('filterStartDate');
    const endDateInput = document.getElementById('filterEndDate');
    const statusSelect = document.getElementById('filterStatus');
    const textInput = document.getElementById('filterText');
    const table = document.getElementById('reservationTable');
    const countSpan = document.getElementById('reservationCountNumber');

    function filterRows() {
        let count = 0;
        const startDate = startDateInput.value;
        const endDate = endDateInput.value;
        const status = statusSelect.value;
        const searchText = textInput.value.trim().toLowerCase();

        Array.from(table.tBodies[0].rows).forEach(row => {
            let show = true;
            const user = row.cells[0].textContent.toLowerCase();
            const carInfo = row.cells[1].textContent.toLowerCase();
            const price = row.cells[2].textContent;
            const resStart = row.cells[3].textContent;
            const resEnd = row.cells[4].textContent;
            const resStatus = row.cells[5].textContent.toLowerCase();

            if (startDate && resStart < startDate) show = false;
            if (endDate && resEnd > endDate) show = false;
            if (status) {
                if (status === "ongoing" && !resStatus.includes("ongoing")) show = false;
                if (status === "completed" && !resStatus.includes("completed")) show = false;
            }
            if (searchText && !(user.includes(searchText) || carInfo.includes(searchText))) show = false;

            row.style.display = show ? "" : "none";
            if (show) count++;
        });
        countSpan.textContent = count;
    }

    startDateInput.addEventListener('change', filterRows);
    endDateInput.addEventListener('change', filterRows);
    statusSelect.addEventListener('change', filterRows);
    textInput.addEventListener('input', filterRows);

    filterRows();
});

