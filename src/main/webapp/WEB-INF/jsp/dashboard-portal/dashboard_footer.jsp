<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

				</div>
				<div class="app-wrapper-footer">
					<footer class="app-footer" style="background:#333; color:#f5f5f5; padding:18px 0; border-top:1px solid #444;">
						<div style="text-align:center;">
							<span style="font-size:15px; font-weight:500;">&copy; <c:out value="MyCar"/> 2022. All rights reserved.</span>
							<br>
							<span style="font-size:12px; color:#aaa;">Powered by DerZiad</span>
						</div>
					</footer>
				</div>
			</div>
		</div>
	</div>
	<script src="<c:url value="/dashboard-design/js/Theme/script.js"/>"></script>
	<script src="<c:url value="/dashboard-design/js/hotel/template.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/dashboard-design/assets/scripts/main.js"/>"></script>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
	google.charts.load('current', {
		'packages': ['bar']
	});
	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {
		var data = google.visualization.arrayToDataTable([
			['Country', 'Reservations made'],
			['Reserved with confirmation', ${reservationsdonenumber}],
			['Reserved without confirmation', ${reservationsnotdonenumber}]]);

		var options = {
			chart: {
				title: 'Student Statistics',
				subtitle: 'Validated, retake and not validated for this period',
			}
		};

		var chart = new google.charts.Bar(document
				.getElementById('columnchart_material'));

		chart.draw(data, google.charts.Bar.convertOptions(options));
	}
</script>
	<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/js/standalone/selectize.min.js" integrity="sha256-+C0A5Ilqmu4QcSPxrlGpaZxJ04VjsRjKu+G82kl5UJk=" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
