$(document).ready(function() {
	$("#errComputerName").hide();
	$("#errIntroduced").hide(); // Initially hiding the error spans
	$("#errDiscontinued").hide();

	$("#errEmail").hide();
	$("#errPassword").hide();
	$("#errUsername").hide();

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

	$("#btnSignIn").click(function() {
		var email = $("#email").val();
		var password = $("#password").val();

		if (email == null || email == "") {
			$("#errEmail").show();
			return false;
		}
		if (password == null || password == "") {
			$("#errPassword").show();
			return false;
		}
		return true;
	})

	$("#btnSignUp").click(function() {
		var email = $("#email").val();
		var password = $("#password").val();
		var username = $("#username").val();

		if (email == null || email == "") {
			$("#errEmail").show();
			return false;
		}
		if (password == null || password == "") {
			$("#errPassword").show();
			return false;
		}
		
		if (username == null || username == "") {
			$("#errUsername").show();
			return false;
		}

		return true;
	})

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

	$("#email").change(function() {
		$("#errEmail").hide();
		return true;
	});

	$("#password").change(function() {
		$("#errPassword").hide();
		return true;
	});

	$("#username").change(function() {
		$("#errUsername").hide();
		return true;
	});

});