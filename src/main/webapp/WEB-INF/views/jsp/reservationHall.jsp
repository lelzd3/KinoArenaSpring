<!DOCTYPE html>
<%@page import="com.kinoarena.model.dao.MovieDao"%>
<%@page import="com.kinoarena.model.dao.CinemaDao"%>
<%@page import="com.kinoarena.model.dao.BroadcastDao"%>
<%@page import="com.kinoarena.model.pojo.Movie"%>
<%@page import="com.kinoarena.model.pojo.Cinema"%>
<%@page import="com.kinoarena.model.pojo.Broadcast"%>
<html>
<head>
<title>Movie Ticket Booking Widget Flat Responsive Widget Template :: w3layouts</title>
<!-- for-mobile-apps -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta name="keywords" content="Movie Ticket Booking Widget Responsive, Login form web template, Sign up Web Templates, Flat Web Templates, Login signup Responsive web template, Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<!-- //for-mobile-apps -->
<link href='//fonts.googleapis.com/css?family=Kotta+One' rel='stylesheet' type='text/css'>
<link href='//fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic' rel='stylesheet' type='text/css'>

<!--   <link href="css/style.css" rel="stylesheet" type="text/css" media="all" /> -->
<script src="js/jquery-1.11.0.min.js"></script>
<script src="js/jquery.seat-charts.js"></script>
<style>

/*--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
--*/

/* reset */
html,body,div,span,applet,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,abbr,acronym,address,big,cite,code,del,dfn,em,img,ins,kbd,q,s,samp,small,strike,strong,sub,sup,tt,var,b,u,i,dl,dt,dd,ol,nav ul,nav li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,embed,figure,figcaption,footer,header,hgroup,menu,nav,output,ruby,section,summary,time,mark,audio,video{margin:0;padding:0;border:0;font-size:100%;font:inherit;vertical-align:baseline;}
article, aside, details, figcaption, figure,footer, header, hgroup, menu, nav, section {display: block;}
ol,ul{list-style:none;margin:0px;padding:0px;}
blockquote,q{quotes:none;}
blockquote:before,blockquote:after,q:before,q:after{content:'';content:none;}
table{border-collapse:collapse;border-spacing:0;}
/* start editing from here */
a{text-decoration:none;}
.txt-rt{text-align:right;}/* text align right */
.txt-lt{text-align:left;}/* text align left */
.txt-center{text-align:center;}/* text align center */
.float-rt{float:right;}/* float right */
.float-lt{float:left;}/* float left */
.clear{clear:both;}/* clear float */
.pos-relative{position:relative;}/* Position Relative */
.pos-absolute{position:absolute;}/* Position Absolute */
.vertical-base{	vertical-align:baseline;}/* vertical align baseline */
.vertical-top{	vertical-align:top;}/* vertical align top */
nav.vertical ul li{	display:block;}/* vertical menu */
nav.horizontal ul li{	display: inline-block;}/* horizontal menu */
img{max-width:100%;}
/*end reset*/

body{
	padding:0;
	margin:0;
 /*background:url(./img/banner2.jpg) no-repeat center;*/
  	background-color: khaki;
    background-size: cover;
    -webkit-background-size: cover;
    -o-background-size: cover;
    -ms-background-size: cover;
    -moz-background-size: cover;
	font-family: 'Open Sans', sans-serif !important;
}

h1,h2,h3,h4,h5,h6{
	margin:0;	
font-family: 'Kotta One', serif;
}	
p{
	margin:0;
}
ul{
	margin:0;
	padding:0;
}
label{
	margin:0;
}
/*-- main --*/
.content{
	padding:50px 0;
}
.content h1{
    color: #fff;
    font-size: 38px;
    text-align: center;
    letter-spacing: 1px;
    text-transform: uppercase;
}
.main{
    width: 45%;
    margin: 45px auto;
    background:rgba(255, 255, 255, 0.88);
    padding: 30px 30px;
}
.main h2 {
    color: #000000;
    font-size: 28px;
    text-align: center;
    margin-bottom: 30px;
    text-transform: capitalize;
    font-weight: 500;
}
p.copy_rights {
    color: #fff;
    font-size: 14px;
    text-align: center;
}
p.copy_rights a{
	text-decoration:none;
	color:#fff;
}
p.copy_rights a:hover{
	text-decoration:underline;
}
/*-- movie ticket --*/
.front{
    margin: 5px 4px 45px 38px;
    background-color: #D88A04;
    color: #fff;
    text-align: center;
    padding: 9px 0;
    border-radius: 3px;
	}
.booking-details {
    float: right;
    width: 38%;
}
.booking-details h3 {
	margin: 5px 5px 0 0;
	font-size: 16px;
	}
.booking-details p {
    line-height: 1.5em;
    font-size: 18px;
    color: #D88A04;
	font-weight:600;
}
.booking-details p span{
    color: #000;
    font-size: 14px;
	font-weight:normal;
}
div.seatCharts-cell {
	color: #182C4E;
    height: 29px;
    width: 29px;
    line-height: 27px;
    margin: 3px;
    float: left;
    text-align: center;
    outline: none;
    font-size: 13px;
	}
div.seatCharts-seat {
	color: #fff;
	cursor: pointer;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
	}
div.seatCharts-row {
	height: 35px;
	}
div.seatCharts-seat.available {
	background-color: #949494;
	}
div.seatCharts-seat.focused {
	background-color: #00B70C;
	border: none;
	}
div.seatCharts-seat.selected {
	background-color: #00B70C;
	}
div.seatCharts-seat.unavailable {
	background-color: #D00000;
	cursor: not-allowed;
	}
div.seatCharts-container {
    border-right: 1px solid #adadad;
    width: 54%;
    padding: 0 20px 0 0;
    float: left;
}
div.seatCharts-legend {
	padding-left: 0px;
	}
ul.seatCharts-legendList {
	padding-left: 0px;
	}
.seatCharts-legendItem{
	margin-top: 10px;
	line-height: 2;
	}
span.seatCharts-legendDescription {
	margin-left: 5px;
	line-height: 30px;
	}
.checkout-button {
    display: block;
    margin: 16px 0 22px;
    border:none;
    font-size: 16px;
    cursor: pointer;
    background: #D88A04;
    padding: 7px 11px;
    color: #fff;
	outline:none;
	transition: 0.5s all;
    -webkit-transition: 0.5s all;
    -o-transition: 0.5s all;
    -moz-transition: 0.5s all;
    -ms-transition: 0.5s all;
	}
.checkout-button:hover {
    background: #000;
	transition: 0.5s all;
    -webkit-transition: 0.5s all;
    -o-transition: 0.5s all;
    -moz-transition: 0.5s all;
	/*-- w3layouts --*/
    -ms-transition: 0.5s all;
}
#selected-seats {
	max-height: 84px;
	overflow-y: auto;
	overflow-x: none;
	width: 200px;
	}
#selected-seats li{
    border: 1px solid #d3d3d3;
    background: #f7f7f7;
    margin: 6px 0;
    font-size: 14px;
    font-weight: bold;
    text-align: center;
    color: #000;
    font-weight: 600;
    padding: 6px 11px;
    width: 50%;
	}
.scrollbar {
    overflow-y: scroll;
}
.booking-details p i {
    color: #000;
    font-size: 18px;
    line-height: 2.2em;
	font-weight:normal;
}
ul.book-left {
    float: left;
    width: 35%;
}
ul.book-right {
    float: right;
    width: 63%;
}
ul.book-left li {
    font-size: 16px;
    font-weight: 600;
    color: #D88A04;
    line-height: 1.9em;
}
ul.book-right li {
    font-size: 16px;
    font-weight: 500;
    color: #000;
    line-height: 1.9em;
}
/*-- agileits --*/
/*-- //movie ticket --*/

/*-- responsive media queries --*/

@media (max-width: 1440px){
	.main {
		width: 50%;
	}
	.content h1 {
		font-size: 33px;
	}	
}
@media (max-width: 1366px){
	.main {
		width: 53%;
	}	
}
@media (max-width: 1280px){
	.main {
		width: 56%;
	}	
}
@media (max-width: 1080px){
	.main {
		width: 67%;
	}	
}
@media (max-width: 1024px){
	div.seatCharts-container {
		width: 57%;
		padding: 0 13px 0 0;
	}
	.booking-details {
		width: 36%;
	}	
}
@media (max-width: 991px){
	div.seatCharts-container {
		width: 58%;
	}
	.content h1 {
		font-size: 31px;
	}	
}
@media (max-width: 800px){
	.main {
		width: 79%;
	}
	/*-- w3layouts --*/
	.booking-details {
		width: 33%;
	}
	div.seatCharts-container {
		width: 62%;
	}
	.front {
		margin: 5px 9px 45px 38px;
	}
	ul.book-right {
		width: 57%;
	}
	.main h2 {
		font-size: 25px;
	}	
}
@media (max-width: 768px){
	div.seatCharts-container {
		width: 60%;
	}
	.main {
		width: 85%;
	}
	.content {
		padding: 112px 0;
	}	
}
@media (max-width: 736px){
	div.seatCharts-container {
		width: 62%;
	}
	.content h1 {
		font-size: 27px;
	}
	.main h2 {
		font-size: 24px;
	}
	.content {
		padding: 74px 0;
	}	
}
@media (max-width: 667px){
	div.seatCharts-container {
		width: 100%;
		padding: 0 0px 0 0;
	    border: none;
	}	
	.booking-details {
		width: 100%;
		margin-top: 35px;
	}
	/*-- agileits --*/
	.main {
		width: 59%;
	}
	.content h1 {
		font-size: 24px;
	}
}
@media (max-width: 640px){
	.content {
		padding: 59px 0;
	}
	.main {
		width: 61%;
	}
	.front {
		margin: 5px 6px 45px 6px;
	}	
}
@media (max-width: 600px){
	.main h2 {
		font-size: 21px;
	}
	.main {
		width: 65%;
	}
	.front {
		margin: 5px 6px 36px 6px;
	}	
}
@media (max-width: 568px){
	.main {
		width: 68%;
	}	
}
@media (max-width: 480px){
	.main {
		padding: 18px 18px;
		width: 81%;
	}
	p.copy_rights {
		font-size: 13px;
		line-height: 1.8em;
		width: 90%;
		margin: 0 auto;
	}	
}
@media (max-width: 414px){
	.main {
		width: 85%;
	}
	div.seatCharts-cell {
		height: 25px;
		width: 25px;
		line-height: 26px;
		margin: 3px;
		font-size: 12px;
	}	
	.content h1 {
		font-size: 22px;
	}
	.main h2 {
		margin-bottom: 21px;
	}
}
@media (max-width: 384px){
	div.seatCharts-cell {
		height: 23px;
		width: 23px;
		line-height: 24px;
	}	
	.content {
		padding: 45px 0;
	}
}
@media (max-width: 375px){
	.main h2 {
		font-size: 20px;
	}
	div.seatCharts-cell {
		margin: 2px 3px 2px 2px;
	}
	.main {
		margin: 28px auto;
	}
	ul.book-right li ,ul.book-left li {
		font-size: 14px;
	}
	.content h1 {
		font-size: 19px;
	}
}
@media (max-width: 320px){
	.content {
		padding: 41px 0;
	}
	.main h2 {
		font-size: 17px;
	}
	.front {
		margin: 5px 6px 25px 6px;
		font-size: 14px;
		padding: 7px 0;
	}
	.content h1 {
		font-size: 18px;
	}
	div.seatCharts-cell {
		margin: 2px 2px 2px 2px;
	}
	.main {
		padding: 15px 8px;
		width: 90%;
	}
	div.seatCharts-cell {
		height: 22px;
		width: 22px;
		line-height: 22px;
		font-size: 11px;
	}
	.checkout-button {
		font-size: 14px;
	}
	span.seatCharts-legendDescription {
		line-height: 0px;
		font-size: 13px;
	}
}

</style>

</head>
<body>
<div class="content">
	<h1>Reservation Hall</h1>
	<div class="main">
		<h2> ${ cinemaName } , hall: ${ hallId } </h2>
		<div class="demo">
			<div id="seat-map">
				<div class="front">SCREEN</div>					
			</div>
			<div class="booking-details">
				<ul class="book-left">
					<li>Movie </li>
					<li>Time </li>
					<li>Tickets</li>
					<li>Total</li>
					<li>Seats :</li>
				</ul>
				<ul class="book-right">
					<li>: ${ movieTitle }</li>
					<li>: ${ broadcastProjectionTime }</li>
					<li>: <span id="counter">0</span></li>
					<li>: <b><i>$</i><span id="total">0</span></b></li>
				</ul>
				<div class="clear"></div>
				<ul id="selected-seats" class="scrollbar scrollbar1"></ul>
			
				<form action="reserveSubmit" method="post">
					<input id="hiddenSeats" name="hiddenSeats" type="hidden" value="1">
					<input id="hiddenBroadcastId" name="hiddenBroadcastId" type="hidden" value="${broadcastId}">
					<input id="submitButton" type="submit" class="checkout-button" value="Book now">
				</form>		
				<div id="legend"></div>
			</div>
			<div style="clear:both"></div>
	    </div>

			<script type="text/javascript">
			
				var price = parseFloat('${broadcastPrice}');
				//var price = 10;
				$(document).ready(function() {
					var $cart = $('#selected-seats'), //Sitting Area
					$counter = $('#counter'), //Votes
					$total = $('#total'); //Total money
					
					var sc = $('#seat-map').seatCharts({
						map: [  //Seating chart
							'aaaaaaaa',
							'aaaaaaaa',
							'aaaaaaaa',
							'aaaaaaaa',
							'aaaaaaaa',
							'aaaaaaaa',
							'aaaaaaaa',
							'aaaaaaaa',
							'aaaaaaaa',
							'aaaaaaaa'
						],
						naming : {
							top : false,
							getLabel : function (character, row, column) {
								return column;
							}
						},
						legend : { //Definition legend
							node : $('#legend'),
							items : [
								[ 'a', 'available',   'Available' ],
								[ 'a', 'unavailable', 'Sold'],
								[ 'a', 'selected', 'Selected']
							]					
						},
						click: function () { //Click event
							if (this.status() == 'available') { //optional seat
								$('<li value="1">Row'+(this.settings.row+1)+' Seat'+this.settings.label+'</li>')
									.attr('id', 'cart-item-'+this.settings.id)
									.data('seatId', this.settings.id)
									.appendTo($cart);
								console.log('<li>Row'+(this.settings.row+1)+' Seat'+this.settings.label+'</li>');
								$counter.text(sc.find('selected').length+1);
								$total.text(recalculateTotal(sc)+price);
											
								return 'selected';
							} else if (this.status() == 'selected') { //Checked
									//Update Number
									$counter.text(sc.find('selected').length-1);
									//update totalnum
									$total.text(recalculateTotal(sc)-price);
										
									//Delete reservation
									$('#cart-item-'+this.settings.id).remove();
									//optional
									return 'available';
							} else if (this.status() == 'unavailable') { //sold
								return 'unavailable';
							} else {
								return this.style();
							}
						}
					});
					document.getElementById("submitButton").onclick= function(){
						var arrayOfSeats = [];
						$("li").each(function(index){
							if(this.getAttribute("value") == "1"){
								arrayOfSeats.push($(this).text());
							}
						});
						document.getElementById("hiddenSeats").value = arrayOfSeats.join();
						console.log(document.getElementById("hiddenSeats").value);
					}
					//sold seats
					
					$(document).ready(function (){
						var xmlHttp = new XMLHttpRequest();
						xmlHttp.onreadystatechange = function(){
							if(xmlHttp.readyState == 4 && xmlHttp.status == 200){

								var x = xmlHttp.responseText;
								sc.get(x.split(", ")).status('unavailable');
							}
						}
						xmlHttp.open("GET","getReservedSeats/${broadcastId}");
						xmlHttp.send(null);
					});
					
					//sc.get(['1_3', '4_4','4_5','6_6','6_7','8_5','8_6','8_7','8_8', '10_1', '10_2']).status('unavailable');
				//	sc.get().status('unavailable');
						
				});
				//sum total money
				function recalculateTotal(sc) {
					var total = 0;
					sc.find('selected').each(function () {
						total += price;
					});
							
					return total;
				}
				
				
				
			</script>
	</div>
	
</div>
<script src="js/jquery.nicescroll.js"></script>
<script src="js/scripts.js"></script>
</body>
</html>
