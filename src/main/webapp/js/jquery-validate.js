$(document).ready(function() {
	$("#errComputerName").hide();
	$("#errIntroduced").hide(); // Initially hiding the error spans
	$("#errDiscontinued").hide();
	$("#btnEditOrAddComputer").click(function() {
		var name = $("#name").val();
		var introduced = $("#introducedDate").val();
		var discontinued = $("#discontinuedDate").val();

		var dateFormat = /(^$|[0-9]{4}[/][0-9]{2}[/][0-9]{2}$)/;

		if (name == null || name == "") {
			$("#errComputerName").show();
			return false;
		}

		if (introduced == null || !(introduced.match(dateFormat))) {
			$("#errIntroduced").show();
			return false;
		}

		if (discontinued == null || !(discontinued.match(dateFormat))) {
			$("#errDiscontinued").show();
			return false;
		}

		return true;
	});

	$("#name").change(function() {
		$("#errComputerName").hide();
		return true;
	});

	$("#introducedDate").change(function() {
		$("#errIntroduced").hide();
		return true;
	});

	$("#discontinuedDate").change(function() {
		$("#errDiscontinued").hide();
		return true;
	});

});