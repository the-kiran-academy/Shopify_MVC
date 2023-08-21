<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/adduser.css">

<meta charset="ISO-8859-1">
<title>Add User</title>

<style type="text/css">
.msg {
	width: 200px; 
    margin: 0 auto;
    text-align: center; 
    padding: 20px; 
}
</style>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<div class="msg">
	<h4>${msg}</h4>
	</div>
	<form action="add-user" method="post">
		<div class="container register">
			<div class="row">
				<div class="col-md-3 register-left">
					<img src="/images/cart.jpeg" alt="" />
					<h3>Welcome</h3>
					<p>You are 30 seconds away from earning your own money!</p>
					
				</div>

				<div class="col-md-9 register-right">

					<div class="tab-content" id="myTabContent">
						<div class="tab-pane fade show active" id="home" role="tabpanel"
							aria-labelledby="home-tab">
							<h3 class="register-heading">Add User</h3>

							<div class="row register-form">
								<div class="col-md-6">
									<div class="form-group">
										<input type="text" class="form-control"
											placeholder="First Name *" value="" name="username" />
									</div>

									<div class="form-group">
										<input type="password" class="form-control"
											placeholder="Password *" value="" name="password" />
									</div>
									<div class="form-group">
										<input type="email" class="form-control"
											placeholder="Your Email *" value="" name="email" />
									</div>

									<div class="form-group">
										<div class="maxl">
											<label class="radio inline"> <input type="radio"
												name="gender" value="male" checked> <span>
													Male </span>
											</label> <label class="radio inline"> <input type="radio"
												name="gender" value="female"> <span>Female </span>
											</label>
										</div>

									</div>
								</div>
								<div class="col-md-6">

									<div class="form-group">
										<input type="text" minlength="10" maxlength="10"
											name="txtEmpPhone" class="form-control"
											placeholder="Your Phone *" value="" name="phone" />
									</div>
									<div class="form-group">
										<select class="form-control" name="question">
											<option class="hidden" selected disabled>Please
												select your Sequrity Question</option>
											<option>What is your Birthdate?</option>
											<option>What is Your old Phone Number</option>
											<option>What is your Pet Name?</option>
										</select>
									</div>
									<div class="form-group">
										<input type="text" class="form-control"
											placeholder="Enter Your Answer *" value="" name="answer" />
									</div>
									<div class="form-group">
										<select class="form-control" name="role">
											<option class="hidden" selected disabled>Please
												select your Role</option>
											<option>Admin</option>
											<option>Sales Staff</option>
										</select>
									</div>
									<input type="submit" class="btnRegister" value="Add User" />
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>

		</div>
	</form>

	<jsp:include page="footer.jsp" />
</body>
</html>